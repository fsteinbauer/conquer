package at.acid.conquer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import at.acid.conquer.fragments.AccountFragment;
import at.acid.conquer.fragments.HighscoreFragment;
import at.acid.conquer.fragments.MapFragment;
import at.acid.conquer.model.User;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements TabLayout.OnTabSelectedListener {
    public static final String TAG = "MapsActivity";
    public static final int TAB_MAP = 0;
    public static final int TAB_HIGHSCORE = 1;
    public static final int TAB_ACCOUNT = 2;

    private TabLayout mTabLayout;
    private MapFragment mMapFragment;
    private HighscoreFragment mHighscoreFragment;
    private AccountFragment mAccountFragment;
    private Fragment mCurrentFragment;

    public ArrayList<String> areaNames;
    User mUser;


    @Override//-------------------------------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //?
        //Log.d(TAG, "Got to Line: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        areaNames = new ArrayList<>();

        mUser = new User(getBaseContext());
        //mUser.clearStoredData();

        initGUIElements();
    }

    //----------------------------------------------------------------------------------------------
    private void initGUIElements() {
        mMapFragment = new MapFragment();
        mHighscoreFragment = new HighscoreFragment();
        mAccountFragment = new AccountFragment();
        mCurrentFragment = mMapFragment;

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_fragment_container, mMapFragment)
                .add(R.id.fl_fragment_container, mHighscoreFragment)
                .add(R.id.fl_fragment_container, mAccountFragment)
                .hide(mHighscoreFragment)
                .hide(mAccountFragment)
                .commit();

        mTabLayout = (TabLayout) findViewById(R.id.tl_tabs);
        mTabLayout.setOnTabSelectedListener(this);
    }

    @Override//-------------------------------------------------------------------------------------
    public void onTabSelected(TabLayout.Tab tab) {
        final int position = tab.getPosition();

        switch (position) {
            case TAB_MAP:
                Log.d(TAG, "Maps clicked");
                getSupportFragmentManager().beginTransaction()
                        .hide(mCurrentFragment)
                        .show(mMapFragment)
                        .commit();
                mCurrentFragment = mMapFragment;
                break;

            case TAB_HIGHSCORE:
                Log.d(TAG, "Highscores clicked");
                getSupportFragmentManager().beginTransaction()
                        .hide(mCurrentFragment)
                        .show(mHighscoreFragment)
                        .commit();
                mCurrentFragment = mHighscoreFragment;
                break;
            case TAB_ACCOUNT:
                Log.d(TAG, "Account clicked");
                getSupportFragmentManager().beginTransaction()
                        .hide(mCurrentFragment)
                        .show(mAccountFragment)
                        .commit();
                mCurrentFragment = mAccountFragment;
                break;
        }
    }

    @Override//-------------------------------------------------------------------------------------
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override//-------------------------------------------------------------------------------------
    public void onTabReselected(TabLayout.Tab tab) {
    }

    //----------------------------------------------------------------------------------------------
    public User getUser() { return mUser; }
}
