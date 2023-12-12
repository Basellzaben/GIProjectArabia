package com.cds_jo.GalaxySalesApp.TQNew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.cds_jo.GalaxySalesApp.assist.Convert_Prapre_Qty_To_Img;
import com.cds_jo.GalaxySalesApp.assist.ESCPSample3;
import com.cds_jo.GalaxySalesApp.assist.PrintReport_SEWOO_ESCPOS;
import com.cds_jo.GalaxySalesApp.assist.PrintReport_Zepra520;
import com.sewoo.jpos.printer.ESCPOSPrinter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Convert_Prapre_Qty_To_Img1 extends AppCompatActivity {
    SqlHandler sqlHandler;
    ListView lvCustomList;
    private Button mButton;
    private View mView;
    private Button mButton2;
    TextView tv_orderno ;
    TextView tv_Total1 ;
    TextView tv_Disc ;
    TextView tv_TotalTax ;
    String AfterTax,BeforeTax;
    Double       TaxLine4=0.0,TaxLine16=0.0;
    TextView tv_NetTotal ;
    TextView textView13 ;
    TextView textView171 ;
    String Flag;
    LinearLayout items_Lsit;
    NumberFormat nf_out;
    private ESCPOSPrinter posPtr;
    ESCPSample3 obj_print=new ESCPSample3();
    ImageView img_Logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_convert_prapare_qty1);
        img_Logo = (ImageView) findViewById(R.id.img_Logo);
        File imgFile = new  File("//sdcard/Android/Cv_Images/logo.jpg");
        try {
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                img_Logo.setImageBitmap(myBitmap);
            }
        }
        catch (Exception ex){}
        mView = findViewById(R.id.f_view);
        Intent i = getIntent();
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.
                getDefaultAdapter();
        mBluetoothAdapter.disable();
        tv_orderno = (TextView)findViewById(R.id.tv_orderno);
        tv_Total1 = (TextView)findViewById(R.id.tv_Total);
        tv_Disc = (TextView)findViewById(R.id.tv_Disc);
        tv_TotalTax = (TextView)findViewById(R.id.tv_TotalTax);
        tv_NetTotal = (TextView)findViewById(R.id.tv_NetTotal);
        textView13 = (TextView)findViewById(R.id.textView13);
        textView171 = (TextView)findViewById(R.id.textView171);
        if(i.getStringExtra("DocType").toString().equals("2"))
        {
            Flag ="1";
            textView13.setText("فاتورة مبيعات");
            textView171.setText("رقم فاتورة البيع");
        }
        else
        { Flag="2";
            textView13.setText("مرتجع مبيعات");
            textView171.setText("رقم مرتجع البيع");
        }
        tv_orderno.setText( i.getStringExtra("DocNo").toString());
        // tv_Total.setText( i.getStringExtra("Amt").toString());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String u =  sharedPreferences.getString("UserName", "");
        TextView    tv_UserNm  = (TextView)findViewById(R.id.tv_UserNm);
        tv_UserNm.setText(sharedPreferences.getString("CustNm", ""));

        TextView    ed_date  = (TextView)findViewById(R.id.ed_date);

        TextView tv_CompName =(TextView)findViewById(R.id.tv_CompName);
        tv_CompName.setText(sharedPreferences.getString("CompanyNm", ""));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());
        ed_date.setText( i.getStringExtra("Date").toString());



        sqlHandler = new SqlHandler(this);
        showList();
        mBluetoothAdapter.enable();


    }

    private void showList() {
        onProgressUpdate( );


    }
    public void onProgressUpdate( ){


        nf_out = NumberFormat.getNumberInstance(Locale.ENGLISH);
        nf_out.setMaximumFractionDigits(3);
        nf_out.setMinimumFractionDigits(3);






        //    final MyTextView tv_InvoiceDate = (MyTextView)findViewById(R.id.tv_InvoiceDate);

        //  tv_InvoiceDate.setText("");



        final Handler _handler = new Handler();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Convert_Prapre_Qty_To_Img1.this);
        final String UserID = sharedPreferences.getString("UserID", "");
        final ProgressDialog custDialog = new ProgressDialog(Convert_Prapre_Qty_To_Img1.this);

        custDialog.setTitle(getResources().getText(R.string.PleaseWait));
        custDialog.setMessage(getResources().getText(R.string.Retrive_DataUnderProcess));
        custDialog.setProgressStyle(custDialog.STYLE_HORIZONTAL);
        custDialog.setCanceledOnTouchOutside(false);
        custDialog.setProgress(0);
        custDialog.setMax(100);
        //custDialog.show();


        // GetData.setVisibility(View.INVISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {

                CallWebServices ws = new CallWebServices(Convert_Prapre_Qty_To_Img1.this);
                ws.CusInvoiceDtl(getIntent().getStringExtra("DocNo").toString(),Flag);


                try {
                    float t_dept , t_cred,t_bb ,tot ,t_tot,temp,t_rate;
                    t_dept= t_cred=t_bb =tot =t_tot =temp=t_rate =  0 ;
                    Integer i;
                    String q = "";

                    if (We_Result.ID>0) {
                        JSONObject js = new JSONObject(We_Result.Msg);
                        JSONArray js_ITEMID = js.getJSONArray("ITEMID");
                        JSONArray js_NAME = js.getJSONArray("NAME");
                        JSONArray js_SALESQTY = js.getJSONArray("SALESQTY");
                        JSONArray js_SALESPRICE = js.getJSONArray("SALESPRICE");
                        JSONArray js_LINEAMOUNT = js.getJSONArray("LINEAMOUNT");
                        JSONArray js_INVENTBATCHID = js.getJSONArray("INVENTBATCHID");
                        JSONArray js_INVOICEDATE = js.getJSONArray("INVOICEDATE");
                        JSONArray js_INVOICEID = js.getJSONArray("INVOICEID");
                        JSONArray js_BOUNCE = js.getJSONArray("BOUNCE");
                        JSONArray js_TAXCODE = js.getJSONArray("TAXCODE");
                        JSONArray js_TaxLine = js.getJSONArray("TaxLine");
                        JSONArray js_BeforeTax = js.getJSONArray("BeforeTax");
                        JSONArray js_AfterTax = js.getJSONArray("AfterTax");

                        final String INVOICEDATE = js_INVOICEDATE.get(0).toString();
                        final String INVOICEID = js_INVOICEID.get(0).toString();
                        AfterTax = (js_AfterTax.get(0).toString());
                        BeforeTax = (js_BeforeTax.get(0).toString());




                        final ArrayList<Cls_CustLastInvoice> cls_acc_reportsList = new ArrayList<Cls_CustLastInvoice>();

                        Cls_CustLastInvoice cls_acc_report;// = new Cls_CustLastInvoice();


                       /* cls_acc_report.setITEMID("رقم المادة");
                        cls_acc_report.setNAME("اسم المادة");
                        cls_acc_report.setUnit("الوحدة");
                        cls_acc_report.setSALESQTY("الكمية");
                        cls_acc_report.setSALESPRICE("السعر");
                        cls_acc_report.setLINEAMOUNT("المجموع");
                        cls_acc_report.setINVENTBATCHID("الباتش");
                        cls_acc_report.setINVOICEDATE("التاريخ");
                        cls_acc_report.setINVOICEID("رقم الفاتورة" + "");
                        cls_acc_report.setBOUNCE("البونص" + "");


                        cls_acc_reportsList.add(cls_acc_report);*/

                        // date,fromstore,tostore,des,docno,itemno,qty,UnitNo,UnitRate,myear


                        for (i = 0; i < js_ITEMID.length(); i++) {
                            cls_acc_report = new Cls_CustLastInvoice();
                            cls_acc_report.setITEMID(js_ITEMID.get(i).toString());
                            cls_acc_report.setNAME(js_NAME.get(i).toString());
                            cls_acc_report.setUnit("Psc");
                            cls_acc_report.setSALESQTY(js_SALESQTY.get(i).toString());
                            cls_acc_report.setSALESPRICE(js_SALESPRICE.get(i).toString());
                            cls_acc_report.setLINEAMOUNT(js_LINEAMOUNT.get(i).toString());
                            cls_acc_report.setINVENTBATCHID(js_INVENTBATCHID.get(i).toString());
                            cls_acc_report.setBOUNCE(js_BOUNCE.get(i).toString());
                            AfterTax = (js_AfterTax.get(0).toString());
                            BeforeTax = (js_BeforeTax.get(0).toString());
                            if(js_TAXCODE.get(i).toString().equals("DO_S4%"))
                            {
                                cls_acc_report.setTax("4");
                                TaxLine4 +=Double.parseDouble(js_TaxLine.get(i).toString());
                            }
                            else if(js_TAXCODE.get(i).toString().equals("DO_S16%"))
                            {
                                TaxLine16 +=Double.parseDouble(js_TaxLine.get(i).toString());
                                cls_acc_report.setTax("16");
                            }

                            cls_acc_reportsList.add(cls_acc_report);
                            custDialog.setMax(js_ITEMID.length());
                            custDialog.incrementProgressBy(1);

                            if (custDialog.getProgress() == custDialog.getMax()) {
                                custDialog.dismiss();

                                //   GetData.setVisibility(View.VISIBLE);
                            }
                        }
                        final int total = i;
                        _handler.post(new Runnable() {

                            public void run() {

                                //  GetData.setVisibility(View.VISIBLE);

                                //      tv_InvoiceDate.setText(INVOICEDATE);

                                LinearLayout ChecksLayout = (LinearLayout) findViewById(R.id.Sal_ItemSLayout);

                                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View view;

                                TextView tv_no;
                                TextView tv_name;

                                TextView tv_Qty;
                                TextView tv_Price;
                                TextView tv_Bounse;
                                TextView tv_Total;
                                TextView tv_Tax1;


                                int row = 1 ;
                                for (Cls_CustLastInvoice Obj : cls_acc_reportsList) {
                                    view = inflater.inflate(R.layout.view_trans_qty_row2, null);

                                    tv_no = (TextView) view.findViewById(R.id.tv_no);
                                    tv_name = (TextView) view.findViewById(R.id.tv_name);

                                    tv_Qty = (TextView) view.findViewById(R.id.tv_Qty);
                                    tv_Price = (TextView) view.findViewById(R.id.tv_Price);
                                    tv_Bounse = (TextView) view.findViewById(R.id.tv_Bounse);
                                    tv_Total = (TextView) view.findViewById(R.id.tv_Total);
                                    tv_Tax1 = (TextView) view.findViewById(R.id.tv_Tax1);



                                    tv_no.setText(Obj.getINVENTBATCHID());
                                    tv_name.setText(Obj.getNAME());
                                    tv_Qty.setText(Obj.getSALESQTY());
                                    tv_Bounse.setText(Obj.getBOUNCE());
                                    tv_Price.setText(Obj.getSALESPRICE());
                                    tv_Total.setText(Obj.getLINEAMOUNT());
                                    tv_Tax1.setText(Obj.getTax());


                                    ChecksLayout.addView(view);

                                }
                                NumberFormat nf_out;
                                nf_out = NumberFormat.getNumberInstance(Locale.ENGLISH);
                                nf_out.setMaximumFractionDigits(3);
                                tv_Total1.setText((BeforeTax)) ;
                                tv_Disc.setText(nf_out.format((TaxLine4))) ;
                                tv_TotalTax.setText(nf_out.format((TaxLine16))) ;
                                tv_NetTotal.setText(((AfterTax)));
                            }
                        });
                    }else {

                        //  GetData.setVisibility(View.VISIBLE);
                        custDialog.dismiss();
                    }
                } catch (final Exception e) {
                    custDialog.dismiss();
                    _handler.post(new Runnable() {

                        public void run() {


                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    Convert_Prapre_Qty_To_Img1.this   ).create();
                            alertDialog.setTitle("اخر فاتورة للعميل");
                            if (We_Result.ID == -404) {
                                alertDialog.setMessage(We_Result.Msg);
                            } else {
                                alertDialog.setMessage("لا يوجد بيانات" );
                            }
                            alertDialog.setIcon(R.drawable.error_new );

                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            //  alertDialog.show();

                        }
                    });
                }


            }
        }).start();





    }

    public void btn_back(View view) {

        Intent k = new Intent(Convert_Prapre_Qty_To_Img1.this, Acc_ReportActivity1.class);
        startActivity(k);
    }

    public void Do_Whatsapp(View view) {

        StoreImage();
        openWhatsApp();
    }
    public void Do_P(View view) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String PrinterType = sharedPreferences.getString("PrinterType", "1") ;
        LinearLayout lay = (LinearLayout) findViewById(R.id.Mainlayout);
        if  (PrinterType.equals("1")) {

         /*   PrintReport_SEWOO_ESCPOS ObjPrint = new PrintReport_SEWOO_ESCPOS(Convert_Prapre_Qty_To_Img1.this,
                    Convert_Prapre_Qty_To_Img1.this,lay,570,1);
            ObjPrint.ConnectToPrinter();*/
        }
        if  (PrinterType.equals("2")) {

            PrintReport_Zepra520 obj =  new PrintReport_Zepra520(Convert_Prapre_Qty_To_Img1.this,
                    Convert_Prapre_Qty_To_Img1.this,lay,570,1);
            obj.DoPrint();


        }


    }


    private void openWhatsApp() {
        File imageFileToShare = new File("//sdcard/zr3.jpg");
        Uri uri2 = Uri.fromFile(imageFileToShare);
        String toNumber = "+9962779176141";
        toNumber = toNumber.replace("+", "").replace(" ", "");
        Intent shareIntent = new Intent("android.intent.action.MAIN");
        shareIntent.setAction(Intent.ACTION_SEND);
        String ExtraText;
        if (Flag.equals("1"))
            ExtraText = "فاتورة مبيعات";
        else
            ExtraText = "مرتجع مبيعات ";
        shareIntent.putExtra(Intent.EXTRA_TEXT, ExtraText);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri2);
        shareIntent.setType("image/jpg");
        shareIntent.setPackage("com.whatsapp");
        shareIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {

            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            // Toast.makeText(getBaseContext(), "Sharing tools have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }
    private  void StoreImage(){
        LinearLayout lay = (LinearLayout) findViewById(R.id.Mainlayout);


        lay.setDrawingCacheEnabled(true);
        lay.buildDrawingCache();



        Bitmap b = loadBitmapFromView_NEW(lay);//loadBitmapFromView(lay);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String filename = "zr3.jpg";
        File sd = Environment.getExternalStorageDirectory();
        File dest = new File(sd, filename);

        try {
            FileOutputStream out = new FileOutputStream(dest);
            b.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();
            //  bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Bitmap loadBitmapFromView_NEW(View v) {


      /*  v.measure(View.MeasureSpec.makeMeasureSpec(v.getLayoutParams().width,
                View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(
                v.getLayoutParams().height, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());*/
        v.measure(View.MeasureSpec.makeMeasureSpec(v.getLayoutParams().width,
                View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(
                v.getLayoutParams().height, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        b.setDensity(DisplayMetrics.DENSITY_XXXHIGH);

        Canvas c = new Canvas(b);
        c.setDensity(DisplayMetrics.DENSITY_XXXHIGH);
        v.draw(c);
        return b;
    }

}
