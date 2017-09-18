package com.peng.mantis.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.peng.mantis.R;
import com.peng.mantis.view.fragment.TestDataFragment;
import com.peng.mantis.view.fragment.TestHintFragment;
import com.peng.mantis.view.fragment.TestHttpFragment;
import com.peng.mantis.view.fragment.TestListFragment;
import com.peng.mantis.view.fragment.TestRefreshFragment;
import com.peng.mantis.view.fragment.TestRepositoryFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
    }

    @OnClick(R.id.btn_skip_data_test)
    public void onDataTest() {
        TestActivity.start(this, TestDataFragment.class);
    }

    @OnClick(R.id.btn_skip_hint_test)
    public void onHintTest() {
        TestActivity.start(this, TestHintFragment.class);
    }

    @OnClick(R.id.btn_skip_refresh_test)
    public void onRefreshTest() {
        TestActivity.start(this, TestRefreshFragment.class);
    }

    @OnClick(R.id.btn_skip_list_test)
    public void onListTest() {
        TestActivity.start(this, TestListFragment.class);
    }


    @OnClick(R.id.btn_skip_http_test)
    public void onClick() {
        TestActivity.start(this, TestHttpFragment.class);
    }

    @OnClick(R.id.btn_skip_repository_test)
    public void onRepository() {
        TestActivity.start(this, TestRepositoryFragment.class);
    }


}
