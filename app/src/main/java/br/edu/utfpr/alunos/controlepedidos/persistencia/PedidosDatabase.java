package br.edu.utfpr.alunos.controlepedidos.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.utfpr.alunos.controlepedidos.modelo.Pedido;

@Database(entities = {Pedido.class}, version = 1,exportSchema = false)
public abstract class PedidosDatabase extends RoomDatabase {
    public abstract PedidoDAO pedidoDAO();

    private static PedidosDatabase instance;

    public static PedidosDatabase getDatabase(final Context context){
        if (instance == null){
            synchronized (PedidosDatabase.class){
                if (instance == null){
                    instance = Room.databaseBuilder(context,
                                                    PedidosDatabase.class,
                                                    "pedidos.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}
