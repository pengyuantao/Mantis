package com.peng.mantis.contract;

import com.peng.library.mantis.expand.data.MantisDataContract;

/**
 *
 * Created by pyt on 2017/6/23.
 */
public interface TestDataContract{

    interface View extends MantisDataContract.View<Present,String>{
        void toast (String msg);
    }

    interface Present extends MantisDataContract.Present<View,String>{

        void login (String account, String password);

        void logout ();
    }

}
