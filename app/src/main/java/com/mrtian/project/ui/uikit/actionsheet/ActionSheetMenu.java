package com.mrtian.project.ui.uikit.actionsheet;

/**
 * Created by tianxiying on 2017/3/22.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mrtian.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部菜单列表
 */
public class ActionSheetMenu extends Dialog {

    private Context mContext;

    /**
     * 菜单点击监听
     */
    private OnClickListener mOnClickListener;

    /**
     * 菜单列表
     */
    private List<ActionSheetItem> mActionSheetItems;

    public ActionSheetMenu(Context context, final ArrayList<ActionSheetItem> actionSheetItems) {
        super(context, android.R.style.Theme_Dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = context;
        mActionSheetItems = actionSheetItems;
        init();
    }

    private ActionSheetMenu(Builder builder) {
        super(builder.mContext, android.R.style.Theme_Dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = builder.mContext;
        mOnClickListener = builder.mOnClickListener;
        mActionSheetItems = builder.mActionSheetItems;
        setOnCancelListener(builder.mOnCancelListener);
        init();
    }

    /**
     * 设置列表点击事件
     * @param onClickListener 监听器
     */
    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    /**
     * 设置列表选项
     * @param actionSheetItems 列表选项
     */
    public void setActionSheetItems(List<ActionSheetItem> actionSheetItems) {
        mActionSheetItems = actionSheetItems;
    }

    private void init() {
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setWindowAnimations(R.style.actionSheetAnim);
        View view = getLayoutInflater().inflate(R.layout.muna_base_action_sheet, null);
        int screenWidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(screenWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        super.setContentView(view, lp);
        final TextView cancel = (TextView) findViewById(R.id.action_sheet_cancel_text);
        final ListView actionSheetList = (ListView) findViewById(R.id.action_sheet_list);

        BaseAdapter adapter = new BaseAdapter() {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv;
                if (convertView == null) {
                    tv = (TextView) View.inflate(mContext, R.layout.muna_base_action_sheet_item, null);
                } else {
                    tv = (TextView) convertView;
                }
                ActionSheetItem item = getItem(position);
                tv.setText(item.getName());
                switch (item.getType()) {
                    case LINK:
                        tv.setTextColor(mContext.getResources().getColor(R.color.base_action_sheet_item_text_highlight));
                        break;

                    case WARN:
                        tv.setTextColor(mContext.getResources().getColor(R.color.base_action_sheet_item_text_warn));
                        break;

                    case NORMAL:
                    default:
                        tv.setTextColor(mContext.getResources().getColor(R.color.base_action_sheet_item_text_normal));
                        break;
                }
                convertView = tv;
                return convertView;
            }

            @Override
            public int getCount() {
                return mActionSheetItems.size();
            }

            @Override
            public ActionSheetItem getItem(int position) {
                return mActionSheetItems.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }
        };

        actionSheetList.setAdapter(adapter);
        actionSheetList.setDivider(new ColorDrawable(mContext.getResources().getColor(R.color.base_action_sheet_divider)));
        actionSheetList.setDividerHeight(1);
        actionSheetList.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(ActionSheetMenu.this, position);
                }
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    /**
     * 底部菜单列表Builder
     */
    public static class Builder {

        private Context mContext;

        private OnClickListener mOnClickListener;

        private List<ActionSheetItem> mActionSheetItems;

        private OnCancelListener mOnCancelListener;

        public Builder(Context context) {
            mContext = context;
        }

        /**
         * 设置列表选择事件监听器
         *
         * @param onClickListener 监听器
         * @return Builder
         */
        public Builder setOnClickListener(OnClickListener onClickListener) {
            mOnClickListener = onClickListener;
            return this;
        }

        /**
         * 设置取消监听器
         * @param onCancelListener 监听器
         * @return Builder
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * 设置菜单列表
         *
         * @param actionSheetItems 菜单列表
         * @return Builder
         */
        public Builder setActionSheetItems(List<ActionSheetItem> actionSheetItems) {
            mActionSheetItems = actionSheetItems;
            return this;
        }

        /**
         * 创建一个底部菜单栏
         *
         * @return ActionSheetMenu
         */
        public ActionSheetMenu build() {
            return new ActionSheetMenu(this);
        }
    }

}
