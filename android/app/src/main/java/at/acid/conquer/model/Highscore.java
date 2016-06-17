package at.acid.conquer.model;

import java.util.ArrayList;

/**
 * Created by Annie on 18/05/2016.
 */


public class Highscore extends ArrayList<Highscore.HighscoreUser> {


    public HighscoreUser findSelf() {

        for (HighscoreUser user : this) {
            if (user.getSelf() == true) {
                return user;
            }
        }
        return null;
    }

    public static class HighscoreUser {
        final private String mUsername;
        final private Long mPoints;
        final private Boolean mSelf;

        final private long mRank;


        public HighscoreUser(String name, long points, boolean self, long rank) {

            mSelf = self;
            mUsername = name;
            mPoints = points;
            mRank = rank;
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

        public Long getRank() {
            return mRank;
        }
    }
}
