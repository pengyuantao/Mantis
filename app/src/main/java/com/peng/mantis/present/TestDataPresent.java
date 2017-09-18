package com.peng.mantis.present;

import android.text.TextUtils;
import android.util.Log;

import com.peng.library.mantis.expand.data.MantisDataPresent;
import com.peng.library.mantis.mvp.PresentEvent;
import com.peng.mantis.contract.TestDataContract;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 测试数据的Present
 * Created by pyt on 2017/6/23.
 */

public class TestDataPresent extends MantisDataPresent<TestDataContract.View, String> implements TestDataContract.Present {

    private static final String TAG = "TestDataPresent";

    @Override
    public void login(String account, String password) {
        if (TextUtils.isEmpty(account)) {
            view.toast("账号为NULL");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            view.toast("密码为NULL");
            return;
        }
        publishData("正在登录。。。。。");
        Observable.<String>create(subscriber -> subscriber.onNext("登录成功  账号：" + account + "  密码：" + password))
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .compose(bindUntilEvent(PresentEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            publishData(s);
                            Log.i(TAG, "login: 执行onNext");
                        }
                        , throwable -> {
                            publishError(throwable);
                            Log.i(TAG, "login: 执行onError");
                        }
                        , () -> {
                            Log.i(TAG, "login: 执行onComplete");
                        });
    }

    @Override
    public void logout() {
        clearData();
    }

    private void clearData(){
        getView().toast("Hello");
    }


}
