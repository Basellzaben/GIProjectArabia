package com.cds_jo.GalaxySalesApp.TQNew;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.R;

import java.util.ArrayList;

public class OrderDetialsChanges extends BaseAdapter {

    Context context;
    ArrayList<ContactListItems1> contactList;

    public OrderDetialsChanges(Context context, ArrayList<ContactListItems1> list) {

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
        ContactListItems1 contactListItems = contactList.get(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.s_order_changes1, null);


        TextView tvSlNo = (TextView) convertView.findViewById(R.id.tv_no);
        tvSlNo.setText(contactListItems.getno());
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
        tvName.setText(contactListItems.getName());
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tv_Price);
        tvPrice.setText(contactListItems.getprice());
        TextView tvQTY = (TextView) convertView.findViewById(R.id.tv_Qty);
        tvQTY.setText(contactListItems.getQty());

        TextView OldQty = (TextView) convertView.findViewById(R.id.tv_OldQty);
        OldQty.setText(contactListItems.getOldQty());

        TextView Bounce = (TextView) convertView.findViewById(R.id.tv_Bounce);
        Bounce.setText(contactListItems.getBounce());

        TextView OldBounce = (TextView) convertView.findViewById(R.id.tv_OldBounce);
        OldBounce.setText(contactListItems.getOldBonus());

        TextView Total = (TextView) convertView.findViewById(R.id.tv_Total);
        Total.setText(contactListItems.getTotal());

        Methdes.MyTextView Stutes = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Stutes);

        if (contactListItems.getStutes().equalsIgnoreCase("1")) {
            Stutes.setText("إضافة من المشرف");
            Stutes.setBackgroundColor(Color.GREEN);
        } else if (contactListItems.getStutes().equalsIgnoreCase("2")) {

            if (contactListItems.getQty().equalsIgnoreCase(contactListItems.getOldQty()) && contactListItems.getBounce().equalsIgnoreCase(contactListItems.getOldBonus())) {
                Stutes.setText("لم يتم تعديل الكمية والبونص");
            } else if (!contactListItems.getQty().equalsIgnoreCase(contactListItems.getOldQty()) && !contactListItems.getBounce().equalsIgnoreCase(contactListItems.getOldBonus())) {
                Stutes.setText("تم تعديل الكمية والبونص");

            } else if (!contactListItems.getQty().equalsIgnoreCase(contactListItems.getOldQty())) {
                Stutes.setText("تم تعديل الكمية فقط");
            } else if (!contactListItems.getBounce().equalsIgnoreCase(contactListItems.getOldBonus())) {
                Stutes.setText("تم تعديل البونص فقط");

            }
            Stutes.setBackgroundColor(Color.MAGENTA);
        } else if (contactListItems.getStutes().equalsIgnoreCase("-1")) {
            Stutes.setText("لم يتم الاعتماد من المشرف");
            Stutes.setBackgroundColor(Color.WHITE);
        } else if (contactListItems.getStutes().equalsIgnoreCase("3")) {
            Stutes.setText("حذف من المشرف");
            Stutes.setBackgroundColor(Color.RED);
        }


        return convertView;
    }

}

