package at.acid.conquer;

/**
 * Created by Trey
 * Created on 02.06.2016
 */
public class Pair<A, B>{
    private final A mFirst;
    private final B mSecond;

    public Pair(A first, B second){
        mFirst = first;
        mSecond = second;
    }

    public A getFirst(){
        return mFirst;
    }

    public B getSecond(){
        return mSecond;
    }
}