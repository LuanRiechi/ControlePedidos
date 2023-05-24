package br.edu.utfpr.alunos.controlepedidos.modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Pedido {


    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String lanche;
    private String adicional;
    @NonNull
    private String entrega;
    @NonNull
    private Float valor;
    @NonNull
    private String formapagamento;

    public Pedido(String lanche, String adicional, String entrega, Float valor, String formapagamento){
        setLanche(lanche);
        setAdicional(adicional);
        setEntrega(entrega);
        setValor(valor);
        setFormapagamento(formapagamento);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanche() {
        return lanche;
    }

    public void setLanche(String lanche) {
        this.lanche = lanche;
    }

    public String getAdicional() {
        return adicional;
    }

    public void setAdicional(String adicional) {
        this.adicional = adicional;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getFormapagamento() {
        return formapagamento;
    }

    public void setFormapagamento(String formapagamento) {
        this.formapagamento = formapagamento;
    }
}