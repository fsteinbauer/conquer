//package at.acid.conquer.test;
//
//import android.provider.Settings;
//import android.support.test.rule.ActivityTestRule;
//import android.test.suitebuilder.annotation.LargeTest;
//import android.util.Log;
//import android.view.View;
//
//import org.hamcrest.BaseMatcher;
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.hamcrest.TypeSafeMatcher;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//
//
//import at.acid.conquer.MapsActivity;
//import at.acid.conquer.R;
//import at.acid.conquer.fragments.MapFragment;
////import at.acid.conquer.util.DrawableMatcher;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.doubleClick;
//import static android.support.test.espresso.action.ViewActions.longClick;
//import static android.support.test.espresso.action.ViewActions.pressBack;
//import static android.support.test.espresso.action.ViewActions.scrollTo;
//import static android.support.test.espresso.action.ViewActions.swipeDown;
//import static android.support.test.espresso.action.ViewActions.swipeLeft;
//import static android.support.test.espresso.action.ViewActions.swipeRight;
//import static android.support.test.espresso.action.ViewActions.swipeUp;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
//import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//
//
//@LargeTest
//public class MapsTest {
//
//
//
//
//    private String mStringToBetyped;
//
//    @Rule
//    public ActivityTestRule<MapsActivity> mActivityRule = new ActivityTestRule<>(
//            MapsActivity.class);
//
//    @Before
//    public void initValidString() {
//        // Specify a valid string.
//        mStringToBetyped = "Espresso";
//    }
//
//   @Test
//   public void startRunning()
//   {
//       MapFragment mf = (MapFragment)mActivityRule.getActivity(). getSupportFragmentManager().findFragmentById(R.id.frag_map);
//       assert (mf.isRunning() == false);
//
//
//       onView(withId(R.id.fab_run_stop)).perform(click());
//
//       assert (mf.isRunning() == true);
//
//       Log.d("JUNIT","Test was successfull!");
//   }
//
//    @Test
//    public void noExceptionTest()
//    {
//        onView(withId(R.id.frag_map)).perform(doubleClick());
//
//        onView(withId(R.id.frag_map)).perform(longClick());
//        onView(withId(R.id.frag_map)).perform(pressBack());
//        onView(withId(R.id.frag_map)).perform(swipeLeft());
//        onView(withId(R.id.frag_map)).perform(scrollTo());
//        onView(withId(R.id.frag_map)).perform(swipeDown());
//        onView(withId(R.id.frag_map)).perform(swipeRight());
//        onView(withId(R.id.frag_map)).perform(swipeUp());
//        Log.d("JUNIT","Test was successfull!");
//    }
//}
