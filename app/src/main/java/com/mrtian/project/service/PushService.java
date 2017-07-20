package com.mrtian.project.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;


import com.mrtian.project.MyApplication;
import com.mrtian.project.R;
import com.mrtian.project.ui.view.MainActivity;

import java.util.Random;

/**
 * Created by tianxiying on 16/7/19.
 */
public class PushService extends Service {
    private String msg;
    private String topic;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        //根据匹配类型处理
        if(intent.getAction().equals(Intent.ACTION_EDIT)){
            topic=intent.getStringExtra("title");
            msg=intent.getStringExtra("content");
        }

        //获取设置的提示音，用的是系统声音
        Uri alarmSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        /**
         * 初始化通知提示框的布局，即在NotificationCompat.Builder对象中为通知指定UI信息和动作
         * 布局里有提示图标，一般是应用的logo，提示的标题，及内容，有声音设置，震动设置
         * 所有其他通知设置和内容都是可选的，具体可参考API NotificationCompat.Builder类
         */
        NotificationCompat.Builder nBuilder=new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher).setContentText(msg).setContentTitle(topic)
                .setSound(alarmSound).setVibrate(new long[]{500,500,500,500}).setAutoCancel(true);

        //单击消息后连接打开的Avtivity
        Intent reslutIntent=new Intent(MyApplication.getContext(),MainActivity.class);
        reslutIntent.putExtra("topic", topic);
        reslutIntent.putExtra("msg", msg);

        //添加动作到通知，当用户点击通知时，触发
        //在通知里面，这个动作通过PendingIntent来定义
        //想要启动Activity，可以通过调用setContentIntent()来添加一个PendingIntent
        TaskStackBuilder stackBuilder= TaskStackBuilder.create(getApplicationContext());//
        stackBuilder.addParentStack(MainActivity.class); //将一个Activity先入栈
        stackBuilder.addNextIntent(reslutIntent);
        PendingIntent resultPendingIntent=stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(resultPendingIntent);//设置显示框内容点击后对应的任务目标，及进入相应的activity

        /**
         * 消息管理器：NotificationManager
         * nBuilder.build()： 发送前必须先使用NotificationCompat.Builder.build()来创建通知
         * 这个方法会返回一个Notification对象
         * 为了发布通知，可以通过调用NotificationManager.notify()来传递Notification对象到系统中
         */
        NotificationManager mNotificationManager=(NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        Random r=new Random();
        mNotificationManager.notify(r.nextInt(), nBuilder.build());//发生通知，产生消息框

        stopSelf();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
