package piyushjohnson.mytyre.common;

import android.net.Uri;
import android.util.Log;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class BindingAdapters {

    private static final String TAG = "BindingAdapters";

    public BindingAdapters() {
    }

    @BindingAdapter({"android:imageUrl"})
    public static void loadNetworkImage(AppCompatImageView view, String url) {
        Log.i(TAG, "loadNetworkImage: url " + url);
        if (url != null) {
            if (Uri.parse(url).isRelative()) {
                Glide.with(view.getContext())
                        .load("https://cdn.ceat.com" + url)
                        .thumbnail(0.2f)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .into(view);
            }
        }

    }

    @BindingAdapter({"android:src"})
    public static void setImageUri(AppCompatImageButton view, int resource) {
        view.setImageResource(resource);
    }
}
