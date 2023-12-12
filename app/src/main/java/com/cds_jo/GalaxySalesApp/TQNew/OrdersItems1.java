package com.cds_jo.GalaxySalesApp.TQNew;

import android.app.Activity;
import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.Cls_SaleManDailyRound;
import com.cds_jo.GalaxySalesApp.ComInfo;
import com.cds_jo.GalaxySalesApp.Companies;
import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.GalaxyLoginActivity;
import com.cds_jo.GalaxySalesApp.GalaxyMainActivity;
import com.cds_jo.GalaxySalesApp.MainActivity;
import com.cds_jo.GalaxySalesApp.NewPackage.GalaxyNewHomeActivity;
import com.cds_jo.GalaxySalesApp.OrderApproval.Show_Orders_Need_Approval;
import com.cds_jo.GalaxySalesApp.PopOrderSelesDetails;
import com.cds_jo.GalaxySalesApp.Pop_Update_Qty;
import com.cds_jo.GalaxySalesApp.R;

import com.cds_jo.GalaxySalesApp.SCR_ACTIONS;
import com.cds_jo.GalaxySalesApp.Select_Customer;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.assist.Acc_ReportActivity;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.cds_jo.GalaxySalesApp.assist.Logtrans.InsertLogTrans;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Methdes.MyTextView;
import header.Header_Frag;
import header.SimpleSideDrawer;


public class OrdersItems1 extends FragmentActivity {
    SqlHandler sqlHandler;
    ListView lv_Items ;
    ArrayList<ContactListItems1> contactList;

    String UserID = "";
    String LoginAccount = "";
    public ProgressDialog loadingdialog;
    public String json;
    Boolean IsNew;
    String CatNo = "-1";
    CheckBox chk_MobileOrder;
    TextView tv_LocationNm;
    ImageView imgBack;
    TextView CustNm;
    TextView accno;
    LinearLayout LytSerach,Lyt_CancleOrder;
    MyTextView ApprovalOrdersCount;
    LinearLayout NotiLyt;
    RelativeLayout.LayoutParams lp;
    Drawable greenProgressbar;
    TextView tv;
    SharedPreferences sharedPreferences;
    String q;
    String ManLevel;
    ImageView Img_Menu;
    TextView OrderNo;
    private SimpleSideDrawer mNav;
    String ForApproval = "0";
    String OwnerSalesManNo = "";
    int UserDoSave = 0;
    Cursor cc;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
       /*     if (ComInfo.Login == 0) {
                Intent i = new Intent(this, GalaxyLoginActivity.class);
                startActivity(i);
                finish();
            }*/
        }catch (Exception sdf){}
        setContentView(R.layout.fragment_orders_items1);
        sqlHandler = new SqlHandler(this);
        CheckBox Tax_Include = (CheckBox) findViewById(R.id.chk_Tax_Include);
        Tax_Include.setChecked(false);
        Tax_Include.setVisibility(View.VISIBLE);
        lv_Items = (ListView) findViewById(R.id.LstvItems);
        sqlHandler = new SqlHandler(this);
        IsNew = true;
        sharedPreferences     = PreferenceManager.getDefaultSharedPreferences(this);
        UserID = sharedPreferences.getString("UserID", "");
        LoginAccount = sharedPreferences.getString("LoginAccount", "");
        ManLevel = "1";
        ManLevel = DB.GetValue(this, "manf", "ManLevel", "LOWER(username)  like'" + LoginAccount.toString().toLowerCase() + "'");
        ForApproval = "0";
        OrderNo = (TextView) findViewById(R.id.et_OrdeNo);
        mNav = new SimpleSideDrawer(OrdersItems1.this);
        mNav.setLeftBehindContentView(R.layout.po_nav_menu);


        Img_Menu = (ImageView) findViewById(R.id.Img_Menu);
        Img_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav.toggleLeftDrawer();
            }
        });

        accno = (TextView) findViewById(R.id.tv_acc);

        accno.setText(sharedPreferences.getString("CustNo", ""));
        String cus = DB.GetValue(this, "Customers", "Tax_Cus", "no ='" + sharedPreferences.getString("CustNo", "") + "'");
        CustNm = (TextView) findViewById(R.id.tv_cusnm);
        CustNm.setText(sharedPreferences.getString("CustNm", ""));
        if(cus.equals("DZ_AR"))
        {
            CustNm.setTextColor(getResources().getColor(R.color.Red));
        }
        else
        {

            CustNm.setTextColor(getResources().getColor(R.color.Black11));

        }



      /*  Button btn_ReturnItems, btn_Payments, btn_ShowAccReport, btn_ShowLastInvoice, btn_ShowStoreQty, btn_GetInvoiceFromBatch, btn_EnterNotes;
        btn_ShowAccReport = (Button) mNav.findViewById(R.id.btn_ShowAccReport);
        btn_ShowLastInvoice = (Button) mNav.findViewById(R.id.btn_ShowLastInvoice);
        btn_ShowStoreQty = (Button) mNav.findViewById(R.id.btn_ShowStoreQty);
        btn_GetInvoiceFromBatch = (Button) mNav.findViewById(R.id.btn_GetInvoiceFromBatch);
        btn_EnterNotes = (Button) mNav.findViewById(R.id.btn_EnterNotes);
        btn_ReturnItems = (Button) mNav.findViewById(R.id.btn_ReturnItems);
        btn_Payments = (Button) mNav.findViewById(R.id.btn_Payments);*/
/*

        btn_ShowAccReport.setTypeface(Typeface.createFromAsset(this.getAssets(), "Hacen Tunisia Lt.ttf"));
        btn_ShowLastInvoice.setTypeface(Typeface.createFromAsset(this.getAssets(), "Hacen Tunisia Lt.ttf"));
        btn_ShowStoreQty.setTypeface(Typeface.createFromAsset(this.getAssets(), "Hacen Tunisia Lt.ttf"));
        btn_GetInvoiceFromBatch.setTypeface(Typeface.createFromAsset(this.getAssets(), "Hacen Tunisia Lt.ttf"));
        btn_EnterNotes.setTypeface(Typeface.createFromAsset(this.getAssets(), "Hacen Tunisia Lt.ttf"));
        btn_ReturnItems.setTypeface(Typeface.createFromAsset(this.getAssets(), "Hacen Tunisia Lt.ttf"));
        btn_Payments.setTypeface(Typeface.createFromAsset(this.getAssets(), "Hacen Tunisia Lt.ttf"));
        if(ManLevel.equals("1")) {
            GetOrders();
        }
        btn_ReturnItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComInfo.SCR_NO = 2;
           *//*     Intent i = new Intent(OrdersItems1.this, ReturnItems.class);
                startActivity(i);*//*
            }
        });


        btn_Payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComInfo.SCR_NO = 2;
                Intent i = new Intent(OrdersItems1.this, RecvVoucherActivity.class);
                startActivity(i);
            }
        });
        btn_EnterNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Scr", "po");
                bundle.putString("OrdeNo", OrderNo.getText().toString());
                FragmentManager Manager = getFragmentManager();
                PopOrderSelesDetails obj = new PopOrderSelesDetails();
                obj.setArguments(bundle);
                obj.show(Manager, null);

            }
        });
        btn_ShowAccReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComInfo.SCR_NO = 2;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("OrderCustNo", accno.getText().toString());
                editor.putString("OrderCustNm", CustNm.getText().toString());
                editor.commit();
                Intent i = new Intent(OrdersItems1.this, Acc_ReportActivity.class);
                startActivity(i);
                ((Activity) OrdersItems1.this).finish();

            }
        });
        btn_ShowLastInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Scr", "po");
                bundle.putString("ACC", accno.getText().toString());
                FragmentManager Manager = getFragmentManager();
                PopShowCustLastInvoice obj = new PopShowCustLastInvoice();
                obj.setArguments(bundle);
                obj.show(Manager, null);

            }
        });

        btn_ShowStoreQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            *//*    Bundle bundle = new Bundle();
                bundle.putString("Scr", "po");
                bundle.putString("OrderNo", OrderNo.getText().toString());
                FragmentManager Manager = getFragmentManager();
                PopShowStoreQtyReport obj = new PopShowStoreQtyReport();
                obj.setArguments(bundle);
                obj.show(Manager, null);*//*

            }
        });


        btn_GetInvoiceFromBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
            *//*    bundle.putString("Scr", "po");
                bundle.putString("ACC", accno.getText().toString());
                FragmentManager Manager = getFragmentManager();
                PopShowInvoiceFromBatch obj = new PopShowInvoiceFromBatch();
                obj.setArguments(bundle);
                obj.show(Manager, null);*//*

            }
        });*/

       // tv_LocationNm = (TextView) findViewById(R.id.tv_LocationNm);
        GetMaxPONo();
        chk_MobileOrder = (CheckBox) findViewById(R.id.chk_MobileOrder);
        Lyt_CancleOrder = (LinearLayout) findViewById(R.id.Lyt_CancleOrder);
        LytSerach = (LinearLayout) findViewById(R.id.LytSerach);
        Lyt_CancleOrder.setVisibility(View.GONE);
        LytSerach.setVisibility(View.VISIBLE);
        ApprovalOrdersCount = (MyTextView) findViewById(R.id.ApprovalOrdersCount);
        NotiLyt = (LinearLayout) findViewById(R.id.NotiLyt);
        Get_CatNo(accno.getText().toString());
        contactList = new ArrayList<ContactListItems1>();
        contactList.clear();
        showList(0);
        Tax_Include.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CalcTotal();
                showList(0);

            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.Home_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k;
                k = new Intent(OrdersItems1.this, GalaxyNewHomeActivity.class);
                startActivity(k);

            }
        });


        TextView tv_Back = (TextView) findViewById(R.id.tv_Back);
        imgBack = (ImageView) findViewById(R.id.imgBack);




        Fragment frag = new Header_Frag();
      FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1, frag).commit();

        NotiLyt.setVisibility(View.INVISIBLE);
        ApprovalOrdersCount.setText("0");


      /*  if (ManLevel.equalsIgnoreCase("1")) {
            NotifcationSitting();
        } else {
            NotiLyt.setVisibility(View.INVISIBLE);
        }*/
        NotifcationSitting();
        try {

            if (!ComInfo.OrderApprovalNo.equals("-1")) {
                Set_Order(ComInfo.OrderApprovalNo,ComInfo.CustNmApproval ,ComInfo.accnoApproval);
                Lyt_CancleOrder.setVisibility(View.VISIBLE);
            }
        }catch (Exception ex){
            // Toast.makeText(this,ex.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
    }
    public void getdata(){
        Set_Order(ComInfo.OrderApprovalNo,ComInfo.CustNmApproval ,ComInfo.accnoApproval);

    }
    private void GetOrders(){
        q = "Delete from Po_dtl  where   orderno in  (select orderno from Po_Hdr where ForApproval='1'    )";
        sqlHandler.executeQuery(q);
        q = "Delete from Po_Hdr where ForApproval='1'  ";

        sqlHandler.executeQuery(q);
        /*

         */
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
        tv.setText("طلبات البيع");
        progressDialog.setCustomTitle(tv);
        progressDialog.setProgressDrawable(greenProgressbar);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(OrdersItems1.this);
                ws.GetSalesOrderForApproval("-1",LoginAccount, ManLevel, "0", "");
                try {
                    Integer i;
                    String q;
                    if(We_Result.Msg.length()>2) {
                        JSONObject js = new JSONObject(We_Result.Msg);
                        JSONArray js_ID = js.getJSONArray("ID");
                        JSONArray js_OrderNo = js.getJSONArray("OrderNo");
                        JSONArray js_CustNo = js.getJSONArray("CustNo");
                        JSONArray js_Cust_Nm = js.getJSONArray("Cust_Nm");
                        JSONArray js_Tax_Total = js.getJSONArray("TaxTotal");
                        JSONArray js_PoDate = js.getJSONArray("PoDate");
                        JSONArray js_Posted = js.getJSONArray("Posted");
                        JSONArray js_Notes = js.getJSONArray("Notes");
                        JSONArray js_Total = js.getJSONArray("Total");
                        JSONArray js_TransDate = js.getJSONArray("TransDate");
                        JSONArray js_V_OrderNo = js.getJSONArray("V_OrderNo");
                        JSONArray js_SuperVisorNotes = js.getJSONArray("V_OrderNo");
                        JSONArray js_NetTotal = js.getJSONArray("NetTotal");
                        JSONArray js_UserID = js.getJSONArray("UserID");
                        JSONArray js_MobileOrder = js.getJSONArray("MobileOrder");


                        JSONArray js_ItemNo = js.getJSONArray("ItemNo");
                        JSONArray js_Price = js.getJSONArray("Price");
                        JSONArray js_Qty = js.getJSONArray("Qty");
                        JSONArray js_Tax = js.getJSONArray("Tax");
                        JSONArray js_Unit = js.getJSONArray("Unit");
                        JSONArray js_Bonus = js.getJSONArray("Bonus");
                        JSONArray js_TaxAmt = js.getJSONArray("TaxAmt");
                        JSONArray js_LineTotal = js.getJSONArray("LineTotal");
                        JSONArray js_include_Tax = js.getJSONArray("IncludeTax");
                        JSONArray js_ManNm = js.getJSONArray("ManNm");

                        for (i = 0; i < js_ID.length(); i++) {
                            q = "INSERT INTO Po_Hdr(ID,OrderNo,acc,Nm,date,userid,posted,Notes,date,Net_Total" +
                                    "  ,MobileOrder,Total,Tax_Total,V_OrderNo,SuperVisorNotes,ForApproval,include_Tax,OwnerSalesManNm,OwnerSalesManNo) values ('"
                                    + js_ID.get(i).toString()
                                    + "','" + js_OrderNo.get(i).toString()
                                    + "','" + js_CustNo.get(i).toString()
                                    + "','" + js_Cust_Nm.get(i).toString()
                                    + "','" + js_PoDate.get(i).toString()
                                    + "','" + js_UserID.get(i).toString()
                                    + "','" + js_Posted.get(i).toString()
                                    + "','" + js_Notes.get(i).toString()
                                    + "','" + js_TransDate.get(i).toString()
                                    + "','" + js_NetTotal.get(i).toString()
                                    + "','" + js_MobileOrder.get(i).toString()
                                    + "','" + js_Total.get(i).toString()
                                    + "','" + js_Tax_Total.get(i).toString()
                                    + "','" + js_V_OrderNo.get(i).toString()
                                    + "','" + js_SuperVisorNotes.get(i).toString()
                                    + "','1','" + js_include_Tax.get(i).toString()
                                    + "','" + js_ManNm.get(i).toString()
                                    + "','" + js_UserID.get(i).toString() + "'   )";

                            sqlHandler.executeQuery(q);


                            q = "INSERT INTO Po_dtl( orderno,itemno,price,qty,tax,unitNo,bounce_qty,tax_Amt,total,ForApproval " +
                                    "  ,dis_Amt,dis_per,OrgPrice,bounce_unitno,net_total,ProID,Pro_bounce,Pro_dis_Per,Pro_amt,pro_Total,ID) values ('"
                                    + js_OrderNo.get(i).toString()
                                    + "','" + js_ItemNo.get(i).toString()
                                    + "','" + js_Price.get(i).toString()
                                    + "','" + js_Qty.get(i).toString()
                                    + "','" + js_Tax.get(i).toString()
                                    + "','" + js_Unit.get(i).toString()
                                    + "','" + js_Bonus.get(i).toString()
                                    + "','" + js_TaxAmt.get(i).toString()
                                    + "','" + js_LineTotal.get(i).toString()
                                    + "','1','0','0','" +
                                    js_Price.get(i).toString() + "','0','0','0','0','0','0','0','0')";
                            sqlHandler.executeQuery(q);
                            progressDialog.setMax(js_ID.length());
                            progressDialog.incrementProgressBy(1);
                            if (progressDialog.getProgress() == progressDialog.getMax()) {
                                progressDialog.dismiss();
                            }
                        }
                        final int total = i;
                        _handler.post(new Runnable() {
                            public void run() {
                                sqlHandler.executeQuery("DELETE  FROM Po_Hdr WHERE   ForApproval = '1' and   no   NOT IN (SELECT MAX(no) FROM Po_Hdr GROUP BY OrderNo )");
                                NotifcationSitting();
                                progressDialog.dismiss();

                            }
                        });
                    }else {
                        progressDialog.dismiss();
                    }
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
    public void NotifcationSitting() {
        NotiLyt.setVisibility(View.VISIBLE);
        q = "Select count(distinct orderno) as orderno  from  Po_Hdr where ForApproval='1' ";
        Cursor c1 = sqlHandler.selectQuery(q);
        ApprovalOrdersCount.setText("0");
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                ApprovalOrdersCount.setText(c1.getString(c1.getColumnIndex("orderno")));
            }
            c1.close();
        }
        if (ApprovalOrdersCount.getText().toString().equalsIgnoreCase("0")) {
            NotiLyt.setVisibility(View.INVISIBLE);
        }

    }

    public void GetMaxPONo() {

        final Handler _handler = new Handler();


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String u = sharedPreferences.getString("UserID", "");
        u = u.trim();


        String query = "SELECT  Distinct  COALESCE(MAX(orderno), 0) +1 AS no FROM Po_Hdr where userid ='" + UserID + "'";
        Cursor c1 = sqlHandler.selectQuery(query);
        String max = "0";
        TextView Maxpo = (TextView) findViewById(R.id.et_OrdeNo);
    //    EditText Maxpo = (EditText) findViewById(R.id.et_OrdeNo);
        if (c1.getCount() > 0 && c1 != null) {
            c1.moveToFirst();
            max = c1.getString(c1.getColumnIndex("no")).replaceAll("[^\\d.]", "");
            c1.close();
        }

        String max1 = "0";
        max1 = DB.GetValue(OrdersItems1.this, "OrdersSitting", "SalesOrder", "1=1").replaceAll("[^\\d.]", "");

        if (max1 == "") {
            max1 = "0";
        }
        max1 = String.valueOf(Integer.parseInt(max1) + 1);
        if (SToD(max1) > SToD(max)) {
            max = max1;
        }

        if (max.length() == 1) {
            String uu = intToString(Integer.parseInt(u.toString().trim().replaceAll("[^\\d.]", "") + ""), 2);
            Maxpo.setText(uu + intToString(Integer.parseInt(max), 5));
            Maxpo.setText(intToString(Integer.valueOf(u), 2) + intToString(Integer.valueOf(max), 5));

        } else {
            Maxpo.setText(intToString(Integer.valueOf(max), 7));

        }



        Maxpo.setFocusable(false);
        Maxpo.setEnabled(false);
        Maxpo.setCursorVisible(false);

        // Maxpo.setText(max);

    }
    /*public void GetMaxPONo() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String u = sharedPreferences.getString("UserID", "");


        String query = "SELECT  Distinct  COALESCE(MAX(orderno), 0) +1 AS no FROM Po_Hdr where ForApproval = '0' and  userid ='" + LoginAccount + "'";
        Cursor c1 = sqlHandler.selectQuery(query);
        String max = "0";
        TextView Maxpo = (TextView) findViewById(R.id.et_OrdeNo);
        if (c1 != null && c1.getCount() > 0) {
            c1.moveToFirst();
            max = c1.getString(c1.getColumnIndex("no"));
            c1.close();
        }

        String max1 = "0";
        max1 = sharedPreferences.getString("m4", "");
        if (max1 == "") {
            max1 = "0";
        }
        max1 = String.valueOf(Integer.parseInt(max1) + 1);
        if (SToD(max1) > SToD(max)) {
            max = max1;
        }

        if (max.length() == 1) {
            Maxpo.setText(intToString(Integer.valueOf(u), 2) + intToString(Integer.valueOf(max), 5));

        } else {
            Maxpo.setText(intToString(Integer.valueOf(max), 7));

        }


        Maxpo.setFocusable(false);
        Maxpo.setEnabled(false);
        Maxpo.setCursorVisible(false);

        // Maxpo.setText(max);

    }*/
    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
    }
    private void UpDateMaxOrderNo() {

        //  String query = "SELECT  Distinct  COALESCE(MAX( orderno ), 0)   AS no FROM Po_Hdr";
        String query = "SELECT  Distinct  COALESCE(MAX( cast(orderno as integer)), 0)   AS no FROM Po_Hdr";
        Cursor c1 = sqlHandler.selectQuery(query);
        String max = "0";

        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            max = c1.getString(c1.getColumnIndex("no"));
            c1.close();//1
        }

        //  max = (intToString(Integer.valueOf(max), 7));

        query = "Update OrdersSitting SET SalesOrder ='" + max + "'";
        sqlHandler.executeQuery(query);

        /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("m4", max);
        editor.commit();*/
    }
    private void showList(Integer f) {


        lv_Items.setAdapter(null);
        ContactListAdapter1 contactListAdapter = new ContactListAdapter1(
                OrdersItems1.this, contactList);
        lv_Items.setAdapter(contactListAdapter);

    }
    public  int  GetItemCount(){
        int s ;
        s=  contactList.size()-1;
        if (s==-1){
            s=0;
        }
        return  s;
    }
    public void showPop() {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "po");
        bundle.putString("CatNo", CatNo);
        bundle.putString("accno", accno.getText().toString());
        bundle.putString("contactListsize", (contactList.size()-1)+"");
        android.app.FragmentManager Manager = getFragmentManager();
        Pop_Po_Select_Items1 obj = new Pop_Po_Select_Items1();
        obj.setArguments(bundle);
        obj.show(Manager, null);


    }
    public Double GetOrderQty(String ItemNo, String Batchno, String Expdate) {
        Double Qty = 0.0;


        for (int x = 1; x < contactList.size(); x++) {
            ContactListItems1 contactListItems = new ContactListItems1();
            contactListItems = contactList.get(x);
            if (contactListItems.getNo() != null) {
                if (contactListItems.getNo().equalsIgnoreCase(ItemNo) && contactListItems.getBatch().equalsIgnoreCase(Batchno) && contactListItems.getExpDate().equalsIgnoreCase(Expdate)) {
                    Qty = Qty + (SToD(contactListItems.getQty()) * SToD(contactListItems.getOperand()));
                }
            }
        }

        return Qty;
    }
    private void Get_CatNo(String ACC_NO) {
        SqlHandler sqlHandler = new SqlHandler(this);
        String q = "Select  distinct ifnull( CatNo,0) as catno from Customers where no = '" + ACC_NO + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.getCount() > 0) {
                c1.moveToFirst();
                CatNo = c1.getString(c1.getColumnIndex("catno"));
            }
            c1.close();
        }

    }
    public void btn_back(View view) {
      /*  ComInfo.OrderApprovalNo = "-1";
        ComInfo.CustNmApproval = "";
        ComInfo.accnoApproval = "";*/
        Intent k = new Intent(this, GalaxyNewHomeActivity.class);
        startActivity(k);
    }

    public void Save_Method(String ItemNo, String p, String q, String t, String u, String dis, String bounce) {

        TextView OrderNo = (TextView) findViewById(R.id.et_OrdeNo);
        String bounce_unitno, bounce_qty, dis_per, dis_value;
        bounce_unitno = bounce_qty = dis_per = dis_value = "";
        String query = "INSERT INTO Po_dtl(orderno,itemno,price,qty,tax,unitNo,dis_Amy,dis_per,bounce_qty,bounce_unitno) values " +
                "('" + OrderNo.getText().toString() + "','" + ItemNo + "'," + p + "," + q + "," + t + ",'" + u + "','" + dis_value + "','" + dis_value + "','" + bounce_qty + "','" + bounce_unitno + "')";

        sqlHandler.executeQuery(query);
        showList(1);
    }
    public void Save_List(String ItemNo, String p, String q, String t, String u, String dis, String bounce, String ItemNm, String UnitName, String dis_Amt) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String cus = DB.GetValue(this, "Customers", "Tax_Cus", "no ='" + sharedPreferences.getString("CustNo","") + "'");
        if(cus.equals("DZ_AR"))
        {
            if (bounce.toString().equals(""))
                bounce = "0";

            if (dis.toString().equals(""))
                dis = "0";

            if (dis_Amt.toString().equals(""))
                dis_Amt = "0";

            Double Item_Total, Price, Tax_Amt, Tax, Total, Net_Total, Tax_Total;

            for (int x = 1; x < contactList.size(); x++) {
                ContactListItems1 contactListItems = new ContactListItems1();
                contactListItems = contactList.get(x);

                if (contactListItems.getNo().equals(ItemNo)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(

                            this).create();

                    alertDialog.setTitle("Galaxy");
                    alertDialog.setMessage(getResources().getText(R.string.ItemExists));            // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.tick);
                    alertDialog.setButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    try {
                        alertDialog.show();
                    }catch (Exception ecx){}
                    return;
                }

            }
            AddEmptyRow();


            Item_Total = Double.parseDouble(q.replace(",", "")) * Double.parseDouble(p.toString().replace("'", ""));
            Price = Double.parseDouble(p.replace("'", ""));
            Tax = Double.parseDouble(t.replace(",", ""));
            Item_Total = Double.parseDouble(Item_Total.toString().replace("'", ""));
            //  Tax_Amt =Double.valueOf((Tax / 100))  * Item_Total;
            // Tax_Amt =(Double.parseDouble(Tax.toString()) /100)   *  ( Double.parseDouble(Item_Total.toString().replace(",","")) -  Double.parseDouble( dis_Amt.toString().replace(",","") ));
            double TaxFactor = 0.0;
            CheckBox Tax_Include = (CheckBox) findViewById(R.id.chk_Tax_Include);
            Double DisValue = Double.parseDouble(dis_Amt.toString().replace(",", ""));


            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat df = (DecimalFormat) nf;

            ContactListItems1 contactListItems = new ContactListItems1();
            contactListItems.setno(ItemNo);
            contactListItems.setName(ItemNm);
            if (Tax_Include.isChecked()) {
                String a= String.valueOf(df.format(Price / ((Tax / 100) + 1)));
                contactListItems.setprice(a);

            } else {
                contactListItems.setprice(String.valueOf(Price));

            }
            if (Tax_Include.isChecked()) {
                String a= String.valueOf(df.format(Price / ((Tax / 100) + 1)));
                contactListItems.setItemOrgPrice(a);

            } else {
                contactListItems.setItemOrgPrice(String.valueOf(Price));

            }

            // contactListItems.setprice(String.valueOf(Price));
            //contactListItems.setItemOrgPrice(String.valueOf(Price));
            contactListItems.setQty(q);
            contactListItems.setTax("0");
            contactListItems.setUnite(u);
            contactListItems.setBounce(bounce);
            contactListItems.setDiscount(dis);
            contactListItems.setDis_Amt(dis_Amt);
            contactListItems.setUniteNm(UnitName);
            contactListItems.setPro_amt("0");
            contactListItems.setPro_dis_Per("0");
            contactListItems.setPro_bounce("0");
            contactListItems.setPro_Total("0");
            contactListItems.setDisAmtFromHdr("0");
            contactListItems.setDisPerFromHdr("0");
            contactListItems.setProID("");
            contactListItems.setTax_Amt("0");
            contactListItems.setTotal(String.valueOf(df.format(Item_Total)));
            contactListItems.setBatch("");
            contactListItems.setExpDate("");
            contactListItems.setOperand("1");
            contactListItems.setSPrise("0");
            contactList.add(contactListItems);
            CalcTotal();

            showList(1);
        }
        else
        {
            if (bounce.toString().equals(""))
                bounce = "0";

            if (dis.toString().equals(""))
                dis = "0";

            if (dis_Amt.toString().equals(""))
                dis_Amt = "0";

            Double Item_Total, Price, Tax_Amt, Tax, Total, Net_Total, Tax_Total;

            for (int x = 1; x < contactList.size(); x++) {
                ContactListItems1 contactListItems = new ContactListItems1();
                contactListItems = contactList.get(x);

                if (contactListItems.getNo().equals(ItemNo)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(

                            this).create();

                    alertDialog.setTitle("Galaxy");
                    alertDialog.setMessage(getResources().getText(R.string.ItemExists));            // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.tick);
                    alertDialog.setButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    try {
                        alertDialog.show();
                    }catch (Exception ecx){}
                    return;
                }

            }
            AddEmptyRow();


            Item_Total = Double.parseDouble(q.replace(",", "")) * Double.parseDouble(p.toString().replace("'", ""));
            Price = Double.parseDouble(p.replace("'", ""));
            Tax = Double.parseDouble(t.replace(",", ""));
            Item_Total = Double.parseDouble(Item_Total.toString().replace("'", ""));
            //  Tax_Amt =Double.valueOf((Tax / 100))  * Item_Total;
            // Tax_Amt =(Double.parseDouble(Tax.toString()) /100)   *  ( Double.parseDouble(Item_Total.toString().replace(",","")) -  Double.parseDouble( dis_Amt.toString().replace(",","") ));
            double TaxFactor = 0.0;
            CheckBox Tax_Include = (CheckBox) findViewById(R.id.chk_Tax_Include);
            Double DisValue = Double.parseDouble(dis_Amt.toString().replace(",", ""));


            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat df = (DecimalFormat) nf;

            ContactListItems1 contactListItems = new ContactListItems1();
            contactListItems.setno(ItemNo);
            contactListItems.setName(ItemNm);
            if (Tax_Include.isChecked()) {
                contactListItems.setprice(String.valueOf(Price / ((Tax / 100) + 1)));
            } else {
                contactListItems.setprice(String.valueOf(Price));

            }

            // contactListItems.setprice(String.valueOf(Price));
            contactListItems.setItemOrgPrice(String.valueOf(Price));
            contactListItems.setQty(q);
            contactListItems.setTax(String.valueOf(Tax));
            contactListItems.setUnite(u);
            contactListItems.setBounce(bounce);
            contactListItems.setDiscount(dis);
            contactListItems.setDis_Amt(dis_Amt);
            contactListItems.setUniteNm(UnitName);
            contactListItems.setPro_amt("0");
            contactListItems.setPro_dis_Per("0");
            contactListItems.setPro_bounce("0");
            contactListItems.setPro_Total("0");
            contactListItems.setDisAmtFromHdr("0");
            contactListItems.setDisPerFromHdr("0");
            contactListItems.setProID("");
            contactListItems.setTax_Amt("0");
            contactListItems.setTotal(String.valueOf(df.format(Item_Total)));
            contactListItems.setBatch("");
            contactListItems.setExpDate("");
            contactListItems.setOperand("1");
            contactListItems.setSPrise("0");
            contactList.add(contactListItems);
            CalcTotal();

            showList(1);
        }
    }
    private void CalcTax() {

        Double All_Dis = 0.0;
        Double RowTotal = 0.0;
        Double NetRow = 0.0;
        Double TaxAmt = 0.0;
        Double TaxFactor = 0.0;
        Double All_Dis_Per = 0.0;
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;

        CheckBox Tax_Include = (CheckBox) findViewById(R.id.chk_Tax_Include);


        ContactListItems1 contactListItems;
        for (int x = 1; x < contactList.size(); x++) {
            contactListItems = new ContactListItems1();
            contactListItems = contactList.get(x);
            //  All_Dis = Double.parseDouble(contactListItems.getDis_Amt().replace(",", "")) + Double.parseDouble(contactListItems.getDisAmtFromHdr().replace(",", "")) + Double.parseDouble(contactListItems.getPro_amt().replace(",", ""));
            All_Dis_Per = SToD(contactListItems.getDiscount()) + SToD(contactListItems.getDisPerFromHdr()) + SToD(contactListItems.getPro_dis_Per());

            if (Tax_Include.isChecked()) {

                contactListItems.setprice(SToD(String.valueOf((SToD(contactListItems.getItemOrgPrice())) / ((SToD(contactListItems.getTax()) / 100) + 1))).toString());

            } else {
                contactListItems.setprice(String.valueOf(SToD(contactListItems.getItemOrgPrice())));


            }
            //  contactListItems.setDis_Amt( (SToD(contactListItems.getprice()) * SToD(contactListItems.getQty()))  * (100)   );
            NumberFormat nf_out;
            nf_out = NumberFormat.getNumberInstance(Locale.ENGLISH);
            nf_out.setMaximumFractionDigits(3);

            RowTotal = SToD(contactListItems.getprice()) * SToD(contactListItems.getQty());
            TaxFactor = (Double.parseDouble(contactListItems.getTax()) / 100);
            NetRow = RowTotal - (RowTotal * (All_Dis_Per / 100));

            TaxAmt = (NetRow * TaxFactor);
            contactListItems.setTax_Amt(nf_out.format(TaxAmt));
        }

    }
    private void CalcTotal() {

        Double Total, Tax_Total, Dis_Amt, Po_Total;
        ContactListItems1 contactListItems = new ContactListItems1();
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;
        Double All_Dis = 0.0;
        Double All_Dis_Per = 0.0;
        Total = 0.0;
        Tax_Total = 0.0;
        Dis_Amt = 0.0;
        Po_Total = 0.0;
        TextView Subtotal = (TextView) findViewById(R.id.et_Total);
        TextView TotalTax = (TextView) findViewById(R.id.et_TotalTax);
        TextView dis = (TextView) findViewById(R.id.et_dis);
        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        CheckBox Tax_Include = (CheckBox) findViewById(R.id.chk_Tax_Include);

        CalcTax();
        Double RowTotal = 0.0;
        Double pq = 0.0;
        Double opq = 0.0;
        Double V_NetTotal = 0.0;
        for (int x = 1; x < contactList.size(); x++) {
            contactListItems = new ContactListItems1();
            contactListItems = contactList.get(x);
            All_Dis = SToD(contactListItems.getDis_Amt()) + SToD(contactListItems.getDisAmtFromHdr()) + SToD(contactListItems.getPro_amt());
            All_Dis_Per = SToD(contactListItems.getDiscount()) + SToD(contactListItems.getDisPerFromHdr()) + SToD(contactListItems.getPro_dis_Per());
            pq = SToD(contactListItems.getprice()) * SToD(contactListItems.getQty());
            opq = SToD(contactListItems.getItemOrgPrice()) * SToD(contactListItems.getQty());

            Tax_Total = Tax_Total + (SToD(contactListItems.getTax_Amt().toString()));
            Dis_Amt = Dis_Amt + (((pq) * (All_Dis_Per / 100)));

            if (Tax_Include.isChecked()) {
                RowTotal = opq - ((opq) * (All_Dis_Per / 100));


            } else {
                RowTotal = pq - ((pq) * (All_Dis_Per / 100)) + SToD(contactListItems.getTax_Amt());
                Total = Total + pq;

            }

            V_NetTotal = V_NetTotal + SToD(RowTotal.toString().replace(",", ""));

            contactListItems.setTotal((SToD(RowTotal.toString().replace(",", ""))).toString());
            All_Dis = 0.0;

        }
        Total = V_NetTotal - Tax_Total + Dis_Amt;
        TotalTax.setText(String.valueOf(df.format(Tax_Total)).replace(",", ""));
        Subtotal.setText(String.valueOf(df.format(Total)).replace(",", ""));
        dis.setText(String.valueOf(df.format(Dis_Amt)).replace(",", ""));


        Po_Total = Po_Total + ((SToD(Subtotal.getText().toString()) - SToD(dis.getText().toString())) + SToD(TotalTax.getText().toString()));

        // showList(0);
        NetTotal.setText(String.valueOf(df.format(V_NetTotal)).replace(",", ""));


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

    public void Set_Cust(String No, String Nm) {
        TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        acc.setText(No);
        CustNm.setText(Nm);

        CustNm.setError(null);
        String cus = DB.GetValue(this, "Customers", "Tax_Cus", "no ='" + No + "'");
        if(cus.equals("DZ_AR"))
        {
            CustNm.setTextColor(getResources().getColor(R.color.Red));
        }
        else
        {

            CustNm.setTextColor(getResources().getColor(R.color.Black11));

        }
        SharedPreferences.Editor editor    = sharedPreferences.edit();
        editor.putString("CustNo",No);
        editor.putString("CustNm",Nm);
        editor.commit();

    }
    private void AddEmptyRow() {

        if (contactList.size() == 0) {
            ContactListItems1 contactListItems = new ContactListItems1();
            contactListItems.setno("");
            contactListItems.setName("");
            contactListItems.setprice("");
            contactListItems.setItemOrgPrice("");
            contactListItems.setQty("");
            contactListItems.setTax("");
            contactListItems.setUniteNm("");
            contactListItems.setBounce("");
            contactListItems.setDiscount("");
            contactListItems.setDis_Amt("");
            contactListItems.setDis_Amt("");
            contactListItems.setUnite("");
            contactListItems.setTax_Amt("");
            contactListItems.setTotal("");
            contactListItems.setProID("");
            contactListItems.setPro_bounce("0");
            contactListItems.setPro_dis_Per("0");
            contactListItems.setBatch("");
            contactListItems.setExpDate("");
            contactListItems.setOperand("1");
            contactListItems.setPro_amt("0");
            contactListItems.setPro_Total("0");
            contactListItems.setDisAmtFromHdr("0");
            contactListItems.setDisPerFromHdr("0");
            contactList.add(contactListItems);
        }
    }
    public void Set_Order_For_Aproval(String No, String Nm, String acc) { // FillList
        String cus = DB.GetValue(this, "Customers", "Tax_Cus", "no ='" + acc + "'");
        if(!cus.equals("DZ_AR"))  {
            TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
            TextView Order_no = (TextView) findViewById(R.id.et_OrdeNo);
            TextView accno = (TextView) findViewById(R.id.tv_acc);
            TextView Total = (TextView) findViewById(R.id.et_Total);
            TextView dis = (TextView) findViewById(R.id.et_dis);
            TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
            TextView TotalTax = (TextView) findViewById(R.id.et_TotalTax);
            CheckBox Tax_Include = (CheckBox) findViewById(R.id.chk_Tax_Include);

            ForApproval = "1";
            Order_no.setText(No);
            CustNm.setText(Nm);
            accno.setText(acc);

            contactList.clear();
            AddEmptyRow();
            showList(0);
            sqlHandler = new SqlHandler(this);
            Tax_Include.setChecked(false);
            String query = "  select   Distinct OwnerSalesManNo, Total as total ,ifnull(disc_Total,'0') as disc, Net_Total as net," +
                    "  Tax_Total as tax,ifnull(include_Tax,'0') as itax  ,ifnull(MobileOrder,'-1') as morder from Po_Hdr  where ForApproval = '1' and orderno ='" + Order_no.getText().toString() + "'";
            Cursor c1 = sqlHandler.selectQuery(query);
            if (c1 != null && c1.getCount() != 0) {
                if (c1.moveToFirst()) {
                    Total.setText(c1.getString(c1.getColumnIndex("total")).toString());
                    dis.setText(c1.getString(c1.getColumnIndex("disc")).toString());
                    NetTotal.setText(c1.getString(c1.getColumnIndex("net")).toString());
                    TotalTax.setText("0");
                    OwnerSalesManNo = c1.getString(c1.getColumnIndex("OwnerSalesManNo")).toString();

                    if (c1.getString(c1.getColumnIndex("itax")).equals("0")) {
                        Tax_Include.setChecked(false);
                    }
                    if (c1.getString(c1.getColumnIndex("morder")).equals("0")) {
                        chk_MobileOrder.setChecked(true);
                    }


                }

                c1.close();
            }


            FillDtl2(No);
        }
        else
        {
            TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
            TextView Order_no = (TextView) findViewById(R.id.et_OrdeNo);
            TextView accno = (TextView) findViewById(R.id.tv_acc);
            TextView Total = (TextView) findViewById(R.id.et_Total);
            TextView dis = (TextView) findViewById(R.id.et_dis);
            TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
            TextView TotalTax = (TextView) findViewById(R.id.et_TotalTax);
            CheckBox Tax_Include = (CheckBox) findViewById(R.id.chk_Tax_Include);

            ForApproval = "1";
            Order_no.setText(No);
            CustNm.setText(Nm);
            accno.setText(acc);

            contactList.clear();
            AddEmptyRow();
            showList(0);
            sqlHandler = new SqlHandler(this);
            Tax_Include.setChecked(false);
            String query = "  select   Distinct OwnerSalesManNo, Total as total ,ifnull(disc_Total,'0') as disc, Net_Total as net," +
                    "  Tax_Total as tax,ifnull(include_Tax,'0') as itax  ,ifnull(MobileOrder,'-1') as morder from Po_Hdr  where ForApproval = '1' and orderno ='" + Order_no.getText().toString() + "'";
            Cursor c1 = sqlHandler.selectQuery(query);
            if (c1 != null && c1.getCount() != 0) {
                if (c1.moveToFirst()) {
                    Total.setText(c1.getString(c1.getColumnIndex("total")).toString());
                    dis.setText(c1.getString(c1.getColumnIndex("disc")).toString());
                    NetTotal.setText(c1.getString(c1.getColumnIndex("net")).toString());
                    TotalTax.setText(c1.getString(c1.getColumnIndex("tax")).toString());
                    OwnerSalesManNo = c1.getString(c1.getColumnIndex("OwnerSalesManNo")).toString();

                    if (c1.getString(c1.getColumnIndex("itax")).equals("0")) {
                        Tax_Include.setChecked(false);
                    }
                    if (c1.getString(c1.getColumnIndex("morder")).equals("0")) {
                        chk_MobileOrder.setChecked(true);
                    }


                }

                c1.close();
            }


            FillDtl(No);

        }
    }

    private  void FillDtl(String No ){


        String q = "  select Distinct Unites.UnitName, pod.OrgPrice ,  ifnull(invf.Item_Name,'Name Not Found')as Item_Name, pod.itemno,pod.price,pod.qty,pod.tax ,pod.unitNo ,pod.dis_Amt,pod.dis_per,pod.bounce_qty ,  pod.tax_Amt   , pod.total  " +
                " , pod.pro_Total    , pod.ProID , pod.Pro_bounce  ,pod.Pro_dis_Per,  pod.Pro_amt ,pod.SPrice    from Po_dtl pod left join invf on invf.Item_No =  pod.itemno    left join Unites on Unites.Unitno=  pod.unitNo " +
                " Where   pod.ForApproval = '1' and  pod.orderno='" + No + "'and invf.myear = '"+sharedPreferences.getString("myear","")+"'";
        sqlHandler = new SqlHandler(getApplicationContext());

        cc= sqlHandler.selectQuery(q);


        ContactListItems1 contactListItems;


        if (cc != null && cc.getCount() != 0) {

            if (cc.moveToFirst()) {
                do {
                    contactListItems = new ContactListItems1();

                    contactListItems.setno(cc.getString(cc
                            .getColumnIndex("itemno")));
                    contactListItems.setName(cc.getString(cc
                            .getColumnIndex("Item_Name")));
                    contactListItems.setprice(cc.getString(cc
                            .getColumnIndex("price")));
                    contactListItems.setItemOrgPrice(cc.getString(cc
                            .getColumnIndex("OrgPrice")));
                    contactListItems.setQty(cc.getString(cc
                            .getColumnIndex("qty")));
                    contactListItems.setTax(cc.getString(cc
                            .getColumnIndex("tax")));
                    contactListItems.setUniteNm(cc.getString(cc
                            .getColumnIndex("UnitName")));
                    contactListItems.setBounce(cc.getString(cc
                            .getColumnIndex("bounce_qty")));
                    contactListItems.setDiscount(cc.getString(cc
                            .getColumnIndex("dis_per")));
                    contactListItems.setDis_Amt(cc.getString(cc
                            .getColumnIndex("dis_Amt")));

                    contactListItems.setDis_Amt(cc.getString(cc
                            .getColumnIndex("dis_Amt")));

                    contactListItems.setUnite(cc.getString(cc
                            .getColumnIndex("unitNo")));

                    contactListItems.setTax_Amt(cc.getString(cc
                            .getColumnIndex("tax_Amt")));

                    contactListItems.setTotal(cc.getString(cc
                            .getColumnIndex("total")));
                    contactListItems.setSPrise(cc.getString(cc
                            .getColumnIndex("SPrice")));
                    contactListItems.setProID("");

                    contactListItems.setPro_bounce("0");

                    contactListItems.setPro_dis_Per("0");
                    contactListItems.setBatch("");
                    contactListItems.setExpDate("");
                    contactListItems.setOperand("1");

                    contactListItems.setPro_amt("0");

                    contactListItems.setPro_Total("0");
                    contactListItems.setDisAmtFromHdr("0");

                    contactListItems.setDisPerFromHdr("0");

                    contactList.add(contactListItems);

                } while (cc.moveToNext());

            }

            cc.close();
        }

        CalcTotal();
        showList(0);
        IsNew = false;
    }
    private  void FillDtl2(String No ){


        String q = "  select Distinct Unites.UnitName, pod.OrgPrice ,  ifnull(invf.Item_Name,'Name Not Found')as Item_Name, pod.itemno,pod.price,pod.qty,pod.tax ,pod.unitNo ,pod.dis_Amt,pod.dis_per,pod.bounce_qty ,  pod.tax_Amt   , pod.total  " +
                " , pod.pro_Total    , pod.ProID , pod.Pro_bounce  ,pod.Pro_dis_Per,  pod.Pro_amt ,pod.SPrice    from Po_dtl pod left join invf on invf.Item_No =  pod.itemno    left join Unites on Unites.Unitno=  pod.unitNo " +
                " Where   pod.ForApproval = '1' and  pod.orderno='" + No + "'and invf.myear = '"+sharedPreferences.getString("myear","")+"'";
        sqlHandler = new SqlHandler(getApplicationContext());

        cc= sqlHandler.selectQuery(q);


        ContactListItems1 contactListItems;


        if (cc != null && cc.getCount() != 0) {

            if (cc.moveToFirst()) {
                do {
                    contactListItems = new ContactListItems1();

                    contactListItems.setno(cc.getString(cc
                            .getColumnIndex("itemno")));
                    contactListItems.setName(cc.getString(cc
                            .getColumnIndex("Item_Name")));
                    contactListItems.setprice(cc.getString(cc
                            .getColumnIndex("price")));
                    contactListItems.setItemOrgPrice(cc.getString(cc
                            .getColumnIndex("OrgPrice")));
                    contactListItems.setQty(cc.getString(cc
                            .getColumnIndex("qty")));
                    contactListItems.setTax("0");
                    contactListItems.setUniteNm(cc.getString(cc
                            .getColumnIndex("UnitName")));
                    contactListItems.setBounce(cc.getString(cc
                            .getColumnIndex("bounce_qty")));
                    contactListItems.setDiscount(cc.getString(cc
                            .getColumnIndex("dis_per")));
                    contactListItems.setDis_Amt(cc.getString(cc
                            .getColumnIndex("dis_Amt")));

                    contactListItems.setDis_Amt(cc.getString(cc
                            .getColumnIndex("dis_Amt")));

                    contactListItems.setUnite(cc.getString(cc
                            .getColumnIndex("unitNo")));

                    contactListItems.setTax_Amt("0");

                    contactListItems.setTotal(cc.getString(cc
                            .getColumnIndex("total")));
                    contactListItems.setSPrise(cc.getString(cc
                            .getColumnIndex("SPrice")));

                    contactListItems.setProID("");

                    contactListItems.setPro_bounce("0");

                    contactListItems.setPro_dis_Per("0");
                    contactListItems.setBatch("");
                    contactListItems.setExpDate("");
                    contactListItems.setOperand("1");

                    contactListItems.setPro_amt("0");

                    contactListItems.setPro_Total("0");
                    contactListItems.setDisAmtFromHdr("0");

                    contactListItems.setDisPerFromHdr("0");

                    contactList.add(contactListItems);

                } while (cc.moveToNext());

            }

            cc.close();
        }

        CalcTotal();
        showList(0);
        IsNew = false;
    }
    public void Set_Order(String No, String Nm, String acc) { // FillList

        //   Toast.makeText(this,No+"",Toast.LENGTH_LONG).show();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String cus = DB.GetValue(this, "Customers", "Tax_Cus", "no ='" + sharedPreferences.getString("CustNo","") + "'");
        if(cus.equals("DZ_AR")) {
            TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
            TextView Order_no = (TextView) findViewById(R.id.et_OrdeNo);
            TextView accno = (TextView) findViewById(R.id.tv_acc);
            TextView Total = (TextView) findViewById(R.id.et_Total);
            TextView dis = (TextView) findViewById(R.id.et_dis);
            TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
            TextView TotalTax = (TextView) findViewById(R.id.et_TotalTax);
            CheckBox Tax_Include = (CheckBox) findViewById(R.id.chk_Tax_Include);
            UserDoSave = 1;
            ForApproval = "0";
            Order_no.setText(No);
            CustNm.setText(Nm);
            accno.setText(acc);
            contactList.clear();
            AddEmptyRow();
            showList(0);
            sqlHandler = new SqlHandler(this);
            Tax_Include.setChecked(false);
            String query = "  select   Distinct Total as total ,ifnull(disc_Total,'0') as disc, Net_Total as net," +
                    "  ifnull(Tax_Total,0) as tax,ifnull(include_Tax,'0') as itax  ,ifnull(MobileOrder,'-1') as morder from Po_Hdr  where orderno ='" + Order_no.getText().toString() + "'";
            Cursor c1 = sqlHandler.selectQuery(query);
            if (c1 != null && c1.getCount() != 0) {
                if (c1.moveToFirst()) {
                    Total.setText(c1.getString(c1.getColumnIndex("total")).toString());
                    dis.setText(c1.getString(c1.getColumnIndex("disc")).toString());
                    NetTotal.setText(c1.getString(c1.getColumnIndex("net")).toString());
                    TotalTax.setText("0");

                    if (c1.getString(c1.getColumnIndex("itax")).equals("0")) {
                        Tax_Include.setChecked(false);
                    }
                    if (c1.getString(c1.getColumnIndex("morder")).equals("0")) {
                        chk_MobileOrder.setChecked(true);
                    }


                }

                c1.close();
            }
            query = "  select Distinct Unites.UnitName, pod.OrgPrice ,  ifnull(invf.Item_Name,'Name Not Found')as Item_Name, pod.itemno,pod.price,pod.qty,pod.tax ,pod.unitNo ,pod.dis_Amt,pod.dis_per,pod.bounce_qty ,  pod.tax_Amt   , pod.total  " +
                    " , pod.pro_Total    , pod.ProID , pod.Pro_bounce  ,pod.Pro_dis_Per,  pod.Pro_amt,pod.SPrice     from Po_dtl pod left join invf on invf.Item_No =  pod.itemno    left join Unites on Unites.Unitno=  pod.unitNo  Where pod.orderno='" + Order_no.getText().toString() + "'and invf.myear = '" + sharedPreferences.getString("myear", "") + "'";
            c1 = sqlHandler.selectQuery(query);

            if (c1 != null && c1.getCount() != 0) {
                //   Toast.makeText(this, c1.getCount()+"  c1 ",Toast.LENGTH_LONG).show();
                if (c1.moveToFirst()) {
                    do {
                        ContactListItems1 contactListItems = new ContactListItems1();

                        contactListItems.setno(c1.getString(c1
                                .getColumnIndex("itemno")));
                        contactListItems.setName(c1.getString(c1
                                .getColumnIndex("Item_Name")));
                        contactListItems.setprice(c1.getString(c1
                                .getColumnIndex("price")));
                        contactListItems.setItemOrgPrice(c1.getString(c1
                                .getColumnIndex("OrgPrice")));
                        contactListItems.setQty(c1.getString(c1
                                .getColumnIndex("qty")));
                        contactListItems.setTax("0");
                        contactListItems.setUniteNm(c1.getString(c1
                                .getColumnIndex("UnitName")));
                        contactListItems.setBounce(c1.getString(c1
                                .getColumnIndex("bounce_qty")));
                        contactListItems.setDiscount(c1.getString(c1
                                .getColumnIndex("dis_per")));
                        contactListItems.setDis_Amt(c1.getString(c1
                                .getColumnIndex("dis_Amt")));

                        contactListItems.setDis_Amt(c1.getString(c1
                                .getColumnIndex("dis_Amt")));

                        contactListItems.setUnite(c1.getString(c1
                                .getColumnIndex("unitNo")));

                        contactListItems.setTax_Amt("0");

                        contactListItems.setTotal(c1.getString(c1
                                .getColumnIndex("total")));
                        contactListItems.setSPrise(c1.getString(c1
                                .getColumnIndex("SPrice")));

                        contactListItems.setProID("");

                        contactListItems.setPro_bounce("0");

                        contactListItems.setPro_dis_Per("0");
                        contactListItems.setBatch("");
                        contactListItems.setExpDate("");
                        contactListItems.setOperand("1");

                        contactListItems.setPro_amt("0");

                        contactListItems.setPro_Total("0");
                        contactListItems.setDisAmtFromHdr("0");

                        contactListItems.setDisPerFromHdr("0");

                        contactList.add(contactListItems);

                    } while (c1.moveToNext());

                }

                c1.close();
            }

            CalcTotal();
            showList(0);
            IsNew = false;
        }
        else
        {
            TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
            TextView Order_no = (TextView) findViewById(R.id.et_OrdeNo);
            TextView accno = (TextView) findViewById(R.id.tv_acc);
            TextView Total = (TextView) findViewById(R.id.et_Total);
            TextView dis = (TextView) findViewById(R.id.et_dis);
            TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
            TextView TotalTax = (TextView) findViewById(R.id.et_TotalTax);
            CheckBox Tax_Include = (CheckBox) findViewById(R.id.chk_Tax_Include);
            UserDoSave = 1;
            ForApproval = "0";
            Order_no.setText(No);
            CustNm.setText(Nm);
            accno.setText(acc);
            contactList.clear();
            AddEmptyRow();
            showList(0);
            sqlHandler = new SqlHandler(this);
            Tax_Include.setChecked(false);
            String query = "  select   Distinct Total as total ,ifnull(disc_Total,'0') as disc, Net_Total as net," +
                    "  ifnull(Tax_Total,0) as tax,ifnull(include_Tax,'0') as itax  ,ifnull(MobileOrder,'-1') as morder from Po_Hdr  where orderno ='" + Order_no.getText().toString() + "'";
            Cursor c1 = sqlHandler.selectQuery(query);
            if (c1 != null && c1.getCount() != 0) {
                if (c1.moveToFirst()) {

                    Total.setText(c1.getString(c1.getColumnIndex("total")).toString());
                    dis.setText(c1.getString(c1.getColumnIndex("disc")).toString());
                    NetTotal.setText(c1.getString(c1.getColumnIndex("net")).toString());
                    TotalTax.setText(c1.getString(c1.getColumnIndex("tax")).toString());

                    if (c1.getString(c1.getColumnIndex("itax")).equals("0")) {
                        Tax_Include.setChecked(false);
                    }
                    if (c1.getString(c1.getColumnIndex("morder")).equals("0")) {
                        chk_MobileOrder.setChecked(true);
                    }

                }
                c1.close();
            }
            query = "  select Distinct Unites.UnitName, pod.OrgPrice ,  ifnull(invf.Item_Name,'Name Not Found')as Item_Name, pod.itemno,pod.price,pod.qty,pod.tax ,pod.unitNo ,pod.dis_Amt,pod.dis_per,pod.bounce_qty ,  pod.tax_Amt   , pod.total  " +
                    " , pod.pro_Total    , pod.ProID , pod.Pro_bounce  ,pod.Pro_dis_Per,  pod.Pro_amt ,pod.SPrice    from Po_dtl pod left join invf on invf.Item_No =  pod.itemno    left join Unites on Unites.Unitno=  pod.unitNo  Where pod.orderno='" + Order_no.getText().toString() + "'";
            c1 = sqlHandler.selectQuery(query);

            if (c1 != null && c1.getCount() != 0) {
                //   Toast.makeText(this, c1.getCount()+"  c1 ",Toast.LENGTH_LONG).show();
                if (c1.moveToFirst()) {
                    do {
                        ContactListItems1 contactListItems = new ContactListItems1();

                        contactListItems.setno(c1.getString(c1
                                .getColumnIndex("itemno")));
                        contactListItems.setName(c1.getString(c1
                                .getColumnIndex("Item_Name")));
                        contactListItems.setprice(c1.getString(c1
                                .getColumnIndex("price")));
                        contactListItems.setItemOrgPrice(c1.getString(c1
                                .getColumnIndex("OrgPrice")));
                        contactListItems.setQty(c1.getString(c1
                                .getColumnIndex("qty")));
                        contactListItems.setTax(c1.getString(c1
                                .getColumnIndex("tax")));
                        contactListItems.setUniteNm(c1.getString(c1
                                .getColumnIndex("UnitName")));
                        contactListItems.setBounce(c1.getString(c1
                                .getColumnIndex("bounce_qty")));
                        contactListItems.setDiscount(c1.getString(c1
                                .getColumnIndex("dis_per")));
                        contactListItems.setDis_Amt(c1.getString(c1
                                .getColumnIndex("dis_Amt")));

                        contactListItems.setDis_Amt(c1.getString(c1
                                .getColumnIndex("dis_Amt")));

                        contactListItems.setUnite(c1.getString(c1
                                .getColumnIndex("unitNo")));

                        contactListItems.setTax_Amt(c1.getString(c1
                                .getColumnIndex("tax_Amt")));

                        contactListItems.setTotal(c1.getString(c1
                                .getColumnIndex("total")));

                        contactListItems.setSPrise(c1.getString(c1
                                .getColumnIndex("SPrice")));

                        contactListItems.setProID("");

                        contactListItems.setPro_bounce("0");

                        contactListItems.setPro_dis_Per("0");
                        contactListItems.setBatch("");
                        contactListItems.setExpDate("");
                        contactListItems.setOperand("1");

                        contactListItems.setPro_amt("0");

                        contactListItems.setPro_Total("0");
                        contactListItems.setDisAmtFromHdr("0");

                        contactListItems.setDisPerFromHdr("0");

                        contactList.add(contactListItems);

                    } while (c1.moveToNext());

                }

                c1.close();
            }

            CalcTotal();
            showList(0);
            IsNew = false;
        }
    }

    public void btn_searchCustomer(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "po1");
        android.app.FragmentManager Manager = getFragmentManager();
        Select_Customer obj = new Select_Customer();

        obj.setArguments(bundle);
        obj.show(Manager, null);
    }
    public void btn_show_Pop(View view) {
        showPop();
    }
    public void btn_print(View view) {

        Intent k = new Intent(this, Convert_Layout_Img1.class);
        //  Intent k = new Intent(this,BluetoothConnectMenu.class);
        k.putExtra("Scr", "po");
        TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView OrdeNo = (TextView) findViewById(R.id.et_OrdeNo);
        TextView accno = (TextView) findViewById(R.id.tv_acc);
        k.putExtra("cusnm", CustNm.getText().toString());
        k.putExtra("OrderNo", OrdeNo.getText().toString());
        k.putExtra("accno", accno.getText().toString());

        startActivity(k);
    }
    public void Save_Recod_Po() {
        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        TextView acc = (TextView) findViewById(R.id.tv_acc);

        TextView Total = (TextView) findViewById(R.id.et_Total);
        TextView dis = (TextView) findViewById(R.id.et_dis);
        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        TextView TotalTax = (TextView) findViewById(R.id.et_TotalTax);

        CheckBox Tax_Include = (CheckBox) findViewById(R.id.chk_Tax_Include);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Long i;


        UserID = sharedPreferences.getString("UserID", "");

        ContentValues cv = new ContentValues();
        cv.put("orderno", pono.getText().toString());
        cv.put("acc", acc.getText().toString());
        cv.put("date", currentDateandTime);
        cv.put("userid", UserID);
        cv.put("Total", Total.getText().toString().replace(",", ""));
        cv.put("Net_Total", NetTotal.getText().toString().replace(",", ""));
        cv.put("Tax_Total", TotalTax.getText().toString().replace(",", ""));
        cv.put("bounce_Total", "0");
        cv.put("posted", "-1");
        cv.put("V_OrderNo", sharedPreferences.getString("V_OrderNo", "0"));
        cv.put("OrderLevel", ManLevel);
        cv.put("ForApproval", ForApproval);
        cv.put("SuperVisorNotes", "");
        if (OwnerSalesManNo.equalsIgnoreCase("")) {
            cv.put("OwnerSalesManNo", UserID);
        } else {
            cv.put("OwnerSalesManNo", OwnerSalesManNo);
        }

        if (chk_MobileOrder.isChecked()) {
            cv.put("MobileOrder", "0");
        } else {
            cv.put("MobileOrder", "-1");
        }


        if (Tax_Include.isChecked()) {
            cv.put("include_Tax", "0");
        } else {
            cv.put("include_Tax", "-1");
        }

        cv.put("disc_Total", dis.getText().toString());


        if (IsNew == true) {
            i = sqlHandler.Insert("Po_Hdr", null, cv);
        } else {
            i = sqlHandler.Update("Po_Hdr", cv, "orderno ='" + pono.getText().toString() + "'");
        }

        if (i > 0) {
            String q = "Delete from  Po_dtl where orderno ='" + pono.getText().toString() + "'";
            sqlHandler.executeQuery(q);


            for (int x = 1; x < contactList.size(); x++) {
                ContactListItems1 contactListItems = new ContactListItems1();
                contactListItems = contactList.get(x);


                cv = new ContentValues();
                cv.put("orderno", pono.getText().toString());
                cv.put("itemno", contactListItems.getNo());
                cv.put("price", contactListItems.getPrice().toString().replace(",", ""));
                cv.put("qty", contactListItems.getQty().toString().replace(",", ""));
                cv.put("tax", contactListItems.getTax().toString().replace(",", ""));
                cv.put("unitNo", contactListItems.getUnite().toString().replace(",", ""));
                cv.put("dis_per", contactListItems.getDiscount().toString().replace(",", ""));
                cv.put("dis_Amt", contactListItems.getDis_Amt().toString().replace(",", ""));
                cv.put("bounce_qty", contactListItems.getBounce().toString().replace(",", ""));
                cv.put("tax_Amt", contactListItems.getTax_Amt().toString().replace(",", ""));
                cv.put("total", contactListItems.getTotal().toString().replace(",", ""));
                cv.put("ProID", contactListItems.getProID().toString().replace(",", ""));
                cv.put("Pro_bounce", contactListItems.getPro_bounce().toString().replace(",", ""));
                cv.put("Pro_dis_Per", contactListItems.getPro_dis_Per().toString().replace(",", ""));
                cv.put("Pro_amt", contactListItems.getPro_amt().toString().replace(",", ""));
                cv.put("pro_Total", contactListItems.getPro_Total().toString().replace(",", ""));
                cv.put("OrgPrice", contactListItems.getItemOrgPrice().toString().replace(",", ""));
                cv.put("SPrice", contactListItems.getSPrise().toString().replace(",", ""));
                cv.put("ForApproval", ForApproval);
                i = sqlHandler.Insert("Po_dtl", null, cv);
            }
        }

        if (i > 0) {



            // GetMaxPONo();
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle(getResources().getText(R.string.Ordersales));
            alertDialog.setMessage(getResources().getText(R.string.AddCompleteSucc));
            IsNew = false;
            UserDoSave = 1;

            if (ForApproval == "0") {
                UpDateMaxOrderNo();
            }
            alertDialog.setIcon(R.drawable.tick);

            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    View view = null;

                }
            });
            // Do_Share();
            ComInfo.OrderApprovalNo = "-1";
            ComInfo.CustNmApproval = "";
            ComInfo.accnoApproval = "";
            try {
                Toast.makeText(this,getResources().getText(R.string.AddCompleteSucc),Toast.LENGTH_LONG).show();
                //   alertDialog.show();

            }catch (Exception ecx){}


        }
    }
    public void Shere_Recod_Po() {
        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        TextView acc = (TextView) findViewById(R.id.tv_acc);

        TextView Total = (TextView) findViewById(R.id.et_Total);
        TextView dis = (TextView) findViewById(R.id.et_dis);
        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        TextView TotalTax = (TextView) findViewById(R.id.et_TotalTax);

        CheckBox Tax_Include = (CheckBox) findViewById(R.id.chk_Tax_Include);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Long i;


        ContentValues cv = new ContentValues();
        cv.put("orderno", pono.getText().toString());
        cv.put("acc", acc.getText().toString());
        cv.put("date", currentDateandTime);
        cv.put("userid", LoginAccount);
        cv.put("Total", Total.getText().toString().replace(",", ""));
        cv.put("Net_Total", NetTotal.getText().toString().replace(",", ""));
        cv.put("Tax_Total", TotalTax.getText().toString().replace(",", ""));
        cv.put("bounce_Total", "0");
        cv.put("posted", "-1");
        cv.put("V_OrderNo", sharedPreferences.getString("V_OrderNo", "0"));
        cv.put("OrderLevel", ManLevel);
        cv.put("ForApproval", ForApproval);
        cv.put("SuperVisorNotes", "");
        if (OwnerSalesManNo.equalsIgnoreCase("")) {
            cv.put("OwnerSalesManNo", LoginAccount);
        } else {
            cv.put("OwnerSalesManNo", OwnerSalesManNo);
        }

        if (chk_MobileOrder.isChecked()) {
            cv.put("MobileOrder", "0");
        } else {
            cv.put("MobileOrder", "-1");
        }


        if (Tax_Include.isChecked()) {
            cv.put("include_Tax", "0");
        } else {
            cv.put("include_Tax", "-1");
        }

        cv.put("disc_Total", dis.getText().toString());


        if (IsNew == true) {
            i = sqlHandler.Insert("Po_Hdr", null, cv);
        } else {
            i = sqlHandler.Update("Po_Hdr", cv, "orderno ='" + pono.getText().toString() + "'");
        }

        if (i > 0) {
            String q = "Delete from  Po_dtl where orderno ='" + pono.getText().toString() + "'";
            sqlHandler.executeQuery(q);


            for (int x = 1; x < contactList.size(); x++) {
                ContactListItems1 contactListItems = new ContactListItems1();
                contactListItems = contactList.get(x);


                cv = new ContentValues();
                cv.put("orderno", pono.getText().toString());
                cv.put("itemno", contactListItems.getNo());
                cv.put("price", contactListItems.getPrice().toString().replace(",", ""));
                cv.put("qty", contactListItems.getQty().toString().replace(",", ""));
                cv.put("tax", contactListItems.getTax().toString().replace(",", ""));
                cv.put("unitNo", contactListItems.getUnite().toString().replace(",", ""));
                cv.put("dis_per", contactListItems.getDiscount().toString().replace(",", ""));
                cv.put("dis_Amt", contactListItems.getDis_Amt().toString().replace(",", ""));
                cv.put("bounce_qty", contactListItems.getBounce().toString().replace(",", ""));
                cv.put("tax_Amt", contactListItems.getTax_Amt().toString().replace(",", ""));
                cv.put("total", contactListItems.getTotal().toString().replace(",", ""));
                cv.put("ProID", contactListItems.getProID().toString().replace(",", ""));
                cv.put("Pro_bounce", contactListItems.getPro_bounce().toString().replace(",", ""));
                cv.put("Pro_dis_Per", contactListItems.getPro_dis_Per().toString().replace(",", ""));
                cv.put("Pro_amt", contactListItems.getPro_amt().toString().replace(",", ""));
                cv.put("pro_Total", contactListItems.getPro_Total().toString().replace(",", ""));
                cv.put("OrgPrice", contactListItems.getItemOrgPrice().toString().replace(",", ""));
                cv.put("SPrice", contactListItems.getSPrise().toString().replace(",", ""));
                cv.put("ForApproval", ForApproval);
                i = sqlHandler.Insert("Po_dtl", null, cv);
            }
        }

        if (i > 0) {



            // GetMaxPONo();
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle(getResources().getText(R.string.Ordersales));
            alertDialog.setMessage(getResources().getText(R.string.AddCompleteSucc));
            IsNew = false;
            UserDoSave = 1;


                UpDateMaxOrderNo();

            alertDialog.setIcon(R.drawable.tick);

            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    View view = null;

                }
            });
            Do_Share();
            ComInfo.OrderApprovalNo = "-1";
            ComInfo.CustNmApproval = "";
            ComInfo.accnoApproval = "";
            try {
                Toast.makeText(this,getResources().getText(R.string.AddCompleteSucc),Toast.LENGTH_LONG).show();
                //   alertDialog.show();

            }catch (Exception ecx){}
            // DoNew();

        }
    }
    public void btn_save_po(View view) {
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        String q = "SELECT Distinct *  from  Po_Hdr where   posted >0 AND   orderno ='" + pono.getText().toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle(getResources().getText(R.string.Ordersales));
            alertDialog.setMessage(getResources().getText(R.string.UpdateNotAllowed));            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            try {
                alertDialog.show();
            }catch (Exception ecx){}
            c1.close();
            return;
        } else {



            if (contactList.size() < 2) {

                AlertDialog alertDialog = new AlertDialog.Builder(
                        this).create();
                alertDialog.setTitle(getResources().getText(R.string.Ordersales));
                alertDialog.setMessage(getResources().getText(R.string.SaveNotAllowedWithoutItem));            // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.tick);
                alertDialog.setButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                try {
                    alertDialog.show();
                }catch (Exception ecx){}

                return;
            }

            TextView custNm = (TextView) findViewById(R.id.tv_cusnm);

            TextView acc = (TextView) findViewById(R.id.tv_acc);
            if (custNm.getText().toString().length() == 0) {
                custNm.setError("required!");
                custNm.requestFocus();
                return;
            }

            if (pono.getText().toString().length() == 0) {
                pono.setError("required!");
                pono.requestFocus();
                return;
            }


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(getResources().getText(R.string.Ordersales));
            alertDialog.setMessage(getResources().getText(R.string.DoYouWantToContinSave));
            alertDialog.setIcon(R.drawable.save);
            alertDialog.setPositiveButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Save_Recod_Po();
                }
            });


            alertDialog.setNegativeButton(getResources().getText(R.string.No), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });


            try {
                // alertDialog.show();
            }catch (Exception ecx){}

            Save_Recod_Po();
        }}
    public void btn_Shere2(View view) {
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        String q = "SELECT Distinct *  from  Po_Hdr where   posted >0 AND   orderno ='" + pono.getText().toString() + "'";
        /*Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle(getResources().getText(R.string.Ordersales));
            alertDialog.setMessage(getResources().getText(R.string.UpdateNotAllowed));            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            try {
                alertDialog.show();
            }catch (Exception ecx){}
            c1.close();
            return;
        }*/


        if (contactList.size() < 2) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle(getResources().getText(R.string.Ordersales));
            alertDialog.setMessage(getResources().getText(R.string.SaveNotAllowedWithoutItem));            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            try {
                alertDialog.show();
            }catch (Exception ecx){}

            return;
        }

        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);

        TextView acc = (TextView) findViewById(R.id.tv_acc);
        if (custNm.getText().toString().length() == 0) {
            custNm.setError("required!");
            custNm.requestFocus();
            return;
        }

        if (pono.getText().toString().length() == 0) {
            pono.setError("required!");
            pono.requestFocus();
            return;
        }


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getText(R.string.Ordersales));
        alertDialog.setMessage(getResources().getText(R.string.DoYouWantToContinSave));
        alertDialog.setIcon(R.drawable.save);
        alertDialog.setPositiveButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Shere_Recod_Po();
            }
        });


        alertDialog.setNegativeButton(getResources().getText(R.string.No), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });


        try {
            // alertDialog.show();
        }catch (Exception ecx){}

        Shere_Recod_Po();
    }
    public void btn_delete(View view) {
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        String q = "SELECT Distinct *  from  Po_Hdr where   posted >0 AND   orderno ='" + pono.getText().toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle(getResources().getText(R.string.Ordersales));
            alertDialog.setMessage(getResources().getText(R.string.UpdateNotAllowed));            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            try {
                alertDialog.show();
            }catch (Exception ecx){}
            c1.close();
            return;
        } else {


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(getResources().getText(R.string.Ordersales));
            alertDialog.setMessage(getResources().getText(R.string.DoYouWantContinDelete));
            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setPositiveButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Delete_Record_PO();

                }
            });


            alertDialog.setNegativeButton(getResources().getText(R.string.No), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });


            try {
                alertDialog.show();
            }catch (Exception ecx){}
        }
    }
    public void Delete_Record_PO() {
        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        TextView acc = (TextView) findViewById(R.id.tv_acc);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String currentDateandTime = sdf.format(new Date());


        String query = "Delete from  Po_Hdr where orderno ='" + pono.getText().toString() + "'";
        sqlHandler.executeQuery(query);

        query = "Delete from  Po_dtl where orderno ='" + pono.getText().toString() + "'";
        sqlHandler.executeQuery(query);
        contactList.clear();
        GetMaxPONo();
        showList(0);
        IsNew = true;
        //custNm.setText("");
        //acc.setText("");

        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setTitle(getResources().getText(R.string.Ordersales));
        alertDialog.setMessage(getResources().getText(R.string.DeleteCompleteSuccsully));
        alertDialog.setIcon(R.drawable.tick);
        alertDialog.setButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        try {
            alertDialog.show();
        }catch (Exception ecx){}
    }
    public void btn_Search_Orders(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "po");
        android.app.FragmentManager Manager = getFragmentManager();
        SearchPoActivity1 obj = new SearchPoActivity1();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }
    public void btn_share(View view) {
        // Do_Share();
       /* Bundle bundle = new Bundle();
        bundle.putString("Scr", "po");
        bundle.putString("OrderNo", OrderNo.getText().toString());
        FragmentManager Manager = getFragmentManager();
        PopShowStoreQtyReport obj = new PopShowStoreQtyReport();
        obj.setArguments(bundle);
        obj.show(Manager, null);*/
        Intent i =new Intent(OrdersItems1.this, Acc_ReportActivity1.class);
        startActivity(i);
    }
    private  void Do_Share (){
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
      /*  String q = "SELECT Distinct *  from  Po_Hdr where ifnull(ForApproval,'0')='0' and   posted >0 AND   orderno ='" + pono.getText().toString() + "'";
        Cursor cc = sqlHandler.selectQuery(q);
        if (cc != null && cc.getCount() != 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle(getResources().getText(R.string.Ordersales));
            alertDialog.setMessage("لا يمكن التعديل ، تم اعتماد طلب بيع ");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            try {
                alertDialog.show();
            }catch (Exception ecx){}
            cc.close();
            return;
        }*/


        if (UserDoSave == 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    OrdersItems1.this).create();
            alertDialog.setTitle(getResources().getText(R.string.Ordersales));
            alertDialog.setMessage("لا يمكن الاعتماد قبل التخزين");
            alertDialog.setIcon(R.drawable.error_new);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            try {
                alertDialog.show();
            }catch (Exception ecx){}
            return;
        }
        final SqlHandler sql_Handler = new SqlHandler(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final TextView custNm = (TextView) findViewById(R.id.tv_cusnm);

        final TextView acc = (TextView) findViewById(R.id.tv_acc);
        final TextView Total = (TextView) findViewById(R.id.et_Total);
        final TextView dis = (TextView) findViewById(R.id.et_dis);
        final TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        final TextView TotalTax = (TextView) findViewById(R.id.et_TotalTax);
        final CheckBox Tax_Include = (CheckBox) findViewById(R.id.chk_Tax_Include);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        final String str;



        String query = "SELECT Distinct  ifnull(OwnerSalesManNm,'') as  OwnerSalesManNm ,  ifnull(OwnerSalesManNo,'') as OwnerSalesManNo,   acc,  date ,Delv_day_count FROM Po_Hdr where orderno  ='" + pono.getText().toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(query);
        String ForApproval, Date, Cust_No, Delv_day_count, OwnerSalesManNm, OwnerSalesManNo;
        Date = OwnerSalesManNm = Cust_No = OwnerSalesManNo = Delv_day_count = "";

        if (c1.getCount() > 0 && c1 != null) {
            c1.moveToFirst();
            Cust_No = c1.getString(c1.getColumnIndex("acc"));
            Delv_day_count = c1.getString(c1.getColumnIndex("Delv_day_count"));
            Date = c1.getString(c1.getColumnIndex("date"));
            OwnerSalesManNm = c1.getString(c1.getColumnIndex("OwnerSalesManNm"));
            OwnerSalesManNo = c1.getString(c1.getColumnIndex("OwnerSalesManNo"));
            c1.close();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    OrdersItems1.this).create();
            alertDialog.setTitle(getResources().getText(R.string.Ordersales));
            alertDialog.setMessage("لا يمكن الاعتماد قبل التخزين");
            alertDialog.setIcon(R.drawable.error_new);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            try {
                alertDialog.show();
            }catch (Exception ecx){}
            return;
        }
        UserID = sharedPreferences.getString("UserID", "");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Cust_No", Cust_No.toString());
            jsonObject.put("day_Count", Delv_day_count == null ? "null" : Delv_day_count.toString());
            jsonObject.put("Date", Date.toString());
            jsonObject.put("UserID", UserID);
            jsonObject.put("OrderNo", pono.getText().toString());

            jsonObject.put("Total", Total.getText().toString().replace(",", ""));
            jsonObject.put("Net_Total", NetTotal.getText().toString().replace(",", ""));
            jsonObject.put("Tax_Total", TotalTax.getText().toString().replace(",", ""));
            jsonObject.put("bounce_Total", "0");
            jsonObject.put("OwnerSalesManNm", CustNm.getText().toString());
            jsonObject.put("OwnerSalesManNo", OwnerSalesManNo);

            jsonObject.put("disc_Total", dis.getText().toString().replace(",", ""));


            if (Tax_Include.isChecked()) {
                jsonObject.put("include_Tax", "0");
            } else {
                jsonObject.put("include_Tax", "-1");
            }


            if (chk_MobileOrder.isChecked()) {
                jsonObject.put("MobileOrder", "0");
            } else {
                jsonObject.put("MobileOrder", "-1");
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        String json = new Gson().toJson(contactList);
        str = jsonObject.toString() + json;


        loadingdialog = ProgressDialog.show(OrdersItems1.this, getResources().getText(R.string.PleaseWait), getResources().getText(R.string.PostUnderProccess), true);
        loadingdialog.setCancelable(true);
        loadingdialog.setCanceledOnTouchOutside(true);
        loadingdialog.show();
        final Handler _handler = new Handler();


        // Toast.makeText(getApplicationContext(),str, Toast.LENGTH_LONG).show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                CallWebServices ws = new CallWebServices(OrdersItems1.this);
                ws.Save_po(str, "Insert_PurshOrder");
                try {

                    if (We_Result.ID > 0) {


                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        OrdersItems1.this).create();
                                alertDialog.setTitle(getResources().getText(R.string.Ordersales));
                                ContentValues cv = new ContentValues();
                                TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
                                cv.put("posted", We_Result.ID);
                                UpDateMaxOrderNo();

                                long i;
                                i = sql_Handler.Update("Po_Hdr", cv, "orderno='" + DocNo.getText().toString() + "'");


                                sqlHandler.executeQuery("Delete from Po_Hdr where orderno='" + DocNo.getText().toString() + "'");
                                sqlHandler.executeQuery("Delete from Po_dtl where orderno='" + DocNo.getText().toString() + "'");
                                alertDialog.setMessage("تمت عملية الاعتماد  بنجاح");
                                //alertDialog.setMessage(getResources().getText(R.string.PostCompleteSuccfully) + "" + We_Result.ID + "");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (ComInfo.ComNo== Companies.Afrah.getValue()) {
                                            String closeRaound = DB.GetValue(OrdersItems1.this, "SaleManRounds", "ifnull(count(*),0)", "Closed =0");
                                           if(closeRaound.equals("1")) {
                                               closeRound();
                                           }
                                        }
                                        DoNew();
                                    }
                                });
                                loadingdialog.dismiss();
                                try {
                                    alertDialog.show();
                                }catch (Exception ecx){}


                                UpdateOrderCount();
                            }
                        });
                    } else {

                        loadingdialog.dismiss();
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        OrdersItems1.this).create();
                                if (We_Result.ID == -5) {
                                    alertDialog.setTitle("اعتماد طلب البيع");
                                    alertDialog.setMessage("تم اعتماد الطلب من قبل المشرف");
                                    alertDialog.setIcon(R.drawable.error_new);
                                    alertDialog.setButton("رجوع", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    try {
                                        alertDialog.show();
                                    }catch (Exception ecx){}


                                } else {

                                    alertDialog.setTitle("طلب البيع")   ;
                                    if (We_Result.Msg.toString().length()>1) {
                                        alertDialog.setMessage(We_Result.Msg.toString());
                                    }else{
                                        alertDialog.setMessage(getResources().getText(R.string.PostNotCompleteSuccfully));
                                    }
                                    alertDialog.setIcon(R.drawable.tick);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    try {
                                        alertDialog.show();
                                    }catch (Exception ecx){}

                                    alertDialog.setIcon(R.drawable.error_new);

                                }
                            }
                        });
                    }

                } catch (final Exception e) {
                    loadingdialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    OrdersItems1.this).create();
                            alertDialog.setTitle(getResources().getText(R.string.ConnectError));
                            alertDialog.setMessage(e.getMessage().toString());
                            alertDialog.setIcon(R.drawable.tick);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

                            try {
                                alertDialog.show();
                            }catch (Exception ecx){}
                        }
                    });
                }
            }
        }).start();
    }
    private void UpdateOrderCount() {
        NotiLyt.setVisibility(View.INVISIBLE);
        ApprovalOrdersCount.setText("0");
        if (ManLevel.equalsIgnoreCase("1")) {
            GetOrders();

        }

    }
    public void btn_new(View view) {
        DoNew();
    }
    private  void DoNew (){
        Lyt_CancleOrder.setVisibility(View.GONE);
        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        TextView Total = (TextView) findViewById(R.id.et_Total);
        TextView dis = (TextView) findViewById(R.id.et_dis);
        TextView TotalTax = (TextView) findViewById(R.id.et_TotalTax);
        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);

        OwnerSalesManNo = "";
        ForApproval = "0";
        UserDoSave = 0;
        Total.setText("0.0");
        dis.setText("0.0");
        TotalTax.setText("0.0");
        NetTotal.setText("0.0");

        IsNew = true;
       // custNm.setText("");
        pono.setText("");
      //  acc.setText("");
        GetMaxPONo();
        contactList.clear();
        showList(0);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getString("CustNo", "").equals(""))
        {
            acc.setText(sharedPreferences.getString("CustNo", ""));
            custNm.setText(sharedPreferences.getString("CustNm", ""));

        }
        else
        {
            acc.setText("");
            custNm.setText("");

        }
      /*  ComInfo.OrderApprovalNo = "-1";
        ComInfo.CustNmApproval = "";
        ComInfo.accnoApproval = "";*/
       /* SharedPreferences.Editor editor    = sharedPreferences.edit();
        editor.putString("CustNo","");
        editor.putString("CustNm","");*/
      //  editor.commit();
    }
    int position;
    public void btn_Delete_Item(final View view) {


        position = lv_Items.getPositionForView(view);
        registerForContextMenu(view);
        openContextMenu(view);

    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Cls_Sal_InvItems contactListItems =new Cls_Sal_InvItems();

        menu.setHeaderTitle(contactList.get(position).getName());
        menu.add(Menu.NONE, 1, Menu.NONE, "حذف المادة");
        menu.add(Menu.NONE, 2, Menu.NONE, "تعديل الكمية");
        menu.add(Menu.NONE, 3, Menu.NONE, "تعديل البونص");


    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        // TODO Auto-generated method stub


        switch (item.getItemId()) {
            case 3: {
                ArrayList<ContactListItems1> ContactListItems = new ArrayList<ContactListItems1>();
                ContactListItems.add(contactList.get(position));
                TextView accno = (TextView) findViewById(R.id.tv_acc);
                Bundle bundle = new Bundle();
                bundle.putString("Scr", "SalesOrder");
                bundle.putString("Cln", "Bounce");
                bundle.putString("Qty", contactList.get(position).getBounce().toString());
                bundle.putString("Nm", contactList.get(position).getName().toString());
                bundle.putString("OrderNo", pono.getText().toString());

                android.app.FragmentManager Manager = getFragmentManager();
                Pop_Update_Qty obj = new Pop_Update_Qty();
                obj.setArguments(bundle);
                obj.show(Manager, null);

            }
            break;
            case 2: {
                ArrayList<ContactListItems1> ContactListItems = new ArrayList<ContactListItems1>();
                ContactListItems.add(contactList.get(position));
                TextView accno = (TextView) findViewById(R.id.tv_acc);
                Bundle bundle = new Bundle();
                bundle.putString("Scr", "SalesOrder");
                bundle.putString("Cln", "Qty");
                bundle.putString("Qty", contactList.get(position).getQty().toString());
                bundle.putString("Nm", contactList.get(position).getName().toString());
                bundle.putString("OrderNo", pono.getText().toString());

                android.app.FragmentManager Manager = getFragmentManager();
                Pop_Update_Qty obj = new Pop_Update_Qty();
                obj.setArguments(bundle);
                obj.show(Manager, null);

            }
            break;
            case 1: {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("طلب البيع");
                alertDialog.setMessage("هل انت متاكد من عملية الحذف");
                alertDialog.setIcon(R.drawable.delete);
                alertDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        contactList.remove(position);
                        CalcTotal();
                        showList(0);

                    }
                });

                alertDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                try {
                    alertDialog.show();
                }catch (Exception ecx){}


            }
            break;
        }


        return super.onContextItemSelected(item);
    }
    public void btn_show_Approval_Pop(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "po");
        bundle.putString("CatNo", CatNo);
        android.app.FragmentManager Manager = getFragmentManager();
        Show_Orders_Need_Approval obj = new Show_Orders_Need_Approval();
        obj.setArguments(bundle);
        obj.show(Manager, null);


    }
    public void HideIcon() {
        NotiLyt.setVisibility(View.INVISIBLE);

        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        TextView Total = (TextView) findViewById(R.id.et_Total);
        TextView dis = (TextView) findViewById(R.id.et_dis);
        TextView TotalTax = (TextView) findViewById(R.id.et_TotalTax);
        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        OwnerSalesManNo = "";
        ForApproval = "0";
        UserDoSave = 0;
        Total.setText("0.0");
        dis.setText("0.0");
        TotalTax.setText("0.0");
        NetTotal.setText("0.0");

        IsNew = true;
        custNm.setText("");
        pono.setText("");
        acc.setText("");
        GetMaxPONo();
        contactList.clear();
        showList(0);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
       /* acc.setText(sharedPreferences.getString("CustNo", ""));
        custNm.setText(sharedPreferences.getString("CustNm", ""));*/
        ComInfo.OrderApprovalNo = "-1";
        ComInfo.CustNmApproval = "";
        ComInfo.accnoApproval = "";
        SharedPreferences.Editor editor    = sharedPreferences.edit();
        editor.putString("CustNo","");
        editor.putString("CustNm","");
        editor.commit();
    }
    public void Set_OrderForApproval(String No, String Nm, String acc) { // FillList

        SharedPreferences.Editor editor    = sharedPreferences.edit();
        editor.putString("CustNo",acc);
        editor.putString("CustNm",Nm);
        editor.commit();

        Set_Order_For_Aproval(No, Nm, acc);
        ComInfo.OrderApprovalNo = No;
        ComInfo.CustNmApproval = Nm;
        ComInfo.accnoApproval = acc;
        Lyt_CancleOrder.setVisibility(View.VISIBLE);

    }
    public void UpdateQty(String qty, String cln) {

        if (cln.equalsIgnoreCase("Qty")) {
            contactList.get(position).setQty(qty);
            CalcBounce(qty);
            CalcTotal();

        } else if (cln.equalsIgnoreCase("Bounce")) {
            contactList.get(position).setBounce(qty);
        }
        showList(1);
    }
    public String CalcBounce(String Qty) {

        String bounce = "0";
        if (!Qty.equalsIgnoreCase("")) {

            try {
                String q = "Select Bounce,AllowRepeats,Qty from Item_Bounce where  CAST(Item_Bounce.Qty as INTEGER ) <=" + Integer.parseInt(Qty) + "   " +
                        "   AND Item_No='" + contactList.get(position).getNo().toString() + "'  ORDER BY CAST(Item_Bounce.priority as INTEGER )   asc   Limit 1 ";
                Cursor c1 = sqlHandler.selectQuery(q);
                bounce = "0";
                String Bounc, AllowRepeat, Contitions;
                AllowRepeat = "0";
                Contitions = "0";
                Bounc = "0";
                int f;
                if (c1 != null && c1.getCount() != 0) {
                    if (c1.moveToFirst()) {
                        do {
                            Bounc = c1.getString(c1.getColumnIndex("Bounce"));
                            AllowRepeat = c1.getString(c1.getColumnIndex("AllowRepeats"));
                            Contitions = c1.getString(c1.getColumnIndex("Qty"));
                        } while (c1.moveToNext());
                    }
                    c1.close();
                }

                if (AllowRepeat.equalsIgnoreCase("1")) {
                    f = Integer.parseInt(Qty) / Integer.parseInt(Contitions);
                } else {
                    f = 1;
                }
                if (!Bounc.equalsIgnoreCase("0")) {
                    bounce = ((f * Integer.parseInt(Bounc + "")) + "");
                }
            } catch (Exception ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        return bounce;
    }
    long PostID;
    ProgressDialog progressDialog;

    public void Cancle_Order(View view) {

        if (!ComInfo.OrderApprovalNo.equalsIgnoreCase("-1")) {

            new UpdateOrderStatus().execute(UserID, ComInfo.OrderApprovalNo, "2", "تمت من قبل المشرف");
            UpdateOrderCount();
        }
    }
    private class UpdateOrderStatus extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {

            tv = new TextView(OrdersItems1.this);
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
            progressDialog = new ProgressDialog(OrdersItems1.this);
            progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgress(0);
            progressDialog.setMax(100);
            progressDialog.setMessage("  الرجاء الانتظار ..." + "  العمل جاري على تعديل حالة الطلب  ");
            tv.setText("الموافقات على طلب البيع");
            progressDialog.setCustomTitle(tv);
            progressDialog.setProgressDrawable(greenProgressbar);
            try {
                progressDialog.show();
            }catch (Exception df){}
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                CallWebServices ws = new CallWebServices(OrdersItems1.this);
                PostID = ws.UpdateSalesOrders_Status(params[0], params[1], params[2], params[3]);
            }catch (Exception sdf){}
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrdersItems1.this);
                tv.setText("الموافقات على طلب البيع");
                alertDialog.setMessage("تمت عملية الحفظ بنجاح");
                alertDialog.setIcon(R.drawable.tick);
                alertDialog.setPositiveButton("رجوع", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setTitle("الموافقات على طلب البيع");

                if (PostID > 0) {

                    GetOrders();
                    alertDialog.setMessage("تمت العملية بنجاح");
                    alertDialog.setIcon(R.drawable.tick);
                    UpdateOrderCount();
                    DoNew();
                } else {
                    alertDialog.setMessage(" العملية لم تتم بنجاح");
                    alertDialog.setIcon(R.mipmap.icon_delete);
                }
                progressDialog.dismiss();
                alertDialog.show();
            }catch (Exception sdf){}
        }

    }
    public void closeRound()
    {

        sqlHandler = new SqlHandler(this);
        TextView CustNm = (TextView) findViewById(R.id.tv_CustName);
        TextView Custadd = (TextView) findViewById(R.id.tv_Loc);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        SimpleDateFormat StartTime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        String StringTime = StartTime.format(new Date());



        ContentValues cv = new ContentValues();

        cv.put("Tr_Data", currentDateandTime);
        cv.put("End_Time", StringTime);
        cv.put("Closed", "1");
        cv.put("Note", "اغلاق الزيارة تلقائي ");


        Long i;
        i = sqlHandler.Update("SaleManRounds", cv, "Closed =0");
        //    i= sqlHandler.executeQuery("update  ManLogTrans set Tr_Data ='"+TrDate.getText().toString()+"' , End_Time = '"+et_EndTime.getText().toString()+"' ,Closed = 1 ,Note = '"+ tv_Note.getText().toString()+"' where Closed = 0");
        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();
       String OrderNo =sharedPreferences.getString("V_OrderNo", "0");

        alertDialog.setTitle(String.valueOf(getResources().getString(R.string.Galaxy)));
        if (i > 0) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("CustNo", "");
            editor.putString("CustNm", "");
            editor.putString("CustAdd", "");
            editor.putString("V_OrderNo", "-1");
            editor.putString("Note", "");
            editor.putString("IDN", "0");
            editor.commit();

            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.EndVisitSucc)));
            alertDialog.setIcon(R.drawable.tick);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
           // noti.cancel(this);
            InsertLogTrans obj=new InsertLogTrans(OrdersItems1.this,"11001" , SCR_ACTIONS.EndVisit.getValue(),OrderNo,accno.getText().toString() ,"","0");

        //    DoNew();
            SharManVisits();
           /* stopService(new Intent(MainActivity.this, AutoPostLocation.class));
            startService(new Intent(MainActivity.this, AutoPostLocation.class));*/
        } else {

            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.dontsave)));
            alertDialog.setIcon(R.drawable.error_new);
        }

    }
    public void SharManVisits() {

        //sqlHandler=new SqlHandler(this);

        final Handler _handler = new Handler();
        String query = "select  distinct no,ManNo, CusNo, DayNum ,Tr_Data ,Start_Time,End_Time, Duration,OrderNo " +
                "  ,Note,  X_Lat,Y_Long,Loct,IsException from SaleManRounds   where Posted = -1";
        Cursor c1 = sqlHandler.selectQuery(query);
        ArrayList<Cls_SaleManDailyRound> RoundList;
        RoundList = new ArrayList<Cls_SaleManDailyRound>();
        RoundList.clear();

        //query = " delete from   SaleManRounds   ";
        // sqlHandler.executeQuery(query);
        Cls_SaleManDailyRound cls_saleManDailyRound;

        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    cls_saleManDailyRound = new Cls_SaleManDailyRound();
                    cls_saleManDailyRound.setNo(c1.getString(c1
                            .getColumnIndex("no")));
                    cls_saleManDailyRound.setManNo(c1.getString(c1
                            .getColumnIndex("ManNo")));
                    cls_saleManDailyRound.setCusNo(c1.getString(c1
                            .getColumnIndex("CusNo")));
                    cls_saleManDailyRound.setDayNum(c1.getString(c1
                            .getColumnIndex("DayNum")));
                    cls_saleManDailyRound.setTr_Data(c1.getString(c1
                            .getColumnIndex("Tr_Data")));
                    cls_saleManDailyRound.setStart_Time(c1.getString(c1
                            .getColumnIndex("Start_Time")));
                    cls_saleManDailyRound.setEnd_Time(c1.getString(c1
                            .getColumnIndex("End_Time")));

                    cls_saleManDailyRound.setDuration(c1.getString(c1
                            .getColumnIndex("Duration")));

                    cls_saleManDailyRound.setOrderNo(c1.getString(c1
                            .getColumnIndex("OrderNo")));

                    cls_saleManDailyRound.setNote(c1.getString(c1
                            .getColumnIndex("Note")));


                    cls_saleManDailyRound.setX_Lat(c1.getString(c1
                            .getColumnIndex("X_Lat")));


                    cls_saleManDailyRound.setY_Long(c1.getString(c1
                            .getColumnIndex("Y_Long")));


                    cls_saleManDailyRound.setLoct(c1.getString(c1
                            .getColumnIndex("Loct")));

                    cls_saleManDailyRound.setIsException(c1.getString(c1
                            .getColumnIndex("IsException")));



                    RoundList.add(cls_saleManDailyRound);

                } while (c1.moveToNext());

            }
            c1.close();
        }
        final String json = new Gson().toJson(RoundList);

        Calendar c = Calendar.getInstance();
        final int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        //  final Handler _handler = new Handler();


        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(OrdersItems1.this);
                ws.SaveManVisits(json);
                try {
                    if (We_Result.ID > 0) {
                        String query = " Update  SaleManRounds  set Posted=1  where Posted = '-1'";
                        sqlHandler.executeQuery(query);
                        _handler.post(new Runnable() {
                            public void run() {

                            }
                        });

                     /*   query = " delete from   SaleManRounds   where Posted =1 and  DayNum < " + dayOfWeek;
                        sqlHandler.executeQuery(query);*/
                    }
                } catch (final Exception e) {
                }
            }
        }).start();
    }
}
