package br.edu.utfpr.alunos.controlepedidos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PedidoAdapter extends BaseAdapter {
    private Context context;
    private List<Pedido> pedidos;
    private NumberFormat numberFormat;

    private static class PedidoHolder{
        public TextView textViewLanche;
        public TextView textViewAdcionais;
        public TextView textViewRetirada;
        public TextView textViewValor;
        public TextView textViewFormaPagamento;
    }

    public PedidoAdapter (Context context, List<Pedido> pedidos){
        this.context = context;
        this.pedidos = pedidos;

        numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
    }

    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public Object getItem(int i) {
        return pedidos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PedidoHolder holder;

        if (view ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adpter_lista_pedidos, viewGroup, false);

            holder = new PedidoHolder();

            holder.textViewLanche = view.findViewById(R.id.textViewLanche);
            holder.textViewAdcionais = view.findViewById(R.id.textViewAdicional);
            holder.textViewRetirada = view.findViewById(R.id.textViewRetirada);
            holder.textViewValor = view.findViewById(R.id.textViewValor);
            holder.textViewFormaPagamento = view.findViewById(R.id.textViewFormaPagamento);

            view.setTag(holder);

        } else {

            holder = (PedidoHolder) view.getTag();
        }

        holder.textViewLanche.setText(pedidos.get(i).getLanche());

        holder.textViewAdcionais.setText(pedidos.get(i).getAdicional());

        holder.textViewRetirada.setText(pedidos.get(i).getEntrega());

        String valorFormatado = numberFormat.format(pedidos.get(i).getValor());
        holder.textViewValor.setText(valorFormatado);

        holder.textViewFormaPagamento.setText(pedidos.get(i).getFormapagamento());

        return view;
    }
}
