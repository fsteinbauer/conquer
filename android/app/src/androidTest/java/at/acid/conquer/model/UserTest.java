package at.acid.conquer.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import at.acid.conquer.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Trey
 * Created on 20.06.2016
 */
@RunWith(AndroidJUnit4.class)
public class UserTest extends InstrumentationTestCase{

    public static final String STORE_NAME = "LocalStore";
    private User user;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class){
        @Override
        protected void beforeActivityLaunched(){
            user = null;
            clearSharedPrefs(InstrumentationRegistry.getTargetContext());
            super.beforeActivityLaunched();
        }
    };

    private void clearSharedPrefs(Context context){
        SharedPreferences prefs = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    @Test
    public void createUser(){
        user = new User(InstrumentationRegistry.getTargetContext());
        assertEquals("Username: ", "John Doe", user.getName());
        assertEquals("ID: ", "", user.getId());
        assertNotNull(user.getRoutes());
        assertNotNull(user.getAreas());
        assertTrue(user.getRoutes().size() == 0);
        assertTrue(user.getAreas().size() == 0);
    }

    @Test
    public void addRoute(){
//        long date, long runningTime, double distance, long points;
        createUser();
        long currenttime = System.currentTimeMillis();
        long runningtime = 120 * 1000;
        double distance = 123456;
        long points = 321;

        user.addRoute(currenttime, runningtime, distance, points);
        assertTrue(user.getRoutes().size() == 1);
        assertEquals(user.getRoutes().get(0).mRunningTime, runningtime);
        assertEquals(user.getRoutes().get(0).mDistance, distance);
        assertEquals(user.getRoutes().get(0).mPoints, points);
        assertEquals(user.getRoutes().get(0).mDate, currenttime);
    }

    @Test
    public void updateArea(){
        createUser();
        int areaID = 1;
        double distance = 123456;
        long points = 321;

        user.updateArea(areaID, distance, points);

        assertTrue(user.getAreas().size() == 1);
        assertEquals(user.getAreas().get(1).mId, areaID);
        assertEquals(user.getAreas().get(1).mDistance, distance);
        assertEquals(user.getAreas().get(1).mPoints, points);

        user.updateArea(areaID, distance, points);
        assertTrue(user.getAreas().size() == 1);
        assertEquals(user.getAreas().get(1).mId, areaID);
        assertEquals(user.getAreas().get(1).mDistance, distance * 2);
        assertEquals(user.getAreas().get(1).mPoints, points * 2);

        user.updateArea(2, distance, points);
        assertTrue(user.getAreas().size() == 2);
        assertEquals(user.getAreas().get(2).mId, 2);
        assertEquals(user.getAreas().get(2).mDistance, distance);
        assertEquals(user.getAreas().get(2).mPoints, points);
    }

    @Test
    public void saveData(){
        createUser();
        user.setId("newID");
        user.setName("newName");

        long currenttime = System.currentTimeMillis();
        long runningtime = 120 * 1000;
        double distance = 123456;
        long points = 321;
        user.addRoute(currenttime, runningtime, distance, points);

        int areaID = 1;
        double distanceArea = 456123;
        long pointsArea = 123;
        user.updateArea(areaID, distanceArea, pointsArea);

        user.saveData();
        user = new User(InstrumentationRegistry.getTargetContext());

        assertEquals("Username: ", "newName", user.getName());
        assertEquals("ID: ", "newID", user.getId());
        assertNotNull(user.getRoutes());
        assertNotNull(user.getAreas());
        assertTrue(user.getRoutes().size() == 1);
        assertEquals(user.getRoutes().get(0).mRunningTime, runningtime);
        assertEquals(user.getRoutes().get(0).mDistance, distance);
        assertEquals(user.getRoutes().get(0).mPoints, points);
        assertEquals(user.getRoutes().get(0).mDate, currenttime);

        assertTrue(user.getAreas().size() == 1);
        assertEquals(user.getAreas().get(1).mId, areaID);
        assertEquals(user.getAreas().get(1).mDistance, distanceArea);
        assertEquals(user.getAreas().get(1).mPoints, pointsArea);
    }

    @Test
    public void clearStoredData(){
        createUser();
        user.setName("newName");
        user.setId("newID");
        user.saveData();

        user = new User(InstrumentationRegistry.getTargetContext());
        assertEquals("Username: ", "newName", user.getName());
        assertEquals("ID: ", "newID", user.getId());

        user.clearStoredData();
        user = new User(InstrumentationRegistry.getTargetContext());
        assertEquals("Username: ", "John Doe", user.getName());
        assertEquals("ID: ", "", user.getId());
    }


}
