package com.peng.library.mantis;

import android.app.Application;

import com.peng.library.mantis.expand.list.link.ListConfig;
import com.peng.library.mantis.hint.HintConfig;
import com.peng.library.mantis.model.ModelManager;
import com.peng.library.mantis.expand.refresh.link.RefreshConfig;

/**
 * 超级大螳螂,目前为主要的管理类
 * <p>
 * 主要功能:
 * 1.对全部的HintView进行初始化
 * <p>
 * Created by pyt on 2017/6/19.
 */
public class Mantis {

    private static Mantis instance = new Mantis();

    private HintConfig.Builder hintConfigBuilder;

    private RefreshConfig.Builder refreshConfigBuilder;

    private ListConfig.Builder listConfigBuilder;

    public static Mantis getInstance() {
        return instance;
    }

    /**
     * @param application
     */
    public void init(Application application) {
        ModelManager.init(application);
    }

    /**
     * 设置提示View的配置Builder
     *
     * @param builder
     */
    public void setHintConfigBuilder(HintConfig.Builder builder) {
        this.hintConfigBuilder = builder;
    }

    /**
     * 设置刷新View的配置Builder
     *
     * @param refreshConfigBuilder
     */
    public void setRefreshConfigBuilder(RefreshConfig.Builder refreshConfigBuilder) {
        this.refreshConfigBuilder = refreshConfigBuilder;
    }

    public void setListConfigBuilder(ListConfig.Builder listConfigBuilder) {
        this.listConfigBuilder = listConfigBuilder;
    }

    public HintConfig.Builder getHintConfigBuilder() {
        return hintConfigBuilder == null ? null : hintConfigBuilder.clone();
    }

    public RefreshConfig.Builder getRefreshConfigBuilder() {
        return refreshConfigBuilder == null ? null : refreshConfigBuilder.clone();
    }

    public ListConfig.Builder getListConfigBuilder() {
        return listConfigBuilder == null ? null : listConfigBuilder.clone();
    }
}
