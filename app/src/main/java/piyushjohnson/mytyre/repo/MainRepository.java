package piyushjohnson.mytyre.repo;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Map;

import javax.inject.Inject;

import piyushjohnson.mytyre.common.DocumentLiveData;
import piyushjohnson.mytyre.common.QueryLiveData;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.model.TyreParams;
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

    public DocumentLiveData<TyreParams> getTyreParams(Filters filters) {
        String documentName = "car";
        if (filters.hasVehicleType()) {
            documentName = filters.getVehicleType();
        }
        Log.d(TAG, "getTyreParams() called with: documentName = [" + documentName + "]");
        return new DocumentLiveData<>(TyreParams.class, toDoc("param", documentName));
    }

    private Query toQuery(String collectionName, Filters filters) {
        Query query = firestore.collection(collectionName);
        CollectionReference reference = firestore.collection(collectionName);
        if (filters != null) {
            if (filters.hasVehicleType())
                query = reference.whereEqualTo(Tyre.FIELD_VEHICLE_TYPE, filters.getVehicleType());
            if (filters.hasTyreParameters()) {
                for (Map.Entry<String, String> parameter : filters.getTyreParameters().entrySet()) {
                    if (parameter.getKey().equals("size")) {
                        query = reference.whereArrayContains(parameter.getKey(), parameter.getValue());
                        continue;
                    }
                    query = reference.whereEqualTo(parameter.getKey(), parameter.getValue());
                }
            }

        }
        Log.i(TAG, "toQuery: created query");
        return query;
    }

    public DocumentReference toDoc(String collectionName, String documentName) {
        DocumentReference reference = null;
        if (documentName != null && collectionName != null) {
            reference = firestore.collection(collectionName).document(documentName.trim().toLowerCase().replaceAll("[\\s /]", "_"));
        }
        return reference;
    }
}
