package com.peng.library.mantis.expand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peng.library.mantis.Mantis;
import com.peng.library.mantis.delegate.DefaultViewDelegate;
import com.peng.library.mantis.hint.HintConfig;
import com.peng.library.mantis.hint.HintManager;
import com.peng.library.mantis.hint.OnRetryListener;
import com.peng.library.mantis.mvp.IPresent;
import com.peng.library.mantis.mvp.IViewDelegate;
import com.peng.library.mantis.mvp.MantisFragment;


/**
 * 这个Fragment主要进行包装，如显示ErrorView，EmptyView，ProgressView,RetryView
 * Created by pyt on 2017/6/18.
 */

public abstract class MantisBaseFragment<P extends IPresent> extends MantisFragment<P> implements OnRetryListener {

    private static final String TAG = "MantisBaseFragment";

    //提示管理类
    private HintManager hintManager;

    private DefaultViewDelegate defaultViewDelegate = new DefaultViewDelegate(hintManager);


    @Override
    public IViewDelegate getViewDelegate() {
        return defaultViewDelegate.setHintManager(hintManager);
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HintConfig.Builder builder = Mantis.getInstance().getHintConfigBuilder();
        View view = onCreateView(inflater, null, savedInstanceState,builder);
        HintConfig hintConfig = builder.build();
        //校验是否支持提示
        if (!hintConfig.isHintEnable()) {
            return view;
        }
        //支持HintConfig
        if (view!=null&&builder != null){
            builder.retryListener(this);
            hintManager = new HintManager(builder);
            return hintManager.bindFragment(view);
        }
        return view;
    }

    /**
     * 给子类复写的方法
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState,HintConfig.Builder hintConfigBuilder);

    @Override
    public void onRetry(int type, Object tag) {

    }
}
