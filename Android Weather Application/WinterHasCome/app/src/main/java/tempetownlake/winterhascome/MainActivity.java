/**
 * Copyright © 2016 by Prashanth Murali. Permission is granted to use, modify, and distribute this document.
 */
package tempetownlake.winterhascome;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.icu.text.DateFormat;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.lang.*;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;

import Util.Operations;
import data.LocationSelect;
import data.ExtractWeatherData;
import data.DataCollection;
import model.Weather;

public class MainActivity extends AppCompatActivity {

    private TextView location;
    private TextView temperature;
    private ImageView icon;
    private TextView description;
    private TextView humidity;
    private TextView pressure;
    private TextView wind;
    private TextView sunrise;
    private TextView sunset;
    private TextView updateTime;
    private static String iconcode="";


    Weather weather=new Weather();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location=(TextView) findViewById(R.id.location);
        icon=(ImageView) findViewById(R.id.icon);
        temperature=(TextView) findViewById(R.id.temp);
        description=(TextView) findViewById(R.id.cloud);
        humidity=(TextView) findViewById(R.id.humidity);
        pressure=(TextView) findViewById(R.id.pressure);
        wind=(TextView) findViewById(R.id.wind);
        sunrise=(TextView) findViewById(R.id.sunrise);
        sunset=(TextView) findViewById(R.id.sunset);
        updateTime=(TextView) findViewById(R.id.updateTime);

        LocationSelect locationSelect=new LocationSelect(MainActivity.this);
        displayWeatherDetails(locationSelect.getLocation());
    }

    public void displayWeatherDetails(String city){
        setWeather weatherTask=new setWeather();
        weather.iconData=iconcode;

        //System.out.println("icon:"+weather.iconData);
        new getImage().execute(weather.iconData);
        weatherTask.execute(new String[]{city+"&units=metric"});


    }


    private class getImage extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... params) {

            return downloadImage(params[0]);
            //return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            icon.setImageBitmap(bitmap);
            //super.onPostExecute(bitmap);
        }
        private Bitmap downloadImage(String code){
            try {
                //System.out.println("code: "+code);
                //Log.v("Code: ",code);
                URL obj=new URL(Operations.PIC_LINK+code+".png");
                //URL obj=new URL("http://openweathermap.org/img/w/50n.png");
                HttpURLConnection con=(HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode=con.getResponseCode();
                if(responseCode!=200)
                {
                    Log.e("Download Error","error" + responseCode);
                    return null;
                }

                InputStream inputStream=null;
                inputStream=con.getInputStream();
                final Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                return bitmap;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }
    }


    private class setWeather extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            String data=((new DataCollection()).readDataFromSite(params[0]));

            weather= ExtractWeatherData.getData(data);
            weather.iconData=weather.currentCondition.getIcon();
            iconcode=weather.iconData;
            //System.out.println("icon:"+weather.iconData);
            Log.v("Data: ",weather.currentCondition.getDescription());
            return weather;

        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            java.text.DateFormat df= java.text.DateFormat.getTimeInstance();

            String sunriseDate=df.format(new Date(weather.place.getSunrise()));
            String sunsetDate=df.format(new Date(weather.place.getSunset()));
            String updateDate=df.format(new Date(weather.place.getLastupdate()));

            DecimalFormat decimalFormat=new DecimalFormat("#.#");
            String tempFormat=decimalFormat.format(weather.currentCondition.getTemperature());

            location.setText(weather.place.getCity() +","+ weather.place.getCountry());
            temperature.setText(""+ tempFormat + "°C.");
            humidity.setText("humidity: "+ weather.currentCondition.getHumidity()+"%");
            pressure.setText("pressure: "+ weather.currentCondition.getPressure() + "hPa");
            wind.setText("Wind: " + weather.wind.getSpeed() + "mps");
            sunrise.setText("Sunrise: " + sunriseDate);
            sunset.setText("Sunset: " + sunsetDate);
            updateTime.setText("Last Updated: "+ updateDate);
            description.setText("Condition: " + weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescription() + ")");

        }
    }

    private void showInputDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change City");
        final EditText cityInput=new EditText(MainActivity.this);
        cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityInput.setHint("Eg.NewYork");
        builder.setView(cityInput);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LocationSelect locationSelect=new LocationSelect(MainActivity.this);
                locationSelect.setCity(cityInput.getText().toString());
                String newCity=locationSelect.getLocation();
                displayWeatherDetails(newCity);
            }
        });
        builder.show();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.change_cityId){
            showInputDialog();
        }
        return super.onOptionsItemSelected(item);
    }
}
