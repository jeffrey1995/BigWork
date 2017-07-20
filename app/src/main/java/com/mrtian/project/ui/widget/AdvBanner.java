package com.mrtian.project.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mrtian.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianxiying on 2017/3/7.
 */

public class AdvBanner extends LinearLayout implements ViewPager.OnPageChangeListener {
    private Context mContext;
    private View mRootView;

    //循环滚动图片相关控件
    private ViewPager viewPager;
    private ViewGroup viewGroup;
    private List<ImageView> imgList;
    private ImageView[] tips;
    private int[] imageId;
    private boolean isLoop = false;  //轮播开关
    private Handler handler = new Handler();
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            if (isLoop) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                handler.postDelayed(task, 5000);
            } else {
                // 如果处于拖拽状态停止自动播放，会每隔5秒检查一次是否可以正常自动播放。
                handler.postDelayed(task, 5000);
            }
        }
    };

    public AdvBanner(Context context) {
        super(context, null);
        initView(context);
    }

    public AdvBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.layout_adv_banner, this, true);
        mContext = context;

        //手动滑动播放广告图
        viewPager = (ViewPager) mRootView.findViewById(R.id.viewpager);
        viewGroup = (ViewGroup) mRootView.findViewById(R.id.viewGroup);

        //载入图片资源id
        imageId = new int[]{R.drawable.android1,R.drawable.android2,R.drawable.android3};

        /*
        广告图自动播放
        每过5秒钟换下一张图片
         */
        if (imageId.length < 2) {
            isLoop = false;
        } else {
            isLoop = true;
            handler.post(task);
        }

        //将图片下面的点点加入到ViewGroup中
        tips = new ImageView[imageId.length];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.drawable.focused);
            } else
                tips[i].setBackgroundResource(R.drawable.unfocused);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            params.leftMargin = 5;
            params.rightMargin = 5;
            viewGroup.addView(imageView, params);
        }

        //将图片装载到数组中
        imgList = new ArrayList<ImageView>();
        for (int i = 0; i < imageId.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imgList.add(imageView);
            imageView.setBackgroundResource(imageId[i]);
        }

        //设置对应适配器和监听器
        viewPager.setAdapter(new ViewPagerAdapter());
        viewPager.setOnPageChangeListener(this);
        if (imageId.length == 1) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem((imgList.size()) * 100);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //对应图片选中的时候要将对应的“点”的选中状态改变
        setImageBackground(position % imgList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            // 闲置中
            case ViewPager.SCROLL_STATE_IDLE:
                isLoop = true;
                break;
            // 拖动中
            case ViewPager.SCROLL_STATE_DRAGGING:
                isLoop = false;
                break;
            // 设置中
            case ViewPager.SCROLL_STATE_SETTLING:
                isLoop = true;
                break;
        }
    }

    /**
     * 改变广告图下方“点点”的选中状态
     *
     * @param item 对应选中图片位置
     */
    public void setImageBackground(int item) {
        for (int i = 0; i < tips.length; i++) {
            if (i == item)
                tips[i].setBackgroundResource(R.drawable.focused);
            else
                tips[i].setBackgroundResource(R.drawable.unfocused);
        }
    }

    /**
     * 自定义PagerAdapter类
     */
    public class ViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if (imageId.length < 1) return 0;
            else return (imageId.length == 1) ? 1 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (imgList.size() > 3)      //  图片数量小于三张时不进行该清除处理
                container.removeView(imgList.get(position % imgList.size()));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            ImageView img = imgList.get(position % imgList.size());
            ViewParent parent = img.getParent();
            if (parent != null) {
                ViewGroup vg = (ViewGroup) parent;
                vg.removeView(img);
            }
            container.addView(imgList.get(position % imgList.size()));
            return imgList.get(position % imgList.size());
        }
    }
}
