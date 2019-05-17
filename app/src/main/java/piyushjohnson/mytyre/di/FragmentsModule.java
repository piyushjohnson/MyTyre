package piyushjohnson.mytyre.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import piyushjohnson.mytyre.TyreFinderFragment;
import piyushjohnson.mytyre.TyreShowFragment;
import piyushjohnson.mytyre.ui.cart.CartFragment;
import piyushjohnson.mytyre.ui.dialogs.TyreSizesListDialogFragment;
import piyushjohnson.mytyre.ui.home.HomeFragment;

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

    @ContributesAndroidInjector
    abstract TyreSizesListDialogFragment contributesTyreSizesListDialogFragment();
}
