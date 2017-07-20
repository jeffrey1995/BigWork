package com.mrtian.project.ui.view.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mrtian.project.R;
import com.mrtian.project.model.EmployeeInfo;
import com.mrtian.project.ui.view.base.BaseActivity;
import com.mrtian.project.utils.GsonProvider;
import com.mrtian.project.utils.LogUtil;

public class DetailEmployeeInfoActivity extends BaseActivity {
    public final String TAG = "DetailEmployeeInfoActivity";
    public static final String EXTRA_ACTIVITY_DATA = "EmployeeString";

    public static Intent newIntent(Context packageContext, String str_data) {
        Intent intent = new Intent(packageContext, DetailEmployeeInfoActivity.class);
        intent.putExtra(EXTRA_ACTIVITY_DATA, str_data);
        return intent;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleName(getString(R.string.details_title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_detail_employee_info);
        init();
    }

    private void init() {
        EmployeeInfo ei = null;
        try {
            String str_data = getIntent().getStringExtra(EXTRA_ACTIVITY_DATA);
            ei = GsonProvider.getInstance().getGson().fromJson(str_data, EmployeeInfo.class);
        } catch (Exception e) {
            LogUtil.d(TAG, getString(R.string.data_error));
            e.printStackTrace();
        }
        if (ei != null) {
            ((TextView) findViewById(R.id.tv_employee_name)).setText(ei.getEmployeeName());
            ((TextView) findViewById(R.id.tv_employee_sex)).setText(ei.getEmployeeSex());
            ((TextView) findViewById(R.id.tv_employee_number)).setText(ei.getEmployeeNumber());
            ((TextView) findViewById(R.id.tv_employee_birthday)).setText(ei.getEmployeeBirthday());
            ((TextView) findViewById(R.id.tv_employee_education)).setText(ei.getEmployeeEducation());
            ((TextView) findViewById(R.id.tv_employee_salary)).setText(ei.getEmployeeSalary());
            ((TextView) findViewById(R.id.tv_employee_address)).setText(ei.getEmployeeAddress());
            ((TextView) findViewById(R.id.tv_employee_phone )).setText(ei.getEmployeePhoneNumber());
            ((TextView) findViewById(R.id.tv_employee_email)).setText(ei.getEmployeeEmail());
            ((TextView) findViewById(R.id.tv_employee_department)).setText(ei.getEmployeeDepartment());
        }
    }
}
