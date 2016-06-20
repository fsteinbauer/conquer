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
public class RegisterUserTest implements Communicator.CummunicatorClient {
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

    // create new user
    @Test
    public void registerUser() {
        RegisterRequest r = new RegisterRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();

        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);
        Assert.assertEquals("John Doe", r.getResult().mName);
        Assert.assertEquals(32, r.getResult().mID.length());
    }

}
