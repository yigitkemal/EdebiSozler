package com.example.edebisozler.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Example {

    @SerializedName("edebi_sozler")
    @Expose
    private List<Quotes> edebiSozler = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<Quotes> getEdebiSozler() {
        return edebiSozler;
    }

    public void setEdebiSozler(List<Quotes> edebiSozler) {
        this.edebiSozler = edebiSozler;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}