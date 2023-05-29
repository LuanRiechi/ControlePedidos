package br.edu.utfpr.alunos.controlepedidos.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.alunos.controlepedidos.modelo.Lanche;

@Dao
public interface LancheDAO {
    @Insert
    long insert (Lanche lanche);

    @Delete
    void delet (Lanche lanche);

    @Update
    void update (Lanche lanche);

    @Query("SELECT count(*) FROM lanches WHERE nome = :nome LIMIT 1")
    int queryforcountLancheName(String nome);

    @Query("SELECT * FROM lanches WHERE nome = :nome")
    Lanche queryforLanche(String nome);

    @Query("SELECT * FROM lanches")
    List<Lanche> queryAll();

    @Query("SELECT count(*) FROM lanches")
    int total();

}
