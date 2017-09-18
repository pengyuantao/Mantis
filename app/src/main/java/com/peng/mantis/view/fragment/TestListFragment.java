package com.peng.mantis.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.peng.library.mantis.expand.list.MantisListFragment;
import com.peng.library.mantis.expand.list.link.ListConfig;
import com.peng.library.mantis.expand.refresh.link.RefreshConfig;
import com.peng.library.mantis.hint.HintConfig;
import com.peng.library.mantis.mvp.BindPresent;
import com.peng.mantis.R;
import com.peng.mantis.contract.TestListContract;
import com.peng.mantis.present.TestListPresent;

/**
 * Created by pyt on 2017/6/29.
 */
@BindPresent(TestListPresent.class)
public class TestListFragment extends MantisListFragment<TestListContract.Present,String> implements TestListContract.View {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, HintConfig.Builder HintConfigBuilder, RefreshConfig.Builder refreshConfigBuilder, ListConfig.Builder listConfigBuilder) {
        listConfigBuilder.canLoadMore(true).hasDivider(false);
        return super.onCreateView(inflater, container, savedInstanceState, HintConfigBuilder, refreshConfigBuilder, listConfigBuilder);
    }

    @Override
    public void onConvert(BaseViewHolder holder, String s, int position) {
        ImageView imageView = holder.getView(R.id.imageView);
        Glide.with(getContext()).load(s).placeholder(R.mipmap.ic_launcher_round).centerCrop().into(imageView);
    }

    @Override
    public void onReceiveError(Throwable throwable) {

    }

    @Override
    public void onRetry(int type, Object tag) {
        super.onRetry(type, tag);
        getViewDelegate().showContentView();
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_list_test_list;
    }
}
