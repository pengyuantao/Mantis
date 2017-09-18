package com.peng.mantis.model.repository;

import com.peng.library.mantis.kit.Kits;
import com.peng.mantis.TestApplication;
import com.peng.mantis.model.db.MantisDBManager;
import com.peng.mantis.model.db.entity.TodayWeather;
import com.peng.mantis.model.db.entity.adapter.TodayWeatherAdapter;
import com.peng.mantis.model.db.greendao.TodayWeatherDao;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * 获取今天天气的Repository
 * Created by pyt on 2017/7/11.
 */

public class TodayWeatherRepository {

    /**
     * @param city
     * @return
     */
    public static Observable<TodayWeather> getTodayWeather(String city){
        //从数据库查询当前的天气
        Observable<TodayWeather> dbTodayWeatherObservable = MantisDBManager.getInstance().getTodayDaoSession()
                .queryBuilder(TodayWeather.class)
                .where(TodayWeatherDao.Properties.City.eq(city))
                .rx()
                .unique();

        //判断是否有网络
        if (Kits.NetWork.NETWORK_TYPE_DISCONNECT.equals(Kits.NetWork.getNetworkTypeName(TestApplication.getApplication()))) {
            return dbTodayWeatherObservable;
        }

        //请求网络
        Observable<TodayWeather> networkTodayWeatherObservable = TestApplication.getApplication().getMantisNetApi().getWeatherInfo(city)
                .map(weather -> new TodayWeatherAdapter(weather).getTodayWeather(city))
                .doOnNext(todayWeather -> Schedulers.io().createWorker()
                                .schedule(() -> MantisDBManager.getInstance().getTodayDaoSession().insertOrReplace(todayWeather)));

        //合并两个Observalbe
        return Observable.concat(dbTodayWeatherObservable, networkTodayWeatherObservable)
                .distinct()
                .filter(todayWeather -> todayWeather != null)
                .takeUntil(todayWeather -> System.currentTimeMillis() - todayWeather.getCacheTime() < 10000);
    }

}
