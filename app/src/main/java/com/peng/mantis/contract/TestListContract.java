package com.peng.mantis.contract;

import com.peng.library.mantis.expand.list.MantisListContract;

/**
 * Created by pyt on 2017/6/29.
 */

public interface TestListContract {

    interface View extends MantisListContract.View<Present,String>{

    }

    interface Present extends MantisListContract.Present<View, String> {

    }

}
