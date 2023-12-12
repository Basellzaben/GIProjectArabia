package com.cds_jo.GalaxySalesApp;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.Mentnis.MentnisActivity;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.cds_jo.GalaxySalesApp.assist.Cls_Stores;
import com.cds_jo.GalaxySalesApp.assist.Cls_Stores_Adapter;
import com.cds_jo.GalaxySalesApp.warehouse.EnterQtyActivity;
import com.cds_jo.GalaxySalesApp.warehouse.ItemsRecepit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Select_StoreNo extends DialogFragment implements View.OnClickListener  {
    View form ;
    Button add,cancel;
    ListView items_Lsit;
    ArrayList<Cls_Stores> customersesList;
    TextView itemnm;
    private SearchView mSearchView;
    EditText filter   ;
    ImageView btn_filter_search ;
    @Override
    public View onCreateView( final LayoutInflater inflater   , ViewGroup container  ,Bundle savestate) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        form = inflater.inflate(R.layout.select_store, container, false);


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
                getdata(s.toString());

            }
        });
        getdata("");

        btn_filter_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                getdata(filter.getText().toString());
            }
        });



        items_Lsit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

            Cls_Stores cc = (Cls_Stores) arg0.getItemAtPosition(position);
                String nm = cc.getSname();
                Exist_Pop();


                if (getArguments().getString("Scr") == "EnterQty") {
                    ((EnterQtyActivity) getActivity()).Set_Store(cc.getSno(), cc.getSname(),1);
                }
                if (getArguments().getString("Scr") == "FromStore") {
                    ((MentnisActivity) getActivity()).Set_Store(cc.getSno(), cc.getSer());
                }

                else if (getArguments().getString("Scr") == "ToStore") {
                    ((TransferQty) getActivity()).Set_Store(cc.getSno(), cc.getSname() ,2);

                }

                  else if (getArguments().getString("Scr") == "ItemRecepit") {
                    ((ItemsRecepit) getActivity()).Set_Store(cc.getSno(), cc.getSname() ,2);

                }





            }


        });
        this.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return  form;
    }

    public void getdata(final String orderno){

        final Handler _handler = new Handler();

        items_Lsit=(ListView) form.findViewById(R.id.listView2);
        items_Lsit.setAdapter(null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(getActivity());
                ws.GetModelCusf(getArguments().getString("Acc"));
                try {
                    Integer i;
                    String q = "";


                    if (We_Result.ID > 0) {
                        JSONObject js = new JSONObject(We_Result.Msg);
                        JSONArray Item_No = js.getJSONArray("Item_No");
                        JSONArray   Item_Name = js.getJSONArray("Item_Name");
                        JSONArray   Serial_no = js.getJSONArray("Serial_no");
                         customersesList = new ArrayList<Cls_Stores>();

                        for (i = 0; i < Item_No.length(); i++) {

                            Cls_Stores s = new Cls_Stores();


                            s.setSno(Item_No.get(i).toString());
                            s.setSname(Item_Name.get(i).toString());
                            s.setSer(Serial_no.get(i).toString());
                            customersesList.add(s);
                            _handler.post(new Runnable() {
                                public void run() {
                                    try {

                                        Cls_Stores_Adapter List = new Cls_Stores_Adapter(
                                                getActivity(), customersesList);

                                        items_Lsit.setAdapter(List);
                                    } catch (Exception ex) {
                                    }
                                }
                            });




                        }

                    } else {

                    }
                } catch (final Exception e) {

                }
            }
        }).start();
    }



    public void onProgressUpdate(String t ){
        final List<String> items_ls = new ArrayList<String>();
        SqlHandler sqlHandler = new SqlHandler(getActivity());
        items_Lsit=(ListView) form.findViewById(R.id.listView2);
        items_Lsit.setAdapter(null);

        String query ;



        if (t.toString().equals("")){
              query = "Select * from stores  ";//   Customers.Cust_type='"+Cust_type+"'";
        }
        else {
            query = "Select * from stores where sname like '%" + t + "%' or  sno like '%" + t + "%'";//  And Customers.Cust_type='"+Cust_type+"'";
        }
        Cursor c = sqlHandler.selectQuery(query);
        ArrayList<Cls_Stores> customersesList = new ArrayList<Cls_Stores>();
        if (c!=null && c.getCount()!=0 ){
            if(c.moveToFirst()){
             do{
                 Cls_Stores s = new Cls_Stores();


                 s.setSno(c.getString(c.getColumnIndex("sno")));
                 s.setSname(c.getString(c.getColumnIndex("sname")));

                 customersesList.add(s);



             }while (c.moveToNext());
            }
            c.close();
        }



            Cls_Stores_Adapter List = new Cls_Stores_Adapter(
                    this.getActivity(), customersesList);

            items_Lsit.setAdapter(List);

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
