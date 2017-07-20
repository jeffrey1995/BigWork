package com.mrtian.project.ui.view.systemsetting;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mrtian.project.MyApplication;
import com.mrtian.project.R;
import com.mrtian.project.manager.UserManager;
import com.mrtian.project.model.EmployeeInfo;
import com.mrtian.project.ui.fragment.MineFragment;
import com.mrtian.project.ui.uikit.actionsheet.ActionSheetItem;
import com.mrtian.project.ui.uikit.actionsheet.ActionSheetMenu;
import com.mrtian.project.ui.view.base.BaseActivity;
import com.mrtian.project.utils.AppUtils;
import com.mrtian.project.utils.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class PersonalCenterActivity extends BaseActivity {
    private final String TAG = "PersonalCenterActivity";
    private final static int CHOOSE_PICTURE = 100;
    private final static int TAKE_PICTURE = 200;
    private final static int CROP_PICTURE = 300;
    private ImageView head_img;
    private TextView account, nickName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_personal_center);
        initView();
    }

    private void initView() {
        head_img = (ImageView) findViewById(R.id.head_img);

        Bitmap bitmap = null;
        File file = new File(UserManager.cachePath);
        if (!file.exists()) {
            file.mkdir();
        }
        bitmap = UserManager.getHeadImage();
        if (bitmap != null) {
            setImageFromUri(UserManager.imageUri, head_img);
        }

        if (BmobUser.getCurrentUser() != null && UserManager.getMyInfo() != null) {
            nickName = ((TextView) findViewById(R.id.nickname_tv));
            nickName.setText(UserManager.getMyInfo().getNickName());
            account = ((TextView) findViewById(R.id.account_tv));
            account.setText(BmobUser.getCurrentUser().getUsername());
        }

        findViewById(R.id.nickname_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = new EditText(mContext);
                showAlertEditDlg("修改昵称", "", "确认", "取消", 0);
            }
        });

        findViewById(R.id.head_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<ActionSheetItem> items = new ArrayList<>();
                ActionSheetItem itemCamera = new ActionSheetItem();
                ActionSheetItem itemAlbum = new ActionSheetItem();

                itemCamera.setName("拍照");
                itemAlbum.setName("从相册选取");

                itemCamera.setType(ActionSheetItem.Type.NORMAL);
                itemAlbum.setType(ActionSheetItem.Type.NORMAL);

                items.add(itemCamera);
                items.add(itemAlbum);

                final ActionSheetMenu.Builder builder = new ActionSheetMenu.Builder(mContext);
                builder.setActionSheetItems(items)
                        .setOnClickListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        takePhotoFromCamera();
                                        break;
                                    case 1:
                                        takePhotoFromAlbum();
                                        break;
                                }
                            }
                        })
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                AppUtils.toast("取消", Toast.LENGTH_SHORT);
                            }
                        })
                        .build().show();
            }
        });
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleName("个人中心");
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
        intent.putExtra(MediaStore.EXTRA_OUTPUT, UserManager.imageUri);
        startActivityForResult(intent, TAKE_PICTURE);//or TAKE_SMALL_PICTURE
    }

    private void takePhotoFromAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);//Pick an item from the data
        intent.setType("image/*");//从所有图片中进行选择
        intent.putExtra("crop", "true");//设置为裁切
        intent.putExtra("aspectX", 1);//裁切的宽比例
        intent.putExtra("aspectY", 1);//裁切的高比例
        intent.putExtra("outputX", 600);//裁切的宽度
        intent.putExtra("outputY", 600);//裁切的高度
        intent.putExtra("scale", true);//支持缩放
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, UserManager.imageUri);//将裁切的结果输出到指定的Uri
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//裁切成的图片的格式
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CHOOSE_PICTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PICTURE:
                    setImageFromUri(UserManager.imageUri, head_img);
                    UserManager.uploadHeadImage(UserManager.cachePath, "head_image.jpg");
                    break;
                case TAKE_PICTURE:
                    cropImageUri(UserManager.imageUri, 800, 800, CROP_PICTURE);
                    break;
                case CROP_PICTURE:
                    setImageFromUri(UserManager.imageUri, head_img);
                    UserManager.uploadHeadImage(UserManager.cachePath, "head_image.jpg");
                    break;
                default:
                    break;
            }
        } else {
            AppUtils.toast("未设置", Toast.LENGTH_SHORT);
        }
    }

    private void setImageFromUri(Uri head_img, ImageView imageView) {
        if (UserManager.imageUri != null) {
            Bitmap bitmap = AppUtils.decodeUriAsBitmap(UserManager.imageUri);    //decode bitmap
            imageView.setImageBitmap(bitmap);
        }
        MineFragment.UPDATE_HEAD_IMAGE = true;  //更改MineFragment的标记位
    }


    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void workOkBtn(int index, Object data) {
        if (index == 0) {
            if (!TextUtils.isEmpty((String) data)) {
                EmployeeInfo employeeInfo = UserManager.getMyInfo();
                employeeInfo.setNickName((String) data);
                UserManager.updatePersonInfo(employeeInfo);
                nickName.setText((String) data);
            }
        }
    }

    @Override
    public void workCancelBtn(int index, Object data) {
        if (index == 0) {

        }
    }
}
