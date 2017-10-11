package com.example.android.bluetoothlegatt.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.bluetoothlegatt.Dao.PatientItemDao;
import com.example.android.bluetoothlegatt.Manager.HttpManager;
import com.example.android.bluetoothlegatt.R;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    EditText edtFirstName, edtLastName, edtAddress, edtSubDistrict,
            edtDistrict, edtProvince, edtMobile;
    Button btnSubmit;
    PatientItemDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        dao = getIntent().getParcelableExtra("dao");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findId();
        initInstance();

    }

    private void initInstance() {
        edtFirstName.setText(dao.getPatFirstName());
        edtLastName.setText(dao.getPatLastName());
        edtAddress.setText(dao.getAddress());
        edtSubDistrict.setText(dao.getSubDistrict());
        edtDistrict.setText(dao.getDistrict());
        edtProvince.setText(dao.getProvince());

        edtMobile.setText(dao.getPatTel());

        btnSubmit.setOnClickListener(submitEditProfile);

    }

    private void setUpdateData() {
        dao.setId(null);
        dao.setPatFirstName(edtFirstName.getText().toString());
        dao.setPatLastName(edtLastName.getText().toString());
        dao.setAddress(edtAddress.getText().toString());
        dao.setSubDistrict(edtSubDistrict.getText().toString());
        dao.setDistrict(edtDistrict.getText().toString());
        dao.setProvince(edtProvince.getText().toString());
        dao.setPatTel(edtMobile.getText().toString());
        dao.setSex(dao.getSex());
    }

    private void findId() {
        edtFirstName = (EditText) findViewById(R.id.edtFirstName);
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtSubDistrict = (EditText) findViewById(R.id.edtSubDistrict);
        edtDistrict = (EditText) findViewById(R.id.edtDistrict);
        edtProvince = (EditText) findViewById(R.id.edtProvince);
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /******
     *  Listener
     */

    View.OnClickListener submitEditProfile = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setUpdateData();
            Call<PatientItemDao> call = HttpManager.getInstance().getService().updatePatient(dao, dao.getPatId());
            call.enqueue(new Callback<PatientItemDao>() {
                @Override
                public void onResponse(Call<PatientItemDao> call, Response<PatientItemDao> response) {
                    if (response.isSuccessful()) {
                        if (Locale.getDefault().getLanguage().equals("th")) {
                            Toast.makeText(EditProfileActivity.this, "แก้ไขข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Edit success", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        try {
                            Toast.makeText(EditProfileActivity.this, response.errorBody().string()
                                    , Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PatientItemDao> call, Throwable throwable) {
                    Toast.makeText(EditProfileActivity.this, throwable.toString()
                            , Toast.LENGTH_LONG).show();
                }
            });

            Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        }

    };
}
