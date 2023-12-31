package com.cds_jo.GalaxySalesApp;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.assist.OrdersItems;
import com.cds_jo.GalaxySalesApp.assist.cls_Search_po;
import com.cds_jo.GalaxySalesApp.assist.cls_Search_po_adapter;

import java.util.ArrayList;

public class SearchPoActivity extends DialogFragment implements View.OnClickListener  {
    View form ;
    Button add,cancel;
    ListView items_Lsit;
     TextView editText4;
    @Override
    public View onCreateView( final LayoutInflater inflater   , ViewGroup container  ,Bundle savestate){
        form =inflater.inflate(R.layout.activity_search_po,container,false);
        getDialog().setTitle("Galaxy International Group");
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //itemnm = (TextView)form.findViewById(R.id.ed_item_nm);
         items_Lsit=(ListView) form.findViewById(R.id.listView2);
        editText4 =(TextView)form.findViewById(R.id.editText4);
        editText4.clearFocus();
        ArrayList<cls_Search_po> cls_search_pos_list  = new ArrayList<cls_Search_po>();
        cls_search_pos_list.clear();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String q;
        if(sharedPreferences.getString("CustNo", "").equals(""))
        {
             q = "Select distinct po.orderno,po.date    , c.name  , po.acc from Po_Hdr po Left join Customers c on c.no = po.acc where  Man_Order = '" + sharedPreferences.getString("UserID", "")+"'";
        }
        else
        {
             q = "Select distinct po.orderno,po.date    , c.name  , po.acc from Po_Hdr po Left join Customers c on c.no = po.acc where  Man_Order = '" + sharedPreferences.getString("UserID", "") + "' and po.acc ='" + sharedPreferences.getString("CustNo", "-1") + "'";

        }
            SqlHandler sqlHandler = new SqlHandler(getActivity());
        Cursor c1 = sqlHandler.selectQuery(q);

        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    cls_Search_po cls_searchpos= new cls_Search_po();

                    cls_searchpos.setCustNo(c1.getString(c1.getColumnIndex("orderno")));
                    cls_searchpos.setCustNm(c1.getString(c1.getColumnIndex("name")));
                    cls_searchpos.setDate(c1.getString(c1.getColumnIndex("date")));
                    cls_searchpos.setAcc(c1.getString(c1.getColumnIndex("acc")));
                    cls_searchpos.setNotes("Notes");
                    cls_search_pos_list.add(cls_searchpos);
                }while (c1.moveToNext());
            }
            c1.close();
        }



        cls_Search_po_adapter cls_search_po_adapter = new cls_Search_po_adapter(
                this.getActivity(),cls_search_pos_list);
        items_Lsit.setAdapter(cls_search_po_adapter);






        items_Lsit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                /*arg1.setBackgroundColor(Color.GREEN);
                Object o = items_Lsit.getItemAtPosition(position);
                String str=(String)o;//As you are using Default String Adapter*/
                cls_Search_po po_obj = (cls_Search_po) arg0.getItemAtPosition(position);
                String nm = po_obj.getCustNo();

              //  Toast.makeText(getActivity(), nm, Toast.LENGTH_LONG).show();

                ((OrdersItems)getActivity()).Set_Order(po_obj.getCustNo(), po_obj.getCustNm(),po_obj.getAcc());
                 Exist_Pop();

            }


        });
        this.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return  form;
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
            /*Toast.makeText(getActivity(),
                    "Your Message", Toast.LENGTH_SHORT).show();*/
            //   ((OrdersItems)getActivity()).Save_Method("maen");


        }


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
