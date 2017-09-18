package com.peng.mantis.present;

import android.os.Bundle;
import android.util.Log;

import com.peng.library.mantis.expand.data.MantisDataPresent;
import com.peng.library.mantis.expand.refresh.MantisRefreshPresent;
import com.peng.mantis.contract.TestRepositoryContract;
import com.peng.mantis.model.db.entity.TodayWeather;
import com.peng.mantis.model.repository.TodayWeatherRepository;
import com.trello.rxlifecycle.LifecycleTransformer;

import javax.annotation.Nonnull;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pyt on 2017/7/11.
 */

public class TestRepositoryPresent extends MantisRefreshPresent<TestRepositoryContract.View> implements TestRepositoryContract.Present {


    private static final String TAG = "TestRepositoryPresent";

    @Override
    public void onRefreshCall() {
        TodayWeatherRepository.getTodayWeather("北京")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TodayWeather>() {
                    @Override
                    public void onCompleted() {
                        getView().refreshComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().refreshComplete();
                        e.printStackTrace();
                        getViewDelegate().showErrorView("恭喜发生错误了", "废话一堆！！！" + e.getMessage());
                    }

                    @Override
                    public void onNext(TodayWeather todayWeather) {
                        if (todayWeather != null) {
                            getViewDelegate().showContentView();
                            getView().setTodayWeather(todayWeather);
                        } else {
                            getViewDelegate().showErrorView("没有请求到数据", "重试吧！！");
                        }
                    }
                });
    }
}
