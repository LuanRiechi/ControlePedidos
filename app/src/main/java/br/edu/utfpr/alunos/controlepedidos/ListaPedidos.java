package br.edu.utfpr.alunos.controlepedidos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ListaPedidos extends AppCompatActivity {

    private ListView listViewPedidos;
    private ArrayList<Pedido> pedidos;

    private PedidoAdapter pedidoAdapter;

    private NumberFormat numberFormat;

    private Button btnAdcionar;

    private Button btnSobre;

    private ActionMode actionMode;

    private int posicaoSelecionada = -1;

    private View viewSelecionada;

    private static final String ARQUVIO = "br.edu.utfpr.alunos.controlepedidos.PREFERENCIAS_TEMA";

    private static final String TEMA = "TEMA";

    private int temaApp = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.item_selecionado,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem menuItem) {

            switch (menuItem.getItemId()){

                case R.id.menuItemEditar:
                    alterar();
                    mode.finish();
                    return true;

                case R.id.menuItemExcluir:
                    mode.finish();
                    ExcluirPedido();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            if (viewSelecionada != null){
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }
            actionMode = null;
            viewSelecionada = null;
            listViewPedidos.setEnabled(true);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);

        listViewPedidos = findViewById(R.id.ListaPedidos);

        btnAdcionar = findViewById(R.id.btnAdicionar);
        btnSobre = findViewById(R.id.btnSobre);


        lerpreferenciaTema();


        numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));

        listViewPedidos.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        posicaoSelecionada = i;

                        Pedido pedido = (Pedido) listViewPedidos.getItemAtPosition(i);

                        String valorFormatado = numberFormat.format(pedido.getValor());

                        Toast.makeText(getApplicationContext(),pedido.getId()+ "\n"+
                                getString(R.string.lanche)+" "+pedido.getLanche()+"\n"
                                        +getString(R.string.adicionais)+" "+pedido.getAdicional()+ "\n"
                                        +pedido.getEntrega()+ "\n"
                                        + getString(R.string.valor)+" "+valorFormatado+ "\n"
                                        + getString(R.string.formaPagamento)+" "+ pedido.getFormapagamento()
                                , Toast.LENGTH_LONG).show();
                    }
                }

        );
        listViewPedidos.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewPedidos.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(actionMode !=null){
                            return false;
                        }
                        posicaoSelecionada = i;
                        view.setBackgroundColor(Color.LTGRAY);
                        viewSelecionada = view;
                        listViewPedidos.setEnabled(false);
                        actionMode = startActionMode(mActionModeCallback);
                        return true;
                    }
                }
        );

        popularLista();

    }

    public void Sobre (View view){
        Intent intent = new Intent(this, sobreAPP.class);

        startActivity(intent);
    }

    public void SobreMenu (MenuItem item){
        Intent intent = new Intent(this, sobreAPP.class);

        startActivity(intent);
    }

    public void Adicionar (View view){
        br.edu.utfpr.alunos.controlepedidos.CadastrarPedido.novoPedido(this);
    }

    public void AdicionarMenu (MenuItem item){
        br.edu.utfpr.alunos.controlepedidos.CadastrarPedido.novoPedido(this);
    }

    public void alterar (){
        Pedido pedido = pedidos.get(posicaoSelecionada);

        br.edu.utfpr.alunos.controlepedidos.CadastrarPedido.alterarPedido(this,pedido);
    }

    public void ExcluirPedido(){
        pedidos.remove(posicaoSelecionada);
        pedidoAdapter.notifyDataSetChanged();

    }


    private   void popularLista(){

        pedidos = new ArrayList<>();

        pedidoAdapter = new PedidoAdapter(this,pedidos);

        listViewPedidos.setAdapter(pedidoAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();

            String lanche = bundle.getString(br.edu.utfpr.alunos.controlepedidos.CadastrarPedido.LANCHE);
            String adicional = bundle.getString(br.edu.utfpr.alunos.controlepedidos.CadastrarPedido.ADICIONAIS);
            String entrega = bundle.getString(br.edu.utfpr.alunos.controlepedidos.CadastrarPedido.ENTREGA);
            Float valorLanche = Float.parseFloat(bundle.getString(br.edu.utfpr.alunos.controlepedidos.CadastrarPedido.VALOR));
            String Pagamento = bundle.getString(br.edu.utfpr.alunos.controlepedidos.CadastrarPedido.PAGAMENTO);

            if (requestCode == br.edu.utfpr.alunos.controlepedidos.CadastrarPedido.ALTERAR){
                Pedido pedido = pedidos.get(posicaoSelecionada);
                pedido.setLanche(lanche);
                pedido.setAdicional(adicional);
                pedido.setEntrega(entrega);
                pedido.setValor(valorLanche);
                pedido.setFormapagamento(Pagamento);

                posicaoSelecionada = -1;

            }else{
                Pedido pedido = new Pedido(lanche,adicional,entrega,valorLanche,Pagamento);
                pedidos.add(pedido);
            }



            pedidoAdapter.notifyDataSetChanged();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_opcoes, menu);
        return true;
    }

    public void salvarPreferenciaTema(int novoTema){
        SharedPreferences shared = getSharedPreferences(ARQUVIO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        editor.putInt(TEMA, novoTema);
        editor.commit();
        temaApp = novoTema;

        AppCompatDelegate.setDefaultNightMode(temaApp);
    }

    public void lerpreferenciaTema (){
        SharedPreferences shared = getSharedPreferences(ARQUVIO,Context.MODE_PRIVATE);
        temaApp = shared.getInt(TEMA, temaApp);

        AppCompatDelegate.setDefaultNightMode(temaApp);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.menuItemEscuro:
                salvarPreferenciaTema(AppCompatDelegate.MODE_NIGHT_YES);
                return true;

            case R.id.menuItemClaro:
                salvarPreferenciaTema(AppCompatDelegate.MODE_NIGHT_NO);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item;
        switch (temaApp){

            case AppCompatDelegate.MODE_NIGHT_YES:
                item = menu.findItem(R.id.menuItemEscuro);
                break;

            case AppCompatDelegate.MODE_NIGHT_NO:
                item = menu.findItem(R.id.menuItemClaro);
                break;

            default:
                return false;
        }
        item.setChecked(true);
        return true;
    }
}