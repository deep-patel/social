package com.drp.social.fbapis.facebook.campaigns;

import com.drp.social.fbapis.Constants;
import com.drp.social.fbapis.Utils;
import com.drp.social.fbapis.facebook.accounts.Account;
import com.facebook.ads.sdk.Campaign;
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
public class CampaignsAPI {

    private APIContext context;

    public CampaignsAPI(String token) {
        context = new APIContext(token, Constants.APP_SECRET).enableDebug(true);
    }

    public JsonObject getCampaigns(List<Account> accounts) {
        JsonObject campaigns = new JsonObject();
        Map<String, List<AdsInsights>> allInsights = new HashMap<>();
        JsonArray campaignMetadata = new JsonArray();
        JsonArray campaignArray = new JsonArray();
        Gson gson = new Gson();
        for (Account account : accounts) {
            APINodeList<Campaign> retrivedCampaign = null;
            try {

                retrivedCampaign = new AdAccount(account.getAccountId(), context).getCampaigns()
                        .setEffectiveStatus("[\"ACTIVE\",\"PAUSED\"]").requestAllFields().execute();

                for (Campaign campaign : retrivedCampaign) {
                    JsonObject tempMetadata = new JsonObject();
                    tempMetadata.addProperty("id", campaign.getFieldId());
                    tempMetadata.addProperty("name", campaign.getFieldName());
                    campaignMetadata.add(tempMetadata);

                    com.drp.social.fbapis.facebook.campaigns.Campaign temp = new com.drp.social.fbapis.facebook.campaigns.Campaign();
                    temp.setId(campaign.getId());
                    temp.setEffectiveStatus(campaign.getFieldEffectiveStatus().toString());
                    temp.setName(campaign.getFieldName());
                    temp.setObjective(campaign.getFieldObjective());
                    temp.setAccountId(campaign.getFieldAccountId());
                    temp.setAccountName(account.getAccountName());
                    List<AdsInsights> adInsight = campaign.getInsights()
                            .setDatePreset(AdsInsights.EnumDatePreset.VALUE_TODAY)
                            .setFields(
                                    "campaign_id,reach,objective,actions,impressions,spend,unique_clicks,total_action_value,total_actions,total_unique_actions")
                            .requestAllFields().execute();
                    if (adInsight.size() > 0) {
                        temp.setImpressions(Long.valueOf(adInsight.get(0).getFieldImpressions()));
                        temp.setSpend(Double.valueOf(adInsight.get(0).getFieldSpend()));
                        JsonObject actionCount = new JsonObject();
                        double cost = 0.0;
                        if (adInsight.get(0).getFieldActions() != null) {
                            for (AdsActionStats adsActionStats : adInsight.get(0).getFieldActions()) {
                                actionCount.addProperty(adsActionStats.getFieldActionType(),
                                        adsActionStats.getFieldValue());
                            }
                            String objective = adInsight.get(0).getFieldObjective();
                            if (objective != null && objective.equalsIgnoreCase("MOBILE_APP_INSTALLS")) {
                                if (actionCount.has("mobile_app_install"))
                                    cost = Utils.round(Double.valueOf(adInsight.get(0).getFieldSpend())
                                            / actionCount.get("mobile_app_install").getAsDouble(), 2);
                                else
                                    cost = 0.0;
                            } else if (objective != null && objective.equalsIgnoreCase("LINK_CLICKS")) {
                                if (actionCount.has("link_click"))
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
                    campaignArray.add(parser.parse(gson.toJson(temp, Campaign.class)).getAsJsonObject());

                    List<AdsInsights> adsInsightsDaily = campaign.getInsights()
                            .setFields("campaign_id,reach,objective,actions,impressions,spend,unique_clicks")
                            .setDatePreset(AdsInsights.EnumDatePreset.VALUE_LAST_7D).setTimeIncrement("1")
                            .requestAllFields().execute();
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
                        temp.addProperty(tempInsight.getFieldCampaignId(), Double.valueOf(tempInsight.getFieldSpend()));
                    }
                    spendStats.add(temp);

                    JsonObject temp2 = new JsonObject();
                    temp2.addProperty("period", date);
                    for (AdsInsights tempInsight : data) {
                        temp2.addProperty(tempInsight.getFieldCampaignId(),
                                Double.valueOf(tempInsight.getFieldImpressions()));
                    }
                    impressionStats.add(temp2);

                    JsonObject temp3 = new JsonObject();
                    temp3.addProperty("period", date);
                    for (AdsInsights tempInsight : data) {
                        temp3.addProperty(tempInsight.getFieldCampaignId(),
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
                            if (objective != null && "MOBILE_APP_INSTALLS".equalsIgnoreCase(objective)) {
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
                        temp4.addProperty(tempInsight.getFieldCampaignId(), goalCount);
                    }
                    goalCountStats.add(temp4);
                }
                campaigns.add("spendStats", spendStats);
                campaigns.add("metadata", campaignMetadata);
                campaigns.add("impressionStats", impressionStats);
                campaigns.add("campaigns", campaignArray);
                campaigns.add("clickStats", clickStats);
                campaigns.add("goalCountStats", goalCountStats);
            } catch (APIException e) {
                e.printStackTrace();
            }
        }

        return campaigns;
    }
}
