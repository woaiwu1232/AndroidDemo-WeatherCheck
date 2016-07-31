package chaochenshi.weachercheck;

import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;

public class JSONTools {//The collection of JSON tools
    public JSONTools () {}
    public static WeatherData getWeatherData(URL url){//Encapsulate weather data into getWeatherData

        String jsonText = new JSONAccess().getJSONData(url);//Create a new Access object, get JSON data by url
        //System.out.println(jsonText);
        WeatherData weather = new WeatherData();


        try {//Get JSON values from the open weather API and put them into local variables
            JSONObject weatherJSONObject = new JSONObject(jsonText);
            JSONArray weatherJSONArray = weatherJSONObject.getJSONArray("weather");
            JSONObject coordJSONObject = weatherJSONObject.getJSONObject("coord");
            double lat = coordJSONObject.getDouble("lat");
            double lon = coordJSONObject.getDouble("lon");
            JSONObject sysJSONObject = weatherJSONObject.getJSONObject("sys");
            String country = sysJSONObject.getString("country");
            JSONObject weatherObject = weatherJSONArray.getJSONObject(0);//the index of description JSON
            String description = weatherObject.getString("description");
            String icon = weatherObject.getString("icon");
            JSONObject mainJSONObject = weatherJSONObject.getJSONObject("main");
            double temperature = mainJSONObject.getInt("temp");
            double temp_min = mainJSONObject.getDouble("temp_min");
            double temp_max = mainJSONObject.getDouble("temp_max");
            int pressure = mainJSONObject.getInt("pressure");
            int humidity = mainJSONObject.getInt("humidity");
            String cityName = weatherJSONObject.getString("name");
            int dt = weatherJSONObject.getInt("dt");


            //Set values of variables by the set method defined in weatherdata
            weather.setCountry(country);
            weather.setTemperature(temperature);
            weather.setTemperatureMin(temp_min);
            weather.setTemperatureMax(temp_max);
            weather.setHumidity(humidity);
            weather.setPressure(pressure);
            weather.setDescription(description);
            weather.setCityName(cityName);
            weather.setIcon(icon);
            weather.setLatitude(lat);
            weather.setLongitude(lon);
            weather.setTimestamp(dt);
            //System.out.println(weather.getTemperatureMaxInCelsius());
            //System.out.println(weather.getTemperatureMinInCelsius());

        } catch (JSONException e) {
            System.out.println("test");
            e.printStackTrace();
        }

        return weather;
    }
    public static WeatherDataMultiDays getWeatherDataMultiDays(URL url, int nDays){//The method to get multidays' weather data

        String jsonText = new JSONAccess().getJSONData(url);
        //System.out.println(jsonText);
        WeatherData weather = new WeatherData();
        WeatherDataMultiDays data7Days = new WeatherDataMultiDays();

        try {
            JSONObject weather7DaysJSONObject = new JSONObject(jsonText);
            JSONObject cityJSONObject = weather7DaysJSONObject.getJSONObject("city");
            String cityName = cityJSONObject.getString("name");
            JSONObject coordJSONObject = cityJSONObject.getJSONObject("coord");
            double lat = coordJSONObject.getDouble("lat");
            double lon = coordJSONObject.getDouble("lon");
            JSONArray listJSONArray = weather7DaysJSONObject.getJSONArray("list");//Object The value of "list" is an array
            weather.setCityName(cityName);
            weather.setLatitude(lat);
            weather.setLongitude(lon);

            for(int i = 0; i < nDays; ++i) {
                JSONObject weather7DaysObjectDays = listJSONArray.getJSONObject(i);//the index of description JSON
                int dtDay = weather7DaysObjectDays.getInt("dt");
                //double pressure = weather7DaysObjectDays.getDouble("pressure");
                //double humidity = weather7DaysObjectDays.getDouble("humidity");
                JSONObject tempJSONObjectDays = weather7DaysObjectDays.getJSONObject("temp");
                double temp_min_Day = tempJSONObjectDays.getDouble("min");
                double temp_max_Day = tempJSONObjectDays.getDouble("max");
                JSONArray weatherArrayDays = weather7DaysObjectDays.getJSONArray("weather");
                JSONObject weatherObjectDays = weatherArrayDays.getJSONObject(0);
                String iconDays = weatherObjectDays.getString("icon");

                weather.setTimestamp(dtDay);
                //weather.setPressure(pressure);
                //weather.setHumidity(humidity);
                weather.setTemperatureMin(temp_min_Day);
                weather.setTemperatureMax(temp_max_Day);
                weather.setIcon(iconDays);
                //System.out.println(weather.getTemperatureMaxInCelsius());
                //System.out.println(weather.getTemperatureMinInCelsius());

                data7Days.addSingleDayWeather(weather);

            }

        } catch (JSONException e) {
            System.out.println("test");
            e.printStackTrace();
        }

        return data7Days;
    }
    public static String getTimeZone(long timestamp, double latitude, double longitude){//Match the location with correct time zone
        String APIKey = "AIzaSyB-3nViGCRIOMLUs4v0hwKJF3834Zkmf_M";
        String urlKey = "&key=" + APIKey;
        String urlAPI = "https://maps.googleapis.com/maps/api/timezone/json";
        String urlLocation = "?location=" + latitude + "," + longitude;
        String urlTimestamp = "&timestamp=" + timestamp;
        String timeZoneId = "Null";

        try {
            URL url = new URL(urlAPI + urlLocation + urlTimestamp + urlKey);
            //System.out.println(url);
            String jsonText = new JSONAccess().getJSONData(url);//
            //System.out.println(jsonText);

            JSONObject timeZoneJSONObject = new JSONObject(jsonText);
            timeZoneId = timeZoneJSONObject.getString("timeZoneId");
        }catch (Exception e) {
            e.printStackTrace();
        }

        return timeZoneId;
    }
}

class JSONAccess {

    private String jsonData = "";

    public  String getJSONData(final URL url){//get weather values form the certain url
        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                InputStream streamIn = null;
                BufferedReader readerBuffered = null;
                String ifFailed = "";

                try {//The way to set a http connection
                    HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
                    urlConnect.setRequestMethod("GET");
                    urlConnect.setReadTimeout(10000 /* milliseconds */);
                    urlConnect.setConnectTimeout(15000 /* milliseconds */);
                    urlConnect.setDoInput(true);
                    urlConnect.connect();

                    streamIn = urlConnect.getInputStream();
                    readerBuffered = new BufferedReader(new InputStreamReader(streamIn));
                    String nullString = "";
                    while((nullString = readerBuffered.readLine()) != null){
                        jsonData += nullString;

                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Fail 1");
                }



                finally {
                    try {//connection closed
                        streamIn.close();
                        readerBuffered.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Fail 2");
                    }
                }
            }
        });

        thread.start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Fail 3");
        }

        return jsonData;
    }
}