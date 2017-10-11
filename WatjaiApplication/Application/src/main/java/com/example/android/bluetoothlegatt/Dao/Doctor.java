package com.example.android.bluetoothlegatt.Dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SirisakPks on 26/8/2560.
 */

public class Doctor {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("docTitle")
    @Expose
    private String docTitle;
    @SerializedName("docFirstName")
    @Expose
    private String docFirstName;
    @SerializedName("docLastName")
    @Expose
    private String docLastName;
    @SerializedName("docTel")
    @Expose
    private String docTel;
    @SerializedName("docId")
    @Expose
    private String docId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocFirstName() {
        return docFirstName;
    }

    public void setDocFirstName(String docFirstName) {
        this.docFirstName = docFirstName;
    }

    public String getDocLastName() {
        return docLastName;
    }

    public void setDocLastName(String docLastName) {
        this.docLastName = docLastName;
    }

    public String getDocTel() {
        return docTel;
    }

    public void setDocTel(String docTel) {
        this.docTel = docTel;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
