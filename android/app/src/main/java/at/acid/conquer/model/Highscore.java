package at.acid.conquer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Annie on 18/05/2016.
 */



public class Highscore extends ArrayList<ArrayList<Highscore.HighscoreUser>>{


    public class HighscoreUser {
        final private String mId;
        final private String mUsername;
        final private Long mPoints;

        public HighscoreUser(String id, String username, Long points)
        {
            mId = id;
            mUsername = username;
            mPoints = points;
        }


        public String getUsername() {
            return mUsername;
        }

        public String getID()
        {
            return mId;
        }

        public long getPoints(){
            return mPoints;
        }
    }
}
