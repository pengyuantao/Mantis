package com.peng.library.mantis.model;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * 使用Handler的机制，使用一个指定的子线程执行耗时操作
 * Created by Mr.Jude on 2015/7/26.
 */
class BackThread extends HandlerThread {

    private Handler handler;

    public BackThread(String name) {
        super(name);
    }

    @Override
    public synchronized void start() {
        super.start();
        if (handler == null) {
            handler = new Handler(getLooper());
        }
    }

    public void execute(Runnable runnable) {
        handler.post(runnable);
    }

}
