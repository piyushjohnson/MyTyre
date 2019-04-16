package piyushjohnson.mytyre;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        Log.i(TAG, "onCreate: " + getArguments().getString("tyreName"));
        mainViewModel = getViewModel(MainViewModel.class);
        Log.i(TAG, "onCreate: MainViewModel " + mainViewModel);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tyre_show, container, false);
        binding.setTyre(new Tyre());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        Log.d(TAG, "onActivityCreated() called with: savedInstanceState = [" + savedInstanceState + "]");
    }

    private void init() {
        setupViewModel();
    }

    private void setupViewModel() {
        Log.d(TAG, "setupViewModel() called");
        mainViewModel.getTyre(getArguments().getString("tyreName")).observe(getParentFragment(), tyreResource -> {
            if (tyreResource.isSuccessful())
                binding.setTyre(tyreResource.data());
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
