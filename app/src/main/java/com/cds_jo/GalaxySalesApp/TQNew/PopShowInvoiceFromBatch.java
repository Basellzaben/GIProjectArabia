package com.cds_jo.GalaxySalesApp.TQNew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.DialogFragment;
import android.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class PopShowInvoiceFromBatch extends DialogFragment implements View.OnClickListener   {
        ListView items_Lsit;
public ProgressDialog loadingdialog;
private int year, month, day;
        String ACC,ItemNo ,  FromDate,ToDate ;
static MyTextView tv_ACC;
        View form ;
        String st;
        NumberFormat nf_out;
        Button Back,GetData;
        Context context ;
        Button update;
//=(TextView)getActivity().findViewById(R.id.tv_ACC);

public static void setcustid(String id){


        tv_ACC.setText(id);

        }


        MyTextView msg;
public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
        }

public void showPop(String gg,String check) {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "po");
        bundle.putString("ACC", gg);
        bundle.putString("check", check);
        FragmentManager Manager = getFragmentManager();
        Pop_ItemSearchBatch obj = new Pop_ItemSearchBatch();
        obj.setArguments(bundle);
        obj.show(Manager, null);

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
        EditText ed_Batch;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savestate){
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        form =inflater.inflate(R.layout.show_invoice_from_batch,container,false);
        context = getActivity();
final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ACC=getArguments().getString("ACC");
        try{
        ItemNo=getArguments().getString("NO");}
        catch (Exception e){}
        update=(Button)form.findViewById(R.id.update);
        ed_Batch =(EditText) form.findViewById(R.id.ed_Batch);
        update.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {

        //    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //   tv_ACC.setText(preferences.getString("OrderCustNm", ""));
        String check = preferences.getString("check", "");

        showPop(ACC,check);
        PopShowInvoiceFromBatch.this.dismiss();


        }
        });
        GetData = (Button)form.findViewById(R.id.GetData);
        GetData.setTypeface(MethodToUse.SetTFace(context));
        GetData.setOnClickListener(this);
        msg = (MyTextView)form.findViewById(R.id.msg);
        msg.setVisibility(View.VISIBLE);
        Back = (Button)form.findViewById(R.id.Back);
        GetData.setVisibility(View.VISIBLE);
        Back.setTypeface(MethodToUse.SetTFace(context));
        Back.setOnClickListener(this);
        getDialog().getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);



final CheckBox searchtype=(CheckBox)form.findViewById(R.id.searchtype);

        //  preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String check = preferences.getString("check", "");
        if(check.equals("true")){
        searchtype.setChecked(true);
        ed_Batch.setHint("ادخل هنا رقم المادة");
        ed_Batch.setText(ItemNo);
        }
        else {
        searchtype.setChecked(false);
        ed_Batch.setHint("ادخل هنا الباتش");

        }


        searchtype.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("check", String.valueOf(searchtype.isChecked()));
        editor.apply();


        if(searchtype.isChecked()){
        ed_Batch.setHint("ادخل هنا رقم المادة");
        }
        else{
        ed_Batch.setText("");
        ed_Batch.setHint("ادخل هنا الباتش");
        }}
        });

        tv_ACC = (MyTextView)form.findViewById(R.id.tv_ACC);
        tv_ACC.setText(ACC);
    /*    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ACC",tv_ACC.getText());
        editor.apply();
*/
        // ed_Batch.setText(globalVal.batch);
        tv_ACC.setText(ACC);

        msg.setText("");
        msg.setVisibility(View.GONE);
        return  form;

        // tv_ACC.setText(preferences.getString("OrderCustNm", ""));

        }
public void onClick(View v) {
        if (v == Back) {
        globalVal.batch="";
        PopShowInvoiceFromBatch.this.dismiss();
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
        st="0";
        CheckBox searchtype=(CheckBox)form.findViewById(R.id.searchtype);
        String msgg="الرجاء إدخال الباتش";
        if(searchtype.isChecked()){
        st="1";
        msgg="الرجاء إدخال رقم المادة";
        }

        nf_out = NumberFormat.getNumberInstance(Locale.ENGLISH);
        nf_out.setMaximumFractionDigits(3);
        nf_out.setMinimumFractionDigits(3);


final List<String> items_ls = new ArrayList<String>();
        items_Lsit=(ListView)form.findViewById(R.id.lst_acc);
        items_Lsit.setAdapter(null);



final EditText ed_Batch = (EditText)form.findViewById(R.id.ed_Batch);


        if (ed_Batch.getText().toString().equalsIgnoreCase("")){
        msg.setVisibility(View.VISIBLE);
        msg.setText( msgg);
        ed_Batch.requestFocus();
        return;
        }
        ed_Batch.setError(null);

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
        //  custDialog.show();

        msg.setVisibility(View.VISIBLE);
        msg.setText("  الرجاء الانتظار ..." + "  العمل جاري على عرض البيانات  ");
        GetData.setVisibility(View.INVISIBLE);


        new Thread(new Runnable() {
@Override
public void run() {

        CallWebServices ws = new CallWebServices(context);
        ws.CusInvoiceFromBatch(ed_Batch.getText().toString(), ACC ,st);


        try {

        Integer i;
        String q = "";

        if(We_Result.ID>0) {
        JSONObject js = new JSONObject(We_Result.Msg);
        JSONArray js_ITEMID = js.getJSONArray("ItemNo");
        JSONArray js_NAME = js.getJSONArray("ItemNm");
        JSONArray js_SALESQTY = js.getJSONArray("Qty");
        JSONArray js_SALESPRICE = js.getJSONArray("Price");
        JSONArray js_LINEAMOUNT = js.getJSONArray("Total");
        JSONArray js_INVOICEDATE = js.getJSONArray("Tr_Date");
        JSONArray js_INVOICEID = js.getJSONArray("InvoiceNo");
        JSONArray js_Bounce = js.getJSONArray("Bounce");
        JSONArray js_Batch = js.getJSONArray("Batch");


final ArrayList<Cls_CustLastInvoice> cls_acc_reportsList = new ArrayList<Cls_CustLastInvoice>();

        Cls_CustLastInvoice cls_acc_report = new Cls_CustLastInvoice();




        int Sperat=0;
        String BillNo="";
        for (i = 0; i < js_ITEMID.length(); i++) {

        cls_acc_report = new Cls_CustLastInvoice();

        if (i==0){
        cls_acc_report.setSperatFlg("1");
        }else {
        if (js_INVOICEID.get(i).toString().equalsIgnoreCase(BillNo)){
        cls_acc_report.setSperatFlg("0");
        }else{
        cls_acc_report.setSperatFlg("1");
        }
        }
        BillNo = js_INVOICEID.get(i).toString();

        cls_acc_report.setITEMID(js_ITEMID.get(i).toString());
        cls_acc_report.setNAME(js_NAME.get(i).toString());
        cls_acc_report.setUnit("حبة");
        cls_acc_report.setSALESQTY(js_SALESQTY.get(i).toString());
        cls_acc_report.setSALESPRICE(js_SALESPRICE.get(i).toString());
        cls_acc_report.setLINEAMOUNT(js_LINEAMOUNT.get(i).toString());
        cls_acc_report.setINVENTBATCHID(js_INVOICEDATE.get(i).toString());
        cls_acc_report.setINVOICEDATE(js_INVOICEDATE.get(i).toString());
        cls_acc_report.setSALESID(js_INVOICEID.get(i).toString());
        cls_acc_report.setBOUNCE(js_Bounce.get(i).toString());
        cls_acc_report.setITEMBATCH(js_Batch.get(i).toString());
        cls_acc_report.setBATCHSEARCH(ed_Batch.getText().toString());
        cls_acc_reportsList.add(cls_acc_report);

        custDialog.setMax(js_ITEMID.length());
        custDialog.incrementProgressBy(1);

        if (custDialog.getProgress() == custDialog.getMax()) {
        HidKeybad();
        custDialog.dismiss();
        msg.setText( "");
        msg.setVisibility(View.GONE);
        GetData.setVisibility(View.VISIBLE);
        }

        }


final int total = i;


        _handler.post(new Runnable() {

public void run() {
        HidKeybad();
        msg.setText( "");
        msg.setVisibility(View.GONE);
        GetData.setVisibility(View.VISIBLE);

        Cls_Invoice_From_Batch cls_acc_report_adapter = new Cls_Invoice_From_Batch(
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

        HidKeybad();
        custDialog.dismiss();
        }
        });
        }else {
        _handler.post(new Runnable() {

public void run() {
        msg.setText( "لا يوجد بيانات");
        msg.setVisibility(View.VISIBLE);
        GetData.setVisibility(View.VISIBLE);
        HidKeybad();
        custDialog.dismiss();
        }
        });
        }
        } catch (final Exception e) {
        custDialog.dismiss();
        _handler.post(new Runnable() {

public void run() {
        HidKeybad();
        msg.setText( We_Result.Msg);
        msg.setVisibility(View.VISIBLE);
        GetData.setVisibility(View.VISIBLE);

        AlertDialog alertDialog = new AlertDialog.Builder(
        context).create();
        alertDialog.setTitle("اخر فاتورة للعميل");
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
        // alertDialog.show();

        }
        });
        }

        }
        }).start();

        }




private  void HidKeybad(){
        try {

final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }catch (Exception ex){}
        }


        }
