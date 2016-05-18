package at.acid.conquer;

import android.app.Service;
import android.content.Intent;
import android.location.*;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.*;

import java.lang.ref.WeakReference;

/**
 * Created by menzi
 * 22.04.2016.
 */
public class LocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {
    public static final String TAG = "LocationService";
    public static final int GPS_UPDATE_INTERVAL = 15000;
    public static final int GPS_UPDATE_INTERVAL_MIN = 10000;

    protected GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    // Binder (that client can call this class)
    private final IBinder mBinder = new LocalBinder();
    public class LocalBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }

    // Client ref (that this class can call client)
    private WeakReference<LocationServiceClient> mClient;
    public interface LocationServiceClient {
        void onLocationUpdate(Location location);
    }

    //----------------------------------------------------------------------------------------------
    public void setServiceClient(LocationServiceClient client) {
        Log.d(TAG, "setServiceClient()");
        if (client == null) {
            mClient = null;
            return;
        }
        mClient = new WeakReference<LocationServiceClient>(client);
    }

    //----------------------------------------------------------------------------------------------
    public Location getLocation() {
        Log.d(TAG, "getLocation()");
        return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override//-------------------------------------------------------------------------------------
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = new LocationRequest()
                .setInterval(GPS_UPDATE_INTERVAL)
                .setFastestInterval(GPS_UPDATE_INTERVAL_MIN)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override//-------------------------------------------------------------------------------------
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        mGoogleApiClient.connect();
        return mBinder;
    }

    @Override//-------------------------------------------------------------------------------------
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStart()");
        mGoogleApiClient.connect();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override//-------------------------------------------------------------------------------------
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        if( mGoogleApiClient.isConnected() ) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        super.onDestroy();
    }

    @Override//-------------------------------------------------------------------------------------
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "onConnected()");
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override//-------------------------------------------------------------------------------------
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended()");
    }

    @Override//-------------------------------------------------------------------------------------
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed()");
    }

    @Override//-------------------------------------------------------------------------------------
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged()");
        if (mClient != null) {
            mClient.get().onLocationUpdate(location);
        }
    }

    //----------------------------------------------------------------------------------------------
    public void pushMockLocation(double lat, double lng, long time){
        Log.d(TAG, "pushMockLocation()");
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(lat);
        location.setLongitude(lng);
        location.setAccuracy(4.0f);
        location.setTime(time);
        location.setElapsedRealtimeNanos(time); //wrong time, never used, but needet
        LocationServices.FusedLocationApi.setMockMode(mGoogleApiClient, true);
        LocationServices.FusedLocationApi.setMockLocation(mGoogleApiClient, location);
    }

    //----------------------------------------------------------------------------------------------
    public boolean isConnected(){
        return mGoogleApiClient.isConnected();
    }
}
