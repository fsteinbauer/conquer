package at.acid.conquer.communication;

import android.util.Log;

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

import static junit.framework.Assert.assertEquals;

/**
 * Created by Menzi on 18/06/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GeneralRequestUsageTest implements Communicator.CummunicatorClient {
    Communicator mComm;

    final static int NUM_USERS = 20;

    int mFinishedRequests = 0;

    static List<String> mUsers = new ArrayList<>();

    @Override
    public void onRequestReady(Request r) {
        mFinishedRequests++;
        //System.out.println("onRequestReady: " + r.getClass().getName());
        if(r instanceof RegisterRequest){
            RegisterRequest.Result result = ((RegisterRequest) r).getResult();
            mUsers.add(result.mID);
        }
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
    }

    @Test
    public void aClearData() {
        ClearDataRequest r = new ClearDataRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);
    }

    @Test
    public void bRegisterUsers() throws Exception {
        List<Thread> threads = new ArrayList<>();
        List<RegisterRequest> requests = new ArrayList<>();
        mFinishedRequests = 0;

        for(int i = 0; i < NUM_USERS; i++) {
            RegisterRequest r = new RegisterRequest();
            threads.add(mComm.sendRequest(r));
            requests.add(r);
        }

        for(Thread thread : threads) {
            thread.join();
        }

        for(RegisterRequest request : requests) {
            Assert.assertEquals(Request.ReturnValue.SUCCESS, request.getResult().mSuccess);
        }

        Assert.assertEquals(NUM_USERS, mFinishedRequests);
    }

    @Test
    public void cRenameUsers() throws Exception {
        List<Thread> threads = new ArrayList<>();
        List<RenameRequest> requests = new ArrayList<>();
        mFinishedRequests = 0;

        int id = 1;
        for(String user : mUsers) {
            RenameRequest r = new RenameRequest(user, "User_" + id);
            id++;
            threads.add(mComm.sendRequest(r));
            requests.add(r);
        }

        for(Thread thread : threads) {
            thread.join();
        }

        for(RenameRequest request : requests) {
            Assert.assertEquals(Request.ReturnValue.SUCCESS, request.getResult().mSuccess);
        }

        Assert.assertEquals(NUM_USERS, mFinishedRequests);
    }

    @Test
    public void dAddScore() throws Exception {
        List<Thread> threads = new ArrayList<>();
        List<AddScoreRequest> requests = new ArrayList<>();
        mFinishedRequests = 0;

        int area = 6;
        int points = 1;
        for(String user : mUsers) {
            AddScoreRequest r = new AddScoreRequest(user, points, area);
            points *= 2;
            threads.add(mComm.sendRequest(r));
            requests.add(r);
        }

        for(Thread thread : threads) {
            thread.join();
        }

        for(AddScoreRequest request : requests) {
            Assert.assertEquals(Request.ReturnValue.SUCCESS, request.getResult().mSuccess);
        }

        Assert.assertEquals(NUM_USERS, mFinishedRequests);
    }

    @Test
    public void eHighscore() throws Exception {
        List<Thread> threads = new ArrayList<>();
        List<AddScoreRequest> requests = new ArrayList<>();
        mFinishedRequests = 0;

        int area = 6;
        String user = mUsers.get(NUM_USERS-7-1);

        HighscoreRequest r = new HighscoreRequest(area, user);
        mComm.sendRequest(r);
        mComm.waitForResponse();

        Assert.assertEquals(Request.ReturnValue.SUCCESS, r.getResult().mSuccess);

        Highscore highscore = r.getResult().mHighScore;

        Long rank = new Long(1);
        Long id = new Long(NUM_USERS);
        Long points = new Long((long)Math.pow(2, NUM_USERS-1));
        for( Highscore.HighscoreUser score : highscore ){
            Assert.assertEquals(rank, score.getRank());
            Assert.assertEquals(points, score.getPoints());
            Assert.assertEquals("User_" + id, score.getUsername());
            System.out.println(id + " - " + score.getSelf());
            Assert.assertEquals(id == NUM_USERS-7, score.getSelf());
            rank++;
            id--;
            points /= 2;
        }

        Assert.assertEquals(1, mFinishedRequests);
    }
}
