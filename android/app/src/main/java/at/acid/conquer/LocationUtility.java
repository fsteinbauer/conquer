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
    public static final long MAX_VALID_TIME_DIFFERENCE = 5 * 60; // in seconds

    Map<Long, Location> locations = new HashMap<>();

    public boolean addLocation(Long time, Location location) {
        if (time == null || location == null) {
            return false;
        }
        if (locations.isEmpty()) {
            locations.put(time, location);
            return true;
        }
        if (!validateLocation(time, location)) {
            return false;
        }

        locations.put(time, location);

        Log.d(TAG, "Added New Location: " + location + " at time:" + time);
        return true;
    }


    public boolean wurst(Long timeOfLocation, Location network, Location gps) {
        if (network == null && gps == null) {
            return false;
        }

        boolean network_valid = validateLocation(timeOfLocation, network);
        boolean gps_valid = validateLocation(timeOfLocation, gps);

        if (!network_valid && !gps_valid) {

            return false;
        } else if (!network_valid && gps_valid) {

            locations.put(timeOfLocation, gps);
            return true;
        } else if (network_valid && !gps_valid) {

            locations.put(timeOfLocation, network);
            return true;
        }

        // locations.put(timeOfLocation, findBetterMatch(network, gps));
        return true;

    }

    private boolean validateLocation(long currentTime, Location location) {
        if (location == null) {
            return false;
        }


        for (Map.Entry<Long, Location> old_location :
                locations.entrySet()) {
            if ((currentTime - old_location.getKey()) > 60000)

            {
                continue;
            }


            float distInMeters = location.distanceTo(old_location.getValue());

            double meterPerSecond = distInMeters / ((currentTime - old_location.getKey()) / 1000);

            double kmh = meterPerSecond * 3.6;


            Log.d(TAG, "KMH = " + kmh);

            if (kmh > 20.0) {
                Log.d(TAG, "rejecting position update, since distance is too high for timespan!");
                return false;
            }
        }
        return true;
    }

    //----------------------------------------------------------------------------------------------
    public static boolean validDistance(TimeLocation tloc1, TimeLocation tloc2) {
        if (tloc1 == null || tloc2 == null)
            return false;

        float distInMeters = tloc1.mLocation.distanceTo(tloc2.mLocation);
        long timeDifferenceInSeconds = Math.abs(tloc1.mTime - tloc2.mTime) / 1000;
        float meterPerSecond = distInMeters / timeDifferenceInSeconds;
        float kmh = meterPerSecond * 3.6f;

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
}
