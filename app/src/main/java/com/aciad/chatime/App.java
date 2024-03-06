package com.aciad.chatime;

import android.app.Application;

import com.aciad.chatime.utils.SharedPrefsUtil;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefsUtil.init(this);
    }
}
