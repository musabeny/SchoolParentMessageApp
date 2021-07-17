package com.beem.schoolmessageapp.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by musa on 13/03/2018.
 */

public class RemoteServer {
    private static RemoteServer mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    public RemoteServer(Context context) {
        mCtx = context;
        requestQueue = getReqestQueue();


    }

    private RequestQueue getReqestQueue() {

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized RemoteServer getmInstance(Context context){
        if(mInstance == null){
            mInstance = new RemoteServer(context);


        }
        return mInstance;
    }
    public<T> void addToRequetQue(Request<T> request){
        getReqestQueue().add(request);
    }

}
