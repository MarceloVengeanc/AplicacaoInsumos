package com.exataid.aplicacaodeinsumos.banco.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.exataid.aplicacaodeinsumos.banco.modelos.OrdemServico;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface OrdemServicoDAO extends ModeloDAO<OrdemServico> {
    @Query("SELECT * FROM AI_ORDEMSERVICO")
    ListenableFuture<List<OrdemServico>> buscarTodas();
}
