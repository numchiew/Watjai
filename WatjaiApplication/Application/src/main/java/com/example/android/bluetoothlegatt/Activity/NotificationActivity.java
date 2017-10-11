package com.example.android.bluetoothlegatt.Activity;

import android.content.Intent;
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
import com.example.android.bluetoothlegatt.R;
import com.example.android.bluetoothlegatt.View.NotificationListItem;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    ListView listView;
    NotificationListAdapter notificationListAdapter;
    ArrayList<WatjaiMeasure> watjaiMeasure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        watjaiMeasure = new ArrayList<>();
        watjaiMeasure = (ArrayList<WatjaiMeasure>) getIntent().getSerializableExtra("notification");
        listView = (ListView) findViewById(R.id.notificationList);
        notificationListAdapter = new NotificationListAdapter();
        if (notificationListAdapter.isEmpty()) {
            notificationListAdapter.addNotification(watjaiMeasure);
        } else {
            notificationListAdapter.addTopNotification(watjaiMeasure);
        }
        listView.setAdapter(notificationListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WatjaiMeasure measure = notificationListAdapter.getItem(position);
                if (measure != null) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.readStatus);
                    imageView.setVisibility(View.GONE);
                    Intent intent = new Intent(NotificationActivity.this, DescriptionNotificationActivity.class);
                    intent.putExtra("measure", measure);
                    startActivity(intent);
                }
            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

class NotificationListAdapter extends BaseAdapter {
    ArrayList<WatjaiMeasure> notifications;

    public NotificationListAdapter() {
        super();
        notifications = new ArrayList<WatjaiMeasure>();
    }

    public void addTopNotification(ArrayList<WatjaiMeasure> newNotification) {
        if (newNotification != null) {
            notifications.addAll(notifications.size(), newNotification);
        }
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

        if (watjaiMeasure != null) {
            item.setTimeText(watjaiMeasure.getAlertTime());
            item.setDescriptionText("อัตราการเต้นของหัวใจ\n"+watjaiMeasure.getHeartRate()+" ครั้ง/นาที");
        }

        return item;
    }
}