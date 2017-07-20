package com.mrtian.project.ui.view.welcome;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mrtian.project.R;
import com.mrtian.project.model.MyUser;
import com.mrtian.project.ui.view.MainActivity;
import com.mrtian.project.ui.view.login.LoginActivity;

import cn.bmob.v3.BmobUser;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (BmobUser.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        super.setContentView(layoutResID);
    }
}
