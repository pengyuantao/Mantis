package com.peng.library.mantis.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

/**
 * 将View中的生命周期方法全部代理到Present
 * Created by pyt on 2017/6/17.
 */

public class ViewHelper<P extends IPresent> {

    //标记位
    public static final String KEY_PRESENT_ID = "KEY_PRESENT_ID";

    private P present;

    private String presentId;

    public <V extends IView> void onCreate(V view, Bundle data) {
        if (data == null || TextUtils.isEmpty(data.getString(KEY_PRESENT_ID))) {
            createPresent(view,data);
        } else {
            presentId = data.getString(KEY_PRESENT_ID);
            IPresent iPresent = AbsPresentManager.getInstance().get(presentId);
            if (iPresent == null) {
                createPresent(view,data);
            }
        }
    }

    public P getPresent(){
        return present;
    }

    public void onDestroy() {
        if (checkPresentIsNotNull()) {
            present.onDestroy();
            AbsPresentManager.getInstance().destroy(presentId);
        }
    }

    public void onCreateView(IView view) {
        if (checkPresentIsNotNull()) {
            present.onCreateView(view);
        }
    }

    public void onDestroyView() {
        if (checkPresentIsNotNull()) {
            present.onDestroyView();
        }
    }

    public void onStart() {
        if (checkPresentIsNotNull()) {
            present.onStart();
        }
    }

    public void onStop() {
        if (checkPresentIsNotNull()) {
            present.onStop();
        }
    }

    public void onResume() {
        if (checkPresentIsNotNull()) {
            present.onResume();
        }
    }

    public void onPause() {
        if (checkPresentIsNotNull()) {
            present.onPause();
        }
    }

    public void onSave(Bundle data) {
        if (checkPresentIsNotNull()) {
            data.putString(KEY_PRESENT_ID, presentId);
            present.onSave(data);
        }
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        present.onResult(requestCode,resultCode,data);
    }

    private boolean checkPresentIsNotNull(){
        return present != null;
    }

    private <V extends IView> void createPresent(V view, Bundle data) {
        Object[] idAndPresent = new Object[2];
        AbsPresentManager.getInstance().create(idAndPresent, view);
        if (!TextUtils.isEmpty((String) idAndPresent[0]) && idAndPresent[1] != null) {
            presentId = (String) idAndPresent[0];
            present = (P) idAndPresent[1];
            present.onCreate(view,data);
        }
    }


}
