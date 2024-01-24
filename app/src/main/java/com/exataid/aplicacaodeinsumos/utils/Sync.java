package com.exataid.aplicacaodeinsumos.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

import androidx.annotation.Nullable;

import com.exataid.aplicacaodeinsumos.AIDatabase;
import com.exataid.aplicacaodeinsumos.banco.modelos.ConfigWS;
import com.exataid.aplicacaodeinsumos.banco.modelos.Depositos;
import com.exataid.aplicacaodeinsumos.banco.modelos.Funcionario;
import com.exataid.aplicacaodeinsumos.banco.modelos.Insumos;
import com.exataid.aplicacaodeinsumos.banco.modelos.LogSync;
import com.exataid.aplicacaodeinsumos.banco.modelos.Lotes;
import com.exataid.aplicacaodeinsumos.banco.modelos.OrdemServico;
import com.exataid.aplicacaodeinsumos.banco.modelos.SistemaAplicacao;
import com.exataid.aplicacaodeinsumos.utils.interfaces.endpoints.repos.CadastroRepo;
import com.exataid.aplicacaodeinsumos.utils.interfaces.endpoints.repos.EnvioRepo;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sync extends Service implements Runnable {
    static int funcionariosTerminou = 0;
    private final Context context;

    public Sync(Context ctx) {
        context = ctx;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        // ValidaModulos(context);
        AtualizaCadastrosSistema(context);
    }

    public static String retornaDataAgora() {
        String Data;
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);
        Data = formata.format(calendario.getTime());
        return Data;
    }

    public static void AtualizaCadastrosSistema(Context context) {
        ConfigWS configWS = null;
        try {
            configWS = AIDatabase.getInstance(context).getConfigWSDAO().buscarUm().get();
            CadastroRepo repo = null;
            EnvioRepo envioRepo = null;
            try {
                repo = CadastroRepo.getInstance(configWS);
                envioRepo = EnvioRepo.getInstance(configWS);
            } catch (Exception e) {
                return;
            }
//            CADASTRO DE Funcionários
            repo.getService().baixarTodosFuncionarios().enqueue(new Callback<List<Funcionario>>() {
                @Override
                public void onResponse(Call<List<Funcionario>> call, Response<List<Funcionario>> response) {
                    try {
                        if (response.isSuccessful()) {
                            List<Funcionario> resposta = response.body();
                            List<Funcionario> ativos = resposta.stream().filter(funcionario -> funcionario.getAtivo().equalsIgnoreCase("Ativo")).collect(Collectors.toList());
                            List<Funcionario> inativos = resposta.stream().filter(funcionario -> funcionario.getAtivo().equalsIgnoreCase("Inativo")).collect(Collectors.toList());
                            AIDatabase.getInstance(context).getFuncionarioDAO().inserir(ativos).get();
                            if (inativos.size() > 0) {
                                AIDatabase.getInstance(context).getFuncionarioDAO().excluir(inativos).get();
                            }
                            AIDatabase.getInstance(context).getFuncionarioDAO().inserir(resposta).get();
                            AIDatabase.getInstance(context).getLogSyncDAO().inserir(
                                    new LogSync(
                                            "CADASTROS_FUNCIONÁRIOS",
                                            retornaDataAgora(),
                                            Long.parseLong(String.valueOf(resposta.size())),
                                            "FINALIZADO")
                            ).get();
                            funcionariosTerminou = 1;
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        try {
                            e.printStackTrace();
                            AIDatabase.getInstance(context).getLogSyncDAO().inserir(
                                    new LogSync(
                                            "CADASTROS_FUNCIONÁRIOS",
                                            retornaDataAgora(),
                                            0L,
                                            Arrays.toString(e.getStackTrace()))
                            ).get();
                        } catch (ExecutionException | InterruptedException y) {
                            y.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Funcionario>> call, Throwable t) {

                }
            });
//            CADASTRO FAZENDAS
            repo.getService().baixarTodasFazendas().enqueue(new Callback<List<Depositos>>() {
                @Override
                public void onResponse(Call<List<Depositos>> call, Response<List<Depositos>> response) {
                    try {
                        if (response.body() != null) {
                            List<Depositos> resposta = response.body();
                            AIDatabase.getInstance(context).getFazendasDAO().inserir(resposta).get();
                            AIDatabase.getInstance(context).getLogSyncDAO().inserir(
                                    new LogSync(
                                            "CADASTROS_DEPÓSITOS",
                                            retornaDataAgora(),
                                            Long.parseLong(String.valueOf(resposta.size())),
                                            "FINALIZADO")
                            ).get();
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        try {
                            e.printStackTrace();
                            AIDatabase.getInstance(context).getLogSyncDAO().inserir(
                                    new LogSync(
                                            "CADASTROS_DEPÓSITOS",
                                            retornaDataAgora(),
                                            0L,
                                            Arrays.toString(e.getStackTrace()))
                            ).get();
                        } catch (ExecutionException | InterruptedException y) {
                            y.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Depositos>> call, Throwable t) {

                }
            });
//            CADASTRO TALHOES
//            repo.getService().baixarTodosTalhoes().enqueue(new Callback<List<Talhao>>() {
//                @Override
//                public void onResponse(Call<List<Talhao>> call, Response<List<Talhao>> response) {
//                    try {
//                        if (response.body() != null) {
//                            List<Talhao> resposta = response.body();
//                            AIDatabase.getInstance(context).getTalhaoDAO().inserir(resposta).get();
//                            AIDatabase.getInstance(context).getLogSyncDAO().inserir(
//                                    new LogSync(
//                                            "CADASTROS_TALHÕES",
//                                            retornaDataAgora(),
//                                            Long.parseLong(String.valueOf(resposta.size())),
//                                            "FINALIZADO")
//                            ).get();
//                            talhoesTerminou = 1;
//                        }
//                    } catch (ExecutionException | InterruptedException e) {
//                        try {
//                            e.printStackTrace();
//                            AIDatabase.getInstance(context).getLogSyncDAO().inserir(
//                                    new LogSync(
//                                            "CADASTROS_TALHÕES",
//                                            retornaDataAgora(),
//                                            0L,
//                                            Arrays.toString(e.getStackTrace()))
//                            ).get();
//                        } catch (ExecutionException | InterruptedException y) {
//                            y.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<Talhao>> call, Throwable t) {
//
//                }
//            });

//            CADASTRO SISTEMAS DE APLICAÇÃO
            repo.getService().baixarTodosSistemaAplicacao().enqueue(new Callback<List<SistemaAplicacao>>() {
                @Override
                public void onResponse(Call<List<SistemaAplicacao>> call, Response<List<SistemaAplicacao>> response) {
                    if (response.body() != null) {
                        List<SistemaAplicacao> resposta = response.body();
                        try {
                            AIDatabase.getInstance(context).getSistemaAplicacaoDAO().inserir(resposta).get();
                            AIDatabase.getInstance(context).getLogSyncDAO().inserir(
                                    new LogSync(
                                            "CADASTROS_SISTEMA_APLICAÇÃO",
                                            retornaDataAgora(),
                                            Long.parseLong(String.valueOf(resposta.size())),
                                            "FINALIZADO")
                            ).get();
                        } catch (ExecutionException | InterruptedException e) {
                            try {
                                e.printStackTrace();
                                AIDatabase.getInstance(context).getLogSyncDAO().inserir(
                                        new LogSync(
                                                "CADASTROS_SISTEMA_APLICAÇÃO",
                                                retornaDataAgora(),
                                                0L,
                                                Arrays.toString(e.getStackTrace()))
                                ).get();
                            } catch (ExecutionException | InterruptedException y) {
                                y.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<SistemaAplicacao>> call, Throwable t) {

                }
            });
            repo.getService().baixarTodasOrdemServico().enqueue(new Callback<List<OrdemServico>>() {
                @Override
                public void onResponse(Call<List<OrdemServico>> call, Response<List<OrdemServico>> response) {
                    if (response.body() != null) {
                        List<OrdemServico> resposta = response.body();
                        try {
                            AIDatabase.getInstance(context).getOrdemServicoDAO().inserir(resposta).get();
                            for (int i = 0; i < resposta.size(); i++) {
                                for (Lotes lotes : resposta.get(i).getTalhoes()) {
                                    AIDatabase.getInstance(context).getTalhaoDAO().inserir(lotes);
                                }

                                for (Insumos insumo : resposta.get(i).getInsumos()) {
                                    AIDatabase.getInstance(context).getInsumosDAO().inserir(insumo);
                                }
                            }
                            AIDatabase.getInstance(context).getLogSyncDAO().inserir(
                                    new LogSync(
                                            "CADASTROS_ORDEM_SERVIÇO",
                                            retornaDataAgora(),
                                            Long.parseLong(String.valueOf(resposta.size())),
                                            "FINALIZADO")
                            ).get();
                            List<Lotes> listaTalhoes = AIDatabase.getInstance(context).getTalhaoDAO().buscarTodos().get();
                            AIDatabase.getInstance(context).getLogSyncDAO().inserir(
                                    new LogSync(
                                            "CADASTROS_TALHÕES",
                                            retornaDataAgora(),
                                            Long.parseLong(String.valueOf(listaTalhoes.size())),
                                            "FINALIZADO")
                            ).get();
                            List<Insumos> listaInsumos = AIDatabase.getInstance(context).getInsumosDAO().buscarTodos().get();
                            AIDatabase.getInstance(context).getLogSyncDAO().inserir(
                                    new LogSync(
                                            "CADASTROS_INSUMOS",
                                            retornaDataAgora(),
                                            Long.parseLong(String.valueOf(listaInsumos.size())),
                                            "FINALIZADO")
                            ).get();
                        } catch (ExecutionException | InterruptedException e) {
                            try {
                                e.printStackTrace();
                                AIDatabase.getInstance(context).getLogSyncDAO().inserir(
                                        new LogSync(
                                                "CADASTROS_ORDEM_SERVIÇO",
                                                retornaDataAgora(),
                                                0L,
                                                Arrays.toString(e.getStackTrace()))
                                ).get();
                            } catch (ExecutionException | InterruptedException y) {
                                y.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<OrdemServico>> call, Throwable t) {
                    Log.e("Error", "N");
                }
            });

            AIDatabase db = AIDatabase.getInstance(context.getApplicationContext());
            //ENVIO DE REGISTRO/APONTAMENTO
//            if (ConfigGerais.UsuarioLogado != null) {
//                apontamentos = AIDatabase.getInstance(context).getApontamentoPlantioDAO().buscarPorNaoEnviado("N", ConfigGerais.UsuarioLogado.getNome()).get();
//                List<CargaDeMuda> cargaMuda = AIDatabase.getInstance(context).getCargaDeMudaDAO().buscarPorApontamento("N", ConfigGerais.UsuarioLogado.getNome()).get();
//                if (apontamentos.size() == 0 & cargaMuda.size() == 0) return;
//                for (ApontamentoPlantio re : apontamentos) {
//                    re.setCargaDeMuda(cargaMuda);
//                    pacoteApontamentoPlantio = re;
//                }
//
//                envioRepo.getService().enviarRegistros(pacoteApontamentoPlantio).enqueue(new Callback<ApontamentoPlantio>() {
//                    @Override
//                    public void onResponse(Call<ApontamentoPlantio> call, Response<ApontamentoPlantio> response) {
//                        if (response.body() != null) {
//                            if (apontamentos.size() == 0) return;
//                            for (int i = 0; i <= apontamentos.size(); i++) {
//                                ApontamentoPlantio apontamentoPlantioOK = apontamentos.stream().filter(u -> u.getEnviado().equalsIgnoreCase("N")).findFirst().orElse(null);
//                                if (apontamentoPlantioOK != null) {
//                                    apontamentoPlantioOK.setEnviado("S");
//                                    apontamentoPlantioOK.setDataEnviado(U_Data_Hora.retornaData(0, U_Data_Hora.YYYY_MM_DD_HH_MM_SS_SSS));
//                                    db.getApontamentoPlantioDAO().atualizar(apontamentoPlantioOK);
//                                }
//                            }
//                            if (cargaMuda.size() == 0) return;
//                            for (int i = 0; i <= cargaMuda.size(); i++) {
//                                CargaDeMuda cargas = cargaMuda.stream().filter(u -> "N".equalsIgnoreCase(u.getEnviado())).findFirst().orElse(null);
//                                if (cargas != null) {
//                                    cargas.setEnviado("S");
//                                    cargas.setDataEnviado(U_Data_Hora.retornaData(0, U_Data_Hora.YYYY_MM_DD_HH_MM_SS_SSS));
//                                    db.getCargaDeMudaDAO().atualizar(cargas);
//                                }
//                            }
//                            try {
//                                AIDatabase.getInstance(context).getLogSyncDAO().inserir(
//                                        new LogSync(
//                                                "APONTAMENTOS_PLANTIOS",
//                                                retornaDataAgora(),
//                                                Long.parseLong(String.valueOf(apontamentos.size())),
//                                                "FINALIZADO")
//                                ).get();
//                                AIDatabase.getInstance(context).getLogSyncDAO().inserir(
//                                        new LogSync(
//                                                "APONTAMENTOS_CARGAS",
//                                                retornaDataAgora(),
//                                                Long.parseLong(String.valueOf(cargaMuda.size())),
//                                                "FINALIZADO")
//                                ).get();
//                            } catch (ExecutionException | InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ApontamentoPlantio> call, Throwable t) {
//                        t.printStackTrace();
//                        try {
//                            AIDatabase.getInstance(context).getLogSyncDAO().inserir(
//                                    new LogSync(
//                                            "APONTAMENTOS_PLANTIOS",
//                                            retornaDataAgora(),
//                                            Long.parseLong(String.valueOf(apontamentos.size())), "FALHOU")
//                            ).get();
//                            AIDatabase.getInstance(context).getLogSyncDAO().inserir(
//                                    new LogSync(
//                                            "APONTAMENTOS_CARGAS",
//                                            retornaDataAgora(),
//                                            Long.parseLong(String.valueOf(cargaMuda.size())), "FALHOU")
//                            ).get();
//
//                        } catch (ExecutionException | InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
        } catch (ExecutionException | InterruptedException u) {
            u.printStackTrace();
        }
    }
}
