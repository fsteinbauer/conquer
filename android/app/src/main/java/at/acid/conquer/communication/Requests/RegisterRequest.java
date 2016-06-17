package at.acid.conquer.communication.Requests;

import android.content.SyncAdapterType;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import at.acid.conquer.model.User;

/**
 * Created by Annie on 04/05/2016.
 */
public class RegisterRequest extends Request {


    final static String TAG = "RegisterRequest";


    private ReturnValue mResult;

    private User mUser;

    public RegisterRequest(User user) {

        this.mResult = ReturnValue.NOT_INITIALIZED;
        this.mUser = user;

    }





    @Override
    public String getURLExtension() {
        return "register"; }

    public ReturnValue getResult()
    {
        return mResult;
    }
    @Override
    public void parseReturn(String returnString) {

        try {
            JSONObject obj = new JSONObject(returnString);

            this.mUser.setId(obj.getString("id"));
            this.mUser.setName(obj.getString("name"));

            this.mResult = ReturnValue.SUCCESS;

        } catch (JSONException e) {


            System.out.println(returnString);

            this.mResult = Request.ReturnValue.JSON_ERROR;
            e.printStackTrace();

        }

    }

}
