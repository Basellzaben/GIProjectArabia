package com.cds_jo.GalaxySalesApp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SalOrdersDtlAdapter extends BaseAdapter {
    LinearLayout RR;
    Context context;
    ArrayList<SaleOrderDtl> contactListItems;
    SaleOrderDtl SaleOrderDtl;
    public SalOrdersDtlAdapter(Context context, ArrayList<SaleOrderDtl> list) {

        this.context = context;
        contactListItems = list;
    }

    @Override
    public int getCount() {

        return contactListItems.size();
    }

    @Override
    public Object getItem(int position) {

        return contactListItems.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
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
    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
         SaleOrderDtl = contactListItems.get(position);
        LayoutInflater inflater;
        if (convertView == null) {
             inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.contentdtl, null);

        }
        Methdes.MyTextView tvSlNo = (Methdes.MyTextView) convertView.findViewById(R.id.tv_no);
        tvSlNo.setText(SaleOrderDtl.getItem_no());
        Methdes.MyTextView tvName = (Methdes.MyTextView) convertView.findViewById(R.id.tv_name);
        tvName.setText(SaleOrderDtl.getItem_Name());
        Methdes.MyTextView tvPrice = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Price);
        tvPrice.setText(SaleOrderDtl.getPrice());
        Methdes.MyTextView tvQTY = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Qty);
        tvQTY.setText(SaleOrderDtl.getQty());
        Methdes.MyTextView tvTAX = (Methdes.MyTextView) convertView.findViewById(R.id.tv_tax);
        tvTAX.setText(SaleOrderDtl.getTax_Amt());

        Methdes.MyTextView Unit = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Unit);
        Unit.setText(SaleOrderDtl.getUnitName());


        Methdes.MyTextView Bounce = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Bounce);
        Bounce.setText( String.valueOf(Double.valueOf(SaleOrderDtl.getBounce()) ));


        Methdes.MyTextView Disc = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Disc_Per);
        Disc.setText(String.valueOf (Double.valueOf( SaleOrderDtl.getDis_Amt() ) ));




        Methdes.MyTextView Disc_Amt = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Disc_Amt);
        Disc_Amt.setText( String.valueOf(  Double.valueOf(SaleOrderDtl.getDis_Amt())   )) ;


        Methdes.MyTextView Total = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Total);
        Total.setText(SaleOrderDtl.getTotal());


        Methdes.MyTextView Tax_Amt = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Tax_Amt);
        Tax_Amt.setText(SaleOrderDtl.getTax_Amt());


        Methdes.MyTextView tv_Notes = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Notes);
        tv_Notes.setText(SaleOrderDtl.getNotes());




        LinearLayout RR=(LinearLayout)convertView.findViewById(R.id.RR);


        if(position%2==0)
        {
            RR.setBackgroundColor(Color.WHITE);
            tvSlNo.setTextColor(Color.BLACK);
            tvName.setTextColor(Color.BLACK);
            tvPrice.setTextColor(Color.BLACK);
            tvQTY.setTextColor(Color.BLACK);
            tvTAX.setTextColor(Color.BLACK);
            Unit.setTextColor(Color.BLACK);
            Bounce.setTextColor(Color.BLACK);
            Disc.setTextColor(Color.BLACK);
            Disc_Amt.setTextColor(Color.BLACK);
            Total.setTextColor(Color.BLACK);
            Tax_Amt.setTextColor(Color.BLACK);
        }
        else
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Gray2));
            tvSlNo.setTextColor(Color.BLACK);
            tvName.setTextColor(Color.BLACK);
            tvPrice.setTextColor(Color.BLACK);
            tvQTY.setTextColor(Color.BLACK);
            tvTAX.setTextColor(Color.BLACK);
            Unit.setTextColor(Color.BLACK);
            Bounce.setTextColor(Color.BLACK);
            Disc.setTextColor(Color.BLACK);
            Disc_Amt.setTextColor(Color.BLACK);
            Total.setTextColor(Color.BLACK);
            Tax_Amt.setTextColor(Color.BLACK);
        }



        return convertView;
    }

}
