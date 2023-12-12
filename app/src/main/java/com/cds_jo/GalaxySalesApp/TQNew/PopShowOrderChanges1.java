package com.cds_jo.GalaxySalesApp.TQNew;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import Methdes.MyTextView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PopShowOrderChanges1 extends DialogFragment implements View.OnClickListener {
    View form;
    long PostID;
    ProgressDialog progressDialog;
    ImageButton add, cancel, acc, invoice, rec, order;
    Button back;
    SqlHandler sqlHandler;
    ListView Lst1, Lst2, Lst3;
    ArrayList<ContactListItems1> ItemsList;
    ContactListItems1 obj;
    Drawable greenProgressbar;
    RelativeLayout.LayoutParams lp;
    TextView tv;
    String GroupNo = "";
    String q;
    String UserID;
    String OrderNo, OrderEmp, Posted;
    MyTextView Tital;

    @Override
    public void onStart() {
        super.onStart();


        if (getDialog() == null)
            return;


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getDialog().getWindow().getAttributes());
        lp.width =  WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(lp);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setWindowAnimations(0);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savestate) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        form = inflater.inflate(R.layout.show_order_changes1, container, false);
        getDialog().setTitle(getArguments().getString("CustNm"));
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        UserID = sharedPreferences.getString("UserID", "-1");
        ItemsList = new ArrayList<ContactListItems1>();
        back = (Button) form.findViewById(R.id.btn_back);

        Lst1 = (ListView) form.findViewById(R.id.Lst1);
        Tital = (MyTextView) form.findViewById(R.id.Tital);
        Tital.setText(getArguments().getString("CustNm"));
        //FillList();
        back.setOnClickListener(this);

        OrderNo = getArguments().getString("OrderNo");
        OrderEmp = getArguments().getString("OrderEmp");
        Posted = getArguments().getString("Posted");
        GetDataFromServer();
        getDialog().getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        return form;
    }

    public void GetDataFromServer() {
        final Handler _handler = new Handler();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String UserID = sharedPreferences.getString("UserID", "");
        final ProgressDialog custDialog = new ProgressDialog(getActivity());

        custDialog.setTitle(getResources().getText(R.string.PleaseWait));
        custDialog.setMessage(
                getResources().getText(R.string.Retrive_DataUnderProcess));
        custDialog.setProgressStyle(custDialog.STYLE_HORIZONTAL);
        custDialog.setCanceledOnTouchOutside(false);
        custDialog.setProgress(0);
        custDialog.setMax(100);
        //custDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(getActivity());
                ws.Get_SalesOrderChanges(OrderEmp, OrderNo);
                try {
                    Integer i;
                    String q = "";
                    if (We_Result.ID > 0) {
                        JSONObject js = new JSONObject(We_Result.Msg);
                        JSONArray js_ID = js.getJSONArray("ID");
                        JSONArray js_Bonus = js.getJSONArray("Bonus");
                        JSONArray js_ItemNo = js.getJSONArray("ItemNo");
                        JSONArray js_ItemNm = js.getJSONArray("ItemNm");
                        JSONArray js_Price = js.getJSONArray("Price");
                        JSONArray js_Qty = js.getJSONArray("Qty");
                        JSONArray jstotal = js.getJSONArray("total");
                        JSONArray jsOldQty = js.getJSONArray("OldQty");
                        JSONArray jsOldBonus = js.getJSONArray("OldBonus");
                        JSONArray jsStutes = js.getJSONArray("Stutes");


                        ItemsList.clear();
                        for (i = 0; i < js_ID.length(); i++) {
                            obj = new ContactListItems1();
                            obj.setno(js_ItemNo.get(i).toString());
                            obj.setQty(js_Qty.get(i).toString());
                            obj.setBounce(js_Bonus.get(i).toString());
                            obj.setName(js_ItemNm.get(i).toString());
                            obj.setTotal(jstotal.get(i).toString());
                            obj.setprice(js_Price.get(i).toString());
                            obj.setOldQty(jsOldQty.get(i).toString());
                            obj.setOldBonus(jsOldBonus.get(i).toString());
                            obj.setStutes(jsStutes.get(i).toString());
                            ItemsList.add(obj);
                            custDialog.setMax(js_ID.length());
                            custDialog.incrementProgressBy(1);
                            if (custDialog.getProgress() == custDialog.getMax()) {
                                custDialog.dismiss();
                            }

                        }

                        _handler.post(new Runnable() {
                            public void run() {
                                FillList();


                                //  custDialog.dismiss();
                            }
                        });

                    } else {
                        _handler.post(new Runnable() {
                            public void run() {
                                try {
                                    custDialog.dismiss();
                                    if (We_Result.ID == -404) {
                                        new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                                .setCustomImage(R.drawable.error_new)
                                                .setContentText("لا يوجد اتصال بالسيرفر")
                                                .show();
                                    }else     {
                                        new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                                .setCustomImage(R.drawable.error_new)
                                                .setContentText("لا يوجد بيانات")
                                                .show();
                                    }


                                }catch (Exception ex){

                                }
                            }
                        });
                    }
                } catch (final Exception e) {
                    custDialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {
                            try {
                                custDialog.dismiss();
                                if (We_Result.ID == -404) {
                                    new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                            .setCustomImage(R.drawable.error_new)
                                            .setContentText("لا يوجد اتصال بالسيرفر")
                                            .show();
                                }else     {
                                    new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                            .setCustomImage(R.drawable.error_new)
                                            .setContentText("لا يوجد بيانات")
                                            .show();
                                }
                            }catch (Exception ex){

                            }


                        }
                    });
                }


            }
        }).start();
    }

    private void FillList() {
        OrderDetialsChanges cls_unitItems_adapter = new OrderDetialsChanges(
                getActivity(), ItemsList);
        Lst1.setAdapter(cls_unitItems_adapter);
    }

    public void onClick(View v) {

        if (v == back) {
            this.dismiss();
        }

    }


    private Double SToD(String str) {
        String f = "";
        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        final DecimalFormat df = (DecimalFormat) nf;
        str = str.replace(",", "");
        Double d = 0.0;
        if (str.length() == 0) {
            str = "0";
        }
        if (str.length() > 0)
            try {
                d = Double.parseDouble(str);
                str = df.format(d).replace(",", "");

            } catch (Exception ex) {
                str = "0";
            }

        df.setParseBigDecimal(true);

        d = Double.valueOf(str.trim()).doubleValue();

        return d;
    }
}