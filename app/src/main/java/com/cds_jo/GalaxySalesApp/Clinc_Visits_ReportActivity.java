package com.cds_jo.GalaxySalesApp;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.NewPackage.GalaxyNewHomeActivity;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import Methdes.MethodToUse;
import Methdes.MyTextView;
import header.Header_Frag;

public class Clinc_Visits_ReportActivity extends FragmentActivity {
    ListView items_Lsit;
    public ProgressDialog loadingdialog;
    private Calendar calendar;
    private int year, month, day;
    public int FlgDate = 0;
    MyTextView FromDate;
    MyTextView ToDate ;
    String UserID,DoctorNo;
    NumberFormat nf_out;

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year,month, day);
        }
        return null;
    }
    public void setDate(View view) {
        showDialog(999);
       /* Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                .show();*/
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        if (FlgDate == 1) {
            FromDate.setText(new StringBuilder().append(intToString(Integer.valueOf(day), 2)).append("/")
                    .append(intToString(Integer.valueOf(month), 2)).append("/").append(year));
        }

        if (FlgDate == 2) {
            ToDate.setText(new StringBuilder().append(intToString(Integer.valueOf(day), 2)).append("/")
                    .append(intToString(Integer.valueOf(month), 2)).append("/").append(year));
        }
    }
    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinc_visits_report);

        Fragment frag=new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1,frag).commit();


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final TextView CustNm =(TextView)findViewById(R.id.tv_cusnm);
        TextView acc = (TextView)findViewById(R.id.tv_acc);

        Button Retrive=(Button)findViewById(R.id.button17);
        Retrive.setTypeface(MethodToUse.SetTFace(Clinc_Visits_ReportActivity.this));

        if( ComInfo.SCR_NO==2|| ComInfo.SCR_NO==3) {
            acc.setText(sharedPreferences.getString("OrderCustNo", ""));
            CustNm.setText(sharedPreferences.getString("OrderCustNm", ""));
        }
        else{
            acc.setText(sharedPreferences.getString("CustNo", ""));
            CustNm.setText(sharedPreferences.getString("CustNm", ""));
        }

        CustNm.setError(null);

        FromDate = (MyTextView)findViewById(R.id.ed_FromDate);
        ToDate = (MyTextView)findViewById(R.id.ed_ToDate);

        UserID = sharedPreferences.getString("UserID", "");
        nf_out = NumberFormat.getNumberInstance(Locale.ENGLISH);
        nf_out.setMaximumFractionDigits(3);
        nf_out.setMinimumFractionDigits(3);

        final Calendar c = Calendar.getInstance();
        c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);


        calendar = Calendar.getInstance();
        year = c.get(Calendar.YEAR);

        month = c.get(Calendar.MONTH);

        day = c.get(Calendar.DAY_OF_MONTH);
        showDate(year, month, day);

        FromDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FlgDate = 1;
                showDialog(999);


            }
        });

        ToDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FlgDate = 2 ;
                showDialog(999);


            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        String currentYear = sdf.format(new Date());


        FromDate.setText("01/01/"+currentYear);
        ToDate.setText("31/12/"+currentYear);
        SimpleDateFormat sdf_currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String currentDate = sdf_currentDate.format(new Date());
        ToDate.setText(currentDate);

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
    public void onProgressUpdate( ){



        final List<String> items_ls = new ArrayList<String>();
        items_Lsit=(ListView)findViewById(R.id.lst_acc);
        items_Lsit.setAdapter(null);



        final   TextView acc = (TextView)findViewById(R.id.tv_acc);

        if(!acc.getText().toString().equalsIgnoreCase("")){
            DoctorNo=acc.getText().toString();
        }else{
            DoctorNo="-1";
        }


        FromDate.setError(null);
        ToDate.setError(null);

        if ( FromDate.getText().toString().length()   <= 0) {
            FromDate.setError("*");
            FromDate.requestFocus();
            return;
        }

        if ( ToDate.getText().toString().length()   <= 0) {
            ToDate.setError("*");
            ToDate.requestFocus();
            return;
        }


        AlertDialog alertDialog  ;
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getResources().getText(R.string.From_Date));
        alertDialog.setMessage( getResources().getText(R.string.PleaseCheckDate));
        alertDialog.setIcon(R.drawable.error_new);
        alertDialog.setButton(getResources().getText(R.string.Ok)    , new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                FromDate.setText("");
                FromDate.requestFocus();
            }
        });

        if(!isValidDate(FromDate.getText().toString())){
            alertDialog.show();
            return;
        }



        alertDialog.setTitle(getResources().getText(R.string.To_Date));
        alertDialog.setMessage( getResources().getText(R.string.PleaseCheckDate));
        alertDialog.setIcon(R.drawable.delete);
        alertDialog.setButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ToDate.setText("");
                ToDate.requestFocus();

            }
        });

        if(!isValidDate(ToDate.getText().toString())){

            alertDialog.show();
            return;
        }



        final Handler _handler = new Handler();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String UserID = sharedPreferences.getString("UserID", "");
        final ProgressDialog custDialog = new ProgressDialog(Clinc_Visits_ReportActivity.this);

        custDialog.setTitle(getResources().getText(R.string.PleaseWait));
        custDialog.setMessage(getResources().getText(R.string.Retrive_DataUnderProcess));
        custDialog.setProgressStyle(custDialog.STYLE_HORIZONTAL);
        custDialog.setCanceledOnTouchOutside(false);
        custDialog.setProgress(0);
        custDialog.setMax(100);
        custDialog.show();




        new Thread(new Runnable() {
            @Override
            public void run() {

                CallWebServices ws = new CallWebServices(Clinc_Visits_ReportActivity.this);
                ws.CallClincVisitsReport(DoctorNo, FromDate.getText().toString(),ToDate.getText().toString(),UserID);


                try {

                    Integer i;
                    String q = "";

                    if (We_Result.Msg.length()>2) {
                        JSONObject js = new JSONObject(We_Result.Msg);
                        JSONArray js_OrderNo = js.getJSONArray("OrderNo");
                        JSONArray js_CustNm = js.getJSONArray("CustNm");
                        JSONArray js_VNotes = js.getJSONArray("VNotes");
                        JSONArray js_SNotes = js.getJSONArray("SNotes");
                        JSONArray js_Tr_Date = js.getJSONArray("Tr_Date");
                        JSONArray js_Tr_Time = js.getJSONArray("Tr_Time");
                        JSONArray js_VisitType = js.getJSONArray("VisitType");
                        JSONArray js_FreeSample = js.getJSONArray("FreeSample");
                        JSONArray js_Subjects = js.getJSONArray("Subjects");

                        final ArrayList<Cls_ClincVisitsReport> cls_acc_reportsList = new ArrayList<Cls_ClincVisitsReport>();
                        Cls_ClincVisitsReport cls_clincVisitsReport = new Cls_ClincVisitsReport();

                        /*cls_clincVisitsReport.setOrderNo(String.valueOf(getResources().getString(R.string.item_no)));
                        cls_clincVisitsReport.setCustNm(String.valueOf(getResources().getString(R.string.doctorname)));
                        cls_clincVisitsReport.setVNotes(String.valueOf(getResources().getText(R.string.notes)));
                        cls_clincVisitsReport.setSNotes(String.valueOf(getResources().getString(R.string.Summary)));
                        cls_clincVisitsReport.setTr_Date(String.valueOf(getResources().getString(R.string.date)));
                        cls_clincVisitsReport.setTr_Time(String.valueOf(getResources().getString(R.string.time)));
                        cls_clincVisitsReport.setVisitType(String.valueOf(getResources().getString(R.string.withsupervisor)));
                        cls_clincVisitsReport.setFreeSample(String.valueOf(getResources().getString(R.string.samples)));
                        cls_clincVisitsReport.setSubjects(String.valueOf(getResources().getString(R.string.Subjects)));

*/

                        //  cls_acc_reportsList.add(cls_clincVisitsReport);


                        for (i = 0; i < js_OrderNo.length(); i++) {
                            cls_clincVisitsReport = new Cls_ClincVisitsReport();

                            String itemno=js_OrderNo.get(i).toString().replace("رقم الزيارة :",String.valueOf(getResources().getString(R.string.item_no))+" :");
                            String Clinicname=js_CustNm.get(i).toString().replace("إسم العيادة :",String.valueOf(getResources().getString(R.string.Clinicname))+" :");
                            String notes=js_VNotes.get(i).toString().replace("الملاحظات :",String.valueOf(getResources().getString(R.string.notes))+" :");
                            //   Toast.makeText(Clinc_Visits_ReportActivity.this,js_OrderNo.get(i).toString(),Toast.LENGTH_LONG).show();
                            cls_clincVisitsReport.setOrderNo(itemno);
                            cls_clincVisitsReport.setCustNm(Clinicname);
                            cls_clincVisitsReport.setVNotes(notes);


                            // String VisitType=js_VisitType.get(i).toString().replace("زيارة مع مشرف :",String.valueOf(getResources().getString(R.string.withsupervisor))+" :");
                            String VisitType=js_VisitType.get(i).toString().replace("زيارة مع مشرف :",String.valueOf(getResources().getString(R.string.withsupervisor))+" :");
                            VisitType=VisitType.replace("لا",String.valueOf(getResources().getString(R.string.no))+"");
                            VisitType=VisitType.replace("نعم",String.valueOf(getResources().getString(R.string.yes))+"");

                            String dateandtime=js_Tr_Date.get(i).toString().replace("التاريخ والوقت :",String.valueOf(getResources().getString(R.string.dateandtime))+" :");
                            String Summary=js_SNotes.get(i).toString().replace("الملخص :",String.valueOf(getResources().getString(R.string.Summary))+" :");

                            cls_clincVisitsReport.setSNotes(Summary);
                            cls_clincVisitsReport.setTr_Date(dateandtime);
                            cls_clincVisitsReport.setTr_Time(js_Tr_Time.get(i).toString());
                            cls_clincVisitsReport.setVisitType(VisitType);
                            cls_clincVisitsReport.setFreeSample(js_FreeSample.get(i).toString());
                            cls_clincVisitsReport.setSubjects(js_Subjects.get(i).toString());


                            cls_acc_reportsList.add(cls_clincVisitsReport);
                            custDialog.setMax(js_OrderNo.length());
                            custDialog.incrementProgressBy(1);
                            if (custDialog.getProgress() == custDialog.getMax()) {
                                custDialog.dismiss();
                            }

                        }

                        _handler.post(new Runnable() {

                            public void run() {

                                Cls_ClincVisitsReport_Adapter cls_acc_report_adapter = new Cls_ClincVisitsReport_Adapter(
                                        Clinc_Visits_ReportActivity.this, cls_acc_reportsList);

                                items_Lsit.setAdapter(cls_acc_report_adapter);
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        Clinc_Visits_ReportActivity.this).create();
                                alertDialog.setTitle(String.valueOf(getResources().getString(R.string.update_info)));

                                alertDialog.setMessage(String.valueOf(getResources().getString(R.string.Retrive_DataCompleteSucc)));
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton(String.valueOf(getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                // alertDialog.show();

                                custDialog.dismiss();
                            }
                        });
                    }else {
                        custDialog.dismiss();
                    }
                } catch (final Exception e) {
                    custDialog.dismiss();
                    _handler.post(new Runnable() {

                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    Clinc_Visits_ReportActivity.this).create();
                            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.medicalreport)));
                            if (We_Result.ID == -404) {
                                alertDialog.setMessage(We_Result.Msg);
                            } else {
                                alertDialog.setMessage(String.valueOf(getResources().getString(R.string.NoData)));
                            }
                            alertDialog.setIcon(R.drawable.info);

                            alertDialog.setButton(String.valueOf(getResources().getString(R.string.back)), new DialogInterface.OnClickListener() {
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

    public void btn_searchCustomer(View view) {


        TextView acc = (TextView)findViewById(R.id.tv_acc);
        MyTextView tv_cusnm = (MyTextView)findViewById(R.id.tv_cusnm);
        acc.setText("");
        tv_cusnm.setText("");





        Bundle bundle = new Bundle();
        bundle.putString("AreaNo","");
        bundle.putString("Scr", "ClincReport");
        FragmentManager Manager = getFragmentManager();
        Select_Customer obj = new Select_Customer();
        obj.setArguments(bundle);
        obj.show(Manager, null);

    }

    public void Set_Cust(final String No, String Nm) {
        final    ProgressDialog progressDialog;
        final TextView CustNm =(TextView)findViewById(R.id.tv_cusnm);
        MyTextView acc = (MyTextView)findViewById(R.id.tv_acc);
        acc.setText(No);
        CustNm.setText(Nm);
        CustNm.setError(null);



    }


    public void btn_GetData(View view) {
        onProgressUpdate();
    }

    @Override
    public void onBackPressed() {
      /*  super.onBackPressed();
        Intent k ;
        k = new Intent(this, GalaxyMainActivity2.class);
        startActivity(k);
        finish();*/
    }

    public void btn_fromYear(View view) {


        DateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        Date today = new Date();

        Calendar c1 = Calendar.getInstance();

        c1.setTime(today);
        c1.set(Calendar.DAY_OF_YEAR, 1);

        FromDate.setText(sdf1.format( c1.getTime()) +"");
        c1.setTime(today);

    }

    public void btn_fromMonth(View view) {


        DateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        Date today = new Date();

        Calendar c1 = Calendar.getInstance();

        c1.setTime(today);
        c1.set(Calendar.DAY_OF_MONTH, 1);

        FromDate.setText(sdf1.format( c1.getTime()) +"");


    }


    public void btn_Back(View view) {
        Intent k ;
        k = new Intent(this, GalaxyNewHomeActivity.class);
        startActivity(k);
        finish();
    }
}




