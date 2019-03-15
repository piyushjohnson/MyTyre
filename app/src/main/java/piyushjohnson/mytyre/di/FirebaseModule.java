package piyushjohnson.mytyre.di;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {

    @Singleton
    @Provides
    FirebaseApp providesFirebase() {
        return FirebaseApp.getInstance();
    }

    @Singleton
    @Provides
    FirebaseFirestore providesFirestore() {
        FirebaseFirestore.setLoggingEnabled(true);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.setFirestoreSettings(settings);
        return firestore;
    }

}
