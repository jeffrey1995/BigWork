package com.mrtian.project.model;

/**
 * Created by tianxiying on 2017/3/23.
 */

public class User {
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 个人信息
     */
    private EmployeeInfo personInfo;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public EmployeeInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(EmployeeInfo personInfo) {
        this.personInfo = personInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
