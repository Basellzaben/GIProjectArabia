package com.cds_jo.GalaxySalesApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hp on 18/03/2016.
 */
public class Cls_Item_Search_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Cls_Item_Search> cls_item_searches;

    public Cls_Item_Search_Adapter(Context context, ArrayList<Cls_Item_Search> list) {

        this.context = context;
        cls_item_searches = list;
    }

    @Override
    public int getCount() {

        return cls_item_searches.size();
    }

    @Override
    public Object getItem(int position) {

        return cls_item_searches.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Cls_Item_Search cls_item_search = cls_item_searches.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.itemsearchrow, null);

        }
        Methdes.MyTextView name = (Methdes.MyTextView) convertView.findViewById(R.id.tv_name);
        name.setText(cls_item_search.getName());

        Methdes.MyTextView no = (Methdes.MyTextView) convertView.findViewById(R.id.tv_no);
        no.setText(cls_item_search.getNo());



        return convertView;
    }

}


