package com.cds_jo.GalaxySalesApp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class Pop_cubic_meter extends DialogFragment  {

    private static final String TAG = "MyCustomDialog";
    public OnInputSelected mOnInputSelected;
    public interface OnInputSelected{
        void sendInput(String input);
    }



    SqlHandler sqlHandler;
    EditText et_Input,et_Input1,et_Input2,et_Input3;
    Button Accept;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pop_cubic_meter, container, false);
        et_Input =(EditText)v.findViewById(R.id.et_Input);
        et_Input1 =(EditText)v.findViewById(R.id.et_Input1);
        et_Input2 =(EditText)v.findViewById(R.id.et_Input2);
        et_Input3 =(EditText)v.findViewById(R.id.et_Input3);
        Accept =(Button) v.findViewById(R.id.Accept);
        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlHandler = new SqlHandler(getActivity());
               String q = "delete  from AddcubicM where     Item_No ='" + getArguments().getString("ItemNo") + "' and OrderNo ='" + getArguments().getString("OrderNo")+"'";
                sqlHandler.executeQuery(q);
                ContentValues cv = new ContentValues();
                cv.put("height", et_Input.getText().toString());
                cv.put("Width", et_Input1.getText().toString());
                cv.put("fish", et_Input2.getText().toString());
                cv.put("number", et_Input3.getText().toString());
                cv.put("Item_No", getArguments().getString("ItemNo"));
                cv.put("OrderNo",  getArguments().getString("OrderNo"));

                sqlHandler.Insert("AddcubicM", null, cv);
              //  mOnInputSelected = (OnInputSelected) getTargetFragment();
double a=SToD(et_Input.getText().toString())*SToD(et_Input1.getText().toString())*SToD(et_Input2.getText().toString())*SToD(et_Input3.getText().toString());
    String aa=String.valueOf(a);
              try {
                  mOnInputSelected.sendInput(String.valueOf(SToD(aa)));
              }catch (Exception e)
              {

              }

            }
        });
       String SumReturn = DB.GetValue(getActivity(),"AddcubicM","ifnull(count(*) ,0)","Item_No ='" + getArguments().getString("ItemNo") + "' and OrderNo ='" + getArguments().getString("OrderNo") +"'");
       if (SumReturn.equals("0"))
       {

       }
       else
       {
           sqlHandler = new SqlHandler(getActivity());
        String   query = "Select    height,Width,fish,number  " +
                   "from  AddcubicM where   Item_No ='" + getArguments().getString("ItemNo") + "' and OrderNo ='" + getArguments().getString("OrderNo")+"'";//AND   ( ( strftime('%s','"+currentDateandTime+"'   ) - strftime('%s','+Sal_invoice_Hdr.Time+' ))  /60 )   >=  " + M ;
           Cursor c1 = sqlHandler.selectQuery(query);

           if (c1 != null && c1.getCount() != 0) {

               if (c1.moveToFirst()) {
                   do {

                       et_Input.setText(c1.getString(c1.getColumnIndex("height")));
                       et_Input1.setText(c1.getString(c1.getColumnIndex("Width")));
                       et_Input2.setText(c1.getString(c1.getColumnIndex("fish")));
                       et_Input3.setText(c1.getString(c1.getColumnIndex("number")));
                   } while (c1.moveToNext());
               }
               c1.close();
           }
       }

  return v;
    }
    private  Double SToD(String str){
        String f = "";
        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        final DecimalFormat df = (DecimalFormat)nf;
        str = str.replace(",", "");
        Double d = 0.0;
        if (str.length()==0) {
            str = "0";
        }
        if (str.length()>0)
            try {
                d =  Double.parseDouble(str);
                str = df.format(d).replace(",", "");

            }
            catch (Exception ex)
            {
                str="0";
            }

        df.setParseBigDecimal(true);

        d = Double.valueOf(str.trim()).doubleValue();

        return d;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
          //  mOnInputSelected = (OnInputSelected) activity;
            android.app.Fragment fragment = getTargetFragment();
            if (fragment instanceof OnInputSelected) {
                mOnInputSelected = (OnInputSelected) fragment;
        }}catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage() );
        }
    }
}