package com.cds_jo.GalaxySalesApp.NewPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.R;

import java.util.ArrayList;

public class CustomerDaysAdapter extends BaseAdapter {

    Context context;
    ArrayList<CustomerDays> cls_search_pos;

    public CustomerDaysAdapter(Context context, ArrayList<CustomerDays> list) {

        this.context = context;
        cls_search_pos = list;
    }

    @Override
    public int getCount() {

        return cls_search_pos.size();
    }

    @Override
    public Object getItem(int position) {

        return cls_search_pos.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        final CustomerDays obj = cls_search_pos.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.customersday, null);

        }
        String DayNm ="" ;
        if (obj.getDayNo().equalsIgnoreCase("1")) DayNm = String.valueOf(context.getResources().getString(R.string.Sunday));
        else if (obj.getDayNo().equalsIgnoreCase("2")) DayNm = String.valueOf(context.getResources().getString(R.string.Monday));
        else if (obj.getDayNo().equalsIgnoreCase("3")) DayNm = String.valueOf(context.getResources().getString(R.string.Tuesday));
        else if (obj.getDayNo().equalsIgnoreCase("4")) DayNm =String.valueOf(context.getResources().getString(R.string.Wednesday));
        else if (obj.getDayNo().equalsIgnoreCase("5")) DayNm = String.valueOf(context.getResources().getString(R.string.Thursday));
        else if (obj.getDayNo().equalsIgnoreCase("6")) DayNm = String.valueOf(context.getResources().getString(R.string.Friday));
        else if (obj.getDayNo().equalsIgnoreCase("7")) DayNm =  String.valueOf(context.getResources().getString(R.string.Saturday));
        TextView Days = (TextView) convertView.findViewById(R.id.Days);
        Days.setText(DayNm);


        TextView Time = (TextView) convertView.findViewById(R.id.Time);
        Time.setText(obj.getTime());

        TextView Duration = (TextView) convertView.findViewById(R.id.Duration);
        Duration.setText(obj.getDuration());


        TextView Notes = (TextView) convertView.findViewById(R.id.Notes);
        Notes.setText(obj.getNotes());







        return convertView;
    }

}


