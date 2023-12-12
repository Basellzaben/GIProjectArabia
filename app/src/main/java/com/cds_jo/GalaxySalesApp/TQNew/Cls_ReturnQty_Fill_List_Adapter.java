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

public class Cls_ReturnQty_Fill_List_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Cls_Invf1> cls_invfs;


    public Cls_ReturnQty_Fill_List_Adapter(Context context, ArrayList<Cls_Invf1> list) {

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
        Cls_Invf1 cls_invf = cls_invfs.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.return_qty_listview_row, null);

        }
        TextView tv_itemno = (TextView) convertView.findViewById(R.id.tv_itemno);
        tv_itemno.setText(cls_invf.getItem_No());


        TextView tv_itemname = (TextView) convertView.findViewById(R.id.tv_itemname);
        tv_itemname.setText(cls_invf.getItem_Name());


        TextView tv_Qty = (TextView) convertView.findViewById(R.id.tv_Qty);
        tv_Qty.setText("الكمية :"+cls_invf.getSalesQty());


        TextView tv_Bounce = (TextView) convertView.findViewById(R.id.tv_Bounce);
        tv_Bounce.setText("البونص :"+cls_invf.getSaleBounce());


        TextView tv_SalesID = (TextView) convertView.findViewById(R.id.tv_SalesID);
        tv_SalesID.setText("رقم الفاتورة :"+cls_invf.getSalesID());


        TextView tv_Date = (TextView) convertView.findViewById(R.id.tv_Date);
        tv_Date.setText("التاريخ :"+cls_invf.getSalesDate());




        if (cls_invf.getShowQty().equalsIgnoreCase("1")){
            tv_Qty.setVisibility(View.VISIBLE);
            tv_Bounce.setVisibility(View.VISIBLE);
            tv_SalesID.setVisibility(View.VISIBLE);
            tv_Date.setVisibility(View.VISIBLE);
        }else
        {
            tv_Qty.setVisibility(View.INVISIBLE);
            tv_Bounce.setVisibility(View.INVISIBLE);
            tv_SalesID.setVisibility(View.INVISIBLE);
            tv_Date.setVisibility(View.INVISIBLE);

        }
        LinearLayout RR=(LinearLayout)convertView.findViewById(R.id.RR1);
        if(position%2==0)
        {
            RR.setBackgroundColor(Color.WHITE);
        }
        else
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Gray2));

        }
    /*     TextView cur_no = (TextView) convertView.findViewById(R.id.tv_cur_no);
       cur_no.setText(cls_invf.getCur_no());

        TextView tvdate = (TextView) convertView.findViewById(R.id.tv_date);
        tvdate.setText(cls_invf.getDate());


        TextView tvdes = (TextView) convertView.findViewById(R.id.tv_des);
        tvdes.setText(cls_invf.getDes());


        TextView tvbb = (TextView) convertView.findViewById(R.id.tv_bb);
        tvbb.setText(cls_invf.getBb());


        TextView tvdept = (TextView) convertView.findViewById(R.id.tv_dept);
        tvdept.setText(cls_invf.getDept());

        TextView tvcred = (TextView) convertView.findViewById(R.id.tv_cred);
        tvcred.setText(cls_invf.getCred());


        TextView tv_rate = (TextView) convertView.findViewById(R.id.tv_rate);
        tv_rate.setText(cls_invf.getRate());


        TextView tv_tot = (TextView) convertView.findViewById(R.id.tv_tot);
        tv_tot.setText(cls_invf.getTot());

*/



        return convertView;
    }

}

