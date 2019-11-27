package com.drp.social.fbapis.facebook.accounts;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by deep.patel on 11/16/16.
 */
public class Account {
    private String                   accountId;
    private String                   accountName;
    private int                      accountStatus;
    private String                   timezone;
    private String                   currency;
    private long                     minimunDailyBudget;
    private Map<String, Application> applications = new HashMap<String, Application>();

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

    public int getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getMinimumDailyBudget() {
        return minimunDailyBudget;
    }

    public void setMinimumDailyBudget(long minimunDailyBudget) {
        this.minimunDailyBudget = minimunDailyBudget;
    }

    public void addApplication(String appName, Application application) {
        applications.put(appName, application);
    }

    public Application getApplication(String appName) {
        return applications.get(appName);
    }

    public boolean equals(Object obj) {
        Account accountObj = (Account) obj;
        if (this.accountId.equalsIgnoreCase(accountObj.getAccountId())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Account{" + "accountId='" + accountId + '\'' + ", accountName='" + accountName + '\''
                + ", accountStatus=" + accountStatus + ", timezone='" + timezone + '\'' + ", currency='" + currency
                + '\'' + ", minimunDailyBudget=" + minimunDailyBudget + ", applications=" + applications + '}';
    }
}
