package com.peng.library.mantis.expand;

import com.peng.library.mantis.mvp.IPresent;
import com.peng.library.mantis.mvp.IView;

/**
 * Created by pyt on 2017/8/8.
 */

public interface MantisBaseContract {

    interface View<PRESENT extends IPresent> extends IView<PRESENT> {

    }

    interface Present<VIEW extends IView> extends IPresent<VIEW>{

    }
}
