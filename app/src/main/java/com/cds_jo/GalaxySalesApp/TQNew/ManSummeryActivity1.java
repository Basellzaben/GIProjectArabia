package com.cds_jo.GalaxySalesApp.TQNew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.NewPackage.GalaxyNewHomeActivity;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.Tab_Payments;
import com.cds_jo.GalaxySalesApp.Tab_UnpostedTransaction;
import com.cds_jo.GalaxySalesApp.Tab_UsedCode;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import Methdes.MyTextView;
import header.Header_Frag;

public class ManSummeryActivity1 extends FragmentActivity {

    SqlHandler sqlHandler;
    SharedPreferences sharedPreferences;
    private Context context=ManSummeryActivity1.this;
    private MyTextView T1,T2,T3,T4,T5;
    private Fragment frag;
    private FragmentManager fragmentManager;
    String LoginAccount = "";
    String ManLevel,UserID;
    RelativeLayout.LayoutParams lp;
    Drawable greenProgressbar;
    TextView tv  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_summery1);
        sqlHandler = new SqlHandler(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        UserID = sharedPreferences.getString("UserID", "");
        LoginAccount= sharedPreferences.getString("LoginAccount", "") ;
        ManLevel="1";
        ManLevel = DB.GetValue(this, "manf", "ManLevel", "LOWER(username)  like'" + LoginAccount.toString().toLowerCase() + "'");

        FillOrderStutes();
        Initi();
        Click();

        frag=new Header_Frag();
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1,frag).commit();

   /*     frag=new Tab_Payments();
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag11,frag).commit();*/
        frag=new Tab_SalesOrders1();
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag11,frag).commit();
    }
    private  void FillOrderStutes(){
        String q ;
        q = "Delete from SalesOrdersStutes ";
        sqlHandler.executeQuery(q);

        greenProgressbar = this.getResources().getDrawable(R.drawable.progrees_states);
        final Handler _handler = new Handler();
        tv = new TextView(this);
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

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setMessage("  الرجاء الانتظار ..." + "  العمل جاري على نسخ البيانات  ");
        tv.setText("حالة طلبات البيع");
        progressDialog.setCustomTitle(tv);
        progressDialog.setProgressDrawable(greenProgressbar);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(ManSummeryActivity1.this);
                ws.GetSalesOrderStutes(LoginAccount,ManLevel );
                try {
                    Integer i;
                    String q;
                    JSONObject js = new JSONObject(We_Result.Msg);

                    JSONArray js_OrderNo = js.getJSONArray("OrderNo");
                    JSONArray js_Posted = js.getJSONArray("Posted");
                    JSONArray js_SuperVisorNotes = js.getJSONArray("SuperVisorNotes");
                    JSONArray js_UserID= js.getJSONArray("UserID");

                    for (i = 0; i < js_OrderNo.length(); i++) {

                        q = "INSERT INTO SalesOrdersStutes(OrderNo,Posted,SuperVisorNotes,UserID,SalesManNotes) values ('"
                                + js_OrderNo.get(i).toString()
                                + "','" + js_Posted.get(i).toString()
                                + "','" + js_SuperVisorNotes.get(i).toString()
                                + "','" + js_UserID.get(i).toString()
                                + "',' ')";

                        sqlHandler.executeQuery(q);

                        progressDialog.setMax(js_OrderNo.length());
                        progressDialog.incrementProgressBy(1);
                        if (progressDialog.getProgress() == progressDialog.getMax()) {
                            progressDialog.dismiss();
                        }
                    }
                    final int total = i;
                    _handler.post(new Runnable() {
                        public void run() {


                            progressDialog.dismiss();

                        }
                    });
                } catch (final Exception e) {
                    progressDialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {

                        }
                    });
                }
            }
        }).start();

    }
    private void Click() {
        T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*T1.setBackgroundColor(getResources().getColor(R.color.Blue));
                T1.setTextColor(Color.WHITE);

                T2.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T2.setTextColor(getResources().getColor(R.color.Blue));

                T3.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T3.setTextColor(getResources().getColor(R.color.Blue));

                T4.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T4.setTextColor(getResources().getColor(R.color.Blue));

                T5.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T5.setTextColor(getResources().getColor(R.color.Blue));

                frag=new Tab_Visits_Summery();
                fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.Frag11,frag).commit();*/

                Intent k;
                k = new Intent(ManSummeryActivity1.this, GalaxyNewHomeActivity.class);
                startActivity(k);
            }
        });

        //////////////////////////////////////////////////////////////

        T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                T1.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T1.setTextColor(getResources().getColor(R.color.Blue));

                T2.setBackgroundColor(getResources().getColor(R.color.Blue));
                T2.setTextColor(Color.WHITE);


                T3.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T3.setTextColor(getResources().getColor(R.color.Blue));

                T4.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T4.setTextColor(getResources().getColor(R.color.Blue));

                T5.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T5.setTextColor(getResources().getColor(R.color.Blue));

                frag=new Tab_Payments();
                fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.Frag11,frag).commit();
            }
        });

        ////////////////////////////////////////////////////////////////////

        T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                T1.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T1.setTextColor(getResources().getColor(R.color.Blue));

                T2.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T2.setTextColor(getResources().getColor(R.color.Blue));

                T3.setBackgroundColor(getResources().getColor(R.color.Blue));
                T3.setTextColor(Color.WHITE);

                T4.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T4.setTextColor(getResources().getColor(R.color.Blue));

                T5.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T5.setTextColor(getResources().getColor(R.color.Blue));

                frag=new Tab_SalesOrders1();
                fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.Frag11,frag).commit();
            }
        });

        //////////////////////////////////////////////////////////////

        T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T1.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T1.setTextColor(getResources().getColor(R.color.Blue));

                T2.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T2.setTextColor(getResources().getColor(R.color.Blue));

                T3.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T3.setTextColor(getResources().getColor(R.color.Blue));

                T4.setBackgroundColor(getResources().getColor(R.color.Blue));
                T4.setTextColor(Color.WHITE);

                T5.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T5.setTextColor(getResources().getColor(R.color.Blue));

                frag=new Tab_UsedCode();
                fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.Frag11,frag).commit();
            }
        });

        ////////////////////////////////////////////////////////////////////

        T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T1.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T1.setTextColor(getResources().getColor(R.color.Blue));

                T2.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T2.setTextColor(getResources().getColor(R.color.Blue));

                T3.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T3.setTextColor(getResources().getColor(R.color.Blue));

                T4.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                T4.setTextColor(getResources().getColor(R.color.Blue));

                T5.setBackgroundColor(getResources().getColor(R.color.Blue));
                T5.setTextColor(Color.WHITE);

                frag=new Tab_UnpostedTransaction();
                fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.Frag11,frag).commit();
            }
        });

        ////////////////////////////////////////////////////////////////////
    }

    private void Initi() {
        T1=(MyTextView)findViewById(R.id.T1);
        T2=(MyTextView)findViewById(R.id.T2);
        T3=(MyTextView)findViewById(R.id.T3);
        T4=(MyTextView)findViewById(R.id.T4);
        T5=(MyTextView)findViewById(R.id.T5);
    }
    @Override
    public void onBackPressed() {
        Intent k = new Intent(this, GalaxyNewHomeActivity.class);
        startActivity(k);
    }


}
