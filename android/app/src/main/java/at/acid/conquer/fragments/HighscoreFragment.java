package at.acid.conquer.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import at.acid.conquer.MainActivity;
import at.acid.conquer.R;
import at.acid.conquer.adapter.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by trewurm
 * 04.05.2016.
 */
public class HighscoreFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private MainActivity mMainActivity;
    private Spinner mSpinnerCity;
    private ListView mLVHighscore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainActivity = ((MainActivity) getActivity());

        View rootView = inflater.inflate(R.layout.fragment_highscores, container, false);
        mSpinnerCity = (Spinner) rootView.findViewById(R.id.spinner_city);
        mLVHighscore = (ListView) rootView.findViewById(R.id.lv_highscore);

        String[] objects = {
                "Graz-Andritz",
                "Graz-Eggenberg",
                "Graz-Geidorf",
                "Graz-Goesting",
                "Graz-Gries",
                "Graz-InnereStadt",
                "Graz-Jakomini",
                "Graz-Lend",
                "Graz-Liebenau",
                "Graz-Mariatrost",
                "Graz-Peter",
                "Graz-Puntigam",
                "Graz-Ries",
                "Graz-StLeonhard",
                "Graz-Strassgang",
                "Graz-Waltendorf",
                "Graz-Wetzelsdorf"
        };

        String[] values = new String[]{"Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile"};

        final ArrayList<String> list = new ArrayList<String>(Arrays.asList(values));

        final SpinnerAdapter areaAdapter = new SpinnerAdapter(getContext());
        areaAdapter.addItems(Arrays.asList(objects));
        mSpinnerCity.setAdapter(areaAdapter);
        mSpinnerCity.setOnItemSelectedListener(this);

        final ArrayAdapter highscoreAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
        mLVHighscore.setAdapter(highscoreAdapter);


        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
