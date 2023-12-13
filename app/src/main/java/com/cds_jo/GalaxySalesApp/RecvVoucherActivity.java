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
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.PostTransActions.PostPayments;
import com.cds_jo.GalaxySalesApp.XprinterDoc.Xprinter_RecVoucher;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.cds_jo.GalaxySalesApp.assist.CheckAdapter;

import com.cds_jo.GalaxySalesApp.assist.Cls_Check;
import com.cds_jo.GalaxySalesApp.assist.Cls_Cur;
import com.cds_jo.GalaxySalesApp.assist.Cls_Cur_Adapter;
import com.cds_jo.GalaxySalesApp.assist.Convert_RecVouch_To_Img;

import com.cds_jo.GalaxySalesApp.assist.Convert_RecVouch_To_Img_GoodSystem;
import com.cds_jo.GalaxySalesApp.assist.Convert_RecVouch_To_Img_Tab10;
import com.cds_jo.GalaxySalesApp.assist.Logtrans.InsertLogTrans;
import com.cds_jo.GalaxySalesApp.assist.OrdersItems;
import com.cds_jo.GalaxySalesApp.assist.PrintReport_TSC;
import com.cds_jo.GalaxySalesApp.assist.PrintReport_Zepra520;
import com.cds_jo.GalaxySalesApp.assist.Sale_InvoiceActivity;
import com.cds_jo.GalaxySalesApp.myelectomic.Pos_Ele_Activity;
import com.google.gson.Gson;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Methdes.MyTextView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import header.Header_Frag;

public class RecvVoucherActivity extends AppCompatActivity {
    ImageButton CustSearch;
    SqlHandler sql_Handler;
    ListView lstView;
    ListView listCheck1;
    CheckAdapter checkAdapter;
    TextView TrDate, CheckData;
    public ProgressDialog loadingdialog;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    public int FlgDate = 0;
    String UserID = "";
    TextView tv;
    double latitude ;
    double longitude ;
    int position;
    RelativeLayout.LayoutParams lp;
    String Celing, query;
    Drawable greenProgressbar;
    SharedPreferences sharedPreferences;
    CharSequence[] values = {"رمز تحقق ", "طلب موافقة المشرف"};
    ArrayList<Cls_Cur> VouchTypeList;
    long PostResult = 0;
    android.support.v7.app.AlertDialog alertDialog2;
    String SCR_NO = "11004";
    TextView tv_CustCelling;
    EditText et_OrdeNo;
    TextView tv_acc;
    String f, Result;
    ArrayList<Cls_Check> ChecklList;
    Boolean IsNew;
    EditText Cash, et_notes1;
    public static final String CALCULATOR_PACKAGE = "com.android.calculator2";
    public static final String CALCULATOR_CLASS = "com.android.calculator2.Calculator";


    public void setDate(View view) {
        showDialog(999);
       /* Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                .show();*/
    }

    public static String getMonthName(int month) {
        switch (month + 1) {
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
            return new DatePickerDialog(this, myDateListener, year, month, day);
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

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.view_recv_voucher_n);

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        tv_CustCelling = (TextView) findViewById(R.id.tv_CustCelling);
        listCheck1 = (ListView) findViewById(R.id.lstCheck);
        // et_notes1 = (EditText) findViewById(R.id.et_notes1);

        lstView = (ListView) findViewById(R.id.lstCheck);
        VouchTypeList = new ArrayList<Cls_Cur>();
        ChecklList = new ArrayList<Cls_Check>();
        ChecklList.clear();
        IsNew = true;
        GetLocation mGPSService = new GetLocation();
        Location l = mGPSService.CurrentLocation(RecvVoucherActivity.this);


        DeleteAllWeek();


         latitude = l.getLatitude();
         longitude = l.getLongitude();


        FillRecType();
        FillCur();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        UserID = sharedPreferences.getString("UserID", "");
        Cash = (EditText) findViewById(R.id.et_Cash);

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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecvVoucherActivity.this);
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


        final TextView et_Cash = (EditText) findViewById(R.id.et_Cash);
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
        et_OrdeNo = (EditText) findViewById(R.id.et_OrdeNo);
        tv_acc = (TextView) findViewById(R.id.tv_acc);
        InsertLogTrans obj = new InsertLogTrans(RecvVoucherActivity.this, SCR_NO, SCR_ACTIONS.open.getValue(), et_OrdeNo.getText().toString(), tv_acc.getText().toString(), "", "0");

        Fragment frag = new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1, frag).commit();
        CustAmtDt();
    }


    private void FillRecType() {

        final Spinner VouchType = (Spinner) findViewById(R.id.sp_VouchType);


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
            c1.close();
        }

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());


        TrDate = (TextView) findViewById(R.id.et_Date);
        TrDate.setText(currentDateandTime);

        TextView CheckDate = (TextView) findViewById(R.id.CheckData);
        CheckTotal.setText("0");
        Spinner VouchType = (Spinner) findViewById(R.id.sp_VouchType);
        Spinner cur = (Spinner) findViewById(R.id.sp_cur);
        VouchType.setSelection(0);
        cur.setSelection(0);

        IsNew = true;

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
        if (Login.toString().equals("No")) {
            Intent i = new Intent(this, NewLoginActivity.class);
            startActivity(i);
        }

        String u = sharedPreferences.getString("UserID", "").replaceAll("[^\\d.]", "");
        sql_Handler = new SqlHandler(this);
        String query = "SELECT  ifnull(MAX(DocNo), 0)  AS no FROM RecVoucher   where   UserID = '" + u.toString().replaceAll("[^\\d.]", "") + "'";
        Cursor c1 = sql_Handler.selectQuery(query);
        Integer max = 0;
        EditText Maxpo = (EditText) findViewById(R.id.et_OrdeNo);
        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            max = Integer.parseInt(c1.getString(c1.getColumnIndex("no")));
            c1.close();
        }
        Integer max1 = 0;
        String Payment_No;
        try {
            Payment_No = DB.GetValue(RecvVoucherActivity.this, "OrdersSitting", "Payment", "1=1");
            Payment_No = Payment_No.replaceAll("[^\\d.]", "");
            max1 = Integer.parseInt(Payment_No.toString());


        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            max1 = 0;
        }
        //max1 = sharedPreferences.getString("m2", "");
        if (max1 <= 0) {
            max1 = 0;
        }

        max1 = max1 + 1;
        if (max1 > max) {
            max = max1;
        }

        if (max.toString().length() == 1) {
            Maxpo.setText(intToString(Integer.valueOf(u), 2) + intToString(Integer.valueOf(max), 5));

        } else {

            Maxpo.setText(intToString(Integer.valueOf(max), 7));

        }
      SqlHandler  sqlHandler = new SqlHandler(this);
   //     query = "Update OrdersSitting SET Payment ='" + max + "'";
        sqlHandler.executeQuery(query);
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

    public void Set_Cust(final String No, String Nm, String IDN) {
        final TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        acc.setText(No);
        CustNm.setText(Nm);
        CustNm.setError(null);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("IDN", IDN);
        editor.commit();

    }

    public void Save_Recod_Po() {
        Location startPoint = new Location("locationA");
        startPoint.setLatitude(latitude);
        startPoint.setLongitude(longitude);

        String Cust_x = "";
        String Cust_y = "";
        Cust_x = DB.GetValue(this, "Customers_man", "Latitude", "IDN='" + sharedPreferences.getString("IDN", "") + "'");
        Cust_y = DB.GetValue(this, "Customers_man", "Longitude", "IDN='" + sharedPreferences.getString("IDN", "") + "'");
        if (Cust_x.equals("")) {
            Cust_x = "0";
        }
        if (Cust_y.equals("")) {
            Cust_y = "0";
        }
        //mohsss
         if (ComInfo.ComNo == Companies.diamond.getValue()) {
            Location endPoint = new Location("locationA");
            endPoint.setLatitude(Double.parseDouble(Cust_x));
            endPoint.setLongitude(Double.parseDouble(Cust_y));

            double distance = startPoint.distanceTo(endPoint);
            if (!(Cust_x.equals("0") || Cust_y.equals("0")))
                if (distance > 50) {
                    Toast.makeText(RecvVoucherActivity.this, "لا يمكن عمل سند قبض خارج نطاق العميل", Toast.LENGTH_LONG).show();
                    return;
                }
        }
        Integer Seq = 0;
        Integer MaxOrder = 0;
        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        TextView amt = (TextView) findViewById(R.id.et_Amt);
        TextView note = (TextView) findViewById(R.id.et_notes);
        TextView Date = (TextView) findViewById(R.id.et_Date);
        TextView CheckTotal = (TextView) findViewById(R.id.tv_CheckAmt);


        String query;

        MaxOrder = Integer.parseInt(DB.GetValue(this, "RecVoucher", "ifnull(count(*),0)", "DocNo='" + DocNo.getText().toString().replaceAll("[^\\d.]", "") + "'"));
        if (MaxOrder != 0) {
            int maxvalue = Integer.parseInt(DocNo.getText().toString()) + 1;
            DocNo.setText(String.valueOf(maxvalue));
        }
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
        Seq = Integer.parseInt(DB.GetValue(this, "RecVoucher", "ifnull(Max(Seq),0)+1", "TrDate='" + Date.getText().toString() + "'"));
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        String q1 = "Select * From RecVoucher Where DocNo='" + DocNo.getText().toString().replaceAll("[^\\d.]", "") + "'";
        Cursor c1;
        c1 = sql_Handler.selectQuery(q1);

        if (c1 != null && c1.getCount() != 0) {
            IsNew = false;
            c1.close();
        } else {
            IsNew = true;
        }


        if (IsNew == true) {
            Seq = Integer.parseInt(DB.GetValue(this, "RecVoucher", "ifnull(Max(Seq),0)+1", "TrDate='" + Date.getText().toString() + "'"));

        } else {
            Seq = Integer.parseInt(DB.GetValue(this, "RecVoucher", "ifnull(Seq,0)", "DocNo='" + DocNo.getText().toString() + "'"));

        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RecvVoucherActivity.this);
        ContentValues cv = new ContentValues();
        cv.put("DocNo", DocNo.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("CustAcc", acc.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("Amnt", amt.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("TrDate", Date.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("Desc", note.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("VouchType", v.getNo().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("curno", o.getNo().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("Cash", Cash.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("CheckTotal", CheckTotal.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("Post", "-1");
        cv.put("Seq", Seq.toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("UserID", sharedPreferences.getString("UserID", "").replace("\u202c", "").replace("\u202d", ""));
        cv.put("V_OrderNo", sharedPreferences.getString("V_OrderNo", "0").replace("\u202c", "").replace("\u202d", ""));
        cv.put("DayNum", dayOfWeek + "".replace("\u202c", "").replace("\u202d", ""));
        cv.put("FromSales", "0");
        //cv.put("NameDrawer",et_notes1.getText().toString());
        cv.put("IDN", sharedPreferences.getString("IDN", ""));
        long i;


        i = sql_Handler.Insert("RecVoucher", null, cv);


        if (i > 0) {

            for (int x = 0; x < ChecklList.size(); x++) {
                Cls_Check cls_check_obj = new Cls_Check();
                cls_check_obj = ChecklList.get(x);


                cv = new ContentValues();
                cv.put("DocNo", DocNo.getText().toString().replace("\u202c", "").replace("\u202d", ""));
                cv.put("CheckNo", cls_check_obj.getCheckNo().toString().replace("\u202c", "").replace("\u202d", ""));
                cv.put("CheckDate", cls_check_obj.getCheckDate().toString().replace("\u202c", "").replace("\u202d", ""));
                cv.put("BankNo", cls_check_obj.getBankNo().toString().replace("\u202c", "").replace("\u202d", ""));
                cv.put("Amnt", cls_check_obj.getAmnt().toString().replace("\u202c", "").replace("\u202d", ""));
                cv.put("NameDrawer", cls_check_obj.getNameDrawer().toString().replace("\u202c", "").replace("\u202d", ""));
                cv.put("UserID", sharedPreferences.getString("UserID", "").replace("\u202c", "").replace("\u202d", ""));

                i = sql_Handler.Insert("RecCheck", null, cv);
            }
        }


        //sql_Handler.executeQuery(query);

        final View view = null;
        alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setTitle("سند القبض");
        if (i > 0) {
            InsertLogTrans obj = new InsertLogTrans(RecvVoucherActivity.this, SCR_NO, SCR_ACTIONS.Insert.getValue(), et_OrdeNo.getText().toString(), tv_acc.getText().toString(), "", "0");

            alertDialog.setMessage(" تمت عملية الحفظ بنجاح");
            alertDialog.setIcon(R.drawable.tick);


            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Boolean ApproveInvoicesDirectly = Boolean.parseBoolean(DB.GetValue(RecvVoucherActivity.this, "manf", "ApproveInvoicesDirectly", "man ='" + sharedPreferences.getString("UserID", "0") + "'"));




           /* GetMaxRecNo();
            showList();
            DoNew();*/
           /* SharedPreferences.Editor editor    = sharedPreferences.edit();
            String Count = sharedPreferences.getString("PayCount", "0");
            Count = (SToD(Count)+1) + "";
            editor.putString("PayCount",Count);
            editor.commit();*/
            IsNew = false;
            CustAmtDt();
            if (ApproveInvoicesDirectly) {
                DoShare();
            }
        } else {
            alertDialog.setMessage("عملية الحفظ لم تتم ");
            alertDialog.setIcon(R.drawable.delete);
        }
        // Setting OK Button
        alertDialog.setButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //  btn_print(view);


            }
        });

        // Showing Alert Message
        alertDialog.show();


    }

    private void UpDateMaxOrderNo() {//

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
        } catch (Exception ex) {

        }
    }
    private void UpDateMaxOrderNo1() {//

        try {


            SqlHandler sqlHandler;
            sqlHandler = new SqlHandler(this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String u = sharedPreferences.getString("UserID", "");
            String query = "SELECT   Distinct  COALESCE(MAX( cast(DocNo as integer))-1, 0)   AS no " +
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
        } catch (Exception ex) {

        }
    }
    public void btn_save_po(View view) { // Save Rec
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
        String NumOfInvPerVisit = DB.GetValue(RecvVoucherActivity.this, "ComanyInfo", "NumOfPayPerVisit", "1=1");

        if (SToD(Count) >= SToD(NumOfInvPerVisit)) {
            alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("سند القبض");
            alertDialog.setMessage("يجب فتح جولة جديدة حتى تتمكن من تنفيذ هذ العملية");
            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setButton("نعم", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    UpDateMaxOrderNo();
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
                CheckCeling();
                // Save_Recod_Po();
            }
        });


        alert_Dialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });


        alert_Dialog.show();


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

        String q = "Select  distinct rc.CheckTotal , rc.Cash, rc.Desc,rc.Amnt,rc.TrDate,rc.CustAcc  ,rc.IDN , rc.curno  ,COALESCE(Post, -1)  as Post   ,rc.VouchType from RecVoucher rc   left join Customers c on c.no = rc.CustAcc  where rc.DocNo = '" + OrderNo.getText().toString().replaceAll("[^\\d.]", "") + "'";
        SqlHandler sqlHandler = new SqlHandler(this);

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                etNote.setText(c1.getString(c1.getColumnIndex("Desc")));
                amt.setText(c1.getString(c1.getColumnIndex("Amnt")));
                et_Date.setText(c1.getString(c1.getColumnIndex("TrDate")));
                tv_acc.setText(c1.getString(c1.getColumnIndex("CustAcc")));
                //  et_notes1.setText(c1.getString(c1.getColumnIndex("NameDrawer")));
                String sad = DB.GetValue(RecvVoucherActivity.this, "Customers_man", "name", "IDN='" + c1.getString(c1.getColumnIndex("IDN")) + "'");

                tv_cusnm.setText(sad);
                Cash.setText(c1.getString(c1.getColumnIndex("Cash")));
                CheckTotal.setText(c1.getString(c1.getColumnIndex("CheckTotal")));

                IsNew = false;
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


        query = " insert into  t_RecCheck (CheckNo,CheckDate,BankNo, Amnt )      " +
                "Select distinct  CheckNo,CheckDate,BankNo, Amnt   from  RecCheck    where DocNo ='" + DocNo.getText().toString().replaceAll("[^\\d.]", "") + "'";
        sql_Handler.executeQuery(query);


        query = "Select  distinct rc.CheckNo,rc.CheckDate,rc.BankNo, rc.Amnt  , b.Bank,ifnull(rc.NameDrawer,'') as NameDrawer from  RecCheck rc  left join banks b on b.bank_num = rc.BankNo where DocNo ='" + DocNo.getText().toString().replaceAll("[^\\d.]", "") + "'";
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

                    cls_check_obj.setNameDrawer(c1.getString(c1
                            .getColumnIndex("NameDrawer")));

                    ChecklList.add(cls_check_obj);
                    i = i + 1;
                } while (c1.moveToNext());


            }
        }


        c1.close();
         checkAdapter = new CheckAdapter(
                RecvVoucherActivity.this, ChecklList);
        listCheck.setAdapter(checkAdapter);

        //  json = new Gson().toJson(contactList);
    }


    public void btn_Delete_Item(final View view) {


            position = listCheck1.getPositionForView(view);
        ArrayList<Cls_Check> ChecklList2 = new ArrayList<Cls_Check>();

        ChecklList2.add(ChecklList.get(position));
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "po");
        bundle.putString("isnew", "0");
        bundle.putSerializable("List", ChecklList2);
        FragmentManager Manager = getFragmentManager();
        PopAddCheck2 obj = new PopAddCheck2();
        obj.setArguments(bundle);
        obj.show(Manager, null);

    }


    public void btn_Delete_Item1(final View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("حذف شيكات");

        // Setting Dialog Message
        alertDialog.setMessage("هل انت متاكد من عملية الحذف ..؟");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                position = listCheck1.getPositionForView(view);
                ChecklList.remove(position);
                checkAdapter.notifyDataSetChanged();
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

/*    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Cls_Sal_InvItems contactListItems =new Cls_Sal_InvItems();

        Toast.makeText(RecvVoucherActivity.this,String.valueOf(525),Toast.LENGTH_SHORT).show();

        menu.setHeaderTitle("أختيار");
        menu.add(Menu.NONE, 1, Menu.NONE, "تعديل");
        menu.add(Menu.NONE, 2, Menu.NONE, "حذف");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 1: {
                ChecklList.remove(position);
                checkAdapter.notifyDataSetChanged();
                break;



            }

            case 2: {


                ChecklList.remove(position);
                checkAdapter.notifyDataSetChanged();
                break;

            }}

        return super.onContextItemSelected(item);
    }*/
    public void UpdateCheck() {
        ListView listCheck = (ListView) findViewById(R.id.lstCheck);
        ChecklList.clear();
        listCheck.setAdapter(null);
        EditText DocNo = (EditText) findViewById(R.id.et_OrdeNo);

        sql_Handler = new SqlHandler(this);


        String query = "Select distinct rc.CheckNo,rc.CheckDate,rc.BankNo, rc.Amnt  , b.Bank,ifnull(rc.NameDrawer,'') as NameDrawer from  t_RecCheck rc  left join banks b on b.bank_num = rc.BankNo ";
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
                    cls_check_obj.setNameDrawer(c1.getString(c1
                            .getColumnIndex("NameDrawer")));

                    ChecklList.add(cls_check_obj);
                    i = i + 1;
                } while (c1.moveToNext());


            }
            c1.close();
        }


         checkAdapter = new CheckAdapter(
                RecvVoucherActivity.this, ChecklList);
        listCheck.setAdapter(checkAdapter);

        Cls_Check cls_check_obj = new Cls_Check();
        Double sum = 0.0;
        for (int x = 0; x < ChecklList.size(); x++) {
            cls_check_obj = new Cls_Check();
            cls_check_obj = ChecklList.get(x);
            sum = sum + SToD(cls_check_obj.getAmnt());
        }
        TextView tv_CheckAmt = (TextView) findViewById(R.id.tv_CheckAmt);
        tv_CheckAmt.setText(sum + "");
        CalcTotal();

        //  json = new Gson().toJson(contactList);
    }

    public void save_Check(String CheckNo, String ChekAmt, Cls_Bank_Search bank, String Check_Data, String NameDrawer,String isnew) {


        TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
        if (DocNo.getText().toString().length() == 0) {
            DocNo.setError("required!");
            DocNo.requestFocus();
            return;
        }

        Cls_Check cls_check_obj = new Cls_Check();
        ListView listCheck = (ListView) findViewById(R.id.lstCheck);
        if (isnew.equals("1"))
        {

            cls_check_obj.setSer(Integer.valueOf(listCheck.getCount() + 1));
            cls_check_obj.setCheckNo(CheckNo);
            cls_check_obj.setCheckDate(Check_Data);
            cls_check_obj.setBankNo(bank.getNo().toString());
            cls_check_obj.setBankName(bank.getName().toString());
            cls_check_obj.setAmnt(ChekAmt);
            cls_check_obj.setNameDrawer(NameDrawer);
            ChecklList.add(cls_check_obj);

            checkAdapter = new CheckAdapter(
                    RecvVoucherActivity.this, ChecklList);
            listCheck.setAdapter(checkAdapter);
        }
        else
        {

            cls_check_obj = ChecklList.get(position);

            cls_check_obj.setCheckNo(CheckNo);
            cls_check_obj.setCheckDate(Check_Data);
            cls_check_obj.setBankNo(bank.getNo().toString());
            cls_check_obj.setBankName(bank.getName().toString());
            cls_check_obj.setAmnt(ChekAmt);
            cls_check_obj.setNameDrawer(NameDrawer);
            listCheck.setAdapter(null);
            checkAdapter = new CheckAdapter(
                    RecvVoucherActivity.this, ChecklList);
            listCheck.setAdapter(checkAdapter);
        }

        Double sum = 0.0;
        for (int x = 0; x < ChecklList.size(); x++) {
            cls_check_obj = new Cls_Check();
            cls_check_obj = ChecklList.get(x);
            sum = sum + SToD(cls_check_obj.getAmnt());
        }
        TextView tv_CheckAmt = (TextView) findViewById(R.id.tv_CheckAmt);
        tv_CheckAmt.setText(sum + "");
        CalcTotal();
    }

    private void CalcTotal() {
        TextView tv_CheckAmt = (TextView) findViewById(R.id.tv_CheckAmt);
        TextView et_Amt = (TextView) findViewById(R.id.et_Amt);
        TextView et_Cash = (TextView) findViewById(R.id.et_Cash);


        et_Amt.setText(String.valueOf(SToD(tv_CheckAmt.getText().toString().replaceAll("[^\\d.]", "")) + SToD(et_Cash.getText().toString().replaceAll("[^\\d.]", ""))));

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

         checkAdapter = new CheckAdapter(
                RecvVoucherActivity.this, ChecklList);
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

        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        sql_Handler = new SqlHandler(this);

        String q = "SELECT Distinct *  from  RecVoucher where   post > '0' AND   DocNo ='" + pono.getText().toString() + "'";
        Cursor c1 = sql_Handler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("سند القبض");
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


        Delete_Record_PO();
    }

    public void Delete_Record_PO() {

        TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
        long i = 0;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setTitle("سند قبض");
        i = sql_Handler.Delete("RecVoucher", "DocNo='" + DocNo.getText().toString() + "'");

        if (i > 0) {

            i = sql_Handler.Delete("RecCheck", "DocNo='" + DocNo.getText().toString() + "'");
            alertDialog.setMessage("تمت عملية الحذف بنجاح");
            alertDialog.setIcon(R.drawable.tick);
            UpDateMaxOrderNo();
        } else {
            alertDialog.setMessage("عملية الحذف لم تتم بنجاح");
            alertDialog.setIcon(R.drawable.delete);
        }


        alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
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
        TextView OrdeNo = (TextView) findViewById(R.id.et_OrdeNo);
        String q3 = "SELECT  *  from  RecVoucher where" +
                "   Post <=0  AND   DocNo ='" + OrdeNo.getText().toString().replaceAll("[^\\d.]", "") + "'";

        Cursor c2 = sql_Handler.selectQuery(q3);
   /*     if (c2 != null && c2.getCount() != 0) {
            new SweetAlertDialog(RecvVoucherActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("سند القبض")
                    .setContentText("لا يمكن الطباعة الا بعد الاعتماد")
                    .setCustomImage(R.drawable.error_new)
                    .show();

            c2.close();
            return;
        }*/
        InsertLogTrans obj = new InsertLogTrans(RecvVoucherActivity.this, SCR_NO, SCR_ACTIONS.Print.getValue(), et_OrdeNo.getText().toString(), tv_acc.getText().toString(), "", "0");

        Intent k = new Intent(this, Convert_RecVouch_To_Img.class);


        String q1 = "Select * From RecVoucher Where DocNo='" + OrdeNo.getText().toString().replaceAll("[^\\d.]", "") + "'";
        Cursor c1;
        c1 = sql_Handler.selectQuery(q1);

        if (c1 != null && c1.getCount() != 0) {
            IsNew = false;
            c1.close();
        } else {
            IsNew = true;
        }


    /*    if (IsNew == true) {
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
        }*/


            k = new Intent(this, Convert_RecVouch_To_Img.class);


        k.putExtra("Scr", "Sale_Inv");
        k.putExtra("OrderNo", OrdeNo.getText().toString().replaceAll("[^\\d.]", ""));
        k.putExtra("IDN", DB.GetValue(RecvVoucherActivity.this, "RecVoucher", "IDN", "DocNo='" + OrdeNo.getText().toString().replaceAll("[^\\d.]", "") + "'"));
        startActivity(k);
        btn_new(view);

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



      //  DeleteAllWeek();
    }


    private void DoShare2() {
        InsertLogTrans obj = new InsertLogTrans(RecvVoucherActivity.this, SCR_NO, SCR_ACTIONS.Share.getValue(), et_OrdeNo.getText().toString(), tv_acc.getText().toString(), "", "0");


        sql_Handler = new SqlHandler(this);
        EditText OrderNo = (EditText) findViewById(R.id.et_OrdeNo);

        loadingdialog = ProgressDialog.show(RecvVoucherActivity.this, "الرجاء الانتظار ...", "العمل جاري على اعتماد سند القبض ...", true);
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
                PostPayments obj = new PostPayments(RecvVoucherActivity.this);
                PostResult = obj.Post_Payments(DocNo);
                try {

                    if (PostResult < 0) {
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        RecvVoucherActivity.this).create();
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


                    } else if (PostResult > 0) {


                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        RecvVoucherActivity.this).create();
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
                                updateCustomer();
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
                                        RecvVoucherActivity.this).create();
                                alertDialog.setTitle("فشل في عملية الاعتماد  " + "   " + We_Result.ID + "");
                                alertDialog.setMessage(We_Result.Msg.toString());
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                alertDialog.show();

                                alertDialog.setIcon(R.drawable.delete);
                                alertDialog.setMessage("عملية الاعتماد لم تتم بنجاح" + "    ");
                            }
                        });
                    }

                } catch (final Exception e) {
                    loadingdialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    RecvVoucherActivity.this).create();
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
    private void DoShare() {
        InsertLogTrans obj = new InsertLogTrans(RecvVoucherActivity.this, SCR_NO, SCR_ACTIONS.Share.getValue(), et_OrdeNo.getText().toString(), tv_acc.getText().toString(), "", "0");

        if (IsNew == true) {
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

        loadingdialog = ProgressDialog.show(RecvVoucherActivity.this, "الرجاء الانتظار ...", "العمل جاري على اعتماد سند القبض ...", true);
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
                PostPayments obj = new PostPayments(RecvVoucherActivity.this);
                PostResult = obj.Post_Payments(DocNo);
                try {

                    if (PostResult < 0) {
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        RecvVoucherActivity.this).create();
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


                    } else if (PostResult > 0) {


                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        RecvVoucherActivity.this).create();
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
                                updateCustomer();
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
                                        RecvVoucherActivity.this).create();
                                alertDialog.setTitle("فشل في عملية الاعتماد  " + "   " + We_Result.ID + "");
                                alertDialog.setMessage(We_Result.Msg.toString());
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                alertDialog.show();

                                alertDialog.setIcon(R.drawable.delete);
                                alertDialog.setMessage("عملية الاعتماد لم تتم بنجاح" + "    ");
                            }
                        });
                    }

                } catch (final Exception e) {
                    loadingdialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    RecvVoucherActivity.this).create();
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

    public void btn_share(View view) {
        InsertLogTrans obj = new InsertLogTrans(RecvVoucherActivity.this, SCR_NO, SCR_ACTIONS.Share.getValue(), et_OrdeNo.getText().toString(), tv_acc.getText().toString(), "", "0");

        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        sql_Handler = new SqlHandler(this);

        String q = "SELECT Distinct *  from  RecVoucher where   post > '0' AND   DocNo ='" + pono.getText().toString() + "'";
        Cursor c1 = sql_Handler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("سند القبض");
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
        if (IsNew == true) {
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

        loadingdialog = ProgressDialog.show(RecvVoucherActivity.this, "الرجاء الانتظار ...", "العمل جاري على اعتماد سند القبض ...", true);
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
                PostPayments obj = new PostPayments(RecvVoucherActivity.this);
                PostResult = obj.Post_Payments(DocNo);
                try {

                    if (PostResult < 0) {
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        RecvVoucherActivity.this).create();
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


                    } else if (PostResult > 0) {


                        _handler.post(new Runnable() {
                            public void run() {
                                UpDateMaxOrderNo();
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        RecvVoucherActivity.this).create();
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
                                updateCustomer();
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
                                        RecvVoucherActivity.this).create();
                                alertDialog.setTitle("فشل في عملية الاعتماد  " + "   " + We_Result.ID + "");
                                alertDialog.setMessage(We_Result.Msg.toString());
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                alertDialog.show();

                                alertDialog.setIcon(R.drawable.delete);
                                alertDialog.setMessage("عملية الاعتماد لم تتم بنجاح" + "    ");
                            }
                        });
                    }

                } catch (final Exception e) {
                    loadingdialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    RecvVoucherActivity.this).create();
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
        int position = lstView.getPositionForView(view);
        ChecklList.remove(position);

        position = ChecklList.size();

        Cls_Check obj = new Cls_Check();
        for (int x = 0; x < position; x++) {
            obj = new Cls_Check();
            obj = ChecklList.get(x);
            ChecklList.remove(x);
            obj.setSer(x + 1);
            ChecklList.add(x, obj);

        }
         checkAdapter = new CheckAdapter(
                RecvVoucherActivity.this, ChecklList);

        lstView.setAdapter(checkAdapter);


    }

    public void btn_ShowPopAddCheck(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "po");
        bundle.putString("isnew", "1");

        FragmentManager Manager = getFragmentManager();
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
            Toast.makeText(this, noSuchActivity.getMessage().toString(), Toast.LENGTH_SHORT).show();

        }


    }

    private void CustAmtDt() {
        TextView accno = (TextView) findViewById(R.id.tv_acc);
        EditText Maxpo = (EditText) findViewById(R.id.et_OrdeNo);
        TextView tv_NetTotal = (TextView) findViewById(R.id.tv_CustNetTotal);
        //  TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        String NetTotal = DB.GetValue(RecvVoucherActivity.this, "Customers", "CUST_NET_BAL", "no ='" + accno.getText() + "' ");
        sql_Handler = new SqlHandler(this);
        String CustCelling = DB.GetValue(RecvVoucherActivity.this, "Customers", "Chqceling", "no ='" + accno.getText() + "' ");
        String CUST_NET_BAL = NetTotal;

        if (CustCelling.equalsIgnoreCase("-1")) {
            CustCelling = "0";
        }

        if (CUST_NET_BAL.equalsIgnoreCase("-1")) {
            CUST_NET_BAL = "0";
        }






    /*    if (NetTotal.equalsIgnoreCase("-1")) {
            NetTotal = "غير مدخله";
        }
        tv_NetTotal.setText(NetTotal + "");*/

        Double Total2 = Double.parseDouble(DB.GetValue(this, "Customers", "BB_Chaq", "no='" + accno.getText() + "'"));
        String q;


        q = "  Select distinct    ifnull( sum(ifnull(RecVoucher.CheckTotal,0.000)),0.000)     as Amt   from RecVoucher " +
                " where  RecVoucher.CustAcc ='" + accno.getText() + "' and  RecVoucher.Post ='-1'";

        String UnpostedRecVoucher = "0.000";
        ;
        Cursor c1 = sql_Handler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                UnpostedRecVoucher = c1.getString(c1.getColumnIndex("Amt"));

            if(Double.parseDouble(UnpostedRecVoucher)<0)
                UnpostedRecVoucher = "0.00";
            }
            c1.close();
        }

        if(Total2<0)
            Total2 = 0.00;


        tv_NetTotal.setText((Total2 + Double.parseDouble(UnpostedRecVoucher)) + "");

//moh123
        //  Double Total = SToD(CustCelling) - SToD(CUST_NET_BAL)+SToD(UnpostedRecVoucher )- SToD(UnpostedSales) ;
        Double Total = Double.parseDouble(DB.GetValue(this, "Customers", "Chqceling", "no='" + accno.getText() + "'"));
        if (Total < 0)
            Total = 0.0; // Total * -1;

        tv_CustCelling.setText(SToD(Total + "") + "");


    }

    public void CreateAlertDialogWithRadioButtonGroup(final String amt) {


        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(RecvVoucherActivity.this);

        builder.setTitle("الرجاء اختيار طريقة تفعيل  الموافقة على سقف التسهيلات ");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch (item) {
                    case 0:
                        CheckCelingOld(amt);

                        break;
                    case 1:
                        CheckCelingNew(amt);

                        break;

                }
                alertDialog2.dismiss();
            }
        });
        alertDialog2 = builder.create();

        alertDialog2.show();

    }

    private void Get_RequestPermission() {


        TextView acc = (TextView) findViewById(R.id.tv_acc);
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        ContentValues cv = new ContentValues();


        final String ManNo, CustNo, OrderNo, Desc, Type;

        ManNo = UserID;
        CustNo = acc.getText().toString();
        OrderNo = pono.getText().toString();

        Type = "1";

        greenProgressbar = this.getResources().getDrawable(R.drawable.progrees_states);
        final Handler _handler = new Handler();
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
        progressDialog = new ProgressDialog(RecvVoucherActivity.this);
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setMessage("  الرجاء الانتظار ..." + "  العمل جاري على نسخ البيانات  ");
        tv.setText("طلب تفعيل سقف التشهيلات");
        progressDialog.setCustomTitle(tv);
        progressDialog.setProgressDrawable(greenProgressbar);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(RecvVoucherActivity.this);
                ws.Get_Request_Permission(ManNo, CustNo, OrderNo, "1");
                try {
                    Integer i;
                    String q;
                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray js_Status = js.getJSONArray("Status");

                    Result = js_Status.get(0).toString();


                    progressDialog.setMax(1);
                    progressDialog.incrementProgressBy(1);
                    if (progressDialog.getProgress() == progressDialog.getMax()) {

                        progressDialog.dismiss();
                    }


                    _handler.post(new Runnable() {
                        public void run() {

                            if (Result.equalsIgnoreCase("0")) {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        RecvVoucherActivity.this).create();
                                alertDialog.setTitle(" طلب الموافقة على سقف التسهيلات");
                                alertDialog.setMessage(We_Result.Msg.toString());
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                alertDialog.show();
                                alertDialog.setIcon(R.drawable.delete);
                                alertDialog.setMessage(" الطلب تحت المعالجة");

                            } else if (Result.equalsIgnoreCase("1")) {

                                query = "Update PermissionCode set Status =1   Where   ManNo='" + ManNo + "' and CustNo ='" + CustNo + "' " +
                                        "And OrderNo ='" + OrderNo + "'";
                                sql_Handler.executeQuery(query);
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        RecvVoucherActivity.this).create();
                                alertDialog.setTitle(" طلب الموافقة على سقف التسهيلات");
                                alertDialog.setMessage(We_Result.Msg.toString());
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Save_Recod_Po();
                                    }
                                });


                                alertDialog.show();
                                alertDialog.setIcon(R.drawable.delete);
                                alertDialog.setMessage("تمت الموافقة على الطلب" + "    ");


                            } else if (Result.equalsIgnoreCase("2")) {
                                query = "Update PermissionCode set Status =2   Where   ManNo='" + ManNo + "' and CustNo ='" + CustNo + "' " +
                                        "And OrderNo ='" + OrderNo + "'";
                                sql_Handler.executeQuery(query);

                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        RecvVoucherActivity.this).create();
                                alertDialog.setTitle(" طلب الموافقة على سقف التسهيلات");
                                alertDialog.setMessage(We_Result.Msg.toString());
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                alertDialog.show();
                                alertDialog.setIcon(R.drawable.delete);
                                alertDialog.setMessage("لم تتم الموافقة");
                            }

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

    private void CheckCelingNew(String Amt) {
        Long i;
        final SqlHandler sql_Handler = new SqlHandler(this);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        ContentValues cv = new ContentValues();
        TextView NetTotal = (TextView) findViewById(R.id.et_Amt);
        String t = "";
        String Order_Status = "";
        final String ManNo, CustNo, OrderNo, Desc, Type;

        String q1 = "Select     ifnull(Status,-1) as  Status  From PermissionCode Where  Type='1' AND OrderNo='" + pono.getText().toString() + "'   Order by no desc limit 1";
        Cursor c1;
        c1 = sql_Handler.selectQuery(q1);

        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            Order_Status = c1.getString(c1.getColumnIndex("Status"));
            c1.close();
        } else {
            Order_Status = "-1";
        }

        if (Order_Status.equalsIgnoreCase("0")) {

            //  Toast.makeText(this,"00000",Toast.LENGTH_SHORT).show();
            Get_RequestPermission();
            return;

        } else if (Order_Status.equalsIgnoreCase("1")) {
            Save_Recod_Po();
            // Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
            return;

        }

      /*  else if(Order_Status.equalsIgnoreCase("2")){

            Toast.makeText(this,"222",Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this," Newwwwwwww",Toast.LENGTH_SHORT).show();
        }

*/


        String UserNm = DB.GetValue(this, "manf", "name", "man='" + UserID + "'");//    "SELECT  name   from  manf where man = '" + email.toString() + "' And password='" + password.toString() + "'";


        ManNo = UserID;
        CustNo = acc.getText().toString();
        OrderNo = pono.getText().toString();
        t = " اسم المندوب : ";
        t = t + UserNm;
        t = t + " اسم العميل : ";
        t = t + custNm.getText().toString();
        t = t + "  رقم الفاتورة : ";
        t = t + OrderNo;
        t = t + "    مبلغ الفاتورة : ";
        t = t + NetTotal.getText().toString();

        Desc = t;
        Type = "1";
        cv.put("ManNo", ManNo);
        cv.put("CustNo", CustNo);
        cv.put("OrderNo", OrderNo);
        cv.put("Status", "0");
        cv.put("Desc", Desc);
        cv.put("Type", Type);
        i = sql_Handler.Insert("PermissionCode", null, cv);


        if (i > 0) {


            loadingdialog = ProgressDialog.show(RecvVoucherActivity.this, "الرجاء الانتظار ...", "العمل جاري على طلب تفعيل سقف التسهيلات", true);
            loadingdialog.setCancelable(false);
            loadingdialog.setCanceledOnTouchOutside(false);
            loadingdialog.show();
            final Handler _handler = new Handler();

            new Thread(new Runnable() {
                @Override
                public void run() {

                    CallWebServices ws = new CallWebServices(RecvVoucherActivity.this);
                    ws.SendRequestPermission(ManNo, CustNo, OrderNo, Desc, "1", "1");
                    try {

                        if (We_Result.ID > 0) {
                            ContentValues cv = new ContentValues();
                            TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
                            cv.put("Post", We_Result.ID);
                            long i;
                            // i = sql_Handler.Update("PermissionCode", cv, "OrderNo='"+ DocNo.getText().toString()+"'");

                            _handler.post(new Runnable() {
                                public void run() {
                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            RecvVoucherActivity.this).create();
                                    alertDialog.setTitle("طلب تفعيل سقف التسهيلات");
                                    alertDialog.setMessage("تمت عملية طلب تفعيل سقف التسهيلات بنجاح رقم الحركة هو :" + We_Result.ID);
                                    alertDialog.setIcon(R.drawable.tick);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {


                                        }
                                    });
                                    loadingdialog.dismiss();
                                    alertDialog.show();


                                }
                            });
                        } else {

                            loadingdialog.dismiss();
                            _handler.post(new Runnable() {
                                public void run() {
                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            RecvVoucherActivity.this).create();
                                    alertDialog.setTitle("فشل في العملية  " + "   " + We_Result.ID + "");
                                    alertDialog.setMessage(We_Result.Msg.toString());
                                    alertDialog.setIcon(R.drawable.tick);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    alertDialog.show();
                                    alertDialog.setIcon(R.drawable.delete);
                                    alertDialog.setMessage("طلب تفعيل سقف التسهيلات لم يتم بنجاح" + "    ");
                                }
                            });
                        }

                    } catch (final Exception e) {
                        loadingdialog.dismiss();
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        RecvVoucherActivity.this).create();
                                alertDialog.setTitle("فشل في عمليه الاتصال");
                                alertDialog.setMessage(e.getMessage().toString());
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
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

    private void CheckCelingOld(String Amt) {

        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        String q;


        String Man = DB.GetValue(RecvVoucherActivity.this, "Tab_Password", "ManNo", "PassNo = 8 AND ManNo ='" + UserID + "'");
        final String pass = DB.GetValue(RecvVoucherActivity.this, "Tab_Password", "Password", "PassNo =8 and ManNo='" + Man + "'");


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecvVoucherActivity.this);
        alertDialog.setTitle("رمز التحقق ");
        alertDialog.setMessage("لقد تجاوزت سقف التسهيلات للعميل ، سقف تسهيلات العميل هو " + Amt);

        final EditText input = new EditText(RecvVoucherActivity.this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setTransformationMethod(new PasswordTransformationMethod());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.key);

        alertDialog.setPositiveButton("موافق",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String password = input.getText().toString();

                        if (pass.equals(password)) {
                            Save_Recod_Po();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "رمز التحقق غير صحيح", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

        alertDialog.setNegativeButton("لا",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();


    }

    private void CheckCeling() {
        Spinner VouchType = (Spinner) findViewById(R.id.sp_VouchType);
        Integer index = VouchType.getSelectedItemPosition();
        Cls_Cur v = (Cls_Cur) VouchType.getItemAtPosition(index);

        if (v.getNo().equals("1")) {
            Save_Recod_Po();
        } else {

            TextView NetTotal = (TextView) findViewById(R.id.tv_CheckAmt);
            TextView tv_CustNetTotal = (TextView) findViewById(R.id.tv_CustNetTotal);
            TextView acc = (TextView) findViewById(R.id.tv_acc);
            String q;
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();


            Celing = "0";
            q = " select distinct  ifnull(Chqceling,0) as Chqceling  from Customers where no='" + acc.getText().toString() + "'";
            Cursor c1 = sql_Handler.selectQuery(q);
            if (c1 != null && c1.getCount() != 0) {
                c1.moveToFirst();
                f = c1.getString(c1.getColumnIndex("Chqceling"));

                // if ((SToD(c1.getString(c1.getColumnIndex("Celing"))) - SToD(tv_NetTotal.getText().toString())) < SToD(NetTotal.getText().toString())) {
                // if ( SToD(tv_CustCelling.getText().toString()) < SToD(NetTotal.getText().toString())) {
                if (tv_CustCelling.getText().toString().equals("0.0")) {
                    Celing = "0";
                } else {
                    if (SToD(tv_CustCelling.getText().toString()) < (SToD(tv_CustNetTotal.getText().toString()) + SToD(NetTotal.getText().toString()))) {
                        Celing = "1";
                    } else {
                        Celing = "0";
                    }

                }
                c1.close();
            }


            if (Celing.equalsIgnoreCase("1")) {
                   /*  if (ComInfo.ComNo == 1) {
                          CheckCelingNew( f);

                     } else {
                         CheckCelingOld(f);
                     }*/
                CreateAlertDialogWithRadioButtonGroup(f);
            } else {
                Save_Recod_Po();
            }
        }

    }

    public void updateCustomer() {
        final Handler _handler = new Handler();


        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(RecvVoucherActivity.this);
                ws.GetCustomers(UserID);
                try {
                    Integer i;
                    String q = "";
                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray js_no = js.getJSONArray("no");
                    JSONArray js_name = js.getJSONArray("name");
                    JSONArray js_Ename = js.getJSONArray("Ename");
                    JSONArray js_barCode = js.getJSONArray("barCode");
                    JSONArray js_Address = js.getJSONArray("Address");
                    JSONArray js_State = js.getJSONArray("State");
                    JSONArray js_SMan = js.getJSONArray("SMan");
                    JSONArray js_Latitude = js.getJSONArray("Latitude");
                    JSONArray js_Longitude = js.getJSONArray("Longitude");
                    JSONArray js_Note2 = js.getJSONArray("Note2");
                    JSONArray js_sat = js.getJSONArray("sat");

                    JSONArray js_sun = js.getJSONArray("sun");
                    JSONArray js_mon = js.getJSONArray("mon");
                    JSONArray js_tues = js.getJSONArray("tues");
                    JSONArray js_wens = js.getJSONArray("wens");
                    JSONArray js_thurs = js.getJSONArray("thurs");
                    JSONArray js_sat1 = js.getJSONArray("sat1");
                    JSONArray js_sun1 = js.getJSONArray("sun1");
                    JSONArray js_mon1 = js.getJSONArray("mon1");
                    JSONArray js_tues1 = js.getJSONArray("tues1");
                    JSONArray js_wens1 = js.getJSONArray("wens1");
                    JSONArray js_thurs1 = js.getJSONArray("thurs1");
                    JSONArray js_Celing = js.getJSONArray("Celing");
                    JSONArray js_CatNo = js.getJSONArray("CatNo");
                    JSONArray js_CustType = js.getJSONArray("CustType");
                    JSONArray js_PAMENT_PERIOD_NO = js.getJSONArray("PAMENT_PERIOD_NO");
                    JSONArray js_CUST_PRV_MONTH = js.getJSONArray("CUST_PRV_MONTH");
                    JSONArray js_CUST_NET_BAL = js.getJSONArray("CUST_NET_BAL");
                    JSONArray js_Pay_How = js.getJSONArray("Pay_How");
                    JSONArray js_Cust_type = js.getJSONArray("Cust_type");


                    JSONArray js_TaxStatus = js.getJSONArray("TaxSts");
                    JSONArray js_country_Nm = js.getJSONArray("country_Nm");
                    JSONArray js_country_No = js.getJSONArray("country_No");
                    JSONArray js_CheckAlowedDay = js.getJSONArray("CheckAlowedDay");
                    JSONArray js_PromotionFlag = js.getJSONArray("PromotionFlag");
                    JSONArray js_CloseVisitWithoutimg = js.getJSONArray("CloseVisitWithoutimg");
                    JSONArray Chqceling = js.getJSONArray("Chqceling");
                    JSONArray BB_bill = js.getJSONArray("BB");
                    JSONArray BB_Chaq = js.getJSONArray("BB_Chaq");


                    q = "Delete from Customers";
                    sql_Handler.executeQuery(q);
                    q = "delete from sqlite_sequence where name='Customers'";
                    sql_Handler.executeQuery(q);

                    for (i = 0; i < js_no.length(); i++) {
                        q = "Insert INTO Customers(Tax_Status,no,name,Ename,barCode,Address,State,SMan,Latitude,Longitude,Note2,sat " +
                                " ,sun,mon,tues,wens,thurs,sat1,sun1,mon1,tues1,wens1,thurs1 , Celing , CatNo " +
                                ",CustType,PAMENT_PERIOD_NO , CUST_PRV_MONTH,CUST_NET_BAL,Pay_How,Cust_type,LocationNo,Location,CheckAlowedDay,PromotionFlag,CloseVisitWithoutimg,BB_Chaq,BB_bill,Chqceling) values ('"
                                + js_TaxStatus.get(i).toString()
                                + "','" + js_no.get(i).toString()
                                + "','" + js_name.get(i).toString()
                                + "','" + js_Ename.get(i).toString()
                                + "','" + js_barCode.get(i).toString()
                                + "','" + js_Address.get(i).toString()
                                + "','" + js_State.get(i).toString()
                                + "','" + js_SMan.get(i).toString()
                                + "','" + js_Latitude.get(i).toString()
                                + "','" + js_Longitude.get(i).toString()
                                + "','" + js_Note2.get(i).toString()
                                + "'," + js_sat.get(i).toString()
                                + "," + js_sun.get(i).toString()
                                + "," + js_mon.get(i).toString()
                                + "," + js_tues.get(i).toString()
                                + "," + js_wens.get(i).toString()
                                + "," + js_thurs.get(i).toString()
                                + "," + js_sat1.get(i).toString()
                                + "," + js_sun1.get(i).toString()
                                + "," + js_mon1.get(i).toString()
                                + "," + js_tues1.get(i).toString()
                                + "," + js_wens1.get(i).toString()
                                + "," + js_thurs1.get(i).toString()
                                + ",'" + js_Celing.get(i).toString()
                                + "','" + js_CatNo.get(i).toString()
                                + "','" + js_CustType.get(i).toString()
                                + "','" + js_PAMENT_PERIOD_NO.get(i).toString()
                                + "','" + js_CUST_PRV_MONTH.get(i).toString()
                                + "','" + js_CUST_NET_BAL.get(i).toString()
                                + "','" + js_Pay_How.get(i).toString()
                                + "','" + js_Cust_type.get(i).toString()
                                + "','" + js_country_No.get(i).toString()

                                + "','" + js_country_Nm.get(i).toString()
                                + "','" + js_CheckAlowedDay.get(i).toString()
                                + "','" + js_PromotionFlag.get(i).toString()
                                + "','" + js_CloseVisitWithoutimg.get(i).toString()
                                + "','" + BB_Chaq.get(i).toString()
                                + "','" + BB_bill.get(i).toString()
                                + "','" + Chqceling.get(i).toString()
                                + "')";
                        sql_Handler.executeQuery(q);

                    }
                    final int total = i;
                    _handler.post(new Runnable() {

                        public void run() {

                        }
                    });

                } catch (final Exception e) {

                    _handler.post(new Runnable() {

                        public void run() {

                        }
                    });
                }
            }
        }).start();
    }



    void DeleteAllWeek() {
try {

        SqlHandler sql_Handler = new SqlHandler(this);


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7); // Subtract 7 days from today's date
        Date oneWeekAgo = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = dateFormat.format(oneWeekAgo);
        formattedDate = formattedDate.substring(0, 10).replace('-', '/');
        System.out.print(" formattedDate : " + formattedDate);

        // مرتجع البيع
        String deleteQuery2 = "DELETE FROM Sal_return_Det WHERE Orderno in (select Orderno FROM Sal_return_Hdr WHERE DATE(date) ='" + formattedDate + "')";
        sql_Handler.executeQuery(deleteQuery2);
        // مرتجع البيع
        String deleteQuery = "DELETE FROM Sal_return_Hdr WHERE DATE(date)  = '" + formattedDate+"'";
        sql_Handler.executeQuery(deleteQuery);


        // فاتورة البيع
        String deleteQuery8 = "DELETE FROM Sal_invoice_Det WHERE OrderNo in (select OrderNo FROM Sal_invoice_Hdr WHERE DATE(date)  ='" + formattedDate + "')";
        sql_Handler.executeQuery(deleteQuery8);
        // فاتورة البيع
        String deleteQuery7 = "DELETE FROM Sal_invoice_Hdr WHERE date  = '" + formattedDate+"'";
        sql_Handler.executeQuery(deleteQuery7);


        // سند القبض
        String deleteQuery4 = "DELETE FROM RecVoucher WHERE TrDate  = '" + formattedDate+"'";
        sql_Handler.executeQuery(deleteQuery4);


        // فاتورة البيع
        String deleteQuery9 = "DELETE FROM Po_dtl WHERE orderno in (select orderno FROM Po_Hdr WHERE DATE(date)  ='" + formattedDate + "')";
        sql_Handler.executeQuery(deleteQuery9);
        // فاتورة البيع
        String deleteQuery10 = "DELETE FROM Po_Hdr WHERE DATE(date)  = '" + formattedDate+"'";
        sql_Handler.executeQuery(deleteQuery10);

}catch(Exception e){
System.out.print("   errrror : "+e.getMessage());

}


    }}

