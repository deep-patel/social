package com.drp.social.fbapis.facebook.campaigns;

import com.google.gson.JsonObject;

/**
 * Created by deep.patel on 11/12/16.
 */

public class Campaign {
    private String     id;
    private String     name;
    private String     objective;
    private String     startTime;
    private String     endTime;
    private long       impressions;
    private long       uniqueClicks;
    private double     spend;
    private String     effectiveStatus;
    private String     accountId;
    private String     accountName;
    private JsonObject actions;
    private long       reach;
    private double     cost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public long getImpressions() {
        return impressions;
    }

    public void setImpressions(long impressions) {
        this.impressions = impressions;
    }

    public double getSpend() {
        return spend;
    }

    public void setSpend(double spend) {
        this.spend = spend;
    }

    public String getEffectiveStatus() {
        return effectiveStatus;
    }

    public void setEffectiveStatus(String effectiveStatus) {
        this.effectiveStatus = effectiveStatus;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getUniqueClicks() {
        return uniqueClicks;
    }

    public void setUniqueClicks(long uniqueClicks) {
        this.uniqueClicks = uniqueClicks;
    }

    public JsonObject getActions() {
        return actions;
    }

    public void setActions(JsonObject actions) {
        this.actions = actions;
    }

    public long getReach() {
        return reach;
    }

    public void setReach(long reach) {
        this.reach = reach;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Campaign{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", objective='" + objective + '\''
                + ", startTime='" + startTime + '\'' + ", endTime='" + endTime + '\'' + ", impressions=" + impressions
                + ", uniqueClicks=" + uniqueClicks + ", spend=" + spend + ", effectiveStatus='" + effectiveStatus + '\''
                + ", accountId='" + accountId + '\'' + ", accountName='" + accountName + '\'' + ", actions='" + actions
                + '\'' + ", reach=" + reach + ", cost=" + cost + '}';
    }

}
