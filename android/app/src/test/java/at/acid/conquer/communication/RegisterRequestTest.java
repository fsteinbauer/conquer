package at.acid.conquer.communication;

import junit.framework.Assert;

import org.junit.Test;

import at.acid.conquer.communication.Requests.RegisterRequest;
import at.acid.conquer.communication.Requests.Request;
import at.acid.conquer.model.Area;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Annie on 04/05/2016.
 */
public class RegisterRequestTest {



    @Test
    public void sendRegisterRequest() throws Exception {
        Communicator c = new Communicator();

        final RegisterRequest rr = new RegisterRequest();
        c.sendRequest(rr);

        Assert.assertEquals(rr.getResult().mSuccess, Request.ReturnValue.SUCCESS);

        Assert.assertNotNull(rr.getResult().mID);
        Assert.assertTrue(!rr.getResult().mID.isEmpty());

        Assert.assertNotNull(rr.getResult().mName);
        Assert.assertTrue(!rr.getResult().mName.isEmpty());


    }


    @Test
    public void ParseVaildReturnString1() throws Exception {
        String returnString =
                " {'id':'ABCD-FGH-YZ', 'name':'halloWelt!'}";

        RegisterRequest rr = new RegisterRequest();

      rr.parseReturn(returnString);
        RegisterRequest.Result res = rr.getResult();

        Assert.assertNotNull(res);

        Assert.assertEquals("Success should be 0!", Request.ReturnValue.SUCCESS, res.mSuccess );
        Assert.assertTrue("Wrong ID returned!", res.mID.equals("ABCD-FGH-YZ"));

       Assert.assertEquals("Wrong name!", res.mName, "halloWelt!");

    }


}
