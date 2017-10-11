package com.example.android.bluetoothlegatt.Dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SirisakPks on 27/8/2560.
 */

public class WatjaiNormal {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("measureData")
    @Expose
    private ArrayList<Float> measureData = null;
    @SerializedName("patId")
    @Expose
    private String patId;
    @SerializedName("measureTime")
    @Expose
    private String measureTime;
    @SerializedName("measureId")
    @Expose
    private String measureId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Float> getMeasureData() {
        return measureData;
    }

    public void setMeasureData(ArrayList<Float> measureData) {
        this.measureData = measureData;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(String measureTime) {
        this.measureTime = measureTime;
    }

    public String getMeasureId() {
        return measureId;
    }

    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

}
