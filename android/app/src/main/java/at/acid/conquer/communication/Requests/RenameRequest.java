package at.acid.conquer.communication.Requests;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Annie on 17/06/2016.
 */
public class RenameRequest extends Request {

    private final String TAG = "RenameRequest";

    private String mUserID;
    private String mName;

    private Result mResult;

    public RenameRequest(String userID, String name) {
        mUserID = userID;
        mName = name.trim().replace(" ", "%20");
        mResult = new Result();
        mResult.mSuccess = ReturnValue.NOT_INITIALIZED;
    }

    @Override
    public String getURLExtension() {
        return "/rename/" + mUserID + "/" + mName;
    }

    @Override
    public void parseReturn(String s) {

        try {
            JSONObject obj = new JSONObject(s);
            if(obj.getBoolean("success"))
            {
                mResult.mSuccess = ReturnValue.SUCCESS;
            }else
            {
                mResult.mSuccess = ReturnValue.DATABASE_ERROR;
            }


        } catch (JSONException e) {

            mResult.mSuccess = ReturnValue.JSON_ERROR;
            Log.e(TAG, "parseReturn(): " + e.getMessage());
            e.printStackTrace();

        }

    }

    @Override
    public void setSuccess(ReturnValue success) {
        mResult.mSuccess = success;
    }

    public Result getResult() {
        return mResult;
    }

    public static class Result {
        public ReturnValue mSuccess;
    }
}
