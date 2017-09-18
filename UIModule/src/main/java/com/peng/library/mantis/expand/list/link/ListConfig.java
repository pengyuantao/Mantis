package com.peng.library.mantis.expand.list.link;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

/**
 * 用于定位用户指定Recycle的View
 * Created by pyt on 2017/6/29.
 */

public class ListConfig {
    //目前所支持的动画
    public static final int ALPHAIN = BaseQuickAdapter.ALPHAIN;
    public static final int SCALEIN = BaseQuickAdapter.SCALEIN;
    public static final int SLIDEIN_BOTTOM = BaseQuickAdapter.SLIDEIN_BOTTOM;
    public static final int SLIDEIN_LEFT = BaseQuickAdapter.SLIDEIN_LEFT;
    public static final int SLIDEIN_RIGHT = BaseQuickAdapter.SLIDEIN_RIGHT;

    public static final int RESOURCE_NULL = -1;
    //RecyclerView在layout里面的id
    @IdRes
    private int recyclerViewId;
    //这个和#{recyclerViewId}两者有一个即可，recycleView优先级高
    private RecyclerView recyclerView;
    //是否支持加载更多
    private boolean canLoadMore;
    //加载更多的View
    private LoadMoreView loadMoreView;
    //列表加载动画
    private int animationType;
    //是否有分割线
    private boolean hasDivider;
    //分割线的颜色
    @ColorInt
    private int dividerColor;
    //分割线的高度
    private float dividerHeightDp;
    //recycleView的背景色
    @ColorInt
    private int backgroundColor;
    //自定义Divider
    private RecyclerView.ItemDecoration customItemDecoration;

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getAnimationType() {
        return animationType;
    }

    public boolean isHasDivider() {
        return hasDivider;
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public float getDividerHeightDp() {
        return dividerHeightDp;
    }

    public int getRecyclerViewId() {
        return recyclerViewId;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public boolean isCanLoadMore() {
        return canLoadMore;
    }

    public LoadMoreView getLoadMoreView() {
        return loadMoreView;
    }

    public RecyclerView.ItemDecoration getCustomItemDecoration() {
        return customItemDecoration;
    }

    public static class Builder implements Cloneable {
        //RecyclerView在layout里面的id
        @IdRes
        private int recyclerViewId = RESOURCE_NULL;
        //是否支持加载更多
        private boolean canLoadMore = false;
        //当前的RecycleView
        private RecyclerView recyclerView;
        //加载更多的View
        private LoadMoreView loadMoreView;
        //列表加载动画
        private int animationType = SLIDEIN_BOTTOM;
        //是否有分割线
        private boolean hasDivider = true;
        //分割线的颜色
        @ColorInt
        private int dividerColor = Color.BLACK;
        //分割线的高度
        private float dividerHeightDp = 1.0f;
        @ColorInt
        private int backgroundColor = RESOURCE_NULL;
        //自定义Divider
        private RecyclerView.ItemDecoration customItemDecoration;

        public Builder animationType(int animationType) {
            this.animationType = animationType;
            return this;
        }

        public Builder hasDivider(boolean hasDivider) {
            this.hasDivider = hasDivider;
            return this;
        }

        public Builder dividerColor(@ColorInt int dividerColor){
            this.dividerColor = dividerColor;
            return this;
        }

        public Builder dividerHeightDp(float dividerHeightDp) {
            this.dividerHeightDp = dividerHeightDp;
            return this;
        }

        public Builder recyclerViewId(@IdRes int recyclerViewId) {
            this.recyclerViewId = recyclerViewId;
            return this;
        }

        public Builder recyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        public Builder loadMoreView(LoadMoreView loadMoreView) {
            this.loadMoreView = loadMoreView;
            return this;
        }

        public Builder canLoadMore(boolean canLoadMore) {
            this.canLoadMore = canLoadMore;
            return this;
        }

        public Builder backgroundColor(@ColorInt int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder customItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
            this.hasDivider = true;
            this.customItemDecoration = itemDecoration;
            return this;
        }

        public ListConfig build() {
            ListConfig listConfig = new ListConfig();
            listConfig.recyclerViewId = recyclerViewId;
            listConfig.canLoadMore = canLoadMore;
            listConfig.recyclerView = recyclerView;
            listConfig.loadMoreView = loadMoreView;
            listConfig.hasDivider = hasDivider;
            listConfig.dividerColor = dividerColor;
            listConfig.dividerHeightDp = dividerHeightDp;
            listConfig.animationType = animationType;
            listConfig.backgroundColor = backgroundColor;
            listConfig.customItemDecoration = customItemDecoration;
            return listConfig;
        }

        @Override
        public Builder clone(){
            try {
                Builder cloneBuilder = ((Builder) super.clone());
                //将recycler和id重置
                cloneBuilder.recyclerViewId = RESOURCE_NULL;
                cloneBuilder.recyclerView = null;
                return cloneBuilder;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return new Builder().canLoadMore(this.canLoadMore)
                    .loadMoreView(this.loadMoreView)
                    .hasDivider(this.hasDivider)
                    .dividerHeightDp(this.dividerHeightDp)
                    .dividerColor(this.dividerColor)
                    .animationType(this.animationType)
                    .backgroundColor(this.backgroundColor)
                    .customItemDecoration(customItemDecoration);
        }

    }
}
