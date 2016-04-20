package at.acid.conquer;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by florian on 09.03.2016.
 */
public class MockLocationProvider {
    String providerName;
    Context ctx;

    public MockLocationProvider(String name, Context ctx) {
        this.providerName = name;
        this.ctx = ctx;

        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);
        lm.addTestProvider(providerName, false, false, false, false, false,
                true, true, 0, 5);
        lm.setTestProviderEnabled(providerName, true);
    }

    public void pushLocation(LatLng latlng) {
        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);

        Location mockLocation = new Location(providerName);
        mockLocation.setLatitude(latlng.latitude);
        mockLocation.setLongitude(latlng.longitude);
        mockLocation.setAccuracy(20);
        mockLocation.setElapsedRealtimeNanos(1457537901000L);
        mockLocation.setAltitude(0);
        mockLocation.setTime(System.currentTimeMillis());
        Log.d("MockLocation", mockLocation.toString());
        lm.setTestProviderLocation(providerName, mockLocation);
    }

    public void shutdown() {
        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);
        lm.removeTestProvider(providerName);
    }
}