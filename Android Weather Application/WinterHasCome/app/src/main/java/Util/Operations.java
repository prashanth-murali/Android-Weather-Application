package Util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Copyright © 2016 by Prashanth Murali. Permission is granted to use, modify, and distribute this document.
 */
public class Operations {
    public static final String WEATHER_LINK="http://api.openweathermap.org/data/2.5/weather?APPID=2e513d7e04738eea5219de70b89acd2c&q=";
    public static final String PIC_LINK="http://openweathermap.org/img/w/";

    public static JSONObject getObject(String tag,JSONObject jsonObject) throws JSONException{

        JSONObject jObj=jsonObject.getJSONObject(tag);
        return jObj;
    }

    public static String getString(String tag, JSONObject jsonObject) throws JSONException{

        return jsonObject.getString(tag);
    }

    public static float getFloat(String tag,JSONObject jsonObject) throws JSONException{
        return (float) jsonObject.getDouble(tag);
    }

    public static double getDouble(String tag, JSONObject jsonObject) throws JSONException{
        return (float) jsonObject.getDouble(tag);
    }
    public static int getInt(String tag,JSONObject jsonObject) throws JSONException{
        return jsonObject.getInt(tag);
    }

}
