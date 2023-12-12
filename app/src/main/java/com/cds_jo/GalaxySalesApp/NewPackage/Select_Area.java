package com.cds_jo.GalaxySalesApp.NewPackage;

import android.app.DialogFragment;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.assist.Customer_List;
import com.cds_jo.GalaxySalesApp.assist.Customers;

import java.util.ArrayList;
import java.util.List;

import Methdes.MyTextView;


public class Select_Area  extends DialogFragment implements View.OnClickListener  {
    View form ;
    Button add,cancel;
    ListView items_Lsit;
    TextView itemnm;
    private SearchView mSearchView;
    EditText filter   ;
    ImageView btn_filter_search ;
    MyTextView tv_nm , tv_no ;
    @Override
    public View onCreateView( final LayoutInflater inflater   , ViewGroup container  ,Bundle savestate) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        form = inflater.inflate(R.layout.fragment_select__area, container, false);
        // Get the SearchView and set the searchable configuration
        btn_filter_search =(ImageView) form.findViewById(R.id.brn_seachAcc);

        tv_nm =(MyTextView) form.findViewById(R.id.tv_nm);
        tv_no =(MyTextView) form.findViewById(R.id.tv_no);

        tv_no.setText(String.valueOf(getResources().getText(R.string.number
        )));
        tv_nm.setText(String.valueOf(getResources().getText(R.string.description
        )));



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
                HidKeybad();
                Exist_Pop();


                if (getArguments().getString("Scr") == "Gps") {
                    ((MainNewActivity) getActivity()).Set_Area(customers.getAcc(), customers.getNm());

                }




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

        String[] date=getArguments().getString("et_Date").split("/");
        String d=date[2]+"/"+date[1]+"/"+date[0];
        if (t.toString().equals("")) {
          if(getArguments().getString("PlanIn").equals("2")){
            query = "Select distinct  A.TableNo, A.ItemNo, A.DescrA,A.DescrE from   AreasN A where A.TableNo='9' ";//and M.TabletNo='2' ";and  M.period='"+ GloblaVar.getPeriodNoOfDay+"' and M.Date='"+d+"'";
        }
          else
          {
              query = "Select distinct  A.TableNo, A.ItemNo, A.DescrA,A.DescrE from   AreasN A where A.TableNo='9' and ItemNo in (select itemno from PlanMonthlyLookups M where  M.TabletNo='2' and  M.period='"+ GloblaVar.getPeriodNoOfDay+"' and M.Date='"+d+"')";
          }
        } else {
            //    query = "Select TableNo, ItemNo ,  DescrA ,Descr  from     AreasN  where DescrA like '%" + t + "%' or  No like '%" + t + "%' and where TableNo='9'";
            if(getArguments().getString("PlanIn").equals("2")){
                query = "Select distinct  A.TableNo, A.ItemNo, A.DescrA,A.DescrE from   AreasN A  where A.TableNo='9'  and(DescrA like '%" + t + "%' or  ItemNo like '%" + t + "%') ";

            }
            else {
            query = "Select distinct  A.TableNo, A.ItemNo, A.DescrA,A.DescrE from   AreasN A  where A.TableNo='9'  and(DescrA like '%" + t + "%' or  ItemNo like '%" + t + "%') and ItemNo in (select itemno from PlanMonthlyLookups M where  M.TabletNo='2' and  M.period='"+ GloblaVar.getPeriodNoOfDay+"' and M.Date='"+d+"')";
        }}




        Cursor c = sqlHandler.selectQuery(query);
        ArrayList<Customers> customersesList = new ArrayList<Customers>();
        customersesList.clear();
        if (c!=null && c.getCount()!=0 ){
            if(c.moveToFirst()){
                do{
                    Customers  customers = new Customers();

                    customers.setNo(c.getString(c.getColumnIndex("ItemNo")));
                    customers.setAcc(c.getString(c.getColumnIndex("ItemNo")));

                    LocaleHelper LocaleHelper=new LocaleHelper();
                    if(LocaleHelper.getlanguage(getActivity()).equals("ar"))
                    {  customers.setNm(c.getString(c
                            .getColumnIndex("DescrA")));}
                    else
                    {
                        customers.setNm(c.getString(c
                                .getColumnIndex("DescrE")));
                    }



                    // customers.setNm(c.getString(c.getColumnIndex("DescrA")));

                    customersesList.add(customers);

                }while (c.moveToNext());
            }
            c.close();
        }
    /*    try {
            JSONObject js = new JSONObject(text[0]);
            JSONArray js_no= js.getJSONArray("no");
            JSONArray js_name= js.getJSONArray("name");

            ArrayList<Customers> customersesList = new ArrayList<Customers>();



            for(int i =0 ; i< js_no.length(); i++) {
                Customers     customers = new Customers();

                customers.setNo(js_no.get(i).toString());
                customers.setAcc(js_no.get(i).toString());
                customers.setNm(js_name.get(i).toString());

                customersesList.add(customers);
            }

*/



        Customer_List Customer_List_adapter = new Customer_List(
                this.getActivity(), customersesList);

        items_Lsit.setAdapter(Customer_List_adapter);

/*
             TextView tdept = (TextView)findViewById(R.id.tv_t_dept);
             TextView tcred = (TextView)findViewById(R.id.tv_t_cred);
             TextView tbb = (TextView)findViewById(R.id.tv_t_bb);
             tdept.setText(String.valueOf(t_dept));
             tcred.setText(String.valueOf(t_cred));
             tbb.setText(String.valueOf(t_bb));
             trate.setText(String.valueOf(t_rate));
             tt_tot.setText(String.valueOf(tot));
*/

        /*}
        catch (    Exception ex)
        {

        }*/
    }

    public void Exist_Pop ()
    {
        this.dismiss();
    }
    /*public  void btn_filter()
    {
        Thread rant = new Thread() {
            public void run() {
                CallWebServices ws = new CallWebServices();
                ws.GetAccNo(filter.getText().toString(), "s");
            }
        };
        rant.start();
        try {
            rant.join();
            onProgressUpdate(We_Result.Msg);

        } catch (Exception ex) {

        }
    }*/
    @Override
    public void onClick(View v) {
        Button bu = (Button) v ;
        if (bu.getText().toString().equals("Cancel")){
            this.dismiss();
        }
        else  if (bu.getText().toString().equals("Add")){
            Toast.makeText(getActivity(),
                    "Your Message", Toast.LENGTH_SHORT).show();
            //   ((OrdersItems)getActivity()).Save_Method("maen");


        }




    }

    private  void HidKeybad(){
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
    public void onListItemClick(ListView l, View v, int position, long id) {


        // Set the item as checked to be highlighted
        items_Lsit.setItemChecked(position, true);
        v.setBackgroundColor(Color.BLUE);

        //conversationAdapter.notifyDataSetChanged();

    }
/*@Override
    public  void OnClick(View view  ){

}*/
}
