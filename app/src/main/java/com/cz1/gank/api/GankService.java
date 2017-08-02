package com.cz1.gank.api;

import android.arch.lifecycle.LiveData;
import com.cz1.gank.data.local.entity.GankEntity;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by wkchen on 2017/8/2.
 */

public interface GankService {

    @GET("data/{type}/{size}/{page}")
    LiveData<ApiResponse<HttpResult<List<GankEntity>>>> getGankList(
        @Path("type") String type, @Path("size") int size, @Path("page") int page);


}
