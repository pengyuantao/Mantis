package com.peng.mantis.view.fragment;

import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peng.library.mantis.expand.data.MantisDataFragment;
import com.peng.library.mantis.hint.HintConfig;
import com.peng.library.mantis.mvp.BindPresent;
import com.peng.mantis.R;
import com.peng.mantis.contract.TestHintContract;
import com.peng.mantis.present.TestHintPresent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 测试提示框架的Fragment
 * Created by pyt on 2017/6/26.
 */
@BindPresent(value = TestHintPresent.class)
public class TestHintFragment extends MantisDataFragment<TestHintContract.Present, String> implements TestHintContract.View {

    private static final String TAG = "TestHintFragment";

    @BindView(R.id.tv_content)
    TextView tvContent;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, HintConfig.Builder builder) {
        View contentView = inflater.inflate(R.layout.fragment_test_hint, container, false);
        Log.i(TAG, "onCreateView: "+contentView.getParent());
        //必须指定Fragment和View
        ButterKnife.bind(this,contentView);
        return contentView;
    }

    @Override
    public void onReceiveData(String s) {
        getViewDelegate().showContentView();
        tvContent.setText(s);
    }

    @Override
    public void onReceiveError(Throwable error) {
        if (error instanceof NetworkErrorException) {
            getViewDelegate().showNetworkErrorView();
        }else {
            getViewDelegate().showErrorView("抱歉我们的程序发生错误", error.getMessage());
        }
    }

    @Override
    public void onRetry(int type,Object tag) {
        getViewDelegate().showProgressView();
       getPresent().reload();
    }
}
