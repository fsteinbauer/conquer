package at.acid.conquer.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 09.03.2016.
 */
abstract public class Shape {

    List<LatLng> points;

    public Shape() {
        this.points = new ArrayList();
    }

    public void addPoint(LatLng point){
        this.points.add(point);
    }

    public List<LatLng> getPoints(){
        return points;
    }
}
