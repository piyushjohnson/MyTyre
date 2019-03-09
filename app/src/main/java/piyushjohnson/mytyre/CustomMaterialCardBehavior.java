package piyushjohnson.mytyre;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.card.MaterialCardView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class CustomMaterialCardBehavior extends CoordinatorLayout.Behavior {

    private static String TAG = "CustomMaterialCardBehavior";

    public CustomMaterialCardBehavior() {
        super();
    }

    public CustomMaterialCardBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        Log.i(TAG, "layoutDependsOn: " + dependency.getClass().toString());
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if(child instanceof  MaterialCardView && dependency instanceof AppBarLayout) {
//            Log.i(TAG, "onDependentViewChanged: " + child.getClass().toString());
//            Log.i(TAG, "onDependentViewChanged: " + dependency.getClass().toString());

            AppBarLayout appBarLayout = (AppBarLayout) dependency;
            final MaterialCardView materialCardView = (MaterialCardView) child;

        }

        return false;
    }
}

