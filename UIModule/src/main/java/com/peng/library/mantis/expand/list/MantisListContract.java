package com.peng.library.mantis.expand.list;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.peng.library.mantis.expand.list.link.ListConfig;
import com.peng.library.mantis.expand.refresh.MantisRefreshContract;
import com.peng.library.mantis.mvp.IPresent;
import com.peng.library.mantis.mvp.IView;

/**
 * 列表对应的协议(这个协议，继承刷新的协议)
 * Created by pyt on 2017/6/29.
 */
public interface MantisListContract {

    interface View<PRESENT extends IPresent,DATA> extends MantisRefreshContract.View<PRESENT> {
        void onConvert (BaseViewHolder holder, DATA data, int position);

        void onReceiveError (Throwable throwable);
    }

    interface Present<VIEW extends IView,DATA> extends MantisRefreshContract.Present<VIEW> {
        BaseQuickAdapter getAdapter ();

        void bindRecyclerView (RecyclerView recyclerView, @LayoutRes int itemLayout, ListConfig listConfig);
    }
}
