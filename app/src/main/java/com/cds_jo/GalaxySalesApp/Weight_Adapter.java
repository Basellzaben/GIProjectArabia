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
public class Weight_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Weight_Model> Obj;


    public Weight_Adapter(Context context, ArrayList<Weight_Model> list) {

        this.context = context;
        Obj = list;
    }

    @Override
    public int getCount() {

        return Obj.size();
    }

    @Override
    public Object getItem(int position) {

        return Obj.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Weight_Model  Location = Obj.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.all_wheght_report_row, null);
        }



        MyTextView tvwh = (MyTextView) convertView.findViewById(R.id.tv_wh);
        MyTextView tvco = (MyTextView) convertView.findViewById(R.id.tv_co);
        tvwh.setText(Location.getWeight());
        tvco.setText(Location.getCount());








    /*    LinearLayout RR=(LinearLayout)convertView.findViewById(R.id.RR);
    if(position%2==0)
        {
            RR.setBackgroundColor(Color.WHITE);
        }
        else
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Gray2));
        }
*/

        return convertView;
    }

}

