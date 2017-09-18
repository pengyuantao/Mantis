package com.peng.library.mantis.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Fragment作为View层，Activity作为环境层，activity主要用来操作View和Present
 * Created by pyt on 2017/6/17.
 */

public abstract class  MantisFragment<P extends IPresent> extends Fragment implements IView<P> {

    private ViewHelper<P> viewHelper = new ViewHelper<>();

    @Override
    public P getPresent() {
        return viewHelper.getPresent();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHelper.onCreate(this,savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewHelper.onCreateView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewHelper.onStart();
    }

    @Override
    public void onStop() {
        viewHelper.onStop();
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
        viewHelper.onResume();
    }

    @Override
    public void onPause() {
        viewHelper.onPause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        viewHelper.onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        viewHelper.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        viewHelper.onSave(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewHelper.onResult(requestCode,resultCode,data);
    }
}
