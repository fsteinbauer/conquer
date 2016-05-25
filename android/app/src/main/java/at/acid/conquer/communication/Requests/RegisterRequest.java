package at.acid.conquer.communication.Requests;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import at.acid.conquer.model.Highscore;
import at.acid.conquer.model.PowerUP;

/**
 * Created by Annie on 04/05/2016.
 */
public class RegisterRequest extends UserRequest {


    final static String TAG = "RegisterRequest";

    final double mLatitude;
    final double mLongitude;

    private Result mResult;
    public RegisterRequest(double latitude, double longitude)
    {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public Result getResult() {
        return mResult;
    }

    public static class Result
    {
        public String mID;

        public int mSuccess;
        public List<PowerUP> mPowerUPs;

        public Highscore mHighscore;
    }




    @Override
    public String getURLExtension() {
        return "user/register/"+ mLatitude + "/"  + mLongitude;
    }

    @Override
    public boolean parseReturn(String returnString) {
        try {
            JSONObject obj = new JSONObject(returnString);
            JSONObject user = obj.getJSONObject("user");

            Result result = new Result();


            result.mID = user.getString("id");
            result.mSuccess = 0;



            JSONArray arrPowerUps = obj.getJSONArray("powerups");

            result.mPowerUPs = this.parsePowerUps(arrPowerUps);

            result.mHighscore = this.parseHighscore(obj.getJSONArray("highscore"));

            this.mResult = result;


            return true;
        }
        catch( JSONException e ){
            Log.d(TAG, "parseReturn(): Error " + e.getMessage());

            e.printStackTrace();

            return false;
        }

    }


}
