package at.acid.conquer.communication;

import junit.framework.Assert;

import org.junit.Test;

import at.acid.conquer.communication.Requests.AddScoreRequest;
import at.acid.conquer.communication.Requests.HighscoreRequest;
import at.acid.conquer.communication.Requests.RegisterRequest;
import at.acid.conquer.communication.Requests.Request;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Annie on 04/05/2016.
 */
public class AddScoreRequestTest {


    @Test
    public void sendRegisterRequest() throws Exception {
        Communicator c = new Communicator();


        final RegisterRequest rr = new RegisterRequest();

        c.sendRequest(rr);

        Assert.assertEquals(rr.getResult().mSuccess, Request.ReturnValue.SUCCESS);


        final AddScoreRequest asr = new AddScoreRequest(rr.getResult().mID, 0, 1 );

        c.sendRequest(asr);

        assertEquals(asr.getResult().mSuccess, Request.ReturnValue.SUCCESS);


    }

    @Test
    public void FaultysendRegisterRequest() throws Exception {
        Communicator c = new Communicator();


        final RegisterRequest rr = new RegisterRequest();

        c.sendRequest(rr);

        Assert.assertEquals( Request.ReturnValue.SUCCESS,rr.getResult().mSuccess);


        final AddScoreRequest asr = new AddScoreRequest(rr.getResult().mID, 0, 0 );

        c.sendRequest(asr);

        assertEquals( Request.ReturnValue.DATABASE_ERROR, asr.getResult().mSuccess);


    }


}
