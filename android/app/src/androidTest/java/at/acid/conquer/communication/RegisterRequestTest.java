package at.acid.conquer.communication;

import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.LargeTest;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;

import at.acid.conquer.MainActivity;
import at.acid.conquer.communication.Communicator;
import at.acid.conquer.communication.Requests.RegisterRequest;
import at.acid.conquer.communication.Requests.Request;
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


   @Test
   public void sendRegisterRequest() throws Exception
   {
       MainActivity mActivity = mActivityRule.getActivity();
       Communicator c = new Communicator("http://conquer2.menzi.at/");

       User user = new User(mActivity.getApplicationContext());

       final RegisterRequest rr = new RegisterRequest(user);
       c.sendRequest(rr);

       Thread.sleep(3000);


       Assert.assertNotNull(user.getId());
       Assert.assertTrue(!user.getId().isEmpty());
       Assert.assertNotNull(user.getName());
       Assert.assertEquals("John Doe", user.getName());

   }

}
