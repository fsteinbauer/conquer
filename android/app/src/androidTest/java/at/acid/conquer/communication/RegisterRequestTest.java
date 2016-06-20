package at.acid.conquer.communication;

import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.LargeTest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import at.acid.conquer.MainActivity;
import at.acid.conquer.communication.Communicator;
import at.acid.conquer.communication.Requests.ClearDataRequest;
import at.acid.conquer.communication.Requests.RegisterRequest;
import at.acid.conquer.communication.Requests.Request;
import at.acid.conquer.fragments.AccountFragment;
import at.acid.conquer.model.User;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Annie on 04/05/2016.
 */
@LargeTest
public class RegisterRequestTest {


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


    }

    @Test
   public void sendRegisterRequest() throws Exception
   {

       mActivity = mActivityRule.getActivity();
       User user = new User(mActivity.getApplicationContext());

       user.clearStoredData();

       AccountFragment ac = mActivity.getmAccountFragment();

       ac.setUser(user);

       ac.registerUser();

       Thread.sleep(5000);

       Assert.assertEquals("John Doe", user.getName());

       Assert.assertNotNull(user.getId());
       Assert.assertTrue(!user.getId().isEmpty());
   }

}
