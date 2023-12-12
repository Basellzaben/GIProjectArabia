package com.cds_jo.GalaxySalesApp;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.Cls_DoctorReport;
import com.cds_jo.GalaxySalesApp.Cls_SearchDoctorReport_Adapter;
import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.EditeTransActivity;
import com.cds_jo.GalaxySalesApp.DoctorReportActivity;
import com.cds_jo.GalaxySalesApp.NewPackage.GloblaVar;
import com.cds_jo.GalaxySalesApp.NewPackage.LocaleHelper;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;

import java.util.ArrayList;

public class DoctorReportSearchActivity extends DialogFragment implements View.OnClickListener {
    View form;
    Button add, cancel;
    ListView items_Lsit;
    // TextView itemnm;
    ArrayList<Cls_DoctorReport> cls_search_pos_list;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savestate) {
        form = inflater.inflate(R.layout.activity_rec_voucher_search, container, false);

        FillList("");
        EditText filter = (EditText) form.findViewById(R.id.et_Search_filter);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }
            @Override
            public void afterTextChanged(Editable s) {

                FillList(s.toString());

            }
        });


        items_Lsit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Cls_DoctorReport po_obj = (Cls_DoctorReport) arg0.getItemAtPosition(position);
                String nm = po_obj.getCustName();

                if (getArguments().getString("Scr") == "DoctorReport") {
                    ((DoctorReportActivity) getActivity()).Set_Order(po_obj.getNo());
                 //   getsubject(po_obj.getNo());
                } else if (getArguments().getString("Scr") == "EditeRec") {
                    ((EditeTransActivity) getActivity()).Set_Order(po_obj.getNo());
                }  else if (getArguments().getString("Scr") == "Exchange_voucher") {
                ((Exchange_voucher) getActivity()).Set_Order(po_obj.getNo());
            }

                Exist_Pop();

            }


        });
        this.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

//        ((DoctorReportActivity) getActivity()).returntransno();

        return form;
    }

    private void FillList(String s) {
        cls_search_pos_list = new ArrayList<Cls_DoctorReport>();
        cls_search_pos_list.clear();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String u = sharedPreferences.getString("UserID", "");
        String q="";
        if (getArguments().getString("Scr") == "DoctorReport")
         q = "Select distinct   dr.OrderNo,dr.CustNo,dr.VNotes,dr.Tr_Date,dr.CustNm " +
                "from DoctorReport  dr where  dr.UserNo ='" + u.toString() + "' And ifnull(dr.CustNm,'') like  '%" + s.toString() + "%' ";
       else
            q = "Select * from Exchange_voucherHDR";

        SqlHandler sqlHandler = new SqlHandler(getActivity());
        Cursor c1 = sqlHandler.selectQuery(q);

        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {

                    Cls_DoctorReport cls_searchRecVou = new Cls_DoctorReport();

                    if (getArguments().getString("Scr") == "DoctorReport"){

                        cls_searchRecVou.setNo(c1.getString(c1.getColumnIndex("OrderNo")));
                    cls_searchRecVou.setCustNo(c1.getString(c1.getColumnIndex("CustNo")));
                    cls_searchRecVou.setVNotes(c1.getString(c1.getColumnIndex("VNotes")));
                    cls_searchRecVou.setTr_Date(c1.getString(c1.getColumnIndex("Tr_Date")));
                    cls_searchRecVou.setCustName(c1.getString(c1.getColumnIndex("CustNm")));
                    }else if (getArguments().getString("Scr") == "Exchange_voucher"){
                        cls_searchRecVou.setNo(c1.getString(c1.getColumnIndex("no")));
                        cls_searchRecVou.setCustNo("");
                        cls_searchRecVou.setVNotes("");
                        cls_searchRecVou.setTr_Date("");
                        cls_searchRecVou.setCustName("");
                    }
                    cls_search_pos_list.add(cls_searchRecVou);


                } while (c1.moveToNext());
            }

            c1.close();
        }

        Cls_SearchDoctorReport_Adapter Adapter = new Cls_SearchDoctorReport_Adapter(
                this.getActivity(), cls_search_pos_list);

        items_Lsit = (ListView) form.findViewById(R.id.listView2);
        items_Lsit.setAdapter(Adapter);


    }

    private void getsubject(String id) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String u = sharedPreferences.getString("UserID", "");


        String q = "Select ItemNo " +
                "from VisitSubjects  where  OrderNo ='"+ id +"'";
        SqlHandler sqlHandler = new SqlHandler(getActivity());
        Cursor c1 = sqlHandler.selectQuery(q);

        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    //     String iditem=
                    GloblaVar.exixit="1";
                    LocaleHelper localeHelper=new LocaleHelper();
                    if(localeHelper.getlanguage(getActivity()).equals("ar")){
                        String nameitem= DB.GetValue(getActivity(),"itemsn","ItemNameA","Id ='"+
                                c1.getString(c1.getColumnIndex("ItemNo")) +"'");
                        //  cls_searchRecVou.setNo(c1.getString(c1.getColumnIndex("OrderNo")));
                        ((DoctorReportActivity) getActivity()).SaveSubject( c1.getString(c1.getColumnIndex("ItemNo")), nameitem );

                    }else {
                        String nameitem = DB.GetValue(getActivity(), "itemsn", "ItemNameE", "Id ='" +
                                c1.getString(c1.getColumnIndex("ItemNo")) + "'");
                        //  cls_searchRecVou.setNo(c1.getString(c1.getColumnIndex("OrderNo")));
                        ((DoctorReportActivity) getActivity()).SaveSubject(c1.getString(c1.getColumnIndex("ItemNo")), nameitem);


                    }
                } while (c1.moveToNext());
            }

            c1.close();
        }



    }


    public void Exist_Pop() {
        this.dismiss();
    }

    @Override
    public void onClick(View v) {
        Button bu = (Button) v;
        if (bu.getText().toString().equals("Cancel")) {
            this.dismiss();
        } else if (bu.getText().toString().equals("Add")) {
            Toast.makeText(getActivity(),
                    "Your Message", Toast.LENGTH_SHORT).show();
        }
    }

    public void onListItemClick(ListView l, View v, int position, long id) {


        items_Lsit.setItemChecked(position, true);
        v.setBackgroundColor(Color.BLUE);


    }

}