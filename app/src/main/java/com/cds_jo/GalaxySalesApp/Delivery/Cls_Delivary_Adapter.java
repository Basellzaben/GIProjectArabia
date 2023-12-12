package com.cds_jo.GalaxySalesApp.Delivery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.MainActivity;
import com.cds_jo.GalaxySalesApp.NewPackage.Cls_Customers_All_adapter;
import com.cds_jo.GalaxySalesApp.NewPackage.CustomerDays;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.StartUpActivity;

import java.util.ArrayList;

public class Cls_Delivary_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Cls_Delivary> cls_delivaries;
    TextView inv_no;
    TextView Cus_no;
    TextView Cus_Name;
    TextView Pay;
    public Cls_Delivary_Adapter(Context context, ArrayList<Cls_Delivary> list) {

        this.context = context;
        cls_delivaries = list;
    }

    @Override
    public int getCount() {

        return cls_delivaries.size();
    }

    @Override
    public Object getItem(int position) {

        return cls_delivaries.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        final Cls_Delivary obj = cls_delivaries.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.delivarydesign, null);

        }
         inv_no = (TextView) convertView.findViewById(R.id.inv_no);
        inv_no.setText(obj.getInvNo());


         Cus_no = (TextView) convertView.findViewById(R.id.Cus_no);
        Cus_no.setText(obj.getCustomerId());

         Cus_Name = (TextView) convertView.findViewById(R.id.Cus_Name);
        Cus_Name.setText(obj.getCustomerName());


        TextView Date = (TextView) convertView.findViewById(R.id.Date);
        Date.setText(obj.getInvDate());

         Pay = (TextView) convertView.findViewById(R.id.Pay);

        if(obj.getPayMethod().equals("1"))
        {
            Pay.setText("نقدي");
        }
        else
        {
            Pay.setText("ذمم");
        }

        Button Done =(Button)convertView.findViewById(R.id.imageButton15) ;
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences   sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("CustNo1", Cus_no.getText().toString());
                editor.putString("CustNm1", Cus_Name.getText().toString());

                editor.putString("Pay", Pay.getText().toString());
                editor.putString("V_OrderNo", inv_no.getText().toString());
                editor.commit();

                if(sharedPreferences.getString("CustNo", "").equals(""))
                {
                    Intent intent =new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
                else if(sharedPreferences.getString("CustNo", "").equals(Cus_no.getText().toString()))
                {
                    Intent intent =new Intent(context, SalesInvoiceAct.class);
                    context.startActivity(intent);
                }else {
                    Intent intent =new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });

        TextView total = (TextView) convertView.findViewById(R.id.total);
        total.setText(obj.getInvTotal());







        return convertView;
    }

}

