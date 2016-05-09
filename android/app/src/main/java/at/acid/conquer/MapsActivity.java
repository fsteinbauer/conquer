package at.acid.conquer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import at.acid.conquer.data.Areas;
import at.acid.conquer.model.Area;
import at.acid.conquer.model.Route;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static at.acid.conquer.Utility.getLatLng;
import static at.acid.conquer.Utility.getSpeed;
import static at.acid.conquer.Utility.validDistance;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationService.LocationServiceClient, View.OnClickListener {
    public static final String TAG = "MapsActivity";
    public static final float DEFAULT_ZOOM = 16.0f;

    private PolylineOptions mPathLineOptions = new PolylineOptions().width(10.0f).color(Color.RED);

    private GoogleMap mMap;
    private Route mRoute;
    private Marker mMarker;
    private List<Area> mAreas = new ArrayList<Area>();
    private Location mLastLocation;
    private LocationService mLocationService;

    private ImageButton mButtonStartStop;

    private TextView mTextArea;
    private TextView mTextInfo;

    private boolean isTracking;

    // handle bidirection connection to LocationService
    private ServiceConnection mLocationServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mLocationService = ((LocationService.LocalBinder) service).getService();
            mLocationService.setServiceClient(MapsActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mLocationService.setServiceClient(null);
            mLocationService = null;
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override//-------------------------------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Connect GUI elements
        mButtonStartStop = (ImageButton) findViewById(R.id.ButtonPlayStop);
        mButtonStartStop.setOnClickListener(this);


        mTextArea = (TextView) findViewById(R.id.TextArea);
        mTextInfo = (TextView) findViewById(R.id.TextInfo);
        updateInfo(0, 0);
        updateArea("unknown");

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

        for (String json : Areas.mAreas) {
            Area area = new Area("");
            area.loadJson(json);
            mAreas.add(area);
            area.draw(mMap, Color.BLUE);
        }


        if (mLastLocation != null) {
            location = mLocationService.getLocation();
        }

        mLastLocation = location;
        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, DEFAULT_ZOOM));

        mMarker = mMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title("me"));
    }

    @Override//-------------------------------------------------------------------------------------
    public void onLocationUpdate(final Location loc) {
        if (loc == null) {
            Log.d(TAG, "onLocationUpdate(): unknown position");
            return;
        }

        if (mMap == null) {
            Log.d(TAG, "onLocationUpdate(): wait for map");

            new Timer().schedule(new TimerTask() {
                public void run() {
                    onLocationUpdate(loc);
                }
            }, 1000);
            return;
        }


        Log.d(TAG, "onLocationUpdate(): " + loc.toString());

        float distance = 0;

        // distance to last valid point is valid - add point
        if (validDistance(mRoute.getLastLocation(), loc)) {
            distance = mRoute.addLocationToCurrentPath(loc);
        }
        // distance to last point is valid - create new route
        else if (validDistance(mLastLocation, loc)) {
            distance = mRoute.addLocationToNewPath(mLastLocation, loc);
        }

        Area currentArea = null;
        for( Area area : mAreas ){
            if( area.inArea(getLatLng(loc)) ){
                area.addDistance( distance );
                currentArea = area;
                break;
            }
        }

        mMarker.setPosition(getLatLng(loc));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(getLatLng(loc)));

        mLastLocation = loc;

        List<Location> path = mRoute.getCurrentPath();
        if (path != null && path.size() > 1) {
            Location loc1 = path.get(path.size() - 1);
            Location loc2 = path.get(path.size() - 2);
            float kmh = getSpeed(loc1, loc2);
            if( currentArea != null ){
                updateInfo(kmh, currentArea.getTravelDistance() / 1000);
                updateArea(currentArea.getName());
            }
            else{
                updateInfo(kmh, mRoute.getDistance() / 1000);
                updateArea("unknown");
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    private void startTracking() {
        Log.d(TAG, "startTracking()");
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
        bindService(intent, mLocationServiceConnection, Context.BIND_AUTO_CREATE);

    }

    //----------------------------------------------------------------------------------------------
    private void stopTracking() {
        Log.d(TAG, "stopTracking()");
        Intent intent = new Intent(this, LocationService.class);
        unbindService(mLocationServiceConnection);
        stopService(intent);
    }

    @Override//-------------------------------------------------------------------------------------
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "App Killed, going to stop Location Service");
        if( mLocationService != null ) {
            Intent intent = new Intent(this, LocationService.class);
            unbindService(mLocationServiceConnection);
            stopService(intent);
        }
    }

    @Override//-------------------------------------------------------------------------------------
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ButtonPlayStop: {
                if (isTracking) {
                    isTracking = false;
                    stopTracking();
                    TranslationAnimator.translateAndScaleCenter(mButtonStartStop);
                } else {
                    isTracking = true;
                    TranslationAnimator.translateAndScaleToBottomRight(mButtonStartStop);
                    startTracking();
                }

                break;
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    private void updateInfo(float kmh, float distance) {
        String text = "Speed: " + String.format("%.3f", kmh) + " km/h";
        text += "\nDistance: " + String.format("%.3f", distance) + " km";
        mTextInfo.setText(text);
    }

    //----------------------------------------------------------------------------------------------
    private void updateArea(CharSequence area) {
        mTextArea.setText(area);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://at.acid.conquer/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://at.acid.conquer/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
