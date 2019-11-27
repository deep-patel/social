package com.drp.social.fbapis;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by deep.patel on 11/18/16.
 */
public class Utils {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    public static JsonObject getJsonFromStream(InputStream is) throws IOException {
        JsonParser parser = new JsonParser();
        return parser.parse(IOUtils.toString(is)).getAsJsonObject();
    }

    public static JSONObject getJSONFromStream(InputStream is) throws IOException {
        JSONObject jsonObject = new JSONObject(IOUtils.toString(is));
        System.out.println(jsonObject);
        return jsonObject;
    }

    public static boolean hasValue(JSONObject data, String key) {

        String keys[] = key.trim().split(".");
        boolean hasValue = false;
        for (int i = 0; i < keys.length; i++) {
            if (data.has(key) && !data.isNull(key) && !data.getString(key).isEmpty()) {
                hasValue = true;
            } else {
                hasValue = false;
                break;
            }
        }
        return hasValue;
    }

    public static String getValue(JSONObject data, String key) {

        String keys[] = key.trim().split(".");
        String value = null;
        for (int i = 0; i < keys.length; i++) {
            if (data.has(key) && !data.isNull(key) && !data.getString(key).isEmpty()) {
                value = data.getString(key);
            } else {
                value = data.getString(key);
                break;
            }
        }
        return value;
    }

}
