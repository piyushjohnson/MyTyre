package piyushjohnson.mytyre;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import piyushjohnson.mytyre.adapter.VehicleTypesAdapter;
import piyushjohnson.mytyre.databinding.DialogFragmentVehicleTypesGridBinding;
import piyushjohnson.mytyre.model.VehicleType;


public class VehicleTypesGridDialogFragment extends BottomSheetDialogFragment {

    private Listener mListener;
    private List<VehicleType> vehicleTypeList;
    private DialogFragmentVehicleTypesGridBinding binding;
    private VehicleTypesAdapter adapter;
    private static final String TAG = "VehicleTypesGridDialog";

    // TODO: Customize parameters
    public static VehicleTypesGridDialogFragment newInstance() {
        final VehicleTypesGridDialogFragment fragment = new VehicleTypesGridDialogFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_vehicle_types_grid, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        initVehicleTypesList();
        populateDataset();
    }

    private void initVehicleTypesList() {
        adapter = new VehicleTypesAdapter(this::onVehicleTypeSelected);
        binding.vehicleTypesList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.vehicleTypesList.setAdapter(adapter);
    }

    private void onVehicleTypeSelected(VehicleType vehicleType) {
        Log.i(TAG, "onVehicleTypeSelected: " + vehicleType.getTitle());
        dismiss();
        mListener.onVehicleTypeSelected(vehicleType);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void onVehicleTypeSelected(VehicleType vehicleType);
    }

    private void populateDataset() {
        vehicleTypeList = new ArrayList<>();
        vehicleTypeList.add(new VehicleType(R.drawable.ic_car, "car"));
        vehicleTypeList.add(new VehicleType(R.drawable.ic_motorcycle, "motorcycle"));
        vehicleTypeList.add(new VehicleType(R.drawable.ic_lastmile, "lastmile"));
        vehicleTypeList.add(new VehicleType(R.drawable.ic_farm, "farm"));
        adapter.setItemsList(vehicleTypeList);
    }


}
