package com.exataid.aplicacaodeinsumos.banco.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.exataid.aplicacaodeinsumos.banco.modelos.AplicacaoInsumos;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface AplicacaoInsumosDAO extends ModeloDAO<AplicacaoInsumos> {

    @Query("SELECT * FROM AI_APLICACAOINSUMOS")
    ListenableFuture<List<AplicacaoInsumos>> buscarTodos();

    @Query("SELECT * FROM AI_APLICACAOINSUMOS WHERE nomeFuncionario=:funcionario")
    ListenableFuture<List<AplicacaoInsumos>> buscarPorFUncionario(String funcionario);

    @Query("SELECT * FROM AI_APLICACAOINSUMOS ORDER BY id desc limit 1")
    ListenableFuture<AplicacaoInsumos> buscarUltimoApontamento();

    @Query("SELECT * FROM AI_APLICACAOINSUMOS r WHERE r.nomeFuncionario=:funcionario and r.dataPlantio between :dataInicial and :dataFinal ORDER BY r.dataPlantio DESC, r.id desc ")
    ListenableFuture<List<AplicacaoInsumos>> buscarApontamentosPorData(String funcionario, String dataInicial, String dataFinal);

    @Query("SELECT * FROM AI_APLICACAOINSUMOS r where r.enviado=:enviado and r.nomeFuncionario=:funcionario")
    ListenableFuture<List<AplicacaoInsumos>> buscarPorNaoEnviado(String enviado, String funcionario);
}
