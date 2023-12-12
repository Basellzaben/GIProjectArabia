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

public class Cls_FreeSample_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Cls_FreeSampleItems> contactList;

    public Cls_FreeSample_Adapter(Context context, ArrayList<Cls_FreeSampleItems> list) {

        this.context = context;
        contactList = list;
    }

    @Override
    public int getCount() {

        return contactList.size();
    }

    @Override
    public Object getItem(int position) {

        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Cls_FreeSampleItems contactListItems = contactList.get(position);


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_free_sample, null);

        }

        LinearLayout RR=(LinearLayout)convertView.findViewById(R.id.RR);


        Methdes.MyTextView tvName = (Methdes.MyTextView) convertView.findViewById(R.id.tv_name);
        tvName.setText(contactListItems.getItemNm());

        Methdes.MyTextView tvQTY = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Qty);
        tvQTY.setText(contactListItems.getQty());

        Methdes.MyTextView Unit = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Unit);
        Unit.setText(contactListItems.getUnitNm());




        if(position%2==0  )
        {   RR.setBackgroundColor(Color.WHITE);

            tvName.setTextColor(Color.BLACK);
            tvQTY.setTextColor(Color.BLACK);
            Unit.setTextColor(Color.BLACK);

        }
        else if(position%2==1    )
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Gray2));

            tvName.setTextColor(Color.BLACK);
            tvQTY.setTextColor(Color.BLACK);
            Unit.setTextColor(Color.BLACK);


        }






        return convertView;
    }

}
