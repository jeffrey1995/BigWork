package com.mrtian.project.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by tianxiying on 16/7/14.
 */
public class MyDBManager {
    private MyDBHelper helper;
    private SQLiteDatabase db;

    public MyDBManager(Context context) {
        helper = new MyDBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

//    /**
//     * add forwardHistory
//     *
//     * @param forwardHistories
//     */
//    public void add(List<ForwardHistory> forwardHistories) {
//        db.beginTransaction();  //开始事务
//        try {
//            for (ForwardHistory fh : forwardHistories) {
//                ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据
//                cv.put("news_id",fh.getNews_id());
//                cv.put("title",fh.getTitle());
//                cv.put("forward_money",fh.getForward_money());
//                cv.put("type",fh.getType());
//                cv.put("date",fh.getDate());
//                db.insert("forward_history",null,cv);
//            }
//            db.setTransactionSuccessful();  //设置事务成功完成
//        } finally {
//            db.endTransaction();    //结束事务
//        }
//    }
//
//
//    /**
//     * query all forwardHistory, return list
//     *
//     * @return List<ForwardHistory>
//     */
//    public List<ForwardHistory> query() {
//        ArrayList<ForwardHistory> history_list = new ArrayList<ForwardHistory>();
//        Cursor c = queryTheCursor();
//        while (c.moveToNext()) {
//            ForwardHistory forwardHistory = new ForwardHistory();
//            forwardHistory.setId(c.getString(c.getColumnIndex("_id")));
//            forwardHistory.setNews_id(c.getString(c.getColumnIndex("news_id")));
//            forwardHistory.setTitle(c.getString(c.getColumnIndex("title")));
//            forwardHistory.setForward_money(c.getString(c.getColumnIndex("forward_money")));
//            forwardHistory.setType(c.getInt(c.getColumnIndex("type")));
//            forwardHistory.setDate(c.getString(c.getColumnIndex("date")));
//            history_list.add(forwardHistory);
//        }
//        c.close();
//        return history_list;
//    }
//
//    /**
//     * 查询数据库中是否包含相同ID的数据
//     * @param news_id
//     * @return
//     */
//    public boolean isExist(String news_id) {
//        boolean result = false;
//        Cursor c = queryTheCursor();
//        while (c.moveToNext()) {
//            if (c.getString(c.getColumnIndex("news_id")).equals(news_id)) {
//                result = true;
//                break;
//            }
//        }
//        c.close();
//        return result;
//    }
//
//    /**
//     * query all persons, return cursor
//     *
//     * @return Cursor
//     */
//    public Cursor queryTheCursor() {
//        Cursor c = db.rawQuery("SELECT * FROM forward_history", null);
//        return c;
//    }
//
//    /**
//     * 从数据库中移除某条数据
//     * @param news_id 新闻ID
//     */
//    public void remove(String news_id)
//    {
//        db.execSQL("DELETE FROM forward_history where news_id=" + news_id);
//    }
//
//    /**
//     * close database
//     */
    public void closeDB() {
        db.close();
    }
}
