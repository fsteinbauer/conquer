package at.acid.conquer.communication.Requests;

import android.content.SyncAdapterType;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Annie on 04/05/2016.
 */
public class RegisterRequest extends Request {


    final static String TAG = "RegisterRequest";


    private Result mResult;

    public RegisterRequest() {
        this.mResult = new Result();
        this.mResult.mSuccess = ReturnValue.NOT_INITIALIZED;

    }

    public Result getResult() {
        return mResult;
    }

    public static class Result {
        public String mID;

        public ReturnValue mSuccess;

        public String mName;

    }


    @Override
    public void setSuccess(ReturnValue success) {
        mResult.mSuccess = success;
    }

    @Override
    public String getURLExtension() {
        return "/register"; //"+ mLatitude + "/"  + mLongitude;
    }

    @Override
    public void parseReturn(String returnString) {

        Log.d(TAG, "received answer: "+ returnString);
        Result result = this.mResult;
        try {
            JSONObject obj = new JSONObject(returnString);

            result.mID = obj.getString("id");
            result.mSuccess = ReturnValue.SUCCESS;

            result.mName = obj.getString("name");


        } catch (JSONException e) {
            Log.d(TAG, "parseReturn(): Error " + e.getMessage());

            Log.d(TAG, "parseReturn(): " + returnString);

            System.out.println(returnString);

            this.mResult.mSuccess = Request.ReturnValue.JSON_ERROR;
            e.printStackTrace();

        }

    }




}
