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

public class ClincVisitNotes_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Cls_ClincVisitsNotes> contactList;

    public ClincVisitNotes_Adapter(Context context, ArrayList<Cls_ClincVisitsNotes> list) {

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
        Cls_ClincVisitsNotes contactListItems = contactList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.visit_notes_row, null);

        }
        Methdes.MyTextView tv_Notes = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Notes);
        tv_Notes.setText(contactListItems.getNotes());

        Methdes.MyTextView tv_Date = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Date);
        tv_Date.setText(contactListItems.getTr_Dates());


        Methdes.MyTextView tv_UserName = (Methdes.MyTextView) convertView.findViewById(R.id.tv_UserName);
        tv_UserName.setText(contactListItems.getUserID());


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

