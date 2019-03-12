package piyushjohnson.mytyre.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import piyushjohnson.mytyre.HomeActivity;

@Module
public abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract HomeActivity contributesMainActivity();
}
