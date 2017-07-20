package com.mrtian.project.ui.view.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mrtian.project.R;
import com.mrtian.project.ui.view.base.BaseActivity;
import com.mrtian.project.ui.widget.TopLayout;

public class AddEmployeeActivity extends BaseActivity {
    private static final String EXTRA_ACTIVITY_TITLE = "activity_title";

    public static Intent newIntent(Context packageContext, String activityCategoryTitle) {
        Intent intent = new Intent(packageContext, AddEmployeeActivity.class);
        intent.putExtra(EXTRA_ACTIVITY_TITLE, activityCategoryTitle);
        return intent;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleName("导入信息");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_add_employee);
        init();
    }

    private void init() {

    }
}
