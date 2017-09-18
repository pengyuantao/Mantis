package com.peng.library.mantis.expand;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.peng.library.mantis.mvp.IView;
import com.peng.library.mantis.mvp.IViewDelegate;
import com.peng.library.mantis.mvp.MantisPresent;

/**
 *
 * Created by pyt on 2017/6/18.
 */

public  abstract class MantisBasePresent<V extends IView> extends MantisPresent<V> {

    /**
     * 提供Activity对象，用于startActivity和Dialog调用使用
     * @return
     */
    protected Activity getActivity() {
        if (view instanceof Activity) {
            return (Activity) view;
        } else if (view instanceof Fragment) {
            return ((Fragment) view).getActivity();
        }
        throw new RuntimeException("can't found class "+view==null?"null":view.getClass().getName());
    }

    /**
     * 返回一个提示相关的代理类
     * @return
     */
    protected IViewDelegate getViewDelegate(){
        return getView().getViewDelegate();
    }

}
