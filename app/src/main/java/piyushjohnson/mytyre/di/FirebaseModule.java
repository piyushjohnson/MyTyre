package piyushjohnson.mytyre.di;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {

    FirebaseFirestore firestore;

    FirebaseModule() {
        FirebaseFirestore.setLoggingEnabled(true);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        this.firestore = FirebaseFirestore.getInstance();
        firestore.setFirestoreSettings(settings);
    }

    @Singleton
    @Provides
    FirebaseApp providesFirebase() {
        return FirebaseApp.getInstance();
    }

    @Singleton
    @Provides
    FirebaseFirestore providesFirestore() {
        return firestore;
    }

}
