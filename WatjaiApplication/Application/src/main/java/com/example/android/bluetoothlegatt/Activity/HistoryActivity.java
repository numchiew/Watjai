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
import android.widget.ListView;

import com.example.android.bluetoothlegatt.Dao.WatjaiMeasure;
import com.example.android.bluetoothlegatt.Manager.Contextor;
import com.example.android.bluetoothlegatt.Manager.HttpManager;
import com.example.android.bluetoothlegatt.R;
import com.example.android.bluetoothlegatt.View.HistoryListItem;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private ListView historyListView;
    private ArrayList<WatjaiMeasure> history;
    private HistoryListAdapter historyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        historyListView = (ListView)findViewById(R.id.historyList);
        historyListAdapter = new HistoryListAdapter();
        NetworkCall networkCall = new NetworkCall();
        networkCall.execute();
        try {
            Thread.sleep(195);
            historyListAdapter.addHistory(history);
            historyListView.setAdapter(historyListAdapter);
            historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    WatjaiMeasure historyDetail = history.get(position);
                    if  (historyDetail != null) {
                        Intent intent = new Intent(HistoryActivity.this, HistoryDetailActivity.class);
                        intent.putExtra("historyDetail", historyDetail.getMeasuringId());
                        System.out.println(historyDetail.getMeasuringId());
                        startActivity(intent);
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class HistoryListAdapter extends BaseAdapter {
        ArrayList<WatjaiMeasure> historys;

        public HistoryListAdapter() {
            super();
            historys = new ArrayList<WatjaiMeasure>();
        }

        public void addHistory(ArrayList<WatjaiMeasure> history) {
            if (history != null) {
                historys.addAll(0, history);
            }
        }

        public WatjaiMeasure getHistory(int position) {
            return historys.get(position);
        }

        @Override
        public int getCount() {
            return historys.size();
        }

        @Override
        public WatjaiMeasure getItem(int position) {
            return historys.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HistoryListItem item;

            if (convertView == null) {
                item = new HistoryListItem(Contextor.getInstance().getContext());
            } else {
                item = new HistoryListItem(parent.getContext());
            }

            WatjaiMeasure watjaiMeasure = historys.get(position);
            String date = watjaiMeasure.getAlertTime();
            date = date.substring(2,10);
            String time = watjaiMeasure.getAlertTime();
            time = time.substring(11,19);
            String dateNotification = date + " " + time;

            if (watjaiMeasure != null) {
                item.setMeasuringId(watjaiMeasure.getMeasuringId());
                item.setHeartRate(watjaiMeasure.getHeartRate()+" ครั้ง/นาที");
                item.setDateTime(dateNotification);
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

    private class NetworkCall extends AsyncTask<Call, Integer, ArrayList<WatjaiMeasure>> {

        @Override
        protected void onPostExecute(ArrayList<WatjaiMeasure> result) {
            history = result;
        }

        @Override
        protected ArrayList<WatjaiMeasure> doInBackground(Call... params) {
            try {
                Call<ArrayList<WatjaiMeasure>> call = HttpManager.getInstance().getService().loadHistory("PA1709001");
                Response<ArrayList<WatjaiMeasure>> response = call.execute();
                history = response.body();
                return history;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}




