package com.cz1.gank.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import com.cz1.gank.data.local.entity.GankEntity;
import com.cz1.gank.data.remote.GankRepository;
import com.cz1.gank.data.remote.Resource;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by wkchen on 2017/8/2.
 */

public class GankListViewModel extends ViewModel {

    private final GankRepository gankRepository;

    private MediatorLiveData<Resource<List<GankEntity>>> ganks = new MediatorLiveData<>();

    private MutableLiveData<Integer> pageLiveData = new MutableLiveData<>();

    private int page = 1;

    @Inject
    public GankListViewModel(final GankRepository gankRepository) {
        this.gankRepository = gankRepository;
        ganks = (MediatorLiveData<Resource<List<GankEntity>>>) Transformations.switchMap(pageLiveData,
            gankRepository::loadGanks);
        setPage(page);
    }


    public LiveData<Resource<List<GankEntity>>> getGankList() {
        return ganks;
    }

    public void setPage(int page) {
        pageLiveData.setValue(page);
    }
}
