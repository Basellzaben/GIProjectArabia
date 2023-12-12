package com.cds_jo.GalaxySalesApp.TQNew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ContactListAdapter1 extends BaseAdapter {

    Context context;
    ArrayList<ContactListItems1> contactList;
    ContactListItems1 contactListItems;
    public ContactListAdapter1(Context context, ArrayList<ContactListItems1> list) {

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
    public View getView(final int position, View convertView, ViewGroup arg2) {
        contactListItems = contactList.get(position);


        if(position==0)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sales_order_item_header, null);

        }
        else {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.contact_list_row5, null);


            TextView tvSlNo = (TextView) convertView.findViewById(R.id.tv_no);
            tvSlNo.setText(contactListItems.getno());
            TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
            tvName.setText(contactListItems.getName());
            TextView tvPrice = (TextView) convertView.findViewById(R.id.tv_Price);
            tvPrice.setText(contactListItems.getItemOrgPrice());
            TextView tvQTY = (TextView) convertView.findViewById(R.id.tv_Qty);
            tvQTY.setText(contactListItems.getQty());
            TextView tvTax = (TextView) convertView.findViewById(R.id.tv_Tax);
            tvTax.setText(contactListItems.getTax());
            TextView tv_Tax_Amt = (TextView) convertView.findViewById(R.id.tv_Tax_Amt);
            tv_Tax_Amt.setText(contactListItems.getTax_Amt());
            CheckBox CHXSPrice = (CheckBox) convertView.findViewById(R.id.CHXSPrice);
            if ((contactListItems.getSPrise().equals("1")))
            {
                CHXSPrice.setChecked(true);
            }
            else
            {
                CHXSPrice.setChecked(false);
            }
            CHXSPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {

                        contactList.get(position).setSPrise("1");
                        contactListItems = contactList.get(position);

                    }
                    else
                    {

                        contactList.get(position).setSPrise("0");
                        contactListItems = contactList.get(position);
                    }
                }
            });





            TextView Bounce = (TextView) convertView.findViewById(R.id.tv_Bounce);
            Bounce.setText(String.valueOf(SToD(contactListItems.getBounce()) + SToD(contactListItems.getPro_bounce())));






            TextView Total = (TextView) convertView.findViewById(R.id.tv_Total);
            Total.setText(contactListItems.getTotal());



        }
        return convertView;
    }
    private Double SToD(String str) {
        String f = "";
        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        final DecimalFormat df = (DecimalFormat) nf;
        str = str.replace(",", "");
        Double d = 0.0;
        if (str.length() == 0) {
            str = "0";
        }
        if (str.length() > 0)
            try {
                d = Double.parseDouble(str);
                str = df.format(d).replace(",", "");

            } catch (Exception ex) {
                str = "0";
            }

        df.setParseBigDecimal(true);

        d = Double.valueOf(str.trim()).doubleValue();

        return d;
    }

}
