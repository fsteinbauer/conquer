package at.acid.conquer.communication;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

import at.acid.conquer.communication.Requests.AddScoreRequest;
import at.acid.conquer.communication.Requests.ClearDataRequest;
import at.acid.conquer.communication.Requests.HighscoreRequest;
import at.acid.conquer.communication.Requests.RegisterRequest;
import at.acid.conquer.communication.Requests.Request;
import at.acid.conquer.model.Highscore;

/**
 * Created by Philipp on 20/06/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HighscoreTest implements Communicator.CummunicatorClient {
    Communicator mComm;

    final static int NUM_USERS = 20;

    int mFinishedRequests = 0;

    static List<String> mUsers = new ArrayList<>();

    @Override
    public void onRequestReady(Request r) {

    }

    @Override
    public void onRequestTimeOut(Request r) {
        Assert.fail("onRequestTimeOut: " + r.getClass().getName());
    }

    @Override
    public void onRequestError(Request r) {
        Assert.fail("onRequestError: " + r.getClass().getName());
    }

    @Before
    public void init() {

        mComm = new Communicator(this, "http://conquer2.menzi.at/");

        ClearDataRequest r = new ClearDataRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
    }

    @Test
    public void highscore0() {
        RegisterRequest r_0 = new RegisterRequest();
        mComm.sendRequest(r_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_0.getResult().mSuccess);

        AddScoreRequest r1_0 = new AddScoreRequest(r_0.getResult().mID, 100, 1);
        mComm.sendRequest(r1_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_0.getResult().mSuccess);

        RegisterRequest r_1 = new RegisterRequest();
        mComm.sendRequest(r_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_1.getResult().mSuccess);

        AddScoreRequest r1_1 = new AddScoreRequest(r_1.getResult().mID, 200, 2);
        mComm.sendRequest(r1_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_1.getResult().mSuccess);

        RegisterRequest r_2 = new RegisterRequest();
        mComm.sendRequest(r_2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_2.getResult().mSuccess);

        AddScoreRequest r1_2 = new AddScoreRequest(r_2.getResult().mID, 300, 3);
        mComm.sendRequest(r1_2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_2.getResult().mSuccess);

        RegisterRequest r_3 = new RegisterRequest();
        mComm.sendRequest(r_3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_3.getResult().mSuccess);

        AddScoreRequest r1_3 = new AddScoreRequest(r_3.getResult().mID, 400, 4);
        mComm.sendRequest(r1_3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_3.getResult().mSuccess);

        RegisterRequest r_4 = new RegisterRequest();
        mComm.sendRequest(r_4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_4.getResult().mSuccess);

        AddScoreRequest r1_4 = new AddScoreRequest(r_4.getResult().mID, 500, 5);
        mComm.sendRequest(r1_4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_4.getResult().mSuccess);

        RegisterRequest r_5 = new RegisterRequest();
        mComm.sendRequest(r_5);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_5.getResult().mSuccess);

        AddScoreRequest r1_5 = new AddScoreRequest(r_5.getResult().mID, 600, 6);
        mComm.sendRequest(r1_5);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_5.getResult().mSuccess);

        RegisterRequest r_6 = new RegisterRequest();
        mComm.sendRequest(r_6);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_6.getResult().mSuccess);

        AddScoreRequest r1_6 = new AddScoreRequest(r_6.getResult().mID, 700, 7);
        mComm.sendRequest(r1_6);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_6.getResult().mSuccess);

        RegisterRequest r_7 = new RegisterRequest();
        mComm.sendRequest(r_7);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_7.getResult().mSuccess);

        AddScoreRequest r1_7 = new AddScoreRequest(r_7.getResult().mID, 800, 8);
        mComm.sendRequest(r1_7);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_7.getResult().mSuccess);

        RegisterRequest r_8 = new RegisterRequest();
        mComm.sendRequest(r_8);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_8.getResult().mSuccess);

        AddScoreRequest r1_8 = new AddScoreRequest(r_8.getResult().mID, 900, 9);
        mComm.sendRequest(r1_8);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_8.getResult().mSuccess);

        RegisterRequest r_9 = new RegisterRequest();
        mComm.sendRequest(r_9);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_9.getResult().mSuccess);

        AddScoreRequest r1_9 = new AddScoreRequest(r_9.getResult().mID, 1000, 10);
        mComm.sendRequest(r1_9);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_9.getResult().mSuccess);

        RegisterRequest r_10 = new RegisterRequest();
        mComm.sendRequest(r_10);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_10.getResult().mSuccess);

        AddScoreRequest r1_10 = new AddScoreRequest(r_10.getResult().mID, 1100, 11);
        mComm.sendRequest(r1_10);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_10.getResult().mSuccess);

        RegisterRequest r_11 = new RegisterRequest();
        mComm.sendRequest(r_11);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_11.getResult().mSuccess);

        AddScoreRequest r1_11 = new AddScoreRequest(r_11.getResult().mID, 1200, 12);
        mComm.sendRequest(r1_11);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_11.getResult().mSuccess);


        HighscoreRequest r2 = new HighscoreRequest(0, r_11.getResult().mID);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        Highscore hscore = r2.getResult().mHighScore;
        Assert.assertEquals(5, hscore.size());
        Assert.assertEquals(new Long(1200), hscore.get(0).getPoints());
        Assert.assertEquals(new Long(800), hscore.get(4).getPoints());
    }


    @Test
    public void highscore1() {
        RegisterRequest r_0 = new RegisterRequest();
        mComm.sendRequest(r_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_0.getResult().mSuccess);

        AddScoreRequest r1_0 = new AddScoreRequest(r_0.getResult().mID, 100, 1);
        mComm.sendRequest(r1_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_0.getResult().mSuccess);

        RegisterRequest r_1 = new RegisterRequest();
        mComm.sendRequest(r_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_1.getResult().mSuccess);

        AddScoreRequest r1_1 = new AddScoreRequest(r_1.getResult().mID, 200, 2);
        mComm.sendRequest(r1_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_1.getResult().mSuccess);

        RegisterRequest r_2 = new RegisterRequest();
        mComm.sendRequest(r_2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_2.getResult().mSuccess);

        AddScoreRequest r1_2 = new AddScoreRequest(r_2.getResult().mID, 300, 3);
        mComm.sendRequest(r1_2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_2.getResult().mSuccess);

        RegisterRequest r_3 = new RegisterRequest();
        mComm.sendRequest(r_3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_3.getResult().mSuccess);

        AddScoreRequest r1_3 = new AddScoreRequest(r_3.getResult().mID, 400, 4);
        mComm.sendRequest(r1_3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_3.getResult().mSuccess);

        RegisterRequest r_4 = new RegisterRequest();
        mComm.sendRequest(r_4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_4.getResult().mSuccess);

        AddScoreRequest r1_4 = new AddScoreRequest(r_4.getResult().mID, 500, 5);
        mComm.sendRequest(r1_4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_4.getResult().mSuccess);

        RegisterRequest r_5 = new RegisterRequest();
        mComm.sendRequest(r_5);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_5.getResult().mSuccess);

        AddScoreRequest r1_5 = new AddScoreRequest(r_5.getResult().mID, 600, 6);
        mComm.sendRequest(r1_5);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_5.getResult().mSuccess);

        RegisterRequest r_6 = new RegisterRequest();
        mComm.sendRequest(r_6);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_6.getResult().mSuccess);

        AddScoreRequest r1_6 = new AddScoreRequest(r_6.getResult().mID, 700, 7);
        mComm.sendRequest(r1_6);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_6.getResult().mSuccess);

        RegisterRequest r_7 = new RegisterRequest();
        mComm.sendRequest(r_7);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_7.getResult().mSuccess);

        AddScoreRequest r1_7 = new AddScoreRequest(r_7.getResult().mID, 800, 8);
        mComm.sendRequest(r1_7);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_7.getResult().mSuccess);

        RegisterRequest r_8 = new RegisterRequest();
        mComm.sendRequest(r_8);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_8.getResult().mSuccess);

        AddScoreRequest r1_8 = new AddScoreRequest(r_8.getResult().mID, 900, 9);
        mComm.sendRequest(r1_8);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_8.getResult().mSuccess);

        RegisterRequest r_9 = new RegisterRequest();
        mComm.sendRequest(r_9);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_9.getResult().mSuccess);

        AddScoreRequest r1_9 = new AddScoreRequest(r_9.getResult().mID, 1000, 10);
        mComm.sendRequest(r1_9);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_9.getResult().mSuccess);

        RegisterRequest r_10 = new RegisterRequest();
        mComm.sendRequest(r_10);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_10.getResult().mSuccess);

        AddScoreRequest r1_10 = new AddScoreRequest(r_10.getResult().mID, 1100, 11);
        mComm.sendRequest(r1_10);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_10.getResult().mSuccess);

        RegisterRequest r_11 = new RegisterRequest();
        mComm.sendRequest(r_11);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_11.getResult().mSuccess);

        AddScoreRequest r1_11 = new AddScoreRequest(r_11.getResult().mID, 1200, 12);
        mComm.sendRequest(r1_11);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_11.getResult().mSuccess);


        HighscoreRequest r2 = new HighscoreRequest(15, r_11.getResult().mID);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        Highscore hscore = r2.getResult().mHighScore;
        Assert.assertEquals(0, hscore.size());
    }

    @Test
    public void highscore2() {
        RegisterRequest r_0 = new RegisterRequest();
        mComm.sendRequest(r_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_0.getResult().mSuccess);

        AddScoreRequest r1_0 = new AddScoreRequest(r_0.getResult().mID, 100, 1);
        mComm.sendRequest(r1_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_0.getResult().mSuccess);

        RegisterRequest r_1 = new RegisterRequest();
        mComm.sendRequest(r_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_1.getResult().mSuccess);

        AddScoreRequest r1_1 = new AddScoreRequest(r_1.getResult().mID, 200, 2);
        mComm.sendRequest(r1_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_1.getResult().mSuccess);

        RegisterRequest r_2 = new RegisterRequest();
        mComm.sendRequest(r_2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_2.getResult().mSuccess);

        AddScoreRequest r1_2 = new AddScoreRequest(r_2.getResult().mID, 300, 3);
        mComm.sendRequest(r1_2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_2.getResult().mSuccess);

        RegisterRequest r_3 = new RegisterRequest();
        mComm.sendRequest(r_3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_3.getResult().mSuccess);

        AddScoreRequest r1_3 = new AddScoreRequest(r_3.getResult().mID, 400, 4);
        mComm.sendRequest(r1_3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_3.getResult().mSuccess);

        RegisterRequest r_4 = new RegisterRequest();
        mComm.sendRequest(r_4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_4.getResult().mSuccess);

        AddScoreRequest r1_4 = new AddScoreRequest(r_4.getResult().mID, 500, 5);
        mComm.sendRequest(r1_4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_4.getResult().mSuccess);

        RegisterRequest r_5 = new RegisterRequest();
        mComm.sendRequest(r_5);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_5.getResult().mSuccess);

        AddScoreRequest r1_5 = new AddScoreRequest(r_5.getResult().mID, 600, 6);
        mComm.sendRequest(r1_5);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_5.getResult().mSuccess);

        RegisterRequest r_6 = new RegisterRequest();
        mComm.sendRequest(r_6);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_6.getResult().mSuccess);

        AddScoreRequest r1_6 = new AddScoreRequest(r_6.getResult().mID, 700, 7);
        mComm.sendRequest(r1_6);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_6.getResult().mSuccess);

        RegisterRequest r_7 = new RegisterRequest();
        mComm.sendRequest(r_7);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_7.getResult().mSuccess);

        AddScoreRequest r1_7 = new AddScoreRequest(r_7.getResult().mID, 800, 8);
        mComm.sendRequest(r1_7);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_7.getResult().mSuccess);

        RegisterRequest r_8 = new RegisterRequest();
        mComm.sendRequest(r_8);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_8.getResult().mSuccess);

        AddScoreRequest r1_8 = new AddScoreRequest(r_8.getResult().mID, 900, 9);
        mComm.sendRequest(r1_8);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_8.getResult().mSuccess);

        RegisterRequest r_9 = new RegisterRequest();
        mComm.sendRequest(r_9);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_9.getResult().mSuccess);

        AddScoreRequest r1_9 = new AddScoreRequest(r_9.getResult().mID, 1000, 10);
        mComm.sendRequest(r1_9);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_9.getResult().mSuccess);

        RegisterRequest r_10 = new RegisterRequest();
        mComm.sendRequest(r_10);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_10.getResult().mSuccess);

        AddScoreRequest r1_10 = new AddScoreRequest(r_10.getResult().mID, 1100, 11);
        mComm.sendRequest(r1_10);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_10.getResult().mSuccess);

        RegisterRequest r_11 = new RegisterRequest();
        mComm.sendRequest(r_11);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_11.getResult().mSuccess);

        AddScoreRequest r1_11 = new AddScoreRequest(r_11.getResult().mID, 1200, 12);
        mComm.sendRequest(r1_11);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_11.getResult().mSuccess);


        HighscoreRequest r2 = new HighscoreRequest(0, r_0.getResult().mID);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        Highscore hscore = r2.getResult().mHighScore;
        Assert.assertEquals(8, hscore.size());
        Assert.assertEquals(new Long(5), hscore.get(4).getRank());
        Assert.assertEquals(new Long(10), hscore.get(5).getRank());
        Assert.assertEquals(new Long(100), hscore.get(7).getPoints());
    }

    @Test
    public void highscore3() {
        RegisterRequest r_0 = new RegisterRequest();
        mComm.sendRequest(r_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_0.getResult().mSuccess);

        AddScoreRequest r1_0 = new AddScoreRequest(r_0.getResult().mID, 100, 1);
        mComm.sendRequest(r1_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_0.getResult().mSuccess);

        RegisterRequest r_1 = new RegisterRequest();
        mComm.sendRequest(r_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_1.getResult().mSuccess);

        AddScoreRequest r1_1 = new AddScoreRequest(r_1.getResult().mID, 200, 2);
        mComm.sendRequest(r1_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_1.getResult().mSuccess);

        RegisterRequest r_2 = new RegisterRequest();
        mComm.sendRequest(r_2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_2.getResult().mSuccess);

        AddScoreRequest r1_2 = new AddScoreRequest(r_2.getResult().mID, 300, 3);
        mComm.sendRequest(r1_2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_2.getResult().mSuccess);

        RegisterRequest r_3 = new RegisterRequest();
        mComm.sendRequest(r_3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_3.getResult().mSuccess);

        AddScoreRequest r1_3 = new AddScoreRequest(r_3.getResult().mID, 400, 4);
        mComm.sendRequest(r1_3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_3.getResult().mSuccess);

        RegisterRequest r_4 = new RegisterRequest();
        mComm.sendRequest(r_4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_4.getResult().mSuccess);

        AddScoreRequest r1_4 = new AddScoreRequest(r_4.getResult().mID, 500, 5);
        mComm.sendRequest(r1_4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_4.getResult().mSuccess);

        RegisterRequest r_5 = new RegisterRequest();
        mComm.sendRequest(r_5);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_5.getResult().mSuccess);

        AddScoreRequest r1_5 = new AddScoreRequest(r_5.getResult().mID, 600, 6);
        mComm.sendRequest(r1_5);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_5.getResult().mSuccess);

        RegisterRequest r_6 = new RegisterRequest();
        mComm.sendRequest(r_6);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_6.getResult().mSuccess);

        AddScoreRequest r1_6 = new AddScoreRequest(r_6.getResult().mID, 700, 7);
        mComm.sendRequest(r1_6);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_6.getResult().mSuccess);

        RegisterRequest r_7 = new RegisterRequest();
        mComm.sendRequest(r_7);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_7.getResult().mSuccess);

        AddScoreRequest r1_7 = new AddScoreRequest(r_7.getResult().mID, 800, 8);
        mComm.sendRequest(r1_7);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_7.getResult().mSuccess);

        RegisterRequest r_8 = new RegisterRequest();
        mComm.sendRequest(r_8);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_8.getResult().mSuccess);

        AddScoreRequest r1_8 = new AddScoreRequest(r_8.getResult().mID, 900, 9);
        mComm.sendRequest(r1_8);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_8.getResult().mSuccess);

        RegisterRequest r_9 = new RegisterRequest();
        mComm.sendRequest(r_9);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_9.getResult().mSuccess);

        AddScoreRequest r1_9 = new AddScoreRequest(r_9.getResult().mID, 1000, 10);
        mComm.sendRequest(r1_9);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_9.getResult().mSuccess);

        RegisterRequest r_10 = new RegisterRequest();
        mComm.sendRequest(r_10);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_10.getResult().mSuccess);

        AddScoreRequest r1_10 = new AddScoreRequest(r_10.getResult().mID, 1100, 11);
        mComm.sendRequest(r1_10);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_10.getResult().mSuccess);

        RegisterRequest r_11 = new RegisterRequest();
        mComm.sendRequest(r_11);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_11.getResult().mSuccess);

        AddScoreRequest r1_11 = new AddScoreRequest(r_11.getResult().mID, 1200, 12);
        mComm.sendRequest(r1_11);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_11.getResult().mSuccess);


        HighscoreRequest r2 = new HighscoreRequest(0, r_2.getResult().mID);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        Highscore hscore = r2.getResult().mHighScore;
        Assert.assertEquals(10, hscore.size());
        Assert.assertEquals(new Long(8), hscore.get(5).getRank());
        Assert.assertEquals(new Long(300), hscore.get(7).getPoints());
        Assert.assertEquals(new Long(12), hscore.get(9).getRank());
    }

    @Test
    public void highscore4() {
        RegisterRequest r_0 = new RegisterRequest();
        mComm.sendRequest(r_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_0.getResult().mSuccess);

        AddScoreRequest r1_0 = new AddScoreRequest(r_0.getResult().mID, 100, 1);
        mComm.sendRequest(r1_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_0.getResult().mSuccess);

        RegisterRequest r_1 = new RegisterRequest();
        mComm.sendRequest(r_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_1.getResult().mSuccess);

        AddScoreRequest r1_1 = new AddScoreRequest(r_1.getResult().mID, 200, 2);
        mComm.sendRequest(r1_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_1.getResult().mSuccess);

        RegisterRequest r_2 = new RegisterRequest();
        mComm.sendRequest(r_2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_2.getResult().mSuccess);

        AddScoreRequest r1_2 = new AddScoreRequest(r_2.getResult().mID, 300, 3);
        mComm.sendRequest(r1_2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_2.getResult().mSuccess);

        RegisterRequest r_3 = new RegisterRequest();
        mComm.sendRequest(r_3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_3.getResult().mSuccess);

        AddScoreRequest r1_3 = new AddScoreRequest(r_3.getResult().mID, 400, 4);
        mComm.sendRequest(r1_3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_3.getResult().mSuccess);

        RegisterRequest r_4 = new RegisterRequest();
        mComm.sendRequest(r_4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_4.getResult().mSuccess);

        AddScoreRequest r1_4 = new AddScoreRequest(r_4.getResult().mID, 500, 5);
        mComm.sendRequest(r1_4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_4.getResult().mSuccess);

        RegisterRequest r_5 = new RegisterRequest();
        mComm.sendRequest(r_5);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_5.getResult().mSuccess);

        AddScoreRequest r1_5 = new AddScoreRequest(r_5.getResult().mID, 600, 6);
        mComm.sendRequest(r1_5);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_5.getResult().mSuccess);

        RegisterRequest r_6 = new RegisterRequest();
        mComm.sendRequest(r_6);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_6.getResult().mSuccess);

        AddScoreRequest r1_6 = new AddScoreRequest(r_6.getResult().mID, 700, 7);
        mComm.sendRequest(r1_6);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_6.getResult().mSuccess);

        RegisterRequest r_7 = new RegisterRequest();
        mComm.sendRequest(r_7);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_7.getResult().mSuccess);

        AddScoreRequest r1_7 = new AddScoreRequest(r_7.getResult().mID, 800, 8);
        mComm.sendRequest(r1_7);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_7.getResult().mSuccess);

        RegisterRequest r_8 = new RegisterRequest();
        mComm.sendRequest(r_8);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_8.getResult().mSuccess);

        AddScoreRequest r1_8 = new AddScoreRequest(r_8.getResult().mID, 900, 9);
        mComm.sendRequest(r1_8);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_8.getResult().mSuccess);

        RegisterRequest r_9 = new RegisterRequest();
        mComm.sendRequest(r_9);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_9.getResult().mSuccess);

        AddScoreRequest r1_9 = new AddScoreRequest(r_9.getResult().mID, 1000, 10);
        mComm.sendRequest(r1_9);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_9.getResult().mSuccess);

        RegisterRequest r_10 = new RegisterRequest();
        mComm.sendRequest(r_10);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_10.getResult().mSuccess);

        AddScoreRequest r1_10 = new AddScoreRequest(r_10.getResult().mID, 1100, 11);
        mComm.sendRequest(r1_10);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_10.getResult().mSuccess);

        RegisterRequest r_11 = new RegisterRequest();
        mComm.sendRequest(r_11);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_11.getResult().mSuccess);

        AddScoreRequest r1_11 = new AddScoreRequest(r_11.getResult().mID, 1200, 12);
        mComm.sendRequest(r1_11);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_11.getResult().mSuccess);


        HighscoreRequest r2 = new HighscoreRequest(0, r_4.getResult().mID);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        Highscore hscore = r2.getResult().mHighScore;
        Assert.assertEquals(10, hscore.size());
        Assert.assertEquals(new Long(10), hscore.get(9).getRank());
    }

    @Test
    public void highscore5() {
        RegisterRequest r_0 = new RegisterRequest();
        mComm.sendRequest(r_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_0.getResult().mSuccess);

        AddScoreRequest r1_0 = new AddScoreRequest(r_0.getResult().mID, 100, 1);
        mComm.sendRequest(r1_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_0.getResult().mSuccess);

        RegisterRequest r_1 = new RegisterRequest();
        mComm.sendRequest(r_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_1.getResult().mSuccess);

        AddScoreRequest r1_1 = new AddScoreRequest(r_1.getResult().mID, 200, 2);
        mComm.sendRequest(r1_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_1.getResult().mSuccess);

        RegisterRequest r_2 = new RegisterRequest();
        mComm.sendRequest(r_2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_2.getResult().mSuccess);

        AddScoreRequest r1_2 = new AddScoreRequest(r_2.getResult().mID, 300, 3);
        mComm.sendRequest(r1_2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_2.getResult().mSuccess);

        RegisterRequest r_3 = new RegisterRequest();
        mComm.sendRequest(r_3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_3.getResult().mSuccess);

        AddScoreRequest r1_3 = new AddScoreRequest(r_3.getResult().mID, 400, 4);
        mComm.sendRequest(r1_3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_3.getResult().mSuccess);

        RegisterRequest r_4 = new RegisterRequest();
        mComm.sendRequest(r_4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_4.getResult().mSuccess);

        AddScoreRequest r1_4 = new AddScoreRequest(r_4.getResult().mID, 500, 5);
        mComm.sendRequest(r1_4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_4.getResult().mSuccess);

        RegisterRequest r_5 = new RegisterRequest();
        mComm.sendRequest(r_5);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_5.getResult().mSuccess);

        AddScoreRequest r1_5 = new AddScoreRequest(r_5.getResult().mID, 600, 6);
        mComm.sendRequest(r1_5);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_5.getResult().mSuccess);

        RegisterRequest r_6 = new RegisterRequest();
        mComm.sendRequest(r_6);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_6.getResult().mSuccess);

        AddScoreRequest r1_6 = new AddScoreRequest(r_6.getResult().mID, 700, 7);
        mComm.sendRequest(r1_6);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_6.getResult().mSuccess);

        RegisterRequest r_7 = new RegisterRequest();
        mComm.sendRequest(r_7);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_7.getResult().mSuccess);

        AddScoreRequest r1_7 = new AddScoreRequest(r_7.getResult().mID, 800, 8);
        mComm.sendRequest(r1_7);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_7.getResult().mSuccess);

        RegisterRequest r_8 = new RegisterRequest();
        mComm.sendRequest(r_8);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_8.getResult().mSuccess);

        AddScoreRequest r1_8 = new AddScoreRequest(r_8.getResult().mID, 900, 9);
        mComm.sendRequest(r1_8);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_8.getResult().mSuccess);

        RegisterRequest r_9 = new RegisterRequest();
        mComm.sendRequest(r_9);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_9.getResult().mSuccess);

        AddScoreRequest r1_9 = new AddScoreRequest(r_9.getResult().mID, 1000, 10);
        mComm.sendRequest(r1_9);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_9.getResult().mSuccess);

        RegisterRequest r_10 = new RegisterRequest();
        mComm.sendRequest(r_10);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_10.getResult().mSuccess);

        AddScoreRequest r1_10 = new AddScoreRequest(r_10.getResult().mID, 1100, 11);
        mComm.sendRequest(r1_10);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_10.getResult().mSuccess);

        RegisterRequest r_11 = new RegisterRequest();
        mComm.sendRequest(r_11);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_11.getResult().mSuccess);

        AddScoreRequest r1_11 = new AddScoreRequest(r_11.getResult().mID, 1200, 12);
        mComm.sendRequest(r1_11);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_11.getResult().mSuccess);


        HighscoreRequest r2 = new HighscoreRequest(0, r_6.getResult().mID);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        Highscore hscore = r2.getResult().mHighScore;
        Assert.assertEquals(8, hscore.size());
        Assert.assertEquals(new Long(6), hscore.get(5).getRank());
        Assert.assertEquals(new Long(700), hscore.get(5).getPoints());
    }

    @Test
    public void highscore6() {
        RegisterRequest r_0 = new RegisterRequest();
        mComm.sendRequest(r_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_0.getResult().mSuccess);

        AddScoreRequest r1_0 = new AddScoreRequest(r_0.getResult().mID, 100, 1);
        mComm.sendRequest(r1_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_0.getResult().mSuccess);

        RegisterRequest r_1 = new RegisterRequest();
        mComm.sendRequest(r_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_1.getResult().mSuccess);

        AddScoreRequest r1_1 = new AddScoreRequest(r_1.getResult().mID, 200, 1);
        mComm.sendRequest(r1_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_1.getResult().mSuccess);

        RegisterRequest r_2 = new RegisterRequest();
        mComm.sendRequest(r_2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_2.getResult().mSuccess);

        AddScoreRequest r1_2 = new AddScoreRequest(r_2.getResult().mID, 300, 2);
        mComm.sendRequest(r1_2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_2.getResult().mSuccess);

        RegisterRequest r_3 = new RegisterRequest();
        mComm.sendRequest(r_3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_3.getResult().mSuccess);

        AddScoreRequest r1_3 = new AddScoreRequest(r_3.getResult().mID, 400, 2);
        mComm.sendRequest(r1_3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_3.getResult().mSuccess);

        RegisterRequest r_4 = new RegisterRequest();
        mComm.sendRequest(r_4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_4.getResult().mSuccess);

        AddScoreRequest r1_4 = new AddScoreRequest(r_4.getResult().mID, 500, 2);
        mComm.sendRequest(r1_4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_4.getResult().mSuccess);

        RegisterRequest r_5 = new RegisterRequest();
        mComm.sendRequest(r_5);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_5.getResult().mSuccess);

        AddScoreRequest r1_5 = new AddScoreRequest(r_5.getResult().mID, 600, 3);
        mComm.sendRequest(r1_5);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_5.getResult().mSuccess);

        RegisterRequest r_6 = new RegisterRequest();
        mComm.sendRequest(r_6);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_6.getResult().mSuccess);

        AddScoreRequest r1_6 = new AddScoreRequest(r_6.getResult().mID, 700, 3);
        mComm.sendRequest(r1_6);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_6.getResult().mSuccess);

        RegisterRequest r_7 = new RegisterRequest();
        mComm.sendRequest(r_7);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_7.getResult().mSuccess);

        AddScoreRequest r1_7 = new AddScoreRequest(r_7.getResult().mID, 800, 3);
        mComm.sendRequest(r1_7);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_7.getResult().mSuccess);

        RegisterRequest r_8 = new RegisterRequest();
        mComm.sendRequest(r_8);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_8.getResult().mSuccess);

        AddScoreRequest r1_8 = new AddScoreRequest(r_8.getResult().mID, 900, 3);
        mComm.sendRequest(r1_8);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_8.getResult().mSuccess);

        RegisterRequest r_9 = new RegisterRequest();
        mComm.sendRequest(r_9);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_9.getResult().mSuccess);

        AddScoreRequest r1_9 = new AddScoreRequest(r_9.getResult().mID, 1000, 4);
        mComm.sendRequest(r1_9);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_9.getResult().mSuccess);

        RegisterRequest r_10 = new RegisterRequest();
        mComm.sendRequest(r_10);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_10.getResult().mSuccess);

        AddScoreRequest r1_10 = new AddScoreRequest(r_10.getResult().mID, 1100, 11);
        mComm.sendRequest(r1_10);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_10.getResult().mSuccess);

        RegisterRequest r_11 = new RegisterRequest();
        mComm.sendRequest(r_11);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_11.getResult().mSuccess);

        AddScoreRequest r1_11 = new AddScoreRequest(r_11.getResult().mID, 1200, 12);
        mComm.sendRequest(r1_11);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_11.getResult().mSuccess);

        HighscoreRequest r2 = new HighscoreRequest(1, r_2.getResult().mID);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        Highscore hscore_0 = r2.getResult().mHighScore;
        Assert.assertEquals(2, hscore_0.size());

        HighscoreRequest r3 = new HighscoreRequest(2, r_3.getResult().mID);
        mComm.sendRequest(r3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r3.getResult().mSuccess);

        Highscore hscore_1 = r3.getResult().mHighScore;
        Assert.assertEquals(3, hscore_1.size());

        HighscoreRequest r4 = new HighscoreRequest(3, r_5.getResult().mID);
        mComm.sendRequest(r4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r4.getResult().mSuccess);

        Highscore hscore_2 = r4.getResult().mHighScore;
        Assert.assertEquals(4, hscore_2.size());

        HighscoreRequest r5 = new HighscoreRequest(12, r_11.getResult().mID);
        mComm.sendRequest(r5);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r5.getResult().mSuccess);

        Highscore hscore_3 = r5.getResult().mHighScore;
        Assert.assertEquals(1, hscore_3.size());
    }

    @Test
    public void highscore7() {
        RegisterRequest r_0 = new RegisterRequest();
        mComm.sendRequest(r_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_0.getResult().mSuccess);

        AddScoreRequest r1_0 = new AddScoreRequest(r_0.getResult().mID, 100, 1);
        mComm.sendRequest(r1_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_0.getResult().mSuccess);

        RegisterRequest r_1 = new RegisterRequest();
        mComm.sendRequest(r_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_1.getResult().mSuccess);

        AddScoreRequest r1_1 = new AddScoreRequest(r_1.getResult().mID, 200, 1);
        mComm.sendRequest(r1_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_1.getResult().mSuccess);

        HighscoreRequest r2 = new HighscoreRequest(18, r_1.getResult().mID);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.JSON_ERROR, r2.getResult().mSuccess);
    }

    @Test
    public void highscore8() {
        RegisterRequest r_0 = new RegisterRequest();
        mComm.sendRequest(r_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r_0.getResult().mSuccess);

        AddScoreRequest r1_0 = new AddScoreRequest(r_0.getResult().mID, 100, 1);
        mComm.sendRequest(r1_0);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r1_0.getResult().mSuccess);

        HighscoreRequest r2 = new HighscoreRequest(0, "8862c3ca5a82c649152901b92cb6f8cd9");
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.JSON_ERROR, r2.getResult().mSuccess);
    }

}