package com.example.android.bluetoothlegatt.Dao;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by SirisakPks on 25/8/2560.
 */

public class PatientItemDao implements Parcelable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("patFirstName")
    @Expose
    private String patFirstName;
    @SerializedName("patLastName")
    @Expose
    private String patLastName;
    @SerializedName("birthDay")
    @Expose
    private Date birthDay;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("subDistrict")
    @Expose
    private String subDistrict;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("province")
    @Expose
    private String province;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("patTel")
    @Expose
    private String patTel;
    @SerializedName("bloodType")
    @Expose
    private String bloodType;
    @SerializedName("docId")
    @Expose
    private String docId;
    @SerializedName("patId")
    @Expose
    private String patId;
    @SerializedName("underlyingDisease")
    @Expose
    private String underlyingDisease;
    @SerializedName("patPic")
    @Expose
    private String patPic;
    @SerializedName("Doctor")
    @Expose
    private List<Doctor> doctor = null;
    @SerializedName("sex")
    @Expose
    private String sex;

    public PatientItemDao(Parcel in) {
        id = in.readString();
        patFirstName = in.readString();
        patLastName = in.readString();
        address = in.readString();
        subDistrict = in.readString();
        district = in.readString();
        province = in.readString();
        postcode = in.readString();
        patTel = in.readString();
        bloodType = in.readString();
        docId = in.readString();
        patId = in.readString();
        underlyingDisease = in.readString();
        patPic = in.readString();
        sex = in.readString();
    }

    public static final Creator<PatientItemDao> CREATOR = new Creator<PatientItemDao>() {
        @Override
        public PatientItemDao createFromParcel(Parcel in) {
            return new PatientItemDao(in);
        }

        @Override
        public PatientItemDao[] newArray(int size) {
            return new PatientItemDao[size];
        }
    };

    public PatientItemDao() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(patFirstName);
        dest.writeString(patLastName);
        dest.writeString(address);
        dest.writeString(subDistrict);
        dest.writeString(district);
        dest.writeString(province);
        dest.writeString(postcode);
        dest.writeString(patTel);
        dest.writeString(bloodType);
        dest.writeString(docId);
        dest.writeString(patId);
        dest.writeString(underlyingDisease);
        dest.writeString(patPic);
        dest.writeString(sex);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatFirstName() {
        return patFirstName;
    }

    public void setPatFirstName(String patFirstName) {
        this.patFirstName = patFirstName;
    }

    public String getPatLastName() {
        return patLastName;
    }

    public void setPatLastName(String patLastName) {
        this.patLastName = patLastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPatTel() {
        return patTel;
    }

    public void setPatTel(String patTel) {
        this.patTel = patTel;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getUnderlyingDisease() {
        return underlyingDisease;
    }

    public void setUnderlyingDisease(String underlyingDisease) {
        this.underlyingDisease = underlyingDisease;
    }

    public String getPatPic() {
        return patPic;
    }

    public void setPatPic(String patPic) {
        this.patPic = patPic;
    }

    public List<Doctor> getDoctor() {
        return doctor;
    }

    public void setDoctor(List<Doctor> doctor) {
        this.doctor = doctor;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
