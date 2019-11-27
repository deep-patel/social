package com.drp.social.fbapis.facebook.store;

import com.drp.social.fbapis.facebook.accounts.Account;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by deep.patel on 11/17/16.
 */
public class AccountStore {
    private static AccountStore        ourInstance      = new AccountStore();
    private Map<String, List<Account>> accountMap       = new HashMap<>();
    private Map<String, String>        accountIdNameMap = new HashMap<>();

    public static AccountStore getInstance() {
        return ourInstance;
    }

    private AccountStore() {}

    public void addAccountList(String apiKey, List<Account> accounts) {
        accountMap.put(apiKey, accounts);
    }

    public List<Account> getAccounts(String apiKey) {
        return accountMap.get(apiKey);
    }

    public void removeAccounts(String apiKey) {
        accountMap.remove(apiKey);
    }
}
