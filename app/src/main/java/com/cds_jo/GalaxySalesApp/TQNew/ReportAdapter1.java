package com.cds_jo.GalaxySalesApp.TQNew;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.R;

import java.util.ArrayList;

public class ReportAdapter1 extends ArrayAdapter<RepModel1> {
    Context context;
    ArrayList<RepModel1> data = new ArrayList<RepModel1>();

    public ReportAdapter1(Context context, int layoutResourceId, ArrayList<RepModel1> data) {
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


        row = inflater.inflate(R.layout.rep_row1, parent, false);
        holder = new RecordHolder();
        holder.CustNo = (TextView) row.findViewById(R.id.CustNo);
        holder.CustName = (TextView) row.findViewById(R.id.CustName);
        holder.OrderNo = (TextView) row.findViewById(R.id.OrderNo);
        holder.Total = (TextView) row.findViewById(R.id.Total);
        holder.TaxTotal = (TextView) row.findViewById(R.id.TaxTotal);
        holder.NetTotal = (TextView) row.findViewById(R.id.NetTotal);
        holder.DESC_A = (TextView) row.findViewById(R.id.DESC_A);
        row.setTag(holder);

        RepModel1 item = data.get(position);
        holder.CustNo.setText(item.getCustNo());
        holder.CustName.setText(item.getCustName());
        holder.OrderNo.setText(item.getOrderNo());
        holder.Total.setText(item.getTotal());
        holder.TaxTotal.setText(item.getTaxTotal());
        holder.NetTotal.setText(item.getNetTotal());
        holder.DESC_A.setText(item.getDESC_A());


        if(item.getStates().equals("1")){
            holder.DESC_A.setTextColor(Color.parseColor("#335DFF"));
        } else if(item.getStates().equals("2")){
            holder.DESC_A.setTextColor(Color.parseColor("#F68208"));
        }else if(item.getStates().equals("5")){
            holder.DESC_A.setTextColor(Color.parseColor("#32CA1A"));
        }else {
            holder.DESC_A.setTextColor(Color.parseColor("#CA1A1A"));
        }


  /* convertView.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Bundle bundle = new Bundle();
           FragmentManager Manager =  getFragmentManager();
           PopSmallMenue obj = new PopSmallMenue();
           bundle.putString("Msg",  CustNo + "   " +CustNm );
           obj.setArguments(bundle);
           obj.show(Manager, null);

       }
   });*/

        // holder.AmountCurCredit.setText( new DecimalFormat("##.##").format( Double.parseDouble(item.getAmountCurCredit())));

        //holder.AmountCurCredit.setText(  String.format(Locale.US,"%.3f", Double.parseDouble(item.getAmountCurCredit())));
        return row;
    }

    static class RecordHolder {

        TextView CustNo; TextView CustName;TextView OrderNo; TextView Total;
        TextView TaxTotal; TextView NetTotal;TextView DESC_A;

    }
}

