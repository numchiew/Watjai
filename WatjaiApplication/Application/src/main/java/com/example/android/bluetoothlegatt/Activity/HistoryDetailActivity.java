package com.example.android.bluetoothlegatt.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.bluetoothlegatt.Dao.WatjaiMeasure;
import com.example.android.bluetoothlegatt.Manager.HttpManager;
import com.example.android.bluetoothlegatt.R;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryDetailActivity extends AppCompatActivity {
    String measuringId;
    ArrayList<WatjaiMeasure> dao;
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    private int totalEcg = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        measuringId = getIntent().getStringExtra("historyDetail");

        Call<ArrayList<WatjaiMeasure>> call = HttpManager.getInstance().getService().findMeasuring(measuringId);
        call.enqueue(new Callback<ArrayList<WatjaiMeasure>>() {
            @Override
            public void onResponse(Call<ArrayList<WatjaiMeasure>> call,
                                   Response<ArrayList<WatjaiMeasure>> response) {
                if (response.isSuccessful()) {
                    dao = new ArrayList<WatjaiMeasure>();
                    dao = response.body();

                    totalEcg = dao.get(0).getMeasuringData().size();
                    for (int l = 0; l < totalEcg; l++) {
                        series.appendData(new DataPoint(lastX++, dao.get(0).getMeasuringData().get(l)), true, totalEcg);
                    }
                } else {
                    try {
                        Toast.makeText(HistoryDetailActivity.this,
                                response.errorBody().string(),
                                Toast.LENGTH_SHORT)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<WatjaiMeasure>> call,
                                  Throwable t) {
                if (Locale.getDefault().getLanguage().equals("th")) {
                    Toast.makeText(HistoryDetailActivity.this, "กรุณาเชื่อมต่ออินเทอร์เน็ต", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HistoryDetailActivity.this, "Disconnect internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        GraphView graph = (GraphView) findViewById(R.id.historyGraph);
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(5);
        viewport.setMinX(0);
        viewport.setMaxX(80);
        viewport.setScrollable(true);
        graph.getViewport().setScalableY(true);
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
