package com.mrtian.project.ui.view.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mrtian.project.R;
import com.mrtian.project.model.EmployeeInfo;
import com.mrtian.project.model.MyUser;
import com.mrtian.project.ui.view.base.BaseActivity;
import com.mrtian.project.utils.AppUtils;
import com.mrtian.project.utils.LogUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {
    private EditText et_account;
    private EditText et_VerificationCode;
    private EditText et_password;
    private CheckBox cb_Agreement;
    private LinearLayout ll_VerificationCode, ll_Password;
    private int STEP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_VerificationCode = (EditText) findViewById(R.id.et_VerificationCode);
        et_password = (EditText) findViewById(R.id.et_password);
        cb_Agreement = (CheckBox) findViewById(R.id.cb_Agreement);
        ll_VerificationCode = (LinearLayout) findViewById(R.id.ll_VerificationCode);
        ll_Password = (LinearLayout) findViewById(R.id.ll_Password);
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (STEP == 1) {
                    registerStep1();
                } else if (STEP == 2) {
                    registerStep2();
                } else if (STEP == 3) {
                    registerStep3();
                }
            }
        });
    }

    /**
     * 输入账号
     */
    private void registerStep1() {
        if (TextUtils.isEmpty(et_account.getText()) || et_account.getText().length() != 11) {
            AppUtils.toast("请输入合法手机号！", Toast.LENGTH_SHORT);
            return;
        } else {
            //检查账号是否存在
            BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
            query.addWhereEqualTo("username", et_account.getText());
            query.findObjects(new FindListener<BmobUser>() {
                @Override
                public void done(List<BmobUser> object, BmobException e) {
                    if (object == null || object.size() > 0) {
                        AppUtils.toast("该账号已被注册！", Toast.LENGTH_SHORT);
                    } else {
                        if (!cb_Agreement.isChecked()) {
                            AppUtils.toast("请阅读协议并同意！", Toast.LENGTH_SHORT);
                        } else {
                            et_account.setFocusable(false);
                            et_account.setFocusableInTouchMode(false);
                            cb_Agreement.setVisibility(View.GONE);
                            STEP++;
                            ll_VerificationCode.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }
    }

    /**
     * 验证码
     */
    private void registerStep2() {
        if (TextUtils.isEmpty(et_VerificationCode.getText())) {
            AppUtils.toast("请输入验证码", Toast.LENGTH_SHORT);
        } else if ("1111".equals(et_VerificationCode.getText().toString())) {
            ((Button) findViewById(R.id.btn_register)).setText("确认");
            STEP++;
            ll_Password.setVisibility(View.VISIBLE);
        } else {
            AppUtils.toast("无效验证码，请重试！", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 密码
     */
    private void registerStep3() {
        if (TextUtils.isEmpty(et_password.getText()) || et_password.getText().length() < 6) {
            AppUtils.toast("密码必须为6位以上合法字符", Toast.LENGTH_SHORT);
        } else {
            registerUser();
        }
    }


    /**
     * 注册
     */
    private void registerUser() {
        MyUser bu = new MyUser();
        bu.setUsername(et_account.getText().toString());
        bu.setPassword(et_password.getText().toString());
        bu.setIdentity(MyUser.MANAGER);
        //注意：不能用save方法进行注册
        bu.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    AppUtils.toast("注册成功:",Toast.LENGTH_SHORT);
                } else {
                    LogUtil.e("BmobUser", e.toString());
                }
                finish();
            }
        });
        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setMyUser(bu);
        employeeInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e != null) {
                    LogUtil.d("bmob", "create personInfo :" + e.toString());
                }
            }
        });
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleName("注册");
    }
}
