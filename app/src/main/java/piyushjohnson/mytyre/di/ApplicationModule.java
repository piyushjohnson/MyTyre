package piyushjohnson.mytyre.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjectionModule;
import piyushjohnson.mytyre.MyTyreApplication;

@Module(includes = AndroidInjectionModule.class)
public abstract class ApplicationModule {

    @Binds
    @Singleton
    abstract Application providesApplication(MyTyreApplication application);
}
