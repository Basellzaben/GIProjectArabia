
package com.cds_jo.GalaxySalesApp;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.NewPackage.GalaxyNewHomeActivity;
import com.cds_jo.GalaxySalesApp.NewPackage.MainNewActivity;
import com.cds_jo.GalaxySalesApp.TQNew.Acc_ReportActivity1;
import com.cds_jo.GalaxySalesApp.TQNew.OrdersItems1;
import com.cds_jo.GalaxySalesApp.assist.Acc_ReportActivity;
import com.cds_jo.GalaxySalesApp.assist.OrdersItems;
import com.cds_jo.GalaxySalesApp.assist.Sale_InvoiceActivity;
import com.cds_jo.GalaxySalesApp.assist.Sale_ReturnActivity;

import Methdes.MyTextView;


/**
 * Created by Hp on 07/02/2016.
 */

public class PopSmallMenue extends DialogFragment implements View.OnClickListener {
    View form;
    ImageButton add, cancel, acc, invoice, rec, order, return1,sanadsarf, btn_round, returnRequest, jareed, addimage;
    Button back;
    String UserID = "";
    MyTextView tv_Invoice;


    @Override
    public void onStart() {
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
        lp.copyFrom(getDialog().getWindow().getAttributes());
        lp.width = 800;
        lp.height = 600;
        getDialog().getWindow().setAttributes(lp);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setWindowAnimations(0);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savestate) {

        form = inflater.inflate(R.layout.popsmallmenue, container, false);
        getDialog().setTitle(getArguments().getString("Msg"));

        back = (Button) form.findViewById(R.id.button22);
        acc = (ImageButton) form.findViewById(R.id.btn_Acc);
        invoice = (ImageButton) form.findViewById(R.id.btn_invoice);
        rec = (ImageButton) form.findViewById(R.id.btn_rec);
        order = (ImageButton) form.findViewById(R.id.btn_order);
        return1 = (ImageButton) form.findViewById(R.id.btn_return);
        btn_round = (ImageButton) form.findViewById(R.id.btn_round);
        returnRequest = (ImageButton) form.findViewById(R.id.returnRequest);
        jareed = (ImageButton) form.findViewById(R.id.jareed);
        addimage = (ImageButton) form.findViewById(R.id.addimage);
        sanadsarf = (ImageButton) form.findViewById(R.id.sanadsarf);

        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        UserID = sharedPreferences1.getString("UserID", "");
        tv_Invoice = (MyTextView) form.findViewById(R.id.tv_Invoice);


        back.setOnClickListener(this);
        order.setOnClickListener(this);
        acc.setOnClickListener(this);
        return1.setOnClickListener(this);
        sanadsarf.setOnClickListener(this);

        btn_round.setOnClickListener(this);
        returnRequest.setOnClickListener(this);
        jareed.setOnClickListener(this);
        addimage.setOnClickListener(this);

        if (ComInfo.ComNo == 4) {
            invoice.setVisibility(View.INVISIBLE);
            tv_Invoice.setVisibility(View.INVISIBLE);
            invoice.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            tv_Invoice.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
        invoice.setOnClickListener(this);
        rec.setOnClickListener(this);

        getDialog().setCanceledOnTouchOutside(false);

        //   getDialog().getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        return form;
    }


    public void onClick(View v) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        String RepType = sharedPreferences.getString("TypeRep", "-1");
        if (v == back) {
            this.dismiss();
        }

        if (v == invoice) {

            String mANsTATES = DB.GetValue(v.getContext(), "manf", "Stoped", "man = '" + UserID + "'");
            if (mANsTATES.equals("1")) {
                Intent k = new Intent(v.getContext(), Sale_InvoiceActivity.class);
                startActivity(k);
            } else {
                Toast.makeText(v.getContext(), "لا يوجد صلاحيه الرجاء مراجعه المشرف", Toast.LENGTH_LONG).show();
            }

        }

        if (v == acc) {
            Intent k = new Intent(v.getContext(), Acc_ReportActivity.class);
            startActivity(k);
        }

        if (v == return1) {
            Intent k = new Intent(v.getContext(), Sale_ReturnActivity.class);
            startActivity(k);
        }

        if (v == rec) {

            if (ComInfo.ComNo == Companies.Afrah.getValue()) {
                Intent k = new Intent(v.getContext(), Acc_ReportActivity1.class);
                startActivity(k);

            } else {
                Intent k = new Intent(v.getContext(), RecvVoucherActivity.class);
                startActivity(k);
            }
        }

        if (v == order) {
            if (ComInfo.ComNo == Companies.Afrah.getValue()) {
                Intent k = new Intent(v.getContext(), OrdersItems1.class);
                startActivity(k);

            } else {
                Intent k = new Intent(v.getContext(), OrdersItems.class);
                startActivity(k);
            }

        }
        if (v == btn_round) {
            if (RepType.equals("1")) {
                startActivity(new Intent(v.getContext(), MainActivity.class));
            } else {
                startActivity(new Intent(v.getContext(), MainNewActivity.class));
                // startActivity(new Intent(context, testAct.class));
            }

        }
        if (v == returnRequest) {
            Intent intent = new Intent(v.getContext(), returnRequest.class);
            startActivity(intent);

        } if (v == jareed) {
            Intent intent = new Intent(v.getContext(), CustomerQty.class);
            startActivity(intent);

        } if (v == addimage) {
            Intent intent = new Intent(v.getContext(), VisitImges.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            v.getContext().startActivity(intent);

        }
        if (v == sanadsarf) {
            Intent intent = new Intent(v.getContext(), MyRequests.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            v.getContext().startActivity(intent);
        }


    }


}


