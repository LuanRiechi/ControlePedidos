package br.edu.utfpr.alunos.controlepedidos.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.alunos.controlepedidos.modelo.Pedido;

@Dao
public interface PedidoDAO {
    @Insert
    long insert(Pedido pedido);

    @Delete
    void delete(Pedido pedido);

    @Update
    void update(Pedido pedido);

    @Query("SELECT * FROM pedido WHERE id = :id")
    Pedido queryForId(long id);

    @Query("SELECT * FROM pedido ")
    List<Pedido> queryAll();
}
