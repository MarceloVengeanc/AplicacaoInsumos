package com.exataid.aplicacaodeinsumos.banco.modelos;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "AI_LOTES")
public class Lotes implements Serializable {
    @PrimaryKey
    private long id;
    private int codFazenda;
    private int codTalhao;
    private float areaTalhao;
    private float loteTalhao;
    private String concluido="";

    public Lotes(long id, int codFazenda, int codTalhao, float areaTalhao, float loteTalhao) {
        this.id = id;
        this.codFazenda = codFazenda;
        this.codTalhao = codTalhao;
        this.areaTalhao = areaTalhao;
        this.loteTalhao = loteTalhao;
    }

    public Lotes() {
    }

    public float getLoteTalhao() {
        return loteTalhao;
    }

    public void setLoteTalhao(float loteTalhao) {
        this.loteTalhao = loteTalhao;
    }

    public String getConcluido() {
        return concluido;
    }

    public void setConcluido(String concluido) {
        this.concluido = concluido;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCodFazenda() {
        return codFazenda;
    }

    public void setCodFazenda(int codFazenda) {
        this.codFazenda = codFazenda;
    }

    public int getCodTalhao() {
        return codTalhao;
    }

    public void setCodTalhao(int codTalhao) {
        this.codTalhao = codTalhao;
    }

    public float getAreaTalhao() {
        return areaTalhao;
    }

    public void setAreaTalhao(float areaTalhao) {
        this.areaTalhao = areaTalhao;
    }
}
