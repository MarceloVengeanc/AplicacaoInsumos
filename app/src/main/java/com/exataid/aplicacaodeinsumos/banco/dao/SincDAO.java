package com.exataid.aplicacaodeinsumos.banco.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.exataid.aplicacaodeinsumos.banco.modelos.Sinc;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;


@Dao
public interface SincDAO extends ModeloDAO<Sinc> {
    @Query("SELECT * FROM AI_SINC order by id desc limit 1 ")
    ListenableFuture<Sinc> buscarAtivo();

    @Query("SELECT * FROM AI_SINC order by id asc limit 1999 ")
    ListenableFuture<List<Sinc>> buscaTodos();
}