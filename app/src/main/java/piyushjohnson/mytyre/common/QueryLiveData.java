package piyushjohnson.mytyre.common;

import android.util.Log;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import piyushjohnson.mytyre.model.Model;

public class QueryLiveData<T extends Model> extends LiveData<Resource<List<T>>> implements EventListener<QuerySnapshot>, OnCompleteListener<QuerySnapshot>, OnCanceledListener, OnFailureListener {

    private static final String TAG = "QueryLiveData";
    private final Query query;
    private final Class<T> type;
    private final boolean realtime;
    private Source source;
    private ListenerRegistration registration;


    public QueryLiveData(Query query, Class<T> type, boolean realtime) {
        this.query = query;
        this.type = type;
        this.realtime = realtime;
        this.source = Source.CACHE;
        Log.i(TAG, "QueryLiveData: initialised query");
    }

    @Override
    protected void onActive() {
        super.onActive();
//        if (realtime)
//            registration = null;
//            registration = query.addSnapshotListener(this);
//        else
        query.get().addOnCompleteListener(this).addOnCanceledListener(this).addOnFailureListener(this);
        Log.i(TAG, "onActive: added query");
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (registration != null) {
            registration.remove();
            registration = null;
            Log.i(TAG, "onInactive: removed query");
        }
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
        if (e != null) {
            setValue(new Resource<List<T>>(e, source));
            return;
        }
        setValue(new Resource<>(snapshotToList(snapshot), source));
    }

    @NonNull
    private List<T> snapshotToList(QuerySnapshot snapshot) {
        final List<T> list = new ArrayList<>();

        if (snapshot.isEmpty()) {
            return list;
        }

        source = getSource(snapshot);

        for (DocumentSnapshot documentSnapshot : snapshot) {
            list.add(documentSnapshot.toObject(type));
        }
        return list;
    }

    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            Log.i(TAG, "onComplete: successfully fetched");
            setValue(new Resource<>(snapshotToList(Objects.requireNonNull(task.getResult())), source));
        } else {
            Log.i(TAG, "onComplete: failed to fetch");
            setValue(new Resource<List<T>>(new Exception("Task failed"), source));
        }
    }

    @Override
    public void onCanceled() {
        Log.i(TAG, "onCanceled: fetch canceled");
        setValue(new Resource<List<T>>(new Exception("Task cancelled"), source));
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Log.i(TAG, "onFailure: fetch failed");
        setValue(new Resource<List<T>>(e, source));
    }

    public Source getSource(QuerySnapshot snapshot) {
        for (DocumentChange change : snapshot.getDocumentChanges()) {
            if (change.getType() == DocumentChange.Type.ADDED) {
                Log.i(TAG, "New item added");
            }
            if (change.getType() == DocumentChange.Type.REMOVED) {
                Log.i(TAG, "New item removed");
            }

            if (change.getType() == DocumentChange.Type.MODIFIED) {
                Log.i(TAG, "New item modified");
            }
        }

        String source = snapshot.getMetadata().isFromCache() ? "local cache" : "server";
        Log.i(TAG, "Data fetched from " + source);

        return source.equals("local cache") ? Source.CACHE : Source.SERVER;
    }
}
