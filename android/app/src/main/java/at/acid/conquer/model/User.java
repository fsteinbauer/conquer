package at.acid.conquer.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.SparseArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import at.acid.conquer.communication.Communicator;
import at.acid.conquer.communication.Requests.AddScoreRequest;
import at.acid.conquer.communication.Requests.RegisterRequest;
import at.acid.conquer.communication.Requests.RenameRequest;
import at.acid.conquer.communication.Requests.Request;

/**
 * Created by florian on 10.05.2016.
 */
public class User {
    public static final String TAG = "User";
    public static final String STORE_NAME = "LocalStore";


    private class AreaStore{
        public int mId;
        public long mRunningTime;
        public double mDistance;
        public long mPoints;

        public JSONObject getJSONObject() {
            JSONObject obj = new JSONObject();
            try {
                obj.put("Id", mId);
                obj.put("Distance", mDistance);
                obj.put("Points", mPoints);
            } catch (JSONException e) {
                Log.d(TAG, "DefaultListItem.toString JSONException: "+e.getMessage());
            }
            return obj;
        }
    }
    public class RouteStore{
        public long mDate;
        public long mRunningTime;
        public double mDistance;
        public long mPoints;

        public JSONObject getJSONObject() {
            JSONObject obj = new JSONObject();
            try {
                obj.put("Date", mDate);
                obj.put("RunningTime", mRunningTime);
                obj.put("Distance", mDistance);
                obj.put("Points", mPoints);
            } catch (JSONException e) {
                Log.d(TAG, "DefaultListItem.toString JSONException: "+e.getMessage());
            }
            return obj;
        }
    }

    private String mId;
    private String mName;


    private Communicator c = new Communicator(new Communicator.CummunicatorClient() {
        @Override
        public void onRequestReady(Request r) {
            RegisterRequest rr = (RegisterRequest) r;

            setId(rr.getResult().mID);
            setName(rr.getResult().mName);
        }

        @Override
        public void onRequestTimeOut(Request r) {

        }

        @Override
        public void onRequestError(Request r) {

        }
    }, "http://conquer.menzi.at");


    private SparseArray<AreaStore> mAreas = new SparseArray<AreaStore>();
    private List<RouteStore> mRoutes = new ArrayList<RouteStore>();
    private long mLastAvtivity;
    private long mLastServerConnect;

    private Context mContext;

    //----------------------------------------------------------------------------------------------
    public User(Context context){
        mContext = context;

        //for( int i = 0; i < Areas.mAreas.size(); i++)
        //for( int i = 0; i < 5; i++)
        //    mAreas.add(new AreaStore());

        SharedPreferences store = mContext.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);

        String routesJson = store.getString("routes", "[]");
        String areasJson = store.getString("areas", "[]");
        mName = store.getString("name", "Name");
        mId = store.getString("id", "");

        if(mId.isEmpty())
        {
            RegisterRequest rr = new RegisterRequest();

            c.sendRequest(rr);
        }
        mLastAvtivity = store.getLong("last_activity", 0);
        mLastServerConnect = store.getLong("last_server_connect", 0);

        try {
            JSONArray jroutes = new JSONArray(routesJson);
            for (int i = 0; i < jroutes.length(); i++) {
                JSONObject jsonobject = jroutes.getJSONObject(i);
                RouteStore routeStore = new RouteStore();
                routeStore.mDate = jsonobject.getLong("Date");
                routeStore.mRunningTime = jsonobject.getLong("RunningTime");
                routeStore.mDistance = jsonobject.getDouble("Distance");
                routeStore.mPoints = jsonobject.getLong("Points");
                mRoutes.add(routeStore);
            }
            JSONArray jareas = new JSONArray(areasJson);
            for (int i = 0; i < jareas.length(); i++) {
                JSONObject jsonobject = jareas.getJSONObject(i);
                AreaStore areaStore = new AreaStore();
                areaStore.mId = jsonobject.getInt("Id");
                areaStore.mDistance = jsonobject.getDouble("Distance");
                areaStore.mPoints = jsonobject.getLong("Points");
                mAreas.put(areaStore.mId, areaStore);
            }
        }
        catch(Exception e){
            Log.d(TAG, e.getMessage());
        }

        // TODO: change points
        mLastAvtivity = System.currentTimeMillis();

        /*addRoute(18052016, 60, 90, 100);
        addRoute(20052016, 120, 180,200);
        updateArea(1,1,1,1);
        updateArea(2,20,25,20);*/
    }

    //----------------------------------------------------------------------------------------------
    public Boolean saveData(){
        SharedPreferences.Editor store = mContext.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE).edit();

        JSONArray routes = new JSONArray();
        for (int i=0; i < mRoutes.size(); i++) {
            routes.put(mRoutes.get(i).getJSONObject());
        }

        JSONArray areas = new JSONArray();
        for (int i=0; i < mAreas.size(); i++) {
            areas.put(mAreas.valueAt(i).getJSONObject());
        }

        store.putString("routes", routes.toString());
        store.putString("areas", areas.toString());
        store.putString("name", mName);
        store.putString("id", mId);
        store.putLong("last_activity", mLastAvtivity);
        store.putLong("last_server_connect", mLastServerConnect);

        //Log.d(TAG, "save:");
        //Log.d(TAG, routes.toString());
        //Log.d(TAG, areas.toString());

        return store.commit();
    }


    Communicator c2 = new Communicator(new Communicator.CummunicatorClient() {
        @Override
        public void onRequestReady(Request r) {

        }

        @Override
        public void onRequestTimeOut(Request r) {

        }

        @Override
        public void onRequestError(Request r) {

        }
    }, "http://conquer.menzi.at");

    public void updateScore() {
        for(int i = 0; i < this.mAreas.size(); i++)
        {
            AreaStore r = this.mAreas.valueAt(i);
            c2.sendRequest(new AddScoreRequest(this.getId(), r.mPoints, r.mId ));
        }

    }

    //----------------------------------------------------------------------------------------------
    public void addRoute(long date, long runningTime, double distance, long points){
        RouteStore route = new RouteStore();
        route.mDate = date;
        route.mRunningTime = runningTime;
        route.mDistance = distance;
        route.mPoints = points;
        mRoutes.add(route);
    }

    //----------------------------------------------------------------------------------------------
    public void updateArea(int areaId, double distance, long points){
        AreaStore area = mAreas.get(areaId);
        if( area == null ) {
           area = new AreaStore();
           area.mId = areaId;
           area.mDistance = distance;
           area.mPoints = points;
           mAreas.put(areaId, area);
        }
        else{
            area.mDistance += distance;
            area.mPoints += points;
        }
    }

    //----------------------------------------------------------------------------------------------
    public void clearStoredData(){
        mContext.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit();
    }

    //----------------------------------------------------------------------------------------------
    public void persist(){
        // TODO: Create Network persist function
    }

    //----------------------------------------------------------------------------------------------
    public String getId() { return mId; }
    public String getName() { return mName; }
    public SparseArray<AreaStore> getAreas(){
        return mAreas;
    }
    public List<RouteStore> getRoutes(){
        return mRoutes;
    }

    //----------------------------------------------------------------------------------------------
    public void setId(String id) { this.mId = id; }


    private Communicator c3 = new Communicator(new Communicator.CummunicatorClient() {
        @Override
        public void onRequestReady(Request r) {

        }

        @Override
        public void onRequestTimeOut(Request r) {

        }

        @Override
        public void onRequestError(Request r) {

        }
    }, "http://conquer.menzi.at");
    public void setName(String name) { this.mName = name;

        RenameRequest ncr = new RenameRequest(this.getId(), name);

        c3.sendRequest(ncr);
    }
}
