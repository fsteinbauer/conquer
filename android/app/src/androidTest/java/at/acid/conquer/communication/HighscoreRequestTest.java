package at.acid.conquer.communication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.acid.conquer.MainActivity;
import at.acid.conquer.adapter.RankingAdapter;
import at.acid.conquer.communication.Requests.ClearDataRequest;
import at.acid.conquer.communication.Requests.HighscoreRequest;
import at.acid.conquer.communication.Requests.RegisterRequest;
import at.acid.conquer.communication.Requests.Request;
import at.acid.conquer.fragments.AccountFragment;
import at.acid.conquer.fragments.HighscoreFragment;
import at.acid.conquer.model.User;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Annie on 04/05/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class HighscoreRequestTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    private Communicator c = new Communicator(new Communicator.CummunicatorClient() {
        @Override
        public void onRequestReady(Request r) {

        }

        @Override
        public void onRequestTimeOut(Request r) {

        }

        @Override
        public void onRequestError(Request r) {

        }
    },"http://conquer2.menzi.at/");



    private MainActivity mActivity;
    @Before
    public void prepareDatabase() throws Exception
    {
        ClearDataRequest cdr = new ClearDataRequest();
        c.sendRequest(cdr);
        Thread.sleep(3000);
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void sendHighscoreRequest() throws Exception {



        User user = new User(mActivity.getApplicationContext());

        AccountFragment ac = mActivity.getmAccountFragment();



        ac.setUser(user);

        ac.registerUser();


        Thread.sleep(5000);

        


//
    }


}
