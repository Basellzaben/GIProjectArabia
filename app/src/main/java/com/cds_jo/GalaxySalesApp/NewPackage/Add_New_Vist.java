package com.cds_jo.GalaxySalesApp.NewPackage;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Add_New_Vist extends DialogFragment {
    View form ;
    SqlHandler sqlHandler;
    TextView types;
    EditText edittext;
    Calendar myCalendar;
String datetosave="";
    EditText textView;
    int hour,min;

    @Override
    public void onStart()
    {
        super.onStart();
        if (getDialog() == null)
            return;

        int dialogWidth = 600; // specify a value here
        int dialogHeight = 700;//WindowManager.LayoutParams.WRAP_CONTENT;//400; // specify a value here

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        form= inflater.inflate(R.layout.fragment_add__new__vist, container, false);
        textView= (EditText) form.findViewById(R.id.time);
        edittext= (EditText) form.findViewById(R.id.date);
        String IsNew= DB.GetValue(getActivity(),"NewVisit","count(*)","OrderNo ='"+getArguments().getString("OrderNo")+"'");
        if (Integer.parseInt(IsNew)>0) {
            String date=DB.GetValue(getActivity(),"NewVisit","Date","OrderNo ='"+getArguments().getString("OrderNo")+"'");
            String Time=DB.GetValue(getActivity(),"NewVisit","Time","OrderNo ='"+getArguments().getString("OrderNo")+"'");
            edittext.setText(date);
            textView.setText(Time);
        }
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




        Calendar mcurrentTime = Calendar.getInstance();
        hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        min = mcurrentTime.get(Calendar.MINUTE);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTime(hour,min);
            }
        });


        final Button addvist=(Button)form.findViewById(R.id.addvist);
        final Button deletevisit=(Button)form.findViewById(R.id.deletevisit);
        deletevisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqlHandler sql_Handler;
                sql_Handler = new SqlHandler(getActivity());
                String q = "Delete from  NewVisit where OrderNo ='" + getArguments().getString("OrderNo") + "'";
                sql_Handler.executeQuery(q);
                Toast.makeText(getActivity(),String.valueOf(getResources().getString(R.string.DeleteCompleteSuccsully)),Toast.LENGTH_LONG).show();
                edittext.setText("");
                textView.setText("");
                dismiss();
            }
        });
        addvist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edittext.getText().toString().equals("")||textView.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), String.valueOf(getResources().getString(R.string.visiteerrorinformation)), Toast.LENGTH_LONG).show();
                }else {
                    addvist();
                    dismiss();
                }
            }
        });
        return form;
    }


    public void addvist(){
        SqlHandler sql_Handler;
        sql_Handler = new SqlHandler(getActivity());
        //   String transno=getArguments().getString("transno");
/*

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("dateofvist",edittext.getText().toString());
        editor.putString("timeofvist",textView.getText().toString());
        editor.apply();

        Toast.makeText(getActivity(),String.valueOf(getResources().getString(R.string.AddCompleteSucc)),Toast.LENGTH_LONG).show();
        edittext.setText("");
        textView.setText("");
*/
        String[] Time;
        String Time1;
        Time=textView.getText().toString().split(":");
        if(Integer.parseInt(Time[0])<12)
        {
            Time1="1";
        }
        else
        {
            Time1="2";
        }
        String IsNew=DB.GetValue(getActivity(),"NewVisit","count(*)","OrderNo ='"+getArguments().getString("OrderNo")+"'");
        Long i;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String Tr_CustNo = preferences.getString("CustNo", "");
        String Tr_Area = preferences.getString("AreaNo", "");
        ContentValues cv = new ContentValues();
        cv.put("OrderNo", getArguments().getString("OrderNo"));
        cv.put("Date", edittext.getText().toString());
        cv.put("DatetoSave", datetosave);
        cv.put("CustNo", Tr_CustNo);
        cv.put("Period", Time1);
        cv.put("AreaNo", Tr_Area);
        cv.put("Post","0");
        cv.put("Time", textView.getText().toString());
        if (Integer.parseInt(IsNew)>0) {
            i = sql_Handler.Update("NewVisit", cv, "OrderNo='" + getArguments().getString("OrderNo") + "'");

            Toast.makeText(getActivity(),String.valueOf(getResources().getString(R.string.AddCompleteSucc) + i.toString() + "  "+ getArguments().getString("OrderNo")),Toast.LENGTH_LONG).show();

        } else {
            i = sql_Handler.Insert("NewVisit", null, cv);
          //  Toast.makeText(getActivity(),String.valueOf(getResources().getString(R.string.AddCompleteSucc)),Toast.LENGTH_LONG).show();
            Toast.makeText(getActivity(),String.valueOf(getResources().getString(R.string.AddCompleteSucc) + i.toString() + "  "+ getArguments().getString("OrderNo")),Toast.LENGTH_LONG).show();

        }
    }
    private void updateLabel() {
        String myFormat = "yyyy/MMM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        datetosave=sdf.format(myCalendar.getTime());

        myFormat = "dd/MM/yyyy"; //In which you need put here
         sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }



    void showTime(int hours,  int minte){
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                textView.setText( selectedHour + ":" + selectedMinute);
                hour=selectedHour;
                min=selectedMinute;
            }
        }, hours, minte, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

}
