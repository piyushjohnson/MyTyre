package piyushjohnson.mytyre.common;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class DataViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    final T binding;

    DataViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
