package piyushjohnson.mytyre.ui.cart;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import piyushjohnson.mytyre.R;
import piyushjohnson.mytyre.adapter.CartItemAdapter;
import piyushjohnson.mytyre.common.BaseFragment;
import piyushjohnson.mytyre.common.OnCartTyreItemActionListener;
import piyushjohnson.mytyre.databinding.FragmentCartBinding;
import piyushjohnson.mytyre.model.Tyre;

public class CartFragment extends BaseFragment implements OnCartTyreItemActionListener {

    private static final String TAG = "CartFragment";

    private FragmentCartBinding binding;
    private CartViewModel cartViewModel;
    private CartItemAdapter adapter;
    private LinearLayoutManager layoutManager;

    public CartFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartViewModel = getViewModel(CartViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        initCartList();
        setupViewModelData();
    }

    private void initCartList() {
        adapter = new CartItemAdapter(this::onCartItemSelected, this);
        layoutManager = new LinearLayoutManager(getContext());
        binding.cartTyresList.setLayoutManager(layoutManager);
        binding.cartTyresList.setAdapter(adapter);
    }

    private void onCartItemSelected(Tyre tyre) {
    }

    @Override
    public void onCartTyreQuantityUp(Tyre tyre) {
        cartViewModel.setCartItemCount(tyre, CartViewModel.CartAction.QUANTITY_UP);
    }

    @Override
    public void onCartTyreQuantityDown(Tyre tyre) {
        cartViewModel.setCartItemCount(tyre, CartViewModel.CartAction.QUANTITY_DOWN);
    }

    private void setupViewModelData() {
        cartViewModel.getCartItems().observe(getActivity(), tyres -> {
            if (tyres.size() > 0)
                adapter.setItemsList(tyres);
            else
                binding.cartNoItemText.setVisibility(View.VISIBLE);
        });
    }
}
