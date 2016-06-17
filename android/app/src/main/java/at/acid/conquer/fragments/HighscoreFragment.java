package at.acid.conquer.fragments;

import android.os.Bundle;
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
import at.acid.conquer.model.Highscore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by trewurm
 * 04.05.2016.
 */
public class HighscoreFragment extends BaseClass implements AdapterView.OnItemSelectedListener{

    private SpinnerAdapter mAreaAdapter;
    private RankingAdapter mHighscoreAdapter;
    private TextView mTVCurrentRank;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_highscores, container, false);
        Spinner spinnerCity = (Spinner) rootView.findViewById(R.id.spinner_city);
        ListView listviewHighscore = (ListView) rootView.findViewById(R.id.lv_highscore);
        TextView tvEmptyHighscore = (TextView) rootView.findViewById(R.id.tv_empty_highscore);
        mTVCurrentRank = (TextView) rootView.findViewById(R.id.tv_current_ranking);

        mAreaAdapter = new SpinnerAdapter(getContext());

        List<String> areaNames = ((MainActivity) getActivity()).areaNames;
        mAreaAdapter.addItem("Graz");
        mAreaAdapter.addItems(areaNames);
        spinnerCity.setAdapter(mAreaAdapter);
        spinnerCity.setOnItemSelectedListener(this);

        mHighscoreAdapter = new RankingAdapter(getContext(),((MainActivity) getActivity()).getUser());

       setCurrentRank(mHighscoreAdapter.getCurrentRank());



        //// TODO: remove this line if dummy data are not needed
        //mHighscoreAdapter.addAll(createDummyData(areaNames));

        listviewHighscore.setAdapter(mHighscoreAdapter);

        listviewHighscore.setEmptyView(tvEmptyHighscore);
        return rootView;
    }



    private HashMap<String, List<Pair>> createDummyData(List<String> areaNames){
        String[] names = {"Robena", "Isela", "Jake", "Margarete", "Hyo", "Yael", "Winnifred", "Kimberely", "Arleen", "Merilyn", "Vergie", "Isidro", "Sixta", "Harriett", "Alden", "Mai", "Lara", "Romelia", "Golden", "Nancy"};
        Pair<String, String> pair;
        HashMap<String, List<Pair>> map = new HashMap<>();
        Random randomGenerator = new Random();

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

        return map;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        mHighscoreAdapter.setCurrentArea(mAreaAdapter.getItem(position), position);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }

    public void setCurrentRank(Long rank)
    {
        if(rank == null)
        {
            this.mTVCurrentRank.setText("-");
            return;
        }
        this.mTVCurrentRank.setText(rank.toString());
    }

    @Override
    public void onFragmentSelected(){


        // TODO: get highscores from server -> mHighscoreAdapter.updateItems(); -> set mTVCurrentRank
        mHighscoreAdapter.notifyDataSetChanged();
    }
}
