package at.acid.conquer.communication;

import junit.framework.Assert;

import org.junit.Test;

import at.acid.conquer.communication.Requests.HighscoreRequest;
import at.acid.conquer.communication.Requests.Request;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Annie on 04/05/2016.
 */
public class HighscoreRequestTest {

    
    @Test
    public void sendRegisterRequest() throws Exception {
        Communicator c = new Communicator();

        final HighscoreRequest hgR = new HighscoreRequest("f8ff4f85-2806-11e6-8f63-3f8e087f657c", -1);

        assertTrue(c.sendRequest(hgR));

        assertEquals(hgR.getmSuccess(), Request.ReturnValue.SUCCESS);

        assertNotNull(hgR.getmHighscore());

        assertEquals(hgR.getmHighscore().size(), 10);



    }

}
