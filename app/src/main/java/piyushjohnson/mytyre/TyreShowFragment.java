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

/*
 * @extends BaseFragment
 * @has-a FragmentTyreShowBinding, MainViewModel
 * */
public class TyreShowFragment extends BaseFragment {

    private static final String TAG = "TyreShowFragment";

    private FragmentTyreShowBinding binding;
    private MainViewModel mainViewModel;

    public TyreShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting instances of view model's to access state
        mainViewModel = getViewModel(MainViewModel.class);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // setting instance of binding by inflating layout of fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tyre_show, container, false);
        // setting binding data for view's of layout, to prevent null for first load
        binding.setTyre(new Tyre());
        // return the root view from binding instance of fragment's layout
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        // start observing live data to, retain latest snapshot of tyre to display
        setupViewModel();
    }

    private void setupViewModel() {
        // listening to latest snapshot of all fetched tyre list from 'MainViewModel' by using 'tyreName' argument supplied at fragment instantiation
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
