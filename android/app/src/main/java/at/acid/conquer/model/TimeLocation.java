package at.acid.conquer.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by menzi on 24.04.2016.
 */
public class TimeLocation {
    public static final String TAG = "TimeLocation";
    public long mTime;
    public Location mLocation;

    //----------------------------------------------------------------------------------------------
    public TimeLocation(Location location){
        mTime = System.currentTimeMillis();
        mLocation = location;
    }

    //----------------------------------------------------------------------------------------------
    public TimeLocation(long time, Location location){
        mTime = time;
        mLocation = location;
    }

    //----------------------------------------------------------------------------------------------
    public TimeLocation( TimeLocation tloc ){
        mTime = tloc.mTime;
        mLocation = tloc.mLocation;
    }

    //----------------------------------------------------------------------------------------------
    public LatLng getLatLng(){
        return new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
    }
}
