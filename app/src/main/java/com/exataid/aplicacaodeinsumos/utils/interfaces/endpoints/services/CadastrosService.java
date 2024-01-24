package com.exataid.aplicacaodeinsumos.utils.interfaces.endpoints.services;

import com.exataid.aplicacaodeinsumos.banco.modelos.AplicacaoInsumos;
import com.exataid.aplicacaodeinsumos.banco.modelos.Depositos;
import com.exataid.aplicacaodeinsumos.banco.modelos.Funcionario;
import com.exataid.aplicacaodeinsumos.banco.modelos.Lotes;
import com.exataid.aplicacaodeinsumos.banco.modelos.OrdemServico;
import com.exataid.aplicacaodeinsumos.banco.modelos.SistemaAplicacao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CadastrosService {

    //Busca Fazendas
    @GET("cf740e4f7cecf60e6d48")
    Call<List<Depositos>> baixarTodasFazendas();

    //Busca Talhões
    @GET("45de8105ab10c26f5f3a")
    Call<List<Lotes>> baixarTodosTalhoes();

    //Busca Funcionarios
    @GET("655afe3ab06b338099da")
    Call<List<Funcionario>> baixarTodosFuncionarios();

    @GET("562fd66944b4fdd40837")
    Call<List<OrdemServico>> baixarTodasOrdemServico();

    @GET("4610cf4f0b45fb971d1b")
    Call<List<SistemaAplicacao>> baixarTodosSistemaAplicacao();

    @POST("post")
    Call<AplicacaoInsumos> enviarRequisicaoFinalizada(@Body AplicacaoInsumos aplicacaoInsumos);

}