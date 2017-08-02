package com.cz1.gank.ui;

import android.support.v7.widget.RecyclerView;
import java.util.List;

/**
 * Created by wkchen on 2017/8/2.
 */
public abstract class BaseAdapter<Type extends RecyclerView.ViewHolder,Data>
    extends RecyclerView.Adapter<Type>{

    public abstract void setData(List<Data> data);

}
