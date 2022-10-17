package com.example.raftaarquiz.Retrofit;

import com.example.raftaarquiz.Model.ProfileResponse;
import com.example.raftaarquiz.Model.RegistrationResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @Multipart
    @POST("updateprofile.php")
    Call<ProfileResponse> UpdateProfileData(
            @Part("id") RequestBody id,
            @Part("name") RequestBody username,
            @Part("contact_number") RequestBody mobile,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part MultipartBody.Part image_url,
            @Part("gender") RequestBody gender
    );
}