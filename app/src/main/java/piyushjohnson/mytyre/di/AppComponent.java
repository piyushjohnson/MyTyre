package piyushjohnson.mytyre.di;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@SuppressWarnings("unchecked")
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivitiesModule.class,
        ViewModelModule.class,
        FirebaseModule.class
})
interface AppComponent extends AndroidInjector<DaggerApplication> {

}
