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
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.Cls_InvoiceMsg;
import com.cds_jo.GalaxySalesApp.Cls_Offers_Hdr;
import com.cds_jo.GalaxySalesApp.ComInfo;
import com.cds_jo.GalaxySalesApp.ContactListItems;
import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.sewoo.jpos.printer.ESCPOSPrinter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Convert_Sal_Invoice_To_Img_Ukrainian extends AppCompatActivity {
    private ESCPOSPrinter posPtr;
    SqlHandler sqlHandler;
    ListView lvCustomList;
    private Button mButton;
    private View mView;
    String ShowTax = "0";
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE_BT = 0;
    private static final DecimalFormat oneDecimal = new DecimalFormat("#,##0.0");

    Button btn_Save, btn_Clear, btn_Cancel;
    LinearLayout mContent;
    View ViewNotes;
    signature mSignature;
    Bitmap bitmap;
    ImageView img_Logo;
    ImageView imgSig;
    int SaveImg = 0;
    String StoredPath;
    String DIRECTORY;
    float STROKE_WIDTH = 5f;
    float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
    Integer DocType = 1;
    TextView tv_lblDocType, tv_ReportTitle, tv_lblDocNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_convert_sal_invoice_to_img_ukrainian);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        DocType = ComInfo.DocType;
        if (DocType < 1) {
            DocType = 1;
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String Company = sharedPreferences.getString("CompanyID", "1");

        tv_ReportTitle = (TextView) findViewById(R.id.tv_ReportTitle);
        tv_lblDocType = (TextView) findViewById(R.id.tv_lblDocType);
        tv_lblDocNo = (TextView) findViewById(R.id.tv_lblDocNo);


        if (DocType == 1) {
            tv_ReportTitle.setText("فاتورة المبيعات");
            tv_lblDocType.setText("نوع المبيعات :");
            tv_lblDocNo.setText("رقم المبيعات :");

        }
        else if (DocType == 2) {
            tv_ReportTitle.setText(" إرجاع  المبيعات");
            tv_lblDocType.setText("نوع الإرجاع :");
            tv_lblDocNo.setText(" رقم الطلب :");

        }
        else if (DocType == 3) {
            tv_ReportTitle.setText("سند تسليم بضاعة  ");
            tv_lblDocType.setText("نوع السند :");
            tv_lblDocNo.setText("رقم السند:");

        }

        String folder_main = "/Android/Cv_Images/SalInv_Sig";

        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }

        DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/Android/Cv_Images/SalInv_Sig/";
        StoredPath = DIRECTORY + DocType.toString()+getIntent().getStringExtra("OrderNo") + ".png";


        mSignature = new signature(Convert_Sal_Invoice_To_Img_Ukrainian.this, null);
        mSignature.setBackgroundColor(Color.WHITE);

        mContent = (LinearLayout) findViewById(R.id.mContent);
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ViewNotes = mContent;


        btn_Save = (Button) findViewById(R.id.btn_Save);
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSig();

            }
        });


        btn_Clear = (Button) findViewById(R.id.btn_Clear);
        btn_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewNotes.setDrawingCacheEnabled(true);
                mSignature.save(ViewNotes, StoredPath);
                mSignature.clear();
                mSignature.setBackgroundColor(Color.WHITE);
                SaveSig();
            }
        });

        // Dynamically generating Layout through java code


        img_Logo = (ImageView) findViewById(R.id.img_Logo);
        File imgFile = new File("//sdcard/Android/Cv_Images/logo.jpg");
        try {
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                img_Logo.setImageBitmap(myBitmap);
            }
        } catch (Exception ex) {
        }

        imgSig = (ImageView) findViewById(R.id.imgSig);
        loadimage();


        Intent i = getIntent();
        mView = findViewById(R.id.f_view);
        TextView OrderNo = (TextView) findViewById(R.id.tv_OrderNo);
        OrderNo.setText(getIntent().getStringExtra("OrderNo"));


        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.
                getDefaultAdapter();




        String u = sharedPreferences.getString("UserName", "");
        String UserID = sharedPreferences.getString("UserID", "");
        TextView tv_TaxAcc = (TextView) findViewById(R.id.tv_TaxAcc);
        tv_TaxAcc.setText(sharedPreferences.getString("TaxAcc1", ""));


        TextView ManManMobile = (TextView) findViewById(R.id.tv_ManMobile);

        String Mobile = "";
        Mobile = DB.GetValue(Convert_Sal_Invoice_To_Img_Ukrainian.this, "manf", "Mobile1", "man='" + UserID + "'");
        if (Mobile.equalsIgnoreCase("")) {
            Mobile = "";
        }
        ManManMobile.setText(Mobile);


        TextView tv_SupervisorMobile = (TextView) findViewById(R.id.tv_SupervisorMobile);
        tv_SupervisorMobile.setText(sharedPreferences.getString("SuperVisorMobile", ""));


        TextView tv_CompName = (TextView) findViewById(R.id.tv_CompName);
        tv_CompName.setText(sharedPreferences.getString("CompanyNm", ""));

        SimpleDateFormat StartTime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        String StringTime = StartTime.format(new Date());
        TextView Time = (TextView) findViewById(R.id.tv_time);
        Time.setText(StringTime);

        TextView tv_footer = (TextView) findViewById(R.id.tv_footer);
        TextView tv_footer1 = (TextView) findViewById(R.id.tv_footer1);

        TextView tv_footer3 = (TextView) findViewById(R.id.tv_footer3);

        tv_footer.setText(sharedPreferences.getString("CompanyNm", "") + " - " + sharedPreferences.getString("Address", "") + " - " + sharedPreferences.getString("Notes", ""));
        tv_footer1.setText(" هاتف الشركة  " + (sharedPreferences.getString("CompanyMobile", "")));


        String footer3 = "";

        if (ComInfo.ComNo == 3) {
            footer3 = "ان المواد المشروحة اعلاه قد سلمت بحالة جيدة بعد المعاينة";

        } else {
            footer3 = " لا تعتبر هذه الفاتورة مدفوعة دون ايصال رسمي من الشركة ،";
            footer3 = footer3 + " \n" + "إن مجرد توقيع المستلم ادناه يعتبر إقرار منه باستلام البضاعة  المبينة اعلاه بالعدد والسعر المحددين";
            footer3 = footer3 + " " + "وان المستلم ادناه ملزم بدفع القيمة كاملة عند المطالبة   دون إشعار أو إنذار  ويسقط حقه في توجيه اليمين";


        }

        tv_footer3.setText(footer3);
        ShowTax = getIntent().getStringExtra("ShowTax");


        TextView tv_UserNm = (TextView) findViewById(R.id.tv_UserNm);
        tv_UserNm.setText(u);

        TextView textView10 = (TextView) findViewById(R.id.textView10);

        TextView textView12 = (TextView) findViewById(R.id.textView12);
        TextView tv_TotalTax = (TextView) findViewById(R.id.tv_TotalTax);

        TextView tv_Tax_Caption = (TextView) findViewById(R.id.tv_Tax_Caption);

        TextView tv_DvNm = (TextView) findViewById(R.id.tv_DvNm);

        TextView textView11 = (TextView) findViewById(R.id.textView11);
        TextView tv_Total = (TextView) findViewById(R.id.tv_Total);
        TextView textView86 = (TextView) findViewById(R.id.textView86);
        TextView tv_Disc = (TextView) findViewById(R.id.tv_Disc);


        textView12.setVisibility(View.VISIBLE);
        tv_TotalTax.setVisibility(View.VISIBLE);
        textView11.setVisibility(View.VISIBLE);
        tv_Total.setVisibility(View.VISIBLE);
        textView86.setVisibility(View.VISIBLE);
        tv_Disc.setVisibility(View.VISIBLE);

        if (ShowTax.equals("0")) {
            textView10.setText("");
            textView12.setText("");
            tv_TotalTax.setText("");
            // tv_TaxAcc.setText("");
            //  tv_Tax_Caption.setText("");

        }


        sqlHandler = new SqlHandler(this);
        ShowRecord(getIntent().getStringExtra("OrderNo"));
        mButton = (Button) findViewById(R.id.btn_Print);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton.setBackground(getResources().getDrawable(R.drawable.blue_fill_white));
                mButton.setTextColor(getResources().getColor(R.color.Blue));
                //
                /*if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                }*/

                // mBluetoothAdapter.enable();

                LinearLayout lay = (LinearLayout) findViewById(R.id.Mainlayout);
   /*             if (PrinterType.equals("1")) {

                    if (Company.equals("1") || Company.equals("2")) {
                        PrintReport_SEWOO_ESCPOS ObjPrint = new PrintReport_SEWOO_ESCPOS(Convert_Sal_Invoice_To_ImgActivity.this,
                                Convert_Sal_Invoice_To_ImgActivity.this, lay, 570, 1);
                        ObjPrint.ConnectToPrinter();

                    }
                    *//*if (Company.equals("2")) {
                        PrintReport_SEWOO_ESCPOS ObjPrint = new PrintReport_SEWOO_ESCPOS(Convert_Sal_Invoice_To_ImgActivity.this,
                                Convert_Sal_Invoice_To_ImgActivity.this, lay, 200, 1);
                        ObjPrint.ConnectToPrinter();
                    }*//*
                }

                if (PrinterType.equals("2")) {
                    PrintReport_Zepra520 obj =  new PrintReport_Zepra520(Convert_Sal_Invoice_To_ImgActivity.this,
                            Convert_Sal_Invoice_To_ImgActivity.this,lay,560,1);
                    obj.DoPrint();
                }*/


                PrintReport_Zepra520 obj = new PrintReport_Zepra520(Convert_Sal_Invoice_To_Img_Ukrainian.this,
                        Convert_Sal_Invoice_To_Img_Ukrainian.this, lay, 560, 1);
                obj.DoPrint();
                //    ObjPrint.CopyCount = 2 ;




            }
        });
        try {
            mBluetoothAdapter.enable();
        }catch (Exception d){}
    }

    private void ResetFoundSize() {
        mSignature.paint.setStrokeWidth(5f);
        ViewNotes.setDrawingCacheEnabled(true);
        STROKE_WIDTH = 5f;
        HALF_STROKE_WIDTH = STROKE_WIDTH / 2;

    }

    public void SaveSig() {
        ViewNotes.setDrawingCacheEnabled(true);
        mSignature.save(ViewNotes, StoredPath);
        loadimage();
    }

    public void btn_Red(View view) {

        mSignature.save(ViewNotes, StoredPath);
        loadimage();
        mSignature.clear();
        mSignature.paint.setColor(Color.RED);
        ResetFoundSize();
    }

    public void btn_Erease(View view) {
        ViewNotes.setDrawingCacheEnabled(true);
        mSignature.save(ViewNotes, StoredPath);
        loadimage();
        mSignature.clear();
        mSignature.paint.setColor(Color.WHITE);
        mSignature.paint.setStrokeWidth(40f);
        ViewNotes.setDrawingCacheEnabled(true);
        STROKE_WIDTH = 30f;
        HALF_STROKE_WIDTH = STROKE_WIDTH / 2;

        /*float u = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,5000,getResources().getDisplayMetrics());*/


    }

    public void btn_yallow(View view) {

        ViewNotes.setDrawingCacheEnabled(true);
        mSignature.save(ViewNotes, StoredPath);
        loadimage();
        mSignature.clear();
        mSignature.paint.setColor(Color.YELLOW);
        ResetFoundSize();
    }

    public void btn_Blue(View view) {

        ViewNotes.setDrawingCacheEnabled(true);
        mSignature.save(ViewNotes, StoredPath);
        loadimage();
        mSignature.clear();
        mSignature.paint.setColor(Color.BLUE);
        ResetFoundSize();
    }

    public void btn_Green(View view) {
        ViewNotes.setDrawingCacheEnabled(true);
        mSignature.save(ViewNotes, StoredPath);
        loadimage();
        mSignature.clear();
        mSignature.paint.setColor(Color.GREEN);
        ResetFoundSize();
    }

    public void btn_Black(View view) {

        ViewNotes.setDrawingCacheEnabled(true);
        mSignature.save(ViewNotes, StoredPath);
        loadimage();
        mSignature.clear();
        mSignature.paint.setColor(Color.BLACK);
        ResetFoundSize();
    }

    private void loadimage() {
        TextView textView129 = (TextView) findViewById(R.id.textView129);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        imgSig = (ImageView) findViewById(R.id.imgSig);
        File imgFile = new File("//sdcard//Android/Cv_Images/SalInv_Sig/" + DocType.toString()+ getIntent().getStringExtra("OrderNo") + ".png");
        if (!imgFile.exists()) {

            mSignature.setBackgroundColor(Color.WHITE);
        } else {
            imgFile = new File("//sdcard//Android/Cv_Images/SalInv_Sig/" + DocType.toString()+getIntent().getStringExtra("OrderNo") + ".png");
            try {
                mSignature.setBackgroundColor(Color.WHITE);

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                Drawable backgroundImage = new BitmapDrawable(myBitmap);
                ViewNotes.setBackgroundDrawable(backgroundImage);
                mSignature.setBackgroundDrawable(backgroundImage);
                imgSig.setImageBitmap(myBitmap);
                textView129.setVisibility(View.INVISIBLE);

            } catch (Exception ex) {
                mSignature.setBackgroundColor(Color.WHITE);
            }
        }


    }

    public class signature extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath) {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmap);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            SaveImg = 1;
            //mGetSign.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    public void ShowRecord(String OrdNo) {


       /* ShapeDrawable shape = new ShapeDrawable(new RectShape());
        shape.getPaint().setColor(Color.BLACK);
        shape.getPaint().setStyle(Paint.Style.STROKE);
        shape.getPaint().setStrokeWidth(2);*/


        // Assign the created border to EditText widget


        TextView tv_cusnm = (TextView) findViewById(R.id.tv_cusname);
        TextView et_Date = (TextView) findViewById(R.id.ed_date);
        TextView tv_Disc = (TextView) findViewById(R.id.tv_Disc);
        TextView tv_NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        TextView tv_TotalTax = (TextView) findViewById(R.id.tv_TotalTax);
        TextView tv_Total = (TextView) findViewById(R.id.tv_Total);

        TextView textView11 = (TextView) findViewById(R.id.textView11);
        TextView textView12 = (TextView) findViewById(R.id.textView12);
        TextView textView86 = (TextView) findViewById(R.id.textView86);
        TextView textView88 = (TextView) findViewById(R.id.textView88);


        TextView textView5 = (TextView) findViewById(R.id.textView5);
        TextView textView7 = (TextView) findViewById(R.id.textView7);
        TextView textView8 = (TextView) findViewById(R.id.textView8);
        TextView textView9 = (TextView) findViewById(R.id.textView9);
        TextView textView43 = (TextView) findViewById(R.id.textView43);
        TextView textView10 = (TextView) findViewById(R.id.textView10);
        TextView tv_invoc_Type = (TextView) findViewById(R.id.tv_invoc_Type);
        TextView textView58 = (TextView) findViewById(R.id.textView58);
        TextView tv_itemCount = (TextView) findViewById(R.id.tv_itemCount);
        TextView textView107 = (TextView) findViewById(R.id.textView107);
        TextView tv_includetax = (TextView) findViewById(R.id.tv_includetax);
        TextView tv_CusBal = (TextView) findViewById(R.id.tv_CusBal);
        TextView tv_Seq = (TextView) findViewById(R.id.tv_Seq);

        TextView tv_DvNm = (TextView) findViewById(R.id.tv_DvNm);

       /* textView5.setBackground(shape);
        textView7.setBackground(shape);
        textView8.setBackground(shape);
        textView9.setBackground(shape);
        textView43.setBackground(shape);
        textView10.setBackground(shape);

        tv_Seq.setBackground(shape);

        textView11.setBackground(shape);
        textView12.setBackground(shape);
        textView86.setBackground(shape);
        textView88.setBackground(shape);
        textView58.setBackground(shape);

        tv_Total.setBackground(shape);
        tv_TotalTax.setBackground(shape);
        tv_NetTotal.setBackground(shape);
        tv_Disc.setBackground(shape);*/


        if (ShowTax.equals("0")) {
            textView12.setVisibility(View.INVISIBLE);
            tv_TotalTax.setVisibility(View.INVISIBLE);
            textView11.setVisibility(View.INVISIBLE);
            tv_Total.setVisibility(View.INVISIBLE);
            textView86.setVisibility(View.INVISIBLE);
            tv_Disc.setVisibility(View.INVISIBLE);

            textView12.setHeight(0);
            tv_TotalTax.setHeight(0);
            textView11.setHeight(0);
            tv_Total.setHeight(0);
            textView86.setHeight(0);
            tv_Disc.setHeight(0);


        }
        /*tv_itemCount.setBackground(shape);
        tv_itemCount.setBackground(shape);
        tv_itemCount.setBackground(shape);
        tv_itemCount.setBackground(shape);

        textView107.setBackground(shape);
        textView107.setBackground(shape);
        textView107.setBackground(shape);
        textView107.setBackground(shape);*/
        String accno = "";


        String q = "Select distinct ifnull(s.Seq,'') as  Seq,   ifnull(s.DelveryNm,'') as  DelveryNm , s.include_Tax, s.inovice_type , s.Total , s.Nm,  s.disc_Total, s.OrderNo,s.Net_Total,s.Tax_Total ,s.acc ,s.date , c.name  " +
                "    from  Sal_invoice_Hdr s left join Customers c on c.no =s.acc   where  ifnull(doctype,'1')='" + DocType.toString() + "'  and s.OrderNo = '" + OrdNo + "'";

        SqlHandler sqlHandler = new SqlHandler(this);

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                et_Date.setText(c1.getString(c1.getColumnIndex("date")));
                tv_cusnm.setText(c1.getString(c1.getColumnIndex("name")));
                if (c1.getString(c1.getColumnIndex("inovice_type")).equals("0")) {
                    tv_cusnm.setText(c1.getString(c1.getColumnIndex("Nm")).toString());

                } else {
                    tv_cusnm.setText(c1.getString(c1.getColumnIndex("name")));
                }
                tv_Disc.setText(c1.getString(c1.getColumnIndex("disc_Total")));
                tv_NetTotal.setText(c1.getString(c1.getColumnIndex("Net_Total")));
                tv_TotalTax.setText(c1.getString(c1.getColumnIndex("Tax_Total")));
                tv_DvNm.setText(c1.getString(c1.getColumnIndex("DelveryNm")));

                if (c1.getString(c1.getColumnIndex("inovice_type")).toString().equals("0")) {
                    tv_invoc_Type.setText("نقدية");

                } else {
                    tv_invoc_Type.setText("ذمم");
                }

                if (ComInfo.ComNo == 3) {
                    tv_invoc_Type.setText("نقدية");
                }

                if (ShowTax.equals("0")) {
                    tv_TotalTax.setText("");

                }
                if (c1.getString(c1.getColumnIndex("include_Tax")).toString().equals("-1")) {
                    tv_includetax.setText("");
                }


                tv_Seq.setText(c1.getString(c1.getColumnIndex("Seq")));

                accno = c1.getString(c1.getColumnIndex("s.acc"));
            /*    //if (c1.getString(c1.getColumnIndex("include_Tax")).toString().equals("0")) {
                ShowTax=getIntent().getStringExtra("ShowTax");
              //  if (ShowTax.equals("0")) {
                        textView12.setVisibility(View.INVISIBLE);
                        tv_TotalTax.setVisibility(View.INVISIBLE);
                        textView11.setVisibility(View.INVISIBLE);
                        tv_Total.setVisibility(View.INVISIBLE);
                        textView86.setVisibility(View.INVISIBLE);
                      tv_Disc.setVisibility(View.INVISIBLE);

             //   }
*/

                tv_Total.setText(c1.getString(c1.getColumnIndex("Total")));
            }
            c1.close();
        }
        showList(OrdNo);
        if (ComInfo.ComNo == 1 && ShowTax.equalsIgnoreCase("1")) {//  بناءا على طلب محمد البستنجي
            tv_includetax.setText("");

        }

        String NetTotal = DB.GetValue(Convert_Sal_Invoice_To_Img_Ukrainian.this, "Customers", "CUST_NET_BAL", "no ='" + accno + "' ");


        if (NetTotal.equalsIgnoreCase("-1")) {
            NetTotal = "غير مدخله";
        }
        tv_CusBal.setText(NetTotal);

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

    private void showList(String OrderNo) {
        Intent i = getIntent();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String Company = sharedPreferences.getString("CompanyID", "1");

        ArrayList<ContactListItems> contactList = new ArrayList<ContactListItems>();
        contactList.clear();
        sqlHandler = new SqlHandler(this);
        String query = "SELECT  distinct  ifnull(sid.weight,0) as weight ,  sid.itemNo,sid.OrgPrice ,sid.price,sid.tax,u.UnitName  , sid.tax_Amt,  ( ifnull(sid.qty,0) +  ifnull(sid.Pro_bounce,0) + ifnull(bounce_qty,0) )  as qty  ,invf.Item_Name  ,  sid.total    " +
                " , ifnull(sid.Pro_dis_Per,0) as Pro_dis_Per ,ifnull(sid.dis_Amt,0) as dis_Amt     FROM Sal_invoice_Det   sid    Left Join Unites u on u.Unitno =sid.unitNo " +
                "Left Join invf on   invf.Item_No=sid.itemNo  where   ifnull(sid.doctype,'1')='" + DocType.toString() + "'  and  sid.OrderNo =  '" + i.getStringExtra("OrderNo").toString() + "'";

        Double Pro = 0.0;
        Double Dis_Amt = 0.0;
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    ContactListItems contactListItems = new ContactListItems();

                    contactListItems.setno(c1.getString(c1
                            .getColumnIndex("itemNo")));
                    contactListItems.setName(c1.getString(c1
                            .getColumnIndex("Item_Name")));
                    if (ShowTax.equals("0")) {
                        contactListItems.setprice(c1.getString(c1
                                .getColumnIndex("OrgPrice")));
                    } else {
                        contactListItems.setprice(c1.getString(c1
                                .getColumnIndex("price")));

                    }

                    contactListItems.setQty(c1.getString(c1
                            .getColumnIndex("qty")));
                    contactListItems.setTax(c1.getString(c1
                            .getColumnIndex("tax_Amt")));
                    contactListItems.setUnite(c1.getString(c1
                            .getColumnIndex("UnitName")));
                    contactListItems.setTotal(c1.getString(c1
                            .getColumnIndex("total")));

                    contactListItems.setPro_dis_Per(c1.getString(c1
                            .getColumnIndex("Pro_dis_Per")));

                    contactListItems.setWeight(c1.getString(c1
                            .getColumnIndex("weight")));


                    Pro = Pro + (SToD(c1.getString(c1.getColumnIndex("Pro_dis_Per"))) / 100) * (SToD(c1.getString(c1.getColumnIndex("price"))) * SToD(c1.getString(c1.getColumnIndex("qty"))));
                    Dis_Amt = Dis_Amt + SToD(c1.getString(c1.getColumnIndex("dis_Amt")));
                    contactList.add(contactListItems);


                } while (c1.moveToNext());


            }


            c1.close();
        }


        TextView textView125 = (TextView) findViewById(R.id.textView125);
        TextView textView126 = (TextView) findViewById(R.id.textView126);
        TextView textView196 = (TextView) findViewById(R.id.textView196);


        TextView tv_Dis_Pro_amt = (TextView) findViewById(R.id.tv_Dis_Pro_amt);
        tv_Dis_Pro_amt.setText(SToD(Pro.toString()) + "");

        TextView tv_Dis_amt = (TextView) findViewById(R.id.tv_Dis_amt);
        tv_Dis_amt.setText(SToD(Dis_Amt.toString()) + "");

        if (SToD(tv_Dis_amt.getText().toString()) == 0.0 && SToD(tv_Dis_Pro_amt.getText().toString()) == 0.0) {
            tv_Dis_amt.setVisibility(View.INVISIBLE);
            tv_Dis_amt.setHeight(0);
            tv_Dis_Pro_amt.setVisibility(View.INVISIBLE);
            tv_Dis_Pro_amt.setHeight(0);

            textView125.setVisibility(View.INVISIBLE);
            textView125.setHeight(0);
            textView126.setVisibility(View.INVISIBLE);
            textView126.setHeight(0);
            textView196.setVisibility(View.INVISIBLE);
            textView196.setHeight(0);

        }


        LinearLayout Sal_ItemSLayout = (LinearLayout) findViewById(R.id.Sal_ItemSLayout);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;

        TextView tv_no;
        TextView tv_name;
        TextView tv_Price;
        TextView tv_Qty;
        TextView tv_Unit;
        TextView tv_tax;
        TextView tv_total;
        TextView tv_Weight;


        TextView tv_itemCount = (TextView) findViewById(R.id.tv_itemCount);
        tv_itemCount.setText(contactList.size() + "");
        for (ContactListItems Obj : contactList) {

            view = inflater.inflate(R.layout.sal_invoce_row_ukrania, null);

                /*if  (Company.equals("2")) {
                    view = inflater.inflate(R.layout.sal_invoce_row_1, null);
                }*/
            tv_no = (TextView) view.findViewById(R.id.tv_no);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_Qty = (TextView) view.findViewById(R.id.tv_Qty);
            tv_Price = (TextView) view.findViewById(R.id.tv_Price);
            tv_Unit = (TextView) view.findViewById(R.id.tv_Unit);
            tv_tax = (TextView) view.findViewById(R.id.tv_tax);
            tv_Weight = (TextView) view.findViewById(R.id.tv_Weight);
            tv_total = (TextView) view.findViewById(R.id.tv_total);


            tv_no.setText(Obj.getno());
            tv_name.setText(Obj.getName());
            tv_Price.setText(Obj.getprice());
            tv_Qty.setText(Obj.getQty());
            tv_Unit.setText(Obj.getUnite());
            tv_tax.setText(Obj.getTax());
            tv_Weight.setText(Obj.getWeight());
            tv_total.setText(Obj.getTotal());
            tv_tax.setText(Obj.getTax());
            tv_tax.setVisibility(View.VISIBLE);
            if (ShowTax.equals("0")) {
                tv_tax.setText("");
                tv_tax.setVisibility(View.INVISIBLE);

            }
            Sal_ItemSLayout.addView(view);

        }


        TextView textView130 = (TextView) findViewById(R.id.textView130);


        ArrayList<Cls_Offers_Hdr> Offer_Header_List = new ArrayList<Cls_Offers_Hdr>();
        Offer_Header_List.clear();
        query = " Select  distinct  Offer_Name, Offer_Date, Offer_Type  from  Offers_Hdr  ";
        c1 = sqlHandler.selectQuery(query);
        textView130.setText("");
        if (c1 != null && c1.getCount() != 0) {
            textView130.setText("العروض");
            if (c1.moveToFirst()) {
                do {

                    Cls_Offers_Hdr obj = new Cls_Offers_Hdr();

                    obj.setOffer_Name(c1.getString(c1
                            .getColumnIndex("Offer_Name")));
                    obj.setOffer_Date(c1.getString(c1
                            .getColumnIndex("Offer_Date")));
                    obj.setOffer_Type(c1.getString(c1
                            .getColumnIndex("Offer_Type")));
                    Offer_Header_List.add(obj);

                } while (c1.moveToNext());
            }
            c1.close();
        }

        LinearLayout Promotion_ItemSLayout = (LinearLayout) findViewById(R.id.Promotion_ItemSLayout);

        LayoutInflater Promotion_inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View Promotion_view;


        for (Cls_Offers_Hdr Obj : Offer_Header_List) {

            Promotion_view = Promotion_inflater.inflate(R.layout.sal_inv_pro_row, null);
            tv_name = (TextView) Promotion_view.findViewById(R.id.tv_name);

            tv_name.setText(Obj.getOffer_Name());

            Promotion_ItemSLayout.addView(Promotion_view);
        }


        FillCustomerMsg();

    }

    private void FillCustomerMsg() {
        String q;
        q = " Select  distinct    msg   from  CustomersMsg Where SaleManFlg ='4' ";// Where  SaleManFlg =4 Cusno = '' ";
        Cursor c1 = sqlHandler.selectQuery(q);
        ArrayList<Cls_InvoiceMsg> MsgList = new ArrayList<Cls_InvoiceMsg>();
        MsgList.clear();
        if (c1 != null && c1.getCount() != 0) {

            if (c1.moveToFirst()) {
                do {
                    Cls_InvoiceMsg obj = new Cls_InvoiceMsg();
                    obj.setMsg(c1.getString(c1
                            .getColumnIndex("msg")));
                    MsgList.add(obj);
                } while (c1.moveToNext());
            }
            c1.close();
        }

        LinearLayout MsgLayout = (LinearLayout) findViewById(R.id.MsgLayout);

        LayoutInflater Msg_inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View Msg_view;
        TextView tv_Msg;
        for (Cls_InvoiceMsg Obj : MsgList) {
            try {
                Msg_view = Msg_inflater.inflate(R.layout.msg_row_new, null);
                tv_Msg = (TextView) Msg_view.findViewById(R.id.tv_Msg);
                tv_Msg.setText(Obj.getMsg());
                MsgLayout.addView(Msg_view);
            } catch (Exception ex) {
            }
        }

    }

    public void btn_back(View view) {
        Intent i = new Intent(this, Sale_InvoiceActivity.class);
        startActivity(i);
    }
}
