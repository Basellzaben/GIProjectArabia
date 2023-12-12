package com.cds_jo.GalaxySalesApp.TQNew;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.DB;


import com.cds_jo.GalaxySalesApp.NewPackage.GalaxyNewHomeActivity;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.Select_Customer;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.UpdateDataToMobileActivity;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.cds_jo.GalaxySalesApp.assist.Cls_Check;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import header.Header_Frag;

public class ReoprtSaleActivity extends AppCompatActivity {
    String UserName;
    private Fragment frag;
    private FragmentManager fragmentManager;
    EditText to, from;
    Calendar dateSelected = Calendar.getInstance();
    DateFormat dateFormat;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    TextView name, acc;
    Calendar calendar;

    ArrayList<Cls_Check> ChecklList;
    TextView tv;
    double sum = 0;
    int countitemm = 0;

    Drawable greenProgressbar;
    RelativeLayout.LayoutParams lp;
    SqlHandler sqlHandler;
    JSONObject js;
    JSONArray CustNo;
    JSONArray CustName;
    JSONArray OrderNo;
    JSONArray Total;

    JSONArray TaxTotal;
    JSONArray NetTotal;
    JSONArray DESC_A;
    JSONArray states;
    ListView recycler;

    TextView updatedata;
    TextView sumitem;
    TextView countitem, finish;

    ArrayList<RepModel1> lista;
    String u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reoprt_sale);
        frag = new Header_Frag();

        finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i =new Intent(ReoprtSaleActivity.this, GalaxyNewHomeActivity.class);
               startActivity(i);
               finish();
            }
        });
        lista = new ArrayList<>();
        sumitem = (TextView) findViewById(R.id.sumitem);
        countitem = (TextView) findViewById(R.id.countitem);
        updatedata = (TextView) findViewById(R.id.updatedata);
        recycler = (ListView) findViewById(R.id.recycler);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1, frag).commit();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        to = findViewById(R.id.to);
        from = findViewById(R.id.from);
        name = findViewById(R.id.name);
        acc = findViewById(R.id.acc);

        name.setText("  ----  ");
        acc.setText("  ----  ");

        sqlHandler = new SqlHandler(this);


        to.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        u = sharedPreferences.getString("UserID", "");
        dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerDialog = new DatePickerDialog(ReoprtSaleActivity.this,
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

                datePickerDialog = new DatePickerDialog(ReoprtSaleActivity.this,
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


        updatedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!acc.getText().toString().equals("  ----  "))
                    getdata(acc.getText().toString(), from.getText().toString()
                            , to.getText().toString(), u);
                else
                    getdata("-1", from.getText().toString()
                            , to.getText().toString(), u);


            }
        });


        recycler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Bundle bundle = new Bundle();
                android.app.FragmentManager Manager = getFragmentManager();
                ItemsFragment1 obj = new ItemsFragment1();
                bundle.putString("OrderNo", lista.get(i).getOrderNo() + "");
                obj.setArguments(bundle);
                obj.show(Manager, null);

            }
        });

        getdata(acc.getText().toString(), from.getText().toString()
                , to.getText().toString(), "-1");


    }

    public void btn_searchCustomer(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "Report1");
        android.app.FragmentManager Manager = getFragmentManager();
        Select_Customer obj = new Select_Customer();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }


    public void Set_Custdata(String acct, String namet) {
        acc.setText(acct);
        name.setText(namet);
      /*  getdata(acc.getText().toString(),from.getText().toString()
                ,to.getText().toString(),acct);
*/
    }

    public void getdata(final String CustNo1, final String FromDate, final String ToDate, final String UserNo) {
        UserName = (DB.GetValue(ReoprtSaleActivity.this, "manf", "LoginName", "man ='" + UserNo.toString() + "'"));

        countitemm = 0;
        sum = 0;
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
        progressDialog = new ProgressDialog(ReoprtSaleActivity.this);
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setMessage("  الرجاء الانتظار ..." + "  العمل جاري على نسخ البيانات  ");
        tv.setText("طلبات البيع");
        progressDialog.setCustomTitle(tv);
        progressDialog.setProgressDrawable(greenProgressbar);
        try {
            progressDialog.show();
        } catch (Exception re) {
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
               CallWebServices ws = new CallWebServices(ReoprtSaleActivity.this);
                ws.Get_Rep_H1(CustNo1, FromDate, ToDate, UserNo);
                try {
                    Integer i;
                    String q = "";


                    if (We_Result.ID > 0) {
                        js = new JSONObject(We_Result.Msg);
                        CustNo = js.getJSONArray("CustNo");
                        CustName = js.getJSONArray("CustName");
                        OrderNo = js.getJSONArray("OrderNo");
                        Total = js.getJSONArray("Total");

                        TaxTotal = js.getJSONArray("TaxTotal");
                        NetTotal = js.getJSONArray("NetTotal");
                        DESC_A = js.getJSONArray("DESC_A");
                        states = js.getJSONArray("states");

                        for (i = 0; i < CustNo.length(); i++) {

                            RepModel1 repModel = new RepModel1();
                            repModel.setCustNo(CustNo.get(i).toString());
                            repModel.setCustName(CustName.get(i).toString());
                            repModel.setOrderNo(OrderNo.get(i).toString());
                            repModel.setTotal(Total.get(i).toString());
                            repModel.setTaxTotal(TaxTotal.get(i).toString());
                            repModel.setNetTotal(NetTotal.get(i).toString());
                            repModel.setDESC_A(DESC_A.get(i).toString());
                            repModel.setStates(states.get(i).toString());
                            lista.add(repModel);

                            sum += Double.parseDouble(NetTotal.get(i).toString());

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
                            countitemm += 1;
                        }
                        final int total = i;
                        _handler.post(new Runnable() {
                            public void run() {
                                try {
                                    progressDialog.dismiss();
                                    ReportAdapter1 reportAdapter = new ReportAdapter1(
                                            ReoprtSaleActivity.this, R.layout.row_grid, lista);

                                    recycler.setAdapter(reportAdapter);

                                    sumitem.setText(String.format(java.util.Locale.US, "%.3f",
                                            sum));


                                    countitem.setText(String.valueOf(countitemm));

                                } catch (Exception df) {
                                }
                            }
                        });
                    } else {
                        _handler.post(new Runnable() {
                            public void run() {
                                try {
                                    progressDialog.dismiss();
                                    Toast.makeText(ReoprtSaleActivity.this, "لا يوجد بيانات", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(ReoprtSaleActivity.this, "حدثت مشكلة اثناء جلب البيانات", Toast.LENGTH_SHORT).show();
                            } catch (Exception d) {
                            }
                        }
                    });
                }
            }
        }).start();
    }


}