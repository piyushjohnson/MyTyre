package piyushjohnson.mytyre.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import piyushjohnson.mytyre.R;
import piyushjohnson.mytyre.common.DataListAdapter;
import piyushjohnson.mytyre.common.OnItemActionListener;
import piyushjohnson.mytyre.common.OnItemClickedListener;
import piyushjohnson.mytyre.databinding.FragmentTyreShowBinding;
import piyushjohnson.mytyre.model.Tyre;

public class TyreSizeChipAdapter extends DataListAdapter<Tyre, FragmentTyreShowBinding, OnItemActionListener> {

    public TyreSizeChipAdapter(OnItemClickedListener<Tyre> listener, OnItemActionListener actionListener) {
        super(listener, actionListener);
    }

    @Override
    protected FragmentTyreShowBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return DataBindingUtil.inflate(inflater,R.layout.fragment_tyre_show_chip_item_view,parent,false);
    }

    @Override
    protected void bind(FragmentTyreShowBinding binding, Tyre tyre) {
        binding.setTyre(tyre);
    }
}
