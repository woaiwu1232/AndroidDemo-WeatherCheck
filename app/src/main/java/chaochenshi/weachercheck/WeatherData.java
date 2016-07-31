package chaochenshi.weachercheck;
//This class defined the data structure of weather and encapsulated the data update process into updateData method

import android.location.Location;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class WeatherData {  // weather data structure. This is a javabean.
    private String country;
    private String description;
    private String cityName;
    private String icon;
    private double temperature;
    private double temperatureMin;
    private double temperatureMax;
    private double humidity;
    private double pressure;
    private double latitude;
    private double longitude;
    private long   timestamp;

    /**
     * Constructors for objects of class WeatherData
     */
    public WeatherData () {//The initial weather data
        country        = "";
        description    = "";
        cityName       = "";
        icon           = "";
        temperature    = 0;
        temperatureMin = 0.;
        temperatureMax = 0.;
        humidity       = 0;
        pressure       = 0;
        latitude       = 0.;
        longitude      = 0.;
        timestamp      = 0;
    }
    public WeatherData (WeatherData data) {//overload of the WeatherData method
        country        = data.getCountry();
        description    = data.getDescription();
        cityName       = data.getCityName();
        icon           = data.getIcon();
        temperature    = data.getTemperature();
        temperatureMin = data.getTemperatureMin();
        temperatureMax = data.getTemperatureMax();
        humidity       = data.getHumidity();
        pressure       = data.getPressure();
        latitude       = data.getLatitude();
        longitude      = data.getLongitude();
        timestamp      = data.getTimestamp();
    }

    /**
     * Methods for setting variables
     */
    public void setWeatherData(WeatherData data) {
        country        = data.getCountry();
        description    = data.getDescription();
        cityName       = data.getCityName();
        icon           = data.getIcon();
        temperature    = data.getTemperature();
        temperatureMin = data.getTemperatureMin();
        temperatureMax = data.getTemperatureMax();
        humidity       = data.getHumidity();
        pressure       = data.getPressure();
        latitude       = data.getLatitude();
        longitude      = data.getLongitude();
        timestamp      = data.getTimestamp();
    }
    public void setCountry(String varCountry) {
        this.country = varCountry;
    }
    public void setTemperature(double varTemperature) {
        this.temperature = varTemperature;
    }
    public void setTemperatureMin(double varTemperatureMin) {
        this.temperatureMin = varTemperatureMin;
    }
    public void setTemperatureMax(double varTemperatureMax) {
        this.temperatureMax = varTemperatureMax;
    }
    public void setHumidity(double varHumidity) {
        this.humidity = varHumidity;
    }
    public void setPressure(double varPressure) {
        this.pressure = varPressure;
    }
    public void setDescription(String varDescription) {
        this.description = varDescription;
    }
    public void setCityName(String varCityName) {
        this.cityName = varCityName;
    }
    public void setIcon(String varIcon) {
        this.icon = varIcon;
    }
    public void setLatitude(double varLatitude) {
        this.latitude = varLatitude;
    }
    public void setLongitude(double varLongitude) {
        this.longitude = varLongitude;
    }
    public void setTimestamp(int varDate) {
        this.timestamp = varDate;
    }

    /**
     * Methods for getting variables
     */
    public String getCountry() {
        return country;
    }
    public double getTemperature() {
        return temperature;
    }
    public int    getTemperatureInCelsius() {
        return (int) Math.round(temperature - 273.15);
    }
    public int    getTemperatureInFahrenheit() {
        return (int) Math.round(temperature*9.0/5.0 - 459.67);
    }
    public double getTemperatureMin() {
        return temperatureMin;
    }
    public int    getTemperatureMinInCelsius() {
        return (int) Math.round(temperatureMin - 273.15);
    }
    public int    getTemperatureMinInFahrenheit() {
        return (int) Math.round(temperatureMin*9.0/5.0 - 459.67);
    }
    public double getTemperatureMax() {
        return temperatureMax;
    }
    public int    getTemperatureMaxInCelsius() {
        return (int) Math.round(temperatureMax - 273.15);
    }
    public int    getTemperatureMaxInFahrenheit() {
        return (int) Math.round(temperatureMax*9.0/5.0 - 459.67);
    }
    public double getHumidity() {
        return humidity;
    }
    public int    getHumidityRounded() {
        return (int) Math.round(humidity);
    }
    public double getPressure() {
        return pressure;
    }
    public int    getPressureRounded() {
        return (int) Math.round(pressure);
    }
    public String getDescription() {
        return description;
    }
    public String getCityName() {
        return cityName;
    }
    public String getIcon() {
        return icon;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public long   getTimestamp() {
        return timestamp;
    }
    public String getTime() {
        long lTimestamp = timestamp * 1000;
        Date dt = new Date(lTimestamp);
        System.out.println(Long.parseLong(String.valueOf(lTimestamp)));
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);
        format.setTimeZone(TimeZone.getTimeZone(JSONTools.getTimeZone(timestamp, this.latitude, this.longitude)));
        return format.format(dt);
    }
    public String getMonthDay() {
        long lTimestamp = timestamp * 1000;
        Date dt = new Date(lTimestamp);
        //System.out.println(Long.parseLong(String.valueOf(lTimestamp)));
        DateFormat format = new SimpleDateFormat("MM/dd", Locale.UK);
        format.setTimeZone(TimeZone.getTimeZone(JSONTools.getTimeZone(timestamp, this.latitude, this.longitude)));
        return format.format(dt);
    }

    /**
     * Method: updateData
     * Purpose: update weather data by city name
     * Arguments:
     * lat  - latitude
     * lon  - longitude
     */
    public void updateData (String name) {
        String APIKey = "a77af8cf272aa58494c741fc8d363fa1";
        String urlAPI = "http://api.openweathermap.org/data/2.5/weather?";
        String urlMode = "q=" + name.replaceAll("\\s+", "");    // Used to delete the possible space in the city name (e.g. new york), otherwise the program would crash.
        String urlKey = "&APPID=" + APIKey;

        try {
            URL url = new URL(urlAPI + urlMode + urlKey);
            this.setWeatherData(JSONTools.getWeatherData(url));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: updateData (overloaded)
     * Purpose: update weather data by geographical coordinates
     * Arguments:
     * latitude   - latitude
     * longitude  - longitude
     */
    public void updateData (Location coordinates) {//overload of the update method, adapt to the GPS module.
        this.latitude = coordinates.getLatitude();
        this.longitude = coordinates.getLongitude();
        String lat = String.valueOf(this.latitude);
        String lon = String.valueOf(this.longitude);

        String APIKey = "a77af8cf272aa58494c741fc8d363fa1";
        String urlAPI = "http://api.openweathermap.org/data/2.5/weather?";
        String urlMode = "lat=" + lat + "&" + "lon=" + lon;
        String urlKey = "&APPID=" + APIKey;

        try {
            URL url = new URL(urlAPI + urlMode + urlKey);
            this.setWeatherData(JSONTools.getWeatherData(url));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}