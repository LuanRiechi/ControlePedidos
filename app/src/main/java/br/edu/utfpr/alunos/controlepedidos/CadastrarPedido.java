package br.edu.utfpr.alunos.controlepedidos;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CadastrarPedido extends AppCompatActivity {

    private EditText editTextLanche, ediTextValor;
    private CheckBox checboxBatata, checboxRefrigerante;
    private RadioGroup radioGroupRetirar;
    private Spinner spinnerPagamento;

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

    }

    private void popularSpinner (){
        ArrayList<String> lista = new ArrayList<>();

        lista.add(getString(R.string.cartao));
        lista.add(getString(R.string.dinehiro));
        lista.add(getString(R.string.pix));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        spinnerPagamento.setAdapter(adapter);
    }

    public void limparCampos (View view){
        editTextLanche.setText(null);
        checboxBatata.setChecked(false);
        checboxRefrigerante.setChecked(false);
        ediTextValor.setText(null);
        radioGroupRetirar.clearCheck();

        Toast.makeText(this, R.string.camposLimpos, Toast.LENGTH_LONG).show();

        editTextLanche.requestFocus();
    }

    public void salvar (View view){
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
        } else {
            adicionais = getString(R.string.adicionaisSelecionados) + " " + adicionais;
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
            Toast.makeText(this, "Selecione uma forma de retirar o pedido!", Toast.LENGTH_LONG).show();
            radioGroupRetirar.requestFocus();
            return;
        }

        if (valor == null || valor.trim().isEmpty()) {
            Toast.makeText(this, "valor nao pode ser vazio", Toast.LENGTH_LONG ).show();
            return;
        }

        if(pagamento == null){
            pagamento = getString(R.string.erroFormaPagamento);
        }

        Toast.makeText(this, lanche.trim() + "\n" + adicionais +"\n"+radioGroupMensagem+ "\n"+pagamento+"\n"+valor.trim(),Toast.LENGTH_LONG).show();
    }

}