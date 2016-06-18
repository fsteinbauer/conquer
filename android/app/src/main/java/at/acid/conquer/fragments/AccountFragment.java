package at.acid.conquer.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import at.acid.conquer.MainActivity;
import at.acid.conquer.R;
import at.acid.conquer.adapter.HistoryAdapter;
import at.acid.conquer.model.User;


/**
 * Created by trewurm
 * 05.05.2016.
 */


public class AccountFragment extends BaseClass implements View.OnClickListener{

    private boolean mEditMode;
    private ImageButton mButtonEditName;
    private TextView mTextFieldName;
    private EditText mEditTextName;
    private HistoryAdapter mHistoryAdapter;
    private TextView mTVDistance;
    private TextView mTVPoints;
    private TextView mTVDuration;


    private User mUser;
    public AccountFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        mButtonEditName = (ImageButton) rootView.findViewById(R.id.ib_name_edit);
        mTextFieldName = (TextView) rootView.findViewById(R.id.tv_profile_name);
        mEditTextName = (EditText) rootView.findViewById(R.id.et_profile_name);
        ListView history = (ListView) rootView.findViewById(R.id.lv_history);
        TextView tvEmptyHistory = (TextView) rootView.findViewById(R.id.tv_empty_history);

        mTVDistance = (TextView) rootView.findViewById(R.id.tv_trackinginfo_info_distance);
        mTVPoints = (TextView) rootView.findViewById(R.id.tv_trackinginfo_info_points);
        mTVDuration = (TextView) rootView.findViewById(R.id.tv_trackinginfo_info_duration);

        mUser = ((MainActivity) getActivity()).getUser();


        mButtonEditName.setOnClickListener(this);
        mTextFieldName.setOnClickListener(this);

        mTextFieldName.setText(mUser.getName());
        mEditTextName.setText(mUser.getName());

        mEditMode = false;

        mHistoryAdapter = new HistoryAdapter(getContext(), mUser.getRoutes());
        history.setAdapter(mHistoryAdapter);
        history.setEmptyView(tvEmptyHistory);

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        mHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.tv_profile_name:
            case R.id.ib_name_edit:
                handleEditButtonClick();
                break;
        }
    }


    private void handleEditButtonClick(){
        if(mEditMode){
            mEditMode = false;
            mButtonEditName.setBackgroundResource(R.drawable.ic_pencil);
            mTextFieldName.setVisibility(View.VISIBLE);
            mEditTextName.setVisibility(View.GONE);

            mUser.setName(mEditTextName.getText().toString());
            mUser.persist();

            mTextFieldName.setText(mUser.getName());

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mTextFieldName.getWindowToken(), 0);


        } else{
            mEditMode = true;
            mButtonEditName.setBackgroundResource(R.drawable.ic_check);
            mTextFieldName.setVisibility(View.GONE);
            mEditTextName.setVisibility(View.VISIBLE);
            mEditTextName.requestFocus();
        }
    }

    @Override
    public void onFragmentSelected(){
        mHistoryAdapter.notifyDataSetChanged();
        updateOverallHighscore();
    }

    private void updateOverallHighscore(){
        long meters = 0L;
        int points = 0;
        long duration = 0L;

        for(User.RouteStore routeStore : mUser.getRoutes()){
            meters += routeStore.mDistance;
            points += routeStore.mPoints;
            duration += routeStore.mRunningTime;
        }

        long second = (duration / 1000) % 60;
        long minute = (duration / (1000 * 60)) % 60;
        long hour = (duration / (1000 * 60 * 60));

        mTVDistance.setText(Long.toString(meters/1000) + "km");
        mTVPoints.setText(Integer.toString(points));
        mTVDuration.setText(String.format("%d:%02d:%02d", hour, minute, second));
    }
}
