package piyushjohnson.mytyre.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import piyushjohnson.mytyre.R;
import piyushjohnson.mytyre.adapter.PopularTyresAdapter;
import piyushjohnson.mytyre.common.BaseFragment;
import piyushjohnson.mytyre.databinding.FragmentHomeBinding;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.ui.MainViewModel;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = getViewModel(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
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
        Bundle bundle = new Bundle();
        bundle.putString("tyreName", tyre.getName());
        NavHostFragment.findNavController(this).navigate(R.id.action_HomeScreen_to_TyreShow, bundle);
    }
}
