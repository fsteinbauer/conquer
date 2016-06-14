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


public class AccountFragment extends Fragment implements View.OnClickListener{

    private boolean mEditMode;
    private ImageButton mButtonEditName;
    private TextView mTextFieldName;
    private EditText mEditTextName;

    private User mUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        mButtonEditName = (ImageButton) rootView.findViewById(R.id.ib_name_edit);
        mTextFieldName = (TextView) rootView.findViewById(R.id.tv_profile_name);
        mEditTextName = (EditText) rootView.findViewById(R.id.et_profile_name);
        ListView history = (ListView) rootView.findViewById(R.id.lv_history);
        TextView tvEmptyHistory = (TextView) rootView.findViewById(R.id.tv_empty_history);

        mUser = ((MainActivity) getActivity()).getUser();
        mUser.setName("Johannes der Läufer");

        mButtonEditName.setOnClickListener(this);
        mTextFieldName.setOnClickListener(this);

        mTextFieldName.setText(mUser.getName());
        mEditTextName.setText(mUser.getName());

        mEditMode = false;

        HistoryAdapter historyAdapter = new HistoryAdapter(getContext());
        String[] names = {"Robena", "Isela", "Jake", "Margarete", "Hyo", "Yael", "Winnifred", "Kimberely", "Arleen", "Merilyn", "Vergie", "Isidro", "Sixta", "Harriett", "Alden", "Mai", "Lara", "Romelia", "Golden", "Nancy",};
//        historyAdapter.addAll(new ArrayList<>(Arrays.asList(names)));

        history.setAdapter(historyAdapter);
        history.setEmptyView(tvEmptyHistory);


        // Inflate the layout for this fragment
        return rootView;
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

}
