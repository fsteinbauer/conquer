package at.acid.conquer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.concurrent.Future;

import at.acid.conquer.R;
import at.acid.conquer.model.User;

/**
 * Created by trewurm
 * 05.05.2016.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {
    private boolean mEditMode;
    private ImageButton mButtonEditName;
    private TextView mTextFieldName;
    private EditText mEditTextName;

    private User mUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        mUser = new User();
        mUser.setName("Johannes der Läufer");


        mButtonEditName = (ImageButton) rootView.findViewById(R.id.ib_name_edit);
        mButtonEditName.setOnClickListener(this);

        mTextFieldName = (TextView) rootView.findViewById(R.id.tv_profile_name);
        mTextFieldName.setText(mUser.getName());
        mTextFieldName.setOnClickListener(this);
        mEditTextName = (EditText) rootView.findViewById(R.id.et_profile_name);
        mEditTextName.setText(mUser.getName());


        mEditMode = false;

        // Inflate the layout for this fragment
        return rootView;
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


        } else {
            mEditMode = true;
            mButtonEditName.setBackgroundResource(R.drawable.ic_check);
            mTextFieldName.setVisibility(View.GONE);
            mEditTextName.setVisibility(View.VISIBLE);
            mEditTextName.requestFocus();
        }
    }

}
