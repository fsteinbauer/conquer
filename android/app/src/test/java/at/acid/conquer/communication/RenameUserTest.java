package at.acid.conquer.communication;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

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
public class RenameUserTest implements Communicator.CummunicatorClient {
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
    public void renameUser0() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();

        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        RenameRequest r2 = new RenameRequest(r.getResult().mID,"Testperson");
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        HighscoreRequest r3 = new HighscoreRequest(0,r.getResult().mID);
        mComm.sendRequest(r3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r3.getResult().mSuccess);

        Highscore hscore = r3.getResult().mHighScore;
        Assert.assertEquals(1, hscore.size());
        Assert.assertEquals("Testperson", hscore.get(0).getUsername());
    }

    @Test
    public void renameUser1() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();

        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        RenameRequest r2 = new RenameRequest(r.getResult().mID,"Max Mustermann");
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        HighscoreRequest r3 = new HighscoreRequest(0,r.getResult().mID);
        mComm.sendRequest(r3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r3.getResult().mSuccess);

        Highscore hscore = r3.getResult().mHighScore;
        Assert.assertEquals(1, hscore.size());
        Assert.assertEquals("Max Mustermann", hscore.get(0).getUsername());
    }

    @Test
    public void renameUser2() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();

        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        RenameRequest r2 = new RenameRequest(r.getResult().mID,"Grätchen");
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        HighscoreRequest r3 = new HighscoreRequest(0,r.getResult().mID);
        mComm.sendRequest(r3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r3.getResult().mSuccess);

        Highscore hscore = r3.getResult().mHighScore;
        Assert.assertEquals(1, hscore.size());
        Assert.assertEquals("Grätchen", hscore.get(0).getUsername());
    }

    @Test
    public void renameUser3() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();

        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        RenameRequest r2 = new RenameRequest(r.getResult().mID,"Läufer1");
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        HighscoreRequest r3 = new HighscoreRequest(0,r.getResult().mID);
        mComm.sendRequest(r3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r3.getResult().mSuccess);

        Highscore hscore = r3.getResult().mHighScore;
        Assert.assertEquals(1, hscore.size());
        Assert.assertEquals("Läufer1", hscore.get(0).getUsername());
    }

    @Test
    // space before name
    public void renameUser4() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();

        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        RenameRequest r2 = new RenameRequest(r.getResult().mID," Schnecke");
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        HighscoreRequest r3 = new HighscoreRequest(0,r.getResult().mID);
        mComm.sendRequest(r3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r3.getResult().mSuccess);

        Highscore hscore = r3.getResult().mHighScore;
        Assert.assertEquals(1, hscore.size());
        Assert.assertEquals("Schnecke", hscore.get(0).getUsername());
    }


    @Test
    // space after name
    public void renameUser5() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();

        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        RenameRequest r2 = new RenameRequest(r.getResult().mID,"Lahm ");
        mComm.sendRequest(r2);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r2.getResult().mSuccess);

        HighscoreRequest r3 = new HighscoreRequest(0,r.getResult().mID);
        mComm.sendRequest(r3);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r3.getResult().mSuccess);

        Highscore hscore = r3.getResult().mHighScore;
        Assert.assertEquals(1, hscore.size());
        Assert.assertEquals("Lahm", hscore.get(0).getUsername());
    }
}
