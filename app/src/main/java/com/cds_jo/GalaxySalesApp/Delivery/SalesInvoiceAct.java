package com.cds_jo.GalaxySalesApp.Delivery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.NewPackage.GalaxyNewHomeActivity;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.assist.Sale_InvoiceActivity;

import java.util.ArrayList;

import header.Header_Frag;

public class SalesInvoiceAct extends AppCompatActivity {
TextView tv_cusnm,et_OrdeNo,tv_acc,et_Total,et_dis,et_TotalTax,tv_NetTotal;
CheckBox chk_Type;

    clsSalesDtl clsSalesDtl;
    clsSalesDtalAdapter clsSalesDtalAdapter;
    ListView listView;
    ArrayList<clsSalesDtl> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_invoice);
        Fragment frag = new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1, frag).commit();
        listView = (ListView) findViewById(R.id.LstvItems);
        list = new ArrayList<>();
        tv_cusnm =(TextView)findViewById(R.id.tv_cusnm) ;
        et_OrdeNo =(TextView)findViewById(R.id.et_OrdeNo) ;
        tv_acc =(TextView)findViewById(R.id.tv_acc) ;
        et_Total =(TextView)findViewById(R.id.et_Total) ;
        et_dis =(TextView)findViewById(R.id.et_dis) ;
        et_TotalTax =(TextView)findViewById(R.id.et_TotalTax) ;
        tv_NetTotal =(TextView)findViewById(R.id.tv_NetTotal) ;
        chk_Type =(CheckBox)findViewById(R.id.chk_Type) ;
        String trans;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SalesInvoiceAct.this);

        if(!(sharedPreferences.getString("CustNo1", "").equals("")) &&
                !(sharedPreferences.getString("CustNm1", "").equals(""))&&
                !(sharedPreferences.getString("V_OrderNo", "").equals(""))
        )
        {
            tv_cusnm.setText(sharedPreferences.getString("CustNm1", ""));
            tv_acc.setText(sharedPreferences.getString("CustNo1", ""));
            et_OrdeNo.setText(sharedPreferences.getString("V_OrderNo", ""));
        }
        else
            return;


        if(!(sharedPreferences.getString("V_OrderNo", "").equals("")))
        {
            if (sharedPreferences.getString("Pay", "").equals("1")) {
                chk_Type.setChecked(true);
            }else {
                chk_Type.setChecked(false);
            }
            String dTotal = DB.GetValue(SalesInvoiceAct.this, "SalesHdr", "dtotal", "bill='" + sharedPreferences.getString("V_OrderNo", "") + "'");//    "SELECT  name   from  manf where man = '" + email.toString() + "' And password='" + password.toString() + "'";
            String Total = DB.GetValue(SalesInvoiceAct.this, "SalesHdr", "tot", "bill='" + sharedPreferences.getString("V_OrderNo", "") + "'");//    "SELECT  name   from  manf where man = '" + email.toString() + "' And password='" + password.toString() + "'";
            String Sum = DB.GetValue(SalesInvoiceAct.this, "SalesHdr", "totalwithtax", "bill='" + sharedPreferences.getString("V_OrderNo", "") + "'");//    "SELECT  name   from  manf where man = '" + email.toString() + "' And password='" + password.toString() + "'";
            String totaltax = DB.GetValue(SalesInvoiceAct.this, "SalesHdr", "TotalTax", "bill='" + sharedPreferences.getString("V_OrderNo", "") + "'");//    "SELECT  name   from  manf where man = '" + email.toString() + "' And password='" + password.toString() + "'";
             trans = DB.GetValue(SalesInvoiceAct.this, "SalesHdr", "trans_no", "bill='" + sharedPreferences.getString("V_OrderNo", "") + "'");//    "SELECT  name   from  manf where man = '" + email.toString() + "' And password='" + password.toString() + "'";
           if(!(Total.equals("-1")))
              et_Total.setText(Total);
           else
               et_Total.setText("0");
            if(!(dTotal.equals("-1")))
                et_dis.setText(dTotal);
            else
                et_dis.setText("0");

            if(!(totaltax.equals("-1")))
                et_TotalTax.setText(totaltax);
            else
                et_TotalTax.setText("0");

            if(!(Sum.equals("-1")))
                tv_NetTotal.setText(Sum);
            else
                tv_NetTotal.setText("0");

        }
        else
        {
            return;
        }
      getdata(trans);
    }

    private void getdata(String trans) {

        SqlHandler sqlHandler = new SqlHandler(SalesInvoiceAct.this);

        String q = "select trans_no , item_no  ,qty ,price ,UnitNo , TotalTax  ,linedis ,SumWithOutTax  ,ItemTax ,bonus from SalesDtl where trans_no = '"+trans+"' ";


        Cursor c1 = sqlHandler.selectQuery(q);

        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {

                    clsSalesDtl = new clsSalesDtl();

                    clsSalesDtl.setTrans_no(c1.getString(c1
                            .getColumnIndex("trans_no")));
                    clsSalesDtl.setItem_no(c1.getString(c1
                            .getColumnIndex("item_no")));
                    clsSalesDtl.setQty(c1.getString(c1
                            .getColumnIndex("qty")));


                    clsSalesDtl.setPrice(c1.getString(c1
                            .getColumnIndex("price")));


                    clsSalesDtl.setUnitNo(c1.getString(c1
                            .getColumnIndex("UnitNo")));

                    clsSalesDtl.setItemTax(c1.getString(c1
                            .getColumnIndex("TotalTax")));
                    clsSalesDtl.setLinedis(c1.getString(c1
                            .getColumnIndex("linedis")));
                    clsSalesDtl.setSumWithOutTax(c1.getString(c1
                            .getColumnIndex("SumWithOutTax")));
                    clsSalesDtl.setItemTax(c1.getString(c1
                            .getColumnIndex("ItemTax")));
                    clsSalesDtl.setBonus(c1.getString(c1
                            .getColumnIndex("bonus")));




                    list.add(clsSalesDtl);

                } while (c1.moveToNext());

            }
            c1.close();
        }


        clsSalesDtalAdapter = new clsSalesDtalAdapter(
                SalesInvoiceAct.this, list);
        listView.setAdapter(clsSalesDtalAdapter);
    }
    public void btn_back(View v)
    {
        Intent i =new Intent(SalesInvoiceAct.this, GalaxyNewHomeActivity.class);
        startActivity(i);
        finish();
    }

    public void btn_save_po(View v)
    { SqlHandler sqlHandler = new SqlHandler(this);
        if (et_OrdeNo.getText().toString().equals(""))
        {
            Toast.makeText(SalesInvoiceAct.this,"يجب اخيار فاتورة",Toast.LENGTH_LONG).show();
        }
        else
        {
        String query = " Update Delivery set Delivered=1 where InvNo = '"+et_OrdeNo.getText().toString()+"'" ;

        sqlHandler.executeQuery(query);
            AlertDialog alertDialog = new AlertDialog.Builder(
                    SalesInvoiceAct.this).create();
            alertDialog.setTitle("سند تسليم");
            alertDialog.setMessage("تم الحفظ سند تسليم البضاعة بنجاح"  );
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {


                }
            });

            alertDialog.show();

        }}
}