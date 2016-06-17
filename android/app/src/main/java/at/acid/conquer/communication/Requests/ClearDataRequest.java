package at.acid.conquer.communication.Requests;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Menzi on 17/06/2016.
 */
public class ClearDataRequest extends Request {

    private final String TAG = "ClearDataRequest";

    private ReturnValue mResult;

    public ClearDataRequest() {
        mResult = ReturnValue.NOT_INITIALIZED;

    }


    @Override
    public String getURLExtension() {
        return "cleardata";
    }

    @Override
    public void parseReturn(String s) {
        try {
            JSONObject obj = new JSONObject(s);
            if (obj.getBoolean("success")) {
                mResult = ReturnValue.SUCCESS;
            } else {
                mResult = ReturnValue.DATABASE_ERROR;
            }
        } catch (JSONException e) {
            mResult = ReturnValue.JSON_ERROR;
            Log.e(TAG, "parseReturn(): " + e.getMessage());
            e.printStackTrace();
        }

    }


    public ReturnValue getResult() {
        return mResult;
    }
}
