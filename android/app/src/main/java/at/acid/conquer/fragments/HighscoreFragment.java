package at.acid.conquer.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.acid.conquer.MainActivity;
import at.acid.conquer.R;

/**
 * Created by trewurm
 * 04.05.2016.
 */
public class HighscoreFragment extends Fragment {
    private MainActivity mMainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainActivity = ((MainActivity) getActivity());
        return inflater.inflate(R.layout.fragment_highscores, container, false);
    }
}
