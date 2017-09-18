package com.peng.mantis.present;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.peng.library.mantis.expand.list.MantisListPresent;
import com.peng.mantis.contract.TestListContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by pyt on 2017/6/29.
 */

public class TestListPresent extends MantisListPresent<TestListContract.View, String> implements TestListContract.Present {

    public static final String[] imageUrl = new String[]{
            "http://pic.58pic.com/58pic/16/00/52/11J58PICD3S_1024.jpg",
            "http://pic16.photophoto.cn/20100727/0006019030380410_b.jpg",
            "http://pic15.photophoto.cn/20100615/0006019058815826_b.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/ac4bd11373f082024d276a1b41fbfbedab641b58.jpg",
            "http://pic33.photophoto.cn/20141204/0006019060393096_b.jpg",
            "http://www.chla.com.cn/uploadfile/2011/new2011617/images/2013/10/20131011164609.jpg",
            "http://tupian.enterdesk.com/2013/mxy/12/28/1/3.jpg",
            "http://pic.58pic.com/58pic/11/35/39/07k58PICUiF.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1536514457,2967672763&fm=26&gp=0.jpg",
            "http://pic1.cxtuku.com/00/13/06/b05615c471c6.jpg",
            "http://pic.qiantucdn.com/58pic/18/24/77/42x58PICVSb_1024.jpg",
    };

    public static final String[] loadMoreUrl = new String[]{
            "http://pic31.photophoto.cn/20140403/0020032841623151_b.jpg",
            "http://pic.58pic.com/58pic/16/69/38/42v58PICzEP_1024.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/b03533fa828ba61e5e6d4c0d4b34970a304e5915.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/574e9258d109b3de25b6324bc6bf6c81800a4c8c.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/5366d0160924ab18cb0aad123ffae6cd7b890bf2.jpg",
            "http://scimg.jb51.net/allimg/151026/14-15102610342N93.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/1e30e924b899a9017c518d1517950a7b0208f5a9.jpg",
            "http://pic.qiantucdn.com/58pic/18/37/96/18n58PICPb7_1024.jpg",
            "http://pic.58pic.com/58pic/11/35/39/51k58PICF9I.jpg",
            "http://pic.58pic.com/58pic/17/14/25/56N58PICCmT_1024.jpg",
            "http://pic.58pic.com/58pic/13/09/62/04u58PICc5D_1024.jpg",
            "http://pic.58pic.com/58pic/14/86/00/79v58PICgXc_1024.jpg",
            "http://img2.91.com/uploads/allimg/131010/32-131010150J8.jpg",
            "http://pic.58pic.com/58pic/14/44/22/36H58PICezB_1024.jpg",
            "http://pic.jj20.com/up/allimg/811/0R914100610/140R9100610-5.jpg"
    };

    private int loadMoreTimes;


    private List<String> testDataList = new ArrayList<>();

    @Override
    public void onCreate(TestListContract.View view, Bundle data) {
        super.onCreate(view, data);
        testDataList.addAll(Arrays.asList(imageUrl));
    }

    @Override
    public void onCreateView(TestListContract.View view) {
        super.onCreateView(view);
        getView().autoRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Toast.makeText(getActivity(), "点击" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefreshCall() {
        loadMoreTimes = 0;
        restLoadMore();
        createCommonObservable("", 2)
                .compose(bindToLifecycle())
                .map(s -> {
                    testDataList.clear();
                    testDataList.addAll(Arrays.asList(imageUrl));
                    Collections.shuffle(testDataList);
                    return testDataList;
                })
                .subscribe(strings -> publishListData(strings), new Action1<Throwable>() {
                    @Override
                    public void call (Throwable throwable) {
                        pushlishError(throwable);
                    }
                });
    }

    @Override
    public void onLoadMoreRequested() {
        loadMoreTimes++;
        if (loadMoreTimes > 5) {
            loadMoreEnd();
            return;
        }
        if (loadMoreTimes == 2) {
            loaMoreFail();
            return;
        }
        createCommonObservable("heh", 2)
                .compose(bindToLifecycle())
                .map(s -> {
                    List<String> strings = Arrays.asList(loadMoreUrl);
                    Collections.shuffle(strings);

                    return strings;
                })
                .subscribe(s -> {
                    getAdapter().addData(s);
                    getAdapter().loadMoreComplete();
                    getViewDelegate().showContentView();
                });
    }

    /**
     * @param text
     * @param delay
     * @return
     */
    private Observable<String> createCommonObservable(String text, int delay) {
        return Observable.<String>create(subscriber -> subscriber.onNext(text))
                .delay(delay, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public boolean onItemLongClick (BaseQuickAdapter adapter, View view, int position) {
        return false;
    }
}
