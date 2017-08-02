package com.cz1.gank.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import com.cz1.gank.api.GankService;
import com.cz1.gank.api.LiveDataCallAdapterFactory;
import com.cz1.gank.data.local.GankDatabase;
import com.cz1.gank.data.local.dao.GankDao;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wkchen on 2017/8/2.
 */
@Module(includes = ViewModelModule.class)
public class AppModule {


    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient().newBuilder()
            .addInterceptor(OkHttpLoggerInterceptor())
            .build();
    }


    @Singleton
    @Provides
    GankService provideGankService(OkHttpClient client) {
        return new Retrofit.Builder()
            .client(client)
            .baseUrl("http://gank.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(new LiveDataCallAdapterFactory())
            .build()
            .create(GankService.class);
    }

    @Singleton
    @Provides
    GankDatabase provideGankDatabase(Application application) {
        return Room.databaseBuilder(application,GankDatabase.class, "ganks.db").build();
    }

    @Singleton
    @Provides
    GankDao provideGankDao(GankDatabase gankDatabase) {
        return gankDatabase.gankDao();
    }

    @Singleton
    private Interceptor OkHttpLoggerInterceptor() {
        return new HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY);
    }
}
