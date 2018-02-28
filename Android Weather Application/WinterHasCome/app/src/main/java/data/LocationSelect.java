package data;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Copyright © 2016 by Prashanth Murali. Permission is granted to use, modify, and distribute this document.
 */
public class LocationSelect {
    SharedPreferences preferences;

    public LocationSelect(Activity activity){
        preferences=activity.getPreferences(Activity.MODE_PRIVATE);

    }

    public String getLocation(){
        return preferences.getString("city","Chennai,IN");
    }

    public void setCity(String city)
    {
        city=city.replaceAll("\\s","");
        //Log.v("city",city);
        preferences.edit().putString("city",city).commit();
    }
}
