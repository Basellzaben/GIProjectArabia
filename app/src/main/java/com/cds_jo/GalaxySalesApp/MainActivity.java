package com.cds_jo.GalaxySalesApp;


import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.cds_jo.GalaxySalesApp.ManLocation.AutoPostLocation;
import com.cds_jo.GalaxySalesApp.NewPackage.GalaxyNewHomeActivity;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.cds_jo.GalaxySalesApp.assist.Cls_Att;
import com.cds_jo.GalaxySalesApp.assist.ESCPSample3;

import com.cds_jo.GalaxySalesApp.assist.Logtrans.InsertLogTrans;
import com.cds_jo.GalaxySalesApp.assist.ManAttenActivity;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sewoo.jpos.printer.ESCPOSPrinter;
import com.zebra.zq110.ZQ110;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Methdes.MyTextView;
import header.Header_Frag;

public class MainActivity extends AppCompatActivity implements LocationListener {
    ImageView imglogo;
    String query,Result;
    RelativeLayout scanBtn;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    LocationManager locationmanager;
    SqlHandler sql_Handler;
    private TextView contentTxt;
    android.support.v7.app.AlertDialog alertDialog2;
    private ESCPOSPrinter posPtr;
    ESCPSample3 obj_print = new ESCPSample3();
    String dayOfWeek;
    SqlHandler sqlHandler;
    TextView TrDate, et_Day, et_StartTime, et_EndTime;
    RelativeLayout EndRound, StartRound;
    String Week_Num = "1";
    ArrayList<Cls_SaleManDailyRound> RoundList;
    public ProgressDialog loadingdialog;
    static ZQ110 mZQ110;
    boolean isGPSEnabled = false;
    LocationManager locationManager;
    String OrderNo = "";
    String Msg = "";
    String UserID;
    String q;
    CharSequence[] values = {"رمز تحقق ", "طلب موافقة المشرف"};
    TextView tv;
    Drawable greenProgressbar;
    RelativeLayout.LayoutParams lp;
    MyTextView tv_VisitWeekNm;
    EditText tv_Note;
    String IsException= "0" ;
    String IDCustomer= "0" ;
    String IDN1= "0" ;
    TextView tv_x , tv_y, tv_Loc ;
    // Minimum time fluctuation for next update (in milliseconds)
    private static final long TIME = 30000;
    // Minimum distance fluctuation for next update (in meters)
    private static final long DISTANCE = 20;
    ArrayList<Integer> ArrayAtts;

    // Declaring a Location Manager
    protected LocationManager mLocationManager;
    GetPermession UserPermission;
   String SCR_NO="11001";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n_activity_main);

        if(GlobaleVar.LanType==1)
        {
            LinearLayout linrtl=(LinearLayout)findViewById(R.id.LL2);
            linrtl.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        else
        {
            LinearLayout linrtl=(LinearLayout)findViewById(R.id.LL2);
            linrtl.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        InsertLogTrans obj=new InsertLogTrans(MainActivity.this,SCR_NO , SCR_ACTIONS.open.getValue(),"","","","0");
        hideSoftKeyboard();
          tv_x = (TextView) findViewById(R.id.tv_x);
          tv_y = (TextView) findViewById(R.id.tv_y);
          tv_Loc = (TextView) findViewById(R.id.tv_Loc);
        ComInfo.ComNo = Integer.parseInt(DB.GetValue(this, "ComanyInfo", "CompanyID", "1=1").replaceAll("[^\\d.]", ""));
        RoundList = new ArrayList<Cls_SaleManDailyRound>();
        RoundList.clear();
        UserPermission = new GetPermession();
        locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria cri = new Criteria();
        String provider = locationmanager.getBestProvider(cri, false);

        ArrayAtts = new ArrayList<Integer>();
        sql_Handler = new SqlHandler(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        tv_VisitWeekNm = (MyTextView) findViewById(R.id.tv_VisitWeekNm);
        TrDate = (TextView) findViewById(R.id.et_Date);
        TrDate.setText(currentDateandTime);
        tv_Note=(EditText) findViewById(R.id.tv_Note);

        Calendar c = Calendar.getInstance();
       // dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
          dayOfWeek   = DB.GetValue(this, "ServerDateTime", "DAYWEEK", "1=1");

        et_Day = (TextView) findViewById(R.id.et_Day);
        et_Day.setText(GetDayName(dayOfWeek));


        contentTxt = (TextView) findViewById(R.id.scan_content);
        scanBtn = (RelativeLayout) findViewById(R.id.scan_button);

        et_StartTime = (TextView) findViewById(R.id.et_StartTime);
        SimpleDateFormat StartTime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        String StringTime = StartTime.format(new Date());
        et_StartTime.setText(StringTime);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        UserID = sharedPreferences.getString("UserID", "");
        try {
            Getlocation();
            ShowRecord();
        } catch (Exception ex) {
        }

        Week_Num = DB.GetValue(this, "ComanyInfo", "VisitWeekNo", "1=1");
       /* if (Week_Num.equalsIgnoreCase("1")) {
            tv_VisitWeekNm.setText("الاسبوع الأول");
        } else {
            tv_VisitWeekNm.setText("الأسبوع الثاني");
        }*/
        // GetlocationNew();
        Fragment frag = new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1, frag).commit();
        //Toast.makeText(this, sharedPreferences.getString("UserID", ""),Toast.LENGTH_LONG).show();
        final TextView tv_CustName = (TextView) findViewById(R.id.tv_CustName);
        tv_CustName.requestFocus();
       // GetData();
    }
    @Override
    public void onStart(){
        super.onStart();
        GetData();

    }
    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    public void GetStreetName() {
        try {
            final TextView tv_x = (TextView) findViewById(R.id.tv_x);
            final TextView tv_y = (TextView) findViewById(R.id.tv_y);

            final TextView tv_Loc = (TextView) findViewById(R.id.tv_Loc);
            Geocoder myLocation = new Geocoder(this, Locale.getDefault());
            List<Address> myList = null;
            try {
                myList = myLocation.getFromLocation(Double.parseDouble(tv_x.getText().toString()), Double.parseDouble(tv_y.getText().toString()), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address1 = (Address) myList.get(0);
            String addressStr = "";
            addressStr += address1.getAddressLine(0) + ", ";
            addressStr += address1.getAddressLine(1) + ", ";
            addressStr += address1.getAddressLine(2);
            tv_Loc.setText(addressStr.replace("null", "").replace(",", ""));
        } catch (Exception ex) {
         //   Toast.makeText(this, ex.getMessage().toString() + "  GetStreetName", Toast.LENGTH_SHORT).show();
        }
    }

    public void GetlocationNew() {
  try {
      String result;

      String address = "";
      GetLocation mGPSService = new GetLocation();
      Location l = mGPSService.CurrentLocation(MainActivity.this);

      final TextView tv_x = (TextView) findViewById(R.id.tv_x);
      final TextView tv_y = (TextView) findViewById(R.id.tv_y);

      final TextView tv_Loc = (TextView) findViewById(R.id.tv_Loc);


      tv_x.setText("");
      tv_y.setText("");
      tv_Loc.setText("");


      double latitude = l.getLatitude();
      double longitude = l.getLongitude();

      tv_x.setText(String.valueOf(latitude));
      tv_y.setText(String.valueOf(longitude));

      Toast.makeText(this, tv_x.getText().toString() + "," + tv_y.getText().toString(), Toast.LENGTH_SHORT).show();
      Cls_HttpGet_Location task = new Cls_HttpGet_Location();
      try {
          result = task.execute(tv_x.getText().toString(), tv_y.getText().toString()).get();
          tv_Loc.setText(result);
      } catch (Exception ex) {
          tv_Loc.setText("الموقع غير متوفر");
      }

      GetStreetName();
  }catch (Exception ex){

  }
    }

    public void Getlocation() {


        String address = "";
        GPSService mGPSService = new GPSService(this);
        mGPSService.getLocation();


        final TextView tv_x = (TextView) findViewById(R.id.tv_x);
        final TextView tv_y = (TextView) findViewById(R.id.tv_y);

        final TextView tv_CustName = (TextView) findViewById(R.id.tv_CustName);
        final TextView tv_Acc = (TextView) findViewById(R.id.tv_Acc);
        final TextView tv_Loc = (TextView) findViewById(R.id.tv_Loc);
        final TextView tv_Address = (TextView) findViewById(R.id.tv_Loc);
        contentTxt = (TextView) findViewById(R.id.scan_content);
        // mGPSService.getLocation();

        if (mGPSService.isLocationAvailable == false) {

            // Here you can ask the user to try again, using return; for that
            // Toast.makeText(this, "Your location is not available, please try again.", Toast.LENGTH_SHORT).show();
            return;

            // Or you can continue without getting the location, remove the return; above and uncomment the line given below
            // address = "Location not available";
        } else {

            // Getting location co-ordinates
            double latitude = mGPSService.getLatitude();
            double longitude = mGPSService.getLongitude();

            address = mGPSService.getLocationAddress();


           /* Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(latitude,longitude, 1);
                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());
                    address = addresses.get(0).getAddressLine(1);
                }
            }
            catch
                    (IOException e) {
                e.printStackTrace();
            }*/

            int precision = (int) Math.pow(10, 6);
            //tv_x.setText(String.format("%.4f", latitude, Locale.US));

          /*  try {
           *//* tv_x.setText(String.valueOf(latitude).substring(0, String.valueOf(latitude).indexOf(".") + 7));
            tv_y.setText(String.valueOf(longitude).substring(0, String.valueOf(longitude).indexOf(".") + 7));
*//*
                //         tv_x.setText(String.valueOf(latitude));
//                tv_y.setText(String.valueOf(longitude));


            } catch (Exception ex) {
                tv_x.setText("0.0");
                tv_y.setText("0.0");
            }*/
            tv_x.setText(String.valueOf(latitude));
            tv_y.setText(String.valueOf(longitude));
            tv_Loc.setText(address);

        }
        GetStreetName();
        // mGPSService.closeGPS();


 /*       String provider;
        Criteria criteria = new Criteria();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        String mprovider="";
        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 1500, 1, this);


            if (location != null)
                onLocationChanged(location);
            else
                Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
            tv_y.setText("114");

            // Setting Current Latitude
            // tv_x.setText("Latitude:" + location.getLatitude() );

        }*/
    }

    @Override
    public void onLocationChanged(Location location) {


        final TextView tv_x = (TextView) findViewById(R.id.tv_x);
        final TextView tv_y = (TextView) findViewById(R.id.tv_y);


        // Getting reference to TextView tv_longitude
        // TextView tvLongitude = (TextView)findViewById(R.id.tv_longitude);

        // Getting reference to TextView tv_latitude
        //TextView tvLatitude = (TextView)findV\iewById(R.id.tv_latitude);

        // Setting Current Longitude
        tv_y.setText("Longitude:" + location.getLongitude());

        // Setting Current Latitude
        tv_x.setText("Latitude:" + location.getLatitude());
    }

    public void btn_SearchCust_dis(View v) {

        Bundle bundle = new Bundle();
        bundle.putString("Scr", "Gps");
        FragmentManager Manager = getFragmentManager();
        Select_Customer_Dis obj = new Select_Customer_Dis();
        obj.setArguments(bundle);
        obj.show(Manager, null);


    }

    public void btn_SearchCust(View v) {
    //  if (ComInfo.ComNo == Companies.beutyLine.getValue() || ComInfo.ComNo == 1||ComInfo.ComNo == Companies.Saad.getValue() ){
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "Gps");
        FragmentManager Manager = getFragmentManager();
        Select_Customer obj = new Select_Customer();
        obj.setArguments(bundle);
        obj.show(Manager, null);
  /*   }else if (ComInfo.ComNo > 1) {


            Button create, show, setting;

            String Man = DB.GetValue(MainActivity.this, "Tab_Password", "ManNo", "PassNo = 3 AND ManNo ='" + UserID + "'");

            final String pass = DB.GetValue(MainActivity.this, "Tab_Password", "Password", "PassNo = 3  and ManNo='" + Man + "'");
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle(DB.GetValue(MainActivity.this, "Tab_Password", "PassDesc", "PassNo = 3 and ManNo='" + Man + "'"));
            alertDialog.setMessage("ادخل رمز التحقق");

            final EditText input = new EditText(MainActivity.this);
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
                                //  Toast.makeText(getApplicationContext(), "رمز التحقق صحيح", Toast.LENGTH_SHORT).show();
                                Bundle bundle = new Bundle();
                                bundle.putString("Scr", "Gps");
                                FragmentManager Manager = getFragmentManager();
                                Select_Customer obj = new Select_Customer();
                                obj.setArguments(bundle);
                                obj.show(Manager, null);
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


        } else {

            if (!UserPermission.CheckAction(this, "30023", SCR_ACTIONS.open.getValue())) {
                com.cds_jo.GalaxySalesApp.ViewDialog alert = new com.cds_jo.GalaxySalesApp.ViewDialog();
                alert.showDialog(MainActivity.this, "نأسف أنت لا تملك الصلاحية  ", "البحث عن العملاء");
                return;
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("Scr", "Gps");
                FragmentManager Manager = getFragmentManager();
                Select_Customer obj = new Select_Customer();
                obj.setArguments(bundle);
                obj.show(Manager, null);
            }


        }
*/

    }


    protected void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.RESULT_HIDDEN);
        }
    }

    static int id = 1;
    MsgNotification noti = new MsgNotification();

    public void btn_StartRound(View view) {
   /*     if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode){
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
               // return;
            }*/
       // }



        String WeekNum="1";

/*        WeekNum = DB.GetValue(this, "Customers_man", "week", "IDN='" + IDN1 + "'");

if(WeekNum=="0"){
    Toast.makeText(this ,"",Toast.LENGTH_LONG).show();

}else {*/

    if (IsException.equals("1")) {
        CreateAlertDialogWithRadioButtonGroup("10");
    } else {
        Save_Recod_Po();
    }


    }

    private void UpDateMaxOrderNo() {

        String query = "SELECT   Distinct  COALESCE(MAX( cast(OrderNo as integer)), 0)   AS no " +
                " FROM SaleManRounds";
        Cursor c1 = sqlHandler.selectQuery(query);
        String max = "0";

        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            max = c1.getString(c1.getColumnIndex("no"));
            c1.close();
        }

        //max = (intToString(Integer.valueOf(max), 7));

        query = "Update OrdersSitting SET Visits ='" + max + "'";
        sqlHandler.executeQuery(query);

        /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("m4", max);
        editor.commit();*/
    }

    public void btn_EndRound(View view) {
        TextView CustNo = (TextView) findViewById(R.id.tv_Acc);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        OrderNo =sharedPreferences.getString("V_OrderNo", "0");
        String CHECK_IMG = DB.GetValue(this, "Customers", "CloseVisitWithoutimg", "no='" + CustNo.getText() + "'");
        if ( ComInfo.ComNo != Companies.Afrah.getValue() ) {
            if ( ComInfo.ComNo == Companies.nwaah.getValue() ) {
            String CHECK_Store = DB.GetValue(this, "CustStoreQtyhdr", "count(*)", "V_OrderNo='" + OrderNo + "'");
            if(CHECK_Store.equals("0"))
            {
                AlertDialog alertDialog = new AlertDialog.Builder(
                        this).create();
                alertDialog.setTitle(String.valueOf(getResources().getString(R.string.Galaxy)));
                alertDialog.setMessage("لا يمكن اغلاق الزيارة قبل جرد كميات العميل");
                alertDialog.setIcon(R.drawable.error_new);
                alertDialog.setButton(String.valueOf(getResources().getString(R.string.Ok)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
                return;
            }}

            if (CHECK_IMG.equalsIgnoreCase("1")) {
            if (ComInfo.ComNo == Companies.beutyLine.getValue() || ComInfo.ComNo == Companies.Afrah.getValue() || ComInfo.ComNo == Companies.Ukrania.getValue() ||ComInfo.ComNo == Companies.diamond.getValue() ||ComInfo.ComNo == Companies.nwaah.getValue()) {


                q = "Select * from VisitImagesHdr where V_OrderNo='" + sharedPreferences.getString("V_OrderNo", "0") + "'";
                Cursor c = sqlHandler.selectQuery(q);
                if (c != null && c.getCount() > 0) {
                    c.close();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            this).create();
                    alertDialog.setTitle(String.valueOf(getResources().getString(R.string.Galaxy)));
                    alertDialog.setMessage(String.valueOf(getResources().getString(R.string.addImagemsg)));
                    alertDialog.setIcon(R.drawable.error_new);
                    alertDialog.setButton(String.valueOf(getResources().getString(R.string.Ok)), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                    return;
                }
            }
        }}
        sqlHandler = new SqlHandler(this);
        TextView CustNm = (TextView) findViewById(R.id.tv_CustName);
        TextView Custadd = (TextView) findViewById(R.id.tv_Loc);
        TrDate = (TextView) findViewById(R.id.et_Date);
        et_EndTime = (TextView) findViewById(R.id.et_EndTime);
        tv_Note=(EditText) findViewById(R.id.tv_Note);

        ContentValues cv = new ContentValues();

        cv.put("Tr_Data", TrDate.getText().toString());
        cv.put("End_Time", et_EndTime.getText().toString());
        cv.put("Closed", "1");
        cv.put("Note", tv_Note.getText().toString());


        Long i;
       i = sqlHandler.Update("SaleManRounds", cv, "Closed =0");
  //    i= sqlHandler.executeQuery("update  ManLogTrans set Tr_Data ='"+TrDate.getText().toString()+"' , End_Time = '"+et_EndTime.getText().toString()+"' ,Closed = 1 ,Note = '"+ tv_Note.getText().toString()+"' where Closed = 0");
        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();


        alertDialog.setTitle(String.valueOf(getResources().getString(R.string.Galaxy)));
        if (i > 0) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("CustNo", "");
            editor.putString("CustNm", "");
            editor.putString("CustAdd", "");
            editor.putString("V_OrderNo", "-1");
            editor.putString("Note", "");
            editor.putString("IDN", "0");
            editor.commit();

            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.EndVisitSucc)));
            alertDialog.setIcon(R.drawable.tick);
            noti.cancel(this);
            InsertLogTrans obj=new InsertLogTrans(MainActivity.this,SCR_NO , SCR_ACTIONS.EndVisit.getValue(),OrderNo, CustNo.getText().toString(),"",IsException);

            DoNew();
            SharManVisits();
           /* stopService(new Intent(MainActivity.this, AutoPostLocation.class));
            startService(new Intent(MainActivity.this, AutoPostLocation.class));*/
        } else {

            alertDialog.setMessage(String.valueOf(getResources().getString(R.string.dontsave)));
            alertDialog.setIcon(R.drawable.error_new);
        }


        alertDialog.setButton(String.valueOf(getResources().getString(R.string.Ok)), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // if(ComInfo.ComNo==4) {
                   /* stopService(new Intent(MainActivity.this, AutoPostLocation.class));
                    startService(new Intent(MainActivity.this, AutoPostLocation.class));*/
                //}
                // stopService(new Intent(MainActivity.this, AutoPostTrans.class));


               /* if (ComInfo.ComNo != 4) {
                    startService(new Intent(MainActivity.this, AutoPostTrans.class));
                }*/
            }
        });


        alertDialog.show();


    }
    public void SharManVisits() {

        //sqlHandler=new SqlHandler(this);

        final Handler _handler = new Handler();
        String query = "select  distinct no,ManNo, CusNo, DayNum ,Tr_Data ,Start_Time,End_Time, Duration,OrderNo " +
                "  ,Note,  X_Lat,Y_Long,Loct,IsException from SaleManRounds   where Posted = -1";
        Cursor c1 = sqlHandler.selectQuery(query);
        ArrayList<Cls_SaleManDailyRound> RoundList;
        RoundList = new ArrayList<Cls_SaleManDailyRound>();
        RoundList.clear();

        //query = " delete from   SaleManRounds   ";
        // sqlHandler.executeQuery(query);
        Cls_SaleManDailyRound cls_saleManDailyRound;

        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    cls_saleManDailyRound = new Cls_SaleManDailyRound();
                    cls_saleManDailyRound.setNo(c1.getString(c1
                            .getColumnIndex("no")));
                    cls_saleManDailyRound.setManNo(c1.getString(c1
                            .getColumnIndex("ManNo")));
                    cls_saleManDailyRound.setCusNo(c1.getString(c1
                            .getColumnIndex("CusNo")));
                    cls_saleManDailyRound.setDayNum(c1.getString(c1
                            .getColumnIndex("DayNum")));
                    cls_saleManDailyRound.setTr_Data(c1.getString(c1
                            .getColumnIndex("Tr_Data")));
                    cls_saleManDailyRound.setStart_Time(c1.getString(c1
                            .getColumnIndex("Start_Time")));
                    cls_saleManDailyRound.setEnd_Time(c1.getString(c1
                            .getColumnIndex("End_Time")));

                    cls_saleManDailyRound.setDuration(c1.getString(c1
                            .getColumnIndex("Duration")));

                    cls_saleManDailyRound.setOrderNo(c1.getString(c1
                            .getColumnIndex("OrderNo")));

                    cls_saleManDailyRound.setNote(c1.getString(c1
                            .getColumnIndex("Note")));


                    cls_saleManDailyRound.setX_Lat(c1.getString(c1
                            .getColumnIndex("X_Lat")));


                    cls_saleManDailyRound.setY_Long(c1.getString(c1
                            .getColumnIndex("Y_Long")));


                    cls_saleManDailyRound.setLoct(c1.getString(c1
                            .getColumnIndex("Loct")));

                    cls_saleManDailyRound.setIsException(c1.getString(c1
                            .getColumnIndex("IsException")));



                    RoundList.add(cls_saleManDailyRound);

                } while (c1.moveToNext());

            }
            c1.close();
        }
        final String json = new Gson().toJson(RoundList);

        Calendar c = Calendar.getInstance();
        final int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        //  final Handler _handler = new Handler();


        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(MainActivity.this);
                ws.SaveManVisits(json);
                try {
                    if (We_Result.ID > 0) {
                        String query = " Update  SaleManRounds  set Posted=1  where Posted = '-1'";
                        sqlHandler.executeQuery(query);
                        _handler.post(new Runnable() {
                            public void run() {

                            }
                        });

                     /*   query = " delete from   SaleManRounds   where Posted =1 and  DayNum < " + dayOfWeek;
                        sqlHandler.executeQuery(query);*/
                    }
                } catch (final Exception e) {
                }
            }
        }).start();
    }
    public void Set_CustException(String No, String Nm,String IDN) {
        Toast.makeText(this ,String.valueOf(getResources().getString(R.string.exceptional)),Toast.LENGTH_LONG).show();
        TextView CustNm = (TextView) findViewById(R.id.tv_CustName);
        TextView acc = (TextView) findViewById(R.id.tv_Acc);
        acc.setText(No);
        CustNm.setText(Nm);
        acc.setError(null);
        IsException = "1" ;
        IDN1=IDN;
        HidKeybad();
    }
    public void Set_Cust(String No, String Nm,String IDN) {
        TextView CustNm = (TextView) findViewById(R.id.tv_CustName);
        TextView acc = (TextView) findViewById(R.id.tv_Acc);
        acc.setText(No);
        CustNm.setText(Nm);
        acc.setError(null);
        IsException = "0" ;
        IDN1=IDN;
        HidKeybad();
    }
    private  void HidKeybad(){
        try{
            if (this.getCurrentFocus() != null) {
                InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }catch (Exception ex){}
    }
    public void GetCustomer() {

        final TextView tv_x = (TextView) findViewById(R.id.tv_x);
        final TextView tv_y = (TextView) findViewById(R.id.tv_y);

        final TextView tv_CustName = (TextView) findViewById(R.id.tv_CustName);
        final TextView tv_Acc = (TextView) findViewById(R.id.tv_Acc);
        final TextView tv_Loc = (TextView) findViewById(R.id.tv_Loc);
        final TextView tv_Address = (TextView) findViewById(R.id.tv_Loc);

        String q = "";

        if (Week_Num.equalsIgnoreCase("1")) {

            if (dayOfWeek.equalsIgnoreCase("7") )
                q = " sat = 1 ";

            if (dayOfWeek.equalsIgnoreCase("1") )
                q = " sun = 1 ";

            if (dayOfWeek.equalsIgnoreCase("2") )
                q = " mon = 1 ";


            if (dayOfWeek.equalsIgnoreCase("3") )
                q = " tues = 1 ";

            if (dayOfWeek.equalsIgnoreCase("4") )
                q = " wens = 1 ";

            if (dayOfWeek.equalsIgnoreCase("5") )
                q = " thurs = 1 ";
        }
        if (Week_Num.equalsIgnoreCase("2")) {
            if (dayOfWeek.equalsIgnoreCase("7") )
                q = " sat1 = 1 ";

            if (dayOfWeek.equalsIgnoreCase("1") )
                q = " sun1 = 1 ";

            if (dayOfWeek.equalsIgnoreCase("2") )
                q = " mon1 = 1 ";


            if (dayOfWeek.equalsIgnoreCase("3") )
                q = " tues1 = 1 ";

            if (dayOfWeek.equalsIgnoreCase("4") )
                q = " wens1 = 1 ";

            if (dayOfWeek.equalsIgnoreCase("5") )
                q = " thurs1 = 1 ";

        }


        // Toast.makeText(this,contentTxt.getText().toString().substring(1,6),Toast.LENGTH_SHORT).show();
        q = "Select   c.no,c.name ,c.Latitude,c.Longitude,c.Address,c.barCode, c.SMan, c.State from Customers  c" +
                "     Where (  c.barCode='-1' or    c.barCode = '" + contentTxt.getText().toString() + "' ) And ( c.Latitude = '-1' or   c.Latitude = '" + tv_x.getText().toString() + "' )" +
                " And (  c.Longitude = '-1' or   c.Longitude = '" + tv_y.getText().toString() + "') And  c." + q;

        SqlHandler sqlHandler = new SqlHandler(this);

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                tv_CustName.setText(c1.getString(c1.getColumnIndex("name")));
                tv_Acc.setText(c1.getString(c1.getColumnIndex("no")));

                tv_Acc.setError(null);
            }
            c1.close();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    MainActivity.this).create();
            alertDialog.setTitle("خط بيع مندوب");

            alertDialog.setMessage("لا يوجد بيانات عميل لهذا المندوب");
            alertDialog.setIcon(R.drawable.delete);

            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
            // progressDialog.incrementProgressBy(1);
        }

    }

    public String GetDayName(String Day) {


        String DayNm = "";

        if   (Day.equalsIgnoreCase("1")) DayNm = String.valueOf(getResources().getString(R.string.Sunday));
        else if (Day.equalsIgnoreCase("2")) DayNm = String.valueOf(getResources().getString(R.string.Monday));
        else if (Day.equalsIgnoreCase("3")) DayNm = String.valueOf(getResources().getString(R.string.Tuesday));
        else if (Day.equalsIgnoreCase("4") ) DayNm =String.valueOf(getResources().getString(R.string.Wednesday));
        else if (Day.equalsIgnoreCase("5")) DayNm = String.valueOf(getResources().getString(R.string.Thursday));
        else if (Day.equalsIgnoreCase("6")) DayNm =String.valueOf(getResources().getString(R.string.Friday));
        else if (Day.equalsIgnoreCase("7") ) DayNm = String.valueOf(getResources().getString(R.string.Saturday));


        return DayNm;

    }

    public void BtnScan(View view) {
        if (view.getId() == R.id.scan_button) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
         /*   String address = "";
            GPSService mGPSService = new GPSService(view.getContext());
            mGPSService.getLocation();*/

        }


    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();


            contentTxt = (TextView) findViewById(R.id.scan_content);


            contentTxt.setText("");


            if (scanningResult.getContents() != null) {
                contentTxt.setText(scanContent.toString());

                GetCustomer();
            }
            //contentTxt.setText( "6253803400018");
            //  GetCustomer();


        }
        // GetCustomer();
    }

    public void btn_GetLocation(View view) {
        try {
            GetlocationNew();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        //Getlocation();

    }

    public void btn_back(View view) {
        Intent i = new Intent(this, JalMasterActivity.class);
        startActivity(i);
    }

    private void DoNew() {
        // ShowRecord();
        TextView CustNo = (TextView) findViewById(R.id.tv_Acc);
        TextView CustNm = (TextView) findViewById(R.id.tv_CustName);
        TextView tv_Address = (TextView) findViewById(R.id.tv_Loc);
        CustNo.setText("");
        CustNm.setText("");


        StartRound = (RelativeLayout) findViewById(R.id.btnStartRound);
        StartRound.setVisibility(View.VISIBLE);

        EndRound = (RelativeLayout) findViewById(R.id.btnEndRound);
        EndRound.setVisibility(View.INVISIBLE);

        TextView tv_Duration = (TextView) findViewById(R.id.tv_Duration);
        tv_Duration.setText("");


        locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria cri = new Criteria();
        String provider = locationmanager.getBestProvider(cri, false);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        TrDate = (TextView) findViewById(R.id.et_Date);
        TrDate.setText(currentDateandTime);

        Calendar c = Calendar.getInstance();
       // dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        DB.GetValue(this , "ServerDateTime", "DAYWEEK", "1=1");

        et_Day = (TextView) findViewById(R.id.et_Day);
        et_Day.setText(GetDayName(dayOfWeek));

        et_StartTime = (TextView) findViewById(R.id.et_StartTime);
        SimpleDateFormat StartTime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        String StringTime = StartTime.format(new Date());
        et_StartTime.setText(StringTime);


        et_EndTime = (TextView) findViewById(R.id.et_EndTime);
        et_EndTime.setText("");
        tv_Note.setText("");
        Getlocation();

    }

    public void ShowRecord() {


        TextView CustNo = (TextView) findViewById(R.id.tv_Acc);
        TextView CustNm = (TextView) findViewById(R.id.tv_CustName);


        sqlHandler = new SqlHandler(this);
        String query = "SELECT  SaleManRounds.no ,SaleManRounds.Note  , SaleManRounds.CusNo,SaleManRounds.Note ,Customers.name ,Tr_Data,DayNum,Start_Time  " +
                "FROM SaleManRounds Left join Customers on Customers.no =SaleManRounds.CusNo  where Closed = 0";
        Cursor c1 = sqlHandler.selectQuery(query);

        et_StartTime = (TextView) findViewById(R.id.et_StartTime);

        et_Day = (TextView) findViewById(R.id.et_Day);
        EndRound = (RelativeLayout) findViewById(R.id.btnEndRound);
        StartRound = (RelativeLayout) findViewById(R.id.btnStartRound);
        et_EndTime = (TextView) findViewById(R.id.et_EndTime);
        TextView tv_Duration = (TextView) findViewById(R.id.tv_Duration);

        tv_Duration.setText("");
        EndRound.setVisibility(View.INVISIBLE);
        StartRound.setVisibility(View.VISIBLE);
        if (c1.getCount() > 0 && c1 != null) {
            //Toast.makeText(this,"يوجد ملف  مفتوح",Toast.LENGTH_SHORT).show();
            c1.moveToFirst();
            CustNo.setText(c1.getString(c1.getColumnIndex("CusNo")));
            CustNm.setText(c1.getString(c1.getColumnIndex("name")));
            TrDate.setText(c1.getString(c1.getColumnIndex("Tr_Data")));
            et_StartTime.setText(c1.getString(c1.getColumnIndex("Start_Time")));
            tv_Note.setText(c1.getString(c1.getColumnIndex("Note")));

            et_Day.setText(GetDayName((c1.getString(c1.getColumnIndex("DayNum")))));
            EndRound.setVisibility(View.VISIBLE);
            StartRound.setVisibility(View.INVISIBLE);
            c1.close();
            // OrderNo = c1.getInt(c1.getColumnIndex("no"));

        }
        tv_Duration.setText("");
        SimpleDateFormat StartTime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        String StringTime = StartTime.format(new Date());
        et_EndTime.setText(StringTime);


        // et_StartTime
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

            Date date1 = simpleDateFormat.parse(et_StartTime.getText().toString());
            Date date2 = simpleDateFormat.parse(et_EndTime.getText().toString());

            long diff = date2.getTime() - date1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            tv_Duration.setText(String.valueOf(diffHours) + ":" + String.valueOf(diffMinutes) + ":" + String.valueOf(diffSeconds));
        } catch (Exception ex) {
        }

    }

    public void btn_delete(View view) {
        sqlHandler.Delete("SaleManRounds", "");
    }

    public void btm_delete(View view) {
        sqlHandler = new SqlHandler(this);

        String query = " delete from   SaleManRounds   ";
        sqlHandler.executeQuery(query);

    }
    public void Save_Cust_Location(View view) {
        if (ComInfo.ComNo == 4) {
            if (!UserPermission.CheckAction(this, "30024", SCR_ACTIONS.Insert.getValue())) {
                com.cds_jo.GalaxySalesApp.ViewDialog alert = new com.cds_jo.GalaxySalesApp.ViewDialog();
                alert.showDialog(MainActivity.this, "نأسف أنت لا تملك الصلاحية  ", "حفظ موقع العميل");
                return;
            } else {
                SaveCustLocation();
            }


        } else {


            if (ComInfo.ComNo == Companies.Ukrania.getValue() ||   (ComInfo.ComNo == Companies.beutyLine.getValue()) || ComInfo.ComNo == Companies.Ma8bel.getValue()) {
                SaveCustLocation();
            } else {
                //    if (ComInfo.ComNo == 1 || ComInfo.ComNo==4) {

                String Man = DB.GetValue(MainActivity.this, "Tab_Password", "ManNo", "PassNo = 10 AND ManNo ='" + UserID + "'");
                final String pass = DB.GetValue(MainActivity.this, "Tab_Password", "Password", "PassNo = 10");
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle(DB.GetValue(MainActivity.this, "Tab_Password", "PassDesc", "PassNo = 10"));
                alertDialog.setMessage("ادخل رمز التحقق");

                final EditText input = new EditText(MainActivity.this);
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
                                    SaveCustLocation();
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
        }

     /*    else{

            SaveCustLocation();
        }*/
    }
    private void SaveCustLocation() {
        final TextView tv_x = (TextView) findViewById(R.id.tv_x);
        final TextView tv_y = (TextView) findViewById(R.id.tv_y);
        TextView CustNo = (TextView) findViewById(R.id.tv_Acc);


        if (CustNo.getText().toString().length() == 0) {
            CustNo.setError("required!");
            CustNo.requestFocus();
            return;
        }

        if (tv_y.getText().toString().length() == 0) {
            tv_y.setError("required!");
            tv_y.requestFocus();
            return;
        }

        if (tv_x.getText().toString().length() == 0) {
            tv_x.setError("required!");
            tv_x.requestFocus();
            return;
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sqlHandler = new SqlHandler(this);

        TextView CustNm = (TextView) findViewById(R.id.tv_CustName);
        TextView Custadd = (TextView) findViewById(R.id.tv_Loc);

        TrDate = (TextView) findViewById(R.id.et_Date);
        et_StartTime = (TextView) findViewById(R.id.et_StartTime);
        ContentValues cv = new ContentValues();
        cv.put("CusNo", CustNo.getText().toString());
        cv.put("Lat", tv_x.getText().toString());
        cv.put("Long", tv_y.getText().toString());
        cv.put("Address", Custadd.getText().toString());
        cv.put("date", TrDate.getText().toString());
        cv.put("UserID", sharedPreferences.getString("UserID", "-1"));
        cv.put("posted", "-1");
        long i;


        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setTitle("تحديد موقع العميل");

        String q = "select * from CustLocation where CusNo ='" + CustNo.getText().toString() + "'";
        Cursor c = sqlHandler.selectQuery(q);

        if (c != null && c.getCount() > 0) {
            i = sqlHandler.Update("CustLocation", cv, "CusNo ='" + CustNo.getText().toString() + "'");
            String s = "Update  Customers set Latitude = '" + tv_x.getText().toString() + "',  Longitude ='" + tv_y.getText().toString() + "' where no = '" + CustNo.getText().toString() + "'";
            sqlHandler.executeQuery(s);
            alertDialog.setMessage("تمت عملية تعديل موقع العميل بنجاح");
            c.close();
        } else {
            i = sqlHandler.Insert("CustLocation", null, cv);
            String s = "Update  Customers set Latitude = '" + tv_x.getText().toString() + "',  Longitude ='" + tv_y.getText().toString() + "' where no = '" + CustNo.getText().toString() + "'";
            sqlHandler.executeQuery(s);
            alertDialog.setMessage("عملية تخزين موقع العميل تمت بنجاح");


        }


        if (i > 0) {
            alertDialog.setIcon(R.drawable.tick);
        } else {
            alertDialog.setMessage("عملية  الحفظ لم تتم ");
            alertDialog.setIcon(R.drawable.delete);
        }

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SharcustLocation();
            }
        });


        alertDialog.show();


    }

    public void SharcustLocation() {

        //sqlHandler=new SqlHandler(this);

        final Handler _handler = new Handler();
        String query = "  select distinct CusNo ,Lat ,Long ,Address, date, UserID from CustLocation   where posted = -1";
        Cursor c1 = sqlHandler.selectQuery(query);
        ArrayList<Cls_CustLocation> objlist;
        objlist = new ArrayList<Cls_CustLocation>();
        objlist.clear();


        if (c1 != null && c1.getCount() > 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_CustLocation obj = new Cls_CustLocation();
                    obj.setCusNo(c1.getString(c1
                            .getColumnIndex("CusNo")));
                    obj.setLat(c1.getString(c1
                            .getColumnIndex("Lat")));
                    obj.setLong(c1.getString(c1
                            .getColumnIndex("Long")));
                    obj.setAddress(c1.getString(c1
                            .getColumnIndex("Address")));
                    obj.setDate(c1.getString(c1
                            .getColumnIndex("date")));
                    obj.setUserID(c1.getString(c1
                            .getColumnIndex("UserID")));
                    obj.setPosted("-1");

                    objlist.add(obj);

                } while (c1.moveToNext());

            }
            c1.close();
        }
        final String json = new Gson().toJson(objlist);
        new Thread(new Runnable() {
            @Override
            public void run() {

                CallWebServices ws = new CallWebServices(MainActivity.this);
                ws.SaveCustLocation(json);
                try {
                    if (We_Result.ID > 0) {
                        String query = " Update  CustLocation  set Posted='1'  where Posted = '-1'";
                        sqlHandler.executeQuery(query);
                        _handler.post(new Runnable() {
                            public void run() {



                            }
                        });


                    }
                } catch (final Exception e) {

                }
            }
        }).start();
    }

    public String GetMaxPONo() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String u = sharedPreferences.getString("UserID", "");
        u = u.trim();
        String query = "SELECT  ifnull(MAX(OrderNo), 0) +1 AS no FROM SaleManRounds where  ManNo ='" + u.toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(query);
        String max = "0";

        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            max = c1.getString(c1.getColumnIndex("no"));
            c1.close();
        }

        String max1 = "0";
        max1 = DB.GetValue(MainActivity.this, "OrdersSitting", "Visits", "1=1").replaceAll("[^\\d.]", "");


        if (max1 == "") {
            max1 = "0";
        }

        max1 = String.valueOf(Integer.parseInt(max1) + 1);
        if (SToD(max1) > SToD(max)) {
            max = max1;
        }


        if (max.length() == 1) {
            max = intToString(Integer.parseInt(u), 2) + intToString(Integer.parseInt(max), 5);
        } else {
            max = (intToString(Integer.parseInt(max), 7));
        }
        return max;
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

    public void btn_Home(View view) {

        Intent k = new Intent(this, JalMasterActivity.class);
        startActivity(k);
    }

    public void SearchArea(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "Gps");
        FragmentManager Manager = getFragmentManager();
        Select_Customer_Dis obj = new Select_Customer_Dis();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }

    public void SearchExceptions(View view) {
        //UpdateUserExceptions();
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "Gps");
        FragmentManager Manager = getFragmentManager();
        Select_Customer_Expetion obj = new Select_Customer_Expetion();
        obj.setArguments(bundle);
        obj.show(Manager, null);

    }

    private void ShowExceptionPop() {

        Bundle bundle = new Bundle();
        bundle.putString("Scr", "ExpGps");

        FragmentManager Manager = getFragmentManager();
        Select_Customer obj = new Select_Customer();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }

    private void UpdateUserExceptions() {


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

        final Handler _handler = new Handler();
        final ProgressDialog custDialog = new ProgressDialog(MainActivity.this);
        custDialog.setProgressStyle(custDialog.STYLE_SPINNER);
        custDialog.setCanceledOnTouchOutside(false);
        custDialog.setProgress(0);
        custDialog.setMax(100);
        custDialog.setMessage("  الرجاء الانتظار ..." + "  العمل جاري على نسخ البيانات  ");
        tv.setText(" الاستثناءات");
        custDialog.setCustomTitle(tv);
        custDialog.setProgressDrawable(greenProgressbar);
        custDialog.show();

        q = "Delete from ManExceptions";
        sqlHandler.executeQuery(q);
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(MainActivity.this);
                ws.Get_ManExceptions(UserID);

                try {

                    q = "Delete from ManExceptions";
                    sqlHandler.executeQuery(q);

                    q = " delete from sqlite_sequence where name='ManExceptions'";
                    sqlHandler.executeQuery(q);

                    Integer i;
                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray js_CustNo = js.getJSONArray("CustNo");
                    JSONArray js_ExptCat = js.getJSONArray("ExptCat");
                    JSONArray js_Freq = js.getJSONArray("Freq");
                    JSONArray js_ExtraAmt = js.getJSONArray("ExtraAmt");
                    JSONArray js_ExtraPrecent = js.getJSONArray("ExtraPrecent");

                    for (i = 0; i < js_CustNo.length(); i++) {

                        q = "INSERT INTO ManExceptions(CustNo, ExptCat  , Freq ,ExtraAmt,ExtraPrecent) values ('"
                                + js_CustNo.get(i).toString()
                                + "','" + js_ExptCat.get(i).toString()
                                + "','" + js_Freq.get(i).toString()
                                + "','" + js_ExtraAmt.get(i).toString()
                                + "','" + js_ExtraPrecent.get(i).toString()
                                + "')";
                        sqlHandler.executeQuery(q);
                        if (custDialog.getProgress() == custDialog.getMax()) {
                            custDialog.dismiss();

                        }
                    }

                    _handler.post(new Runnable() {

                        public void run() {

                            custDialog.dismiss();

                            ShowExceptionPop();


                        }
                    });

                } catch (final Exception e) {

                    _handler.post(new Runnable() {

                        public void run() {
                            custDialog.dismiss();
                            ShowExceptionPop();
                        }
                    });
                }
            }
        }).start();

    }



    public void GetData() {



        final Handler _handler = new Handler();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String UserID = sharedPreferences.getString("UserID", "");



        new Thread(new Runnable() {
            @Override
            public void run() {




                CallWebServices ws = new CallWebServices(MainActivity.this);
                ws.GetSalesManAtt(UserID );
                try {
                    Integer i;
                    String q = "";
                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray js_ID = js.getJSONArray("ID");

                    JSONArray js_ActionNo = js.getJSONArray("ActionNo");



                    /*q = "Delete from Man_Vac";
                    sql_Handler.executeQuery(q);
                    q = "delete from sqlite_sequence where name='Man_Vac'";
                    sql_Handler.executeQuery(q);*/
                    Cls_Att obj;
                    for (i = 0; i < js_ID.length(); i++) {
                        ArrayAtts.add(Integer.parseInt(js_ActionNo.get(i).toString()));


                    }






                } catch (final Exception e) {

                }
            }
        }).start();
    }
    private void CheckCelingOld(String Amt) {

        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        String q;


        String Man = DB.GetValue(MainActivity.this, "Tab_Password", "ManNo", "PassNo = 13 ");
        final String pass = DB.GetValue(MainActivity.this, "Tab_Password", "Password", "PassNo =13 ");


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("رمز التحقق ");
        alertDialog.setMessage("لقد تجاوزت الجولة الاستثنائية للعميل ، سقف تسهيلات العميل هو " + Amt);

        final EditText input = new EditText(MainActivity.this);
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
    private void createNotificationChannel() {


        Intent notificationIntent = new Intent(getApplicationContext() , MainActivity. class ) ;
        notificationIntent.putExtra( "fromNotification" , true ) ;
        notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP ) ;
        PendingIntent pendingIntent = PendingIntent. getActivity ( this, 0 , notificationIntent , 0 ) ;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
        mBuilder.setContentTitle( "الاشعارات" ) ;
        mBuilder.setContentIntent(pendingIntent) ;
        mBuilder.setContentText( "يجب تحصيل من العميل " ) ;
        mBuilder.setSmallIcon(R.drawable. ic_baseline_notifications_24 ) ;
        mBuilder.setAutoCancel( true ) ;
        mBuilder.setBadgeIconType( NotificationCompat.BADGE_ICON_NONE ) ;
        mBuilder.setNumber( 1 ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;


    }
    private void Save_Recod_Po() {
        try{
            /*if (ComInfo.ComNo == Companies.diamond.getValue()) {
                TextView tv_Acc = (TextView) findViewById(R.id.tv_Acc);
               int Allow = Integer.parseInt(DB.GetValue(this, "Customers_man", " Location", "IDN='" + IDN1 + "' limit 1"));
                int not =Integer.parseInt( DB.GetValue(this, "Customers_man", " LocationNo", "IDN='" + IDN1 + "' limit 1"));
                int howpay =Integer.parseInt( DB.GetValue(this, "Customers_man", " Pay_How", "IDN='" + IDN1 + "' limit 1"));
                int BB =Integer.parseInt( DB.GetValue(this, "Customers", " BB_bill", "no='" + tv_Acc + "' limit 1"));
                if(howpay==2) {
                    if (Allow == 0 || not < Allow || BB == 0) {

                    } else {
                        createNotificationChannel();
                    }
                }



            }*/
                SqlHandler sql_Handler;

                sql_Handler = new SqlHandler(this);

                String query = "SELECT * FROM ManLogTrans WHERE no = (SELECT MAX(no) FROM ManLogTrans)";
                Cursor c1 = sql_Handler.selectQuery(query);
                if (c1 != null && c1.getCount() != 0) {
                    if (c1.moveToFirst()) {
                        Toast.makeText(MainActivity.this, c1.getString(c1.getColumnIndex("ActionNo")), Toast.LENGTH_LONG).show();
                        if (ArrayAtts.get(0) != 2) {
                       // if (2== 2) {


                            TextView CustNo = (TextView) findViewById(R.id.tv_Acc);
                            if (CustNo.getText().toString().length() == 0) {
                                CustNo.setError("required!");
                                CustNo.requestFocus();
                                return;
                            }
                            if (tv_x.getText().toString().equals("") || tv_y.getText().toString().equals("")) {
                                Toast.makeText(MainActivity.this, String.valueOf(getResources().getString(R.string.PerLocation)), Toast.LENGTH_LONG).show();
                                return;
                            }
                            Location startPoint = new Location("locationA");
                            startPoint.setLatitude(Double.parseDouble(tv_x.getText().toString()));
                            startPoint.setLongitude(Double.parseDouble(tv_y.getText().toString()));

                            String Cust_x = "";
                            String Cust_y = "";
                            Cust_x = DB.GetValue(this, "Customers_man", "Latitude", "IDN='" + IDN1 + "'");
                            Cust_y = DB.GetValue(this, "Customers_man", "Longitude", "IDN='" + IDN1 + "'");
                            if (Cust_x.equals("")) {
                                Cust_x = "0";
                            }
                            if (Cust_y.equals("")) {
                                Cust_y = "0";
                            }
                            //mohsss
                            else if (ComInfo.ComNo != Companies.Afrah.getValue()) {
                                Location endPoint = new Location("locationA");
                                endPoint.setLatitude(Double.parseDouble(Cust_x));
                                endPoint.setLongitude(Double.parseDouble(Cust_y));

                                double distance = startPoint.distanceTo(endPoint);
                                if (!(Cust_x.equals("0") || Cust_y.equals("0")))
                                    if (distance > 50) {
                                        Toast.makeText(MainActivity.this, String.valueOf(getResources().getString(R.string.notopenvivit)), Toast.LENGTH_LONG).show();
                                        return;
                                    }
                            }
                            OrderNo = GetMaxPONo();
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                            sqlHandler = new SqlHandler(this);

                            TextView CustNm = (TextView) findViewById(R.id.tv_CustName);
                            TextView Custadd = (TextView) findViewById(R.id.tv_Loc);

                            TrDate = (TextView) findViewById(R.id.et_Date);
                            et_StartTime = (TextView) findViewById(R.id.et_StartTime);

                            ContentValues cv = new ContentValues();
                            cv.put("CusNo", CustNo.getText().toString());
                            cv.put("ManNo", sharedPreferences.getString("UserID", ""));
                            cv.put("DayNum", dayOfWeek);
                            cv.put("Tr_Data", TrDate.getText().toString());
                            cv.put("Start_Time", et_StartTime.getText().toString());
                            cv.put("Closed", "0");
                            cv.put("Posted", "-1");
                            cv.put("OrderNo", OrderNo);
                            cv.put("Note", tv_Note.getText().toString());
                            cv.put("X_Lat", tv_x.getText().toString());
                            cv.put("IsException", IsException);
                            cv.put("Y_Long", tv_y.getText().toString());
                            cv.put("Loct", tv_Loc.getText().toString());
                            cv.put("IDN",IDN1);

                            final String CusNm = CustNm.getText().toString();
                            final String CusNo = CustNo.getText().toString();
                            long i;
                            i = sqlHandler.Insert("SaleManRounds", null, cv);
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    this).create();


                            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.Galaxy)));
                            if (i > 0) {
//moh525
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("CustNo", CustNo.getText().toString());
                                editor.putString("CustNm", CustNm.getText().toString());
                                editor.putString("CustAdd", Custadd.getText().toString());
                                editor.putString("PayCount", "0");
                                editor.putString("InvCount", "0");
                                editor.putString("V_OrderNo", OrderNo);
                                editor.putString("IDN", IDN1);
                                editor.commit();
                                InsertLogTrans obj = new InsertLogTrans(MainActivity.this, SCR_NO, SCR_ACTIONS.StartVisit.getValue(), OrderNo, CustNo.getText().toString(), "",IsException);
                                alertDialog.setMessage(String.valueOf(getResources().getString(R.string.StartVisitSucc)) + OrderNo);
                                alertDialog.setIcon(R.drawable.tick);
                                StartRound.setVisibility(View.INVISIBLE);
                                EndRound.setVisibility(View.VISIBLE);
                                StartRound = (RelativeLayout) findViewById(R.id.btnStartRound);
                                UpDateMaxOrderNo();


            /*if (ComInfo.ComNo != 4) {
                stopService(new Intent(MainActivity.this, AutoPostTrans.class));
            }*/
           /* if(ComInfo.ComNo==4) {
                stopService(new Intent(MainActivity.this,AutoPostLocation.class));
            }*/

                                stopService(new Intent(MainActivity.this, AutoPostLocation.class));
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                                String currentDateandTime = sdf.format(new Date());


                                String Amt;
                                Msg = DB.GetValue(MainActivity.this, "CustomersMsg", "msg", "  SaleManFlg ='2' and  Cusno=" + CustNo.getText().toString()).toString();
                                //  Tr_date custNo Amt orderDate

                                //  Amt=DB.GetValue(MainActivity.this, "InvoicePaymentSchedule", "sum( cast( Amt as float))", "  Tr_date ='"+currentDateandTime +"' and  custNo='" + CustNo.getText().toString()+"'").toString();
//sum( cast( Amt as float))  AS no

                                query = "SELECT   ifnull(Amt,'0')  AS no  , ifnull(New_Amt,'0') as New_Amt " +
                                        "  ,ifnull(SupervisorNutes,' ') as SupervisorNutes ,ifnull(New_Tr_date,' ') as New_Tr_date " +
                                        " FROM InvoicePaymentSchedule Where ( Tr_date ='" + currentDateandTime + "' or New_Tr_date ='" + currentDateandTime + "' )and  custNo='" + CustNo.getText().toString() + "'";
                                c1 = sqlHandler.selectQuery(query);


                                if (c1 != null && c1.getCount() != 0) {
                                    c1.moveToFirst();
                                    if (Msg.toString().length() > 2) {
                                        Msg = Msg + "\r\n" + "  يوجد دفعات مستحقة " + "القيمة الأصلية" + ":" + c1.getString(c1.getColumnIndex("no"));
                                        Msg = Msg + "\r\n" + "تعديل القيمة من المشرف " + ":" + c1.getString(c1.getColumnIndex("New_Amt"));
                                        Msg = Msg + "\r\n" + "تعديل التاريخ من المشرف " + ":" + c1.getString(c1.getColumnIndex("New_Tr_date"));
                                        Msg = Msg + "\r\n" + "ملاحظات  المشرف " + ":" + c1.getString(c1.getColumnIndex("SupervisorNutes"));
                                    } else {
                                        Msg = "  يوجد دفعات مستحقة " + "القيمة" + ":" + c1.getString(c1.getColumnIndex("no"));
                                        Msg = Msg + "\r\n" + "تعديل القيمة من المشرف " + ":" + c1.getString(c1.getColumnIndex("New_Amt"));
                                        Msg = Msg + "\r\n" + "تعديل التاريخ من المشرف " + ":" + c1.getString(c1.getColumnIndex("New_Tr_date"));
                                        Msg = Msg + "\r\n" + "ملاحظات  المشرف " + ":" + c1.getString(c1.getColumnIndex("SupervisorNutes"));
                                    }
                                }


                            } else {

                                alertDialog.setMessage(String.valueOf(getResources().getString(R.string.dontsave)));
                                alertDialog.setIcon(R.drawable.delete);
                            }


                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    if (Msg.toString().length() > 3) {
                                        noti.notify(MainActivity.this, "\r\n" + Msg, String.valueOf(getResources().getString(R.string.takeCustNotesMsg)), CusNm, id);
                                        id++;
                                    }


                                    if (ComInfo.ComNo != Companies.Arabian.getValue()) {
                                        Bundle bundle = new Bundle();
                                        FragmentManager Manager = getFragmentManager();
                                        PopSmallMenue obj = new PopSmallMenue();
                                        bundle.putString("Msg", CusNo + "   " + CusNm);
                                        obj.setArguments(bundle);
                                        obj.show(Manager, null);
                                    }
                                }
                            });


                            alertDialog.show();

                        } else {
                            Toast.makeText(MainActivity.this, "يجب بدء الدوام اولا", Toast.LENGTH_LONG).show();

                        }



                }

            }
        }catch(Exception exception){
            Toast.makeText(MainActivity.this, exception.getMessage()+"س", Toast.LENGTH_LONG).show();
        }
    }

    private void CheckCelingNew(String Amt) {
        Long i;
        final SqlHandler sql_Handler = new SqlHandler(this);
        TextView acc = (TextView) findViewById(R.id.tv_Acc);
       // TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        TextView custNm = (TextView) findViewById(R.id.tv_CustName);
        ContentValues cv = new ContentValues();
        TextView NetTotal = (TextView) findViewById(R.id.et_Amt);
        String t = "";
        OrderNo = GetMaxPONo();
        String Order_Status = "";
        final String ManNo, CustNo, Desc, Type;

        String q1 = "Select     ifnull(Status,-1) as  Status  From PermissionCode Where CustNo='"+acc.getText().toString()+"' and  Type='4' AND OrderNo='" + OrderNo + "'   Order by no desc limit 1";
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
       // OrderNo = pono.getText().toString();
        OrderNo = GetMaxPONo();
        t = " اسم المندوب : ";
        t = t + UserNm;
        t = t + " اسم العميل : ";
        t = t + custNm.getText().toString();
        t = t + "  رقم الجولة : ";
        t = t + OrderNo;

//        t = t + NetTotal.getText().toString();

        Desc = t;
        Type = "4";
        cv.put("ManNo", ManNo);
        cv.put("CustNo", CustNo);
        cv.put("OrderNo", OrderNo);
        cv.put("Status", "0");
        cv.put("Desc", Desc);
        cv.put("Type", Type);
        i = sql_Handler.Insert("PermissionCode", null, cv);


        if (i > 0) {


            loadingdialog = ProgressDialog.show(MainActivity.this, "الرجاء الانتظار ...", "العمل جاري على طلب تفعيل الجولة الاستثنائية", true);
            loadingdialog.setCancelable(false);
            loadingdialog.setCanceledOnTouchOutside(false);
            loadingdialog.show();
            final Handler _handler = new Handler();

            new Thread(new Runnable() {
                @Override
                public void run() {

                    CallWebServices ws = new CallWebServices(MainActivity.this);
                    ws.SendRequestPermission(ManNo, CustNo, OrderNo, Desc, "4", "1");
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
                                            MainActivity.this).create();
                                    alertDialog.setTitle("طلب تفعيل الجولة الاستثنائية");
                                    alertDialog.setMessage("تمت عملية طلب تفعيل الجولة الاستثنائية رقم الحركة هو :" + We_Result.ID);
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
                                            MainActivity.this).create();
                                    alertDialog.setTitle("فشل في العملية  " + "   " + We_Result.ID + "");
                                    alertDialog.setMessage(We_Result.Msg.toString());
                                    alertDialog.setIcon(R.drawable.tick);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    alertDialog.show();
                                    alertDialog.setIcon(R.drawable.delete);
                                    alertDialog.setMessage("طلب تفعيل الجولة الاستثنائية لم يتم بنجاح" + "    ");
                                }
                            });
                        }

                    } catch (final Exception e) {
                        loadingdialog.dismiss();
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        MainActivity.this).create();
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
    public void CreateAlertDialogWithRadioButtonGroup(final String amt) {


        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);

        builder.setTitle("الرجاء اختيار طريقة تفعيل  الموافقة على الجولة الاستثنائية ");

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


        TextView acc = (TextView) findViewById(R.id.tv_Acc);
        // TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        TextView custNm = (TextView) findViewById(R.id.tv_CustName);
        ContentValues cv = new ContentValues();


        final String ManNo, CustNo, OrderNo, Desc, Type;

        ManNo = UserID;
        CustNo = acc.getText().toString();
        OrderNo = GetMaxPONo();

        Type = "4";

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
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setMessage("  الرجاء الانتظار ..." + "  العمل جاري على نسخ البيانات  ");
        tv.setText("طلب تفعيل الجولة الاستثنائية");
        progressDialog.setCustomTitle(tv);
        progressDialog.setProgressDrawable(greenProgressbar);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(MainActivity.this);
                ws.Get_Request_Permission(ManNo, CustNo, OrderNo, "4");
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
                                        MainActivity.this).create();
                                alertDialog.setTitle(" طلب الموافقة على الجولة الاستثنائية");
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
                                        MainActivity.this).create();
                                alertDialog.setTitle(" طلب الموافقة على الجولة الاستثنائية");
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
                                        MainActivity.this).create();
                                alertDialog.setTitle(" طلب الموافقة على الجولة الاستثنائية");
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

}
