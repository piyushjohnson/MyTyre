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

import java.util.Objects;

import piyushjohnson.mytyre.R;
import piyushjohnson.mytyre.adapter.PopularTyresAdapter;
import piyushjohnson.mytyre.common.BaseFragment;
import piyushjohnson.mytyre.databinding.FragmentHomeBinding;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.ui.MainViewModel;

/*
 * @extends BaseFragment
 * @has-a FragmentHomeBinding, MainViewModel, PopularTyresAdapter, GridLayoutManager
 * */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private MainViewModel mainViewModel;
    private GridLayoutManager layoutManager;
    private PopularTyresAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = getViewModel(MainViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // setting instance of binding by inflating layout of fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        // return the root view from binding instance of fragment's layout
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        // initializing list of popular tyre's onto recycler view
        initPopularTyresList();
        // start observing live data to, retain latest snapshot of tyre's to display
        setupViewModelData();
    }


    private void setupViewModelData() {
        // listening to latest snapshot of all fetched popular tyre's list from 'MainViewModel'
        // also updates 'isOffline' live data with fetched resource 'isStale' method, which checks if it's from cache or network
        //TODO:  show's progress bar to mark loading state of popular tyre's list, after list is attached to adapter, makes it hidden
        mainViewModel.getPopularTyres().observe(Objects.requireNonNull(getActivity()), listResource -> {
            mainViewModel.setIsOffline(listResource.isStale());
            if (listResource.isSuccessful()) {
                adapter.setItemsList(listResource.data());
            }
        });
    }

    private void initPopularTyresList() {
        // setting 'PopularTyresAdapter' to transform list of tyres onto adapter view, also registering event listeners for popular tyre selection
        adapter = new PopularTyresAdapter(this::onPopularTyreSelected);
        // setting 'GridLayoutManager' to arrange each tyre item in grid list
        layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        // attaching layout manager to current adapter view instance
        binding.popularTyresList.setLayoutManager(layoutManager);
        // attaching adapter to current adapter view instance
        binding.popularTyresList.setAdapter(adapter);
    }

    private void onPopularTyreSelected(Tyre tyre) {
        // setting bundle with selected tyre name
        Bundle bundle = new Bundle();
        bundle.putString("tyreName", tyre.getName());
        // navigates from 'HomeScreen' to 'TyreShow' destination, with attached bundle
        NavHostFragment.findNavController(this).navigate(R.id.action_HomeScreen_to_TyreShow, bundle);
    }
}
