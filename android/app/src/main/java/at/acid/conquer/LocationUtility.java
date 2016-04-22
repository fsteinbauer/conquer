package at.acid.conquer;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

import at.acid.conquer.model.Area;
import at.acid.conquer.model.Route;

/**
 * Created by florian on 09.03.2016.
 */
public abstract class LocationUtility {


    public final String TAG = "LocationUtility";

    Map<Long, Location> locations = new HashMap<>();

    public boolean addLocation(Long time, Location location)
    {
        if(time == null || location == null)
        {
            return false;
        }
        if(locations.isEmpty())
        {
            locations.put(time, location);
            return true;
        }
        if(!validateLocation(time, location))
        {
            return false;
        }

        locations.put(time, location);

        Log.d(TAG, "Added New Location: " + location +" at time:" + time);
        return true;
    }


    public boolean wurst(Long timeOfLocation, Location network, Location gps) {
        if (network == null && gps == null) {
            return false;
        }

        boolean network_valid = validateLocation(timeOfLocation, network);
        boolean gps_valid = validateLocation(timeOfLocation, gps);

        if (!network_valid && !gps_valid) {

            return false;
        } else if (!network_valid && gps_valid) {

            locations.put(timeOfLocation, gps);
            return true;
        } else if (network_valid && !gps_valid) {

            locations.put(timeOfLocation, network);
            return true;
        }

        // locations.put(timeOfLocation, findBetterMatch(network, gps));
        return true;

    }

    private boolean validateLocation(long currentTime, Location location) {
        if (location == null) {
            return false;
        }


        for (Map.Entry<Long, Location> old_location :
                locations.entrySet()) {
            if ((currentTime - old_location.getKey()) > 60000)

            {
                continue;
            }


            float distInMeters = location.distanceTo(old_location.getValue());

            double meterPerSecond = distInMeters / ((currentTime - old_location.getKey()) / 1000);

            double kmh = meterPerSecond * 3.6;


            Log.d(TAG, "KMH = " + kmh);

            if (kmh > 20.0) {
                Log.d(TAG,"rejecting position update, since distance is too high for timespan!");
                return false;
            }
        }
        return true;


    }

    public Route currentRoute;

    protected LocationUtility() {
        this.currentRoute = new Route();
    }

    public void handleLocationUpdate(Location location) {
        // handle application logic


        this.currentRoute.addPoint(new LatLng(location.getLatitude(), location.getLongitude()));

        if (isCircle(this.currentRoute)) {
            Area area = new Area(this.currentRoute);
            this.currentRoute.deletePoints();
            addArea(area);
        } else {
            changeRoute(currentRoute);
        }
    }

    private boolean isCircle(Route route) {

        int size = route.getPoints().size();
        return size > 1 && route.getPoints().get(0).latitude == route.getPoints().get(size - 1).latitude &&
                route.getPoints().get(0).longitude == route.getPoints().get(size - 1).longitude;
    }

    protected abstract void changeRoute(Route route);

    protected abstract void addArea(Area area);
}
