package at.acid.conquer.communication;

import android.support.design.widget.TabLayout;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.test.suitebuilder.annotation.LargeTest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.acid.conquer.MainActivity;
import at.acid.conquer.R;
import at.acid.conquer.adapter.RankingAdapter;
import at.acid.conquer.communication.Requests.ClearDataRequest;
import at.acid.conquer.communication.Requests.HighscoreRequest;
import at.acid.conquer.communication.Requests.RegisterRequest;
import at.acid.conquer.communication.Requests.Request;
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


    @Before
    public void prepareDatabase() throws Exception
    {
        ClearDataRequest cdr = new ClearDataRequest();
        c.sendRequest(cdr);
        Thread.sleep(3000);
    }

    @Test
    public void sendHighscoreRequest() throws Exception {

        TabLayout tl = (TabLayout) mActivityRule.getActivity().findViewById(R.id.tl_tabs);

        mActivityRule.getActivity().onTabSelected(tl.getTabAt(1));

        Thread.sleep(500);



        User user = new User(mActivityRule.getActivity().getApplicationContext());
        final RegisterRequest rr = new RegisterRequest();

        c.sendRequest(rr);
        Thread.sleep(3000);

        Assert.assertEquals(Request.ReturnValue.SUCCESS, rr.getResult().mSuccess);


        HighscoreFragment hf = null;

        for (Fragment f : mActivityRule.getActivity().getSupportFragmentManager().getFragments()) {
            if (f instanceof HighscoreFragment) {
                hf = (HighscoreFragment) f;
            }
        }

        assertNotNull(hf);


        HighscoreRequest hgR = new HighscoreRequest(0, user.getId());

        c.sendRequest(hgR);

        Thread.sleep(3000);

        assertEquals(Request.ReturnValue.SUCCESS, hgR.getResult().mReturn);

        assertTrue(hf.getRanking().getCurrentRank() != null);
//
    }


}
