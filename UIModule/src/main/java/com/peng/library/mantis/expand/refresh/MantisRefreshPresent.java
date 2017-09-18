package com.peng.library.mantis.expand.refresh;

import com.peng.library.mantis.expand.MantisBasePresent;

/**
 * 使用刷新的Present
 * Created by pyt on 2017/6/27.
 */

public abstract class MantisRefreshPresent<VIEW extends MantisRefreshContract.View> extends MantisBasePresent<VIEW> implements MantisRefreshContract.Present<VIEW> {

    /**
     * 将属性完成的方法代理过来
     */
    protected void refreshComplete(){
        getView().refreshComplete();
    }

}
