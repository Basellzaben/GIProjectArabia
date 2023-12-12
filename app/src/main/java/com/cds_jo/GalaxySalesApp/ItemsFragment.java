package com.cds_jo.GalaxySalesApp;


import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.cds_jo.GalaxySalesApp.assist.CallWebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Hp on 07/02/2016.
 */

public class ItemsFragment extends DialogFragment  {
    View form ;
    ImageButton add,cancel,acc,invoice,rec,order;
    Button back;
    ListView itemlistview,itemlistview2;
    ArrayList<ItemsModel> lista;
    ArrayList<ItemsModelGift> lista2;
    TextView tv;
    LinearLayout backk;
    String orderno,userno;
    Drawable greenProgressbar;
    RelativeLayout.LayoutParams lp;
    String u;
    @Override
    public void onStart()
    {
        super.onStart();

        // safety check
        if (getDialog() == null)
            return;

        //  int dialogWidth =410; // specify a value here
        //  int dialogHeight = 300; // specify a value here


        //  int dialogWidth = 420; // specify a value here
        //  int dialogHeight =WindowManager.LayoutParams.WRAP_CONTENT;
        //  getDialog().getWindow().setLayout(dialogWidth, dialogHeight);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom( getDialog().getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(lp);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setWindowAnimations(0);


    }


    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container,Bundle savestate){

        form =inflater.inflate(R.layout.fragment_items,container,false);
        getDialog().setTitle(getArguments().getString("Msg"));
        backk= (LinearLayout) form.findViewById(R.id.back);
        lista=new ArrayList<>();
        lista2=new ArrayList<>();


        orderno=  getArguments().getString("OrderNo");
        userno=  getArguments().getString("UserNo");

//        Toast.makeText(getActivity(),getArguments().getString("OrderNo"),Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        u =  sharedPreferences.getString("UserID", "");

        itemlistview= (ListView) form.findViewById(R.id.itemlistview);
        itemlistview2= (ListView) form.findViewById(R.id.itemlistviewgift);
        getDialog().setCanceledOnTouchOutside(false);
        getdata(orderno,userno);

        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        itemlistview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if(lista.get(i).getItemKeys().length()<1){
                    Toast.makeText(getActivity(),"لا يوجد بيانات",Toast.LENGTH_SHORT).show();
              return;
                }

                Bundle bundle = new Bundle();
                final android.app.FragmentManager Manager =  getFragmentManager();
                final Keys_fragment obj = new Keys_fragment();
                bundle.putString("list", lista.get(i).getItemKeys());

                obj.setArguments(bundle);

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        obj.show(Manager, null);

                    }
                }, 100);

            }
        });

        return  form;
    }


    public void getdata(final String orderno,final String UserNo){

        lista.clear();
        itemlistview.setAdapter(null);
        tv = new TextView(getActivity());
        lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setLayoutParams(lp);
        tv.setPadding(10, 15, 10, 15);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundColor(Color.BLUE);
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);

        final ProgressDialog progressDialog;
        final Handler _handler = new Handler();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setMessage("  الرجاء الانتظار ..." + "  العمل جاري على نسخ البيانات  ");
        tv.setText("الاصناف");
        progressDialog.setCustomTitle(tv);
        progressDialog.setProgressDrawable(greenProgressbar);
        try {
            progressDialog.show();
        } catch (Exception re) {
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(getActivity());
                ws.Get_Rep_D(orderno,UserNo);
                try {
                    Integer i;
                    String q = "";


                    if (We_Result.ID > 0) {
                        JSONObject  js = new JSONObject(We_Result.Msg);
                        JSONArray SubjectID = js.getJSONArray("SubjectID");
                        JSONArray   SubjectDsec = js.getJSONArray("SubjectDsec");
                        JSONArray   ItemKeys = js.getJSONArray("ItemKeys");

                        for (i = 0; i < SubjectID.length(); i++) {

                            ItemsModel repModel=new ItemsModel();
                            repModel.setSubjectID(SubjectID.get(i).toString());
                            repModel.setSubjectDsec(SubjectDsec.get(i).toString());
                            repModel.setItemKeys(ItemKeys.get(i).toString());
                            lista.add(repModel);


                            progressDialog.setMax(SubjectID.length());
                            progressDialog.incrementProgressBy(1);




                            if (progressDialog.getProgress() == progressDialog.getMax()) {
                                progressDialog.dismiss();
                            }

                            if (!progressDialog.isShowing()) {
                                _handler.post(new Runnable() {
                                    public void run() {
                                        try {
                                            progressDialog.show();
                                        } catch (Exception ex) {
                                        }
                                    }
                                });
                            }

                        }
                        final int total = i;
                        _handler.post(new Runnable() {
                            public void run() {
                                try {
                                    progressDialog.dismiss();
                                    ItemsAdapter reportAdapter = new ItemsAdapter(
                                            getActivity(),R.layout.row_grid, lista);

                                    itemlistview.setAdapter(reportAdapter);
                                    getdatagifts(orderno,userno);

                                } catch (Exception df) {
                                    progressDialog.dismiss();
                                    getdatagifts(orderno,userno);

                                }
                            }
                        });
                    } else {
                        _handler.post(new Runnable() {
                            public void run() {
                                try {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(),"لا يوجد بيانات",Toast.LENGTH_SHORT).show();
                                    getdatagifts(orderno,userno);

                                } catch (Exception df) {
                                    getdatagifts(orderno,userno);

                                }
                            }
                        });
                    }
                } catch (final Exception e) {
                    _handler.post(new Runnable() {
                        public void run() {
                            try {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(),"حدثت مشكلة اثناء جلب البيانات",Toast.LENGTH_SHORT).show();
                                getdatagifts(orderno,userno);

                            } catch (Exception d) {
                                getdatagifts(orderno,userno);
                            }
                        }
                    });
                }
            }
        }).start();
    }


    public void getdatagifts(final String orderno,final String UserNo){

        lista2.clear();
        itemlistview2.setAdapter(null);
        tv = new TextView(getActivity());
        lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setLayoutParams(lp);
        tv.setPadding(10, 15, 10, 15);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundColor(Color.BLUE);
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);

        final ProgressDialog progressDialog;
        final Handler _handler = new Handler();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setMessage("  الرجاء الانتظار ..." + "  العمل جاري على نسخ البيانات  ");
        tv.setText("الهدايا");
        progressDialog.setCustomTitle(tv);
        progressDialog.setProgressDrawable(greenProgressbar);
        try {
            progressDialog.show();
        } catch (Exception re) {
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(getActivity());
                ws.GetManVisitDF(orderno,UserNo);
                try {
                    Integer i;
                    String q = "";


                    if (We_Result.ID > 0) {
                        JSONObject  js = new JSONObject(We_Result.Msg);
                        JSONArray ItemNo = js.getJSONArray("ItemNo");
                        JSONArray   ItemName = js.getJSONArray("ItemName");
                        JSONArray   Unit = js.getJSONArray("Unit");
                        JSONArray   Qty = js.getJSONArray("Qty");

                        for (i = 0; i < ItemNo.length(); i++) {

                            ItemsModelGift repModel=new ItemsModelGift();
                            repModel.setItemNo(ItemNo.get(i).toString());
                            repModel.setItemName(ItemName.get(i).toString());
                            repModel.setUnit(Unit.get(i).toString());
                            repModel.setQty(Qty.get(i).toString());
                            lista2.add(repModel);


                            progressDialog.setMax(ItemNo.length());
                            progressDialog.incrementProgressBy(1);




                            if (progressDialog.getProgress() == progressDialog.getMax()) {
                                progressDialog.dismiss();
                            }

                            if (!progressDialog.isShowing()) {
                                _handler.post(new Runnable() {
                                    public void run() {
                                        try {
                                            progressDialog.show();
                                        } catch (Exception ex) {
                                        }
                                    }
                                });
                            }

                        }
                        final int total = i;
                        _handler.post(new Runnable() {
                            public void run() {
                                try {
                                    progressDialog.dismiss();
                                    ItemsAdaptergift reportAdapter = new ItemsAdaptergift(
                                            getActivity(),R.layout.row_grid, lista2);

                                    itemlistview2.setAdapter(reportAdapter);

                                } catch (Exception df) {
                                    progressDialog.dismiss();

                                }
                            }
                        });
                    } else {
                        _handler.post(new Runnable() {
                            public void run() {
                                try {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(),"لا يوجد بيانات",Toast.LENGTH_SHORT).show();
                                } catch (Exception df) {
                                }
                            }
                        });
                    }
                } catch (final Exception e) {
                    _handler.post(new Runnable() {
                        public void run() {
                            try {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(),"حدثت مشكلة اثناء جلب البيانات",Toast.LENGTH_SHORT).show();
                            } catch (Exception d) {
                            }
                        }
                    });
                }
            }
        }).start();
    }




}


