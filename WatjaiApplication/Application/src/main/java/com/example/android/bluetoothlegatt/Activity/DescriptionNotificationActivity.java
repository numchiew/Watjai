package com.example.android.bluetoothlegatt.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.bluetoothlegatt.Dao.WatjaiMeasure;
import com.example.android.bluetoothlegatt.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class DescriptionNotificationActivity extends AppCompatActivity {
    WatjaiMeasure watjaiMeasure;
    TextView tvHeartRate;
    ArrayList<Float> data;
    private LineGraphSeries series;
    private int sizeOfGraph = 0, lastX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_notification);

        watjaiMeasure = getIntent().getParcelableExtra("measure");
        tvHeartRate = (TextView) findViewById(R.id.tvHeartRate);

        tvHeartRate.setText(watjaiMeasure.getHeartRate()+"");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
