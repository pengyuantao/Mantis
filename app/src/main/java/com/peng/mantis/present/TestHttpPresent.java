package com.peng.mantis.present;

import android.os.Bundle;

import com.peng.library.mantis.expand.data.MantisDataPresent;
import com.peng.library.mantis.mvp.PresentEvent;
import com.peng.mantis.contract.TestHttpContract;
import com.peng.mantis.model.http.ApiClient;
import com.peng.mantis.model.http.ApiConstants;
import com.peng.mantis.model.http.service.MantisNetApi;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by pyt on 201/7/5.
 */

public class TestHttpPresent extends MantisDataPresent<TestHttpContract.View,String> implements TestHttpContract.Present {

    private static final String TAG = "TestHttpPresent";

    private MantisNetApi mantisNetApi;

    @Override
    public void onCreate(TestHttpContract.View view, Bundle data) {
        super.onCreate(view, data);
        mantisNetApi = ApiClient.createApiService(ApiConstants.API_BENGUO_NET_URL, MantisNetApi.class);
    }

    @Override
    public void startGetRequest() {
//        mantisNetApi.getBaiduImage("明星")
//                .compose(bindUntilEvent(PresentEvent.DESTROY))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(baiDuImage -> this.publishData(baiDuImage), throwable -> publishError(throwable));

        ApiClient.createApiService(ApiConstants.API_BENGUO_NET_URL, MantisNetApi.class).getOrderList()
                .compose(bindUntilEvent(PresentEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(string -> this.publishData(string), throwable -> publishError(throwable));
    }

    @Override
    public void startPostRequest() {
//        mantisNetApi.getBaiduImagePost(0, 50, "美女", "全部", 0, "utf-8", "utf-8")
//                .compose(bindUntilEvent(PresentEvent.DESTROY))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(baiDuImage -> publishData(baiDuImage), throwable -> publishError(throwable));

        mantisNetApi.get12306Request()
                .compose(bindUntilEvent(PresentEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baiDuImage -> publishData(baiDuImage), throwable -> publishError(throwable));
    }


}
