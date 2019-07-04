package piyushjohnson.mytyre.ui.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import piyushjohnson.mytyre.R;
import piyushjohnson.mytyre.adapter.VehicleTypesAdapter;
import piyushjohnson.mytyre.databinding.DialogFragmentVehicleTypesGridBinding;
import piyushjohnson.mytyre.model.VehicleType;

public class VehicleTypesGridDialogFragment extends BottomSheetDialogFragment {

    private static final String TAG = "VehicleTypesGridDialog";

    private Listener mListener;
    private DialogFragmentVehicleTypesGridBinding binding;
    private List<VehicleType> vehicleTypeList;
    private VehicleTypesAdapter adapter;


    public static VehicleTypesGridDialogFragment newInstance() {
        // creating self instance
        return new VehicleTypesGridDialogFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // retrieving and setting instance of parent fragment
        final Fragment parent = getParentFragment();
        // setting instance 'Listener', by checking if context is of parent fragment or activity
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // setting instance of binding by inflating layout of fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_vehicle_types_grid, container, false);
        // return the root view from binding instance of fragment's layout
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onDetach() {
        // setting instance of fragment listener to prevent any leak, before fragments detaches from host fragment/activity
        mListener = null;
        super.onDetach();
    }

    private void init() {
        // generating a static dataset of vehicle type's
        populateDataset();
        // initializing list of vehicle type's onto recycler view
        initVehicleTypesList();
    }

    private void initVehicleTypesList() {
        // setting 'TyresSizesAdapter' to transform list of tyres onto adapter view, also registering event listeners for tyre size selection
        adapter = new VehicleTypesAdapter(this::onVehicleTypeSelected);
        // setting 'GridLayoutManager' to arrange each vehicle type item in vertical list
        // attaching layout manager to current adapter view instance
        binding.vehicleTypesList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        // attaching adapter to current adapter view instance
        binding.vehicleTypesList.setAdapter(adapter);
        // attaching static vehicle type's list
        adapter.setItemsList(vehicleTypeList);
    }

    // implemented to generate vehicle type's static array list
    private void populateDataset() {
        vehicleTypeList = new ArrayList<>();
        vehicleTypeList.add(new VehicleType(R.drawable.ic_car, "car"));
        vehicleTypeList.add(new VehicleType(R.drawable.ic_motorcycle, "motorcycle"));
        vehicleTypeList.add(new VehicleType(R.drawable.ic_lastmile, "lastmile"));
        vehicleTypeList.add(new VehicleType(R.drawable.ic_farm, "farm"));
    }

    // Fires when a vehicle type is selected
    private void onVehicleTypeSelected(VehicleType vehicleType) {
        // dismisses this dialog
        dismiss();
        // recall's listener to fire 'onVehicleTypeSelected' event handler onto 'HomeActivity'
        mListener.onVehicleTypeSelected(vehicleType);
    }

    public interface Listener {
        void onVehicleTypeSelected(VehicleType vehicleType);
    }
}
