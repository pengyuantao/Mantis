package com.peng.library.mantis.expand.list;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peng.library.mantis.Mantis;
import com.peng.library.mantis.expand.list.link.ListConfig;
import com.peng.library.mantis.expand.refresh.MantisRefreshFragment;
import com.peng.library.mantis.expand.refresh.link.RefreshConfig;
import com.peng.library.mantis.hint.HintConfig;

/**
 * 列表类型的fragment
 * Created by pyt on 2017/6/29.
 */

public abstract class MantisListFragment<PRESENT extends MantisListContract.Present, DATA> extends MantisRefreshFragment<PRESENT> implements MantisListContract.View<PRESENT, DATA> {

    protected RecyclerView recyclerView;


    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, HintConfig.Builder hintConfigBuilder, RefreshConfig.Builder refreshConfigBuilder) {
        ListConfig.Builder listConfigBuilder = getListConfigBuilder();
        if (listConfigBuilder == null) {
            throw new IllegalArgumentException("The ListConfigBuilder must not null!");
        }
        //获取子类返回的View
        View contentView = onCreateView(inflater, container, savedInstanceState,hintConfigBuilder, refreshConfigBuilder, listConfigBuilder);
        ListConfig listConfig = listConfigBuilder.build();

        if (contentView != null) {
            //找到它对应的RecyclerView
            if (listConfig.getRecyclerView() != null) {
                recyclerView = listConfig.getRecyclerView();
            } else if (listConfig.getRecyclerViewId() != ListConfig.RESOURCE_NULL) {
                recyclerView = (RecyclerView) contentView.findViewById(listConfig.getRecyclerViewId());
            } else {
                throw new IllegalArgumentException("You must set the recyclerView or id of recyclerView in the ListConfig");
            }
            setRecyclerViewConfig(recyclerView, listConfig);
            return contentView;
        } else {
            recyclerView = new RecyclerView(getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            recyclerView.setLayoutParams(params);
            setRecyclerViewConfig(recyclerView, listConfig);
            return recyclerView;
        }
    }

    /**
     * 配置这个RecycleView
     *
     * @param recyclerView
     * @param listConfig
     */
    private void setRecyclerViewConfig(RecyclerView recyclerView, ListConfig listConfig) {
        //设置Recycle背景色
        if (listConfig.getBackgroundColor() != ListConfig.RESOURCE_NULL) {
            recyclerView.setBackgroundColor(listConfig.getBackgroundColor());
        }
        getPresent().bindRecyclerView(recyclerView, getItemLayout(), listConfig);
    }

    @LayoutRes
    public abstract int getItemLayout();

    /**
     * 给子类复写的onCreateView
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @param refreshConfigBuilder 可以自己配置相关刷新的属性
     * @param listConfigBuilder    配置加载更多，自定义界面必须指定RecyclerView
     * @return
     */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState,HintConfig.Builder hintConfigBuilder, RefreshConfig.Builder refreshConfigBuilder, ListConfig.Builder listConfigBuilder) {
        return null;
    }

    /**
     * 返回当前自定义的Builder
     *
     * @return
     */
    public ListConfig.Builder getListConfigBuilder() {
        return Mantis.getInstance().getListConfigBuilder();
    }

}
