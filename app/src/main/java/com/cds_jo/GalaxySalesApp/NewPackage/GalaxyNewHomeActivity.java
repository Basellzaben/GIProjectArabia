package com.cds_jo.GalaxySalesApp.NewPackage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.ComInfo;
import com.cds_jo.GalaxySalesApp.Companies;
import com.cds_jo.GalaxySalesApp.CustomerQty;
import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.Delivery.DeliveryAct;
import com.cds_jo.GalaxySalesApp.Delivery.SalesInvoiceAct;
import com.cds_jo.GalaxySalesApp.DoctorReportActivity;
import com.cds_jo.GalaxySalesApp.GlobaleVar;
import com.cds_jo.GalaxySalesApp.ItemCostActivity;
import com.cds_jo.GalaxySalesApp.ItemGalleryActivity;
import com.cds_jo.GalaxySalesApp.JalMasterActivity;
import com.cds_jo.GalaxySalesApp.MainActivity;
import com.cds_jo.GalaxySalesApp.ManBalanceQtyActivity;
import com.cds_jo.GalaxySalesApp.ManCard.DetailCardMan;
import com.cds_jo.GalaxySalesApp.ManSummeryNew;
import com.cds_jo.GalaxySalesApp.Mentnis.MentnisActivity;
import com.cds_jo.GalaxySalesApp.NewHomePage.NewHomePage;
import com.cds_jo.GalaxySalesApp.NewLoginActivity;
import com.cds_jo.GalaxySalesApp.PreapareManQty;
import com.cds_jo.GalaxySalesApp.QuestneerActivity;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.RecvVoucherActivity;
import com.cds_jo.GalaxySalesApp.ScheduleManActivity;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.TQNew.Acc_ReportActivity1;
import com.cds_jo.GalaxySalesApp.TQNew.ManSummeryActivity1;
import com.cds_jo.GalaxySalesApp.TQNew.OrdersItems1;
import com.cds_jo.GalaxySalesApp.TransQtyReportActivity;
import com.cds_jo.GalaxySalesApp.UpdateDataToMobileActivity;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.assist.Acc_ReportActivity;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.cds_jo.GalaxySalesApp.assist.CustomerReturnQtyActivity;
import com.cds_jo.GalaxySalesApp.assist.ManAttenActivity;
import com.cds_jo.GalaxySalesApp.assist.OrdersItems;
import com.cds_jo.GalaxySalesApp.assist.Sale_InvoiceActivity;
import com.cds_jo.GalaxySalesApp.testAct;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import header.Header_Frag;

public class GalaxyNewHomeActivity extends FragmentActivity {
    private Context context;
    private LinearLayout RR1,RR2,RR3,R_Content,R_Update,R_Summery,R_Balance_Q,R_Trans_R,R_Visit,BottenDey,manatt;
    private LinearLayout R_Edit_Tran,R_Customer_Q,R_Question;
    private RelativeLayout R_Back;
    private FloatingActionMenu actionMenu1,actionMenu2;
    private ImageView Img_Menu;
    String UserID="";
    String lan  = "en";
    String RepType="";
    SqlHandler sqlHandler;
    SharedPreferences sharedPreferences1;

    private String getday() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat mdformatyear = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        String stryear = mdformatyear.format(calendar.getTime());

        SimpleDateFormat mdformatmonth = new SimpleDateFormat("MM", Locale.ENGLISH);
        String strmonth = mdformatmonth.format(calendar.getTime());

        SimpleDateFormat mdformatday = new SimpleDateFormat("dd", Locale.ENGLISH);
        String strday = mdformatday.format(calendar.getTime());

        String day1=strday+"/"+strmonth+"/"+stryear;

        return day1;

    }
    public void SetVisits()

    {
        sharedPreferences1  = PreferenceManager.getDefaultSharedPreferences(GalaxyNewHomeActivity.this);
        final Handler _handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                CallWebServices ws = new CallWebServices(GalaxyNewHomeActivity.this);
                ws.SetVisits(sharedPreferences1.getString("UserID", ""));
                try {
                    Integer i;
                    String q;
                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray ID = js.getJSONArray("ID");





                    _handler.post(new Runnable() {
                        public void run() {


                        }
                    });
                } catch (final Exception e) {}}}).start();
    }
    public void updateCustomer()
    {
        final Handler _handler = new Handler();



        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(GalaxyNewHomeActivity.this);
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
                    sqlHandler.executeQuery(q);
                    q = "delete from sqlite_sequence where name='Customers'";
                    sqlHandler.executeQuery(q);

                    for (i = 0; i < js_no.length(); i++) {
                        q = "Insert INTO Customers(Tax_Status,no,name,Ename,barCode,Address,State,SMan,Latitude,Longitude,Note2,sat " +
                                " ,sun,mon,tues,wens,thurs,sat1,sun1,mon1,tues1,wens1,thurs1 , Celing , CatNo " +
                                ",CustType,PAMENT_PERIOD_NO , CUST_PRV_MONTH,CUST_NET_BAL,Pay_How,Cust_type,LocationNo,Location,CheckAlowedDay,PromotionFlag,CloseVisitWithoutimg,BB_Chaq,BB_bill,Chqceling) values ('"
                                + js_TaxStatus.get(i).toString()
                                + "','"  + js_no.get(i).toString()
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
                        sqlHandler.executeQuery(q);

                    }
                    final int total = i;
                    _handler.post(new Runnable() {

                        public void run() {

                        }
                    });

                } catch (final Exception e) {
                    _handler.post(new Runnable() {
                        public void run() {}
                    });
                }
            }
        }).start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_galaxy_new_home_activity);
        Initi();


     /*   if (ComInfo.ComNo == Companies.Sector.getValue()||ComInfo.ComNo == Companies.nwaah.getValue()) {
            startActivity(new Intent(GalaxyNewHomeActivity.this, NewHomePage.class));
        }*/
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime1 = sdf1.format(new Date());
        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(context);
        if(!(sharedPreferences1.getString("DateUpdate", "").equals(currentDateandTime1))||sharedPreferences1.getString("DateUpdate", "").equals("")) {

            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putString("DateUpdate", currentDateandTime1);
            editor.commit();
            GlobaleVar.flag=1;
            Intent k = new Intent(GalaxyNewHomeActivity.this, UpdateDataToMobileActivity.class);
            startActivity(k);
        }

        if(ComInfo.ComNo == Companies.Afrah.getValue()||ComInfo.ComNo == Companies.Ukrania.getValue()) {
            sqlHandler = new SqlHandler(this);
            try {
                Date date = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                String visitdate = DB.GetValue(this, "VisitDate", "Date", "1=1");
              //  Toast.makeText(GalaxyNewHomeActivity.this, dayOfWeek + "", Toast.LENGTH_LONG).show();
                if (dayOfWeek == 7) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                    String currentDateandTime = sdf.format(new Date());
                    if (visitdate.equals("-1")) {
                        String qur = "insert into VisitDate (Date ) values ( '" +
                                currentDateandTime

                                + "')";

                        sqlHandler.executeQuery(qur);
                    }
                    if (!(currentDateandTime.equals(visitdate))) {
                        String qu = "UPDATE VisitDate SET Date='" + currentDateandTime;
                        sqlHandler.executeQuery(qu);
                        SetVisits();
                        updateCustomer();


                    }

                }
            } catch (SQLException e) {
                // Log.i("CREATE TABLE   CustLastPrice","Week already Operand");
            }
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(GalaxyNewHomeActivity.this);
        lan =  Locale.getDefault().getDisplayLanguage();
        lan=  sharedPreferences.getString("Lan", "");
        lan= LocaleHelper.getlanguage(GalaxyNewHomeActivity.this);//.setLocale(getActivity(), "ar");

        // View view=inflater.inflate(R.layout.header_frag_ar,container, false);

        if(lan.equalsIgnoreCase("ar"))
        {
            GlobaleVar.LanType=1;
            R_Content.setBackgroundResource(R.drawable.newhomepagepac);
        }
        else
        {     GlobaleVar.LanType=2;
            R_Content.setBackgroundResource(R.mipmap.homebacken);
        }



      //  SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(GalaxyNewHomeActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CompanyID", DB.GetValue(this,"ComanyInfo","CompanyID","1=1"));
        editor.putString("CompanyNm", DB.GetValue(this,"ComanyInfo","CompanyNm","1=1") );
        editor.putString("TaxAcc1",  DB.GetValue(this,"ComanyInfo","TaxAcc1","1=1") );
        editor.putString("Address",  DB.GetValue(this,"ComanyInfo","Address","1=1") );
        editor.putString("Notes",  DB.GetValue(this,"ComanyInfo","Notes","1=1") );
        editor.putString("Permession",  DB.GetValue(this,"ComanyInfo","Permession","1=1") );
        editor.putString("CompanyMobile",  DB.GetValue(this,"ComanyInfo","CompanyMobile","1=1") );
        editor.putString("CompanyMobile2",  DB.GetValue(this,"ComanyInfo","CompanyMobile2","1=1") );
        editor.putString("SuperVisorMobile",  DB.GetValue(this,"ComanyInfo","SuperVisorMobile","1=1") );

        String q=DB.GetValue(this,"cardMan","no","1=1");
        //  SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        UserID = sharedPreferences.getString("UserID", "");

        String  companym= DB.GetValue(GalaxyNewHomeActivity.this,"ComanyInfo","SuperVisorMobile","ID='"+UserID+"'");
        String mansupermobile= DB.GetValue(GalaxyNewHomeActivity.this,"manf","SupervisorMobile","man='"+UserID+"'");




        String AllowLoginOutside= DB.GetValue(GalaxyNewHomeActivity.this,"manf","AllowLoginOutside","man='"+UserID+"'");
        String RangeLogin= DB.GetValue(GalaxyNewHomeActivity.this,"manf","RangeLogin","man='"+UserID+"'");


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor2 = preferences.edit();
        if(AllowLoginOutside!=null)
        editor2.putString("AllowLoginOutside",AllowLoginOutside);
        else
        editor2.putString("AllowLoginOutside","0");

        if(RangeLogin!=null)
            editor2.putString("RangeLogin",RangeLogin);
        else
            editor2.putString("RangeLogin","0");

        editor2.apply();


      //  Toast.makeText(GalaxyNewHomeActivity.this,AllowLoginOutside.toString(),Toast.LENGTH_SHORT).show();


        RepType= sharedPreferences.getString("TypeRep", "-1");

        // SharedPreferences.Editor editor = sharedPreferences.edit();
        //     editor.putString("SupervisorMobile", SupervisorMobile.get(i).toString());

        if(mansupermobile==null || mansupermobile.equals("0")||mansupermobile.equals("")) {
            editor.putString("SuperVisorMobile", companym);
            //editor.putString("SupervisorMobile", SupervisorMobile.get(i).toString());
            editor.apply();
        }else{
            editor.putString("SupervisorMobile",mansupermobile);
            editor.apply();
        }



        String query = "Select DateLogin From TimeLogin where manNo ='"+UserID +"'";
        sqlHandler = new SqlHandler(this);
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                try{
                    String day=getday();
                    String d=c1.getString(c1
                            .getColumnIndex("DateLogin"));
                    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date d1 = sdf.parse(d);
                    Date day2 = sdf.parse(day);
                    if(sdf.format(d1).equals(sdf.format( day2)))
                    {

                    }else
                    {
                        String qu="UPDATE TimeLogin SET DateLogin='"+String.valueOf(day)+"' WHERE  manNo ='"+UserID+"'" ;
                        sqlHandler.executeQuery(qu);
                       /* DetailCardMan exampleDialog = new DetailCardMan();
                        exampleDialog.show(getSupportFragmentManager(), "dialog");*/
                        android.app.FragmentManager fragmentManager = getFragmentManager();
                        DetailCardMan detailCardMan =new DetailCardMan();
                        detailCardMan.show(fragmentManager,null);



                    }



                }catch(ParseException ex){
                    // handle parsing exception if date string was different from the pattern applying into the SimpleDateFormat contructor
                }

            }}
        else
        {
            String day=getday();

            String qur = "insert into TimeLogin (manNo,DateLogin ) values ( '" +
                    UserID
                    + "','" + day
                    + "')";

            sqlHandler.executeQuery(qur);
           /* DetailCardMan exampleDialog = new DetailCardMan();
            exampleDialog.show(getSupportFragmentManager(), "dialog");*/

            android.app.FragmentManager fragmentManager = getFragmentManager();
            DetailCardMan detailCardMan = new DetailCardMan();
            detailCardMan.show( fragmentManager,null);
        }


        try {
            ComInfo.ComNo = Integer.parseInt(DB.GetValue(this, "ComanyInfo", "CompanyID", "1=1"));
        }catch (Exception t){}



        if(RepType.equals("1")) {
            Cir1();
            Cir4();
        }else
        {
            Cir3();
        }
        //Cir2();

        Layout_Click();
        Fragment frag=new Header_Frag();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1,frag).commit();
        if(RepType.equals("1"))
        {
            R_Content.setBackgroundResource(R.drawable.newhomepagepac);
        }
    }

    private void Layout_Click() {

        RR2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RepType.equals("1")) {

                    if (ComInfo.ComNo== Companies.Afrah.getValue()) {
                        Toast.makeText(GalaxyNewHomeActivity.this,"لا يوجد لديك صلاحية",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        startActivity(new Intent(context, ItemGalleryActivity.class));

                    }

                 }
                 else
                {
                    Toast.makeText(GalaxyNewHomeActivity.this,"لا يوجد لديك صلاحية",Toast.LENGTH_LONG).show();
                }
            }
        });

        R_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, UpdateDataToMobileActivity.class));
            }
        });

        R_Summery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RepType.equals("1")) {
                    if (ComInfo.ComNo== Companies.Afrah.getValue()) {
                        startActivity(new Intent(context, ManSummeryActivity1.class));

                    }
                    else
                    {
                        startActivity(new Intent(context, ManSummeryNew.class));

                    }

                }
                 else
                {
                    Toast.makeText(GalaxyNewHomeActivity.this,"لا يوجد لديك صلاحية",Toast.LENGTH_LONG).show();
                }
            }
        });

        R_Balance_Q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RepType.equals("1")) {
                    if (ComInfo.ComNo== Companies.Afrah.getValue()) {
                        Toast.makeText(GalaxyNewHomeActivity.this,"لا يوجد لديك صلاحية",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        String Man = DB.GetValue(GalaxyNewHomeActivity.this, "Tab_Password", "ManNo", "PassNo = 10 AND ManNo ='" + UserID + "'");
                        final String pass = DB.GetValue(GalaxyNewHomeActivity.this, "Tab_Password", "Password", "PassNo = 12");
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(GalaxyNewHomeActivity.this);
                        alertDialog.setTitle(DB.GetValue(GalaxyNewHomeActivity.this, "Tab_Password", "PassDesc", "PassNo = 12"));
                        alertDialog.setMessage("ادخل رمز التحقق");

                        final EditText input = new EditText(GalaxyNewHomeActivity.this);
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

                                        if (pass.equalsIgnoreCase(password)) {
                                            // Toast.makeText(getApplicationContext(),"رمز التحقق صحيح", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(context, ManBalanceQtyActivity.class));
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

                } else
                {
                    Toast.makeText(GalaxyNewHomeActivity.this,"لا يوجد لديك صلاحية",Toast.LENGTH_LONG).show();
                }
            }
        });

        R_Trans_R.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RepType.equals("1")) {

                    if (ComInfo.ComNo== Companies.Afrah.getValue()) {
                        Toast.makeText(GalaxyNewHomeActivity.this,"لا يوجد لديك صلاحية",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        startActivity(new Intent(context, TransQtyReportActivity.class));
                    }
                }
                else
                {
                    Toast.makeText(GalaxyNewHomeActivity.this,"لا يوجد لديك صلاحية",Toast.LENGTH_LONG).show();
                }

            }
        });

        R_Visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RepType.equals("1")) {

                    if (ComInfo.ComNo== Companies.Afrah.getValue()) {
                        Toast.makeText(GalaxyNewHomeActivity.this,"لا يوجد لديك صلاحية",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        startActivity(new Intent(context, ScheduleManActivity.class));
                    }


                 }
                 else
                {
                    Toast.makeText(GalaxyNewHomeActivity.this,"لا يوجد لديك صلاحية",Toast.LENGTH_LONG).show();
                }}

        });
        manatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RepType.equals("1")) {
                    startActivity(new Intent(context, ManAttenActivity.class));


                 }
                 else
                {
                    Toast.makeText(GalaxyNewHomeActivity.this,"لا يوجد لديك صلاحية",Toast.LENGTH_LONG).show();
                }}

        });

        R_Edit_Tran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(RepType.equals("1")) {
                    startActivity(new Intent(context, MainActivity.class));
                }
                else
                {
                   startActivity(new Intent(context, MainNewActivity.class));
                   // startActivity(new Intent(context, testAct.class));
                }
            }
        });

        R_Customer_Q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RepType.equals("1"))
                {

                    if (ComInfo.ComNo== Companies.Afrah.getValue()) {
                        Toast.makeText(GalaxyNewHomeActivity.this,"لا يوجد لديك صلاحية",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        startActivity(new Intent(context, CustomerQty.class));
                    }
            }
                else
                {
                    Toast.makeText(GalaxyNewHomeActivity.this,"لا يوجد لديك صلاحية",Toast.LENGTH_LONG).show();
                }

            }
        });

        R_Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RepType.equals("2")) {
                    startActivity(new Intent(context, QuestneerActivity.class));
                }
                 else
                {
                    Toast.makeText(GalaxyNewHomeActivity.this,"لا يوجد لديك صلاحية",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Initi() {
        context=GalaxyNewHomeActivity.this;
        RR1= (LinearLayout) findViewById(R.id.RR1);
        RR2= (LinearLayout) findViewById(R.id.RR2);
        R_Update= (LinearLayout) findViewById(R.id.R_Update);
        R_Summery= (LinearLayout) findViewById(R.id.R_Summery);
        R_Balance_Q= (LinearLayout) findViewById(R.id.R_Balance_Q);
        R_Trans_R= (LinearLayout) findViewById(R.id.R_Trans_R);
        R_Visit= (LinearLayout) findViewById(R.id.R_Visit);
        manatt= (LinearLayout) findViewById(R.id.manatt);
        R_Edit_Tran= (LinearLayout) findViewById(R.id.R_Edit_Tran);
        R_Customer_Q= (LinearLayout) findViewById(R.id.R_Customer_Q);
        R_Question= (LinearLayout) findViewById(R.id.R_Question);
        R_Content= (LinearLayout) findViewById(R.id.R_Content);
        R_Back= (RelativeLayout) findViewById(R.id.R_Back);
        Img_Menu=(ImageView)findViewById(R.id.imageView6);
        BottenDey=(LinearLayout)findViewById(R.id.BottenDey);


    }

    /////////////////////////////////////////////////////////////////////////////////////
    private void Cir1() {
        RelativeLayout.LayoutParams params;
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.cir_green));


        ImageView itemIcon = new ImageView(this);
        ImageView itemIcon2 = new ImageView(this);
        ImageView itemIcon3 = new ImageView(this);
        ImageView itemIcon4 = new ImageView(this);

        itemIcon.setImageResource(R.mipmap.aa1);
        itemIcon2.setImageResource(R.mipmap.aa2);
        itemIcon3.setImageResource(R.mipmap.aa3);
        itemIcon4.setImageResource(R.mipmap.aa4);


        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();
        SubActionButton button4 = itemBuilder.setContentView(itemIcon4).build();





        params = new RelativeLayout.LayoutParams(200, 200);

        button4.setLayoutParams(params);
        button3.setLayoutParams(params);
        button2.setLayoutParams(params);
        button1.setLayoutParams(params);

        actionMenu1 = new FloatingActionMenu.Builder(this)
                .setStartAngle(0) // A whole circle!
                .setEndAngle(180)
                .setRadius(250)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .addSubActionView(button4)

                .attachTo(RR1)
                .setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
                    @Override
                    public void onMenuOpened(FloatingActionMenu floatingActionMenu) {

                    //    R_Content.setBackgroundResource(R.mipmap.homeback_blure2);

                        R_Back.setBackgroundColor(getResources().getColor(R.color.Main_Gray_Blure));
                        RR2.setEnabled(false);
                        R_Update.setEnabled(false);
                        R_Summery.setEnabled(false);
                        R_Balance_Q.setEnabled(false);
                        R_Trans_R.setEnabled(false);
                        R_Visit.setEnabled(false);
                        manatt.setEnabled(false);
                        /*if(actionMenu2.isOpen())
                        {
                            actionMenu2.close(true);
                        }*/
                    }

                    @Override
                    public void onMenuClosed(FloatingActionMenu floatingActionMenu) {

                      //  R_Content.setBackgroundResource(R.mipmap.homeback1);

                        R_Back.setBackgroundColor(getResources().getColor(R.color.Main_Gray_Non_Blure));
                        RR2.setEnabled(true);
                        R_Update.setEnabled(true);
                        R_Summery.setEnabled(true);
                        R_Balance_Q.setEnabled(true);
                        R_Trans_R.setEnabled(true);
                        R_Visit.setEnabled(true);
                        manatt.setEnabled(true);
                    }
                })
                .build();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ComInfo.ComNo== Companies.Afrah.getValue()) {
                    startActivity(new Intent(context, OrdersItems1.class));

                }
                else
                {
                  startActivity(new Intent(context, OrdersItems.class));
                  //startActivity(new Intent(context, MentnisActivity.class));
                }


            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ComInfo.ComNo== Companies.Afrah.getValue()) {
                    startActivity(new Intent(context, Acc_ReportActivity1.class));

                }
                else
                {
                    startActivity(new Intent(context, Acc_ReportActivity.class));
                }


            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mANsTATES = DB.GetValue(GalaxyNewHomeActivity.this, "manf", "Stoped", "man = '"+UserID+"'");
                if (mANsTATES.equals("1"))
                {
                    startActivity(new Intent(context, Sale_InvoiceActivity.class));
                }
                else {
                    Toast.makeText(GalaxyNewHomeActivity.this,"لا يوجد صلاحيه الرجاء مراجعه المشرف",Toast.LENGTH_LONG).show();
                }

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, RecvVoucherActivity.class));

            }
        });


    }
    private void Cir4() {
        RelativeLayout.LayoutParams params;
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.cir_green));


        ImageView itemIcon = new ImageView(this);
        ImageView itemIcon2 = new ImageView(this);


        itemIcon.setImageResource(R.mipmap.aa9);
        itemIcon2.setImageResource(R.mipmap.aa3);



        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();






        params = new RelativeLayout.LayoutParams(200, 200);


        button2.setLayoutParams(params);
        button1.setLayoutParams(params);

        actionMenu1 = new FloatingActionMenu.Builder(this)
                .setStartAngle(0) // A whole circle!
                .setEndAngle(180)
                .setRadius(250)
                .addSubActionView(button1)
                .addSubActionView(button2)


                .attachTo(BottenDey)
                .setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
                    @Override
                    public void onMenuOpened(FloatingActionMenu floatingActionMenu) {

                   //     R_Content.setBackgroundResource(R.mipmap.homeback_blure2);

                        R_Back.setBackgroundColor(getResources().getColor(R.color.Main_Gray_Blure));
                        RR2.setEnabled(false);
                        R_Update.setEnabled(false);
                        R_Summery.setEnabled(false);
                        R_Balance_Q.setEnabled(false);
                        R_Trans_R.setEnabled(false);
                        R_Visit.setEnabled(false);
                        manatt.setEnabled(false);
                   /*     if(actionMenu2.isOpen())
                        {
                            actionMenu2.close(true);
                        }*/
                    }

                    @Override
                    public void onMenuClosed(FloatingActionMenu floatingActionMenu) {

                     //   R_Content.setBackgroundResource(R.mipmap.homeback1);

                        R_Back.setBackgroundColor(getResources().getColor(R.color.Main_Gray_Non_Blure));
                        RR2.setEnabled(true);
                        R_Update.setEnabled(true);
                        R_Summery.setEnabled(true);
                        R_Balance_Q.setEnabled(true);
                        R_Trans_R.setEnabled(true);
                        R_Visit.setEnabled(true);
                        manatt.setEnabled(true);
                    }
                })
                .build();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, DeliveryAct.class));

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SalesInvoiceAct.class));

            }
        });




    }
    /////////////////////////////////////////////////////////////////////////////////
    private void Cir2() {
        RelativeLayout.LayoutParams params;
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.cir_blue));

        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageResource(R.mipmap.bb1 );
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();

        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageResource(R.mipmap.bb2 );
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();

        ImageView itemIcon3 = new ImageView(this);
        itemIcon3.setImageResource(R.mipmap.bb3 );
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();


        ImageView itemIcon4 = new ImageView(this);
        itemIcon4.setImageResource(R.mipmap.bb4 );
        SubActionButton button4 = itemBuilder.setContentView(itemIcon4).build();


        params = new RelativeLayout.LayoutParams(200, 200);
        button4.setLayoutParams(params);
        button3.setLayoutParams(params);
        button2.setLayoutParams(params);
        button1.setLayoutParams(params);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, PreapareManQty.class));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ItemCostActivity.class));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CustomerReturnQtyActivity.class));
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,ItemGalleryActivity.class));
            }
        });

/*        actionMenu2 = new FloatingActionMenu.Builder(this)
                .setStartAngle(180) // A whole circle!
                .setEndAngle(0)
                .setRadius(250)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .addSubActionView(button4)
                .attachTo(RR2)
                .setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
                    @Override
                    public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
                        R_Content.setBackgroundResource(R.mipmap.homeback_blure1);
                        R_Back.setBackgroundColor(getResources().getColor(R.color.Main_Gray_Blure));
                        RR1.setEnabled(false);
                        R_Update.setEnabled(false);
                        R_Summery.setEnabled(false);
                        R_Balance_Q.setEnabled(false);
                        R_Trans_R.setEnabled(false);
                        R_Visit.setEnabled(false);
                        if(actionMenu1.isOpen())
                        {
                            actionMenu1.close(true);
                        }
                    }

                    @Override
                    public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
                        R_Content.setBackgroundResource(R.mipmap.homeback1);
                        R_Back.setBackgroundColor(getResources().getColor(R.color.Main_Gray_Non_Blure));
                        RR1.setEnabled(true);
                        R_Update.setEnabled(true);
                        R_Summery.setEnabled(true);
                        R_Balance_Q.setEnabled(true);
                        R_Trans_R.setEnabled(true);
                        R_Visit.setEnabled(true);
                    }
                })
                .build();*/

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void Cir3() {
        RelativeLayout.LayoutParams params;
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.cir_green));


        ImageView itemIcon = new ImageView(this);
        ImageView itemIcon2 = new ImageView(this);
       // ImageView itemIcon3 = new ImageView(this);
       // ImageView itemIcon4 = new ImageView(this);

        itemIcon.setImageResource(R.mipmap.plan1);
        itemIcon2.setImageResource(R.mipmap.medical1);
      //  itemIcon3.setImageResource(R.mipmap.aa3);
        //itemIcon4.setImageResource(R.mipmap.aa4);

        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();
       // SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();
       // SubActionButton button4 = itemBuilder.setContentView(itemIcon4).build();




        params = new RelativeLayout.LayoutParams(200, 200);
      //  button4.setLayoutParams(params);
      //  button3.setLayoutParams(params);
        button2.setLayoutParams(params);
        button1.setLayoutParams(params);

        actionMenu1 = new FloatingActionMenu.Builder(this)
                .setStartAngle(0) // A whole circle!
                .setEndAngle(180)
                .setRadius(250)
                .addSubActionView(button1)
                .addSubActionView(button2)
                //.addSubActionView(button3)
                //.addSubActionView(button4)
                .attachTo(RR1)
                .setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
                    @Override
                    public void onMenuOpened(FloatingActionMenu floatingActionMenu) {

                        R_Content.setBackgroundResource(R.mipmap.homeback_blure2);

                        R_Back.setBackgroundColor(getResources().getColor(R.color.Main_Gray_Blure));
                        RR2.setEnabled(false);
                        R_Update.setEnabled(false);
                        R_Summery.setEnabled(false);
                        R_Balance_Q.setEnabled(false);
                        R_Trans_R.setEnabled(false);
                        R_Visit.setEnabled(false);
                        manatt.setEnabled(false);
                        /*if(actionMenu2.isOpen())
                        {
                            actionMenu2.close(true);
                        }*/
                    }

                    @Override
                    public void onMenuClosed(FloatingActionMenu floatingActionMenu) {

                        R_Content.setBackgroundResource(R.drawable.newhomepagepac);

                        R_Back.setBackgroundColor(getResources().getColor(R.color.Main_Gray_Non_Blure));
                        RR2.setEnabled(true);
                        R_Update.setEnabled(true);
                        R_Summery.setEnabled(true);
                        R_Balance_Q.setEnabled(true);
                        R_Trans_R.setEnabled(true);
                        R_Visit.setEnabled(true);
                        manatt.setEnabled(true);
                    }
                })
                .build();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MonthlySalesManSchedule.class));

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, DoctorReportActivity.class));

            }
        });

/*        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Sale_InvoiceActivity.class));*/

     /*       }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, RecvVoucherActivity.class));

            }
        });*/


    }
    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String Login = sharedPreferences.getString("Login", "No");
        if (Login.toString().equals("No")) {
            Intent i = new Intent(this, NewLoginActivity.class);
            startActivity(i);
        }
        return;
    }

}
