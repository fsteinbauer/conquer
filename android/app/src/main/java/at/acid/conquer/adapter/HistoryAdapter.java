package at.acid.conquer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import at.acid.conquer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Trey
 * Created on 10.05.2016
 */
public class HistoryAdapter extends BaseAdapter{

    private final LayoutInflater mInflater;
    private final List<String> mItems;

    public HistoryAdapter(Context context){
        mItems = new ArrayList();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addAll(ArrayList<String> objects){
        mItems.addAll(objects);
    }

    @Override
    public int getCount(){
        return mItems.size();
    }

    @Override
    public Object getItem(int position){
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        if(view == null){
            view = mInflater.inflate(R.layout.history_list_item, parent, false);
        }

        TextView date = (TextView) view.findViewById(R.id.tv_history_date);
        TextView duration = (TextView) view.findViewById(R.id.tv_history_duration);
        TextView points = (TextView) view.findViewById(R.id.tv_history_points);

        date.setText("12.Juni 2016");
        duration.setText("12.5km - 01:24:38");
        Random randomGenerator = new Random();

        points.setText(Integer.toString(randomGenerator.nextInt(20000)));

        return view;
    }

}
