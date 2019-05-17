package piyushjohnson.mytyre.common;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Source;

import javax.annotation.Nullable;

import piyushjohnson.mytyre.model.Model;

public class DocumentLiveData<T extends Model> extends LiveData<Resource<T>> implements EventListener<DocumentSnapshot> {

    private final Class<T> type;
    private final DocumentReference reference;
    private Source source;
    private ListenerRegistration registration;

    public DocumentLiveData(Class<T> type, DocumentReference reference) {
        this.type = type;
        this.reference = reference;
        this.source = Source.DEFAULT;
    }

    @Override
    protected void onActive() {
        super.onActive();
        registration = reference.addSnapshotListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (registration == null) {
            registration.remove();
            registration = null;
        }

    }


    @Override
    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
        getSource(snapshot);
        if (e != null) {
            setValue(new Resource<T>(e, source));
        }
        setValue(new Resource<>(snapshot.toObject(type), source));
    }

    public void getSource(@NonNull DocumentSnapshot snapshot) {
        source = snapshot.getMetadata().isFromCache() ? Source.CACHE : Source.SERVER;
    }
}
