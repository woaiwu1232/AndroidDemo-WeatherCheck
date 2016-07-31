package chaochenshi.weachercheck;
// 这个类封装了调用GPS以及UI变化的过程
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationController {
    private static final int OUT_OF_SERVICE = 0;
    private static final int TEMPORARILY_UNAVAILABLE = 1;
    private static final int AVAILABLE = 2;
    private static Activity activity;          // 用来实现在non-activity类中控制GUI
    private static Context context;    // 用来实现在non-activity类中获取LOCATION_SERVICE

    public LocationController (Context varContext, Activity varActivity) {
        activity = varActivity;
        context = varContext;
    }

    public Location getLocation () {
        String gpsProvider = LocationManager.GPS_PROVIDER;
        LocationManager  lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location tempLocation = lm.getLastKnownLocation(gpsProvider);
        if (tempLocation == null) { //tempLocation == null
            String networkProvider = LocationManager.NETWORK_PROVIDER;
            LocationManager  lmNet = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Location tempLocationNet = lmNet.getLastKnownLocation(networkProvider);
            if (tempLocationNet == null) {
                return null;
            } else {
                System.out.println("Network Provider");
                return tempLocationNet;
            }
        } else {
            System.out.println("GPS Provider");
            return tempLocation;
        }
    }
}
