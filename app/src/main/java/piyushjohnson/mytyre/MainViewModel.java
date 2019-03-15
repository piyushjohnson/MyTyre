package piyushjohnson.mytyre;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import piyushjohnson.mytyre.common.Resource;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.repo.MainRepository;
import piyushjohnson.mytyre.util.Filters;
import piyushjohnson.mytyre.util.SingleLiveEvent;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
    private final MainRepository repository;
    private final LiveData<Resource<List<Tyre>>> popularTyres;
    private SingleLiveEvent<Boolean> isOffline = new SingleLiveEvent<>();
    private MutableLiveData<Filters> filters = new MutableLiveData<>();
    private LiveData<Resource<List<Tyre>>> tyres;

    @Inject
    MainViewModel(MainRepository mainRepository) {
        this.repository = mainRepository;
        popularTyres = repository.popularTyres(null);
        Log.i(TAG, "MainViewModel: initialised main view model");
    }

    public LiveData<Resource<List<Tyre>>> getPopularTyres() {
        Log.i(TAG, "getPopularTyres: added popular tyres live data");
        return popularTyres;
    }

    public LiveData<Resource<List<Tyre>>> getTyres(Filters filters) {
        Log.i(TAG, "getTyres: added tyres live data");
        tyres = repository.getTyres(filters == null ? Filters.getDefault() : filters);
        return tyres;
    }

    public LiveData<Boolean> getIsOffline() {
        Log.i(TAG, "getIsOffline: " + isOffline.hasActiveObservers());
        return this.isOffline;
    }

    public void setIsOffline(boolean isOffline) {
        this.isOffline.setValue(isOffline);
        this.isOffline.call();
    }

    public void setFilters(Filters filters) {

        if (filters == null) {
            return;
        }
        this.filters.setValue(filters);
    }
}
