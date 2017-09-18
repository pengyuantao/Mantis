package com.peng.mantis.present;

import android.util.Log;

import com.peng.library.mantis.expand.refresh.MantisRefreshPresent;
import com.peng.library.mantis.kit.MLog;
import com.peng.mantis.contract.TestRefreshContract;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pyt on 2017/6/28.
 */

public class TestRefreshPresent extends MantisRefreshPresent<TestRefreshContract.View> implements TestRefreshContract.Present {

    private static final String TAG = "TestRefreshPresent";
    private Observer<? super String> proxySubscriber = new  Subscriber<String>(){

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            getView().onReceiveError(e);
        }

        @Override
        public void onNext(String o) {
            getView().onReceiveData(o);
        }
    };


    @Override
    public void onCreateView(TestRefreshContract.View view) {
        super.onCreateView(view);
    }

    @Override
    public void giveMeData() {

    }


    /**
     * 获取一个公共的观察者
     *
     * @param text
     * @param delay
     * @return
     */
    private Observable<String> createCommonObservable(String text, int delay) {
        return Observable.<String>create(subscriber -> subscriber.onNext(text))
                .delay(delay, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void onRefreshCall() {
        MLog.i("哈哈哈哈哈哈");
        Log.d(TAG, "giveMeData() called");
        createCommonObservable("终于拉出来了，一共拉了 " + ((int) (Math.random() * 1000)) + " 坨", 2)
                .subscribe(proxySubscriber);
    }
}
