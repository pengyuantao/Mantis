package com.peng.mantis.contract;

import com.peng.library.mantis.expand.refresh.MantisRefreshContract;

/**
 * Created by pyt on 2017/6/28.
 */

public interface TestRefreshContract {


    interface View extends MantisRefreshContract.View<Present>{
        void onReceiveData (String data);

        void onReceiveError (Throwable error);
    }

    interface Present extends MantisRefreshContract.Present<View>{
        void giveMeData ();
    }


}
