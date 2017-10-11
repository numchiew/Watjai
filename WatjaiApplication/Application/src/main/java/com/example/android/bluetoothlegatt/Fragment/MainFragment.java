package com.example.android.bluetoothlegatt.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bluetoothlegatt.Activity.BlankNotificationActivity;
import com.example.android.bluetoothlegatt.Activity.HistoryActivity;
import com.example.android.bluetoothlegatt.Activity.NotificationActivity;
import com.example.android.bluetoothlegatt.Activity.ProfileActivity;
import com.example.android.bluetoothlegatt.Bluetooth.DeviceControlActivity;
import com.example.android.bluetoothlegatt.Bluetooth.DeviceScanActivity;
import com.example.android.bluetoothlegatt.Dao.WatjaiMeasure;
import com.example.android.bluetoothlegatt.Manager.HttpManager;
import com.example.android.bluetoothlegatt.R;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    private ImageButton buttonProfile, buttonMeasure, buttonHistory, buttonSetting, buttonHelp, buttonNotification;
    private TextView countNotification;
    private boolean isBluetoothStatus = false;
    private int countNoti = 0;
    private String lastIdMeasure = "";
    private ArrayList<WatjaiMeasure> watjaiMeasure;

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState

        buttonProfile = (ImageButton) rootView.findViewById(R.id.btnProfile);
        buttonMeasure = (ImageButton) rootView.findViewById(R.id.btnMeasure);
        buttonHistory = (ImageButton) rootView.findViewById(R.id.btnHistory);
        buttonSetting = (ImageButton) rootView.findViewById(R.id.btnSetting);
        buttonHelp = (ImageButton) rootView.findViewById(R.id.btnHelp);
        buttonNotification = (ImageButton) rootView.findViewById(R.id.btnNotification);
        countNotification = (TextView) rootView.findViewById(R.id.countNotification);

        checkAlert();

        buttonMeasure.setOnClickListener(this);
        buttonProfile.setOnClickListener(this);
        buttonHistory.setOnClickListener(this);
        buttonNotification.setOnClickListener(this);
        buttonHelp.setOnClickListener(this);
    }

    /*@Override
    public void onStart() {
        super.onStart();
        System.out.println("onstart " +countNoti);
        showCountNotification();
    }*/

    @Override
    public void onResume() {
        super.onResume();
        if (countNoti>0) {
            showCountNotification();
        }

    }

    private void checkAlert() {
        Call<ArrayList<WatjaiMeasure>> call = HttpManager.getInstance().getService().loadWatjaiMeasureAlert("PA1709001","");
        NetworkCallRefresh refresh = new NetworkCallRefresh();
        NetworkCall task = new NetworkCall();
        refresh.execute(call);
        task.execute(call);



        /*call.enqueue(new Callback<ArrayList<WatjaiMeasure>>() {
            @Override
            public void onResponse(Call<ArrayList<WatjaiMeasure>> call, Response<ArrayList<WatjaiMeasure>> response) {
                if (response.isSuccessful()) {
                    watjaiMeasure = response.body();
                    if (watjaiMeasure.size() > 0) {
                        countNoti = watjaiMeasure.size();
                        lastIdMeasure = watjaiMeasure.get(countNoti-1).getMeasuringId();
                        showCountNotification();
                    }
                } else {
                    try {
                        Toast.makeText(getContext(),
                                response.errorBody().string(),
                                Toast.LENGTH_SHORT)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<WatjaiMeasure>> call, Throwable throwable) {
                if (Locale.getDefault().getLanguage().equals("th")) {
                    Toast.makeText(getContext(), "กรุณาเชื่อมต่ออินเทอร์เน็ต", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Disconnect internet", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        if (v == buttonProfile) {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);
        } else if (v == buttonMeasure) {
            Intent intent = new Intent(getContext(), DeviceScanActivity.class);

            if (isBluetoothStatus) {
                Intent watjai = new Intent(getContext(), DeviceControlActivity.class);
                startActivity(watjai);
            } else {
                startActivity(intent);
            }
        } else if (v == buttonHistory) {
            Intent intent = new Intent(getContext(), HistoryActivity.class);
            startActivity(intent);
        } else if (v == buttonNotification) {
            if  (countNoti > 0 ) {
                Intent noti = new Intent(getContext(), NotificationActivity.class);
                noti.putExtra("notification", watjaiMeasure);
                startActivity(noti);
            } else {
                Intent blank = new Intent(getContext(), BlankNotificationActivity.class);
                startActivity(blank);
            }
        } else if (v == buttonHelp) {
        }

    }

    private void showCountNotification() {
        countNotification.setVisibility(View.VISIBLE);
        countNotification.setText(countNoti+"");
    }

    private void hideCountNotification() {
        countNotification.setVisibility(View.GONE);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
        outState.putParcelableArrayList("watjaimeasure",watjaiMeasure);
        outState.putInt("count", countNoti);
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
        watjaiMeasure = savedInstanceState.getParcelableArrayList("watjaimeasure");
        countNoti = savedInstanceState.getInt("count");
    }

    private class NetworkCallRefresh extends AsyncTask<Call, Integer, ArrayList<WatjaiMeasure>> {
        int refreshTime=100;

        @Override
        protected void onPostExecute(ArrayList<WatjaiMeasure> result) {
            watjaiMeasure = result;
            countNoti = watjaiMeasure.size();
            if (watjaiMeasure.size() != 0)
                lastIdMeasure = watjaiMeasure.get(countNoti-1).getMeasuringId();
        }

        @Override
        protected ArrayList<WatjaiMeasure> doInBackground(Call... params) {

            try {
                for (int i=0; i<refreshTime; i++) {
                    Call<ArrayList<WatjaiMeasure>> call = HttpManager.getInstance().getService().loadWatjaiMeasureAlert("PA1709001","");
                    Response<ArrayList<WatjaiMeasure>> response = call.execute();
                    if (response.body() != null) {
                        watjaiMeasure = response.body();
                        countNoti = watjaiMeasure.size();
                        if (watjaiMeasure.size() != 0)
                            lastIdMeasure = watjaiMeasure.get(countNoti-1).getMeasuringId();
                        System.out.println(countNoti);
                        Thread.sleep(1000);
                    }
                }
                return watjaiMeasure;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

    private class NetworkCall extends AsyncTask<Call, Integer, ArrayList<WatjaiMeasure>> {

        @Override
        protected void onPostExecute(ArrayList<WatjaiMeasure> result) {
            watjaiMeasure = result;
            countNoti = watjaiMeasure.size();
            //lastIdMeasure = watjaiMeasure.get(countNoti-1).getMeasuringId();
        }

        @Override
        protected ArrayList<WatjaiMeasure> doInBackground(Call... params) {

            try {

                    Call<ArrayList<WatjaiMeasure>> call = HttpManager.getInstance().getService().loadWatjaiMeasureAlert("PA1709001","");
                    Response<ArrayList<WatjaiMeasure>> response = call.execute();
                    watjaiMeasure = response.body();
                    countNoti = watjaiMeasure.size();

                return watjaiMeasure;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

}



