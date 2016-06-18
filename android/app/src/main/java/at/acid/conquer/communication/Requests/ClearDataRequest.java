package at.acid.conquer.communication.Requests;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Menzi on 17/06/2016.
 */
public class ClearDataRequest extends Request {

    private final String TAG = "ClearDataRequest";

    private final Result mResult;


    public static class Result
    {
        public ReturnValue mSuccess;
    }

    public ClearDataRequest() {
        mResult = new Result();
        mResult.mSuccess = ReturnValue.NOT_INITIALIZED;

    }


    @Override
    public void setSuccess(ReturnValue success) {
        mResult.mSuccess = success;
    }

    @Override
    public String getURLExtension() {
        return "cleardata";
    }

    @Override
    public void parseReturn(String s) {
        try {
            JSONObject obj = new JSONObject(s);
            if(obj.getBoolean("success"))
            {
                mResult.mSuccess = ReturnValue.SUCCESS;
            } else {
                mResult.mSuccess = ReturnValue.DATABASE_ERROR;
            }
        } catch (JSONException e) {
            mResult.mSuccess = ReturnValue.JSON_ERROR;
            Log.e(TAG, "parseReturn(): " + e.getMessage());
            e.printStackTrace();
        }

    }


    public Result getResult() {
        return mResult;
    }
}
