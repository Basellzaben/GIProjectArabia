package com.cds_jo.GalaxySalesApp.NewPackage;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;

import java.util.ArrayList;


public class CustomerInfoFrag extends DialogFragment {
    View form ;
    SqlHandler sqlHandler;
    TextView types;
    LinearLayout linearLayout;
    ArrayList<CustomerDays> customerDays;
    CustomerDaysAdapter customerDaysAdapter;
    //  ArrayList<GetDoneBarCode> list1;
    Button btn_Cancel;
    ListView Customers;
    EditText Cust;
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
        form= inflater.inflate(R.layout.fragment_customer_info, container, false);
        Customers=(ListView) form.findViewById(R.id.lv);
        customerDays = new ArrayList<CustomerDays>();
        sqlHandler = new SqlHandler(getActivity());
        linearLayout =(LinearLayout)form.findViewById(R.id.sss);
        //  types=(TextView) form.findViewById(R.id.type);
        //    AllCustomers = new ArrayList<Cls_Country>();
        //     rating = (EditText) form.findViewById(R.id.rating);
        Cust = (EditText) form.findViewById(R.id.Cust);
        String CustName;

        if (LocaleHelper.getlanguage(getActivity()).equals("ar")) {

            CustName= DB.GetValue(getActivity(),"CustomersN","ArabicName","id='"+getArguments().getString("CustNo")+"'");

            Cust.setText(CustName);
            Customers.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        } else {

            CustName=DB.GetValue(getActivity(),"CustomersN","EnglishName","id='"+getArguments().getString("CustNo")+"'");
            Cust.setText(CustName);
            Customers.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        FillAllCustomers();



        btn_Cancel=(Button)form.findViewById(R.id.btn_Cancel);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        getActivity().getFragmentManager().beginTransaction().remove(CustomerInfoFrag.this).commit();

            }
        });
        return form;
    }


    public void end(){
        getActivity().getFragmentManager().beginTransaction().remove(CustomerInfoFrag.this).commit();

    }


    public void FillAllCustomers() {

        customerDays.clear();
        String  q;

        q = " Select   *  from CustomerFavDays where CustomerId = " + getArguments().getString("CustNo");



        //  GetTodayAllArea();
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    CustomerDays obj = new CustomerDays();
                    LocaleHelper LocaleHelper = new LocaleHelper();
                    obj.setCustomerId(c1.getString(c1.getColumnIndex("CustomerId")));
                    obj.setDayNo(c1.getString(c1.getColumnIndex("DayNo")));
                    obj.setNotes(c1.getString(c1.getColumnIndex("Notes")));
                    obj.setTime(c1.getString(c1.getColumnIndex("Time")));
                   /* if (LocaleHelper.getlanguage(getActivity()).equals("ar")) {
                        obj.setNm(c1.getString(c1
                                .getColumnIndex("ArabicName")));
                    } else {
                        obj.setNm(c1.getString(c1
                                .getColumnIndex("EnglishName")));
                    }*/

                    //obj.setNm(c1.getString(c1.getColumnIndex("DescrA")));
                 /*   boolean x = true;
                    for (int i = 0; i < customerDays.size(); i++)
                        if (c1.getString(c1.getColumnIndex("id")).equals(customerDays.get(i).getID()))
                            x = false;
                    if (x)
                        customerDays.add(obj);*/
                    customerDays.add(obj);
                } while (c1.moveToNext());

            }
            c1.close();
        }

        customerDaysAdapter = new CustomerDaysAdapter(
                getActivity(), customerDays);
        Customers.setAdapter(customerDaysAdapter);
    }

}
