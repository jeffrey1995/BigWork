package com.mrtian.project.ui.view.systemsetting;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.jingchen.pulltorefresh.PullableListView;
import com.mrtian.project.R;
import com.mrtian.project.model.EmployeeInfo;
import com.mrtian.project.model.MyUser;
import com.mrtian.project.model.SalaryInfo;
import com.mrtian.project.ui.adapter.SalaryInfoAdapter;
import com.mrtian.project.ui.view.base.BaseActivity;
import com.mrtian.project.utils.AppUtils;
import com.mrtian.project.utils.GsonProvider;
import com.mrtian.project.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MySalaryInfoActivity extends BaseActivity {
    private static final String EXTRA_ACTIVITY_TITLE = "activity_title";

    private PullToRefreshLayout refresh_rl;
    private PullableListView listView;
    private List<SalaryInfo> list_salary;
    private SalaryInfoAdapter salaryInfoAdapter;

    public static Intent newIntent(Context packageContext, String activityCategoryTitle) {
        Intent intent = new Intent(packageContext, MySalaryInfoActivity.class);
        intent.putExtra(EXTRA_ACTIVITY_TITLE, activityCategoryTitle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_salary);
        init();
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleName("我的工资");
    }

    private void init() {
        listView = (PullableListView) findViewById(R.id.listView);
        refresh_rl = (PullToRefreshLayout) findViewById(R.id.refresh_rl);
        refresh_rl.setPullUpEnable(false);
        refresh_rl.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                LogUtil.d("txy", "触发下拉刷新了!");
                querySalaryInfo();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                LogUtil.d("txy", "触发下拉加载了!");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh_rl.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }, 2000);
            }
        });
        querySalaryInfo();
        list_salary = new ArrayList<SalaryInfo>();
        salaryInfoAdapter = new SalaryInfoAdapter(this, R.layout.salary_list_item, list_salary);
        listView.setAdapter(salaryInfoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str_data = GsonProvider.getInstance().getGson().toJson(list_salary.get(i), SalaryInfo.class);
                startActivity(DetailSalaryInfoActivity.newIntent(getApplicationContext(), str_data));
            }
        });
        querySalaryInfo();
    }

    private void querySalaryInfo() {
        final BmobQuery<SalaryInfo> query = new BmobQuery<SalaryInfo>();
        query.addWhereEqualTo("myUser", BmobUser.getCurrentUser().getObjectId());
        query.findObjects(new FindListener<SalaryInfo>() {
            @Override
            public void done(List<SalaryInfo> list, BmobException e) {
                if (e == null) {
                    list_salary.clear();
                    for (SalaryInfo salaryInfo : list) {
                        list_salary.add(salaryInfo);
                    }
                    salaryInfoAdapter.notifyDataSetChanged();
                    refresh_rl.refreshFinish(PullToRefreshLayout.SUCCEED);
                } else {
                    AppUtils.toast("异常,请重试！",Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
