package com.cds_jo.GalaxySalesApp.TQNew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.R;

import java.util.ArrayList;

public class Cls_Expire_Batch_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Cls_Item_Expire> cls_curs;


    public Cls_Expire_Batch_Adapter(Context context, ArrayList<Cls_Item_Expire> list) {

        this.context = context;
        cls_curs = list;
    }@Override
    public int getCount() {

        return cls_curs.size();
    }

    @Override
    public Object getItem(int position) {

        return cls_curs.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }
    public long getPostion(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Cls_Item_Expire obj = cls_curs.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //  convertView = inflater.inflate(R.layout.expire_batch_row, null);
            convertView = inflater.inflate(R.layout.cust_qty_row, null);

        }
        TextView tv_Batchno = (TextView) convertView.findViewById(R.id.Batchno);
        tv_Batchno.setText(obj.getBatchno());

        TextView tv_Expdate = (TextView) convertView.findViewById(R.id.Expdate);
        tv_Expdate.setText(obj.getExpdate());

        TextView tv_Net = (TextView) convertView.findViewById(R.id.Net);
        tv_Net.setText(obj.getNet());

        return convertView;
    }

}




