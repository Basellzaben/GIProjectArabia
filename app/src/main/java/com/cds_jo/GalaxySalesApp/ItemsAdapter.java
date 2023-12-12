package com.cds_jo.GalaxySalesApp;

import android.app.Activity;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.NewPackage.LocaleHelper;

import java.util.ArrayList;

public class ItemsAdapter extends ArrayAdapter<ItemsModel> {
    Context context;
    ArrayList<ItemsModel> data = new ArrayList<ItemsModel>();

    public ItemsAdapter(Context context, int layoutResourceId, ArrayList<ItemsModel> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder2 holder = null;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        row = inflater.inflate(R.layout.item_row, parent, false);
        holder = new RecordHolder2();
        holder.subjectNo = (TextView) row.findViewById(R.id.subjectNo);
        holder.subjectdesc = (TextView) row.findViewById(R.id.subjectdesc);
        holder.key = (ImageView) row.findViewById(R.id.key);

        row.setTag(holder);

        ItemsModel item = data.get(position);
        holder.subjectNo.setText(item.getSubjectID());
        holder.subjectdesc.setText(item.getSubjectDsec());


        holder.key.setImageResource(R.drawable.ic_edit_black_24dp);
  return row;
    }

    static class RecordHolder2 {

        TextView subjectNo; TextView subjectdesc;
        ImageView key;

    }
}

