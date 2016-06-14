package at.acid.conquer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import at.acid.conquer.Pair;
import at.acid.conquer.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Trey
 * Created on 02.06.2016
 */
public class RankingAdapter extends BaseAdapter{

    private final LayoutInflater mInflater;
    private final Map<String, List<Pair>> mItems;
    private String mCurrentArea = "Graz-Andritz";

    public RankingAdapter(Context context){
        mItems = new HashMap<>();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void addAll(Map<String, List<Pair>> ranking){
        mItems.putAll(ranking);
    }

    public void updateItems(Map<String, List<Pair>> ranking){
        mItems.clear();
        mItems.putAll(ranking);
    }

    @Override
    public int getCount(){
        if(mItems.get(mCurrentArea) != null){
            return mItems.get(mCurrentArea).size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        if(view == null){
            view = mInflater.inflate(R.layout.ranking_list_item, parent, false);
        }

        Pair currentRunner = mItems.get(mCurrentArea).get(position);
        TextView name = (TextView) view.findViewById(R.id.tv_ranking_name);
        TextView points = (TextView) view.findViewById(R.id.tv_ranking_points);

        name.setText(position + 1 + ". " + currentRunner.getFirst());
        points.setText(currentRunner.getSecond().toString());

        return view;
    }

    public void setCurrentArea(String area){
        mCurrentArea = area;
        this.notifyDataSetChanged();
    }
}

