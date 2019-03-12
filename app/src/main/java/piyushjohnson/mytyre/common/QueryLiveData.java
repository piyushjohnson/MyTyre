package piyushjohnson.mytyre.common;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import piyushjohnson.mytyre.model.Model;

public class QueryLiveData<T extends Model> extends LiveData<Resource<List<T>>> implements EventListener<QuerySnapshot>, OnCompleteListener<QuerySnapshot>, OnCanceledListener, OnFailureListener {

    private final Query query;
    private final Class<T> type;
    private final boolean realtime;
    private ListenerRegistration registration;


    public QueryLiveData(Query query, Class<T> type, boolean realtime) {
        this.query = query;
        this.type = type;
        this.realtime = realtime;
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (realtime)
            registration = query.addSnapshotListener(this);
        else
            query.get().addOnCompleteListener(this).addOnCanceledListener(this).addOnFailureListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (registration != null) {
            registration.remove();
            registration = null;
        }
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
        if (e != null) {
            setValue(new Resource<List<T>>(e));
            return;
        }
        setValue(new Resource<>(snapshotToList(snapshot)));
    }

    @NonNull
    private List<T> snapshotToList(QuerySnapshot snapshot) {
        final List<T> list = new ArrayList<>();

        if (snapshot.isEmpty()) {
            return list;
        }

        for (DocumentSnapshot documentSnapshot : snapshot) {
            list.add(documentSnapshot.toObject(type));
        }
        return list;
    }

    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            setValue(new Resource<>(snapshotToList(Objects.requireNonNull(task.getResult()))));
        } else {
            setValue(new Resource<List<T>>(new Exception("Task failed")));
        }
    }

    @Override
    public void onCanceled() {

    }

    @Override
    public void onFailure(@NonNull Exception e) {
        setValue(new Resource<List<T>>(e));
    }
}
