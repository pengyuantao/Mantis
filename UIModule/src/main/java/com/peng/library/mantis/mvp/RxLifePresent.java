package com.peng.library.mantis.mvp;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.util.Log;

import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.OutsideLifecycleException;
import com.trello.rxlifecycle.RxLifecycle;

import rx.Observable;
import rx.functions.Func1;


/**
 * 用于对Present的生命周期的管理，防止在使用Rxjava的时候内存泄露
 * Created by pyt on 2017/6/17.
 */

public class RxLifePresent {

    private static final String TAG = "RxLifePresent";

    // Figures out which corresponding next lifecycle event in which to unsubscribe, for Present
    private static final Func1<PresentEvent, PresentEvent> PRESENT_LIFECYCLE =
            new Func1<PresentEvent, PresentEvent>() {
                @Override
                public PresentEvent call(PresentEvent lastEvent) {
                    Log.i(TAG, "RxLifePresent-->" + lastEvent);
                    switch (lastEvent) {
                        case CREATE:
                            return PresentEvent.DESTROY;
                        case CREATE_VIEW:
                            return PresentEvent.DESTROY_VIEW;
                        case START:
                            return PresentEvent.STOP;
                        case RESUME:
                            return PresentEvent.PAUSE;
                        case PAUSE:
                            return PresentEvent.STOP;
                        case STOP:
                            return PresentEvent.DESTROY;
                        case DESTROY_VIEW:
                            return PresentEvent.DESTROY;
                        case DESTROY:
                            throw new OutsideLifecycleException("Cannot bind to Activity lifecycle when outside of it.");
                        default:
                            throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
                    }
                }
            };


    @NonNull
    @CheckResult
    public static <T> LifecycleTransformer<T> bindPresent(@NonNull final Observable<PresentEvent> lifecycle) {
        return RxLifecycle.bind(lifecycle, PRESENT_LIFECYCLE);
    }


}
