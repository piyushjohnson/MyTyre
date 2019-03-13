package piyushjohnson.mytyre.common;

import com.bumptech.glide.Glide;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

public class BindingAdapters {

    public BindingAdapters() {
    }

    @BindingAdapter({"android:imageUrl"})
    public static void loadNetworkImage(AppCompatImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .into(view);
    }

    @BindingAdapter({"android:src"})
    public static void setImageUri(AppCompatImageButton view, int resource) {
        view.setImageResource(resource);
    }
}
