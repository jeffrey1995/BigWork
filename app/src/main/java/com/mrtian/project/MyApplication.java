package com.mrtian.project;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.mrtian.project.db.ShareManager;
import com.mrtian.project.manager.UserManager;
import com.mrtian.project.model.EmployeeInfo;
import com.mrtian.project.model.User;
import com.mrtian.project.utils.AppUtils;
import com.mrtian.project.utils.GsonProvider;
import com.mrtian.project.utils.LogUtil;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.helper.GsonUtil;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * Created by tianxiying on 16/12/21.
 */
public class MyApplication extends Application {
    private static String TAG = "MyApplication";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        AppUtils.init(this);    //初始化工具类
        UserManager.initUserManager(this);
        initBmob();
    }

    /**
     * 初始化Bmob
     */
    private void initBmob() {
        //第一：默认初始化
        Bmob.initialize(this, "97c633f0b306db80e733bdb0a8d99846");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("97c633f0b306db80e733bdb0a8d99846")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
    }

    public static Context getContext() {
        return context;
    }
}
