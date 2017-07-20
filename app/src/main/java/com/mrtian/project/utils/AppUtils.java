package com.mrtian.project.utils;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mrtian.project.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.Request;
import okio.Buffer;

/**
 * Created by tianxiying on 2016/12/21.
 */

public class AppUtils {
    private static Context mContext;
    private static Toast mToast;
    private static String strContent = "";
    public static String HEADIMAGEPATH;
    public static String HEADIMAGNAME;

    public static void init(Context context) {
        mContext = context;
        Context applicationContext;
        if (context instanceof Application) applicationContext = context;
        else applicationContext = context.getApplicationContext();
        mToast = new Toast(context);
        View view = View.inflate(applicationContext, R.layout.toast_layout, null);
        mToast.setView(view);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        HEADIMAGEPATH = context.getApplicationContext().getCacheDir() + "/bmob/";
        HEADIMAGNAME = "head_image.jpg";
    }

    /**
     * 获取存放头像的Uri
     *
     * @return
     */
    public static Uri getHeadUri() {
        //初始化imageUri
        File file = new File(HEADIMAGEPATH);
        if (!file.exists()) {
            file.mkdir();
        }
        return Uri.fromFile(new File(HEADIMAGEPATH, "head_image.jpg"));
    }

    // Drawable转换成Bitmap
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    // 图片转为文件
    public static boolean saveBitmap2file(Bitmap bmp) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.PNG;
        int quality = 100;
        OutputStream stream = null;
        try {
            // 判断SDcard状态
            if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                // 错误提示
                return false;
            }

            // 检查SDcard空间
            File SDCardRoot = Environment.getExternalStorageDirectory();
            if (SDCardRoot.getFreeSpace() < 10000) {
                // 弹出对话框提示用户空间不够
                Log.e("Utils", "存储空间不够");
                return false;
            }

            // 在SDcard创建文件夹及文件
            File bitmapFile = new File(SDCardRoot.getPath() + HEADIMAGEPATH);
            bitmapFile.getParentFile().mkdirs();// 创建文件夹
            stream = new FileOutputStream(SDCardRoot.getPath() + HEADIMAGEPATH);// "/sdcard/"
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bmp.compress(format, quality, stream);
    }

    public static Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            // 先通过getContentResolver方法获得一个ContentResolver实例，
            // 调用openInputStream(Uri)方法获得uri关联的数据流stream
            // 把上一步获得的数据流解析成为bitmap
            bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 防止重复弹出相同信息
     *
     * @param msg
     * @param duration
     */
    public static void toast(String msg, int duration) {
        if (mToast == null) {
            throw new RuntimeException("ToastUtil uninitialized, you must call ToastUtil.init(Context) to initial ToastUtil before you use it!");
        }
        TextView toastTextView = (TextView) mToast.getView().findViewById(R.id.toast_text_view);
        toastTextView.setText(msg);
        mToast.setDuration(duration);
        mToast.show();
        strContent = msg;
    }

    public static void copyToClipBoard(Context context, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(
                Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("xdroid_copy", text));
        Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
    }

    public static void openInBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "打开失败了，没有可打开的应用", Toast.LENGTH_SHORT).show();
        }
    }

    public static void shareText(Context context, String shareText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "分享"));
    }

    public static void shareImage(Context context, Uri uri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }

    public static boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }

    /**
     * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }
    }
}
