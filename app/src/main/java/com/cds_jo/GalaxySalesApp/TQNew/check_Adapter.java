package com.cds_jo.GalaxySalesApp.TQNew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.R;

import java.util.ArrayList;
import java.util.Locale;

public class check_Adapter extends ArrayAdapter<check_Model> {
    Context context;
    ArrayList<check_Model> data = new ArrayList<check_Model>();

    public check_Adapter(Context context, int layoutResourceId, ArrayList<check_Model> data) {
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


        row = inflater.inflate(R.layout.check_row, parent, false);
        holder = new RecordHolder();
        holder.CustAccount = (TextView) row.findViewById(R.id.CustAccount);
        holder.CheckNumber = (TextView) row.findViewById(R.id.CheckNumber);
        holder.MaturityDate = (TextView) row.findViewById(R.id.MaturityDate);
        holder.AmountCurCredit = (TextView) row.findViewById(R.id.AmountCurCredit);
        row.setTag(holder);

        check_Model item = data.get(position);
        // TextView CustAccount = (TextView) row.findViewById(R.id.CustAccount);
        holder.CustAccount.setText(item.getCustAccount());
        holder.CheckNumber.setText(item.getCheckNumber());
        holder.MaturityDate.setText(item.getMaturityDate().substring(0,10));

        // holder.AmountCurCredit.setText( new DecimalFormat("##.##").format( Double.parseDouble(item.getAmountCurCredit())));

        holder.AmountCurCredit.setText(  String.format(Locale.US,"%.3f", Double.parseDouble(item.getAmountCurCredit())));
        return row;
    }

    static class RecordHolder {
        TextView CustAccount ;
        TextView CheckNumber ;
        TextView MaturityDate ;
        TextView AmountCurCredit ;

    }
}

