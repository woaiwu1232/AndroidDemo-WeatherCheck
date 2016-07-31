package chaochenshi.weachercheck;
// 此类用来封装一切控制UI的方法

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GUIController {
    Activity activity;  // 同LocationController，用来实现控制UI
    private TextView temperature, description, humidity, pressure, latitude, longitude, cityName,
                     fahrenheit, celsius;
    private TextView[] date, tempMax, tempMin;
    private EditText search;
    private ImageView icon;
    private ImageView[] iconDays;
    private LinearLayout contentLayout;

    /**
     * Constructor for objects of class GUIControl
     */
    public GUIController(Activity varActivity) {
        activity = varActivity;

        date     = new TextView[7];
        tempMax  = new TextView[7];
        tempMin  = new TextView[7];
        iconDays = new ImageView[7];

        search      = (EditText) this.activity.findViewById(R.id.editTextLocation);
        cityName    = (TextView) this.activity.findViewById(R.id.textViewCityName);
        temperature = (TextView) this.activity.findViewById(R.id.textViewTemp);
        humidity    = (TextView) this.activity.findViewById(R.id.textViewHumidity);
        pressure    = (TextView) this.activity.findViewById(R.id.textViewPressure);
        description = (TextView) this.activity.findViewById(R.id.textViewDescript);
        latitude    = (TextView) this.activity.findViewById(R.id.textViewLatitude);
        longitude   = (TextView) this.activity.findViewById(R.id.textViewLongitude);
        celsius     = (TextView) this.activity.findViewById(R.id.textViewCelsius);
        fahrenheit  = (TextView) this.activity.findViewById(R.id.textViewFahrenheit);
        icon        = (ImageView) this.activity.findViewById(R.id.imageViewToday);

        date[0] = (TextView) this.activity.findViewById(R.id.textViewDateDay1);
        date[1] = (TextView) this.activity.findViewById(R.id.textViewDateDay2);
        date[2] = (TextView) this.activity.findViewById(R.id.textViewDateDay3);
        date[3] = (TextView) this.activity.findViewById(R.id.textViewDateDay4);
        date[4] = (TextView) this.activity.findViewById(R.id.textViewDateDay5);
        date[5] = (TextView) this.activity.findViewById(R.id.textViewDateDay6);
        date[6] = (TextView) this.activity.findViewById(R.id.textViewDateDay7);

        tempMax[0] = (TextView) this.activity.findViewById(R.id.textViewMaxDay1);
        tempMax[1] = (TextView) this.activity.findViewById(R.id.textViewMaxDay2);
        tempMax[2] = (TextView) this.activity.findViewById(R.id.textViewMaxDay3);
        tempMax[3] = (TextView) this.activity.findViewById(R.id.textViewMaxDay4);
        tempMax[4] = (TextView) this.activity.findViewById(R.id.textViewMaxDay5);
        tempMax[5] = (TextView) this.activity.findViewById(R.id.textViewMaxDay6);
        tempMax[6] = (TextView) this.activity.findViewById(R.id.textViewMaxDay7);

        tempMin[0] = (TextView) this.activity.findViewById(R.id.textViewMinDay1);
        tempMin[1] = (TextView) this.activity.findViewById(R.id.textViewMinDay2);
        tempMin[2] = (TextView) this.activity.findViewById(R.id.textViewMinDay3);
        tempMin[3] = (TextView) this.activity.findViewById(R.id.textViewMinDay4);
        tempMin[4] = (TextView) this.activity.findViewById(R.id.textViewMinDay5);
        tempMin[5] = (TextView) this.activity.findViewById(R.id.textViewMinDay6);
        tempMin[6] = (TextView) this.activity.findViewById(R.id.textViewMinDay7);

        iconDays[0] = (ImageView) this.activity.findViewById(R.id.imageViewDay1);
        iconDays[1] = (ImageView) this.activity.findViewById(R.id.imageViewDay2);
        iconDays[2] = (ImageView) this.activity.findViewById(R.id.imageViewDay3);
        iconDays[3] = (ImageView) this.activity.findViewById(R.id.imageViewDay4);
        iconDays[4] = (ImageView) this.activity.findViewById(R.id.imageViewDay5);
        iconDays[5] = (ImageView) this.activity.findViewById(R.id.imageViewDay6);
        iconDays[6] = (ImageView) this.activity.findViewById(R.id.imageViewDay7);

        contentLayout = (LinearLayout) this.activity.findViewById(R.id.contentLayout);

    }

    /**
     * Method: getSearchText
     * Purpose: fetch the city name from editText1
     */
    public String getSearchText() {
        return search.getText().toString();
    }

    /**
     * Method: displayWeather
     * Purpose: display weather information on GUI
     */
    public void displayWeather(WeatherDataMultiDays dataArray, WeatherData data, boolean unitStatus) {

        /* Display current day */
        String cityNameText = data.getCityName() + ", " +  data.getCountry();
        String pressureText = String.valueOf(data.getPressureRounded()) + " mb";
        String humidityText = String.valueOf(data.getHumidityRounded()) + "%";
        String latitudeText = String.valueOf(data.getLatitude()) + "°";
        String longitudeText = String.valueOf(data.getLongitude()) + "°";

        /* Determine temperature unit */
        String tempText;
        if (unitStatus) { tempText = String.valueOf(data.getTemperatureInCelsius()); }
        else {tempText = String.valueOf(data.getTemperatureInFahrenheit());}

        cityName.setText(cityNameText);
        humidity.setText(humidityText);
        pressure.setText(pressureText);
        temperature.setText(tempText);
        description.setText(data.getDescription());
        latitude.setText(latitudeText);
        longitude.setText(longitudeText);

        switch (data.getIcon()) {//匹配得到的图标数据与本地数据，设置天气图标
            case "01d":
                icon.setImageResource(R.drawable.clear_sky_day_hd);
                break;
            case "02d":
                icon.setImageResource(R.drawable.few_clouds_day_hd);
                break;
            case "03d":
                icon.setImageResource(R.drawable.scattered_clouds_hd);
                break;
            case "04d":
                icon.setImageResource(R.drawable.broken_clouds_hd);
                break;
            case "09d":
                icon.setImageResource(R.drawable.heavy_rain_hd);
                break;
            case "10d":
                icon.setImageResource(R.drawable.light_rain_hd);
                break;
            case "11d":
                icon.setImageResource(R.drawable.thunderstorm_hd);
                break;
            case "13d":
                icon.setImageResource(R.drawable.snow_hd);
                break;
            case "50d":
                icon.setImageResource(R.drawable.haze_hd);
                break;
            case "01n":
                icon.setImageResource(R.drawable.clear_sky_night_hd);
                break;
            case "02n":
                icon.setImageResource(R.drawable.few_clouds_night_hd);
                break;
            case "03n":
                icon.setImageResource(R.drawable.scattered_clouds_hd);
                break;
            case "04n":
                icon.setImageResource(R.drawable.broken_clouds_hd);
                break;
            case "09n":
                icon.setImageResource(R.drawable.heavy_rain_hd);
                break;
            case "10n":
                icon.setImageResource(R.drawable.light_rain_hd);
                break;
            case "11n":
                icon.setImageResource(R.drawable.thunderstorm_hd);
                break;
            case "13n":
                icon.setImageResource(R.drawable.snow_hd);
                break;
            case "50n":
                icon.setImageResource(R.drawable.haze_hd);
                break;
        }

        /* Display 7 days (include current day) at the bottom */
        for (int i = 0; i < 7; ++i) {

            /* Strings for 7 days display */
            String tempMaxText;
            String tempMinText;
            if (unitStatus) {
                tempMaxText = String.valueOf(dataArray.getData()[i].getTemperatureMaxInCelsius()) + "°";
                tempMinText = String.valueOf(dataArray.getData()[i].getTemperatureMinInCelsius()) + "°";
            } else {
                tempMaxText = String.valueOf(dataArray.getData()[i].getTemperatureMaxInFahrenheit()) + "°";
                tempMinText = String.valueOf(dataArray.getData()[i].getTemperatureMinInFahrenheit()) + "°";
            }

            String dateText = dataArray.getData()[i].getMonthDay();

            tempMax[i].setText(tempMaxText);
            tempMin[i].setText(tempMinText);
            date[i].setText(dateText);

            switch (dataArray.getData()[i].getIcon()) {//匹配得到的图标数据与本地数据，设置天气图标
                case "01d":
                    iconDays[i].setImageResource(R.drawable.clear_sky_day);
                    break;
                case "02d":
                    iconDays[i].setImageResource(R.drawable.few_clouds_day);
                    break;
                case "03d":
                    iconDays[i].setImageResource(R.drawable.scattered_clouds);
                    break;
                case "04d":
                    iconDays[i].setImageResource(R.drawable.broken_clouds);
                    break;
                case "09d":
                    iconDays[i].setImageResource(R.drawable.heavy_rain);
                    break;
                case "10d":
                    iconDays[i].setImageResource(R.drawable.light_rain);
                    break;
                case "11d":
                    iconDays[i].setImageResource(R.drawable.thunderstorm);
                    break;
                case "13d":
                    iconDays[i].setImageResource(R.drawable.snow);
                    break;
                case "50d":
                    iconDays[i].setImageResource(R.drawable.haze);
                    break;
                case "01n":
                    iconDays[i].setImageResource(R.drawable.clear_sky_night);
                    break;
                case "02n":
                    iconDays[i].setImageResource(R.drawable.few_clouds_night);
                    break;
                case "03n":
                    iconDays[i].setImageResource(R.drawable.scattered_clouds);
                    break;
                case "04n":
                    iconDays[i].setImageResource(R.drawable.broken_clouds);
                    break;
                case "09n":
                    iconDays[i].setImageResource(R.drawable.heavy_rain);
                    break;
                case "10n":
                    iconDays[i].setImageResource(R.drawable.light_rain);
                    break;
                case "11n":
                    iconDays[i].setImageResource(R.drawable.thunderstorm);
                    break;
                case "13n":
                    iconDays[i].setImageResource(R.drawable.snow);
                    break;
                case "50n":
                    iconDays[i].setImageResource(R.drawable.haze);
                    break;
            }
        }

        setUnitTextOpacity(unitStatus);
    }
    public void displayTemperatureOnly (WeatherDataMultiDays dataArray, WeatherData data, boolean unitStatus) {
        /* Determine temperature unit */
        String tempText;
        if (unitStatus) { tempText = String.valueOf(data.getTemperatureInCelsius()); }
        else {tempText = String.valueOf(data.getTemperatureInFahrenheit());}

        temperature.setText(tempText);

        /* Display 7 days (include current day) at the bottom */
        for (int i = 0; i < 7; ++i) {

            /* Strings for 7 days display */
            String tempMaxText;
            String tempMinText;
            if (unitStatus) {
                tempMaxText = String.valueOf(dataArray.getData()[i].getTemperatureMaxInCelsius()) + "°";
                tempMinText = String.valueOf(dataArray.getData()[i].getTemperatureMinInCelsius()) + "°";
            } else {
                tempMaxText = String.valueOf(dataArray.getData()[i].getTemperatureMaxInFahrenheit()) + "°";
                tempMinText = String.valueOf(dataArray.getData()[i].getTemperatureMinInFahrenheit()) + "°";
            }

            tempMax[i].setText(tempMaxText);
            tempMin[i].setText(tempMinText);
        }

        setUnitTextOpacity(unitStatus);
    }
    public void searchControlFocus (final MainActivity mainActivity) {
        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    contentLayout.setVisibility(View.INVISIBLE);
                    search.getText().clear();
                    //searchControlAction(mainActivity, unitStatus);
                } else {
                    String searchPrompt = "Enter a city name";
                    search.setText(searchPrompt);
                    hideSoftKeyboard();
                    contentLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void searchControlAction (final MainActivity mainActivity) {
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_NEXT) {
                    mainActivity.showFullWhether(mainActivity.updateWeather());
                    handled = true;
                }
                String searchPrompt = "Enter a city name";
                search.setText(searchPrompt);
                clearSearchFocus();
                hideSoftKeyboard();
                return handled;
            }
        });
    }

    public void clearSearchFocus() {
        search.clearFocus();
    }

    public void showDialogLocationFail () {
        FragmentManager fm = activity.getFragmentManager();
        DialogLocationFail dialog = new DialogLocationFail();
        dialog.show(fm, "DialogLocationFail");
    }

    public void showDialogSearchFail () {
        FragmentManager fm = activity.getFragmentManager();
        DialogSearchFail dialog = new DialogSearchFail();
        dialog.show(fm, "DialogSearchFail");
    }

    public void showDialogNetworkFail () {
        FragmentManager fm = activity.getFragmentManager();
        DialogNetworkFail dialog = new DialogNetworkFail();
        dialog.show(fm, "DialogNetworkFail");
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
    }

    public void setUnitTextOpacity (boolean unitStatus) {
        if (unitStatus) {
            fahrenheit.setTextColor(Color.argb(255, 255, 207, 124));
            fahrenheit.setTypeface(null, Typeface.NORMAL);
            celsius.setTypeface(null, Typeface.BOLD);
            celsius.setTextColor(Color.argb(255, 255, 255, 255));
        } else {
            celsius.setTextColor(Color.argb(255, 255, 207, 124));
            celsius.setTypeface(null, Typeface.NORMAL);
            fahrenheit.setTypeface(null, Typeface.BOLD);
            fahrenheit.setTextColor(Color.argb(255, 255, 255, 255));
        }
    }
}

