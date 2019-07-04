package piyushjohnson.mytyre.ui.dialogs;

import android.content.Context;
import android.os.Bundle;
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
        // creating self instance
        final TyreSizesListDialogFragment fragment = new TyreSizesListDialogFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // retrieving and setting instance of parent fragment
        final Fragment parent = getParentFragment();
        // setting instance 'Listener', by checking if context is of parent fragment or activity
        if (parent != null) {
            mListener = (TyreSizesListDialogFragment.Listener) parent;
        } else {
            mListener = (TyreSizesListDialogFragment.Listener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // setting instance of binding by inflating layout of fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_tyre_sizes_list, container, false);
        // setting binding event handler for view's of layout
        binding.setHandlers(new TyreSizesListDialogBindingHandlers());
        // return the root view from binding instance of fragment's layout
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting instances of view model's to access state
        mainViewModel = getViewModel(MainViewModel.class);
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
        // initializing list of tyre's onto recycler view
        initTyreSizesList();
        // start observing live data to, retain latest snapshot of tyre's to display
        setupViewModel();
    }

    private void setupViewModel() {
        // listening to latest snapshot of all 'filters' from 'MainViewModel'
        // toggle b/w vehicle type chips
        mainViewModel.getFilters().observe(getActivity(), filters -> {
            if (filters.hasVehicleType()) {
                ((Chip) binding.tyreSizesTypeChips.findViewWithTag(filters.getVehicleType().toLowerCase())).setChecked(true);
            }
        });
        // listening to latest snapshot of all 'tyreParams' from 'MainViewModel'
        // attaches only 'size' parameter into list
        mainViewModel.getTyreParams().observe(getActivity(), tyreParamsResource -> {
            if (tyreParamsResource.isSuccessful())
                adapter.setItemsList(tyreParamsResource.data().getSize());
        });
    }

    // Fires when tyre size parameter is selected from list
    private void onTyreSizeClicked(Param param) {
        // dismisses this dialog
        dismiss();
        // recall's listener to fire 'onTyreSizeSelected' event handler onto 'HomeActivity'
        mListener.onTyreSizeSelected(param);
    }

    private void initTyreSizesList() {
        // setting 'TyresSizesAdapter' to transform list of tyres onto adapter view, also registering event listeners for tyre size selection
        adapter = new TyreSizesAdapter(this::onTyreSizeClicked, this);
        // setting 'LinearLayoutManager' to arrange each tyre size item in vertical list
        layoutManager = new LinearLayoutManager(getActivity());
        // attaching layout manager to current adapter view instance
        binding.tyreSizesList.setLayoutManager(layoutManager);
        // attaching adapter to current adapter view instance
        binding.tyreSizesList.setAdapter(adapter);
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
