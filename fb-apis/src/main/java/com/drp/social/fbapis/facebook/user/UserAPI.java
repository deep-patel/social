package com.drp.social.fbapis.facebook.user;

import com.drp.social.fbapis.Constants;
import com.facebook.ads.sdk.*;

/**
 * Created by deep.patel on 11/9/16.
 */
public class UserAPI {
    public static String getUserDetails(String token) {
        String name = null;
        APIContext context = new APIContext(token, Constants.APP_SECRET).enableDebug(true);
        APIRequest<User> request = new APIRequest<User>(context, "me", "", "GET", User.getParser());
        try {
            APINodeList<User> users = (APINodeList<User>) (request.execute());
            if (users == null || users.isEmpty()) return null;

            User user = users.get(0);
            name = user.getFieldName();
        } catch (APIException e) {
            e.printStackTrace();
        }
        return name;
    }


}
