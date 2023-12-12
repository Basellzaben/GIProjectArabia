package com.cds_jo.GalaxySalesApp.TQNew;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Tab_SalesOrders1 extends Fragment {
    ArrayList<cls_Tab_Order_Sales1> cls_Tab_Order_Sales  ;
    ListView items_Lsit;
    TextView count,tv_Summatin;
    String OrderNo,CustNm;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_sales_orders1,container,false);
        items_Lsit=(ListView) v.findViewById(R.id.SalesSummery);
        items_Lsit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                arg1.setBackgroundColor(Color.GRAY);
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat df = (DecimalFormat) nf;
                cls_Tab_Order_Sales1 o = (cls_Tab_Order_Sales1) items_Lsit.getItemAtPosition(position);
                OrderNo = (String) o.getCustNo();
                CustNm = (String) o.getCustNm();
                ShowOrderDetails();
            }
        });
        cls_Tab_Order_Sales  = new ArrayList<cls_Tab_Order_Sales1>();
        cls_Tab_Order_Sales.clear();
        count = (TextView)v.findViewById(R.id.tv_Count);
        tv_Summatin = (TextView)v.findViewById(R.id.tv_Summatin);

        FillList();
        return v;
    }
    private  Double SToD(String str){
        String f = "";
        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        final DecimalFormat df = (DecimalFormat)nf;
        str = str.replace(",", "");
        Double d = 0.0;
        if (str.length()==0) {
            str = "0";
        }
        if (str.length()>0)
            try {
                d =  Double.parseDouble(str);
                str = df.format(d).replace(",", "");

            }
            catch (Exception ex)
            {
                str="0";
            }

        df.setParseBigDecimal(true);

        d = Double.valueOf(str.trim()).doubleValue();

        return d;
    }
    public void FillList(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());
        String q ="";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        cls_Tab_Order_Sales.clear();
        items_Lsit.setAdapter(null);
        String  LoginAccount= sharedPreferences.getString("UserID", "") ;
        q =     " Select distinct po.posted as SuperVisorNotes , po.orderno,po.date, c.name,po.acc ,po.posted  ,COALESCE(po.Net_Total,0) as total" +
                " from Po_Hdr po Left join Customers c on c.no = po.acc" +
                "  left join SalesOrdersStutes s on  cast( s.OrderNo as integer)  = cast( po.orderno  as integer)  " +
                " where  po.posted='-1' and po. userid ='"+LoginAccount+"'  order by po.no desc ";
        //"' and  po.date ='" + currentDateandTime + "'";

        SqlHandler sqlHandler = new SqlHandler(getActivity());
        Cursor c1 = sqlHandler.selectQuery(q);


        cls_Tab_Order_Sales1 cls_searchpos;


        Double sum = 0.0 ;
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    cls_searchpos= new cls_Tab_Order_Sales1();
                    cls_searchpos.setCustNo(c1.getString(c1.getColumnIndex("orderno")));
                    cls_searchpos.setCustNm(c1.getString(c1.getColumnIndex("name")));
                    cls_searchpos.setDate(c1.getString(c1.getColumnIndex("date")));
                    cls_searchpos.setAcc(c1.getString(c1.getColumnIndex("acc")));

                    cls_searchpos.setNotes("غير مرحل");


                    cls_searchpos.setType("0");
                    cls_searchpos.setTot(c1.getString(c1.getColumnIndex("total")));
                    sum=sum+SToD(c1.getString(c1.getColumnIndex("total")));
                    cls_Tab_Order_Sales.add(cls_searchpos);
                }while (c1.moveToNext());
            }

            c1.close();
        }

        tv_Summatin.setText(sum +"");
        tv_Summatin.setText(SToD(tv_Summatin.getText().toString()).toString());
        count.setText((cls_Tab_Order_Sales.size()) +"");
        cls_Tab__Order_Sales_adapter1 SalesAdapter = new cls_Tab__Order_Sales_adapter1(
                this.getActivity(),cls_Tab_Order_Sales, Tab_SalesOrders1.this);

        items_Lsit.setAdapter(SalesAdapter);
    }
    private  void ShowOrderDetails (){
        Bundle bundle = new Bundle();
        FragmentManager Manager = getActivity().getFragmentManager();
        bundle.putString("OrderNo", OrderNo);
        bundle.putString("OrderEmp", "");
        bundle.putString("CustNm", CustNm);
        bundle.putString("Posted", "");
        PopShowOrderChanges1 obj = new PopShowOrderChanges1();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }
}
