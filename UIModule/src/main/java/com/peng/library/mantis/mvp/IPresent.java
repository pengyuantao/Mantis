package com.peng.library.mantis.mvp;

import android.content.Intent;
import android.os.Bundle;

/**
 * MVP模式里面所有Present的顶层接口
 * Created by pyt on 2017/6/16.
 */

public interface IPresent<VIEW extends IView> {

    /**
     * 这个方法在创建这个Present时候调用，但是View没有完全初始化完毕
     * @param data
     */
    void onCreate (VIEW view, Bundle data);

    /**
     * 当Present被从缓存中移除了
     */
    void onDestroy ();

    /**
     * 当View被创建并且被初始化完毕
     * @param view
     */
    void onCreateView (VIEW view);

    /**
     * 当View被销毁
     */
    void onDestroyView ();


    //所有的生命周期方法
    void onStart ();

    void onStop ();

    void onResume ();

    void onPause ();

    void onSave (Bundle data);

    //调用startActivityResult
    void onResult (int requestCode, int resultCode, Intent data);

}
