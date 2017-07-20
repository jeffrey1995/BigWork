package com.mrtian.project.ui.view.systemsetting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mrtian.project.R;
import com.mrtian.project.ui.view.base.BaseActivity;
import com.mrtian.project.ui.view.login.LoginActivity;
import com.mrtian.project.utils.ActivityCollector;

import cn.bmob.v3.BmobUser;

public class SettingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_setting);
        findViewById(R.id.logout_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDlg("退出登录", "您确定要退出码？", "确定", "取消", 0);
            }
        });
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleName("设置");
    }

    @Override
    public void workOkBtn(int index, Object data) {
        if (index == 0) {
            BmobUser.logOut();
            ActivityCollector.finishAll();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void workCancelBtn(int index, Object data) {
        super.workCancelBtn(index, data);
    }
}
