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

    final long Latitude;
    final long Longitude;
    public RegisterRequest(long latitude, long longitude)
    {
        Latitude = latitude;
        Longitude = longitude;
    }

    private String id;
    private String name;
    private int success;
    private List<PowerUP> powerUPs;


    @Override
    public String getURLExtension() {
        return "user/register/"+ Latitude + "/"  + Longitude;
    }

    @Override
    public boolean parseReturn(String returnString) {
        try {
            JSONObject obj = new JSONObject(returnString);
            JSONObject user = obj.getJSONObject("user");


            id = user.getString("id");
            name = user.getString("name");

            success = 0;
            //success = obj.getInt("success");

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