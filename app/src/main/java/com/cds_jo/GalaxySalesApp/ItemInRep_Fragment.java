package com.cds_jo.GalaxySalesApp;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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

public class ItemInRep_Fragment extends DialogFragment {
    View form ;
    Button back;
    ListView itemlistview;
    ArrayList<ItemsRepModel> lista;
    TextView tv;
    LinearLayout backk;
    String orderno,userno;
    Drawable greenProgressbar;
    RelativeLayout.LayoutParams lp;
    String u;
    TextView total,dis,tax,net;
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
        total=(TextView)form.findViewById(R.id.totale);
        dis=(TextView)form.findViewById(R.id.dise);
        tax=(TextView)form.findViewById(R.id.taxe);
        net=(TextView)form.findViewById(R.id.nete);

    }


    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container,Bundle savestate){

        form =inflater.inflate(R.layout.fragment_item_in_rep_,container,false);
        getDialog().setTitle(getArguments().getString("Msg"));
        backk= (LinearLayout) form.findViewById(R.id.back);
        lista=new ArrayList<>();


        orderno=  getArguments().getString("OrderNo");


        itemlistview= (ListView) form.findViewById(R.id.itemlistview);
        getDialog().setCanceledOnTouchOutside(false);
        getdata(orderno);

        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        itemlistview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


             /*   if(lista.get(i).getItemKeys().length()<1){
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
                }, 100);*/

            }
        });

        return  form;
    }


    public void getdata(final String orderno){

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
                ws.GetManSalesRep(orderno);
                try {
                    Integer i;
                    String q = "";


                    if (We_Result.ID > 0) {
                        JSONObject js = new JSONObject(We_Result.Msg);
                        JSONArray Item_no = js.getJSONArray("Item_no");
                        JSONArray   Item_Name = js.getJSONArray("Item_Name");
                        JSONArray   price = js.getJSONArray("price");
                        JSONArray   Qty = js.getJSONArray("Qty");
                        JSONArray   UnitName = js.getJSONArray("UnitName");
                        JSONArray   Tax_Amt = js.getJSONArray("Tax_Amt");
                        JSONArray   Dis_Amt = js.getJSONArray("Dis_Amt");
                        JSONArray   Bounce = js.getJSONArray("Bounce");
                        JSONArray   Total = js.getJSONArray("Total");

                        String   taxs = js.getJSONArray("HTTotal").get(0).toString();
                        String   nets = js.getJSONArray("HNTotal").get(0).toString();
                        String   diss = js.getJSONArray("HDTotal").get(0).toString();
                        String   totals = js.getJSONArray("Htotal").get(0).toString();
                        TextView  totall=(TextView)form.findViewById(R.id.totale);
                        TextView  disl=(TextView)form.findViewById(R.id.dise);
                        TextView taxl=(TextView)form.findViewById(R.id.taxe);
                        TextView netl=(TextView)form.findViewById(R.id.nete);
                        taxl.setText(taxs);
                        netl.setText(nets);
                        disl.setText(diss);
                        totall.setText(totals);

                        for (i = 0; i < Item_no.length(); i++) {
                            ItemsRepModel repModel=new ItemsRepModel();
                            repModel.setItem_no(Item_no.get(i).toString());
                            repModel.setItem_Name(Item_Name.get(i).toString());
                            repModel.setPrice(price.get(i).toString());
                            repModel.setQty(Qty.get(i).toString());
                            repModel.setUnitName(UnitName.get(i).toString());
                            repModel.setTax_Amt(Tax_Amt.get(i).toString());
                            repModel.setDis_Amt(Dis_Amt.get(i).toString());
                            repModel.setBounce(Bounce.get(i).toString());
                            repModel.setTotal(Total.get(i).toString());
                            lista.add(repModel);


                            progressDialog.setMax(Item_no.length());
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
                                    RepItem_Adapter reportAdapter = new RepItem_Adapter(
                                            getActivity(),R.layout.row_grid, lista);

                                    itemlistview.setAdapter(reportAdapter);

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
                                Toast.makeText(getActivity(), e.getMessage() +"حدثت مشكلة اثناء جلب البيانات",Toast.LENGTH_SHORT).show();

                            } catch (Exception d) {
                            }
                        }
                    });
                }
            }
        }).start();
    }




}
