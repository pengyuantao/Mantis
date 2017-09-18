package com.peng.mantis.present;

import android.os.Bundle;
import android.util.Log;

import com.peng.library.mantis.expand.data.MantisDataPresent;
import com.peng.mantis.contract.TestHintContract;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 测试提示框架的Present
 * Created by pyt on 2017/6/26.
 */

public class TestHintPresent extends MantisDataPresent<TestHintContract.View, String> implements TestHintContract.Present {

    private int reloadIndex = 0;

    @Override
    public void onCreate(TestHintContract.View view, Bundle data) {
        super.onCreate(view, data);
    }

    @Override
    public void onCreateView(TestHintContract.View view) {
        super.onCreateView(view);
        //由于其他网络请求或则是耗时操作，先显示ProgressView
        getViewDelegate().showProgressView();
        //2s后显示emptyView
        createCommonObservable("", 1).subscribe(s -> getViewDelegate().showEmptyView());
    }


    @Override
    public void reload() {
        reloadIndex++;
        Log.i(TAG, "reload: index [ " + reloadIndex + " ]");
        if ( reloadIndex == 1) {
            createCommonObservable("", 1).subscribe(s -> getViewDelegate().showNetworkErrorView());
            Log.i(TAG, "reload:显示网络异常View");
        } else if ( reloadIndex == 2) {
            createCommonObservable("", 1) .subscribe(s -> getViewDelegate().showErrorView("十分的抱歉", "由于攻城狮GG的失误，导致程序的奔溃，截此图奖励9999999元"));
            Log.i(TAG, "reload: 显示程序错误View");
        }else {
            createCommonObservable("恭喜恭喜，终于显示ContentView了，来自不易啊!", 1).subscribe(s -> publishData(s), throwable -> publishError(throwable));
            Log.i(TAG, "reload: 显示内容View");
            reloadIndex = 0;
        }
    }

    /**
     * @param text
     * @param delay
     * @return
     */
    private Observable<String> createCommonObservable(String text, int delay){
        return Observable.<String>create(subscriber -> subscriber.onNext(text))
                .delay(delay, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
