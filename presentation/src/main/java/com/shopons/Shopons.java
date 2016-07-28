package com.shopons;

import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.shopons.data.utils.RealmFactory;

import net.danlew.android.joda.JodaTimeAndroid;

import io.fabric.sdk.android.Fabric;

public class Shopons extends MultiDexApplication {

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(getApplicationContext(), new Crashlytics());
        RealmFactory.Init(getApplicationContext());
        JodaTimeAndroid.init(getApplicationContext());
        //RealmFactory.Init(getApplicationContext());
        //AppversionSave.init();
    }

    /**
     * This method is for use in emulated process environments.  It will
     * never be called on a production Android device, where processes are
     * removed by simply killing them; no user code (including this callback)
     * is executed when doing so.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
