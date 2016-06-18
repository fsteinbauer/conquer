package at.acid.conquer.communication.Requests;

import android.database.CursorJoiner;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import at.acid.conquer.adapter.RankingAdapter;
import at.acid.conquer.model.Highscore;

/**
 * Created by Annie on 17/06/2016.
 */
public class HighscoreRequest extends Request {

    private final String TAG = "HighscoreRequest";

    private final int mArea;
    private final String mUserID;



    private Result mResult;



    public static class Result{
        public Highscore mHighScore;
        public ReturnValue mReturn;
    }

    public Result getResult()
    {
        return mResult;
    }

    public HighscoreRequest(int Area, String userID) {
        if (Area < 0) {
            throw new IllegalArgumentException("Area may not be negative!");

        }

        mArea = Area;
        mUserID = userID;
        mResult = new Result();
        mResult.mReturn = ReturnValue.NOT_INITIALIZED;


    }


    @Override
    public void setSuccess(ReturnValue success) {
        mResult.mReturn = success;
    }

    @Override
    public String getURLExtension() {
        return "/highscore/" + mUserID + "/" + mArea;
    }

    @Override
    public void parseReturn(String s) {

        Highscore hghs = new Highscore();
        try {
            JSONObject obj = new JSONObject(s);
            JSONArray arr = obj.getJSONArray("highscores");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject user = arr.getJSONObject(i);
                long rank = user.getLong("rank");

                String name = user.getString("name");

                Long points = user.getLong("points");

                Boolean is_user = user.getBoolean("is_user");


                hghs.add(new Highscore.HighscoreUser(name, points, is_user, rank));


            }

            Log.d(TAG, "self contained:" + hghs.findSelf());
            Log.d(TAG, "parsed " + hghs.size() + " entries");

            mResult.mReturn = ReturnValue.SUCCESS;
            mResult.mHighScore = hghs;




        } catch (JSONException e) {

            mResult.mReturn = ReturnValue.JSON_ERROR;
            Log.e(TAG, "parseReturn(): " + e.getMessage());
            Log.e(TAG, "return String was: " + s);
            System.out.println("return String was: " + s);
            e.printStackTrace();

        }



    }


}
