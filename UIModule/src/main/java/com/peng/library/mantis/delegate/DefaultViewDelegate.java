package com.peng.library.mantis.delegate;


import com.peng.library.mantis.hint.HintManager;
import com.peng.library.mantis.kit.MLog;
import com.peng.library.mantis.mvp.IViewDelegate;

/**
 * Created by pyt on 2017/6/21.
 */

public class DefaultViewDelegate implements IViewDelegate {

    private static final String TAG = "DefaultViewDelegate";

    private HintManager hintManager;

    public DefaultViewDelegate(HintManager hintManager) {
        this.hintManager = hintManager;
        if (hintManager == null) {//不正确
            MLog.i( "DefaultViewDelegate: HintConfig.Builder of config is incorrect, the viewDelegate is useLess!");
        }
    }

    public DefaultViewDelegate setHintManager(HintManager hintManager) {
        this.hintManager = hintManager;
        return this;
    }

    @Override
    public void showErrorView(String title, String content) {
        if (hintManager != null) {
            hintManager.showErrorView(title, content);
        } else {
            MLog.i(  "showErrorView: null");
        }
    }

    @Override
    public void showNetworkErrorView() {
        if (hintManager != null) {
            hintManager.showNetworkErrorView();
        }else {
            MLog.i( "showNetworkErrorView: null");
        }
    }

    @Override
    public void showEmptyView() {
        if (hintManager != null) {
            hintManager.showEmptyView();
        }else {
            MLog.i( "showEmptyView: null");
        }
    }

    @Override
    public void showProgressView() {
        if (hintManager != null) {
            hintManager.showProgressView();
        }else {
            MLog.i(  "showProgressView: null");
        }
    }

    @Override
    public void showContentView() {
        if (hintManager != null) {
            hintManager.showContentView();
        }else {
            MLog.i( "showContentView: null");
        }
    }

    @Override
    public void showErrorView(String title, String content, Object tag) {
        if (hintManager != null) {
            hintManager.showErrorView(title, content, tag);
        }else {
            MLog.i( "showErrorView: null");
        }
    }

    @Override
    public void showNetworkErrorView(Object tag) {
        if (hintManager != null) {
            hintManager.showNetworkErrorView( tag);
        }else {
            MLog.i( "showNetworkErrorView: null");
        }
    }

    @Override
    public void showEmptyView(Object tag) {
        if (hintManager != null) {
            hintManager.showEmptyView( tag);
        }else {
            MLog.i( "showEmptyView: null");
        }
    }



}
