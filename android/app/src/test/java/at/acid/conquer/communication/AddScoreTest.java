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
import at.acid.conquer.communication.Requests.RenameRequest;
import at.acid.conquer.communication.Requests.Request;
import at.acid.conquer.model.Highscore;

/**
 * Created by Philipp on 20/06/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddScoreTest implements Communicator.CummunicatorClient {
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
    public void init(){

        mComm = new Communicator(this, "http://conquer2.menzi.at/");

        ClearDataRequest r = new ClearDataRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
    }

    @Test
    public void addScore0() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        AddScoreRequest r2 = new AddScoreRequest(r.getResult().mID, 100, 1);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        HighscoreRequest r3 = new HighscoreRequest(0,r.getResult().mID);
        mComm.sendRequest(r3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r3.getResult().mSuccess);

        Highscore hscore = r3.getResult().mHighScore;
        Assert.assertEquals(1, hscore.size());
        Assert.assertEquals(new Long(100), hscore.get(0).getPoints());
    }

    @Test
    public void addScore1() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        AddScoreRequest r2 = new AddScoreRequest(r.getResult().mID, 100, 1);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        AddScoreRequest r2_1 = new AddScoreRequest(r.getResult().mID, 200, 2);
        mComm.sendRequest(r2_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        HighscoreRequest r3 = new HighscoreRequest(0,r.getResult().mID);
        mComm.sendRequest(r3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r3.getResult().mSuccess);

        Highscore hscore = r3.getResult().mHighScore;
        Assert.assertEquals(new Long(300), hscore.get(0).getPoints());
    }

    @Test
    public void addScore2() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        AddScoreRequest r2 = new AddScoreRequest(r.getResult().mID, 100, 2);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        AddScoreRequest r2_1 = new AddScoreRequest(r.getResult().mID, 200, 2);
        mComm.sendRequest(r2_1);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        HighscoreRequest r3 = new HighscoreRequest(2,r.getResult().mID);
        mComm.sendRequest(r3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r3.getResult().mSuccess);

        Highscore hscore = r3.getResult().mHighScore;
        Assert.assertEquals(new Long(300), hscore.get(0).getPoints());
    }

    @Test
    public void addScore3() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        AddScoreRequest r2 = new AddScoreRequest(r.getResult().mID, 1000111000, 17);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        HighscoreRequest r3 = new HighscoreRequest(0,r.getResult().mID);
        mComm.sendRequest(r3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r3.getResult().mSuccess);

        Highscore hscore = r3.getResult().mHighScore;
        Assert.assertEquals(new Long(1000111000), hscore.get(0).getPoints());
    }

    @Test
    public void addScore4() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        AddScoreRequest r2 = new AddScoreRequest(r.getResult().mID, 2000111000, 18);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.DATABASE_ERROR, r2.getResult().mSuccess);
    }

    @Test
    public void addScore5() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        AddScoreRequest r2 = new AddScoreRequest(r.getResult().mID, 0, 1);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        HighscoreRequest r3 = new HighscoreRequest(1,r.getResult().mID);
        mComm.sendRequest(r3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r3.getResult().mSuccess);

        Highscore hscore = r3.getResult().mHighScore;
        Assert.assertEquals(new Long(0), hscore.get(0).getPoints());
    }

    @Test
    public void addScore6() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        HighscoreRequest r3 = new HighscoreRequest(0,r.getResult().mID);
        mComm.sendRequest(r3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r3.getResult().mSuccess);

        Highscore hscore = r3.getResult().mHighScore;
        Assert.assertEquals(new Long(0), hscore.get(0).getPoints());
    }

    @Test
    public void addScore7() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        AddScoreRequest r2 = new AddScoreRequest(r.getResult().mID, 500, 1);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.DATABASE_ERROR, r2.getResult().mSuccess);
    }
}
