package at.acid.conquer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import at.acid.conquer.MainActivity;
import at.acid.conquer.Pair;
import at.acid.conquer.R;
import at.acid.conquer.adapter.RankingAdapter;
import at.acid.conquer.adapter.SpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by trewurm
 * 04.05.2016.
 */
public class HighscoreFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private SpinnerAdapter mAreaAdapter;
    private RankingAdapter mHighscoreAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_highscores, container, false);
        Spinner spinnerCity = (Spinner) rootView.findViewById(R.id.spinner_city);
        ListView listviewHighscore = (ListView) rootView.findViewById(R.id.lv_highscore);
        TextView tvEmptyHighscore = (TextView) rootView.findViewById(R.id.tv_empty_highscore);

        String[] names = {"Robena", "Isela", "Jake", "Margarete", "Hyo", "Yael", "Winnifred", "Kimberely", "Arleen", "Merilyn", "Vergie", "Isidro", "Sixta", "Harriett", "Alden", "Mai", "Lara", "Romelia", "Golden", "Nancy"};

        mAreaAdapter = new SpinnerAdapter(getContext());

        List<String> areaNames = ((MainActivity) getActivity()).areaNames;

        mAreaAdapter.addItems(areaNames);
        spinnerCity.setAdapter(mAreaAdapter);
        spinnerCity.setOnItemSelectedListener(this);

        mHighscoreAdapter = new RankingAdapter(getContext());


        HashMap<String, List<Pair>> map = new HashMap<>();

        Random randomGenerator = new Random();
        Pair<String, String> pair;

        for(String area : areaNames){
            List<Pair> pairs = new ArrayList<>();
            for(int idx = 1; idx <= 35; ++idx){
                int randomInt = randomGenerator.nextInt(100000);
                if(idx == 1){
                    pair = new Pair<>("asdfasdfhljkasdhfjklhasjdkfhljkashdfjlhasljkdhflöasjdölkfjköalsdjfköljasdköfjöklasdjfökjasdfökljasdöklfj", Integer.toString(randomInt));
                    pairs.add(pair);

                } else{
                    int rand = randomGenerator.nextInt(20);
                    pair = new Pair<>(names[rand], Integer.toString(randomInt));
                    pairs.add(pair);
                }
            }
            map.put(area, pairs);
        }

        mHighscoreAdapter.addAll(map);
        listviewHighscore.setAdapter(mHighscoreAdapter);
        listviewHighscore.setEmptyView(tvEmptyHighscore);


        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        mHighscoreAdapter.setCurrentArea(mAreaAdapter.getItem(position));
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }
}
