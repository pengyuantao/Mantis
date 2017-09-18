package com.peng.mantis.contract;

import com.peng.library.mantis.expand.data.MantisDataContract;

import java.util.List;

/**
 * 测试提示框架的协议
 * Created by pyt on 2017/6/26.
 */

public interface TestHintContract {


    interface View extends MantisDataContract.View<Present,String>{
    }

    interface Present extends MantisDataContract.Present<View,String>{

        void reload ();

    }
}
