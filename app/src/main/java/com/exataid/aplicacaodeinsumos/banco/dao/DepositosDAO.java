package com.exataid.aplicacaodeinsumos.banco.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.exataid.aplicacaodeinsumos.banco.modelos.Depositos;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface DepositosDAO extends ModeloDAO<Depositos> {
    @Query("SELECT * FROM AI_DEPOSITOS ORDER BY codDeposito asc")
    ListenableFuture<List<Depositos>> buscarTodas();
}
