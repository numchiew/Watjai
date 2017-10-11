package com.example.android.bluetoothlegatt.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.bluetoothlegatt.Dao.WatjaiNormal;
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

public class HistoryActivity extends AppCompatActivity {
    ArrayList<WatjaiNormal> dao;
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    private int countEcg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Call<ArrayList<WatjaiNormal>> call = HttpManager.getInstance().getService().loadWatjai5Minute("PA1709001");
        call.enqueue(new Callback<ArrayList<WatjaiNormal>>() {
            @Override
            public void onResponse(Call<ArrayList<WatjaiNormal>> call,
                                   Response<ArrayList<WatjaiNormal>> response) {
                if (response.isSuccessful()) {
                    dao = new ArrayList<WatjaiNormal>();
                    dao = response.body();
                    for (int i = 0; i < dao.size(); i++) {
                        if (dao.get(i).getPatId().contentEquals("PA1709001") ) {
                            countEcg += dao.get(i).getMeasureData().size();
                            for (int l = 0; l < dao.get(i).getMeasureData().size(); l++) {
                                series.appendData(new DataPoint(lastX++, dao.get(i).getMeasureData().get(l)), true, countEcg);
                            }
                        }
                    }
                } else {
                    try {
                        Toast.makeText(HistoryActivity.this,
                                response.errorBody().string(),
                                Toast.LENGTH_SHORT)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<WatjaiNormal>> call,
                                  Throwable t) {
                if (Locale.getDefault().getLanguage().equals("th")) {
                    Toast.makeText(HistoryActivity.this, "กรุณาเชื่อมต่ออินเทอร์เน็ต", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HistoryActivity.this, "Disconnect internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        GraphView graph = (GraphView) findViewById(R.id.graphHistory);
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




