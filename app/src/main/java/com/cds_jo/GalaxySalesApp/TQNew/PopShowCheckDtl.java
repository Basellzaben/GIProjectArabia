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

public class PopShowCheckDtl extends DialogFragment implements View.OnClickListener  {

    public ProgressDialog loadingdialog;
    private int year, month, day;
    String DocNo,Amt,ACC ,  FromDate,ToDate ;
    MyTextView msg,tv_checkNo,tv_mutDate,tvBanknm,tv_des,tv_PersonNm ;
    View form ;
    NumberFormat nf_out;
    Button Back;//,GetData;
    Context context;
    MyTextView tv_InvoiceNo,tv_amtNo;
    JSONArray js_ACCOUNTNUM , js_Tr_date,js_amt , js_doc_num  ,js_Tr_des ,js_CHECKNUMBER , js_MATURITYDATE,js_BANKACCOUNTID;
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
        form =inflater.inflate(R.layout.check_details,container,false);
        context=getActivity();
        msg = (MyTextView)form.findViewById(R.id.msg);
        tv_checkNo = (MyTextView)form.findViewById(R.id.tv_checkNo);
        tv_mutDate = (MyTextView)form.findViewById(R.id.tv_mutDate);
        tvBanknm = (MyTextView)form.findViewById(R.id.tvBanknm);
        tv_des = (MyTextView)form.findViewById(R.id.tv_des);
        tv_PersonNm = (MyTextView)form.findViewById(R.id.tv_PersonNm);




        Back = (Button)form.findViewById(R.id.Back);
        Back.setTypeface(MethodToUse.SetTFace(context));
        Back.setOnClickListener(this);
        getDialog().getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        DocNo=getArguments().getString("DocNo");
        ACC=getArguments().getString("ACC");
        Amt=getArguments().getString("Amt");



        tv_InvoiceNo = (MyTextView)form.findViewById(R.id.tv_InvoiceNo);
        tv_InvoiceNo.setText(DocNo);


        tv_amtNo = (MyTextView)form.findViewById(R.id.tv_amtNo);
        tv_amtNo.setText(Amt.replace("-",""));

        msg.setVisibility(View.VISIBLE);
        onProgressUpdate();
        return  form;
    }
    public void onClick(View v) {
        if (v == Back) {
            this.dismiss();
        }
       /* else if (v == GetData) {
        onProgressUpdate();
    }*/
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

        final MyTextView tv_InvoiceDate = (MyTextView)form.findViewById(R.id.tv_InvoiceDate);

        tv_InvoiceDate.setText("");



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
        // GetData.setVisibility(View.INVISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(context);
                ws.ShowCheckDtlDtl(ACC,DocNo);
                try {
                    Integer i;
                    String q = "";
                    if (We_Result.ID>0) {
                        JSONObject js = new JSONObject(We_Result.Msg);
                        js_ACCOUNTNUM = js.getJSONArray("ACCOUNTNUM");
                        js_Tr_date= js.getJSONArray("Tr_date");
                        js_amt = js.getJSONArray("amt");
                        js_doc_num     = js.getJSONArray("doc_num");
                        js_Tr_des = js.getJSONArray("Tr_des");
                        js_CHECKNUMBER = js.getJSONArray("CHECKNUMBER");
                        js_MATURITYDATE = js.getJSONArray("MATURITYDATE");
                        js_BANKACCOUNTID = js.getJSONArray("BANKACCOUNTID");

                        tv_checkNo.setText(js_CHECKNUMBER.get(0).toString());
                        tv_mutDate.setText(js_MATURITYDATE.get(0).toString());
                        tvBanknm.setText(js_BANKACCOUNTID.get(0).toString());
                        tv_des.setText(js_Tr_des.get(0).toString());
                        tv_PersonNm.setText("");
                        tv_InvoiceDate.setText(js_Tr_date.get(0).toString());
                        tv_amtNo.setText(js_amt.get(0).toString().replace("-",""));

                        msg.setText( "");
                        msg.setVisibility(View.GONE);

                        _handler.post(new Runnable() {

                            public void run() {
                                msg.setText( "");
                                msg.setVisibility(View.GONE);


                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        context).create();
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
                        msg.setText( "لا يوجد بيانات");
                        msg.setVisibility(View.GONE);

                        custDialog.dismiss();
                    }
                } catch (final Exception e) {
                    custDialog.dismiss();
                    _handler.post(new Runnable() {

                        public void run() {
                            msg.setText( "لا يوجد بيانات");
                            msg.setVisibility(View.VISIBLE);


                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    context).create();
                            alertDialog.setTitle("تفاصيل الشيكات");
                            if (We_Result.ID == -404) {
                                alertDialog.setMessage(We_Result.Msg);
                            } else {
                                alertDialog.setMessage("لا يوجد بيانات" );
                            }
                            alertDialog.setIcon(R.drawable.error_new );

                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            //  alertDialog.show();

                        }
                    });
                }


            }
        }).start();
    }
}


