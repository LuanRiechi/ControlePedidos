package br.edu.utfpr.alunos.controlepedidos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    public static final int CadastrarPedido = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);

        listViewPedidos = findViewById(R.id.ListaPedidos);

        btnAdcionar = findViewById(R.id.btnAdicionar);
        btnAdcionar.setVisibility(View.INVISIBLE);
        btnSobre = findViewById(R.id.btnSobre);
        btnSobre.setVisibility(View.INVISIBLE);


        numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));

        listViewPedidos.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
        Intent intent = new Intent(this, CadastrarPedido.class);

        startActivityForResult(intent, CadastrarPedido);
    }

    public void AdicionarMenu (MenuItem item){
        Intent intent = new Intent(this, CadastrarPedido.class);

        startActivityForResult(intent, CadastrarPedido);
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

            Pedido pedido = new Pedido(lanche,adicional,entrega,valorLanche,Pagamento);
            pedidos.add(pedido);

            pedidoAdapter.notifyDataSetChanged();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_opcoes, menu);
        return true;
    }

}