package com.cds_jo.GalaxySalesApp.TQNew;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Methdes.MethodToUse;
import Methdes.MyTextView;

public class PopShowCustLastInvoice1 extends DialogFragment implements View.OnClickListener  {
    ListView items_Lsit;
    public ProgressDialog loadingdialog;
    private int year, month, day;
    String ACC ,  FromDate,ToDate ;
    MyTextView msg ;
    View form ;
    NumberFormat nf_out;
    Button Back,GetData;
    Context context;


    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
    }
    @Override
    public void onStart()
    {
        super.onStart();


        if (getDialog() == null)
            return;


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom( getDialog().getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(lp);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setWindowAnimations(0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savestate){
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        form =inflater.inflate(R.layout.show_cust_last_invoice1,container,false);
        context=getActivity();
        msg = (MyTextView)form.findViewById(R.id.msg);
        GetData = (Button)form.findViewById(R.id.GetData);
        GetData.setTypeface(MethodToUse.SetTFace(context));
        GetData.setOnClickListener(this);

        Back = (Button)form.findViewById(R.id.Back);
        Back.setTypeface(MethodToUse.SetTFace(context));
        Back.setOnClickListener(this);
        getDialog().getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        ACC=getArguments().getString("ACC");
        msg.setVisibility(View.VISIBLE);
        onProgressUpdate();
        return  form;
    }
    public void onClick(View v) {
        if (v == Back) {
            this.dismiss();
        }else if (v == GetData) {
            onProgressUpdate();
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

    public void onProgressUpdate( ){


        nf_out = NumberFormat.getNumberInstance(Locale.ENGLISH);
        nf_out.setMaximumFractionDigits(3);
        nf_out.setMinimumFractionDigits(3);


        final List<String> items_ls = new ArrayList<String>();
        items_Lsit=(ListView)form.findViewById(R.id.lst_acc);
        items_Lsit.setAdapter(null);


        final MyTextView tv_InvoiceNo = (MyTextView)form.findViewById(R.id.tv_InvoiceNo);
        final MyTextView tv_InvoiceDate = (MyTextView)form.findViewById(R.id.tv_InvoiceDate);

        tv_InvoiceDate.setText("");
        tv_InvoiceNo.setText("");



        final Handler _handler = new Handler();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final String UserID = sharedPreferences.getString("UserID", "");
        final ProgressDialog custDialog = new ProgressDialog(context);

        custDialog.setTitle(getResources().getText(R.string.PleaseWait));
        custDialog.setMessage(getResources().getText(R.string.Retrive_DataUnderProcess));
        custDialog.setProgressStyle(custDialog.STYLE_HORIZONTAL);
        custDialog.setCanceledOnTouchOutside(false);
        custDialog.setProgress(0);
        custDialog.setMax(100);
        //custDialog.show();

        msg.setVisibility(View.VISIBLE);
        msg.setText("  الرجاء الانتظار ..." + "  العمل جاري على عرض البيانات  ");
        GetData.setVisibility(View.INVISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {

                CallWebServices ws = new CallWebServices(context);
                ws.CusLastInvoice(ACC);


                try {
                    float t_dept , t_cred,t_bb ,tot ,t_tot,temp,t_rate;
                    t_dept= t_cred=t_bb =tot =t_tot =temp=t_rate =  0 ;
                    Integer i;
                    String q = "";
                    if (We_Result.ID>0) {
                        JSONObject js = new JSONObject(We_Result.Msg);


                        JSONArray js_ITEMID = js.getJSONArray("ITEMID");
                        JSONArray js_NAME = js.getJSONArray("NAME");
                        JSONArray js_SALESQTY = js.getJSONArray("SALESQTY");
                        JSONArray js_SALESPRICE = js.getJSONArray("SALESPRICE");
                        JSONArray js_LINEAMOUNT = js.getJSONArray("LINEAMOUNT");
                        JSONArray js_INVENTBATCHID = js.getJSONArray("INVENTBATCHID");
                        JSONArray js_INVOICEDATE = js.getJSONArray("INVOICEDATE");
                        JSONArray js_INVOICEID = js.getJSONArray("INVOICEID");
                        JSONArray js_BOUNCE = js.getJSONArray("BOUNCE");

                        final String INVOICEDATE = js_INVOICEDATE.get(0).toString();
                        final String INVOICEID = js_INVOICEID.get(0).toString();


                        final ArrayList<Cls_CustLastInvoice> cls_acc_reportsList = new ArrayList<Cls_CustLastInvoice>();

                        Cls_CustLastInvoice cls_acc_report;// = new Cls_CustLastInvoice();




                        for (i = 0; i < js_ITEMID.length(); i++) {
                            cls_acc_report = new Cls_CustLastInvoice();

                            cls_acc_report.setITEMID(js_ITEMID.get(i).toString());
                            cls_acc_report.setNAME(js_NAME.get(i).toString());
                            cls_acc_report.setUnit("Psc");
                            cls_acc_report.setSALESQTY(js_SALESQTY.get(i).toString());
                            cls_acc_report.setSALESPRICE(js_SALESPRICE.get(i).toString());
                            cls_acc_report.setLINEAMOUNT(js_LINEAMOUNT.get(i).toString());
                            cls_acc_report.setINVENTBATCHID(js_INVENTBATCHID.get(i).toString());
                            cls_acc_report.setBOUNCE(js_BOUNCE.get(i).toString());


                            cls_acc_reportsList.add(cls_acc_report);


                            custDialog.setMax(js_ITEMID.length());
                            custDialog.incrementProgressBy(1);

                            if (custDialog.getProgress() == custDialog.getMax()) {
                                custDialog.dismiss();
                                msg.setText( "");
                                msg.setVisibility(View.GONE);
                                GetData.setVisibility(View.VISIBLE);
                            }

                        }
                        final int total = i;
                        _handler.post(new Runnable() {
                            public void run() {
                                try {
                                    msg.setText("");
                                    msg.setVisibility(View.GONE);
                                    GetData.setVisibility(View.VISIBLE);

                                    tv_InvoiceDate.setText(INVOICEDATE);
                                    tv_InvoiceNo.setText(INVOICEID);

                                    Cls_Cust_Last_Invoice_Adapter1 cls_acc_report_adapter = new Cls_Cust_Last_Invoice_Adapter1(
                                            context, cls_acc_reportsList);

                                    items_Lsit.setAdapter(cls_acc_report_adapter);
                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            context).create();
                                    alertDialog.setTitle("تحديث البيانات");

                                    alertDialog.setMessage("تمت عملية استرجاع  البيانات بنجاح ");
                                    alertDialog.setIcon(R.drawable.tick);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    // alertDialog.show();
                                    try {
                                        custDialog.dismiss();
                                    } catch (Exception ex) {
                                    }
                                }catch ( Exception ds){}
                            }
                        });
                    }else {
                        _handler.post(new Runnable() {
                            public void run() {
                                try {
                                    msg.setText("لا يوجد بيانات");
                                    msg.setVisibility(View.GONE);
                                    GetData.setVisibility(View.VISIBLE);
                                    custDialog.dismiss();
                                }catch (Exception sdf){}
                            }
                        });
                    }
                } catch (final Exception e) {
                    custDialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {
                            try {
                                msg.setText(We_Result.Msg);
                                msg.setVisibility(View.VISIBLE);
                                GetData.setVisibility(View.VISIBLE);

                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        context).create();
                                alertDialog.setTitle("اخر فاتورة للعميل");
                                if (We_Result.ID == -404) {
                                    alertDialog.setMessage(We_Result.Msg);
                                } else {
                                    alertDialog.setMessage("لا يوجد بيانات");
                                }
                                alertDialog.setIcon(R.drawable.error_new);

                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                //  alertDialog.show();

                            }catch (Exception sdad){}
                        }
                    });
                }
            }
        }).start();
    }
}




