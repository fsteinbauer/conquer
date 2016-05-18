/*package at.acid.conquer;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.runner.RunWith;

/**
 * Created by menzi on 09.05.2016.
 *
@RunWith(AndroidJUnit4.class)
@SmallTest
public class LocationServiceTest extends AndroidTestCase{
}
*/

package at.acid.conquer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ServiceTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by menzi on 09.05.2016.
 */
@RunWith(AndroidJUnit4.class)
public class LocationServiceTest implements LocationService.LocationServiceClient{
    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();

    protected List<Location> mLocations = new ArrayList<Location>();
    private LocationService mLocationService;

    private ServiceConnection mLocationServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mLocationService = ((LocationService.LocalBinder) service).getService();
            mLocationService.setServiceClient(LocationServiceTest.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mLocationService.setServiceClient(null);
            mLocationService = null;
        }
    };

    @Override//-------------------------------------------------------------------------------------
    public void onLocationUpdate(Location location) {

        mLocations.add(location);
        Log.d("LocationService", "onLocationUpdate() : " + mLocations.size());
    }

    @Before//---------------------------------------------------------------------------------------
    public void startLocationService() throws Exception {
        mLocations.clear();

        Intent intent = new Intent(InstrumentationRegistry.getTargetContext(), LocationService.class);
        mServiceRule.startService(intent);
        mServiceRule.bindService(intent, mLocationServiceConnection, Context.BIND_AUTO_CREATE);

        for(int i = 0; i < 10 && (mLocationService == null || !mLocationService.isConnected()); i++)
            Thread.sleep(100);
    }

    @After//----------------------------------------------------------------------------------------
    public void stopLocationService(){
        mLocationService.onDestroy();
    }

    @Test//-----------------------------------------------------------------------------------------
    public void simpleGPSLocation() throws Exception {
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(10);
        location.setLongitude(10);
        long time = System.currentTimeMillis();

        mLocationService.pushMockLocation(location.getLatitude(), location.getLongitude(), time);
        for(int i = 0; i < 10 && mLocations.size() < 1; i++)
            Thread.sleep(100);

        assertEquals(location.getLatitude(), mLocationService.getLocation().getLatitude());
        assertEquals(location.getLongitude(), mLocationService.getLocation().getLongitude());
        assertEquals(1, mLocations.size());
    }

    @Test//-----------------------------------------------------------------------------------------
    public void stopAndRestartService() throws Exception {
        mLocationService.pushMockLocation(0, 0, System.currentTimeMillis());
        for(int i = 0; i < 10 && mLocations.size() < 1; i++)
            Thread.sleep(100);

        assertEquals(1, mLocations.size());

        stopLocationService();
        startLocationService();

        mLocationService.pushMockLocation(0, 0, System.currentTimeMillis());
        for(int i = 0; i < 10 && mLocations.size() < 1; i++)
            Thread.sleep(100);

        assertEquals(1, mLocations.size());
    }
}
