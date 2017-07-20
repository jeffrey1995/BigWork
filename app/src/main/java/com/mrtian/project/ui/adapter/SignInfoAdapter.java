package com.mrtian.project.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mrtian.project.R;
import com.mrtian.project.model.SignInfo;
import com.mrtian.project.utils.Constant;

import java.util.List;

/**
 * Created by tianxiying on 2017/4/10.
 */

public class SignInfoAdapter extends ArrayAdapter<SignInfo> {

    private int resourceId;

    public SignInfoAdapter(Context context, int resource, List<SignInfo> list) {
        super(context, resource, list);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SignInfo signInfo = getItem(position);

        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            viewHolder.tv_sign_in_time = (TextView) view.findViewById(R.id.tv_sign_in_time);
            viewHolder.tv_sign_in_state = (TextView) view.findViewById(R.id.tv_sign_in_state);
            viewHolder.tv_sign_out_time = (TextView) view.findViewById(R.id.tv_sign_out_time);
            viewHolder.tv_sign_out_state = (TextView) view.findViewById(R.id.tv_sign_out_state);
            viewHolder.tv_sign_state = (TextView) view.findViewById(R.id.tv_sign_state);
            view.setTag(viewHolder); //将viewholder存储在viwe中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_date.setText(signInfo.getYearAndMounthAndDay());
        viewHolder.tv_sign_in_time.setText(signInfo.getInTime());
        viewHolder.tv_sign_out_time.setText(signInfo.getOutTime());
        viewHolder.tv_sign_in_state.setText(getStateString(signInfo.getState_sign_in()));
        viewHolder.tv_sign_out_state.setText(getStateString(signInfo.getState_sign_out()));
        viewHolder.tv_sign_state.setText(getStateString(signInfo.getState()));
        return view;
    }

    private String getStateString(int state) {
        switch (state) {
            case Constant.SINGSTATE_NORMAL:
                return "正常";
            case Constant.SINGSTATE_ABNORMAL:
                return "异常";
            case Constant.SINGSTATE_NOTHING:
                return "无记录";
            default:
                return "null";
        }
    }

    class ViewHolder {
        TextView tv_date;
        TextView tv_sign_in_time;
        TextView tv_sign_in_state;
        TextView tv_sign_out_time;
        TextView tv_sign_out_state;
        TextView tv_sign_state;
    }

}

