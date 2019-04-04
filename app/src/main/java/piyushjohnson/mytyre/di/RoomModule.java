package piyushjohnson.mytyre.di;

import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import piyushjohnson.mytyre.db.AppDB;
import piyushjohnson.mytyre.db.daos.CartTyreDAO;

@Module
public class RoomModule {
    private AppDB db;

    public RoomModule(Context context) {
        db = Room.databaseBuilder(context, AppDB.class, "mytyre-db").fallbackToDestructiveMigration().build();
    }

    @Singleton
    @Provides
    AppDB providesAppDB() {
        return db;
    }

    @Singleton
    @Provides
    CartTyreDAO providesCartTyreDAO() {
        return db.getCartTyreDAO();
    }
}
