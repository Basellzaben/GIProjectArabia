package com.cds_jo.GalaxySalesApp.NewPackage;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;

import java.util.ArrayList;


public class Search_CustomerFrq  extends DialogFragment {
    View form ;
    SqlHandler sqlHandler;
    TextView types;
    ArrayList<Cls_Country> AllCustomers;
    Cls_Customers_All_adapter cls_Customers_sechedule_adapter;
    //  ArrayList<GetDoneBarCode> list1;
    Button btn_Cancel;
    Button btn_Clear;
    RecyclerView Customers;
    EditText rating,Cust,specialization;
    /* GetDoneBarCodeAdapter adapter;
     GetDoneBarCode cls_barCode;
 */
    String transno="";
    ProgressDialog progressdialog;


    /*  @Override
      public void onStart()
      {
          super.onStart();

          // safety check
          if (getDialog() == null)
              return;
          int dialogWidth =  WindowManager.LayoutParams.WRAP_CONTENT;//340; // specify a value here
          int dialogHeight =  WindowManager.LayoutParams.WRAP_CONTENT;//400; // specify a value here
          getDialog().getWindow().setLayout(dialogWidth, dialogHeight);

      }*/

    @Override
    public void onStart()
    {
        super.onStart();
        if (getDialog() == null)
            return;

        int dialogWidth = 1000; // specify a value here
        int dialogHeight = WindowManager.LayoutParams.MATCH_PARENT;//400; // specify a value here

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        form= inflater.inflate(R.layout.fragment_search__customer_frq, container, false);
        Customers=(RecyclerView) form.findViewById(R.id.lv);
        types=(TextView) form.findViewById(R.id.type);
        AllCustomers = new ArrayList<Cls_Country>();
        rating = (EditText) form.findViewById(R.id.rating);
        Cust = (EditText) form.findViewById(R.id.Cust);
        specialization = (EditText)form. findViewById(R.id.specialization);

        showCustomer("2");

        Cust.addTextChangedListener(new TextWatcher() {
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
        rating.addTextChangedListener(new TextWatcher() {
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
        specialization.addTextChangedListener(new TextWatcher() {
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



        btn_Cancel=(Button)form.findViewById(R.id.btn_Cancel);
        btn_Clear=(Button)form.findViewById(R.id.btn_Clear);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().remove(Search_CustomerFrq.this).commit();

            }
        });
        btn_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specialization.setText("");
                rating.setText("");
                Cust.setText("");
                showCustomer("2");

            }
        });
        return form;
    }


    public void end(){
        getActivity().getFragmentManager().beginTransaction().remove(Search_CustomerFrq.this).commit();

    }


    public void FillAllCustomers(String idC) {

        AllCustomers.clear();
        String q1, q2, q3, q;
//mohkhaldi
        //     q = " Select   ItemNo , DescrA from AreasN  where    cast(ItemNo as INTEGER ) >0";

        if (Cust.getText().toString().equals("")&&rating.getText().toString().equals("")&&specialization.getText().toString().equals("")) {
            q = " Select   *  from CustomersN where " + idC;
        } else {
            q1 = GetNameSearch("");
            q2 = GetTypeCustomerSearch2();
            q3 = GetTypeCustomerSearch();
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
                        q += " and " + q2;

                    } else {
                        q += q2;
                        x += 1;
                    }
                }
                if (!(q3.equals(""))) {
                    if (x > 1) {
                        q += " and " + q3;
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
                    if (LocaleHelper.getlanguage(getActivity()).equals("ar")) {
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
                getActivity(), AllCustomers);
        Customers.setLayoutManager(new LinearLayoutManager(getActivity()));

        Customers.setAdapter(cls_Customers_sechedule_adapter);
    }
    public String GetNameSearch(String TabletNo) {
        //   EditText Search_Customer = (EditText) findViewById(R.id.Search_Customer);

        String NameC = "";
        if(!(Cust.getText().toString().equals(""))) {
            String query;
            if (LocaleHelper.getlanguage(getActivity()).equals("ar")) {
                query = " Select  id  from CustomersN where ArabicName like '%" + Cust.getText().toString() + "%' ";
            } else {
                query = " Select  id  from CustomersN where EnglishName like '%" + Cust.getText().toString() + "%' ";
            }
            sqlHandler = new SqlHandler(getActivity());


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
        }

        return NameC;
    }

    public String GetTypeCustomerSearch() {


        String NameC = "";
        if (!(specialization.getText().toString().equals(""))) {
            String query;
            if (LocaleHelper.getlanguage(getActivity()).equals("ar")) {
                query = " Select  Id  from AreasN where DescrA like '%" + specialization.getText().toString() + "%' and TableNo = '2' ";
            } else {
                query = " Select  Id  from AreasN where DescrE like '%" + specialization.getText().toString() + "%' and TableNo = '2' ";
            }
            sqlHandler = new SqlHandler(getActivity());


            Cursor c1 = sqlHandler.selectQuery(query);
            if (c1 != null && c1.getCount() != 0) {
                if (c1.moveToFirst()) {
                    do {

                        if (NameC.equals("")) {
                            NameC = "SpecializedNo = '" + c1.getString(c1.getColumnIndex("Id")) + "'";
                        } else {
                            NameC += "or SpecializedNo = '" + c1.getString(c1.getColumnIndex("Id")) + "'";


                        }


                    } while (c1.moveToNext());

                }
                c1.close();

            }
        }

        return NameC;
    }
    public String GetTypeCustomerSearch2() {


        String NameC = "";
        if (!(rating.getText().toString().equals(""))) {
            String query;
            if (LocaleHelper.getlanguage(getActivity()).equals("ar")) {
                query = " Select  Id  from AreasN where DescrA like '%" + rating.getText().toString() + "%' and TableNo = '3' ";
            } else {
                query = " Select  Id  from AreasN where DescrE like '%" + rating.getText().toString() + "%' and TableNo = '3' ";
            }
            sqlHandler = new SqlHandler(getActivity());


            Cursor c1 = sqlHandler.selectQuery(query);
            if (c1 != null && c1.getCount() != 0) {
                if (c1.moveToFirst()) {
                    do {

                        if (NameC.equals("")) {
                            NameC = "CategoryNo = '" + c1.getString(c1.getColumnIndex("Id")) + "'";
                        } else {
                            NameC += "or CategoryNo = '" + c1.getString(c1.getColumnIndex("Id")) + "'";

                        }


                    } while (c1.moveToNext());

                }
                c1.close();

            }

        }
        return NameC;
    }

    public void showCustomer(String TabletNo) {
        String country = "";

        String query = " Select  itemno  from PlanMonthlyLookups where Date='" + getArguments().getString("date") + "' and Period ='" + getArguments().getString("Period") + "' and UserId ='" + getArguments().getString("UserId") + "' and TabletNo ='" + TabletNo + "' ";

        sqlHandler = new SqlHandler(getActivity());


        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    String country2 = DB.GetValue(getActivity(), "AreasN", "Id", "ItemNo='" + c1.getString(c1.getColumnIndex("itemno")) + "'and TableNo ='9'");

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
}
