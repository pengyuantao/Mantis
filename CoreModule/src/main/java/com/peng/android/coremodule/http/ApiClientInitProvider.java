package com.peng.android.coremodule.http;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 *
 * 初始化ApiClient内容提供器
 *
 * Created by pyt on 2017/7/6.
 */

public interface ApiClientInitProvider {

    OkHttpClient provideOkHttpClient();

    Retrofit provideRetrofit(OkHttpClient okHttpClient);

}
