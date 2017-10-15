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
        Realm.init(this);
    }
}
