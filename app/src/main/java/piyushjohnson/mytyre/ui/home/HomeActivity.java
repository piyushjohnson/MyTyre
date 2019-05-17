package piyushjohnson.mytyre.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import piyushjohnson.mytyre.R;
import piyushjohnson.mytyre.TyreFinderFragment;
import piyushjohnson.mytyre.common.BaseActivity;
import piyushjohnson.mytyre.databinding.ActivityHomeBinding;
import piyushjohnson.mytyre.model.Param;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.model.VehicleType;
import piyushjohnson.mytyre.ui.MainViewModel;
import piyushjohnson.mytyre.ui.dialogs.TyreSizesListDialogFragment;
import piyushjohnson.mytyre.ui.dialogs.VehicleTypesGridDialogFragment;
import piyushjohnson.mytyre.util.Filters;

public class HomeActivity extends BaseActivity implements TyreFinderFragment.Listener, VehicleTypesGridDialogFragment.Listener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, TyreSizesListDialogFragment.Listener, DrawerLayout.DrawerListener {

    private static final String TAG = "HomeActivity";

    private ActivityHomeBinding binding;
    private MainViewModel mainViewModel;
    private NavController navController;
    private Filters filters;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        setSupportActionBar(binding.toolbar);

        mainViewModel = getViewModel(MainViewModel.class);

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
            boolean shouldExpand = false, shouldScroll = false;
            switch (destination.getId()) {
                case R.id.YourCart:
                    shouldExpand = shouldScroll = false;
                    break;
                case R.id.HomeScreen:
                    shouldExpand = shouldScroll = true;
                    break;
                case R.id.Contact:
                    break;
            }
            binding.appBar.setExpanded(shouldExpand, true);
            binding.nestedScrollContent.setNestedScrollingEnabled(shouldScroll);
        });

        filters = new Filters();
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
                dialogFragment = TyreSizesListDialogFragment.newInstance(4);
                break;
        }
        dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());
    }

    @Override
    public void onVehicleTypeSelected(VehicleType vehicleType) {
        filters.setVehicleType(vehicleType.getTitle());
        filters.setTyreParameters(null);
        mainViewModel.setFilters(filters);
        navController.navigate(R.id.TyreFinder);
    }

    @Override
    public void onTyreSizeSelected(Param param) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("size", param.getValue());
        filters.setVehicleType(null);
        filters.setTyreParameters(parameters);
        mainViewModel.setFilters(filters);
        navController.navigate(R.id.TyreFinder);
    }

    @Override
    public void onTyreSelected(Tyre tyre) {
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
        boolean shouldCloseDrawer = true;
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
                shouldCloseDrawer = false;
                navController.navigate(R.id.Contact);
                break;
        }
        menuItem.setChecked(true);
        if(shouldCloseDrawer)
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
        binding.rootLayout.setAlpha(0.8f);
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        binding.rootLayout.setAlpha(1.0f);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
