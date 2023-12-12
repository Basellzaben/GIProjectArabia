package com.cds_jo.GalaxySalesApp.TQNew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.R;

import java.util.ArrayList;

public class Cls_BouncePalne_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Cls_Bounce_Plane> cls_curs;


    public Cls_BouncePalne_Adapter(Context context, ArrayList<Cls_Bounce_Plane> list) {

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
        Cls_Bounce_Plane cls_cur = cls_curs.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bounce_plane_row, null);

        }
        TextView tv_Name = (TextView) convertView.findViewById(R.id.Qty);
        tv_Name.setText(cls_cur.getQty());

        TextView tv_No = (TextView) convertView.findViewById(R.id.Boune);
        tv_No.setText(cls_cur.getBounce());

        return convertView;
    }

}



