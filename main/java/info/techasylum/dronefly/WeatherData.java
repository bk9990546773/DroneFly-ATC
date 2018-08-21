package info.techasylum.dronefly;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * Created by pankaj on 11/19/2017.
 */

public class WeatherData {
    private static final String OPEN_WEATHER_MAP_URL =
            "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric";

    private static final String OPEN_WEATHER_MAP_API = "d79ddff7c1cec072d2f3d363fb93e33d";

    public interface AsyncResponse {
        void processFinish(String output1, String output2, String output3, String output4, String output5, String output6, String output7);
    }
    public static class placeIdTask extends AsyncTask<String, Void, JSONObject> {

        public AsyncResponse delegate = null;//Call back interface

        public placeIdTask(AsyncResponse asyncResponse) {
            delegate = asyncResponse;//Assigning call back interfacethrough constructor
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject jsonWeather = null;
            try {
                jsonWeather = getWeatherJSON(params[0], params[1]);
            } catch (Exception e) {
                Log.d("Error", "Cannot process JSON results", e);
            }


            return jsonWeather;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if(json != null){
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    JSONObject sys = json.getJSONObject("sys");
                    JSONObject wind = json.getJSONObject("wind");
                    DateFormat df = DateFormat.getDateTimeInstance();
                    /*===================weather object data==================*/
                    String description = details.getString("description").toUpperCase(Locale.US);
                    String icon = details.getString("icon");

                    /*===================main object data==================*/
                    String temperature = String.format("%.2f", main.getDouble("temp"))+ "°";

                    String humidity = main.getString("humidity") + "%";
                    String pressure = main.getString("pressure");
                                                    // + " hPa"

                    /*===================wind object data==================*/
                    String visible = json.getString("visibility");

                    String wspeed = wind.getString("speed");
                                                        //+ "km/h"

                    delegate.processFinish(description,temperature,humidity,pressure,visible,wspeed,icon);

                }
            } catch (JSONException e) {
                //Log.e(LOG_TAG, "Cannot process JSON results", e);
            }
        }
    }


    public static JSONObject getWeatherJSON(String lat, String lon){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_URL, lat, lon));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());
            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            return null;
        }
    }
}