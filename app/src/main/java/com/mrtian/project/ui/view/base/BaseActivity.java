package com.mrtian.project.ui.view.base;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.mrtian.project.R;
import com.mrtian.project.ui.widget.CommonDialog;
import com.mrtian.project.ui.widget.ModifyDialogListener;
import com.mrtian.project.ui.widget.TopLayout;
import com.mrtian.project.utils.ActivityCollector;


/**
 * 所有Activity继承于BaseActivity
 * 方便管理所有的活动
 * 方便测试
 * Created by mrtian on 2016/7/7.
 */
public class BaseActivity extends AppCompatActivity {
    private final static String TAG = "BaseActivity";
    protected Context mContext = this;
    protected String moduleName = "";
    protected TopLayout mTitleBar;
    protected ViewGroup mRootViewGroup;
    protected View view_overall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ActivityCollector.addActivity(this);
        mTitleBar = (TopLayout) findViewById(R.id.top_rl);
        mRootViewGroup = (ViewGroup) findViewById(R.id.view_layout);
        view_overall = findViewById(R.id.view_overall);
        initTitleBar();
    }

    protected void initTitleBar() {

    }

    public void setTitleName(String name) {
        mTitleBar.setTitle(name);
    }

    public void setmTitleBarEnable(Boolean value) {
        if (!value) mTitleBar.setVisibility(View.GONE);
        else mTitleBar.setVisibility(View.VISIBLE);
    }

    public void setMainView(@LayoutRes int layoutResID) {
        View mainView = this.getLayoutInflater().inflate(layoutResID, null);
        mRootViewGroup.addView(mainView, mRootViewGroup.getLayoutParams());
    }

    public void setMainView(View view) {
        mRootViewGroup.addView(view);
    }

    public void setMainView(View view, ViewGroup.LayoutParams params) {
        mRootViewGroup.addView(view, params);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


    /**
     * 弹出普通对话框
     *
     * @param title
     * @param msg
     * @param posTitle
     * @param negTitle
     * @param index
     * @return
     */
    protected Dialog showAlertDlg(String title, String msg, String posTitle,
                                  String negTitle, final int index) {
        final CommonDialog editableDialog = new CommonDialog(this, new ModifyDialogListener() {
            @Override
            public void onResponse(int result, Object data) {
                if (result == CommonDialog.POSITIVE) {
                    workOkBtn(index, data);
                } else if(result == CommonDialog.NEGATIVE) {
                    workCancelBtn(index, data);
                }
            }
        });
        editableDialog.setTitleName(title).setMsg(msg).setPosTitle(posTitle).setNegTitle(negTitle).show();
        return editableDialog;
    }

    protected Dialog showAlertEditDlg(String title, CharSequence msg, String posTitle,
                                      String negTitle, final int index) {
        final CommonDialog editableDialog = new CommonDialog(this, new ModifyDialogListener() {
            @Override
            public void onResponse(int result, Object data) {
                if (result == CommonDialog.POSITIVE) {
                    workOkBtn(index, data);
                } else if(result == CommonDialog.NEGATIVE) {
                    workCancelBtn(index, data);
                }
            }
        });
        editableDialog.setTitleName(title).setPosTitle(posTitle).setNegTitle(negTitle).setEditable().show();
        return editableDialog;
    }


    /**
     * 确认按钮回调函数
     *
     * @param index
     * @param data
     */
    public void workOkBtn(int index, Object data) {
    }

    /**
     * 取消按钮回调函数
     *
     * @param index
     * @param data
     */
    public void workCancelBtn(int index, Object data) {
    }
}
