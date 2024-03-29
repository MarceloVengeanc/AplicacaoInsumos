package com.exataid.aplicacaodeinsumos.utils.interfaces.endpoints.repos;

import com.exataid.aplicacaodeinsumos.banco.modelos.ConfigWS;
import com.exataid.aplicacaodeinsumos.utils.interfaces.endpoints.services.EnvioService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnvioRepo {
    private static EnvioRepo instance;
    private final EnvioService service;


    public static EnvioRepo getInstance(ConfigWS servidor) {
        if (instance == null) {
            instance = new EnvioRepo(servidor);
        }
        return instance;
    }

    public EnvioRepo(ConfigWS servidor) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                // .baseUrl(String.format("%s:%s/",servidor.getUrl(),servidor.getPorta()))
                .baseUrl(String.format("https://httpbin.org/"))
//                .baseUrl(String.format("https://api.npoint.io/"))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(EnvioService.class);
    }

    public EnvioService getService() {
        return service;
    }
}