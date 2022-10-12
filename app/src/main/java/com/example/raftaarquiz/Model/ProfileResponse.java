package com.example.raftaarquiz.Model;

import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("status")
    String status;

    @SerializedName("message")
    String message;


    public ProfileResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
