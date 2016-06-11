//package at.acid.conquer.communication.Requests;
//
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//*
// * Created by Annie on 04/05/2016.
//
//
//public class LoginRequest extends UserRequest{
//
//    final static String TAG = "RegisterRequest";
//
//    private  final long mID;
//    public LoginRequest(long ID)
//    {
//        mID = ID;
//    }
//
//
//    private int mSuccess;
//
//
//    @Override
//    public String getURLExtension() {
//        return "user/login/" + mID;
//    }
//
//
//    @Override
//    public boolean parseReturn(String returnString) {
//        try {
//            JSONObject obj = new JSONObject(returnString);
//            JSONArray arrPowerUps = obj.getJSONArray("powerups");
//            this.parsePowerUps(arrPowerUps);
//
//            JSONArray arrHighscore = obj.getJSONArray("highscore");
//            this.parseHighscore(arrHighscore);
//
//            return true;
//
//
//        }
//        catch( JSONException e ){
//            Log.d(TAG, "parseReturn(): Error " + e.getMessage());
//            return false;
//        }
//
//    }
//}
