package at.acid.conquer;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

import at.acid.conquer.model.Area;
import at.acid.conquer.model.Route;
import at.acid.conquer.model.TimeLocation;

/**
 * Created by florian on 09.03.2016.
 */
public abstract class LocationUtility {
    public static final String TAG = "LocationUtility";
    public static final float MAX_VALID_SPEED = 20.0f; // in km/h
    public static final long MAX_VALID_TIME_DIFFERENCE = 60; // in seconds


    //----------------------------------------------------------------------------------------------
    public static boolean validDistance(TimeLocation tloc1, TimeLocation tloc2) {
        if (tloc1 == null || tloc2 == null)
            return false;

        float kmh = getSpeed(tloc1, tloc2);
        long timeDifferenceInSeconds = Math.abs(tloc1.mTime - tloc2.mTime) / 1000;

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
    public static float getSpeed(TimeLocation tloc1, TimeLocation tloc2) {
        if (tloc1 == null || tloc2 == null)
            return 0.0f;

        float distInMeters = tloc1.mLocation.distanceTo(tloc2.mLocation);
        long timeDifferenceInSeconds = Math.abs(tloc1.mTime - tloc2.mTime) / 1000;
        float meterPerSecond = distInMeters / timeDifferenceInSeconds;
        float kmh = meterPerSecond * 3.6f;
        return kmh;
    }

    //----------------------------------------------------------------------------------------------
    public static int setAlpha(int color, float alpha){
        color &= 0x00ffffff; // reset alpha bits
        color |= ((char)(255*(1-alpha))) << 24;
        return color;
    }
}
