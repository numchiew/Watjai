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
    @SerializedName("alertTime")
    @Expose
    private String alertTime;
    @SerializedName("measuringId")
    @Expose
    private String measuringId;
    @SerializedName("heartRate")
    @Expose
    private int heartRate;
    @SerializedName("abnormalStatus")
    @Expose
    private Boolean abnormalStatus;
    @SerializedName("abnormalDetail")
    @Expose
    private String abnormalDetail;
    @SerializedName("readStatus")
    @Expose
    private String readStatus;
    @SerializedName("comment")
    @Expose
    private String comment;

    public WatjaiMeasure(Parcel in) {
        id = in.readString();
        patId = in.readString();
        alertTime = in.readString();
        measuringId = in.readString();
        abnormalDetail = in.readString();
        readStatus = in.readString();
        comment = in.readString();
        measuringData = (ArrayList<Float>) in.readSerializable();
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

    public WatjaiMeasure() {

    }

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

    public Boolean getAbnormalStatus() {
        return abnormalStatus;
    }

    public void setAbnormalStatus(Boolean abnormalStatus) {
        this.abnormalStatus = abnormalStatus;
    }

    public String getAbnormalDetail() {
        return abnormalDetail;
    }

    public void setAbnormalDetail(String abnormalDetail) {
        this.abnormalDetail = abnormalDetail;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        dest.writeString(abnormalDetail);
        dest.writeString(readStatus);
        dest.writeString(comment);
        dest.writeSerializable(measuringData);
    }
}
