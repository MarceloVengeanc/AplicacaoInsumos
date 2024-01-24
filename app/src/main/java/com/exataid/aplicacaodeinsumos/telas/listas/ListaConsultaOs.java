package com.exataid.aplicacaodeinsumos.telas.listas;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exataid.aplicacaodeinsumos.AIDatabase;
import com.exataid.aplicacaodeinsumos.R;
import com.exataid.aplicacaodeinsumos.banco.modelos.OrdemServico;
import com.exataid.aplicacaodeinsumos.telas.adapters.OrdemServicoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ListaConsultaOs extends AppCompatActivity {

    private EditText consultaOs;
    private TextView txtListaOs;
    private RecyclerView rvOrdemServico;
    Resources res;
    private int acrescenta;
    private OrdemServicoAdapter listaOsAdapter;
    private List<OrdemServico> listaOs, listaOsFiltrada;
    private List<OrdemServico> listaOsVazia = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_consulta_os);
        try {
            listaOs = AIDatabase.getInstance(this).getOrdemServicoDAO().buscarTodas().get();

            carregaBotoes();
            setarCampos();
            setarRecyclerListaOs(false);
            setOsEscolhida();
        } catch (Exception e) {
            e.printStackTrace();
        }

        consultaOs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setOsEscolhida();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setOsEscolhida() {
        try {
            if (!consultaOs.getText().toString().equalsIgnoreCase("")) {
                //BUSCA POR Número
                if (Character.isDigit(consultaOs.getText().toString().charAt(0))) {
                    if (listaOs.stream().anyMatch(cod -> String.valueOf(cod.getNumOrdemServico()).toLowerCase(Locale.ROOT).contains(consultaOs.getText()))) {
                        listaOsFiltrada = listaOs.stream().filter(u -> String.valueOf(u.getNumOrdemServico()).toLowerCase(Locale.ROOT).contains(consultaOs.getText())).collect(Collectors.toList());
                        setarRecyclerListaOs(false);
                    } else {
                        setarRecyclerListaOs(true);
                    }
                }
            } else {
                listaOsFiltrada = listaOs;
                setarRecyclerListaOs(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setResultOsEscolhida(OrdemServico ordemServico) {
        setResult(Activity.RESULT_OK, this.getIntent().putExtra("resultadoBuscaOs", ordemServico));
        this.finish();
    }

    private void setarCampos() {
        res = getResources();
        setarTotalItens();
    }

    private void setarTotalItens() {
        if (listaOsFiltrada != null) {
            txtListaOs.setText(res.getString(R.string.lista_os, listaOsFiltrada.size()));
        } else {
            listaOsFiltrada = new ArrayList<>();
            txtListaOs.setText(res.getString(R.string.lista_os, 0));
        }
    }

    private void setarRecyclerListaOs(boolean vazio) {
        if (vazio)
            listaOsAdapter = new OrdemServicoAdapter(listaOsVazia, this::setResultOsEscolhida);
        if (!vazio)
            listaOsAdapter = new OrdemServicoAdapter(listaOsFiltrada, this::setResultOsEscolhida);
        rvOrdemServico.setAdapter(listaOsAdapter);
        if (acrescenta == 0) {
            rvOrdemServico.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
            acrescenta++;
        }
        rvOrdemServico.setLayoutManager(new LinearLayoutManager(this));
        if (vazio) txtListaOs.setText(res.getString(R.string.lista_os, 0));
        if (!vazio) setarTotalItens();
    }

    public void carregaBotoes() {
        consultaOs = findViewById(R.id.editOS);
        txtListaOs = findViewById(R.id.txtListaOs);
        rvOrdemServico = findViewById(R.id.rvListaOs);
    }
}