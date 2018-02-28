package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Util.Operations;

/**
 * Copyright © 2016 by Prashanth Murali. Permission is granted to use, modify, and distribute this document.
 */
public class DataCollection {
    public String readDataFromSite(String place)
    {
        HttpURLConnection weatherData=null;
        InputStream input=null;

        try {
            weatherData=(HttpURLConnection)(new URL(Operations.WEATHER_LINK+place)).openConnection();
            weatherData.setRequestMethod("GET");
            weatherData.setDoInput(true);
            weatherData.setDoInput(true);
            weatherData.connect();

            StringBuffer bufferData=new StringBuffer();
            input=weatherData.getInputStream();
            BufferedReader bufferedReader=new BufferedReader((new InputStreamReader(input)));
            String line=null;

            while ((line=bufferedReader.readLine())!=null)
            {
                bufferData.append(line+"\r\n");
            }

            input.close();
            weatherData.disconnect();

            return bufferData.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
