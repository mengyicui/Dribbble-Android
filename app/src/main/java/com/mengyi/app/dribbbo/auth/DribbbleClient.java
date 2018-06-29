package com.mengyi.app.dribbbo.auth;


import com.mengyi.app.dribbbo.model.AccessToken;
import com.mengyi.app.dribbbo.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DribbbleClient {

    @Headers("Accept: application/json")

    @FormUrlEncoded
    @POST("oauth/access_token")
    Call<AccessToken> getAccessToken(
            @Field("client_id") String CLIENT_ID,
            @Field("client_secret") String CLIENT_SECRET,
            @Field("code") String code
    );

    @GET("users/{user}/repos")
    Call<List<User>> listRepos(@Path("user") String user);
}