package br.edu.utfpr.alunos.controlepedidos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CadastrarPedido extends AppCompatActivity {

    private EditText editTextLanche, ediTextValor;
    private CheckBox checboxBatata, checboxRefrigerante;
    private RadioGroup radioGroupRetirar;
    private Spinner spinnerPagamento;


    public static final String ID       = "ID";
    public static final String LANCHE       = "LANCHE";
    public static final String VALOR       = "VALOR";
    public static final String ADICIONAIS       = "ADICIONAIS";
    public static final String ENTREGA       = "ENTREGA";
    public static final String PAGAMENTO       = "PAGAMENTO";

    public static final String MODO = "MODO";
    public static final int NOVO = 1;
    public static final int ALTERAR = 2;

    private int modo;

    public static void novoPedido (AppCompatActivity activity){
        Intent intent = new Intent(activity,CadastrarPedido.class);
        intent.putExtra(MODO, NOVO);
        activity.startActivityForResult(intent,NOVO);
    }

    public static void alterarPedido (AppCompatActivity activity, Pedido pedido){
        Intent intent = new Intent(activity, CadastrarPedido.class);
        intent.putExtra(MODO, ALTERAR);

        intent.putExtra(ID, pedido.getId());
        intent.putExtra(LANCHE, pedido.getLanche());
        intent.putExtra(ADICIONAIS, pedido.getAdicional());
        intent.putExtra(ENTREGA, pedido.getEntrega());
        intent.putExtra(VALOR, pedido.getValor());
        intent.putExtra(PAGAMENTO, pedido.getFormapagamento());

        activity.startActivityForResult(intent, ALTERAR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pedido);
        editTextLanche = findViewById(R.id.editTextLanche);
        checboxBatata = findViewById(R.id.checkBoxBatata);
        checboxRefrigerante = findViewById(R.id.checkBoxRefrigerante);
        ediTextValor = findViewById(R.id.editTextValor);
        radioGroupRetirar = findViewById(R.id.RadioGroupRetirar);
        spinnerPagamento = findViewById(R.id.spinnerPagamento);


        popularSpinner();


        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        if (bundle != null){
            modo = bundle.getInt(MODO);
            if(modo == NOVO){
                setTitle(getString(R.string.cadpedido));
            }else {
                editTextLanche.setText(bundle.getString(LANCHE));
                String adicional = bundle.getString(ADICIONAIS);
                if (adicional.equals(getString(R.string.batata) + " ")){
                    checboxBatata.setChecked(true);
                }
                if (adicional.equals(getString(R.string.refrigerante) + " ")){
                    checboxRefrigerante.setChecked(true);
                }
                if (adicional.equals( getString(R.string.batata) + " " + getString(R.string.refrigerante) + " ")){
                    checboxRefrigerante.setChecked(true);
                    checboxBatata.setChecked(true);
                }
                String entrega = bundle.getString(ENTREGA);
                RadioButton button;

                if (entrega.equals(getString(R.string.retirarlocal))){
                    button = findViewById(R.id.radioButtonRetirar);
                    button.setChecked(true);
                }else{
                    button = findViewById(R.id.radioButtonEntregar);
                    button.setChecked(true);
                }

                ediTextValor.setText(String.valueOf(bundle.getFloat(VALOR)));

                String pagamento = bundle.getString(PAGAMENTO);

                for (int pos = 0; pos <spinnerPagamento.getAdapter().getCount(); pos++){
                    String valor = (String) spinnerPagamento.getItemAtPosition(pos);
                    if (valor.equals(pagamento)){
                        spinnerPagamento.setSelection(pos);
                        break;
                    }

                }
                setTitle(getString(R.string.editapedido));
            }
        }

    }

    private void popularSpinner (){
        ArrayList<String> lista = new ArrayList<>();

        lista.add(getString(R.string.cartao));
        lista.add(getString(R.string.dinehiro));
        lista.add(getString(R.string.pix));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        spinnerPagamento.setAdapter(adapter);
    }



    public void limparCamposMenu (){
        editTextLanche.setText(null);
        checboxBatata.setChecked(false);
        checboxRefrigerante.setChecked(false);
        ediTextValor.setText(null);
        radioGroupRetirar.clearCheck();

        Toast.makeText(this, R.string.camposLimpos, Toast.LENGTH_LONG).show();

        editTextLanche.requestFocus();
    }


    public void salvarMenu (){
        String lanche = editTextLanche.getText().toString();
        String valor = ediTextValor.getText().toString();
        String adicionais = "";
        String radioGroupMensagem = "";
        String pagamento = (String) spinnerPagamento.getSelectedItem();

        if (lanche == null || lanche.trim().isEmpty()){
            Toast.makeText(this, R.string.erroLanche, Toast.LENGTH_LONG).show();
            editTextLanche.requestFocus();
            return;

        }


        if (checboxBatata.isChecked() ){
            adicionais += getString(R.string.batata) + " ";

        }
        if (checboxRefrigerante.isChecked()) {
            adicionais += getString(R.string.refrigerante) + " ";
        }
        if (adicionais.equals("")) {
            adicionais = getString(R.string.semAdicional);
        }

        switch (radioGroupRetirar.getCheckedRadioButtonId()){

            case R.id.radioButtonRetirar:
                radioGroupMensagem = getString(R.string.retirarlocal);
                break;

            case R.id.radioButtonEntregar:
                radioGroupMensagem = getString(R.string.entregar);
                break;

            default:
                radioGroupMensagem = "";
        }
        if (radioGroupMensagem == ""){
            Toast.makeText(this, R.string.erroRetirar, Toast.LENGTH_LONG).show();
            radioGroupRetirar.requestFocus();
            return;
        }

        if (valor == null || valor.trim().isEmpty()) {
            Toast.makeText(this, R.string.erroValor, Toast.LENGTH_LONG ).show();
            ediTextValor.requestFocus();
            return;
        }

        if(pagamento == null){
            pagamento = getString(R.string.erroFormaPagamento);
        }

        Intent intent = new Intent();
        intent.putExtra(LANCHE, lanche);
        intent.putExtra(ADICIONAIS, adicionais);
        intent.putExtra(ENTREGA, radioGroupMensagem);
        intent.putExtra(VALOR, valor);
        intent.putExtra(PAGAMENTO, pagamento);

        setResult(Activity.RESULT_OK, intent);

        finish();

    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastro_opcoes, menu);
        return true;
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
}