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

/*
 * @extends BaseActivity
 * @implements TyreFinderFragment.Listener ,VehicleTypesGridDialogFragment.Listener,
 * View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, TyreSizesListDialogFragment.Listener,
 * TyreSizesListDialogFragment.Listener, DrawerLayout.DrawerListener
 * @has-a ActivityHomeBinding, MainViewModel mainViewModel, NavController navController, Filters filters
 * */
public class HomeActivity extends BaseActivity implements TyreFinderFragment.Listener, VehicleTypesGridDialogFragment.Listener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, TyreSizesListDialogFragment.Listener, DrawerLayout.DrawerListener {

    // setting log tag for home activity
    private static final String TAG = "HomeActivity";
    private static final int RC_SIGN_IN = 9001;

    private ActivityHomeBinding binding;
    private MainViewModel mainViewModel;
    private NavController navController;
    private Filters filters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting instance to binding view's to layout file
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        // setting instance of navigation controller to listen to destination change events
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // transforming toolbar to support action bar, to be backwards compatible
        setSupportActionBar(binding.toolbar);

        // setting the instance of MainViewModel to access live data
        mainViewModel = getViewModel(MainViewModel.class);

        // listening to 'isSignedIn' live data, to be sure if user is logged in
        // if not then FirebaseAuth UI is shown, else updates the 'loggedInUser' to current firebase user
        mainViewModel.getIsSignedIn().observe(this, isSignedIn -> {
            if (!isSignedIn)
                authenticate();
            else
                mainViewModel.setLoggedInUser(FirebaseAuth.getInstance().getCurrentUser());
        });

        // listening to 'loggedInUser' live data
        // if currently a user is logged in then updates profile username displayed on navigation view
        mainViewModel.getLoggedInUser().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                ((TextView) binding.navigationView.getHeaderView(0).findViewById(R.id.nav_header_profile_username)).setText(firebaseUser.getDisplayName());
            }

        });

        // listening to 'isOffline' live data
        // if currently app is working in offline mode, then displays a snack bar with warning message
        mainViewModel.getIsOffline().observe(this, isOffline -> {
            if (isOffline)
                Snackbar.make(binding.rootLayout, "Oops! You're offline", Snackbar.LENGTH_LONG).show();
        });

        // registering click listeners for search a tyre-by-sizes(tyreSizeBtn) and tyre-vehicle-type(tyreVehicleBtn)
        //TODO: make this listener declare in activity_home.xml directly
        binding.tyreVehicleBtn.setOnClickListener(this);
        binding.tyreSizeBtn.setOnClickListener(this);

        // setup action bar to integrate with nav controller to automatically update title based on destination change
        // also injects a drawer toggle icon onto action bar
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout);
        NavigationUI.setupWithNavController(binding.navigationView, navController);

        binding.navigationView.setNavigationItemSelectedListener(this);
        binding.drawerLayout.setScrimColor(Color.TRANSPARENT);
        binding.drawerLayout.addDrawerListener(this);

        // listen's to destination changes
        // implemented to toggle expanded/collapsed app bar and also to enable/disable nested scrolling for content
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

    // Fires after FirebaseAuthUI activity closes to throw a result of authentication
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                mainViewModel.setLoggedInUser(FirebaseAuth.getInstance().getCurrentUser());
            } else if (resultCode == RESULT_CANCELED) {
                authenticate();
            }
        }
    }

    // Fires when tyre-by-vehicle or tyre-by-size is clicked
    // Open appropriate dialog to choose a criteria(vehicle type or tyre size) for showing tyres onto 'TyreFinderFragment'
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

    // Fires when a vehicle type is selected from 'VehicleTypeDialogFragment'
    // Updates filter with selected vehicle type title, then navigates to 'TyreFinder' destination
    // also resets any last selected tyre parameter from 'TyreSizesListDialogFragment'
    @Override
    public void onVehicleTypeSelected(VehicleType vehicleType) {
        filters.setVehicleType(vehicleType.getTitle());
        //TODO: reset this tyre parameter to null automatically whenever vehicle type updates
        filters.setTyreParameters(null);
        mainViewModel.setFilters(filters);
        navController.navigate(R.id.TyreFinder);
    }

    // Fires when a tyre size is selected from 'TyreSizesListDialogFragment'
    // Updates filter with map of all selected tyre parameters
    // resets any last selected vehicle type from 'VehicleTypeDialogFragment'
    @Override
    public void onTyreSizeSelected(Param param) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("size", param.getValue());
        //TODO: reset this vehicle type to null automatically whenever tyre parameter updates
        filters.setVehicleType(null);
        filters.setTyreParameters(parameters);
        mainViewModel.setFilters(filters);
        navController.navigate(R.id.TyreFinder);
    }

    // Fires when a tyre item is selected on TyreFinderFragment
    @Override
    public void onTyreSelected(Tyre tyre) {
        //TODO : must be remove this unused method
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, binding.drawerLayout);
    }

    // Fires only if the navigation drawer is in open state, then closes it on back button press
    // else executes default behaviour of back button
    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Fires when a menu item is selected from navigation view
    // Navigates to appropriate destination, then updates menu item state to checked
    // 'shouldCloseDrawer' implemented to toggle b/w close/open drawer, based on target destination
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

    // Fires when drawer is in sliding or dragging state
    // implemented to update rootLayout(content) x-axis translation based on how much width drawer currently takes(offset)
    // to create a sliding transition when drawer is swiped from a corner
    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        float slideX = drawerView.getWidth() * slideOffset;
        binding.rootLayout.setTranslationX(slideX);
    }

    // Fires when drawer is in fully visible state
    // implemented to update alpha(transparency) level of rootLayout(content) to more transparent value
    // to create a fade-in effect when drawer is active
    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        binding.rootLayout.setAlpha(0.8f);
    }

    // Fires when drawer is in fully gone state
    // implemented to update alpha(transparency) level of rootLayout(content) to fully opaque value
    // to create a fade-in effect when drawer is active
    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        binding.rootLayout.setAlpha(1.0f);
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        //TODO: mandatory to override this, nothing to fixed here
    }
}
