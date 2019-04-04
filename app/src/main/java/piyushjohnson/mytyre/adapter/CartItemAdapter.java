package piyushjohnson.mytyre.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import piyushjohnson.mytyre.R;
import piyushjohnson.mytyre.common.DataListAdapter;
import piyushjohnson.mytyre.common.OnItemActionListener;
import piyushjohnson.mytyre.common.OnItemClickedListener;
import piyushjohnson.mytyre.databinding.FragmentCartItemViewBinding;
import piyushjohnson.mytyre.model.Tyre;

public class CartItemAdapter extends DataListAdapter<Tyre, FragmentCartItemViewBinding, OnItemActionListener> {

    public CartItemAdapter(OnItemClickedListener<Tyre> listener, OnItemActionListener actionListener) {
        super(listener, null);
    }

    @Override
    protected FragmentCartItemViewBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        FragmentCartItemViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart_item_view, parent, false);
        return binding;
    }

    @Override
    protected void bind(FragmentCartItemViewBinding binding, Tyre tyre) {
        binding.setTyre(tyre);
    }
}
