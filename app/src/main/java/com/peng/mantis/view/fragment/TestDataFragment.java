package com.peng.mantis.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.peng.library.mantis.expand.data.MantisDataFragment;
import com.peng.library.mantis.hint.HintConfig;
import com.peng.library.mantis.mvp.BindPresent;
import com.peng.mantis.R;
import com.peng.mantis.contract.TestDataContract;
import com.peng.mantis.present.TestDataPresent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pyt on 2017/6/23.
 */
@BindPresent(value = TestDataPresent.class)
public class TestDataFragment extends MantisDataFragment<TestDataContract.Present, String> implements TestDataContract.View {

    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.tv_result)
    TextView tvResult;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, HintConfig.Builder builder) {
        View contentView = inflater.inflate(R.layout.fragment_test_data, container, false);
        ButterKnife.bind(this, contentView);
        return contentView;
    }

    @Override
    public void onReceiveData(String s) {
        tvResult.setText(s);
    }

    @Override
    public void onReceiveError(Throwable error) {
        error.printStackTrace();
        tvResult.setText("发生错误：" + error.getMessage());
    }


    @Override
    public void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_login)
    public void login() {
        getPresent().login(etAccount.getText().toString(), etPassword.getText().toString());
    }


    public void onLogout(View view) {
        getPresent().logout();

    }
}
