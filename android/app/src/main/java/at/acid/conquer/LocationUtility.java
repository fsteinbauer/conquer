package at.acid.conquer;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import at.acid.conquer.model.Area;
import at.acid.conquer.model.Route;

/**
 * Created by florian on 09.03.2016.
 */
public abstract class LocationUtility {

    public Route currentRoute;

    protected LocationUtility() {
        this.currentRoute = new Route();
    }

    public void handleLocationUpdate(Location location){
        // handle application logic


        this.currentRoute.addPoint(new LatLng(location.getLatitude(), location.getLongitude()));

        if(isCircle(this.currentRoute)){
            Area area = new Area(this.currentRoute);
            this.currentRoute.deletePoints();
            addArea(area);
        } else {
            changeRoute(currentRoute);
        }
    }

    private boolean isCircle(Route route){

        int size = route.getPoints().size();
        return size > 1 && route.getPoints().get(0).latitude == route.getPoints().get(size-1).latitude &&
                route.getPoints().get(0).longitude == route.getPoints().get(size-1).longitude;
    }

    protected abstract void changeRoute(Route route);

    protected abstract void addArea(Area area);
}
