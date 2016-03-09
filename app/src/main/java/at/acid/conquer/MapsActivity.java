package at.acid.conquer;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import at.acid.conquer.model.Route;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String TAG = "MapsActivity";

    private GoogleMap mMap;
    private LocationUtility mLocationUtility;
    private LocationManager mLocationManager;
    private Polyline mPolyline;
    private CameraPosition cameraPosition;

    MockLocationProvider mock;

    List<LatLng> mockLatLng = new ArrayList<>();
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mock = new MockLocationProvider(LocationManager.NETWORK_PROVIDER, this);

        mockLatLng.add(new LatLng(47.060469, 15.468863));
        mockLatLng.add(new LatLng(47.0627766,15.4661078));
        mockLatLng.add(new LatLng(47.0616808,15.4634255));
        mockLatLng.add(new LatLng(47.0604991,15.462735));
        mockLatLng.add(new LatLng(47.0602757,15.4635859));
        mockLatLng.add(new LatLng(47.0617761,15.4671111));
        mockLatLng.add(new LatLng(47.060469, 15.468863));


        new Timer().scheduleAtFixedRate(new TimerTask() {


            @Override
            public void run() {
                Log.d(TAG, "TIMER");
                if(index < mockLatLng.size()){
                    mock.pushLocation(mockLatLng.get(index++));
                }
            }
        }, 10000, 1000);

        mLocationUtility = new LocationUtility() {
            @Override
            protected void changeRoute(Route route) {
                Log.d(TAG, "Polyline has Points: "+ route.getPoints().size());
                mPolyline.setPoints(route.getPoints());

            }
        };



        // Acquire a reference to the system Location Manager
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener listener = new LocationListener(mLocationUtility);

        // Register the listener with the Location Manager to receive location updates
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);

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
        mMap = googleMap;


        // If we find a Location, set the camera accordingly
        Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location != null){
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());

            this.cameraPosition = new CameraPosition.Builder()
                    .target(latlng)
                    .zoom(14)
                    .bearing(0)
                    .tilt(45)
                    .build();

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(this.cameraPosition));
        } else {
            Log.d(TAG, "Could not find Location");
        }

        // Create Polyline
        PolylineOptions lineOptions = new PolylineOptions()
                .width(10)
                .color(Color.RED)
                .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0));
        mPolyline = mMap.addPolyline(lineOptions);


    }



}
