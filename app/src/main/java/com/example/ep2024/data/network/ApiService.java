package com.example.ep2024.data.network;

import com.example.ep2024.data.model.UserLoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    Call<UserLoginResponse> login(@Field("username") String username, @Field("password") String password);
}
