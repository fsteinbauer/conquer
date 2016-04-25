package at.acid.conquer.model;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

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
    private LatLng mCenter;

    private List<LatLng> mPolygon = new ArrayList<LatLng>();

    public Area(@NonNull String name) {
        mName = name;
    }

    public void setPoints(List<LatLng> polygon) {
        mPolygon = polygon;
    }

    public void addPoint(LatLng point) {
        mPolygon.add(point);

        if( mBBoxMin == null || mBBoxMax == null ) {
            mBBoxMin = point;
            mBBoxMax = point;
        }
        else if( point.latitude < mBBoxMin.latitude )
            mBBoxMin = new LatLng(point.latitude, mBBoxMin.longitude);
        else if( point.longitude < mBBoxMin.longitude )
            mBBoxMin = new LatLng(mBBoxMin.latitude, point.longitude);
        else if( point.latitude > mBBoxMax.latitude )
            mBBoxMin = new LatLng(point.latitude, mBBoxMax.longitude);
        else if( point.longitude > mBBoxMax.longitude )
            mBBoxMin = new LatLng(mBBoxMax.latitude, point.longitude);
    }

    public void draw(@NonNull GoogleMap map, @NonNull Color color) {

    }
}
