package com.cds_jo.GalaxySalesApp.Pos;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.EditeTransActivity;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.assist.CustomerReturnQtyActivity;
import com.cds_jo.GalaxySalesApp.assist.Sale_InvoiceActivity;
import com.cds_jo.GalaxySalesApp.assist.cls_Search_Inv_Sale_adapter;
import com.cds_jo.GalaxySalesApp.assist.cls_Search_po;
import com.cds_jo.GalaxySalesApp.myelectomic.Pos_Ele_Activity;

import java.util.ArrayList;

public class Pos_SearchActivity extends DialogFragment implements View.OnClickListener  {
    View form ;
    Button add,cancel;
    ListView items_Lsit;
   // TextView itemnm;

String DocType ="1";
    ArrayList<cls_Search_po> cls_search_pos_list  ;
    @Override
   public View onCreateView( final LayoutInflater inflater   , ViewGroup container  ,Bundle savestate){
        form =inflater.inflate(R.layout.activity_pos__inv__search,container,false);
        DocType = getArguments().getString("doctype") ;
        getDialog().setTitle("البحث في فواتير المبيعات");
        final TextView Search = (TextView)form.findViewById(R.id.tv_Search)  ;
        DocType = getArguments().getString("doctype") ;
        items_Lsit=(ListView) form.findViewById(R.id.listView2);
        cls_search_pos_list  = new ArrayList<cls_Search_po>();
        cls_search_pos_list.clear();
        FillList("");
        Search.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Search.setText("", TextView.BufferType.EDITABLE);
                }
            }

        });


        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                //   if(s.toString().length()>0)
                FillList(s.toString());


            }


        });







        items_Lsit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                cls_Search_po po_obj = (cls_Search_po) arg0.getItemAtPosition(position);
                String nm = po_obj.getCustNo();

                if (getArguments().getString("Scr") == "Sal_inv") {

                    ((Sale_InvoiceActivity) getActivity()).Set_Order(po_obj.getCustNo().toString());
                    Exist_Pop();
                } else if (getArguments().getString("Scr") == "RetnQty") {
                    ((CustomerReturnQtyActivity) getActivity()).Set_Order_From_invoice(po_obj.getCustNo().toString());
                    Exist_Pop();
                } else if (getArguments().getString("Scr") == "Edite_inv") {
                    ((EditeTransActivity) getActivity()).Set_Order_Sal_Inv(po_obj.getCustNo().toString());
                    Exist_Pop();
                }else if (getArguments().getString("Scr") == "POS") {
                    ((Pos_Activity) getActivity()).Set_Order(po_obj.getCustNo().toString());
                    Exist_Pop();
                }else if (getArguments().getString("Scr") == "POS_ele") {
                    ((Pos_Ele_Activity) getActivity()).Set_Order(po_obj.getCustNo().toString());
                    Exist_Pop();
                }


            }


        });
        this.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return  form;
    }


     private void FillList(String Filter){
         String q ="";

         SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
         cls_search_pos_list.clear();
         items_Lsit.setAdapter(null);
         if (Filter.length()==0) {
             q = "Select distinct ifnull(OrderDesc,'') as OrderDesc,  s.Net_Total, s.OrderNo ,s.acc ,s.date , s.inovice_type,    s.Nm  as  name   " +
                     "from  Sal_invoice_Hdr s      where       UserID='"+sharedPreferences.getString("UserID", "")+"'";
         }else {
             q = "Select  distinct ifnull(OrderDesc,'') as OrderDesc, s.Net_Total ,s.OrderNo ,s.acc ,s.date , s.inovice_type,    s.Nm   as  name   from  Sal_invoice_Hdr s  " +
                     " Where      UserID='"+sharedPreferences.getString("UserID","") +"'" +
                     " and s.Nm like '%" + Filter + "%'   or   s.acc  like '%" + Filter + "%'  or  s.OrderNo     like '%" + Filter + "%'";
         }

         SqlHandler sqlHandler = new SqlHandler(getActivity());
         Cursor c1 = sqlHandler.selectQuery(q);

         if (c1 != null && c1.getCount() != 0) {
             if (c1.moveToFirst()) {
                 do {
                     cls_Search_po cls_searchpos= new cls_Search_po();
                     cls_searchpos.setCustNo(c1.getString(c1.getColumnIndex("OrderNo")));
                     cls_searchpos.setCustNm(c1.getString(c1.getColumnIndex("name")));
                     cls_searchpos.setDate(c1.getString(c1.getColumnIndex("date")));
                     cls_searchpos.setAcc(c1.getString(c1.getColumnIndex("acc")));
                     cls_searchpos.setNotes(c1.getString(c1.getColumnIndex("OrderDesc")));
                     cls_searchpos.setType(c1.getString(c1.getColumnIndex("inovice_type")));
                     cls_searchpos.setTot(c1.getString(c1.getColumnIndex("Net_Total")));
                     cls_search_pos_list.add(cls_searchpos);
                 }while (c1.moveToNext());
             }
         }
         c1.close();


         TextView count = (TextView)form.findViewById(R.id.tv_Count);
         count.setText(  cls_search_pos_list.size() +"");
         cls_Search_Inv_POS_adapter cls_Search_invSale_adapter_obj = new cls_Search_Inv_POS_adapter(
                 this.getActivity(),cls_search_pos_list);


         items_Lsit.setAdapter(cls_Search_invSale_adapter_obj);
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
            //   ((OrdersItems)getActivity()).Save_Method("maen");


        }


    }


    public void onListItemClick(ListView l, View v, int position, long id) {



        items_Lsit.setItemChecked(position, true);
        v.setBackgroundColor(Color.BLUE);



    }

}
