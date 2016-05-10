package at.acid.conquer.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import at.acid.conquer.LocationService;
import at.acid.conquer.R;
import at.acid.conquer.data.Areas;
import at.acid.conquer.model.Area;
import at.acid.conquer.model.Route;
import at.acid.conquer.model.TimeLocation;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static at.acid.conquer.LocationUtility.getSpeed;
import static at.acid.conquer.LocationUtility.validDistance;

/**
 * Created by trewurm
 * 04.05.2016.
 */
public class MapFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback, LocationService.LocationServiceClient {
    public static final String TAG = "MapFragment";
    public static final float DEFAULT_ZOOM = 16.0f;

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private FloatingActionButton mFABTrackingInfo;

    private PolylineOptions mPathLineOptions = new PolylineOptions().width(10.0f).color(Color.RED);
    private Route mRoute;
    private List<Area> mAreas = new ArrayList<>();
    private TimeLocation mLastLocation;
    private LocationService mLocationService;

    private boolean mIsRunning;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Got to Line: "+Thread.currentThread().getStackTrace()[2].getLineNumber());


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mFABTrackingInfo = (FloatingActionButton) rootView.findViewById(R.id.fab_run_stop);
        mFABTrackingInfo.setOnClickListener(this);

        Log.d(TAG, "Got to Line: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        // Gets the MapView from the XML layout and creates it
        mMapView = (MapView) rootView.findViewById(R.id.mv_map);
        Log.d(TAG, "Got to Line: "+Thread.currentThread().getStackTrace()[2].getLineNumber());
        final Bundle mapViewSavedInstanceState = savedInstanceState != null ? savedInstanceState.getBundle("mapViewSaveState") : null;
        Log.d(TAG, "Got to Line: "+Thread.currentThread().getStackTrace()[2].getLineNumber());
        mMapView.onCreate(mapViewSavedInstanceState);

        Log.d(TAG, "Got to Line: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        // Gets to GoogleMap from the MapView and does initialization stuff
        mMapView.getMapAsync(this);

        Log.d(TAG, "Got to Line: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_run_stop:
                if (mIsRunning) {
                    mIsRunning = false;
//                    stopTracking();
                    mFABTrackingInfo.setImageResource(R.drawable.ic_run);
//                    mFABTrackingInfo.setBackgroundTintList(ColorStateList.valueOf(ContextCompact.getColor(v.getContext(), R.color.green)));

                } else {
                    mIsRunning = true;
//                    startTracking();
                    mFABTrackingInfo.setImageResource(R.drawable.ic_hotel);
//                    mFABTrackingInfo.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(v.getContext(), R.color.red)));
                }
                break;

        }
    }

    @Override//-------------------------------------------------------------------------------------
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady()");
        mGoogleMap = googleMap;
        MapsInitializer.initialize(getActivity());

        mRoute = new Route(mGoogleMap, mPathLineOptions);

        // default locatin: graz
        Location location = new Location("Wikipedia");
        location.setLatitude(47.067);
        location.setLongitude(15.433);

        for (String json : Areas.mAreas) {
            Area area = new Area("");
            area.loadJson(json);
            mAreas.add(area);
            area.draw(mGoogleMap, Color.BLUE);
        }


        if (mLastLocation != null) {
            location = mLocationService.getLocation();
        }

        mLastLocation = new TimeLocation(location);
        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, DEFAULT_ZOOM));
    }

    @Override
    public void onLocationUpdate(final Location location) {
        if (location == null) {
            Log.d(TAG, "onLocationUpdate(): unknown position");
            return;
        }

        if (mGoogleMap == null) {
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
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(tloc.getLatLng()));
        }
        // distance to last point is valid - create new route
        else if (validDistance(mLastLocation, tloc)) {
            mRoute.addLocationToNewPath(mLastLocation, tloc);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(tloc.getLatLng()));
        }

        mLastLocation = tloc;

        List<TimeLocation> path = mRoute.getCurrentPath();
        if (path != null && path.size() > 1) {
            TimeLocation tloc1 = path.get(path.size() - 1);
            TimeLocation tloc2 = path.get(path.size() - 2);
            float kmh = getSpeed(tloc1, tloc2);
            float distance = mRoute.getDistance() / 1000; // distance in km
            updateInfo(kmh, distance);
        }
    }


    private void startTracking() {
        Log.d(TAG, "startTracking()");

        Intent intent = new Intent(getContext(), LocationService.class);
        getContext().bindService(intent, mLocationServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void stopTracking() {
        Log.d(TAG, "stopTracking()");
        getContext().unbindService(mLocationServiceConnection);
    }

    //
    //----------------------------------------------------------------------------------------------
    private void updateInfo(float kmh, float distance) {
        String text = "Speed: " + String.format("%.3f", kmh) + " km/h";
        text += "\nDistance: " + String.format("%.3f", distance) + " km";
//        mTextInfo.setText(text);


    }


    //----------------------------------------------------------------------------------------------
    private void updateArea(CharSequence area) {
//        mTextArea.setText(area);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        //This MUST be done before saving any of your own or your base class's variables
        final Bundle mapViewSaveState = new Bundle(outState);
        mMapView.onSaveInstanceState(mapViewSaveState);
        outState.putBundle("mapViewSaveState", mapViewSaveState);
        //Add any other variables here.
        super.onSaveInstanceState(outState);
    }

    @Override
    public final void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}

