package at.acid.conquer.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 09.03.2016.
 * Killed and revived by menzi on 25.04.2016
 */
public class Area {
    public static final String TAG = "Area";
    private String mName;
    private int mId;

    private float mTravelDistance;

    private LatLng mBBoxMin;
    private LatLng mBBoxMax;

    private List<LatLng> mPolygon = new ArrayList<LatLng>();

    //----------------------------------------------------------------------------------------------
    public Area() {
    }

    //----------------------------------------------------------------------------------------------
    public Area(@NonNull String name, int id, LatLng bBoxMin, LatLng bBoxMax, List<LatLng> polygon)
    {
        mName = name;
        mId = id;
        mBBoxMin = bBoxMin;
        mBBoxMax = bBoxMax;
        mPolygon = polygon;
    }

    //----------------------------------------------------------------------------------------------
    public boolean loadJson(@NonNull String json) {
        try {
            JSONObject obj = new JSONObject(json);
            mName = obj.getString("name");
            mId = obj.getInt("id");
            JSONArray points = obj.getJSONArray("data");
            for( int i = 0; i < points.length(); i++ ) {
                JSONObject point = points.getJSONObject(i);
                addPoint( new LatLng(point.getDouble("lat"), point.getDouble("lon")) );
            }
        }
        catch( JSONException e ){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //----------------------------------------------------------------------------------------------
    public void addPoint(LatLng point) {
        mPolygon.add(point);

        if (mBBoxMin == null || mBBoxMax == null) {
            mBBoxMin = point;
            mBBoxMax = point;
            return;
        }

        if (point.latitude < mBBoxMin.latitude)
            mBBoxMin = new LatLng(point.latitude, mBBoxMin.longitude);
        if (point.longitude < mBBoxMin.longitude)
            mBBoxMin = new LatLng(mBBoxMin.latitude, point.longitude);
        if (point.latitude > mBBoxMax.latitude)
            mBBoxMax = new LatLng(point.latitude, mBBoxMax.longitude);
        if (point.longitude > mBBoxMax.longitude)
            mBBoxMax = new LatLng(mBBoxMax.latitude, point.longitude);
    }

    //----------------------------------------------------------------------------------------------
    public void draw(@NonNull GoogleMap map, int color, int colorBorder) {
        PolygonOptions options = new PolygonOptions()
                .strokeColor(colorBorder)
                .fillColor(color);

        Log.d(TAG, "Points: " + mPolygon);
        options.addAll(mPolygon);
        map.addPolygon(options);
    }

    //----------------------------------------------------------------------------------------------
    public boolean inArea(LatLng point) {
        double x = point.latitude;
        double y = point.longitude;

        // check for bounding box
        if (x < mBBoxMin.latitude || x > mBBoxMax.latitude ||
                y < mBBoxMin.longitude || y > mBBoxMax.longitude)
            return false;

        int size = mPolygon.size();
        LatLng a, b;
        int intersections = 0;
        double diffy, t, sx;
        for( int i = 0; i < size; i++ ){
            a = mPolygon.get(i);
            b = mPolygon.get((i+1) % size);

            // if point equals an edge point of polygon, return true
            if((a.latitude == x && a.longitude == y) || (b.latitude == x && b.longitude == y))
                return true;

            // if the segment does not cross the scan line, ignore it
            if((a.longitude < y && b.longitude < y) || (a.longitude > y && b.longitude > y) ||
                    (a.latitude > x && b.latitude > x))
                continue;

            diffy = b.longitude - a.longitude;

            // line parallel to scanline
            if( diffy == 0.0 ){
                if( (a.latitude < x && b.latitude > x) || (a.latitude > x && b.latitude < x) )
                    return true;
                continue;
            }

            t = (y - a.longitude) / diffy;
            sx = a.latitude + t * (b.latitude - a.latitude);

            // point is exactly on segment
            if( sx == x )
                return true;

            // count intersections (only from one side of point)
            if( sx < x )
                intersections++;
        }

        // even number of intersections means the point is outside the polygon
        if( intersections % 2 == 0 )
            return false;

        return true;
    }

    //----------------------------------------------------------------------------------------------
    public void addDistance(float distance){
        mTravelDistance += distance;
    }

    //----------------------------------------------------------------------------------------------
    public String getName(){
        return mName;
    }
    public int getId() { return mId; }
    public float getTravelDistance(){
        return mTravelDistance;
    }


}
