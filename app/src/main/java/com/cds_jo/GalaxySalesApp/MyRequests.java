package com.cds_jo.GalaxySalesApp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.assist.CallWebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import header.Header_Frag;

public class MyRequests extends AppCompatActivity {
ListView salorderlist;
    ArrayList<myrequestsmodel> contactList;
    Drawable greenProgressbar;
    RelativeLayout.LayoutParams lp;
    SharedPreferences sharedPreferences;
    public void btn_back(View view) {
        Intent k = new Intent(this,JalMasterActivity.class);
        startActivity(k);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);




        Fragment frag = new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1, frag).commit();

        salorderlist = (ListView) findViewById(R.id.myrequestlistt);

        contactList = new ArrayList<myrequestsmodel>();
        contactList.clear();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String man = sharedPreferences.getString("UserID", "");

        GetData( man);



    }
    public void showSalOrderList(){

        myrequestAdapter contactListAdapter = new myrequestAdapter(MyRequests.this, contactList);
        salorderlist.setAdapter(contactListAdapter);
    }

    public void GetData(final String man) {
        
        contactList.clear();
        showSalOrderList();
        TextView tv = new TextView(MyRequests.this);
        lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setLayoutParams(lp);
        tv.setPadding(10, 15, 10, 15);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundColor(Color.BLUE);
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);

        final Handler _handler = new Handler();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyRequests.this);
        final String UserID = sharedPreferences.getString("UserID", "");
        final ProgressDialog custDialog = new ProgressDialog(MyRequests.this);
        custDialog.setProgressStyle(custDialog.STYLE_HORIZONTAL);
        custDialog.setCanceledOnTouchOutside(false);
        custDialog.setProgress(0);
        custDialog.setMax(100);
        custDialog.setMessage("  الرجاء الانتظار ..." + "  العمل جاري على جلب البيانات  ");
        tv.setText("طلباتي");
        custDialog.setCustomTitle(tv);
        custDialog.setProgressDrawable(greenProgressbar);
        custDialog.show();


        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(MyRequests.this);



                ws.GatManStatesOrder("-1","-1","204" );
                try {

                    Integer i;
                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray inovice_type= js.getJSONArray("inovice_type");
                    JSONArray CustName = js.getJSONArray("CustName");
                    JSONArray OrderNo = js.getJSONArray("OrderNo");
                    JSONArray Tr_Date = js.getJSONArray("Tr_Date");
                    JSONArray CustNo = js.getJSONArray("CustNo");
                    JSONArray Notes = js.getJSONArray("Notes");


                    myrequestsmodel obj ;
                    for (i = inovice_type.length()-1; i > -1 ; i--) {
                        obj = new myrequestsmodel();
                        obj.setInovice_type(inovice_type.get(i).toString());
                        obj.setCustName(CustName.get(i).toString());
                        obj.setOrderNo(OrderNo.get(i).toString());
                        obj.setTr_Date(Tr_Date.get(i).toString());
                        obj.setNotes(Notes.get(i).toString());
                        obj.setCustNo(CustNo.get(i).toString());
                        contactList.add(obj);

                        custDialog.setMax(inovice_type.length());
                        custDialog.incrementProgressBy(1);
                        if (custDialog.getProgress() == custDialog.getMax()) {
                            custDialog.dismiss();
                        }
                    }
                    final int total = i;
                    _handler.post(new Runnable() {

                        public void run() {
                            custDialog.dismiss();
                            showSalOrderList();
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    MyRequests.this).create();
                            alertDialog.setTitle("طلباتي");
                            alertDialog.setMessage("تمت عملية التحديث بنجاح");
                            alertDialog.setIcon(R.drawable.tick);
                            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            });
                            //   alertDialog.show();


                        }
                    });

                } catch (final Exception e) {
                    custDialog.dismiss();
                    _handler.post(new Runnable() {

                        public void run() {
                            custDialog.dismiss();
                            try{
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        MyRequests.this).create();
                                alertDialog.setTitle("طلباتي");
                                alertDialog.setMessage( "لا يوجد طلبات");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alertDialog.show();}catch (Exception e){}

                        }
                    });
                }
            }
        }).start();
    }

}