package at.acid.conquer.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 09.03.2016.
 * Killed and revived by menzi on 24.04.2016
 */
public class Route {
    public static final String TAG = "Route";

    private PolylineOptions mPolylineOptions;
    private GoogleMap mMap;

    private List<Polyline> mPolylines = new ArrayList<Polyline>();
    private List<List<TimeLocation>> mPaths = new ArrayList<List<TimeLocation>>();

    //----------------------------------------------------------------------------------------------
    public Route(@NonNull GoogleMap map, @NonNull PolylineOptions options) {
        mMap = map;
        mPolylineOptions = options;
    }

    //----------------------------------------------------------------------------------------------
    public void addLocationToCurrentPath(@NonNull TimeLocation tloc) {
        TimeLocation oldTloc = getLastLocation();

        if (oldTloc == null) {
            Log.d(TAG, "addLocationToCurrentPath(): no current path! (call addLocationToNewPath first)");
            return;
        }

        List<TimeLocation> path = mPaths.get(mPaths.size() - 1);
        path.add(tloc);

        Polyline poly = mPolylines.get(mPolylines.size() - 1);
        List<LatLng> points = poly.getPoints();
        points.add(tloc.getLatLng());
        poly.setPoints(points);
    }

    //----------------------------------------------------------------------------------------------
    public void addLocationToNewPath(@NonNull TimeLocation tloc1, @NonNull TimeLocation tloc2) {
        List<TimeLocation> newPath = new ArrayList<TimeLocation>();
        newPath.add(tloc1);
        newPath.add(tloc2);
        mPaths.add(newPath);

        Polyline poly = mMap.addPolyline(mPolylineOptions);
        List<LatLng> points = poly.getPoints();
        points.add(tloc1.getLatLng());
        points.add(tloc2.getLatLng());
        poly.setPoints(points);
        mPolylines.add(poly);
    }

    //----------------------------------------------------------------------------------------------
    public TimeLocation getLastLocation() {
        if (mPaths.isEmpty())
            return null;

        List<TimeLocation> path = mPaths.get(mPaths.size() - 1);
        if (path.isEmpty())
            return null;

        return path.get(path.size() - 1);
    }
}
