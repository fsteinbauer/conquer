package at.acid.conquer.communication;


import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import at.acid.conquer.communication.Requests.Request;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Annie on 04/05/2016.
 */
public class Communicator {

    private class AsyncHelper extends AsyncHttpResponseHandler {

        public final Request mRequest;

        AsyncHelper(Request req) {
            mRequest = req;
        }

        @Override
        public boolean getUseSynchronousMode() {
            return false;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            mRequest.parseReturn(new String(responseBody));
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            error.printStackTrace();
            Log.d(TAG, "Connection Error: ", error);
        }


    }


    final static String TAG = "Communicator";
    private final String mServerUrl;

    public final static String TESTING_URL = "http://conquer2.menzi.at/";

    public final static String PRODUCTION_URL = "http://conquer.menzi.at/";

    public Communicator(String server_url) {
        mServerUrl = server_url;
    }

    private AsyncHttpClient client = new AsyncHttpClient();


    public void sendRequest(final Request req) {


        client.get(mServerUrl + req.getURLExtension(), new AsyncHelper(req));


    }


}
