package piyushjohnson.mytyre;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.card.MaterialCardView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class CustomMaterialCardBehavior extends CoordinatorLayout.Behavior {

    private static String TAG = "CustomMaterialCardBehavior";
    private int scrollRange = -1;
    private boolean isShow = false;
    private boolean isHide = false;

    public CustomMaterialCardBehavior() {
        super();
    }

    public CustomMaterialCardBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
//        Log.i(TAG, "layoutDependsOn: " + dependency.getClass().toString());
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if(child instanceof  MaterialCardView && dependency instanceof AppBarLayout) {
//            Log.i(TAG, "onDependentViewChanged: " + child.getClass().toString());
//            Log.i(TAG, "onDependentViewChanged: " + dependency.getClass().toString());

            AppBarLayout appBarLayout = (AppBarLayout) dependency;
            final MaterialCardView materialCardView = (MaterialCardView) child;
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                    materialCardView.animate().translationY(i);
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.getTotalScrollRange();
//                        Log.i(TAG, "onOffsetChanged: initialised");
                    }
                    if (scrollRange + i == 0) {
//                        Log.i(TAG, "onOffsetChanged: up");
//                        materialCardView.animate().alpha(0f);
                        isShow = true;
                    } else if (isShow) {
//                        Log.i(TAG, "onOffsetChanged: down");
//                        materialCardView.animate().alpha(1f);
                        isShow = false;
                    }
                }
            });

        }

        return false;
    }
}

