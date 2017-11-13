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

    @GET("patients/{patId}")
    Call<PatientItemDao> loadPatient(@Path("patId") String id);

    @PATCH("patients/{patId}" )
    @Headers({"Content-Type: application/json"})
    Call<PatientItemDao> updatePatient(@Body PatientItemDao dao, @Path("patId") String id);

    @POST("watjainormal" )
    @Headers({"Content-Type: application/json"})
    Call<WatjaiNormal> insertECG(@Body WatjaiNormal ecg);

    @POST("watjaimeasure" )
    @Headers({"Content-Type: application/json"})
    Call<WatjaiMeasure> insertECGtoDetecing(@Body WatjaiMeasure ecg);

    @GET("patients/{patId}/history")
    Call<ArrayList<WatjaiMeasure>> loadHistory(@Path("patId") String id);

    @GET("watjaimeasure/showabnormal/{patId}/after/{measuringId}")
    Call<ArrayList<WatjaiMeasure>> loadWatjaiMeasureAlert(@Path("patId") String patId, @Path("measuringId") String measuringId);

    @PATCH("watjaimeasure/changereadstatus/{measuringId}")
    @Headers({"Content-Type: application/json"})
    Call<WatjaiMeasure> chageReadStatus(@Body WatjaiMeasure dao,@Path("measuringId") String measuringId);

    @GET("watjaimeasure/{measuringId}")
    Call<ArrayList<WatjaiMeasure>> findMeasuring(@Path("measuringId") String id);
}
