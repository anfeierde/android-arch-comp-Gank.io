package com.cz1.gank.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.cz1.gank.R;
import com.cz1.gank.data.local.entity.GankEntity;
import com.cz1.gank.databinding.ItemGankBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wkchen on 2017/8/2.
 */

public class GankListAdapter extends BaseAdapter<GankListAdapter.GankViewHolder,GankEntity> {

    private List<GankEntity> gankEntities;
    private final GankListCallback gankCallback;


    public GankListAdapter(@NonNull GankListCallback gankCallback) {
        gankEntities = new ArrayList<>();
        this.gankCallback = gankCallback;
    }


    @Override public GankListAdapter.GankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return GankViewHolder.create(LayoutInflater.from(parent.getContext()),parent,gankCallback);
    }


    @Override public void onBindViewHolder(GankListAdapter.GankViewHolder holder, int position) {
        holder.onBind(gankEntities.get(position));
    }


    @Override public int getItemCount() {
        return gankEntities == null ? 0: gankEntities.size();
    }



    @Override public void setData(List<GankEntity> entities) {
        this.gankEntities = entities;
        notifyDataSetChanged();
    }



    static class GankViewHolder extends RecyclerView.ViewHolder {

        public static GankViewHolder create(LayoutInflater inflater, ViewGroup parent, GankListCallback callback) {
            ItemGankBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_gank, parent,
                false);
            return new GankViewHolder(binding,callback);
        }

        ItemGankBinding binding;

        public GankViewHolder(ItemGankBinding binding,GankListCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(view -> {
                callback.onGankClicked(binding.getGank(),view);
            });
        }


        public void onBind(GankEntity gankEntity) {
            binding.setGank(gankEntity);
            binding.executePendingBindings();
        }
    }
}
