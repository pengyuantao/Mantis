package com.peng.mantis.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peng.library.mantis.expand.refresh.MantisRefreshFragment;
import com.peng.library.mantis.expand.refresh.link.RefreshConfig;
import com.peng.library.mantis.hint.HintConfig;
import com.peng.library.mantis.mvp.BindPresent;
import com.peng.library.mantis.mvp.IViewDelegate;
import com.peng.mantis.R;
import com.peng.mantis.contract.TestRefreshContract;
import com.peng.mantis.present.TestRefreshPresent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pyt on 2017/6/28.
 */
@BindPresent(TestRefreshPresent.class)
public class TestRefreshFragment extends MantisRefreshFragment<TestRefreshContract.Present> implements TestRefreshContract.View {

    private static final String TAG = "TestRefreshFragment";

    @BindView(R.id.tv_no_1)
    TextView tvNo1;
    @BindView(R.id.tv_no_2)
    TextView tvNo2;

    @Override
    public void onReceiveData(String s) {
        Log.d(TAG, "onReceiveData() called with: s = [" + s + "]");
        refreshComplete();
        tvNo1.setText(s);
        tvNo2.setText(s);
    }

    @Override
    public void onReceiveError(Throwable error) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onRetry(int type,Object tag) {
        if (type == IViewDelegate.INDEX_EMPTY) {
            getViewDelegate().showContentView();
            autoRefresh();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, HintConfig.Builder hintBuilder, RefreshConfig.Builder refreshBuilder) {
        refreshBuilder.autoRefresh(false);
        return inflater.inflate(R.layout.fragment_test_refresh, container, false);
    }
}
