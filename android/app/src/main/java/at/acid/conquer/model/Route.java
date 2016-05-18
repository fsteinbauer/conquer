package at.acid.conquer.model;

import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import static at.acid.conquer.Utility.getLatLng;

/**
 * Created by florian on 09.03.2016.
 * Killed and revived by menzi on 24.04.2016
 */
public class Route {
    public static final String TAG = "Route";

    private PolylineOptions mPolylineOptions;
    private GoogleMap mMap;
    private float mDistance;

    private List<Polyline> mPolylines = new ArrayList<Polyline>();
    private List<List<Location>> mPaths = new ArrayList<List<Location>>();

    //----------------------------------------------------------------------------------------------
    public Route(@NonNull GoogleMap map, @NonNull PolylineOptions options) {
        mMap = map;
        mPolylineOptions = options;
    }

    //----------------------------------------------------------------------------------------------
    public float addLocationToCurrentPath(@NonNull Location loc) {
        Location oldLoc = getLastLocation();

        if (oldLoc == null) {
            Log.d(TAG, "addLocationToCurrentPath(): no current path! (call addLocationToNewPath first)");
            return 0;
        }

        List<Location> path = mPaths.get(mPaths.size() - 1);
        path.add(loc);

        Polyline poly = mPolylines.get(mPolylines.size() - 1);
        List<LatLng> points = poly.getPoints();
        points.add(getLatLng(loc));
        poly.setPoints(points);

        float distance = loc.distanceTo(oldLoc);
        mDistance += distance;
        return distance;
    }

    //----------------------------------------------------------------------------------------------
    public float addLocationToNewPath(@NonNull Location loc1, @NonNull Location loc2) {
        List<Location> newPath = new ArrayList<Location>();
        newPath.add(loc1);
        newPath.add(loc2);
        mPaths.add(newPath);

        Polyline poly = mMap.addPolyline(mPolylineOptions);
        List<LatLng> points = poly.getPoints();
        points.add(getLatLng(loc1));
        points.add(getLatLng(loc2));
        poly.setPoints(points);
        mPolylines.add(poly);

        float distance = loc1.distanceTo(loc2);
        mDistance += distance;
        return distance;
    }

    //----------------------------------------------------------------------------------------------
    public Location getLastLocation() {
        if (mPaths.isEmpty())
            return null;

        List<Location> path = mPaths.get(mPaths.size() - 1);
        if (path.isEmpty())
            return null;

        return path.get(path.size() - 1);
    }

    //----------------------------------------------------------------------------------------------
    public List<Location> getCurrentPath() {
        if (mPaths.isEmpty())
            return null;
        return mPaths.get(mPaths.size() - 1);
    }

    //----------------------------------------------------------------------------------------------
    public float getDistance(){
        return mDistance;
    }
}
