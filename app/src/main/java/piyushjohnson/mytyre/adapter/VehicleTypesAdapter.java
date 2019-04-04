package piyushjohnson.mytyre.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import piyushjohnson.mytyre.R;
import piyushjohnson.mytyre.common.DataListAdapter;
import piyushjohnson.mytyre.common.OnItemActionListener;
import piyushjohnson.mytyre.common.OnItemClickedListener;
import piyushjohnson.mytyre.databinding.DialogFragmentVehicleTypesGridItemBinding;
import piyushjohnson.mytyre.model.VehicleType;

public class VehicleTypesAdapter extends DataListAdapter<VehicleType, DialogFragmentVehicleTypesGridItemBinding, OnItemActionListener> {


    public VehicleTypesAdapter(OnItemClickedListener<VehicleType> listener) {
        super(listener, null);
    }

    @Override
    protected DialogFragmentVehicleTypesGridItemBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        DialogFragmentVehicleTypesGridItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_vehicle_types_grid_item, parent, false);
        return binding;
    }

    @Override
    protected void bind(DialogFragmentVehicleTypesGridItemBinding binding, VehicleType vehicleType) {
        binding.setVehicleType(vehicleType);
        binding.vehicleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(vehicleType);
            }
        });
    }
}
