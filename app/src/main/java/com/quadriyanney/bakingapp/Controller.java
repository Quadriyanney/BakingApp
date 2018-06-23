package com.quadriyanney.bakingapp;

import android.app.Application;

/**
 * Created by quadriy on 6/5/17.
 */

public class Controller extends Application {

//    public static final String TAG = Controller.class.getSimpleName();
//
//    private RequestQueue requestQueue;
//    private static Controller controller;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        controller = this;
//    }
//
//    public static synchronized Controller getInstance() {
//        return controller;
//    }
//
//    public RequestQueue getRequestQueue() {
//        if (requestQueue == null) {
//            requestQueue = Volley.newRequestQueue(getApplicationContext());
//        }
//        return requestQueue;
//    }
//
//    public <T> void addToRequestQueue(Request<T> req, String tag) {
//        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
//        getRequestQueue().add(req);
//    }
}