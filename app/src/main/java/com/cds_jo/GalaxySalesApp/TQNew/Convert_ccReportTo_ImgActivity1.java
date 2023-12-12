package com.cds_jo.GalaxySalesApp.TQNew;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Convert_ccReportTo_ImgActivity1 extends AppCompatActivity {
    EditText et_phoneNo;
    SqlHandler sqlHandler;
    ListView lvCustomList;
    private Button mButton;
    private View mView;
    String ShowTax = "0";
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE_BT = 0;
    private static final DecimalFormat oneDecimal = new DecimalFormat("#,##0.0");
    ImageView img_Logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String Company = sharedPreferences.getString("CompanyID", "1") ;
        final String PrinterType = sharedPreferences.getString("PrinterType", "1") ;
        setContentView(R.layout.covert_acc_report5);
        img_Logo = (ImageView) findViewById(R.id.img_Logo);
        File imgFile = new  File("//sdcard/Android/Cv_Images/logo.jpg");
        try {
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                img_Logo.setImageBitmap(myBitmap);
            }
        }
        catch (Exception ex){}
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Intent i = getIntent();
        mView = findViewById(R.id.f_view);



        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.
                getDefaultAdapter();




        String u =  sharedPreferences.getString("UserName", "");
        String UserID =  sharedPreferences.getString("UserID", "");



        TextView tv_CompName =(TextView)findViewById(R.id.tv_CompName);
        tv_CompName.setText(sharedPreferences.getString("CompanyNm", ""));

        SimpleDateFormat StartTime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);











        sqlHandler = new SqlHandler(this);
        String query = "SELECT   Tot, Dept,Des,Date, ifnull(Cred,0) as Cred ,FDate,TDate  ,Cust_Nm from ACC_REPORT    " ;

        ShowRecord ("");
        mButton = (Button) findViewById(R.id.btn_Print);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LinearLayout lay = (LinearLayout) findViewById(R.id.Mainlayout);



        /*        PrintReport_Zepra520 obj = new PrintReport_Zepra520(Convert_ccReportTo_ImgActivity1.this,
                        Convert_ccReportTo_ImgActivity1.this, lay, 570, 1);
                obj.DoPrint();*/


            }
        });
        et_phoneNo=(EditText) findViewById(R.id.et_phoneNo);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mBluetoothAdapter.enable();
    }

    public  void ShowRecord( String OrdNo){

        showList(OrdNo);

    }

    private  Double SToD(String str){
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
    private void showList(String OrderNo) {
        Intent i = getIntent();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String Company = sharedPreferences.getString("CompanyID", "1") ;



        sqlHandler = new SqlHandler(this);
        String query = "SELECT   Tot, Dept,Des,Date, ifnull(Cred,0) as Cred ,FDate,TDate  ,Cust_Nm from ACC_REPORT    " ;



        // "Left Join invf on   invf.Item_No=sid.itemNo  where sid.OrderNo =  '"+  i.getStringExtra("OrderNo").toString()+"'";
        TextView tv_cusnm =(TextView)findViewById(R.id.tv_cusname);
        TextView tv_fdate =(TextView)findViewById(R.id.tv_fdate);
        TextView tv_tdate =(TextView)findViewById(R.id.tv_tdate);
        TextView NetBall =(TextView)findViewById(R.id.NetBall);
        ArrayList<Cls_Acc_Report1> contactList = new ArrayList<Cls_Acc_Report1>();
        tv_fdate.setText(getIntent().getStringExtra("From") );
        tv_tdate.setText(getIntent().getStringExtra("ToDate") );
        tv_cusnm.setText("  "+ getIntent().getStringExtra("cusnm") +"  ");
        NetBall.setText("  "+ getIntent().getStringExtra("NetBall") +"  ");

        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {


                do {
                    Cls_Acc_Report1 contactListItems = new Cls_Acc_Report1();

                    if(c1.getString(c1.getColumnIndex("Date")).equalsIgnoreCase("عدد الحركات")) {
                        contactListItems.setDes(c1.getString(c1.getColumnIndex("Des")) );
                    }else{
                        contactListItems.setDes(c1.getString(c1
                                .getColumnIndex("Des")) +"\n\r   " + c1.getString(c1.getColumnIndex("Date")));
                    }


                    contactListItems.setCred(c1.getString(c1
                            .getColumnIndex("Cred")));
                    contactListItems.setDept(c1.getString(c1
                            .getColumnIndex("Dept")));
                    contactListItems.setTot(c1.getString(c1
                            .getColumnIndex("Tot")));

                    contactList.add(contactListItems);


                } while (c1.moveToNext());


            }



            c1.close();
        }





        LinearLayout Sal_ItemSLayout = (LinearLayout) findViewById(R.id.Sal_ItemSLayout);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;

        TextView tv_Des;
        TextView Cred;
        TextView Dept;
        TextView Tot;




        for (Cls_Acc_Report1 Obj : contactList){

            view = inflater.inflate(R.layout.report_img_row, null);

            tv_Des = (TextView) view.findViewById(R.id.tv_Des);
            Cred = (TextView) view.findViewById(R.id.tv_Cred);
            Dept = (TextView) view.findViewById(R.id.tv_Dept);
            Tot = (TextView) view.findViewById(R.id.tv_Tot);


            tv_Des.setText(Obj.getDes());
            Cred.setText(Obj.getCred());
            Dept.setText(Obj.getDept());
            Tot.setText(Obj.getTot());

            Sal_ItemSLayout.addView(view);

        }






    }

    public void btn_back(View view) {
        Intent i =  new Intent(this,Acc_ReportActivity1.class);
        startActivity(i);
    }

    public static Bitmap loadBitmapFromView(View v) {



        v.measure(View.MeasureSpec.makeMeasureSpec(v.getLayoutParams().width,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(
                v.getLayoutParams().height, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    public static Bitmap setViewToBitmapImage(View view) {
//Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
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
    private  void StoreImage(){
        LinearLayout lay = (LinearLayout) findViewById(R.id.Mainlayout);


        lay.setDrawingCacheEnabled(true);
        lay.buildDrawingCache();



        Bitmap b = loadBitmapFromView_NEW(lay);//loadBitmapFromView(lay);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String filename = "z1.jpg";
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
    public void Do_Whatsapp(View view) {

        StoreImage();
        openWhatsApp();
    }
    private void openWhatsApp() {
        File imageFileToShare = new File("//sdcard/z1.jpg");
        Uri uri2 = Uri.fromFile(imageFileToShare);
        String toNumber = "+962796892140";
        toNumber="+962" + et_phoneNo.getText();
        toNumber = toNumber.replace("+", "").replace(" ", "");
        Intent shareIntent = new Intent("android.intent.action.MAIN");
        shareIntent.setAction(Intent.ACTION_SEND);
        String ExtraText;
        ExtraText = "Galaxy Sales App";
        shareIntent.putExtra(Intent.EXTRA_TEXT, ExtraText);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri2);
        shareIntent.setType("image/jpg");
        shareIntent.setPackage("com.whatsapp");
        shareIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {

            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getBaseContext(), "Sharing tools have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }
}

