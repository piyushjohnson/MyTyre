package piyushjohnson.mytyre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import piyushjohnson.mytyre.common.BaseFragment;
import piyushjohnson.mytyre.databinding.FragmentTyreShowBinding;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.ui.MainViewModel;

public class TyreShowFragment extends BaseFragment {

    private static final String TAG = "TyreShowFragment";

    private FragmentTyreShowBinding binding;
    private MainViewModel mainViewModel;

    public TyreShowFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = getViewModel(MainViewModel.class);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tyre_show, container, false);
        binding.setTyre(new Tyre());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        setupViewModel();
    }

    private void setupViewModel() {
        if (getArguments() != null) {
            if (getParentFragment() != null) {
                mainViewModel.getTyre(getArguments().getString("tyreName")).observe(getParentFragment(), tyreResource -> {
                    if (tyreResource.isSuccessful())
                        binding.setTyre(tyreResource.data());
                });
            }
        }
    }
}
