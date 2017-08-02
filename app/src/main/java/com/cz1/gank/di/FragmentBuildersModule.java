package com.cz1.gank.di;

import com.cz1.gank.ui.GankListFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract GankListFragment contributeDailyFragment();

}