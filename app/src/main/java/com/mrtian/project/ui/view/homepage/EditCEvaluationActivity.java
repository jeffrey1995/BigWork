package com.mrtian.project.ui.view.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.mrtian.project.R;
import com.mrtian.project.ui.view.base.BaseActivity;
import com.mrtian.project.ui.widget.TopLayout;
import com.mrtian.project.utils.AppUtils;
import com.mrtian.project.utils.LogUtil;

public class EditCEvaluationActivity extends BaseActivity {
    public static final String EXTRA_ACTIVITY_TITLE = "activity_title";

    public static Intent newIntent(Context packageContext, String title) {
        Intent intent = new Intent(packageContext, EditCEvaluationActivity.class);
        intent.putExtra(EXTRA_ACTIVITY_TITLE, title);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_edit_evaluation);
        init();
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleName("" + getIntent().getStringExtra(EXTRA_ACTIVITY_TITLE));
    }

    private void init() {
        findViewById(R.id.commit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.d("txy", "click!");
                AppUtils.toast("提交成功", Toast.LENGTH_SHORT);
                finish();
            }
        });
    }

}
