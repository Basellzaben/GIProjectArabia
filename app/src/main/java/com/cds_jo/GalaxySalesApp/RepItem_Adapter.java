package com.cds_jo.GalaxySalesApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RepItem_Adapter extends ArrayAdapter<ItemsRepModel> {
    Context context;
    ArrayList<ItemsRepModel> data = new ArrayList<ItemsRepModel>();

    public RepItem_Adapter(Context context, int layoutResourceId, ArrayList<ItemsRepModel> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RepItem_Adapter.RecordHolder2 holder = null;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        row = inflater.inflate(R.layout.item_row_rep, parent, false);
        holder = new RepItem_Adapter.RecordHolder2();
        holder.Item_no = (TextView) row.findViewById(R.id.Item_no);
        holder.Item_Name = (TextView) row.findViewById(R.id.Item_Name);
        holder.price = (TextView) row.findViewById(R.id.price);
        holder.Qty = (TextView) row.findViewById(R.id.Qty);
        holder.UnitName = (TextView) row.findViewById(R.id.UnitName);
        holder.Tax_Amt = (TextView) row.findViewById(R.id.Tax_Amt);
        holder.Dis_Amt = (TextView) row.findViewById(R.id.Dis_Amt);
        holder.Bounce = (TextView) row.findViewById(R.id.Bounce);
        holder.Total = (TextView) row.findViewById(R.id.Total);
        row.setTag(holder);

        ItemsRepModel item = data.get(position);
        holder.Item_no.setText(item.getItem_no());
        holder.Item_Name.setText(item.getItem_Name());
        holder.price.setText(item.getPrice());
        holder.Qty.setText(item.getQty());
        holder.UnitName.setText(item.getUnitName());
        holder.Tax_Amt.setText(item.getTax_Amt());
        holder.Dis_Amt.setText(item.getDis_Amt());
        holder.Bounce.setText(item.getBounce());
        holder.Total.setText(item.getTotal());


        return row;
    }

    static class RecordHolder2 {

        TextView Item_no; TextView Item_Name;
        TextView price; TextView Qty;
        TextView UnitName; TextView Tax_Amt;
        TextView Dis_Amt; TextView Bounce;
        TextView Total; 

    }
}


