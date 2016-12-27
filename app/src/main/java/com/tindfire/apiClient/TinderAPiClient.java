package com.tindfire.apiClient;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vcareall on 26/12/16.
 */
public class TinderAPiClient {
    public static final String BASE_URL="https://api.gotinder.com";
    public static Retrofit retrofit=null;
    public static Retrofit getCLient(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
