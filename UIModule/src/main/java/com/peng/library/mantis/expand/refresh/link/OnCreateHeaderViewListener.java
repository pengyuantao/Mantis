package com.peng.library.mantis.expand.refresh.link;

import android.view.View;

import com.peng.refresh.layout.PtrFrameLayout;

/**
 * 当创建HeaderView的时候进行调用
 * Created by pyt on 2017/6/28.
 */

public interface OnCreateHeaderViewListener {
    View onCreateHeaderView (PtrFrameLayout ptrFrameLayout);
}
