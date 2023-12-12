package com.cds_jo.GalaxySalesApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.NewPackage.Add_New_Vist;
import com.cds_jo.GalaxySalesApp.NewPackage.Cls_FreeSampleItems;
import com.cds_jo.GalaxySalesApp.NewPackage.Cls_FreeSample_Adapter;
import com.cds_jo.GalaxySalesApp.NewPackage.Cls_Subjects;
import com.cds_jo.GalaxySalesApp.NewPackage.Cls_Subjects_Adapter;
import com.cds_jo.GalaxySalesApp.NewPackage.GalaxyNewHomeActivity;
import com.cds_jo.GalaxySalesApp.NewPackage.GloblaVar;
import com.cds_jo.GalaxySalesApp.NewPackage.LocaleHelper;
import com.cds_jo.GalaxySalesApp.NewPackage.MainNewActivity;
import com.cds_jo.GalaxySalesApp.NewPackage.Pop_Free_Sample_Items;
import com.cds_jo.GalaxySalesApp.NewPackage.Pop_Subjects_Items;
import com.cds_jo.GalaxySalesApp.NewPackage.Pop_show_MEDIA;
import com.cds_jo.GalaxySalesApp.NewPackage.PostMonthlyScedule;
import com.cds_jo.GalaxySalesApp.NewPackage.ViewVisitFrg;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import Methdes.MyTextView;
import header.Header_Frag;

public class DoctorReportActivity extends AppCompatActivity {
    LinearLayout footer;
    //21624
    SqlHandler sql_Handler;
    Spinner VisitType, Location;
    EditText tv_No, spi, SampleType, VNotes, SNotes;
    MyTextView tv_CustNm;
    ImageButton CustSearch;
    Boolean IsNew;
    int PrvVisitType = 1;
    String itemkeys;
    String Year;
    String mon;
    String et_NoS;
    int PostResult;
    public ProgressDialog loadingdialog;
    String Tr_date, Cust_No;
    ListView LstItems, LstSubjects;
    ArrayList<Cls_FreeSampleItems> ArrayList;
    ArrayList<Cls_Subjects> ArraySubjects;
    MultiAutoCompleteTextView tv_VisitSummery;
    CheckBox chk_DoubleVisit;
    String OrderNo, CustNo, CustNm, Notes, Timetovisit, Datetovisit, SummeryVisit, Tr_Date, Tr_Time, DoubleVisit, UserNo;

    String Id, TransNo, TabNo, ItemNo, Desc, KeyId, SaveState, id;
 /*public void endfragment(){
   //  DoctorReportActivity f= getContext();
     android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
     FragmentManager.
  }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_report);

        /*Context  context = LocaleHelper.setLocale(DoctorReportActivity.this, "ar");
        Resources resources = context.getResources();
*/
        sql_Handler = new SqlHandler(this);
//View.LAYOUT_DIRECTION_LOCALE
        LinearLayout layroot = (LinearLayout) findViewById(R.id.layroot);
        RelativeLayout dd1 = (RelativeLayout) findViewById(R.id.dd1);
        LinearLayout Content = (LinearLayout) findViewById(R.id.Content);
        LinearLayout footer = (LinearLayout) findViewById(R.id.footer);
        LocaleHelper localeHelper = new LocaleHelper();
        if (localeHelper.getlanguage(DoctorReportActivity.this).equals("ar")) {
            layroot.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            footer.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
          //  dd1.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            //   Content.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        } else {
            layroot.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            footer.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            //   dd1.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            //  Content.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        }


        String q = "Delete from  ItemsCheck where SaveState ='0'";
        sql_Handler.executeQuery(q);

        q = "Insert INTO CustomersN(Id" +
                ",ArabicName" +
                ",EnglishName" +
                ",SpecializedNo" +
                ",CategoryNo" +
                ",CountryNo" +
                ",Mobile" +
                ",Tel" +
                ",WhatsApp" +
                ",Email" +
                ",StatesNo" +
                ",Notes" +
                ",Address" +
                ",LocX" +
                ",LocY" +
                ",VisitWeeklyCount" +
                ",VisitMounthlyCount" +
                ",DoubleVisitWeekly" +
                ",DoubleVisitMonthly" +
                ",Image" +
                ",TypeNo) values ('"
                + "0"
                + "','" + "ArabicName.get(i).toString()"
                + "','" + "EnglishName.get(i).toString()"
                + "','" + "27"
                + "','" + "27"
                + "','" + "1"
                + "','" + "EnglishName.get(i).toString()"
                + "','" + "EnglishName.get(i).toString()"
                + "','" + "EnglishName.get(i).toString()"
                + "','" + "EnglishName.get(i).toString()"
                + "','" + "EnglishName.get(i).toString()"
                + "','" + "EnglishName.get(i).toString()"
                + "','" + "EnglishName.get(i).toString()"
                + "','" + "EnglishName.get(i).toString()"
                + "','" + "EnglishName.get(i).toString()"
                + "','" + "EnglishName.get(i).toString()"
                + "','" + "EnglishName.get(i).toString()"
                + "','" + "EnglishName.get(i).toString()"
                + "','" + "EnglishName.get(i).toString()"
                + "','" + "EnglishName.get(i).toString()"
                + "','" + "EnglishName.get(i).toString()"
                + "')";
        //   sql_Handler.executeQuery(q);


        Fragment frag = new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1, frag).commit();


//        fragmentManager.popBackStack();
        ArrayList = new ArrayList<>();
        ArrayList.clear();

        chk_DoubleVisit = (CheckBox) findViewById(R.id.chk_DoubleVisit);

        ArraySubjects = new ArrayList<>();
        ArraySubjects.clear();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd", Locale.ENGLISH);
        Tr_date = sdf.format(new Date());

        tv_VisitSummery = (MultiAutoCompleteTextView) findViewById(R.id.tv_VisitSummery);
        SimpleDateFormat StartTime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        Tr_Time = StartTime.format(new Date());
        LstItems = (ListView) findViewById(R.id.LstItems);
        LstSubjects = (ListView) findViewById(R.id.LstSubjects);
        tv_No = (EditText) findViewById(R.id.et_No);
      //  spi = (EditText) findViewById(R.id.et_spi);
       // SampleType = (EditText) findViewById(R.id.et_SampleType);
        VNotes = (EditText) findViewById(R.id.et_VNotes);
       // SNotes = (EditText) findViewById(R.id.et_SNotes);
       // SampleType = (EditText) findViewById(R.id.et_SampleType);
        //SampleType = (EditText) findViewById(R.id.et_SampleType);
       // CustSearch = (ImageButton) findViewById(R.id.btn_Cust_Search);
        //VisitType = (Spinner) findViewById(R.id.sp_VisitType);
       // Location = (Spinner) findViewById(R.id.sp_Location);

        tv_CustNm = (MyTextView) findViewById(R.id.tv_CustNm);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Cust_No = sharedPreferences.getString("CustNo", "");
        tv_CustNm.setText(sharedPreferences.getString("CustNm", "اسم العيادة غير مدخل").toString());

        GetMaxRecNo();
        // FillVisitType();
        //  FillLocation();
        IsNew = true;

        EditText et_No = (EditText) findViewById(R.id.et_No);
        et_NoS = et_No.getText().toString();


    }

    public void btn_back(View view) {
        Intent k = new Intent(this, GalaxyNewHomeActivity.class);
        startActivity(k);
    }

    public void btn_share(View view) {

        String transNo = returntransno();
        String post = DB.GetValue(DoctorReportActivity.this, "DoctorReport", "Posted", "OrderNo ='" + transNo + "'");

        if (!post.equals("-1")) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    DoctorReportActivity.this).create();
            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.Update)));

            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.UpdateNotAllowedafterpost)));

            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setButton(String.valueOf(getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        } else {

            String xw="";
            for(int x=0 ;x<ArraySubjects.size();x++)
             xw=ArraySubjects.get(x).getSubjectID();
            File imgFile = new File("//sdcard/Android/PDF/"+xw+".pdf");
            try {
                if (imgFile.exists()) {

                    String urls=DB.GetValue(DoctorReportActivity.this,"CheackOpenPdf","ifnull(count(*),0)","Item_No="+xw+" and OrderNo="+tv_No.getText().toString());
                    if(urls.equals("0")) {
                        Toast.makeText(DoctorReportActivity.this,"يجب فتج البرشور قبل اغلاق الزيارة",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
            catch (Exception ex){}
            We_Result.Msg = " ";
            We_Result.ID = -1;

            if (IsNew == true) {
                AlertDialog alertDialog = new AlertDialog.Builder(
                        this).create();
                alertDialog.setTitle(String.valueOf(getResources().getString(R.string.medicalvisits)));
                alertDialog.setMessage(String.valueOf(getResources().getString(R.string.saveandpost)));

                alertDialog.setIcon(R.drawable.delete);
                alertDialog.setButton(String.valueOf(getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
                return;
            }

            OrderNo = CustNo = CustNm = Notes = SummeryVisit = Tr_Date = Tr_Time = DoubleVisit = UserNo = "";
            final String str;
            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

            sql_Handler = new SqlHandler(this);


            ArrayList<Cls_DoctorReport> objlist;
            objlist = new ArrayList<Cls_DoctorReport>();
            objlist.clear();

            PostPlan();
            String query = "  select distinct OrderNo,Timetovisit, Datetovisit , CustNo,CustNm,VNotes,SNotes,Tr_Date,Tr_Time,DoubleVisit,UserNo   from DoctorReport   " +
                    "where OrderNo ='" + tv_No.getText().toString() + "'";
            Cursor c1 = sql_Handler.selectQuery(query);
            if (c1 != null && c1.getCount() > 0) {
                if (c1.moveToFirst()) {
                    OrderNo = c1.getString(c1.getColumnIndex("OrderNo"));
                    CustNo = c1.getString(c1.getColumnIndex("CustNo"));
                    CustNm = c1.getString(c1.getColumnIndex("CustNm"));
                    Notes = c1.getString(c1.getColumnIndex("VNotes"));
                    SummeryVisit = c1.getString(c1.getColumnIndex("SNotes"));
                    Tr_Date = c1.getString(c1.getColumnIndex("Tr_Date"));
                    Tr_Time = c1.getString(c1.getColumnIndex("Tr_Time"));
                    DoubleVisit = c1.getString(c1.getColumnIndex("DoubleVisit"));
                    UserNo = c1.getString(c1.getColumnIndex("UserNo"));

                    Timetovisit = c1.getString(c1.getColumnIndex("Timetovisit"));
                    Datetovisit = c1.getString(c1.getColumnIndex("Datetovisit"));


                    if (c1.getString(c1.getColumnIndex("DoubleVisit")).toString().equalsIgnoreCase("1")) {
                        chk_DoubleVisit.setChecked(true);
                    } else {
                        chk_DoubleVisit.setChecked(false);
                    }

                }
                c1.close();
            }

            String NDate = DB.GetValue(DoctorReportActivity.this, "NewVisit", "DatetoSave", " OrderNo='" + tv_No.getText().toString() + "'");

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("OrderNo", OrderNo);
                jsonObject.put("CustNo", CustNo);
                jsonObject.put("CustNm", CustNm);
                jsonObject.put("VNotes", Notes);
                jsonObject.put("SNotes", SummeryVisit);
                jsonObject.put("Tr_Date", NDate);
                jsonObject.put("Tr_Time", Tr_Time);
                jsonObject.put("DoubleVisit", DoubleVisit);
                jsonObject.put("UserNo", UserNo);
                jsonObject.put("newdate", Datetovisit);
                jsonObject.put("newtime", Timetovisit);


            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            post = DB.GetValue(DoctorReportActivity.this, "DoctorReport", "Posted", "OrderNo ='" + transNo + "'");
            String itemkeys = "";
            String customercat = "";
            String Objectivesofvisit = "";
            String resultofthevisit = "";
            String visttype = "";
            query = "  select distinct Id  , TransNo,TabNo,ItemNo,Desc,KeyId,SaveState  from ItemsCheck   " +
                    "where TransNo ='" + tv_No.getText().toString() + "' and SaveState='1'";
            c1 = sql_Handler.selectQuery(query);
            if (c1 != null && c1.getCount() > 0) {
                // for (int i=0;i< c1.getCount();i++){
                if (c1.moveToFirst()) {
                    do {
                        Id = c1.getString(c1.getColumnIndex("Id"));
                        TransNo = c1.getString(c1.getColumnIndex("TransNo"));
                        TabNo = c1.getString(c1.getColumnIndex("TabNo"));
                        ItemNo = c1.getString(c1.getColumnIndex("ItemNo"));
                        Desc = c1.getString(c1.getColumnIndex("Desc"));
                        KeyId = c1.getString(c1.getColumnIndex("KeyId"));
                        SaveState = c1.getString(c1.getColumnIndex("SaveState"));
                        id = DB.GetValue(DoctorReportActivity.this, "AreasN", "Id", "DescrA='" + Desc + "' or DescrE='" + Desc + "' and TableNo='" + TabNo + "'");


                        if (!id.equals("-1")) {
   /* if (TabNo.equals("7")) {
        if (itemkeys.equals(""))
            itemkeys += id;
        else
            itemkeys += "," + id;
    } else */
                            if (TabNo.equals("5")) {
                                // resultofthevisit += id + ",";

                                if (resultofthevisit.equals(""))
                                    resultofthevisit += id;
                                else
                                    resultofthevisit += "," + id;
                            } else if (TabNo.equals("1")) {
                                if (visttype.equals(""))
                                    visttype += id;
                                else
                                    visttype += "," + id;
                            } else if (TabNo.equals("3")) {
                                if (customercat.equals(""))
                                    customercat += id;
                                else
                                    customercat += "," + id;
                            } else if (TabNo.equals("4")) {
                                if (Objectivesofvisit.equals(""))
                                    Objectivesofvisit += id;
                                else
                                    Objectivesofvisit += "," + id;
                            }
                        }
                    } while (c1.moveToNext());
                }
                // }

            }


            final JSONObject jsonObjectLockUps = new JSONObject();
            try {


                //  jsonObject.put("itemkeys",itemkeys);
                jsonObject.put("customercat", customercat);
                jsonObject.put("Objectivesofvisit", Objectivesofvisit);
                jsonObject.put("resultofthevisit", resultofthevisit);
                jsonObject.put("visttype", visttype);


             /*   jsonObjectLockUps.put("Id", Id );
                jsonObjectLockUps.put("TransNo", TransNo );
                jsonObjectLockUps.put("TabNo", TabNo);
                jsonObjectLockUps.put("ItemNo",ItemNo);
                jsonObjectLockUps.put("Desc", Desc);
                jsonObjectLockUps.put("KeyId",KeyId);
                jsonObjectLockUps.put("SaveState",SaveState);*/

            } catch (JSONException ex) {
                ex.printStackTrace();
            }
//String itemkeyesJSON='"'+itemkeys+'"'+itemkeys

            FillListSubjectItems();

            final String json = new Gson().toJson(ArrayList);
            final String json1 = new Gson().toJson(ArraySubjects);
            // final String jsonL = new Gson().toJson(jsonObjectLockUps);
            str = jsonObject.toString() + json + json1;

            loadingdialog = ProgressDialog.show(DoctorReportActivity.this, String.valueOf(getResources().getString(R.string.PleaseWait)), String.valueOf(getResources().getString(R.string.postprogres)), true);
            loadingdialog.setCancelable(false);
            loadingdialog.setCanceledOnTouchOutside(false);
            loadingdialog.show();


            final Handler _handler = new Handler();

            System.out.print(str+SummeryVisit + "thhhhhis");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    CallWebServices ws = new CallWebServices(DoctorReportActivity.this);
                    ws.SaveDoctorReportMed(str, SummeryVisit/*,jsonL*/);
                    try {

                        if (We_Result.ID > 0) {
                            ContentValues cv = new ContentValues();
                            TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
                            cv.put("Posted", We_Result.ID);
                            long i;
                            i = sql_Handler.Update("DoctorReport", cv, "OrderNo='" + tv_No.getText().toString() + "'");

                            _handler.post(new Runnable() {
                                public void run() {
                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            DoctorReportActivity.this).create();
                                    alertDialog.setTitle(String.valueOf(getResources().getString(R.string.postmediacalvist)));
                                    alertDialog.setMessage(String.valueOf(getResources().getString(R.string.PostCompleteSuccfully2))/*+"json :"+str*/);
                                    alertDialog.setIcon(R.drawable.tick);
                                    alertDialog.setButton(String.valueOf(getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {


                                        }
                                    });
                                    loadingdialog.dismiss();
                                    alertDialog.show();
                                    alertDialog.show();
                                    DoNew();
                                    GetMaxRecNo();

                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DoctorReportActivity.this);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("close", "1");
                                    editor.apply();

                                    Intent i = new Intent(DoctorReportActivity.this, MainNewActivity.class);
                                    i.putExtra("close","1");
                                    startActivity(i);


                                   //   ((MainNewActivity) getApplicationContext()).EndRound();

                                }
                            });
                        }
                        else {

                            loadingdialog.dismiss();
                            _handler.post(new Runnable() {
                                public void run() {
                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            DoctorReportActivity.this).create();
                                    alertDialog.setTitle(String.valueOf(getResources().getString(R.string.PostNotCompleteSuccfully)) + "   " + We_Result.ID + "");
                                    alertDialog.setMessage(We_Result.Msg.toString());
                                    alertDialog.setIcon(R.drawable.tick);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    alertDialog.show();

                                    alertDialog.setIcon(R.drawable.delete);
                                    alertDialog.setMessage(String.valueOf(getResources().getString(R.string.PostNotCompleteSuccfully)) + "    ");
                                }
                            });
                        }

                    } catch (final Exception e) {
                        loadingdialog.dismiss();
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        DoctorReportActivity.this).create();
                                alertDialog.setTitle(String.valueOf(getResources().getString(R.string.ConnectError)));
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
    }


    public void gola_vist(View view) {
        Bundle bundle = new Bundle();
        et_NoS = returntransno();
        bundle.putString("type", "5");
        bundle.putString("transno", et_NoS);
        bundle.putString("itemid", "");

        ViewVisitFrg exampleDialog = new ViewVisitFrg();
        //Search_CustomerFrq exampleDialog = new Search_CustomerFrq();
        exampleDialog.setArguments(bundle);
        exampleDialog.show(getFragmentManager(), "example dialog");
    }

    public void detals_vist(View view) {
        Bundle bundle = new Bundle();
        et_NoS = returntransno();

        bundle.putString("type", "1");
        bundle.putString("transno", et_NoS);
        bundle.putString("itemid", "");

        ViewVisitFrg exampleDialog = new ViewVisitFrg();
        exampleDialog.setArguments(bundle);
        exampleDialog.show(getFragmentManager(), "example dialog");
    }

    public void type_Customer(View view) {
        Bundle bundle = new Bundle();
        et_NoS = returntransno();

        bundle.putString("type", "4");
        bundle.putString("transno", et_NoS);
        bundle.putString("itemid", "");

        ViewVisitFrg exampleDialog = new ViewVisitFrg();
        exampleDialog.setArguments(bundle);
        exampleDialog.show(getFragmentManager(), "example dialog");
    }

    public void type_vist(View view) {
        Bundle bundle = new Bundle();
        et_NoS = returntransno();

        bundle.putString("type", "2");
        bundle.putString("itemid", "");

        bundle.putString("transno", et_NoS);

        ViewVisitFrg exampleDialog = new ViewVisitFrg();
        exampleDialog.setArguments(bundle);
        exampleDialog.show(getFragmentManager(), "example dialog");

    }

    public void ShowRecord() {
        String q = "Select distinct   ifnull(DoubleVisit,0) as DoubleVisit ,  CustNo, CustNm  , VNotes ,SNotes  " +
                "  ,Tr_Date , Tr_Time   ,UserNo , COALESCE(DoctorReport.Posted, -1)  as Post    " +
                "  from DoctorReport  where DoctorReport.OrderNo = '" + tv_No.getText().toString() + "'";
        SqlHandler sqlHandler = new SqlHandler(this);

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                VNotes.setText(c1.getString(c1.getColumnIndex("VNotes")));
                tv_VisitSummery.setText(c1.getString(c1.getColumnIndex("SNotes")));
                tv_CustNm.setText(c1.getString(c1.getColumnIndex("CustNm")));
                Cust_No = c1.getString(c1.getColumnIndex("CustNo"));

                if (c1.getString(c1.getColumnIndex("DoubleVisit")).equalsIgnoreCase("1")) {
                    chk_DoubleVisit.setChecked(true);
                } else {
                    chk_DoubleVisit.setChecked(false);
                }
                IsNew = false;
            }
            c1.close();
        }

        FillListFreeItems();
        FillListSubjectItems();

    }

    private void FillListFreeItems() {
        ArrayList.clear();
        String q = " select  FreeSample.Unit  , Unites.UnitName, itemsn.ItemNameA,itemsn.ItemNameE, FreeSample.ItemNo,FreeSample.Qty,FreeSample.Unit,FreeSample.Oprand   " +
                " from FreeSample     left join itemsn on itemsn.ItemNo =  FreeSample.ItemNo  left join Unites on Unites.Unitno=  FreeSample.Unit                                                " +
                " where FreeSample.OrderNo ='" + tv_No.getText().toString() + "'";

        SqlHandler sqlHandler = new SqlHandler(this);

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_FreeSampleItems obj = new Cls_FreeSampleItems();
                    obj.setItemNo(c1.getString(c1.getColumnIndex("ItemNo")));
                    LocaleHelper localeHelper = new LocaleHelper();
                    if (LocaleHelper.getlanguage(DoctorReportActivity.this).equals("ar"))
                        obj.setItemNm(c1.getString(c1.getColumnIndex("ItemNameA")));
                    else
                        obj.setItemNm(c1.getString(c1.getColumnIndex("ItemNameE")));

                    obj.setQty(c1.getString(c1.getColumnIndex("Qty")));
                    obj.setUnitNo(c1.getString(c1.getColumnIndex("Unit")));
                    obj.setUnitNm(c1.getString(c1.getColumnIndex("UnitName")));
                    obj.setOprand(c1.getString(c1.getColumnIndex("Oprand")));
                    ArrayList.add(obj);
                } while (c1.moveToNext());
            }
            c1.close();
        }


        showList();
    }

    private void FillListSubjectItems() {
        ArraySubjects.clear();
        String q = " select itemsn.ItemNameA,itemsn.ItemNameE, VisitSubjects.ItemNo from VisitSubjects     " +
                " left join itemsn on itemsn.Id =  VisitSubjects.ItemNo      " +
                " where VisitSubjects.OrderNo ='" + tv_No.getText().toString() + "'";

        SqlHandler sqlHandler = new SqlHandler(this);

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {

            if (c1.moveToFirst()) {
                do {
                    Cls_Subjects obj = new Cls_Subjects();
                    obj.setSubjectID(c1.getString(c1.getColumnIndex("ItemNo")));
                    LocaleHelper localeHelper = new LocaleHelper();
                    if (LocaleHelper.getlanguage(DoctorReportActivity.this).equals("ar"))
                        obj.setSubjectDesc(c1.getString(c1.getColumnIndex("ItemNameA")));
                    else
                        obj.setSubjectDesc(c1.getString(c1.getColumnIndex("ItemNameE")));
                    id = "";
                    itemkeys = "";
                    String query = "  select distinct Id  , TransNo,TabNo,ItemNo,Desc,KeyId,SaveState  from ItemsCheck   " +
                            "where TransNo ='" + tv_No.getText().toString() + "' and SaveState='1'and ItemNo='" + ItemNo + "'";

                    EditText tv_No = (EditText) findViewById(R.id.et_No);
//i.TabNo = '7' and
                    query = "select distinct KeyId from ItemsCheck i left join AreasN u on  i.ItemNo= u.ItemNo where i.TabNo = '7' and  i.ItemNo = '" + c1.getString(c1.getColumnIndex("ItemNo")) + "' and TransNo='" + tv_No.getText().toString() + "'";

                    Cursor c2 = sql_Handler.selectQuery(query);
                    if (c2 != null && c2.getCount() > 0) {
                        // for (int i=0;i< c1.getCount();i++){
                        if (c2.moveToFirst()) {
                            do {
                                /* *//* Id = c2.getString(c2.getColumnIndex("Id"));
                                TransNo = c2.getString(c2.getColumnIndex("TransNo"));
                                TabNo = c2.getString(c2.getColumnIndex("TabNo"));
                                ItemNo = c2.getString(c2.getColumnIndex("ItemNo"));
                                Desc = c2.getString(c2.getColumnIndex("Desc"));
                                KeyId = c2.getString(c2.getColumnIndex("KeyId"));
                                SaveState = c2.getString(c2.getColumnIndex("SaveState"));*//*
                                tv_No = (EditText) findViewById(R.id.et_No);

                                id = DB.GetValue(DoctorReportActivity.this, "AreasN", "ItemNo", "DescrA='" + Desc + "' or DescrE='" + Desc + "' and ItemNo='"+ItemNo+"'");
                                id = DB.GetValue(DoctorReportActivity.this, "ItemsCheck", "KeyId", "DescrA='" + Desc + "' or DescrE='" + Desc +"' and TransNo='"+tv_No.getText()+"'");

                                String id2 = DB.GetValue(DoctorReportActivity.this, "ItemsKeysN", "ItemId", "KeyId='"+ItemNo+"'");
                       */
                                id = c2.getString(c2.getColumnIndex("KeyId"));
                                //  if (TabNo.equals("7")&&!id.equals("-1")&&!id2.equals("-1"))
                                if (itemkeys.equals(""))
                                    itemkeys += id;
                                else
                                    itemkeys += "," + id;


                            } while (c2.moveToNext());
                        }
                        // }

                    }

                    obj.setKeys(itemkeys);
                    itemkeys = "";
                    id = "";
                    //   obj.setKeys(itemkeys.replace(",\"","\""));
                    ArraySubjects.add(obj);
                    String gg = ArraySubjects.toString();
                } while (c1.moveToNext());
            }
            c1.close();
        }
        ShowListSubjects();
    }

    public void Set_Order(String No) {
        tv_No.setText(No);
        ShowRecord();
    }

    public void btn_search_Recv(View view) {
        returntransno();
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "DoctorReport");
        FragmentManager Manager = getFragmentManager();
        DoctorReportSearchActivity obj = new DoctorReportSearchActivity();
        obj.setArguments(bundle);
        obj.show(Manager, null);

    }

    public void Delete_Record_PO() {


        long i;

        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setTitle(String.valueOf(getResources().getString(R.string.medicalvisits)));

        i = sql_Handler.Delete("DoctorReport", "OrderNo='" + tv_No.getText().toString() + "'");
        i = sql_Handler.Delete("FreeSample", "OrderNo='" + tv_No.getText().toString() + "'");
        i = sql_Handler.Delete("VisitSubjects", "OrderNo='" + tv_No.getText().toString() + "'");

        i = sql_Handler.Delete("ItemsCheck", "TransNo='" + tv_No.getText().toString() + "'");


        if (i > 0) {
            DoNew();
            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.deleteone)));
            alertDialog.setIcon(R.drawable.tick);
        } else {
            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.deleteerror)));
            alertDialog.setIcon(R.drawable.delete);
        }

        alertDialog.setButton(String.valueOf(getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


            }
        });


        alertDialog.show();


    }

    public void btn_delete(View view) {
        EditText pono = (EditText) findViewById(R.id.et_No);
        String q = "SELECT Distinct *  from  DoctorReport where   Posted !='-1' AND   OrderNo ='" + pono.getText().toString() + "'";
        Cursor c1 = sql_Handler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.Visits)));
            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.UpdateNotAllowedafterpost)));
            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
            c1.close();
            return;
        } else {


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.medicalvisits)));
            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.deleteconf)));

            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setPositiveButton(String.valueOf(getResources().getString(R.string.yes)), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Delete_Record_PO();
                    Intent i = new Intent(DoctorReportActivity.this, MainNewActivity.class);
                    i.putExtra("close","1");
                    startActivity(i);
                }
            });
            alertDialog.setNegativeButton(String.valueOf(getResources().getString(R.string.no)), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Intent i = new Intent(DoctorReportActivity.this, MainNewActivity.class);
                    i.putExtra("close","1");
                    startActivity(i);
                }
            });
            alertDialog.show();
        }

    }

    public void btn_new(View view) {
        DoNew();
    }

    public void DoNew() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DoctorReportActivity.this);

        GetMaxRecNo();
        IsNew = true;
        VNotes.setText("");
        tv_VisitSummery.setText("");
        chk_DoubleVisit.setChecked(false);
        Cust_No = sharedPreferences.getString("CustNo", "");
        tv_CustNm.setText(sharedPreferences.getString("CustNm", "اسم العيادة غير مدخل").toString());
        FillListFreeItems();
        FillListSubjectItems();

        String q = "Delete from  ItemsCheck where SaveState ='0'";
        sql_Handler.executeQuery(q);

    }

    public void Save_Recod_Po() throws ParseException {


        String VT = DB.GetValue(DoctorReportActivity.this, "ItemsCheck", "Id", "TabNo='1' and TransNo='" + tv_No.getText().toString() + "'");
        String VG = DB.GetValue(DoctorReportActivity.this, "ItemsCheck", "Id", "TabNo='4' and TransNo='" + tv_No.getText().toString() + "'");
        String VR = DB.GetValue(DoctorReportActivity.this, "ItemsCheck", "Id", "TabNo='5' and TransNo='" + tv_No.getText().toString() + "'");

        //Cls_Subjects obj = new Cls_Subjects();
        if (VT.equals("-1") || VG.equals("-1") || VR.equals("-1")||ArraySubjects.size()==0) {
            AlertDialog  alertDialog = new AlertDialog.Builder(
                    this).create();
            // alertDialog.setTitle(String.valueOf(getResources().getString(R.string.medicalvisits)));

            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.AddNotCompleteSucc)));

            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.errorselect)));
            alertDialog.setIcon(R.drawable.delete);
            // Toast.makeText(DoctorReportActivity.this, String.valueOf(getResources().getString(R.string.errorselect)), Toast.LENGTH_LONG).show();
            alertDialog.setButton(String.valueOf(getResources().getString(R.string.yes)), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // btn_print(view);
                }
            });

            // Showing Alert Message
            alertDialog.show();
        } else{

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String Tr_dateVistnew = preferences.getString("dateofvist", "");
            String Tr_TimeVistnew = preferences.getString("timeofvist", "");

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();

            String NTime = DB.GetValue(DoctorReportActivity.this, "NewVisit", "Time", "OrderNo='" + tv_No.getText().toString() + "'");
            String NDate = DB.GetValue(DoctorReportActivity.this, "NewVisit", "DatetoSave", " OrderNo='" + tv_No.getText().toString() + "'");


          /*  Date date1=new SimpleDateFormat("yyyy/MMM/dd").parse(NDate);
            NDate= String.valueOf(date1);;

*/
      /*      SimpleDateFormat format = new SimpleDateFormat("yyyy/MMM/dd");

                Date date = format.parse(NDate);

                NDate = format.format(date);
*/

           // NTime= sdf.format(myCalendar.getTime()));


            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            ContentValues cv = new ContentValues();
            cv.put("VType", "1");
            cv.put("OrderNo", tv_No.getText().toString());
            cv.put("CustNo", Cust_No);
            cv.put("CustNm", tv_CustNm.getText().toString());
            cv.put("LocatNo", "1");
            cv.put("Sp1", "0");
            cv.put("SampleType", "");
            cv.put("VNotes", VNotes.getText().toString());
            cv.put("SNotes", tv_VisitSummery.getText().toString());
            cv.put("Tr_Date", Tr_date);
            cv.put("Tr_Time", Tr_Time);
            cv.put("Posted", "-1");
            cv.put("Timetovisit", NTime);
            cv.put("Datetovisit", NDate);
            if (chk_DoubleVisit.isChecked()) {
                cv.put("DoubleVisit", "1");
            } else {
                cv.put("DoubleVisit", "0");
            }
            cv.put("UserNo", sharedPreferences.getString("UserID", ""));
            long i;

            if (IsNew == true) {
                i = sql_Handler.Insert("DoctorReport", null, cv);

            } else {
                i = sql_Handler.Update("DoctorReport", cv, "OrderNo='" + tv_No.getText().toString() + "'");
            }
            AddPlan();
            String q = "Delete from  FreeSample where OrderNo ='" + tv_No.getText().toString() + "'";
            sql_Handler.executeQuery(q);


            for (int x = 0; x < ArrayList.size(); x++) {
                Cls_FreeSampleItems obj = new Cls_FreeSampleItems();
                obj = ArrayList.get(x);


                cv = new ContentValues();
                cv.put("OrderNo", tv_No.getText().toString());
                cv.put("ItemNo", obj.getItemNo());
                cv.put("Qty", obj.getQty());
                cv.put("Unit", obj.getUnitNm());
                cv.put("Oprand", obj.getOprand());
                i = sql_Handler.Insert("FreeSample", null, cv);
            }


            q = "Delete from  VisitSubjects where OrderNo ='" + tv_No.getText().toString() + "'";
            sql_Handler.executeQuery(q);

            // if()

            for (int x = 0; x < ArraySubjects.size(); x++) {
                Cls_Subjects obj = new Cls_Subjects();
                obj = ArraySubjects.get(x);
                cv = new ContentValues();
                cv.put("OrderNo", tv_No.getText().toString());
                cv.put("ItemNo", obj.getSubjectID());

                i = sql_Handler.Insert("VisitSubjects", null, cv);


//            SqlHandler   sqlHandler = new SqlHandler(DoctorReportActivity.this);


                EditText et_No = (EditText) findViewById(R.id.et_No);
                sql_Handler.executeQuery("Update ItemsCheck set SaveState='1' Where TransNo='" + et_No.getText() + "'");
                //}

            }


            alertDialog = new AlertDialog.Builder(
                    this).create();
            // alertDialog.setTitle(String.valueOf(getResources().getString(R.string.medicalvisits)));

            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.medicalvisits)));
            if (i > 0) {
                preferences = PreferenceManager.getDefaultSharedPreferences(DoctorReportActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("dateofvist", "");
                editor.putString("timeofvist", "");
                editor.apply();

                alertDialog.setMessage(String.valueOf(getResources().getString(R.string.AddCompleteSucc)));
                alertDialog.setIcon(R.drawable.tick);
           /*  GetMaxRecNo();
            showList();
            DoNew();*/
                editor = sharedPreferences.edit();

                IsNew = false;
                // DoNew();
            } else {
                alertDialog.setMessage(String.valueOf(getResources().getString(R.string.AddNotCompleteSucc)));
                alertDialog.setIcon(R.drawable.delete);


            }
            // Setting OK Button
            alertDialog.setButton(String.valueOf(getResources().getString(R.string.yes)), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // btn_print(view);
                }
            });

            // Showing Alert Message
            alertDialog.show();
            //GetMaxRecNo();

        }
    }

    public void AddPlan() {


        Long i;
        int j = 0;
        String query = " Select  *  from NewVisit where OrderNo ='" + tv_No.getText().toString() + "'";

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SqlHandler sqlHandler = new SqlHandler(DoctorReportActivity.this);


        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    String[] Date1;


                    //   String Year = DB.GetValue(DoctorReportActivity.this, "Month_Dates", "CurrentYear", "TodayDate ='" + c1.getString(c1.getColumnIndex("Date")) + "'");
                    // String mon = DB.GetValue(DoctorReportActivity.this, "Month_Dates", "CurrentMonth", "TodayDate ='" + c1.getString(c1.getColumnIndex("Date")) + "'");
                    Date1 =c1.getString(c1.getColumnIndex("Date")).split("/");
                    String Year=Date1[2];
                    String mon=Date1[1];
                    String IsNew = DB.GetValue(DoctorReportActivity.this, "Monthly_Schedule", "count(*)", "Today_Date ='" + c1.getString(c1.getColumnIndex("Date")) + "' and Period_No ='" + c1.getString(c1.getColumnIndex("Period")) + "'");
                    if (Integer.parseInt(IsNew) <= 0) {
                        ContentValues cv = new ContentValues();
                        cv.put("Today_Date", c1.getString(c1.getColumnIndex("Date")));
                        cv.put("Period_No", c1.getString(c1.getColumnIndex("Period")));
                        // cv.put("Area_No", AllCountry.get(position).getID().toString());
                        cv.put("User_No", sharedPreferences.getString("UserID", ""));
                        cv.put("TrYear", Year);
                        cv.put("TrMonth", mon);
                        cv.put("DoubleVisit", "0");
                        cv.put("Posted", "1");
                        // cv.put("VisitCustomers", AllCustomers.get(position).getID().toString());

                        i = sqlHandler.Insert("Monthly_Schedule", null, cv);
                    }
                    ContentValues cv1 = new ContentValues();
                    cv1.put("Date", c1.getString(c1.getColumnIndex("Date")));
                    cv1.put("Period", c1.getString(c1.getColumnIndex("Period")));
                    cv1.put("itemno", c1.getString(c1.getColumnIndex("AreaNo")));
                    cv1.put("UserId", sharedPreferences.getString("UserID", ""));
                    cv1.put("Post", "1");
                    cv1.put("TabletNo", "2");
                    // cv.put("VisitCustomers", AllCustomers.get(position).getID().toString());

                    i = sqlHandler.Insert("PlanMonthlyLookups", null, cv1);

                    ContentValues cv2 = new ContentValues();
                    cv2.put("Date", c1.getString(c1.getColumnIndex("Date")));
                    cv2.put("Period", c1.getString(c1.getColumnIndex("Period")));
                    cv2.put("itemno", c1.getString(c1.getColumnIndex("CustNo")));
                    cv2.put("UserId", sharedPreferences.getString("UserID", ""));
                    cv2.put("Post", "1");
                    cv2.put("TabletNo", "3");
                    // cv.put("VisitCustomers", AllCustomers.get(position).getID().toString());

                    i = sqlHandler.Insert("PlanMonthlyLookups", null, cv2);

                    String  q1 = "Update Month_Dates set entry='1' Where  TodayDate = '" +  c1.getString(c1.getColumnIndex("Date")) + "' and PeriodNo ='" + c1.getString(c1.getColumnIndex("Period")) + "'";
                    sqlHandler.executeQuery(q1);


                } while (c1.moveToNext());

            }
            c1.close();
        }



        //i = sqlHandler.Update("Monthly_Schedule", cv, "Today_Date='" + SelectedDate.getDate() + "' and Period_No='" + SelectedDate.getPeriodNo() + "' and User_No='" + UserID + "'");


    }

    public void btn_save_po(View view) {
        String transNo = returntransno();
        String post = DB.GetValue(DoctorReportActivity.this, "DoctorReport", "Posted", "OrderNo ='" + transNo + "'");

        if (!post.equals("-1")) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    DoctorReportActivity.this).create();
            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.Update)));

            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.UpdateNotAllowedafterpost)));


            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setButton(String.valueOf(getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        } else {

            tv_No.setError(null);
            tv_CustNm.setError(null);
            VNotes.setError(null);
            if (tv_No.getText().toString().equalsIgnoreCase("")) {
                tv_No.setError("required!");
                tv_No.requestFocus();
                return;
            }

            if (tv_CustNm.getText().toString().length() == 0) {
                tv_CustNm.setError("required!");
                tv_CustNm.requestFocus();
                return;
            }


            AlertDialog.Builder alert_Dialog = new AlertDialog.Builder(this);
            alert_Dialog.setTitle(String.valueOf(getResources().getString(R.string.medicalvisits)));
            alert_Dialog.setMessage(String.valueOf(getResources().getString(R.string.DoYouWantToContinSave)) + "؟");
            alert_Dialog.setIcon(R.drawable.save);
            alert_Dialog.setPositiveButton(String.valueOf(getResources().getString(R.string.yes)), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    try {
                        Save_Recod_Po();
                    } catch (ParseException e) {
                       Toast.makeText(getApplicationContext(),"مشكلة في الحفظ", Toast.LENGTH_LONG).show();

                    }
                }
            });
            alert_Dialog.setNegativeButton(String.valueOf(getResources().getString(R.string.no)), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alert_Dialog.show();
        }
    }

    public void btn_SearchCust(View v) {
        ImageButton ib = (ImageButton) v;

        if (ib == CustSearch) {
            if (PrvVisitType == 2) {

                Bundle bundle = new Bundle();
                bundle.putString("Scr", "DoctorReprot");
                bundle.putString("PrvVisitType", PrvVisitType + "");
                FragmentManager Manager = getFragmentManager();
                Select_Customer obj = new Select_Customer();
                obj.setArguments(bundle);
                obj.show(Manager, null);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("Scr", "DoctorReprot");
                FragmentManager Manager = getFragmentManager();
                Select_Doctor obj = new Select_Doctor();
                obj.setArguments(bundle);
                obj.show(Manager, null);
            }
        }
    }

    public void Set_Cust(final String No, String Nm) {

        Cust_No = No;
        tv_CustNm.setText(Nm);
        tv_CustNm.setError(null);
    }


    public String returntransno() {

        EditText et_No = (EditText) findViewById(R.id.et_No);
        et_NoS = et_No.getText().toString();

        return et_NoS;
    }

    public void PostPlan()
    {

        String query = " Select    from NewVisit where OrderNo ='" + tv_No.getText().toString() + "'";
        String Date = DB.GetValue(DoctorReportActivity.this, "NewVisit", "Date", "OrderNo ='" + tv_No.getText().toString() + "'");
        Year = DB.GetValue(DoctorReportActivity.this, "Month_Dates", "CurrentYear", "TodayDate ='" + Date + "'");
        mon = DB.GetValue(DoctorReportActivity.this, "Month_Dates", "CurrentMonth", "TodayDate ='" + Date + "'");

        final Handler _handler = new Handler();

        // Toast.makeText(getApplicationContext(),str, Toast.LENGTH_LONG).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                PostMonthlyScedule obj = new PostMonthlyScedule(DoctorReportActivity.this);
                PostResult = 1;
                obj.Post_Scedule(mon, Year);
                try {
                    if (PostResult > 0) {
                        _handler.post(new Runnable() {
                            public void run() {



                            }
                        });


                    } else if (PostResult == -3) {
                        loadingdialog.dismiss();
                        _handler.post(new Runnable() {
                            public void run() {

                            }
                        });


                        loadingdialog.dismiss();
                        _handler.post(new Runnable() {
                            public void run() {

                            }
                        });
                    } else {
                        _handler.post(new Runnable() {
                            public void run() {

                            }
                        });
                    }
                } catch (final Exception e) {
                    loadingdialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {

                        }
                    });
                }
            }
        }).start();

    }
    public void GetMaxRecNo() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        /*String Login = sharedPreferences.getString("Login", "No");
        if (Login.toString().equals("No")) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }*/

        String u = sharedPreferences.getString("UserID", "");
        sql_Handler = new SqlHandler(this);
        String query = "SELECT  COALESCE(MAX(OrderNo), 0) +1 AS No FROM DoctorReport   where UserNo = '" + u.toString() + "'";
        Cursor c1 = sql_Handler.selectQuery(query);
        String max = "0";



        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            max = c1.getString(c1.getColumnIndex("No"));
            c1.close();
        }

        String max1 = "0";

        max1 = DB.GetValue(DoctorReportActivity.this, "MaxOrders", "MedicalReport", "1=1");

        if (max1 == "") {
            max1 = "0";
        }
        max1 = String.valueOf(Integer.parseInt(max1) + 1);
        if (SToD(max1) > SToD(max)) {
            max = max1;
        }

        if (max.length() == 1) {

            tv_No.setText(intToString(Integer.valueOf(u), 2) + intToString(Integer.valueOf(max), 5));

        } else {

            tv_No.setText(intToString(Integer.valueOf(max), 7));

        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tv_No.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        tv_No.setFocusable(false);
        tv_No.setEnabled(false);
        tv_No.setCursorVisible(false);

        et_NoS = tv_No.getText().toString();
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

    @Override
    public void onBackPressed() {
        Intent k;
        k = new Intent(this, GalaxyNewHomeActivity.class);
        startActivity(k);
    }

    public void ShowItems(View view) {
        String transNo = returntransno();
        String post = DB.GetValue(DoctorReportActivity.this, "DoctorReport", "Posted", "OrderNo ='" + transNo + "'");

        if (!post.equals("-1")) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    DoctorReportActivity.this).create();
            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.Update)));

            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.UpdateNotAllowedafterpost)));


            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setButton(String.valueOf(getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("Scr", "DR");
            FragmentManager Manager = getFragmentManager();
            Pop_Free_Sample_Items obj = new Pop_Free_Sample_Items();
            obj.setArguments(bundle);
            obj.show(Manager, null);

        }
    }


    public void Save_List(String ItemNo, String q, String u, String ItemNm, String UnitName, String Operand) {
        Cls_FreeSampleItems obj = new Cls_FreeSampleItems();
        obj.setItemNo(ItemNo);
        obj.setItemNm(ItemNm);
        obj.setQty(q);
        obj.setUnitNo(u);
        obj.setUnitNm(UnitName);
        obj.setOprand(Operand);
        ArrayList.add(obj);
        showList();

    }

    private void showList() {

        LstItems.setAdapter(null);
        Cls_FreeSample_Adapter contactListAdapter = new Cls_FreeSample_Adapter(
                DoctorReportActivity.this, ArrayList);
        LstItems.setAdapter(contactListAdapter);


    }

    public void ShowSubjects(View view) {

        String transNo = returntransno();
        String post = DB.GetValue(DoctorReportActivity.this, "DoctorReport", "Posted", "OrderNo ='" + transNo + "'");

        if (!post.equals("-1")) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    DoctorReportActivity.this).create();
            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.Update)));

            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.UpdateNotAllowedafterpost)));


            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setButton(String.valueOf(getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        } else {


            Bundle bundle = new Bundle();
            bundle.putString("Scr", "DR");
            bundle.putString("transno", et_NoS);
            FragmentManager fmSubject = getFragmentManager();
            Pop_Subjects_Items obj = new Pop_Subjects_Items();
            obj.setArguments(bundle);
            obj.show(fmSubject, null);
        }
    }

    public void SaveSubject(String ItemNo, String ItemNm) {
        Cls_Subjects obj = new Cls_Subjects();
        obj.setSubjectID(ItemNo);
        obj.setSubjectDesc(ItemNm);
        boolean pass = true;
        for (int i = 0; i < ArraySubjects.size(); i++) {
            if (ArraySubjects.get(i).SubjectID.equals(ItemNo))
                pass = false;
        }
        if (pass) {
            ArraySubjects.add(obj);
            ShowListSubjects();
        } else {
            if (!GloblaVar.exixit.equals("1"))
                Toast.makeText(DoctorReportActivity.this, String.valueOf(getResources().getString(R.string.alreadyadded)), Toast.LENGTH_SHORT).show();
            else {
                GloblaVar.exixit = "1";
            }
        }
    }

    private void ShowListSubjects() {
        LstSubjects.setAdapter(null);
        Cls_Subjects_Adapter Adapter = new Cls_Subjects_Adapter(DoctorReportActivity.this, ArraySubjects);
        LstSubjects.setAdapter(Adapter);

    }

    public void btn_Delete_FreeItem(final View view) {

        /*String q = "Delete from  ItemsCheck where SaveState ='0'";
        sql_Handler.executeQuery(q);*/

        String transNo = returntransno();
        String post = DB.GetValue(DoctorReportActivity.this, "DoctorReport", "Posted", "OrderNo ='" + transNo + "'");

        if (!post.equals("-1")) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    DoctorReportActivity.this).create();
            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.Update)));

            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.UpdateNotAllowedafterpost)));


            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setButton(String.valueOf(getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        } else {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.medicalvisits)));

            alertDialog.setMessage(getResources().getText(R.string.DoYouWantContinDelete));
            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setPositiveButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    int position = LstItems.getPositionForView(view);
                    ArrayList.remove(position);
                    showList();
                }
            });

            alertDialog.setNegativeButton(getResources().getText(R.string.No), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }
    }

    public void btn_Delete_Subject(final View view) {

        String transNo = returntransno();
        String post = DB.GetValue(DoctorReportActivity.this, "DoctorReport", "Posted", "OrderNo ='" + transNo + "'");

        if (!post.equals("-1")) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    DoctorReportActivity.this).create();
            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.Update)));

            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.UpdateNotAllowedafterpost)));


            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setButton(String.valueOf(getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        } else {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.medicalvisits)));
            alertDialog.setMessage(getResources().getText(R.string.DoYouWantContinDelete));
            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setPositiveButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    int position = LstSubjects.getPositionForView(view);
                    ArraySubjects.remove(position);
                    ShowListSubjects();
                }
            });

            alertDialog.setNegativeButton(getResources().getText(R.string.No), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }
    }

    public void endViwFragment() {
        this.getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("example dialog")).commit();

    }


    public void addtime(View view) {
        String transNo = returntransno();
        String post = DB.GetValue(DoctorReportActivity.this, "DoctorReport", "Posted", "OrderNo ='" + transNo + "'");

        if (!post.equals("-1")) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    DoctorReportActivity.this).create();
            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.Update)));

            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.UpdateNotAllowedafterpost)));


            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setButton(String.valueOf(getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        } else {
            Bundle bundle = new Bundle();

            bundle.putString("transno", returntransno());
            bundle.putString("OrderNo", tv_No.getText().toString());

            Add_New_Vist exampleDialog = new Add_New_Vist();
            exampleDialog.setArguments(bundle);
            exampleDialog.show(getFragmentManager(), "example dialog");

            //  Toast.makeText(DoctorReportActivity.this,"اضافة موعد",Toast.LENGTH_SHORT).show();

        }
    }

    public void btn_Show_Subject(final View view) {
        Bundle bundle = new Bundle();

        bundle.putString("type", "3");
        bundle.putString("itemid", "");

        ViewVisitFrg exampleDialog = new ViewVisitFrg();
        exampleDialog.setArguments(bundle);
        exampleDialog.show(getFragmentManager(), "example dialog");
    }

    public void btn_Show_Object(String itemid) {

        Bundle bundle = new Bundle();

        bundle.putString("type", "3");
        bundle.putString("itemid", itemid);
        bundle.putString("transno", returntransno());

        ViewVisitFrg exampleDialog = new ViewVisitFrg();
        exampleDialog.setArguments(bundle);
        exampleDialog.show(getFragmentManager(), "example dialog");
    }

    public void btn_Show_Media(String itemid) {

        File imgFile = new  File("//sdcard/Android/PDF/"+itemid+".jpg");
        File pdfFile = new  File("//sdcard/Android/PDF/"+itemid+".pdf");
        try {
            if (!(imgFile.exists())&&!(pdfFile.exists())) {
             Toast.makeText(DoctorReportActivity.this,"لا يوجد بروشور لهذه المادة ",Toast.LENGTH_LONG).show();
             return;
            }
        }catch (Exception e)
        {

        }
        Bundle bundle = new Bundle();


          bundle.putString("itemid", itemid);
          bundle.putString("OrderNo", tv_No.getText().toString());

        Pop_show_MEDIA exampleDialog = new Pop_show_MEDIA();

        exampleDialog.setArguments(bundle);
        exampleDialog.show(getFragmentManager(), "example dialog");

    }

}
