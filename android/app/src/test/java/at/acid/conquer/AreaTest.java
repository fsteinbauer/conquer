package at.acid.conquer;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import at.acid.conquer.model.Area;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class AreaTest {

    @Test
    public void createWithName() throws Exception {
        Area area = new Area("Graz");
        assertEquals("Graz", area.getName());
    }

    @Test
    public void createWithAll() throws Exception {
        List<LatLng> polygon = new ArrayList<LatLng>();
        polygon.add(new LatLng(10, 10));
        polygon.add(new LatLng(10,-10));
        polygon.add(new LatLng(-5,-5));
        polygon.add(new LatLng(-10,10));
        LatLng bBoxMin = new LatLng(-10, -10);
        LatLng bBoxMax = new LatLng(10, 10);

        Area area = new Area("Graz", bBoxMin, bBoxMax, polygon);

        assertEquals("Graz", area.getName());
    }

    @Test
    public void pointInConvexArea() throws Exception{
        List<LatLng> polygon = new ArrayList<LatLng>();
        polygon.add(new LatLng(10, 10));
        polygon.add(new LatLng(10,-10));
        polygon.add(new LatLng(0,0));
        polygon.add(new LatLng(-8,10));
        LatLng bBoxMin = new LatLng(-10, -10);
        LatLng bBoxMax = new LatLng(10, 10);

        Area area = new Area("Graz", bBoxMin, bBoxMax, polygon);

        assertEquals("Point outside 1", false, area.inArea(new LatLng(10, 11)));
        assertEquals("Point outside 2", false, area.inArea(new LatLng(11, 10)));
        assertEquals("Point outside 3", false, area.inArea(new LatLng(-9, 10)));
        assertEquals("Point inside", true, area.inArea(new LatLng(3,3)));
        assertEquals("Point on corner 1", true, area.inArea(new LatLng(-8, 10)));
        assertEquals("Point on corner 2", true, area.inArea(new LatLng(10, 10)));
        assertEquals("Point on horizontal line 1", true, area.inArea(new LatLng(2, 10)));
        assertEquals("Point on horizontal line 2", false, area.inArea(new LatLng(2, 10.0001)));
        assertEquals("Point on horizontal line 3", true, area.inArea(new LatLng(2, 9.9999)));
        assertEquals("Point on vertical line 1", true, area.inArea(new LatLng(10, 3)));
        assertEquals("Point on vertical line 2", false, area.inArea(new LatLng(10.0001, 3)));
        assertEquals("Point on vertical line 3", true, area.inArea(new LatLng(9.9999, 3)));
        assertEquals("Point on diagonal line 1", true, area.inArea(new LatLng(3, -3)));
        assertEquals("Point on diagonal line 2", false, area.inArea(new LatLng(3, -3.0001)));
        assertEquals("Point on diagonal line 3", true, area.inArea(new LatLng(3, -2.9999)));
        assertEquals("Point on diagonal line 4", true, area.inArea(new LatLng(3.0001, -3)));
        assertEquals("Point on diagonal line 5", false, area.inArea(new LatLng(2.9999, -3)));
        assertEquals("Point on diagonal line 6", true, area.inArea(new LatLng(-4, 5)));
        assertEquals("Point on diagonal line 7", true, area.inArea(new LatLng(-4, 5.0001)));
        assertEquals("Point on diagonal line 8", false, area.inArea(new LatLng(-4, 4.9999)));
        assertEquals("Point on diagonal line 9", false, area.inArea(new LatLng(-4.0001, 5)));
        assertEquals("Point on diagonal line 10", true, area.inArea(new LatLng(-3.9999, 5)));
    }
}