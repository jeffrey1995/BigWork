package com.mrtian.project.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.mrtian.project.R;
import com.mrtian.project.model.EmployeeInfo;
import com.tsy.sdk.myokhttp.MyOkHttp;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tianxiying on 2017/3/8.
 */

public class EmployeInfoAdapter extends ArrayAdapter<EmployeeInfo> {
    private Context mContext;
    private int resourceId;
    private OkHttpClient client;

    public EmployeInfoAdapter(Context context, int resource, List<EmployeeInfo> list) {
        super(context, resource, list);
        mContext = context;
        resourceId = resource;
        client = new OkHttpClient();
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        EmployeeInfo employeeInfo = getItem(position);

        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.title_iv = (ImageView) view.findViewById(R.id.title_iv);
            viewHolder.title_tv = (TextView) view.findViewById(R.id.title_tv);
            viewHolder.department_tv = (TextView) view.findViewById(R.id.department);
            viewHolder.phone_tv = (TextView) view.findViewById(R.id.phone);
            view.setTag(viewHolder); //将viewholder存储在viwe中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (employeeInfo.getEmployeeHeadImage() != null) {
            loadImage(viewHolder.title_iv, employeeInfo.getEmployeeHeadImage().getFileUrl());
        } else {
            loadImage(viewHolder.title_iv, "");
        }
        viewHolder.title_tv.setText(employeeInfo.getEmployeeName());
        viewHolder.department_tv.setText(employeeInfo.getEmployeeDepartment());
        viewHolder.phone_tv.setText(employeeInfo.getEmployeePhoneNumber());
        return view;
    }

    private void loadImage(final ImageView imageView, String uri) {
        Request request = new Request.Builder().url("http://bmob-cdn-10669.b0.upaiyun.com/2017/04/20/dc8acc8a42464b3695226719b2b116f9.jpg").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //此处处理请求失败的业务逻辑
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //我写的这个例子是请求一个图片
                //response的body是图片的byte字节
                byte[] bytes = response.body().bytes();
                //response.body().close();

                //把byte字节组装成图片
                final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                //回调是运行在非ui主线程，
                //数据请求成功后，在主线程中更新
                if (imageView != null) {
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bmp);
                        }
                    });
                }
            }
        });
    }

    class ViewHolder {
        ImageView title_iv;
        TextView title_tv;
        TextView department_tv;
        TextView phone_tv;
    }
}