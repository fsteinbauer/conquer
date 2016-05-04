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
                "   'success': 0,\n" +
                "   'powerups': [],\n" +
                "   'highscores': []\n" +
                "}";

        RegisterRequest rr = new RegisterRequest();

        assertTrue("Return of parseReturn was False, should be True", rr.parseReturn(returnString));

        Assert.assertEquals("Success should be 0!",rr.getSuccess(), 0);
        Assert.assertTrue("Wrong ID returned!", rr.getId() != 0);

        Assert.assertNotNull("PowerUps should not be Null!", rr.getPowerUPs());
        Assert.assertTrue("PowerUps should be empty!", rr.getPowerUPs().isEmpty());

        

        //TODO: Highscore!



    }
}
