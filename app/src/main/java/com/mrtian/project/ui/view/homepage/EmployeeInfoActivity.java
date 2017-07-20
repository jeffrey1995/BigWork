package com.mrtian.project.ui.view.homepage;

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
import com.mrtian.project.model.SignInfo;
import com.mrtian.project.ui.adapter.EmployeInfoAdapter;
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

public class EmployeeInfoActivity extends BaseActivity {
    private static final String EXTRA_ACTIVITY_TITLE = "activity_title";
    private EmployeInfoAdapter employeInfoAdapter;
    private List<EmployeeInfo> employeeInfoList;

    private PullToRefreshLayout refresh_rl;
    private PullableListView listView;

    public static Intent newIntent(Context packageContext, String activityCategoryTitle) {
        Intent intent = new Intent(packageContext, EmployeeInfoActivity.class);
        intent.putExtra(EXTRA_ACTIVITY_TITLE, activityCategoryTitle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_employee_info);
        initView();
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleName("" + getIntent().getStringExtra(EXTRA_ACTIVITY_TITLE));
        mTitleBar.setRightAreaImage(R.drawable.add);
        mTitleBar.setRightAreaOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), AddEmployeeActivity.class));
            }
        });
    }

    private void initView() {
        listView = (PullableListView) findViewById(R.id.listView);
        refresh_rl = (PullToRefreshLayout) findViewById(R.id.refresh_rl);
        refresh_rl.setPullUpEnable(false);
        refresh_rl.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                LogUtil.d("txy", "触发下拉刷新了!");
                queryEmployeeInfo();
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

        employeeInfoList = new ArrayList<EmployeeInfo>();
        queryEmployeeInfo();
        employeInfoAdapter = new EmployeInfoAdapter(this, R.layout.employee_info_list_item, employeeInfoList);
        listView.setAdapter(employeInfoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LogUtil.d("txy", "list item click :" + i);
                String str_data = GsonProvider.getInstance().getGson().toJson(employeeInfoList.get(i), EmployeeInfo.class);
                startActivity(DetailEmployeeInfoActivity.newIntent(getApplicationContext(), str_data));
            }
        });
    }

    private void queryEmployeeInfo() {
        final BmobQuery<EmployeeInfo> query = new BmobQuery<EmployeeInfo>();
        query.findObjects(new FindListener<EmployeeInfo>() {
            @Override
            public void done(List<EmployeeInfo> list, BmobException e) {
                if (e == null) {
                    employeeInfoList.clear();
                    for (EmployeeInfo ei : list) {
                        employeeInfoList.add(ei);
                    }
                    employeInfoAdapter.notifyDataSetChanged();
                    refresh_rl.refreshFinish(PullToRefreshLayout.SUCCEED);
                } else {
                    AppUtils.toast("异常,请重试！", Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
