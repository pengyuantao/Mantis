package com.peng.library.mantis.expand.list;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.peng.library.mantis.expand.list.link.ListConfig;
import com.peng.library.mantis.expand.list.link.RecyclerViewDivider;
import com.peng.library.mantis.expand.refresh.MantisRefreshPresent;
import com.peng.library.mantis.kit.Kits;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscriber;

/**
 * ListPresent
 * Created by pyt on 2017/6/29.
 */

public abstract class MantisListPresent<VIEW extends MantisListContract.View, DATA>
        extends MantisRefreshPresent<VIEW>
        implements MantisListContract.Present<VIEW, DATA>, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemLongClickListener {

    private BaseQuickAdapter<DATA, BaseViewHolder> adapter;

    protected final Subscriber<List<DATA>> listSubscriber = new Subscriber<List<DATA>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            getView().onReceiveError(e);
        }

        @Override
        public void onNext(List<DATA> datas) {
            getView().refreshComplete();
            adapter.disableLoadMoreIfNotFullPage();
            adapter.replaceData(datas);
        }
    };


    @Override
    public BaseQuickAdapter<DATA,BaseViewHolder> getAdapter() {
        return adapter;
    }

    @Override
    public void bindRecyclerView(RecyclerView recyclerView, @LayoutRes int itemLayout, ListConfig listConfig) {
        adapter = new BaseQuickAdapter<DATA, BaseViewHolder>(itemLayout, new ArrayList<DATA>()) {
            @Override
            protected void convert(BaseViewHolder helper, DATA item) {
                //noinspection unchecked
                getView().onConvert(helper, item, helper.getAdapterPosition());
            }
        };
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        boolean canLoadMore = listConfig.isCanLoadMore();
        if (canLoadMore) {
            if (listConfig.getLoadMoreView() == null) {
                throw new IllegalArgumentException("you must set you LoadMore View!");
            }
            adapter.setOnLoadMoreListener(this, recyclerView);
            adapter.setLoadMoreView(listConfig.getLoadMoreView());
        }
        adapter.setEnableLoadMore(canLoadMore);
        //设置动画
        adapter.openLoadAnimation(listConfig.getAnimationType());
        if (listConfig.isHasDivider()) {
            if (listConfig.getCustomItemDecoration() != null) {
                //设置成用户自己指定的item
                recyclerView.addItemDecoration(listConfig.getCustomItemDecoration());
            } else {
                //需要将传过来的dp转成px
                recyclerView.addItemDecoration(new RecyclerViewDivider(recyclerView.getContext()
                        , LinearLayoutManager.HORIZONTAL, Kits.Dimens.dpToPxInt(recyclerView.getContext()   , listConfig.getDividerHeightDp()), listConfig.getDividerColor()));
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    /**
     * 发布List数据
     *
     * @param dataList 数据集合
     */
    protected void publishListData(List<DATA> dataList) {
        listSubscriber.onNext(dataList);
    }

    /**
     * 发布错误信息
     *
     * @param e 错误
     */
    protected void pushlishError(Throwable e) {
        listSubscriber.onError(e);
    }

    /**
     * 替换当前Adapter里面的数据
     *
     * @param datas 数据集合
     */
    protected void replaceData(List<DATA> datas) {
        adapter.replaceData(datas);
    }

    /**
     * 完成loadMore的加载
     */
    public void loadMoreComplete() {
        adapter.loadMoreComplete();
    }

    /**
     * 完成所有loadMore
     *
     * @param isGone 是否去除LoadView
     */
    public void loadMoreEnd(boolean isGone) {
        adapter.loadMoreEnd(isGone);
    }

    /**
     * 完成所有的LoadMore并且显示LoadMoreView
     */
    public void loadMoreEnd() {
        loadMoreEnd(false);
    }

    /**
     * 如果调用了loadMoreEnd，如果还想再次记性加载更多的话，必须再次调用这个方法
     */
    public void restLoadMore(){
        adapter.notifyLoadMoreToLoading();
    }

    /**
     * 加载更多失败，点击之后进行重试
     */
    public void loaMoreFail() {
        adapter.loadMoreFail();
    }
}
