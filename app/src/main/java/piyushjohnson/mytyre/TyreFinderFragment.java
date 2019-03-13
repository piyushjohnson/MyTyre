package piyushjohnson.mytyre;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import piyushjohnson.mytyre.common.BaseFragment;

public class TyreFinderFragment extends BaseFragment {

    private List<Dataset> datasets;
    private Listener mListener;
    RecyclerView recyclerView;
    private static final String TAG = "TyreFinderFragment";

    public TyreFinderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateDataset();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        getParentFragment().getView().setLayoutParams(layoutParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tyre_finder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onCreate: " + getArguments().getString("vehicleType"));
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ItemAdapter(4));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (Listener) context;
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void onItemClicked(int position, View view);
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        final TextView title;
        final TextView tagline;
        final AppCompatImageView image;
        final AppCompatRatingBar rating;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_tyre_finder_item_view, parent, false));
            title = itemView.findViewById(R.id.tyre_name);
            tagline = itemView.findViewById(R.id.tyre_tagline);
            image = itemView.findViewById(R.id.tyre_img);
            rating = itemView.findViewById(R.id.tyre_rating);
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
            Dataset dataset = datasets.get(position);
            holder.title.setText(dataset.title);
            holder.tagline.setText(dataset.tagline);
            holder.rating.setRating(dataset.rating);
            holder.image.setImageResource(dataset.image);
        }

        @Override
        public int getItemCount() {
            return datasets.size();
        }

    }

    private class Dataset {
        private int image;
        private String title;
        private String tagline;
        private int rating;

        public Dataset(int icon, String title, String tagline, int rating) {
            this.image = icon;
            this.title = title;
            this.tagline = tagline;
            this.rating = rating;
        }
    }

    private void populateDataset() {
        datasets = new ArrayList<>();
        datasets.add(new Dataset(R.drawable.sample_tyre_1, "1", "Grippy", 4));
        datasets.add(new Dataset(R.drawable.sample_tyre_2, "2", "Tension free", 3));
        datasets.add(new Dataset(R.drawable.sample_tyre_1, "3", "Grrippy", 5));
        datasets.add(new Dataset(R.drawable.sample_tyre_2, "Zoom XL", "Tension free", 5));
        datasets.add(new Dataset(R.drawable.sample_tyre_2, "Zoom XL", "Tension free", 5));
    }

}
