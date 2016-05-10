package at.acid.conquer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import at.acid.conquer.fragments.AccountFragment;
import at.acid.conquer.fragments.HighscoreFragment;
import at.acid.conquer.fragments.MapFragment;


public class MapsActivity extends FragmentActivity implements TabLayout.OnTabSelectedListener {
    public static final String TAG = "MapsActivity";
    public final static int TAB_MAP = 0;
    public final static int TAB_HIGHSCORE = 1;
    public final static int TAB_ACCOUNT = 2;

    private TabLayout mTabLayout;

    @Override//-------------------------------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.d(TAG, "Got to Line: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        initGUIElements();
    }

    private void initGUIElements() {
        MapFragment mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_fragment_container, mapFragment).commit();
        mTabLayout = (TabLayout) findViewById(R.id.tl_tabs);
        mTabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        final int position = tab.getPosition();
        switch (position) {
            case TAB_MAP:
                Log.d(TAG, "Maps clicked");
                MapFragment mapFragment = new MapFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, mapFragment).commit();
                break;

            case TAB_HIGHSCORE:
                Log.d(TAG, "Highscores clicked");
                HighscoreFragment highscoreFragment = new HighscoreFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, highscoreFragment).commit();
                break;
            case TAB_ACCOUNT:
                Log.d(TAG, "Account clicked");
                AccountFragment accountFragment = new AccountFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, accountFragment).commit();
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }
}
