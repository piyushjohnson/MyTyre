package piyushjohnson.mytyre.common;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;

public class BaseFragment extends DaggerFragment {

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    protected <T extends ViewModel> T getViewModel(final Class<T> tClass) {
        return ViewModelProviders.of(this, viewModelFactory).get(tClass);
    }
}
