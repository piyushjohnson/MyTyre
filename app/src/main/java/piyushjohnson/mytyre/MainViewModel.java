package piyushjohnson.mytyre;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import piyushjohnson.mytyre.common.Resource;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.repo.MainRepository;
import piyushjohnson.mytyre.util.Filters;

public class MainViewModel extends ViewModel {
    private final MainRepository repository;
    private final LiveData<Resource<List<Tyre>>> popularTyres;
    private LiveData<Resource<List<Tyre>>> tyres;

    @Inject
    MainViewModel(MainRepository mainRepository) {
        this.repository = mainRepository;
        popularTyres = repository.popularTyres(null);
    }

    public LiveData<Resource<List<Tyre>>> getPopularTyres() {
        return popularTyres;
    }

    public LiveData<Resource<List<Tyre>>> getTyres(Filters filters) {
        tyres = repository.getTyres(filters == null ? Filters.getDefault() : filters);
        return tyres;
    }
}
