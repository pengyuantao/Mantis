package com.peng.library.mantis.hint;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/**
 * 相关提示View的管理类
 * Created by pyt on 2017/6/18.
 */

public class HintManager implements IHintView {

    private static final String TAG = "HintManager";

    private HintConfig.Builder builder;
    private HintLayout hintLayout;

    public HintManager(HintConfig.Builder builder) {
        this.builder = builder;
        if (builder == null) {
            throw new IllegalArgumentException("the hintConfig must not null!");
        }
    }

    /**
     * 绑定Fragment
     *
     * @param fragmentView
     */
    public View bindFragment(View fragmentView) {
        //fix:Fragment无法remove(由于替换掉了onCreateView导致)
        ViewGroup parent = ((ViewGroup) fragmentView.getParent());
        if (parent != null) {
            parent.removeView(fragmentView);
        }
        hintLayout = new HintLayout(fragmentView.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        hintLayout.setLayoutParams(params);
        this.builder.contentView(fragmentView);
        hintLayout.init(this.builder.build());
        return hintLayout;
    }

    /**
     * 绑定Activity
     *
     * @param activity
     */
    public void bindActivity(Activity activity) {
        bindView(activity.findViewById(android.R.id.content));
    }

    /**
     * 绑定View
     *
     * @param contentView
     */
    public void bindView(View contentView) {
        //找到这个View的父View
        ViewGroup parentView = (ViewGroup) contentView.getParent();
        if (parentView == null) {
            throw new IllegalArgumentException("bindView: the content's parent view is null,binding failure!");
        }
        //获取当前View在ParentView中的位置
        int childCount = parentView.getChildCount();
        int contentViewIndex = 0;
        for (int i = 0; i < childCount; i++) {
            if (parentView.getChildAt(i) == contentView) {
                contentViewIndex = i;
            }
        }
        //获取contentView的LayoutParams，并且进行替换
        hintLayout = new HintLayout(contentView.getContext());
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        parentView.removeView(contentView);
        this.builder.contentView(contentView);
        hintLayout.init(this.builder.build());
        parentView.addView(hintLayout, contentViewIndex, layoutParams);
    }

    @Override
    public void showErrorView(String title, String content) {
        hintLayout.showErrorView(title, content);
    }

    @Override
    public void showNetworkErrorView() {
        hintLayout.showNetworkErrorView();
    }


    @Override
    public void showEmptyView() {
        hintLayout.showEmptyView();
    }

    @Override
    public void showProgressView() {
        hintLayout.showProgressView();
    }

    @Override
    public void showContentView() {
        hintLayout.showContentView();
    }

    @Override
    public void showErrorView(String title, String content, Object tag) {
        hintLayout.showErrorView(title, content, tag);
    }

    @Override
    public void showNetworkErrorView(Object tag) {
        showNetworkErrorView(tag);
    }

    @Override
    public void showEmptyView(Object tag) {
        showEmptyView(tag);
    }
}
