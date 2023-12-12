package com.cds_jo.GalaxySalesApp;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.NewPackage.Select_CustomerMed;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Methdes.MyTextView;
import Methdes.MyTextView_Digital;
import header.Header_Frag;

public class VisitsDone_Report extends AppCompatActivity {

    EditText to,from;

    TextView name,acc;


    Calendar dateSelected = Calendar.getInstance();
    DateFormat dateFormat;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    int countitemm=0;
ListView recycler;
    Drawable greenProgressbar;
    RelativeLayout.LayoutParams lp;
    SqlHandler sqlHandler ;
    TextView tv  ;
    TextView updatedata;
    ArrayList<RepModel> lista;
    String u;
    ImageView clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visits_done_report);

        Fragment frag=new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1,frag).commit();
        lista=new ArrayList<>();

        clear=(ImageView)findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name.setText("  ----  ");
                acc.setText("  ----  ");

     getdata("-1",from.getText().toString(),to.getText().toString(),u);

            }
        });

       TextView finish= (TextView) findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        to= (EditText) findViewById(R.id.to);
        from= (EditText) findViewById(R.id.from);
        to.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        name= (TextView) findViewById(R.id.name);
        acc= (TextView) findViewById(R.id.acc);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        name.setText("  ----  ");
        acc.setText("  ----  ");
        recycler=(ListView)findViewById(R.id.recycler);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
      //  u =  sharedPreferences.getString("UserID", "");
        dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        to.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerDialog = new DatePickerDialog(VisitsDone_Report.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                to.setText(day + "/" + (month + 1) + "/" + year);
                                //   getdata(acc.getText().toString(),from.getText().toString()
                                //         ,to.getText().toString(),u);
                            }
                        }, year, month, dayOfMonth);
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        from.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerDialog = new DatePickerDialog(VisitsDone_Report.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                from.setText(day + "/" + (month + 1) + "/" + year);
                                //   getdata(acc.getText().toString(),from.getText().toString()
                                //  ,to.getText().toString(),u);
                            }
                        }, year, month, dayOfMonth);
                //    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
         u =  sharedPreferences.getString("UserID", "");

        updatedata=(TextView)findViewById(R.id.updatedata);
        updatedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!acc.getText().toString().equals("  ----  "))
                    getdata(acc.getText().toString(),from.getText().toString()
                            ,to.getText().toString(),u);
                else
                    getdata("-1",from.getText().toString()
                            ,to.getText().toString(),u);

            }
        });

        recycler.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Bundle bundle = new Bundle();
                android.app.FragmentManager Manager =  getFragmentManager();
                ItemsFragment obj = new ItemsFragment();
                bundle.putString("OrderNo", lista.get(i).getOrderNo()+""  );
                bundle.putString("UserNo", lista.get(i).getCustNO()+""  );
                obj.setArguments(bundle);
                obj.show(Manager, null);

            }
        });

    }

   /* public void btn_searchCustomer(View view) {


        Bundle bundle = new Bundle();
        bundle.putString("AreaNo","000");
        bundle.putString("Scr", "visitReport");
        FragmentManager Manager = getFragmentManager();
        Select_Customer obj = new Select_Customer();
        obj.setArguments(bundle);
        obj.show(Manager, null);

    }*/

    public void btn_searchCustomer(View view){
        Bundle bundle = new Bundle();
        bundle.putString("area", "");
        bundle.putString("AreaNo", "");
        bundle.putString("Scr", "donevisite");
        bundle.putString("Plan", "");
        bundle.putString("et_Date", "12/9/2022");

        FragmentManager Manager = getFragmentManager();
        Select_CustomerMed obj = new Select_CustomerMed();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }

    public void Set_Cust(String acct ,String namet){
        acc.setText(acct);
        name.setText(namet);
      /*  getdata(acc.getText().toString(),from.getText().toString()
                ,to.getText().toString(),acct);
*/
    }


    public void getdata(final String CustNo1, final String FromDate,final String ToDate,final String UserNo){

        lista.clear();
        recycler.setAdapter(null);
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
        final Handler _handler = new Handler();
        progressDialog = new ProgressDialog(VisitsDone_Report.this);
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setMessage("  الرجاء الانتظار ..." + "  العمل جاري على نسخ البيانات  ");
        tv.setText("الزيارات");
        progressDialog.setCustomTitle(tv);
        progressDialog.setProgressDrawable(greenProgressbar);
        try {
            progressDialog.show();
        } catch (Exception re) {
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(VisitsDone_Report.this);
                ws.Get_Rep_H(CustNo1,FromDate,ToDate,UserNo);
                try {
                    Integer i;
                    String q = "";


                    if (We_Result.ID > 0) {
                        JSONObject js = new JSONObject(We_Result.Msg);
                        JSONArray CustNo = js.getJSONArray("CustNo");
                        JSONArray  CustName = js.getJSONArray("CustName");
                        JSONArray  OrderNo = js.getJSONArray("OrderNo");
                        JSONArray date = js.getJSONArray("Tr_Date");
                        JSONArray time = js.getJSONArray("Tr_Time");
                        JSONArray doublevisit = js.getJSONArray("DoubleVisit");

                        JSONArray VisitType = js.getJSONArray("VisitType");
                        JSONArray VisitResult = js.getJSONArray("VisitResult");
                        JSONArray VisitObjective = js.getJSONArray("VisitObjective");


                        for (i = 0; i < CustNo.length(); i++) {

                            RepModel repModel=new RepModel();
                            repModel.setCustNO(CustNo.get(i).toString());
                            repModel.setCustName(CustName.get(i).toString());
                            repModel.setOrderNo(OrderNo.get(i).toString());
                            repModel.setTr_Date(date.get(i).toString().substring(0,10));
                            repModel.setTr_Time(time.get(i).toString());
                            repModel.setDoubleVisit(doublevisit.get(i).toString());
                            repModel.setVisitType(VisitType.get(i).toString());
                            repModel.setVisitResult(VisitResult.get(i).toString());
                            repModel.setVisitObjective(VisitObjective.get(i).toString());
                            lista.add(repModel);


                            progressDialog.setMax(CustNo.length());
                            progressDialog.incrementProgressBy(1);




                            if (progressDialog.getProgress() == progressDialog.getMax()) {
                                progressDialog.dismiss();
                            }

                            if (!progressDialog.isShowing()) {
                                _handler.post(new Runnable() {
                                    public void run() {
                                        try {
                                            progressDialog.show();
                                        } catch (Exception ex) {
                                        }
                                    }
                                });
                            }
                            countitemm+=1;
                        }
                        final int total = i;
                        _handler.post(new Runnable() {
                            public void run() {
                                try {
                                    progressDialog.dismiss();
                                    ReportAdapter reportAdapter = new ReportAdapter(
                                            VisitsDone_Report.this,R.layout.row_grid, lista);

                                    recycler.setAdapter(reportAdapter);


                                } catch (Exception df) {
                                }
                            }
                        });
                    } else {
                        _handler.post(new Runnable() {
                            public void run() {
                                try {
                                    progressDialog.dismiss();
                                    Toast.makeText(VisitsDone_Report.this,"لا يوجد بيانات",Toast.LENGTH_SHORT).show();
                                } catch (Exception df) {
                                }
                            }
                        });
                    }
                } catch (final Exception e) {
                    _handler.post(new Runnable() {
                        public void run() {
                            try {
                                progressDialog.dismiss();
                                Toast.makeText(VisitsDone_Report.this,"حدثت مشكلة اثناء جلب البيانات",Toast.LENGTH_SHORT).show();
                            } catch (Exception d) {
                            }
                        }
                    });
                }
            }
        }).start();
    }


}