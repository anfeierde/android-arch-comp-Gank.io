package com.cz1.gank.di;

import android.app.Application;
import com.cz1.gank.GankApplication;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import javax.inject.Singleton;

/**
 * Created by wkchen on 2017/8/2.
 */
@Singleton
@Component(modules = {
    AndroidInjectionModule.class,
    AppModule.class,
    MainActivityModule.class})
public interface AppComponent {

    void inject(GankApplication gankerApplication);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}