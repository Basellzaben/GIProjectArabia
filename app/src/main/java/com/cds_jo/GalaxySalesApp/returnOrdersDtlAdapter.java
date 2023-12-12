package com.cds_jo.GalaxySalesApp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

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

public class returnOrdersDtlAdapter extends BaseAdapter {
    LinearLayout RR;
    Context context;
    ArrayList<returnOrderDtl> contactListItems;
    returnOrderDtl returnOrderDtl;
    public returnOrdersDtlAdapter(Context context, ArrayList<returnOrderDtl> list) {

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
        returnOrderDtl = contactListItems.get(position);
        LayoutInflater inflater;
        if (convertView == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.contentdtlreturn, null);

        }
        Methdes.MyTextView tvSlNo = (Methdes.MyTextView) convertView.findViewById(R.id.tv_no);
        tvSlNo.setText(returnOrderDtl.getItem_no());
        Methdes.MyTextView tvName = (Methdes.MyTextView) convertView.findViewById(R.id.tv_name);
        tvName.setText(returnOrderDtl.getItem_Name());
        Methdes.MyTextView tv_Qty = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Qty);
        tv_Qty.setText(returnOrderDtl.getPrice());
        Methdes.MyTextView tv_Unit = (Methdes.MyTextView) convertView.findViewById(R.id.tv_Unit);
        tv_Unit.setText(returnOrderDtl.getQty());


        LinearLayout RR=(LinearLayout)convertView.findViewById(R.id.RR);


        if(position%2==0)
        {
            RR.setBackgroundColor(Color.WHITE);
            tvSlNo.setTextColor(Color.BLACK);
            tvName.setTextColor(Color.BLACK);
            tv_Unit.setTextColor(Color.BLACK);
            tv_Qty.setTextColor(Color.BLACK);

        }
        else
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Gray2));
            tvSlNo.setTextColor(Color.BLACK);
            tvName.setTextColor(Color.BLACK);
            tv_Unit.setTextColor(Color.BLACK);
            tv_Qty.setTextColor(Color.BLACK);

        }



        return convertView;
    }

}
