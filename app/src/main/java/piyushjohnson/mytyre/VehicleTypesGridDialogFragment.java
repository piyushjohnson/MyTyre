package piyushjohnson.mytyre;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class VehicleTypesGridDialogFragment extends BottomSheetDialogFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_COUNT = "item_count";
    private Listener mListener;
    private List<Dataset> datasets;

    // TODO: Customize parameters
    public static VehicleTypesGridDialogFragment newInstance(int itemCount) {
        final VehicleTypesGridDialogFragment fragment = new VehicleTypesGridDialogFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list_dialog, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateDataset();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final View rootView = (View) view;
        final RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if (getArguments() != null) {
            recyclerView.setAdapter(new ItemAdapter(getArguments().getInt(ARG_ITEM_COUNT)));
        }
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
        void onItemClicked(int position);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        final ImageButton vehicleIcon;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            super(inflater.inflate(R.layout.fragment_item_list_dialog_item, parent, false));
            vehicleIcon = (ImageButton) itemView.findViewById(R.id.vehicle_icon);
            vehicleIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClicked(getAdapterPosition());
                        dismiss();
                    }
                }
            });
        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final int mItemCount;

        ItemAdapter(int itemCount) {
            mItemCount = itemCount;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.vehicleIcon.setImageResource(datasets.get(position).icon);
        }

        @Override
        public int getItemCount() {
            return mItemCount;
        }

    }

    private class Dataset {
        private int icon;
        private String title;

        public Dataset(int icon, String title) {
            this.icon = icon;
            this.title = title;
        }
    }

    private void populateDataset() {
        datasets = new ArrayList<>();
        datasets.add(new Dataset(R.drawable.ic_car,"Car"));
        datasets.add(new Dataset(R.drawable.ic_motorcycle,"Motorcycle"));
        datasets.add(new Dataset(R.drawable.ic_truck,"Truck"));
        datasets.add(new Dataset(R.drawable.ic_tractor,"Tractor"));
    }

}
