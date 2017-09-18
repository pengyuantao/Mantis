package com.peng.library.mantis.expand.refresh;

import com.peng.library.mantis.mvp.IPresent;
import com.peng.library.mantis.mvp.IView;

/**
 * 刷新数据的协议
 * Created by pyt on 2017/6/27.
 */

public interface MantisRefreshContract {

    interface View<PRESENT extends IPresent> extends IView<PRESENT>{
        void autoRefresh ();
        void refreshComplete ();
        boolean isRefreshing ();
    }

    interface Present<VIEW extends IView> extends IPresent<VIEW>{
        void onRefreshCall ();
    }

}
