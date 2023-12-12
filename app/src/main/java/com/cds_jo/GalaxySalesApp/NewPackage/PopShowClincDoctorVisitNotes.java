package com.cds_jo.GalaxySalesApp.NewPackage;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import Methdes.MyTextView;


public class PopShowClincDoctorVisitNotes extends DialogFragment implements View.OnClickListener  {
    View form ;
    ImageButton add,cancel,acc,invoice,rec,order;
    Button back,Getdate;
    String UserID,ClincNo;
    SqlHandler sqlHandler;
    ListView Lst1,Lst2,Lst3 ;
    ArrayList<Cls_ClincVisitsNotes> ItemsList1 ;

    String   CustNo,CustNm;
    @Override
    public void onStart()
    {
        super.onStart();


        if (getDialog() == null)
            return;


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom( getDialog().getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(lp);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setWindowAnimations(0);

    }


    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container,Bundle savestate){
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        form =inflater.inflate(R.layout.fragment_pop_show_clinc_doctor_visit_notes,container,false);

        ItemsList1 = new ArrayList<Cls_ClincVisitsNotes>();

        back = (Button)form.findViewById(R.id.back);
        Getdate = (Button)form.findViewById(R.id.Getdate);
        Lst1 = (ListView)form.findViewById(R.id.Lst1);
        MyTextView Title = (MyTextView)form.findViewById(R.id.Title);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        UserID = sharedPreferences.getString("UserID", "-1");

        back.setOnClickListener(this);
        Getdate.setOnClickListener(this);


        ClincNo =   getArguments().getString("ClincNo");

        Title.setText( getArguments().getString("CustName"));
        FillList1( );

        getDialog().getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        return  form;
    }
    private  void FillList1( ){
        ItemsList1.clear();
        final Handler _handler = new Handler();

        final ProgressDialog custDialog = new ProgressDialog(getActivity());

        custDialog.setTitle(getResources().getText(R.string.PleaseWait));
        custDialog.setMessage(getResources().getText(R.string.Retrive_DataUnderProcess));
        custDialog.setProgressStyle(custDialog.STYLE_HORIZONTAL);
        custDialog.setCanceledOnTouchOutside(false);
        custDialog.setProgress(0);
        custDialog.setMax(100);
        custDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {

                CallWebServices ws = new CallWebServices(getActivity());
                ws.Get_ClincNotes(ClincNo,UserID );
                try {
                    Integer i;

                    if (We_Result.Msg.length()>2) {
                        JSONObject js = new JSONObject(We_Result.Msg);


                        JSONArray js_Clinc_No = js.getJSONArray("Clinc_No");
                        JSONArray js_ClincName= js.getJSONArray("ClincName");
                        JSONArray js_Notes = js.getJSONArray("Notes");
                        JSONArray js_Tr_Date = js.getJSONArray("Tr_Date");
                        JSONArray js_ManName = js.getJSONArray("ManName");

                        Cls_ClincVisitsNotes obj ;


                        for (i = 0; i < js_Clinc_No.length(); i++) {
                            obj = new Cls_ClincVisitsNotes();
                            obj.setNotes(js_Notes.get(i).toString());
                            obj.setTr_Dates(js_Tr_Date.get(i).toString());
                            obj.setUserID(js_ManName.get(i).toString());
                            obj.setClincName(js_ClincName.get(i).toString());



                            ItemsList1.add(obj);

                            custDialog.setMax(js_Clinc_No.length());
                            custDialog.incrementProgressBy(1);

                            if (custDialog.getProgress() == custDialog.getMax()) {
                                custDialog.dismiss();
                            }

                        }


                        _handler.post(new Runnable() {

                            public void run() {
                                ClincVisitNotes_Adapter cls_unitItems_adapter = new ClincVisitNotes_Adapter(
                                        getActivity(), ItemsList1);
                                Lst1.setAdapter(cls_unitItems_adapter);
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        getActivity() ).create();
                                alertDialog.setTitle("تحديث البيانات");

                                alertDialog.setMessage("تمت عملية استرجاع  البيانات بنجاح ");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                // alertDialog.show();

                                custDialog.dismiss();
                            }
                        });
                    }else {
                        custDialog.dismiss();
                    }
                } catch (final Exception e) {
                    custDialog.dismiss();
                    _handler.post(new Runnable() {

                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    getActivity()).create();
                            alertDialog.setTitle("ملاحظات زيارة الطبيب");
                            if (We_Result.ID == -404) {
                                alertDialog.setMessage(We_Result.Msg);
                            } else {
                                alertDialog.setMessage("لا يوجد بيانات" );
                            }
                            alertDialog.setIcon(R.drawable.info);

                            alertDialog.setButton(String.valueOf(getResources().getString(R.string.back)), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialog.show();

                        }
                    });
                }


            }
        }).start();

    }

    public void onClick(View v) {
        if (v == back) {
            this.dismiss();
        } if (v == Getdate) {
            FillList1();
        }

    }


}


