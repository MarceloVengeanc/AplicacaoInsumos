package com.exataid.aplicacaodeinsumos.banco.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.exataid.aplicacaodeinsumos.banco.modelos.Funcionario;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface FuncionarioDAO extends ModeloDAO<Funcionario> {

    @Query("SELECT * FROM AI_FUNCIONARIO")
    ListenableFuture<List<Funcionario>> buscarTodos();

    @Query("SELECT * FROM AI_FUNCIONARIO WHERE login = :login and senha = :senha")
    ListenableFuture<Funcionario> validarLogin(String login, String senha);
}
