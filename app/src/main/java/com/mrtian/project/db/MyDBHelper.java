package com.mrtian.project.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tianxiying on 16/7/14.
 */
public class MyDBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "forward.db";
    private static final int DATABASE_VERSION = 1;

    public MyDBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS forward_history" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,news_id VARCHAR,title VARCHAR, forward_money VARCHAR,type INTEGER, date VARCHAR)");
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE forward_history ADD COLUMN other STRING");
    }
}
