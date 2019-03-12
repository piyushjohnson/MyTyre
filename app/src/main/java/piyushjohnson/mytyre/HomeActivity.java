package piyushjohnson.mytyre;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import piyushjohnson.mytyre.common.BaseActivity;
import piyushjohnson.mytyre.common.Resource;
import piyushjohnson.mytyre.model.Tyre;

public class HomeActivity extends BaseActivity implements TyreFinderFragment.Listener, VehicleTypesGridDialogFragment.Listener, VehicleModelsListDialogFragment.Listener, View.OnClickListener {

    private static final String TAG = "HomeActivity";

    MaterialButton tyreVehicleBtn, tyreSizeBtn;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    View collapsingToolbarLayoutContent;
    Toolbar toolbar;

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mainViewModel = getViewModel(MainViewModel.class);
        Log.i(TAG, "onCreate: " + mainViewModel.hashCode());
        mainViewModel.getPopularTyres().observe(this, new Observer<Resource<List<Tyre>>>() {
            @Override
            public void onChanged(Resource<List<Tyre>> listResource) {
                if (listResource.isSuccessful()) {
                    for (Tyre tyre : listResource.data()) {
                        Toast.makeText(HomeActivity.this, tyre.name, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tyreVehicleBtn = findViewById(R.id.tyre_vehicle_btn);
        tyreSizeBtn = findViewById(R.id.tyre_size_btn);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayoutContent = findViewById(R.id.collapsing_toolbar_layout_content);
        appBarLayout = findViewById(R.id.app_bar);

        tyreVehicleBtn.setOnClickListener(this);
        tyreSizeBtn.setOnClickListener(this);


        Navigation.findNavController(this, R.id.nav_host_fragment).addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.i(TAG, "onDestinationChanged: ");
                switch (destination.getId()) {
                    case R.id.TyreFinder:
                        appBarLayout.setExpanded(false, true);
                        break;
                }
            }
        });
    }

    @Override
    public void onItemClicked(int position, View view) {
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_HomeScreen_to_TyreFinder);
    }

    @Override
    public void onClick(View v) {
        DialogFragment dialogFragment = null;
        switch (v.getId()) {
            case R.id.tyre_vehicle_btn:
                dialogFragment = VehicleTypesGridDialogFragment.newInstance(4);
                break;
            case R.id.tyre_size_btn:
                dialogFragment = VehicleModelsListDialogFragment.newInstance(4);
                break;
        }
        dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());
    }
}
