package piyushjohnson.mytyre.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import piyushjohnson.mytyre.R;
import piyushjohnson.mytyre.common.DataListAdapter;
import piyushjohnson.mytyre.common.OnItemActionListener;
import piyushjohnson.mytyre.common.OnItemClickedListener;
import piyushjohnson.mytyre.databinding.DialogFragmentTyreSizesListItemBinding;
import piyushjohnson.mytyre.model.Param;

public class TyreSizesAdapter extends DataListAdapter<Param, DialogFragmentTyreSizesListItemBinding, OnItemActionListener> {

    public TyreSizesAdapter(OnItemClickedListener<Param> listener, OnItemActionListener actionListener) {
        super(listener, actionListener);
    }

    @Override
    protected DialogFragmentTyreSizesListItemBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_tyre_sizes_list_item, parent, false);
    }

    @Override
    protected void bind(DialogFragmentTyreSizesListItemBinding binding, Param param) {
        binding.getRoot().setOnClickListener(v -> listener.onItemClicked(param));
        binding.setParam(param);
    }

}
