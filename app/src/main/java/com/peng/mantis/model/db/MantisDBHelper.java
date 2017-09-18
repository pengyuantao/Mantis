package com.peng.mantis.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.peng.android.coremodule.db.GreenDBUpdateHelper;
import com.peng.mantis.model.db.greendao.DaoMaster;
import com.peng.mantis.model.db.greendao.TodayWeatherDao;


/**
 * 数据库升级主要类
 * Created by pyt on 2017/7/11.
 */

class MantisDBHelper extends DaoMaster.OpenHelper {


    public MantisDBHelper(Context context, String name) {
        super(context, name);
    }

    public MantisDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //自动升级
        GreenDBUpdateHelper.migrate(db, TodayWeatherDao.class);
    }
}
