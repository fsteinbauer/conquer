package at.acid.conquer.communication.Requests;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import at.acid.conquer.model.PowerUP;

/**
 * Created by Annie on 04/05/2016.
 */
public class RegisterRequest extends Request {


    final static String TAG = "RegisterRequest";
    public RegisterRequest()
    {

    }

    private long id;
    private int success;
    private List<PowerUP> powerUPs;


    @Override
    protected String getMessage() {
        return "REGISTER()";
    }

    @Override
    public boolean parseReturn(String returnString) {
        try {
            JSONObject obj = new JSONObject(returnString);

            success = obj.getInt("success");

            JSONArray arrPowerUps = obj.getJSONArray("powerups");

            for( int i = 0; i < arrPowerUps.length(); i++ ) {
                JSONObject pow = arrPowerUps.getJSONObject(i);

                LatLng pos = new LatLng(pow.getDouble("lat"), pow.getDouble("lng"));

                int intType = pow.getInt("type");
                if(intType < 0 || intType >= PowerUP.Type.values().length)
                {
                    return false;
                }
                PowerUP.Type type = PowerUP.Type.POWER_UP.values()[intType];
                powerUPs.add( new PowerUP(pow.getInt("id"), pos, type));
            }
            return true;


        }
        catch( JSONException e ){
            Log.d(TAG, "parseReturn(): Error " + e.getMessage());
            return false;
        }

    }


}
