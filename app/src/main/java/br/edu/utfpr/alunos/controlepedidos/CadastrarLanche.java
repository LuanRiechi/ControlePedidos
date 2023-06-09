package br.edu.utfpr.alunos.controlepedidos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.alunos.controlepedidos.modelo.Lanche;
import br.edu.utfpr.alunos.controlepedidos.persistencia.PedidosDatabase;

public class CadastrarLanche extends AppCompatActivity {

    private EditText editTextLanche;

    public static final String MODO = "MODO";

    public static final String NOME = "NOME";
    public static final int NOVO = 1;
    public static final int ALTERAR = 2;

    private Lanche lanche;

    private int modo;

    public static void novoLanche (AppCompatActivity activity){
        Intent intent = new Intent(activity,CadastrarLanche.class);
        intent.putExtra(MODO, NOVO);
        activity.startActivityForResult(intent,NOVO);
    }

    public static void alterarLanche (AppCompatActivity activity, Lanche lanche){
        Intent intent = new Intent(activity, CadastrarLanche.class);
        intent.putExtra(MODO, ALTERAR);

        intent.putExtra(NOME, lanche.getNome());

        activity.startActivityForResult(intent, ALTERAR);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_lanche);
        editTextLanche = findViewById(R.id.editTextLancheNome);


        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            modo = bundle.getInt(MODO);
            if (modo == NOVO) {
                setTitle(getString(R.string.registrarLanche));
            } else {
                setTitle(getString(R.string.alterarLanche));
                String nomeLanche = bundle.getString(NOME);
                PedidosDatabase database = PedidosDatabase.getDatabase(this);
                lanche = database.lancheDAO().queryforLanche(nomeLanche);
                editTextLanche.setText(lanche.getNome());
            }
        }

    }
    public void limparCamposMenu (){
        editTextLanche.setText(null);

        Toast.makeText(this, R.string.camposLimpos, Toast.LENGTH_LONG).show();

        editTextLanche.requestFocus();
    }

    public void salvarMenu(){
        String nome = editTextLanche.getText().toString();
        PedidosDatabase database = PedidosDatabase.getDatabase(this);


        if (nome == null || nome.trim().isEmpty()){
            Toast.makeText(this, R.string.erroLanche, Toast.LENGTH_LONG).show();
            editTextLanche.requestFocus();
            return;

        }else {
            int contador = database.lancheDAO().queryforcountLancheName(nome);
            if (contador > 0 ){
                Toast.makeText(this, R.string.lancheExistente, Toast.LENGTH_LONG).show();
                editTextLanche.requestFocus();
            }else {
                if (modo == NOVO) {
                    lanche = new Lanche(nome);
                    database.lancheDAO().insert(lanche);
                } else {
                    lanche.setNome(nome);
                    database.lancheDAO().update(lanche);
                }

                setResult(Activity.RESULT_OK);
                finish();
            }

        }

    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuItemSalvar:
                salvarMenu();
                return true;

            case R.id.menuItemLimpar:
                limparCamposMenu();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastro_lanche_menu,menu);
        return true;
    }

}