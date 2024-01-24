package com.exataid.aplicacaodeinsumos.banco.dao;

import androidx.room.Dao;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import com.exataid.aplicacaodeinsumos.banco.modelos.SistemaAplicacao;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface SistemaAplicacaoDAO extends ModeloDAO<SistemaAplicacao> {
    @Query("SELECT * FROM AI_SISTEMAAPLICACAO")
    ListenableFuture<List<SistemaAplicacao>> buscarTodos();
}
