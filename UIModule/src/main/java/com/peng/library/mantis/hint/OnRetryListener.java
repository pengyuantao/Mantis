package com.peng.library.mantis.hint;

/**
 * Created by pyt on 2017/6/18.
 */

public interface OnRetryListener {

    /**
     * 进行重试的方法
     * @param type
     * @param tag
     */
    void onRetry (int type, Object tag);
}
