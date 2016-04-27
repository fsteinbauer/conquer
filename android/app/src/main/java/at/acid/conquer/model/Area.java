package at.acid.conquer.model;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
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

    private LatLng mBBoxMin;
    private LatLng mBBoxMax;

    private List<LatLng> mPolygon = new ArrayList<LatLng>();

    //----------------------------------------------------------------------------------------------
    public Area(@NonNull String name) {
        mName = name;
    }

    //----------------------------------------------------------------------------------------------
    public Area(@NonNull String name, LatLng bBoxMin, LatLng bBoxMax, List<LatLng> polygon)
    {
        mName = name;
        mBBoxMin = bBoxMin;
        mBBoxMax = bBoxMax;
        mPolygon = polygon;
    }

    //----------------------------------------------------------------------------------------------
    public boolean loadJson(@NonNull String json) {
        try {

            JSONObject obj = new JSONObject(json);
            String name = obj.getString("name");
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
        } else if (point.latitude < mBBoxMin.latitude)
            mBBoxMin = new LatLng(point.latitude, mBBoxMin.longitude);
        else if (point.longitude < mBBoxMin.longitude)
            mBBoxMin = new LatLng(mBBoxMin.latitude, point.longitude);
        else if (point.latitude > mBBoxMax.latitude)
            mBBoxMin = new LatLng(point.latitude, mBBoxMax.longitude);
        else if (point.longitude > mBBoxMax.longitude)
            mBBoxMin = new LatLng(mBBoxMax.latitude, point.longitude);
    }

    //----------------------------------------------------------------------------------------------
    public void draw(@NonNull GoogleMap map, int color) {

        int alphaBitmask = 230 << 3*8;
        int transparentColor = color ^ alphaBitmask;


        int alphaBitmaskBorder = 128 << 3*8;
        int transparentColorBorder = color ^ alphaBitmaskBorder;
        PolygonOptions options = new PolygonOptions()
                .strokeColor(transparentColorBorder)
                .fillColor(transparentColor);


        Log.d(TAG, "Points: " + mPolygon);
        options.addAll(mPolygon);
        map.addPolygon(options);

    }

    //----------------------------------------------------------------------------------------------
    public boolean inArea(LatLng point) {
        double x = point.latitude;
        double y = point.longitude;

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

            if((a.latitude == x && a.longitude == y) || (b.latitude == x && b.longitude == y))
                return true;

            if((a.longitude < y && b.longitude < y) || (a.longitude > y && b.longitude > y) ||
                    (a.latitude > x && b.latitude > x))
                continue;

            diffy = b.longitude - a.longitude;
            if( diffy == 0.0 ){
                if( (a.latitude < x && b.latitude > x) || (a.latitude > x && b.latitude < x) )
                    return true;
                continue;
            }

            t = (y - a.longitude) / diffy;
            sx = a.latitude + t * (b.latitude - a.latitude);

            if( sx == x )
                return true;

            if( sx < x )
                intersections++;
        }

        if( intersections % 2 == 0 )
            return false;

        return true;
    }

    //----------------------------------------------------------------------------------------------
    public String getName(){
        return mName;
    }
}
