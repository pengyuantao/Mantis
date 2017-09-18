package com.peng.library.mantis.expand.data;

import com.peng.library.mantis.expand.MantisBaseFragment;

/**
 * 单一数据的Fragment
 * Created by pyt on 2017/6/22.
 */

public abstract class MantisDataFragment<PRESENT extends MantisDataContract.Present,DATA> extends MantisBaseFragment<PRESENT> implements MantisDataContract.View<PRESENT,DATA>{

}
