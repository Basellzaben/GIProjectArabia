package com.cds_jo.GalaxySalesApp.assist;

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

import static com.cds_jo.GalaxySalesApp.R.id.RR1;

/**
 * Created by Hp on 18/03/2016.
 */
public class Cls_CustWithoutPayment_Adapter extends BaseAdapter{
    Context context;
    ArrayList<Cls_CustomerWithoutPayment> cls_curs;


    public Cls_CustWithoutPayment_Adapter(Context context, ArrayList<Cls_CustomerWithoutPayment> list) {

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
        Cls_CustomerWithoutPayment cls_cur = cls_curs.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cust_without_payment, null);

        }
        TextView tv_Name = (TextView) convertView.findViewById(R.id.tv_Name);
        tv_Name.setText(cls_cur.getCustNm());

        LinearLayout RR=(LinearLayout)convertView.findViewById(RR1);
/*
        if(position%2==0)
        {
            RR.setBackgroundColor(Color.WHITE);
        }
        else
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Gray2));

        }*/

        return convertView;
    }

}



