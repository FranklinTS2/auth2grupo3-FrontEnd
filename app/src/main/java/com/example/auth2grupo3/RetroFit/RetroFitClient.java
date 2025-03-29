package com.example.auth2grupo3.RetroFit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {
    private static Retrofit retrofit;
    private static final String baseURL = "http://192.168.88.115:3000/api/";//IP de la computadora
    public static Retrofit getRetrofitInstance() {
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
