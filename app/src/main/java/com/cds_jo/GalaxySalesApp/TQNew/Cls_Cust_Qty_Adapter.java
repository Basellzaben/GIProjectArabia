package com.cds_jo.GalaxySalesApp.TQNew;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.R;

import java.util.ArrayList;

public class Cls_Cust_Qty_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Cls_Cust_Item_Qty> cls_invfs;


    public Cls_Cust_Qty_Adapter(Context context, ArrayList<Cls_Cust_Item_Qty> list) {

        this.context = context;
        cls_invfs = list;
    }
    @Override
    public int getCount() {

        return cls_invfs.size();
    }

    @Override
    public Object getItem(int position) {

        return cls_invfs.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Cls_Cust_Item_Qty cls_invf = cls_invfs.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.return_cust_qty_row, null);

        }
        TextView lbl_Qty = (TextView) convertView.findViewById(R.id.lbl_Qty);
        lbl_Qty.setText(cls_invf.getTransTypeDesc());


        TextView tv_Qty = (TextView) convertView.findViewById(R.id.tv_Qty);
        tv_Qty.setText(cls_invf.getQty());





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

