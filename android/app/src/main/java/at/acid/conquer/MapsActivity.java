package at.acid.conquer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import at.acid.conquer.adapter.NavDrawerListAdapter;
import at.acid.conquer.animation.TrackingInfoAnimator;
import at.acid.conquer.data.Areas;
import at.acid.conquer.model.Area;
import at.acid.conquer.model.NavDrawerItem;
import at.acid.conquer.model.Route;
import at.acid.conquer.model.TimeLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static at.acid.conquer.LocationUtility.getSpeed;
import static at.acid.conquer.LocationUtility.validDistance;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationService.LocationServiceClient, View.OnClickListener, View.OnTouchListener{
    public static final String TAG = "MapsActivity";
    public static final float DEFAULT_ZOOM = 16.0f;

    private PolylineOptions mPathLineOptions = new PolylineOptions().width(10.0f).color(Color.RED);

    private GoogleMap mMap;
    private Route mRoute;
    private List<Area> mAreas = new ArrayList<Area>();
    private TimeLocation mLastLocation;
    private LocationService mLocationService;

    private FloatingActionButton mFABTrackingInfo;
    private LinearLayout mLayoutTrackingInfoLong;
    private LinearLayout mLayoutTrackingInfoShort;
    private TextView mTextViewInvisble;

    private TrackingInfoAnimator mTIAnimator;
    private boolean mIsLayoutExtended;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private ActionBarDrawerToggle mDrawerToggle;

    // handle bidirection connection to LocationService
    private ServiceConnection mLocationServiceConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName className, IBinder service){
            mLocationService = ((LocationService.LocalBinder) service).getService();
            mLocationService.setServiceClient(MapsActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0){
            mLocationService.setServiceClient(null);
            mLocationService = null;
        }
    };

    @Override//-------------------------------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        navDrawerItems.add(new NavDrawerItem("Map", R.drawable.ic_map_white_24dp));
        navDrawerItems.add(new NavDrawerItem("Highscores", R.drawable.ic_star_circle_white_24dp));
        navDrawerItems.add(new NavDrawerItem("Account", R.drawable.ic_account_white_24dp));


        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

//        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//                R.drawable.common_signin_btn_icon_dark, //nav menu toggle icon
//                R.string.app_name, // nav drawer open - description for accessibility
//                R.string.app_name // nav drawer close - description for accessibility
//        ){
//            public void onDrawerClosed(View view) {
//                getActionBar().setTitle(mTitle);
//                // calling onPrepareOptionsMenu() to show action bar icons
//                invalidateOptionsMenu();
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                getActionBar().setTitle(mDrawerTitle);
//                // calling onPrepareOptionsMenu() to hide action bar icons
//                invalidateOptionsMenu();
//            }
//        };
//        mDrawerLayout.setDrawerListener(mDrawerToggle);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_map);
        mapFragment.getMapAsync(this);

        // Connect GUI elements
        initGUIElements();
        mTIAnimator = new TrackingInfoAnimator(getApplicationContext(), mFABTrackingInfo, mLayoutTrackingInfoShort, mLayoutTrackingInfoLong, mTextViewInvisble);

        updateInfo(0, 0);
        updateArea("Graz");

    }

    private void initGUIElements(){
        mLayoutTrackingInfoShort = (LinearLayout) findViewById(R.id.ll_trackinginfo_short);
        mLayoutTrackingInfoLong = (LinearLayout) findViewById(R.id.ll_trackinginfo_long);

        LinearLayout mLayoutFrame = (LinearLayout) findViewById(R.id.ll_display_frame);
        mLayoutFrame.setOnTouchListener(this);

        mFABTrackingInfo = (FloatingActionButton) findViewById(R.id.fab_trackinginfo);
        mFABTrackingInfo.setOnClickListener(this);

        mTextViewInvisble = (TextView) findViewById(R.id.tv_invisible);
    }


    @Override//-------------------------------------------------------------------------------------
    public void onMapReady(GoogleMap googleMap){
        Log.d(TAG, "onMapReady()");
        mMap = googleMap;
        mRoute = new Route(mMap, mPathLineOptions);

        // default locatin: graz
        Location location = new Location("Wikipedia");
        location.setLatitude(47.067);
        location.setLongitude(15.433);

        for(String json : Areas.mAreas){
            Area area = new Area("");
            area.loadJson(json);
            mAreas.add(area);
            area.draw(mMap, Color.BLUE);
        }


        if(mLastLocation != null){ location = mLocationService.getLocation(); }

        mLastLocation = new TimeLocation(location);
        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, DEFAULT_ZOOM));
    }

    @Override//-------------------------------------------------------------------------------------
    public void onLocationUpdate(final Location location){
        if(location == null){
            Log.d(TAG, "onLocationUpdate(): unknown position");
            return;
        }

        if(mMap == null){
            Log.d(TAG, "onLocationUpdate(): wait for map");

            Timer timer = new Timer();
            timer.schedule(new TimerTask(){
                public void run(){
                    onLocationUpdate(location);
                }
            }, 1000);
            return;
        }


        Log.d(TAG, "onLocationUpdate(): " + location.toString());

        TimeLocation tloc = new TimeLocation(location);

        // distance to last valid point is valid - add point
        if(validDistance(mRoute.getLastLocation(), tloc)){
            mRoute.addLocationToCurrentPath(tloc);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(tloc.getLatLng()));
        }
        // distance to last point is valid - create new route
        else if(validDistance(mLastLocation, tloc)){
            mRoute.addLocationToNewPath(mLastLocation, tloc);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(tloc.getLatLng()));
        }

        mLastLocation = tloc;

        List<TimeLocation> path = mRoute.getCurrentPath();
        if(path != null && path.size() > 1){
            TimeLocation tloc1 = path.get(path.size() - 1);
            TimeLocation tloc2 = path.get(path.size() - 2);
            float kmh = getSpeed(tloc1, tloc2);
            float distance = mRoute.getDistance() / 1000; // distance in km
            updateInfo(kmh, distance);
        }
    }

    private void startTracking(){
        Log.d(TAG, "startTracking()");

        Intent intent = new Intent(this, LocationService.class);
        bindService(intent, mLocationServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void stopTracking(){
        Log.d(TAG, "stopTracking()");
        unbindService(mLocationServiceConnection);
    }

    @Override//-------------------------------------------------------------------------------------
    public void onClick(View v){
        switch(v.getId()){
            case R.id.fab_trackinginfo:

                if(mIsLayoutExtended){
                    mIsLayoutExtended = false;
                    mTIAnimator.shrinkTrackingInfo();
                } else{

                    mIsLayoutExtended = true;
                    mTIAnimator.extendTrackingInfo();
                }
                break;
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event){

        if(mLayoutTrackingInfoLong.getVisibility() == View.VISIBLE){
            Rect infoRect = new Rect();
            mLayoutTrackingInfoLong.getHitRect(infoRect);

            if(!infoRect.contains((int) event.getX(), (int) event.getY())){
                mIsLayoutExtended = false;
                mTIAnimator.shrinkTrackingInfo();
                return true;
            }
        }

        return false;
    }


    //----------------------------------------------------------------------------------------------
    private void updateInfo(float kmh, float distance){
        String text = "Speed: " + String.format("%.3f", kmh) + " km/h";
        text += "\nDistance: " + String.format("%.3f", distance) + " km";
//        mTextInfo.setText(text);
    }

    //----------------------------------------------------------------------------------------------
    private void updateArea(CharSequence area){
//        mTextArea.setText(area);
    }


}
