package com.exataid.aplicacaodeinsumos.telas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exataid.aplicacaodeinsumos.AIDatabase;
import com.exataid.aplicacaodeinsumos.R;
import com.exataid.aplicacaodeinsumos.banco.modelos.Coletor;
import com.exataid.aplicacaodeinsumos.banco.modelos.Depositos;
import com.exataid.aplicacaodeinsumos.banco.modelos.Insumos;
import com.exataid.aplicacaodeinsumos.banco.modelos.OrdemServico;
import com.exataid.aplicacaodeinsumos.banco.modelos.Lotes;
import com.exataid.aplicacaodeinsumos.telas.adapters.InsumosAdapter;
import com.exataid.aplicacaodeinsumos.telas.adapters.LotesAdapter;
import com.exataid.aplicacaodeinsumos.telas.listas.ListaConsultaDepósito;
import com.exataid.aplicacaodeinsumos.telas.listas.ListaConsultaOs;
import com.exataid.aplicacaodeinsumos.utils.OnOneOffClickListener;
import com.exataid.aplicacaodeinsumos.utils.U_Data_Hora;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ApontamentoInsumosActivity extends AppCompatActivity {
    private Button btnSalvar;
    private ImageButton btnData, btnOs, btnPrePlantio;
    private EditText editData, editOS, editCentroDeCusto, editOperacao, editFazenda, editVazao, editPrePlantio;
    private RecyclerView rvInsumos, rvLotes;
    private Calendar dataCompleta, dataAtual, dataEscolhida;
    private Coletor coletor;
    private Context ctx;
    private Resources res;
    private String dataSelecionada = "", servicoescolhido = "";
    private int acrescentaInsumos = 0, acrescentaLotes = 0, area = 0, contSalvar = 0;
    private boolean trocarMsg = false, editar = false, finalizar = false, possuiPonto = false, editarCargaD = false;
    private Dialog dialog;
    private List<OrdemServico> listaOS;
    private InsumosAdapter insumosAdapter;
    private List<Insumos> listaInsumos;

    private Insumos insumoEscolhido;
    private OrdemServico ordemServicoEscolhido;
    private Depositos depositoEscolhido;
    private Button btnLancar;
    private ImageButton btnBuscaDp;
    private EditText editInsumo, editProg, editAplic, editDp;
    private List<Lotes> listaLotes;
    private Lotes loteEscolhido;
    private LotesAdapter lotesAdapter;
    private AlertDialog alertDialogInsumo, alertDialogLote;
    private EditText editLote, editArea, editFazendaLote;
    private Button btnLancarLote;
    private RadioButton rbSim, rbNao;
    private float areaLote = 0;
    ActivityResultLauncher<Intent> aguardaResultado = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        if (data.hasExtra("resultadoBuscaOs")) {
                            setBuscaOs((OrdemServico) data.getSerializableExtra("resultadoBuscaOs"));
                        } else if (data.hasExtra("resultadoBuscaDepositos")) {
                            setBuscaDeposito((Depositos) data.getSerializableExtra("resultadoBuscaDepositos"));
                        }
                    }
                }
            }
    );


    //
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apontamento_insumos);
//        Intent i = getIntent();
//        apontamentoPlantioEditar = (ApontamentoPlantio) i.getSerializableExtra("Editar");
//
        ctx = this;
        carregaBotoes();
        setarData();
//        setarRecyclerListaCargaDeMuda();
        setarBotoes();
        carregaBanco();
//        carregaEditarPlantio();

//
//
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (listaInsumos.size() != 0 || listaLotes.size() != 0) {
                    finalizar = true;
                    String msg = "";
                    if (editar) {
                        alertAviso("Os registros editados não serão alterados. Deseja continuar?", true, false, true);
                    } else {
                        alertAviso("Os registros inseridos não serão salvos. Deseja continuar?", true, false, true);
                    }
                } else {
                    finish();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);


    }

    //
//
//    private void salvaApontamento() {
//        AIDatabase db = AIDatabase.getInstance(this.getApplicationContext());
//        if (!editar) {
//            ApontamentoPlantio apontamentoPlantio = criarApontamentoPlantio();
//            if (apontamentoPlantio != null) {
//                db.getApontamentoPlantioDAO().inserir(apontamentoPlantio);
//            }
//            try {
//                ultimoApontamentoPlantio = AIDatabase.getInstance(this).getApontamentoPlantioDAO().buscarUltimoApontamento().get();
//            } catch (ExecutionException | InterruptedException e) {
//                e.printStackTrace();
//            }
//            salvarCargaDeMudas(ultimoApontamentoPlantio);
//            alertAviso("Apontamento salvo com sucesso!", true, false, false);
//            limparCampos();
//
//        } else {
//            //ALTERAR MODO EDITAR
//            apontamentoPlantioEditar.setDataPlantio(!dataSelecionada.equalsIgnoreCase("") ? dataSelecionada : apontamentoPlantioEditar.getDataPlantio());
//            apontamentoPlantioEditar.setConcluido(cbConcluido.isChecked() ? "Sim" : "Não");
//            apontamentoPlantioEditar.setCodFazenda(fazendaEscolhida.getCodFazenda());
//            apontamentoPlantioEditar.setNomeFazenda(fazendaEscolhida.getNomeFazenda());
//            apontamentoPlantioEditar.setCodTalhao(talhaoEscolhido.getCodTalhao());
//            apontamentoPlantioEditar.setAreaTalhao(talhaoEscolhido.getArea());
//            apontamentoPlantioEditar.setCodSisPlantio(sistemaPlantioEscolhido.getCodSistemaPlantio());
//            apontamentoPlantioEditar.setNomeSisPlantio(sistemaPlantioEscolhido.getNomeSistemaPlantio());
//            apontamentoPlantioEditar.setCodVariedade(variedadeEscolhida.getCodVariedade());
//            apontamentoPlantioEditar.setNomeVariedade(variedadeEscolhida.getNomeVariedade());
//            apontamentoPlantioEditar.setCodTipoPlantio(tipoEscolhido.getCodTipo());
//            apontamentoPlantioEditar.setNomeTipoPlantio(tipoEscolhido.getNomeTipo());
//            apontamentoPlantioEditar.setAreaPlantada(Double.parseDouble(editAreaPlantada.getText().toString()));
//            apontamentoPlantioEditar.setLote(rbLiquidado.isChecked() ? "Por Liquidado" : "Por Conta");
//            db.getApontamentoPlantioDAO().atualizar(apontamentoPlantioEditar);
//            salvarCargaDeMudas(apontamentoPlantioEditar);
//        }
//    }
//
//    private ApontamentoPlantio criarApontamentoPlantio() {
//        ApontamentoPlantio apontamentoPlantio = new ApontamentoPlantio();
//        apontamentoPlantio.setEnviado("N");
//        apontamentoPlantio.setColetor(coletor.getNumero());
//        apontamentoPlantio.setNomeFuncionario(ConfigGerais.UsuarioLogado.getNome());
//        apontamentoPlantio.setMatriculaFuncionario(ConfigGerais.UsuarioLogado.getMatricula());
//        apontamentoPlantio.setDataPlantio(!dataSelecionada.equalsIgnoreCase("") ? dataSelecionada : U_Data_Hora.retornaData(0, U_Data_Hora.YYYY_MM_DD));
//        apontamentoPlantio.setDataPlantioSalvo(U_Data_Hora.retornaData(0, U_Data_Hora.YYYY_MM_DD));
//        String resultadoConcluido = (cbConcluido.isChecked()) ? "Sim" : "Não";
//        apontamentoPlantio.setConcluido(resultadoConcluido);
//        apontamentoPlantio.setCodFazenda(fazendaEscolhida.getCodFazenda());
//        apontamentoPlantio.setNomeFazenda(fazendaEscolhida.getNomeFazenda());
//        apontamentoPlantio.setCodTalhao(talhaoEscolhido.getCodTalhao());
//        apontamentoPlantio.setAreaTalhao(talhaoEscolhido.getArea());
//        apontamentoPlantio.setCodSisPlantio(sistemaPlantioEscolhido.getCodSistemaPlantio());
//        apontamentoPlantio.setNomeSisPlantio(sistemaPlantioEscolhido.getNomeSistemaPlantio());
//        apontamentoPlantio.setCodVariedade(variedadeEscolhida.getCodVariedade());
//        apontamentoPlantio.setNomeVariedade(variedadeEscolhida.getNomeVariedade());
//        apontamentoPlantio.setCodTipoPlantio(tipoEscolhido.getCodTipo());
//        apontamentoPlantio.setNomeTipoPlantio(tipoEscolhido.getNomeTipo());
//        apontamentoPlantio.setAreaPlantada(Double.parseDouble(editAreaPlantada.getText().toString()));
//        String resultadoLote = (rbLiquidado.isChecked()) ? "Por Liquidado" : "Por Conta";
//        apontamentoPlantio.setLote(resultadoLote);
//        return apontamentoPlantio;
//    }
//
//    private void salvarCargaDeMudas(ApontamentoPlantio ultimoApontamentoPlantio) {
//        AIDatabase db = AIDatabase.getInstance(this.getApplicationContext());
//        if (!editar) {
//            for (CargaDeMuda cargaDeMuda : listaCargaDeMuda) {
//                CargaDeMuda cargaDeMuda1 = criarCargaMuda(ultimoApontamentoPlantio, cargaDeMuda);
//                db.getCargaDeMudaDAO().inserir(cargaDeMuda1);
//            }
//        } else {
//            for (CargaDeMuda cargaDeMuda1 : listaCargaDeMuda) {
//                if (cargaDeMuda1.getIdApontamento() != ultimoApontamentoPlantio.getId()) {
//                    CargaDeMuda novaCargaDeMuda = criarCargaMuda(ultimoApontamentoPlantio, cargaDeMuda1);
//                    db.getCargaDeMudaDAO().inserir(novaCargaDeMuda);
//                } else {
//                    db.getCargaDeMudaDAO().atualizar(cargaDeMuda1);
//                }
//            }
//            finalizar = true;
//            alertAviso("Apontamento alterado com sucesso!", true, false, false);
//        }
//    }
//
//    private CargaDeMuda criarCargaMuda(ApontamentoPlantio ultimoApontamentoPlantio, CargaDeMuda cargaDeMuda) {
//        CargaDeMuda cargaDeMuda1 = new CargaDeMuda();
//        cargaDeMuda1.setEnviado("N");
//        cargaDeMuda1.setMatriculaFuncionario(ConfigGerais.UsuarioLogado.getMatricula());
//        cargaDeMuda1.setNomeFuncionario(ConfigGerais.UsuarioLogado.getNome());
//        cargaDeMuda1.setColetor(coletor.getNumero());
//        cargaDeMuda1.setDataApontamento(ultimoApontamentoPlantio.getDataPlantio());
//        cargaDeMuda1.setCodFazenda(cargaDeMuda.getCodFazenda());
//        cargaDeMuda1.setNomeFazenda(cargaDeMuda.getNomeFazenda());
//        cargaDeMuda1.setCodTalhao(cargaDeMuda.getCodTalhao());
//        cargaDeMuda1.setAreaTalhao(cargaDeMuda.getAreaTalhao());
//        cargaDeMuda1.setCodProcedencia(cargaDeMuda.getCodProcedencia());
//        cargaDeMuda1.setNomeProcedencia(cargaDeMuda.getNomeProcedencia());
//        cargaDeMuda1.setCarga(cargaDeMuda.getCarga());
//        cargaDeMuda1.setPeso(cargaDeMuda.getPeso());
//        cargaDeMuda1.setIdApontamento(ultimoApontamentoPlantio.getId());
//        return cargaDeMuda1;
//    }
//
//    private void adicionaCargaNaLista() {
//        if (!editarCargaD) {
//            CargaDeMuda cargaDeMuda = new CargaDeMuda();
//            cargaDeMuda.setCodProcedencia(procedenciaEscolhida.getCodProcedencia());
//            cargaDeMuda.setNomeProcedencia(procedenciaEscolhida.getNomeProcedencia());
//            cargaDeMuda.setCodFazenda(fazendaEscolhidaD.getCodFazenda());
//            cargaDeMuda.setNomeFazenda(fazendaEscolhidaD.getNomeFazenda());
//            cargaDeMuda.setCodTalhao(talhaoEscolhidoD.getCodTalhao());
//            cargaDeMuda.setAreaTalhao(talhaoEscolhidoD.getArea());
//            cargaDeMuda.setCarga(Integer.parseInt(editCargaD.getText().toString()));
//            cargaDeMuda.setPeso(Double.parseDouble(editPesoD.getText().toString()));
//            listaCargaDeMuda.add(cargaDeMuda);
//        } else {
//            cargaDeMuda.setCodProcedencia(procedenciaEscolhida.getCodProcedencia());
//            cargaDeMuda.setNomeProcedencia(procedenciaEscolhida.getNomeProcedencia());
//            cargaDeMuda.setCodFazenda(fazendaEscolhidaD.getCodFazenda());
//            cargaDeMuda.setNomeFazenda(fazendaEscolhidaD.getNomeFazenda());
//            cargaDeMuda.setCodTalhao(talhaoEscolhidoD.getCodTalhao());
//            cargaDeMuda.setAreaTalhao(talhaoEscolhidoD.getArea());
//            cargaDeMuda.setCarga(Integer.parseInt(editCargaD.getText().toString()));
//            cargaDeMuda.setPeso(Double.parseDouble(editPesoD.getText().toString()));
//            editarCargaD = false;
//        }
//        setarRecyclerListaCargaDeMuda();
//        dialog.dismiss();
//    }
//
//    private void chamaVerificaCampos(String servico) {
//        if (servico.equalsIgnoreCase("apontamento")) {
//            if (editVazio(editData, "Data")) return;
//            if (editVazio(editFazenda, "Fazenda")) return;
//            if (editVazio(editTalhao, "Talhão")) return;
//            if (editVazio(editAreaPlantada, "Área Plantada")) return;
//        } else if (servico.equalsIgnoreCase("carga")) {
//            if (editVazio(editProcedenciaD, "Procedência")) return;
//            if (editVazio(editFazendaD, "Fazenda")) return;
//            if (editVazio(editTalhaoD, "Talhão")) return;
//            if (editVazio(editCargaD, "Carga")) return;
//            if (editVazio(editPesoD, "Peso")) return;
//        }
//
//        String estimativaText = editAreaPlantada.getText().toString();
//        if (estimativaText.matches("^0+$")) {
//            alertAviso("Campo Área Plantada não pode ser 0.", true, false, false);
//            return;
//        }
//        String cargaText = editCargaD.getText().toString();
//        if (cargaText.matches("^0+$")) {
//            alertAviso("Campo Carga não pode ser 0.", true, false, false);
//            return;
//        }
//        String pesoText = editPesoD.getText().toString();
//        if (pesoText.matches("^0+$")) {
//            alertAviso("Campo Peso não pode ser 0.", true, false, false);
//            return;
//        }
//        if (servico.equalsIgnoreCase("apontamento")) {
////            SALVA APONTAMENTO
//            salvaApontamento();
//        } else if (servico.equalsIgnoreCase("carga")) {
//            // ADICIONA NA LISTA DE CARGAS DE MUDA
//            adicionaCargaNaLista();
//        }
//
//    }
//
//    private boolean editVazio(EditText editText, String fieldName) {
//        if (editText.getText().toString().trim().isEmpty()) {
//            alertAviso(fieldName, false, false, false);
//            editText.requestFocus();
//            return true;
//        }
//        return false;
//    }
//
//
//

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    private void chamaDialogLancamentoInsumo(Insumos insumo) {
        //Cria Alert
        AlertDialog.Builder builder = new AlertDialog.Builder(ApontamentoInsumosActivity.this);
        View view = getLayoutInflater().inflate(R.layout.alert_lancamento_insumos, null);
        //Declara variáveis
        btnLancar = view.findViewById(R.id.btnSalvar);
        btnBuscaDp = view.findViewById(R.id.btnBuscaDp);
        editInsumo = view.findViewById(R.id.editInsumo);
        editProg = view.findViewById(R.id.editProg);
        editAplic = view.findViewById(R.id.editAplic);
        editDp = view.findViewById(R.id.editDp);
        builder.setView(view);
        alertDialogInsumo = builder.create();
        editInsumo.setText(insumo.getCodInsumo() + " - " + insumo.getDescInsumo());
        editProg.setText("Programado: " + insumo.getQuantidadeProgramado());
        btnBuscaDp.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent i = new Intent(ApontamentoInsumosActivity.this, ListaConsultaDepósito.class);
                aguardaResultado.launch(i);
            }
        });

        btnLancar.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                chamaVerificaCampos("insumos");
                setarRecyclerListainsumos();
            }
        });
        editAplic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (text.contains(".")) {
                    possuiPonto = true;
                } else {
                    possuiPonto = false;
                }

                if (possuiPonto) {

                    int dotIndex = text.indexOf(".");
                    int maxLength = dotIndex + 4;
                    editAplic.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                } else {
                    editAplic.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                }
            }
        });
        alertDialogInsumo.show();
    }

    private boolean editVazio(EditText editText, String fieldName) {
        if (editText.getText().toString().trim().isEmpty()) {
            alertAviso(fieldName, false, false, false);
            editText.requestFocus();
            return true;
        }
        return false;
    }

    private void chamaVerificaCampos(String lancamento) {
        if (lancamento.equalsIgnoreCase("insumos")) {
            if (editVazio(editAplic, "Aplicado")) return;
            if (editVazio(editDp, "Depósito")) return;
            String aplicadoText = editAplic.getText().toString();
            if (aplicadoText.matches("^0+$")) {
                alertAviso("Campo Aplicado não pode ser 0.", true, false, false);
                return;
            }
        } else if (lancamento.equalsIgnoreCase("lotes")) {
            if (!editArea.getText().toString().equalsIgnoreCase("Área: ")) {
                if (editVazio(editArea, "Área")) return;
                areaLote = Float.parseFloat(editArea.getText().toString().replace("Área: ", ""));
                if (loteEscolhido.getAreaTalhao() < areaLote) {
                    alertAviso("Área lançada é maior que a do Lote", true, false, false);
                    return;
                }
                String areaText = editArea.getText().toString();
                if (areaText.matches("^0+$")) {
                    alertAviso("Campo Área não pode ser 0.", true, false, false);
                    return;
                }
                if (!rbNao.isChecked() && !rbSim.isChecked()) {
                    alertAviso("Marcar Lote como Concluído: Sim ou Não", true, false, false);
                    return;
                }
            } else {
                alertAviso("Campo Área vazio", true, false, false);
            }
        }

        if (lancamento.equalsIgnoreCase("insumos")) {
            salvaInsumo();
        } else if (lancamento.equalsIgnoreCase("lotes")) {
            salvaLote();
        }

    }

    private void salvaLote() {
        loteEscolhido.setAreaTalhao(areaLote);
        loteEscolhido.setConcluido(rbSim.isChecked() ? "Sim" : "Não");
        areaLote = 0;
        setarRecyclerListaLotes();
        alertDialogLote.dismiss();
    }

    private void salvaInsumo() {
        insumoEscolhido.setQuantidadeAplicado(Double.parseDouble(editAplic.getText().toString()));
        insumoEscolhido.setCodDp(depositoEscolhido.getCodDeposito());
        insumoEscolhido.setDescDp(depositoEscolhido.getNomeDeposito());
        alertDialogInsumo.dismiss();
    }

    private void chamaAlertCalendario() {
        //CRIA ALERT
        AlertDialog.Builder builder = new AlertDialog.Builder(ApontamentoInsumosActivity.this);
        View view1 = getLayoutInflater().inflate(R.layout.alert_calendario_dialog, null);

        //DECLARA VARIAVEIS
        Button btnOk;
        DatePicker datePicker;
        btnOk = view1.findViewById(R.id.btn_Okdata);
        datePicker = view1.findViewById(R.id.dtpicker);
        datePicker.setMinDate(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(6));
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int ano = datePicker.getYear();
                int mes = datePicker.getMonth() + 1;
                int dia = datePicker.getDayOfMonth();

                // Criar um objeto Calendar para a data selecionada
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(ano, (mes - 1), dia);

                // Obter a data atual do sistema
                Calendar currentCalendar = Calendar.getInstance();


                if (selectedCalendar.before(currentCalendar) || selectedCalendar.equals(currentCalendar)) {
                    if (mes < 10 && dia < 10) {
                        editData.setText("0" + dia + "/0" + mes + "/" + ano);
                        dataSelecionada = ano + "-0" + mes + "-0" + dia;
                        alertDialog.dismiss();
                    } else if (mes < 10) {
                        editData.setText(dia + "/0" + mes + "/" + ano);
                        dataSelecionada = ano + "-0" + mes + "-" + dia;
                        alertDialog.dismiss();
                    } else if (dia < 10) {
                        editData.setText("0" + dia + "/" + mes + "/" + ano);
                        dataSelecionada = ano + "-" + mes + "-0" + dia;
                        alertDialog.dismiss();
                    } else {
                        editData.setText(dia + "/" + mes + "/" + ano);
                        dataSelecionada = ano + "-" + mes + "-" + dia;
                        alertDialog.dismiss();
                    }

                } else if (selectedCalendar.after(currentCalendar)) {
                    Toast.makeText(ApontamentoInsumosActivity.this, "A data é posterior à data atual", Toast.LENGTH_SHORT).show();
                }
            }

        });
        alertDialog.show();
    }

    private void setarBotoes() {
        btnOs.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (listaOS.size() != 0) {
                    Intent i = new Intent(ApontamentoInsumosActivity.this, ListaConsultaOs.class);
                    aguardaResultado.launch(i);
                } else {
                    alertAviso("Nenhum Ordem de Serviço encontrada! Tente Sincronizar", true, false, false);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setBuscaDeposito(Depositos deposito) {
        if (deposito == null) return;
        editDp.setText(deposito.getCodDeposito() + " - " + deposito.getNomeDeposito());
        depositoEscolhido = deposito;
    }

    @SuppressLint("SetTextI18n")
    private void setBuscaOs(OrdemServico ordemServico) {
        if (ordemServico == null) return;
        editOS.setText("OS: " + ordemServico.getNumOrdemServico());
        editCentroDeCusto.setText("C. Custo: " + ordemServico.getCodCentroCusto() + " - " + ordemServico.getNomeCentroCusto());
        editOperacao.setText("Oper: " + ordemServico.getCodOperacao() + " - " + ordemServico.getNomeOperacao());
        editFazenda.setText("Faz: " + ordemServico.getCodFazenda() + " - " + ordemServico.getNomeFazenda());
        editVazao.setText("Vazão: " + ordemServico.getVazao());
        ordemServicoEscolhido = ordemServico;
        try {
            listaLotes = AIDatabase.getInstance(this).getTalhaoDAO().buscaTalhaoFazenda(ordemServico.getCodFazenda()).get();
            listaInsumos = AIDatabase.getInstance(this).getInsumosDAO().buscarPorOS(ordemServico.getNumOrdemServico()).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        setarRecyclerListainsumos();
        setarRecyclerListaLotes();
    }

    private void setarRecyclerListainsumos() {
        //configurar Adapter
        insumosAdapter = new InsumosAdapter(listaInsumos, ctx, res, this::setInsumoEscolhido);
        //configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvInsumos.setLayoutManager(layoutManager);
        rvInsumos.setHasFixedSize(true);
        rvInsumos.setAdapter(insumosAdapter);
        if (acrescentaInsumos == 0) {
            rvInsumos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
            acrescentaInsumos++;
        }
    }

    private void setarRecyclerListaLotes() {
        //configurar Adapter
        lotesAdapter = new LotesAdapter(listaLotes, ctx, res, this::setLoteEscolhido);
        //configurar Recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvLotes.setLayoutManager(layoutManager);
        rvLotes.setHasFixedSize(true);
        rvLotes.setAdapter(lotesAdapter);
        if (acrescentaLotes == 0) {
            rvLotes.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
            acrescentaLotes++;
        }
    }

    private void setLoteEscolhido(Lotes lote) {
        if (lote == null) return;
        loteEscolhido = lote;
        chamaDialogLancamentoLote(loteEscolhido);

    }

    private void setInsumoEscolhido(Insumos insumo) {
        if (insumo == null) return;
        insumoEscolhido = insumo;
        chamaDialogLancamentoInsumo(insumoEscolhido);
    }

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    private void chamaDialogLancamentoLote(Lotes lote) {
        //Cria Alert
        AlertDialog.Builder builder = new AlertDialog.Builder(ApontamentoInsumosActivity.this);
        View view = getLayoutInflater().inflate(R.layout.alert_lancamento_lotes, null);
        //Declara variáveis
        btnLancarLote = view.findViewById(R.id.btnLancar);
        editArea = view.findViewById(R.id.editArea);
        editLote = view.findViewById(R.id.editLote);
        editFazendaLote = view.findViewById(R.id.editFazenda);
        rbSim = view.findViewById(R.id.rbSim);
        rbNao = view.findViewById(R.id.rbNao);
        editFazendaLote.setText(ordemServicoEscolhido.getCodFazenda() + " - " + ordemServicoEscolhido.getNomeFazenda());
        editLote.setText("Lote: " + lote.getCodTalhao());
        editArea.setText("Área: " + lote.getAreaTalhao());
        String palavraFixa = "Área: ";


        builder.setView(view);
        alertDialogLote = builder.create();
        editArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().startsWith(palavraFixa)) {
                    editArea.setText(palavraFixa);
                    editArea.setSelection(palavraFixa.length());
                }
                String text = editable.toString();
                if (text.contains(".")) {
                    possuiPonto = true;
                } else {
                    possuiPonto = false;
                }

                if (possuiPonto) {

                    int dotIndex = text.indexOf(".");
                    int maxLength = dotIndex + 3;
                    editArea.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                } else {
                    editArea.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                }
            }
        });
        btnLancarLote.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                chamaVerificaCampos("lotes");
            }
        });
        alertDialogLote.show();
    }

    private void setarData() {
        editData.setText(U_Data_Hora.retornaData(0, U_Data_Hora.DDMMYYYY));
        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ApontamentoInsumosActivity.this);
                builder.setTitle("Senha");
                final EditText txtSenha = new EditText(ApontamentoInsumosActivity.this);
                txtSenha.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                txtSenha.requestFocus();
                builder.setView(txtSenha);
                builder.setPositiveButton(("Ok"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (txtSenha.getText().toString().equalsIgnoreCase("17395")) {
                            chamaAlertCalendario();
                        } else if (txtSenha.getText().toString().equalsIgnoreCase("")) {
                            alertAviso("Informe a Senha!", true, false, false);
                        } else {
                            alertAviso("Senha incorreta!", true, false, false);
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    private void alertAviso(String mensagem, boolean trocarMsg, boolean editarCarga,
                            boolean sair) {
        //CRIA ALERT
        AlertDialog.Builder builder = new AlertDialog.Builder(ApontamentoInsumosActivity.this);
        View view1 = getLayoutInflater().inflate(R.layout.alert_aviso, null);

        //DECLARA VARIAVEIS
        Button btnOk, btnCancela;
        TextView msg;
        btnOk = view1.findViewById(R.id.btn_OkAlert2);
        btnCancela = view1.findViewById(R.id.btn_CancelaAlert);
        msg = view1.findViewById(R.id.txtmsg);
        if (sair) {
            btnCancela.setVisibility(View.VISIBLE);
            btnOk.setText("Sim");
        }
        if (editarCarga) {
            btnCancela.setVisibility(View.VISIBLE);
            btnOk.setText("Editar");
        }
        if (trocarMsg) {
            msg.setText(mensagem);
        } else {
            btnOk.setText("Ok");
            msg.setText("Campo " + mensagem + " Vazio!");
        }

        builder.setView(view1);
        trocarMsg = false;
        AlertDialog alertDialog = builder.create();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editarCarga) {
                    chamaDialogLancamentoInsumo(insumoEscolhido);
                }
                if (finalizar) {
                    finish();
                }
                alertDialog.dismiss();
                btnCancela.setVisibility(View.GONE);
            }
        });
        btnCancela.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                editar = false;
                finalizar = false;
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void carregaBanco() {
        try {
            listaOS = AIDatabase.getInstance(this).getOrdemServicoDAO().buscarTodas().get();
            coletor = AIDatabase.getInstance(this).getColetorDAO().buscarAtivo().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongViewCast")
    private void carregaBotoes() {
        btnData = findViewById(R.id.btnBuscaData);
        btnOs = findViewById(R.id.btnBuscaOS);
        btnPrePlantio = findViewById(R.id.btnBuscaPrePlantio);
        btnData = findViewById(R.id.btnBuscaData);
        editData = findViewById(R.id.editData);
        editOS = findViewById(R.id.editOS);
        editCentroDeCusto = findViewById(R.id.editCentroDeCusto);
        editOperacao = findViewById(R.id.editOperacao);
        editFazenda = findViewById(R.id.editFazenda);
        editVazao = findViewById(R.id.editVazao);
        editPrePlantio = findViewById(R.id.editPrePlantio);
        rvInsumos = findViewById(R.id.rvInsumos);
        rvLotes = findViewById(R.id.rvLotes);
        res = getResources();
    }

}