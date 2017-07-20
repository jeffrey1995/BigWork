package com.mrtian.project.ui.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.mrtian.project.R;
import com.mrtian.project.manager.UserManager;
import com.mrtian.project.model.SalaryInfo;
import com.mrtian.project.model.SignInfo;
import com.mrtian.project.ui.widget.CalendarView;
import com.mrtian.project.ui.widget.TopLayout;
import com.mrtian.project.utils.AppUtils;
import com.mrtian.project.utils.LogUtil;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by tianxiying on 16/7/18.
 */
public class SignFragment extends BasePageFragment implements View.OnClickListener {
    private CalendarView calendarView;
    private SignInfo[] sign = new SignInfo[32];
    private TextView detail_tv;
    private List<SignInfo> list_sign;

    @Override
    protected void initView(View view) {
        ((TopLayout) view.findViewById(R.id.top_rl)).setTitle(getString(R.string.menu_attendance));
        ((TopLayout) view.findViewById(R.id.top_rl)).setLeftAreaVisble(false);
        calendarView = (CalendarView) view.findViewById(R.id.calendar);
        detail_tv = (TextView) view.findViewById(R.id.detail_tv);
        list_sign = new ArrayList<SignInfo>();
        querySignInfo();
        calendarView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void OnItemClick(Date date) {
                updateCalender();
                SimpleDateFormat ft = new SimpleDateFormat("yyyy MM dd");
                int m = Integer.parseInt(calendarView.getYearAndmonthNumber().split(" ")[1]);
                int m0 = Integer.parseInt(ft.format(date).split(" ")[1]);
                if (m == m0) {
                    int d = Integer.parseInt((ft.format(date).split(" "))[2]);
                    if (sign[d] != null) {
                        detail_tv.setText("签到时间：" + sign[d].getInTime().toString() + ", 签退时间：" + sign[d].getOutTime().toString());
                    } else {
                        detail_tv.setText("没有记录");
                    }
                }
            }
        });
        view.findViewById(R.id.sign_in_btn).setOnClickListener(this);
        view.findViewById(R.id.sign_out_btn).setOnClickListener(this);
    }

    private void updateCalender() {
        String curYearAndMonth = calendarView.getYearAndmonthNumber();
        String curYearAndMonthAndDay = "";
        for (int i = 1; i <= 31; i++) {
            sign[i] = null;
            curYearAndMonthAndDay = curYearAndMonth + " " + i;
            for (SignInfo signInfo : list_sign) {
                if (signInfo.getYearAndMounthAndDay().equals(curYearAndMonthAndDay)) {
                    sign[i] = signInfo;
                }
            }
        }
        calendarView.setList_sign(list_sign);
        calendarView.upDateView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invitation;
    }

    @Override
    public void fetchData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_btn:
                final BmobQuery<SignInfo> query = new BmobQuery<SignInfo>();
                query.addWhereEqualTo("yearAndMounthAndDay", getYearAndmonthNumber());
                query.addWhereEqualTo("myUser", BmobUser.getCurrentUser().getObjectId());
                query.findObjects(new FindListener<SignInfo>() {
                    @Override
                    public void done(List<SignInfo> list, BmobException e) {
                        if (e == null) {
                            if (list.size() == 0) {
                                SignInfo signInfo = new SignInfo("", getYearAndmonthNumber(), getHourAndMinuteNumber(), " ");
                                signInfo.setMyUser(UserManager.getUser());
                                signInfo.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            AppUtils.toast(getString(R.string.sign_in_success), Toast.LENGTH_SHORT);
                                        }
                                    }
                                });
                            } else {
                                AppUtils.toast("已签到！", Toast.LENGTH_SHORT);
                                querySignInfo();
                            }
                        } else {
                            AppUtils.toast("异常,请重试！", Toast.LENGTH_SHORT);
                        }
                    }
                });
                break;
            case R.id.sign_out_btn:
                BmobQuery<SignInfo> query0 = new BmobQuery<SignInfo>();
                query0.addWhereEqualTo("yearAndMounthAndDay", getYearAndmonthNumber());
                query0.addWhereEqualTo("myUser", BmobUser.getCurrentUser().getObjectId());
                query0.findObjects(new FindListener<SignInfo>() {
                    @Override
                    public void done(List<SignInfo> list, BmobException e) {
                        if (e == null) {
                            if (list.size() == 0) {
                                AppUtils.toast("未签到！", Toast.LENGTH_SHORT);
                            } else {
                                final SignInfo signInfo = new SignInfo();
                                signInfo.setOutTime(getHourAndMinuteNumber());
//                                LogUtil.d("txy", getYearAndmonthNumber() + "id:" + si.getObjectId() + "outTime:" + si.getOutTime());
                                list.get(0).setOutTime(getHourAndMinuteNumber());
//                                LogUtil.d("txy", "id:" + si.getObjectId() + "outTime:" + si.getOutTime());
                                signInfo.update(list.get(0).getObjectId(),new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            AppUtils.toast(getString(R.string.sign_out_success), Toast.LENGTH_SHORT);
                                            querySignInfo();
                                        } else {
                                            LogUtil.d("txy", e.getMessage());
                                            AppUtils.toast("签退失败,请重试！", Toast.LENGTH_SHORT);
                                        }
                                    }
                                });
                            }
                        } else {
                            LogUtil.d("txy", e.getMessage());
                            AppUtils.toast("异常,请重试！", Toast.LENGTH_SHORT);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    private String getYearAndmonthNumber() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return year + " " + (month + 1) + " " + day;
    }

    private String getHourAndMinuteNumber() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return hour + ":" + minute;
    }

    private void querySignInfo() {
        final BmobQuery<SignInfo> query = new BmobQuery<SignInfo>();
        query.addWhereEqualTo("myUser", BmobUser.getCurrentUser().getObjectId());
        query.findObjects(new FindListener<SignInfo>() {
            @Override
            public void done(List<SignInfo> list, BmobException e) {
                if (e == null) {
                    list_sign.clear();
                    for (SignInfo signInfo : list) {
                        list_sign.add(signInfo);
                    }
                    updateCalender();
                } else {
                    AppUtils.toast("异常,请重试！", Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
