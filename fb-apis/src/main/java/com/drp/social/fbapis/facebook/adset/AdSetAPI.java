package com.drp.social.fbapis.facebook.adset;

import com.drp.social.fbapis.Constants;
import com.drp.social.fbapis.Utils;
import com.facebook.ads.sdk.AdSet;
import com.facebook.ads.sdk.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by deep.patel on 11/12/16.
 */
public class AdSetAPI {

    private APIContext context;

    public AdSetAPI(String token) {
        context = new APIContext(token, Constants.APP_SECRET).enableDebug(false);
    }

    public JsonObject getAdSets(String campaignId) {
        JsonObject adsets = new JsonObject();
        Map<String, List<AdsInsights>> allInsights = new HashMap<>();
        JsonArray adSetMetadata = new JsonArray();
        JsonArray adSetArray = new JsonArray();
        Gson gson = new Gson();
        Campaign campaign = new Campaign(campaignId, context);
        APINodeList<AdSet> retrivedAdsets = null;
        try {
            retrivedAdsets = campaign.getAdSets().requestAllFields().execute();

            for (AdSet adSet : retrivedAdsets) {
                JsonObject tempMetadata = new JsonObject();
                tempMetadata.addProperty("id", adSet.getFieldId());
                tempMetadata.addProperty("name", adSet.getFieldName());
                adSetMetadata.add(tempMetadata);

                com.drp.social.fbapis.facebook.adset.AdSet temp = new com.drp.social.fbapis.facebook.adset.AdSet();
                temp.setId(adSet.getFieldId());
                temp.setName(adSet.getFieldName());
                temp.setBillingEvent(adSet.getFieldBillingEvent().toString());
                temp.setOptimizationGoal(adSet.getFieldOptimizationGoal().toString());
                temp.setDailyBudget(Double.valueOf(adSet.getFieldDailyBudget()));
                temp.setStatus(adSet.getFieldEffectiveStatus().toString());
                List<AdsInsights> adInsight = adSet.getInsights().setDatePreset(AdsInsights.EnumDatePreset.VALUE_TODAY)
                        .setFields(
                                "adset_id,reach,objective,actions,impressions,spend,unique_clicks,total_action_value,total_actions,total_unique_actions")
                        .requestAllFields().execute();
                if (adInsight.size() > 0) {
                    temp.setImpression(Long.valueOf(adInsight.get(0).getFieldImpressions()));
                    temp.setSpend(Double.valueOf(adInsight.get(0).getFieldSpend()));
                    JsonObject actionCount = new JsonObject();
                    System.out.println(adInsight);
                    System.out.println(adInsight.get(0));
                    System.out.println(adInsight.get(0).getFieldActions());
                    double cost = 0.0;
                    if (adInsight.get(0).getFieldActions() != null) {
                        for (AdsActionStats adsActionStats : adInsight.get(0).getFieldActions()) {
                            actionCount.addProperty(adsActionStats.getFieldActionType(),
                                    adsActionStats.getFieldValue());
                        }
                        System.out.println(actionCount);
                        String objective = adInsight.get(0).getFieldObjective();
                        if (objective != null && objective.equalsIgnoreCase("MOBILE_APP_INSTALLS")) {
                            if (actionCount.has("mobile_app_install"))
                                cost = Utils.round(Double.valueOf(adInsight.get(0).getFieldSpend())
                                        / actionCount.get("mobile_app_install").getAsDouble(), 2);
                            else
                                cost = 0.0;
                        } else if (objective != null && objective.equalsIgnoreCase("LINK_CLICKS")) {
                            if (actionCount.has("mobile_app_install"))
                                cost = Utils.round(Double.valueOf(adInsight.get(0).getFieldSpend())
                                        / actionCount.get("link_click").getAsDouble(), 2);
                            else
                                cost = 0.0;
                        }
                    }
                    temp.setCost(cost);
                    temp.setReach(Long.valueOf(adInsight.get(0).getFieldReach()));
                    temp.setUniqueClicks(Long.valueOf(adInsight.get(0).getFieldUniqueClicks()));
                    temp.setActions(actionCount);
                }
                JsonParser parser = new JsonParser();
                adSetArray.add(parser.parse(gson.toJson(temp, com.drp.social.fbapis.facebook.adset.AdSet.class))
                        .getAsJsonObject());

                List<AdsInsights> adsInsightsDaily =
                        adSet.getInsights().setDatePreset(AdsInsights.EnumDatePreset.VALUE_LAST_7D)
                                .setFields("adset_id,reach,objective,actions,impressions,spend,unique_clicks")
                                .setTimeIncrement("1").requestAllFields().execute();
                for (AdsInsights adsInsights : adsInsightsDaily) {
                    if (!allInsights.containsKey(adsInsights.getFieldDateStart()))
                        allInsights.put(adsInsights.getFieldDateStart(), new ArrayList<AdsInsights>());
                    allInsights.get(adsInsights.getFieldDateStart()).add(adsInsights);
                }
            }

            JsonArray spendStats = new JsonArray();
            JsonArray impressionStats = new JsonArray();
            JsonArray clickStats = new JsonArray();
            JsonArray goalCountStats = new JsonArray();
            for (Map.Entry<String, List<AdsInsights>> entry : allInsights.entrySet()) {
                String date = entry.getKey();
                List<AdsInsights> data = entry.getValue();
                JsonObject temp = new JsonObject();
                temp.addProperty("period", date);
                for (AdsInsights tempInsight : data) {
                    temp.addProperty(tempInsight.getFieldAdsetId(), Double.valueOf(tempInsight.getFieldSpend()));
                }
                spendStats.add(temp);

                JsonObject temp2 = new JsonObject();
                temp2.addProperty("period", date);
                for (AdsInsights tempInsight : data) {
                    temp2.addProperty(tempInsight.getFieldAdsetId(), Double.valueOf(tempInsight.getFieldImpressions()));
                }
                impressionStats.add(temp2);

                JsonObject temp3 = new JsonObject();
                temp3.addProperty("period", date);
                for (AdsInsights tempInsight : data) {
                    temp3.addProperty(tempInsight.getFieldAdsetId(),
                            Double.valueOf(tempInsight.getFieldUniqueClicks()));
                }
                clickStats.add(temp3);

                JsonObject temp4 = new JsonObject();
                temp4.addProperty("period", date);
                for (AdsInsights tempInsight : data) {
                    long goalCount = 0l;
                    JsonObject actionCount = new JsonObject();
                    if (tempInsight.getFieldActions() != null) {
                        for (AdsActionStats adsActionStats : tempInsight.getFieldActions()) {
                            actionCount.addProperty(adsActionStats.getFieldActionType(),
                                    adsActionStats.getFieldValue());
                        }
                        String objective = tempInsight.getFieldObjective();
                        if (objective != null && objective.equalsIgnoreCase("MOBILE_APP_INSTALLS")) {
                            if (actionCount.has("mobile_app_install"))
                                goalCount = actionCount.get("mobile_app_install").getAsLong();
                            else
                                goalCount = 0;
                        } else if (objective != null && objective.equalsIgnoreCase("LINK_CLICKS")) {
                            if (actionCount.has("link_click"))
                                goalCount = actionCount.get("link_click").getAsLong();
                            else
                                goalCount = 0;
                        }
                    }
                    temp4.addProperty(tempInsight.getFieldAdsetId(), goalCount);
                }
                goalCountStats.add(temp4);
            }
            adsets.add("spendStats", spendStats);
            adsets.add("metadata", adSetMetadata);
            adsets.add("impressionStats", impressionStats);
            adsets.add("adsets", adSetArray);
            adsets.add("clickStats", clickStats);
            adsets.add("goalCountStats", goalCountStats);
        } catch (APIException e) {
            e.printStackTrace();
        }

        return adsets;
    }
}
