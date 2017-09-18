package com.peng.mantis.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.peng.mantis.R;

/**
 * 如果一个Activity里面只有一个Fragment，那么只要通过这个包装的Activity进行启动就行。
 */
public class TestActivity extends AppCompatActivity {

    public static final String FLAG_ACTIVITY = "FLAG_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        addFragmentToContainer();
    }

    private void addFragmentToContainer() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        try {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, (Fragment) getFragmentClz().newInstance(), FLAG_ACTIVITY).commitNow();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public Class getFragmentClz(){
        return (Class) getIntent().getSerializableExtra(FLAG_ACTIVITY);
    }

    public static void start(Context context, Class fragmentClz) {
        Intent intent = new Intent(context, TestActivity.class);
        intent.putExtra(FLAG_ACTIVITY, fragmentClz);
        context.startActivity(intent);
    }

}
