package com.tindfire.apiClient;

import com.tindfire.model.AuthModel.AuthRequest;
import com.tindfire.model.AuthModel.AuthResponceExample;
import com.tindfire.model.RecomondationModel.RecomondationResponce;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * Created by vcareall on 26/12/16.
 */
public interface TinderAPiInterface {
    @Headers("Content-Type:application/json")
    @POST("/auth")
    Call<AuthResponceExample> getAuthResponceExampleCall(@Body AuthRequest request);

    @GET("/user/recs?locale=en")
    Call<RecomondationResponce> getrRecomondationResponceCall(@Header("X-Auth-Token") String token);






}
