package com.exataid.aplicacaodeinsumos.banco.modelos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "AI_SISTEMAAPLICACAO")
public class SistemaAplicacao implements Serializable {
    @PrimaryKey
    private long id;
    private int codSistemaAplicacao;
    private String descSistemaAplicacao;

    public SistemaAplicacao() {
    }

    public SistemaAplicacao(long id, int codSistemaAplicacao, String descSistemaAplicacao) {
        this.id = id;
        this.codSistemaAplicacao = codSistemaAplicacao;
        this.descSistemaAplicacao = descSistemaAplicacao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCodSistemaAplicacao() {
        return codSistemaAplicacao;
    }

    public void setCodSistemaAplicacao(int codSistemaAplicacao) {
        this.codSistemaAplicacao = codSistemaAplicacao;
    }

    public String getDescSistemaAplicacao() {
        return descSistemaAplicacao;
    }

    public void setDescSistemaAplicacao(String descSistemaAplicacao) {
        this.descSistemaAplicacao = descSistemaAplicacao;
    }
}
