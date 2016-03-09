package at.acid.conquer;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import at.acid.conquer.model.Area;
import at.acid.conquer.model.Route;

/**
 * Created by florian on 09.03.2016.
 */
public abstract class LocationUtility {

    protected Route currentRoute;

    protected LocationUtility() {
        this.currentRoute = new Route();
    }

    public void handleLocationUpdate(Location location){
        // handle application logic

        this.currentRoute.addPoint(new LatLng(location.getLatitude(), location.getLongitude()));
        changeRoute(currentRoute);
    }

    protected abstract void changeRoute(Route route);

   // protected abstract void addArea(Area area);
}
