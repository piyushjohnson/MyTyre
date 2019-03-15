package piyushjohnson.mytyre;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import piyushjohnson.mytyre.di.DaggerAppComponent;

public class MyTyreApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.create();
    }
}
