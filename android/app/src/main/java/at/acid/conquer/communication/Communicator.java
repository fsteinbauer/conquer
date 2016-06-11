package at.acid.conquer.communication;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import at.acid.conquer.communication.Requests.RegisterRequest;
import at.acid.conquer.communication.Requests.Request;

/**
 * Created by Annie on 04/05/2016.
 */
public class Communicator {

    final static String URL_STRING = "http://conquer.menzi.at/";
    final static String TAG = "Communicator";


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

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    final java.net.URL url = new URL(URL_STRING + req.getURLExtension());
                    System.out.println(url);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {

                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        req.parseReturn(readStream(in));
                    } finally {
                        urlConnection.disconnect();
                    }


                } catch (IOException ioe) {
                    Log.d(TAG, ioe.getMessage());
                }
            }
        });

        t.start();

        try {
            t.join(500);
        } catch (InterruptedException e) {

            Log.d(TAG, "THREAD INTERUPT");
            return false;
        }


        return true;


    }


}
