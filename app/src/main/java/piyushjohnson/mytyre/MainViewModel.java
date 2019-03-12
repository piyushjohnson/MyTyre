package piyushjohnson.mytyre;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import piyushjohnson.mytyre.common.Resource;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.repo.MainRepository;

public class MainViewModel extends ViewModel {
    private final MainRepository repository;
    private final LiveData<Resource<List<Tyre>>> popularTyres;

    @Inject
    MainViewModel(MainRepository mainRepository) {
        this.repository = mainRepository;
        popularTyres = repository.popularTyres();
    }

    public LiveData<Resource<List<Tyre>>> getPopularTyres() {
        return popularTyres;
    }
}
