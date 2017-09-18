package com.peng.android.coremodule.router;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * 路由Activity
 * Created by pyt on 2017/7/11.
 */

public class RouterActivity extends AppCompatActivity {

    @Override
    public FragmentManager getSupportFragmentManager() {
        return RouterHelper.create(super.getSupportFragmentManager(), this);
    }
}
