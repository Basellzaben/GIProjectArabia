package com.cds_jo.GalaxySalesApp.NewPackage;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.AbuHaltam.ProductionlineAct;
import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import header.Header_Frag;

public class MonthlySalesManSchedule extends FragmentActivity {
    ListView AllDays_Lst, Lst_Country_Day, LstView_Week_days, LstWeekNo;
RecyclerView Lst_Country_All, Customers;
    ArrayList<Cls_Monthly_Schedule> AddDays;
    ArrayList<Cls_WeekDays> weekDaysList;
    ArrayList<Cls_WeekDays> weekNoList;
    ArrayList<Cls_Country> CountryOfDay;
    ArrayList<Cls_Country> AllCountry;
    ArrayList<Cls_Country> AllCustomers;
    ArrayList<keys_modle> cls_invf_List;
    long PostResult = 0;
    TextView tv;
    EditText Shers;
    Button deleteShera;
    String country;
    Cls_Customers_All_adapter cls_Customers_sechedule_adapter;
    SharedPreferences sharedPreferences;
    Drawable greenProgressbar;
    RelativeLayout.LayoutParams lp;
    SqlHandler sqlHandler;
    Cls_Monthly_Sechedule_adapter cls_monthly_sechedule_adapter;
    String q;
    String UserID = "";
    ListView lv;
    String WeekNo, DayNo, PeriodNo, Enter_Status;
    CheckBox chk_Period2, chk_Period1, chk_all, chk_Enter_all, chk_Enter, chk_Not_Enter,chk_DoubleVisit;
    Cls_Monthly_Schedule SelectedDate;
    Methdes.MyTextView tv_Month, ed_Year;
    public ProgressDialog loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_sales_man_schedule);
        AllDays_Lst = (ListView) findViewById(R.id.AllDays);
        Lst_Country_Day = (ListView) findViewById(R.id.Lst_Country_Day);
        Lst_Country_All = (RecyclerView) findViewById(R.id.Lst_Country_All);

        Customers = (RecyclerView) findViewById(R.id.customers);
        chk_DoubleVisit = (CheckBox) findViewById(R.id.chk_DoubleVisit);
        deleteShera = (Button) findViewById(R.id.deleteShera);


        chk_DoubleVisit.setChecked(false);
        chk_DoubleVisit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    q = "Update Month_Dates set DoubleVisit='1' Where  TodayDate = '" + GloblaVar.Date + "' and PeriodNo ='" + GloblaVar.PeriodNo + "'";
                    sqlHandler.executeQuery(q);
                }
                else
                {
                    q = "Update Month_Dates set DoubleVisit='0' Where  TodayDate = '" + GloblaVar.Date + "' and PeriodNo ='" + GloblaVar.PeriodNo + "'";
                    sqlHandler.executeQuery(q);
                }
            }
        });

        Shers =(EditText)findViewById(R.id.Shers) ;
        Shers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FillAllCountry(Shers.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        LstView_Week_days = (ListView) findViewById(R.id.LstWeekDays);
        LstWeekNo = (ListView) findViewById(R.id.LstWeekNo);

        lv = (ListView) findViewById(R.id.lv);

        tv_Month = (Methdes.MyTextView) findViewById(R.id.tv_Month);
        ed_Year = (Methdes.MyTextView) findViewById(R.id.ed_Year);
        SimpleDateFormat MonthFormt = new SimpleDateFormat("MM", Locale.ENGLISH);
        String currentMonth = MonthFormt.format(new Date());

        tv_Month.setText(Integer.parseInt(currentMonth) + "");

        SimpleDateFormat YearFormt = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        String currentYear = YearFormt.format(new Date());

        ed_Year.setText(currentYear);

        chk_Period2 = (CheckBox) findViewById(R.id.chk_Period2);
        chk_Period1 = (CheckBox) findViewById(R.id.chk_Period1);
        chk_all = (CheckBox) findViewById(R.id.chk_all);

        chk_Enter_all = (CheckBox) findViewById(R.id.chk_Enter_all);
        chk_Enter = (CheckBox) findViewById(R.id.chk_Enter);
        chk_Not_Enter = (CheckBox) findViewById(R.id.chk_Not_Enter);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        UserID = sharedPreferences.getString("UserID", "");
        SelectedDate = new Cls_Monthly_Schedule();
        SelectedDate.setDate("");
        chk_all.setChecked(true);
        chk_Enter_all.setChecked(true);


        chk_Enter_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    Enter_Status = "-1";
                    FillAllDays();
                    chk_Enter.setChecked(false);
                    chk_Not_Enter.setChecked(false);
                }

            }

        });
        chk_Enter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    Enter_Status = "1";
                    FillAllDays();
                    chk_Enter_all.setChecked(false);
                    chk_Not_Enter.setChecked(false);
                }

            }

        });

        chk_Not_Enter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    Enter_Status = "2";
                    FillAllDays();
                    chk_Enter_all.setChecked(false);
                    chk_Enter.setChecked(false);
                }

            }

        });


        chk_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    PeriodNo = "-1";
                    FillAllDays();
                    chk_Period2.setChecked(false);
                    chk_Period1.setChecked(false);
                }

            }

        });
        chk_Period2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    PeriodNo = "2";
                    FillAllDays();
                    chk_all.setChecked(false);
                    chk_Period1.setChecked(false);
                }

            }

        });

        chk_Period1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    PeriodNo = "1";
                    FillAllDays();
                    chk_all.setChecked(false);
                    chk_Period2.setChecked(false);
                }

            }

        });

        sqlHandler = new SqlHandler(this);
        AllDays_Lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View argu, int position, long arg3) {
                try {

                    Shers.setText("");
                    SelectedDate = (Cls_Monthly_Schedule) AllDays_Lst.getItemAtPosition(position);
                    GloblaVar.PeriodNo = SelectedDate.PeriodNo;
                    GloblaVar.Date = SelectedDate.Date;

                    FillAllCountry("");

                    GloblaVar.selected = position;
                    GloblaVar.arq = argu;
                    GloblaVar.arg0 = arg0;
                    //   FillAllCountryOfDay();
                    showCustomer("2");
                    argu.setBackgroundColor(Color.GRAY);
                    for (int i = 0; i < AllDays_Lst.getChildCount(); i++) {
                        View listItem = AllDays_Lst.getChildAt(i);
                        if (i % 2 == 0)
                            listItem.setBackgroundColor(Color.WHITE);
                        if (i % 2 == 1)
                            listItem.setBackgroundColor(MonthlySalesManSchedule.this.getResources().getColor(R.color.Gray2));
                    }
                    argu.setBackgroundColor(Color.GRAY);

                } catch (Exception ex) {
                    Toast.makeText(MonthlySalesManSchedule.this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }

                try {
                    FillKeys(SelectedDate.Date, SelectedDate.PeriodNo);
                    GloblaVar.Date = SelectedDate.Date;
                    GloblaVar.PeriodNo = SelectedDate.PeriodNo;
                    GloblaVar.UserID = UserID;
                    SelectedDate.setCountryId("0");

                    FillAllCountry("");
                    chk_DoubleVisit.setChecked(false);
                    String DoubleVisit = DB.GetValue(MonthlySalesManSchedule.this, "Month_Dates", "DoubleVisit", " TodayDate = '" + GloblaVar.Date + "' and PeriodNo ='" + GloblaVar.PeriodNo + "'");
//moh78
                    if(DoubleVisit.equals("1"))
                    {
                        chk_DoubleVisit.setChecked(true);
                    }
                    else {
                        chk_DoubleVisit.setChecked(false);
                    }
                    // FillAllCustomers("CountryNo = '0'");
                    showCustomer("2");
                } catch (Exception e) {
                }
            }
        });

        LstWeekNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View argu, int position, long arg3) {
                try {
                    lv.setAdapter(null);
                    Shers.setText("");
                    Cls_WeekDays o = (Cls_WeekDays) LstWeekNo.getItemAtPosition(position);
                    WeekNo = o.getNo();
                    FillAllDays();
                    //    FillAllCountry();
                    //  FillAllCustomers("-1");
                    argu.setBackgroundColor(Color.GRAY);
                    for (int i = 0; i < LstWeekNo.getChildCount(); i++) {
                        View listItem = LstWeekNo.getChildAt(i);
                        if (i % 2 == 0)
                            listItem.setBackgroundColor(Color.WHITE);
                        if (i % 2 == 1)
                            listItem.setBackgroundColor(MonthlySalesManSchedule.this.getResources().getColor(R.color.Gray2));
                    }

                    GetTodayAllArea("", "");
                    AllCountry.clear();
                    // Cls_Customers_All_adapter s =new Cls_Customers_All_adapter;
                    Lst_Country_All.setAdapter(null);
                    Customers.setAdapter(null);
                    argu.setBackgroundColor(Color.GRAY);
                } catch (Exception ex) {
                    Toast.makeText(MonthlySalesManSchedule.this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        LstView_Week_days.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View argu, int position, long arg3) {
                try {
                    Cls_WeekDays o = (Cls_WeekDays) LstView_Week_days.getItemAtPosition(position);
                    DayNo = o.getNo();
                    Shers.setText("");
                    FillAllDays();
                    // FillAllCustomers("CountryNo = '0'");

                    // FillAllCountry();
                    // showCustomer("2");
                    // FillKeys("0", "0");
                    argu.setBackgroundColor(Color.GRAY);
                    for (int i = 0; i < LstView_Week_days.getChildCount(); i++) {
                        View listItem = LstView_Week_days.getChildAt(i);
                        if (i % 2 == 0)
                            listItem.setBackgroundColor(Color.WHITE);
                        if (i % 2 == 1)
                            listItem.setBackgroundColor(MonthlySalesManSchedule.this.getResources().getColor(R.color.Gray2));
                    }
                    AllCountry.clear();
                    // Cls_Customers_All_adapter s =new Cls_Customers_All_adapter;
                    Lst_Country_All.setAdapter(null);
                    Customers.setAdapter(null);
                    argu.setBackgroundColor(Color.GRAY);

                } catch (Exception ex) {
                    Toast.makeText(MonthlySalesManSchedule.this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }


            }
        });
        EditText Search_Customer = (EditText) findViewById(R.id.Search_Customer);
        Search_Customer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showCustomer("2");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        AddDays = new ArrayList<Cls_Monthly_Schedule>();
        CountryOfDay = new ArrayList<Cls_Country>();
        AllCountry = new ArrayList<Cls_Country>();
        AllCustomers = new ArrayList<Cls_Country>();
        weekDaysList = new ArrayList<Cls_WeekDays>();
        weekNoList = new ArrayList<Cls_WeekDays>();

        AddDays.clear();

        Fragment frag = new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1, frag).commit();
        GloblaVar.PeriodNo = "0";
        GloblaVar.Date = "0";
        WeekNo = "-1";
        DayNo = "-1";
        PeriodNo = "-1";
        Enter_Status = "-1";
        FillWeekDays();
        FillWeekNo();
        FillAllDays(); // Select All Date For This Month

        deleteShera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shers.setText("");
            }
        });
        // FillAllCountryOfDay();
        //  FillAllCountry();
        //   month_Dates();
        // FillAllCustomers("CountryNo = '0'");
        //showCustomer("2");

//        FillKeys(SelectedDate.Date,SelectedDate.PeriodNo,UserID);
  /*      Customers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Cls_Country SelectedDate = (Cls_Country) Customers.getItemAtPosition(position);
                    Bundle bundle = new Bundle();
                    //  et_NoS = returntransno();
                    bundle.putString("CustNo", SelectedDate.getID());


                    // ViewVisitFrg exampleDialog = new ViewVisitFrg();
                    CustomerInfoFrag exampleDialog = new CustomerInfoFrag();
                    exampleDialog.setArguments(bundle);
                    exampleDialog.show(getFragmentManager(), "example dialog");
                }catch (Exception ex)
                {

                }
            }
        });*/
    }


    public void UpdateScreen() {
        // FillWeekDays();
        ///  FillWeekNo();
        //  FillAllDays(); // Select All Date For This Month
        AllCustomers.clear();
        //   FillAllCustomers(SelectedDate.CountryId);
        FillAllCountry("");
        //  month_Dates();

    }

    public void Monthly_Schedule() {


        final Handler _handler = new Handler();
        q = "Delete from PlanMonthlyLookups";
        sqlHandler.executeQuery(q);
        q = " delete from sqlite_sequence where name='PlanMonthlyLookups'";
        sqlHandler.executeQuery(q);

        q = "Delete from Monthly_Schedule";
        sqlHandler.executeQuery(q);
        q = " delete from sqlite_sequence where name='Monthly_Schedule'";

        q = "Delete from Month_Dates";
        sqlHandler.executeQuery(q);
        q = " delete from sqlite_sequence where name='Month_Dates'";
        sqlHandler.executeQuery(q);
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(MonthlySalesManSchedule.this);
                ws.GetMonth_Dates(UserID, tv_Month.getText().toString(), ed_Year.getText().toString());
                try {
                    Integer i;
                    String q = "";


                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray js_TodayDate = js.getJSONArray("TodayDate");


                    JSONArray js_Day_Nm_En = js.getJSONArray("Day_Nm_En");


                    JSONArray js_Day_No = js.getJSONArray("Day_No");
                    JSONArray js_Day_Nm_Ar = js.getJSONArray("Day_Nm_Ar");
                    JSONArray js_PeriodNo = js.getJSONArray("PeriodNo");
                    JSONArray js_PeriodDesc = js.getJSONArray("PeriodDesc");
                    JSONArray js_Week_No = js.getJSONArray("Week_No");
                    JSONArray js_CurrentMonth = js.getJSONArray("CurrentMonth");
                    JSONArray js_CurrentYear = js.getJSONArray("CurrentYear");
                    JSONArray js_Day_No_Sort = js.getJSONArray("Day_No_Sort");

/*
                    JSONArray VisitItems = js.getJSONArray("VisitItems");
                    JSONArray VisitGoals = js.getJSONArray("VisitGoals");
                    JSONArray VisitResult = js.getJSONArray("VisitResult");
                    JSONArray VisitType = js.getJSONArray("VisitType");*/


                    q = "Delete from Month_Dates";
                    sqlHandler.executeQuery(q);
                    q = " delete from sqlite_sequence where name='Month_Dates'";
                    sqlHandler.executeQuery(q);

                    for (i = 0; i < js_TodayDate.length(); i++) {
                    /*    q = "INSERT INTO Month_Dates (  TodayDate,Day_Nm_En,Day_No,Day_Nm_Ar,PeriodNo,PeriodDesc,Week_No,CurrentMonth,CurrentYear,Day_No_Sort" +
                                "VisitItems,VisitGoals,VisitResult,VisitType) values ('" */
                        q = "INSERT INTO Month_Dates (  TodayDate,Day_Nm_En,Day_No,Day_Nm_Ar,PeriodNo,PeriodDesc,Week_No,CurrentMonth,CurrentYear,Day_No_Sort" +
                                ",entry,DoubleVisit) values ('"
                                + js_TodayDate.get(i).toString()
                                + "','" + js_Day_Nm_En.get(i).toString()
                                + "','" + js_Day_No.get(i).toString()
                                + "','" + js_Day_Nm_Ar.get(i).toString()
                                + "','" + js_PeriodNo.get(i).toString()
                                + "','" + js_PeriodDesc.get(i).toString()
                                + "','" + js_Week_No.get(i).toString()
                                + "','" + js_CurrentMonth.get(i).toString()
                                + "','" + js_CurrentYear.get(i).toString()
                                + "','" + js_Day_No_Sort.get(i).toString()


                                + "','" + '0'
                                + "','" + '0'
                               /* + "','" + VisitGoals.get(i).toString()
                                + "','" + VisitResult.get(i).toString()
                                + "','" + VisitType.get(i).toString()*/


                                + "' )";
                        sqlHandler.executeQuery(q);


                    }
                    final int total = i;
                    _handler.post(new Runnable() {

                        public void run() {
                            //  month_Dates();
                            //    FillAllDays(); // Select All Date For This Month
                            //  FillAllCustomers("0");

                            Locale locale = new Locale("en", "US");
                            DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", locale);
                            String date = df.format(Calendar.getInstance().getTime());
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MonthlySalesManSchedule.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("dateupdate", date);
                            editor.apply();
                            setletastUpdate(UserID, date);
                            month_Dates();

                            Cls_WeekDays o = (Cls_WeekDays) LstView_Week_days.getItemAtPosition(position);
                            DayNo = "-1";
                            Shers.setText("");
                            FillAllDays();
                            // FillAllCustomers("CountryNo = '0'");

                            // FillAllCountry();
                            // showCustomer("2");
                            // FillKeys("0", "0");
                            //  argu.setBackgroundColor(Color.GRAY);
                            for (int i = 0; i < LstView_Week_days.getChildCount(); i++) {
                                View listItem = LstView_Week_days.getChildAt(i);
                                if (i % 2 == 0)
                                    listItem.setBackgroundColor(Color.WHITE);
                                if (i % 2 == 1)
                                    listItem.setBackgroundColor(MonthlySalesManSchedule.this.getResources().getColor(R.color.Gray2));
                            }
                            AllCountry.clear();
                            // Cls_Customers_All_adapter s =new Cls_Customers_All_adapter;
                            Lst_Country_All.setAdapter(null);
                            Customers.setAdapter(null);


                        }
                    });

                } catch (final Exception e) {

                    _handler.post(new Runnable() {

                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    MonthlySalesManSchedule.this).create();
                            alertDialog.setTitle("فشل في عمليه الاتصال");
                            alertDialog.setMessage(e.getMessage().toString());
                            alertDialog.setIcon(R.drawable.tick);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

                            alertDialog.show();
                            return;


                        }
                    });
                }
            }
        }).start();

    }

    private void month_Dates() {

        sqlHandler.executeQuery(q);
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

        final ProgressDialog custDialog = new ProgressDialog(MonthlySalesManSchedule.this);
        custDialog.setProgressStyle(custDialog.STYLE_HORIZONTAL);
        custDialog.setCanceledOnTouchOutside(false);
        custDialog.setProgress(0);
        custDialog.setMax(100);
        custDialog.setMessage(String.valueOf(getResources().getString(R.string.Retrive_DataUnderProcess)));
        tv.setText(String.valueOf(getResources().getString(R.string.Monthly_plan)));
        custDialog.setCustomTitle(tv);
        custDialog.setProgressDrawable(greenProgressbar);
        custDialog.show();
       // Monthly_Schedule();
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(MonthlySalesManSchedule.this);
                ws.Get_Month_S(UserID, tv_Month.getText().toString(), ed_Year.getText().toString());
                try {
                    Integer i;
                    Integer k;
                    String[] split1;
                    String[] split2;
                    String[] split3;
                    String q = "";
                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray js_TodayDate = js.getJSONArray("TodayDate");


                    JSONArray ManNo1 = js.getJSONArray("ManNo1");


                    JSONArray js_Day_No = js.getJSONArray("Day_No");
                    JSONArray EventNo = js.getJSONArray("EventNo");
                    JSONArray js_PeriodNo = js.getJSONArray("PeriodNo");
                    JSONArray Customers1 = js.getJSONArray("Customers");
                    JSONArray js_Week_No = js.getJSONArray("Week_No");
                    JSONArray js_CurrentMonth = js.getJSONArray("CurrentMonth");
                    JSONArray js_CurrentYear = js.getJSONArray("CurrentYear");
                    JSONArray AreaNo = js.getJSONArray("AreaNo");
                    JSONArray DoubleVisit = js.getJSONArray("DoubleVisit");


                    q = "Delete from Monthly_Schedule";
                    sqlHandler.executeQuery(q);
                    q = " delete from sqlite_sequence where name='Monthly_Schedule'";
                    sqlHandler.executeQuery(q);


                    q = "Delete from PlanMonthlyLookups";
                    sqlHandler.executeQuery(q);
                    q = " delete from sqlite_sequence where name='PlanMonthlyLookups'";
                    sqlHandler.executeQuery(q);

                    for (i = 0; i < js_TodayDate.length(); i++) {
                        Long j;
                        ContentValues cv = new ContentValues();
                        cv.put("Today_Date", js_TodayDate.get(i).toString());
                        cv.put("Period_No", js_PeriodNo.get(i).toString());
                        // cv.put("Area_No", js_TodayDate.get(i).toString());
                        cv.put("User_No", UserID);
                        cv.put("TrYear", js_CurrentYear.get(i).toString());
                        cv.put("TrMonth", js_CurrentMonth.get(i).toString());
                        cv.put("DoubleVisit", DoubleVisit.get(i).toString());
                        cv.put("Posted", "1");


                        // cv.put("VisitCustomers", AllCustomers.get(position).getID().toString());

                        j = sqlHandler.Insert("Monthly_Schedule", null, cv);
                        if (j > 0) {
                            q = "Update Month_Dates set entry='1' Where  TodayDate = '" + js_TodayDate.get(i).toString() + "' and PeriodNo ='" + js_PeriodNo.get(i).toString() + "'";
                            sqlHandler.executeQuery(q);
                            q = "Update Month_Dates set DoubleVisit='1' Where  TodayDate = '" + js_TodayDate.get(i).toString() + "' and PeriodNo ='" + js_PeriodNo.get(i).toString() + "'";
                            sqlHandler.executeQuery(q);
                            split1 = EventNo.get(i).toString().split(",");
                            split2 = AreaNo.get(i).toString().split(",");
                            split3 = Customers1.get(i).toString().split(",");

                            for (k = 0; k < split1.length; k++) {

                                String item;

                                if (!(split1[k].equals(""))) {
                                    String country2 = DB.GetValue(MonthlySalesManSchedule.this, "AreasN", "ItemNo", "Id='" + split1[k] + "'");

                                    ContentValues cv1 = new ContentValues();
                                    cv1.put("Date", js_TodayDate.get(i).toString());
                                    cv1.put("Period", js_PeriodNo.get(i).toString());
                                    cv1.put("itemno", country2);
                                    cv1.put("UserId", UserID);
                                    cv1.put("Post", "1");
                                    cv1.put("TabletNo", "1");
                                    // cv.put("VisitCustomers", AllCustomers.get(position).getID().toString());

                                    j = sqlHandler.Insert("PlanMonthlyLookups", null, cv1);
                                }
                            }

                            for (k = 0; k < split2.length; k++) {

                                String item;
                                if (!(split2[k].equals(""))) {
                                    String country2 = DB.GetValue(MonthlySalesManSchedule.this, "AreasN", "ItemNo", "Id='" + split2[k] + "'");
                                    ContentValues cv1 = new ContentValues();
                                    cv1.put("Date", js_TodayDate.get(i).toString());
                                    cv1.put("Period", js_PeriodNo.get(i).toString());
                                    cv1.put("itemno", country2);
                                    cv1.put("UserId", UserID);
                                    cv1.put("Post", "1");
                                    cv1.put("TabletNo", "2");
                                    // cv.put("VisitCustomers", AllCustomers.get(position).getID().toString());

                                    j = sqlHandler.Insert("PlanMonthlyLookups", null, cv1);
                                }
                            }

                            for (k = 0; k < split3.length; k++) {

                                if (!(split3[k].equals(""))) {
                                    ContentValues cv1 = new ContentValues();
                                    cv1.put("Date", js_TodayDate.get(i).toString());
                                    cv1.put("Period", js_PeriodNo.get(i).toString());
                                    cv1.put("itemno", split3[k]);
                                    cv1.put("UserId", UserID);
                                    cv1.put("Post", "1");
                                    cv1.put("TabletNo", "3");
                                    // cv.put("VisitCustomers", AllCustomers.get(position).getID().toString());

                                    j = sqlHandler.Insert("PlanMonthlyLookups", null, cv1);
                                }

                            }
                        }


                    }
                    custDialog.setMax(js_TodayDate.length());
                    custDialog.incrementProgressBy(1);
                    if (custDialog.getProgress() == custDialog.getMax()) {

                        custDialog.dismiss();
                    }
                    final int total = i;
                    _handler.post(new Runnable() {

                        public void run() {
                            //  FillAllDays(); // Select All Date For This Month
                            //  FillAllCustomers("0");
                            FillAllDays();
                            Locale locale = new Locale("en", "US");
                            DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", locale);
                            String date = df.format(Calendar.getInstance().getTime());
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MonthlySalesManSchedule.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("dateupdate", date);
                            editor.apply();
                            setletastUpdate(UserID, date);

                            Cls_WeekDays o = (Cls_WeekDays) LstView_Week_days.getItemAtPosition(position);
                            DayNo = "-1";
                            Shers.setText("");
                            FillAllDays();
                            // FillAllCustomers("CountryNo = '0'");

                            // FillAllCountry();
                            // showCustomer("2");
                            // FillKeys("0", "0");
                          //  argu.setBackgroundColor(Color.GRAY);
                            for (int i = 0; i < LstView_Week_days.getChildCount(); i++) {
                                View listItem = LstView_Week_days.getChildAt(i);
                                if (i % 2 == 0)
                                    listItem.setBackgroundColor(Color.WHITE);
                                if (i % 2 == 1)
                                    listItem.setBackgroundColor(MonthlySalesManSchedule.this.getResources().getColor(R.color.Gray2));
                            }
                            AllCountry.clear();
                            // Cls_Customers_All_adapter s =new Cls_Customers_All_adapter;
                            Lst_Country_All.setAdapter(null);
                            Customers.setAdapter(null);
                          //  argu.setBackgroundColor(Color.GRAY);

                            custDialog.dismiss();
                        }
                    });

                } catch (final Exception e) {
                    custDialog.dismiss();
                    _handler.post(new Runnable() {

                        public void run() {

                            custDialog.dismiss();

                        }
                    });
                }
            }
        }).start();


    }

    public String getDetailCustomer(String flag,String cusId)
    {  String res ="";
        try{

        LocaleHelper localeHelper = new LocaleHelper();
        if(flag.equals("1"))
        {
            String Item = DB.GetValue(MonthlySalesManSchedule.this, "CustomersN", "ifnull(CategoryNo,'0')", "id='" + cusId + "'");

            if (localeHelper.getlanguage(MonthlySalesManSchedule.this).equals("ar")) {
                res = DB.GetValue(MonthlySalesManSchedule.this, "AreasN", "DescrA", "Id='" + Item + "'");

            } else {
                res = DB.GetValue(MonthlySalesManSchedule.this, "AreasN", "DescrE", "Id='" + Item + "'");
            }
            if(res.equals("-1"))
            {
                res="";
            }
        }else if(flag.equals("2"))
        {

                res = DB.GetValue(MonthlySalesManSchedule.this, "CustomersN", "VisitMounthlyCount", "id='" + cusId + "'");

            if(res.equals("-1"))
            {
                res="0";
            }
        }else if(flag.equals("3"))
        {
            res = DB.GetValue(MonthlySalesManSchedule.this, "PlanMonthlyLookups", "count(*)", "itemno='" + cusId + "' and TabletNo='3'");
            if(res.equals("-1"))
            {
                res="0";
            }
        }

    }
            catch (Exception e)
    {

    }
        return res;
    }

    public void setletastUpdate(final String UserId, final String UpdateTime) {

        final Handler _handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(MonthlySalesManSchedule.this);
                ws.SetUpdateTime(UserId, UpdateTime);
                try {

                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray js_ID = js.getJSONArray("ID");
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

    private void FillWeekNo() {
        weekNoList.clear();
        Cls_WeekDays obj = new Cls_WeekDays();
        obj.setNo("-1");
        obj.setDesc(String.valueOf(getResources().getString(R.string.all)));
        weekNoList.add(obj);

        obj = new Cls_WeekDays();
        obj.setNo("1");
        obj.setDesc(String.valueOf(getResources().getString(R.string.week1)) + "1");
        weekNoList.add(obj);

        obj = new Cls_WeekDays();
        obj.setNo("2");
        obj.setDesc(String.valueOf(getResources().getString(R.string.week1)) + "2");

        //obj.setDesc("الأسبوع 2");
        weekNoList.add(obj);


        obj = new Cls_WeekDays();
        obj.setNo("3");
        obj.setDesc(String.valueOf(getResources().getString(R.string.week1)) + "3");

        weekNoList.add(obj);

        obj = new Cls_WeekDays();
        obj.setNo("4");
        obj.setDesc(String.valueOf(getResources().getString(R.string.week1)) + "4");

        weekNoList.add(obj);


        obj = new Cls_WeekDays();
        obj.setNo("5");
        obj.setDesc(String.valueOf(getResources().getString(R.string.week1)) + "5");

        weekNoList.add(obj);


        Cls_Week_No_adapter cls_week_no_adapter = new Cls_Week_No_adapter(
                this, weekNoList);
        LstWeekNo.setAdapter(cls_week_no_adapter);


    }

    private void FillWeekDays() {
        weekDaysList.clear();
        Cls_WeekDays obj = new Cls_WeekDays();
        obj.setNo("-1");
        obj.setDesc(String.valueOf(getResources().getString(R.string.all)) + "");

        weekDaysList.add(obj);

        obj = new Cls_WeekDays();
        obj.setNo("7");
        obj.setDesc(String.valueOf(getResources().getString(R.string.Saturday)) + "");

        weekDaysList.add(obj);

        obj = new Cls_WeekDays();
        obj.setNo("1");
        obj.setDesc(String.valueOf(getResources().getString(R.string.Sunday)) + "");

        weekDaysList.add(obj);


        obj = new Cls_WeekDays();
        obj.setNo("2");
        obj.setDesc(String.valueOf(getResources().getString(R.string.Monday)) + "");

        weekDaysList.add(obj);

        obj = new Cls_WeekDays();
        obj.setNo("3");
        obj.setDesc(String.valueOf(getResources().getString(R.string.Tuesday)) + "");

        weekDaysList.add(obj);


        obj = new Cls_WeekDays();
        obj.setNo("4");
        obj.setDesc(String.valueOf(getResources().getString(R.string.Wednesday)) + "");

        weekDaysList.add(obj);

        obj = new Cls_WeekDays();
        obj.setNo("5");
        obj.setDesc(String.valueOf(getResources().getString(R.string.Thursday)) + "");

        weekDaysList.add(obj);


        Cls_Week_Days_adapter cls_monthly_sechedule_adapter = new Cls_Week_Days_adapter(
                this, weekDaysList);
        LstView_Week_days.setAdapter(cls_monthly_sechedule_adapter);

        //  FillKeys();
    }

    private void FillAllDays() {
        /*  SelectedDate=null;*/
        Lst_Country_Day.setAdapter(null);
        AddDays.clear();
        q = " Select distinct Monthly_Schedule.Today_Date,Monthly_Schedule.Period_No, Area_No,TodayDate,Day_Nm_En,Day_No,Day_Nm_Ar,PeriodNo,PeriodDesc,Week_No,CurrentMonth,CurrentYear,Day_No_Sort  from  Month_Dates  " +
                " left  join Monthly_Schedule on Monthly_Schedule.Today_Date= Month_Dates.TodayDate and Monthly_Schedule.Period_No = Month_Dates.PeriodNo " +
                " Where ('" + WeekNo + "'='-1' or Week_No='" + WeekNo + "') " +
                "  And ('" + DayNo + "'='-1' or Day_No='" + DayNo + "') " +
                "  And ('" + PeriodNo + "'='-1' or PeriodNo='" + PeriodNo + "') " +
                "  And ('" + Enter_Status + "'='-1' or ('" + Enter_Status + "'='1'  and Monthly_Schedule.Area_No is not null )       or ('" + Enter_Status + "'='2'  )  ) ";
        Cursor c1 = sqlHandler.selectQuery(q);//and Monthly_Schedule.Area_No is null
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Monthly_Schedule obj = new Cls_Monthly_Schedule();

                    obj.setDayNum(c1.getString(c1.getColumnIndex("Day_No")));

                    LocaleHelper localeHelper = new LocaleHelper();
                    if (localeHelper.getlanguage(MonthlySalesManSchedule.this).equals("ar")) {
                        obj.setDayNm(c1.getString(c1.getColumnIndex("Day_Nm_Ar")));
                        obj.setPeriodDesc(c1.getString(c1.getColumnIndex("PeriodDesc")));

                    } else {
                        obj.setDayNm(c1.getString(c1.getColumnIndex("Day_Nm_En")));

                        if (c1.getString(c1.getColumnIndex("PeriodDesc")).equals("صباحية"))
                            obj.setPeriodDesc("morning");
                        else
                            obj.setPeriodDesc("evening");
                    }


                    String are = DB.GetValue(MonthlySalesManSchedule.this, "AreasN", "DescrA", "Id='" +
                            c1.getString(c1.getColumnIndex("Area_No")) + "'");
                    obj.setDate(c1.getString(c1.getColumnIndex("TodayDate")));
                    obj.setCountryId(c1.getString(c1.getColumnIndex("Area_No")));//CountryId
                    obj.setCountryNm(GetTodayAllArea(c1.getString(c1.getColumnIndex("TodayDate")), c1.getString(c1.getColumnIndex("PeriodNo"))));
                    //  obj.setCountryNm(GetTodayAllArea(c1.getString(c1.getColumnIndex("TodayDate")), c1.getString(c1.getColumnIndex("PeriodNo"))));
                    // obj.setCountryNm("");
                    obj.setPeriodNo(c1.getString(c1.getColumnIndex("PeriodNo")));
                    //obj.setPeriodDesc(c1.getString(c1.getColumnIndex("PeriodDesc")));
                    boolean X = true;
                    for (int i = 0; i < AddDays.size(); i++)
                        if (c1.getString(c1.getColumnIndex("PeriodNo")).equals(AddDays.get(i).PeriodNo)
                                && c1.getString(c1.getColumnIndex("TodayDate")).equals(AddDays.get(i).Date)) {
                            X = false;
                        }
                    if (Enter_Status.equals("2")) {
                        if (c1.getString(c1.getColumnIndex("Area_No")) == null)
                            if (X)
                                AddDays.add(obj);
                    } else if (X) {
                        AddDays.add(obj);
                    }

                } while (c1.moveToNext());

            }
            c1.close();
        }

      /*  Cls_Monthly_Schedule obj = new Cls_Monthly_Schedule();
        obj.setDayNum("1");
        obj.setDayNum("الاحد");
        obj.setDate("28/05/2018");
        obj.setCountryId("1");
        obj.setCountryNm("الشميساني/عبدون");
        obj.setPeriodNo("1");
        obj.setPeriodDesc("الفترة الصباحية");
        AddDays.add(obj);


        obj = new Cls_Monthly_Schedule();
        obj.setDayNum("2");
        obj.setDayNum("الاثنين");
        obj.setDate("29/05/2018");
        obj.setCountryId("1");
        obj.setCountryNm("تلاع العلي");
        obj.setPeriodNo("1");
        obj.setPeriodDesc("الفترة الصباحية");
        AddDays.add(obj);




        obj = new Cls_Monthly_Schedule();
        obj.setDayNum("2");
        obj.setDayNum("الثلاثاء");
        obj.setDate("30/05/2018");
        obj.setCountryId("1");
        obj.setCountryNm("اربد");
        obj.setPeriodNo("1");
        obj.setPeriodDesc("الفترة الصباحية");
        AddDays.add(obj);


        obj = new Cls_Monthly_Schedule();
        obj.setDayNum("2");
        obj.setDayNum("الثلاثاء");
        obj.setDate("30/05/2018");
        obj.setCountryId("1");
        obj.setCountryNm("اربد");
        obj.setPeriodNo("1");
        obj.setPeriodDesc("الفترة المسائية");
        AddDays.add(obj);



        obj = new Cls_Monthly_Schedule();
        obj.setDayNum("2");
        obj.setDayNum("الاربعاء");
        obj.setDate("1/06/2018");
        obj.setCountryId("1");
        obj.setCountryNm("السلط");
        obj.setPeriodNo("1");
        obj.setPeriodDesc("الفترة الصباحية");
        AddDays.add(obj);*/

        cls_monthly_sechedule_adapter = new Cls_Monthly_Sechedule_adapter(
                this, AddDays);
        AllDays_Lst.setAdapter(cls_monthly_sechedule_adapter);

        //AllDays_Lst.setItemChecked(1,true);
        //   cls_monthly_sechedule_adapter.notifyDataSetChanged();
        // FillAllCustomers("-1");
        GloblaVar.Date = "";
        //FillAllCountry();
    }

    public String GetTodayAllArea2(String Date, String PeriodNo) {
        String All_Area = "";
        q = " Select AreasN.DescrA,AreasN.DescrE,Area_No from   Monthly_Schedule  left join AreasN on  AreasN.ItemNo=Monthly_Schedule.Area_No   where Today_Date='" + Date + "' and Period_No='" + PeriodNo + "' and User_No='" + UserID + "' and TableNo='9'";

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {

                    LocaleHelper LocaleHelper = new LocaleHelper();
                    if (LocaleHelper.getlanguage(MonthlySalesManSchedule.this).equals("ar")) {
                        All_Area = All_Area + " or DescrA='" + c1.getString(c1.getColumnIndex("DescrA")) + "' ";

                    } else
                        All_Area = All_Area + " or DescrA='" + c1.getString(c1.getColumnIndex("DescrE")) + "' ";


                } while (c1.moveToNext());

            }
            c1.close();
        } else {
            All_Area = String.valueOf(getResources().getString(R.string.notentered));
        }
        return All_Area;
    }


    public String GetTodayAllArea(String Date, String PeriodNo) {
        String All_Area = "";
        AllCustomers.clear();
        q = " Select AreasN.DescrA,AreasN.DescrE,Area_No from   Monthly_Schedule  left join AreasN on  AreasN.ItemNo=Monthly_Schedule.Area_No   where Today_Date='" + Date + "' and Period_No='" + PeriodNo + "' and User_No='" + UserID + "' and TableNo='9'";

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {

                    showCustomer("2");
                    LocaleHelper LocaleHelper = new LocaleHelper();
                    if (!All_Area.contains(c1.getString(c1.getColumnIndex("DescrA"))))
                        if (LocaleHelper.getlanguage(MonthlySalesManSchedule.this).equals("ar")) {
                            All_Area = All_Area + c1.getString(c1.getColumnIndex("DescrA")) + ",";

                        } else
                            All_Area = All_Area + c1.getString(c1.getColumnIndex("DescrE")) + ",";


                } while (c1.moveToNext());

            }
            c1.close();
        } else {
            All_Area = String.valueOf(getResources().getString(R.string.notentered));
        }
        return All_Area;
    }

    public String getAreaToday(String Date, String PeriodNo, String Item_no, String TabletNo) {


        q = " Select * from PlanMonthlyLookups   where Date='" + Date + "' and Period='" + PeriodNo + "' and UserId='" + UserID + "' and TableNo='" + TabletNo + "' and itemno= '" + Item_no + "'";

        country = DB.GetValue(MonthlySalesManSchedule.this, "PlanMonthlyLookups", "ifnull(itemno,'0')", "Date='" + Date + "' and Period='" + PeriodNo + "' and UserId='" + UserID + "' and tabletno='" + TabletNo + "' and itemno= '" + Item_no + "'");
        return country;
    }

    public String getChackEntery(String Date, String PeriodNo) {


//moh
        country = DB.GetValue(MonthlySalesManSchedule.this, "PlanMonthlyLookups", "count(*)", "Date='" + Date + "' and Period='" + PeriodNo + "' and UserId='" + UserID + "'");
        return country;
       //String country = "0";
       // String q = "SELECT count(*) as total FROM PlanMonthlyLookups where Date ='" + Date + "' and Period ='" + PeriodNo + "' and UserId='" + UserID + "'";
        /*Cursor cc = sqlHandler.selectQuery(q);
        if (cc != null && cc.getCount() != 0) {
            country =cc.getString(cc.getColumnIndex("total"));


            cc.close();
         //   x = 0;
        }*/


       // return country;
    }

    public int getChackPosted() {
        int x = 1;
        TextView tv_Month = (TextView) findViewById(R.id.tv_Month);
        TextView ed_Year = (TextView) findViewById(R.id.ed_Year);
        String q = "SELECT * FROM Monthly_Schedule where trmonth ='" + tv_Month.getText().toString() + "' and tryear ='" + ed_Year.getText().toString() + "' and posted>0";
        Cursor cc = sqlHandler.selectQuery(q);
        if (cc != null && cc.getCount() != 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    MonthlySalesManSchedule.this).create();
            alertDialog.setTitle(getResources().getText(R.string.Monthly_plan));
            alertDialog.setMessage("لا يمكن التعديل ، تم اعتماد الخطة الشهرية ");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton(getResources().getText(R.string.Ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
            cc.close();
            x = 0;
        }


        return x;
    }


    public String GetTodayAllCustomers(String Date, String PeriodNo) {
        String All_Area = "";
        q = " Select CustomersN.ArabicName,CustomersN.EnglishName,VisitCustomers from   Monthly_Schedule  left join CustomersN on  CustomersN.id=Monthly_Schedule.VisitCustomers   where Today_Date='" + Date + "' and Period_No='" + PeriodNo + "' and User_No='" + UserID + "'";

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {

                    LocaleHelper LocaleHelper = new LocaleHelper();
                    if (LocaleHelper.getlanguage(MonthlySalesManSchedule.this).equals("ar")) {
                        All_Area = All_Area + c1.getString(c1.getColumnIndex("ArabicName")) + "   ";

                    } else
                        All_Area = All_Area + c1.getString(c1.getColumnIndex("EnglishName")) + "   ";


                } while (c1.moveToNext());

            }
            c1.close();
        } else {
            All_Area = String.valueOf(getResources().getString(R.string.notentered));
        }
        return All_Area;
    }

    private String GetTovistAllCustomers(String Date, String PeriodNo) {
        String All_Area = "";
        q = " Select CustomersN.DescrA,CustomersN.DescrE,Area_No from   Monthly_Schedule  left join AreasN on  AreasN.ItemNo=Monthly_Schedule.Area_No   where Today_Date='" + Date + "' and Period_No='" + PeriodNo + "' and User_No='" + UserID + "' and TableNo='9'";

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {

                    LocaleHelper LocaleHelper = new LocaleHelper();
                    if (LocaleHelper.getlanguage(MonthlySalesManSchedule.this).equals("ar")) {
                        All_Area = All_Area + c1.getString(c1.getColumnIndex("DescrA")) + "   ";

                    } else
                        All_Area = All_Area + c1.getString(c1.getColumnIndex("DescrE")) + "   ";


                } while (c1.moveToNext());

            }
            c1.close();
        } else {
            All_Area = String.valueOf(getResources().getString(R.string.notentered));
        }
        return All_Area;
    }


    private void FillAllCountryOfDay() {
        CountryOfDay.clear();

        q = " Select  Area_No, AreasN.DescrA,AreasN.Id from   Monthly_Schedule  left join AreasN on  AreasN.Id=Monthly_Schedule.Area_No   where Today_Date='" + SelectedDate.getDate() + "' and Period_No='" + SelectedDate.getPeriodNo() + "' and User_No='" + UserID + "'" +
                "and AreasN.TableNo='9'";

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Country obj = new Cls_Country();
                    obj.setID(c1.getString(c1.getColumnIndex("Area_No")));
                    obj.setNm(c1.getString(c1.getColumnIndex("DescrA")));

                    CountryOfDay.add(obj);

                } while (c1.moveToNext());

            }
            c1.close();
        }





     /*   Cls_Country obj = new Cls_Country();
        obj.setID("1");
        obj.setNm("الشميساني");
        CountryOfDay.add(obj);



        obj = new Cls_Country();
        obj.setID("2");
        obj.setNm("عبدون");
        CountryOfDay.add(obj);

*/
        Cls_Country_Day_adapter cls_monthly_sechedule_adapter = new Cls_Country_Day_adapter(
                this, CountryOfDay);
        Lst_Country_Day.setAdapter(cls_monthly_sechedule_adapter);
    }

    public void SetEntry(String Date, String PeriodNo) {
        country = DB.GetValue(MonthlySalesManSchedule.this, "PlanMonthlyLookups", "itemno", "Date='" + Date + "' and Period='" + PeriodNo + "' and UserId='" + UserID + "'");
        if (country.equals("-1")) {
            q = "Update Month_Dates set entry='0' Where  TodayDate = '" + Date + "' and PeriodNo ='" + PeriodNo + "'";
            sqlHandler.executeQuery(q);
            //cls_monthly_sechedule_adapter.notifyDataSetChanged();

        } else {
            q = "Update Month_Dates set entry='1' Where  TodayDate = '" + Date + "' and PeriodNo ='" + PeriodNo + "'";
            sqlHandler.executeQuery(q);
            //cls_monthly_sechedule_adapter.notifyDataSetChanged();
        }

        //GloblaVar.arg0.getChildAt(position).setBackgroundColor(Color.GRAY);

        cls_monthly_sechedule_adapter.notifyDataSetChanged();

               /*LinearLayout layout=(LinearLayout) GloblaVar.arq.findViewById(R.id.RR1);
               layout.setBackgroundColor(Color.GRAY);*/
        /*    AllDays_Lst.getChildAt(position).setBackgroundColor(Color.GRAY);*/


    }

    public String GetNameSearch(String TabletNo) {
        EditText Search_Customer = (EditText) findViewById(R.id.Search_Customer);

        String NameC = "";

        String query;
        if (LocaleHelper.getlanguage(MonthlySalesManSchedule.this).equals("ar")) {
            query = " Select  id  from CustomersN where ArabicName like '%" + Search_Customer.getText().toString() + "%' ";
        } else {
            query = " Select  id  from CustomersN where EnglishName like '%" + Search_Customer.getText().toString() + "%' ";
        }
        sqlHandler = new SqlHandler(MonthlySalesManSchedule.this);


        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {


                    if (NameC.equals("")) {
                        NameC = "id = '" + c1.getString(c1.getColumnIndex("id")) + "'";
                    } else {
                        NameC += "or id = '" + c1.getString(c1.getColumnIndex("id")) + "'";

                    }


                } while (c1.moveToNext());

            }
            c1.close();

        }


        return NameC;
    }

    public String GetTypeCustomerSearch(String TabletNo) {
        EditText Search_Customer = (EditText) findViewById(R.id.Search_Customer);

        String NameC = "";

        String query;
        if (LocaleHelper.getlanguage(MonthlySalesManSchedule.this).equals("ar")) {
            query = " Select  Id  from AreasN where DescrA like '%" + Search_Customer.getText().toString() + "%' and TableNo = '" + TabletNo + "' ";
        } else {
            query = " Select  Id  from AreasN where DescrE like '%" + Search_Customer.getText().toString() + "%' and TableNo = '" + TabletNo + "' ";
        }
        sqlHandler = new SqlHandler(MonthlySalesManSchedule.this);


        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    if (TabletNo.equals("3")) {
                        if (NameC.equals("")) {
                            NameC = "CategoryNo = '" + c1.getString(c1.getColumnIndex("Id")) + "'";
                        } else {
                            NameC += "or CategoryNo = '" + c1.getString(c1.getColumnIndex("Id")) + "'";

                        }
                    } else {
                        if (NameC.equals("")) {
                            NameC = "SpecializedNo = '" + c1.getString(c1.getColumnIndex("Id")) + "'";
                        } else {
                            NameC += "or SpecializedNo = '" + c1.getString(c1.getColumnIndex("Id")) + "'";

                        }
                    }


                } while (c1.moveToNext());

            }
            c1.close();

        }


        return NameC;
    }

    public void FillAllCustomers(String idC) {

        AllCustomers.clear();
        String q1, q2, q3, q;
//mohkhaldi
        //     q = " Select   ItemNo , DescrA from AreasN  where    cast(ItemNo as INTEGER ) >0";
        EditText Search_Customer = (EditText) findViewById(R.id.Search_Customer);
        if (Search_Customer.getText().toString().equals("")) {
            q = " Select   *  from CustomersN where " + idC;
        } else {
            q1 = GetNameSearch("");
            q2 = GetTypeCustomerSearch("3");
            q3 = GetTypeCustomerSearch("2");
            q = " Select   *  from CustomersN where (" + idC+")";
            if (q1.equals("") && q2.equals("") && q3.equals("")) {

            } else {
                int x = 1;
                q += "and (";
                if (!(q1.equals(""))) {
                    q += q1;
                    x += 1;
                }
                if (!(q2.equals(""))) {
                    if (x > 1) {
                        q += " or " + q2;
                    } else {
                        q += q2;
                    }
                }
                if (!(q3.equals(""))) {
                    if (x > 1) {
                        q += " or " + q3;
                    } else {
                        q += q3;
                    }
                }
                q += ")";
            }
        }


        //  GetTodayAllArea();
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Country obj = new Cls_Country();
                    LocaleHelper LocaleHelper = new LocaleHelper();
                    obj.setID(c1.getString(c1.getColumnIndex("id")));
                    if (LocaleHelper.getlanguage(MonthlySalesManSchedule.this).equals("ar")) {
                        obj.setNm(c1.getString(c1
                                .getColumnIndex("ArabicName")));
                    } else {
                        obj.setNm(c1.getString(c1
                                .getColumnIndex("EnglishName")));
                    }

                    //obj.setNm(c1.getString(c1.getColumnIndex("DescrA")));
                    boolean x = true;
                    for (int i = 0; i < AllCustomers.size(); i++)
                        if (c1.getString(c1.getColumnIndex("id")).equals(AllCustomers.get(i).getID()))
                            x = false;
                    if (x)
                        AllCustomers.add(obj);

                } while (c1.moveToNext());

            }
            c1.close();
        }

        cls_Customers_sechedule_adapter = new Cls_Customers_All_adapter(
                this, AllCustomers);
        Customers.setLayoutManager(new LinearLayoutManager(this));
        Customers.setNestedScrollingEnabled(false);
        Customers.setAdapter(cls_Customers_sechedule_adapter);
    }

    private void FillAllCountry(String S) {

        AllCountry.clear();

        //     q = " Select   ItemNo , DescrA from AreasN  where    cast(ItemNo as INTEGER ) >0";
        if (S.equals("")) {
            q = " Select   *  from AreasN where TableNo='9'";
        }
        else
        {
            q = " Select   *  from AreasN where TableNo='9' and DescrA like '%"+S+"%'";
        }
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Country obj = new Cls_Country();
                    LocaleHelper LocaleHelper = new LocaleHelper();
                    obj.setID(c1.getString(c1.getColumnIndex("ItemNo")));
                    if (LocaleHelper.getlanguage(MonthlySalesManSchedule.this).equals("ar")) {
                        obj.setNm(c1.getString(c1.getColumnIndex("DescrA")));
                    } else {
                        obj.setNm(c1.getString(c1.getColumnIndex("DescrE")));
                    }

                    //obj.setNm(c1.getString(c1.getColumnIndex("DescrA")));

                    AllCountry.add(obj);

                } while (c1.moveToNext());

            }
            c1.close();
        }







        /*Cls_Country obj = new Cls_Country();
        obj.setID("1");
        obj.setNm("الشميساني");
        AllCountry.add(obj);



        obj = new Cls_Country();
        obj.setID("2");
        obj.setNm("عبدون");
        AllCountry.add(obj);


        obj = new Cls_Country();
        obj.setID("3");
        obj.setNm("اربد");
        AllCountry.add(obj);


        obj = new Cls_Country();
        obj.setID("4");
        obj.setNm("السلط");
        AllCountry.add(obj);


        obj = new Cls_Country();
        obj.setID("5");
        obj.setNm("الطفيلة");
        AllCountry.add(obj);


        obj = new Cls_Country();
        obj.setID("6");
        obj.setNm("معان");
        AllCountry.add(obj);



        obj = new Cls_Country();
        obj.setID("7");
        obj.setNm("الكرك");
        AllCountry.add(obj);


        obj = new Cls_Country();
        obj.setID("8");
        obj.setNm("جبل الحسين");
        AllCountry.add(obj);
        */


        Cls_Country_All_adapter cls_monthly_sechedule_adapter = new Cls_Country_All_adapter(
                this, AllCountry);
        Lst_Country_All.setLayoutManager(new LinearLayoutManager(this));

        Lst_Country_All.setAdapter(cls_monthly_sechedule_adapter);

        Lst_Country_All.post(new Runnable()
        {
            @Override
            public void run() {
             //   cls_monthly_sechedule_adapter.notifyDataSetChanged();
            }
        });

    }

    public void btn_GetData(View view) {
      //  month_Dates();
        Monthly_Schedule();
    }

    int position;

    public int btn_Insert_City(int position) {

        position = position;//Lst_Country_All.getPositionForView(view);
        if (SelectedDate.getDate().equalsIgnoreCase("")) {
            Toast.makeText(this, String.valueOf(getResources().getString(R.string.Pleaseselectadate)), Toast.LENGTH_SHORT).show();
            return 0;
        }

        boolean Exist = true;
        Exist = CheckArea(SelectedDate.getDate().toString(), SelectedDate.getPeriodNo().toString(), AllCountry.get(position).getID().toString());
        if (Exist == false) {
            return 0;
        }

        Long i;
        ContentValues cv = new ContentValues();
        cv.put("Today_Date", SelectedDate.getDate().toString());
        cv.put("Period_No", SelectedDate.getPeriodNo().toString());
        cv.put("Area_No", AllCountry.get(position).getID().toString());
        cv.put("User_No", UserID);
        cv.put("TrYear", ed_Year.getText().toString());
        cv.put("TrMonth", tv_Month.getText().toString());
        cv.put("Posted", "0");
        // cv.put("VisitCustomers", AllCustomers.get(position).getID().toString());

        i = sqlHandler.Insert("Monthly_Schedule", null, cv);
        //i = sqlHandler.Update("Monthly_Schedule", cv, "Today_Date='" + SelectedDate.getDate() + "' and Period_No='" + SelectedDate.getPeriodNo() + "' and User_No='" + UserID + "'");
        if (i > 0) {
            //  GetTodayAllArea(SelectedDate.getDate(),SelectedDate.getPeriodNo());


            // Lst_Country_All.setpo
            // FillAllCountryOfDay();
            // FillAllCustomers(SelectedDate.CountryId);
            FillAllDays();
            GetTodayAllArea(SelectedDate.getDate(), SelectedDate.getPeriodNo());

        }
        //   Toast.makeText(this, SelectedDate.getDate(), Toast.LENGTH_SHORT).show();
        // updateItemAtPosition( GloblaVar.selected);
        return 1;
    }


    public int AddArea(int position, String TabletNo) {

        position = position;//Lst_Country_All.getPositionForView(view);
     /*   if (SelectedDate.getDate().equalsIgnoreCase("")) {
            Toast.makeText(this, String.valueOf(getResources().getString(R.string.Pleaseselectadate)), Toast.LENGTH_SHORT).show();
            return 0;
        }*/

 /*       boolean Exist = true;
        Exist = CheckArea(SelectedDate.getDate().toString(), SelectedDate.getPeriodNo().toString(), AllCountry.get(position).getID().toString());
        if (Exist == false) {
            return 0;
        }*/

        Long i;
        String item;
        ContentValues cv = new ContentValues();
        cv.put("Date", SelectedDate.getDate().toString());
        cv.put("Period", SelectedDate.getPeriodNo().toString());
        if (TabletNo.equals("2"))
            item = AllCountry.get(position).getID().toString();
        else if (TabletNo.equals("1"))
            item = cls_invf_List.get(position).getItemNo().toString();
        else
            item = AllCustomers.get(position).getID().toString();

        cv.put("itemno", item);
        cv.put("UserId", UserID);
        cv.put("Post", "0");
        cv.put("TabletNo", TabletNo);
        // cv.put("VisitCustomers", AllCustomers.get(position).getID().toString());

        i = sqlHandler.Insert("PlanMonthlyLookups", null, cv);
        //i = sqlHandler.Update("Monthly_Schedule", cv, "Today_Date='" + SelectedDate.getDate() + "' and Period_No='" + SelectedDate.getPeriodNo() + "' and User_No='" + UserID + "'");
        if (i > 0) {
            //  GetTodayAllArea(SelectedDate.getDate(),SelectedDate.getPeriodNo());


            // Lst_Country_All.setpo
            // FillAllCountryOfDay();
            if (TabletNo.equals("2")) {
                showCustomer(TabletNo);
            }
            // FillAllDays();
            //GetTodayAllArea(SelectedDate.getDate(),SelectedDate.getPeriodNo());

        }
        //   Toast.makeText(this, SelectedDate.getDate(), Toast.LENGTH_SHORT).show();
        // updateItemAtPosition( GloblaVar.selected);
        return 1;
    }

    public void showCustomer(String TabletNo) {
        String country = "";

        String query = " Select  itemno  from PlanMonthlyLookups where Date='" + SelectedDate.getDate() + "' and Period ='" + SelectedDate.getPeriodNo() + "' and UserId ='" + UserID + "' and TabletNo ='" + TabletNo + "' ";

        sqlHandler = new SqlHandler(MonthlySalesManSchedule.this);


        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    String country2 = DB.GetValue(MonthlySalesManSchedule.this, "AreasN", "Id", "ItemNo='" + c1.getString(c1.getColumnIndex("itemno")) + "'and TableNo ='9'");

                    if (country.equals("")) {
                        country = "CountryNo = '" + country2 + "'";
                    } else {
                        country += "or CountryNo = '" + country2 + "'";

                    }


                } while (c1.moveToNext());

            }
            c1.close();
        }

        FillAllCustomers(country);


    }


    public int ClearLookups(String Item_no, String Tablet_no) {

        if (Tablet_no.equals("2")) {
            //  String country=DB.GetValue(MonthlySalesManSchedule.this,"CustomersN","CountryNo","CountryNo='"+userID+"'");
            sqlHandler = new SqlHandler(MonthlySalesManSchedule.this);
            String query = "Select CountryNo  from CustomersN where  CountryNo='" + Item_no + "'";


            Cursor c1 = sqlHandler.selectQuery(query);
            if (c1 != null && c1.getCount() != 0) {
                if (c1.moveToFirst()) {
                    do {

                        String q = "Delete from  PlanMonthlyLookups where TabletNo ='" + 3 + "'" +
                                "and Period='" + SelectedDate.getPeriodNo().toString() + "' and UserId='" + UserID + "' and itemno='" + c1.getString(c1.getColumnIndex("CountryNo")) + "'";
                        sqlHandler.executeQuery(q);
                        //cls_Customers_sechedule_adapter.notifyDataSetChanged();


                    } while (c1.moveToNext());

                }
                c1.close();
            }


        }
        SqlHandler sqlHandler = new SqlHandler(MonthlySalesManSchedule.this);

        String q = "Delete from  PlanMonthlyLookups where TabletNo ='" + Tablet_no + "'" +
                "and Period='" + SelectedDate.getPeriodNo().toString() + "' and UserId='" + UserID + "' and itemno='" + Item_no + "'";
        //  UpdateScreen();
        sqlHandler.executeQuery(q);


      //  showCustomer("2");


        //  FillAllCustomers(SelectedDate.CountryId);


        //    FillAllDays();

        //   GetTodayAllArea(SelectedDate.getDate(),SelectedDate.getPeriodNo());

        // UpdateScreen();
        // FillAllDays();
        //updateItemAtPosition( GloblaVar.selected);

        return 1;
    }


    public void btn_Delete_Area(View view) {
        position = Lst_Country_Day.getPositionForView(view);


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(String.valueOf(getResources().getString(R.string.Monthly_plan)));
        alertDialog.setMessage(String.valueOf(getResources().getString(R.string.DoYouWantContinDelete)));
        alertDialog.setIcon(R.drawable.delete);
        alertDialog.setPositiveButton(String.valueOf(getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                q = "Delete From Monthly_Schedule where Area_No='" + CountryOfDay.get(position).getID().toString() + "'And Today_Date='" + SelectedDate.getDate() + "' and Period_No='" + SelectedDate.getPeriodNo() + "' and User_No='" + UserID + "'";
                sqlHandler.executeQuery(q);
                FillAllDays();
                //   FillAllCountryOfDay();
            }
        });
        alertDialog.setNegativeButton(String.valueOf(getResources().getString(R.string.no)), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void btn_Collections(View view) {
        if (SelectedDate.getDate().equalsIgnoreCase("")) {
            Toast.makeText(this, String.valueOf(getResources().getString(R.string.Pleaseselectadate)), Toast.LENGTH_SHORT).show();
        } else {
            Long i;
            ContentValues cv = new ContentValues();
            cv.put("Today_Date", SelectedDate.getDate().toString());
            cv.put("Period_No", SelectedDate.getPeriodNo().toString());
            cv.put("Area_No", "-1");
            cv.put("User_No", UserID);
            cv.put("TrYear", ed_Year.getText().toString());
            cv.put("TrMonth", tv_Month.getText().toString());
            cv.put("Posted", "0");
            i = sqlHandler.Insert("Monthly_Schedule", null, cv);
            if (i > 0) {
                FillAllDays();
                // FillAllCountryOfDay();

            }
            //   Toast.makeText(this, SelectedDate.getDate(), Toast.LENGTH_SHORT).show();
        }
    }

    public void DeleteAll() {

        String q;
        SqlHandler sqlHandler = new SqlHandler(MonthlySalesManSchedule.this);


        sqlHandler.executeQuery("Delete from SaleManRounds");

        sqlHandler.executeQuery("Delete from CustomersN");

        sqlHandler.executeQuery("Delete from Sal_invoice_Hdr");
        sqlHandler.executeQuery("Delete from Sal_invoice_Det");
        sqlHandler.executeQuery("Delete from Po_dtl");
        sqlHandler.executeQuery("Delete from Po_Hdr");


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MonthlySalesManSchedule.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("m4", "0");

        editor.putString("m6", "0");

        editor.commit();


        AlertDialog alertDialog = new AlertDialog.Builder(
                MonthlySalesManSchedule.this).create();
        alertDialog.setTitle("الإعدادات العامة");
        alertDialog.setMessage(String.valueOf(getResources().getString(R.string.DeleteCompleteSuccsully)));
        alertDialog.setIcon(R.drawable.tick);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });


        alertDialog.show();


    }

    public void btn_Others(View view) {
        DeleteAll();
        if (SelectedDate.getDate().equalsIgnoreCase("")) {
            Toast.makeText(this, String.valueOf(getResources().getString(R.string.Pleaseselectadate)), Toast.LENGTH_SHORT).show();
        } else {
            Long i;
            ContentValues cv = new ContentValues();
            cv.put("Today_Date", SelectedDate.getDate().toString());
            cv.put("Period_No", SelectedDate.getPeriodNo().toString());
            cv.put("Area_No", "-2");
            cv.put("User_No", UserID);
            cv.put("TrYear", ed_Year.getText().toString());
            cv.put("TrMonth", tv_Month.getText().toString());
            cv.put("Posted", "0");
            i = sqlHandler.Insert("Monthly_Schedule", null, cv);
            if (i > 0) {
                FillAllDays();
                //   FillAllCountryOfDay();

            }
            //   Toast.makeText(this, SelectedDate.getDate(), Toast.LENGTH_SHORT).show();
        }
    }

    public int btn_Holiday(View view) {

        boolean Exist = true;
        Exist = CheckArea(SelectedDate.getDate().toString(), SelectedDate.getPeriodNo().toString(), "-4");
        if (Exist == false) {
            return 0;
        }


        if (SelectedDate.getDate().equalsIgnoreCase("")) {
            Toast.makeText(this, String.valueOf(getResources().getString(R.string.Pleaseselectadate)), Toast.LENGTH_SHORT).show();
        } else {
            Long i;
            ContentValues cv = new ContentValues();
            cv.put("Today_Date", SelectedDate.getDate().toString());
            cv.put("Period_No", SelectedDate.getPeriodNo().toString());
            cv.put("Area_No", "-4");
            cv.put("User_No", UserID);
            cv.put("TrYear", ed_Year.getText().toString());
            cv.put("TrMonth", tv_Month.getText().toString());
            cv.put("Posted", "0");
            i = sqlHandler.Insert("Monthly_Schedule", null, cv);
            if (i > 0) {
                FillAllDays();
                // FillAllCountryOfDay();

            }
            //   Toast.makeText(this, SelectedDate.getDate(), Toast.LENGTH_SHORT).show();
        }
        return 1;
    }

    public int btn_Vacations(View view) {

        boolean Exist = true;
        Exist = CheckArea(SelectedDate.getDate().toString(), SelectedDate.getPeriodNo().toString(), "-3");
        if (Exist == false) {
            return 0;
        }


        if (SelectedDate.getDate().equalsIgnoreCase("")) {
            Toast.makeText(this, String.valueOf(getResources().getString(R.string.Pleaseselectadate)), Toast.LENGTH_SHORT).show();
        } else {
            Long i;
            ContentValues cv = new ContentValues();
            cv.put("Today_Date", SelectedDate.getDate().toString());
            cv.put("Period_No", SelectedDate.getPeriodNo().toString());
            cv.put("Area_No", "-3");
            cv.put("User_No", UserID);
            cv.put("TrYear", ed_Year.getText().toString());
            cv.put("TrMonth", tv_Month.getText().toString());
            cv.put("Posted", "0");
            i = sqlHandler.Insert("Monthly_Schedule", null, cv);
            if (i > 0) {
                FillAllDays();
                //  FillAllCountryOfDay();

            }
            //   Toast.makeText(this, SelectedDate.getDate(), Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    private boolean CheckArea(String Date, String Period, String AreaNo) {
        boolean f = true;

/*        if (AreaNo.equalsIgnoreCase("-3") || AreaNo.equalsIgnoreCase("-4")) {
            q = " Select * from  Monthly_Schedule where Today_Date='" + Date + "'   and Period_No ='" + Period + "' and User_No='" + UserID + "'";
            Cursor c1 = sqlHandler.selectQuery(q);
            if (c1 != null && c1.getCount() != 0) {


                Toast.makeText(this, "الرجاء حذف المدخلات الموجودة اولا", Toast.LENGTH_SHORT).show();
                f = false;
                c1.close();
                return f;


            }


        }


        q = " Select * from  Monthly_Schedule where Today_Date='" + Date + "'   and Period_No ='" + Period + "' and Area_No ='" + AreaNo + "' and User_No='" + UserID + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {


            Toast.makeText(this, "تم إضافة المنطقة سابقا", Toast.LENGTH_SHORT).show();
            f = false;
            c1.close();

            return f;
        }

        q = " Select * from  Monthly_Schedule where Today_Date='" + Date + "'   and Period_No ='" + Period + "' and Area_No ='-4' and User_No='" + UserID + "'  ";
        c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {


            Toast.makeText(this, "لا يمكن اضافة اي منطقة  مع العطلة الرسمية", Toast.LENGTH_SHORT).show();
            f = false;
            c1.close();
            return f;
        }

        q = " Select * from  Monthly_Schedule where Today_Date='" + Date + "'   and Period_No ='" + Period + "' and Area_No ='-3' and User_No='" + UserID + "'  ";
        c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {


            Toast.makeText(this, "لا يمكن اضافة اي منطقة  مع الإجازة", Toast.LENGTH_SHORT).show();
            f = false;
            c1.close();
            return f;
        }


        q = " Select * from  Monthly_Schedule where Today_Date='" + Date + "'   and Period_No ='" + Period + "' and Area_No ='-1' and User_No='" + UserID + "'  ";
        c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            Toast.makeText(this, "لا يمكن اضافة اي منطقة  مع التحصيل", Toast.LENGTH_SHORT).show();
            f = false;
            c1.close();
            return f;
        }*/
        return f;
    }


    public void btn_back(View view) {
        Intent k = new Intent(this,GalaxyNewHomeActivity.class);
        startActivity(k);
    }

    @Override
    public void onBackPressed() {
        Intent k;
        k = new Intent(this, GalaxyNewHomeActivity.class);
        startActivity(k);
    }

    public void btn_SelectMonth(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "SalesOrder");
        bundle.putString("Nm", "اختيار الشهر");
        bundle.putString("Month", tv_Month.getText().toString());

        FragmentManager Manager = getFragmentManager();
        Pop_Update_Month obj = new Pop_Update_Month();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }

    public void UpdateMonth(String m) {
        tv_Month.setText(m);
        month_Dates();
    }

    public void btn_SelectYear(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "SalesOrder");
        bundle.putString("Nm", "اختيار السنة");
        bundle.putString("Year", ed_Year.getText().toString());
        FragmentManager Manager = getFragmentManager();
        Pop_Update_Year obj = new Pop_Update_Year();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }

    public void UpdateYear(String y) {
        ed_Year.setText(y);
        month_Dates();
    }

    public void btn_share(View view) {

        int x = getChackPosted();
        if (x == 1) {

            loadingdialog = ProgressDialog.show(MonthlySalesManSchedule.this, String.valueOf(getResources().getString(R.string.PleaseWait)), "العمل جاري على اعتماد الخطة الشهرية", true);
            loadingdialog.setCancelable(false);
            loadingdialog.setCanceledOnTouchOutside(false);
            loadingdialog.show();
            final Handler _handler = new Handler();


            // Toast.makeText(getApplicationContext(),str, Toast.LENGTH_LONG).show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    PostMonthlyScedule obj = new PostMonthlyScedule(MonthlySalesManSchedule.this);
                    PostResult = 1;
                    obj.Post_Scedule(tv_Month.getText().toString(), ed_Year.getText().toString());
                    try {
                        if (PostResult > 0) {
                            _handler.post(new Runnable() {
                                public void run() {
                                    q = "Update Monthly_Schedule set Posted='1' Where  TrMonth = '" + tv_Month.getText().toString() + "' and TrYear ='" + ed_Year.getText().toString() + "'";
                                    sqlHandler.executeQuery(q);
                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            MonthlySalesManSchedule.this).create();
                                    alertDialog.setTitle(String.valueOf(getResources().getString(R.string.monthlypost)));
                                    alertDialog.setMessage("تم أرسال الخطة الى المشرف بنجاح");
                                    alertDialog.setIcon(R.drawable.tick);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });

                                    loadingdialog.dismiss();
                                    alertDialog.show();


                                }
                            });


                        } else if (PostResult == -3) {
                            loadingdialog.dismiss();
                            _handler.post(new Runnable() {
                                public void run() {
                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            MonthlySalesManSchedule.this).create();
                                    alertDialog.setTitle(String.valueOf(getResources().getString(R.string.PostNotCompleteSuccfully)) + "   " + We_Result.ID + "");
                                    alertDialog.setMessage(We_Result.Msg.toString());
                                    alertDialog.setIcon(R.drawable.delete);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    alertDialog.show();


                                    alertDialog.setIcon(R.drawable.delete);
                                    alertDialog.setMessage(We_Result.Msg);
                                }
                            });


                            loadingdialog.dismiss();
                            _handler.post(new Runnable() {
                                public void run() {
                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            MonthlySalesManSchedule.this).create();
                                    alertDialog.setTitle(String.valueOf(getResources().getString(R.string.PostNotCompleteSuccfully)) + "   " + We_Result.ID + "");
                                    alertDialog.setMessage(We_Result.Msg.toString());
                                    alertDialog.setIcon(R.drawable.delete);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    alertDialog.show();

                                    alertDialog.setIcon(R.drawable.delete);
                                    alertDialog.setMessage(String.valueOf(getResources().getString(R.string.PostNotCompleteSuccfully)) + "    ");
                                }
                            });
                        } else {
                            _handler.post(new Runnable() {
                                public void run() {
                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            MonthlySalesManSchedule.this).create();
                                    alertDialog.setTitle(String.valueOf(getResources().getString(R.string.monthlypost)));
                                    alertDialog.setMessage("تم أرسال الخطة الى المشرف بنجاح");
                                    alertDialog.setIcon(R.drawable.tick);
                                    alertDialog.setButton(String.valueOf(getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });

                                    loadingdialog.dismiss();
                                    alertDialog.show();
                                }
                            });
                        }
                    } catch (final Exception e) {
                        loadingdialog.dismiss();
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        MonthlySalesManSchedule.this).create();
//                            alertDialog.setTitle( String.valueOf(getResources().getString(R.string.ConnectError));
                                alertDialog.setTitle(String.valueOf(getResources().getString(R.string.ConnectError)));

                                alertDialog.setMessage(e.getMessage().toString());
                                alertDialog.setIcon(R.drawable.delete);
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


    private void FillKeys(String Date, String PeriodNo) {
        //   filter = (EditText) form.findViewById(R.id.et_Search_filter);
        // String query = "";
        try {
            sqlHandler = new SqlHandler(MonthlySalesManSchedule.this);
            String query = "Select *  from AreasN where  TableNo='13'";

            //     Bundle bundle = this.getArguments();

            // String KeyId=DB.GetValue(getActivity(),"ItemsKeysN","KeyId","ItemId='"+itemid+"'");

            cls_invf_List = new ArrayList<keys_modle>();
            cls_invf_List.clear();
            Cursor c1 = sqlHandler.selectQuery(query);
            if (c1 != null && c1.getCount() != 0) {
                if (c1.moveToFirst()) {
                    do {
                        keys_modle keys_modle = new keys_modle();
                        LocaleHelper LocaleHelper = new LocaleHelper();
                        if (LocaleHelper.getlanguage(MonthlySalesManSchedule.this).equals("ar")) {
                            keys_modle.setKey(c1.getString(c1
                                    .getColumnIndex("DescrA")));
                        } else {
                            keys_modle.setKey(c1.getString(c1
                                    .getColumnIndex("DescrE")));
                        }

                        keys_modle.setItemNo(c1.getString(c1
                                .getColumnIndex("ItemNo")));

                        //   query = "Select ItemNo, Desc  from ItemsCheck where TransNo='"+getArguments().getString("transno")+"' and ItemNo='"+getArguments().getString("itemid")+"'";

                        String item = DB.GetValue(MonthlySalesManSchedule.this, "ItemsCheck", "KeyId", "Desc='" + c1.getString(c1
                                .getColumnIndex("DescrA")) + "' and Today_Date='" + Date + "'   and Period_No ='" + PeriodNo + "'  and User_No='" + UserID + "'  ");


                        String item2 = DB.GetValue(MonthlySalesManSchedule.this, "ItemsCheck", "KeyId", "Desc='" + c1.getString(c1
                                .getColumnIndex("DescrE")) + "' and Today_Date='" + Date + "'   and Period_No ='" + PeriodNo + "'   and User_No='" + UserID + "'  ");
/*
                        if (item.equals("-1") && item2.equals("-1")) {
                            keys_modle.setCheck(false);

                        } else {
                            keys_modle.setCheck(true);

                        }*/
                        cls_invf_List.add(keys_modle);

                    } while (c1.moveToNext());

                }
                c1.close();
            }

            Monthly_adapter cls_invf_adapter = new Monthly_adapter(
                    MonthlySalesManSchedule.this, cls_invf_List);
            lv.setAdapter(cls_invf_adapter);

            // Cls_Keys_adapter gg = new Cls_Keys_adapter(MonthlySalesManSchedule.this);
        } catch (Exception e) {
        }


    }

    public void btn_save(View view) {
        int x = getChackPosted();
        if (x == 1) {


            q = "Delete from Monthly_Schedule";
            sqlHandler.executeQuery(q);


            Long i;
            int j = 0;
            String query = " Select  *  from Month_Dates where entry ='1'";

            sqlHandler = new SqlHandler(MonthlySalesManSchedule.this);


            Cursor c1 = sqlHandler.selectQuery(query);
            if (c1 != null && c1.getCount() != 0) {
                if (c1.moveToFirst()) {
                    do {


                        ContentValues cv = new ContentValues();
                        cv.put("Today_Date", c1.getString(c1.getColumnIndex("TodayDate")));
                        cv.put("Period_No", c1.getString(c1.getColumnIndex("PeriodNo")));
                         cv.put("Area_No", AllCountry.get(position).getID().toString());
                        cv.put("User_No", UserID);
                        cv.put("TrYear", c1.getString(c1.getColumnIndex("CurrentYear")));
                        cv.put("TrMonth", c1.getString(c1.getColumnIndex("CurrentMonth")));
                        cv.put("Posted", "0");
                        cv.put("DoubleVisit", c1.getString(c1.getColumnIndex("DoubleVisit")));
                        // cv.put("VisitCustomers", AllCustomers.get(position).getID().toString());

                        i = sqlHandler.Insert("Monthly_Schedule", null, cv);
                        j += i;
                        if (i < 0) {

                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    MonthlySalesManSchedule.this).create();
                            alertDialog.setTitle(String.valueOf(getResources().getString(R.string.monthlypost)));
                            alertDialog.setMessage("فشل في عملية التخزين");
                            alertDialog.setIcon(R.drawable.error_new);
                            alertDialog.setButton(String.valueOf(getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

                            alertDialog.show();


                        }

                    } while (c1.moveToNext());

                }
                c1.close();
            }
            if (j > 0) {
                q = "Update PlanMonthlyLookups set Post ='1' where Post = '0' ";
                sqlHandler.executeQuery(q);
                AlertDialog alertDialog = new AlertDialog.Builder(
                        MonthlySalesManSchedule.this).create();
                alertDialog.setTitle(String.valueOf(getResources().getString(R.string.monthlypost)));
                alertDialog.setMessage("نجح في عملية التخزين");
                alertDialog.setIcon(R.drawable.tick);
                alertDialog.setButton(String.valueOf(getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            }


            //i = sqlHandler.Update("Monthly_Schedule", cv, "Today_Date='" + SelectedDate.getDate() + "' and Period_No='" + SelectedDate.getPeriodNo() + "' and User_No='" + UserID + "'");


        }
    }
    public void search_cust(View v)
    {
        Bundle bundle = new Bundle();
        //  et_NoS = returntransno();
        bundle.putString("date", SelectedDate.getDate() );
        bundle.putString("Period", SelectedDate.getPeriodNo() );
        bundle.putString("UserId", UserID);

        // ViewVisitFrg exampleDialog = new ViewVisitFrg();
        Search_CustomerFrq exampleDialog = new Search_CustomerFrq();
        exampleDialog.setArguments(bundle);
        exampleDialog.show(getFragmentManager(), "example dialog");
    }




}
