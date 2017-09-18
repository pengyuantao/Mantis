package com.peng.library.mantis.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;

import rx.Observable;
import rx.subjects.BehaviorSubject;


/**

 * Created by pyt on 2017/6/17.
 */
public abstract class MantisPresent<V extends IView> implements IPresent<V>, LifecycleProvider<PresentEvent> {

    protected V view;

    private final BehaviorSubject<PresentEvent> lifecycleSubject = BehaviorSubject.create();

    /**
     * Rxjava的调用绑定Present的生命周期
     * 说明: 如果你调用bindToLifecycle()出于onCreate状态，那么在onDestroy状态
     * 就会完成这种绑定状态，自动调用onComplete()方法完成解绑的动作
     *
     * @param <T>
     * @return
     */
    @NonNull
    @Override
    @CheckResult
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifePresent.bindPresent(lifecycleSubject);
    }

    @NonNull
    @Override
    public Observable<PresentEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    /**
     * 指定生命周期进行解绑(如果到了绑定生命周期方法的执行，那么如果还没有取消绑定，那么这里会自动完成，然后取消绑定)
     * @param event
     * @param <T>
     * @return
     */
    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@NonNull PresentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    public void onCreate(V view,Bundle data) {
        lifecycleSubject.onNext(PresentEvent.CREATE);
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(PresentEvent.DESTROY);
    }

    @Override
    public void onCreateView(V view) {
        this.view = view;
        lifecycleSubject.onNext(PresentEvent.CREATE_VIEW);
    }

    protected V getView(){
        return view;
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(PresentEvent.DESTROY_VIEW);
    }

    @Override
    public void onStart() {
        lifecycleSubject.onNext(PresentEvent.START);
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(PresentEvent.STOP);
    }

    @Override
    public void onResume() {
        lifecycleSubject.onNext(PresentEvent.RESUME);
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(PresentEvent.PAUSE);
    }

    @Override
    public void onSave(Bundle data) {

    }

    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {

    }
}
