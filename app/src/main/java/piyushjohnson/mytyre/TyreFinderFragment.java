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

public class TyreFinderFragment extends BaseFragment implements OnTyreItemActionListener {

    private static final String TAG = "TyreFinderFragment";

    private FragmentTyreFinderBinding binding;
    private MainViewModel mainViewModel;
    private CartViewModel cartViewModel;
    private LinearLayoutManager layoutManager;
    private TyresAdapter adapter;
    private Listener mListener;

    public TyreFinderFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (Listener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = getViewModel(MainViewModel.class);
        cartViewModel = getViewModel(CartViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tyre_finder, container, false);
        binding.setHandlers(new TyreFinderBindingHandlers());
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

    @Override
    public void onResume() {
        super.onResume();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        getParentFragment().getView().setLayoutParams(layoutParams);
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
        mainViewModel.getTyres().observe(getActivity(), listResource -> {
            mainViewModel.setIsOffline(listResource.isStale());
            if (listResource.isSuccessful())
                adapter.setItemsList(listResource.data());
        });
    }

    private void onTyreSelected(Tyre tyre) {
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
        public void onFilterSelected(View view, boolean isChecked) {
            Log.d(TAG, "onFilterSelected() called with: view = [" + view + "], id = [" + isChecked + "]");
        }
    }
}
