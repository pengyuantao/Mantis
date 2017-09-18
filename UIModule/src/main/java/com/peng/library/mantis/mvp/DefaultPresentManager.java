package com.peng.library.mantis.mvp;

import android.support.annotation.Size;

import java.util.HashMap;

/**
 * Created by pyt on 2017/6/17.
 */

public class DefaultPresentManager extends AbsPresentManager {

    public static final String DIVISION_PRESENT_ID = "_";

    //缓存Present地方
    private HashMap<String, IPresent> cachePresent = new HashMap<String, IPresent>();

    private int nextIndex;

    private StringBuilder idBuilder = new StringBuilder();


    @Override
    public void create(@Size(2) Object[] idAndPresent, Object view) {
        String id = generatePresentId();
        try {
            BindPresent bindPresentAnnotation = view.getClass().getAnnotation(BindPresent.class);
            Class<? extends IPresent> value = bindPresentAnnotation.value();
            if (value == null) {
                throw new IllegalArgumentException("you "+view.getClass().getName()+" must use @BindPresent at the TYPE");
            }
            IPresent iPresent = value.newInstance();
            cachePresent.put(id, iPresent);
            idAndPresent[0] = id;
            idAndPresent[1] = iPresent;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private String generatePresentId() {
        idBuilder.append(nextIndex)
                .append(DIVISION_PRESENT_ID)
                .append(System.nanoTime())
                .append(DIVISION_PRESENT_ID)
                .append(((int) (Math.random() * 1000)));
        String generateId = idBuilder.toString();
        idBuilder.setLength(0);
        return generateId;
    }

    @Override
    public <P extends IPresent> P get(String id) {
        return (P) cachePresent.get(id);
    }

    @Override
    public void destroy(String id) {
        cachePresent.remove(id);
    }


}
