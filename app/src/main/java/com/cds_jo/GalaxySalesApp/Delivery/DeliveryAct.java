package com.cds_jo.GalaxySalesApp.Delivery;


import android.content.Intent;
import android.database.Cursor;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cds_jo.GalaxySalesApp.NewPackage.GalaxyNewHomeActivity;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;

import java.util.ArrayList;

import header.Header_Frag;


public class DeliveryAct extends AppCompatActivity {
    Cls_Delivary clsDelivary;
    Cls_Delivary_Adapter clsDelivaryAdapter;
    ListView listView;
    ArrayList<Cls_Delivary> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Fragment frag = new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1, frag).commit();
        listView = (ListView) findViewById(R.id.LstvItems);
        list = new ArrayList<>();



        getData();
    }

    private void getData() {




            SqlHandler sqlHandler = new SqlHandler(DeliveryAct.this);

            String q = "select RepId,CustomerId,CustomerName,InvDate,InvNo,InvTotal,PaymentMethod from delivery where Delivered =0";


            Cursor c1 = sqlHandler.selectQuery(q);

            if (c1 != null && c1.getCount() != 0) {
                if (c1.moveToFirst()) {
                    do {

                        clsDelivary = new Cls_Delivary();

                        clsDelivary.setCustomerId(c1.getString(c1
                                .getColumnIndex("CustomerId")));
                        clsDelivary.setCustomerName(c1.getString(c1
                                .getColumnIndex("CustomerName")));
                        clsDelivary.setInvNo(c1.getString(c1
                                .getColumnIndex("InvNo")));


                        clsDelivary.setInvTotal(c1.getString(c1
                                .getColumnIndex("InvTotal")));


                        clsDelivary.setRepId(c1.getString(c1
                                .getColumnIndex("RepId")));

                        clsDelivary.setPayMethod(c1.getString(c1
                                .getColumnIndex("PaymentMethod")));
                        clsDelivary.setInvDate(c1.getString(c1
                                .getColumnIndex("InvDate")));





                        list.add(clsDelivary);

                    } while (c1.moveToNext());

                }
                c1.close();
            }


        clsDelivaryAdapter = new Cls_Delivary_Adapter(
                DeliveryAct.this, list);
        listView.setAdapter(clsDelivaryAdapter);

        }
        public void btn_back(View v)
        {
            Intent i =new Intent(DeliveryAct.this, GalaxyNewHomeActivity.class);
            startActivity(i);
            finish();
        }

}