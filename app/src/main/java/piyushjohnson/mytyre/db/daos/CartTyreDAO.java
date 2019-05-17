package piyushjohnson.mytyre.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import piyushjohnson.mytyre.model.Tyre;

@Dao
public interface CartTyreDAO {

    @Query("SELECT * FROM tyre")
    LiveData<List<Tyre>> getCartTyres();

    @Insert
    void addCartTyre(Tyre cartTyre);

    @Delete
    void removeCartTyre(Tyre cartTyre);

    @Update
    void updateCartTyre(Tyre cartTyre);
}
