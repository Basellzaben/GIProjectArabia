package com.cds_jo.GalaxySalesApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.NewPackage.LocaleHelper;

import java.util.ArrayList;

public class Sales_Invoice_Rep_Adapter extends ArrayAdapter<Sale_Invoice_Rep_Model> {
    Context context;
    ArrayList<Sale_Invoice_Rep_Model> data = new ArrayList<Sale_Invoice_Rep_Model>();

    public Sales_Invoice_Rep_Adapter(Context context, int layoutResourceId, ArrayList<Sale_Invoice_Rep_Model> data) {
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

        row = inflater.inflate(R.layout.sale_rep_row, parent, false);
        holder = new RecordHolder();
        holder.wight = (TextView) row.findViewById(R.id.wight);
        holder.qty = (TextView) row.findViewById(R.id.qty);
        holder.name = (TextView) row.findViewById(R.id.name);
        row.setTag(holder);


        Sale_Invoice_Rep_Model item=data.get(position);;
        holder.wight.setText(item.getWight());
        holder.name.setText(item.getname());
        holder.qty.setText(item.getQty());



  return row;
    }

    static class RecordHolder {

        TextView name,wight,qty;

    }
}

