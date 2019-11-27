package com.drp.social.fbapis.facebook.accounts;

import com.drp.social.fbapis.Constants;
import com.facebook.ads.sdk.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.*;

/**
 * Created by deep.patel on 11/16/16.
 */
public class AccountsAPI {
    private APIContext context;

    public AccountsAPI(String token) {
        context = new APIContext(token, Constants.APP_SECRET).enableDebug(true);
    }


    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        APIRequest<AdAccount> request =
                new APIRequest<AdAccount>(context, "me", "/adaccounts", "GET", AdAccount.getParser());
        request.requestFields(Arrays.asList("account_status", "timezone_name", "spend_cap", "name", "amount_spent",
                "amount_spent", "currency", "disable_reason", "applications", "min_daily_budget"));
        try {
            APINodeList<AdAccount> fbAccounts = (APINodeList<AdAccount>) (request.execute());
            for (AdAccount adAccount : fbAccounts) {
                Account account = new Account();
                account.setAccountId(adAccount.getId());
                account.setAccountName(adAccount.getFieldName());
                account.setAccountStatus(adAccount.getFieldAccountStatus().intValue());
                account.setCurrency(adAccount.getFieldCurrency());
                account.setMinimumDailyBudget(adAccount.getFieldMinDailyBudget());
                account.setTimezone(adAccount.getFieldTimezoneName());
                accounts.add(account);
            }
        } catch (APIException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public List<Page> getAllPages() {
        List<Page> pages = new ArrayList<>();
        APIRequest<AdAccount> account = new APIRequest<>(context, "me", "/accounts", "GET", AdAccount.getParser());

        try {
            JsonObject response = account.execute().getRawResponseAsJsonObject();
            JsonArray data = response.getAsJsonArray("data");

            for (int i = 0; i < data.size(); i++) {
                JsonObject temp = data.get(i).getAsJsonObject();
                Page page = new Page();
                page.setAccessToken(temp.get("access_token").getAsString());
                page.setCategory(temp.get("category").getAsString());
                page.setId(temp.get("id").getAsString());
                page.setName(temp.get("name").getAsString());
                List<String> permissions = new ArrayList<>();
                JsonArray perms = temp.get("perms").getAsJsonArray();
                for (int j = 0; j < perms.size(); j++) {
                    permissions.add(perms.get(j).getAsString());
                }
                page.setPerms(permissions);
                pages.add(page);
            }

        } catch (APIException e) {
            e.printStackTrace();
        }

        return pages;
    }

    public List<Application> getAdvertisableApplication(String accountId, Account account) {
        List<Application> applications = new ArrayList<>();
        AdAccount adAccount = new AdAccount(accountId, context);

        try {
            APINodeList<APINode> list = adAccount.getAdvertisableApplications().execute();
            for (APINode apiNode : list) {
                JsonObject jsonObject = apiNode.getRawResponseAsJsonObject();
                APIRequest request = new APIRequest(context, "/" + jsonObject.get("id").getAsString(), "", "GET",
                        AdAccount.getParser());
                request.requestFields(Arrays.asList("logo_url", "supported_platforms", "object_store_urls"));
                JsonObject appDetails = request.execute().getRawResponseAsJsonObject();
                JsonArray supportedPlaform = appDetails.getAsJsonArray("supported_platforms");
                JsonObject storeUrl = appDetails.getAsJsonObject("object_store_urls");
                if (supportedPlaform != null) {
                    for (int i = 0; i < supportedPlaform.size(); i++) {
                        Application application = new Application();
                        application.setId(jsonObject.get("id").getAsString());
                        application.setCategory(
                                (jsonObject.has("category")) ? jsonObject.get("category").getAsString() : "");
                        application.setFbAppUrl((jsonObject.has("link")) ? jsonObject.get("link").getAsString() : "");
                        String platform = supportedPlaform.get(i).getAsString();
                        if (supportedPlaform.size() > 1)
                            application.setName(jsonObject.get("name").getAsString() + " " + platform);
                        else
                            application.setName(jsonObject.get("name").getAsString());
                        application.setPlatform(platform);
                        if (platform.equalsIgnoreCase("ANDROID")) {
                            application.setStoreUrl(storeUrl.get("google_play").getAsString());
                        } else if (platform.equalsIgnoreCase("IPHONE")) {
                            application.setStoreUrl(storeUrl.get("itunes").getAsString());
                        } else if (platform.equalsIgnoreCase("IPAD")) {
                            application.setStoreUrl(storeUrl.get("itunes_ipad").getAsString());
                        } else {
                            continue;
                        }
                        applications.add(application);

                        account.addApplication(application.getId(), application);
                    }
                }
            }
        } catch (APIException e) {
            e.printStackTrace();
        }

        return applications;
    }

    public JsonObject getAccountInsights(List<Account> accounts) {
        JsonObject insights = new JsonObject();
        Map<String, List<AdsInsights>> allInsights = new HashMap<>();
        JsonArray accountMetadata = new JsonArray();

        for (Account account : accounts) {
            AdAccount adAccount = new AdAccount(account.getAccountId(), context);
            try {
                JsonObject metadata = new JsonObject();
                metadata.addProperty("id", account.getAccountId());
                metadata.addProperty("name", account.getAccountName());
                accountMetadata.add(metadata);
                List<AdsInsights> stats =
                        adAccount.getInsights().setDatePreset(AdsInsights.EnumDatePreset.VALUE_LAST_7D)
                                .setTimeIncrement("1").requestAllFields().execute();
                for (AdsInsights adsInsights : stats) {
                    if (!allInsights.containsKey(adsInsights.getFieldDateStart()))
                        allInsights.put(adsInsights.getFieldDateStart(), new ArrayList<AdsInsights>());
                    allInsights.get(adsInsights.getFieldDateStart()).add(adsInsights);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JsonArray spendStats = new JsonArray();
        JsonArray impressionStats = new JsonArray();
        for (Map.Entry<String, List<AdsInsights>> entry : allInsights.entrySet()) {
            String date = entry.getKey();
            List<AdsInsights> data = entry.getValue();
            JsonObject temp = new JsonObject();
            temp.addProperty("period", date);
            for (AdsInsights tempInsight : data) {
                temp.addProperty(tempInsight.getFieldAccountId(), Double.valueOf(tempInsight.getFieldSpend()));
            }
            spendStats.add(temp);

            JsonObject temp2 = new JsonObject();
            temp2.addProperty("period", date);
            for (AdsInsights tempInsight : data) {
                temp2.addProperty(tempInsight.getFieldAccountId(), Double.valueOf(tempInsight.getFieldImpressions()));
            }
            impressionStats.add(temp2);
        }

        insights.add("spendStats", spendStats);
        insights.add("metadata", accountMetadata);
        insights.add("impressionStats", impressionStats);
        return insights;
    }
}
