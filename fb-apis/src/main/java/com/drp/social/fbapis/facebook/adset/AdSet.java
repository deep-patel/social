package com.drp.social.fbapis.facebook.adset;

import com.google.gson.JsonObject;

/**
 * Created by deep.patel on 11/18/16.
 */
public class AdSet {
    private String     id;
    private String     name;
    private double     spend;
    private long       impression;
    private long       clicks;
    private double     dailyBudget;
    private String     optimizationGoal;
    private String     billingEvent;
    private String     status;
    private JsonObject actions;
    private long       reach;
    private double     cost;
    private long       uniqueClicks;

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

    public double getSpend() {
        return spend;
    }

    public void setSpend(double spend) {
        this.spend = spend;
    }

    public long getImpression() {
        return impression;
    }

    public void setImpression(long impression) {
        this.impression = impression;
    }

    public long getClicks() {
        return clicks;
    }

    public void setClicks(long clicks) {
        this.clicks = clicks;
    }

    public double getDailyBudget() {
        return dailyBudget;
    }

    public void setDailyBudget(double dailyBudget) {
        this.dailyBudget = dailyBudget;
    }

    public String getOptimizationGoal() {
        return optimizationGoal;
    }

    public void setOptimizationGoal(String optimizationGoal) {
        this.optimizationGoal = optimizationGoal;
    }

    public String getBillingEvent() {
        return billingEvent;
    }

    public void setBillingEvent(String billingEvent) {
        this.billingEvent = billingEvent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "AdSet{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", spend=" + spend + ", impression="
                + impression + ", clicks=" + clicks + ", dailyBudget=" + dailyBudget + ", optimizationGoal='"
                + optimizationGoal + '\'' + ", billingEvent='" + billingEvent + '\'' + ", status='" + status + '\''
                + ", actions=" + actions + ", reach=" + reach + ", cost=" + cost + ", uniqueClicks=" + uniqueClicks
                + '}';
    }

    public long getUniqueClicks() {
        return uniqueClicks;
    }

    public void setUniqueClicks(long uniqueClicks) {
        this.uniqueClicks = uniqueClicks;
    }

}
