package com.mrtian.project.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by tianxiying on 2017/3/13.
 */

public class SalaryInfo extends BmobObject{
    //基本工资、岗位津贴、工资总额、入职/离职病事假、其他补助、应付总额、个税、养老金、公积金、实际收入
    private MyUser myUser;

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }

    /**
     * 来源
     */
    private String incomeSource;
    /**
     * 日期
     */
    private String date;
    /**
     * 基本工资
     */
    private int baseSalary;
    /**
     * 岗位津贴
     */
    private int jobSubsidies;
    /**
     * 工资总额
     */
    private int totalSalary;
    /**
     * 入职/离职病事假
     */
    private int sickLeave;
    /**
     * 其他补助
     */
    private int otherSubsidies;
    /**
     * 应付总额
     */
    private int totalPay;
    /**
     * 个税
     */
    private int individualIncomeTax;
    /**
     * 养老金
     */
    private int annuity;
    /**
     * 公积金
     */
    private int accumulationFund;

    public SalaryInfo(String incomeSource, String date, int baseSalary, int jobSubsidies, int totalSalary, int sickLeave, int otherSubsidies, int totalPay, int individualIncomeTax, int annuity, int accumulationFund, int actualIncome) {
        this.incomeSource = incomeSource;
        this.date = date;
        this.baseSalary = baseSalary;
        this.jobSubsidies = jobSubsidies;
        this.totalSalary = totalSalary;
        this.sickLeave = sickLeave;
        this.otherSubsidies = otherSubsidies;
        this.totalPay = totalPay;
        this.individualIncomeTax = individualIncomeTax;
        this.annuity = annuity;
        this.accumulationFund = accumulationFund;
        this.actualIncome = actualIncome;
    }

    /**
     * 实际收入
     */


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIncomeSource() {
        return incomeSource;
    }

    public void setIncomeSource(String incomeSource) {
        this.incomeSource = incomeSource;
    }
    private int actualIncome;

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public int getJobSubsidies() {
        return jobSubsidies;
    }

    public void setJobSubsidies(int jobSubsidies) {
        this.jobSubsidies = jobSubsidies;
    }

    public int getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(int totalSalary) {
        this.totalSalary = totalSalary;
    }

    public int getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(int sickLeave) {
        this.sickLeave = sickLeave;
    }

    public int getOtherSubsidies() {
        return otherSubsidies;
    }

    public void setOtherSubsidies(int otherSubsidies) {
        this.otherSubsidies = otherSubsidies;
    }

    public int getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(int totalPay) {
        this.totalPay = totalPay;
    }

    public int getIndividualIncomeTax() {
        return individualIncomeTax;
    }

    public void setIndividualIncomeTax(int individualIncomeTax) {
        this.individualIncomeTax = individualIncomeTax;
    }

    public int getAnnuity() {
        return annuity;
    }

    public void setAnnuity(int annuity) {
        this.annuity = annuity;
    }

    public int getAccumulationFund() {
        return accumulationFund;
    }

    public void setAccumulationFund(int accumulationFund) {
        this.accumulationFund = accumulationFund;
    }

    public int getActualIncome() {
        return actualIncome;
    }

    public void setActualIncome(int actualIncome) {
        this.actualIncome = actualIncome;
    }
}
