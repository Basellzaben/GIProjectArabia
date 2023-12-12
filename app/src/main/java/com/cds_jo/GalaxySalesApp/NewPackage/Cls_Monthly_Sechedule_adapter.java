package com.cds_jo.GalaxySalesApp.NewPackage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.cds_jo.GalaxySalesApp.R;

import java.util.ArrayList;

import Methdes.MyTextView;

public class Cls_Monthly_Sechedule_adapter  extends BaseAdapter {

    Context context;
    ArrayList<Cls_Monthly_Schedule> cls_search_pos;

    public Cls_Monthly_Sechedule_adapter(Context context, ArrayList<Cls_Monthly_Schedule> list) {

        this.context = context;
        cls_search_pos = list;
    }

    @Override
    public int getCount() {

        return cls_search_pos.size();
    }

    @Override
    public Object getItem(int position) {

        return cls_search_pos.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Cls_Monthly_Schedule obj = cls_search_pos.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.monthly_sechedule_row, null);

        }
        MyTextView tv_Date = (MyTextView) convertView.findViewById(R.id.tv_Date);
        tv_Date.setText(obj.getDate());


        MyTextView tv_dayNum = (MyTextView) convertView.findViewById(R.id.tv_dayNum);
        tv_dayNum.setText(obj.getDayNm());

        MyTextView tv_CountryNm = (MyTextView) convertView.findViewById(R.id.tv_CountryNm);

        String q=  ((MonthlySalesManSchedule)context).getChackEntery(obj.getDate(),obj.getPeriodNo());
        String l= LocaleHelper.getlanguage(context);
        if(l.equals("en")) {
            if(q.equals("0"))
            {
                tv_CountryNm.setText(context.getResources().getString(R.string.notentered));
                tv_CountryNm.setTextColor(Color.RED);
            }
            else
            {
                tv_CountryNm.setText(context.getResources().getString(R.string.entered));
                tv_CountryNm.setTextColor(Color.BLUE);
            }

        }else{
            if(q.equals("0"))
            {
                tv_CountryNm.setText("غير مدخلة");
                tv_CountryNm.setTextColor(Color.RED);
            }
            else
            {
                tv_CountryNm.setText(" مدخلة");
                tv_CountryNm.setTextColor(Color.BLUE);
            }
        }


        MyTextView tv_PeriodDesc = (MyTextView) convertView.findViewById(R.id.tv_PeriodDesc);
        tv_PeriodDesc.setText(obj.getPeriodDesc());


        LinearLayout RR=(LinearLayout)convertView.findViewById(R.id.RR1);
        if(position%2==0)
        {
            RR.setBackgroundColor(Color.WHITE);
        }
        else
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Gray2));

        }


        tv_CountryNm.setTextColor(Color.BLUE);
        if(tv_CountryNm.getText().toString().equalsIgnoreCase("غير مدخلة") || tv_CountryNm.getText().toString().equalsIgnoreCase("not entered")){
            tv_CountryNm.setTextColor(Color.RED);
        }
        return convertView;
    }

    public void setEntry(int position) {


    }
}

