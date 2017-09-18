package com.peng.mantis.model.db.entity.adapter;

import com.peng.library.mantis.kit.Kits;
import com.peng.mantis.model.db.entity.TodayWeather;
import com.peng.mantis.model.http.entity.Weather;

import java.util.List;

/**
 * Created by pyt on 2017/7/11.
 */

public class TodayWeatherAdapter {

    private Weather weather;

    public TodayWeatherAdapter(Weather weather) {
        this.weather = weather;
    }

    public TodayWeather getTodayWeather(String city) {
        List<Weather.DataBean.ForecastBean> forecast = weather.getData().getForecast();
        if (forecast != null && !forecast.isEmpty() && forecast.size() != 0) {
            Weather.DataBean.ForecastBean forecastBean = forecast.get(0);
            TodayWeather todayWeather = new TodayWeather();
            todayWeather.setCacheTime(System.currentTimeMillis());
            todayWeather.setCity(weather.getDesc());
            todayWeather.setDate(Kits.Date.getYmd(System.currentTimeMillis()));
            todayWeather.setFengli(forecastBean.getFengli());
            todayWeather.setFengxiang(forecastBean.getFengxiang());
            todayWeather.setHigh(forecastBean.getHigh());
            todayWeather.setLow(forecastBean.getLow());
            todayWeather.setCity(city);
            todayWeather.setType(forecastBean.getType());
            return todayWeather;
        }
        return null;
    }
}
