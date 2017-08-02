package com.cz1.gank.data.remote;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import com.cz1.gank.AppExecutors;
import com.cz1.gank.api.ApiResponse;
import com.cz1.gank.api.GankService;
import com.cz1.gank.api.HttpResult;
import com.cz1.gank.data.local.GankDatabase;
import com.cz1.gank.data.local.dao.GankDao;
import com.cz1.gank.data.local.entity.GankEntity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by wkchen on 2017/8/2.
 */
@Singleton
public class GankRepository {

    private final GankDatabase gankDatabase;
    private final GankDao gankDao;
    private final GankService gankService;
    private final AppExecutors appExecutors;

    @Inject
    public GankRepository(GankDatabase gankDatabase, GankDao gankDao, GankService gankService, AppExecutors appExecutors) {
        this.gankDatabase = gankDatabase;
        this.gankDao = gankDao;
        this.gankService = gankService;
        this.appExecutors = appExecutors;
    }


    public LiveData<Resource<List<GankEntity>>> loadGanks(int page) {
        return new NetworkBoundResource<List<GankEntity>, HttpResult<List<GankEntity>>>(
            appExecutors) {
            @Override protected void saveCallResult(@NonNull HttpResult<List<GankEntity>> item) {
                gankDao.insert(item.results);
            }


            @Override protected boolean shouldFetch(@Nullable List<GankEntity> data) {
                if (data == null || data.isEmpty()) {
                    return true;
                }
                if (data.size() > 0) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD", Locale.CHINA);
                    try {
                        Date date = simpleDateFormat.parse(data.get(0).getPublishedAt());
                        return !DateUtils.isToday(date.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }


            @NonNull @Override protected LiveData<List<GankEntity>> loadFromDb() {
                return gankDao.loadGanks();
            }


            @NonNull @Override
            protected LiveData<ApiResponse<HttpResult<List<GankEntity>>>> createCall() {
                return gankService.getGankList("Android", 10, page);
            }
        }.asLiveData();
    }
}
