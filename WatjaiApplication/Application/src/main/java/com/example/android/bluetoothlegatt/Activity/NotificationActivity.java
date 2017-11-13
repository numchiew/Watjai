package com.example.android.bluetoothlegatt.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.android.bluetoothlegatt.Dao.WatjaiMeasure;
import com.example.android.bluetoothlegatt.Manager.Contextor;
import com.example.android.bluetoothlegatt.Manager.CounterNotification;
import com.example.android.bluetoothlegatt.Manager.HttpManager;
import com.example.android.bluetoothlegatt.R;
import com.example.android.bluetoothlegatt.View.NotificationListItem;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
    ListView listView;
    NotificationListAdapter notificationListAdapter;
    ArrayList<WatjaiMeasure> watjaiMeasure;
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        watjaiMeasure = new ArrayList<>();
        watjaiMeasure = (ArrayList<WatjaiMeasure>) getIntent().getSerializableExtra("notification");
        listView = (ListView) findViewById(R.id.notificationList);
        CounterNotification.getInstance().resetCountNotification();
        notificationListAdapter = new NotificationListAdapter();
        notificationListAdapter.addNotification(watjaiMeasure);
        listView.setAdapter(notificationListAdapter);
        listView.setOnItemClickListener(listNotification);
        notificationListAdapter = new NotificationListAdapter();
        NetworkCall task = new NetworkCall();
        task.execute();
        try {
            Thread.sleep(100);
            notificationListAdapter.addNotification(watjaiMeasure);
            listView.setAdapter(notificationListAdapter);
            listView.setOnItemClickListener(listNotification);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // update TextView here!
                                Call<ArrayList<WatjaiMeasure>> call = HttpManager.getInstance().getService().loadWatjaiMeasureAlert("PA1709001", "");
                                NetworkCall task = new NetworkCall();
                                task.execute(call);
                                notificationListAdapter = new NotificationListAdapter();
                                notificationListAdapter.addNotification(watjaiMeasure);
                                listView.setAdapter(notificationListAdapter);
                                listView.setOnItemClickListener(listNotification);
                            }
                        });
                        Thread.sleep(10000);
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();*/
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /***
     * variable
     */
    AdapterView.OnItemClickListener listNotification = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            WatjaiMeasure measure = notificationListAdapter.getItem(position);
            Intent intent = new Intent(NotificationActivity.this, DescriptionNotificationActivity.class);
            intent.putExtra("measure", measure);
            if (measure != null) {
                if (measure.getReadStatus().equalsIgnoreCase("unread")) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.readStatus);
                    imageView.setImageResource(R.drawable.bg_listnotificationpng);
                    measure.setId(null);
                    measure.setReadStatus("read");
                    Call<WatjaiMeasure> call = HttpManager.getInstance().getService().chageReadStatus(measure, measure.getMeasuringId());
                    call.enqueue(new Callback<WatjaiMeasure>() {
                        @Override
                        public void onResponse(Call<WatjaiMeasure> call, Response<WatjaiMeasure> response) {
                            if (response.isSuccessful()) {
                                System.out.println("success");
                            } else {
                                try {
                                    System.out.println(response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<WatjaiMeasure> call, Throwable throwable) {
                            System.out.println(throwable.toString());
                        }
                    });
                }
                startActivity(intent);
            }
        }
    };

    /****
     *  inner class
     */

    private class NetworkCall extends AsyncTask<Call, Integer, ArrayList<WatjaiMeasure>> {

        @Override
        protected void onPostExecute(ArrayList<WatjaiMeasure> result) {
            watjaiMeasure = result;
        }

        @Override
        protected ArrayList<WatjaiMeasure> doInBackground(Call... params) {

            try {
                Call<ArrayList<WatjaiMeasure>> call = HttpManager.getInstance().getService().loadWatjaiMeasureAlert("PA1709001", "");
                Response<ArrayList<WatjaiMeasure>> response = call.execute();
                watjaiMeasure = response.body();
                return watjaiMeasure;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class NotificationListAdapter extends BaseAdapter {
        ArrayList<WatjaiMeasure> notifications;

        public NotificationListAdapter() {
            super();
            notifications = new ArrayList<WatjaiMeasure>();
        }

        public void addNotification(ArrayList<WatjaiMeasure> notification) {
            if (notification != null) {
                notifications.addAll(0, notification);
            }

        }

        public WatjaiMeasure getNotifaction(int position) {
            return notifications.get(position);
        }

        @Override
        public int getCount() {
            return notifications.size();
        }

        @Override
        public WatjaiMeasure getItem(int position) {
            return notifications.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NotificationListItem item;

            if (convertView == null) {
                item = new NotificationListItem(Contextor.getInstance().getContext());
            } else {
                item = new NotificationListItem(parent.getContext());
            }

            WatjaiMeasure watjaiMeasure = notifications.get(position);

            String date = watjaiMeasure.getAlertTime();
            date = date.substring(2,10);
            String time = watjaiMeasure.getAlertTime();
            time = time.substring(11,19);
            String dateNotification = date + " " + time;

            if (watjaiMeasure != null) {
                item.setTimeText(dateNotification);
                item.setDescriptionText(watjaiMeasure.getComment());
            }

            if  (watjaiMeasure.getReadStatus().equalsIgnoreCase("read")) {
                item.setReadStatus();
            }

            //Date currentTime = Calendar.getInstance().getTime();


            // sub str แยกวัน แยกเวลา ล้ะค่อยลบกัน

            /*SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

            Date d2 = null;
            try {
                d2 = format.parse(dateNotification);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Get msec from each, and subtract.
            long diff = currentTime.getTime() - d2.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000);
            long diffDay = diff / (24 * 60 * 60 * 1000);
            System.out.println("Time in seconds: " + diffSeconds + " seconds.");
            System.out.println("Time in minutes: " + diffMinutes + " minutes.");
            System.out.println("Time in hours: " + diffHours + " hours.");
            System.out.println("Time in Day: " + diffDay + " days.");*/

            return item;
        }
    }
}