package com.mrtian.project.ui.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mrtian.project.R;
import com.mrtian.project.manager.UserManager;
import com.mrtian.project.model.EmployeeInfo;
import com.mrtian.project.model.EvaluateInfo;
import com.mrtian.project.model.MyUser;
import com.mrtian.project.model.SalaryInfo;
import com.mrtian.project.model.SignInfo;
import com.mrtian.project.ui.view.MainActivity;
import com.mrtian.project.ui.view.base.BaseActivity;
import com.mrtian.project.utils.AppUtils;
import com.mrtian.project.utils.LogUtil;

import java.sql.Time;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class LoginActivity extends BaseActivity {
    private EditText et_account;
    private EditText et_password;
    private TextView tv_test_e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_login);
        initView();
        //登录
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_account.getText()) || TextUtils.isEmpty(et_password.getText())) {
                    AppUtils.toast("账号和密码不能为空！", Toast.LENGTH_SHORT);
                } else {
                    final MyUser user = new MyUser();
                    user.setUsername(et_account.getText().toString());
                    user.setPassword(et_password.getText().toString());
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null) {
                                LogUtil.d("txy", myUser.getIdentity() + "");
                                UserManager.setUser(user);
                                UserManager.queryPersonInfo();
                                startActivity(new Intent(getApplication(), MainActivity.class));
                                finish();
                            } else {
                                AppUtils.toast("登录失败，账号不存在或密码错误！", Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }
            }
        });
        //注册
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), RegisterActivity.class));
            }
        });
        findViewById(R.id.tv_test_e).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_account.setText("18842678655");
                et_password.setText("t123456");
            }
        });
    }

    private void initView() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_test_e = (TextView) findViewById(R.id.tv_test_e);
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleName("登陆");
        mTitleBar.setLeftAreaVisble(false);
    }
}
