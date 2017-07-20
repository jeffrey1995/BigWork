package com.mrtian.project.model;

import android.content.Context;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/18.
 */

public class EvaluateInfo extends BmobObject{
    /**
     * 评价内容
     */
    private String content_info;
    /**
     * 联系方式
     */
    private String contact_info;

    public EvaluateInfo(String contact_info, String content_info) {
        this.contact_info = contact_info;
        this.content_info = content_info;
    }

    public String getContent_info() {
        return content_info;
    }

    public void setContent_info(String content_info) {
        this.content_info = content_info;
    }

    public String getContact_info() {
        return contact_info;
    }

    public void setContact_info(String contact_info) {
        this.contact_info = contact_info;
    }
}
