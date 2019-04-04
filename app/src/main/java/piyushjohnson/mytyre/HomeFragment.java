package piyushjohnson.mytyre;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import piyushjohnson.mytyre.adapter.PopularTyresAdapter;
import piyushjohnson.mytyre.common.BaseFragment;
import piyushjohnson.mytyre.databinding.FragmentHomeBinding;
import piyushjohnson.mytyre.model.Tyre;

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    private MainViewModel mainViewModel;
    private PopularTyresAdapter adapter;
    private GridLayoutManager layoutManager;
    private FragmentHomeBinding binding;

    public HomeFragment() {
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
        mainViewModel = getViewModel(MainViewModel.class);
        Log.i(TAG, "onCreate: MainViewModel" + mainViewModel.hashCode());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        Log.d(TAG, "onActivityCreated() called with: savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void init() {
        initPopularTyresList();
        setupViewModelData();
    }

    private void setupViewModelData() {
        mainViewModel.getPopularTyres().observe(getActivity(), listResource -> {
            mainViewModel.setIsOffline(listResource.isStale());
            if (listResource.isSuccessful()) {
                adapter.setItemsList(listResource.data());
            }
        });
    }

    private void initPopularTyresList() {
        adapter = new PopularTyresAdapter(this::onPopularTyreSelected);
        layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        binding.popularTyresList.setLayoutManager(layoutManager);
        binding.popularTyresList.setAdapter(adapter);
    }

    private void onPopularTyreSelected(Tyre tyre) {

    }
}
