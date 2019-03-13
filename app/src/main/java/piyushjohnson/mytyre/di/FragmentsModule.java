package piyushjohnson.mytyre.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import piyushjohnson.mytyre.HomeFragment;
import piyushjohnson.mytyre.TyreFinderFragment;

@Module
public abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract HomeFragment contributesHomeFragment();

    @ContributesAndroidInjector
    abstract TyreFinderFragment contributesTyreFinderFragment();
}
