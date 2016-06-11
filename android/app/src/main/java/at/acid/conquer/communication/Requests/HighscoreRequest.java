package at.acid.conquer.communication.Requests;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import at.acid.conquer.model.Highscore;

/**
 * Created by Annie on 11/06/2016.
 */
public class HighscoreRequest extends Request {


    final static String TAG = "RegisterRequest";

    private final String mID;

    private final long mArea;

    public HighscoreRequest(String ID, int area) {
        mID = ID;
        mArea = area;
    }


    private ReturnValue mSuccess = ReturnValue.NOT_INITIALIZED;


    @Override
    public String getURLExtension() {
        return "highscore/" + mID ;
    }


    private Highscore mHighscore = new Highscore();;



    @Override
    public boolean parseReturn(String returnString) {

        setSuccess(ReturnValue.NOT_INITIALIZED);
        try {

            JSONObject obj = new JSONObject(returnString);

            if(obj.getBoolean("success"))
            {
                setSuccess(ReturnValue.SUCCESS);
            }else
            {
                setSuccess(ReturnValue.JSON_ERROR);
            }


            JSONObject highscore = obj.getJSONObject("highscores");

            Iterator x = highscore.keys();
            mHighscore.clear();



            while (x.hasNext()) {


                String position = (String) x.next();


                JSONObject highscoreEntry = highscore.getJSONObject(position);

                String name = highscoreEntry.getString("name");
                long points = highscoreEntry.getLong("points");

                boolean self = highscoreEntry.getBoolean("self");

                getmHighscore().put(Long.parseLong(position), new Highscore.HighscoreUser(name, points, self));


            }

            return true;
        } catch (JSONException e) {
            this.setSuccess(ReturnValue.JSON_ERROR);
            e.printStackTrace();
            Log.d(TAG, "parseReturn(): Error " + e.getMessage());
            return false;
        } catch (NumberFormatException nfe) {
            this.setSuccess(ReturnValue.NUMBER_FORMAT_ERROR);
            nfe.printStackTrace();
            Log.d(TAG, "parseReturn(): NumberFormatException: " + nfe.getMessage());
            return false;
        }
    }


    public ReturnValue getmSuccess() {
        return mSuccess;
    }

    public Highscore getmHighscore() {
        return mHighscore;
    }

    public void setSuccess(ReturnValue mSuccess) {
        this.mSuccess = mSuccess;
    }
}
