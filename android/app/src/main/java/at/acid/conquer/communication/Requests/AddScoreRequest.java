package at.acid.conquer.communication.Requests;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Annie on 17/06/2016.
 */
public class AddScoreRequest extends Request {

    private final String TAG = "AddScoreRequest";

    private final long mPoints;
    private final String mUserID;

    private final int mArea;

    private ReturnValue mResult = ReturnValue.NOT_INITIALIZED;

    public AddScoreRequest(String userID, long Points, int Area) {
        if (Points < 0) {
            throw new IllegalArgumentException("Points may not be negative!");

        }
        if (Area < 0) {
            throw new IllegalArgumentException("Area may not be negative!");

        }

        Log.d(TAG, "sending AddScoreRequest");
        mPoints = Points;

        mArea = Area;

        mUserID = userID;
    }


    @Override
    public String getURLExtension() {
        return "addscore/" + mUserID + "/" + mArea + "/" + mPoints;
    }

    @Override
    public void parseReturn(String s) {

        try {
            JSONObject obj = new JSONObject(s);

            if(obj.getBoolean("success"))
            {
                this.mResult = ReturnValue.SUCCESS;
            }
            else
            {
                this.mResult = ReturnValue.DATABASE_ERROR;
            }

            Log.d(TAG, "parsed Add Score Request Return");

        } catch (JSONException e) {

            mResult = ReturnValue.JSON_ERROR;
            Log.e(TAG, "parseReturn(): " + e.getMessage());
            e.printStackTrace();

        }

    }



}
