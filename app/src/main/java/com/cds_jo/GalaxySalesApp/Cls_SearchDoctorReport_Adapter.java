package com.cds_jo.GalaxySalesApp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Methdes.MyTextView;


public class Cls_SearchDoctorReport_Adapter  extends BaseAdapter {
    Context context;
    ArrayList<Cls_DoctorReport> ArrayList;

    public Cls_SearchDoctorReport_Adapter(Context context, ArrayList<Cls_DoctorReport> list) {

        this.context = context;
        ArrayList = list;
    }

    @Override
    public int getCount() {

        return ArrayList.size();
    }

    @Override
    public Object getItem(int position) {

        return ArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Cls_DoctorReport Obj = ArrayList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_doctor_report, null);

        }
        TextView tv_DocNo = (TextView) convertView.findViewById(R.id.tv_DocNo);
        tv_DocNo.setText(Obj.getNo());

        TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);
        tv_date.setText(Obj.getTr_Date());


        TextView tv_CustNm = (TextView) convertView.findViewById(R.id.tv_CustNm);
        tv_CustNm.setText(Obj.getCustName());

        TextView tv_notes = (TextView) convertView.findViewById(R.id.tv_notes);
        tv_notes.setText(Obj.getVNotes());


        LinearLayout RR=(LinearLayout)convertView.findViewById(R.id.RR1);
        if(position%2==0)
        {
            RR.setBackgroundColor(Color.WHITE);
        }
        else
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Gray2));

        }
        return convertView;

    }

}



