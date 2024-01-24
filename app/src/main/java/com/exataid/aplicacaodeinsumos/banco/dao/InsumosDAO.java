package com.exataid.aplicacaodeinsumos.banco.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.exataid.aplicacaodeinsumos.banco.modelos.Insumos;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface InsumosDAO extends ModeloDAO<Insumos> {
    @Query("SELECT * FROM AI_INSUMOS")
    ListenableFuture<List<Insumos>> buscarTodos();

    @Query("SELECT * FROM AI_INSUMOS i where i.codOS=:codOS")
    ListenableFuture<List<Insumos>> buscarPorOS(long codOS);
}
