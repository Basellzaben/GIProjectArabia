package com.cds_jo.GalaxySalesApp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.PostTransActions.PostPayments;
import com.cds_jo.GalaxySalesApp.XprinterDoc.Xprinter_RecVoucher;
import com.cds_jo.GalaxySalesApp.assist.CheckAdapter;
import com.cds_jo.GalaxySalesApp.assist.Cls_Check;
import com.cds_jo.GalaxySalesApp.assist.Cls_Cur;
import com.cds_jo.GalaxySalesApp.assist.Cls_Cur_Adapter;
import com.cds_jo.GalaxySalesApp.assist.Convert_RecVouch_To_Img;
import com.cds_jo.GalaxySalesApp.assist.Convert_RecVouch_To_Img_GoodSystem;
import com.cds_jo.GalaxySalesApp.assist.Convert_RecVouch_To_Img_Tab10;
import com.cds_jo.GalaxySalesApp.assist.Logtrans.InsertLogTrans;
import com.cds_jo.GalaxySalesApp.assist.Sale_InvoiceActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import header.Header_Frag;

public class Catch_Receipt extends AppCompatActivity {
    ImageButton CustSearch;
    SqlHandler sql_Handler;
    ListView lstView;
    TextView TrDate, CheckData;
    public ProgressDialog loadingdialog;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    public int FlgDate = 0;
    long PostResult=0 ;
    String SCR_NO="11004";
    EditText et_OrdeNo;
    TextView tv_acc;
    ArrayList<Cls_Check> ChecklList;
    Boolean IsNew;
    EditText  Cash ;
    public static final String CALCULATOR_PACKAGE ="com.android.calculator2";
    public static final String CALCULATOR_CLASS ="com.android.calculator2.Calculator";
    EditText et_ss;

    public void setDate(View view) {
        showDialog(999);
       /* Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                .show();*/
    }
    public static String getMonthName(int month){
        switch(month+1){
            case 1:
                return "Jan";

            case 2:
                return "Feb";

            case 3:
                return "Mar";

            case 4:
                return "Apr";

            case 5:
                return "May";

            case 6:
                return "Jun";

            case 7:
                return "Jul";

            case 8:
                return "Aug";

            case 9:
                return "Sep";

            case 10:
                return "Oct";

            case 11:
                return "Nov";

            case 12:
                return "Dec";
        }

        return "";
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year,month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day

            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        if (FlgDate == 1) {
            dateView.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }

        if (FlgDate == 2) {
            CheckData.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_receipt);

         et_ss = (EditText) findViewById(R.id.et_ss);

        lstView = (ListView) findViewById(R.id.lstCheck);

        ChecklList = new ArrayList<Cls_Check>();
        ChecklList.clear();
        IsNew = true;

        FillRecType();
        FillCur();

        Cash =(EditText)findViewById(R.id.et_Cash);

        final Calendar c = Calendar.getInstance();
        c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);

        dateView = (TextView) findViewById(R.id.et_Date);
        calendar = Calendar.getInstance();
        year = c.get(Calendar.YEAR);

        month = c.get(Calendar.MONTH);

        day = c.get(Calendar.DAY_OF_MONTH);
        showDate(year, month, day);

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlgDate = 1;
                showDialog(999);


            }
        });

        TextView CheckTotal = (TextView) findViewById(R.id.tv_CheckAmt);
        CheckTotal.setFocusable(false);
        CheckTotal.setEnabled(false);
        CheckTotal.setCursorVisible(false);

        TextView et_Amt = (TextView) findViewById(R.id.et_Amt);
        et_Amt.setFocusable(false);
        et_Amt.setEnabled(false);
        et_Amt.setCursorVisible(false);



       /*lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                final  Integer position =pos  ;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Catch_Receipt.this);
                alertDialog.setTitle("حذف الشيكات");
                alertDialog.setMessage("هل انت متاكد من عملية الحذف ..؟");

                alertDialog.setIcon(R.drawable.delete);


                alertDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ChecklList.remove(position);

                    }
                });


                alertDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });


                alertDialog.show();

            }
        });

*/





        ArrayList<Cls_Bank_Search> cls_bank_searches = new ArrayList<Cls_Bank_Search>();
        cls_bank_searches.clear();
        try {
            sql_Handler = new SqlHandler(this);
        } catch (Exception ex) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();

            // Setting Dialog Title
            alertDialog.setTitle("سند القبض");

            // Setting Dialog Message
            alertDialog.setMessage(ex.getMessage().toString());


            alertDialog.setIcon(R.drawable.tick);

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            // Showing Alert Message
            alertDialog.show();
        }



        CustSearch = (ImageButton) findViewById(R.id.btn_Cust_Search);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        GetMaxRecNo();
        TrDate = (TextView) findViewById(R.id.et_Date);
        TrDate.setText(currentDateandTime);

        TextView CheckDate = (TextView) findViewById(R.id.CheckData);





        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);



        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        custNm.setText("");
        acc.setText("");
        custNm.setText(sharedPreferences.getString("CustNm", ""));
        acc.setText(sharedPreferences.getString("CustNo", ""));


        final TextView et_Cash =    (EditText)findViewById(R.id.et_Cash);
        et_Cash.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Toast.makeText(getActivity(),s.toString(),Toast.LENGTH_SHORT).show();
                CalcTotal();

            }
        });


        Cash.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                Cash.setText("", TextView.BufferType.EDITABLE);
                return false;
            }
        });


        et_Cash.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_Cash.setText("", TextView.BufferType.EDITABLE);
                }
            }

        });
        et_OrdeNo=(EditText)findViewById(R.id.et_OrdeNo);
        tv_acc=(TextView)findViewById(R.id.tv_acc);
        InsertLogTrans obj=new InsertLogTrans(Catch_Receipt.this,SCR_NO , SCR_ACTIONS.open.getValue(),et_OrdeNo.getText().toString(),tv_acc.getText().toString(),"","0");

        Fragment frag=new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1,frag).commit();
    }
    private void FillRecType() {

        final Spinner VouchType = (Spinner) findViewById(R.id.sp_VouchType);


        ArrayList<Cls_Cur> VouchTypeList = new ArrayList<Cls_Cur>();
        VouchTypeList.clear();

        Cls_Cur cls_cur = new Cls_Cur();
        cls_cur.setName("نقــدي");
        cls_cur.setNo("1");
        VouchTypeList.add(cls_cur);


        cls_cur = new Cls_Cur();
        cls_cur.setName("شيكـــات");
        cls_cur.setNo("2");
        VouchTypeList.add(cls_cur);


        cls_cur = new Cls_Cur();
        cls_cur.setName("نقدي + شيكات");
        cls_cur.setNo("3");
        VouchTypeList.add(cls_cur);


        Cls_Cur_Adapter cls_cur_adapter = new Cls_Cur_Adapter(
                this, VouchTypeList);
        VouchType.setAdapter(cls_cur_adapter);
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
    public void DoNew() {

        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);

        TextView acc = (TextView) findViewById(R.id.tv_acc);
        TextView amt = (TextView) findViewById(R.id.et_Amt);
        TextView note = (TextView) findViewById(R.id.et_notes);
        TextView CheckTotal = (TextView) findViewById(R.id.tv_CheckAmt);
        // custNm.setText("");
        //acc.setText("");
        amt.setText("0");
        note.setText("");
        Cash.setText("");
        CheckTotal.setText("0");
        Spinner VouchType = (Spinner) findViewById(R.id.sp_VouchType);
        Spinner cur = (Spinner) findViewById(R.id.sp_cur);
        VouchType.setSelection(0);
        cur.setSelection(0);

        IsNew=true;

     /*   ImageButton btn_delete = (ImageButton)findViewById(R.id.imageButton6);
        ImageButton btn_add = (ImageButton)findViewById(R.id.imageButton3);
        ImageButton btn_share= (ImageButton)findViewById(R.id.imageButton9);

            btn_delete.setEnabled(false);
            btn_add.setEnabled(true);
            btn_share.setEnabled(true);*/
        ChecklList.clear();
    }
    public void btn_SearchCust(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "RecVoch");
        FragmentManager Manager = getFragmentManager();
        Select_Customer obj = new Select_Customer();
        obj.setArguments(bundle);
        obj.show(Manager, null);

    }
    public void GetMaxRecNo() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String Login = sharedPreferences.getString("Login", "No");
        if(Login.toString().equals("No")){
            Intent i = new Intent(this,NewLoginActivity.class);
            startActivity(i);
        }

        String u =  sharedPreferences.getString("UserID", "").replaceAll("[^\\d.]", "");
        sql_Handler = new SqlHandler(this);
        String query = "SELECT  ifnull(MAX(DocNo), 0) +1 AS no FROM RecVoucher   where UserID = '"+u.toString().replaceAll("[^\\d.]", "")+"'";
        Cursor c1 = sql_Handler.selectQuery(query);
        Integer max =  0 ;
        EditText Maxpo = (EditText) findViewById(R.id.et_OrdeNo);
        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            max =Integer.parseInt(  c1.getString(c1.getColumnIndex("no")));
            c1.close();
        }
        Integer max1=0;
        String Payment_No;
        try {
            Payment_No = DB.GetValue(Catch_Receipt.this, "OrdersSitting", "Payment", "1=1");
            Payment_No = Payment_No.replaceAll("[^\\d.]", "");
            max1 = Integer.parseInt(Payment_No.toString());


        }catch (Exception ex){
            Toast.makeText(this,ex.getMessage().toString(),Toast.LENGTH_SHORT).show();
            max1=0;
        }
        //max1 = sharedPreferences.getString("m2", "");
        if (max1<=0){
            max1 =0;
        }

        max1 = max1 + 1;
        if (max1 >  max )
        {
            max = max1 ;
        }

        if (max.toString().length()==1) {
            Maxpo.setText(intToString(Integer.valueOf(u), 2) + intToString(Integer.valueOf(max), 5));

        }
        else {

            Maxpo.setText(intToString(Integer.valueOf(max), 7)  );

        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(Maxpo.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        Maxpo.setFocusable(false);
        Maxpo.setEnabled(false);
        Maxpo.setCursorVisible(false);


        // Maxpo.setKeyListener(null);
        // Maxpo.setBackgroundColor(Color.TRANSPARENT);

        DoNew();
        showList();

    }
    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
    }
    public void Set_Cust(final String No, String Nm) {
        final TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        acc.setText(No);
        CustNm.setText(Nm);
        CustNm.setError(null);
    }
    public void Save_Recod_Po() {

        Integer  Seq = 0 ;
        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        TextView amt = (TextView) findViewById(R.id.et_Amt);
        TextView note = (TextView) findViewById(R.id.et_notes);
        TextView Date = (TextView) findViewById(R.id.et_Date);
        TextView CheckTotal = (TextView) findViewById(R.id.tv_CheckAmt);


        String query;


        // query = "Delete from  RecVoucher  where DocNo ='" + DocNo.getText().toString() + "'";
        //sql_Handler.executeQuery(query);
        Spinner sp_cur = (Spinner) findViewById(R.id.sp_cur);

        Spinner VouchType = (Spinner) findViewById(R.id.sp_VouchType);

        Integer indexValue = sp_cur.getSelectedItemPosition();
        Cls_Cur o = (Cls_Cur) sp_cur.getItemAtPosition(indexValue);

        if (Cash.getText().toString().length() == 0) {
            Cash.setText("0");
        }

        if (CheckTotal.getText().toString().length() == 0) {
            CheckTotal.setText("0");
        }


        Integer index = VouchType.getSelectedItemPosition();
        Cls_Cur v = (Cls_Cur) VouchType.getItemAtPosition(index);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        int dayOfWeek;
        Calendar c = Calendar.getInstance();
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        Seq =Integer.parseInt( DB.GetValue(this,"RecVoucher","ifnull(Max(Seq),0)+1","TrDate='"+Date.getText().toString()+"'"));
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);



        String q1 = "Select * From RecVoucher Where DocNo='"+ DocNo.getText().toString().replaceAll("[^\\d.]", "")+"'";
        Cursor c1 ;
        c1 =sql_Handler.selectQuery(q1);

        if(c1!=null && c1.getCount()!=0){
            IsNew=false;
            c1.close();
        }else
        {
            IsNew= true;
        }



        if (IsNew==true) {
            Seq = Integer.parseInt(DB.GetValue(this, "RecVoucher", "ifnull(Max(Seq),0)+1", "TrDate='" +  Date.getText().toString()+ "'"));

        }else {
            Seq = Integer.parseInt(DB.GetValue(this, "RecVoucher", "ifnull(Seq,0)", "DocNo='" + DocNo.getText().toString() + "'"));

        }
        ContentValues cv = new ContentValues();
        cv.put("DocNo", DocNo.getText().toString().replace("\u202c","").replace("\u202d",""));
        cv.put("CustAcc", acc.getText().toString().replace("\u202c","").replace("\u202d",""));
        cv.put("Amnt", amt.getText().toString().replace("\u202c","").replace("\u202d",""));
        cv.put("TrDate", Date.getText().toString().replace("\u202c","").replace("\u202d",""));
        cv.put("Desc", note.getText().toString().replace("\u202c","").replace("\u202d",""));
        cv.put("VouchType", v.getNo().toString().replace("\u202c","").replace("\u202d",""));
        cv.put("curno", o.getNo().toString().replace("\u202c","").replace("\u202d",""));
        cv.put("Cash", Cash.getText().toString().replace("\u202c","").replace("\u202d",""));
        cv.put("CheckTotal", CheckTotal.getText().toString().replace("\u202c","").replace("\u202d",""));
        cv.put("Post","-1");
        cv.put("Seq",Seq.toString().replace("\u202c","").replace("\u202d",""));
        cv.put("UserID", sharedPreferences.getString("UserID", "").replace("\u202c","").replace("\u202d",""));
        cv.put("V_OrderNo",sharedPreferences.getString("V_OrderNo", "0").replace("\u202c","").replace("\u202d",""));
        cv.put("DayNum",dayOfWeek+"".replace("\u202c","").replace("\u202d",""));
        cv.put("FromSales","0");
        long i;

        if (IsNew==true) {
            i = sql_Handler.Insert("RecVoucher", null, cv);
        }
        else
        {
            alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("سند القبض");

            alertDialog.setMessage("لا يمكن التعديل على سند القبض");
            alertDialog.setIcon(R.drawable.delete);

            alertDialog.setButton("نعم", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
            return;

        }

        if (i > 0) {

            for (int x = 0; x < ChecklList.size(); x++) {
                Cls_Check cls_check_obj = new Cls_Check();
                cls_check_obj = ChecklList.get(x);


                cv = new ContentValues();
                cv.put("DocNo", DocNo.getText().toString().replace("\u202c","").replace("\u202d",""));
                cv.put("CheckNo", cls_check_obj.getCheckNo().toString().replace("\u202c","").replace("\u202d",""));
                cv.put("CheckDate", cls_check_obj.getCheckDate().toString().replace("\u202c","").replace("\u202d",""));
                cv.put("BankNo", cls_check_obj.getBankNo().toString().replace("\u202c","").replace("\u202d",""));
                cv.put("Amnt", cls_check_obj.getAmnt().toString().replace("\u202c","").replace("\u202d",""));
                cv.put("UserID", sharedPreferences.getString("UserID", "").replace("\u202c","").replace("\u202d",""));

                i = sql_Handler.Insert("RecCheck", null, cv);
            }
        }


        //sql_Handler.executeQuery(query);

        final View view=null;
        alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setTitle("سند القبض");
        if (i > 0) {
            InsertLogTrans obj=new InsertLogTrans(Catch_Receipt.this,SCR_NO , SCR_ACTIONS.Insert.getValue(),et_OrdeNo.getText().toString(),tv_acc.getText().toString(),"","0");

            alertDialog.setMessage(" تمت عملية الحفظ بنجاح");
            alertDialog.setIcon(R.drawable.tick);
           /* GetMaxRecNo();
            showList();
            DoNew();*/
           /* SharedPreferences.Editor editor    = sharedPreferences.edit();
            String Count = sharedPreferences.getString("PayCount", "0");
            Count = (SToD(Count)+1) + "";
            editor.putString("PayCount",Count);
            editor.commit();*/
            IsNew = false;

        } else {
            alertDialog.setMessage("عملية الحفظ لم تتم ");
            alertDialog.setIcon(R.drawable.delete);
        }
        // Setting OK Button
        alertDialog.setButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                UpDateMaxOrderNo();
                //  btn_print(view);



            }
        });

        // Showing Alert Message
        alertDialog.show();



    }
    private  void  UpDateMaxOrderNo() {//

        try {


            SqlHandler sqlHandler;
            sqlHandler = new SqlHandler(this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String u = sharedPreferences.getString("UserID", "");
            String query = "SELECT   Distinct  COALESCE(MAX( cast(DocNo as integer)), 0)   AS no " +
                    " FROM RecVoucher   where  UserID = '" + u.toString() + "'";
            Cursor c1 = sqlHandler.selectQuery(query);
            String max = "0";

            if (c1 != null && c1.getCount() != 0) {
                c1.moveToFirst();
                max = c1.getString(c1.getColumnIndex("no"));
                c1.close();
            }

            query = " Update OrdersSitting SET Payment ='" + max + "'";
            sqlHandler.executeQuery(query);
     /*   SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("m2",max);
        editor.commit();*/
        }
        catch (Exception ex){

        }
    }
    public void btn_save_po(View view) {


        if ( et_ss.getText().toString().equals("")) {
          Toast.makeText(Catch_Receipt.this,"يرجى تعبئة جميع البيانات",Toast.LENGTH_LONG).show();
        }else{
        // Save Rec
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        TextView amt = (TextView) findViewById(R.id.et_Amt);
        amt.setError(null);
        custNm.setError(null);
        DocNo.setError(null);
        if (SToD(amt.getText().toString().replaceAll("[^\\d.]", "")) == 0) {
            amt.setError("required!");
            amt.requestFocus();
            return;
        }

        if (custNm.getText().toString().length() == 0) {
            custNm.setError("required!");
            custNm.requestFocus();
            return;
        }

        if (DocNo.getText().toString().length() == 0) {
            DocNo.setError("required!");
            DocNo.requestFocus();
            return;
        }
        amt.setError(null);
        custNm.setError(null);
        DocNo.setError(null);
        if (SToD(amt.getText().toString().replaceAll("[^\\d.]", "")) == 0) {
            amt.setError("required!");
            amt.requestFocus();
            return;
        }

        if (custNm.getText().toString().length() == 0) {
            custNm.setError("required!");
            custNm.requestFocus();
            return;
        }

        if (DocNo.getText().toString().length() == 0) {
            DocNo.setError("required!");
            DocNo.requestFocus();
            return;
        }


        ///////////////////////////////////////////////////
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String Count = sharedPreferences.getString("PayCount", "0");
        String NumOfInvPerVisit = DB.GetValue(Catch_Receipt.this, "ComanyInfo", "NumOfPayPerVisit", "1=1");

        if (SToD(Count) >= SToD(NumOfInvPerVisit)) {
            alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("سند القبض");
            alertDialog.setMessage("يجب فتح جولة جديدة حتى تتمكن من تنفيذ هذ العملية");
            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setButton("نعم", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            // alertDialog.show();
            //return;
        }
        ///////////////////////////////////////////////////////
        Spinner VouchType = (Spinner) findViewById(R.id.sp_VouchType);
        Integer index = VouchType.getSelectedItemPosition();
        Cls_Cur v = (Cls_Cur) VouchType.getItemAtPosition(index);

        if (ChecklList.size() > 0 && v.getNo().toString().equals("1")) {
            alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("سند القبض");

            alertDialog.setMessage("لا يمكن ادخال شيكات لان نوع القبض نقدي");

            alertDialog.setIcon(R.drawable.delete);

            alertDialog.setButton("نعم", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
            return;
        }


        if (v.getNo().toString().equals("1") && ((SToD(Cash.getText().toString().replaceAll("[^\\d.]", "")) - SToD(amt.getText().toString().replaceAll("[^\\d.]", ""))) != 0)) {
            alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("سند القبض");

            alertDialog.setMessage("قيمة القبض النقدي يجب ان تساوي قيمة القبض");
            alertDialog.setIcon(R.drawable.delete);

            alertDialog.setButton("نعم", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
            return;
        }


        if (ChecklList.size() == 0 && (v.getNo().toString().equals("3") || v.getNo().toString().equals("2"))) {
            alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("سند القبض");

            alertDialog.setMessage("يجب  ادخال معلومات الشيكات");
            alertDialog.setIcon(R.drawable.delete);

            alertDialog.setButton("نعم", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
            return;
        }
        if ((SToD(Cash.getText().toString()) > 0) && v.getNo().toString().equals("2")) {
            alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("سند القبض");

            alertDialog.setMessage("لا يمكن ادخال مبلغ نقدي لان نوع القبض شيكات ");
            alertDialog.setIcon(R.drawable.delete);

            alertDialog.setButton("نعم", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
            return;
        }

        Double sum = 0.0;
        Double amount = SToD(amt.getText().toString().replaceAll("[^\\d.]", ""));
        for (int x = 0; x < ChecklList.size(); x++) {
            Cls_Check cls_check_obj = new Cls_Check();
            cls_check_obj = ChecklList.get(x);
            sum = sum + SToD(cls_check_obj.getAmnt());
        }

        if (v.getNo().toString().equals("2") && (sum + (SToD(Cash.getText().toString())) != amount)) {
            alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("سند القبض");

            alertDialog.setMessage("الرجاء التاكد من قيمة الشيكات");
            alertDialog.setIcon(R.drawable.delete);

            alertDialog.setButton("نعم", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
            return;
        }


        if (v.getNo().toString().equals("2")) {
            if ((SToD(amount.toString()) - SToD(sum.toString())) != 0) {
                alertDialog = new AlertDialog.Builder(
                        this).create();
                alertDialog.setTitle("سند القبض");

                alertDialog.setMessage("الرجاء التاكد من قيمة الشيكات" + "، مجموع الشيكات يجب ان يساوي " + String.valueOf(amount));
                alertDialog.setIcon(R.drawable.delete);

                alertDialog.setButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
                return;
            }
        }

        if (v.getNo().toString().equals("3")) {
            if (amount <= sum) {
                alertDialog = new AlertDialog.Builder(
                        this).create();
                alertDialog.setTitle("سند القبض");

                alertDialog.setMessage("الرجاء التاكد من قيمة الشيكات" + "  ، يجب ان تكون اقل من" + String.valueOf(amount));
                alertDialog.setIcon(R.drawable.delete);

                alertDialog.setButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
                return;
            }
        }


        EditText OrderNo = (EditText) findViewById(R.id.et_OrdeNo);
        TextView tv_acc = (TextView) findViewById(R.id.tv_acc);
        String Msg = "";
        SqlHandler sqlHandler;
        sqlHandler = new SqlHandler(this);
        String q = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        q = "SELECT *  from  RecVoucher where   CustAcc  ='" + tv_acc.getText() + "'   AND   TrDate  ='" + currentDateandTime + "' " +
                " And   DocNo !='" + OrderNo.getText().toString() + "'";

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() > 0) {
            Msg = "يوجد سند قبض لهذا العميل في نفس هذا اليوم" + "\n\r";
            c1.close();

        }

        AlertDialog.Builder alert_Dialog = new AlertDialog.Builder(this);
        alert_Dialog.setTitle("سند القبض");
        alert_Dialog.setMessage(Msg + "  " + "هل  تريد الاستمرار بعملية الحفظ " + "؟");
        alert_Dialog.setIcon(R.drawable.save);
        alert_Dialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // User pressed YES button. Write Logic Here
                /*Toast.makeText(getApplicationContext(), "You clicked on YES",
                        Toast.LENGTH_SHORT).show();*/
                Save_Recod_Po();
            }
        });


        alert_Dialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });


        alert_Dialog.show();

    }
    }
    public void ShowRecord() {
        EditText OrderNo = (EditText) findViewById(R.id.et_OrdeNo);
        TextView amt = (TextView) findViewById(R.id.et_Amt);
        EditText etNote = (EditText) findViewById(R.id.et_notes);
        TextView et_Date = (TextView) findViewById(R.id.et_Date);
        TextView tv_acc = (TextView) findViewById(R.id.tv_acc);
        TextView tv_cusnm = (TextView) findViewById(R.id.tv_cusnm);
        Spinner sp_cur = (Spinner) findViewById(R.id.sp_cur);
        Spinner VouchType = (Spinner) findViewById(R.id.sp_VouchType);
        TextView CheckTotal = (TextView) findViewById(R.id.tv_CheckAmt);
        Cls_Cur_Adapter VouchType_adapter = (Cls_Cur_Adapter) VouchType.getAdapter();
        Cls_Cur obj = new Cls_Cur();

        Cls_Cur_Adapter sp_cur_adapter = (Cls_Cur_Adapter) sp_cur.getAdapter();

        String q = "Select  distinct rc.CheckTotal , rc.Cash, rc.Desc,rc.Amnt,rc.TrDate,rc.CustAcc  ,c.name , rc.curno  ,COALESCE(Post, -1)  as Post   ,rc.VouchType from RecVoucher rc   left join Customers c on c.no = rc.CustAcc  where rc.DocNo = '" + OrderNo.getText().toString().replaceAll("[^\\d.]", "")+ "'";
        SqlHandler sqlHandler = new SqlHandler(this);

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                etNote.setText(c1.getString(c1.getColumnIndex("Desc")));
                amt.setText(c1.getString(c1.getColumnIndex("Amnt")));
                et_Date.setText(c1.getString(c1.getColumnIndex("TrDate")));
                tv_acc.setText(c1.getString(c1.getColumnIndex("CustAcc")));
                tv_cusnm.setText(c1.getString(c1.getColumnIndex("name")));
                Cash.setText(c1.getString(c1.getColumnIndex("Cash")));
                CheckTotal.setText(c1.getString(c1.getColumnIndex("CheckTotal")));

                IsNew=false;
/*
                ImageButton btn_delete = (ImageButton)findViewById(R.id.imageButton6);
                ImageButton btn_add = (ImageButton)findViewById(R.id.imageButton3);
                ImageButton btn_share= (ImageButton)findViewById(R.id.imageButton9);

                if(c1.getString(c1.getColumnIndex("Post")).equals("-1")){
                    btn_delete.setEnabled(false);
                    btn_add.setEnabled(true);
                    btn_share.setEnabled(true);
                }
                else {
                    btn_delete.setEnabled(false);
                    btn_add.setEnabled(false);
                    btn_share.setEnabled(false);
                }*/
                for (int i = 0; i < VouchType_adapter.getCount(); i++) {
                    obj = (Cls_Cur) VouchType_adapter.getItem(i);
                    //  Toast.makeText(this,c1.getString(c1.getColumnIndex("VouchType")).toString(),Toast.LENGTH_SHORT).show();

                    if (obj.getNo().equals(c1.getString(c1.getColumnIndex("VouchType")).toString())) {
                        VouchType.setSelection(i);
                        break;
                    }
                }


                for (int i = 0; i < sp_cur_adapter.getCount(); i++) {
                    obj = (Cls_Cur) sp_cur_adapter.getItem(i);
                    //  Toast.makeText(this,c1.getString(c1.getColumnIndex("VouchType")).toString(),Toast.LENGTH_SHORT).show();

                    if (obj.getNo().equals(c1.getString(c1.getColumnIndex("curno")).toString())) {
                        sp_cur.setSelection(i);
                        break;
                    }
                }

                 /* indexValue = cls_cur_adapter.getp(c1.getString(c1.getColumnIndex("curno")));
                 Toast.makeText(this,indexValue.toString(),Toast.LENGTH_SHORT).show();*/
            }
            c1.close();
        }


        showList();
        CalcTotal();
    }
    private  Double SToD(String str){
        String f = "";
        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        final DecimalFormat df = (DecimalFormat)nf;
        str = str.replace(",","");
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
    public void Set_Order(String No) {
        TextView OrdeNo = (TextView) findViewById(R.id.et_OrdeNo);
        OrdeNo.setText(No);
        ShowRecord();
    }
    private void showList() {
        ListView listCheck = (ListView) findViewById(R.id.lstCheck);
        ChecklList.clear();
        listCheck.setAdapter(null);
        EditText DocNo = (EditText) findViewById(R.id.et_OrdeNo);

        sql_Handler = new SqlHandler(this);

        String query = "delete from t_RecCheck ";
        sql_Handler.executeQuery(query);


        query=" insert into  t_RecCheck (CheckNo,CheckDate,BankNo, Amnt )      " +
                "Select distinct  CheckNo,CheckDate,BankNo, Amnt   from  RecCheck    where DocNo ='" + DocNo.getText().toString().replaceAll("[^\\d.]", "") + "'";
        sql_Handler.executeQuery(query);


        query = "Select  distinct rc.CheckNo,rc.CheckDate,rc.BankNo, rc.Amnt  , b.Bank from  RecCheck rc  left join banks b on b.bank_num = rc.BankNo where DocNo ='" + DocNo.getText().toString().replaceAll("[^\\d.]", "") + "'";
        Integer i = 1;
        Cursor c1 = sql_Handler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Check cls_check_obj = new Cls_Check();
                    cls_check_obj.setSer(i);
                    cls_check_obj.setCheckNo(c1.getString(c1
                            .getColumnIndex("CheckNo")));
                    cls_check_obj.setCheckDate(c1.getString(c1
                            .getColumnIndex("CheckDate")));
                    cls_check_obj.setBankName(c1.getString(c1
                            .getColumnIndex("Bank")));
                    cls_check_obj.setBankNo(c1.getString(c1
                            .getColumnIndex("BankNo")));
                    cls_check_obj.setAmnt(c1.getString(c1
                            .getColumnIndex("Amnt")));

                    ChecklList.add(cls_check_obj);
                    i=i+1;
                } while (c1.moveToNext());


            }
        }


        c1.close();
        CheckAdapter checkAdapter = new CheckAdapter(
                Catch_Receipt.this, ChecklList);
        listCheck.setAdapter(checkAdapter);

        //  json = new Gson().toJson(contactList);
    }
    public void UpdateCheck() {
        ListView listCheck = (ListView) findViewById(R.id.lstCheck);
        ChecklList.clear();
        listCheck.setAdapter(null);
        EditText DocNo = (EditText) findViewById(R.id.et_OrdeNo);

        sql_Handler = new SqlHandler(this);



        String query = "Select distinct rc.CheckNo,rc.CheckDate,rc.BankNo, rc.Amnt  , b.Bank from  t_RecCheck rc  left join banks b on b.bank_num = rc.BankNo ";
        Integer i = 1;
        Cursor c1 = sql_Handler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Check cls_check_obj = new Cls_Check();
                    cls_check_obj.setSer(i);
                    cls_check_obj.setCheckNo(c1.getString(c1
                            .getColumnIndex("CheckNo")));
                    cls_check_obj.setCheckDate(c1.getString(c1
                            .getColumnIndex("CheckDate")));
                    cls_check_obj.setBankName(c1.getString(c1
                            .getColumnIndex("Bank")));
                    cls_check_obj.setBankNo(c1.getString(c1
                            .getColumnIndex("BankNo")));
                    cls_check_obj.setAmnt(c1.getString(c1
                            .getColumnIndex("Amnt")));

                    ChecklList.add(cls_check_obj);
                    i=i+1;
                } while (c1.moveToNext());


            }
            c1.close();
        }



        CheckAdapter checkAdapter = new CheckAdapter(
                Catch_Receipt.this, ChecklList);
        listCheck.setAdapter(checkAdapter);

        Cls_Check cls_check_obj = new Cls_Check();
        Double  sum = 0.0;
        for (int x = 0; x < ChecklList.size(); x++) {
            cls_check_obj = new Cls_Check();
            cls_check_obj = ChecklList.get(x);
            sum = sum + SToD(cls_check_obj.getAmnt());
        }
        TextView tv_CheckAmt = (TextView)findViewById(R.id.tv_CheckAmt);
        tv_CheckAmt.setText(sum + "");
        CalcTotal();

        //  json = new Gson().toJson(contactList);
    }
    public void save_Check(String CheckNo , String ChekAmt , Cls_Bank_Search bank ,String Check_Data ) {


        TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
        if (DocNo.getText().toString().length() == 0) {
            DocNo.setError("required!");
            DocNo.requestFocus();
            return;
        }


        ListView listCheck = (ListView) findViewById(R.id.lstCheck);
        Cls_Check cls_check_obj = new Cls_Check();
        cls_check_obj.setSer(Integer.valueOf(listCheck.getCount() + 1));
        cls_check_obj.setCheckNo(CheckNo);
        cls_check_obj.setCheckDate(Check_Data);
        cls_check_obj.setBankNo(bank.getNo().toString());
        cls_check_obj.setBankName(bank.getName().toString());
        cls_check_obj.setAmnt(ChekAmt);
        ChecklList.add(cls_check_obj);

        CheckAdapter checkAdapter = new CheckAdapter(
                Catch_Receipt.this, ChecklList);
        listCheck.setAdapter(checkAdapter);


        Double  sum = 0.0;
        for (int x = 0; x < ChecklList.size(); x++) {
            cls_check_obj = new Cls_Check();
            cls_check_obj = ChecklList.get(x);
            sum = sum + SToD(cls_check_obj.getAmnt());
        }
        TextView tv_CheckAmt = (TextView)findViewById(R.id.tv_CheckAmt);
        tv_CheckAmt.setText(sum + "");
        CalcTotal();
    }
    private  void CalcTotal(){
        TextView tv_CheckAmt = (TextView)findViewById(R.id.tv_CheckAmt);
        TextView et_Amt = (TextView)findViewById(R.id.et_Amt);
        TextView et_Cash = (TextView)findViewById(R.id.et_Cash);


        et_Amt.setText(String.valueOf( SToD(tv_CheckAmt.getText().toString().replaceAll("[^\\d.]", "")) + SToD(et_Cash.getText().toString().replaceAll("[^\\d.]", "")))  );

    }
    public void btn_save_Check(View view) {


        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);

        TextView acc = (TextView) findViewById(R.id.tv_acc);
        TextView CheckData = (TextView) findViewById(R.id.CheckData);
        TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);



        //TextView CheckBank = (TextView)findViewById(R.id.CheckBank);


        if (DocNo.getText().toString().length() == 0) {
            DocNo.setError("required!");
            DocNo.requestFocus();
            return;
        }



        if (CheckData.getText().toString().length() == 0) {
            CheckData.setError("required!");
            CheckData.requestFocus();
            return;
        }

      /*  if( CheckBank.getText().toString().length() == 0 ) {
            CheckBank.setError("required!");
            CheckBank.requestFocus();
            return;
        } */



        ListView listCheck = (ListView) findViewById(R.id.lstCheck);



        Cls_Check cls_check_obj = new Cls_Check();
        cls_check_obj.setSer(Integer.valueOf(listCheck.getCount() + 1));

        cls_check_obj.setCheckDate(CheckData.getText().toString());


        ChecklList.add(cls_check_obj);

        CheckAdapter checkAdapter = new CheckAdapter(
                Catch_Receipt.this, ChecklList);
        listCheck.setAdapter(checkAdapter);
        CheckData.setText("");



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());
        CheckData.setText(currentDateandTime);


    }
    public void btn_delete(View view) {
       /* AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("حذف سند القبض");

        // Setting Dialog Message
        alertDialog.setMessage("هل انت متاكد من عملية الحذف ..؟");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Delete_Record_PO();
                // Write your code here to invoke YES event
                // Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
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
        alertDialog.show();*/
    }
    public void Delete_Record_PO() {

        TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
        long i = 0 ;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setTitle("سند قبض");
        i=   sql_Handler.Delete("RecVoucher","DocNo='"+ DocNo.getText().toString()+"'");

        if ( i >0 ){
            i=   sql_Handler.Delete("RecCheck","DocNo='"+ DocNo.getText().toString()+"'");
            alertDialog.setMessage("تمت عملية الحذف بنجاح");
            alertDialog.setIcon(R.drawable.tick);
        }

        else
        {
            alertDialog.setMessage("عملية الحذف لم تتم بنجاح");
            alertDialog.setIcon(R.drawable.delete);
        }


        alertDialog.setButton("Cls_Country", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        // Showing Alert Message

        alertDialog.show();
        GetMaxRecNo();
        DoNew();
        showList();
    }
    public void btn_back(View view) {
        Intent k = new Intent(this, JalMasterActivity.class);
        startActivity(k);

    }
    public void btn_print(View view) {


        EditText OrdeNo = (EditText) findViewById(R.id.et_OrdeNo);




        InsertLogTrans obj=new InsertLogTrans(Catch_Receipt.this,SCR_NO , SCR_ACTIONS.Print.getValue(),et_OrdeNo.getText().toString(),tv_acc.getText().toString(),"","0");

        Intent k = new Intent(this, Convert_RecVouch_To_Img.class);
       //  OrdeNo = (TextView) findViewById(R.id.et_OrdeNo);

        String q1 = "Select * From RecVoucher Where DocNo='"+ OrdeNo.getText().toString().replaceAll("[^\\d.]", "") +"'";

        Cursor  c1 =sql_Handler.selectQuery(q1);

        if(c1!=null && c1.getCount()!=0){
            IsNew=false;
            c1.close();
        }else
        {
            IsNew= true;
        }


        if ( IsNew== true) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("سند قبض");
            alertDialog.setMessage("يجب تخزين سند القبض اولاَ");
            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
            return;
        }

        if (ComInfo.ComNo== Companies.Arabian.getValue()) {
            k = new Intent(this, Convert_RecVouch_To_Img_Tab10.class);
        }
        else  if (ComInfo.ComNo== Companies.goodsystem.getValue()) {
            k = new Intent(this, Convert_RecVouch_To_Img_GoodSystem.class);


        } else  if (ComInfo.ComNo== Companies.Ukrania.getValue()) {

            k = new Intent(this, Xprinter_RecVoucher.class);
            k.putExtra("OrderNo", OrdeNo.getText().toString().replaceAll("[^\\d.]", ""));
            startActivity(k);
        }  else  if (ComInfo.ComNo== Companies.beutyLine.getValue()) {
            k = new Intent(this, Convert_RecVouch_To_Img_Tab10.class);

        }
        else  if (ComInfo.ComNo== Companies.Afrah.getValue()) {
            k = new Intent(this, Xprinter_RecVoucher.class);

        }
        else {

            k = new Intent(this, Convert_RecVouch_To_Img.class);

        }
        k.putExtra("Scr", "Sale_Inv");
        k.putExtra("OrderNo", OrdeNo.getText().toString().replaceAll("[^\\d.]", ""));
        startActivity(k);
    //    btn_new(view);

    }
    public void btn_search_Recv(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "Rec");
        FragmentManager Manager = getFragmentManager();
        RecVoucherSearchActivity obj = new RecVoucherSearchActivity();
        obj.setArguments(bundle);
        obj.show(Manager, null);

    }
    public void btn_new(View view) {
        GetMaxRecNo();
        showList();
        DoNew();
    }
    public void btn_share(View view) {
        InsertLogTrans obj=new InsertLogTrans(Catch_Receipt.this,SCR_NO , SCR_ACTIONS.Share.getValue(),et_OrdeNo.getText().toString(),tv_acc.getText().toString(),"","0");

        if(IsNew==true){
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("سند قبض");
            alertDialog.setMessage("يجب تخزين سند القبض اولاَ");
            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
            return;
        }



        sql_Handler = new SqlHandler(this);
        EditText OrderNo = (EditText) findViewById(R.id.et_OrdeNo);

        loadingdialog = ProgressDialog.show(Catch_Receipt.this, "الرجاء الانتظار ...",  "العمل جاري على اعتماد سند القبض ...", true);
        loadingdialog.setCancelable(false);
        loadingdialog.setCanceledOnTouchOutside(false);
        loadingdialog.show();

        final String DocNo = OrderNo.getText().toString();
        final Handler _handler = new Handler();

        Calendar c = Calendar.getInstance();
        final int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        new Thread(new Runnable() {
            @Override
            public void run() {
                PostPayments obj = new PostPayments(Catch_Receipt.this);
                PostResult=  obj.Post_Payments(DocNo);
                try {

                    if (PostResult < 0) {
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        Catch_Receipt.this).create();
                                alertDialog.setTitle("اعتماد سند القبض ");
                                alertDialog.setMessage(" فشل في عملية الاعتماد" + PostResult + "");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                loadingdialog.dismiss();
                                alertDialog.show();
                            }
                        });


                    }else   if (PostResult > 0) {


                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        Catch_Receipt.this).create();
                                alertDialog.setTitle("اعتماد سند القبض");
                                alertDialog.setMessage("تمت عملية اعتماد سند القبض بنجاح");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                loadingdialog.dismiss();
                                alertDialog.show();
                                alertDialog.show();
                                //DoNew();
                                //GetMaxRecNo();
                                //showList();
                            }
                        });
                    } else {

                        loadingdialog.dismiss();
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        Catch_Receipt.this).create();
                                alertDialog.setTitle("فشل في عملية الاعتماد  " + "   " +We_Result.ID+"");
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
                                    Catch_Receipt.this).create();
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
    public void myClickHandler(View view) {
        lstView = (ListView) findViewById(R.id.lstCheck);
        int position =lstView.getPositionForView(view);
        ChecklList.remove(position);

        position =ChecklList.size();

        Cls_Check obj = new Cls_Check();
        for (int x = 0; x < position; x++) {
            obj = new Cls_Check();
            obj = ChecklList.get(x);
            ChecklList.remove(x);
            obj.setSer(x+1);
            ChecklList.add(x,obj);

        }
        CheckAdapter checkAdapter = new CheckAdapter(
                Catch_Receipt.this, ChecklList);

        lstView.setAdapter(checkAdapter);





    }
    public void btn_ShowPopAddCheck(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "po");

        FragmentManager Manager =  getFragmentManager();
        PopAddCheck2 obj = new PopAddCheck2();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }
    public void OpenCalculator(View view) {
/*

       Intent intent = new Intent();
      //  intent.setAction(Intent.ACTION_MAIN);
      //  intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(
                CALCULATOR_PACKAGE,
                CALCULATOR_CLASS));
        startActivity(intent);



        Intent intent = new Intent();
             intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setComponent(new ComponentName(CALCULATOR_PACKAGE,
                      CALCULATOR_CLASS));
            try {

                     this.startActivity(intent);
                 } catch (Exception noSuchActivity) {
                Toast.makeText(this ,noSuchActivity.getMessage().toString(),Toast.LENGTH_SHORT).show();
                     // handle exception where calculator intent filter is not registered
                  }
        */




      /*  Intent i = new Intent();
        i.setAction(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_APP_CALCULATOR);*/

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(
                CALCULATOR_PACKAGE,
                CALCULATOR_CLASS));
        try {

            startActivity(intent);
        } catch (Exception noSuchActivity) {
            Toast.makeText(this ,noSuchActivity.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }



    }
}
