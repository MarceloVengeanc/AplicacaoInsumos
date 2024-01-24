package com.exataid.aplicacaodeinsumos.banco.modelos;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "AI_ORDEMSERVICO")
public class OrdemServico implements Serializable {
    @PrimaryKey
    private long id;
    private int numOrdemServico;
    private int codCentroCusto;
    private String nomeCentroCusto;
    private int codOperacao;
    private String nomeOperacao;
    private int codFazenda;
    private String nomeFazenda;
    private double vazao;
    @Ignore
    private List<Lotes> talhoes;
    @Ignore
    private List<Insumos> insumos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumOrdemServico() {
        return numOrdemServico;
    }

    public void setNumOrdemServico(int numOrdemServico) {
        this.numOrdemServico = numOrdemServico;
    }

    public String getNomeCentroCusto() {
        return nomeCentroCusto;
    }

    public void setNomeCentroCusto(String nomeCentroCusto) {
        this.nomeCentroCusto = nomeCentroCusto;
    }

    public int getCodOperacao() {
        return codOperacao;
    }

    public void setCodOperacao(int codOperacao) {
        this.codOperacao = codOperacao;
    }

    public String getNomeOperacao() {
        return nomeOperacao;
    }

    public void setNomeOperacao(String nomeOperacao) {
        this.nomeOperacao = nomeOperacao;
    }

    public int getCodFazenda() {
        return codFazenda;
    }

    public void setCodFazenda(int codFazenda) {
        this.codFazenda = codFazenda;
    }

    public String getNomeFazenda() {
        return nomeFazenda;
    }

    public void setNomeFazenda(String nomeFazenda) {
        this.nomeFazenda = nomeFazenda;
    }

    public double getVazao() {
        return vazao;
    }

    public void setVazao(double vazao) {
        this.vazao = vazao;
    }

    public List<Lotes> getTalhoes() {
        return talhoes;
    }

    public void setTalhoes(List<Lotes> talhoes) {
        this.talhoes = talhoes;
    }

    public List<Insumos> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<Insumos> insumos) {
        this.insumos = insumos;
    }

    public int getCodCentroCusto() {
        return codCentroCusto;
    }

    public void setCodCentroCusto(int codCentroCusto) {
        this.codCentroCusto = codCentroCusto;
    }

    public OrdemServico(long id, int numOrdemServico, int codCentroCusto, String nomeCentroCusto, int codOperacao, String nomeOperacao, int codFazenda, String nomeFazenda, double vazao, List<Lotes> talhoes, List<Insumos> insumos) {
        this.id = id;
        this.numOrdemServico = numOrdemServico;
        this.codCentroCusto = codCentroCusto;
        this.nomeCentroCusto = nomeCentroCusto;
        this.codOperacao = codOperacao;
        this.nomeOperacao = nomeOperacao;
        this.codFazenda = codFazenda;
        this.nomeFazenda = nomeFazenda;
        this.vazao = vazao;
        this.talhoes = talhoes;
        this.insumos = insumos;
    }

    public OrdemServico() {
    }
}
