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
import com.peng.mantis.R;
import com.peng.mantis.contract.TestRepositoryContract;
import com.peng.mantis.model.db.entity.TodayWeather;
import com.peng.mantis.present.TestRepositoryPresent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by pyt on 2017/7/11.
 */
@BindPresent(TestRepositoryPresent.class)
public class TestRepositoryFragment extends MantisRefreshFragment<TestRepositoryContract.Present> implements TestRepositoryContract.View {


    private static final String TAG = "TestRepositoryFragment";

    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_templature)
    TextView tvTemplature;
    @BindView(R.id.tv_wind_level)
    TextView tvWindLevel;
    @BindView(R.id.tv_weather)
    TextView tvWeather;
    @BindView(R.id.tv_cache_time)
    TextView tvCacheTime;
    private Unbinder bind;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @Override
    public void setTodayWeather(TodayWeather todayWeather) {
        Log.d(TAG, "setTodayWeather() called with: todayWeather = [" + todayWeather + "]");
        tvCity.setText(todayWeather.getCity());
        tvTemplature.setText(todayWeather.getHigh());
        tvCacheTime.setText(String.valueOf(todayWeather.getCacheTime()));
        tvWeather.setText(todayWeather.getType());
        tvWindLevel.setText(todayWeather.getFengli());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, HintConfig.Builder hintBuilder, RefreshConfig.Builder refreshBuilder) {
        refreshBuilder.autoRefresh(true);
        View contentView = inflater.inflate(R.layout.fragment_test_repository, container, false);
        bind = ButterKnife.bind(this,contentView);
        return contentView;
    }
}
