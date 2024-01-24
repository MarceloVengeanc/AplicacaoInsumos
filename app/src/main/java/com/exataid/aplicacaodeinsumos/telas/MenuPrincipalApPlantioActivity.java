package com.exataid.aplicacaodeinsumos.telas;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.exataid.aplicacaodeinsumos.AIDatabase;
import com.exataid.aplicacaodeinsumos.R;
import com.exataid.aplicacaodeinsumos.banco.modelos.Depositos;
import com.exataid.aplicacaodeinsumos.banco.modelos.Funcionario;
import com.exataid.aplicacaodeinsumos.banco.modelos.Lotes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MenuPrincipalApPlantioActivity extends AppCompatActivity {

    private Button btnApontamento, btnConsulta, btnSinc;
    private TextView txtVersao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carregaBotoes();
        setaBotoes();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };

    }
    private void setaBotoes() {
        btnApontamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalApPlantioActivity.this, ApontamentoInsumosActivity.class);
                startActivity(i);
            }
        });

        btnConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalApPlantioActivity.this, ConsultaActivity.class);
                startActivity(i);
            }
        });

        btnSinc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalApPlantioActivity.this, SincronizarActivity.class);
                startActivity(i);
            }
        });
    }

    public void criaBanco() {

        AIDatabase db = AIDatabase.getInstance(this.getApplicationContext());
        List<Depositos> fazendas = new ArrayList<>();
        List<Lotes> talhoes = new ArrayList<>();
        List<Funcionario> funcionarios = new ArrayList<>();

        Funcionario funcionario = new Funcionario();
        funcionario.setMatricula(1001);
        funcionario.setNome("Teste");
        funcionario.setLogin("teste1");
        funcionario.setSenha("exata");
        funcionarios.add(funcionario);

        Depositos fazenda = new Depositos();
        fazenda.setNomeDeposito("Vale Verde");
        fazenda.setCodDeposito(12455);
        fazendas.add(fazenda);

        Depositos fazenda1 = new Depositos();
        fazenda1.setNomeDeposito("Laranjeira");
        fazenda1.setCodDeposito(78945);
        fazendas.add(fazenda1);

        Depositos fazenda2 = new Depositos();
        fazenda2.setNomeDeposito("Podoado");
        fazenda2.setCodDeposito(85274);
        fazendas.add(fazenda2);

        Depositos fazenda3 = new Depositos();
        fazenda3.setNomeDeposito("Lagoa Lagoa");
        fazenda3.setCodDeposito(45683);
        fazendas.add(fazenda3);

        Lotes lotes = new Lotes();
        lotes.setCodTalhao(1111);
        lotes.setAreaTalhao(20);
        lotes.setCodFazenda(12455);
        talhoes.add(lotes);

        Lotes lotes1 = new Lotes();
        lotes1.setCodTalhao(1245);
        lotes1.setAreaTalhao(50);
        lotes1.setCodFazenda(12455);
        talhoes.add(lotes1);

        Lotes lotes9 = new Lotes();
        lotes9.setCodTalhao(9999);
        lotes9.setAreaTalhao(300);
        lotes9.setCodFazenda(12455);
        talhoes.add(lotes9);

        Lotes lotes10 = new Lotes();
        lotes10.setCodTalhao(1000);
        lotes10.setAreaTalhao(400);
        lotes10.setCodFazenda(12455);
        talhoes.add(lotes10);

        Lotes lotes2 = new Lotes();
        lotes2.setCodTalhao(6589);
        lotes2.setAreaTalhao(30);
        lotes2.setCodFazenda(85274);
        talhoes.add(lotes2);

        Lotes lotes7 = new Lotes();
        lotes7.setCodTalhao(7777);
        lotes7.setAreaTalhao(60);
        lotes7.setCodFazenda(85274);
        talhoes.add(lotes7);

        Lotes lotes8 = new Lotes();
        lotes8.setCodTalhao(8888);
        lotes8.setAreaTalhao(140);
        lotes8.setCodFazenda(85274);
        talhoes.add(lotes8);

        Lotes lotes3 = new Lotes();
        lotes3.setCodTalhao(6699);
        lotes3.setAreaTalhao(80);
        lotes3.setCodFazenda(45683);
        talhoes.add(lotes3);

        Lotes lotes4 = new Lotes();
        lotes4.setCodTalhao(3333);
        lotes4.setAreaTalhao(50);
        lotes4.setCodFazenda(45683);
        talhoes.add(lotes4);

        Lotes lotes5 = new Lotes();
        lotes5.setCodTalhao(4444);
        lotes5.setAreaTalhao(120);
        lotes5.setCodFazenda(45683);
        talhoes.add(lotes5);

        Lotes lotes6 = new Lotes();
        lotes6.setCodTalhao(555);
        lotes6.setAreaTalhao(140);
        lotes6.setCodFazenda(45683);
        talhoes.add(lotes6);

        try {
            AIDatabase.getInstance(this).getFazendasDAO().inserir(fazendas).get();
            AIDatabase.getInstance(this).getTalhaoDAO().inserir(talhoes).get();
            AIDatabase.getInstance(this).getFuncionarioDAO().inserir(funcionarios).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void carregaBotoes() {
        txtVersao = findViewById(R.id.txtVersao);
        try {
            txtVersao.setText(this.getString(R.string.msg_versao_app, this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0).versionName));
        } catch (PackageManager.NameNotFoundException e) {
            txtVersao.setText("");
            e.printStackTrace();
        }
        btnApontamento = findViewById(R.id.btnApontamento);
        btnConsulta = findViewById(R.id.btnConsulta);
        btnSinc = findViewById(R.id.btnSinc);
        txtVersao = findViewById(R.id.txtVersao);
    }
}