package com.peng.android.coremodule.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by pyt on 2017/7/10.
 */

public class PreferenceHelper {

    public static final int DEFAULT_NUM_VALUE = -1;
    public static final boolean DEFAULT_BOL_VALUE = false;
    public static final String DEFAULT_STR_VALUE = "";

    //缓存类
    private SharedPreferences sharedPreferences;

    private PreferenceHelper(Context context, String name) {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 创建一个辅助类
     *
     * @param context
     * @param preferenceName
     * @return
     */
    public static PreferenceHelper create(Context context, String preferenceName) {
        if (context == null || TextUtils.isEmpty(preferenceName)) {
            throw new IllegalArgumentException("the Context is null ,or preferenceName is null.");
        }
        return new PreferenceHelper(context.getApplicationContext(), preferenceName);
    }

    /**
     * 存数据
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        if (TextUtils.isEmpty(key) || value == null) {
            return;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof String) {
            edit.putString(key, (String) value);
        }else{
            throw new IllegalArgumentException("not support "+value.getClass()+" type!");
        }
    }

    /**
     * @param key
     * @return
     */
    public int getInt(String key) {
        return getInt(key, DEFAULT_NUM_VALUE);
    }

    /**
     *
     * @param key
     * @param defValue
     * @return
     */
    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, DEFAULT_NUM_VALUE);
    }

    /**
     *
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, DEFAULT_BOL_VALUE);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    /**
     *
     * @param key
     * @return
     */
    public float getFloat(String key) {
        return getFloat(key, DEFAULT_NUM_VALUE);
    }

    /**
     *
     * @param key
     * @param defValue
     * @return
     */
    public float getFloat(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    /**
     *
     * @param key
     * @return
     */
    public long getLong(String key) {
        return getLong(key, DEFAULT_NUM_VALUE);
    }

    /**
     *
     * @param key
     * @param defValue
     * @return
     */
    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    /**
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return getString(key, DEFAULT_STR_VALUE);
    }

    /**
     *
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

}
