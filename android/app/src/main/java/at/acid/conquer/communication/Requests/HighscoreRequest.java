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

    private final long mID;

    private final long mArea;

    public HighscoreRequest(long ID, int area) {
        mID = ID;
        mArea = area;
    }


    private int mSuccess;


    @Override
    public String getURLExtension() {
        return "highscore/" + mID ;
    }


    private Highscore mHighscore;


    /*{"success":true,"highscores":{"1":{"name":"Johannes der L\u00e4ufer","points":2550,"self":false},"2":{"name":"Johannes der L\u00e4ufer","points":1500,"self":false},"3":{"name":"Johannes der L\u00e4ufer","points":850,"self":false},"4":{"name":"Johannes der L\u00e4ufer","points":770,"self":false},"5":{"name":"Johannes der L\u00e4ufer","points":650,"self":false},"6":{"name":"Johannes der L\u00e4ufer","points":600,"self":false},"7":{"name":"Johannes der L\u00e4ufer","points":600,"self":false},"41":{"name":"Johannes der L\u00e4ufer","points":280,"self":false},"42":{"name":"Johannes der L\u00e4ufer","points":270,"self":true},"43":{"name":"Johannes der L\u00e4ufer","points":260,"self":false}}}
     */

    @Override
    public boolean parseReturn(String returnString) {
        try {

            JSONObject obj = new JSONObject(returnString);

            mSuccess = obj.getInt("success");

            JSONObject highscore = obj.getJSONObject("highscore");

            Iterator x = highscore.keys();
            mHighscore = new Highscore();

            while (x.hasNext()) {
                String position = (String) x.next();


                JSONObject highscoreEntry = highscore.getJSONObject(position);

                String name = highscoreEntry.getString("name");
                long points = highscoreEntry.getLong("points");

                boolean self = highscoreEntry.getBoolean("self");

                getmHighscore().put(Long.parseLong(position), getmHighscore().new HighscoreUser(name, points, self));


            }

            return true;
        } catch (JSONException e) {
            mSuccess = -1;
            Log.d(TAG, "parseReturn(): Error " + e.getMessage());
            return false;
        } catch (NumberFormatException nfe) {
            mSuccess = -2;
            Log.d(TAG, "parseReturn(): NumberFormatException: " + nfe.getMessage());
            return false;
        }

    }


    public int getmSuccess() {
        return mSuccess;
    }

    public Highscore getmHighscore() {
        return mHighscore;
    }
}
