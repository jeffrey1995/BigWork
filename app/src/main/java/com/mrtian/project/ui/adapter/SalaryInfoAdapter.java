package com.mrtian.project.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mrtian.project.R;
import com.mrtian.project.model.SalaryInfo;

import java.util.List;



/**
 * Created by tianxiying on 2017/3/13.
 */

public class SalaryInfoAdapter extends ArrayAdapter<SalaryInfo>{

    private int resourceId;

    public SalaryInfoAdapter(Context context, int resource, List<SalaryInfo> list) {
        super(context, resource, list);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SalaryInfo salaryInfo = getItem(position);

        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            viewHolder.tv_salary = (TextView) view.findViewById(R.id.tv_salary);
            viewHolder.tv_income_source = (TextView) view.findViewById(R.id.tv_income_source);
            view.setTag(viewHolder); //将viewholder存储在viwe中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_date.setText(salaryInfo.getDate());
        viewHolder.tv_salary.setText("+"+salaryInfo.getActualIncome());
        viewHolder.tv_income_source.setText(salaryInfo.getIncomeSource());
        return view;
    }

    class ViewHolder {
        TextView tv_date;
        TextView tv_salary;
        TextView tv_income_source;
    }
}
