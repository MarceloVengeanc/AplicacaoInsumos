package com.exataid.aplicacaodeinsumos.banco.modelos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "AI_DEPOSITOS")
public class Depositos implements Serializable {
    @PrimaryKey
    private long id;
    private String nomeDeposito;
    private int codDeposito;

    public Depositos() {
    }

    public Depositos(long id, String nomeDeposito, int codDeposito) {
        this.id = id;
        this.nomeDeposito = nomeDeposito;
        this.codDeposito = codDeposito;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeDeposito() {
        return nomeDeposito;
    }

    public void setNomeDeposito(String nomeDeposito) {
        this.nomeDeposito = nomeDeposito;
    }

    public int getCodDeposito() {
        return codDeposito;
    }

    public void setCodDeposito(int codDeposito) {
        this.codDeposito = codDeposito;
    }
}
