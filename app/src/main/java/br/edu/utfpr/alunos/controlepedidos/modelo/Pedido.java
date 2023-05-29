package br.edu.utfpr.alunos.controlepedidos.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "pedidos",
            foreignKeys = @ForeignKey(entity = Lanche.class,
                                        parentColumns = "nome",
                                        childColumns = "lancheNome"))
public class Pedido {


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(index = true)
    private String lancheNome;
    private String adicional;
    @NonNull
    private String entrega;
    @NonNull
    private Float valor;
    @NonNull
    private String formapagamento;

    public Pedido(String lancheNome, String adicional, String entrega, Float valor, String formapagamento){
        setLancheNome(lancheNome);
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

    public String getLancheNome() {
        return lancheNome;
    }

    public void setLancheNome(String lancheNome) {
        this.lancheNome = lancheNome;
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
