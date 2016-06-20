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

import at.acid.conquer.R;
import at.acid.conquer.adapter.HistoryAdapter;
import at.acid.conquer.communication.Communicator;
import at.acid.conquer.communication.Requests.RegisterRequest;
import at.acid.conquer.communication.Requests.Request;
import at.acid.conquer.model.User;


/**
 * Created by trewurm
 * 05.05.2016.
 */


public class AccountFragment extends BaseClass implements View.OnClickListener {

    private boolean mEditMode;
    private ImageButton mButtonEditName;
    private TextView mTextFieldName;
    private EditText mEditTextName;
    private HistoryAdapter mHistoryAdapter;
    private TextView mTVDistance;
    private TextView mTVPoints;
    private TextView mTVDuration;
    private ListView mLVHistory;
    private TextView mTVEmptyList;

    private User mUser;

    public AccountFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        mButtonEditName = (ImageButton) rootView.findViewById(R.id.ib_name_edit);
        mTextFieldName = (TextView) rootView.findViewById(R.id.tv_profile_name);
        mEditTextName = (EditText) rootView.findViewById(R.id.et_profile_name);
        mLVHistory = (ListView) rootView.findViewById(R.id.lv_history);
        mTVEmptyList = (TextView) rootView.findViewById(R.id.tv_empty_history);

        mTVDistance = (TextView) rootView.findViewById(R.id.tv_trackinginfo_info_distance);
        mTVPoints = (TextView) rootView.findViewById(R.id.tv_trackinginfo_info_points);
        mTVDuration = (TextView) rootView.findViewById(R.id.tv_trackinginfo_info_duration);

        mButtonEditName.setOnClickListener(this);
        mTextFieldName.setOnClickListener(this);


        mEditMode = false;


        mTextFieldName.setText(mUser.getName());
        mEditTextName.setText(mUser.getName());

        mHistoryAdapter = new HistoryAdapter(getContext(), mUser.getRoutes());

        mLVHistory.setAdapter(mHistoryAdapter);
        mLVHistory.setEmptyView(mTVEmptyList);
        return rootView;
    }

    public void setUser(User user) {
        mUser = user;
    }

    @Override
    public void onResume() {
        super.onResume();
        mHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_profile_name:
            case R.id.ib_name_edit:
                handleEditButtonClick();
                break;
        }
    }


    public void registerUser() {
        if (mUser.getId() == null || mUser.getId().isEmpty()) {
            RegisterRequest rr = new RegisterRequest();

            Communicator c = new Communicator(new Communicator.CummunicatorClient() {
                @Override
                public void onRequestReady(Request r) {
                    RegisterRequest rr = (RegisterRequest) r;


                    if (rr.getResult().mSuccess != Request.ReturnValue.SUCCESS) {
                        return;
                    } else {
                        mUser.setName(rr.getResult().mName);


                    }

                    mUser.setId(rr.getResult().mID);
                    mUser.setName(rr.getResult().mName);
                    mTextFieldName.setText(mUser.getName());
                }

                @Override
                public void onRequestTimeOut(Request r) {

                }

                @Override
                public void onRequestError(Request r) {

                }
            }, "http://conquer.menzi.at");

            c.sendRequest(rr);

        }
    }


    private void handleEditButtonClick() {
        if (mEditMode) {
            mEditMode = false;
            mButtonEditName.setBackgroundResource(R.drawable.ic_pencil);
            mTextFieldName.setVisibility(View.VISIBLE);
            mEditTextName.setVisibility(View.GONE);

            mUser.changeName(mEditTextName.getText().toString());


            mUser.saveData();

            mTextFieldName.setText(mUser.getName());

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mTextFieldName.getWindowToken(), 0);

        } else {
            mEditMode = true;
            mButtonEditName.setBackgroundResource(R.drawable.ic_check);
            mTextFieldName.setVisibility(View.GONE);
            mEditTextName.setVisibility(View.VISIBLE);
            mEditTextName.requestFocus();
        }
    }

    @Override
    public void onFragmentSelected() {
        mHistoryAdapter.notifyDataSetChanged();
        updateOverallHighscore();
    }

    private void updateOverallHighscore() {
        double meters = 0L;
        int points = 0;
        long duration = 0L;

        for (User.RouteStore routeStore : mUser.getRoutes()) {
            meters += routeStore.mDistance;
            points += routeStore.mPoints;
            duration += routeStore.mRunningTime;
        }

        long second = (duration / 1000) % 60;
        long minute = (duration / (1000 * 60)) % 60;
        long hour = (duration / (1000 * 60 * 60));

        mTVDistance.setText(String.format("%.2fkm", meters / 1000));
        mTVPoints.setText(Integer.toString(points));
        mTVDuration.setText(String.format("%02d:%02d:%02d", hour, minute, second));
    }
}
