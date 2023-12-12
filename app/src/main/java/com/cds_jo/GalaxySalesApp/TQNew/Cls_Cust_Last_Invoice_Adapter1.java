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

public class Cls_Cust_Last_Invoice_Adapter1 extends BaseAdapter {

    Context context;
    ArrayList<Cls_CustLastInvoice> cls_acc_reports;


    public Cls_Cust_Last_Invoice_Adapter1(Context context, ArrayList<Cls_CustLastInvoice> list) {

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
        Cls_CustLastInvoice  cls_acc_report = cls_acc_reports.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cust_last_invoice_row, null);
        }
        MyTextView tv_ITEMID = (MyTextView) convertView.findViewById(R.id.tv_ITEMID);
        MyTextView tv_NAME = (MyTextView) convertView.findViewById(R.id.tv_NAME);
        MyTextView tv_Unit = (MyTextView) convertView.findViewById(R.id.tv_Unit);
        MyTextView tv_Qty = (MyTextView) convertView.findViewById(R.id.tv_Qty);
        MyTextView tv_Price = (MyTextView) convertView.findViewById(R.id.tv_Price);
        MyTextView tv_Total = (MyTextView) convertView.findViewById(R.id.tv_Total);
        MyTextView tv_Batch = (MyTextView) convertView.findViewById(R.id.tv_Batch);
        MyTextView tv_Bounce = (MyTextView) convertView.findViewById(R.id.tv_Bounce);


        tv_ITEMID.setText(cls_acc_report.getITEMID());
        tv_NAME.setText(cls_acc_report.getNAME());
        tv_Unit.setText(cls_acc_report.getUnit());
        tv_Qty.setText(cls_acc_report.getSALESQTY());
        tv_Price.setText(cls_acc_report.getSALESPRICE());
        tv_Total.setText(cls_acc_report.getLINEAMOUNT());
        tv_Batch.setText(cls_acc_report.getINVENTBATCHID());
        tv_Bounce.setText(cls_acc_report.getBOUNCE());
        LinearLayout RR=(LinearLayout)convertView.findViewById(R.id.RR);
        LinearLayout RR1=(LinearLayout)convertView.findViewById(R.id.RR1);


      /*  if(position==0)
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Black11));
            tv_ITEMID.setTextColor(Color.WHITE);
            tv_NAME.setTextColor(Color.WHITE);
            tv_Unit.setTextColor(Color.WHITE);
            tv_Qty.setTextColor(Color.WHITE);
            tv_Price.setTextColor(Color.WHITE);
            tv_Total.setTextColor(Color.WHITE);
            tv_Batch.setTextColor(Color.WHITE);
            tv_Bounce.setTextColor(Color.WHITE);

        }*/
        if(position%2==0)
        {
            RR.setBackgroundColor(Color.WHITE);
            RR1.setBackgroundColor(Color.WHITE);
            tv_ITEMID.setTextColor(Color.BLACK);
            tv_NAME.setTextColor(Color.BLACK);
            tv_Unit.setTextColor(Color.BLACK);
            tv_Qty.setTextColor(Color.BLACK);
            tv_Price.setTextColor(Color.BLACK);
            tv_Total.setTextColor(Color.BLACK);
            tv_Batch.setTextColor(Color.BLACK);
            tv_Bounce.setTextColor(Color.BLACK);

        }
        else
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Gray2));
            RR1.setBackgroundColor(context.getResources().getColor(R.color.Gray2));
            tv_ITEMID.setTextColor(Color.BLACK);
            tv_NAME.setTextColor(Color.BLACK);
            tv_Unit.setTextColor(Color.BLACK);
            tv_Qty.setTextColor(Color.BLACK);
            tv_Price.setTextColor(Color.BLACK);
            tv_Total.setTextColor(Color.BLACK);
            tv_Batch.setTextColor(Color.BLACK);
            tv_Bounce.setTextColor(Color.BLACK);

        }


        return convertView;
    }

}


