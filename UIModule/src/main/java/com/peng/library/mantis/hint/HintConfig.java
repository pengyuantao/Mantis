package com.peng.library.mantis.hint;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Created by pyt on 2017/6/18.
 */

public class HintConfig {

    public static final int NO_LAYOUT_ID = -1;

    private int layoutEmptyId = NO_LAYOUT_ID;

    private int layoutProgressId = NO_LAYOUT_ID;

    private int layoutErrorId = NO_LAYOUT_ID;

    private int layoutNetworkErrorId = NO_LAYOUT_ID;

    private View contentView;

    private OnRetryListener onRetryListener;

    private OnShowErrorViewListener onShowErrorViewListener;

    private boolean hintEnable;

    public int getLayoutEmptyId() {
        return layoutEmptyId;
    }

    public int getLayoutProgressId() {
        return layoutProgressId;
    }

    public int getLayoutErrorId() {
        return layoutErrorId;
    }

    public int getLayoutNetworkErrorId() {
        return layoutNetworkErrorId;
    }

    public View getContentView() {
        return contentView;
    }

    public OnRetryListener getOnRetryListener() {
        return onRetryListener;
    }

    public OnShowErrorViewListener getOnShowErrorViewListener() {
        return onShowErrorViewListener;
    }

    public boolean isHintEnable() {
        return hintEnable;
    }

    /**
     * HontConfig构建器
     */
    public static class Builder implements Cloneable{

        private int layoutEmptyId = NO_LAYOUT_ID;

        private int layoutProgressId = NO_LAYOUT_ID;

        private int layoutErrorId = NO_LAYOUT_ID;

        private int layoutNetworkErrorId = NO_LAYOUT_ID;

        private View contentView;

        private OnRetryListener onRetryListener;

        private OnShowErrorViewListener onShowErrorViewListener;

        private boolean hintEnable = true;

        public Builder layoutEmptyId(@LayoutRes int layoutId) {
            this.layoutEmptyId = layoutId;
            return this;
        }

        public Builder layoutProgressId(@LayoutRes int layoutId) {
            this.layoutProgressId = layoutId;
            return this;
        }

        public Builder layoutErrorId(@LayoutRes int layoutId) {
            this.layoutErrorId = layoutId;
            return this;
        }

        public Builder layoutNetworkErrorId(@LayoutRes int layoutId) {
            this.layoutNetworkErrorId = layoutId;
            return this;
        }

        public Builder contentView(View view) {
            this.contentView = view;
            return this;
        }

        public Builder retryListener(OnRetryListener retryListener) {
            this.onRetryListener = retryListener;
            return this;
        }

        public Builder showErrorViewListener(OnShowErrorViewListener showErrorViewListener) {
            this.onShowErrorViewListener = showErrorViewListener;
            return this;
        }

        public Builder hintEnable(boolean hintEnable) {
            this.hintEnable = hintEnable;
            return this;
        }

        public HintConfig build() {
            HintConfig hintConfig = new HintConfig();
            hintConfig.layoutNetworkErrorId = layoutNetworkErrorId;
            hintConfig.layoutEmptyId = layoutEmptyId;
            hintConfig.layoutErrorId = layoutErrorId;
            hintConfig.layoutProgressId = layoutProgressId;
            hintConfig.onRetryListener = onRetryListener;
            hintConfig.onShowErrorViewListener = onShowErrorViewListener;
            hintConfig.contentView = contentView;
            hintConfig.hintEnable = hintEnable;
            return hintConfig;
        }

        @Override
        public Builder clone() {
            try {
                Builder cloneBuilder = (Builder) super.clone();
                //去除contentView和onRetryListener引用
                cloneBuilder.contentView = null;
                cloneBuilder.onRetryListener = null;
                return cloneBuilder;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            //如果克隆失败，那么就自己创建
            return new Builder()
                    .layoutProgressId(layoutProgressId)
                    .layoutNetworkErrorId(layoutNetworkErrorId)
                    .layoutErrorId(layoutErrorId)
                    .layoutEmptyId(layoutEmptyId)
                    .showErrorViewListener(onShowErrorViewListener)
                    .hintEnable(hintEnable);
        }
    }

}
