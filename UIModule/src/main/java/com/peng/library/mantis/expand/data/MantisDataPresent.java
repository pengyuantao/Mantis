package com.peng.library.mantis.expand.data;

import com.peng.library.mantis.expand.MantisBasePresent;

import rx.Subscriber;
import rx.subjects.BehaviorSubject;

/**
 * 单一数据的Present
 * Created by pyt on 2017/6/22.
 */

public abstract class MantisDataPresent<VIEW extends MantisDataContract.View,DATA> extends MantisBasePresent<VIEW> implements MantisDataContract.Present<VIEW,DATA>{

    //对初始化时候传入数据的处理
    private final BehaviorSubject<DATA> behaviorSubject = BehaviorSubject.create();

    //操作View的Subscriber,通过它操作当前View中的数据
    private final Subscriber<DATA> viewSubscriber = new Subscriber<DATA>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            view.onReceiveError(e);
        }

        @Override
        public void onNext(DATA data) {
            view.onReceiveData(data);
        }
    };


    @Override
    public void onCreateView(VIEW view) {
        super.onCreateView(view);
        DATA dataFromIntent = getDataFromIntent();
        if (dataFromIntent != null) {
            behaviorSubject.onNext(dataFromIntent);
        }
        //绑定当前的生命周期并且开始进行订阅(在onDestroyView的时候取消订阅)
        behaviorSubject.compose(bindToLifecycle()).subscribe(viewSubscriber);
    }

    /**
     * 向View层发送Data
     * @param data
     */
    protected final void publishData(DATA data) {
        viewSubscriber.onNext(data);
    }

    /**
     * 向View层发送Error
     * @param error
     */
    protected final void publishError(Throwable error) {
        viewSubscriber.onError(error);
    }

    /**
     * 如果从其他页面跳转过来的，那么可以重写这个方法
     * 只要数据不为null，那么就会自动发布了
     * @return
     */
    protected DATA getDataFromIntent(){
        return null;
    }


}
