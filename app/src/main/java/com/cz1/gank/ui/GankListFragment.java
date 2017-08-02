package com.cz1.gank.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.cz1.gank.R;
import com.cz1.gank.data.local.entity.GankEntity;
import com.cz1.gank.databinding.FragmentGankListBinding;
import com.cz1.gank.di.Injectable;
import com.cz1.gank.data.remote.Resource;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by wkchen on 2017/8/2.
 */

public class GankListFragment extends LifecycleFragment implements GankListCallback,Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    GankListViewModel gankViewModel;

    FragmentGankListBinding binding;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gank_list, container, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(new GankListAdapter(this));
        return binding.getRoot();
    }


    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gankViewModel = ViewModelProviders.of(this, viewModelFactory).get(GankListViewModel.class);
        gankViewModel.getGankList()
            .observe(this, new Observer<Resource<List<GankEntity>>>() {
                @Override public void onChanged(@Nullable Resource<List<GankEntity>> resource) {
                    binding.setResource(resource);
                }
            });
    }


    @Override public void onGankClicked(GankEntity gankEntity, View view) {
        Toast.makeText(getActivity(), gankEntity.getDesc(), Toast.LENGTH_SHORT).show();
    }
}
