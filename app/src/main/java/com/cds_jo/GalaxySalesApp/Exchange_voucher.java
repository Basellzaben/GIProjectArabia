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
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.ManLocation.AutoPostLocation;

import com.cds_jo.GalaxySalesApp.PostTransActions.PostSalesInvoice;
import com.cds_jo.GalaxySalesApp.TQNew.ContactListItems1;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.cds_jo.GalaxySalesApp.assist.Cls_Cur;
import com.cds_jo.GalaxySalesApp.assist.Cls_Cur_Adapter;
import com.cds_jo.GalaxySalesApp.assist.Logtrans.InsertLogTrans;
import com.cds_jo.GalaxySalesApp.assist.OrdersItems;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import header.Header_Frag;

public class Exchange_voucher extends AppCompatActivity {
    TextView no,et_date;    ExchangeModel  obj;
    ExchangeAdapter  contactListAdapter;
    ArrayList<ExchangeModel> Records;
    ListView lv_Items;
    public ProgressDialog loadingdialog;
    long PostResult = 0;

    RadioButton d, m;
    double daSUM=0;
    double mdSUM=0;
    RadioGroup radiogroup;
    boolean IsNew;
    Button btn_add;        SqlHandler  sqlHandler
;
    EditText name,desc,net;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_voucher);
lv_Items=(ListView)findViewById(R.id.lstCheck);
        name=(EditText)findViewById(R.id.name);
        desc=(EditText)findViewById(R.id.desc);
        net=(EditText)findViewById(R.id.net);
        radiogroup=(RadioGroup)findViewById(R.id.radiogroup);
        no=(TextView) findViewById(R.id.no);
        et_date=(TextView)findViewById(R.id.et_date) ;
        GetMaxPONo();
        btn_add=(Button)findViewById(R.id.btn_add);
        Records = new ArrayList<ExchangeModel>();
        Fragment frag=new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1,frag).commit();

        d=(RadioButton)findViewById(R.id.d);
        m=(RadioButton)findViewById(R.id.m);
        m.setChecked(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String currentDateandTime = sdf.format(new Date());
        et_date.setText(currentDateandTime.toString());
        sqlHandler = new SqlHandler(this);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().length()<1 &&  net.getText().length()<1 && no.getText().length()<1 && name.getText().length()<1){
                    Toast.makeText(Exchange_voucher.this,"يجب ادخال معلومات صحيحة" ,Toast.LENGTH_SHORT).show();
                }else
          additem(name.getText().toString(),no.getText().toString(),net.getText().toString(),desc.getText().toString());

                name.getText().clear();
                desc.getText().clear();
                net.getText().clear();
                no.setText("");
                name.getText().clear();

            }
        });
        FillCur();
    }



    public void btn_save_po(View view) {
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        String q = "SELECT Distinct *  from  Exchange_voucherHDR where   posted >0 AND   no ='" + pono.getText().toString() + "'";
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



            if (Records.size() < 1) {

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

            if (mdSUM != daSUM) {

                AlertDialog alertDialog = new AlertDialog.Builder(
                        this).create();
                alertDialog.setTitle(getResources().getText(R.string.Ordersales));
                alertDialog.setMessage("يجب ان يتساوى مجموع مبالغ المدفوع له و المدفوع منه");            // Setting Icon to Dialog
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
    public void btn_search_orders(View view) {
        Bundle bundle = new Bundle();
        FragmentManager Manager = getFragmentManager();
        ordernoSearchActivity obj = new ordernoSearchActivity();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }
    public void Set_OrderS(String namee,String noe){
        name.setText(namee);
        no.setText(noe);
    }

    private void FillCur() {
        final Spinner sp_cur = (Spinner) findViewById(R.id.sp_cur);
        SqlHandler sqlHandler = new SqlHandler(this);

        String query = "Select  distinct cur_no , cur from curf";
        ArrayList<Cls_Cur> cls_curs = new ArrayList<Cls_Cur>();
        cls_curs.clear();

        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Cur cls_cur = new Cls_Cur();

                    cls_cur.setName(c1.getString(c1
                            .getColumnIndex("cur")));
                    cls_cur.setNo(c1.getString(c1
                            .getColumnIndex("cur_no")));

                    cls_curs.add(cls_cur);

                } while (c1.moveToNext());

            }
            c1.close(); }

        Cls_Cur_Adapter cls_cur_adapter = new Cls_Cur_Adapter(
                this, cls_curs);

        sp_cur.setAdapter(cls_cur_adapter);

    }

    private  void additem(String name,String no,String net,String desc){
        String f,Flagnum;
        d=(RadioButton)findViewById(R.id.d);
        m=(RadioButton)findViewById(R.id.m);
        if(m.isChecked()){
            f="1";
            Flagnum="مدفوع له";
            mdSUM+=Double.parseDouble(net);
        }else {
            f="0";
            Flagnum="مدفوع منه";
            daSUM+=Double.parseDouble(net);
        }

        obj = new ExchangeModel();
        obj.setnamedtl(name);
        obj.setNo(no);
        obj.setNet(net);
        obj.setDesc(desc);
        obj.setFlag(Flagnum);
        obj.setFlagnum(f);
        Records.add(obj);

        contactListAdapter = new ExchangeAdapter(
                Exchange_voucher.this, Records);
        lv_Items.setAdapter(contactListAdapter);

        TextView mdtotal=(TextView)findViewById(R.id.mdtotal);
        TextView datotal=(TextView)findViewById(R.id.datotal);
        mdtotal.setText( String.valueOf(mdSUM));
        datotal.setText( String.valueOf(daSUM));
    }

    public void Save_Recod_Po() {



        TextView OrderNo = (TextView) findViewById(R.id.et_OrdeNo);

        String q = "SELECT Distinct *  from  Exchange_voucherHDR where   posted >0 AND   no ='" + OrderNo.getText().toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    Exchange_voucher.this).create();
            alertDialog.setTitle(getResources().getText(R.string.Ordersales));
            alertDialog.setMessage("لا يمكن التعديل على سند الصرف بعد اعتماده");            // Setting Icon to Dialog
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
        }else {

        EditText name=(EditText)findViewById(R.id.name);
        TextView    no=(TextView) findViewById(R.id.no);
        Spinner VouchType = (Spinner) findViewById(R.id.sp_cur);
        Integer index = VouchType.getSelectedItemPosition();
        Cls_Cur v = (Cls_Cur) VouchType.getItemAtPosition(index);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Long i;


       String UserID = sharedPreferences.getString("UserID", "");
         OrderNo = (TextView) findViewById(R.id.et_OrdeNo);
        EditText dd=(EditText)findViewById(R.id.dd);

        ContentValues cv = new ContentValues();
        cv.put("no", OrderNo.getText().toString());
        cv.put("name", dd.getText().toString());
        cv.put("date", currentDateandTime);
        cv.put("userid", UserID);
        cv.put("coin", v.getNo().toString().replace("\u202c","").replace("\u202d",""));
        cv.put("totalCreditor", String.valueOf(daSUM));
        cv.put("totalDebit", String.valueOf(mdSUM));
        cv.put("posted", "-1");


        String q1 = "Select * From Exchange_voucherHDR Where no='" + OrderNo.getText().toString() + "'";
        c1 = sqlHandler.selectQuery(q1);

        if (c1 != null && c1.getCount() != 0) {
            IsNew = false;
            c1.close();//6
        } else {
            IsNew = true;
        }


        if (IsNew == true) {
            i = sqlHandler.Insert("Exchange_voucherHDR", null, cv);
        } else {
            i = sqlHandler.Update("Exchange_voucherHDR", cv, "no ='" + OrderNo.getText().toString() + "'");
        }

        if (i > 0) {
             q = "Delete from  Exchange_voucherDTL where nohdr ='" + OrderNo.getText().toString() + "'";
            sqlHandler.executeQuery(q);


            for (int x = 0; x < Records.size(); x++) {
                ExchangeModel contactListItems = new ExchangeModel();
                contactListItems = Records.get(x);

                no=(TextView) findViewById(R.id.no);

                cv = new ContentValues();
                    cv.put("nohdr", OrderNo.getText().toString());
                    cv.put("no", contactListItems.getNo().toString());
                cv.put("name", contactListItems.getnamedtl());
                cv.put("net", contactListItems.getNet().toString().replace(",", ""));
                cv.put("note", contactListItems.getDesc().toString().replace(",", ""));
                cv.put("flag", contactListItems.getFlagnum().toString().replace(",", ""));
                i = sqlHandler.Insert("Exchange_voucherDTL", null, cv);
            }
        }

        if (i > 0) {



           GetMaxPONo();
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle(getResources().getText(R.string.Ordersales));
            alertDialog.setMessage(getResources().getText(R.string.AddCompleteSucc));
            IsNew = false;

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
            GetMaxPONo();
            lv_Items.setAdapter(null);
            name.getText().clear();
            desc.getText().clear();
            net.getText().clear();
            no.setText("");
            name.getText().clear();
             dd=(EditText)findViewById(R.id.dd);
            dd.getText().clear();
            Records.clear();
            mdSUM=0;
            daSUM=0;
            GetMaxPONo();
            lv_Items.setAdapter(null);
            name.getText().clear();
            desc.getText().clear();
            net.getText().clear();
            no.setText("");
            name.getText().clear();
            TextView mdtotal=(TextView)findViewById(R.id.mdtotal);
            TextView datotal=(TextView)findViewById(R.id.datotal);
            mdtotal.setText( "0.0");
            datotal.setText( "0.0");
            try {
                Toast.makeText(this,getResources().getText(R.string.AddCompleteSucc),Toast.LENGTH_LONG).show();
                //   alertDialog.show();

            }catch (Exception ecx){}


        }
    }}

    public void btn_delete(View view)
    {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("حذف جرد كميات العميل");
        alertDialog.setMessage("هل انت متاكد من عملية الحذف");
        alertDialog.setIcon(R.drawable.delete);
        alertDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                TextView no = (TextView)findViewById(R.id.no);
                TextView OrderNo = (TextView) findViewById(R.id.et_OrdeNo);

                String q = "SELECT Distinct *  from  Exchange_voucherHDR where   posted >0 AND   no ='" + OrderNo.getText().toString() + "'";
                Cursor c1 = sqlHandler.selectQuery(q);
                if (c1 != null && c1.getCount() != 0) {

                    AlertDialog alertDialog = new AlertDialog.Builder(
                            Exchange_voucher.this).create();
                    alertDialog.setTitle(getResources().getText(R.string.Ordersales));
                    alertDialog.setMessage("لا يمكن حذف سند الصرف بعد اعتماده");            // Setting Icon to Dialog// Setting Icon to Dialog
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
                }else
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
     //   TextView no = (TextView)findViewById(R.id.no);
        TextView acc = (TextView)findViewById(R.id.tv_acc);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String currentDateandTime = sdf.format(new Date());


        EditText no = (EditText)findViewById(R.id.et_OrdeNo);


        String query ="Delete from  Exchange_voucherHDR where no ='"+ no.getText().toString()+"'";
        sqlHandler.executeQuery(query);


        query ="Delete from  Exchange_voucherDTL where nohdr ='"+no.getText().toString()+"'";
        sqlHandler.executeQuery(query);
        Records.clear();
        GetMaxPONo();
        lv_Items.setAdapter(null);
        IsNew = true;
       // custNm.setText("");
       // acc.setText("");

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
    public void GetMaxPONo() {
        TextView OrderNo = (TextView) findViewById(R.id.et_OrdeNo);
        sqlHandler = new SqlHandler(Exchange_voucher.this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String UserID = sharedPreferences.getString("UserID", "");

        String query = "SELECT  Distinct  COALESCE(MAX(no), 0) +1 AS no FROM Exchange_voucherHDR where userId ='" + UserID + "'";
        Cursor c1 = sqlHandler.selectQuery(query);
        String max = "0";

        if (c1 != null && c1.getCount() > 0  ) {
            c1.moveToFirst();
            max = c1.getString(c1.getColumnIndex("no"));
            c1.close();
        }

        String max1 = "0";
       max1 = DB.GetValue(Exchange_voucher.this, "OrdersSitting", "Exchange", "1=1").replaceAll("[^\\d.]", "");
        if (max1 == "") {
            max1 = "0";
        }
        max1 = String.valueOf(Integer.parseInt(max1) + 1);
        if (SToD(max1) > SToD(max)) {
            max = max1;
        }

        if (max.length() == 1) {
            OrderNo.setText(intToString(Integer.valueOf(UserID), 2) + intToString(Integer.valueOf(max), 5));

        } else {
            OrderNo.setText(intToString(Integer.valueOf(max), 7));

        }


        OrderNo.setFocusable(false);
        OrderNo.setEnabled(false);
        OrderNo.setCursorVisible(false);



    }
    public void btn_back(View view) {
        Intent k = new Intent(this,JalMasterActivity.class);
        startActivity(k);
    }

    public void btn_new(View view) {
        EditText dd=(EditText)findViewById(R.id.dd);
        dd.getText().clear();
        Records.clear();
        mdSUM=0;
        daSUM=0;
        GetMaxPONo();
        lv_Items.setAdapter(null);
        name.getText().clear();
        desc.getText().clear();
        net.getText().clear();
        no.setText("");
        name.getText().clear();
        TextView mdtotal=(TextView)findViewById(R.id.mdtotal);
        TextView datotal=(TextView)findViewById(R.id.datotal);
        mdtotal.setText( "0.0");
        datotal.setText( "0.0");

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
    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
    }
   public void btn_search_Recv(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "Exchange_voucher");
        FragmentManager Manager = getFragmentManager();
        DoctorReportSearchActivity obj = new DoctorReportSearchActivity();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }

    public void Set_Order(String No) {
        TextView OrderNo = (TextView) findViewById(R.id.et_OrdeNo);

        OrderNo.setText(No);
        FillList(No);
    }

    private  void FillList(String OrderNoo ){
   double total=0;
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());
*/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Exchange_voucher.this);
        String currentDateandTime =preferences.getString("spinnerdateselected", "");


        TextView mdtotal=(TextView)findViewById(R.id.mdtotal);
        TextView datotal=(TextView)findViewById(R.id.datotal);

        Records  = new ArrayList<ExchangeModel>();
        Records.clear();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Exchange_voucher.this);
        String u =  sharedPreferences.getString("UserID", "");

        TextView OrderNo = (TextView) findViewById(R.id.et_OrdeNo);

        String q = "Select * FROM Exchange_voucherDTL where  nohdr ='"+OrderNoo+"'";
        SqlHandler sqlHandler = new SqlHandler(Exchange_voucher.this);
        Cursor c1 = sqlHandler.selectQuery(q);
        Double sum = 0.0 ;
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    ExchangeModel cls_searchRecVou= new ExchangeModel();

                    String fname="";
                    String f=c1.getString(c1.getColumnIndex("flag"));
                    if(f.contains("1"))
                        fname="مدفوع له";
                    else
                        fname="مدفوع منه";
                    total+=Double.parseDouble(c1.getString(c1.getColumnIndex("net")));
                    cls_searchRecVou.setNo(c1.getString(c1.getColumnIndex("no")));
                    cls_searchRecVou.setnamedtl(c1.getString(c1.getColumnIndex("name")));
                    cls_searchRecVou.setNet(c1.getString(c1.getColumnIndex("net")));
                    cls_searchRecVou.setDesc(c1.getString(c1.getColumnIndex("note")));
                    cls_searchRecVou.setFlag(fname);
                    cls_searchRecVou.setFlagnum(c1.getString(c1.getColumnIndex("flag")));

                    sum=sum+1;
                    Records.add(cls_searchRecVou);



                }while (c1.moveToNext());
            }
            c1.close();
        }
        ExchangeAdapter cls_Payments_Adapter = new ExchangeAdapter(
                Exchange_voucher.this,Records);

        lv_Items=(ListView)findViewById(R.id.lstCheck);

        lv_Items.setAdapter(cls_Payments_Adapter);
        EditText dd=(EditText)findViewById(R.id.dd);
        et_date=(TextView)findViewById(R.id.et_date) ;
        Spinner VouchType = (Spinner) findViewById(R.id.sp_cur);

        String  name =DB.GetValue(Exchange_voucher.this,"Exchange_voucherHDR","name","no ='"+OrderNoo+"'");
        String  date =DB.GetValue(Exchange_voucher.this,"Exchange_voucherHDR","date","no ='"+OrderNoo+"'");
        String  coin =DB.GetValue(Exchange_voucher.this,"Exchange_voucherHDR","coin","no ='"+OrderNoo+"'");

        dd.setText(name.toString());
        et_date.setText(date.toString());
        VouchType.setSelection(Integer.parseInt(coin)-1);



        mdSUM=total/2;
        daSUM=total/2;
        mdtotal.setText( String.valueOf(mdSUM));
        datotal.setText( String.valueOf(daSUM));

    }
   // name hdr dtl
    public void btn_share(View view) {
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        String userId = DB.GetValue(Exchange_voucher.this, "Exchange_voucherHDR", "userId", "no='"+pono.getText().toString()+"'");

        //String posted = DB.GetValue(Exchange_voucher.this, "Exchange_voucherHDR", "posted", "no='"+pono.getText().toString()+"'");
 if(userId.equals("-1")){
    new SweetAlertDialog(Exchange_voucher.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
            .setTitleText("سند الصرف")
            .setContentText("فشل في عملية الإعتماد , يجب حفظ السند قبل اعتماده")
            .setCustomImage(R.drawable.error_new)
            .show();
                          }else
    DoShare();
    }

    private  void DoShare(){
        //InsertLogTrans obj=new InsertLogTrans(Exchange_voucher.this,SCR_NO , SCR_ACTIONS.Share.getValue(),et_OrdeNo.getText().toString(),tv_acc.getText().toString(),"","0");
        final SqlHandler sql_Handler = new SqlHandler(this);
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        final String Doc_No = pono.getText().toString();

        loadingdialog = ProgressDialog.show(Exchange_voucher.this, "الرجاء الانتظار ...",    "العمل جاري على اعتماد السند " , true);
        loadingdialog.setCancelable(true);
        loadingdialog.setCanceledOnTouchOutside(true);
        loadingdialog.show();
        final Handler _handler = new Handler();



final String json=Post_json(pono.getText().toString());


        new Thread(new Runnable() {
            @Override
            public void run() {

                CallWebServices ws = new CallWebServices(Exchange_voucher.this);
                PostResult = ws.JsonSaveExchange(json);
                try {

                    if (PostResult < 0) {
                        _handler.post(new Runnable() {
                            public void run() {
                                loadingdialog.dismiss();



                                new SweetAlertDialog(Exchange_voucher.this,SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                        .setTitleText("سند الصرف")
                                        .setContentText("فشل في عملية الاعتماد")
                                        .setCustomImage(R.drawable.error_new)
                                        .show();
                            }
                        });

                    } else if (We_Result.ID > 0) {

                        _handler.post(new Runnable() {
                            public void run() {
                             //   updateManStore();
                                ContentValues cv = new ContentValues();
                                TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
                                cv.put("posted", We_Result.ID);
                                long i;
                                i = sql_Handler.Update("Exchange_voucherHDR", cv, " no='" + DocNo.getText().toString() + "'");

                                loadingdialog.dismiss();
                                new SweetAlertDialog(Exchange_voucher.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                        .setTitleText("سند الصرف")
                                        .setContentText("تمت عملية الأعتماد  بنجاح")
                                        .setCustomImage(R.drawable.tick)
                                        .show();
                              //  setCode();


                            }
                        });


                    //    DoPrint();
                    } else {


                        _handler.post(new Runnable() {
                            public void run() {
                                loadingdialog.dismiss();
                                new SweetAlertDialog(Exchange_voucher.this,SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                        .setTitleText(  "فاتورة المبيعات")
                                        .setContentText("لا يمكن الاعتماد , تم اعتماد السند مسبقا")
                                        .setCustomImage(R.drawable.error_new)
                                        .show();

                            }
                        });
                    }

                } catch (final Exception e) {

                    _handler.post(new Runnable() {
                        public void run() {
                            loadingdialog.dismiss();
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    Exchange_voucher.this).create();
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

    public String Post_json(String OrderNo) {
        final String pno = OrderNo;
        long Reslut = -1;

        String json = "[{''}]";
        try {
            if (Records.size() > 0) {
                json = new Gson().toJson(Records);
            }

        } catch (Exception ex) {

        }


        String query = "select * from Exchange_voucherHDR where no = '" + OrderNo.toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(query);
        JSONObject jsonObject = new JSONObject();
        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            try {
                jsonObject.put("namehdr", c1.getString(c1.getColumnIndex("name")));
                jsonObject.put("no", c1.getString(c1.getColumnIndex("no")));
                jsonObject.put("date", c1.getString(c1.getColumnIndex("date")));
                jsonObject.put("coin", c1.getString(c1.getColumnIndex("coin")));
                jsonObject.put("totalCreditor",String.valueOf(daSUM));
                jsonObject.put("totalDebit", String.valueOf(mdSUM));
                jsonObject.put("posted", c1.getString(c1.getColumnIndex("posted")));
                jsonObject.put("userId", c1.getString(c1.getColumnIndex("userId")));

            } catch (JSONException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            c1.close();
        }
        final String str;
        str = jsonObject.toString() + json;


        return str;
    }

}
