package com.peng.mantis.model.http.interceptor;

import com.peng.mantis.model.http.ApiConstants;
import com.peng.mantis.model.repository.UserIdRepository;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 本果网络拦截器，拦截url
 * _userid=2782&_httpstate=UDP
 * 添加参数_userid和_state
 * Created by pyt on 2017/7/5.
 */

public class BenguoUrlInterceptor implements Interceptor {

    public static final String KEY_USERID = "_userid";
    public static final String KEY_HTTP_STATE = "_httpstate";
    public static final String VALUE_UDP = "UDP";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        if (ApiConstants.API_BENGUO_NET_URL.contains(oldRequest.url().host())) {
            HttpUrl url = oldRequest.url();
            //添加附加的参数
            HttpUrl.Builder urlBuilder = url.newBuilder();
            urlBuilder.addQueryParameter(KEY_USERID, UserIdRepository.getUserId());
            urlBuilder.addQueryParameter(KEY_HTTP_STATE, VALUE_UDP);
            Request newRequest = oldRequest.newBuilder().url(urlBuilder.build()).build();
            return chain.proceed(newRequest);
        } else {
            Request newRequest = oldRequest.newBuilder().addHeader("Accept-Encoding","identity").url(oldRequest.url()).build();
            return chain.proceed(newRequest);
        }
    }

    public static BenguoUrlInterceptor create(){
        return new BenguoUrlInterceptor();
    }
}
