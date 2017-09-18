package com.peng.mantis.contract;

import com.peng.library.mantis.expand.data.MantisDataContract;
import com.peng.library.mantis.expand.refresh.MantisRefreshContract;
import com.peng.mantis.model.db.entity.TodayWeather;

/**
 * 测试将Repository对象协议
 * Created by pyt on 2017/7/11.
 */

public interface TestRepositoryContract {

    interface View extends MantisRefreshContract.View<Present>{
        void setTodayWeather (TodayWeather todayWeather);
    }

    interface Present extends MantisRefreshContract.Present<View> {

    }

}
