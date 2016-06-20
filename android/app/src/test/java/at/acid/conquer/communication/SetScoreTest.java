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
import at.acid.conquer.communication.Requests.SetScoreRequest;
import at.acid.conquer.model.Highscore;

/**
 * Created by Philipp on 20/06/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SetScoreTest implements Communicator.CummunicatorClient {
    Communicator mComm;

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

    // replace existing score
    @Test
    public void setScore0() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        AddScoreRequest r2 = new AddScoreRequest(r.getResult().mID, 100, 1);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        SetScoreRequest r3 = new SetScoreRequest(r.getResult().mID, 200, 1);
        mComm.sendRequest(r3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r3.getResult().mSuccess);

        HighscoreRequest r4 = new HighscoreRequest(0,r.getResult().mID);
        mComm.sendRequest(r4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r4.getResult().mSuccess);

        Highscore hscore = r4.getResult().mHighScore;
        Assert.assertEquals(new Long(200), hscore.get(0).getPoints());
    }

    // set score to not existing user
    @Test
    public void setScore1() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        SetScoreRequest r2 = new SetScoreRequest("0002c3ca5a82c49152901b92cb6f8cd9", 100, 1);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.DATABASE_ERROR, r2.getResult().mSuccess);
    }

    // set score at different areas
    @Test
    public void setScore2() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        SetScoreRequest r2 = new SetScoreRequest(r.getResult().mID, 100, 2);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        SetScoreRequest r3 = new SetScoreRequest(r.getResult().mID, 100, 3);
        mComm.sendRequest(r3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r3.getResult().mSuccess);

        HighscoreRequest r4 = new HighscoreRequest(0,r.getResult().mID);
        mComm.sendRequest(r4);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r4.getResult().mSuccess);

        Highscore hscore = r4.getResult().mHighScore;
        Assert.assertEquals(new Long(200), hscore.get(0).getPoints());
    }

    // set score in not existing area
    @Test
    public void setScore3() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        SetScoreRequest r2 = new SetScoreRequest(r.getResult().mID, 100, 18);
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.DATABASE_ERROR, r2.getResult().mSuccess);
    }
}
