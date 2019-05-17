package piyushjohnson.mytyre.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import piyushjohnson.mytyre.R;
import piyushjohnson.mytyre.common.DataListAdapter;
import piyushjohnson.mytyre.common.OnCartTyreItemActionListener;
import piyushjohnson.mytyre.common.OnItemClickedListener;
import piyushjohnson.mytyre.databinding.FragmentCartItemViewBinding;
import piyushjohnson.mytyre.model.Tyre;

public class CartItemAdapter extends DataListAdapter<Tyre, FragmentCartItemViewBinding, OnCartTyreItemActionListener> {

    private static final String TAG = "CartItemAdapter";

    public CartItemAdapter(OnItemClickedListener<Tyre> listener, OnCartTyreItemActionListener actionListener) {
        super(listener, actionListener);
    }

    @Override
    protected FragmentCartItemViewBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        FragmentCartItemViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart_item_view, parent, false);
        return binding;
    }

    @Override
    protected void bind(FragmentCartItemViewBinding binding, Tyre tyre) {
        binding.setTyre(tyre);
        binding.cartTyreQuantityDown.setOnClickListener(v -> {
            Log.i(TAG, "quantity down" + tyre.getName());
            actionListener.onCartTyreQuantityDown(tyre);

        });
        binding.cartTyreQuantityUp.setOnClickListener(v -> {
            Log.i(TAG, "quantity up" + tyre.getName());
            actionListener.onCartTyreQuantityUp(tyre);
        });
    }
}
