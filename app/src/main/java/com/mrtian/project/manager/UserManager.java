package com.mrtian.project.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.mrtian.project.db.ShareManager;
import com.mrtian.project.model.EmployeeInfo;
import com.mrtian.project.model.MyUser;
import com.mrtian.project.utils.AppUtils;
import com.mrtian.project.utils.GsonProvider;
import com.mrtian.project.utils.LogUtil;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.helper.GsonUtil;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by tianxiying on 2017/4/19.
 */

public class UserManager {
    private static MyUser user;
    private static Bitmap headImage;
    public static String cachePath = Environment.getExternalStorageDirectory().getPath() + "/temp/";
    public static Uri imageUri = Uri.fromFile(new File(cachePath, "head_image.jpg")); //临时uri，保存拍照或者相册选取的头像图片
    private static Context mContext;
    private static ShareManager shareManager;
    private static EmployeeInfo myInfo;

    public static MyUser getUser() {
        if (user == null) {
            user = GsonProvider.getInstance().getGson().fromJson(shareManager.getMyUser(), MyUser.class);
        }
        return user;
    }

    public static void setUser(MyUser user) {
        UserManager.user = user;
        shareManager.setMyUser(GsonProvider.getInstance().getGson().toJson(user));
    }

    public static void initUserManager(Context context) {
        mContext = context;
        shareManager = new ShareManager(mContext);
    }

    public static Bitmap getHeadImage() {
        try {
            headImage = AppUtils.decodeUriAsBitmap(imageUri);    //decode bitmap
        } catch (Exception e) {
            e.printStackTrace();
        }
        return headImage;
    }

    public static EmployeeInfo getMyInfo() {
        if ("".equals(shareManager.getPersonInfo().toString())) {
            queryPersonInfo();
        } else {
            myInfo = GsonProvider.getInstance().getGson().fromJson(shareManager.getPersonInfo().toString(), EmployeeInfo.class);
        }
        return myInfo;
    }

    public static void setMyInfo(EmployeeInfo employeeInfo) {
        if (employeeInfo != null) {
            shareManager.setPersonInfo(GsonProvider.getInstance().getGson().toJson(employeeInfo));
        } else {
            shareManager.setPersonInfo("");
        }
    }

    /**
     * 查询个人信息并保存
     */
    public static void queryPersonInfo() {
        BmobQuery<EmployeeInfo> query = new BmobQuery<EmployeeInfo>();
        query.addWhereEqualTo("myUser", BmobUser.getCurrentUser().getObjectId());
        query.findObjects(new FindListener<EmployeeInfo>() {
            @Override
            public void done(List<EmployeeInfo> list, BmobException e) {
                if (e == null) {
                    setMyInfo(list.get(0));
                    downloadHeadImage();
                } else {
                    AppUtils.toast("还未填写个人信息，请完善！", Toast.LENGTH_SHORT);
                    LogUtil.d("bmob", "" + e.toString());
                    EmployeeInfo employeeInfo = new EmployeeInfo();
                    employeeInfo.setMyUser(BmobUser.getCurrentUser(MyUser.class));
                    employeeInfo.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e != null) {
                                LogUtil.d("bmob", s + e.toString());
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 修改个人信息
     *
     * @param employeeInfo
     */
    public static void updatePersonInfo(final EmployeeInfo employeeInfo) {
        if (employeeInfo == null) {
            return;
        }
        employeeInfo.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    LogUtil.d("bmob", "更新个人信息成功");
                    setMyInfo(employeeInfo);
                } else {
                    LogUtil.d("bmob", "更新个人信息失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 上传个人头像
     */
    public static void uploadHeadImage(String path, String name) {
        final File file = new File(path, name);
        if (file == null | !file.isFile()) return;

        BmobFile oldBmobFile = new BmobFile();
        if (getMyInfo().getEmployeeHeadImage() != null) {
            oldBmobFile.setUrl(getMyInfo().getEmployeeHeadImage().getUrl());
            oldBmobFile.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        LogUtil.d("bmob", "头像文件删除成功");
                    } else {
                        LogUtil.d("bmob ", "头像文件删除失败：" + e.getErrorCode() + "," + e.getMessage());
                    }
                }
            });
        }

        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    AppUtils.toast("头像上传成功", Toast.LENGTH_SHORT);
                    EmployeeInfo employeeInfo = new EmployeeInfo();
                    employeeInfo.setEmployeeHeadImage(bmobFile);
                    employeeInfo.update(getMyInfo().getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                LogUtil.d("bmob", "更新成功");
                                EmployeeInfo eInfo = UserManager.getMyInfo();
                                eInfo.setEmployeeHeadImage(bmobFile);
                                setMyInfo(eInfo);
                            } else {
                                LogUtil.d("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                } else {
                    AppUtils.toast("头像上传失败，请重试!", Toast.LENGTH_SHORT);
                }
            }
        });

    }


    public static void downloadHeadImage() {
        BmobFile bmobFile = getMyInfo().getEmployeeHeadImage();
        bmobFile.download(new DownloadFileListener() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    LogUtil.d("bmob", "头像下载成功:" + s);
                    try {
                        AppUtils.copyFile(mContext.getApplicationContext().getCacheDir() + "/bmob/" + "head_image.jpg", UserManager.cachePath + "head_image.jpg");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    LogUtil.d("bmob", "头像下载失败" + e.toString());
                }
            }

            @Override
            public void onProgress(Integer integer, long l) {

            }
        });
    }

}
