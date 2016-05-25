package at.acid.conquer.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import at.acid.conquer.MapsActivity;
import at.acid.conquer.R;
import at.acid.conquer.model.Area;
import at.acid.conquer.model.Route;
import com.google.android.gms.maps.MapView;


/**
 * Created by trewurm
 * 04.05.2016.
 */
public class MapFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "MapFragment";

    private MapView mMapView;

    private FloatingActionButton mFABTrackingInfo;

    private TextView mTVDuration;
    private TextView mTVDistance;
    private TextView mTVPoints;
    private TextView mTVArea;


    private MapsActivity mapsActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Got to Line: " + Thread.currentThread().getStackTrace()[2].getLineNumber());


        mapsActivity = ((MapsActivity) getActivity());

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mFABTrackingInfo = (FloatingActionButton) rootView.findViewById(R.id.fab_run_stop);
        if (mapsActivity.mIsRunning) {
            mFABTrackingInfo.setImageResource(R.drawable.ic_hotel);
        } else {
            mFABTrackingInfo.setImageResource(R.drawable.ic_run);
        }
        mFABTrackingInfo.setOnClickListener(this);

        mTVDuration = (TextView) rootView.findViewById(R.id.tv_trackinginfo_info_duration);
        mTVDistance = (TextView) rootView.findViewById(R.id.tv_trackinginfo_info_distance);
        mTVPoints = (TextView) rootView.findViewById(R.id.tv_trackinginfo_info_points);
        mTVArea = (TextView) rootView.findViewById(R.id.tv_trackinginfo_info_place);


        // Gets the MapView from the XML layout and creates it
        mMapView = (MapView) rootView.findViewById(R.id.mv_map);
        final Bundle mapViewSavedInstanceState = savedInstanceState != null ? savedInstanceState.getBundle("mapViewSaveState") : null;
        mMapView.onCreate(mapViewSavedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mMapView.getMapAsync(mapsActivity);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_run_stop:
                if (mapsActivity.mIsRunning) {
                    mapsActivity.mIsRunning = false;
                    mapsActivity.stopTracking();

                    mFABTrackingInfo.setImageResource(R.drawable.ic_run);
//                    mFABTrackingInfo.setBackgroundTintList(ColorStateList.valueOf(ContextCompact.getColor(v.getContext(), R.color.green)));

                } else {
                    mapsActivity.mIsRunning = true;
                    mapsActivity.startTracking();
                    mFABTrackingInfo.setImageResource(R.drawable.ic_hotel);
//                    mFABTrackingInfo.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(v.getContext(), R.color.red)));
                }
                break;

        }
    }

//    @Override//-------------------------------------------------------------------------------------
//    public void onMapReady(GoogleMap googleMap) {
//        Log.d(TAG, "onMapReady()");
//        mapsActivity.setmGoogleMap(googleMap);
//        MapsInitializer.initialize(getActivity());
//
//        mapsActivity.setRoute(new Route(googleMap, mPathLineOptions));
//
//        // default locatin: graz
//        Location location = new Location("Wikipedia");
//        location.setLatitude(47.067);
//        location.setLongitude(15.433);
//
//        for (String json : Areas.mAreas) {
//            Area area = new Area("");
//            area.loadJson(json);
//            mAreas.add(area);
//            area.draw(googleMap, Color.BLUE);
//        }
//
//
//        if (mLastLocation != null) {
//            location = mLocationService.getLocation();
//        }
//
//
//        mLastLocation = location;
//        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, DEFAULT_ZOOM));
//
//        mMarker = googleMap.addMarker(new MarkerOptions()
//                .position(latlng)
//                .title("me"));
//
//    }

//    @Override
//    public void onLocationUpdate(final Location location) {
//        if (location == null) {
//            Log.d(TAG, "onLocationUpdate(): unknown position");
//            return;
//        }
//
//        if (mGoogleMap == null) {
//            Log.d(TAG, "onLocationUpdate(): wait for map");
//
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//                public void run() {
//                    onLocationUpdate(location);
//                }
//            }, 1000);
//            return;
//        }
//
//
//        Log.d(TAG, "onLocationUpdate(): " + location.toString());
//
//
//        Location loc = new Location(location);
//        float distance = 0;
//
//        // distance to last valid point is valid - add point
//        if (validDistance(mapsActivity.getmRoute().getLastLocation(), loc)) {
//            distance = mapsActivity.getmRoute().addLocationToCurrentPath(loc);
//        }
//        // distance to last point is valid - create new route
//        else if (validDistance(mLastLocation, loc)) {
//            distance = mapsActivity.getmRoute().addLocationToNewPath(mLastLocation, loc);
//        }
//
//        mMarker.setPosition(getLatLng(loc));
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(getLatLng(loc)));
//
//        Area currentArea = null;
//        for (Area area : mAreas) {
//            if (area.inArea(getLatLng(loc))) {
//                area.addDistance(distance);
//                currentArea = area;
//                break;
//            }
//        }
//
//        mLastLocation = loc;
//
//        List<Location> path = mapsActivity.getmRoute().getCurrentPath();
//        if (path != null && path.size() > 1) {
//            Location loc1 = path.get(path.size() - 1);
//            Location loc2 = path.get(path.size() - 2);
//            float kmh = getSpeed(loc1, loc2);
//            if (currentArea != null) {
//                updateInfo(kmh, currentArea.getTravelDistance() / 1000);
//                updateArea(currentArea.getName());
//            } else {
//                updateInfo(kmh, mapsActivity.getmRoute().getDistance() / 1000);
//                updateArea("unknown");
//            }
//        }
//    }
//
//    private void startTracking() {
//        Log.d(TAG, "startTracking()");
//        Context context = getContext();
//        Intent intent = new Intent(context, LocationService.class);
//        context.startService(intent);
//        context.bindService(intent, mLocationServiceConnection, Context.BIND_AUTO_CREATE);
//        ((MapsActivity) getActivity()).startTimer();
//    }
//
//
//    private void stopTracking() {
//        Log.d(TAG, "stopTracking()");
//        Context context = getContext();
//        Intent intent = new Intent(context, LocationService.class);
//        context.unbindService(mLocationServiceConnection);
//        context.stopService(intent);
//        ((MapsActivity) getActivity()).stopTimer();
//    }


    public void updateDuration(long difference) {
        long second = (difference / 1000) % 60;
        long minute = (difference / (1000 * 60)) % 60;
        long hour = (difference / (1000 * 60 * 60));

        String time = String.format("%d:%02d:%02d", hour, minute, second);
        mTVDuration.setText(time);
    }

    //----------------------------------------------------------------------------------------------
    public void updateInfo(Route route, Area area) {
        String areaName = "unknown";
        String points = "0";
        String distance = "0.00";

        if (route != null) {
            points = Integer.toString((int) route.getDistance() / 10);
            distance = String.format("%.2f", route.getDistance());
        }

        if (area != null) {
            areaName = area.getName();
        }

        mTVArea.setText(areaName);
        mTVPoints.setText(points);
        mTVDistance.setText(distance + "km");
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

