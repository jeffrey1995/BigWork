package com.mrtian.project.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrtian.project.R;
import com.mrtian.project.manager.UserManager;
import com.mrtian.project.ui.view.systemsetting.MyInfoActivity;
import com.mrtian.project.ui.view.systemsetting.MySignInfoActivity;
import com.mrtian.project.ui.view.systemsetting.PersonalCenterActivity;
import com.mrtian.project.ui.view.systemsetting.MySalaryInfoActivity;
import com.mrtian.project.ui.view.systemsetting.SettingActivity;
import com.mrtian.project.ui.widget.waveview.WaveHelper;
import com.mrtian.project.ui.widget.waveview.WaveView;

/**
 * Created by tianxiying on 16/7/18.
 */
public class MineFragment extends BasePageFragment implements View.OnClickListener {
    private LinearLayout itemLayout;
    private ImageView head_img;
    private TextView username_tv;
    private TextView description_tv;
    public static boolean UPDATE_HEAD_IMAGE = false;
    public static boolean UPDATE_USERNAME = false;

    private WaveView waveView;
    private WaveHelper waveHelper;
    private Handler handler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateInfo();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                waveHelper.resume();
            }
        },500);
    }

    @Override
    public void onPause() {
        super.onPause();
        waveHelper.pause();
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.top_rl).setOnClickListener(this);
        itemLayout = (LinearLayout) view.findViewById(R.id.item_ll);
        head_img = (ImageView) view.findViewById(R.id.head_img);
        username_tv = (TextView) view.findViewById(R.id.tv_userName);
        description_tv = (TextView) view.findViewById(R.id.tv_description);

        if (!TextUtils.isEmpty(UserManager.getMyInfo().getEmployeeDepartment())) {
            description_tv.setText(UserManager.getMyInfo().getEmployeeDepartment());
        }

        if (UserManager.getHeadImage() != null) {
            head_img.setImageBitmap(UserManager.getHeadImage());
        }
        if (UserManager.getMyInfo() != null && UserManager.getMyInfo().getEmployeeName() != null) {
            username_tv.setText(UserManager.getMyInfo().getEmployeeName());
        }

        addItemView(getString(R.string.my_info), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyInfoActivity.class));
            }
        });
        addItemView(getString(R.string.my_salary), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MySalaryInfoActivity.class));
            }
        });
        addItemView(getString(R.string.my_attendance), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MySignInfoActivity.class));
            }
        });
        addItemView(getString(R.string.setting), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });

        waveView = (WaveView) view.findViewById(R.id.wave_view);
        waveHelper = new WaveHelper(waveView);
        waveHelper.start();
    }

    private void updateInfo() {
        if (UPDATE_HEAD_IMAGE) {
            if (UserManager.getHeadImage() != null) {
                head_img.setImageBitmap(UserManager.getHeadImage());
            }
            UPDATE_HEAD_IMAGE = false;
        }
        if (UPDATE_USERNAME) {
            if (UserManager.getMyInfo() != null && UserManager.getMyInfo().getEmployeeName() != null) {
                username_tv.setText(UserManager.getMyInfo().getEmployeeName());
            }
            UPDATE_USERNAME = false;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void fetchData() {
//        MyOkHttp.get().get(this.getActivity(), "http://www.baidu.com", null, new RawResponseHandler() {
//            @Override
//            public void onSuccess(int i, String s) {
//                LogUtil.d("txy", s);
//                AppUtils.toast(s, Toast.LENGTH_SHORT);
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                LogUtil.d("txy", s);
//                AppUtils.toast(s, Toast.LENGTH_SHORT);
//            }
//        });
    }

    protected void addItemView(String name, View.OnClickListener listener) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) itemLayout.getLayoutParams();
        View view = this.getActivity().getLayoutInflater().inflate(R.layout.system_item, null);
        ((TextView) view.findViewById(R.id.item_tv)).setText(name);
        view.setOnClickListener(listener);
        lp.setMargins(0, 3, 0, 3);
        itemLayout.addView(view, lp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_rl:
                startActivity(new Intent(getActivity(), PersonalCenterActivity.class));
                break;
            default:
                break;
        }
    }
}
