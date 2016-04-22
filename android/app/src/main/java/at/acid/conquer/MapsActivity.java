package at.acid.conquer;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.util.TimeUtils;
import android.text.format.Time;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import at.acid.conquer.model.Area;
import at.acid.conquer.model.Route;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String TAG = "MapsActivity";

    private GoogleMap mMap;
    private LocationUtility mLocationUtility;
    private LocationManager mLocationManager;
    private Polyline mPolyline;
    private CameraPosition cameraPosition;

    private List<Location> network_locations = new LinkedList<>();
    private List<Location> gps_locations = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mLocationUtility = new LocationUtility() {
            @Override
            protected void changeRoute(Route route) {

            }

            @Override
            protected void addArea(Area area) {

            }
        };

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,new LocationListener(mLocationUtility));

        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,new LocationListener(mLocationUtility));
        Timer timer = new Timer();


        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Location gps_location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Location network_location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                gps_locations.add(gps_location);
                network_locations.add(network_location);


                Log.d(TAG, "" + gps_location);
                Log.d(TAG, "" +network_location);

                long time = System.currentTimeMillis();

                mLocationUtility.addLocation(time, gps_location);
                mLocationUtility.addLocation(time, network_location);
            }
        }, 5000, 5000);





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
                    //.tilt(45)
                    .build();

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(this.cameraPosition));
        } else {
            Log.d(TAG, "Could not find Location");
        }

        // Create Polyline
        /*PolylineOptions lineOptions = new PolylineOptions()
                .width(10)
                .color(Color.RED)
                .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0));
        mPolyline = mMap.addPolyline(lineOptions);*/


    }



}
