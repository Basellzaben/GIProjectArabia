package com.cds_jo.GalaxySalesApp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.AbuHaltam.cls_Inv_Sal_Done;
import com.cds_jo.GalaxySalesApp.InquireItem.GlobleV;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.cds_jo.GalaxySalesApp.warehouse.Cls_Item_Store_Qty;
import com.cds_jo.GalaxySalesApp.warehouse.Item_Store_qty_Adapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import header.Header_Frag;

public class requisitionsselling extends AppCompatActivity {
    ListView salorderlist,LstvItems;
    ArrayList<SaleOrder> contactList;
    ArrayList<SaleOrderDtl> contactListDtl;
int selectedP;
    AdapterView<?> selectedparent;
    View selectedview;
    ImageView hidden;
    Button show;
    Intent BackInt;
EditText et_ordernoO;
TextView tv_cusnmO,manno,tv_accO,tv_datee,
    et_TotalO,et_disO,et_TotalTaxO,tv_NetTotalO;
    LinearLayout linearlist, linearall;
    Drawable greenProgressbar;
    RelativeLayout.LayoutParams lp;
    public void btn_back(View view) {
        BackInt = new Intent(this, JalMasterActivity.class);
        startActivity(BackInt);




       /* if (contactListDtl.size() > 0) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("طلبات البيع");
            alertDialog.setMessage("هل تريد الاستمرار بعملية الخروج");
            alertDialog.setIcon(R.drawable.save);
            alertDialog.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(BackInt);
                }
            });


            alertDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
        } else {

            startActivity(BackInt);
        }*/


      /*  Intent k= new Intent(this, Main2Activity.class);
        startActivity(k);*/

    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisitionsselling);
        et_ordernoO=(EditText)findViewById(R.id.et_orderno);



       // getdays();

        tv_datee=(TextView)findViewById(R.id.tv_datee);
        tv_cusnmO=(TextView)findViewById(R.id.tv_cusnm);
        manno=(TextView)findViewById(R.id.manno);
        tv_accO=(TextView)findViewById(R.id.tv_acc);

        et_TotalO=(TextView)findViewById(R.id.et_Total);
        et_disO=(TextView)findViewById(R.id.et_dis);
        et_TotalTaxO=(TextView)findViewById(R.id.et_TotalTax);
        tv_NetTotalO=(TextView)findViewById(R.id.tv_NetTotal);

        Fragment frag = new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1, frag).commit();


        salorderlist = (ListView) findViewById(R.id.salorderlistt);
        LstvItems = (ListView) findViewById(R.id.LstvItems);

        contactList = new ArrayList<SaleOrder>();
        contactList.clear();

        contactListDtl = new ArrayList<SaleOrderDtl>();
        contactListDtl.clear();


        showSalOrderList();


        show = (Button) findViewById(R.id.show);
        hidden = (ImageView) findViewById(R.id.hidden);
        linearlist = (LinearLayout) findViewById(R.id.linearlist);
        linearall = (LinearLayout) findViewById(R.id.linearall);

        show.setVisibility(View.GONE);
        hidden.setVisibility(View.VISIBLE);
        linearlist.setVisibility(View.VISIBLE);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearlist.setVisibility(View.VISIBLE);
                hidden.setVisibility(View.VISIBLE);
                show.setVisibility(View.GONE);
                linearall.setWeightSum((float) 2.0);

            }
        });
        hidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearlist.setVisibility(View.GONE);
                hidden.setVisibility(View.GONE);
                show.setVisibility(View.VISIBLE);
                linearall.setWeightSum((float) 1.5);
            }
        });
        salorderlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                setHdrData(contactList.get(position).getInovice_type(),
                        contactList.get(position).getCustName(),
                        contactList.get(position).getOrderNo(),
                        contactList.get(position).getTr_Date(),
                        contactList.get(position).getNet_Total(),
                        contactList.get(position).getArabicName(),
                        contactList.get(position).getCustNo(),
                        contactList.get(position).getTotal(),
                        contactList.get(position).getTaxTotal(),
                        contactList.get(position).getDiscTotal(),
                        contactList.get(position).getDeliveryDate()

                        );





                selectedview=view;
                selectedparent=parent;
                final LinearLayout RR=(LinearLayout)view.findViewById(R.id.RR);
                selectedP=position;
                View oldView = parent.getChildAt(position);
                if (oldView != null && position != -1) {
                    RR.setBackgroundColor(getResources().getColor(R.color.Footer_Menu));
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(position%2==0)
                            {
                                RR.setBackgroundColor(Color.WHITE);
                            }
                            else
                            {
                                RR.setBackgroundColor(getResources().getColor(R.color.Gray2));
                            }
                        }
                    }, 200);
                }
            }
        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String man = sharedPreferences.getString("UserID", "");

        GetData(man);

    }

    public void setHdrData(String inovice_type, String CustName, String OrderNo, String Tr_Date, String Net_Total, String ArabicName,
                           String CustNo, String Total, String TaxTotal, String DiscTotal,String ddate){
        et_ordernoO=(EditText)findViewById(R.id.et_orderno);
        tv_cusnmO=(TextView)findViewById(R.id.tv_cusnm);
        manno=(TextView)findViewById(R.id.manno);
        tv_accO=(TextView)findViewById(R.id.tv_acc);

        et_TotalO=(TextView)findViewById(R.id.et_Total);
        et_disO=(TextView)findViewById(R.id.et_dis);
        et_TotalTaxO=(TextView)findViewById(R.id.et_TotalTax);
        tv_NetTotalO=(TextView)findViewById(R.id.tv_NetTotal);

        et_ordernoO.setText(OrderNo);
        tv_cusnmO.setText(CustName);
        manno.setText(ArabicName);
        tv_accO.setText(CustNo);

        et_TotalO.setText(Total);
        et_disO.setText(DiscTotal);
        et_TotalTaxO.setText(TaxTotal);
        tv_NetTotalO.setText(Net_Total);
        tv_datee.setText(ddate);

        GetDataDtl(OrderNo);

    }

    public void showSalOrderList(){

        SalOrdersAdapter contactListAdapter = new SalOrdersAdapter(requisitionsselling.this, contactList);
        salorderlist.setAdapter(contactListAdapter);
    }

    public void showSalOrderListDtl(){

        SalOrdersDtlAdapter contactListAdapter = new SalOrdersDtlAdapter(requisitionsselling.this, contactListDtl);
        LstvItems.setAdapter(contactListAdapter);
    }

    public void GetDataDtl(final String orderno) {
        contactListDtl.clear();
        showSalOrderListDtl();
        TextView tv = new TextView(requisitionsselling.this);
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requisitionsselling.this);
        final String UserID = sharedPreferences.getString("UserID", "");
        final ProgressDialog custDialog = new ProgressDialog(requisitionsselling.this);
        custDialog.setProgressStyle(custDialog.STYLE_HORIZONTAL);
        custDialog.setCanceledOnTouchOutside(false);
        custDialog.setProgress(0);
        custDialog.setMax(100);
        custDialog.setMessage("  الرجاء الانتظار ..." + "  العمل جاري على نسخ البيانات  ");
        tv.setText("طلبات بيع المندوبين");
        custDialog.setCustomTitle(tv);
        custDialog.setProgressDrawable(greenProgressbar);
        custDialog.show();


        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(requisitionsselling.this);



                ws.GatManSalesOrdeDtl(orderno);
                try {

                    Integer i;
                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray Item_no= js.getJSONArray("Item_no");
                    JSONArray Item_Name = js.getJSONArray("Item_Name");
                    JSONArray price = js.getJSONArray("price");
                    JSONArray Qty = js.getJSONArray("Qty");
                    JSONArray Tax_Amt = js.getJSONArray("Tax_Amt");
                    JSONArray Total = js.getJSONArray("Total");
                    JSONArray UnitName = js.getJSONArray("UnitName");
                    JSONArray Htotal = js.getJSONArray("Htotal");
                    JSONArray Dis_Amt = js.getJSONArray("Dis_Amt");
                    JSONArray Bounce = js.getJSONArray("Bounce");
                    JSONArray Notes = js.getJSONArray("Notes");


                    SaleOrderDtl obj ;

                    for (i = 0; i < Item_no.length(); i++) {
                        obj = new SaleOrderDtl();
                        obj.setBounce(Bounce.get(i).toString());
                        obj.setDis_Amt(Dis_Amt.get(i).toString());
                        obj.setItem_Name(Item_Name.get(i).toString());
                        obj.setItem_no(Item_no.get(i).toString());
                        obj.setPrice(price.get(i).toString());
                        obj.setQty(Qty.get(i).toString());
                        obj.setTax_Amt(Tax_Amt.get(i).toString());
                        obj.setTotal(Total.get(i).toString());
                        obj.setUnitName(UnitName.get(i).toString());
                        obj.setHtotal(Htotal.get(i).toString());
                        obj.setNotes(Notes.get(i).toString());
                        contactListDtl.add(obj);

                        custDialog.setMax(Item_no.length());
                        custDialog.incrementProgressBy(1);
                        if (custDialog.getProgress() == custDialog.getMax()) {
                            custDialog.dismiss();
                        }
                    }
                    final int total = i;
                    _handler.post(new Runnable() {

                        public void run() {
                            custDialog.dismiss();
                            showSalOrderListDtl();
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    requisitionsselling.this).create();
                            alertDialog.setTitle("طلبات بيع المندوبين");
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
                                        requisitionsselling.this).create();
                                alertDialog.setTitle("طلبات بيع المندوبين");
                                alertDialog.setMessage( We_Result.Msg);
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






    public void GetData(final String man) {
        contactList.clear();
        showSalOrderList();
        TextView tv = new TextView(requisitionsselling.this);
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requisitionsselling.this);
        final String UserID = sharedPreferences.getString("UserID", "");
        final ProgressDialog custDialog = new ProgressDialog(requisitionsselling.this);
        custDialog.setProgressStyle(custDialog.STYLE_HORIZONTAL);
        custDialog.setCanceledOnTouchOutside(false);
        custDialog.setProgress(0);
        custDialog.setMax(100);
        custDialog.setMessage("  الرجاء الانتظار ..." + "  العمل جاري على نسخ البيانات  ");
        tv.setText("طلبات بيع المندوبين");
        custDialog.setCustomTitle(tv);
        custDialog.setProgressDrawable(greenProgressbar);
        custDialog.show();


        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(requisitionsselling.this);



                ws.GatManSalesOrde("-1","-1",man );
                try {

                    Integer i;
                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray inovice_type= js.getJSONArray("inovice_type");
                    JSONArray CustName = js.getJSONArray("CustName");
                    JSONArray OrderNo = js.getJSONArray("OrderNo");
                    JSONArray Tr_Date = js.getJSONArray("Tr_Date");
                    JSONArray Net_Total = js.getJSONArray("Net_Total");
                    JSONArray ArabicName = js.getJSONArray("ArabicName");
                    JSONArray CustNo = js.getJSONArray("CustNo");
                    JSONArray Total = js.getJSONArray("Total");
                    JSONArray TaxTotal = js.getJSONArray("TaxTotal");
                    JSONArray DiscTotal = js.getJSONArray("DiscTotal");
                    JSONArray Notes = js.getJSONArray("Notes");
                    JSONArray DeliveryDate = js.getJSONArray("DeliveryDate");


                    SaleOrder obj ;
                    for (i = inovice_type.length()-1; i > -1 ; i--) {

                        obj = new SaleOrder();
                        obj.setInovice_type(inovice_type.get(i).toString());
                        obj.setCustName(CustName.get(i).toString());
                        obj.setOrderNo(OrderNo.get(i).toString());
                        obj.setTr_Date(Tr_Date.get(i).toString());
                        obj.setNet_Total(Net_Total.get(i).toString());
                        obj.setArabicName(ArabicName.get(i).toString());
                        obj.setCustNo(CustNo.get(i).toString());
                        obj.setTotal(Total.get(i).toString());
                        obj.setTaxTotal(TaxTotal.get(i).toString());
                        obj.setDiscTotal(DiscTotal.get(i).toString());
                        obj.setNote(Notes.get(i).toString());
                        obj.setDeliveryDate(DeliveryDate.get(i).toString());

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
                                    requisitionsselling.this).create();
                            alertDialog.setTitle("طلبات بيع المندوبين");
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
                                        requisitionsselling.this).create();
                                alertDialog.setTitle("طلبات بيع المندوبين");
                                alertDialog.setMessage("لا يوجد طلبات");
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

    public void btn_delete(View v){
        AlertDialog.Builder boite;
        boite = new AlertDialog.Builder(requisitionsselling.this);
        boite.setTitle("رفض طلب البيع");
        boite.setIcon(R.drawable.tick);
        final EditText input = new EditText(requisitionsselling.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("الملاحظات");
        input.setBackground(getResources().getDrawable(R.drawable.btn_cir_white_fill_black));
        input.setSingleLine(false);  //add this
        input.setLines(3);
        input.setMaxLines(10);
        // input.setGravity(Gravity.LEFT | Gravity.TOP);
        input.setHorizontalScrollBarEnabled(false);

        boite.setView(input);

        boite.setNegativeButton("رفض", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DoShare(et_ordernoO.getText().toString(),input.getText().toString()
                        ,"6","رفض طلب البيع");
            }
        });
        boite.setNeutralButton("رجوع", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //whatever action
            }
        });
        boite.show();
    }

public void btn_share(View v){
    AlertDialog.Builder boite;
    boite = new AlertDialog.Builder(requisitionsselling.this);
    boite.setTitle("الموافقة على طلب البيع");
    boite.setIcon(R.drawable.tick);
    final EditText input = new EditText(requisitionsselling.this);
    input.setInputType(InputType.TYPE_CLASS_TEXT);
    input.setHint("الملاحظات");
    input.setBackground(getResources().getDrawable(R.drawable.btn_cir_white_fill_black));
    input.setSingleLine(false);  //add this
    input.setLines(3);
    input.setMaxLines(10);
   // input.setGravity(Gravity.LEFT | Gravity.TOP);
    input.setHorizontalScrollBarEnabled(false);

    boite.setView(input);

    boite.setNegativeButton("اعتماد", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            et_ordernoO=(EditText)findViewById(R.id.et_orderno);
            tv_cusnmO=(TextView)findViewById(R.id.tv_cusnm);
            manno=(TextView)findViewById(R.id.manno);
            tv_accO=(TextView)findViewById(R.id.tv_acc);
            DoShare(et_ordernoO.getText().toString(),input.getText().toString()
            ,"4","الموافقة على طلب البيع");
        }
    });
    boite.setNeutralButton("رجوع", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            //whatever action
        }
    });
    boite.show();
}


    private  void DoShare(
    final String OrderNo, final String Notes, final String States , final  String txt){

        final ProgressDialog loadingdialog = ProgressDialog.show(requisitionsselling.this, "الرجاء الانتظار ...", "العمل جاري على ترحيل سند أخراج", true);
        loadingdialog.setCancelable(false);
        loadingdialog.setCanceledOnTouchOutside(false);
        loadingdialog.show();
        final Handler _handler = new Handler();


        // Toast.makeText(getApplicationContext(),str, Toast.LENGTH_LONG).show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                CallWebServices ws = new CallWebServices(requisitionsselling.this);
                ws.PostApprovalSalesOrder(OrderNo,Notes,States);
                try {
                    if (We_Result.ID > 0) {

                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        requisitionsselling.this).create();
                                alertDialog.setTitle(txt);
                                alertDialog.setMessage("تمت العملية بنجاح");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                loadingdialog.dismiss();
                                alertDialog.show();
                                alertDialog.show();

                            }
                        });
                    } else {

                        loadingdialog.dismiss();
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        requisitionsselling.this).create();
                                if (We_Result.ID == 0) {
                                    alertDialog.setMessage(txt);

                                } else {
                                    alertDialog.setMessage("عملية الترحيل لم تتم بنجاح " + "    ");

                                }

                                alertDialog.setTitle(" عملية الترحيل لم تتم بنجاح"+ "   " + We_Result.ID+"");
                                //  alertDialog.setMessage(We_Result.Msg.toString());
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                alertDialog.show();

                                alertDialog.setIcon(R.drawable.delete);
                            }
                        });
                    }

                } catch (final Exception e) {
                    loadingdialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    requisitionsselling.this).create();
                            alertDialog.setTitle("فشل في عمليه الاتصال");
                            alertDialog.setMessage(e.getMessage().toString());
                            alertDialog.setIcon(R.drawable.tick);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

                            alertDialog.show();
                        }
                    });
                }
            }
        }).start();
    }


}