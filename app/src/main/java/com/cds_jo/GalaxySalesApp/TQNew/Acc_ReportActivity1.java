package com.cds_jo.GalaxySalesApp.TQNew;

import android.app.AlertDialog;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.ComInfo;
import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.NewPackage.GalaxyNewHomeActivity;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.Select_Customer;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.cds_jo.GalaxySalesApp.assist.Convert_Prapre_Qty_To_Img;
import com.cds_jo.GalaxySalesApp.assist.Convert_ccReportTo_ImgActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Methdes.MethodToUse;
import Methdes.MyTextView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import header.Header_Frag;


public class Acc_ReportActivity1  extends FragmentActivity {
    ListView items_Lsit;
    public ProgressDialog loadingdialog;
    private Calendar calendar;
    private int year, month, day;
    public int FlgDate = 0;
    TextView memo;
    String MEMO1;
    String total1;
    EditText et_FromDay,et_FromMonth,et_FromYear,et_ToDay,et_ToMonth,et_ToYear;
    MyTextView tv_tot_total,tv_cred_total,tv_dept_total;
    String From,ToDate;
    NumberFormat nf_out;
    MyTextView NetBall  ;
    String Amt;
    ArrayList<Cls_Acc_Report1> Lists;
    double tot;
    SharedPreferences sharedPreferences;
    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

        }catch (Exception sd){}
        setContentView(R.layout.fragment_acc__report_activity1);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Fragment frag=new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager. beginTransaction().replace(R.id.Frag1,frag).commit();

        et_FromDay =(EditText)findViewById(R.id.et_FromDay);
        et_FromMonth =(EditText)findViewById(R.id.et_FromMonth);
        et_FromYear =(EditText)findViewById(R.id.et_FromYear);
        et_ToDay =(EditText)findViewById(R.id.et_ToDay);
        et_ToMonth =(EditText)findViewById(R.id.et_ToMonth);
        et_ToYear =(EditText)findViewById(R.id.et_ToYear);

        memo =findViewById(R.id.memo);
        tv_tot_total =(MyTextView) findViewById(R.id.tv_tot_total);
        tv_cred_total =(MyTextView)findViewById(R.id.tv_cred_total);
        tv_dept_total =(MyTextView)findViewById(R.id.tv_dept_total);

        NetBall = (MyTextView)findViewById(R.id.tv_NetBall);

        et_ToMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getLastDayInmonth(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //et_ToMonth.setText( intToString( Integer.parseInt( et_ToMonth.getText().toString()),2));


            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final TextView CustNm =(TextView)findViewById(R.id.tv_cusnm);
        TextView acc = (TextView)findViewById(R.id.tv_acc);

        Button btn_Back=(Button)findViewById(R.id.btn_Back);
        btn_Back.setTypeface(MethodToUse.SetTFace(Acc_ReportActivity1.this));

        Button Retrive=(Button)findViewById(R.id.button17);
        Retrive.setTypeface(MethodToUse.SetTFace(Acc_ReportActivity1.this));


        if( ComInfo.SCR_NO==2|| ComInfo.SCR_NO==3) {
            acc.setText(sharedPreferences.getString("OrderCustNo", ""));
            CustNm.setText(sharedPreferences.getString("OrderCustNm", ""));
        }
        else{
            acc.setText(sharedPreferences.getString("CustNo", ""));
            CustNm.setText(sharedPreferences.getString("CustNm", ""));
        }

        CustNm.setError(null);




        nf_out = NumberFormat.getNumberInstance(Locale.ENGLISH);
        nf_out.setMaximumFractionDigits(3);
        nf_out.setMinimumFractionDigits(3);

        final Calendar c = Calendar.getInstance();
        c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);


        calendar = Calendar.getInstance();
        year = c.get(Calendar.YEAR);

        month = c.get(Calendar.MONTH);

        day = c.get(Calendar.DAY_OF_MONTH);




        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        String currentYear = sdf.format(new Date());

        SimpleDateFormat MM = new SimpleDateFormat("MM", Locale.ENGLISH);
        String currentMonth= MM.format(new Date());


        et_FromDay.setText("01");
        et_FromMonth.setText("01");
        et_FromYear.setText(currentYear);
        et_ToDay.setText("31");
        et_ToMonth.setText(currentMonth);
        et_ToYear.setText(currentYear);



     /*   FromDate.setText("01/01/"+currentYear);
        ToDate.setText("31/12/"+currentYear);*/
        SimpleDateFormat sdf_currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String currentDate = sdf_currentDate.format(new Date());

        items_Lsit=(ListView)findViewById(R.id.lst_acc);
        items_Lsit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Cls_Acc_Report1 o = (Cls_Acc_Report1) items_Lsit.getItemAtPosition(position);

                if(o.getTRANSTYPE().equalsIgnoreCase("2")){
                    Amt=o.getDept();
                }else if (o.getTRANSTYPE().equalsIgnoreCase("3")){
                    Amt=o.getCred();
                }
                if(o.getTRANSTYPE().equalsIgnoreCase("4")) {
                    showDtl(o.getDoc_num(), o.getTRANSTYPE(), Amt + "",o.getDate(),o.getName());
                }else{
                    showDtl(o.getINVOICE(), o.getTRANSTYPE(), Amt + "",o.getDate(),o.getName());
                }
            }
        });
        Lists = new ArrayList<Cls_Acc_Report1>();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        onProgressUpdate();
    }
    private  void showDtl(String DocNo , String DocType,String Amt, String Date,String Name){

        TextView acc = (TextView)findViewById(R.id.tv_acc);
        if(DocType.equalsIgnoreCase("2")   ||DocType.equalsIgnoreCase("3") ) {
      /*          Bundle bundle = new Bundle();
                bundle.putString("Scr", "po");
                bundle.putString("DocNo", DocNo);
                bundle.putString("Amt", Amt);
                FragmentManager Manager = getFragmentManager();
                PopShowInvoiceDtl obj = new PopShowInvoiceDtl();
                obj.setArguments(bundle);
                obj.show(Manager, null);*/


            Intent i = new Intent(this, Convert_Prapre_Qty_To_Img1.class);
            i.putExtra("DocNo",DocNo);
            i.putExtra("Amt",Amt);
            i.putExtra("DocType",DocType);

            i.putExtra("Date",Date);
            i.putExtra("Name",Name);
            startActivity(i);


        }else if (DocType.equalsIgnoreCase("4")) {
            Bundle bundle = new Bundle();
            bundle.putString("Scr", "po");
            bundle.putString("DocNo", DocNo);
            bundle.putString("ACC", acc.getText().toString());
            bundle.putString("Amt", Amt);
            FragmentManager Manager = getFragmentManager();
            PopShowCheckDtl obj = new PopShowCheckDtl();
            obj.setArguments(bundle);
            obj.show(Manager, null);

        }
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


    public void btn_ShowCheck(View view) {
        TextView accno = (TextView) findViewById(R.id.tv_acc);
        TextView acc = (TextView)findViewById(R.id.tv_acc);

        Bundle bundle = new Bundle();
        bundle.putString("accno", acc.getText().toString());
        bundle.putString("Balance", tv_tot_total.getText().toString());
        FragmentManager Manager =  getFragmentManager();
        ShoeCheckFragment obj = new ShoeCheckFragment();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }

    private boolean isValidDate(String dateOfBirth) {
        boolean valid = true;

        if(dateOfBirth.trim().length()<10) {
            return false;
        }
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");


        } catch (Exception e) {
            valid = false;
            return valid;
        }


        String[] parts = dateOfBirth.split("/");
        String part1 = parts[0];
        String part2 = parts[1];
        String part3 = parts[2];
        if ( SToD(part1) <0  || SToD(part1)>31 ){
            valid = false;
            return valid;
        }
        if ( SToD(part2) <0  || SToD(part2)>12 ){
            valid = false;
            return valid;
        }

        if ( SToD(part3) <1990  || SToD(part3)>2050 ){
            valid = false;
            return valid;
        }

        return valid;
    }
    private  void getLastDayInmonth(String m){
        //
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf.parse("01/"+m+"/"+et_ToYear.getText().toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int lastday=  calendar.getActualMaximum(Calendar.DAY_OF_MONTH) ;
            et_ToDay.setText(lastday+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    private  void getLastDayInmonth_FromDate(String m){
        //
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf.parse("01/"+m+"/"+et_FromYear.getText().toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int lastday=  calendar.getActualMaximum(Calendar.DAY_OF_MONTH) ;
            et_FromDay.setText(lastday+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public void onProgressUpdate( ){
        tot=0;
        Lists.clear();

        final List<String> items_ls = new ArrayList<String>();

        items_Lsit.setAdapter(null);

        final MyTextView CheqBal = (MyTextView)findViewById(R.id.tv_CheqBal);
        final MyTextView tv_bb = (MyTextView)findViewById(R.id.tv_bb);
        final MyTextView CusTop = (MyTextView)findViewById(R.id.tv_CusTop);


        final   TextView acc = (TextView)findViewById(R.id.tv_acc);



        et_FromDay.setText( intToString( Integer.parseInt( et_FromDay.getText().toString()),2));
        et_FromMonth.setText( intToString( Integer.parseInt( et_FromMonth.getText().toString()),2));
        et_FromYear.setText( intToString( Integer.parseInt( et_FromYear.getText().toString()),4));


        et_ToDay.setText( intToString( Integer.parseInt( et_ToDay.getText().toString()),2));
        //et_ToMonth.setText( intToString( Integer.parseInt( et_ToMonth.getText().toString()),2));
        et_ToYear.setText( intToString( Integer.parseInt( et_ToYear.getText().toString()),4));


        et_FromDay.setError(null);
        et_FromMonth.setError(null);
        et_FromYear.setError(null);

        et_ToDay.setError(null);
        et_ToMonth.setError(null);
        et_ToYear.setError(null);
        if(Integer.parseInt(et_FromDay.getText().toString())>31){
            et_FromDay.setError("إدخال غير صحيح");
            et_FromDay.setFocusable(true);
            return;
        }
        if(Integer.parseInt(et_FromMonth.getText().toString())>12){
            et_FromMonth.setError("إدخال غير صحيح");
            et_FromMonth.setFocusable(true);
            return;
        }


        if(Integer.parseInt(et_ToDay.getText().toString())>31){
            et_ToDay.setError("إدخال غير صحيح");
            et_ToDay.setFocusable(true);
            return;
        }
        if(Integer.parseInt(et_FromMonth.getText().toString())>12){
            et_ToMonth.setError("إدخال غير صحيح");
            et_ToMonth.setFocusable(true);
            return;
        }


        if(Integer.parseInt(et_FromYear.getText().toString())>Integer.parseInt(et_ToYear.getText().toString())){
            et_ToYear.setError("إدخال غير صحيح");
            et_FromYear.setError("إدخال غير صحيح");
            et_ToYear.setFocusable(true);
            return;
        }

        From= et_FromDay.getText().toString()+"/"+et_FromMonth.getText().toString()+"/"+et_FromYear.getText().toString();
        ToDate= et_ToDay.getText().toString()+"/"+et_ToMonth.getText().toString()+"/"+et_ToYear.getText().toString();



        From= et_FromYear.getText().toString()+"/"+et_FromMonth.getText().toString()+"/"+et_FromDay.getText().toString();
        ToDate= et_ToYear.getText().toString()+"/"+et_ToMonth.getText().toString()+"/"+et_ToDay.getText().toString();



        CheqBal.setText("");
        tv_bb.setText("");
        CusTop.setText("");
        NetBall.setText("");


        final Handler _handler = new Handler();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String UserID = sharedPreferences.getString("UserID", "");
        final ProgressDialog custDialog = new ProgressDialog(Acc_ReportActivity1.this);
        memo =findViewById(R.id.memo);
        custDialog.setTitle(getResources().getText(R.string.PleaseWait));
        custDialog.setMessage(getResources().getText(R.string.Retrive_DataUnderProcess));
        custDialog.setProgressStyle(custDialog.STYLE_HORIZONTAL);
        custDialog.setCanceledOnTouchOutside(true);
        custDialog.setProgress(0);
        custDialog.setMax(100);
        if(Acc_ReportActivity1.this!=null) {
            custDialog.show();
        }



        new Thread(new Runnable() {
            @Override
            public void run() {

                CallWebServices ws = new CallWebServices(Acc_ReportActivity1.this);
                ws.CallReport(acc.getText().toString(), From,ToDate,"");

                HidKeybad();
                try {
                    double t_dept , t_cred,t_bb    ,t_tot,temp,t_rate;
                    t_dept= t_cred=t_bb   =t_tot =temp=t_rate =  0 ;
                    Integer i;
                    String q = "";

                    if (We_Result.ID>0) {
                        JSONObject js = new JSONObject(We_Result.Msg);
                        JSONArray js_doc_num = js.getJSONArray("doc_num");
                        JSONArray js_doctype = js.getJSONArray("doctype");
                        JSONArray js_cur_no = js.getJSONArray("cur_no");
                        JSONArray js_date = js.getJSONArray("date");
                        JSONArray js_des = js.getJSONArray("des");
                        JSONArray js_bb = js.getJSONArray("bb");
                        JSONArray js_dept = js.getJSONArray("dept");
                        JSONArray js_cred = js.getJSONArray("cred");
                        JSONArray js_rate = js.getJSONArray("rate");
                        JSONArray js_TRANSTYPE = js.getJSONArray("TRANSTYPE");
                        JSONArray js_INVOICE = js.getJSONArray("INVOICE");
                        //  JSONArray js_Memo = js.getJSONArray("MEMO");




                        Cls_Acc_Report1 cls_acc_report ;

                        for (i = 0; i < js_doc_num.length(); i++) {

                            if (i==0){
                                cls_acc_report = new Cls_Acc_Report1();
                                cls_acc_report.setTot(js_bb.get(0).toString() + "");
                                cls_acc_report.setRate(getResources().getText(R.string.CurrencyValue) + "");
                                cls_acc_report.setCred("");
                                cls_acc_report.setDept("");
                                cls_acc_report.setBb("");
                                cls_acc_report.setDes("");
                                cls_acc_report.setDate("");
                                cls_acc_report.setCur_no("العملة" + "");
                                cls_acc_report.setDoctype("الرصيد الأفتتاحي");
                                cls_acc_report.setDoc_num("");
                                cls_acc_report.setTRANSTYPE("0");
                                cls_acc_report.setINVOICE("");
                                MEMO1=(js.getString("MEMO").toString());



                                Lists.add(cls_acc_report);
                            }
                            else {
                                cls_acc_report = new Cls_Acc_Report1();

                                cls_acc_report.setDoc_num(js_doc_num.get(i).toString());
                                cls_acc_report.setDoctype(js_doctype.get(i).toString());
                                cls_acc_report.setCur_no(js_cur_no.get(i).toString());
                                cls_acc_report.setDate(js_date.get(i).toString());
                                cls_acc_report.setDes(js_des.get(i).toString());
                                cls_acc_report.setTRANSTYPE(js_TRANSTYPE.get(i).toString());
                                cls_acc_report.setINVOICE(js_INVOICE.get(i).toString());


                                cls_acc_report.setDept(nf_out.format(Double.parseDouble(js_dept.get(i).toString())) + "");
                                cls_acc_report.setCred(nf_out.format(Double.parseDouble(js_cred.get(i).toString())) + "");

                                if (Float.parseFloat(js_dept.get(i).toString()) > 0)
                                    temp = Double.parseDouble(js_dept.get(i).toString()) * Double.parseDouble(js_rate.get(i).toString());
                                else
                                    temp = Double.parseDouble(js_cred.get(i).toString()) * Double.parseDouble(js_rate.get(i).toString());


                                cls_acc_report.setRate(nf_out.format(temp) + "");
                                t_rate = t_rate + temp;
                                t_dept = t_dept + Double.parseDouble(js_dept.get(i).toString());
                                t_cred = t_cred + Double.parseDouble(js_cred.get(i).toString());
                                t_bb = Double.parseDouble(js_bb.get(i).toString());


                                if (i == 1)
                                    tot = tot + (Double.parseDouble(js_dept.get(i).toString()) + Double.parseDouble(js_bb.get(0).toString()) + Double.parseDouble(js_cred.get(i).toString()));
                                else
                                    tot = tot + (Double.parseDouble(js_dept.get(i).toString()) + Double.parseDouble(js_cred.get(i).toString()));


                                cls_acc_report.setTot(String.valueOf(nf_out.format(tot)));

                                Lists.add(cls_acc_report);
                            }

                            custDialog.setMax(js_doc_num.length());
                            custDialog.incrementProgressBy(1);

                            if (custDialog.getProgress() == custDialog.getMax()) {
                                custDialog.dismiss();
                            }

                        }


                        final int total = i;
                      /*  final String txtCheqBal = js.getString("CheqBal");
                        final String txtBall = js.getString("Ball");
                        final String txtCusTop = js.getString("CusTop");
                        final String txtNetBall = js.getString("NetBall");*/
                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setMinimumFractionDigits(3);
                        //Double myVal = 4.15465454;

                        final String S_t_bb = String.valueOf(t_bb);
                        final String S_dept = String.valueOf(nf_out.format(t_dept));
                        final String S_cred = String.valueOf(t_cred);
                        final String S_rate = String.valueOf(t_rate);
                        total1= (nf_out.format(tot));
                        final String S_tot = String.format(total1, Locale.ENGLISH);


                        _handler.post(new Runnable() {

                            public void run() {


                                CheqBal.setText("");

                                tv_bb.setText("");

                                CusTop.setText("");

                                NetBall.setText( ""     );
                                //NetBall.setText(    nf_out.format(Double.parseDouble(txtNetBall)) + "");

                              /*  Cls_Acc_Report cls_acc_report1 = new Cls_Acc_Report();
                                cls_acc_report1 = new Cls_Acc_Report();
                                cls_acc_report1.setCur_no("عدد الحركات");
                                cls_acc_report1.setDate((cls_acc_reportsList.size() - 1) + "");
                                cls_acc_report1.setDoctype("عدد الحركات");
                                cls_acc_report1.setDoc_num(" ");
                                cls_acc_report1.setDes("المجموع");
                                cls_acc_report1.setBb(nf_out.format(Double.parseDouble(S_t_bb)) + "");
                                cls_acc_report1.setDept(nf_out.format(Double.parseDouble(S_dept)) + "");
                                cls_acc_report1.setCred(nf_out.format(Double.parseDouble(S_cred)) + "");
                                cls_acc_report1.setRate(nf_out.format(Double.parseDouble(S_rate)) + "");
                                cls_acc_report1.setTot(nf_out.format(Double.parseDouble(S_tot)) + "");
                                cls_acc_report1.setTot(nf_out.format(Double.parseDouble(S_tot)) + "");


                                cls_acc_reportsList.add(cls_acc_report1);
*/
                                memo.setText(MEMO1);
                                tv_tot_total.setText(S_tot+"");
                                tv_cred_total.setText(S_cred.replace("-","")+"");
                                tv_dept_total.setText(S_dept+"");
                                tv_cred_total.setText( String.format(java.util.Locale.US,"%.3f",
                                        Double.valueOf(tv_cred_total.getText().toString())));
                                //String.format(java.util.Locale.US,"%.2f", floatValue);




                                //   NetBall.setText(tot+"");
                                NetBall.setText(String.format(total1, Locale.ENGLISH));


                                Cls_Acc_Report_Adapter1 cls_acc_report_adapter = new Cls_Acc_Report_Adapter1(
                                        Acc_ReportActivity1.this, Lists);

                                items_Lsit.setAdapter(cls_acc_report_adapter);
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        Acc_ReportActivity1.this).create();
                                alertDialog.setTitle("تحديث البيانات");

                                alertDialog.setMessage("تمت عملية استرجاع  البيانات بنجاح ");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });


                                custDialog.dismiss();
                            }
                        });
                    }else {


                        _handler.post(new Runnable() {
                            public void run() {

                                try {
                                    custDialog.dismiss();
                                    if (We_Result.ID == -404) {
                                        new SweetAlertDialog(Acc_ReportActivity1.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                                .setCustomImage(R.drawable.error_new)
                                                .setContentText("لا يوجد اتصال بالسيرفر")
                                                .show();
                                    }else     {
                                        new SweetAlertDialog(Acc_ReportActivity1.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                                .setCustomImage(R.drawable.error_new)
                                                .setContentText("لا يوجد بيانات")
                                                .show();
                                    }


                                }catch (Exception ex){

                                }
                            }
                        });

                    }
                } catch (final Exception e) {
                    custDialog.dismiss();
                    _handler.post(new Runnable() {

                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    Acc_ReportActivity1.this).create();
                            alertDialog.setTitle("كشف حساب تفصيلي");
                            if (We_Result.ID == -404) {
                                alertDialog.setMessage(We_Result.Msg);
                            } else {
                                alertDialog.setMessage("لا يوجد بيانات" );
                            }
                            alertDialog.setIcon(R.drawable.info);

                            alertDialog.setButton("رجــــوع", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            if(Acc_ReportActivity1.this!=null) {
                                try {
                                    alertDialog.show();
                                }catch (Exception t){}
                            }

                        }
                    });
                }


            }
        }).start();

    }


    private  void HidKeybad(){
        try{
            if (this.getCurrentFocus() != null) {
                InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }catch (Exception ex){}
    }
    public void btn_searchCustomer(View view) {


    /*    TextView acc = (TextView)findViewById(R.id.tv_acc);
        MyTextView tv_cusnm = (MyTextView)findViewById(R.id.tv_cusnm);
        acc.setText("");
        tv_cusnm.setText("");

        Bundle bundle = new Bundle();
        bundle.putString("Scr", "AccReport");
        FragmentManager Manager =  getFragmentManager();
        Select_Customer obj = new Select_Customer();
        obj.setArguments(bundle);
        obj.show(Manager, null);*/
    }
    public void Set_Cust(final String No, String Nm) {
        final    ProgressDialog progressDialog;
        final TextView CustNm =(TextView)findViewById(R.id.tv_cusnm);
        MyTextView acc = (MyTextView)findViewById(R.id.tv_acc);
        acc.setText(No);
        CustNm.setText(Nm);
        CustNm.setError(null);

        String MEMO = DB.GetValue(this, "Customers", "MEMO", "no ='" + No + "'");
        memo=findViewById(R.id.memo);
        memo.setText(MEMO);

        SharedPreferences.Editor editor    = sharedPreferences.edit();
        editor.putString("CustNo",No);
        editor.putString("CustNm",Nm);
        editor.commit();
        onProgressUpdate();

    }


    public void btn_GetData(View view) {
        HidKeybad();
        onProgressUpdate();
    }



    public void btnBack(View view) {
        Intent k = null;
        if(ComInfo.SCR_NO==2) {
            k = new Intent(this, GalaxyNewHomeActivity.class);
        }

        // k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(k);
        finish();
    }

    public void btn_Ptint(View view) {

        TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView accno = (TextView) findViewById(R.id.tv_acc);
        String q ="Delete from  ACC_REPORT ";
        SqlHandler sqlHandler = new SqlHandler(this);
        sqlHandler.executeQuery(q);
        ContentValues cv =new ContentValues();
        final ProgressDialog custDialog = new ProgressDialog(Acc_ReportActivity1.this);
        if (Lists.size()>0) {


            Toast.makeText(this,"الرجاء الأنتظار جاري العمل على طباعة الكشف",Toast.LENGTH_LONG).show();





            Long i;

            cv = new ContentValues();
            cv.put("Cust_No", "");
            cv.put("Cust_Nm", "");
            cv.put("FDate","");
            cv.put("TDate", "");
            cv.put("Tot", "الرصيد");
            cv.put("Rate","");
            cv.put("Cred", "مدين");
            cv.put("Dept","دائن");
            cv.put("Bb", "");
            cv.put("Des","البيان");
            cv.put("Date", "");
            cv.put("Cur_no", "");
            cv.put("Doctype","البيان");
            cv.put("Doc_num","رقم الحركة");
            cv.put("CheqBal","");
            cv.put("Ball",  "");
            cv.put("CusTop",  "");
            cv.put("NetBall",  tv_tot_total.getText().toString());
            cv.put("Notes", "");
            i = sqlHandler.Insert("ACC_REPORT", null, cv);


            int index = 0;
            if( Lists.size()>  30){
                index=30;
            }


            for (int x = 0; x < Lists.size(); x++) {
                Cls_Acc_Report1 contactListItems = new Cls_Acc_Report1();
                contactListItems = Lists.get(x);


                cv = new ContentValues();
                cv.put("Cust_No", accno.getText().toString());
                cv.put("Cust_Nm", CustNm.getText().toString());
                cv.put("FDate", From);
                cv.put("TDate", ToDate);
                cv.put("Tot", contactListItems.getTot().toString().replace(",", ""));
                cv.put("Rate", contactListItems.getRate().toString().replace(",", ""));
                cv.put("Cred", contactListItems.getCred().toString().replace(",", ""));
                cv.put("Dept", contactListItems.getDept().toString().replace(",", ""));
                cv.put("Bb", "");
                cv.put("Des", contactListItems.getDoctype().toString().replace(",", ""));
                cv.put("Date", contactListItems.getDate().toString() == null ? "" : contactListItems.getDate().toString());
                cv.put("Cur_no", contactListItems.getCur_no().toString().replace(",", ""));
                cv.put("Doctype", contactListItems.getDoctype().toString().replace(",", ""));
                cv.put("Doc_num", contactListItems.getDoc_num().toString().replace(",", ""));
                cv.put("CheqBal", "");
                cv.put("Ball", "");
                cv.put("CusTop", "");
                cv.put("NetBall", tv_tot_total.getText().toString());
                cv.put("Notes", "");
                i = sqlHandler.Insert("ACC_REPORT", null, cv);
            }

            Intent k = new Intent(this, Convert_ccReportTo_ImgActivity1.class);

            k.putExtra("Scr", "po");

            k.putExtra("cusnm", CustNm.getText().toString());
            k.putExtra("OrderNo", "");
            k.putExtra("accno", accno.getText().toString());
            k.putExtra("From", From);
            k.putExtra("ToDate", ToDate);
            k.putExtra("NetBall",tv_tot_total.getText().toString());

            startActivity(k);





        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("كشف الحساب");
            alertDialog.setMessage("يجب استرجاع البيانات اولا");
            alertDialog.setIcon(R.drawable.error_new);
            alertDialog.setButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            try {
                alertDialog.show();
            }catch (Exception ecx){}
        }

    }


}
