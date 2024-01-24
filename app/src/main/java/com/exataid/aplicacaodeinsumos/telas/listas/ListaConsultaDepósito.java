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
import com.exataid.aplicacaodeinsumos.banco.modelos.Depositos;
import com.exataid.aplicacaodeinsumos.telas.adapters.DepositosAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ListaConsultaDepósito extends AppCompatActivity {

    private EditText consultaDeposito;
    private TextView txtListaDeposito;
    private RecyclerView rvDeposito;
    Resources res;
    private int acrescenta;
    private DepositosAdapter listaDepositoAdapter;
    private List<Depositos> listaDepositos, listaDepositosFiltrada;
    private List<Depositos> listaDepositoVazia = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_consulta_fazenda);
        try {
            listaDepositos = AIDatabase.getInstance(this).getFazendasDAO().buscarTodas().get();

            carregaBotoes();
            setarCampos();
            setarRecyclerListaDepositos(false);
            setDepositoEscolhido();
        } catch (Exception e) {
            e.printStackTrace();
        }

        consultaDeposito.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setDepositoEscolhido();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setDepositoEscolhido() {
        try {
            if (!consultaDeposito.getText().toString().equalsIgnoreCase("")) {
                //BUSCA POR NOME
                if (!Character.isDigit(consultaDeposito.getText().toString().charAt(0))) {
                    if (listaDepositos.stream().anyMatch(item -> item.getNomeDeposito().toLowerCase(Locale.ROOT).contains(String.valueOf(consultaDeposito.getText())))) {
                        listaDepositosFiltrada = listaDepositos.stream().filter(itens -> itens.getNomeDeposito().toLowerCase(Locale.ROOT).contains(String.valueOf(consultaDeposito.getText()))).collect(Collectors.toList());
                        setarRecyclerListaDepositos(false);
                    } else {
                        setarRecyclerListaDepositos(true);
                    }
                    //BUSCA POR CODIGO
                } else if (Character.isDigit(consultaDeposito.getText().toString().charAt(0))) {
                    if (listaDepositos.stream().anyMatch(cod -> String.valueOf(cod.getCodDeposito()).toLowerCase(Locale.ROOT).contains(consultaDeposito.getText()))) {
                        listaDepositosFiltrada = listaDepositos.stream().filter(u -> String.valueOf(u.getCodDeposito()).toLowerCase(Locale.ROOT).contains(consultaDeposito.getText())).collect(Collectors.toList());
                        setarRecyclerListaDepositos(false);
                    } else {
                        setarRecyclerListaDepositos(true);
                    }
                }
            } else {
                listaDepositosFiltrada = listaDepositos;
                setarRecyclerListaDepositos(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setResultDepositoEscolhido(Depositos depositos) {
        setResult(Activity.RESULT_OK, this.getIntent().putExtra("resultadoBuscaDepositos", depositos));
        this.finish();
    }

    private void setarCampos() {
        res = getResources();
        setarTotalItens();
    }

    private void setarTotalItens() {
        if (listaDepositosFiltrada != null) {
            txtListaDeposito.setText(res.getString(R.string.lista_depositos, listaDepositosFiltrada.size()));
        } else {
            listaDepositosFiltrada = new ArrayList<>();
            txtListaDeposito.setText(res.getString(R.string.lista_depositos, 0));
        }
    }

    private void setarRecyclerListaDepositos(boolean vazio) {
        if (vazio)
            listaDepositoAdapter = new DepositosAdapter(listaDepositoVazia, this::setResultDepositoEscolhido);
        if (!vazio)
            listaDepositoAdapter = new DepositosAdapter(listaDepositosFiltrada, this::setResultDepositoEscolhido);
        rvDeposito.setAdapter(listaDepositoAdapter);
        if (acrescenta == 0) {
            rvDeposito.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
            acrescenta++;
        }
        rvDeposito.setLayoutManager(new LinearLayoutManager(this));
        if (vazio) txtListaDeposito.setText(res.getString(R.string.lista_depositos, 0));
        if (!vazio) setarTotalItens();
    }

    public void carregaBotoes() {
        consultaDeposito = findViewById(R.id.edit_fazendaCodigo);
        txtListaDeposito = findViewById(R.id.txt_listaFazenda);
        rvDeposito = findViewById(R.id.rv_ListaFazenda);
    }
}