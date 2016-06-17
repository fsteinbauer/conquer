package at.acid.conquer.communication.Requests;

import android.util.JsonWriter;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import at.acid.conquer.model.Highscore;

/**
 * Created by Annie on 17/06/2016.
 */
public class HighscoreRequest extends Request {

    private final String TAG = "HighscoreRequest";

    private final int mArea;
    private final String mUserID;

    private final Result mResult;

    public HighscoreRequest(int Area, String userID) {
        if (Area < 0) {
            throw new IllegalArgumentException("Area may not be negative!");

        }

        mArea = Area;
        mUserID = userID;
        mResult = new Result();
        mResult.mSuccess = ReturnValue.NOT_INITIALIZED;

    }


    @Override
    public String getURLExtension() {
        return "highscore/" + mUserID + "/" + mArea;
    }

    @Override
    public void parseReturn(String s) {
        mResult.mHgs = new Highscore();
        try {
            JSONObject obj = new JSONObject(s);
            JSONArray arr = obj.getJSONArray("highscores");

            for(int i = 0; i < arr.length(); i++)
            {
                JSONObject user = arr.getJSONObject(i);
                long rank = user.getLong("rank");

                String name = user.getString("name");

                Long points = user.getLong("points");

                Boolean is_user = user.getBoolean("is_user");


                mResult.mHgs.add( new Highscore.HighscoreUser(name, points, is_user, rank));
            }

            Log.d(TAG, "parsed "+ mResult.mHgs.size() + " entries");

            mResult.mSuccess = ReturnValue.SUCCESS;


        } catch (JSONException e) {

            mResult.mSuccess = ReturnValue.JSON_ERROR;
            Log.e(TAG, "parseReturn(): " + e.getMessage());
            Log.e(TAG, "return String was: " + s);
            System.out.println( "return String was: " + s);
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

        public Highscore mHgs;
    }
}
