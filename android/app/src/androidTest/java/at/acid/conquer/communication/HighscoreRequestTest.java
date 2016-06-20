package at.acid.conquer.communication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.acid.conquer.MainActivity;
import at.acid.conquer.communication.Requests.ClearDataRequest;
import at.acid.conquer.communication.Requests.Request;
import at.acid.conquer.fragments.AccountFragment;
import at.acid.conquer.fragments.HighscoreFragment;
import at.acid.conquer.model.Highscore;
import at.acid.conquer.model.User;

/**
 * Created by Annie on 04/05/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class HighscoreRequestTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    private Communicator c = new Communicator(new Communicator.CummunicatorClient() {
        @Override
        public void onRequestReady(Request r) {

        }

        @Override
        public void onRequestTimeOut(Request r) {

        }

        @Override
        public void onRequestError(Request r) {

        }
    }, "http://conquer2.menzi.at");


    private MainActivity mActivity;

    @Before
    public void prepareDatabase() throws Exception {
        c.mServerUrl = "http://conquer2.menzi.at";
        ClearDataRequest cdr = new ClearDataRequest();
        c.sendRequest(cdr);
        Thread.sleep(3000);
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void sendHighscoreRequest() throws Exception {


        User user = mActivity.getUser();

        AccountFragment ac = mActivity.getmAccountFragment();
        ac.setUser(user);

        user.setId("");
        user.setName("");

        ac.registerUser();


        Thread.sleep(5000);

        user.updateArea(1, 3000, 30);

        user.updateScore();

        Thread.sleep(5000);

        HighscoreFragment hf = mActivity.getmHighscoreFragment();

        hf.getRanking().setCurrentArea("Graz", 0);

        Thread.sleep(5000);

        Assert.assertEquals(1, hf.getRanking().getCount());

        Assert.assertEquals(new Long(1), hf.getRanking().getCurrentRank());

        Highscore.HighscoreUser hu = (Highscore.HighscoreUser) hf.getRanking().getItem(0);


        Assert.assertEquals(new Long(1), hu.getRank());

        Assert.assertEquals(new Long(30), hu.getPoints());

        Assert.assertTrue(hu.getSelf());
        Assert.assertEquals(user.getName(), hu.getUsername());

        hf.getRanking().setCurrentArea("Graz", 1);

        Thread.sleep(5000);

        Assert.assertEquals(1, hf.getRanking().getCount());


        Assert.assertEquals(new Long(1), hf.getRanking().getCurrentRank());

        hu = (Highscore.HighscoreUser) hf.getRanking().getItem(0);


        Assert.assertEquals(new Long(1), hu.getRank());

        Assert.assertEquals(new Long(30), hu.getPoints());

        Assert.assertTrue(hu.getSelf());
        Assert.assertEquals(user.getName(), hu.getUsername());


        hf.getRanking().setCurrentArea("Graz", 2);

        Thread.sleep(5000);

        Assert.assertEquals(0, hf.getRanking().getCount());

        Assert.assertEquals(null, hf.getRanking().getCurrentRank());


    }

    @Test
    public void HighscoreWithTwoUsersDifferenArea() throws Exception {


        User user = mActivity.getUser();

        AccountFragment ac = mActivity.getmAccountFragment();
        ac.setUser(user);

        user.setId("");
        user.setName("");

        ac.registerUser();


        Thread.sleep(5000);

        user.updateArea(1, 3000, 30);

        user.updateScore();

        Thread.sleep(5000);

        HighscoreFragment hf = mActivity.getmHighscoreFragment();

        hf.getRanking().setCurrentArea("Graz", 0);

        Thread.sleep(5000);

        Assert.assertEquals(1, hf.getRanking().getCount());

        Assert.assertEquals(new Long(1), hf.getRanking().getCurrentRank());

        Highscore.HighscoreUser hu = (Highscore.HighscoreUser) hf.getRanking().getItem(0);


        Assert.assertEquals(new Long(1), hu.getRank());

        Assert.assertEquals(new Long(30), hu.getPoints());

        Assert.assertTrue(hu.getSelf());
        Assert.assertEquals(user.getName(), hu.getUsername());

        hf.getRanking().setCurrentArea("Graz", 1);

        Thread.sleep(5000);

        Assert.assertEquals(1, hf.getRanking().getCount());


        Assert.assertEquals(new Long(1), hf.getRanking().getCurrentRank());

        hu = (Highscore.HighscoreUser) hf.getRanking().getItem(0);


        Assert.assertEquals(new Long(1), hu.getRank());

        Assert.assertEquals(new Long(30), hu.getPoints());

        Assert.assertTrue(hu.getSelf());
        Assert.assertEquals(user.getName(), hu.getUsername());


        hf.getRanking().setCurrentArea("Graz", 2);

        Thread.sleep(5000);

        Assert.assertEquals(0, hf.getRanking().getCount());

        Assert.assertEquals(null, hf.getRanking().getCurrentRank());

        user.getAreas().clear();

        user.setId("");
        user.setName("");

        ac.registerUser();


        Thread.sleep(5000);

        user.updateArea(2, 7000, 70);

        user.updateScore();

        Thread.sleep(5000);


        hf.getRanking().setCurrentArea("Graz", 0);

        Thread.sleep(5000);

        Assert.assertEquals(2, hf.getRanking().getCount());

        Assert.assertEquals(new Long(1), hf.getRanking().getCurrentRank());

        hu = (Highscore.HighscoreUser) hf.getRanking().getItem(0);


        Assert.assertEquals(new Long(1), hu.getRank());

        Assert.assertEquals(new Long(70), hu.getPoints());

        Assert.assertTrue(hu.getSelf());
        Assert.assertEquals(user.getName(), hu.getUsername());

        hf.getRanking().setCurrentArea("Graz", 1);

        Thread.sleep(5000);

        Assert.assertEquals(1, hf.getRanking().getCount());

        Assert.assertEquals(null, hf.getRanking().getCurrentRank());

        hf.getRanking().setCurrentArea("Graz", 2);

        Thread.sleep(5000);

        Assert.assertEquals(1, hf.getRanking().getCount());

        Assert.assertEquals(new Long(1), hf.getRanking().getCurrentRank());

        hu = (Highscore.HighscoreUser) hf.getRanking().getItem(0);


        Assert.assertEquals(new Long(1), hu.getRank());

        Assert.assertEquals(new Long(70), hu.getPoints());


    }


    @Test
    public void HighscoreWithTwoUsersSameArea() throws Exception {


        User user = mActivity.getUser();

        AccountFragment ac = mActivity.getmAccountFragment();
        ac.setUser(user);

        user.setId("");
        user.setName("");

        ac.registerUser();


        Thread.sleep(5000);

        user.updateArea(1, 3000, 30);

        user.updateScore();

        Thread.sleep(5000);

        HighscoreFragment hf = mActivity.getmHighscoreFragment();

        hf.getRanking().setCurrentArea("Graz", 0);

        Thread.sleep(5000);

        Assert.assertEquals(1, hf.getRanking().getCount());

        Assert.assertEquals(new Long(1), hf.getRanking().getCurrentRank());

        Highscore.HighscoreUser hu = (Highscore.HighscoreUser) hf.getRanking().getItem(0);


        Assert.assertEquals(new Long(1), hu.getRank());

        Assert.assertEquals(new Long(30), hu.getPoints());

        Assert.assertTrue(hu.getSelf());
        Assert.assertEquals(user.getName(), hu.getUsername());

        hf.getRanking().setCurrentArea("Graz", 1);

        Thread.sleep(5000);

        Assert.assertEquals(1, hf.getRanking().getCount());


        Assert.assertEquals(new Long(1), hf.getRanking().getCurrentRank());

        hu = (Highscore.HighscoreUser) hf.getRanking().getItem(0);


        Assert.assertEquals(new Long(1), hu.getRank());

        Assert.assertEquals(new Long(30), hu.getPoints());

        Assert.assertTrue(hu.getSelf());
        Assert.assertEquals(user.getName(), hu.getUsername());


        hf.getRanking().setCurrentArea("Graz", 2);

        Thread.sleep(5000);

        Assert.assertEquals(0, hf.getRanking().getCount());

        Assert.assertEquals(null, hf.getRanking().getCurrentRank());

        user.getAreas().clear();

        user.setId("");
        user.setName("");

        ac.registerUser();


        Thread.sleep(5000);

        user.updateArea(1, 7000, 70);

        user.updateScore();

        Thread.sleep(5000);


        hf.getRanking().setCurrentArea("Graz", 0);

        Thread.sleep(5000);

        Assert.assertEquals(2, hf.getRanking().getCount());

        Assert.assertEquals(new Long(1), hf.getRanking().getCurrentRank());

        hu = (Highscore.HighscoreUser) hf.getRanking().getItem(0);


        Assert.assertEquals(new Long(1), hu.getRank());

        Assert.assertEquals(new Long(70), hu.getPoints());

        Assert.assertTrue(hu.getSelf());
        Assert.assertEquals(user.getName(), hu.getUsername());

        hf.getRanking().setCurrentArea("Graz", 1);

        Thread.sleep(5000);

        Assert.assertEquals(2, hf.getRanking().getCount());


        hu = (Highscore.HighscoreUser) hf.getRanking().getItem(0);


        Assert.assertEquals(new Long(1), hu.getRank());

        Assert.assertEquals(new Long(70), hu.getPoints());

        Assert.assertTrue(hu.getSelf());
        Assert.assertEquals(user.getName(), hu.getUsername());


        hf.getRanking().setCurrentArea("Graz", 2);

        Thread.sleep(5000);

        Assert.assertEquals(0, hf.getRanking().getCount());

        Assert.assertEquals(null, hf.getRanking().getCurrentRank());
        
    }


}
