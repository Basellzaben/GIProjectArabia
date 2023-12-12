package com.cds_jo.GalaxySalesApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Sales_Invoice_Rep2_Adapter extends ArrayAdapter<Sale_Invoice_Rep2_Model> {
    Context context;
    ArrayList<Sale_Invoice_Rep2_Model> data = new ArrayList<Sale_Invoice_Rep2_Model>();

    public Sales_Invoice_Rep2_Adapter(Context context, int layoutResourceId, ArrayList<Sale_Invoice_Rep2_Model> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        row = inflater.inflate(R.layout.sale_rep_row2, parent, false);
        holder = new RecordHolder();
        holder.CustName = (TextView) row.findViewById(R.id.CustName23);
        holder.date = (TextView) row.findViewById(R.id.date23);
      //  holder.type = (TextView) row.findViewById(R.id.type23);
       // holder.net = (TextView) row.findViewById(R.id.net23);
        holder.orderNo = (TextView) row.findViewById(R.id.orderNo23);
        row.setTag(holder);


        Sale_Invoice_Rep2_Model item=data.get(position);;
        holder.orderNo.setText(item.getType_Name());
        holder.CustName.setText(item.getItemWeight());
        holder.date.setText(item.getQTY());



        return row;
    }

    static class RecordHolder {

        TextView date,CustName,orderNo,type,net;

    }
}


