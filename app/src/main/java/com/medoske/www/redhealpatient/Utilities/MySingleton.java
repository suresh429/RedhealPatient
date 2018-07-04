package com.medoske.www.redhealpatient.Utilities;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by USER on 10.8.17.
 */

public class MySingleton extends Application {

    public static MySingleton mInstance;
    public static Context mcontext;
    private RequestQueue mRequestQueue;

    public MySingleton(Context context) {
        mcontext = context;
        mRequestQueue= getmRequestQueue();
    }

    private RequestQueue getmRequestQueue(){

        if (mRequestQueue==null){

         mRequestQueue = Volley.newRequestQueue(mcontext.getApplicationContext());

        }
        return mRequestQueue;
    }

    public static synchronized MySingleton getmInstance(Context context){

        if (mInstance==null){

            mInstance= new MySingleton(context);
        }

        return mInstance;
    }

    public<T> void addToRequestQue(Request<T> request){

        getmRequestQueue().add(request);
    }
}
