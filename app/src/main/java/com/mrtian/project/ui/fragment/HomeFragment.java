package com.mrtian.project.ui.fragment;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mrtian.project.MyApplication;
import com.mrtian.project.R;
import com.mrtian.project.manager.UserManager;
import com.mrtian.project.model.MyUser;
import com.mrtian.project.ui.view.homepage.CompanyEvaluationActivity;
import com.mrtian.project.ui.view.homepage.EmployeeInfoActivity;
import com.mrtian.project.ui.view.homepage.RecruitmentActivity;
import com.mrtian.project.ui.view.homepage.SearchActivity;
import com.mrtian.project.ui.adapter.MyGridAdapter;
import com.mrtian.project.ui.widget.MyGridView;
import com.mrtian.project.ui.widget.TopLayout;
import com.mrtian.project.utils.AppUtils;


/**
 * Created by tianxiying on 16/7/18.
 */
public class HomeFragment extends BasePageFragment {
    private MyGridView gridview;

    @Override
    protected void initView(View view) {
        TopLayout topLayout = ((TopLayout) view.findViewById(R.id.top_rl));
        topLayout.setTitle(getString(R.string.menu_homepage));
        topLayout.setLeftAreaImage(R.drawable.search);
        topLayout.setLeftAreaOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        gridview = (MyGridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(new MyGridAdapter(getContext()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                AppUtils.toast("position :" + position + " ,id :" + id, Toast.LENGTH_SHORT);
                switch (position) {
                    case 0:    //员工基本信息
//                        if (UserManager.getUser() != null) {
//                            if (UserManager.getUser().getIdentity() == MyUser.MANAGER) {
//                                startActivity(EmployeeInfoActivity.newIntent(getContext(), getString(R.string.employee_info)));
//                            } else {
//                                AppUtils.toast("没有操作权限！", Toast.LENGTH_SHORT);
//                            }
//                        }
                        startActivity(EmployeeInfoActivity.newIntent(getContext(), getString(R.string.employee_info)));
                        break;
                    case 1:    //公司评价
                        startActivity(CompanyEvaluationActivity.newIntent(getContext(), getString(R.string.company_evaluation)));
                        break;
                    case 2:    //招聘管理
                        startActivity(RecruitmentActivity.newIntent(getContext(), getString(R.string.recruitment_manage)));
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forward;
    }

    @Override
    public void fetchData() {
    }
}
