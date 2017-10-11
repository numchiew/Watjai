package com.example.android.bluetoothlegatt.Dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by SirisakPks on 27/8/2560.
 */

public class WatjaiMeasure implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("measuringData")
    @Expose
    private ArrayList<Float> measuringData = null;
    @SerializedName("patId")
    @Expose
    private String patId;
    @SerializedName("abnormalStatus")
    @Expose
    private Boolean abnormalStatus;
    @SerializedName("alertTime")
    @Expose
    private String alertTime;
    @SerializedName("measuringId")
    @Expose
    private String measuringId;
    @SerializedName("heartRate")
    @Expose
    private int heartRate;

    protected WatjaiMeasure(Parcel in) {
        id = in.readString();
        patId = in.readString();
        alertTime = in.readString();
        measuringId = in.readString();
        measuringData = (ArrayList<Float>) in.readSerializable();
        heartRate = in.readInt();
    }

    public static final Creator<WatjaiMeasure> CREATOR = new Creator<WatjaiMeasure>() {
        @Override
        public WatjaiMeasure createFromParcel(Parcel in) {
            return new WatjaiMeasure(in);
        }

        @Override
        public WatjaiMeasure[] newArray(int size) {
            return new WatjaiMeasure[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Float> getMeasuringData() {
        return measuringData;
    }

    public void setMeasuringData(ArrayList<Float> measuringData) {
        this.measuringData = measuringData;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public Boolean getAbnormalStatus() {
        return abnormalStatus;
    }

    public void setAbnormalStatus(Boolean abnormalStatus) {
        this.abnormalStatus = abnormalStatus;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    public String getMeasuringId() {
        return measuringId;
    }

    public void setMeasuringId(String measuringId) {
        this.measuringId = measuringId;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(patId);
        dest.writeString(alertTime);
        dest.writeString(measuringId);
        dest.writeSerializable(measuringData);
        dest.writeInt(heartRate);
    }
}
