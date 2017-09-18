package com.peng.library.mantis.expand.refresh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peng.library.mantis.Mantis;
import com.peng.library.mantis.expand.MantisBaseFragment;
import com.peng.library.mantis.expand.refresh.link.MantisPtrHandler;
import com.peng.library.mantis.expand.refresh.link.RefreshConfig;
import com.peng.library.mantis.hint.HintConfig;
import com.peng.refresh.layout.PtrFrameLayout;
import com.peng.refresh.layout.PtrUIHandler;

/**
 * 含有刷新Layout的Fragment
 * Created by pyt on 2017/6/27.
 */

public abstract class MantisRefreshFragment<PRESENT extends MantisRefreshContract.Present> extends MantisBaseFragment<PRESENT> implements MantisRefreshContract.View<PRESENT>{

    private PtrFrameLayout refreshLayout;

    /**
     * 返回一个刷新需要的配置
     *
     * @return
     */
    protected RefreshConfig.Builder getRefreshConfigBuilder() {
        return Mantis.getInstance().getRefreshConfigBuilder();
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, HintConfig.Builder hintConfigBuilder) {
        RefreshConfig.Builder refreshConfigBuilder = getRefreshConfigBuilder();
        if (refreshConfigBuilder == null) {
            throw new IllegalArgumentException("The RefreshConfigBuilder must not null!");
        }
        //调用子类的onCreateView方法
        View needRefreshView = onCreateView(inflater, null, savedInstanceState,hintConfigBuilder, refreshConfigBuilder);
        //获取当前的刷新配置
        RefreshConfig refreshConfig = refreshConfigBuilder.builder();
        if (!refreshConfig.isRefreshEnable()) {
            return needRefreshView;
        }
        //创建RefreshLayout
        this.refreshLayout = new PtrFrameLayout(getContext());
        this.refreshLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //设置刷新的回调
        this.refreshLayout.setPtrHandler(new MantisPtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                onRefreshCall();
            }
        });

        View refreshHeaderView = refreshConfig.createRefreshHeaderView(refreshLayout);
        if (refreshHeaderView == null) {
            throw new IllegalStateException("can't create refresh header view,check you PtrUiHandler!");
        }
        //校验是否使用一个HeaderView
        if (refreshHeaderView.getParent() != null) {
            throw new IllegalStateException("you must create new HeaderView,not use single View!");
        }
        //校验有没有是实现PtrUIHandler接口
        if (!(refreshHeaderView instanceof PtrUIHandler)) {
            throw new IllegalArgumentException("The Header view must implements PtrUIHandler");
        } else {
            this.refreshLayout.addPtrUIHandler((PtrUIHandler) refreshHeaderView);
        }

        //添加这个HeaderView
        this.refreshLayout.setHeaderView(refreshHeaderView);
        //切换用户的刷新View
        int needRefreshViewId = refreshConfig.getNeedRefreshViewId();
        if (needRefreshViewId != 0) {
            View needRefreshChildView = needRefreshView.findViewById(needRefreshViewId);
            ViewGroup parent = (ViewGroup) needRefreshChildView.getParent();
            parent.removeView(needRefreshChildView);
            ViewGroup.LayoutParams layoutParams = needRefreshChildView.getLayoutParams();
            needRefreshChildView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            this.refreshLayout.addContentView(needRefreshChildView);
            this.refreshLayout.setLayoutParams(layoutParams);
            parent.addView(this.refreshLayout);
        } else {
            //将子View添加到RefreshLayout里面
            this.refreshLayout.addContentView(needRefreshView);
        }
        //配置RefreshLayout的属性
        if (refreshConfig.getHeaderBackgroundColor() != RefreshConfig.RESOURCE_NULL) {
            this.refreshLayout.setHeaderBackgroundColor(refreshConfig.getHeaderBackgroundColor());
        }
        this.refreshLayout.setDurationToClose(refreshConfig.getDurationToClose());
        this.refreshLayout.setDurationToCloseHeader(refreshConfig.getDurationToCloseHeader());
        this.refreshLayout.setRatioOfHeaderHeightToRefresh(refreshConfig.getRadioOfHeaderHeightToRefresh());
        this.refreshLayout.setResistance(refreshConfig.getRefreshResistance());
        this.refreshLayout.setPullToRefresh(refreshConfig.isPullToRefresh());
        this.refreshLayout.setKeepHeaderWhenRefresh(refreshConfig.isKeepHeaderWhenRefresh());
        this.refreshLayout.setLoadingMinTime(refreshConfig.getLoadingMinTime());
        if (refreshConfig.isAutoRefresh()) {
            autoRefresh();
        }
        return needRefreshViewId != 0?needRefreshView:this.refreshLayout;
    }

    /**
     * 完成刷新的状态
     */
    @Override
    public void refreshComplete(){
        if (refreshLayout != null) {
            this.refreshLayout.refreshComplete();
        }
    }

    /**
     * 进入页面自动刷新
     */
    @Override
    public void autoRefresh() {
        if (!isRefreshing()) {
            refreshLayout.getContentView().postDelayed(() -> refreshLayout.autoRefresh(), 200);
        }
    }

    /**
     * 是否正在刷新
     * @return
     */
    @Override
    public boolean isRefreshing(){
        return refreshLayout.isRefreshing();
    }

    /**
     * 当开始刷新的时候进行回调
     */
    public void onRefreshCall(){
        getPresent().onRefreshCall();
    }

    /**
     * refresh必须实现的方法
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @param hintBuilder
     * @param refreshBuilder
     * @return
     */
    public abstract View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState,HintConfig.Builder hintBuilder, RefreshConfig.Builder refreshBuilder);
}