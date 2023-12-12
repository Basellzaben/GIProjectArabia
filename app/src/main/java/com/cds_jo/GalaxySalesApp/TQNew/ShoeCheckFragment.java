package com.cds_jo.GalaxySalesApp.TQNew;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ShoeCheckFragment extends DialogFragment {
    View form;
    Double sum=0.0;

    check_Adapter check_Adapter;
    ArrayList<check_Model> PaymentssList  ;
    check_Model  obj;
    ListView checklist;
    Context c;
    TextView sumcheck;
    TextView Balance;
    Button back;
    @Override
    public void onStart()
    {
        super.onStart();


        if (getDialog() == null)
            return;


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom( getDialog().getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(lp);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setWindowAnimations(0);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savestate) {

        form = inflater.inflate(R.layout.check_show_fragment, container, false);


        Window window = getDialog().getWindow();
        window.setGravity(Gravity.TOP| Gravity.CENTER);
        checklist= (ListView) form.findViewById(R.id.checklist);


        c=getActivity();
        GetDataFromServer(getArguments().getString("accno"));

        back=(Button) form.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

        return form;
    }


    public void GetDataFromServer(final String accno){

        sum=0.0;
        PaymentssList  = new ArrayList<check_Model>();
        sumcheck=(TextView)form.findViewById(R.id.sumcheck);
        Balance=(TextView)form.findViewById(R.id.Balance);
        final Handler _handler = new Handler();

        final ProgressDialog custDialog = new ProgressDialog(c);
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
                CallWebServices ws = new CallWebServices(getActivity().getApplicationContext());
                ws.GetAcc_Check(accno);
                try {
                    Integer i;
                    String q = "";
                    if (We_Result.ID > 0) {
                        JSONObject js = new JSONObject(We_Result.Msg);
                        JSONArray CustAccount = js.getJSONArray("CustAccount");
                        JSONArray CheckNumber = js.getJSONArray("CheckNumber");
                        JSONArray MaturityDate = js.getJSONArray("MaturityDate");
                        JSONArray AmountCurCredit = js.getJSONArray("AmountCurCredit");

                        PaymentssList.clear();
                        for (i = 0; i < CustAccount.length(); i++) {
                            obj = new check_Model();

                            obj.setCustAccount(CustAccount.get(i).toString());
                            obj.setCheckNumber(CheckNumber.get(i).toString());
                            obj.setMaturityDate(MaturityDate.get(i).toString());
                            obj.setAmountCurCredit(AmountCurCredit.get(i).toString());

                            PaymentssList.add(obj);
                            sum+=Double.valueOf(AmountCurCredit.get(i).toString());


                            custDialog.setMax(CustAccount.length());
                            custDialog.incrementProgressBy(1);

                            if (custDialog.getProgress() == custDialog.getMax()) {
                                custDialog.dismiss();
                            }

                        }

                        _handler.post(new Runnable() {
                            public void run() {
                                try {


                                    check_Adapter = new check_Adapter(getActivity().getApplicationContext(), R.layout.check_row, PaymentssList);
                                    checklist= (ListView) form.findViewById(R.id.checklist);

                                    checklist.setAdapter(check_Adapter);


                                    sumcheck.setText(String.valueOf(sum));
                                    Balance.setText(String.valueOf(sum+SToD(getArguments().getString("Balance"))));

                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            getActivity().getApplicationContext()).create();
                                    alertDialog.setTitle("عرض الشيكات");
                                    alertDialog.setMessage("تمت عملية استرجاع  البيانات بنجاح ");
                                    alertDialog.setIcon(R.drawable.tick);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    custDialog.dismiss();
                                }catch (Exception df){}
                            }
                        });

                    }else {
                        _handler.post(new Runnable() {
                            public void run() {
                                try {
                                    custDialog.dismiss();
                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            getActivity().getApplicationContext()).create();
                                    alertDialog.setTitle("عرض الشيكات");

                                    alertDialog.setMessage("لا يوجد بيانات ");
                                    alertDialog.setIcon(R.drawable.error_new);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    alertDialog.show();
                                }catch (Exception ee){}
                            }});
                    }
                } catch (final Exception e) {
                    custDialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {
                            try {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        getActivity().getApplicationContext()).create();
                                alertDialog.setTitle("عرض الشيكات");

                                if (We_Result.ID == -404) {
                                    alertDialog.setMessage(We_Result.Msg);
                                } else {
                                    alertDialog.setMessage("لا يوجد بيانات");
                                }
                                alertDialog.setIcon(R.drawable.info);

                                alertDialog.setButton("رجــــوع", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                alertDialog.show();

                            }catch (Exception sf){}
                        }
                    });
                }
            }
        }).start();





    }
    public   Double SToD(String str){
        String f = "";
        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        final DecimalFormat df = (DecimalFormat)nf;
        str = str.replace(",","");
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
    private  void ShowList( ){
        // tv_Summatin.setText(sum +"");
        //count.setText((PaymentssList.size()) +"");








    }


}



