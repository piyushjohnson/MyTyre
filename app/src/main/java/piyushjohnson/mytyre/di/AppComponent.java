package piyushjohnson.mytyre.di;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@SuppressWarnings("unchecked")
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivitiesModule.class,
        FragmentsModule.class,
        ViewModelModule.class,
        FirebaseModule.class
})
interface AppComponent extends AndroidInjector<DaggerApplication> {

}
