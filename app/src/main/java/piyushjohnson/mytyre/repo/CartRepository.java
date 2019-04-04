package piyushjohnson.mytyre.repo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import piyushjohnson.mytyre.db.daos.CartTyreDAO;
import piyushjohnson.mytyre.model.Tyre;

public class CartRepository {

    private final CartTyreDAO dao;
    private static final int CORE_POOL_SIZE = 1;
    private static final int MAX_POOL_SIZE = 3;
    private static final long KEEP_ALIVE_TIMEOUT = 5000;
    private final ExecutorService executorService;

    @Inject
    public CartRepository(CartTyreDAO cartTyreDAO) {
        this.dao = cartTyreDAO;
        this.executorService = Executors.newFixedThreadPool(1);
    }

    public LiveData<List<Tyre>> getCartTyres() {
        return dao.getCartTyres();
    }

    public void addCartTyre(Tyre tyre) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                dao.addCartTyre(tyre);
            }
        });
    }

    public void removeCartTyre(Tyre tyre) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                dao.removeCartTyre(tyre);
            }
        });
    }
}
