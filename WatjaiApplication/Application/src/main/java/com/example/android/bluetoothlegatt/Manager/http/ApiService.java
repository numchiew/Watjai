package com.example.android.bluetoothlegatt.Manager.http;

import com.example.android.bluetoothlegatt.Dao.PatientItemDao;
import com.example.android.bluetoothlegatt.Dao.WatjaiMeasure;
import com.example.android.bluetoothlegatt.Dao.WatjaiNormal;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by SirisakPks on 24/8/2560.
 */

public interface ApiService {

    @GET("patients/{id}")
    Call<PatientItemDao> loadPatient(@Path("id") String id);

    @PATCH("patients/{id}" )
    @Headers({"Content-Type: application/json"})
    Call<PatientItemDao> updatePatient(@Body PatientItemDao dao, @Path("id") String id);

    @POST("watjainormal" )
    @Headers({"Content-Type: application/json"})
    Call<WatjaiNormal> insertECG(@Body WatjaiNormal ecg);

    @GET("watjainormal/{id}/history/5minute")
    Call<ArrayList<WatjaiNormal>> loadWatjai5Minute(@Path("id") String id);

    @GET("watjaimeasure/showabnormal/{patId}/after/{measuringId}")
    Call<ArrayList<WatjaiMeasure>> loadWatjaiMeasureAlert(@Path("patId") String patId, @Path("measuringId") String measuringId);
}
