package piyushjohnson.mytyre;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import piyushjohnson.mytyre.common.BaseActivity;
import piyushjohnson.mytyre.databinding.ActivityHomeBinding;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.model.VehicleType;

public class HomeActivity extends BaseActivity implements TyreFinderFragment.Listener, VehicleTypesGridDialogFragment.Listener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {

    private static final String TAG = "HomeActivity";

    private ActivityHomeBinding binding;
    private MainViewModel mainViewModel;
    private NavController navController;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        setSupportActionBar(binding.toolbar);

        mainViewModel = getViewModel(MainViewModel.class);
        Log.i(TAG, "onCreate: MainViewModel " + mainViewModel.hashCode());
        Log.i(TAG, "onCreate: NavController " + navController.hashCode());

        mainViewModel.getIsSignedIn().observe(this, isSignedIn -> {
            if (!isSignedIn)
                authenticate();
            else
                mainViewModel.setLoggedInUser(FirebaseAuth.getInstance().getCurrentUser());
        });
        mainViewModel.getLoggedInUser().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                ((TextView) binding.navigationView.getHeaderView(0).findViewById(R.id.nav_header_profile_username)).setText(firebaseUser.getDisplayName());
            }

        });
        mainViewModel.getIsOffline().observe(this, isOffline -> {
            if (isOffline)
                Snackbar.make(binding.rootLayout, "Oops! You're offline", Snackbar.LENGTH_LONG).show();
        });

        binding.tyreVehicleBtn.setOnClickListener(this);
        binding.tyreSizeBtn.setOnClickListener(this);

        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout);
        NavigationUI.setupWithNavController(binding.navigationView, navController);

        binding.navigationView.setNavigationItemSelectedListener(this);
        binding.drawerLayout.setScrimColor(Color.TRANSPARENT);
        binding.drawerLayout.addDrawerListener(this);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            Log.i(TAG, "onDestinationChanged: " + destination.getLabel());
            boolean shouldExpand = false, shouldScroll = false;
            switch (destination.getId()) {
                case R.id.YourCart:
                    shouldExpand = shouldScroll = false;
                    break;
                case R.id.HomeScreen:
                    shouldExpand = shouldScroll = true;
                    break;
            }
            binding.appBar.setExpanded(shouldExpand, true);
            binding.nestedScrollContent.setNestedScrollingEnabled(shouldScroll);
        });

    }

    private void authenticate() {
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                        new AuthUI.IdpConfig.PhoneBuilder().build()
                ))
                .setTheme(R.style.AppTheme_Auth)
                .setLogo(R.drawable.ic_logo)
                .setIsSmartLockEnabled(false)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_SIGN_IN:
                if (resultCode == RESULT_OK) {
                    mainViewModel.setLoggedInUser(FirebaseAuth.getInstance().getCurrentUser());
                } else {
                    authenticate();
                }
        }
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
        Log.d(TAG, "onVehicleTypeSelected() called with: vehicleType = [" + vehicleType.getTitle() + "]");
        Bundle bundle = new Bundle();
        bundle.putString("vehicleType", vehicleType.getTitle());
        if (navController.getCurrentDestination().getId() == R.id.TyreFinder) {
            navController.popBackStack();
        }
        navController.navigate(R.id.action_HomeScreen_to_TyreFinder, bundle);
    }

    @Override
    public void onTyreSelected(Tyre tyre) {
        Log.d(TAG, "onTyreSelected() called with: tyre = [" + tyre.getName() + "]");
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, binding.drawerLayout);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_my_wishlist:

                break;
            case R.id.nav_cart:
                navController.navigate(R.id.YourCart);
                break;
            case R.id.nav_about_us:
                navController.navigate(R.id.AboutUs);
                break;
            case R.id.nav_contact:
                navController.navigate(R.id.Contact);
                break;
        }
        menuItem.setChecked(true);
        binding.drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        float slideX = drawerView.getWidth() * slideOffset;
        binding.rootLayout.setTranslationX(slideX);
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
