package com.peng.mantis.contract;

import com.peng.library.mantis.expand.data.MantisDataContract;

/**
 * 测试网络的协议
 * Created by pyt on 2017/7/5.
 */

public interface TestHttpContract {


    interface View extends MantisDataContract.View<Present,String>{

    }

    interface Present extends MantisDataContract.Present<View,String>{
        void startGetRequest ();

        void startPostRequest ();
    }

}
