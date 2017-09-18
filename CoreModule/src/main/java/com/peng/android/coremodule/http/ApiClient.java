package com.peng.android.coremodule.http;

import android.os.Looper;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 所有的API调用都必须在主线程里面
 *
 * Created by pyt on 2017/7/6.
 */

public class ApiClient {

    //缓存Service的Map
    private HashMap<Class, Object> serviceMap = new HashMap<>();

    //OKHttp
    private OkHttpClient defaultClient;

    //Retrofit
    private Retrofit defaultRetrofit;

    /**
     * 获取单例
     * @return
     */
    public static ApiClient getInstance(){
        return ApiClientHolder.INSTANCE;
    }

    /**
     * 初始化这个Client
     * @param listener
     */
    public void init(ApiClientInitProvider listener) {
        checkIsMainThread();
        if (listener != null) {
            defaultClient = listener.provideOkHttpClient();
            defaultRetrofit = listener.provideRetrofit(defaultClient);
        }
        if (defaultClient == null) {
            defaultClient = new OkHttpClient();
        }

        if (defaultRetrofit == null) {
            defaultRetrofit = new Retrofit.Builder().build();
        }
    }


    /**
     * 返回一个默认的OkHttpClient
     *
     * 一般情况下用不到，除非有自定义的OkHttpClient，而已还需要默认的配置的
     *
     * @return OkHttpClient
     */
    public OkHttpClient getDefaultOkHttpClient(){
        checkIsMainThread();
        return defaultClient;
    }

    /**
     * 返回一个默认的Retrofit
     *
     * 情况下用不到，除非有自定义Retrofit，并且需要默认的配置
     *
     * @return Retrofit
     */
    public Retrofit getDefaultRetrofit(){
        checkIsMainThread();
        return defaultRetrofit;
    }

    /**
     * 获取Retrofit对应的Service
     * @param retrofit
     * @param serviceClz
     * @param <T>
     * @return
     */
    public <T> T obtainService(Retrofit retrofit, Class<T> serviceClz) {
        checkIsMainThread();
        if (serviceClz == null) {
            throw new AssertionError();
        }
        Object o = serviceMap.get(serviceClz);
        if (o == null) {
            o = retrofit.create(serviceClz);
            serviceMap.put(serviceClz, o);
        }
        return (T) o;
    }

    /**
     * 获取Retrofit对应的Service
     * @param serviceClz
     * @param <T>
     * @return
     */
    public <T> T obtainService(Class<T> serviceClz) {
        return obtainService(defaultRetrofit, serviceClz);
    }

    /**
     * 校验是否为主线程
     * @return
     */
    private void checkIsMainThread(){
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new AssertionError();
        }
    }

    //single
    private static class ApiClientHolder{
        public static final ApiClient INSTANCE = new ApiClient();
    }


}