package piyushjohnson.mytyre.ui;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import piyushjohnson.mytyre.common.Resource;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.repo.MainRepository;
import piyushjohnson.mytyre.util.Filters;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
    private final MainRepository repository;
    private final LiveData<Resource<List<Tyre>>> popularTyres;
    private MutableLiveData<FirebaseUser> loggedInUser = new MutableLiveData<>();
    private MutableLiveData<Boolean> isOffline = new MutableLiveData<>();
    private MutableLiveData<Filters> filters = new MutableLiveData<>();
    private LiveData<Boolean> isSignedIn;
    private LiveData<Resource<List<Tyre>>> tyres;
    private LiveData<Resource<Tyre>> tyre;

    @Inject
    MainViewModel(MainRepository mainRepository) {
        this.repository = mainRepository;
        popularTyres = repository.popularTyres();
        isSignedIn = new LiveData<Boolean>() {
            @Override
            protected void onActive() {
                super.onActive();
                setValue(FirebaseAuth.getInstance().getCurrentUser() != null);
            }
        };
        Log.i(TAG, "MainViewModel: initialised main view model");
        Log.i(TAG, "MainViewModel: popularTyres " + popularTyres.hashCode());
        Log.d(TAG, "MainViewModel() called with: mainRepository = [" + mainRepository.hashCode() + "]");

    }

    public LiveData<Boolean> getIsSignedIn() {
        return isSignedIn;
    }

    public LiveData<FirebaseUser> getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(FirebaseUser firebaseUser) {
        loggedInUser.setValue(firebaseUser);
    }

    public LiveData<Resource<List<Tyre>>> getPopularTyres() {
        Log.d(TAG, "getPopularTyres() called");
        Log.i(TAG, "getPopularTyres: added popular tyres live data " + popularTyres.hashCode());
        return popularTyres;
    }

    public LiveData<Resource<List<Tyre>>> getTyres(Filters filters) {
        Log.d(TAG, "getTyres() called with: filters = [" + filters + "]");
        Log.i(TAG, "getTyres: added tyres live data");
        tyres = repository.getTyres(filters == null ? Filters.getDefault() : filters);
        return tyres;
    }

    public LiveData<Resource<Tyre>> getTyre(String tyreName) {
        Log.d(TAG, "getTyre() called with tyreName =[" + tyreName + "]");
        tyre = repository.getTyre(tyreName);
        return tyre;
    }

    public LiveData<Boolean> getIsOffline() {
        Log.d(TAG, "getIsOffline() called");
        Log.i(TAG, "getIsOffline: " + isOffline.hasActiveObservers());
        return this.isOffline;
    }

    public void setIsOffline(boolean isOffline) {
        this.isOffline.setValue(isOffline);
        Log.d(TAG, "setIsOffline() called with: isOffline = [" + isOffline + "]");
    }

    public void setFilters(Filters filters) {
        Log.d(TAG, "setFilters() called with: filters = [" + filters + "]");
        if (filters == null) {
            return;
        }
        this.filters.setValue(filters);
    }
}