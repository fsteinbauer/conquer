package at.acid.conquer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.location.Location;

import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import at.acid.conquer.model.Route;
import at.acid.conquer.model.TimeLocation;

import static at.acid.conquer.LocationUtility.validDistance;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        LocationService.LocationServiceClient {
    public static final String TAG = "MapsActivity";
    public static final float DEFAULT_ZOOM = 16.0f;

    private PolylineOptions mPathLineOptions = new PolylineOptions()
            .width(10.0f)
            .color(Color.RED);

    private GoogleMap mMap;
    private Route mRoute;
    private TimeLocation mLastLocation;
    private LocationService mLocationService;

    // handle bidirection connection to LocationService
    private ServiceConnection mLocationServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            mLocationService = ((LocationService.LocalBinder) service).getService();
            mLocationService.setServiceClient(MapsActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mLocationService.setServiceClient(null);
            mLocationService = null;
        }
    };

    @Override//-------------------------------------------------------------------------------------
    public void onLocationUpdate(final Location location) {
        if (location == null) {
            Log.d(TAG, "onLocationUpdate(): unknown position");
            return;
        }

        if (mMap == null) {
            Log.d(TAG, "onLocationUpdate(): wait for map");

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    onLocationUpdate(location);
                }
            }, 1000);
            return;
        }


        Log.d(TAG, "onLocationUpdate(): " + location.toString());

        TimeLocation tloc = new TimeLocation(location);

        // distance to last valid point is valid - add point
        if (validDistance(mRoute.getLastLocation(), tloc)) {
            mRoute.addLocationToCurrentPath(tloc);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(tloc.getLatLng()));
        }
        // distance to last point is valid - create new route
        else if (validDistance(mLastLocation, tloc)) {
            mRoute.addLocationToNewPath(mLastLocation, tloc);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(tloc.getLatLng()));
        }

        mLastLocation = tloc;
    }

    @Override//-------------------------------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Start LocationService
        Intent intent = new Intent(this, LocationService.class);
        bindService(intent, mLocationServiceConnection, Context.BIND_AUTO_CREATE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override//-------------------------------------------------------------------------------------
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady()");
        mMap = googleMap;
        mRoute = new Route(mMap, mPathLineOptions);

        // default locatin: graz
        Location location = new Location("Wikipedia");
        location.setLatitude(47.067);
        location.setLongitude(15.433);

        if (mLastLocation != null)
            location = mLocationService.getLocation();

        mLastLocation = new TimeLocation(location);
        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, DEFAULT_ZOOM));
    }
}
