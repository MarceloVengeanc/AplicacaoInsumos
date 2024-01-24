package com.exataid.aplicacaodeinsumos.banco.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.exataid.aplicacaodeinsumos.banco.modelos.ConfigWS;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;


@Dao
public interface ConfigWSDAO extends ModeloDAO<ConfigWS> {
    @Query("SELECT * FROM AI_CONFIGWS order by prioridade asc")
    ListenableFuture<List<ConfigWS>> buscarTodas();

    @Query("SELECT * FROM AI_CONFIGWS")
    ListenableFuture<ConfigWS>buscarUm();

    @Query("SELECT * FROM AI_CONFIGWS WHERE prioridade=:pri")
    ListenableFuture<ConfigWS> buscarPorPrioridade(Integer pri);

    @Query("DELETE FROM AI_CONFIGWS")
    ListenableFuture<Void> limpar();
}