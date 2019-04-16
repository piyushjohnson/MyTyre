package piyushjohnson.mytyre.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import piyushjohnson.mytyre.ui.home.HomeActivity;

@Module
public abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract HomeActivity contributesMainActivity();
}
