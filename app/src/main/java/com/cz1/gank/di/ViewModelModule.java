package com.cz1.gank.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import com.cz1.gank.ui.GankListViewModel;
import com.cz1.gank.viewmodel.GankerViewModelFactory;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GankListViewModel.class)
    abstract ViewModel bindDailyViewModel(GankListViewModel dailyViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(GankerViewModelFactory factory);

}