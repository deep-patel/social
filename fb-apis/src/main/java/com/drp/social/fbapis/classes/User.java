package com.drp.social.fbapis.classes;

/**
 * Created by deep.patel on 11/28/16.
 */
public class User {
    private String _id;
    private String fbLongLiveToken;
    private String sessionId;
    private String username;
    private String password;

    public String getUserId() {
        return _id;
    }

    public void setUserId(String userId) {
        this._id = userId;
    }

    public String getFbLongLiveToken() {
        return fbLongLiveToken;
    }

    public void setFbLongLiveToken(String fbLongLiveToken) {
        this.fbLongLiveToken = fbLongLiveToken;
    }

    @Override
    public String toString() {
        return "User{" + "userId='" + _id + '\'' + ", fbLongLiveToken='" + fbLongLiveToken + '\'' + ", sessionId='"
                + sessionId + '\'' + ", username='" + username + '\'' + ", password='" + password + '\'' + '}';
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
