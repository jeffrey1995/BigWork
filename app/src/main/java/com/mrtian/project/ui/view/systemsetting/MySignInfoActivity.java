package com.mrtian.project.ui.view.systemsetting;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.jingchen.pulltorefresh.PullableListView;
import com.mrtian.project.R;
import com.mrtian.project.model.SalaryInfo;
import com.mrtian.project.model.SignInfo;
import com.mrtian.project.ui.adapter.SignInfoAdapter;
import com.mrtian.project.ui.view.base.BaseActivity;
import com.mrtian.project.utils.AppUtils;
import com.mrtian.project.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MySignInfoActivity extends BaseActivity {
    private PullToRefreshLayout refresh_rl;
    private PullableListView listView;

    private List<SignInfo> signInfoList;
    private SignInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_sign_info);
        initView();
    }

    private void initView() {
        listView = (PullableListView) findViewById(R.id.listView);
        refresh_rl = (PullToRefreshLayout) findViewById(R.id.refresh_rl);
        refresh_rl.setPullUpEnable(false);
        refresh_rl.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                LogUtil.d("txy", "触发下拉刷新了!");
                querySignInfo();
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

        signInfoList = new ArrayList<SignInfo>();
        adapter = new SignInfoAdapter(this, R.layout.sign_list_item, signInfoList);
        listView.setAdapter(adapter);
        querySignInfo();
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleName("我的考勤");
    }

    private void querySignInfo() {
        final BmobQuery<SignInfo> query = new BmobQuery<SignInfo>();
        query.addWhereEqualTo("myUser", BmobUser.getCurrentUser().getObjectId());
        query.findObjects(new FindListener<SignInfo>() {
            @Override
            public void done(List<SignInfo> list, BmobException e) {
                if (e == null) {
                    signInfoList.clear();
                    for (SignInfo signInfo : list) {
                        signInfoList.add(signInfo);
                    }
                    adapter.notifyDataSetChanged();
                    refresh_rl.refreshFinish(PullToRefreshLayout.SUCCEED);
                } else {
                    AppUtils.toast("异常,请重试！", Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
