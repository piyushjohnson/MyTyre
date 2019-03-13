package piyushjohnson.mytyre.repo;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import piyushjohnson.mytyre.common.QueryLiveData;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.util.Filters;

public class MainRepository {

    private final FirebaseFirestore firestore;

    @Inject
    public MainRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    public QueryLiveData<Tyre> popularTyres(@Nullable Filters filters) {
        return new QueryLiveData<>(toQuery("popular", filters), Tyre.class, false);
    }

    public QueryLiveData<Tyre> getTyres(Filters filters) {
        return new QueryLiveData<>(toQuery("tyres", filters), Tyre.class, false);
    }

    private Query toQuery(String collectionName, Filters filters) {
        Query query = firestore.collection(collectionName);
        if (filters != null) {
            if (filters.hasVehicleType())
                query = firestore.collection(collectionName).document(filters.getVehicleType()).collection("all");
        }
        return query;
    }

}
