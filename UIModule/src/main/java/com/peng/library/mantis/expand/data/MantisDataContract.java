package com.peng.library.mantis.expand.data;

import com.peng.library.mantis.mvp.IPresent;
import com.peng.library.mantis.mvp.IView;

/**
 * 单一数据对应的协议(如果使用Data相关模块，必须使用这个协议)
 * Created by pyt on 2017/6/22.
 */

public interface MantisDataContract {

    interface View<PRESENT extends IPresent,DATA> extends IView<PRESENT>{
        void onReceiveData (DATA data);

        void onReceiveError (Throwable error);
    }

    interface Present<VIEW extends IView,DATA> extends IPresent<VIEW>{

    }

}
