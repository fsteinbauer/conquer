package at.acid.conquer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import at.acid.conquer.data.Areas;
import at.acid.conquer.fragments.AccountFragment;
import at.acid.conquer.fragments.HighscoreFragment;
import at.acid.conquer.fragments.MapFragment;
import at.acid.conquer.model.Area;
import at.acid.conquer.model.Route;
import at.acid.conquer.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.*;

import static at.acid.conquer.Utility.getLatLng;
import static at.acid.conquer.Utility.validDistance;


public class MapsActivity extends FragmentActivity implements TabLayout.OnTabSelectedListener, OnMapReadyCallback, LocationService.LocationServiceClient {
    public static final String TAG = "MapsActivity";
    public final static int TAB_MAP = 0;
    public final static int TAB_HIGHSCORE = 1;
    public final static int TAB_ACCOUNT = 2;
    public static final float DEFAULT_ZOOM = 16.0f;


    private TabLayout mTabLayout;

    private Timer timer;
    private TimerTask timerTask;
    public boolean mIsRunning;
    private Route mRoute;
    private List<Area> mAreas = new ArrayList<>();

    private Location mLastLocation;
    private Area currentArea;
    private Marker mMarker;

    public GoogleMap mGoogleMap;
    private LocationService mLocationService;

    private PolylineOptions mPathLineOptions = new PolylineOptions().width(10.0f).color(Color.RED);

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

    @Override//-------------------------------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.d(TAG, "Got to Line: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        User user = new User(getBaseContext());

        user.saveData();

        user.updateArea(2, 22, 27, 22);

        user.saveData();

        initGUIElements();
    }

    private void initGUIElements() {
        MapFragment mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_fragment_container, mapFragment).commit();
        mTabLayout = (TabLayout) findViewById(R.id.tl_tabs);
        mTabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        final int position = tab.getPosition();
        switch (position) {
            case TAB_MAP:
                Log.d(TAG, "Maps clicked");
                MapFragment mapFragment = new MapFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, mapFragment).commit();
                break;

            case TAB_HIGHSCORE:
                Log.d(TAG, "Highscores clicked");
                HighscoreFragment highscoreFragment = new HighscoreFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, highscoreFragment).commit();
                break;
            case TAB_ACCOUNT:
                Log.d(TAG, "Account clicked");
                AccountFragment accountFragment = new AccountFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, accountFragment).commit();
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void startTimer() {

        timerTask = new TimerTask() {
            Calendar startTimeCalendar = Calendar.getInstance();
            final long startTime = startTimeCalendar.getTimeInMillis();


            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Calendar calendar = Calendar.getInstance();
                        long endTime = calendar.getTimeInMillis();
                        long difference = endTime - startTime;

                        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_fragment_container);
                        if (fragment != null && fragment instanceof MapFragment) {
                            ((MapFragment) getSupportFragmentManager().findFragmentById(R.id.fl_fragment_container)).updateDuration(difference);
                        }
                    }
                });
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }

    public void stopTimer() {
        timer.cancel();
        ((MapFragment) getSupportFragmentManager().findFragmentById(R.id.fl_fragment_container)).updateDuration(0);
    }


    @Override
    public void onLocationUpdate(final Location location) {
        if (location == null) {
            Log.d(TAG, "onLocationUpdate(): unknown position");
            return;
        }

        Log.d(TAG, "onLocationUpdate(): " + location.toString());


        Location loc = new Location(location);
        float distance = 0;

        // distance to last valid point is valid - add point
        if (validDistance(mRoute.getLastLocation(), loc)) {
            distance = mRoute.addLocationToCurrentPath(loc);
        }
        // distance to last point is valid - create new route
        else if (validDistance(mLastLocation, loc)) {
            distance = mRoute.addLocationToNewPath(mLastLocation, loc);
        }

        mMarker.setPosition(getLatLng(loc));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(getLatLng(loc)));

        currentArea = null;
        for (Area area : mAreas) {
            if (area.inArea(getLatLng(loc))) {
                area.addDistance(distance);
                currentArea = area;
                break;
            }
        }

        mLastLocation = loc;

        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_fragment_container);
        if (fragment != null && fragment instanceof MapFragment) {
            ((MapFragment) getSupportFragmentManager().findFragmentById(R.id.fl_fragment_container)).updateInfo(mRoute, currentArea);
        }


    }


    @Override//-------------------------------------------------------------------------------------
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady()");
        mGoogleMap = googleMap;
        MapsInitializer.initialize(this);

        mRoute = (new Route(googleMap, mPathLineOptions));

        // default locatin: graz
        Location location = new Location("Wikipedia");
        location.setLatitude(47.067);
        location.setLongitude(15.433);

        for (String json : Areas.mAreas) {
            Area area = new Area("");
            area.loadJson(json);
            mAreas.add(area);
            area.draw(googleMap, Color.BLUE);
        }


//        if (mLastLocation != null) {
//            location = mLocationService.getLocation();
//        }


        mLastLocation = location;
        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, DEFAULT_ZOOM));

        mMarker = googleMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title("me"));

    }

    public void startTracking() {
        Log.d(TAG, "startTracking()");
        Context context = this;
        Intent intent = new Intent(context, LocationService.class);
        context.startService(intent);
        context.bindService(intent, mLocationServiceConnection, Context.BIND_AUTO_CREATE);
        startTimer();
    }


    public void stopTracking() {
        Log.d(TAG, "stopTracking()");
        Context context = this;
        Intent intent = new Intent(context, LocationService.class);
        context.unbindService(mLocationServiceConnection);
        context.stopService(intent);
        stopTimer();
    }
}
