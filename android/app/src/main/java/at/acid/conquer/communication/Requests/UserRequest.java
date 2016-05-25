package at.acid.conquer.communication.Requests;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import at.acid.conquer.model.Highscore;
import at.acid.conquer.model.PowerUP;

/**
 * Created by Annie on 18/05/2016.
 */
public abstract  class UserRequest extends Request{
    @Override
    public String getURLExtension() {
        return "user/" + this.getURLExtension();
    }


    public List<PowerUP> parsePowerUps(JSONArray JSONPowrUps) throws JSONException {

        List<PowerUP> powerUPs;
        powerUPs = new LinkedList<>();
        for( int i = 0; i < JSONPowrUps.length(); i++ ) {
            JSONObject pow = JSONPowrUps.getJSONObject(i);

            LatLng pos = new LatLng(pow.getDouble("lat"), pow.getDouble("lng"));

            int intType = pow.getInt("type");
            if(intType < 0 || intType >= PowerUP.Type.values().length)
            {
                throw new JSONException("Unsuported PowerUpType");
            }
            PowerUP.Type type = PowerUP.Type.POWER_UP.values()[intType];
            powerUPs.add( new PowerUP(pow.getInt("id"), pos, type));
        }
        return powerUPs;
    }

    public Highscore parseHighscore(JSONArray JSONHighscore) throws JSONException {
        Highscore highscore = new Highscore();
        for( int i = 0; i < JSONHighscore.length(); i++ ) {
            JSONObject JSONArea = JSONHighscore.getJSONObject(i);

            int areaId = JSONArea.getInt("id");

            JSONArray JSONHighscoreForArea = JSONArea.getJSONArray("areaHighscore");
            ArrayList highscoreForArea = new ArrayList();
            highscore.add(areaId, highscoreForArea);

            for(int j = 0; j < JSONHighscoreForArea.length(); j++)
            {
                JSONObject userHighScore = JSONHighscoreForArea.getJSONObject(j);
                Highscore.HighscoreUser highscoreUser = highscore.new HighscoreUser(userHighScore.getString("id"), userHighScore.getString("name"), userHighScore.getLong("points"));
                highscoreForArea.add(j, highscoreUser);
            }



        }
    return highscore;
    }

}
