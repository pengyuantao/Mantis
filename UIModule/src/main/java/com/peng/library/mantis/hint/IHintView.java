package com.peng.library.mantis.hint;

/**
 * 对所支持显示View的抽象,将IViewDeleget的抽象拿过来，这个将来可以单独作为一个SDK
 * Created by pyt on 2017/6/18.
 */

public interface IHintView {

    /**
     * 显示错误的界面（可重试）
     * @param title
     * @param content
     */
    void showErrorView (String title, String content);

    /**
     * 显示网络错误的界面（可重试）
     */
    void showNetworkErrorView ();

    /**
     * 显示空的界面（可重试）
     */
    void showEmptyView ();

    /**
     * 显示进度界面
     */
    void showProgressView ();

    /**
     * 显示当前的内容View
     */
    void showContentView ();

    /**
     * 显示错误的界面（可重试）
     * @param title
     * @param content
     */
    void showErrorView (String title, String content, Object tag);

    /**
     * 显示网络错误的界面（可重试）
     */
    void showNetworkErrorView (Object tag);

    /**
     * 显示空的界面（可重试）
     */
    void showEmptyView (Object tag);
}
