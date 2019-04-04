package piyushjohnson.mytyre.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import piyushjohnson.mytyre.R;
import piyushjohnson.mytyre.common.DataListAdapter;
import piyushjohnson.mytyre.common.OnItemClickedListener;
import piyushjohnson.mytyre.common.OnTyreItemActionListener;
import piyushjohnson.mytyre.databinding.FragmentTyreFinderItemViewBinding;
import piyushjohnson.mytyre.model.Tyre;

public class TyresAdapter extends DataListAdapter<Tyre, FragmentTyreFinderItemViewBinding, OnTyreItemActionListener> {

    public TyresAdapter(OnItemClickedListener<Tyre> listener, OnTyreItemActionListener actionListener) {
        super(listener, actionListener);
    }

    @Override
    protected FragmentTyreFinderItemViewBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        FragmentTyreFinderItemViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tyre_finder_item_view, parent, false);
        return binding;
    }

    @Override
    protected void bind(FragmentTyreFinderItemViewBinding binding, Tyre tyre) {
        binding.getRoot().setOnClickListener(v -> listener.onItemClicked(tyre));
        binding.cartBtn.setOnClickListener(v -> actionListener.onTyreAddToCart(tyre));
        binding.buyBtn.setOnClickListener(v -> actionListener.onTyreBuyNow(tyre));
        binding.setTyre(tyre);
    }
}
