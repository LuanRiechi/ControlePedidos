package br.edu.utfpr.alunos.controlepedidos;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.edu.utfpr.alunos.controlepedidos.modelo.Lanche;
import br.edu.utfpr.alunos.controlepedidos.persistencia.PedidosDatabase;

public class ListaLanches extends AppCompatActivity {

    private ListView listviewLanches;

    private ArrayAdapter<Lanche> listaAdapter;

    private List<Lanche> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lanches);

        listviewLanches = findViewById(R.id.listaLanche);
        carregaLanches();
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
}