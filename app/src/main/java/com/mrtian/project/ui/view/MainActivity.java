package com.mrtian.project.ui.view;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mrtian.project.R;
import com.mrtian.project.ui.view.base.BaseActivity;
import com.mrtian.project.ui.fragment.HomeFragment;
import com.mrtian.project.ui.fragment.SignFragment;
import com.mrtian.project.ui.fragment.MineFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Fragment currentFragment, homeFragment, signFragment, mineFragment;
    private FragmentManager mFragmentManager;
    private RadioGroup rg;
    private RadioButton rb_home, rb_course, rb_direct_seeding;
    private int position = 0;   //tab标志
    private int tabSize = 70;   //tab图片的大小

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_main);
        getFragment();
        findView();
        setView();

        ((RadioButton) rg.getChildAt(position)).setChecked(true);
        currentFragment.setUserVisibleHint(true);
        initListener();
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setmTitleBarEnable(false);
    }

    /**
     * 监听事件
     */
    private void initListener() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                if (false == radioButton.isChecked()) {
                    return;
                }
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        switchContent(currentFragment, homeFragment);
                        break;

                    case R.id.rb_course:
                        position = 1;
                        switchContent(currentFragment, signFragment);
                        break;

                    case R.id.rb_direct_seeding:
                        position = 2;
                        switchContent(currentFragment, mineFragment);
                        break;
                    default:
                        position = 0;
                        break;
                }

            }
        });
    }

    /**
     * 实例化Fragment
     */
    private void getFragment() {
        homeFragment = new HomeFragment();
        signFragment = new SignFragment();
        mineFragment = new MineFragment();
        mFragmentManager = getSupportFragmentManager();
        if (mFragmentManager.beginTransaction().isEmpty()) {
            mFragmentManager.beginTransaction().add(R.id.main_content_ll, homeFragment).commit();
        }
        currentFragment = homeFragment;
    }

    /**
     * 切换fragment
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (currentFragment != to) {
            currentFragment = to;
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from);
                transaction.add(R.id.main_content_ll, to);
                transaction.commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from); // 隐藏当前的fragment，显示下一个
                transaction.show(to);
                transaction.commit();
            }
            //设置Fragment的可见状态
            from.setUserVisibleHint(false);
            to.setUserVisibleHint(true);
        }
    }

    private void findView() {
        rg = (RadioGroup) findViewById(R.id.rg);
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_course = (RadioButton) findViewById(R.id.rb_course);
        rb_direct_seeding = (RadioButton) findViewById(R.id.rb_direct_seeding);
    }

    /**
     * 调整View的属性
     */
    private void setView() {
        //定义底部标签图片大小
        int[] id_rb_img = {R.drawable.footer_home_ico, R.drawable.footer_course_ioc, R.drawable.footer_direct_ioc};
        RadioButton[] rb = {rb_home, rb_course, rb_direct_seeding};
        for (int i = 0; i < rb.length; i++) {
            Drawable drawable = getResources().getDrawable(id_rb_img[i]);
            drawable.setBounds(0, 0, tabSize, tabSize);//第一0是距左右边距离，第二0是距上下边距离，第三长度,第四宽度
            rb[i].setCompoundDrawables(null, drawable, null, null);//只放上面
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}
