package com.cds_jo.GalaxySalesApp.warehouse;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.assist.OrdersItems;

import Methdes.MyTextView;

/**
 * Created by Hp on 21/08/2017.
 */


public class Pop_Price_Qty extends DialogFragment implements View.OnClickListener {
    Button Inc, Dec;
    View form;
    EditText tv_qty ;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savestate) {

        form = inflater.inflate(R.layout.update_price, container, false);


        Window window = getDialog().getWindow();
        window.setGravity(Gravity.TOP|Gravity.RIGHT);



        Inc = (Button) form.findViewById(R.id.btn_Increment);
        Inc.setOnClickListener(this);
        Dec = (Button) form.findViewById(R.id.btn_Dicrement);
        Dec.setOnClickListener(this);


        tv_qty = (EditText)form.findViewById(R.id.tv_qty);
        tv_qty.setText(getArguments().getString("Qty"));
        getDialog().setTitle(getArguments().getString("Nm"));

        tv_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(getArguments().getString("Scr").equalsIgnoreCase("ItemRecepit")) {
                    ((ItemsRecepit) getActivity()).UpdatePrice(tv_qty.getText().toString().replaceAll("[^\\d.]", ""));



                }
            }
        });
        return form;
    }
    @Override
    public void onClick(View v) {

        if (v==Inc){
            tv_qty.setText( (Double.parseDouble(tv_qty.getText().toString().replaceAll("[^\\d.]", ""))+1)   +"" );
            if(getArguments().getString("Scr").equalsIgnoreCase("SalesOrder")) {
                ((OrdersItems) getActivity()).UpdateQty(tv_qty.getText().toString().replaceAll("[^\\d.]", ""));

            }   else   if(getArguments().getString("Scr").equalsIgnoreCase("ItemRecepit")) {
                ((ItemsRecepit) getActivity()).UpdatePrice(tv_qty.getText().toString().replaceAll("[^\\d.]", ""));

            }   else   if(getArguments().getString("Scr").equalsIgnoreCase("ItemRecepitQty")) {
                ((ItemsRecepit) getActivity()).UpdateQty(tv_qty.getText().toString().replaceAll("[^\\d.]", ""));
            }
            if( Double.parseDouble( tv_qty.getText().toString().replaceAll("[^\\d.]", "")) > 1 ) {
                Dec.setVisibility(View.VISIBLE);
            }

        }
        if(v==Dec){

            tv_qty.setText( (Double.parseDouble(tv_qty.getText().toString().replaceAll("[^\\d.]", ""))-1)   +"" );
            if( Double.parseDouble( tv_qty.getText().toString().replaceAll("[^\\d.]", "")) < 1 ) {
                tv_qty.setText("1");
                Dec.setVisibility(View.INVISIBLE);
            }
            if(getArguments().getString("Scr").equalsIgnoreCase("SalesOrder")) {
                ((OrdersItems) getActivity()).UpdateQty(tv_qty.getText().toString().replaceAll("[^\\d.]", ""));

            }else   if(getArguments().getString("Scr").equalsIgnoreCase("ItemRecepit")) {
                ((ItemsRecepit) getActivity()).UpdatePrice(tv_qty.getText().toString().replaceAll("[^\\d.]", ""));

            }   else   if(getArguments().getString("Scr").equalsIgnoreCase("ItemRecepitQty")) {
                ((ItemsRecepit) getActivity()).UpdateQty(tv_qty.getText().toString().replaceAll("[^\\d.]", ""));
            }
        }
  }
}
