package com.cds_jo.GalaxySalesApp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SalOrdersAdapter  extends BaseAdapter {
    LinearLayout RR;
    Context context;
    ArrayList<SaleOrder> contactList;
    SaleOrder SaleOrder;
    public SalOrdersAdapter(Context context, ArrayList<SaleOrder> list) {

        this.context = context;
        contactList = list;
    }

    @Override
    public int getCount() {

        return contactList.size();
    }

    @Override
    public Object getItem(int position) {

        return contactList.get(position);
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
         SaleOrder = contactList.get(position);
        LayoutInflater inflater;
        if (convertView == null) {
             inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.salorder_row, null);

        }
        TextView tvSlNo = (TextView) convertView.findViewById(R.id.id);
        tvSlNo.setText(SaleOrder.getOrderNo());
        TextView date = (TextView) convertView.findViewById(R.id.date);
        date.setText(SaleOrder.getTr_Date());
        TextView note = (TextView) convertView.findViewById(R.id.note);
        note.setText(SaleOrder.getNote());

         RR=(LinearLayout)convertView.findViewById(R.id.RR);


        if(position%2==0)
        {
            RR.setBackgroundColor(Color.WHITE);
            tvSlNo.setTextColor(Color.BLACK);
          
        }
        else
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Gray2));
            tvSlNo.setTextColor(Color.BLACK);
           
        }


  /*      convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RR.setBackgroundColor(context.getResources().getColor(R.color.Blue));
                Toast.makeText(view.getContext(),"click : "+SaleOrder.getId(),Toast.LENGTH_LONG).show();
                notifyDataSetChanged();
            }
        });
*/
        return convertView;
    }

}
