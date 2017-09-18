package com.peng.library.mantis.mvp;

/**
 * MVP模式里面所有View的顶层接口
 * Created by pyt on 2017/6/16.
 */

public interface IView<PRESENT extends IPresent> {

    PRESENT getPresent ();

    IViewDelegate getViewDelegate ();

}
