package com.cds_jo.GalaxySalesApp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;


import java.util.ArrayList;

import Methdes.MyTextView;

/**
 * Created by Hp on 06/03/2016.
 */
public class Cls_ClincVisitsReport_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Cls_ClincVisitsReport> cls_clincVisitsReports;


    public Cls_ClincVisitsReport_Adapter(Context context, ArrayList<Cls_ClincVisitsReport> list) {

        this.context = context;
        cls_clincVisitsReports = list;
    }

    @Override
    public int getCount() {

        return cls_clincVisitsReports.size();
    }

    @Override
    public Object getItem(int position) {

        return cls_clincVisitsReports.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Cls_ClincVisitsReport  cls_acc_report = cls_clincVisitsReports.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.clinc_report_row, null);
        }
        MyTextView tv_OrderNo = (MyTextView) convertView.findViewById(R.id.tv_OrderNo);
        MyTextView tv_CustNm = (MyTextView) convertView.findViewById(R.id.tv_CustNm);
        MyTextView tv_VNotes = (MyTextView) convertView.findViewById(R.id.tv_VNotes);
        MyTextView tv_SNotes = (MyTextView) convertView.findViewById(R.id.tv_SNotes);
        MyTextView tv_Tr_Date = (MyTextView) convertView.findViewById(R.id.tv_Tr_Date);
        MyTextView tv_Tr_Time = (MyTextView) convertView.findViewById(R.id.tv_Tr_Time);
        MyTextView tv_VisitType = (MyTextView) convertView.findViewById(R.id.tv_VisitType);
        MyTextView tv_FreeSample = (MyTextView) convertView.findViewById(R.id.tv_FreeSample);
        MyTextView tv_Subjects = (MyTextView) convertView.findViewById(R.id.tv_Subjects);

/*
String gg="";
        LocaleHelper localeHelper=new LocaleHelper();
        if(localeHelper.getlanguage(context).equals("رقم الزيارة"))
            gg=cls_acc_report.getOrderNo().replace("رقم الزيارة","item number");
*/

        tv_OrderNo.setText(cls_acc_report.getOrderNo());//رقم زيارة
       // tv_OrderNo.setText(gg);
        tv_CustNm.setText(cls_acc_report.getCustNm());
        tv_VNotes.setText(cls_acc_report.getVNotes());//ملاحظات
        tv_SNotes.setText(cls_acc_report.getSNotes());//ملخص
        tv_Tr_Date.setText(cls_acc_report.getTr_Date());
        tv_Tr_Time.setText(cls_acc_report.getTr_Time());
        tv_VisitType.setText(cls_acc_report.getVisitType());
        tv_FreeSample.setText(cls_acc_report.getFreeSample());
        tv_Subjects.setText(cls_acc_report.getSubjects());

        LinearLayout RR=(LinearLayout)convertView.findViewById(R.id.RR);
       /* if(position==0)
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Black11));
            tv_OrderNo.setTextColor(Color.WHITE);
            tv_CustNm.setTextColor(Color.WHITE);
            tv_VNotes.setTextColor(Color.WHITE);
            tv_SNotes.setTextColor(Color.WHITE);
            tv_Tr_Date.setTextColor(Color.WHITE);
            tv_Tr_Time.setTextColor(Color.WHITE);
            tv_VisitType.setTextColor(Color.WHITE);
            tv_FreeSample.setTextColor(Color.WHITE);
            tv_Subjects.setTextColor(Color.WHITE);
        }*/
          if(position%2==0)
        {
            RR.setBackgroundColor(Color.WHITE);
            tv_OrderNo.setTextColor(Color.BLACK);
            tv_CustNm.setTextColor(Color.BLACK);
            tv_VNotes.setTextColor(Color.BLACK);
            tv_SNotes.setTextColor(Color.BLACK);
            tv_Tr_Date.setTextColor(Color.BLACK);
            tv_Tr_Time.setTextColor(Color.BLACK);
            tv_VisitType.setTextColor(Color.BLACK);
            tv_FreeSample.setTextColor(Color.BLACK);
            tv_Subjects.setTextColor(Color.BLACK);
        }
        else
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Gray2));
            tv_OrderNo.setTextColor(Color.BLACK);
            tv_CustNm.setTextColor(Color.BLACK);
            tv_VNotes.setTextColor(Color.BLACK);
            tv_SNotes.setTextColor(Color.BLACK);
            tv_Tr_Date.setTextColor(Color.BLACK);
            tv_Tr_Time.setTextColor(Color.BLACK);
            tv_VisitType.setTextColor(Color.BLACK);
            tv_FreeSample.setTextColor(Color.BLACK);
            tv_Subjects.setTextColor(Color.BLACK);
        }


        return convertView;
    }

}

