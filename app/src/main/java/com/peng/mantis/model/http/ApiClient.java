package com.peng.mantis.model.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.peng.android.coremodule.http.convert.FastJsonConverterFactory;
import com.peng.mantis.BuildConfig;
import com.peng.mantis.model.http.interceptor.BenguoUrlInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * 网络请求客户端
 * Created by pyt on 2017/7/5.
 */

public class ApiClient {

    /**
     * 根据不同的URL和Service clz对象，创建指定的Service
     *
     * @param baseUrl
     * @param serviceClz
     * @param <T>
     * @return
     */
    public static <T> T createApiService(String baseUrl, Class<T> serviceClz) {
        //创建和初始化OkHttp
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        //debug状态添加Log拦截器
        if (BuildConfig.DEBUG) {
            //这里必须设置成Body模式，默认为null，则不会进行打log
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpBuilder.addInterceptor(httpLoggingInterceptor);
        }
        okHttpBuilder.connectTimeout(20, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(10, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(10, TimeUnit.SECONDS);
        //添加url拦截器
        okHttpBuilder.addInterceptor(BenguoUrlInterceptor.create());
        okHttpBuilder.addNetworkInterceptor(new StethoInterceptor());
        //构建OKHttpClient
        OkHttpClient okHttpClient = okHttpBuilder.build();
        //初始化Retrofit
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        //添加Soap请求的转换器
        Retrofit retrofit = retrofitBuilder
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .build();
        return retrofit.create(serviceClz);
    }


}
