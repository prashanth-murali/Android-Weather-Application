package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Util.Operations;
import model.Place;
import model.Weather;

/**
 * Copyright © 2016 by Prashanth Murali. Permission is granted to use, modify, and distribute this document.
 */
public class ExtractWeatherData {
    public static Weather getData(String data)
    {
        Weather weatherObject=new Weather();
        try {
            JSONObject jsonData=new JSONObject(data);

            Place place=new Place();

            JSONObject coordinates= Operations.getObject("coord",jsonData);
            place.setLat(Operations.getFloat("lat",coordinates));
            place.setLon(Operations.getFloat("lon",coordinates));

            JSONObject sysData=Operations.getObject("sys",jsonData);
            place.setCountry(Operations.getString("country",sysData));
            place.setLastupdate(Operations.getInt("dt",jsonData));
            place.setSunrise(Operations.getInt("sunrise",sysData));
            place.setSunset(Operations.getInt("sunset",sysData));
            place.setCity(Operations.getString("name",jsonData));
            weatherObject.place=place;

            JSONArray jsonArrayData=jsonData.getJSONArray("weather");
            JSONObject jsonWeatherData=jsonArrayData.getJSONObject(0);
            weatherObject.currentCondition.setWeatherId(Operations.getInt("id",jsonWeatherData));

            weatherObject.currentCondition.setDescription(Operations.getString("description",jsonWeatherData));
            weatherObject.currentCondition.setCondition(Operations.getString("main",jsonWeatherData));
            weatherObject.currentCondition.setIcon(Operations.getString("icon",jsonWeatherData));

            JSONObject mainData=Operations.getObject("main",jsonData);
            weatherObject.currentCondition.setHumidity(Operations.getInt("humidity",mainData));
            weatherObject.currentCondition.setPressure(Operations.getInt("pressure",mainData));
            weatherObject.currentCondition.setMinTemp(Operations.getFloat("temp_min",mainData));
            weatherObject.currentCondition.setMaxTemp(Operations.getFloat("temp_max",mainData));
            weatherObject.currentCondition.setTemperature(Operations.getDouble("temp",mainData));


            JSONObject windData=Operations.getObject("wind",jsonData);
            weatherObject.wind.setSpeed(Operations.getFloat("speed",windData));
            weatherObject.wind.setDeg(Operations.getFloat("deg",windData));

            JSONObject cloudData=Operations.getObject("clouds",jsonData);
            weatherObject.clouds.setPrecipitation(Operations.getInt("all",cloudData));

            return weatherObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
