package piyushjohnson.mytyre.ui.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.chip.Chip;

import piyushjohnson.mytyre.R;
import piyushjohnson.mytyre.adapter.TyreSizesAdapter;
import piyushjohnson.mytyre.common.BaseBottomSheetDialogFragment;
import piyushjohnson.mytyre.common.OnItemActionListener;
import piyushjohnson.mytyre.databinding.DialogFragmentTyreSizesListBinding;
import piyushjohnson.mytyre.model.Param;
import piyushjohnson.mytyre.ui.MainViewModel;
import piyushjohnson.mytyre.util.Filters;

public class TyreSizesListDialogFragment extends BaseBottomSheetDialogFragment implements OnItemActionListener {

    private static final String TAG = "TyreSizesListDialogFrag";

    private DialogFragmentTyreSizesListBinding binding;
    private MainViewModel mainViewModel;
    private Listener mListener;
    private TyreSizesAdapter adapter;
    private LinearLayoutManager layoutManager;

    public static TyreSizesListDialogFragment newInstance(int itemCount) {
        final TyreSizesListDialogFragment fragment = new TyreSizesListDialogFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_tyre_sizes_list, container, false);
        binding.setHandlers(new TyreSizesListDialogBindingHandlers());
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = getViewModel(MainViewModel.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        initTyreSizesList();
        setupViewModel();
    }

    private void setupViewModel() {
        mainViewModel.getFilters().observe(getActivity(), filters -> {
            if (filters.hasVehicleType()) {
                ((Chip) binding.tyreSizesTypeChips.findViewWithTag(filters.getVehicleType().toLowerCase())).setChecked(true);
            }
        });
        mainViewModel.getTyreParams().observe(getActivity(), tyreParamsResource -> {
            if (tyreParamsResource.isSuccessful())
                adapter.setItemsList(tyreParamsResource.data().getSize());
        });
    }

    private void onTyreSizeClicked(Param param) {
        Log.d(TAG, "onTyreSizeClicked() called with: param = [" + param.getValue() + "]");
        dismiss();
        mListener.onTyreSizeSelected(param);
    }

    private void initTyreSizesList() {
        adapter = new TyreSizesAdapter(this::onTyreSizeClicked, this);
        layoutManager = new LinearLayoutManager(getActivity());
        binding.tyreSizesList.setLayoutManager(layoutManager);
        binding.tyreSizesList.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (TyreSizesListDialogFragment.Listener) parent;
        } else {
            mListener = (TyreSizesListDialogFragment.Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void onTyreSizeSelected(Param param);
    }

    public class TyreSizesListDialogBindingHandlers {
        public void onVehicleTypeChanged(View view, boolean isChecked) {
            if (isChecked) {
                Filters filters = new Filters();
                filters.setVehicleType(view.getTag().toString());
                mainViewModel.setFilters(filters);
            }
        }
    }

}
