package piyushjohnson.mytyre.ui.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

    // TODO: Customize parameters
    public static VehicleTypesGridDialogFragment newInstance() {
        final VehicleTypesGridDialogFragment fragment = new VehicleTypesGridDialogFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_vehicle_types_grid, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated() called with: savedInstanceState = [" + savedInstanceState + "]");
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() called");
    }

    @Override
    public void onDetach() {
        mListener = null;
        Log.d(TAG, "onDetach() called");
        super.onDetach();
    }

    private void init() {
        populateDataset();
        initVehicleTypesList();
    }

    private void initVehicleTypesList() {
        adapter = new VehicleTypesAdapter(this::onVehicleTypeSelected);
        adapter.setItemsList(vehicleTypeList);
        binding.vehicleTypesList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.vehicleTypesList.setAdapter(adapter);
    }

    private void populateDataset() {
        vehicleTypeList = new ArrayList<>();
        vehicleTypeList.add(new VehicleType(R.drawable.ic_car, "car"));
        vehicleTypeList.add(new VehicleType(R.drawable.ic_motorcycle, "motorcycle"));
        vehicleTypeList.add(new VehicleType(R.drawable.ic_lastmile, "lastmile"));
        vehicleTypeList.add(new VehicleType(R.drawable.ic_farm, "farm"));
    }

    private void onVehicleTypeSelected(VehicleType vehicleType) {
        Log.i(TAG, "onVehicleTypeSelected: " + vehicleType.getTitle());
        dismiss();
        mListener.onVehicleTypeSelected(vehicleType);
    }

    public interface Listener {
        void onVehicleTypeSelected(VehicleType vehicleType);
    }
}
