package com.cds_jo.GalaxySalesApp.TQNew;

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

public class Cls_Acc_Report_Adapter1 extends BaseAdapter {

    Context context;
    ArrayList<Cls_Acc_Report1> cls_acc_reports;


    public Cls_Acc_Report_Adapter1(Context context, ArrayList<Cls_Acc_Report1> list) {

        this.context = context;
        cls_acc_reports = list;
    }

    @Override
    public int getCount() {

        return cls_acc_reports.size();
    }

    @Override
    public Object getItem(int position) {

        return cls_acc_reports.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Cls_Acc_Report1 cls_acc_report = cls_acc_reports.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.acc_report_row1, null);
        }
        MyTextView tv_doc_num = (MyTextView) convertView.findViewById(R.id.tv_doc_num);
        MyTextView Doctype = (MyTextView) convertView.findViewById(R.id.tv_doctype);
        MyTextView cur_no = (MyTextView) convertView.findViewById(R.id.tv_cur_no);
        MyTextView tvdate = (MyTextView) convertView.findViewById(R.id.tv_date);
        MyTextView tvdes = (MyTextView) convertView.findViewById(R.id.tv_des);
        //MyTextView tvbb = (MyTextView) convertView.findViewById(R.id.tv_bb);
        MyTextView tvdept = (MyTextView) convertView.findViewById(R.id.tv_dept);
        MyTextView tvcred = (MyTextView) convertView.findViewById(R.id.tv_cred);
        MyTextView tv_tot = (MyTextView) convertView.findViewById(R.id.tv_tot);
        MyTextView tv_invoice = (MyTextView) convertView.findViewById(R.id.tv_invoice);
        //MyTextView tv_rate = (MyTextView) convertView.findViewById(R.id.tv_rate);


        tv_doc_num.setText(cls_acc_report.getDoc_num());
        Doctype.setText(cls_acc_report.getDoctype());
        cur_no.setText(cls_acc_report.getCur_no());
        tvdate.setText(cls_acc_report.getDate());
        tvdes.setText(cls_acc_report.getDes());
        // tvbb.setText(cls_acc_report.getBb());
        tvdept.setText(cls_acc_report.getDept());
        tvcred.setText(cls_acc_report.getCred().replace("-",""));
        //  tv_rate.setText(cls_acc_report.getRate());
        tv_tot.setText(cls_acc_report.getTot());
        tv_invoice.setText(cls_acc_report.getINVOICE());

        LinearLayout RR = (LinearLayout) convertView.findViewById(R.id.RR);
        LinearLayout RR1 = (LinearLayout) convertView.findViewById(R.id.RR1);
        if (cls_acc_report.getTRANSTYPE().equalsIgnoreCase("2")){
            RR.setBackgroundColor(Color.WHITE);
            RR1.setBackgroundColor(Color.WHITE);
        }else  if (cls_acc_report.getTRANSTYPE().equalsIgnoreCase("3")){
            RR.setBackgroundColor(context.getResources().getColor(R.color.Blue));
            RR1.setBackgroundColor(context.getResources().getColor(R.color.Blue));
        }else  if (cls_acc_report.getTRANSTYPE().equalsIgnoreCase("4")){
            RR.setBackgroundColor(context.getResources().getColor(R.color.ColorPrimary));
            RR1.setBackgroundColor(context.getResources().getColor(R.color.ColorPrimary));

        }else  if (cls_acc_report.getTRANSTYPE().equalsIgnoreCase("0")){
            RR.setBackgroundColor(context.getResources().getColor(R.color.main_green_color));
            RR1.setBackgroundColor(context.getResources().getColor(R.color.main_green_color));
        }
        //   if(position%2==0)
        //   {

        tv_doc_num.setTextColor(Color.BLACK);
        Doctype.setTextColor(Color.BLACK);
        cur_no.setTextColor(Color.BLACK);
        tvdate.setTextColor(Color.BLACK);
        tvdes.setTextColor(Color.BLACK);
        // tvbb.setTextColor(Color.BLACK);
        tvdept.setTextColor(Color.BLACK);
        tvcred.setTextColor(Color.BLACK);
        // tv_rate.setTextColor(Color.BLACK);
        tv_tot.setTextColor(Color.BLACK);
        tv_invoice.setTextColor(Color.BLACK);
        //  }
        //  else
        // {

        tv_doc_num.setTextColor(Color.BLACK);
        Doctype.setTextColor(Color.BLACK);
        cur_no.setTextColor(Color.BLACK);
        tvdate.setTextColor(Color.BLACK);
        tvdes.setTextColor(Color.BLACK);
        // tvbb.setTextColor(Color.BLACK);
        tvdept.setTextColor(Color.BLACK);
        tvcred.setTextColor(Color.BLACK);
        //  tv_rate.setTextColor(Color.BLACK);
        tv_tot.setTextColor(Color.BLACK);
        tv_invoice.setTextColor(Color.BLACK);
        //  }


        return convertView;
    }

}

