package com.cds_jo.GalaxySalesApp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.cds_jo.GalaxySalesApp.assist.Convert_Layout_Img;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import header.Header_Frag;

public class CustomerQty extends AppCompatActivity {
    SqlHandler sqlHandler;
    ListView lv_Items;
    ArrayList<ContactListItems> contactList ;
    EditText etItemNm, etPrice, etQuantity,etTax;
    Button btnsubmit;
    String UserID= "";
    String V_OrderNo= "";
    TextView tv;
    Drawable greenProgressbar;
    RelativeLayout.LayoutParams lp;
    public ProgressDialog loadingdialog;
public   String json;
    Boolean IsNew;

    public  void GetMaxPONo()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String u =  sharedPreferences.getString("UserID", "");
        String Login = sharedPreferences.getString("Login", "No");

        String query = "SELECT  COALESCE(MAX(orderno), 0) +1 AS no FROM CustStoreQtyhdr where userid ='"+UserID+"'";
        Cursor c1 = sqlHandler.selectQuery(query);
        String max="0";
        EditText Maxpo = (EditText) findViewById(R.id.et_OrdeNo);
        if (c1.getCount() > 0) {
            c1.moveToFirst();
            max= String.valueOf(c1.getInt(0));
        }

        if (max.length()==1) {
            Maxpo.setText(intToString(Integer.valueOf(u), 2) + intToString(Integer.valueOf(max), 5));
        }
        else {
            Maxpo.setText(max);

        }

        // Maxpo.setText(max);

    }
    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_qty);
        lv_Items = (ListView) findViewById(R.id.LstvItems);
        sqlHandler = new SqlHandler(this);
        IsNew = true;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        UserID= sharedPreferences.getString("UserID", "");

        GetMaxPONo();

        contactList = new ArrayList<ContactListItems>();
        contactList.clear();

        // showList(0);





        ImageButton back=(ImageButton)findViewById(R.id.imageButton7);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(CustomerQty.this, JalMasterActivity.class);
                startActivity(k);
            }
        });


        TextView CustNm =(TextView)findViewById(R.id.tv_cusnm);

        TextView accno = (TextView)findViewById(R.id.tv_acc);
        accno.setText(sharedPreferences.getString("CustNo", ""));
        CustNm.setText(sharedPreferences.getString("CustNm", ""));

      //  getitems(sharedPreferences.getString("CustNo", ""));
       // showList(1);
        Fragment frag = new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1, frag).commit();
    }

    private void showList( Integer f  )
    {

         lv_Items.setAdapter(null);
        float Total = 0 ;
        float Total_Tax = 0 ;
        float  TTemp= 0 ;
        float PTemp = 0 ;
        float PQty = 0 ;
        String query ="";
        TextView     etTotal, et_Tottal_Tax ;
        TextView ed_date ;
        etTotal = (TextView) findViewById(R.id.et_Total);
        et_Tottal_Tax = (TextView) findViewById(R.id.et_TotalTax);
       // etTotal.setText(String.valueOf(Total));
       // et_Tottal_Tax.setText(String.valueOf(Total_Tax));
        ed_date=(TextView) findViewById(R.id.ed_date);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Modify adapter's content here
                Cls_Cust_Qty_Item_Adapter contactListAdapter = new Cls_Cust_Qty_Item_Adapter(
                        CustomerQty.this, contactList);
                contactListAdapter.notifyDataSetChanged();
                lv_Items.setAdapter(contactListAdapter);
            }
        });



        //  json = new Gson().toJson(contactList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    public  void showPop()
    {


      Bundle bundle = new Bundle();
      bundle.putString("Scr", "CustQty");

      FragmentManager Manager =  getFragmentManager();
      PopCus_Qty_Items obj = new PopCus_Qty_Items();
      obj.setArguments(bundle);
      obj.show(Manager, null);

  }
    public void btn_Po(View view)
    {

    Intent k = new Intent(this,Convert_Layout_Img.class);
      //  Intent k = new Intent(this,BluetoothConnectMenu.class);



        startActivity(k);
    }
    public void btn_back(View view)
    {
        Intent k= new Intent(this, JalMasterActivity.class);
        startActivity(k);

    }
    public void btn_showPop(View view) {

        showPop();
    }
    public void Save_Method(String ItemNo , String p , String q ,String t,String u ,String dis ,String bounce)
    {

        TextView OrderNo = (TextView)findViewById(R.id.et_OrdeNo);
        String bounce_unitno,bounce_qty,dis_per,dis_value;
        bounce_unitno=bounce_qty=dis_per=dis_value="";
        String  query = "INSERT INTO CustStoreQtydetl(orderno,itemno,price,qty,tax,unitNo,dis_Amy,dis_per,bounce_qty,bounce_unitno) values " +
                "('"+ OrderNo.getText().toString()+"','"+ItemNo +"',"+p+","+q+","+t+",'"+u+"','"+dis_value+"','"+dis_value+"','"+bounce_qty+"','"+bounce_unitno+"')";

        sqlHandler.executeQuery(query);
        showList(1);
    }
    public void Save_List(String ItemNo   , String q ,  String   u ,  String   ItemNm , String UnitName )
    {
       // Toast.makeText(CustomerQty.this,contactList.size()+"",Toast.LENGTH_LONG).show();
        for (int x = 0; x < contactList.size(); x++) {
            ContactListItems contactListItems = new ContactListItems();
            contactListItems = contactList.get(x);

         if ( contactListItems.getNo().equals(ItemNo)) {
             AlertDialog alertDialog = new AlertDialog.Builder(

                     this).create();

             alertDialog.setTitle("Galaxy");
             alertDialog.setMessage("المادة موجودة");            // Setting Icon to Dialog
             alertDialog.setIcon(R.drawable.tick);
             alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {

                 }
             });

             alertDialog.show();
             return;
         }

        }

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat)nf;

        ContactListItems contactListItems = new ContactListItems();
        contactListItems.setno(ItemNo);
        contactListItems.setName(ItemNm);
        contactListItems.setUnite(u);
        contactListItems.setQty(q);
        contactListItems.setUniteNm(UnitName);
        contactList.add(contactListItems);


        showList(1);




  }
    public void btn_Search_Cust(View view)
    {

        FragmentManager Manager =  getFragmentManager();
        Select_Customer obj = new Select_Customer();
        obj.show(Manager, null);
    }
    public void Set_Cust(String No, String Nm)
    {
       TextView CustNm =(TextView)findViewById(R.id.tv_cusnm);
        TextView acc = (TextView)findViewById(R.id.tv_acc);
        acc.setText(No);
        CustNm.setText(Nm);
        CustNm.setError(null);
    }

    public void Set_Order(String No, String Nm, String acc)
    {
        TextView CustNm =(TextView)findViewById(R.id.tv_cusnm);
        TextView Order_no = (TextView)findViewById(R.id.et_OrdeNo);
        TextView accno = (TextView)findViewById(R.id.tv_acc);
        TextView Total = (TextView)findViewById(R.id.et_Total);
        TextView dis = (TextView)findViewById(R.id.et_dis);
        TextView NetTotal = (TextView)findViewById(R.id.tv_NetTotal);
        TextView TotalTax = (TextView)findViewById(R.id.et_TotalTax);

        Order_no.setText(No);
        CustNm.setText(Nm);
        accno.setText(acc);
        contactList.clear();
        showList(0);

       sqlHandler = new SqlHandler(this);


        String query = "  select  *  from CustStoreQtyhdr  where orderno ='" + Order_no.getText().toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                Total.setText(c1.getString(c1.getColumnIndex("Total")).toString());
                dis.setText(c1.getString(c1.getColumnIndex("disc_Total")).toString());
                NetTotal.setText(c1.getString(c1.getColumnIndex("Net_Total")).toString());
                TotalTax.setText(c1.getString(c1.getColumnIndex("Tax_Total")).toString());


            }
        }
        c1.close();

         query = "  select Unites.UnitName,  invf.Item_Name, pod.itemno,pod.price,pod.qty,pod.tax ,pod.unitNo ,pod.dis_Amt,pod.dis_per,pod.bounce_qty ,  pod.tax_Amt   , pod.total  " +
               "from CustStoreQtydetl pod left join invf on invf.Item_No =  pod.itemno    left join Unites on Unites.Unitno=  pod.unitNo  Where pod.orderno='" + Order_no.getText().toString() + "'";
        c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    ContactListItems contactListItems = new ContactListItems();

                    contactListItems.setno(c1.getString(c1
                            .getColumnIndex("itemno")));
                    contactListItems.setName(c1.getString(c1
                            .getColumnIndex("Item_Name")));
                    contactListItems.setprice(c1.getString(c1
                            .getColumnIndex("price")));
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


                    contactList.add(contactListItems);

                } while (c1.moveToNext());

            }
        }
        c1.close();


        showList(0);
        IsNew=false;
    }
    public void btn_searchCustomer(View view)
    {

        Bundle bundle = new Bundle();
        bundle.putString("Scr", "CustQty");
        FragmentManager Manager =  getFragmentManager();
        Select_Customer obj = new Select_Customer();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }
    public void btn_show_Pop(View view) {
        showPop();
    }
    public void btn_print(View view)
    {

/*
          Intent k = new Intent(this,Convert_Layout_Img.class);

        k.putExtra("Scr", "po");
        TextView   CustNm =(TextView)findViewById(R.id.tv_cusnm);
        TextView   OrdeNo = (TextView)findViewById(R.id.et_OrdeNo);
        TextView   accno = (TextView)findViewById(R.id.tv_acc);
        k.putExtra("cusnm", CustNm.getText().toString());
        k.putExtra("OrderNo", OrdeNo.getText().toString());
        k.putExtra("accno", accno.getText().toString());

        startActivity(k);*/
    }
    public void   Save_Recod_Po()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        TextView custNm = (TextView)findViewById(R.id.tv_cusnm);
        TextView pono = (TextView)findViewById(R.id.et_OrdeNo);
        TextView acc = (TextView)findViewById(R.id.tv_acc);

        TextView Total = (TextView)findViewById(R.id.et_Total);
        TextView dis = (TextView)findViewById(R.id.et_dis);
        TextView NetTotal = (TextView)findViewById(R.id.tv_NetTotal);
        TextView TotalTax = (TextView)findViewById(R.id.et_TotalTax);

        CheckBox Tax_Include = (CheckBox)findViewById(R.id.chk_Tax_Include);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());



        Long i;
        ContentValues cv =new ContentValues();
        cv.put("orderno", pono.getText().toString());
        cv.put("acc",acc.getText().toString());
        cv.put("date",currentDateandTime);
        cv.put("userid",UserID);
        cv.put("Total",Total.getText().toString());
        cv.put("Net_Total",NetTotal.getText().toString());
        cv.put("Tax_Total",TotalTax.getText().toString());
        cv.put("bounce_Total","0");
        cv.put("include_Tax","-1");
        cv.put("V_OrderNo", sharedPreferences.getString("V_OrderNo", "0"));



        cv.put("disc_Total",dis.getText().toString());


        if (IsNew==true) {
            i = sqlHandler.Insert("CustStoreQtyhdr", null, cv);
        }
        else {
            i = sqlHandler.Update("CustStoreQtyhdr", cv, "orderno ='"+pono.getText().toString()+"'");
        }





        if(i>0){
            String q ="Delete from  CustStoreQtydetl where orderno ='"+ pono.getText().toString()+"'";
            sqlHandler.executeQuery(q);

            for (int x = 0; x < contactList.size(); x++) {
                ContactListItems contactListItems = new ContactListItems();
                contactListItems = contactList.get(x);


                cv = new ContentValues();
                cv.put("orderno", pono.getText().toString());
                cv.put("itemno",contactListItems.getNo());
                cv.put("price","0");
                cv.put("qty", contactListItems.getQty().toString());
                cv.put("tax", "0");
                cv.put("unitNo", contactListItems.getUnite().toString());
                cv.put("dis_per","0");
                cv.put("dis_Amt", "0");
                cv.put("bounce_qty", "0");
                cv.put("tax_Amt", "0");
                cv.put("total", "0");
                i = sqlHandler.Insert("CustStoreQtydetl", null, cv);
            }
        }

            if (i> 0 ) {
                GetMaxPONo();
                AlertDialog alertDialog = new AlertDialog.Builder(
                        this).create();
                alertDialog.setTitle("Galaxy");
                alertDialog.setMessage("تمت عملية  الحفظ بنجاح");
             /*   contactList.clear();
                showList(0);
                custNm.setText("");
                acc.setText("");
                Total.setText("");
                dis.setText("");
                NetTotal.setText("");
                TotalTax.setText("");
                alertDialog.setIcon(R.drawable.tick);*/

                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
    }
    public void btn_save_po(View view)
    {
        if(contactList.size()<1){
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("الجرد");
            alertDialog.setMessage("لا يمكن حفظ البطاقة، يجب ادخال المواد");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
            //  c1.close();//7
            //  return;
            return;
        }

        sqlHandler = new SqlHandler(CustomerQty.this);

        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        String q = "SELECT Distinct *  from  CustStoreQtyhdr where   posted !='0' AND   orderno ='" + pono.getText().toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("الجرد");
            alertDialog.setMessage("لا يمكن التعديل على البطاقة، لقد تم ترحيل الطلب");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
            //  c1.close();//7
            //  return;
            return;
        }

        TextView custNm = (TextView)findViewById(R.id.tv_cusnm);
         pono = (TextView)findViewById(R.id.et_OrdeNo);
        TextView acc = (TextView)findViewById(R.id.tv_acc);
        if( custNm.getText().toString().length() == 0 ) {
            custNm.setError("required!");
            custNm.requestFocus();
            return;
        }

        if( pono.getText().toString().length() == 0 ) {
            pono.setError("required!");
            pono.requestFocus();
            return;
        }


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Galaxy");
        alertDialog.setMessage("هل تريد الاستمرار بعملية الحفظ");
        alertDialog.setIcon(R.drawable.save);
        alertDialog.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                 Save_Recod_Po();
            }
        });


        alertDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });


        alertDialog.show();


    }
    public void btn_delete(View view)
    {
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);

        String q = "SELECT Distinct *  from  CustStoreQtyhdr where   posted !='0' AND   orderno ='" +pono.getText().toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("طلب المرتجع");
            alertDialog.setMessage("لا يمكن التعديل  لقد تم ترحيل الطلب");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
            c1.close();
            return;

        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("حذف جرد كميات العميل");
       alertDialog.setMessage("هل انت متاكد من عملية الحذف");
       alertDialog.setIcon(R.drawable.delete);
        alertDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Delete_Record_PO();

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    public void Delete_Record_PO()
    {
        TextView custNm = (TextView)findViewById(R.id.tv_cusnm);
        TextView pono = (TextView)findViewById(R.id.et_OrdeNo);
        TextView acc = (TextView)findViewById(R.id.tv_acc);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String currentDateandTime = sdf.format(new Date());




        String query ="Delete from  CustStoreQtyhdr where orderno ='"+ pono.getText().toString()+"'";
        sqlHandler.executeQuery(query);


        query ="Delete from  CustStoreQtydetl where orderno ='"+ pono.getText().toString()+"'";
        sqlHandler.executeQuery(query);
        contactList.clear();
        GetMaxPONo();
        showList(0);
        IsNew = true;
       custNm.setText("");
       acc.setText("");

        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setTitle("جرد كميات العميل ");
        alertDialog.setMessage("تمت عملية الحذف بنجاح");
        alertDialog.setIcon(R.drawable.tick);
        alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
       alertDialog.show();
    }
    public void btn_Search_Orders(View view)
    {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "po");
        FragmentManager Manager =  getFragmentManager();
        SearchCustStoreQtyActivity obj = new SearchCustStoreQtyActivity();
        obj.setArguments(bundle);
        obj.show(Manager, null);
       }
    public void btn_share(View view)
    { TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        String q = "SELECT Distinct *  from  CustStoreQtyhdr where   posted > '0' AND   orderno ='" + pono.getText().toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("الجرد");
            alertDialog.setMessage("لا يمكن التعديل على البطاقة، لقد تم ترحيل الطلب");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
            //  c1.close();//7
            //  return;
            return;
        }
        q = "SELECT Distinct *  from  CustStoreQtyhdr where orderno ='" + pono.getText().toString() + "'";
        c1 = sqlHandler.selectQuery(q);

        if (c1 == null || c1.getCount() == 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("الجرد");
            alertDialog.setMessage("لا يمكن اعتماد الطلب قبل الحفظ");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
            //  c1.close();//7
            //  return;
            return;
        }
       final  SqlHandler sql_Handler = new SqlHandler(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final   TextView custNm = (TextView)findViewById(R.id.tv_cusnm);
         pono = (TextView)findViewById(R.id.et_OrdeNo);
        final  TextView acc = (TextView)findViewById(R.id.tv_acc);
        final  TextView Total = (TextView)findViewById(R.id.et_Total);
        final  TextView dis = (TextView)findViewById(R.id.et_dis);
        final  TextView NetTotal = (TextView)findViewById(R.id.tv_NetTotal);
        final  TextView TotalTax = (TextView)findViewById(R.id.et_TotalTax);
         CheckBox Tax_Include = (CheckBox)findViewById(R.id.chk_Tax_Include);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        final  String str;





        String  query = "SELECT acc,  date ,Delv_day_count,V_OrderNo FROM CustStoreQtyhdr where orderno  ='" +pono.getText().toString()+"'";
          c1 = sqlHandler.selectQuery(query);
        String  Date,Cust_No,Delv_day_count;
        Date=Cust_No=Delv_day_count="";

        if (c1.getCount() > 0) {
            c1.moveToFirst();
            Cust_No=c1.getString(c1.getColumnIndex("acc"));
            Delv_day_count   =c1.getString(c1.getColumnIndex("Delv_day_count"));
            Date  =c1.getString(c1.getColumnIndex("date"));
            V_OrderNo  =c1.getString(c1.getColumnIndex("V_OrderNo"));

        }
        c1.close();
        JSONObject  jsonObject = new JSONObject();
        try {
           jsonObject.put("Cust_No", Cust_No.toString());
           jsonObject.put("V_OrderNo", V_OrderNo.toString());
           jsonObject.put("day_Count",Delv_day_count==null ? "" : Delv_day_count.toString());
           jsonObject.put("Date",Date.toString());
           jsonObject.put("UserID", sharedPreferences.getString("UserID", ""));
           jsonObject.put("OrderNo",pono.getText().toString());
            if(Total.getText().toString().equals(""))
            jsonObject.put("Total","0");
            else
                jsonObject.put("Total",Total.getText().toString());

            if(NetTotal.getText().toString().equals(""))
            jsonObject.put("Net_Total","0");
            else
                jsonObject.put("Net_Total",NetTotal.getText().toString());


            if(TotalTax.getText().toString().equals(""))
            jsonObject.put("Tax_Total","0");
            else
                jsonObject.put("Tax_Total",TotalTax.getText().toString());


            jsonObject.put("bounce_Total","0");
            if(dis.getText().toString().equals(""))
                jsonObject.put("disc_Total","0");
            else
                jsonObject.put("disc_Total",dis.getText().toString());




           /* CheckBox Tax_Include1 = (CheckBox)findViewById(R.id.chk_Tax_Include);
            if (Tax_Include1.isChecked()){
                jsonObject.put("include_Tax","1");
            }
            else
            {
           */     jsonObject.put("include_Tax","0");
            //}

        }
        catch ( JSONException ex){
            ex.printStackTrace();
        }
        String json = new Gson().toJson(contactList);
         str = jsonObject.toString()+ json;


        loadingdialog = ProgressDialog.show(CustomerQty.this, "الرجاء الانتظار ...", "العمل جاري على اعتماد طلب البيع", true);
        loadingdialog.setCancelable(false);
        loadingdialog.setCanceledOnTouchOutside(false);
        loadingdialog.show();
        final Handler _handler = new Handler();


        // Toast.makeText(getApplicationContext(),str, Toast.LENGTH_LONG).show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                CallWebServices ws = new CallWebServices(CustomerQty.this);
                ws.Insert_CustomerQTY(str);
                try {

                    if (We_Result.ID > 0) {
                        ContentValues cv = new ContentValues();
                        TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
                        cv.put("posted", We_Result.ID);
                        long i;
                        i = sql_Handler.Update("CustStoreQtyhdr", cv, "orderno='"+ DocNo.getText().toString()+"'");

                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        CustomerQty.this).create();
                                alertDialog.setTitle("اعتماد جرد كميات العميل");
                                alertDialog.setMessage("تمت عملية اعتماد طلب البيع بنجاح" + We_Result.ID +"");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                loadingdialog.dismiss();
                                alertDialog.show();
                                alertDialog.show();
                                contactList.clear();
                                showList(0);
                                custNm.setText("");
                                acc.setText("");
                                Total.setText("");
                                dis.setText("");
                                NetTotal.setText("");
                                TotalTax.setText("");
                            }
                        });
                    } else {

                        loadingdialog.dismiss();
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        CustomerQty.this).create();
                                alertDialog.setTitle("فشل في عملية الاعتماد  " + "   " +We_Result.ID+"" );
                                alertDialog.setMessage(We_Result.Msg.toString());
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                alertDialog.show();

                                alertDialog.setIcon(R.drawable.delete);
                                alertDialog.setMessage("عملية الاعتماد لم تتم بنجاح" +"    " );
                            }
                        });
                    }

                } catch (final Exception e) {
                    loadingdialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    CustomerQty.this).create();
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
    public void btn_new(View view)
    {
        TextView custNm = (TextView)findViewById(R.id.tv_cusnm);
        TextView pono = (TextView)findViewById(R.id.et_OrdeNo);
        TextView acc = (TextView)findViewById(R.id.tv_acc);
        TextView Total = (TextView)findViewById(R.id.et_Total);
        TextView dis = (TextView)findViewById(R.id.et_dis);
        TextView TotalTax = (TextView)findViewById(R.id.et_TotalTax);
        TextView NetTotal = (TextView)findViewById(R.id.tv_NetTotal);


        Total.setText("0.0");
        dis.setText("0.0");
        TotalTax.setText("0.0");
        NetTotal.setText("0.0");


        custNm.setText("");
        pono.setText("");
        acc.setText("");
        GetMaxPONo();
        contactList.clear();
        showList(0);
    }
    public void btn_edit_Item( final View view)
    {
        int position =lv_Items.getPositionForView(view);
        String qty=contactList.get(position).qty;
        String name=contactList.get(position).name;
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "CustQtyedit");
        bundle.putString("itemid", String.valueOf(position));
        bundle.putString("no", String.valueOf(contactList.get(position).no));
        bundle.putString("qt", qty);
        bundle.putString("name", name);

        FragmentManager Manager =  getFragmentManager();
        PopCus_Qty_Items obj = new PopCus_Qty_Items();
        obj.setArguments(bundle);
        obj.show(Manager, null);

    }

    public void deleteItem(int p,String qt){
        contactList.get(p).setQty(qt);
        showList(0);
    }

    public void btn_Delete_Item( final View view)
    { TextView OrderNo = (TextView)findViewById(R.id.et_OrdeNo);

        //   Delete_Record_PO();
        String q = "SELECT Distinct *  from  CustStoreQtyhdr where   posted !='0' AND   orderno ='" +OrderNo.getText().toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("الجرد");
            alertDialog.setMessage("لا يمكن التعديل  لقد تم ترحيل الطلب");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
            c1.close();
            return;

        } {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("جرد كميات العميل ");
        alertDialog.setMessage("هل انت متاكد من عملية الحذف");
        alertDialog.setIcon(R.drawable.delete);
        alertDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                int position =lv_Items.getPositionForView(view);
                contactList.remove(position);

                showList(0);
            }
        });

        alertDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
             dialog.cancel();
            }
        });
         alertDialog.show();
    }}
    @Override
    public void onBackPressed()
    {
        Intent     BackInt = new Intent(this, JalMasterActivity.class);
        startActivity(BackInt);
    }

 /*   public void getitems(String customerIdd){
        final String customerId=customerIdd;
        tv = new TextView(getApplicationContext());
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
        final Handler _handler = new Handler();
        progressDialog = new ProgressDialog(CustomerQty.this);
        progressDialog.setMessage(" الرجاء الانتظار ..." + "  العمل جاري على نسخ البيانات");
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        tv.setText(" الوحـــــدات");
        progressDialog.setCustomTitle(tv);
        progressDialog.setProgressDrawable(greenProgressbar);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(CustomerQty.this);
                ws.Get_CustomerQty(customerId);
                try {
                    Integer i;
                    Integer count = 0;
                    String q = "";
                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray Item_No = js.getJSONArray("Item_No");
                    JSONArray Qty = js.getJSONArray("Qty");
                    JSONArray Item_Name = js.getJSONArray("Item_Name");
                    JSONArray Unit = js.getJSONArray("Unit");


                    for (i = 0; i < Item_No.length(); i++) {
                        String UintName= DB.GetValue(CustomerQty.this,
                                "Unites", "UnitName", "Unitno='" + Unit.get(i).toString() + "'");

*//*
                        Save_List(Item_No.get(i).toString(),Qty.get(i).toString(),Unit.get(i).toString()
       ,Item_Name.get(i).toString(),UintName);*//*
                        ContactListItems contactListItems = new ContactListItems();
                        contactListItems.setno(Item_No.get(i).toString());
                        contactListItems.setName(Item_Name.get(i).toString());
                        contactListItems.setUnite(Unit.get(i).toString());
                        contactListItems.setQty("0");
                        contactListItems.setUniteNm(UintName);
                        contactList.add(contactListItems);


                       // showList(1);
                        if (progressDialog.getProgress() == progressDialog.getMax()) {
                            progressDialog.dismiss();
                        }
                    }
                    _handler.post(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            Cls_Cust_Qty_Item_Adapter contactListAdapter = new Cls_Cust_Qty_Item_Adapter(
                                    CustomerQty.this, contactList);
                            contactListAdapter.notifyDataSetChanged();
                            lv_Items.setAdapter(contactListAdapter);
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
    }*/

}
