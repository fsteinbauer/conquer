package at.acid.conquer.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Annie on 04/05/2016.
 */
public class PowerUP {

    public enum Type
    {
        POWER_UP ;
    }

    private final int ID;

    private final LatLng pos;

    private final Type type;

    public PowerUP(int id, LatLng pos, Type type)
    {
        this.ID = id;
        this.pos = pos;
        this.type = type;

    }
}
