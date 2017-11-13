package com.example.android.bluetoothlegatt.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.example.android.bluetoothlegatt.GlobalService;

import com.example.android.bluetoothlegatt.Bluetooth.BluetoothLeService;
import com.example.android.bluetoothlegatt.Fragment.MainFragment;
import com.example.android.bluetoothlegatt.R;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {

    private static boolean isLoginStatus = false;
    private final static String TAG = MainActivity.class.getSimpleName();
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            GlobalService.mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!GlobalService.mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            if(GlobalService.mBluetoothLeService.getDeviceAddress() != null && GlobalService.mBluetoothLeService.getDeviceAddress().equalsIgnoreCase("ehealth")) {
                GlobalService.mBluetoothLeService.connect(GlobalService.mBluetoothLeService.getDeviceAddress());
            }
            Toast.makeText(MainActivity.this, "Service have been create", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            GlobalService.mBluetoothLeService = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        isLoginStatus = getIntent().getBooleanExtra("loginStatus", false);

        if (isLoginStatus) {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentContainer, MainFragment.newInstance())
                        .commit();
            }
            Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
            startService(gattServiceIntent);
            bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }




}
