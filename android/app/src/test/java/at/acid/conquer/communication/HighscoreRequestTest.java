//package at.acid.conquer.communication;
//
//import junit.framework.Assert;
//
//import org.junit.Test;
//
//import at.acid.conquer.communication.Requests.HighscoreRequest;
//import at.acid.conquer.communication.Requests.RegisterRequest;
//import at.acid.conquer.communication.Requests.Request;
//
//import static junit.framework.Assert.assertEquals;
//import static junit.framework.Assert.assertNotNull;
//import static junit.framework.Assert.assertTrue;
//
///**
// * Created by Annie on 04/05/2016.
// */
//public class HighscoreRequestTest {
//
//
//    @Test
//    public void sendHighscoreRequest() throws Exception {
//        Communicator c = new Communicator("http://conquer2.menzi.at/");
//
//
//        final RegisterRequest rr = new RegisterRequest();
//
//        c.sendRequest(rr);
//
//        Assert.assertEquals(Request.ReturnValue.SUCCESS, rr.getResult().mSuccess);
//
//
//        final HighscoreRequest hgR = new HighscoreRequest(0,rr.getResult().mID );
//
//        c.sendRequest(hgR);
//
//        assertEquals(Request.ReturnValue.SUCCESS, hgR.getResult().mSuccess);
//
//    }
//
//}
