/**
 *  本程序支持Android 4.1.2 - 5.1 (API 16 - 22)，暂不支持Android 6.0 (API 23)
 *
 *  EasyWhether V1.0
 *
 *  改进修复：
 *  1. 修复无网络连接时崩溃问题
 *  2. 修复触摸屏幕无法隐藏键盘问题
 *  3. 进入程序时不再自动弹出键盘
 *  4. 代码改进，性能大幅提高，卡顿减少
 *  5. UI细节改进 (例如去掉scrollbar)
 *  6. 点击搜索栏时隐藏天气UI
 *  7. 天气搜索可通过键盘完成键触发
 *  8. 加入摄氏度/华氏度转换
 *  9. 图片压缩，程序空间减小，UI响应加快
 *  10. 禁止横屏模式
 *  11. 修复未输入城市名的情况下依然能搜索的问题
 *  12. 进入程序后自动获取天气
 *  13. 添加程序ICON
 *  14. 修复其余多处崩溃问题
 */

package chaochenshi.weachercheck;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends Activity {    // MainActivity更简洁，新的功能拓展可以在新的封装好的类中进行
    private LocationController location;
    private static WeatherDataMultiDays weekData = new WeatherDataMultiDays(7);
    private static WeatherData todayData = new WeatherData();
    private static boolean gpsCalled;
    private static boolean unitStatus;  // true - Celsius, false - Fahrenheit
    private static GUIController UI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    // 启动程序时触发
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UI = new GUIController(this);
        if(isNetworkSuccess()) {
            if (!gpsCalled) {
                location = new LocationController(this, this);
                Location coordinates = location.getLocation();
                updateWeather(coordinates);
                gpsCalled = !(coordinates == null);
            }
            unitStatus = true;
            showFullWhether(gpsCalled);
        } else {
            UI.showDialogNetworkFail();
        }
        UI.searchControlFocus(this);
        UI.searchControlAction(this);
    }

    public void clickImageButtonSearch(View view) {   // 按钮触发
        showFullWhether(updateWeather());
    }

    public void clickTextViewFahrenheit (View view) {
        unitStatus = false;
        showTemperatureOnly(false);
    }

    public void clickTextViewCelsius (View view) {
        unitStatus = true;
        showTemperatureOnly(true);
    }

    /**
     * Method: updateWeather
     * Purpose: update weather data by search
     * Returns:
     * true     - Success
     * false    - Fail
     */
    public boolean updateWeather() {
        String searchText = UI.getSearchText();
        if (searchText.compareTo("Enter a city name") == 0 || searchText.compareTo("") == 0) {
            UI.clearSearchFocus();
            UI.showDialogSearchFail();
            return false;
        } else {
            todayData.updateData(UI.getSearchText()); // 用city name更新天气数据
            weekData.updateDataMultiDays(UI.getSearchText());
            UI.clearSearchFocus();
            return true;
        }
    }

    /**
     * Method: updateWeather (overloaded)
     * Purpose: update weather data by location
     * Arguments:
     * location  - Geographical coordinates
     * Returns:
     * true     - Success
     * false    - Fail
     */
    public boolean updateWeather(Location location) {
        if (location != null) {
            todayData.updateData(location);
            weekData.updateDataMultiDays(location);
            return true;
        } else {
            UI.showDialogLocationFail();
            return false;
        }
    }

    public void showFullWhether (boolean success) {
        if (success) {
            UI.displayWeather(weekData, todayData, unitStatus);
        }
    }

    public void showTemperatureOnly (boolean unitStatus) {
        UI.displayTemperatureOnly(weekData, todayData, unitStatus);
    }

    public void callPosition(View view) {   // 调用位置
        showFullWhether(updateWeather(location.getLocation()));
    }

    public void onClick(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private boolean isNetworkSuccess() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null) && (networkInfo.isConnected());
    }
}

