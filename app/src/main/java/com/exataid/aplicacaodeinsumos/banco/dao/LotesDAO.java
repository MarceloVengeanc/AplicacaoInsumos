package com.exataid.aplicacaodeinsumos.banco.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.exataid.aplicacaodeinsumos.banco.modelos.Lotes;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface LotesDAO extends ModeloDAO<Lotes>{
    @Query("SELECT * FROM AI_LOTES order by codTalhao asc")
    ListenableFuture<List<Lotes>> buscarTodos();

    @Query("SELECT * FROM AI_LOTES t WHERE t.codFazenda=:codFazenda")
    ListenableFuture<List<Lotes>> buscaTalhaoFazenda(int codFazenda);
}
