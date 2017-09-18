package com.peng.library.mantis.expand.refresh.link;


import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.view.View;

import com.peng.library.mantis.expand.list.link.ListConfig;
import com.peng.refresh.layout.PtrFrameLayout;

/**
 * 刷新相关的Config
 * Created by pyt on 2017/6/27.
 */

public class RefreshConfig {

    public static final int RESOURCE_NULL = -1;

    //刷新拉动的阻尼
    private float refreshResistance ;
    //达到高度为多少时，开始刷新
    private float radioOfHeaderHeightToRefresh;
    //拉动的高度大于header高度，那么大于的这个差值，回滚的时长
    private int durationToClose;
    //关闭Header用的时长
    private int durationToCloseHeader;
    //是否下拉的时候刷新，否者的话是释放刷新
    private boolean pullToRefresh;
    //刷新的时候是否保持头部
    private boolean keepHeaderWhenRefresh;
    //是否进行自动刷新
    private boolean autoRefresh;
    //设置最小刷新时间（防止刷新太快，导致动画播放的效果不是很好）
    private int loadingMinTime;
    //创建下拉刷新头的监听器
    private OnCreateHeaderViewListener onCreateHeaderViewListener;
    //刷新头的背景
    @ColorInt
    private int headerBackgroundColor;
    //需要刷新的View
    @IdRes
    private int needRefreshViewId;
    //是否开启刷新(默认是开启)
    private boolean refreshEnable = true;

    public int getHeaderBackgroundColor() {
        return headerBackgroundColor;
    }

    public float getRefreshResistance() {
        return refreshResistance;
    }

    public float getRadioOfHeaderHeightToRefresh() {
        return radioOfHeaderHeightToRefresh;
    }

    public int getDurationToClose() {
        return durationToClose;
    }

    public int getDurationToCloseHeader() {
        return durationToCloseHeader;
    }

    public boolean isPullToRefresh() {
        return pullToRefresh;
    }

    public boolean isKeepHeaderWhenRefresh() {
        return keepHeaderWhenRefresh;
    }

    public boolean isAutoRefresh() {
        return autoRefresh;
    }

    public int getLoadingMinTime() {
        return loadingMinTime;
    }

    public int getNeedRefreshViewId() {
        return needRefreshViewId;
    }

    public boolean isRefreshEnable() {
        return refreshEnable;
    }

    public View createRefreshHeaderView(PtrFrameLayout refreshLayout){
        return onCreateHeaderViewListener.onCreateHeaderView(refreshLayout);
    }

    public static class Builder implements Cloneable {
        //刷新拉动的阻尼
        private float refreshResistance = 2f;
        //达到高度为多少时，开始刷新
        private float radioOfHeaderHeightToRefresh = 1.0f;
        //拉动的高度大于header高度，那么大于的这个差值，回滚的时长
        private int durationToClose = 200;
        //关闭Header用的时长
        private int durationToCloseHeader = 1000;
        //是否下拉的时候刷新，否者的话是释放刷新
        private boolean pullToRefresh = false;
        //刷新的时候是否保持头部
        private boolean keepHeaderWhenRefresh = true;
        //是否进行自动刷新
        private boolean autoRefresh = false;
        //设置最小刷新时间（防止刷新太快，导致动画播放的效果不是很好）
        private int loadingMinTime = 500;
        //创建HeaderView的监听器
        private OnCreateHeaderViewListener onCreateHeaderViewListener;
        //刷新头的背景
        @ColorInt
        private int headerBackgroundColor = RESOURCE_NULL;
        //需要刷新的View
        @IdRes
        private int needRefreshViewId;
        //是否开启刷新(默认是开启)
        private boolean refreshEnable = true;

        public Builder refreshResistance(float refreshResistance) {
            this.refreshResistance = refreshResistance;
            return this;
        }

        public Builder radioOfHeaderHeightToRefresh(float radioOfHeaderHeightToRefresh) {
            this.radioOfHeaderHeightToRefresh = radioOfHeaderHeightToRefresh;
            return this;
        }

        public Builder durationToClose(int durationToClose) {
            this.durationToClose = durationToClose;
            return this;
        }

        public Builder durationToCloseHeader(int durationToCloseHeader) {
            this.durationToCloseHeader = durationToCloseHeader;
            return this;
        }

        public Builder pullToRefresh(boolean pullToRefresh) {
            this.pullToRefresh = pullToRefresh;
            return this;
        }

        public Builder keepHeaderWhenRefresh(boolean keepHeaderWhenRefresh) {
            this.keepHeaderWhenRefresh = keepHeaderWhenRefresh;
            return this;
        }

        public Builder autoRefresh(boolean autoRefresh) {
            this.autoRefresh = autoRefresh;
            return this;
        }

        public Builder loadingMinTime(int loadingMinTime) {
            this.loadingMinTime = loadingMinTime;
            return this;
        }

        public Builder headerBackgroundColor(@ColorInt int headerBackgroundColor) {
            this.headerBackgroundColor = headerBackgroundColor;
            return this;
        }

        public Builder onCreateHeaderViewListener(OnCreateHeaderViewListener onCreateHeaderViewListener) {
            this.onCreateHeaderViewListener = onCreateHeaderViewListener;
            return this;
        }

        public Builder needRefreshViewId(@IdRes int viewId) {
            this.needRefreshViewId = viewId;
            return this;
        }

        public Builder refreshEnable(boolean refreshEnable) {
            this.refreshEnable = refreshEnable;
            return this;
        }

        public RefreshConfig builder() {
            RefreshConfig refreshConfig = new RefreshConfig();
            //校验OnCreateHeaderViewListener
            if (onCreateHeaderViewListener == null) {
                throw new IllegalArgumentException("onCreateHeaderViewListener must not null,you must init it!");
            }
            //配置所有熟悉感
            refreshConfig.onCreateHeaderViewListener = onCreateHeaderViewListener;
            refreshConfig.refreshResistance = refreshResistance;
            refreshConfig.radioOfHeaderHeightToRefresh = radioOfHeaderHeightToRefresh;
            refreshConfig.durationToClose = durationToClose;
            refreshConfig.durationToCloseHeader = durationToCloseHeader;
            refreshConfig.pullToRefresh = pullToRefresh;
            refreshConfig.keepHeaderWhenRefresh = keepHeaderWhenRefresh;
            refreshConfig.autoRefresh = autoRefresh;
            refreshConfig.loadingMinTime = loadingMinTime;
            refreshConfig.headerBackgroundColor = headerBackgroundColor;
            refreshConfig.needRefreshViewId = needRefreshViewId;
            refreshConfig.refreshEnable = refreshEnable;
            return refreshConfig;
        }

        @Override
        public Builder clone() {
            try {
                Object clone = super.clone();
                return (Builder) clone;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            //如果clone失败，这里进行重新创建
            return new Builder().onCreateHeaderViewListener(onCreateHeaderViewListener)
                    .refreshResistance(refreshResistance)
                    .radioOfHeaderHeightToRefresh(radioOfHeaderHeightToRefresh)
                    .durationToClose(durationToClose)
                    .durationToCloseHeader(durationToCloseHeader)
                    .pullToRefresh(pullToRefresh)
                    .keepHeaderWhenRefresh(keepHeaderWhenRefresh)
                    .autoRefresh(autoRefresh)
                    .loadingMinTime(loadingMinTime)
                    .headerBackgroundColor(headerBackgroundColor)
                    .refreshEnable(refreshEnable);
        }

    }

}
