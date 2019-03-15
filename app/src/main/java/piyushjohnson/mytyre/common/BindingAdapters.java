package piyushjohnson.mytyre.common;

import android.net.Uri;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

public class BindingAdapters {

    public BindingAdapters() {
    }

    @BindingAdapter({"android:imageUrl"})
    public static void loadNetworkImage(AppCompatImageView view, String url) {
        if (Uri.parse(url).isRelative()) {
            Glide.with(view.getContext())
                    .load("https://www.ceat.com" + url)
                    .thumbnail(0.2f)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(view);
        }

    }

    @BindingAdapter({"android:src"})
    public static void setImageUri(AppCompatImageButton view, int resource) {
        view.setImageResource(resource);
    }
}
