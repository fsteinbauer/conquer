package at.acid.conquer;

import android.support.design.widget.TabLayout;
import android.support.test.espresso.action.ScrollToAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Spinner;
import at.acid.conquer.fragments.AccountFragment;
import at.acid.conquer.fragments.HighscoreFragment;
import at.acid.conquer.fragments.MapFragment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.*;

/**
 * Created by Trey
 * Created on 20.06.2016
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest{

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void changeTabs(){
        TabLayout tabLayout = (TabLayout) mActivityRule.getActivity().findViewById(R.id.tl_tabs);

        assertTrue(mActivityRule.getActivity().mCurrentFragment instanceof MapFragment);

        TabLayout.Tab tab = tabLayout.getTabAt(1);
        mActivityRule.getActivity().onTabSelected(tab);
        assertTrue(mActivityRule.getActivity().mCurrentFragment instanceof HighscoreFragment);

        tab = tabLayout.getTabAt(2);
        mActivityRule.getActivity().onTabSelected(tab);
        assertTrue(mActivityRule.getActivity().mCurrentFragment instanceof AccountFragment);
    }

    @Test
    public void mapActions(){
        assertTrue(mActivityRule.getActivity().mCurrentFragment instanceof MapFragment);
        assertFalse(((MapFragment) mActivityRule.getActivity().mCurrentFragment).ismIsRunning());
        onView(withId(R.id.fab_run_stop)).perform(click());
        assertTrue(((MapFragment) mActivityRule.getActivity().mCurrentFragment).ismIsRunning());
    }

    @Test
    public void highscoreActions() throws  Exception{
        TabLayout tabLayout = (TabLayout) mActivityRule.getActivity().findViewById(R.id.tl_tabs);
        TabLayout.Tab tab = tabLayout.getTabAt(1);

        mActivityRule.getActivity().onTabSelected(tab);
        assertTrue(mActivityRule.getActivity().mCurrentFragment instanceof HighscoreFragment);

        onView(withId(R.id.spinner_city)).perform(click());

        onView(withText("Graz")).perform(swipeUp());
        onView(withText("Graz - Gries")).perform(click());

        Spinner spinner = (Spinner)mActivityRule.getActivity().findViewById(R.id.spinner_city);
        String text = spinner.getSelectedItem().toString();
        assertEquals("Graz - Gries", text);
    }

    @Test
    public void accountActions(){
        TabLayout tabLayout = (TabLayout) mActivityRule.getActivity().findViewById(R.id.tl_tabs);
        TabLayout.Tab tab = tabLayout.getTabAt(2);

        mActivityRule.getActivity().onTabSelected(tab);
        assertTrue(mActivityRule.getActivity().mCurrentFragment instanceof AccountFragment);

        onView(withId(R.id.tv_profile_name)).perform(click());
        onView(withId(R.id.et_profile_name)).perform(clearText());
        onView(withId(R.id.et_profile_name)).perform(typeText("NeuerName"));
        onView(withId(R.id.ib_name_edit)).perform(click());
        onView(withId(R.id.tv_profile_name)).check(matches(withText("NeuerName")));
    }
}
