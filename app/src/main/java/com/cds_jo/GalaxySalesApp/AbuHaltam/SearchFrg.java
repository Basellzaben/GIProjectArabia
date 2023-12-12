package com.cds_jo.GalaxySalesApp.AbuHaltam;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFrg  extends DialogFragment {
    cls_get_items_search cls_acc_report ;
    ArrayList<cls_get_items_search> list;
    final Handler _handler = new Handler();
    cls_get_items_search_adabter adapter;
    ListView listView;
    Button back,update;
    EditText search;
    String s;

    @Override
    public void onStart()
    {
        super.onStart();
        if (getDialog() == null)
            return;

        int dialogWidth = WindowManager.LayoutParams.MATCH_PARENT;//340; // specify a value here
        int dialogHeight = WindowManager.LayoutParams.MATCH_PARENT;//400; // specify a value here

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("استعلام عن مادة");
        View view = inflater.inflate(R.layout.fragment_search_frg, container, false);
        int dialogWidth = WindowManager.LayoutParams.MATCH_PARENT;//340; // specify a value here
        int dialogHeight = WindowManager.LayoutParams.MATCH_PARENT;//400; // specify a value here

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        back=(Button) view.findViewById(R.id.back);
        update=(Button) view.findViewById(R.id.update);
        //cls_acc_report =new cls_ACC_Report();
        list =new ArrayList<>();
        search =(EditText)view.findViewById(R.id.search);
        listView=(ListView) view.findViewById(R.id.LstvItems);
        showD();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                showD();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), ProductionlineAct.class);
                getActivity().startActivity(myIntent);
                dismiss();

            }});






        return view;
    }

    private void showD() {
        s = search.getText().toString();

        Thread thread = new Thread() {
            @Override
            public void run() {


                CallWebServices ws = new CallWebServices(getActivity());
                ws.Get_Item(s);
                try {
                    Integer i;

                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray ItemNeme = js.getJSONArray("Item_Name");
                    JSONArray ItemNo= js.getJSONArray("Item_No");

                    cls_acc_report = new cls_get_items_search();
                    for (i = 0; i < ItemNeme.length(); i++) {
                        cls_acc_report = new cls_get_items_search();
                        cls_acc_report.setItemName(ItemNeme.get(i).toString());
                        cls_acc_report.setItemNo(ItemNo.get(i).toString());

                        list.add(cls_acc_report);
                    }
                    _handler.post(new Runnable() {
                        public void run() {

                            adapter = new cls_get_items_search_adabter(getActivity(), list);
                            listView.setAdapter(adapter);
                        }
                    });

                } catch (final Exception e) {

                }

            }
        };
        thread.start();

//    }

    }
    public void ser (View v)
    {
        showD();
    }


}

