package at.acid.conquer.communication.Requests;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import at.acid.conquer.model.Highscore;

/**
 * Created by Annie on 17/06/2016.
 */
public class AddScoreRequest extends Request {

    private final String TAG = "AddScoreRequest";

    private final long mPoints;
    private final String mUserID;

    private final Result mResult;

    private final int mArea;

    public AddScoreRequest(String userID, long Points, int Area) {
        if (Points < 0) {
            throw new IllegalArgumentException("Points may not be negative!");

        }
        if (Area < 0) {
            throw new IllegalArgumentException("Area may not be negative!");

        }
        mPoints = Points;

        mArea = Area;

        mUserID = userID;
        mResult = new Result();
        mResult.mSuccess = ReturnValue.NOT_INITIALIZED;

    }


    @Override
    public String getURLExtension() {
        return "/addscore/" + mUserID + "/" + mArea + "/" + mPoints;
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
