package com.cz1.gank.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.cz1.gank.data.local.entity.GankEntity;
import java.util.List;

/**
 * Created by wkchen on 2017/8/2.
 */
@Dao
public abstract class GankDao {

    @Query("SELECT * FROM ganks")
    public abstract LiveData<List<GankEntity>> loadGanks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<GankEntity> gankEntities);
}
