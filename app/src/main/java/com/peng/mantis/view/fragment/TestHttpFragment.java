package com.peng.mantis.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.peng.library.mantis.expand.data.MantisDataFragment;
import com.peng.library.mantis.hint.HintConfig;
import com.peng.library.mantis.mvp.BindPresent;
import com.peng.mantis.R;
import com.peng.mantis.contract.TestHttpContract;
import com.peng.mantis.present.TestHttpPresent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pyt on 2017/7/5.
 */
@BindPresent(TestHttpPresent.class)
public class TestHttpFragment extends MantisDataFragment<TestHttpContract.Present, String> implements TestHttpContract.View {

    private static final String TAG = "TestHttpFragment";

    @BindView(R.id.btn_post)
    Button btnPost;
    @BindView(R.id.btn_get)
    Button btnGet;
    @BindView(R.id.tv_response)
    TextView tvResponse;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, HintConfig.Builder builder) {
        View contentView = inflater.inflate(R.layout.fragment_test_http, container, false);
        ButterKnife.bind(this, contentView);
        return contentView;
    }

    @Override
    public void onReceiveData(String s) {
        getViewDelegate().showContentView();
        tvResponse.setText(com.alibaba.fastjson.JSON.toJSONString(s));
    }

    @Override
    public void onReceiveError(Throwable error) {
        error.printStackTrace();
        getViewDelegate().showContentView();
        Log.i(TAG, "onReceiveError: "+error);
        tvResponse.setText("发生错误：" + error);
    }


    @OnClick({R.id.btn_post, R.id.btn_get})
    public void onClick(View view) {
        getViewDelegate().showProgressView();
        switch (view.getId()) {
            case R.id.btn_post:
                getPresent().startPostRequest();
                break;
            case R.id.btn_get:
                getPresent().startGetRequest();
                break;
        }
    }
}
