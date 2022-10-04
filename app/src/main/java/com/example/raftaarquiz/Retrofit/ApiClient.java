package com.example.raftaarquiz.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static String BASE_URL = "https://adminapp.tech/raftarquiz/userapi/";
    private static ApiClient apiClient;
    private static Retrofit retrofit;

    private ApiClient() {
        retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized ApiClient getInstance() {
        if (apiClient == null) {
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    public UsersServices getApi() {
        return retrofit.create(UsersServices.class);
    }

}
