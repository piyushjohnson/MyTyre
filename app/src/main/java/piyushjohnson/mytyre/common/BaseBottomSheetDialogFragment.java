package piyushjohnson.mytyre.common;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

public class BaseBottomSheetDialogFragment extends DaggerBottomSheetDialogFragment {
    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    protected <T extends ViewModel> T getViewModel(final Class<T> tClass) {
        return ViewModelProviders.of(getActivity(), viewModelFactory).get(tClass);
    }
}
