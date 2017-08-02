package com.cz1.gank;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.cz1.gank.databinding.ActivityMainBinding;
import com.cz1.gank.ui.GankListFragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

/**
 * Created by wkchen on 2017/8/2.
 */
public class MainActivity extends AppCompatActivity
    implements LifecycleRegistryOwner, HasSupportFragmentInjector {


    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    ActivityMainBinding binding;


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (savedInstanceState == null) {
            GankListFragment fragment = new GankListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                .commit();
        }
    }


    @Override public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }


    @Override public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
