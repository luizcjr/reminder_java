package com.luiz.reminderjava.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notes {

    @SerializedName("_id")
    @Expose
    private String id;
    private String title;
    private String description;
    private String date;
    private Boolean is_notified;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public Boolean getIs_notified() {
        return is_notified;
    }
}
