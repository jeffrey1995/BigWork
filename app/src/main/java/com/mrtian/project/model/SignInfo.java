package com.mrtian.project.model;

import android.os.NetworkOnMainThreadException;

import com.mrtian.project.utils.Constant;

import cn.bmob.v3.BmobObject;

/**
 * Created by tianxiying on 2017/3/20.
 */

public class SignInfo extends BmobObject {
    private MyUser myUser;

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }

    /**
     * 姓名
     */
    private String personName;
    /**
     * 日期
     */
    private String yearAndMounthAndDay;
    /**
     * 签到时间
     */
    private String inTime;
    /**
     * 签到状态
     */
    private int state_sign_in;
    /**
     * 签退时间
     */
    private String outTime;
    /**
     * 签退状态
     */
    private int state_sign_out;
    /**
     * 考勤状态
     */
    private int state;

    public SignInfo() {
        state = Constant.SINGSTATE_ABNORMAL;
        state_sign_in = Constant.SINGSTATE_ABNORMAL;
        state_sign_out = Constant.SINGSTATE_ABNORMAL;
    }

    public SignInfo(String personName, String yearAndMounthAndDay, String inTime, String outTime) {
        this.personName = personName;
        this.yearAndMounthAndDay = yearAndMounthAndDay;
        this.inTime = inTime;
        this.outTime = outTime;
        state = Constant.SINGSTATE_ABNORMAL;
        state_sign_in = Constant.SINGSTATE_ABNORMAL;
        state_sign_out = Constant.SINGSTATE_ABNORMAL;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getYearAndMounthAndDay() {
        return yearAndMounthAndDay;
    }

    public void setYearAndMounthAndDay(String yearAndMounthAndDay) {
        this.yearAndMounthAndDay = yearAndMounthAndDay;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public int getState_sign_in() {
        return state_sign_in;
    }

    public void setState_sign_in(int state_sign_in) {
        this.state_sign_in = state_sign_in;
    }

    public int getState_sign_out() {
        return state_sign_out;
    }

    public void setState_sign_out(int state_sign_out) {
        this.state_sign_out = state_sign_out;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
