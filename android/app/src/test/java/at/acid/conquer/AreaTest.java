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
    @Test//-----------------------------------------------------------------------------------------
    public void createArea() throws Exception {
        List<LatLng> polygon = new ArrayList<LatLng>();
        polygon.add(new LatLng(10, 10));
        polygon.add(new LatLng(10,-10));
        polygon.add(new LatLng(-5,-5));
        polygon.add(new LatLng(-10,10));
        LatLng bBoxMin = new LatLng(-10, -10);
        LatLng bBoxMax = new LatLng(10, 10);

        Area area = new Area("Graz", 0, bBoxMin, bBoxMax, polygon);

        assertEquals("Name", "Graz", area.getName());
    }

    @Test//-----------------------------------------------------------------------------------------
    public void pointInConvexArea() throws Exception{
        List<LatLng> polygon = new ArrayList<LatLng>();
        polygon.add(new LatLng(10, 10));
        polygon.add(new LatLng(10,-10));
        polygon.add(new LatLng(0,0));
        polygon.add(new LatLng(-8,10));
        LatLng bBoxMin = new LatLng(-10, -10);
        LatLng bBoxMax = new LatLng(10, 10);

        Area area = new Area("Graz", 0, bBoxMin, bBoxMax, polygon);

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

    @Test//-----------------------------------------------------------------------------------------
    public void pointInConcaveArea(){
        List<LatLng> polygon = new ArrayList<LatLng>();
        polygon.add(new LatLng(0, 10));
        polygon.add(new LatLng(10,10));
        polygon.add(new LatLng(10,-10));
        polygon.add(new LatLng(-10,-10));
        polygon.add(new LatLng(-10, 10));
        polygon.add(new LatLng(0, 10));
        polygon.add(new LatLng(0, 5));
        polygon.add(new LatLng(-5, 5));
        polygon.add(new LatLng(-5, -5));
        polygon.add(new LatLng(5, -5));
        polygon.add(new LatLng(5, 5));
        polygon.add(new LatLng(0, 5));
        LatLng bBoxMin = new LatLng(-10, -10);
        LatLng bBoxMax = new LatLng(10, 10);

        Area area = new Area("Graz", 0, bBoxMin, bBoxMax, polygon);
        assertEquals("Point inside 1", true, area.inArea(new LatLng(1, 5)));
        assertEquals("Point inside 2", true, area.inArea(new LatLng(1, 5.0001)));
        assertEquals("Point inside 3", false, area.inArea(new LatLng(1, 4.999)));
        assertEquals("Point inside 4", true, area.inArea(new LatLng(-5, -5)));
        assertEquals("Point inside 5", true, area.inArea(new LatLng(-5.0001, -5.0001)));
        assertEquals("Point inside 6", false, area.inArea(new LatLng(-4.9999, -4.9999)));
        assertEquals("Point inside 7", true, area.inArea(new LatLng(-1, -10)));
        assertEquals("Point inside 7", false, area.inArea(new LatLng(-1, -10.0001)));
        assertEquals("Point inside 7", true, area.inArea(new LatLng(-1, -9.9999)));
    }

    @Test//-----------------------------------------------------------------------------------------
    public void loadArea(){
        String json = "{\"id\":6,\"data\":[\n" +
                "{\"lon\":15.446295,\"lat\":47.066774},\n" +
                "{\"lon\":15.446458,\"lat\":47.067621},\n" +
                "{\"lon\":15.445586,\"lat\":47.068092},\n" +
                "{\"lon\":15.446021,\"lat\":47.068450},\n" +
                "{\"lon\":15.447282,\"lat\":47.069765},\n" +
                "{\"lon\":15.448277,\"lat\":47.071098},\n" +
                "{\"lon\":15.448294,\"lat\":47.071225},\n" +
                "{\"lon\":15.448670,\"lat\":47.071828},\n" +
                "{\"lon\":15.448715,\"lat\":47.072120},\n" +
                "{\"lon\":15.448463,\"lat\":47.072120},\n" +
                "{\"lon\":15.448533,\"lat\":47.072350},\n" +
                "{\"lon\":15.448479,\"lat\":47.072701},\n" +
                "{\"lon\":15.448195,\"lat\":47.073249},\n" +
                "{\"lon\":15.447875,\"lat\":47.073809},\n" +
                "{\"lon\":15.446973,\"lat\":47.073635},\n" +
                "{\"lon\":15.446716,\"lat\":47.074271},\n" +
                "{\"lon\":15.443562,\"lat\":47.077829},\n" +
                "{\"lon\":15.441588,\"lat\":47.078209},\n" +
                "{\"lon\":15.439893,\"lat\":47.078530},\n" +
                "{\"lon\":15.439099,\"lat\":47.079085},\n" +
                "{\"lon\":15.437146,\"lat\":47.078888},\n" +
                "{\"lon\":15.436556,\"lat\":47.078778},\n" +
                "{\"lon\":15.436116,\"lat\":47.078625},\n" +
                "{\"lon\":15.435708,\"lat\":47.078413},\n" +
                "{\"lon\":15.434648,\"lat\":47.077641},\n" +
                "{\"lon\":15.433420,\"lat\":47.077332},\n" +
                "{\"lon\":15.433134,\"lat\":47.077094},\n" +
                "{\"lon\":15.434348,\"lat\":47.074351},\n" +
                "{\"lon\":15.434761,\"lat\":47.072349},\n" +
                "{\"lon\":15.435082,\"lat\":47.070963},\n" +
                "{\"lon\":15.435146,\"lat\":47.069592},\n" +
                "{\"lon\":15.435131,\"lat\":47.069499},\n" +
                "{\"lon\":15.434567,\"lat\":47.064285},\n" +
                "{\"lon\":15.434760,\"lat\":47.064247},\n" +
                "{\"lon\":15.435518,\"lat\":47.064212},\n" +
                "{\"lon\":15.438406,\"lat\":47.064362},\n" +
                "{\"lon\":15.439677,\"lat\":47.064486},\n" +
                "{\"lon\":15.440246,\"lat\":47.064592},\n" +
                "{\"lon\":15.440820,\"lat\":47.064588},\n" +
                "{\"lon\":15.442263,\"lat\":47.064354},\n" +
                "{\"lon\":15.442542,\"lat\":47.064409},\n" +
                "{\"lon\":15.442799,\"lat\":47.064548},\n" +
                "{\"lon\":15.443287,\"lat\":47.065078},\n" +
                "{\"lon\":15.443673,\"lat\":47.065429},\n" +
                "{\"lon\":15.444435,\"lat\":47.065758},\n" +
                "{\"lon\":15.445250,\"lat\":47.066080},\n" +
                "{\"lon\":15.446114,\"lat\":47.066358},\n" +
                "{\"lon\":15.446232,\"lat\":47.066566},\n" +
                "{\"lon\":15.446286,\"lat\":47.066745}\n" +
                "], \"name\": \"Graz - Innere Stadt\" }\n";

        Area area = new Area();
        area.loadJson(json);

        assertEquals("Name", "Graz - Innere Stadt", area.getName());
        assertEquals("Id", 6, area.getId());
        assertEquals("Point inside", true, area.inArea(new LatLng(47.067050, 15.442080)));
    }
}