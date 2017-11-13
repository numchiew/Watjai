package com.example.android.bluetoothlegatt.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.bluetoothlegatt.Dao.WatjaiMeasure;
import com.example.android.bluetoothlegatt.R;

public class DescriptionNotificationActivity extends AppCompatActivity {

    private WatjaiMeasure watjaiMeasure;
    TextView tvDate, tvTime, tvComment;
    String date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvComment = (TextView) findViewById(R.id.tvComment);
        watjaiMeasure = new WatjaiMeasure();
        watjaiMeasure = getIntent().getParcelableExtra("measure");
        tvComment.setText(watjaiMeasure.getComment());
        date = watjaiMeasure.getAlertTime();
        date = date.substring(0,10);
        time = watjaiMeasure.getAlertTime();
        time = time.substring(11,16);
        tvDate.setText(date);
        tvTime.setText(time);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
