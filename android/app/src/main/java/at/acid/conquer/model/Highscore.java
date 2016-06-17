package at.acid.conquer.model;

import java.util.HashMap;

/**
 * Created by Annie on 18/05/2016.
 */


public class Highscore extends HashMap<Long, Highscore.HighscoreUser> {


    public long getSelf() {

        for (Entry<Long, HighscoreUser> user : this.entrySet()) {
            if (user.getValue().getSelf()) {
                return user.getKey();
            }
        }
        return -1;
    }

    public static class HighscoreUser {
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


        public Long getPoints() {
            return mPoints;
        }

        public boolean getSelf() {
            return mSelf;
        }

    }
}
