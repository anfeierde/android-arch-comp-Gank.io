package com.cz1.gank;

import android.app.Activity;
import android.app.Application;
import com.cz1.gank.di.AppInjector;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import javax.inject.Inject;

/**
 * Created by wkchen on 2017/8/2.
 */

public class GankApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;


    @Override public void onCreate() {
        super.onCreate();

        AppInjector.init(this);
    }


    @Override public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
