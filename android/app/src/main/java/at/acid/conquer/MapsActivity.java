package at.acid.conquer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;

import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationService.LocationServiceClient {

    public static final String TAG = "MapsActivity";

    private GoogleMap mMap;
    private LocationUtility mLocationUtility;
    private LocationManager mLocationManager;
    private Polyline mPolyline;
    private List<LatLng> mRoute = new ArrayList<LatLng>();

    private ServiceConnection mLocationServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            mLocationService = ((LocationService.LocalBinder)service).getService();
            mLocationService.setServiceClient(MapsActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mLocationService.setServiceClient(null);
            mLocationService = null;
        }
    };


    private LocationService mLocationService;

    private List<Location> network_locations = new LinkedList<>();
    private List<Location> gps_locations = new LinkedList<>();

    @Override
    public void onLocationUpdate(final Location location) {
        if( location == null ) {
            Log.d(TAG, "onLocationUpdate(): unknown position");
            return;
        }

        if( mMap != null ) {
            Log.d(TAG, "onLocationUpdate(): " + location.toString());
            mRoute.add(new LatLng(location.getLatitude(), location.getLongitude()));
            mPolyline.setPoints(mRoute);
            mMap.moveCamera(
                    CameraUpdateFactory.newLatLng(
                            new LatLng(location.getLatitude(), location.getLongitude())
                    )
            );
        }
        else{
            Log.d(TAG, "onLocationUpdate(): wait for map");

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    onLocationUpdate(location);
                }
            }, 1000);
        }
    }

    @Override
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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady()");
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.zoomTo(17));

        PolylineOptions lineOptions = new PolylineOptions()
                .width(10)
                .color(Color.RED);
        mPolyline = mMap.addPolyline(lineOptions);
    }
}
