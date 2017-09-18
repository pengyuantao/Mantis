package com.peng.mantis.model.db;

import android.content.Context;

import com.peng.mantis.model.db.greendao.DaoMaster;
import com.peng.mantis.model.db.greendao.DaoSession;
import com.peng.refresh.layout.header.MaterialHeader;


/**
 * 数据库初始化管理类
 * Created by pyt on 2017/7/11.
 */

public class MantisDBManager {

    public static final String DB_WEATHER_NAME = "today_weather";

    private static MantisDBManager instance = new MantisDBManager();

    private DaoSession weatherDaoSeeeion;

    private MantisDBManager() {

    }

    public static MantisDBManager getInstance() {
        return instance;
    }

    /**
     * 初始化所有的数据库
     *
     * @param context
     */
    public void init(Context context) {
        MantisDBHelper mantisDBHelper = new MantisDBHelper(context.getApplicationContext(), DB_WEATHER_NAME);
        DaoMaster daoMaster = new DaoMaster(mantisDBHelper.getWritableDb());
        weatherDaoSeeeion = daoMaster.newSession();
    }

    public DaoSession getTodayDaoSession() {
        return weatherDaoSeeeion;
    }

}
