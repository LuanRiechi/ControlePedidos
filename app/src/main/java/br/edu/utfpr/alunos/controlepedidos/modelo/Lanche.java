package br.edu.utfpr.alunos.controlepedidos.modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lanches")
public class Lanche {

    @PrimaryKey
    @NonNull
    private String nome;

    public Lanche(@NonNull String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString(){
        return getNome();
    }
}
