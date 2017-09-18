package com.peng.mantis;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.facebook.stetho.Stetho;
import com.peng.library.mantis.Mantis;
import com.peng.library.mantis.expand.list.link.ListConfig;
import com.peng.library.mantis.expand.refresh.link.RefreshConfig;
import com.peng.library.mantis.hint.HintConfig;
import com.peng.library.mantis.kit.Kits;
import com.peng.mantis.model.db.MantisDBManager;
import com.peng.mantis.model.http.ApiClient;
import com.peng.mantis.model.http.ApiConstants;
import com.peng.mantis.model.http.service.MantisNetApi;
import com.peng.refresh.layout.PtrFrameLayout;
import com.peng.refresh.layout.header.StoreHouseHeader;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by pyt on 2017/6/17.
 */

public class TestApplication extends Application {

    private static TestApplication instance;

    private MantisNetApi mantisNetApi;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ARouter.init(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        PtrFrameLayout.DEBUG = false;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        Mantis.getInstance().init(this);
        //设置提示的Builder
        Mantis.getInstance().setHintConfigBuilder(getDefaultHintConfigBuilder());
        Mantis.getInstance().setRefreshConfigBuilder(getDefaultRefreshBuilder());
        Mantis.getInstance().setListConfigBuilder(getDefaultListBuilder());
        Stetho.initializeWithDefaults(this);
        MantisDBManager.getInstance().init(this);
    }

    /**
     * 获取默认的列表配置Builder
     * @return
     */
    public ListConfig.Builder getDefaultListBuilder(){
        ListConfig.Builder builder = new ListConfig.Builder();
        builder.canLoadMore(false).loadMoreView(new SimpleLoadMoreView());
        return builder;
    }

    /**
     * 获取默认的刷新配置的Builder
     * @return
     */
    public RefreshConfig.Builder getDefaultRefreshBuilder(){
        RefreshConfig.Builder builder = new RefreshConfig.Builder();
        builder.onCreateHeaderViewListener(ptrFrameLayout -> {
            StoreHouseHeader header = new StoreHouseHeader(this);
            int padding = ((int) Kits.Dimens.dpToPx(this, 20));
            header.setPadding(0,padding , 0,padding);
            header.initWithString("PENG YUAN TAO");
            return header;
        }).headerBackgroundColor(Color.BLACK);
        return builder;
    }

    /**
     * 获取默认的提示配置的Builder
     * @return
     */
    private HintConfig.Builder getDefaultHintConfigBuilder () {
        HintConfig.Builder builder = new HintConfig.Builder();
        builder.layoutEmptyId(R.layout.hint_empty)
                .layoutProgressId(R.layout.hint_progress)
                .layoutErrorId(R.layout.hint_error)
                .showErrorViewListener((errorView, title, content) -> {
					((TextView) errorView.findViewById(R.id.tv_error_title)).setText(title);
					((TextView) errorView.findViewById(R.id.tv_error_content)).setText(content);
				})
                .layoutNetworkErrorId(R.layout.hint_net_error);
        return builder;
    }

    public static TestApplication getApplication(){
        return instance;
    }

    public MantisNetApi getMantisNetApi (){
        if (mantisNetApi == null) {
            mantisNetApi = ApiClient.createApiService(ApiConstants.API_BENGUO_NET_URL, MantisNetApi.class);
        }
        return mantisNetApi;
    }

}
