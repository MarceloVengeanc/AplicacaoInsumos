package com.exataid.aplicacaodeinsumos.banco.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.exataid.aplicacaodeinsumos.banco.modelos.LogSync;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;


@Dao
public interface LogSyncDAO extends ModeloDAO<LogSync> {
    @Query("SELECT * FROM AI_LOGSSYNC where tipo like '%' || :tipo || '%'")
    ListenableFuture<List<LogSync>> buscarTodasPorTipo(String tipo);

    @Query("SELECT * FROM AI_LOGSSYNC where tipo =:tipo")
    ListenableFuture<LogSync> buscarPorTipo(String tipo);

    @Query("DELETE FROM AI_LOGSSYNC ")
    ListenableFuture<Void> limparSyncs();
}