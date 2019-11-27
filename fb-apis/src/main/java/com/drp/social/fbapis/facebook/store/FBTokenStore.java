package com.drp.social.fbapis.facebook.store;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by deep.patel on 11/16/16.
 */
public class FBTokenStore {
    private static FBTokenStore ourInstance = new FBTokenStore();
    private Map<String, String> tokenMap    = new HashMap<String, String>();

    public static FBTokenStore getInstance() {
        return ourInstance;
    }

    private FBTokenStore() {}

    public String addToken(String token) {
        String apiId = UUID.randomUUID().toString();
        tokenMap.put(apiId, token);
        return apiId;
    }

    public String getToken(String apiId) {
        return tokenMap.get(apiId);
    }

    public void removeToken(String apiId) {
        tokenMap.remove(apiId);
    }
}
