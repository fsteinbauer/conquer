package at.acid.conquer.communication;

import junit.framework.Assert;

import org.junit.Test;

import at.acid.conquer.communication.Requests.HighscoreRequest;
import at.acid.conquer.communication.Requests.Request;
import at.acid.conquer.model.Highscore;
import at.acid.conquer.model.Highscore;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.*;


/**
 * Created by MiguelYuste on 11/06/2016.
 */
public class HighscoreRequestTest2 {
    @Test
    public void parseRequest(){
        String returnString =
                "{" + "success" + ":true," +
                        "highscores" + ":{" +
                        "1" + ":{" + "name" + ":" + "Johannes der L\u00e4ufer" + "," + "points" + ":2550," + "self" + ":false}," +
                        "2" + ":{" + "name" + ":" + "Johannes der L\u00e4ufer" + "," + "points" + ":1500," + "self" + ":false}," +
                        "3" + ":{" + "name" + ":" + "Johannes der L\u00e4ufer" + "," + "points" + ":850," + "self" + ":false}," +
                        "4" + ":{" + "name" + ":" + "Johannes der L\u00e4ufer" + "," + "points" + ":770," + "self" + ":false}," +
                        "5" + ":{" + "name" + ":" + "Johannes der L\u00e4ufer" + "," + "points" + ":650," + "self" + ":false}," +
                        "6" + ":{" + "name" + ":" + "Johannes der L\u00e4ufer" + "," + "points" + ":600," + "self" + ":false}," +
                        "7" + ":{" + "name" + ":" + "Johannes der L\u00e4ufer" + "," + "points" + ":600," + "self" + ":false}," +
                        "41" + ":{" + "name" + ":" + "Johannes der L\u00e4ufer" + "," + "points" + ":280," + "self" + ":false}," +
                        "42" + ":{" + "name" + ":" + "Johannes der L\u00e4ufer" + "," + "points" + ":270," + "self" + ":true}," +
                        "43" + ":{" + "name" + ":" + "Johannes der L\u00e4ufer" + "," + "points" + ":260," + "self" + ":false}" +
                        "}" +
                        "}" +
                        "}";

        HighscoreRequest req = new HighscoreRequest(1224, "1");

        HighscoreRequest.Result res = req.getResult();

        Highscore hc = res.mHgs;

        Request.ReturnValue rSuc = res.mSuccess;

        assertTrue("mSuccess shouldn't be false!", rSuc == Request.ReturnValue.SUCCESS);

        assertNotNull("Highscore should not be null!", hc);

        assertNotNull("Highscore name should not be null!", hc.get(1).getUsername());

        assertEquals("Highscore name is wrong!", hc.get(1).getUsername(), "Johannes der Läufer");

        assertNotNull("Highscore points should not be null!", hc.get(1).getPoints());

        assertEquals("Highscore points are wrong!", hc.get(1).getUsername(), 2550);

        assertNotNull("Highscore self should not be null!", hc.get(1).getSelf());

        assertTrue("Highscore self should be false!", !hc.get(1).getSelf());

        assertEquals("Success should be 1!", rSuc, 1);

        assertNotNull("Success should not be null!", rSuc);


    }
}
