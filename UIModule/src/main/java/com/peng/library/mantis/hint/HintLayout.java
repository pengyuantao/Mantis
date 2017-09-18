package com.peng.library.mantis.hint;

import android.content.Context;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.peng.library.mantis.R;


/**
 * Created by pyt on 2017/6/18.
 */

public class HintLayout extends FrameLayout implements View.OnClickListener {

    private static final String TAG = "HintLayout";

    public static final int INDEX_CONTENT = 0;

    public static final int INDEX_EMPTY = 1;

    public static final int INDEX_PROGRESS = 2;

    public static final int INDEX_ERROR = 3;

    public static final int INDEX_NETWORK_ERROR = 4;

    private LayoutInflater layoutInflater;

    private HintConfig hintConfig;

    //数组缓存所有需要操作的view(contentView,emptyView,progressView,errorView,networkErrorView)
    private View[] hintViews = new View[5];

    private int curShowIndex = -1;

    public HintLayout(@NonNull Context context) {
        this(context, null);
    }

    public HintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HintLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        layoutInflater = LayoutInflater.from(context);
    }

    public void init(HintConfig hintConfig) {
        this.hintConfig = hintConfig;
        if (hintConfig.getContentView() == null) {
            throw new IllegalArgumentException("the content view must not null!");
        }
        initAllHintView(hintConfig);
        initRetryEvent(INDEX_EMPTY, INDEX_ERROR, INDEX_NETWORK_ERROR);
    }

    /**
     * 初始化所有的需要重试的View
     *
     * @param indexOfView
     */
    private void initRetryEvent(int... indexOfView) {
        if (indexOfView == null) {
            return;
        }
        for (int i = 0; i < indexOfView.length; i++) {
            View hintView = this.hintViews[indexOfView[i]];
            if (hintView != null) {
                View clickableView = hintView.findViewById(R.id.hint_retry);
                if (clickableView != null) {
                    clickableView.setTag(indexOfView[i]);
                    clickableView.setOnClickListener(this);
                }
            }
        }
    }


    private void initAllHintView(HintConfig hintConfig) {
        if (hintConfig.getContentView() != null) {
            hintViews[0] = hintConfig.getContentView();
        } else {//包含
            throw new IllegalArgumentException("Check you HintConfig,HintLayout must include contentView!");
        }
        addView(hintViews[0]);
        //设置当前显示View的index
        curShowIndex = 0;
        int[] layoutIds = new int[]{hintConfig.getLayoutEmptyId(), hintConfig.getLayoutProgressId(),
                hintConfig.getLayoutErrorId(), hintConfig.getLayoutNetworkErrorId()};
        for (int i = 0; i < layoutIds.length; i++) {
            int layoutId = layoutIds[i];
            if (layoutId != HintConfig.NO_LAYOUT_ID) {
                View view = layoutInflater.inflate(layoutId, this, false);
                addView(view);
                view.setVisibility(View.GONE);
                this.hintViews[i + 1] = view;
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            int childWidthSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
            int childHeightSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.EXACTLY);
            getChildAt(i).measure(childWidthSpec, childHeightSpec);
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    public void showContentView() {
        showView(INDEX_CONTENT);
    }

    public void showEmptyView() {
        showView(INDEX_EMPTY);
    }

    public void showProgressView() {
        showView(INDEX_PROGRESS);
    }

    public void showErrorView(String title, String content) {
        View errorView = showView(INDEX_ERROR);
        if (errorView != null && this.hintConfig.getOnShowErrorViewListener() != null) {
            hintConfig.getOnShowErrorViewListener().onShow(errorView, title, content);
        }
    }

    public void showNetworkErrorView(Object tag) {
        showView(INDEX_NETWORK_ERROR, tag);
    }

    public void showEmptyView(Object tag) {
        showView(INDEX_EMPTY, tag);
    }

    public void showErrorView(String title, String content, Object tag) {
        View errorView = showView(INDEX_ERROR, tag);
        if (errorView != null && this.hintConfig.getOnShowErrorViewListener() != null) {
            hintConfig.getOnShowErrorViewListener().onShow(errorView, title, content);
        }
    }

    public void showNetworkErrorView() {
        showView(INDEX_NETWORK_ERROR);
    }

    private void showViewInMainThread(View view, int index) {
        if (view != null && index != curShowIndex) {
            view.setVisibility(View.VISIBLE);
            this.hintViews[curShowIndex].setVisibility(GONE);
            curShowIndex = index;
        }
    }

    private void showViewNotInMainThread(final View view, final int index) {
        post(new Runnable() {
            @Override
            public void run() {
                showViewInMainThread(view, index);
            }
        });
    }

    private View showView(int index) {
        return showView(index, null);
    }

    /**
     * 显示View的方法
     *
     * @param index
     */
    private View showView(int index, Object tag) {
        View hintView = hintViews[index];
        hintView.setTag(tag);
        if (index == curShowIndex) {
            return hintView;
        }
        if (isMainThread()) {
            showViewInMainThread(hintView, index);
        } else {
            showViewNotInMainThread(hintView, index);
        }
        return hintView;
    }

    @Override
    public void onClick(View v) {
        if (hintConfig.getOnRetryListener() != null) {
            Integer index = (Integer) v.getTag();
            int viewIndex = index.intValue();
            Object tag = hintViews[viewIndex].getTag();
            hintConfig.getOnRetryListener().onRetry(viewIndex, tag);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        return new HintState(curShowIndex);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        showView(curShowIndex);
    }

    /**
     * 获取当前显示View的角标
     *
     * @return
     */
    public int getCurShowIndex() {
        return curShowIndex;
    }

    /**
     * 获取当前显示View的文字描述
     *
     * @return
     */
    public String getCurShowViewText() {
        return indexToViewName(curShowIndex);
    }

    public String indexToViewName(int index) {
        if (index == INDEX_CONTENT) {
            return "Context View";
        } else if (index == INDEX_EMPTY) {
            return "Empty View";
        } else if (index == INDEX_ERROR) {
            return "Error View";
        } else if (index == INDEX_NETWORK_ERROR) {
            return "Network Error View";
        } else if (index == INDEX_PROGRESS) {
            return "Progress View";
        } else {
            return "un know View";
        }
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static class HintState implements Parcelable {

        private int curShowIndex;

        public HintState(int curShowIndex) {
            this.curShowIndex = curShowIndex;
        }


        protected HintState(Parcel in) {
            curShowIndex = in.readInt();
        }

        public static final Creator<HintState> CREATOR = new Creator<HintState>() {
            @Override
            public HintState createFromParcel(Parcel in) {
                return new HintState(in);
            }

            @Override
            public HintState[] newArray(int size) {
                return new HintState[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(curShowIndex);
        }

        /**
         * 获取当前显示View的index
         *
         * @return
         */
        public int getCurShowViewIndex() {
            return curShowIndex;
        }

    }

}
