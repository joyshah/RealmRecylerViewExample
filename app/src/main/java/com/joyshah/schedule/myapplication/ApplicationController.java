package com.joyshah.schedule.myapplication;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Joy Shah on 15-10-2017.
 */

public class ApplicationController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize realm (so we can create realm instance anywhere,just once per application)
        Realm.init(this);
    }
}
