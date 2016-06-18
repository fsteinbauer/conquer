package at.acid.conquer.communication;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;

import at.acid.conquer.R;
import at.acid.conquer.communication.Requests.Request;

/**
 * Created by Annie on 04/05/2016.
 */
public class Communicator {
    final static String TAG = "Communicator";
    private String mServerUrl;
    private Thread mLastRequest;

    private CummunicatorClient mClient;
    public interface CummunicatorClient {
        void onRequestReady(Request r);
        void onRequestTimeOut(Request r);
        void onRequestError(Request r);
    }

    public Communicator(CummunicatorClient client, String server_url){
        mClient = client;
        mServerUrl = server_url;
    }

    private String readStream(InputStream in) throws IOException {
        int bytesRead = 0;
        byte[] read = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while ((bytesRead = in.read(read)) != -1) {
            sb.append(new String(read, 0, bytesRead));
        }

        return sb.toString();
    }


    public Thread sendRequest(final Request req) {
        mLastRequest  = new Thread(new Runnable() {
            public void run() {
                try {
                    final java.net.URL url = new URL(mServerUrl + req.getURLExtension());

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setReadTimeout(5000);
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        req.parseReturn(readStream(in));
                    } finally {
                        mClient.onRequestReady(req);
                        urlConnection.disconnect();
                    }
                }
                catch (SocketTimeoutException e) {
                    req.setSuccess(Request.ReturnValue.TIME_OUT);
                    mClient.onRequestTimeOut(req);
                }
                catch (IOException ioe) {
                    Log.d(TAG, ioe.getMessage());
                    ioe.printStackTrace();
                    req.setSuccess(Request.ReturnValue.IO_ERROR);
                    mClient.onRequestError(req);
                }
            }
        });

        mLastRequest.start();
        return mLastRequest;
    }

    void waitForResponse(){
        try {
            mLastRequest.join();
        }
        catch(InterruptedException e){
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
    }
}
