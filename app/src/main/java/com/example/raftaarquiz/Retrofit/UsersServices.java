package com.example.raftaarquiz.Retrofit;

import com.example.raftaarquiz.Model.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UsersServices {

    @FormUrlEncoded
    @POST("register.php")
    Call<RegistrationResponse> SendUserDetails_server(
            @Field("name") String username,
            @Field("email") String email,
            @Field("contact_number") String mobile_no,
            @Field("password") String password,
            @Field("gender") String gender
    );
}