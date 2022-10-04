package com.example.raftaarquiz.Model;

public class RegistrationResponse {
    String status;
    String response;

    public RegistrationResponse(String status, String response) {
        this.status = status;
        this.response = response;
    }

    public String getStatus() {
        return response;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
