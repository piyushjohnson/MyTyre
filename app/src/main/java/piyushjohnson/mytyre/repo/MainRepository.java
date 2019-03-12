package piyushjohnson.mytyre.repo;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import javax.inject.Inject;

import piyushjohnson.mytyre.common.QueryLiveData;
import piyushjohnson.mytyre.model.Tyre;

public class MainRepository {

    private final FirebaseFirestore firestore;

    @Inject
    public MainRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    public QueryLiveData<Tyre> popularTyres() {
        return new QueryLiveData<>(toQuery(), Tyre.class, false);
    }

    private Query toQuery() {
        Query query = firestore.collection("popular");
        return query;
    }


}
