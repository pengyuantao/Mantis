package com.peng.android.coremodule.router;

import android.app.Activity;
import android.support.v4.app.FragmentManager;



/**
 * 路由辅助类
 * Created by pyt on 2017/7/10.
 */

public class RouterHelper {

    /**
     * 获取支持路由的帮助类
     * @param fragmentManager
     * @param activity
     * @return
     */
    public static FragmentManager create(FragmentManager fragmentManager, Activity activity) {
        return ProxyFragmentManager.create(fragmentManager, activity.getIntent());
    }

}
