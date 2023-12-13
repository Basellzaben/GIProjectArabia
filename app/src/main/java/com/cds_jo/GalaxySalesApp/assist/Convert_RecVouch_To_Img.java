package com.cds_jo.GalaxySalesApp.assist;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.ComInfo;
import com.cds_jo.GalaxySalesApp.Companies;
import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.Pos.Print_POS_Activity;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.RecvVoucherActivity;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.TspPrinter.PrinterFunctions;
import com.sewoo.jpos.printer.ESCPOSPrinter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Convert_RecVouch_To_Img extends AppCompatActivity {
    private ESCPOSPrinter posPtr;
    private View mView;
    SqlHandler sqlHandler ;
    ListView lvCustomList;
    View ReportView;

    private Button mButton;
    private Context context;
    ImageView img_Logo;
    String BPrinter_MAC_ID ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        BPrinter_MAC_ID= sharedPreferences.getString("AddressBT", "");
        setContentView(R.layout.activity_convert__rec_vouch__to__img);

        final String Company = sharedPreferences.getString("CompanyID", "1") ;



        img_Logo = (ImageView) findViewById(R.id.img_Logo);
        File imgFile = new  File("//sdcard/Android/Cv_Images/logo.jpg");
       try {
           if (imgFile.exists()) {
               Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
               img_Logo.setImageBitmap(myBitmap);
           }
       }
       catch (Exception ex){}


        Intent i = getIntent();
        View test1View = findViewById(R.id.f_view);

        String u =  sharedPreferences.getString("UserName", "");
        TextView    tv_UserNm  = (TextView)findViewById(R.id.tv_UserNm);
        tv_UserNm.setText(u);


        TextView tv_CompName =(TextView)findViewById(R.id.tv_CompName);
        tv_CompName.setText(sharedPreferences.getString("CompanyNm", ""));

        final String PrinterType = sharedPreferences.getString("PrinterType", "1") ;
        String OrderNo = getIntent().getStringExtra("OrderNo");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());


       ShowRecord (OrderNo);

        sqlHandler = new SqlHandler(this);

        mButton = (Button) findViewById(R.id.btn_Print);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createPdf();



        }
    });

        //mBluetoothAdapter.enable();




    } private void createPdf() {


        Toast.makeText(this, "PRINTING ... " , Toast.LENGTH_LONG).show();

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight
                , convertWidth = (int) width;

        LinearLayout lay = (LinearLayout) findViewById(R.id.Mainlayout);



        ReportView = lay;
        Bitmap bitmap = loadBitmapFromView(ReportView);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);


        Canvas canvas = page.getCanvas();


        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);


        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);


        // write the document content
        // String targetPdf = "/sdcard/test.pdf";
        String folder_main = "/Android/Cv_Images/SalInv_Sig" +
                getIntent().getStringExtra("OrderNo") + "PP.pdf";
        File filePath = new File(Environment.getExternalStorageDirectory(), folder_main);

        // File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        // OpenPDFFile3();


        File file = filePath;
        String extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());

        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_NEW_TASK);

        Uri uri = FileProvider.getUriForFile(Convert_RecVouch_To_Img.this, getPackageName() + ".provider", file);

        intent.setDataAndType(uri, mimeType);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(Intent.createChooser(intent, "choseFile"));




    }

    public static Bitmap loadBitmapFromView(View v) {

        v.measure(View.MeasureSpec.makeMeasureSpec(v.getLayoutParams().width,
                View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(
                v.getLayoutParams().height, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }
    public  void ShowRecord( String OrdNo){
         TextView OrderNo = (TextView)findViewById(R.id.et_OrdeNo);
        TextView amt =(TextView)findViewById(R.id.et_Amt);
        TextView etNote =(TextView)findViewById(R.id.et_notes);
        TextView et_Date =(TextView)findViewById(R.id.et_Date);
        TextView tv_acc =(TextView)findViewById(R.id.tv_acc);
        TextView tv_cusnm =(TextView)findViewById(R.id.tv_cusnm);
        TextView cash =(TextView)findViewById(R.id.tv_Cash);
        TextView tv_Cur =(TextView)findViewById(R.id.tv_Cur);
        TextView tv_type =(TextView)findViewById(R.id.tv_type);
        TextView tv_Checktotal =(TextView)findViewById(R.id.tv_Checktotal);
        TextView tv_Checktotal1 =(TextView)findViewById(R.id.tv_Checktotal1);

        TextView tv_Seq =(TextView)findViewById(R.id.tv_Seq);
        LinearLayout lytCheck1 = (LinearLayout) findViewById(R.id.lytCheck1);
        LinearLayout lytCheck2 = (LinearLayout) findViewById(R.id.lytCheck2);

        OrderNo.setText(OrdNo);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Convert_RecVouch_To_Img.this);
        String q ="Select DISTINCT ifnull(rc.Seq,'') as  Seq,  ifnull(CheckTotal,0) as CheckTotal ,   ifnull( rc.cash ,0) as Cash, rc.VouchType,rc.curno  ,curf.cur    ,  rc.Desc,rc.Amnt,rc.TrDate,rc.CustAcc  ,rc.IDN from RecVoucher rc   " +
                "left join Customers_man c on c.no = rc.CustAcc   Left join curf on curf.cur_no = rc.curno " +
                "where rc.DocNo = '"+ OrdNo +"' and FromSales =0 and rc.IDN='"+getIntent().getStringExtra("IDN")+"'";
        SqlHandler sqlHandler = new SqlHandler(this);

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                etNote.setText(c1.getString(c1.getColumnIndex("Desc")));
                amt.setText(c1.getString(c1.getColumnIndex("Amnt")));
                et_Date.setText(c1.getString(c1.getColumnIndex("TrDate")));
                tv_acc.setText(c1.getString(c1.getColumnIndex("CustAcc")));
            //    tv_cusnm.setText(c1.getString(c1.getColumnIndex("name")));
                String  sad = DB.GetValue(Convert_RecVouch_To_Img.this, "Customers_man","name", "IDN='" + c1.getString(c1.getColumnIndex("IDN")) + "'");
                tv_cusnm.setText(sad);
                cash.setText(c1.getString(c1.getColumnIndex("Cash")));
                tv_Cur.setText(c1.getString(c1.getColumnIndex("cur")));
                tv_Checktotal.setText(c1.getString(c1.getColumnIndex("CheckTotal")));

                 LinearLayout.LayoutParams lp =  new  LinearLayout.LayoutParams(0,0);

                 if (c1.getString(c1.getColumnIndex("VouchType")).equals("1")){
                    tv_type.setText("نقدي");
                    lytCheck1.setVisibility(View.INVISIBLE);
                     lytCheck1.setLayoutParams(lp);
                     lytCheck2.setVisibility(View.INVISIBLE);
                     lytCheck2.setLayoutParams(lp);
                     tv_Checktotal.setVisibility(View.INVISIBLE);
                     tv_Checktotal1.setVisibility(View.INVISIBLE);
                 }
               else if (c1.getString(c1.getColumnIndex("VouchType")).equals("2")){
                    tv_type.setText("شيكات");
                }

                else if (c1.getString(c1.getColumnIndex("VouchType")).equals("3")){
                    tv_type.setText(" نقدي + شيكات");
                }

                tv_Seq.setText(c1.getString(c1.getColumnIndex("Seq")));
            }


        c1.close();
        }
        showList();

    }

    private void showList( ) {

        TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
        ArrayList<Cls_Check> contactList = new ArrayList<Cls_Check>();
        contactList.clear();
        sqlHandler = new SqlHandler(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String Company = sharedPreferences.getString("CompanyID", "1");

        String query = "Select DISTINCT rc.CheckNo,rc.CheckDate,rc.BankNo, rc.Amnt, b.Bank from RecCheck rc left join banks b on b.bank_num = rc.BankNo where DocNo ='" + DocNo.getText().toString() + "'";
        Integer i = 1;
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.moveToFirst()) { // Move to the first row of the result set
            do {
                Cls_Check cls_check_obj = new Cls_Check();
                cls_check_obj.setSer(i);

                cls_check_obj.setCheckDate(c1.getString(c1.getColumnIndex("CheckDate")));
                cls_check_obj.setBankName(c1.getString(c1.getColumnIndex("Bank")));
                cls_check_obj.setAmnt(c1.getString(c1.getColumnIndex("Amnt")));
                cls_check_obj.setCheckNo(c1.getString(c1.getColumnIndex("CheckNo")));
                contactList.add(cls_check_obj);

                i++;
            } while (c1.moveToNext());

            c1.close();
        }
        LinearLayout ChecksLayout = (LinearLayout) findViewById(R.id.ChecksLayout);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;

        TextView tv_Amnt;
        TextView tv_BankNm;
        TextView tv_CheckDate;
        TextView tv_Checkno;
        TextView Chkser;

        for (Cls_Check Obj : contactList){


            view = inflater.inflate(R.layout.check_print_list_row ,null);
            if  (Company.equals("2")) {
                view = inflater.inflate(R.layout.check_print_list_row_1, null);
            }



            tv_Amnt = (TextView) view.findViewById(R.id.tv_Amnt);
            tv_BankNm = (TextView) view.findViewById(R.id.tv_BankNm);
            tv_CheckDate = (TextView) view.findViewById(R.id.tv_CheckDate);
            tv_Checkno = (TextView) view.findViewById(R.id.tv_Checkno);
            Chkser = (TextView) view.findViewById(R.id.ser);
        try {
            tv_Amnt.setText(Obj.getAmnt());
            tv_BankNm.setText(Obj.getBankName());
            tv_CheckDate.setText(Obj.getCheckDate());
            tv_Checkno.setText(Obj.getCheckNo());
            Chkser.setText(Obj.getSer() + "");
             ChecksLayout.addView(view);
            }

            catch (Exception ex){

            }

        }

    }


    public void btn_back(View view) {
        super.onBackPressed();
    }
}
