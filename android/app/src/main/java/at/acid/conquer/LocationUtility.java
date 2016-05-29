package at.acid.conquer;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by florian on 09.03.2016.
 */
public abstract class LocationUtility {
    public static final String TAG = "Utility";
    public static final float MAX_VALID_SPEED = 20.0f; // in km/h
    public static final long MAX_VALID_TIME_DIFFERENCE = 60; // in seconds


    //----------------------------------------------------------------------------------------------
    public static boolean validDistance(Location loc1, Location loc2) {
        if (loc1 == null || loc2 == null)
            return false;

        float kmh = getSpeed(loc1, loc2);
        long timeDifferenceInSeconds = Math.abs(loc1.getTime() - loc2.getTime()) / 1000;

        Log.d(TAG, "km/h = " + kmh);

        if (kmh > MAX_VALID_SPEED) {
            Log.d(TAG, "rejecting position update, since distance is too high for timespan!");
            return false;
        }

        if (timeDifferenceInSeconds > MAX_VALID_TIME_DIFFERENCE) {
            Log.d(TAG, "rejecting position update, since time difference is too big!");
            return false;
        }

        return true;
    }

    //----------------------------------------------------------------------------------------------
    public static float getSpeed(Location loc1, Location loc2) {
        if (loc1 == null || loc2 == null)
            return 0.0f;

        float distInMeters = loc1.distanceTo(loc2);
        long timeDifferenceInSeconds = Math.abs(loc1.getTime() - loc2.getTime()) / 1000;
        float meterPerSecond = distInMeters / timeDifferenceInSeconds;
        float kmh = meterPerSecond * 3.6f;
        return kmh;
    }

    //----------------------------------------------------------------------------------------------
    public static LatLng getLatLng(Location location){
        return new LatLng(location.getLatitude(), location.getLongitude());
    }
}
