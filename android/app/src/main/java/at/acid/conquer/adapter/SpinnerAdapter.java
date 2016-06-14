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

/**
 * Created by trewurm
 * 02.06.2016.
 */
public class SpinnerAdapter extends BaseAdapter{

    private final LayoutInflater mInflater;
    private final List<String> mItems;

    public SpinnerAdapter(Context context){
        mItems = new ArrayList<>();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void clear(){
        mItems.clear();
    }

    public void addItem(String item){
        mItems.add(item);
    }

    public void addItems(List<String> items){
        mItems.addAll(items);
    }

    @Override
    public int getCount(){
        return mItems.size();
    }

    @Override
    public String getItem(int position){
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent){
        if(view == null || !view.getTag().toString().equals("DROPDOWN")){
            view = mInflater.inflate(R.layout.spinner_dropdown_item, parent, false);
            view.setTag("DROPDOWN");
        }

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(mItems.get(position));

        return view;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        if(view == null || !view.getTag().toString().equals("NON_DROPDOWN")){
            view = mInflater.inflate(R.layout.spinner_item, parent, false);
            view.setTag("NON_DROPDOWN");
        }

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(mItems.get(position));
        return view;
    }

}
