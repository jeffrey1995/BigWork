package com.mrtian.project.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mrtian.project.R;


/**
 * Created by tianxiying on 16/12/7.
 */
public class TopLayout extends RelativeLayout {
    private String titleName = "title";

    RelativeLayout left_area_rl;
    RelativeLayout right_area_rl;
    TextView title_tv;
    ImageView right_area_img;
    ImageView left_area_img;

    public TopLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_top, this);
        left_area_rl = (RelativeLayout) findViewById(R.id.left_area_rl);
        title_tv = (TextView) findViewById(R.id.title_tv);
        right_area_rl = (RelativeLayout) findViewById(R.id.right_area_rl);
        left_area_img = (ImageView) findViewById(R.id.left_area_img);
        right_area_rl.setVisibility(View.GONE); //默认右边区域隐藏
        left_area_rl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }

    public void setRightAreaOnClickListener(OnClickListener onClickListener) {
        right_area_rl.setVisibility(View.VISIBLE);
        right_area_rl.setOnClickListener(onClickListener);
    }

    public void setRightAreaImage(int img_id) {
        Drawable drawable = getResources().getDrawable(img_id);
        right_area_img = (ImageView) findViewById(R.id.right_area_img);
        if (drawable != null) {
            right_area_img.setImageDrawable(drawable);
        }
    }

    public void setLeftAreaVisble(Boolean value) {
        if (value) {
            left_area_rl.setVisibility(VISIBLE);
        } else {
            left_area_rl.setVisibility(GONE);
        }
    }

    public void setLeftAreaImage(int img_id) {
        Drawable drawable = getResources().getDrawable(img_id);
        left_area_img = (ImageView) findViewById(R.id.left_area_img);
        if (drawable != null) {
            left_area_img.setImageDrawable(drawable);
        }
    }

    public void setLeftAreaOnClickListener(OnClickListener onClickListener) {
        left_area_rl.setOnClickListener(onClickListener);
    }

    public String getTitle() {
        return title_tv.toString();
    }

    public TopLayout setTitle(CharSequence title) {
        title_tv.setText(title);
        return this;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public void setTitle(String title) {
        title_tv.setText(title);
    }
}
