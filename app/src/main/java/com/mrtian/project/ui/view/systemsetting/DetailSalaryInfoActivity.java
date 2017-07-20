package com.mrtian.project.ui.view.systemsetting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mrtian.project.R;
import com.mrtian.project.model.SalaryInfo;
import com.mrtian.project.ui.view.base.BaseActivity;
import com.mrtian.project.utils.GsonProvider;
import com.mrtian.project.utils.LogUtil;

public class DetailSalaryInfoActivity extends BaseActivity {
    public static final String EXTRA_ACTIVITY_DATA = "SalaryString";

    public static Intent newIntent(Context packageContext, String str_data) {
        Intent intent = new Intent(packageContext, DetailSalaryInfoActivity.class);
        intent.putExtra(EXTRA_ACTIVITY_DATA, str_data);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_detail_salary_info);
        init();
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleName("收入详情");
    }

    private void init() {
        SalaryInfo si = null;
        try {
            String str_data = getIntent().getStringExtra(EXTRA_ACTIVITY_DATA);
            si = GsonProvider.getInstance().getGson().fromJson(str_data, SalaryInfo.class);
        } catch (Exception e) {
            LogUtil.d("txy", "数据获取错误！");
            e.printStackTrace();
        }
        if (si != null) {
            ((TextView) findViewById(R.id.tv_date)).setText("" + si.getDate());
            ((TextView) findViewById(R.id.tv_base_salary)).setText("+" + si.getBaseSalary());
            ((TextView) findViewById(R.id.tv_job_subsidies)).setText("+" + si.getJobSubsidies());
            ((TextView) findViewById(R.id.tv_total_salary)).setText("+" + si.getTotalPay());
            ((TextView) findViewById(R.id.tv_sick_leave)).setText("-" + si.getSickLeave());
            ((TextView) findViewById(R.id.tv_other_subsidies)).setText("+" + si.getOtherSubsidies());
            ((TextView) findViewById(R.id.tv_individual_income_tax)).setText("-" + si.getIndividualIncomeTax());
            ((TextView) findViewById(R.id.tv_annuity)).setText("-" + si.getAnnuity());
            ((TextView) findViewById(R.id.tv_accumulation_fund)).setText("-" + si.getAccumulationFund());
            ((TextView) findViewById(R.id.tv_actual_income)).setText("+" + si.getActualIncome());
        }
    }
}
