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

public class Exchange_voucherAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cls_SearchRecVou2> Cls_SearchRecVou2s;

    public Exchange_voucherAdapter(Context context, ArrayList<Cls_SearchRecVou2> list) {

        this.context = context;
        Cls_SearchRecVou2s = list;
    }

    @Override
    public int getCount() {

        return Cls_SearchRecVou2s.size();
    }

    @Override
    public Object getItem(int position) {

        return Cls_SearchRecVou2s.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Cls_SearchRecVou2 Cls_SearchRecVou2 = Cls_SearchRecVou2s.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.search_recv_row2, null);
        }
        TextView tv_DocNo = (TextView) convertView.findViewById(R.id.name);
        tv_DocNo.setText(Cls_SearchRecVou2.getName());

        TextView tv_date = (TextView) convertView.findViewById(R.id.no);
        tv_date.setText(Cls_SearchRecVou2.getNo());


        LinearLayout RR=(LinearLayout)convertView.findViewById(R.id.RR);
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



