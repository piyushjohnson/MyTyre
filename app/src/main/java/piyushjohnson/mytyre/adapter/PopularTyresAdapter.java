package piyushjohnson.mytyre.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import piyushjohnson.mytyre.R;
import piyushjohnson.mytyre.common.DataListAdapter;
import piyushjohnson.mytyre.common.OnItemClickedListener;
import piyushjohnson.mytyre.databinding.FragmentHomePopularTyreItemViewBinding;
import piyushjohnson.mytyre.model.Tyre;

public class PopularTyresAdapter extends DataListAdapter<Tyre, FragmentHomePopularTyreItemViewBinding> {

    public PopularTyresAdapter(OnItemClickedListener<Tyre> listener) {
        super(listener);
    }

    @Override
    protected FragmentHomePopularTyreItemViewBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        final FragmentHomePopularTyreItemViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_popular_tyre_item_view, parent, false);
        return binding;
    }

    @Override
    protected void bind(FragmentHomePopularTyreItemViewBinding binding, Tyre tyre) {
        binding.setTyre(tyre);
    }
}
