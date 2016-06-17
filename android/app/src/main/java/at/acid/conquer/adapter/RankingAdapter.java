package at.acid.conquer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import at.acid.conquer.Pair;
import at.acid.conquer.R;
import at.acid.conquer.communication.Communicator;
import at.acid.conquer.communication.Requests.HighscoreRequest;
import at.acid.conquer.communication.Requests.Request;
import at.acid.conquer.model.Highscore;
import at.acid.conquer.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Trey
 * Created on 02.06.2016
 */
public class RankingAdapter extends BaseAdapter{

    private final LayoutInflater mInflater;
    private Highscore mHighScore;

    private int mCurrentAreaID = 0;

    private String mCurrentArea = "Graz";

    private final User mSelf;


    Communicator c = new Communicator(Communicator.PRODUCTION_URL);
    public void setHighscore(Highscore highscore)
    {
        this.mHighScore = highscore;
    }

    public RankingAdapter(Context context, User self){
        mHighScore = new Highscore();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mSelf = self;

        HighscoreRequest hgr = new HighscoreRequest(mCurrentAreaID, mSelf.getId());

        c.sendRequest(hgr);

        if(hgr.getResult().mSuccess == Request.ReturnValue.SUCCESS)
        {
            updateItems(hgr.getResult().mHgs);
        }
    }




    public void updateItems(Highscore ranking){
        mHighScore.clear();
        mHighScore.putAll(ranking);
    }

    @Override
    public int getCount(){
        if(mHighScore != null){
            return mHighScore.size();
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

        Long currentRunner = mHighScore.getSelf();


        TextView name = (TextView) view.findViewById(R.id.tv_ranking_name);
        TextView points = (TextView) view.findViewById(R.id.tv_ranking_points);

        name.setText(position + currentRunner + ". " + mHighScore.get(currentRunner).getUsername());
        points.setText(mHighScore.get(currentRunner).getPoints().toString());

        return view;
    }

    public void setCurrentArea(String areaName, int AreaID){
        mCurrentArea = areaName;
        mCurrentAreaID = AreaID;
        HighscoreRequest hgr = new HighscoreRequest(mCurrentAreaID, mSelf.getId());

        c.sendRequest(hgr);

        if(hgr.getResult().mSuccess == Request.ReturnValue.SUCCESS)
        {
            updateItems(hgr.getResult().mHgs);
        }
        this.notifyDataSetChanged();
    }
}

