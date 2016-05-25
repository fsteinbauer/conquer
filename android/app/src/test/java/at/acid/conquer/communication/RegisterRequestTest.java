package at.acid.conquer.communication;

import junit.framework.Assert;

import org.junit.Test;

import at.acid.conquer.communication.Requests.RegisterRequest;
import at.acid.conquer.model.Area;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Annie on 04/05/2016.
 */
public class RegisterRequestTest {


    @Test
    public void ParseVaildReturnString1() throws Exception {
        String returnString =
                "{\n" +
                "   'user': {'id':'ABCD-FGH-YZ', 'name':'halloWelt!'},\n" +
                "   'powerups': [],\n" +
                "   'highscore': []\n" +
                "}";

        RegisterRequest rr = new RegisterRequest(0.5, 0.5);

        assertTrue("Return of parseReturn was False, should be True", rr.parseReturn(returnString));
        RegisterRequest.Result res = rr.getResult();

        Assert.assertNotNull(res);

        Assert.assertEquals("Success should be 0!",res.mSuccess, 0);
        Assert.assertTrue("Wrong ID returned!", res.mID.equals("ABCD-FGH-YZ"));

        Assert.assertNotNull("PowerUps should not be Null!", res.mPowerUPs);
        Assert.assertTrue("PowerUps should be empty!", res.mPowerUPs.isEmpty());

    }

    @Test
    public void ParsePowerUpsAndHighScore() throws Exception {
        String returnString =
                "{\n" +
                        "   'user': {'id':'ABCD-FGH-YZ', 'name':'halloWelt!'},\n" +
                        "   'powerups': [],\n" +
                        "   'highscore': []\n" +
                        "}";

        RegisterRequest rr = new RegisterRequest(0.5, 0.5);

        assertTrue("Return of parseReturn was False, should be True", rr.parseReturn(returnString));
        RegisterRequest.Result res = rr.getResult();

        Assert.assertNotNull(res);

        Assert.assertEquals("Success should be 0!",res.mSuccess, 0);
        Assert.assertTrue("Wrong ID returned!", res.mID.equals("ABCD-FGH-YZ"));

        Assert.assertNotNull("PowerUps should not be Null!", res.mPowerUPs);
        Assert.assertTrue("PowerUps should be empty!", res.mPowerUPs.isEmpty());

    }
}
