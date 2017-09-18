package com.peng.mantis.model.http.service;

import com.peng.mantis.model.http.entity.Weather;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 本果网络请求的api
 * Created by pyt on 2017/7/5.
 */

public interface MantisNetApi {

    @GET("http://image.baidu.com/channel/listjson?pn=30&rn=30&tag2=全部&ftags=&sorttype=0&ie=utf8&oe=utf-8&fr=channel&app=img.browse.channel.star")
    Observable<String> getBaiduImage (@Query("tag1") String type);

    //http://image.baidu.com/channel/listjson?pn=0&rn=30&tag1=%E7%BE%8E%E5%A5%B3&tag2=%E5%85%A8%E9%83%A8&sorttype=0&ie=utf8&oe=utf-8
    @FormUrlEncoded
    @POST("http://image.baidu.com/channel/listjson")
    Observable<String> getBaiduImagePost (@Field("pn") int pn, @Field("rn") int rn, @Field("tag1") String tag1, @Field("tag2") String tag2, @Field("sorttype") int sortType, @Field("ie") String ie, @Field("oe") String oe);

    //查询天气
    @GET("http://wthrcdn.etouch.cn/weather_mini")
    Observable<Weather> getWeatherInfo (@Query(value = "city", encoded = true) String city);

    @GET("https://bkzjapi.21move.net/api.aspx?action=bk_buy_order_list_get&did=a5e530da36c053f494f006d8e03e40c6&token=e36cefaf8b494684b4771b8d4f67416e&format=json&v=3&terminal=H5&lang=CN&sign_method=md5&appkey=21ee4afb24d149beb430ff16258bf974&appid=d3254130218942f5af7bbbf94cbfbcd8&limit=6&start=0&statue=&fstatue=&timestamp=2017-07-12+13%3A54%3A59&sign=7cccddfcc72f6622f02351e681e94a41&_1499838899798=")
    Observable<String> getOrderList ();

    @GET("https://kyfw.12306.cn/otn/regist/init")
    Observable<String> get12306Request ();

}
