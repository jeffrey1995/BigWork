package com.mrtian.project.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by tianxiying on 2017/4/17.
 */

public class MyUser extends BmobUser {
    public static final int EMPLOYEE = 0;
    public static final int MANAGER = 1;

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    private int identity = EMPLOYEE;
}
