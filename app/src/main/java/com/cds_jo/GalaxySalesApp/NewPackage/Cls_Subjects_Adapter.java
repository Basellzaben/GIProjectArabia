package com.cds_jo.GalaxySalesApp.NewPackage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.cds_jo.GalaxySalesApp.DoctorReportActivity;
import com.cds_jo.GalaxySalesApp.R;

import java.util.ArrayList;

public class Cls_Subjects_Adapter  extends BaseAdapter {

    Context context;
    ArrayList<Cls_Subjects> contactList;

    public Cls_Subjects_Adapter(Context context, ArrayList<Cls_Subjects> list) {

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
        final Cls_Subjects contactListItems = contactList.get(position);


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.subject_row, null);

        }

        LinearLayout RR=(LinearLayout)convertView.findViewById(R.id.RR);


        Methdes.MyTextView tvName = (Methdes.MyTextView) convertView.findViewById(R.id.tv_name);
        tvName.setText(contactListItems.getSubjectDesc());

        Methdes.MyTextView tv_ID = (Methdes.MyTextView) convertView.findViewById(R.id.tv_ID);
        tv_ID.setText(contactListItems.getSubjectID());


        ImageButton show=(ImageButton)convertView.findViewById(R.id.imageButton145);
        ImageButton showmedis=(ImageButton)convertView.findViewById(R.id.showmedis);

        show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((DoctorReportActivity)context).btn_Show_Object(contactListItems.getSubjectID().toString());
            }
        });

        showmedis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((DoctorReportActivity)context).btn_Show_Media(contactListItems.getSubjectID().toString());

            }
        });


        if(position%2==0  )
        {   RR.setBackgroundColor(Color.WHITE);

            tvName.setTextColor(Color.BLACK);
            tv_ID.setTextColor(Color.BLACK);


        }
        else if(position%2==1    )
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Gray2));

            tvName.setTextColor(Color.BLACK);
            tv_ID.setTextColor(Color.BLACK);



        }






        return convertView;
    }

}

