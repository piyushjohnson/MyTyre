package piyushjohnson.mytyre.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import piyushjohnson.mytyre.R;
import piyushjohnson.mytyre.adapter.CartItemAdapter;
import piyushjohnson.mytyre.common.BaseFragment;
import piyushjohnson.mytyre.common.OnCartTyreItemActionListener;
import piyushjohnson.mytyre.databinding.FragmentCartBinding;
import piyushjohnson.mytyre.model.Tyre;

/*
 * @extends BaseFragment
 * @implements OnCartTyreItemActionListener
 * @has-a FragmentCartBinding, CartViewModel, LinearLayoutManager, CartItemAdapter
 * */
public class CartFragment extends BaseFragment implements OnCartTyreItemActionListener {

    private static final String TAG = "CartFragment";

    private FragmentCartBinding binding;
    private CartViewModel cartViewModel;
    private CartItemAdapter adapter;
    private LinearLayoutManager layoutManager;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting instances of view model's to access state
        cartViewModel = getViewModel(CartViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // setting instance of binding by inflating layout of fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        // return the root view from binding instance of fragment's layout
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        // initializing list of cart tyre's onto recycler view
        initCartList();
        // start observing live data to, retain latest snapshot of tyre's to display
        setupViewModelData();
    }

    private void initCartList() {
        // setting 'CartItemAdapter' to transform list of cart tyres onto adapter view,
        // also registering event listeners for tyre selection
        adapter = new CartItemAdapter(this::onCartItemSelected, this);
        // setting 'LinearLayoutManager' to arrange each tyre item in vertical list
        layoutManager = new LinearLayoutManager(getContext());
        // attaching layout manager to current adapter view instance
        binding.cartTyresList.setLayoutManager(layoutManager);
        // attaching adapter to current adapter view instance
        binding.cartTyresList.setAdapter(adapter);
    }

    // Fires when any cart tyre item is selected
    private void onCartItemSelected(Tyre tyre) {
        //TODO: to be implemented, when any cart tyre item is selected,then either show the tyre or other options
    }

    @Override
    public void onCartTyreQuantityUp(Tyre tyre) {
        // to update selected cart item count, incrementing quantity of single cart item
        cartViewModel.setCartItemCount(tyre, CartViewModel.CartAction.QUANTITY_UP);
    }

    @Override
    public void onCartTyreQuantityDown(Tyre tyre) {
        // to update selected cart item count, decrementing quantity of single cart item
        cartViewModel.setCartItemCount(tyre, CartViewModel.CartAction.QUANTITY_DOWN);
    }

    private void setupViewModelData() {
        // listening to latest snapshot of all fetched cart tyre's list from 'MainViewModel'
        // show 'cartNoItemText' only if no cart item's are in list i.e 0
        // or else attaches tyres list to adapter
        cartViewModel.getCartItems().observe(Objects.requireNonNull(getActivity()), tyres -> {
            if (tyres.size() > 0)
                adapter.setItemsList(tyres);
            else
                binding.cartNoItemText.setVisibility(View.VISIBLE);
        });
    }
}
