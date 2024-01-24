package com.exataid.aplicacaodeinsumos;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;

import com.exataid.aplicacaodeinsumos.banco.dao.AplicacaoInsumosDAO;
import com.exataid.aplicacaodeinsumos.banco.dao.AutenticaLoginDAO;
import com.exataid.aplicacaodeinsumos.banco.dao.ColetorDAO;
import com.exataid.aplicacaodeinsumos.banco.dao.ConfigWSDAO;
import com.exataid.aplicacaodeinsumos.banco.dao.DepositosDAO;
import com.exataid.aplicacaodeinsumos.banco.dao.FuncionarioDAO;
import com.exataid.aplicacaodeinsumos.banco.dao.InsumosDAO;
import com.exataid.aplicacaodeinsumos.banco.dao.LogSyncDAO;
import com.exataid.aplicacaodeinsumos.banco.dao.LotesDAO;
import com.exataid.aplicacaodeinsumos.banco.dao.OrdemServicoDAO;
import com.exataid.aplicacaodeinsumos.banco.dao.SincDAO;
import com.exataid.aplicacaodeinsumos.banco.dao.SistemaAplicacaoDAO;
import com.exataid.aplicacaodeinsumos.banco.modelos.AplicacaoInsumos;
import com.exataid.aplicacaodeinsumos.banco.modelos.AutenticaLogin;
import com.exataid.aplicacaodeinsumos.banco.modelos.Coletor;
import com.exataid.aplicacaodeinsumos.banco.modelos.ConfigWS;
import com.exataid.aplicacaodeinsumos.banco.modelos.Depositos;
import com.exataid.aplicacaodeinsumos.banco.modelos.Funcionario;
import com.exataid.aplicacaodeinsumos.banco.modelos.Insumos;
import com.exataid.aplicacaodeinsumos.banco.modelos.LogSync;
import com.exataid.aplicacaodeinsumos.banco.modelos.Lotes;
import com.exataid.aplicacaodeinsumos.banco.modelos.OrdemServico;
import com.exataid.aplicacaodeinsumos.banco.modelos.Sinc;
import com.exataid.aplicacaodeinsumos.banco.modelos.SistemaAplicacao;
import com.exataid.aplicacaodeinsumos.utils.Conversores;

@Database(
        entities = {
                Depositos.class,
                Lotes.class,
                Funcionario.class,
                AplicacaoInsumos.class,
                ConfigWS.class,
                Sinc.class,
                LogSync.class,
                Coletor.class,
                AutenticaLogin.class,
                OrdemServico.class,
                Insumos.class,
                SistemaAplicacao.class

        },
        version = 20, exportSchema = false
)
@TypeConverters({Conversores.class})
public abstract class AIDatabase extends RoomDatabase {
    public static final String AI_DATABASE = "aplicacaoinsumos_db";
    private static AIDatabase INSTANCE;

    public abstract DepositosDAO getFazendasDAO();

    public abstract LotesDAO getTalhaoDAO();

    public abstract FuncionarioDAO getFuncionarioDAO();
    public  abstract OrdemServicoDAO getOrdemServicoDAO();

    public abstract ConfigWSDAO getConfigWSDAO();

    public abstract SincDAO getSincDAO();

    public abstract LogSyncDAO getLogSyncDAO();

    public abstract ColetorDAO getColetorDAO();

    public abstract AutenticaLoginDAO getAutenticaLoginDAO();

    public abstract AplicacaoInsumosDAO getAplicacaoInsumosDAO();
    public abstract InsumosDAO getInsumosDAO();
    public abstract SistemaAplicacaoDAO getSistemaAplicacaoDAO();

    public Migration[] migrations = {};

    public static AIDatabase getInstance(Context ctx) {
        return Room.databaseBuilder(ctx, AIDatabase.class, AI_DATABASE)
                .fallbackToDestructiveMigration().build();
//                .addMigrations(Migrations.MIGRATION_3_4).build();
    }
}
