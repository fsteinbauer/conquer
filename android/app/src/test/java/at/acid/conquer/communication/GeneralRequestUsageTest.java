package at.acid.conquer.communication;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import at.acid.conquer.communication.Requests.AddScoreRequest;
import at.acid.conquer.communication.Requests.ClearDataRequest;
import at.acid.conquer.communication.Requests.RegisterRequest;
import at.acid.conquer.communication.Requests.Request;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Annie on 04/05/2016.
 */
public class GeneralRequestUsageTest {
    Communicator mComm;

    final static int NUM_USERS = 10;

    @Before
    public void init(){
        mComm = new Communicator("http://conquer2.menzi.at/");
    }

    @Test
    public void clearData() {
        ClearDataRequest r = new ClearDataRequest();
        mComm.sendRequest(r);

        Assert.assertEquals(r.getResult().mSuccess, Request.ReturnValue.SUCCESS);
    }

    @Test
    public void registerUsers() {
        RegisterRequest r = new RegisterRequest();

        for(int i = 0; i < NUM_USERS; i++) {
            mComm.sendRequest(r);

            Assert.assertEquals(r.getResult().mSuccess, Request.ReturnValue.SUCCESS);
        }
    }
}
