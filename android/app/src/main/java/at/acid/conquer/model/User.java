package at.acid.conquer.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.acid.conquer.data.Areas;

/**
 * Created by florian on 10.05.2016.
 */
public class User {
    private class AreaStore{
        public long mRunningTime;
        public double mDistance;
        public long mPoints;

        public JSONObject getJSONObject() {
            JSONObject obj = new JSONObject();
            try {
                obj.put("RunningTime", mRunningTime);
                obj.put("Distance", mDistance);
                obj.put("Points", mPoints);
            } catch (JSONException e) {
                //trace("DefaultListItem.toString JSONException: "+e.getMessage());
            }
            return obj;
        }
    }
    private class RouteStore{
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
                //trace("DefaultListItem.toString JSONException: "+e.getMessage());
            }
            return obj;
        }
    }

    private String mId;
    private String mName;
    private List<AreaStore> mAreas = new ArrayList<AreaStore>();
    private List<RouteStore> mRoutes = new ArrayList<RouteStore>();
    private long mLastAvtivity;
    private long mLastServerConnect;

    private Context mContext;
    static private String STORE_NAME = "LocalStore";
    static public String TAG = "User";

    public User(Context context){
        mContext = context;

        //for( int i = 0; i < Areas.mAreas.size(); i++)
        for( int i = 0; i < 5; i++)
            mAreas.add(new AreaStore());

        SharedPreferences store = mContext.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);

        String routes = store.getString("routes", "{}");
        String areas = store.getString("areas", "{}");
        mName = store.getString("name", "Name");
        mId = store.getString("id", "");
        mLastAvtivity = store.getLong("last_activity", 0);
        mLastServerConnect = store.getLong("last_server_connect", 0);

        try {
            JSONArray jroutes = new JSONArray(routes);
            for (int i = 0; i < jroutes.length(); i++) {
                JSONObject jsonobject = jroutes.getJSONObject(i);
                RouteStore routeStore = new RouteStore();
                routeStore.mDate = jsonobject.getLong("mDate");
                routeStore.mRunningTime = jsonobject.getLong("mRunningTime");
                routeStore.mDistance = jsonobject.getDouble("mDistance");
                routeStore.mPoints = jsonobject.getLong("mPoints");
                mRoutes.add(routeStore);
            }
            JSONArray jareas = new JSONArray(areas);
            for (int i = 0; i < jareas.length(); i++) {
                JSONObject jsonobject = jareas.getJSONObject(i);
                AreaStore areaStore = new AreaStore();
                areaStore.mRunningTime = jsonobject.getLong("mRunningTime");
                areaStore.mDistance = jsonobject.getDouble("mDistance");
                areaStore.mPoints = jsonobject.getLong("mPoints");
                mAreas.add(areaStore);
            }

        }
        catch(Exception e){
            Log.d(TAG, e.getMessage());
        }

        // TODO: change points
        mLastAvtivity = System.currentTimeMillis();

        addRoute(18052016, 60, 90, 100);
        addRoute(20052016, 120, 180,200);
        updateArea(1,1,1,1);
        updateArea(2,20,25,20);
    }

    public Boolean saveData(){
        SharedPreferences.Editor store = mContext.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE).edit();

        JSONArray routes = new JSONArray();
        for (int i=0; i < mRoutes.size(); i++) {
            routes.put(mRoutes.get(i).getJSONObject());
        }

        JSONArray areas = new JSONArray();
        for (int i=0; i < mAreas.size(); i++) {
            areas.put(mAreas.get(i).getJSONObject());
        }

        store.putString("routes", routes.toString());
        store.putString("areas", areas.toString());
        store.putString("name", mName);
        store.putString("id", mId);
        store.putLong("last_activity", mLastAvtivity);
        store.putLong("last_server_connect", mLastServerConnect);

        Log.d(TAG, routes.toString());
        Log.d(TAG, areas.toString());

        Log.d(TAG, areas.toString());

        return store.commit();
    }

    public void addRoute(long date, long runningTime, double distance, long points){
        RouteStore route = new RouteStore();
        route.mDate = date;
        route.mRunningTime = runningTime;
        route.mDistance = distance;
        route.mPoints = points;
        mRoutes.add(route);
    }

    public void updateArea(int areaId, long runningTime, double distance, long points){
        //if( areaId < Areas.mAreas.size() ) {
        if( areaId < 5 ) {
            AreaStore area = mAreas.get(areaId);
            area.mRunningTime += runningTime;
            area.mDistance += distance;
            area.mPoints += points;
        }
    }

    public void persist(){
        // TODO: Create Network persist function
    }

    public String getId() {
        return mId;
    }
    public void setId(String id) {
        this.mId = id;
    }
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        this.mName = name;
    }
}
