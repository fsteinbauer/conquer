package at.acid.conquer.communication.Requests;


/**
 * Created by Annie on 04/05/2016.
 */
public abstract class Request {


    public abstract void setSuccess(ReturnValue success);

    public enum ReturnValue {
        NOT_INITIALIZED,
        SUCCESS,
        JSON_ERROR,
        NUMBER_FORMAT_ERROR,
        TIME_OUT,
        DATABASE_ERROR,
        IO_ERROR
    }

    public abstract String getURLExtension();


    public abstract void parseReturn(String s);


}





