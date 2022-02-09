package com.example.aadharscanner.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonVolley extends Volley {

    @Nullable
    private static SingletonVolley singletonVolley = null;
    private Context context;
    private RequestQueue requestQueue;

    public SingletonVolley(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    @Nullable
    public static synchronized SingletonVolley getInstance(Context appContext) {

        if (singletonVolley == null) {
            singletonVolley = new SingletonVolley(appContext);
        }
        return singletonVolley;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(@NonNull Request<T> request) {
        getRequestQueue().add(request);
    }
}
