package com.example.android.bluetoothlegatt;

import android.app.Application;

import com.example.android.bluetoothlegatt.Manager.Contextor;


/**
 * Created by User on 20/3/2560.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Restore Singleton data here

        // initialize ting(s) here
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        // Save Singleton data here

    }




}
