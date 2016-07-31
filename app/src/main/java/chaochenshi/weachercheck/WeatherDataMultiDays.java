package chaochenshi.weachercheck;

import android.location.Location;
import java.net.URL;
import java.util.Iterator;

public class WeatherDataMultiDays {
    public WeatherData[] data;

    /**
     * Constructors for objects of class WeatherDataMultiDays
     */
    WeatherDataMultiDays() {
        data = new WeatherData[0];
    }

    WeatherDataMultiDays(int nDays) { //通过天数初始化
        data = new WeatherData[nDays];
        //data[0] = new WeatherData();
        for (int i = 0; i < nDays; ++i) {
            data[i] = new WeatherData();
        }
    }

    WeatherDataMultiDays(WeatherData[] varData) { //通过WeatherData[]数组初始化
        this.setWeatherDataMultiDays(varData);
    }

    WeatherDataMultiDays(WeatherDataMultiDays varDataMultiDays) {  //通过本类的实例初始化
        this.setWeatherDataMultiDays(varDataMultiDays.getData());
    }

    public int getNumberOfDays() {
        return data.length;
    }


    public WeatherData[] getData () {
        return data;
    }

    /*public WeatherDataMultiDays setWeatherDataMultiDays (WeatherData[] varData) {//argument:array
        data = new WeatherData[varData.length];
        int index = 0;
        for (WeatherData item : data) { //item:迭代器
            item.setWeatherData(varData[index++]);
        }
        return this;
    }*/
    public WeatherDataMultiDays setWeatherDataMultiDays (WeatherData[] varData) {//argument:array
        int numberOfDays = varData.length;
        data = new WeatherData[numberOfDays];
        for (int i = 0; i < numberOfDays; ++i) {
            data[i] = new WeatherData();
            data[i].setWeatherData(varData[i]);
        }
        return this;
    }



    /**
     * Method: updateData (overloaded)
     */
    public WeatherDataMultiDays setWeatherDataMultiDays (WeatherDataMultiDays varDataMultiDays) {//argument:class
        int numberOfDays = varDataMultiDays.getData().length;
        data = new WeatherData[numberOfDays];
        for (int i = 0; i < numberOfDays; ++i) {
            data[i] = new WeatherData();
            data[i].setWeatherData(varDataMultiDays.getData()[i]);
        }
        return this;
    }

    public WeatherDataMultiDays addSingleDayWeather (WeatherData singleDayWeather) {
        int newNumberOfDays = data.length + 1;
        WeatherDataMultiDays newDataMultiDays = new WeatherDataMultiDays(newNumberOfDays);
        int index = 0;
        for (int i = 0; i < data.length; ++i) {
            index++;
            newDataMultiDays.getData()[i].setWeatherData(data[i]);
        }

        newDataMultiDays.data[index].setWeatherData(singleDayWeather);

        this.setWeatherDataMultiDays(newDataMultiDays.data);

        return this;
    }

    /**
     * Method: updateDataMultiDays
     * Purpose: update whole week weather data by city name
     * Arguments:
     * lat  - latitude
     * lon  - longitude
     */
    public WeatherDataMultiDays updateDataMultiDays (String name) {
        String APIKey = "a77af8cf272aa58494c741fc8d363fa1";
        String urlAPI = "http://api.openweathermap.org/data/2.5/forecast/daily?";
        String urlMode = "q=" + name.replaceAll("\\s+", "");    // 用来去掉city name中可能存在的空格（比如new york），否则会导致程序崩溃
        String urlCnt = "&cnt=" + String.valueOf(data.length);
        String urlKey = "&appid=" + APIKey;

        try {
            URL url = new URL(urlAPI + urlMode + urlCnt + urlKey);
            //System.out.println(url);
            this.setWeatherDataMultiDays(JSONTools.getWeatherDataMultiDays(url, data.length));
        }catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * Method: updateDataMultiDays (overloaded)
     * Purpose: update weather data by geographical coordinates
     * Arguments:
     * latitude   - latitude
     * longitude  - longitude
     */
    public WeatherDataMultiDays updateDataMultiDays (Location coordinates) {
        String lat = String.valueOf(coordinates.getLatitude());
        String lon = String.valueOf(coordinates.getLongitude());
        String APIKey = "a77af8cf272aa58494c741fc8d363fa1";
        String urlAPI = "http://api.openweathermap.org/data/2.5/forecast/daily?";
        String urlLocation = "lat=" + lat + "&" + "lon=" + lon;
        String urlCnt = "&cnt=" + data.length;
        String urlMode = "&mode=json";
        String urlKey = "&APPID=" + APIKey;


        for (int i = 0; i < data.length; ++i) {
            data[i].setLatitude(coordinates.getLatitude());
            data[i].setLongitude(coordinates.getLongitude());
        }

        try {
            URL url = new URL(urlAPI + urlLocation + urlCnt + urlMode + urlKey);
            this.setWeatherDataMultiDays(JSONTools.getWeatherDataMultiDays(url, data.length));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }
}
