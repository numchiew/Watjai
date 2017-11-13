package com.example.android.bluetoothlegatt;

import android.app.Application;

import com.example.android.bluetoothlegatt.Manager.Contextor;
import com.example.android.bluetoothlegatt.Manager.MyNotificationOpenedHandler;
import com.example.android.bluetoothlegatt.Manager.MyNotificationReceivedHandler;
import com.onesignal.OneSignal;


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
        OneSignal.sendTag("patId", "PA1709001");

        OneSignal.startInit(getApplicationContext())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
                .setNotificationReceivedHandler(new MyNotificationReceivedHandler())
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        // Save Singleton data here

    }




}
