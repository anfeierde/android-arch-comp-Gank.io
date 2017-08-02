package com.cz1.gank.ui;

import android.view.View;
import com.cz1.gank.data.local.entity.GankEntity;

/**
 * Created by wkchen on 2017/8/2.
 */

public interface GankListCallback {
    void onGankClicked(GankEntity gankEntity, View view);
}
