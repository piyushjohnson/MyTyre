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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import piyushjohnson.mytyre.adapter.TyresAdapter;
import piyushjohnson.mytyre.common.BaseFragment;
import piyushjohnson.mytyre.common.OnTyreItemActionListener;
import piyushjohnson.mytyre.databinding.FragmentTyreFinderBinding;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.ui.MainViewModel;
import piyushjohnson.mytyre.ui.cart.CartViewModel;
import piyushjohnson.mytyre.util.Filters;

public class TyreFinderFragment extends BaseFragment implements OnTyreItemActionListener {

    private static final String TAG = "TyreFinderFragment";

    private FragmentTyreFinderBinding binding;
    private MainViewModel mainViewModel;
    private CartViewModel cartViewModel;
    private LinearLayoutManager layoutManager;
    private TyresAdapter adapter;
    private Listener mListener;

    public TyreFinderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
        mListener = (Listener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        mainViewModel = getViewModel(MainViewModel.class);
        cartViewModel = getViewModel(CartViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tyre_finder, container, false);
        binding.setHandlers(new TyreFinderBindingHandlers());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated() called with: savedInstanceState = [" + savedInstanceState + "]");
        init();
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
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        getParentFragment().getView().setLayoutParams(layoutParams);
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

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    private void init() {
        initTyresList();
        setupViewModelData();
    }

    private void initTyresList() {
        adapter = new TyresAdapter(this::onTyreSelected, this);
        layoutManager = new LinearLayoutManager(getContext());
        binding.tyresList.setLayoutManager(layoutManager);
        binding.tyresList.setAdapter(adapter);
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

    private void onTyreSelected(Tyre tyre) {
        Log.i(TAG, "onTyreSelected: " + tyre.getName());
        Bundle bundle = new Bundle();
        bundle.putString("tyreName", tyre.getName());
        NavHostFragment.findNavController(this).navigate(R.id.action_TyreFinder_to_TyreShow, bundle);
        mListener.onTyreSelected(tyre);
    }

    @Override
    public void onTyreAddToCart(Tyre tyre) {
        Log.d(TAG, "onTyreAddToCart() called with: tyre = [" + tyre.getName() + "]");
        cartViewModel.addCartItem(tyre);
    }

    @Override
    public void onTyreBuyNow(Tyre tyre) {
        Log.d(TAG, "onTyreBuyNow() called with: tyre = [" + tyre.getName() + "]");
    }

    public interface Listener {
        void onTyreSelected(Tyre tyre);
    }

    public class TyreFinderBindingHandlers {
        public void onFilterSelected(View view) {
            Log.d(TAG, "onFilterSelected() called with: filter = [" + view.getTag() + "]");
        }
    }
}
