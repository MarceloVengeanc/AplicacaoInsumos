package com.exataid.aplicacaodeinsumos.banco.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.exataid.aplicacaodeinsumos.banco.modelos.AutenticaLogin;
import com.google.common.util.concurrent.ListenableFuture;

@Dao
public interface AutenticaLoginDAO extends ModeloDAO<AutenticaLogin> {
    @Query("SELECT * FROM AI_AUTENTICALOGIN ORDER BY ID DESC LIMIT 1")
    ListenableFuture<AutenticaLogin> buscarAtivo();

    @Query("SELECT * FROM AI_AUTENTICALOGIN WHERE id = :id")
    ListenableFuture<AutenticaLogin> buscarPorId(Long id);
}
