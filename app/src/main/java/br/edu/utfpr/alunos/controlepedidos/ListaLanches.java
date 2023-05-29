package br.edu.utfpr.alunos.controlepedidos;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.edu.utfpr.alunos.controlepedidos.modelo.Lanche;
import br.edu.utfpr.alunos.controlepedidos.persistencia.PedidosDatabase;

public class ListaLanches extends AppCompatActivity {

    private ListView listviewLanches;

    private ArrayAdapter<Lanche> listaAdapter;

    private List<Lanche> lista;

    private ActionMode actionMode;

    private int posicaoSelecionada = -1;

    private View viewSelecionada;

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
                    excluir();
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
            listviewLanches.setEnabled(true);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lanches);

        setTitle(R.string.lanches);

        listviewLanches = findViewById(R.id.listaLanche);
        carregaLanches();

        listviewLanches.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listviewLanches.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(actionMode !=null){
                            return false;
                        }
                        posicaoSelecionada = i;
                        view.setBackgroundColor(Color.LTGRAY);
                        viewSelecionada = view;
                        listviewLanches.setEnabled(false);
                        actionMode = startActionMode(mActionModeCallback);
                        return true;
                    }
                }
        );

    }

    public void carregaLanches(){
        PedidosDatabase database = PedidosDatabase.getDatabase(this);
        lista = database.lancheDAO().queryAll();

        listaAdapter = new ArrayAdapter<>(ListaLanches.this,
                                            android.R.layout.simple_list_item_1,
                                            lista);
        listviewLanches.setAdapter(listaAdapter);
    }

    public void AdicionarMenu (MenuItem item){
            CadastrarLanche.novoLanche(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_lanches_menu,menu);
        return true;
    }

    public void alterar(){
        Lanche lanche = lista.get(posicaoSelecionada);
        CadastrarLanche.alterarLanche(this,lanche);
    }

    public void excluir(){
        PedidosDatabase database = PedidosDatabase.getDatabase(this);
        Lanche lanche = lista.get(posicaoSelecionada);
        int UsoLanche = database.pedidoDAO().queryForLancheNome(lanche.getNome());

        if (UsoLanche != 0){
            avisoSemLanche(this,R.string.lancheUsado);
        } else {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i){
                        case DialogInterface.BUTTON_POSITIVE:
                            database.lancheDAO().delet(lanche);
                            lista.remove(posicaoSelecionada);
                            listaAdapter.notifyDataSetChanged();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            confirmacaoAlert(this,getString(R.string.excluirLanche),listener);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if((requestCode == CadastrarLanche.ALTERAR ||requestCode == CadastrarLanche.NOVO) && resultCode == Activity.RESULT_OK){
            carregaLanches();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void confirmacaoAlert (Context contexto, String mensagem, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        builder.setTitle(R.string.confirmacao);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(mensagem);

        builder.setPositiveButton(R.string.sim, listener);
        builder.setNegativeButton(R.string.nao, listener);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public  void avisoSemLanche(Context contexto, int idTexto){

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        builder.setTitle(R.string.aviso);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(idTexto);

        builder.setNeutralButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}