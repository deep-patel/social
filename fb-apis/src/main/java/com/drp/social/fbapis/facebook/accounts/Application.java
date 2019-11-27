package com.drp.social.fbapis.facebook.accounts;

/**
 * Created by deep.patel on 11/16/16.
 */
public class Application {
    private String id;
    private String name;
    private String fbAppUrl;
    private String storeUrl;
    private String category;
    private String platform;

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

    public String getFbAppUrl() {
        return fbAppUrl;
    }

    public void setFbAppUrl(String fbAppUrl) {
        this.fbAppUrl = fbAppUrl;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "Application{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", fbAppUrl='" + fbAppUrl + '\''
                + ", storeUrl='" + storeUrl + '\'' + ", category='" + category + '\'' + ", platform='" + platform + '\''
                + '}';
    }
}
