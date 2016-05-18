package at.acid.conquer.communication;



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

    final static String URL_STRING = "TODO";

    void register(final long lat, final long lng)
    {

        new Thread(new Runnable() {
            public void run() {

                if(sendRequest(new RegisterRequest(lat, lng)))
                {
                    //TODO;
                }
            }

        }).start();

    }

    private String readStream(InputStream in) throws IOException
    {
        int bytesRead = 0;
        byte[] read = new byte[1024];
        StringBuilder sb =  new StringBuilder();
        while( (bytesRead = in.read(read)) != -1){
            sb.append(new String(read, 0, bytesRead));
        }

        return sb.toString();
    }



    protected boolean sendRequest(Request req) {

        try {
            final java.net.URL url = new URL(URL_STRING + req.getURLExtension());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                return req.parseReturn(readStream(in));
            }
            finally{
                urlConnection.disconnect();
            }

        }catch (IOException ioe)
        {
            return false;
        }
    }

}
