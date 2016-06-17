package at.acid.conquer.communication;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import at.acid.conquer.R;
import at.acid.conquer.communication.Requests.Request;

/**
 * Created by Annie on 04/05/2016.
 */
public class Communicator {
    final static String TAG = "Communicator";
    private String mServerUrl;

    public final String TESTING_URL = "conquer2.menzi.at";

    public final String PRODUCTION_URL = "conquer.menzi.at";

    public Communicator(String server_url){
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


    public boolean sendRequest(final Request req) {

        Log.d(TAG, "Sending request: " + mServerUrl + req.getURLExtension());

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    final java.net.URL url = new URL(mServerUrl + req.getURLExtension());

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {

                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        req.parseReturn(readStream(in));


                    } finally {
                        urlConnection.disconnect();

                    }


                } catch (IOException ioe) {
                    Log.d(TAG, ioe.getMessage());
                    ioe.printStackTrace();
                    req.setSuccess(Request.ReturnValue.IO_ERROR);
                }
            }
        });

        t.start();


        try {
            t.join(5000);
        } catch (InterruptedException e) {


            req.setSuccess(Request.ReturnValue.TIME_OUT);
            return false;
        }

        if(t.isAlive()) {
            req.setSuccess(Request.ReturnValue.TIME_OUT);
            t.interrupt();

            return false;
        }

        return true;


    }


}
