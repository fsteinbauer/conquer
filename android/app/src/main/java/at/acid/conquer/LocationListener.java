package at.acid.conquer;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import at.acid.conquer.model.Area;

/**
 * Created by florian on 09.03.2016.
 */
public class LocationListener implements android.location.LocationListener {

    public static final String TAG = "LocationListener";

    private LocationUtility mUtility;

    public LocationListener(LocationUtility mUtility) {
        Log.d(TAG, "Location Listener Created");
        this.mUtility = mUtility;
    }

    public void onLocationChanged(Location location){
        Log.d(TAG, "Location Changed: "+location.toString());

        this.mUtility.handleLocationUpdate(location);
    }

    public void onProviderDisabled(String provider){
        Log.d(TAG, "Provider Disabled: " + provider);

    }

    public void onProviderEnabled(String provider){
        Log.d(TAG, "Provider Enabled: "+provider);
    }

    public void onStatusChanged(String provider, int status, Bundle extras){
        Log.d(TAG, "Status Changed: "+provider);
    }



}
