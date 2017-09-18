package com.peng.library.mantis.mvp;

/**
 * Created by pyt on 2017/6/17.
 */

public abstract class AbsPresentManager {

    private static AbsPresentManager instance = new DefaultPresentManager();


    public static AbsPresentManager getInstance(){
        return instance;
    }

    /**
     * 创建Present和生成id的方法
     * @param idAndPresent
     * @param view
     */
    public abstract void create(Object[] idAndPresent,Object view);

    /**
     * 根据id获取present
     * @param id
     * @param <P>
     * @return
     */
    public abstract <P extends IPresent> P get(String id);

    /**
     * 根据id销毁present
     * @param id
     */
    public abstract void destroy(String id);
}
