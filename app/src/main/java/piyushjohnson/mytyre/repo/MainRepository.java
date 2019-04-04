package piyushjohnson.mytyre.repo;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import javax.inject.Inject;

import piyushjohnson.mytyre.common.DocumentLiveData;
import piyushjohnson.mytyre.common.QueryLiveData;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.util.Filters;

public class MainRepository {

    private static final String TAG = "MainRepository";
    private final FirebaseFirestore firestore;

    @Inject
    public MainRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
        Log.i(TAG, "MainRepository: initialised main repo");
        Log.d(TAG, "MainRepository() called with: firestore = [" + firestore.hashCode() + "]");
    }

    public QueryLiveData<Tyre> popularTyres() {
        Log.i(TAG, "popularTyres: requested popular tyres");
        Log.d(TAG, "popularTyres() called");
        return new QueryLiveData<Tyre>(toQuery("popular", null), Tyre.class, false);
    }

    public QueryLiveData<Tyre> getTyres(Filters filters) {
        Log.i(TAG, "getTyres: requested tyres");
        Log.d(TAG, "getTyres() called with: filters = [" + filters + "]");
        return new QueryLiveData<>(toQuery("tyres", filters), Tyre.class, false);
    }

    public DocumentLiveData<Tyre> getTyre(String documentName) {
        return new DocumentLiveData<>(Tyre.class, toDoc("tyres", documentName));
    }

    private Query toQuery(String collectionName, Filters filters) {
        Query query = firestore.collection(collectionName);
        if (filters != null) {
            if (filters.hasVehicleType())
                query = firestore.collection(collectionName).whereEqualTo(Tyre.FIELD_VEHICLE_TYPE, filters.getVehicleType());
        }
        Log.i(TAG, "toQuery: created query");
        return query;
    }

    public DocumentReference toDoc(String collectionName, String documentName) {
        DocumentReference reference = null;
        if (documentName != null && collectionName != null) {
            reference = firestore.collection("tyres").document(documentName.trim().toLowerCase().replaceAll("[\\s /]", "_"));
        }
        return reference;
    }
}
