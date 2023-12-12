package com.cds_jo.GalaxySalesApp.Mentnis;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.Cls_Sal_InvItems;
import com.cds_jo.GalaxySalesApp.ComInfo;
import com.cds_jo.GalaxySalesApp.Companies;
import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.JalMasterActivity;
import com.cds_jo.GalaxySalesApp.NewPackage.ViewVisitFrg;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SCR_ACTIONS;
import com.cds_jo.GalaxySalesApp.Sal_Inv_SearchActivity;
import com.cds_jo.GalaxySalesApp.Select_StoreNo;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.assist.Logtrans.InsertLogTrans;
import com.cds_jo.GalaxySalesApp.assist.Pop_Confirm_Serial_From_Zero;
import com.cds_jo.GalaxySalesApp.assist.Sale_InvoiceActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Methdes.MyTextView;

public class MentnisActivity extends AppCompatActivity {
    TextView  OrderNo ,tv_Model;
    Boolean IsNew;
    String UserID = "";
    EditText  CustNo,CustName ,Ser,Min,Max,Notes,Perventive,Corrective,Temp,Remedy,AIRFILTER_H,OilFILTER_H,OilFilling_H,Belt_H,Oilseparator_H;
    EditText  LineAirFliter2_Model,LineAirFliter1_Model ,LineAirFliter3_Model,LineAirFliter4_Model,
            LineAirFliter1_H,LineAirFliter2_H,LineAirFliter3_H,LineAirFliter4_H,
            malfunction,Water_Separator,Air_Dryer,Air_receiver;
    CheckBox AIRFILTER_Clean,AIRFILTER_renew,OilFILTER_AP,OilFILTER_renew,
            Oil_Filling_Fill,Oil_Filling_Check,  Oil_Filling_Renew,Belt_Retighten,Belt_Check,Belt_Renew,Oilseparator_AP,Oilseparator_renew;
    String query;
    SqlHandler sqlHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentnis);

        OrderNo =(TextView) findViewById(R.id.tv_OrderNo);
        tv_Model =(TextView) findViewById(R.id.tv_Model);
        CustNo =(EditText) findViewById(R.id.CustNo);
        Ser =(EditText) findViewById(R.id.Ser);
        Perventive =(EditText) findViewById(R.id.Perventive);
        Corrective =(EditText) findViewById(R.id.Corrective);
        Temp =(EditText) findViewById(R.id.Temp);
        CustName =(EditText) findViewById(R.id.CustName);
        Remedy =(EditText) findViewById(R.id.Remedy);
        Min =(EditText) findViewById(R.id.Min);
        Max =(EditText) findViewById(R.id.Max);
        Notes =(EditText) findViewById(R.id.Notes);
        AIRFILTER_H =(EditText) findViewById(R.id.AIRFILTER_H);
        OilFILTER_H =(EditText) findViewById(R.id.OilFILTER_H);
        OilFilling_H =(EditText) findViewById(R.id.Oil_Filling_H);
        Belt_H =(EditText) findViewById(R.id.Belt_H);
        LineAirFliter2_Model =(EditText) findViewById(R.id.LineAirFliter2_Model);
        LineAirFliter1_Model =(EditText) findViewById(R.id.LineAirFliter1_Model);
        LineAirFliter3_Model =(EditText) findViewById(R.id.LineAirFliter3_Model);
        LineAirFliter4_Model =(EditText) findViewById(R.id.LineAirFliter4_Model);
        LineAirFliter1_H =(EditText) findViewById(R.id.LineAirFliter1_H);
        LineAirFliter2_H =(EditText) findViewById(R.id.LineAirFliter2_H);
        LineAirFliter3_H =(EditText) findViewById(R.id.LineAirFliter3_H);
        LineAirFliter4_H =(EditText) findViewById(R.id.LineAirFliter4_H);
        Air_receiver =(EditText) findViewById(R.id.Air_receiver);
        malfunction =(EditText) findViewById(R.id.malfunction);
        Water_Separator =(EditText) findViewById(R.id.Water_Separator);
        Air_Dryer =(EditText) findViewById(R.id.Air_Dryer);
        Oilseparator_H =(EditText) findViewById(R.id.Oilseparator_H);
        AIRFILTER_Clean =(CheckBox) findViewById(R.id.AIRFILTER_Clean);
        AIRFILTER_renew =(CheckBox) findViewById(R.id.AIRFILTER_renew);
        OilFILTER_AP =(CheckBox) findViewById(R.id.OilFILTER_AP);
        OilFILTER_renew =(CheckBox) findViewById(R.id.OilFILTER_renew);
        Oil_Filling_Fill =(CheckBox) findViewById(R.id.Oil_Filling_Fill);
        Oil_Filling_Check =(CheckBox) findViewById(R.id.Oil_Filling_Check);
        Oil_Filling_Renew =(CheckBox) findViewById(R.id.Oil_Filling_Renew);
        Belt_Check =(CheckBox) findViewById(R.id.Belt_Check);
        Belt_Retighten =(CheckBox) findViewById(R.id.Belt_Retighten);
        Belt_Renew =(CheckBox) findViewById(R.id.Belt_Renew);
        Oilseparator_AP =(CheckBox) findViewById(R.id.Oilseparator_AP);
        Oilseparator_renew =(CheckBox) findViewById(R.id.Oilseparator_renew);
        sqlHandler=  new SqlHandler(MentnisActivity.this);
        GetMaxPONo();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        CustNo.setText(sharedPreferences.getString("CustNo", ""));
        CustName.setText(sharedPreferences.getString("CustNm", ""));


    }
    public void btn_save_po(final View view) {
        Save_Recod_Po();
    }
    public void btn_Search_Orders(View view) {


        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "Man");
        bundle.putString("doctype", "1");
            bundle.putString("typ", "0");

        FragmentManager Manager = getFragmentManager();
        Sal_Inv_SearchActivity obj = new Sal_Inv_SearchActivity();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }
    public void Set_Order(String No) {
        No = No.replace("\u202c", "").replace("\u202d", "");

        String q = "Select  s.* ,c.name" +
                "    from  Maint s   " +
                "   left join Customers c on c.no =s.Customer   " +

                "where     s.OrderNo = '" + No + "'";

        Cursor c1 = sqlHandler.selectQuery(q);
        CustNo.setText("");
        CustName.setText("");


        if (c1 != null && c1.getCount() != 0) {
            if (c1.getCount() > 0) {
                c1.moveToFirst();
                try {


               //     CustNm.setText(c1.getString(c1.getColumnIndex("Nm")).toString());

                } catch (Exception ex) {

                    Toast.makeText(this, "لا يمكن استرجاع الحساب", Toast.LENGTH_SHORT).show();
                }

                OrderNo.setText(No);
                tv_Model.setText(c1.getString(c1.getColumnIndex("Model")));
                Ser.setText(c1.getString(c1.getColumnIndex("Serial_No")));
                Perventive.setText(c1.getString(c1.getColumnIndex("Preventive")));
                Corrective.setText(c1.getString(c1.getColumnIndex("Corrective")));
                Temp.setText(c1.getString(c1.getColumnIndex("Temp")));
                Remedy.setText(c1.getString(c1.getColumnIndex("Remedy")));
                Min.setText(c1.getString(c1.getColumnIndex("Min")));
                Max.setText(c1.getString(c1.getColumnIndex("Max")));
                Notes.setText(c1.getString(c1.getColumnIndex("Remarks")));
                AIRFILTER_H.setText(c1.getString(c1.getColumnIndex("AirFilter_hrs")));
                OilFILTER_H.setText(c1.getString(c1.getColumnIndex("OilFilter_hrs")));
                OilFilling_H.setText(c1.getString(c1.getColumnIndex("OilFilling_hrs")));
                Belt_H.setText(c1.getString(c1.getColumnIndex("V_Belt_hrs")));
                LineAirFliter2_Model.setText(c1.getString(c1.getColumnIndex("LineAirFliter2_Model")));
                LineAirFliter1_Model.setText(c1.getString(c1.getColumnIndex("LineAirFliter1_Model")));
                LineAirFliter4_Model.setText(c1.getString(c1.getColumnIndex("LineAirFliter4_Model")));
                LineAirFliter3_Model.setText(c1.getString(c1.getColumnIndex("LineAirFliter3_Model")));
                LineAirFliter1_H.setText(c1.getString(c1.getColumnIndex("LineAirFliter1_h")));
                LineAirFliter2_H.setText(c1.getString(c1.getColumnIndex("LineAirFliter2_h")));
                LineAirFliter3_H.setText(c1.getString(c1.getColumnIndex("LineAirFliter3_h")));
                LineAirFliter4_H.setText(c1.getString(c1.getColumnIndex("LineAirFliter4_h")));
                Air_receiver.setText(c1.getString(c1.getColumnIndex("Air_Receiver")));
                malfunction.setText(c1.getString(c1.getColumnIndex("Malfunction")));
                Water_Separator.setText(c1.getString(c1.getColumnIndex("Water_Separator")));
                Air_Dryer.setText(c1.getString(c1.getColumnIndex("Air_Dryer")));
                Oilseparator_H.setText(c1.getString(c1.getColumnIndex("OilSeparator_hrs")));




               /*
                AIRFILTER_Clean =(CheckBox) findViewById(R.id.AIRFILTER_Clean);
                AIRFILTER_renew =(CheckBox) findViewById(R.id.AIRFILTER_renew);
                OilFILTER_AP =(CheckBox) findViewById(R.id.OilFILTER_AP);
                OilFILTER_renew =(CheckBox) findViewById(R.id.OilFILTER_renew);
                Oil_Filling_Fill =(CheckBox) findViewById(R.id.Oil_Filling_Fill);
                Oil_Filling_Check =(CheckBox) findViewById(R.id.Oil_Filling_Check);
                Oil_Filling_Renew =(CheckBox) findViewById(R.id.Oil_Filling_Renew);
                Belt_Check =(CheckBox) findViewById(R.id.Belt_Check);
                Belt_Retighten =(CheckBox) findViewById(R.id.Belt_Retighten);
                Belt_Renew =(CheckBox) findViewById(R.id.Belt_Renew);
                Oilseparator_AP =(CheckBox) findViewById(R.id.Oilseparator_AP);
                Oilseparator_renew =(CheckBox) findViewById(R.id.Oilseparator_renew);
                if (c1.getString(c1.getColumnIndex("include_Tax")).equals("0")) {
                    IncludeTax_Flag.setChecked(true);
                }
                if (c1.getString(c1.getColumnIndex("inovice_type")).equals("0")) {
                    chk_Type.setChecked(true);

                }
                chk_Type.setEnabled(true);*/
            }
            c1.close();
        }


//        IsChange = false;
        IsNew = false;
    }
    public void Save_Recod_Po() {
        TextView Total = (TextView) findViewById(R.id.et_Total);

        Integer Seq = 0;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String u = sharedPreferences.getString("UserID", "");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDate = sdf.format(new Date());

        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());


        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        long i;
        int dayOfWeek;
        Calendar c = Calendar.getInstance();
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        String q1 = "Select * From Maint Where    OrderNo='" + OrderNo.getText().toString() + "'";
        Cursor c1;
        c1 = sqlHandler.selectQuery(q1);

        if (c1 != null && c1.getCount() != 0) {
            IsNew = false;
            c1.close();
        } else {
            IsNew = true;
        }






        ContentValues cv = new ContentValues();
        cv.put("Customer", CustNo.getText().toString());
        cv.put("Date", currentDateandTime);
        cv.put("MaintTeam", "maintTeamValue");
        cv.put("ArrivalTime", "arrivalTimeValue");
        cv.put("Departure", "departureValue");
        cv.put("Preventive", Perventive.getText().toString());
        cv.put("Corrective", Corrective.getText().toString());
        cv.put("UserId", u);
        cv.put("Temp", Temp.getText().toString());
        cv.put("RunningHours", "runningHoursValue");
        cv.put("Min", Min.getText().toString());
        cv.put("Max", Max.getText().toString());
        cv.put("Malfunction", malfunction.getText().toString());
        cv.put("Remedy", Remedy.getText().toString());
        cv.put("OilFILTER_H", OilFILTER_H.getText().toString());
        if(AIRFILTER_renew.isChecked()&&AIRFILTER_Clean.isChecked())
        {
            cv.put("AirFilter", 2);
        }
        else
        {
            if(AIRFILTER_renew.isChecked())
            {
                cv.put("AirFilter", 1);
            }
            else if (AIRFILTER_Clean.isChecked())
            {
                cv.put("AirFilter", 0);
            }
        }
        if(Oilseparator_AP.isChecked()&&Oilseparator_renew.isChecked())
        {
            cv.put("OilSeparator", 2);
        }
        else
        {
            if(Oilseparator_AP.isChecked())
            {
                cv.put("OilSeparator", 1);
            }
            else if (Oilseparator_renew.isChecked())
            {
                cv.put("OilSeparator", 0);
            }
        }

        cv.put("AirFilter_hrs", AIRFILTER_H.getText().toString());

        cv.put("OilSeparator_hrs", Oilseparator_H.getText().toString());
        if(OilFILTER_AP.isChecked()&&OilFILTER_renew.isChecked())
        {
            cv.put("OilFilter", 2);
        }
        else
        {
            if(OilFILTER_AP.isChecked())
            {
                cv.put("OilFilter", 1);
            }
            else if (OilFILTER_renew.isChecked())
            {
                cv.put("OilFilter", 0);
            }
        }
        if(Oil_Filling_Check.isChecked())
        {
            cv.put("OilFilling_Check", "1");
        }else {
            cv.put("OilFilling_Check", "0");
        }
        if(Oil_Filling_Fill.isChecked())
        {
            cv.put("OilFilling_Fill", "1");
        }else {
            cv.put("OilFilling_Fill", "0");
        }
        if(Oil_Filling_Renew.isChecked())
        {
            cv.put("OilFilling_Renew", "1");
        }else {
            cv.put("OilFilling_Renew", "0");
        }


        cv.put("OilFilling_hrs", OilFilling_H.getText().toString());
        if(Belt_Renew.isChecked())
        {
            cv.put("V_Belt_Renew", "1");
        }else {
            cv.put("V_Belt_Renew", "0");
        }
        if(Belt_Check.isChecked())
        {
            cv.put("V_Belt_Check", "1");
        }else {
            cv.put("V_Belt_Check", "0");
        }
        if(Belt_Retighten.isChecked())
        {
            cv.put("V_Belt_Ret", "1");
        }else {
            cv.put("V_Belt_Ret", "0");
        }
        cv.put("V_Belt_hrs", Belt_H.getText().toString());
        cv.put("OrderNo", OrderNo.getText().toString());
        cv.put("Current_U", "1");
        cv.put("Current_V", "1");
        cv.put("Current_W", "1");
        cv.put("Water_Separator",Water_Separator.getText().toString());
        cv.put("Air_Dryer", Air_Dryer.getText().toString());
        cv.put("Air_Receiver", Air_receiver.getText().toString());
        cv.put("Remarks", Notes.getText().toString());
        cv.put("Model", tv_Model.getText().toString());
        cv.put("LineAirFliter1_Model", LineAirFliter1_Model.getText().toString());
        cv.put("LineAirFliter2_Model", LineAirFliter2_Model.getText().toString());
        cv.put("LineAirFliter3_Model",LineAirFliter3_Model.getText().toString());
        cv.put("LineAirFliter4_Model", LineAirFliter4_Model.getText().toString());
        cv.put("LineAirFliter4_h",LineAirFliter4_H.getText().toString());
        cv.put("LineAirFliter3_h", LineAirFliter3_H.getText().toString());
        cv.put("LineAirFliter2_h", LineAirFliter2_H.getText().toString());
        cv.put("LineAirFliter1_h",LineAirFliter1_H.getText().toString());
        cv.put("posted", "0");
        cv.put("Serial_No", Ser.getText().toString());




        if (IsNew == true) {
            i = sqlHandler.Insert("Maint", null, cv);
        } else {
            i = sqlHandler.Update("Maint", cv, " OrderNo ='" + OrderNo.getText().toString().replace("\u202c", "").replace("\u202d", "") + "'");
        }




        if (i > 0) {
            if (IsNew) {

                InsertLogTrans obj = new InsertLogTrans(MentnisActivity.this, "566682", SCR_ACTIONS.Insert.getValue(), OrderNo.getText().toString(), CustNo.getText().toString(), "", "0");

            } else {
                InsertLogTrans obj = new InsertLogTrans(MentnisActivity.this, "566682", SCR_ACTIONS.Modify.getValue(), OrderNo.getText().toString(), CustNo.getText().toString(), "", "0");
            }



            alertDialog.setTitle("الصيانة");
            alertDialog.setMessage("تمت عمليةالتخزين  بنجاح");
            Toast.makeText(this, "تمت عملية الحفظ بنجاح", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String Count = sharedPreferences.getString("InvCount", "0");
            Count = (SToD(Count) + 1) + "";
            editor.putString("InvCount", Count);
            editor.commit();
          //  IsChange = false;





            if (ComInfo.ComNo == Companies.beutyLine.getValue()) {

            }
            IsNew = false;
            alertDialog.setIcon(R.drawable.tick);



            // alertDialog.show();


        }

    }

    public void Select_FromSrore(View view) {


        Bundle bundle = new Bundle();
        bundle.putString("Scr", "FromStore");
        bundle.putString("Acc", CustNo.getText().toString());
        FragmentManager Manager = getFragmentManager();
        Select_StoreNo obj = new Select_StoreNo()  ;
        obj.setArguments(bundle);
        obj.show(Manager, null);






    }
    public void Set_Store(String No, String Nm ) {


            tv_Model.setText(No);
            Ser.setText(Nm);

    }
    public void gola_vist(View view) {
        Bundle bundle = new Bundle();

        bundle.putString("type", "5");
        bundle.putString("transno", OrderNo.getText().toString());
        bundle.putString("itemid", "");

        ViewVisitFrg exampleDialog = new ViewVisitFrg();
        //Search_CustomerFrq exampleDialog = new Search_CustomerFrq();
        exampleDialog.setArguments(bundle);
        exampleDialog.show(getFragmentManager(), "example dialog");
    }
    public void AddItem(View view) {
        Bundle bundle = new Bundle();


        bundle.putString("transno", OrderNo.getText().toString());


        AddItemmFragment exampleDialog = new AddItemmFragment();
        //Search_CustomerFrq exampleDialog = new Search_CustomerFrq();
        exampleDialog.setArguments(bundle);
        exampleDialog.show(getFragmentManager(), "example dialog");
    }
    private Double SToD(String str) {
        str = str.replaceAll("[^\\d.]", "");
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

    public void GetMaxPONo() {

//

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String update = preferences.getString("update", "0");

        // Toast.makeText(this,update,Toast.LENGTH_LONG).show();

  /*      final Handler _handler = new Handler();
        if(update.equals("1"))
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CallWebServices ws = new CallWebServices(Sale_InvoiceActivity.this);
                    ws.GetMaxOrder1(Integer.parseInt(UserID), 1);
                    try {
                        Integer i;
                        String q;
                        JSONObject js = new JSONObject(We_Result.Msg);
                        JSONArray js_MaxOrder = js.getJSONArray("MaxOrder");

                        Result = js_MaxOrder.get(0).toString();





                        _handler.post(new Runnable() {
                            public void run() {
                             //       Maxpo1.setText(intToString(Math.max(Integer.parseInt(getmaxN()),Integer.valueOf(Result)),7));
                           if(Integer.parseInt(getmaxN())>=Integer.valueOf(Result))
                                Maxpo1.setText(intToString(Integer.parseInt(getmaxN())+1,7));
                        else
                                Maxpo1.setText(intToString(Integer.valueOf(Result)+1,7));


                            }
                        });
                    } catch (final Exception e) {


                    }
                }
            }).start();


        }else {*/
        try {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String u = sharedPreferences.getString("UserID", "");
          //  EditText Maxpo = (EditText) findViewById(R.id.et_OrdeNo);
            // query = "SELECT  ifnull(MAX(OrderNo), 0) +1 AS no FROM Sal_invoice_Hdr where  ifnull(doctype,'1')='"+DocType.toString()+"'  and     UserID ='" + u.toString() + "'";
            query = "SELECT   COALESCE(MAX( cast(OrderNo as integer))+1, 0)  as  no FROM Maint ";
            Cursor c1 = sqlHandler.selectQuery(query);
            String max = "0";

            if (c1 != null && c1.getCount() != 0) {
                c1.moveToFirst();
                max = c1.getString(c1.getColumnIndex("no"));


                c1.close();
            }

            String max1 = "0";


            String q = " SELECT  COALESCE(MAX( cast(CustCash as integer))+1, 0) as Sales   from OrdersSitting    ";
            Cursor c = sqlHandler.selectQuery(q);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                max1 = c.getString(c.getColumnIndex("Sales"));
                c.close();
            }
            if (max1 == "") {
                max1 = "0";
            }

            if (SToD(max1) > SToD(max)) {
                max = max1;
            }

            if (ComInfo.ComNo != Companies.beutyLine.getValue()) {
                if (max.length() == 1) {

                    OrderNo.setText(intToString(Integer.valueOf(u), 2) + intToString(Integer.valueOf(max), 5));
                    Bundle bundle = new Bundle();
                    bundle.putString("Scr", "po");
                    bundle.putString("msg", "الرجاء الانتباه ، سيتم تخزين  الطلب برقم " + OrderNo.getText().toString());
                    FragmentManager Manager = getFragmentManager();
                    Pop_Confirm_Serial_From_Zero obj = new Pop_Confirm_Serial_From_Zero();
                    obj.setArguments(bundle);
                    obj.show(Manager, null);
                } else {

                    OrderNo.setText(intToString(Integer.valueOf(max), 7));
                }
            }
            //  Maxpo.setText(String.valueOf(Integer.valueOf(max)));
            // max1 = DB.GetValue(Sale_InvoiceActivity.this, "OrdersSitting", "Sales", "1=1");
            //Maxpo.setText(intToString(Integer.valueOf(max), 7));
            OrderNo.setFocusable(false);
            OrderNo.setEnabled(false);
            OrderNo.setCursorVisible(false);


           // contactList.clear();
        } catch (Exception rd) {
            // }
            // }
        }
    }
    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
    }

}