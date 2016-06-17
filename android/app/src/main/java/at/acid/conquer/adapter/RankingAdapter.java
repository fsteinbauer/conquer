package at.acid.conquer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import at.acid.conquer.R;
import at.acid.conquer.communication.Communicator;
import at.acid.conquer.communication.Requests.HighscoreRequest;
import at.acid.conquer.communication.Requests.Request;
import at.acid.conquer.model.Highscore;
import at.acid.conquer.model.User;

/**
 * Created by Trey
 * Created on 02.06.2016
 */
public class RankingAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;


    private int mCurrentAreaID;

    private String mCurrentArea ;

    private final User mSelf;




    Communicator c = new Communicator(Communicator.PRODUCTION_URL);


    public RankingAdapter(Context context, User self) {
        mHighscore = new Highscore();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mSelf = self;

        this.setCurrentArea("Graz", 0);
    }

    private Highscore mHighscore;


    public void updateItems(Highscore ranking) {
        mHighscore.clear();
        mHighscore.addAll(ranking);

    }

    @Override
    public int getCount() {
        if (mHighscore != null) {
            return mHighscore.size();
        }
        return 0;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = mInflater.inflate(R.layout.ranking_list_item, parent, false);
        }


        Highscore.HighscoreUser user = mHighscore.get(position);

        TextView name = (TextView) view.findViewById(R.id.tv_ranking_name);
        TextView points = (TextView) view.findViewById(R.id.tv_ranking_points);

        name.setText(user.getRank() + ". " + user.getUsername());
        points.setText(user.getPoints().toString());


        return view;
    }

    public void setCurrentArea(String areaName, int AreaID) {
        mCurrentArea = areaName;
        mCurrentAreaID = AreaID;
        HighscoreRequest hgr = new HighscoreRequest(mCurrentAreaID, mSelf.getId());

        c.sendRequest(hgr);

        if (hgr.getResult().mSuccess == Request.ReturnValue.SUCCESS) {
            updateItems(hgr.getResult().mHgs);
        }




        this.notifyDataSetChanged();
    }

    public Long getCurrentRank() {

        Highscore.HighscoreUser self = mHighscore.findSelf();
        if(self == null)
        {
            return null;
        }

        return self.getRank();
    }
}

