package com.cds_jo.GalaxySalesApp.NewPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.R;

import java.util.ArrayList;

public class Media_adapter extends BaseAdapter {

    Context context;
    ArrayList<Media_Modle> Media_Modle;


    public Media_adapter(Context context, ArrayList<Media_Modle> list) {

        this.context = context;
        Media_Modle = list;
    }@Override
    public int getCount() {

        return Media_Modle.size();
    }

    @Override
    public Object getItem(int position) {

        return Media_Modle.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        final Media_Modle  cls_deptf = Media_Modle.get(position);
        //   cls_deptf.setCheck(false);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.media_item, null);

        }
        TextView Title = (TextView) convertView.findViewById(R.id.mediatitle);
        Title.setText(cls_deptf.getTitle());





        return convertView;
    }

}

