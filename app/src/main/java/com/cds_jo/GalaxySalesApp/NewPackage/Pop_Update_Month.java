package com.cds_jo.GalaxySalesApp.NewPackage;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;


import com.cds_jo.GalaxySalesApp.R;

import Methdes.MyTextView;




 public class Pop_Update_Month extends DialogFragment implements View.OnClickListener {
        Button Inc, Dec;
        View form;
        MyTextView tv_qty ;
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savestate) {

        form = inflater.inflate(R.layout.fragment_pop__update__month, container, false);


        Window window = getDialog().getWindow();
        window.setGravity(Gravity.TOP| Gravity.CENTER);



        Inc = (Button) form.findViewById(R.id.btn_Increment);
        Inc.setOnClickListener(this);
        Dec = (Button) form.findViewById(R.id.btn_Dicrement);
        Dec.setOnClickListener(this);


        tv_qty = (MyTextView)form.findViewById(R.id.tv_qty);
        tv_qty.setText(getArguments().getString("Month"));
        getDialog().setTitle(getArguments().getString("Nm"));
        if( Integer.parseInt( tv_qty.getText().toString()) >11 ) {
        Inc.setVisibility(View.INVISIBLE);
        }
        return form;
        }
@Override
public void onClick(View v) {

        if (v==Inc){
        tv_qty.setText( (Integer.parseInt(tv_qty.getText().toString())+1)   +"" );

        ((MonthlySalesManSchedule) getActivity()).UpdateMonth(tv_qty.getText().toString() );
        if( Integer.parseInt( tv_qty.getText().toString()) > 1 ) {
        Dec.setVisibility(View.VISIBLE);
        }

        if( Integer.parseInt( tv_qty.getText().toString()) > 11 ) {
        Inc.setVisibility(View.INVISIBLE);
        }

        }
        if(v==Dec){

        tv_qty.setText( (Integer.parseInt(tv_qty.getText().toString())-1)   +"" );
        if( Integer.parseInt( tv_qty.getText().toString()) < 1 ) {
        tv_qty.setText("1");
        Dec.setVisibility(View.INVISIBLE);
        }
        if( Integer.parseInt( tv_qty.getText().toString()) < 12 ) {
        Inc.setVisibility(View.VISIBLE);
        }
        ((MonthlySalesManSchedule) getActivity()).UpdateMonth(tv_qty.getText().toString());
        }
        }
        }
