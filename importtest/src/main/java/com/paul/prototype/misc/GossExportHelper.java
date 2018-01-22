package com.paul.prototype.misc;

import com.paul.prototype.config.GossExportFieldNames;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.util.Date;

public class GossExportHelper {


    public static Long getLong(JSONObject gossJson, GossExportFieldNames fieldName) {
        Object value = gossJson.get(fieldName.getName());

        if(value instanceof Long)
        {
            return (Long) value;
        }else {
            // TODO mandatory missing check.
            return null;
        }
    }


    public static String getString(JSONObject gossJson, GossExportFieldNames fieldName) {
        return (String) gossJson.get(fieldName.getName());
    }

    public static final String DF = "MMM, dd yyyy HH:mm:ss Z";

    public static Date getDate(JSONObject gossJson, GossExportFieldNames fieldName) {
        // May, 23 2016 16:03:33 +0100 (Goss date example)
        String date = (String) gossJson.get(fieldName.getName());
        Date d = null;
        try {
            d = DateUtils.parseDate(date, DF);
        } catch (ParseException e) {
            // TODO exception.
            System.out.println(e);
        }

        return d;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    public static boolean getBoolean(JSONObject gossJson, GossExportFieldNames fieldName, boolean defaultValue) {
        String value = (String) gossJson.get(fieldName.getName());
        if("true".equals(value))
            return true;
        if("false".equals(value))
            return false;
        return defaultValue;
    }
}
