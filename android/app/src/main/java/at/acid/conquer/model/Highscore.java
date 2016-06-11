package at.acid.conquer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Annie on 18/05/2016.
 */



public class Highscore extends HashMap<Long, Highscore.HighscoreUser> {


    public class HighscoreUser {
        final private String mUsername;
        final private Long mPoints;
        final private Boolean mSelf;



        public HighscoreUser(String name, long points, boolean self) {

            mSelf = self;
            mUsername = name;
            mPoints = points;
        }


        public String getUsername() {
            return mUsername;
        }


        public long getPoints(){
            return mPoints;
        }

        public boolean getSelf ()
        {
            return mSelf;
        }
    }
}
