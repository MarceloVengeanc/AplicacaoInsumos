package com.exataid.aplicacaodeinsumos.banco.modelos;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "AI_SINC")
public class Sinc {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private Date dataFimSinc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public Date getDataFimSinc() {
        return dataFimSinc;
    }

    public void setDataFimSinc(@NonNull Date dataFimSinc) {
        this.dataFimSinc = dataFimSinc;
    }
}