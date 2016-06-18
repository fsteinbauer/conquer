package at.acid.conquer.communication;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import at.acid.conquer.Pair;
import at.acid.conquer.communication.Requests.AddScoreRequest;
import at.acid.conquer.communication.Requests.ClearDataRequest;
import at.acid.conquer.communication.Requests.RegisterRequest;
import at.acid.conquer.communication.Requests.Request;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Annie on 04/05/2016.
 */
public class GeneralRequestUsageTest implements Communicator.CummunicatorClient {
    Communicator mComm;

    final static int NUM_USERS = 10;

    int mFinishedRequests = 0;

    List<Pair<String, String>> mUsers = new ArrayList<Pair<String, String>>();

    @Override
    public void onRequestReady(Request r) {
        mFinishedRequests++;

        if(r instanceof RegisterRequest){
            RegisterRequest.Result result = ((RegisterRequest) r).getResult();
            mUsers.add(new Pair<String, String>(result.mID, result.mName));
        }
    }

    @Override
    public void onRequestTimeOut(Request r) {

    }

    @Override
    public void onRequestError(Request r) {

    }

    @Before
    public void init(){
        mComm = new Communicator(this, "http://conquer2.menzi.at/");
    }

    @Test
    public void clearData() {
        ClearDataRequest r = new ClearDataRequest();
        mComm.sendRequest(r);
        mComm.waitForResponse();
        Assert.assertEquals(r.getResult().mSuccess, Request.ReturnValue.SUCCESS);
    }

    @Test
    public void registerUsers() throws Exception {
        List<Thread> threads = new ArrayList<>();
        List<RegisterRequest> requests = new ArrayList<>();

        for(int i = 0; i < NUM_USERS; i++) {
            RegisterRequest r = new RegisterRequest();
            threads.add(mComm.sendRequest(r));
            requests.add(r);
        }

        for(Thread thread : threads) {
            thread.join();
        }

        for(RegisterRequest request : requests) {
            Assert.assertEquals(request.getResult().mSuccess, Request.ReturnValue.SUCCESS);
        }

        Assert.assertEquals(mFinishedRequests, 10);

    }


}
