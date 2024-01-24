package com.exataid.aplicacaodeinsumos.banco.modelos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "AI_INSUMOS")
public class Insumos implements Serializable {
    @PrimaryKey
    private long id;
    private long codInsumo;
    private String descInsumo;
    private double quantidadeProgramado;
    private double quantidadeAplicado;

    public int getCodDp() {
        return codDp;
    }

    public void setCodDp(int codDp) {
        this.codDp = codDp;
    }

    public String getDescDp() {
        return descDp;
    }

    public void setDescDp(String descDp) {
        this.descDp = descDp;
    }

    private int codDp;
    private String descDp;
    private long codOS;

    public Insumos() {
    }

    public double getQuantidadeAplicado() {
        return quantidadeAplicado;
    }

    public void setQuantidadeAplicado(double quantidadeAplicado) {
        this.quantidadeAplicado = quantidadeAplicado;
    }

    public Insumos(long id, long codInsumo, String descInsumo, double quantidadeProgramado, double quantidadeAplicado, long codOS) {
        this.id = id;
        this.codInsumo = codInsumo;
        this.descInsumo = descInsumo;
        this.quantidadeProgramado = quantidadeProgramado;
        this.quantidadeAplicado = quantidadeAplicado;
        this.codOS = codOS;
    }

    public long getCodOS() {
        return codOS;
    }

    public void setCodOS(long codOS) {
        this.codOS = codOS;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCodInsumo() {
        return codInsumo;
    }

    public void setCodInsumo(long codInsumo) {
        this.codInsumo = codInsumo;
    }

    public String getDescInsumo() {
        return descInsumo;
    }

    public void setDescInsumo(String descInsumo) {
        this.descInsumo = descInsumo;
    }

    public double getQuantidadeProgramado() {
        return quantidadeProgramado;
    }

    public void setQuantidadeProgramado(double quantidadeProgramado) {
        this.quantidadeProgramado = quantidadeProgramado;
    }
}
