package com.cds_jo.GalaxySalesApp;

import android.app.DialogFragment;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.Reports.Report_Home;
import com.cds_jo.GalaxySalesApp.assist.Acc_ReportActivity;
import com.cds_jo.GalaxySalesApp.assist.CustomerReturnQtyActivity;
import com.cds_jo.GalaxySalesApp.assist.Customer_List;
import com.cds_jo.GalaxySalesApp.assist.Customers;
import com.cds_jo.GalaxySalesApp.assist.OrdersItems;
import com.cds_jo.GalaxySalesApp.assist.Sale_InvoiceActivity;

import java.util.ArrayList;
import java.util.List;


public class Select_Customer_Expetion extends DialogFragment implements View.OnClickListener  {
    View form ;
    Button add,cancel;
    ListView items_Lsit;
    TextView itemnm;
    private SearchView mSearchView;
    EditText filter   ;
    ImageView btn_filter_search ;
    @Override
    public View onCreateView( final LayoutInflater inflater   , ViewGroup container  ,Bundle savestate) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        form = inflater.inflate(R.layout.activity_select__customer, container, false);

        getDialog().setTitle("Galaxy International Group");
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.TOP|Gravity.CENTER);
        if(GlobaleVar.LanType==1)
        {
            LinearLayout linrtl=(LinearLayout)form.findViewById(R.id.LL2);
            linrtl.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        else
        {
            LinearLayout linrtl=(LinearLayout)form.findViewById(R.id.LL2);
            linrtl.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        // Get the SearchView and set the searchable configuration
        btn_filter_search =(ImageView) form.findViewById(R.id.brn_seachAcc);
        filter =    (EditText) form.findViewById(R.id.et_Search_filter);


        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Toast.makeText(getActivity(),s.toString(),Toast.LENGTH_SHORT).show();
                onProgressUpdate(s.toString());

            }
        });
        onProgressUpdate("");

        btn_filter_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                onProgressUpdate(filter.getText().toString());
            }
        });





        items_Lsit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                /*arg1.setBackgroundColor(Color.GREEN);
                Object o = items_Lsit.getItemAtPosition(position);
                String str=(String)o;//As you are using Default String Adapter*/
                Customers customers = (Customers) arg0.getItemAtPosition(position);
                String nm = customers.getNm();
                Exist_Pop();
                //Toast.makeText(getActivity(), nm, Toast.LENGTH_LONG).show();

                if (getArguments().getString("Scr") == "po") {
                    ((OrdersItems) getActivity()).Set_Cust(customers.getAcc(), customers.getNm());

                }

                else if (getArguments().getString("Scr") == "AccReport") {
                    ((Acc_ReportActivity) getActivity()).Set_Cust(customers.getAcc(), customers.getNm());

                }

                else if (getArguments().getString("Scr") == "RecVoch") {
                    ((RecvVoucherActivity) getActivity()).Set_Cust(customers.getAcc(), customers.getNm(),customers.getIDN());

                }
                else if (getArguments().getString("Scr") == "Sale_Inv") {
                    ((Sale_InvoiceActivity) getActivity()).Set_Cust(customers.getAcc(), customers.getNm());

                }
                else if (getArguments().getString("Scr") == "Sale_Inv") {
                    ((Report_Home) getActivity()).Set_Cust(customers.getAcc(), customers.getNm());

                }
                else if (getArguments().getString("Scr") == "SaleRound") {
                    ((SaleManRoundsActivity) getActivity()).Set_Cust(customers.getAcc(), customers.getNm());

                }
                else if (getArguments().getString("Scr") == "Gps" || getArguments().getString("Scr") == "ExpGps") {
                    ((MainActivity) getActivity()).Set_CustException(customers.getAcc(), customers.getNm(),customers.getIDN());

                }
                else if (getArguments().getString("Scr") == "CustQty") {
                    ((CustomerQty) getActivity()).Set_Cust(customers.getAcc(), customers.getNm());

                }

                else if (getArguments().getString("Scr") == "RetnQty") {
                    ((CustomerReturnQtyActivity) getActivity()).Set_Cust(customers.getAcc(), customers.getNm());

                }
                else if (getArguments().getString("Scr") == "DoctorReprot") {
                    ((DoctorReportActivity) getActivity()).Set_Cust(customers.getAcc(), customers.getNm());
                }

                    else if (getArguments().getString("Scr") == "CusfCard") {
                    ((CusfCard) getActivity()).Set_Cust(customers.getAcc(), customers.getNm());
                }


               // Exist_Pop();
                //getDialog().setTitle(str);
                ////itemnm.setText(str);
            }


        });
        this.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return  form;
    }


    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);

        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }


    public void onProgressUpdate(String t ){
        final List<String> items_ls = new ArrayList<String>();
        SqlHandler sqlHandler = new SqlHandler(getActivity());
        items_Lsit=(ListView) form.findViewById(R.id.listView2);
        items_Lsit.setAdapter(null);
        float t_dept , t_cred,t_bb ,tot ,t_tot,temp,t_rate;
        t_dept= t_cred=t_bb =tot =t_tot =temp=t_rate =  0 ;
        String query ;

        String Cust_type="1";
        if (getArguments().getString("Scr") == "DoctorReprot") {
            Cust_type= getArguments().getString("PrvVisitType");
        }


        if (  getArguments().getString("Scr") == "Gps" || getArguments().getString("Scr") == "ExpGps") {
            if (t.toString().equals("")) {
                //query = "Select c.no , c.name,c.Ename, c.Location  from Customers  c inner join ManExceptions on c.no=ManExceptions.CustNo and  ManExceptions.ExptCat='1'";//   Customers.Cust_type='"+Cust_type+"'";
                query = "Select DISTINCT c.no , c.name,c.Ename, c.Location ,IDN,Address from Customers_Man  c";//   Customers.Cust_type='"+Cust_type+"'";
            } else {
                query = "Select DISTINCT c.no , c.name,c.Ename, c.Location,IDN,Address from Customers_Man  c  " +
                        "   where c.name like '%" + t + "%' or  c.no like '%" + t + "%'";//  And Customers.Cust_type='"+Cust_type+"'";
            }


        } else {
                if (t.toString().equals("")){
                   // query = "Select DISTINCT barCode as no,Address,name,Ename from Customers_man  where no in (select cast (CustomerId as Int)  from ManExceptions1)";//   Customers.Cust_type='"+Cust_type+"'";
                    query = "Select  barCode as no,Address,name,Ename from Customers  ";//   Customers.Cust_type='"+Cust_type+"'";
                }
                else {
                    //query = "Select * from Customers_man where no in (select cast (CustomerId as Int)  from ManExceptions1) amd name like '%" + t + "%' or  no like '%" + t + "%'";//  And Customers.Cust_type='"+Cust_type+"'";
                    query = "Select * from Customers where  name like '%" + t + "%' or  no like '%" + t + "%'";//  And Customers.Cust_type='"+Cust_type+"'";
                }

            }

        Cursor c = sqlHandler.selectQuery(query);
        ArrayList<Customers> customersesList = new ArrayList<Customers>();
        if (c!=null && c.getCount()!=0 ){
            if(c.moveToFirst()){
             do{
                 Customers     customers = new Customers();

                 customers.setNo(c.getString(c.getColumnIndex("no")));
                 customers.setAcc(c.getString(c.getColumnIndex("no")));
                 if (ComInfo.ComNo == Companies.diamond.getValue() || ComInfo.ComNo == Companies.nwaah.getValue()) {
                     customers.setIDN(c.getString(c.getColumnIndex("IDN")));
                 }
                 if(GlobaleVar.LanType==1)
                     customers.setNm(c.getString(c.getColumnIndex("name")));
                 else
                     customers.setNm(c.getString(c.getColumnIndex("Ename")));
                 customers.setLocation(c.getString(c.getColumnIndex("Address")));

                 customersesList.add(customers);

             }while (c.moveToNext());
            }
            c.close();
        }




            Customer_List Customer_List_adapter = new Customer_List(
                    this.getActivity(), customersesList);

            items_Lsit.setAdapter(Customer_List_adapter);


    }

       public void Exist_Pop ()
       {
           this.dismiss();
       }

    @Override
    public void onClick(View v) {
        Button bu = (Button) v ;
        if (bu.getText().toString().equals("Cancel")){
            this.dismiss();
        }
        else  if (bu.getText().toString().equals("Add")){
            Toast.makeText(getActivity(),
                    "Your Message", Toast.LENGTH_SHORT).show();



        }




    }


    public void onListItemClick(ListView l, View v, int position, long id) {



        items_Lsit.setItemChecked(position, true);
        v.setBackgroundColor(Color.BLUE);



    }










 }
