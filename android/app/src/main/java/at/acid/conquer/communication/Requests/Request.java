package at.acid.conquer.communication.Requests;

import java.nio.charset.Charset;

/**
 * Created by Annie on 04/05/2016.
 */
public abstract class Request {


    protected abstract String getMessage();

    public byte[] getRequestBytes()
    {
         return getMessage().getBytes(Charset.forName("UTF-8"));
    }

    public abstract boolean parseReturn(String s);
}





