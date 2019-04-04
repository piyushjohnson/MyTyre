package piyushjohnson.mytyre.common;

import android.util.Log;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import piyushjohnson.mytyre.model.Model;

public class QueryLiveData<T extends Model> extends LiveData<Resource<List<T>>> implements OnCompleteListener<QuerySnapshot>, OnCanceledListener, OnFailureListener {

    private static final String TAG = "QueryLiveData";
    private final Class<T> type;
    private final boolean realtime;
    private Source source;
    private Query query;
    private ListenerRegistration registration;


    public QueryLiveData(Query query, Class<T> type, boolean realtime) {
        this.query = query;
        this.type = type;
        this.realtime = realtime;
        this.source = Source.DEFAULT;
        query.get(this.source).addOnCompleteListener(this).addOnCanceledListener(this).addOnFailureListener(this);
        Log.d(TAG, "QueryLiveData() called with: query = [" + query + "], type = [" + type + "], realtime = [" + realtime + "]");
        Log.i(TAG, "QueryLiveData: initialised query " + this.hashCode());
    }

    @Override
    protected void onActive() {
        super.onActive();
//        if (realtime)
//            registration = null;
//            registration = query.addSnapshotListener(this);
//        else
        Log.d(TAG, "onActive() called");
        if (source == Source.SERVER) {
            query.get(Source.CACHE).addOnCompleteListener(this).addOnCanceledListener(this).addOnFailureListener(this);
        }
        Log.i(TAG, "onActive: added query");
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Log.d(TAG, "onInactive() called");
        Log.i(TAG, "onInactive: removed query");
    /*    if (registration != null) {
            registration.remove();
            registration = null;
            Log.i(TAG, "onInactive: removed query");
        }*/
    }

    /*@Override
    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
        if (e != null) {
            setValue(new Resource<List<T>>(e, source));
            return;
        }
        setValue(new Resource<>(snapshotToList(snapshot), source));
    }*/

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
            setValue(new Resource<>(new Exception("Task failed"), source));
        }
        Log.d(TAG, "onComplete() called with: task = [" + task.isSuccessful() + "]");
    }

    @Override
    public void onCanceled() {
        Log.i(TAG, "onCanceled: fetch canceled");
        setValue(new Resource<>(new Exception("Task cancelled"), source));
        Log.d(TAG, "onCanceled() called");
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Log.i(TAG, "onFailure: fetch failed");
        setValue(new Resource<>(e, source));
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
