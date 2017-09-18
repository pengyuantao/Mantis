package com.peng.library.mantis.mvp;

import com.peng.library.mantis.hint.HintLayout;
import com.peng.library.mantis.hint.IHintView;

/**
 * View的代理类，这个类主要用于View的辅助显示
 * <p>
 * 相关辅助weidget
 * 1.errorView
 * 2.emptyView
 * 3.progressView
 * <p>
 * Created by pyt on 2017/6/16.
 */

public interface IViewDelegate extends IHintView {

    int INDEX_CONTENT = HintLayout.INDEX_CONTENT;

    int INDEX_EMPTY = HintLayout.INDEX_EMPTY;

    int INDEX_PROGRESS = HintLayout.INDEX_PROGRESS;

    int INDEX_ERROR = HintLayout.INDEX_ERROR;

    int INDEX_NETWORK_ERROR = HintLayout.INDEX_NETWORK_ERROR;
}
