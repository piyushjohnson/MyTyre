package piyushjohnson.mytyre.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import piyushjohnson.mytyre.CartFragment;
import piyushjohnson.mytyre.HomeFragment;
import piyushjohnson.mytyre.TyreFinderFragment;
import piyushjohnson.mytyre.TyreShowFragment;

@Module
public abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract HomeFragment contributesHomeFragment();

    @ContributesAndroidInjector
    abstract CartFragment contributesCartFragment();

    @ContributesAndroidInjector
    abstract TyreFinderFragment contributesTyreFinderFragment();

    @ContributesAndroidInjector
    abstract TyreShowFragment contributesTyreShowFragment();
}
