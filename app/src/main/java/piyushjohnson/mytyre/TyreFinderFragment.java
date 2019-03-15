package piyushjohnson.mytyre;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import piyushjohnson.mytyre.adapter.TyresAdapter;
import piyushjohnson.mytyre.common.BaseFragment;
import piyushjohnson.mytyre.databinding.FragmentTyreFinderBinding;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.util.Filters;

public class TyreFinderFragment extends BaseFragment {

    private static final String TAG = "TyreFinderFragment";
    private FragmentTyreFinderBinding binding;
    private MainViewModel mainViewModel;
    private LinearLayoutManager layoutManager;
    private TyresAdapter adapter;
    private Listener mListener;

    public TyreFinderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        getParentFragment().getView().setLayoutParams(layoutParams);
        mainViewModel = getViewModel(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tyre_finder, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        initTyresList();
        setupViewModelData();
    }

    private void setupViewModelData() {
        Filters filters = new Filters();
        filters.setVehicleType(getArguments().getString("vehicleType"));
        mainViewModel.getTyres(filters).observe(getActivity(), listResource -> {
            mainViewModel.setIsOffline(listResource.isStale());
            if (listResource.isSuccessful())
                adapter.setItemsList(listResource.data());
        });
    }

    private void initTyresList() {
        adapter = new TyresAdapter(this::onTyreSelected);
        layoutManager = new LinearLayoutManager(getContext());
        binding.tyresList.setLayoutManager(layoutManager);
        binding.tyresList.setAdapter(adapter);
    }

    private void onTyreSelected(Tyre tyre) {
        Log.i(TAG, "onTyreSelected: " + tyre.getName());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (Listener) context;
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void onItemClicked(int position, View view);
    }
}
