package com.mrtian.project.ui.view.systemsetting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrtian.project.MyApplication;
import com.mrtian.project.R;
import com.mrtian.project.manager.UserManager;
import com.mrtian.project.model.EmployeeInfo;
import com.mrtian.project.ui.fragment.MineFragment;
import com.mrtian.project.ui.view.base.BaseActivity;

public class MyInfoActivity extends BaseActivity {
    private LinearLayout itemLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_my_info);
        itemLayout = (LinearLayout) findViewById(R.id.view_ll);
        initItemView(UserManager.getMyInfo());
    }

    private void initItemView(EmployeeInfo emInfo) {
        EmployeeInfo employeeInfo = emInfo;
        if (employeeInfo != null) {
            addItemView(getString(R.string.employee_name), employeeInfo.getEmployeeName());
            addItemView(getString(R.string.employee_sex), employeeInfo.getEmployeeSex());
            addItemView(getString(R.string.employee_number), employeeInfo.getEmployeeNumber());
            addItemView(getString(R.string.employee_birthday), employeeInfo.getEmployeeBirthday());
            addItemView(getString(R.string.employee_education), employeeInfo.getEmployeeEducation());
            addItemView(getString(R.string.employee_salary), employeeInfo.getEmployeeSalary());
            addItemView(getString(R.string.employee_address), employeeInfo.getEmployeeAddress());
            addItemView(getString(R.string.employee_phone), employeeInfo.getEmployeePhoneNumber());
            addItemView(getString(R.string.employee_email), employeeInfo.getEmployeeEmail());
        }
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBar.setTitle("我的信息");
    }

    private int getMark(String name) {
        if (name.equals(getString(R.string.employee_name))) {
            return 1;
        } else if (name.equals(getString(R.string.employee_sex))) {
            return 2;
        } else if (name.equals(getString(R.string.employee_number))) {
            return 3;
        } else if (name.equals(getString(R.string.employee_birthday))) {
            return 4;
        } else if (name.equals(getString(R.string.employee_education))) {
            return 5;
        } else if (name.equals(getString(R.string.employee_salary))) {
            return 6;
        } else if (name.equals(getString(R.string.employee_address))) {
            return 7;
        } else if (name.equals(getString(R.string.employee_phone))) {
            return 8;
        } else if (name.equals(getString(R.string.employee_email))) {
            return 9;
        }
        return 0;
    }

    protected void addItemView(final String name, final String value) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) itemLayout.getLayoutParams();
        View view = this.getLayoutInflater().inflate(R.layout.employee_info_item, null);
        ((TextView) view.findViewById(R.id.item_name_tv)).setText(name);
        ((TextView) view.findViewById(R.id.item_value_tv)).setText(value);
        lp.setMargins(0, 0, 0, 3);
        itemLayout.addView(view, lp);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertEditDlg("修改" + name, "", "确定", "取消", getMark(name));
            }
        });
    }

    @Override
    public void workOkBtn(int index, Object data) {
        if (!TextUtils.isEmpty((String) data)) {
            EmployeeInfo employeeInfo = UserManager.getMyInfo();
            switch (index) {
                case 1:
                    employeeInfo.setEmployeeName((String) data);
                    MineFragment.UPDATE_USERNAME = true;
                    break;
                case 2:
                    employeeInfo.setEmployeeSex((String) data);
                    break;
                case 3:
                    employeeInfo.setEmployeeNumber((String) data);
                    break;
                case 4:
                    employeeInfo.setEmployeeBirthday((String) data);
                    break;
                case 5:
                    employeeInfo.setEmployeeEducation((String) data);
                    break;
                case 6:
                    employeeInfo.setEmployeeSalary((String) data);
                    break;
                case 7:
                    employeeInfo.setEmployeeAddress((String) data);
                    break;
                case 8:
                    employeeInfo.setEmployeePhoneNumber((String) data);
                    break;
                case 9:
                    employeeInfo.setEmployeeEmail((String) data);
                    break;
            }
            UserManager.updatePersonInfo(employeeInfo);
            itemLayout.removeAllViews();
            initItemView(employeeInfo);
        }
    }

    @Override
    public void workCancelBtn(int index, Object data) {
        super.workCancelBtn(index, data);
    }
}
