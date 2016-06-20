package at.acid.conquer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import at.acid.conquer.R;
import at.acid.conquer.communication.Communicator;
import at.acid.conquer.communication.Requests.HighscoreRequest;
import at.acid.conquer.communication.Requests.Request;
import at.acid.conquer.fragments.HighscoreFragment;
import at.acid.conquer.model.Highscore;
import at.acid.conquer.model.User;

import static com.google.android.gms.internal.zzid.runOnUiThread;

/**
 * Created by Trey
 * Created on 02.06.2016
 */
public class RankingAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;


    private int mCurrentAreaID;

    private String mCurrentArea;

    private final User mSelf;


    Communicator c = new Communicator(new Communicator.CummunicatorClient() {
        @Override
        public void onRequestReady(Request r) {
            HighscoreRequest hr = (HighscoreRequest) r;


            updateItems(hr.getResult().mHighScore);


        }

        @Override
        public void onRequestTimeOut(Request r) {

        }

        @Override
        public void onRequestError(Request r) {

        }


    }, "http://conquer.menzi.at");

    private HighscoreFragment mParent;


    public RankingAdapter(Context context, User self, HighscoreFragment hf) {
        mHighscore = new Highscore();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mSelf = self;

        mParent = hf;

    }

    private Highscore mHighscore;


    public void updateItems(Highscore ranking) {

        mHighscore.clear();

        if (ranking != null) {

            mHighscore.addAll(ranking);
        }


        final Long currentRank = this.getCurrentRank();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
                mParent.setCurrentRank(currentRank);

            }
        });

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

        this.updateItems(null);
        HighscoreRequest hgr = new HighscoreRequest(mCurrentAreaID, mSelf.getId());

        c.sendRequest(hgr);

    }

    public Long getCurrentRank() {

        Highscore.HighscoreUser self = mHighscore.findSelf();
        if (self == null) {
            return null;
        }

        return self.getRank();
    }
}

