package com.cz1.gank.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.cz1.gank.data.local.dao.GankDao;
import com.cz1.gank.data.local.entity.GankEntity;

/**
 * Created by wkchen on 2017/8/2.
 */
@Database(entities = {
    GankEntity.class
},version = 1)
public abstract class GankDatabase extends RoomDatabase {
    abstract public GankDao gankDao();
}
