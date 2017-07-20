package com.mrtian.project.model;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by tianxiying on 2017/3/8.
 */

public class EmployeeInfo extends BmobObject{
    //头像、姓名、性别、职工号、生日、学历、工资、住址、电话、邮箱、所属部门

    /**
     * 关联用户
     */
    private MyUser myUser;

    public EmployeeInfo() {
    }

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }

    /**
     * 头像
     */
    private BmobFile employeeHeadImage;
    /**
     * 姓名
     */
    private String employeeName;

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别
     */
    private String employeeSex;
    /**
     * 职工号
     */
    private String employeeNumber;
    /**
     * 生日
     */
    private String employeeBirthday;
    /**
     * 学历
     */
    private String employeeEducation;
    /**
     * 工资
     */
    private String employeeSalary;
    /**
     * 住址
     */
    private String employeeAddress;
    /**
     * 电话
     */
    private String employeePhoneNumber;
    /**
     * 邮箱
     */
    private String employeeEmail;
    /**
     * 所属部门
     */
    private String employeeDepartment;

    public EmployeeInfo(BmobFile employeeHeadImage, String employeeName, String nickName, String employeeSex, String employeeNumber, String employeeBirthday, String employeeEducation, String employeeSalary, String employeeAddress, String employeePhoneNumber, String employeeEmail, String employeeDepartment) {
        this.employeeHeadImage = employeeHeadImage;
        this.employeeName = employeeName;
        this.nickName = nickName;
        this.employeeSex = employeeSex;
        this.employeeNumber = employeeNumber;
        this.employeeBirthday = employeeBirthday;
        this.employeeEducation = employeeEducation;
        this.employeeSalary = employeeSalary;
        this.employeeAddress = employeeAddress;
        this.employeePhoneNumber = employeePhoneNumber;
        this.employeeEmail = employeeEmail;
        this.employeeDepartment = employeeDepartment;
    }

    public BmobFile getEmployeeHeadImage() {
        return employeeHeadImage;
    }

    public void setEmployeeHeadImage(BmobFile employeeHeadImage) {
        this.employeeHeadImage = employeeHeadImage;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmployeeSex() {
        return employeeSex;
    }

    public void setEmployeeSex(String employeeSex) {
        this.employeeSex = employeeSex;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeeBirthday() {
        return employeeBirthday;
    }

    public void setEmployeeBirthday(String employeeBirthday) {
        this.employeeBirthday = employeeBirthday;
    }

    public String getEmployeeEducation() {
        return employeeEducation;
    }

    public void setEmployeeEducation(String employeeEducation) {
        this.employeeEducation = employeeEducation;
    }

    public String getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(String employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeePhoneNumber() {
        return employeePhoneNumber;
    }

    public void setEmployeePhoneNumber(String employeePhoneNumber) {
        this.employeePhoneNumber = employeePhoneNumber;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }
}
