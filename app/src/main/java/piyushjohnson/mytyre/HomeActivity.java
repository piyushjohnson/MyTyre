package piyushjohnson.mytyre;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import piyushjohnson.mytyre.common.BaseActivity;
import piyushjohnson.mytyre.databinding.ActivityHomeBinding;
import piyushjohnson.mytyre.model.VehicleType;

public class HomeActivity extends BaseActivity implements TyreFinderFragment.Listener, VehicleTypesGridDialogFragment.Listener, VehicleModelsListDialogFragment.Listener, View.OnClickListener {

    private static final String TAG = "HomeActivity";

    private ActivityHomeBinding binding;
    private MainViewModel mainViewModel;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        mainViewModel = getViewModel(MainViewModel.class);
        Log.i(TAG, "onCreate: MainViewModel " + mainViewModel.hashCode());
        Log.i(TAG, "onCreate: NavController " + navController.hashCode());
        /*mainViewModel.getTyres(Filters.getDefault()).observe(this, listResource -> {
            if (listResource.isSuccessful()) {
                for (Tyre tyre : listResource.data()) {
                    Log.i(TAG, tyre.getName());
                }
            }
        });*/

        binding.tyreVehicleBtn.setOnClickListener(this);
        binding.tyreSizeBtn.setOnClickListener(this);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            Log.i(TAG, "onDestinationChanged: " + destination.getLabel());
            switch (destination.getId()) {
                case R.id.TyreFinder:
                    binding.appBar.setExpanded(false, true);
                    break;
            }
        });
    }

    @Override
    public void onItemClicked(int position, View view) {

    }

    @Override
    public void onClick(View v) {
        DialogFragment dialogFragment = null;
        switch (v.getId()) {
            case R.id.tyre_vehicle_btn:
                dialogFragment = VehicleTypesGridDialogFragment.newInstance();
                break;
            case R.id.tyre_size_btn:
                dialogFragment = VehicleModelsListDialogFragment.newInstance(4);
                break;
        }
        dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());
        Log.i(TAG, "onClick: changed dialog");
    }

    @Override
    public void onVehicleTypeSelected(VehicleType vehicleType) {
        Log.i(TAG, "onVehicleTypeSelected: " + vehicleType.getTitle());
        Bundle bundle = new Bundle();
        bundle.putString("vehicleType", vehicleType.getTitle());
        if (navController.getCurrentDestination().getId() == R.id.TyreFinder) {
            navController.popBackStack();
        }
        navController.navigate(R.id.action_HomeScreen_to_TyreFinder, bundle);
    }
}
