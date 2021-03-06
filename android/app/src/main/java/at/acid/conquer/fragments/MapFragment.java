package at.acid.conquer.fragments;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import at.acid.conquer.LocationService;
import at.acid.conquer.MainActivity;
import at.acid.conquer.R;
import at.acid.conquer.model.Area;
import at.acid.conquer.model.Route;
import at.acid.conquer.model.User;

import static at.acid.conquer.LocationUtility.getLatLng;
import static at.acid.conquer.LocationUtility.validDistance;


/**
 * Created by trewurm
 * 04.05.2016.
 */
public class MapFragment extends BaseClass implements View.OnClickListener, OnMapReadyCallback, LocationService.LocationServiceClient {
    public static final String TAG = "MapFragment";
    public static final float MAP_DEFAULT_ZOOM = 16f;
    public static final int AREA_COLOR_BORDER = Color.argb(192, 128, 128, 128);
    public static final int AREA_COLOR_UNKNOWN = Color.argb(64, 128, 128, 128);
    public static final int AREA_COLOR_GOOD = Color.argb(64, 0, 255, 0);
    public static final int AREA_COLOR_AVERAGE = Color.argb(64, 255, 255, 0);
    public static final int AREA_COLOR_BAD = Color.argb(64, 255, 0, 0);
    public static final int ROUTE_COLOR = Color.argb(255, 0, 0, 255);
    public static final float ROUTE_WIDTH = 10f;

    private MainActivity mMainActivity;

    private FloatingActionButton mFABTrackingInfo;

    private TextView mTVDuration;
    private TextView mTVDistance;
    private TextView mTVPoints;
    private TextView mTVArea;

    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean mIsRunning;

    private Route mRoute;
    private SparseArray<Area> mAreas = new SparseArray<>();

    private Location mLastLocation;
    private Area mCurrentArea;
    private Marker mMarker;

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private LocationService mLocationService;

    public static final int MY_LOCATION_PERMISSION = 7;

    // handle bidirection connection to LocationService
    private ServiceConnection mLocationServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mLocationService = ((LocationService.LocalBinder) service).getService();
            mLocationService.setServiceClient(MapFragment.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mLocationService.setServiceClient(null);
            mLocationService = null;
        }

    };

    @Override//-------------------------------------------------------------------------------------
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainActivity = ((MainActivity) getActivity());
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mFABTrackingInfo = (FloatingActionButton) rootView.findViewById(R.id.fab_run_stop);
        if (mIsRunning) {
            mFABTrackingInfo.setImageResource(R.drawable.ic_hotel);
        } else {
            mFABTrackingInfo.setImageResource(R.drawable.ic_run);
        }
        mFABTrackingInfo.setOnClickListener(this);

        mTVDuration = (TextView) rootView.findViewById(R.id.tv_trackinginfo_info_duration);
        mTVDistance = (TextView) rootView.findViewById(R.id.tv_trackinginfo_info_distance);
        mTVPoints = (TextView) rootView.findViewById(R.id.tv_trackinginfo_info_points);
        mTVArea = (TextView) rootView.findViewById(R.id.tv_trackinginfo_info_place);

        mAreas = loadAreasFromAssets("areas");


        // Gets the MapView from the XML layout and creates it
        mMapView = (MapView) rootView.findViewById(R.id.mv_map);
        final Bundle mapViewSavedInstanceState = savedInstanceState != null ? savedInstanceState.getBundle("mapViewSaveState") : null;
        mMapView.onCreate(mapViewSavedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mMapView.getMapAsync(this);

        updateInfo();


        return rootView;
    }


    @Override//-------------------------------------------------------------------------------------
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_run_stop:
                if (mIsRunning) {
                    mIsRunning = false;
                    stopTracking();

                    mFABTrackingInfo.setImageResource(R.drawable.ic_run);

                } else {
                    mIsRunning = true;
                    tryStartTracking();
                    mFABTrackingInfo.setImageResource(R.drawable.ic_hotel);
                }
                break;

        }
    }

    //----------------------------------------------------------------------------------------------
    public void startTimer() {
        mTimerTask = new TimerTask() {
            Calendar startTimeCalendar = Calendar.getInstance();
            final long startTime = startTimeCalendar.getTimeInMillis();


            @Override
            public void run() {
                mMainActivity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Calendar calendar = Calendar.getInstance();
                        long endTime = calendar.getTimeInMillis();
                        long difference = endTime - startTime;
                        updateDuration(difference);
                    }
                });
            }
        };

        updateDuration(0);
        mTimer = new Timer();
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    //----------------------------------------------------------------------------------------------
    public void stopTimer() {
        mTimer.cancel();
    }


    @Override//-------------------------------------------------------------------------------------
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

        mCurrentArea = null;
        for (int i = 0; i < mAreas.size(); i++) {
            Area area = mAreas.valueAt(i);
            if (area.inArea(getLatLng(loc))) {
                area.addDistance(distance);
                mCurrentArea = area;
                break;
            }
        }

        if (mCurrentArea != null) {
            User user = mMainActivity.getUser();
            user.updateArea(mCurrentArea.getId(), distance, (long) distance / 100);
        }

        mLastLocation = loc;

        updateInfo();
    }


    @Override//-------------------------------------------------------------------------------------
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady()");
        mGoogleMap = googleMap;
        MapsInitializer.initialize(getContext());

        for (int i = 0; i < mAreas.size(); i++) {
            Area area = mAreas.valueAt(i);
            area.draw(mGoogleMap, AREA_COLOR_UNKNOWN, AREA_COLOR_BORDER);
        }

        Location location = new Location("Graz");
        location.setLatitude(47.067);
        location.setLongitude(15.433);

        mLastLocation = location;
        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, MAP_DEFAULT_ZOOM));

        mMarker = googleMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title("me"));
    }


    public void tryStartTracking() {

        Log.d(TAG, "try start tracking");
        if (ContextCompat.checkSelfPermission(mMainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, "permission not granted");


            ActivityCompat.requestPermissions(mMainActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_PERMISSION
            );
            Log.d(TAG, "asking for permission");
            return;
        }


        startTracking();

    }

    //----------------------------------------------------------------------------------------------
    public void startTracking() {
        Log.d(TAG, "startTracking()");


        Context context = getContext();
        Intent intent = new Intent(context, LocationService.class);
        context.startService(intent);
        context.bindService(intent, mLocationServiceConnection, Context.BIND_AUTO_CREATE);
        startTimer();

        PolylineOptions mPathLineOptions = new PolylineOptions()
                .width(ROUTE_WIDTH)
                .color(ROUTE_COLOR);
        mRoute = new Route(mGoogleMap, mPathLineOptions);
    }

    //----------------------------------------------------------------------------------------------
    public void stopTracking() {
        Log.d(TAG, "stopTracking()");
        Context context = getContext();
        Intent intent = new Intent(context, LocationService.class);
        context.unbindService(mLocationServiceConnection);
        context.stopService(intent);
        stopTimer();

        User user = mMainActivity.getUser();
        user.addRoute(mRoute.getStartTime(),
                mRoute.getRunTime(),
                mRoute.getDistance(),
                (long) mRoute.getDistance() / 100);
        user.saveData();

        user.updateScore();
    }

    //----------------------------------------------------------------------------------------------
    public void updateDuration(long difference) {
        long second = (difference / 1000) % 60;
        long minute = (difference / (1000 * 60)) % 60;
        long hour = (difference / (1000 * 60 * 60));

        String time = String.format("%d:%02d:%02d", hour, minute, second);
        mTVDuration.setText(time);
    }

    //----------------------------------------------------------------------------------------------
    public void updateInfo() {
        String areaName = "Unknown Area";
        String points = "0";
        String distance = "0.00";

        if (mRoute != null) {
            points = Integer.toString((int) mRoute.getDistance() / 100);
            distance = String.format("%.2f", mRoute.getDistance() / 1000);
        }

        if (mCurrentArea != null) {
            areaName = mCurrentArea.getName();
        }

        mTVArea.setText(areaName);
        mTVPoints.setText(points);
        mTVDistance.setText(distance + "km");
    }


    @Override//-------------------------------------------------------------------------------------
    public void onSaveInstanceState(Bundle outState) {
        //This MUST be done before saving any of your own or your base class's variables
        final Bundle mapViewSaveState = new Bundle(outState);
        mMapView.onSaveInstanceState(mapViewSaveState);
        outState.putBundle("mapViewSaveState", mapViewSaveState);
        //Add any other variables here.
        super.onSaveInstanceState(outState);
    }

    @Override//-------------------------------------------------------------------------------------
    public final void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override//-------------------------------------------------------------------------------------
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override//-------------------------------------------------------------------------------------
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override//-------------------------------------------------------------------------------------
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    //----------------------------------------------------------------------------------------------
    SparseArray<Area> loadAreasFromAssets(String path) {
        ArrayList<String> areas_json = new ArrayList<String>();
        try {
            String[] area_files;
            area_files = getContext().getAssets().list(path);
            for (String filename : area_files) {
                StringBuilder buf = new StringBuilder();
                InputStream file = getContext().getAssets().open(path + "/" + filename);
                BufferedReader in = new BufferedReader(new InputStreamReader(file, "UTF-8"));
                String str;
                while ((str = in.readLine()) != null) {
                    buf.append(str);
                }
                in.close();
                areas_json.add(buf.toString());
                //Log.d(TAG, filename + " : " + buf.toString());
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to load area file \n" + e.getMessage());
        }

        for (String json : areas_json) {
            Area area = new Area();
            area.loadJson(json);
            mMainActivity.areaNames.add(area.getName());
            mAreas.put(area.getId(), area);
        }

        return mAreas;
    }

    @Override
    public void onFragmentSelected() {

    }

    public boolean ismIsRunning() {
        return mIsRunning;
    }
}

