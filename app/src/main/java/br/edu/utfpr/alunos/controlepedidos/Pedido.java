package br.edu.utfpr.alunos.controlepedidos;

public class Pedido {

    private static  int sequencia = 0;

    private int id;
    private String lanche;
    private String adicional;
    private String entrega;
    private Float valor;
    private String formapagamento;

    public Pedido(String lanche, String adicional, String entrega, Float valor, String formapagamento){
        this.id = sequencia++;
        setLanche(lanche);
        setAdicional(adicional);
        setEntrega(entrega);
        setValor(valor);
        setFormapagamento(formapagamento);
    }

    public int getId() {
        return id;
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
