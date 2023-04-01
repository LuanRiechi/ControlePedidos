package br.edu.utfpr.alunos.controlepedidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ListaPedidos extends AppCompatActivity {

    private ListView listViewPedidos;
    private ArrayList<Pedido> pedidos;

    private NumberFormat numberFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);

        listViewPedidos = findViewById(R.id.ListaPedidos);

        numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));

        listViewPedidos.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Pedido pedido = (Pedido) listViewPedidos.getItemAtPosition(i);

                        String valorFormatado = numberFormat.format(pedido.getValor());

                        Toast.makeText(getApplicationContext(),
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

    public void Adicionar (View view){
        Intent intent = new Intent(this, CadastrarPedido.class);

        startActivity(intent);
    }

    private  void popularLista(){
        String [] lanches = getResources().getStringArray(R.array.lanches);
        String [] adicionais = getResources().getStringArray(R.array.adicionais);
        String [] entregas = getResources().getStringArray(R.array.retiradas);
        String [] valores = getResources().getStringArray(R.array.valores);
        String [] formasPagamentos = getResources().getStringArray(R.array.formaspagamentos);

        pedidos = new ArrayList<>();

        Pedido pedido;
        String lanche;
        String adicional;
        String entrega;
        float valorLanche;
        String formaPagamento;

        for (int i = 0; i < lanches.length; i++){
            lanche = lanches[i];
            adicional = adicionais[i];
            entrega = entregas[i];
            valorLanche = Float.parseFloat(valores[i]);
            formaPagamento = formasPagamentos[i];

            pedido = new Pedido(lanche,adicional,entrega,valorLanche,formaPagamento);


            pedidos.add(pedido);
        }

        PedidoAdapter pedidoAdapter = new PedidoAdapter(this,pedidos);

        listViewPedidos.setAdapter(pedidoAdapter);

    }
}