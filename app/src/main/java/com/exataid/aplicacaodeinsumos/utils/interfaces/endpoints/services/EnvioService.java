package com.exataid.aplicacaodeinsumos.utils.interfaces.endpoints.services;

import com.exataid.aplicacaodeinsumos.banco.modelos.AplicacaoInsumos;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EnvioService {
    //ENVIO
    @POST("post")
    Call<AplicacaoInsumos> enviarRegistros(@Body AplicacaoInsumos aplicacaoInsumos);

}