package at.acid.conquer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import at.acid.conquer.R;
import at.acid.conquer.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Trey
 * Created on 10.05.2016
 */
public class HistoryAdapter extends BaseAdapter{

    private final LayoutInflater mInflater;
    private final List<User.RouteStore> mItems;
    private static final SimpleDateFormat dateSDF = new SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.GERMAN);

    public HistoryAdapter(Context context, List<User.RouteStore> items){
        mItems = items;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        User.RouteStore currentItem = mItems.get(position);
        date.setText(dateSDF.format(new Date(currentItem.mDate)));

        long time = currentItem.mRunningTime;
        long second = (time / 1000) % 60;
        long minute = (time / (1000 * 60)) % 60;
        long hour = (time / (1000 * 60 * 60));

        duration.setText(String.format("%.2fkm - %d:%02d:%02d", currentItem.mDistance, hour, minute, second));
        points.setText(Long.toString(currentItem.mPoints));

        return view;
    }

}
