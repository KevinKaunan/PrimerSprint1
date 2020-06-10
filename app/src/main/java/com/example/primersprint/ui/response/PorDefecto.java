package com.example.primersprint.ui.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PorDefecto {
    @SerializedName("message")
    @Expose
    private String message;
    public String getMessage() {
        return message;
    }
}
