package com.exataid.aplicacaodeinsumos.banco.modelos;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "AI_APLICACAOINSUMOS")
public class AplicacaoInsumos implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long coletor;
    private String nomeFuncionario;
    private long matriculaFuncionario;
    private String dataPlantio;
    private String dataPlantioSalvo;
    private int codFazenda;
    private String nomeFazenda;
    private String enviado;

    public AplicacaoInsumos() {
    }

    public String getEnviado() {
        return enviado;
    }

    public void setEnviado(String enviado) {
        this.enviado = enviado;
    }

    public AplicacaoInsumos(long id, long coletor, String nomeFuncionario, long matriculaFuncionario, String dataPlantio, String dataPlantioSalvo, int codFazenda, String nomeFazenda, String enviado) {
        this.id = id;
        this.coletor = coletor;
        this.nomeFuncionario = nomeFuncionario;
        this.matriculaFuncionario = matriculaFuncionario;
        this.dataPlantio = dataPlantio;
        this.dataPlantioSalvo = dataPlantioSalvo;
        this.codFazenda = codFazenda;
        this.nomeFazenda = nomeFazenda;
        this.enviado = enviado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getColetor() {
        return coletor;
    }

    public void setColetor(long coletor) {
        this.coletor = coletor;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public long getMatriculaFuncionario() {
        return matriculaFuncionario;
    }

    public void setMatriculaFuncionario(long matriculaFuncionario) {
        this.matriculaFuncionario = matriculaFuncionario;
    }

    public String getDataPlantio() {
        return dataPlantio;
    }

    public void setDataPlantio(String dataPlantio) {
        this.dataPlantio = dataPlantio;
    }

    public String getDataPlantioSalvo() {
        return dataPlantioSalvo;
    }

    public void setDataPlantioSalvo(String dataPlantioSalvo) {
        this.dataPlantioSalvo = dataPlantioSalvo;
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
}
