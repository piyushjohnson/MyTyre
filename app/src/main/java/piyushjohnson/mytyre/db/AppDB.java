package piyushjohnson.mytyre.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import piyushjohnson.mytyre.db.daos.CartTyreDAO;
import piyushjohnson.mytyre.model.Tyre;

@Database(entities = {Tyre.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {

    public abstract CartTyreDAO getCartTyreDAO();
}
