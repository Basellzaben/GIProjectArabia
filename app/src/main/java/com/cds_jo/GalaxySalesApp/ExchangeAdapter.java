package com.cds_jo.GalaxySalesApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class ExchangeAdapter extends BaseAdapter {
    Context context;
    ArrayList<ExchangeModel> ExchangeModels;


    public ExchangeAdapter(Context context, ArrayList<ExchangeModel> list) {

        this.context = context;
        ExchangeModels = list;
    }@Override
    public int getCount() {

        return ExchangeModels.size();
    }

    @Override
    public Object getItem(int position) {

        return ExchangeModels.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }
    public long getPostion(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ExchangeModel ExchangeModel = ExchangeModels.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exchange_row, null);

        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(ExchangeModel.getnamedtl());

        TextView no = (TextView) convertView.findViewById(R.id.no);
        no.setText(ExchangeModel.getNo());

        TextView net = (TextView) convertView.findViewById(R.id.net);
        net.setText(ExchangeModel.getNet());


        TextView desc = (TextView) convertView.findViewById(R.id.desc);
        desc.setText(ExchangeModel.getDesc());


        TextView flag = (TextView) convertView.findViewById(R.id.flag);
        flag.setText(ExchangeModel.getFlag());

     /*   TextView tv_No = (TextView) convertView.findViewById(R.id.tv_No);
        tv_No.setText(ExchangeModel.getNo());*/

        return convertView;
    }

}



