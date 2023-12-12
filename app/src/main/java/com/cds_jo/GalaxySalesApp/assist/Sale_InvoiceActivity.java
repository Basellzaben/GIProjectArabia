package com.cds_jo.GalaxySalesApp.assist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.AlertChoiceItem;
import com.cds_jo.GalaxySalesApp.Cls_Man_Balanc;
import com.cds_jo.GalaxySalesApp.Cls_Offers_Dtl_Gifts;
import com.cds_jo.GalaxySalesApp.Cls_Offers_Groups;
import com.cds_jo.GalaxySalesApp.Cls_Offers_Hdr;
import com.cds_jo.GalaxySalesApp.Cls_Sal_Inv_Adapter;
import com.cds_jo.GalaxySalesApp.Cls_Sal_InvItems;
import com.cds_jo.GalaxySalesApp.ComInfo;
import com.cds_jo.GalaxySalesApp.Companies;
import com.cds_jo.GalaxySalesApp.ContactListItems;
import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.Delivery_VoucherAct;
import com.cds_jo.GalaxySalesApp.GlobaleVar;
import com.cds_jo.GalaxySalesApp.JalMasterActivity;
import com.cds_jo.GalaxySalesApp.MainActivity;
import com.cds_jo.GalaxySalesApp.ManBalanceQtyActivity;
import com.cds_jo.GalaxySalesApp.NewLoginActivity;
import com.cds_jo.GalaxySalesApp.PopEnterActQty;
import com.cds_jo.GalaxySalesApp.PopEnterInvoiceHeaderDiscount;
import com.cds_jo.GalaxySalesApp.PopSal_Inv_Select_Items;
import com.cds_jo.GalaxySalesApp.PopSal_return_Select_Items;
import com.cds_jo.GalaxySalesApp.PopShowCustDiscountDept;
import com.cds_jo.GalaxySalesApp.PopShowCustLastTrans;
import com.cds_jo.GalaxySalesApp.Pop_Confirm_New;
import com.cds_jo.GalaxySalesApp.Pop_Man_Vac;
import com.cds_jo.GalaxySalesApp.Pop_Payments_method;
import com.cds_jo.GalaxySalesApp.Pos.Pos_Activity;
import com.cds_jo.GalaxySalesApp.PostTransActions.PostSalesInvoice;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.RecvVoucherActivity;
import com.cds_jo.GalaxySalesApp.SCR_ACTIONS;
import com.cds_jo.GalaxySalesApp.Sal_Inv_SearchActivity;
import com.cds_jo.GalaxySalesApp.SearchManBalanceQty;
import com.cds_jo.GalaxySalesApp.Select_Cash_Customer;
import com.cds_jo.GalaxySalesApp.Select_Customer;
import com.cds_jo.GalaxySalesApp.Select_Drivers;
import com.cds_jo.GalaxySalesApp.SqlDbHelper;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.UpdateDataToMobileActivity;
import com.cds_jo.GalaxySalesApp.VisitImges;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.XprinterDoc.Xprinter_ManSummeryTo_img;
import com.cds_jo.GalaxySalesApp.XprinterDoc.Xprinter_SalesInvoice;
import com.cds_jo.GalaxySalesApp.XprinterDoc.Xprinter_SalesOrder;
import com.cds_jo.GalaxySalesApp.assist.Logtrans.InsertLogTrans;
import com.cds_jo.GalaxySalesApp.cls_Tab_Sales;
import com.cds_jo.GalaxySalesApp.myelectomic.Xprinter_SalesInvoice_ele;
import com.cds_jo.GalaxySalesApp.returnRequest;
import com.cds_jo.GalaxySalesApp.uptodate;
import com.google.gson.Gson;
//import com.sun.mail.imap.Quota;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import Methdes.MyTextView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import header.Header_Frag;
import header.SimpleSideDrawer;

public class Sale_InvoiceActivity extends AppCompatActivity implements note_manFrg.ExampleDialogListener {

    public int flagV;

    public String idfromjard;

    public String getmaxN() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String u = sharedPreferences.getString("UserID", "");
        EditText Maxpo = (EditText) findViewById(R.id.et_OrdeNo);
        // query = "SELECT  ifnull(MAX(OrderNo), 0) +1 AS no FROM Sal_invoice_Hdr where  ifnull(doctype,'1')='"+DocType.toString()+"'  and     UserID ='" + u.toString() + "'";
        query = "SELECT  COALESCE(MAX( cast(OrderNo as integer)), 0)  as  no FROM Sal_invoice_Hdr ";
        Cursor c1 = sqlHandler.selectQuery(query);
        String max = "0";

        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            max = c1.getString(c1.getColumnIndex("no"));

            /*basel alzaben*/
            c1.close();
        }

        String max1 = "0";


        String q = " SELECT  COALESCE(MAX( cast(Sales as integer)), 0) as Sales   from OrdersSitting    ";
        Cursor c = sqlHandler.selectQuery(q);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            max1 = c.getString(c.getColumnIndex("Sales"));
            c.close();
        }
        if (max1 == "") {
            max1 = "0";
        }

        if (SToD(max1) > SToD(max)) {
            max = max1;
        }

        return max;

    }

    TextView tv;
    RelativeLayout.LayoutParams lp;
    Drawable greenProgressbar;
    int ExistAfterSacve = 0;
    TextView et_Total1;
    SqlHandler sqlHandler;
    EditText Maxpo1;
    TextView custNm;
    String RecvV1;
    String note_sal = "";
    ListView lvCustomList;
    Integer DoPrint = 0, DocType = 1;
    String CatNo = "-1";
    Double OfferAmt;
    Double OfferAmt2;
    ArrayList<Cls_Sal_InvItems> contactList;
    ArrayList<Cls_Offers_Groups> offer_groups_List;
    ArrayList<Cls_Offers_Groups> offer_groups_Effict_List;
    ArrayList<Cls_Offers_Dtl_Gifts> cls_offers_dtl_giftses;
    public ArrayList<Cls_Offers_Hdr> cls_offers_hdrs;
    EditText etItemNm, etPrice, etQuantity, etTax;
    Button btnsubmit;
    Boolean IsNew;
    Boolean IsChange, BalanceQtyTrans;
    String UserID = "";
    public ProgressDialog loadingdialog;
    Double Hdr_Dis_A_Amt, Hdr_Dis_Per;
    EditText hdr_Disc;
    CheckBox chk_hdr_disc, chk_stopOffer;
    String MaxStoreQtySer = "0";
    Animation shake;
    int z;
    TextView textView99;
    EditText et_hdr_Disc;
    String query;
    String Celing;
    int AllowSalInvMinus;
    String f, Result;
    long PostResult = 0;
    CheckBox IncludeTax_Flag;
    CheckBox chk_cus_name;
    private ImageView Img_Menu;
    private SimpleSideDrawer mNav;
    CharSequence[] values = {"رمز تحقق ", "طلب موافقة المشرف"};

    private static AlertDialog alertDialog1;
    TextView tv_CustCelling;
    MyTextView tv_ScrTitle, DriverNm;
    EditText OrderNo;
    String ServerDate, DeviceDate;
    Intent BackInt;
    ImageButton update;
    TextView tv_NetTotal;
    MyTextView tv_HeaderDscount;
    double FinalDiscountpercent = 0.0, FinalDiscountAmt;
    String FinalDiscountType = "0";// 1 نسبة    2 مبلغ
    Button btn_Payment;
    String ff;

    String SCR_NO = "11002";
    Double DisAmt = 0.0;
    String DiscountDept = "-1";
    private static final String DIALOG_DATE = "date";

    private void HiddenHdrDiscount() {
        int flg;
        flg = Integer.parseInt(DB.GetValue(this, "ComanyInfo", "EnbleHdrDiscount", "1=1"));

        textView99 = (TextView) findViewById(R.id.textView99);
        et_hdr_Disc = (EditText) findViewById(R.id.et_hdr_Disc);
        chk_hdr_disc = (CheckBox) findViewById(R.id.chk_hdr_disc);

        if (flg == 1) {
            textView99.setVisibility(View.VISIBLE);
            et_hdr_Disc.setVisibility(View.VISIBLE);
            chk_hdr_disc.setVisibility(View.VISIBLE);
        } else {
            textView99.setVisibility(View.INVISIBLE);
            et_hdr_Disc.setVisibility(View.INVISIBLE);
            chk_hdr_disc.setVisibility(View.INVISIBLE);

        }


    }


    private Double SToD(String str) {
        str = str.replaceAll("[^\\d.]", "");
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

    public void GetMaxPONo() {
        Maxpo1 = (EditText) findViewById(R.id.et_OrdeNo);
//

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String update = preferences.getString("update", "0");

        // Toast.makeText(this,update,Toast.LENGTH_LONG).show();

  /*      final Handler _handler = new Handler();
        if(update.equals("1"))
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CallWebServices ws = new CallWebServices(Sale_InvoiceActivity.this);
                    ws.GetMaxOrder1(Integer.parseInt(UserID), 1);
                    try {
                        Integer i;
                        String q;
                        JSONObject js = new JSONObject(We_Result.Msg);
                        JSONArray js_MaxOrder = js.getJSONArray("MaxOrder");

                        Result = js_MaxOrder.get(0).toString();





                        _handler.post(new Runnable() {
                            public void run() {
                             //       Maxpo1.setText(intToString(Math.max(Integer.parseInt(getmaxN()),Integer.valueOf(Result)),7));
                           if(Integer.parseInt(getmaxN())>=Integer.valueOf(Result))
                                Maxpo1.setText(intToString(Integer.parseInt(getmaxN())+1,7));
                        else
                                Maxpo1.setText(intToString(Integer.valueOf(Result)+1,7));


                            }
                        });
                    } catch (final Exception e) {


                    }
                }
            }).start();


        }else {*/
        try {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String u = sharedPreferences.getString("UserID", "");
            EditText Maxpo = (EditText) findViewById(R.id.et_OrdeNo);
            // query = "SELECT  ifnull(MAX(OrderNo), 0) +1 AS no FROM Sal_invoice_Hdr where  ifnull(doctype,'1')='"+DocType.toString()+"'  and     UserID ='" + u.toString() + "'";
            query = "SELECT   COALESCE(MAX( cast(OrderNo as integer))+1, 0)  as  no FROM Sal_invoice_Hdr ";
            Cursor c1 = sqlHandler.selectQuery(query);
            String max = "0";

            if (c1 != null && c1.getCount() != 0) {
                c1.moveToFirst();
                max = c1.getString(c1.getColumnIndex("no"));


                c1.close();
            }

            String max1 = "0";


            String q = " SELECT  COALESCE(MAX( cast(Sales as integer))+1, 0) as Sales   from OrdersSitting    ";
            Cursor c = sqlHandler.selectQuery(q);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                max1 = c.getString(c.getColumnIndex("Sales"));
                c.close();
            }
            if (max1 == "") {
                max1 = "0";
            }

            if (SToD(max1) > SToD(max)) {
                max = max1;
            }

            if (ComInfo.ComNo != Companies.beutyLine.getValue()) {
                if (max.length() == 1) {

                    Maxpo.setText(intToString(Integer.valueOf(u), 2) + intToString(Integer.valueOf(max), 5));
                    Bundle bundle = new Bundle();
                    bundle.putString("Scr", "po");
                    bundle.putString("msg", "الرجاء الانتباه ، سيتم تخزين  الطلب برقم " + Maxpo.getText().toString());
                    FragmentManager Manager = getFragmentManager();
                    Pop_Confirm_Serial_From_Zero obj = new Pop_Confirm_Serial_From_Zero();
                    obj.setArguments(bundle);
                    obj.show(Manager, null);
                } else {

                    Maxpo.setText(intToString(Integer.valueOf(max), 7));
                }
            }
            //  Maxpo.setText(String.valueOf(Integer.valueOf(max)));
            // max1 = DB.GetValue(Sale_InvoiceActivity.this, "OrdersSitting", "Sales", "1=1");
            //Maxpo.setText(intToString(Integer.valueOf(max), 7));
            Maxpo.setFocusable(false);
            Maxpo.setEnabled(false);
            Maxpo.setCursorVisible(false);


            contactList.clear();
        } catch (Exception rd) {
            // }
            // }
        }
    }

    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
    }

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    public void updateManStore() {
        final String Ser = "1";
        String q;
        q = "Delete from ManStore";
        sqlHandler.executeQuery(q);
        q = "delete from sqlite_sequence where name='ManStore'";
        sqlHandler.executeQuery(q);

        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(Sale_InvoiceActivity.this);
                ws.TrnsferQtyFromMobile(UserID, "0", "");
                try {
                    Integer i;
                    String q = "";
                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray js_date = js.getJSONArray("date");
                    JSONArray js_fromstore = js.getJSONArray("fromstore");
                    JSONArray js_tostore = js.getJSONArray("tostore");
                    JSONArray js_des = js.getJSONArray("des");
                    JSONArray js_docno = js.getJSONArray("docno");
                    JSONArray js_itemno = js.getJSONArray("itemno");
                    JSONArray js_qty = js.getJSONArray("qty");
                    JSONArray js_UnitNo = js.getJSONArray("UnitNo");
                    JSONArray js_UnitRate = js.getJSONArray("UnitRate");
                    JSONArray js_myear = js.getJSONArray("myear");
                    JSONArray js_StoreName = js.getJSONArray("StoreName");
                    JSONArray js_RetailPrice = js.getJSONArray("RetailPrice");


                    for (i = 0; i < js_docno.length(); i++) {
                        q = "Insert INTO ManStore(SManNo,date,fromstore,tostore,des,docno,itemno,qty,UnitNo,UnitRate,myear,RetailPrice ,StoreName ,ser) values ("
                                + UserID.toString()
                                + ",'" + js_date.get(i).toString()
                                + "','" + js_fromstore.get(i).toString()
                                + "','" + js_tostore.get(i).toString()
                                + "','" + js_des.get(i).toString()
                                + "','" + js_docno.get(i).toString()
                                + "','" + js_itemno.get(i).toString()
                                + "','" + js_qty.get(i).toString()
                                + "','" + js_UnitNo.get(i).toString()
                                + "','" + js_UnitRate.get(i).toString()
                                + "','" + js_myear.get(i).toString()
                                + "','" + js_RetailPrice.get(i).toString()
                                + "','" + js_StoreName.get(i).toString()
                                + "'," + Ser.toString()
                                + " )";
                        sqlHandler.executeQuery(q);

                    }

                    //  Toast.makeText(getApplicationContext(),"تم التحديث",Toast.LENGTH_LONG).show();

                } catch (final Exception e) {
//Toast.makeText(getApplicationContext(),"لم يتم التحديث",Toast.LENGTH_LONG).show();
                }
            }
        }).start();
    }

    private void Fade_Fun(ImageButton myButton) {
        Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);

        myButton.setAnimation(animFadeOut);
        myButton.setAnimation(animFadeIn);
        myButton.setAnimation(animFadeOut);
        myButton.setAnimation(animFadeIn);

    }

    boolean checkItem, CheckGeroupQty, CheckGroupAmt = false;
    public ArrayList<Cls_Offers_Hdr> cls_offers_hdrsNew;
    ArrayList<AlertChoiceItem> itemList;
    AlertDialog.Builder builder;
    ContentValues cvv;
    CharSequence[] symtoms;
    Long SqlRes;
    ListView listView;
    android.support.v7.app.AlertDialog alertDialog2;
    EditText et_OrdeNo;
    TextView tv_acc;


    public void setmaxn(String maxn) {
        EditText et_OrdeNo = (EditText) findViewById(R.id.et_OrdeNo);
        et_OrdeNo.setText(maxn);

    }

    public String customerstate() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView tv_acc = (TextView) findViewById(R.id.tv_acc);
        TextView tv_CustStatus = (TextView) findViewById(R.id.tv_CustStatus);
        String q;
        if (ComInfo.ComNo == Companies.Atls.getValue()) {
            q = " select distinct     ifnull(Tax_Status,0) as Tax_Status , Location,ifnull(State,1) as State  " +
                    " from Customers_man where no='" + tv_acc.getText().toString() + "'";

        } else {
            q = " select distinct     ifnull(Tax_Status,0) as Tax_Status , Location,ifnull(State,1) as State  " +
                    " from Customers_man where no='" + sharedPreferences.getString("CustNo", "") + "'";
        }
        sqlHandler = new SqlHandler(Sale_InvoiceActivity.this);

        String State = "";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            State = c1.getString(c1.getColumnIndex("State"));
            c1.close();
        }
        /*if((State.equals("1") || State.equals("2") ||State.equals("2")))
            State="1";
*/
        if (State.equalsIgnoreCase("1")) {
            if (ComInfo.ComNo == 3) {
                tv_CustStatus.setText("  حالة العميل : فعال");
            } else {
                tv_CustStatus.setText("  حالة العميل : مفتوح");
            }

            tv_CustStatus.setTextColor(Color.GREEN);

        } else if (State.equalsIgnoreCase("2")) {
            if (ComInfo.ComNo == 3) {
                tv_CustStatus.setText("  حالة العميل : موقوف");
            } else {
                tv_CustStatus.setText("  حالة العميل : معلق");
            }


            tv_CustStatus.setTextColor(Color.BLUE);
        } else {

            if (ComInfo.ComNo == 3) {
                tv_CustStatus.setText("  حالة العميل : موقوف");
            } else {
                tv_CustStatus.setText("حالة العميل : ملغي");
            }

            tv_CustStatus.setTextColor(Color.RED);
        }


        return State;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_sale__invoice);
        update = (ImageButton) findViewById(R.id.update);


        customerstate();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.setTitle(sharedPreferences.getString("CompanyNm", "") + "/" + sharedPreferences.getString("Address", ""));
        // setContentView(R.layout.activity_sale__invoice);
        shake = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
        tv_ScrTitle = (MyTextView) findViewById(R.id.tv_ScrTitle);
        DocType = ComInfo.DocType;
        if (DocType < 1) {
            DocType = 1;
        }
        OrderNo = (EditText) findViewById(R.id.et_OrdeNo);
        custNm = (TextView) findViewById(R.id.tv_cusnm);
        HiddenHdrDiscount();
        //if necessary then call:
        BalanceQtyTrans = false;
        try {
            trimCache(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        flagV = 0;
        Fragment frag = new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1, frag).commit();
        AllowSalInvMinus = Integer.parseInt(DB.GetValue(this, "ComanyInfo", "AllowSalInvMinus", "1=1"));
        sqlHandler = new SqlHandler(this);
        tv_CustCelling = (TextView) findViewById(R.id.tv_CustCelling);
        CheckBox chk_showTax = (CheckBox) findViewById(R.id.chk_showTax);
        chk_showTax.setTypeface(Typeface.createFromAsset(this.getAssets(), "Hacen Tunisia Lt.ttf"));
        chk_showTax.setChecked(true);

        et_OrdeNo = (EditText) findViewById(R.id.et_OrdeNo);
        IncludeTax_Flag = (CheckBox) findViewById(R.id.chk_Tax_Include);
        IncludeTax_Flag.setTypeface(Typeface.createFromAsset(this.getAssets(), "Hacen Tunisia Lt.ttf"));
        IncludeTax_Flag.setChecked(true);

        chk_cus_name = (CheckBox) findViewById(R.id.chk_cus_name);
        chk_stopOffer = (CheckBox) findViewById(R.id.chk_stopOffer);
        chk_cus_name.setTypeface(Typeface.createFromAsset(this.getAssets(), "Hacen Tunisia Lt.ttf"));

        lvCustomList = (ListView) findViewById(R.id.LstvItems);
        sqlHandler = new SqlHandler(this);
        ComInfo.ComNo = Integer.parseInt(DB.GetValue(this, "ComanyInfo", "CompanyID", "1=1"));
        // Spinner sp_Invo_Type =(Spinner)findViewById(R.id.sp_Invo_Type);
        contactList = new ArrayList<Cls_Sal_InvItems>();
        contactList.clear();

        cls_offers_dtl_giftses = new ArrayList<Cls_Offers_Dtl_Gifts>();
        cls_offers_dtl_giftses.clear();
        cls_offers_hdrs = new ArrayList<Cls_Offers_Hdr>();
        cls_offers_hdrs.clear();

        offer_groups_List = new ArrayList<Cls_Offers_Groups>();
        offer_groups_List.clear();


        cls_offers_hdrsNew = new ArrayList<Cls_Offers_Hdr>();
        cls_offers_hdrsNew.clear();

        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
        chk_Type.setTypeface(Typeface.createFromAsset(this.getAssets(), "Hacen Tunisia Lt.ttf"));
        chk_Type.setChecked(true);
        chk_stopOffer.setChecked(true);

        if (ComInfo.ComNo == 2) {
            //chk_Type.setChecked(true);
            IncludeTax_Flag.setChecked(false);
            IncludeTax_Flag.setVisibility(View.INVISIBLE);
        }

        fill_Offers_Group();

        //btn_Payment=(Button) findViewById(R.id.Lytbutton);
        offer_groups_Effict_List = new ArrayList<Cls_Offers_Groups>();
        offer_groups_Effict_List.clear();

        IsNew = true;


        // try {
        mNav = new SimpleSideDrawer(this);
        mNav.setLeftBehindContentView(R.layout.sal_inv_menu);
        Img_Menu = (ImageView) findViewById(R.id.Img_Menu);
        Img_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNav.toggleLeftDrawer();
            }


        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("maxn", et_OrdeNo.getText().toString());
                bundle.putString("activit", "activit1");

                FragmentManager Manager = (Sale_InvoiceActivity.this).getFragmentManager();
                uptodate popShowOffers = new uptodate();
                popShowOffers.setArguments(bundle);
                popShowOffers.show(Manager, null);
            }


        });

        //btn_Payment.setOnClickListener(new View.OnClickListener() {

           /* public void btn_Payment(View v) {
                FragmentManager fm = getFragmentManager();
                Pop_Payments_method dialog = new Pop_Payments_method();
                //dialog.show(fm, DIALOG_DATE);
            }*/
        //});

      /*  FragmentManager Frag_manger = getFragmentManager();
        Pop_Payments_method Payment_dialog = new Pop_Payments_method();
        Payment_dialog.show(Frag_manger, null);*/



       /* }catch (Exception ex ){
            Toast.makeText(this,ex.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }*/
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        UserID = sharedPreferences.getString("UserID", "");
        IsNew = true;
        IsChange = false;

        final List<String> items_cat = new ArrayList<String>();
        items_cat.add("نوع الفاتورة");
        items_cat.add("نقدي");
        items_cat.add("ذمم");
        ArrayAdapter<String> Inoc_Type_cat = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items_cat);
        // sp_Invo_Type.setAdapter(Inoc_Type_cat);

        hdr_Disc = (EditText) findViewById(R.id.et_hdr_Disc);
        hdr_Disc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hdr_Disc.setText("", TextView.BufferType.EDITABLE);
                }
            }
        });


        Hdr_Dis_A_Amt = 0.0;
        Hdr_Dis_Per = 0.0;

        chk_hdr_disc = (CheckBox) findViewById(R.id.chk_hdr_disc);
        final TextView et_Total = (TextView) findViewById(R.id.et_Total);
        et_Total1 = (TextView) findViewById(R.id.et_Total);
        hdr_Disc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (hdr_Disc.getText().toString().equals("") || hdr_Disc.getText().toString().equals("."))
                    return;
                else if (SToD(hdr_Disc.getText().toString()) > 100 && chk_hdr_disc.isChecked()) {
                    hdr_Disc.setText("100");
                    return;
                } else if ((SToD(hdr_Disc.getText().toString()) > SToD(et_Total.getText().toString())) && chk_hdr_disc.isChecked() == false) {
                    hdr_Disc.setText(et_Total.getText());
                    return;
                }
                //   Calc_Dis_Hdr();


            }
        });


        chk_hdr_disc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (hdr_Disc.getText().toString().equals("") || hdr_Disc.getText().toString().equals("."))
                    return;
                if (hdr_Disc.getText().toString().equals("") || hdr_Disc.getText().toString().equals("."))
                    return;
                else if (SToD(hdr_Disc.getText().toString()) > 100 && chk_hdr_disc.isChecked()) {
                    hdr_Disc.setText("100");
                    return;
                } else if ((SToD(hdr_Disc.getText().toString()) > SToD(et_Total.getText().toString())) && chk_hdr_disc.isChecked() == false) {
                    hdr_Disc.setText(et_Total.getText());
                    return;
                }

            }
        });
        if (ComInfo.ComNo == Companies.Afrah.getValue()) {
            //chk_Type.setChecked(true);
            IncludeTax_Flag.setChecked(false);
            IncludeTax_Flag.setEnabled(false);
        }


        IncludeTax_Flag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CalcTotal();
                showList();


            }
        });

        GetMaxPONo();

        TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);

        final TextView accno = (TextView) findViewById(R.id.tv_acc);
        accno.setText(sharedPreferences.getString("CustNo", ""));
        CustNm.setText(sharedPreferences.getString("CustNm", ""));


        Get_CatNo(accno.getText().toString());

        GetStoreQtySer();
        Bundle extras = getIntent().getExtras();


/*

       String flg = String.valueOf(Integer.parseInt(DB.GetValue(this, "Customers", "BB_bill", "no='" + UserID +" '")));

       Toast.makeText(Sale_InvoiceActivity.this,flg.toString() +"  gghgh  ",Toast.LENGTH_SHORT).show();

*/

        ///////////////////////////////////////////////////
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

       /* if (ComInfo.ComNo == 1) {

            String Count = sharedPreferences.getString("InvCount", "0");
            String NumOfInvPerVisit = DB.GetValue(Sale_InvoiceActivity.this, "ComanyInfo", "NumOfInvPerVisit  ", "1=1");

            if ((SToD(Count) >= SToD(NumOfInvPerVisit)) && IsNew == true && BalanceQtyTrans == false) {
                alertDialog = new AlertDialog.Builder(
                        this).create();
                alertDialog.setTitle("فاتورة مبيعات");
                alertDialog.setMessage("يجب فتح جولة جديدة حتى تتمكن من تنفيذ هذ العملية");

                alertDialog.setIcon(R.drawable.delete);
                alertDialog.setButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();

            }
        }*/
        ///////////////////////////////////////////////////////
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());
        String RecVoucher_DocNo = DB.GetValue(Sale_InvoiceActivity.this, "RecVoucherSeles", "DocNo", "CustAcc ='" + accno.getText() + "' AND ((TrDate)=('" + currentDateandTime + "'))");
        String PAMENT_PERIOD_NO = DB.GetValue(Sale_InvoiceActivity.this, "Customers_man", "PAMENT_PERIOD_NO", "no ='" + accno.getText() + "' ");

        /*if (RecVoucher_DocNo == "-1" && PAMENT_PERIOD_NO.equals("0")) {
            AlertDialog alert_Dialog = new AlertDialog.Builder(this).create();
            alert_Dialog.setTitle(tv_ScrTitle.getText().toString());
            alert_Dialog.setMessage("يجب عمل سند قبض اولاَ ، لان نوع العميل فاتورة بفاتورة");
            alert_Dialog.setIcon(R.drawable.delete);
            alert_Dialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert_Dialog.show();
            return;

        }*/
        String CUST_PRV_MONTH = DB.GetValue(Sale_InvoiceActivity.this, "Customers_man", "CUST_PRV_MONTH", "no ='" + accno.getText() + "' ");

        if (PAMENT_PERIOD_NO.equals("1") && SToD(CUST_PRV_MONTH) > 0) {
            AlertDialog alert_Dialog = new AlertDialog.Builder(this).create();
            alert_Dialog.setTitle(tv_ScrTitle.getText().toString());
            alert_Dialog.setMessage("    نوع العميل اغلاق الذمة شهريا ، يجب تسديد القيمة :" + "  " + CUST_PRV_MONTH);
            alert_Dialog.setIcon(R.drawable.delete);
            alert_Dialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert_Dialog.show();
            return;

        }


        sqlHandler = new SqlHandler(this);
        query = "SELECT *  from  Sal_invoice_Hdr where  ifnull(doctype,'1')='" + DocType.toString() + "'  and   Post ='-1' And ( (date)   <  ('" + currentDateandTime + "'))  ";

        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.getCount() > 0) {
                /*  c1.moveToFirst();*/
                AlertDialog alert_Dialog = new AlertDialog.Builder(this).create();
                alert_Dialog.setTitle(tv_ScrTitle.getText().toString());
                alert_Dialog.setMessage("يوجد فواتير بتاريخ سابق غير مرحلة ، الرجاء التاكد من الحركات الغير مرحلة في شاشة الملخص اليومي");
                alert_Dialog.setIcon(R.drawable.delete);
                alert_Dialog.setButton("موافق", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert_Dialog.show();


            }

            c1.close();
        }

        if (ComInfo.ComNo == 3) {
            chk_Type.setChecked(true);
            IncludeTax_Flag.setChecked(false);
            chk_showTax.setChecked(true);
            IncludeTax_Flag.setVisibility(View.INVISIBLE);
        }
        if (ComInfo.ComNo == 12) {
            chk_showTax.setChecked(true);
        }


      /*  Cls_AutoPosting obj =  new Cls_AutoPosting(Sale_InvoiceActivity.this,Sale_InvoiceActivity.this,1);
        obj.DoPost();*/
        CustAmtDt();

        Bundle bundle = new Bundle();


        FragmentManager Manager = getFragmentManager();
        PopShowCustLastTrans popShowOffers = new PopShowCustLastTrans();
        popShowOffers.setArguments(bundle);
        popShowOffers.show(Manager, null);

        /*    FragmentManager Manager = getFragmentManager();
            PopShowCustDiscountDept popShowCustDiscountDept = new PopShowCustDiscountDept();
            popShowCustDiscountDept.setArguments(bundle);
            popShowCustDiscountDept.show(Manager, null);*/


        if (ComInfo.ComNo == Companies.Atls.getValue()) {
            //chk_Type.setChecked(true);
            //  SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String cash_bill = DB.GetValue(this, "manf", "PosAcc", "man ='" + sharedPreferences.getString("UserID", "0") + "'");

            accno.setText(cash_bill);
            CustNm.setText("حساب نقدي");
        }

        DriverNm = (MyTextView) mNav.findViewById(R.id.tv_DriverNm);
        tv_NetTotal = (TextView) mNav.findViewById(R.id.tv_NetTotal);
        // TextView et_Total1 = (TextView) mNav.findViewById(R.id.et_Total);
//        TextView et_dis = (TextView) mNav.findViewById(R.id.et_dis);
//        if (!(et_Total.getText().toString().equalsIgnoreCase("" )&& et_dis.getText().toString().equalsIgnoreCase("") ))
//        {
//            et_Total.setText(0);
//            et_dis.setText(0);
//            z = Integer.parseInt(et_Total.getText().toString()) - Integer.parseInt(et_dis.getText().toString());
//        }
        tv_HeaderDscount = (MyTextView) mNav.findViewById(R.id.tv_HeaderDscount);

        LinearLayout LytHeaderDiscount = (LinearLayout) mNav.findViewById(R.id.LytHeaderDiscount);
        LytHeaderDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                //  bundle.putStringArrayList("Product",contactList);
                bundle.putString("OrederNo", et_OrdeNo.getText().toString());
                bundle.putString("Discount", "0");
                if (IncludeTax_Flag.isChecked()) {
                    bundle.putString("NetTotal", tv_NetTotal.getText().toString());
                } else {
                    bundle.putString("NetTotal", et_Total.getText().toString());
                }
                FragmentManager Manager = getFragmentManager();
                PopEnterInvoiceHeaderDiscount obj = new PopEnterInvoiceHeaderDiscount();
                obj.setArguments(bundle);
                obj.show(Manager, null);
            }
        });


        LinearLayout LytDriver = (LinearLayout) mNav.findViewById(R.id.LytDriver);
        LytDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String q = " SELECT distinct *  from  Sal_invoice_Hdr where  ifnull(doctype,'1')='" + DocType.toString() + "'" +
                        " And   OrderNo ='" + OrderNo.getText().toString() + "'";

                Cursor c1 = sqlHandler.selectQuery(q);
                if (c1 != null && c1.getCount() != 0) {
                    c1.close();
                    Bundle bundle = new Bundle();
                    bundle.putString("Scr", "Sale_Inv");
                    FragmentManager Manager = getFragmentManager();
                    Select_Drivers obj = new Select_Drivers();
                    obj.setArguments(bundle);
                    obj.show(Manager, null);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            Sale_InvoiceActivity.this).create();
                    alertDialog.setTitle(tv_ScrTitle.getText().toString());
                    alertDialog.setMessage("يجب التخزين  اولاّ");
                    alertDialog.setIcon(R.drawable.delete);
                    alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    alertDialog.show();

                    return;
                }


            }
        });

        LinearLayout LytPayment = (LinearLayout) mNav.findViewById(R.id.Lytbutton);
        LytPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText Order = (EditText) findViewById(R.id.et_OrdeNo);
                tv_NetTotal = (TextView) mNav.findViewById(R.id.tv_NetTotal);
                Bundle bundle = new Bundle();
                bundle.putString("Scr", "Sale_Inv");
                bundle.putString("OrderNo", Order.getText().toString());
                bundle.putString("custNo", accno.getText().toString());
                bundle.putString("Net_Total", tv_NetTotal.getText().toString());
                FragmentManager Manager = getFragmentManager();
                Pop_Payments_method obj = new Pop_Payments_method();
                obj.setArguments(bundle);
                obj.show(Manager, null);

            }
        });
        LinearLayout Lytbutton1 = (LinearLayout) mNav.findViewById(R.id.Lytbutton1);
        Lytbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText Order = (EditText) findViewById(R.id.et_OrdeNo);
                GlobaleVar.orderno = Order.getText().toString();
                note_manFrg exampleDialog = new note_manFrg();
                exampleDialog.show(getFragmentManager(), "example dialog");

            }
        });

        ServerDate = DB.GetValue(this, "ServerDateTime", "ServerDate", "1=1");

        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        DeviceDate = sdf.format(new Date());
        String msg = "تاريخ التابلت غير مطابق لتاريخ السيرفر ، الرجاء تحديث بيانات المؤسسة" + "\r\n";
        msg = msg + "تاريخ التابلت  : " + DeviceDate + "\r\n" + "تاريخ السيرفر : " + ServerDate;
        if ((!ServerDate.equalsIgnoreCase(DeviceDate)) && ComInfo.ComNo == 4) {

            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("فاتورة المبيعات");
            alertDialog.setCancelable(false);
            alertDialog.setMessage(msg);
            alertDialog.setIcon(R.drawable.error_new);
            alertDialog.setButton("رجـــــــوع", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    BackInt = new Intent(Sale_InvoiceActivity.this, JalMasterActivity.class);
                    startActivity(BackInt);
                }
            });
            alertDialog.show();

        }
        if (ComInfo.ComNo == Companies.beutyLine.getValue()) {

            IncludeTax_Flag.setChecked(false);
            IncludeTax_Flag.setVisibility(View.INVISIBLE);
            chk_showTax.setChecked(true);
            chk_showTax.setVisibility(View.INVISIBLE);
            chk_cus_name.setChecked(true);

        }
        chk_cus_name.setChecked(true);
        tv_acc = (TextView) findViewById(R.id.tv_acc);


        clearList();
        try {
            if (extras.getString("BalanceQtyOrderNo") != "") {
                String acc;
                String Acc_name;
                acc = DB.GetValue(this, "manf", "Acc", "  man='" + sharedPreferences.getString("UserID", "") + "'");
                Acc_name = DB.GetValue(this, "manf", "AccName", "  man='" + sharedPreferences.getString("UserID", "") + "'");
                flagV = 1;
                accno.setText(acc);
                CustNm.setText(Acc_name);
                InsertBalanceQty(extras.getString("BalanceQtyOrderNo"), "");
            }
            if (extras.getString("from").equals("0")) {
                chk_Type.setChecked(false);
                chk_Type.setEnabled(false);
                UserID = sharedPreferences.getString("UserID", "");
                String name = DB.GetValue(Sale_InvoiceActivity.this, "manf", "MEName", "man='" + UserID + "'");
                CustNm.setText(name);
                TextView tv_CustStatus = (TextView) findViewById(R.id.tv_CustStatus);
                tv_CustStatus.setText("  حالة العميل : فعال");
                tv_CustStatus.setTextColor(Color.BLUE);
            }


            idfromjard = extras.getString("id");

        } catch (Exception ex) {
            idfromjard = "فاتورة بيع";
        }

    }

    public void InsertDiscount(String DiscountAmt, String DiscountType) {
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        String q = "SELECT distinct *  from  Sal_invoice_Hdr where    ifnull(doctype,'1')='" + DocType.toString() + "'  and   Post >0 AND   OrderNo ='" + pono.getText().toString() + "'";

        TextView acc = (TextView) findViewById(R.id.tv_acc);
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(tv_ScrTitle.getText().toString());
            alertDialog.setMessage("لقد تم ترحيل المستند لايمكن التعديل");
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (ExistAfterSacve == 1) {
                        ExistAfterSacve = 0;
                        Intent k = new Intent(Sale_InvoiceActivity.this, JalMasterActivity.class);
                        startActivity(k);
                    }
                    if (DoPrint == 1) {
                        View view = null;
                        btn_print(view);
                    }

                }
            });

            alertDialog.show();


            c1.close();
            return;
        }
        {


            double ItemWieght = 0.0;

            for (int x = 0; x < contactList.size(); x++) {
                // ItemWieght=((SToD( contactList.get(x).getTotal())/SToD(tv_NetTotal.getText().toString()))*100);
                // contactList.get(x).setDisPerFromHdr((((ItemWieght*FinalDiscountpercent)/100))+"");
                contactList.get(x).setDisPerFromHdr("0");
                contactList.get(x).setDisAmtFromHdr("0");
            }

            CalcTotal();
            showList();
            if (DiscountType.equalsIgnoreCase("1")) {
                FinalDiscountpercent = SToD(DiscountAmt);
                FinalDiscountAmt = ((SToD(DiscountAmt) / 100) * SToD(et_Total1.getText().toString()));
                FinalDiscountAmt = SToD(FinalDiscountAmt + "");
            } else {
                if (IncludeTax_Flag.isChecked()) {
                    FinalDiscountpercent = SToD(DiscountAmt) / SToD(tv_NetTotal.getText().toString());
                    FinalDiscountpercent = FinalDiscountpercent * 100;
                    FinalDiscountAmt = SToD(DiscountAmt);
                    FinalDiscountpercent = SToD(FinalDiscountpercent + "");
                } else {
                    FinalDiscountpercent = SToD(DiscountAmt) / SToD(et_Total1.getText().toString());
                    FinalDiscountpercent = FinalDiscountpercent * 100;
                    FinalDiscountAmt = SToD(DiscountAmt);
                    FinalDiscountpercent = SToD(FinalDiscountpercent + "");
                }
            }
            FinalDiscountType = DiscountType;
            tv_HeaderDscount.setText(SToD((FinalDiscountpercent) + "") + "%");


            if (IncludeTax_Flag.isChecked()) {

                for (int x = 0; x < contactList.size(); x++) {

                    contactList.get(x).setDisPerFromHdr(FinalDiscountpercent + "");
                    contactList.get(x).setDisAmtFromHdr(((FinalDiscountpercent * (SToD(tv_NetTotal.getText().toString()))) / 100) + "");

                }
            } else {

                for (int x = 0; x < contactList.size(); x++) {

                    contactList.get(x).setDisPerFromHdr(FinalDiscountpercent + "");
                    contactList.get(x).setDisAmtFromHdr(((FinalDiscountpercent * (SToD(et_Total1.getText().toString()))) / 100) + "");

                }
            }

            CalcTotal();
            showList();

            Save_Recod_Po();
        }
    }

    private void CustAmtDt() {
        TextView accno = (TextView) findViewById(R.id.tv_acc);
        EditText Maxpo = (EditText) findViewById(R.id.et_OrdeNo);
        TextView tv_NetTotal = (TextView) findViewById(R.id.tv_CustNetTotal);
        String NetTotal = DB.GetValue(Sale_InvoiceActivity.this, "Customers", "CUST_NET_BAL", "no ='" + accno.getText() + "' ");

        String CustCelling = DB.GetValue(Sale_InvoiceActivity.this, "Customers", "Celing", "no ='" + accno.getText() + "' ");
        String TotalRet = DB.GetValue(Sale_InvoiceActivity.this, "Sal_return_Hdr", "ifnull(sum(Net_Total),0)", "acc ='" + accno.getText().toString() + "' and Post = '-1' ");
        String CUST_NET_BAL = NetTotal;

        if (CustCelling.equalsIgnoreCase("-1")) {
            CustCelling = "0";
        }

        if (CUST_NET_BAL.equalsIgnoreCase("-1")) {
            CUST_NET_BAL = "0";
        }






    /*    if (NetTotal.equalsIgnoreCase("-1")) {
            NetTotal = "غير مدخله";
        }
        tv_NetTotal.setText(NetTotal + "");*/

        Double Total2 = Double.parseDouble(DB.GetValue(this, "Customers", "BB_bill", "no='" + accno.getText() + "'"));
        String q = "Select distinct   ifnull( sum(ifnull(s.Net_Total,0.000)),0.000)   as Amt    " +
                "  from  Sal_invoice_Hdr s    where    ifnull(s.doctype,'1')='" + DocType.toString() + "'  and  " +
                "  s.acc='" + accno.getText() + "' and inovice_type ='-1'   and  s.Post ='-1'";// and   s.OrderNo !='"+Maxpo.getText()+"'"; //and s.inovice_type = '-1'

        String UnpostedSales = "0.000";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                UnpostedSales = c1.getString(c1.getColumnIndex("Amt"));
            }
            c1.close();
        }


        q = "  Select distinct    ifnull( sum(ifnull(RecVoucher.Amnt,0.000)),0.000)     as Amt   from RecVoucher " +
                " where  RecVoucher.CustAcc ='" + accno.getText() + "' and  RecVoucher.Post ='-1'";

        String UnpostedRecVoucher = "0.000";
        c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                UnpostedRecVoucher = c1.getString(c1.getColumnIndex("Amt"));
            }
            c1.close();
        }
        if (ComInfo.ComNo == Companies.Atls.getValue()) {

            tv_NetTotal.setText("0");
        } else {
            tv_NetTotal.setText(String.format(
                    Locale.ENGLISH, "%.3f", ((Total2 + Double.parseDouble(UnpostedSales)) - Double.parseDouble(UnpostedRecVoucher) - Double.parseDouble(TotalRet))) + "");

        }

//moh123
        //  Double Total = SToD(CustCelling) - SToD(CUST_NET_BAL)+SToD(UnpostedRecVoucher )- SToD(UnpostedSales) ;
        Double Total = Double.parseDouble(DB.GetValue(this, "Customers", "Celing", "no='" + accno.getText() + "'"));
        if (Total < 0)
            Total = 0.0; // Total * -1;

        tv_CustCelling.setText(SToD(Total + "") + "");

        MyTextView Nav_CustTotal = (MyTextView) mNav.findViewById(R.id.tv_CustTotal);
        MyTextView Nav_Celing = (MyTextView) mNav.findViewById(R.id.tv_Celing);
        MyTextView Nav_UnpostedSales = (MyTextView) mNav.findViewById(R.id.tv_UnpostedSales);
        MyTextView Nav_UnpostedPayment = (MyTextView) mNav.findViewById(R.id.tv_UnpostedPayment);
        MyTextView Nav_NetCelling = (MyTextView) mNav.findViewById(R.id.tv_NetCelling);


        Nav_CustTotal.setText(CUST_NET_BAL + "");
        Nav_Celing.setText(CustCelling + "");
        Nav_UnpostedSales.setText(UnpostedSales + "");
        Nav_UnpostedPayment.setText(UnpostedRecVoucher + "");
        Nav_NetCelling.setText(SToD(Total + "") + "");


    }

    private void Get_CatNo(String ACC_NO) {
        SqlHandler sqlHandler = new SqlHandler(this);
        String q = "Select  distinct ifnull( CatNo,0) as catno from Customers where no = '" + ACC_NO + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.getCount() > 0) {
                c1.moveToFirst();
                CatNo = c1.getString(c1.getColumnIndex("catno"));
            }
            c1.close();
        }

    }

    private void GetStoreQtySer() {
        SqlHandler sqlHandler = new SqlHandler(this);
        String query = "SELECT  distinct COALESCE(MAX(ser), 0)   AS no from ManStore";
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.getCount() > 0) {
                c1.moveToFirst();
                MaxStoreQtySer = String.valueOf(c1.getInt(0));
            }
            c1.close();
        }

    }
  /*  private void Calc_Dis_Hdr() {

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;

        if (hdr_Disc.getText().toString().equals(""))
            Hdr_Dis_Per = 0.0;
        else
            Hdr_Dis_Per = Double.parseDouble(hdr_Disc.getText().toString().replace(",", ""));

        TextView et_Total = (TextView) findViewById(R.id.et_Total);
        Cls_Sal_InvItems contactListItems;
        if (chk_hdr_disc.isChecked()) {
            Hdr_Dis_A_Amt = SToD(et_Total.getText().toString()) * (Hdr_Dis_Per / 100);
            for (int x = 0; x < contactList.size(); x++) {


                contactListItems = new Cls_Sal_InvItems();
                contactListItems = contactList.get(x);
                //if( contactListItems.getProID().toString().equals("")) {
                Double NewPercent = 0.0;
                Double NewAmt = 0.0;
                NewPercent = Hdr_Dis_Per;
                // NewAmt = (NewPercent / 100) * Double.parseDouble(contactListItems.getTotal().replace(",", ""));
                NewAmt = (NewPercent / 100) * (SToD(contactListItems.getprice()) * SToD(contactListItems.getQty()));


                contactListItems.setDisPerFromHdr((NewPercent).toString());
                contactListItems.setDisAmtFromHdr((NewAmt).toString());


              *//*  }
                else {
                      contactListItems.setDisPerFromHdr("0");
                      contactListItems.setDisAmtFromHdr("0");
                  }*//*
            }

        } else {

            for (int x = 0; x < contactList.size(); x++) {
                contactListItems = new Cls_Sal_InvItems();
                contactListItems = contactList.get(x);
                Double NewPercent = 0.0;
                Double NewAmt = 0.0;
                // if( contactListItems.getProID().toString().equals("")) {
                NewAmt = Hdr_Dis_Per;
                if (contactListItems.getTotal().equals("0")) {
                    contactListItems.setDisPerFromHdr("0");
                    contactListItems.setDisAmtFromHdr("0");
                } else {
                    // NewPercent = NewAmt / Double.parseDouble(contactListItems.getTotal().replace(",", ""));
                    if ((SToD(contactListItems.getprice()) * SToD(contactListItems.getQty()) == 0)) {
                        NewPercent = 0.0;

                    } else {
                        NewPercent = (NewAmt / (SToD(contactListItems.getprice()) * SToD(contactListItems.getQty())) * 100);
                    }
                    contactListItems.setDisPerFromHdr(SToD(NewPercent.toString()).toString());
                    contactListItems.setDisAmtFromHdr(SToD(NewAmt.toString()).toString());
                }


            }

        }
        CalcTotal();
        //    showList();

    }*/

    private void fill_Offers_Group() {
        String query = " select * from Offers_Groups";
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Offers_Groups cls_offers_groups = new Cls_Offers_Groups();
                    cls_offers_groups.setID(c1.getString(c1
                            .getColumnIndex("ID")));
                    cls_offers_groups.setGrv_no(c1.getString(c1
                            .getColumnIndex("grv_no")));
                    cls_offers_groups.setGro_ename(c1.getString(c1
                            .getColumnIndex("gro_name")));
                    cls_offers_groups.setGro_type(c1.getString(c1
                            .getColumnIndex("gro_type")));
                    cls_offers_groups.setItem_no(c1.getString(c1
                            .getColumnIndex("item_no")));
                    cls_offers_groups.setUnit_no(c1.getString(c1
                            .getColumnIndex("unit_no")));
                    cls_offers_groups.setQty(c1.getString(c1
                            .getColumnIndex("qty")));
                    cls_offers_groups.setSerNo(c1.getString(c1
                            .getColumnIndex("SerNo")));

                    offer_groups_List.add(cls_offers_groups);

                } while (c1.moveToNext());
            }
            c1.close();
        }

    }


    public Double getqty(String itemno) {

        Double qty = 0.0;

        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getNo().equals(itemno))
                qty += Double.parseDouble(contactList.get(i).getQty()) * Double.parseDouble(contactList.get(i).getOperand());
            else
                continue;
        }


        return qty;
    }

    public Double getbounc(String itemno) {

        Double bounc = 0.0;

        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getNo().equals(itemno))
                bounc += Double.parseDouble(contactList.get(i).getBounce()) * Double.parseDouble(contactList.get(i).getOperand());
            else
                continue;
        }


        return bounc;
    }

    public Double getbounc1(String itemno) {

        Double bounc = 0.0;

        for (int i = 0; i < contactList.size(); i++) {

            bounc += Double.parseDouble(contactList.get(i).getBounce()) * Double.parseDouble(contactList.get(i).getOperand());

        }


        return bounc;
    }

    private void clearList() {

        lvCustomList.setAdapter(null);
    }

    private void showList() {

        lvCustomList.setAdapter(null);
        Cls_Sal_Inv_Adapter contactListAdapter = new Cls_Sal_Inv_Adapter(
                Sale_InvoiceActivity.this, contactList);
        lvCustomList.setAdapter(contactListAdapter);
        //  json = new Gson().toJson(contactList);
    }

    private void FillAdapter() {
        contactList.clear();
        float Total = 0;
        float Total_Tax = 0;
        float TTemp = 0;
        float PTemp = 0;
        float PQty = 0;
        String query = "";
        TextView etTotal, et_Tottal_Tax;

        etTotal = (TextView) findViewById(R.id.et_Total);
        et_Tottal_Tax = (TextView) findViewById(R.id.et_TotalTax);
        etTotal.setText(String.valueOf(Total));
        et_Tottal_Tax.setText(String.valueOf(Total_Tax));


        sqlHandler = new SqlHandler(this);


        EditText Order_no = (EditText) findViewById(R.id.et_OrdeNo);
        query = "  select distinct  ifnull(pod.DisAmtFromHdr,0)   as   DisAmtFromHdr , ifnull(pod.DisPerFromHdr,0)   as DisPerFromHdr  , ifnull(pod.weight,0)     as weight,    ifnull(pod.Pro_Type,0)     as Pro_Type,        ifnull(pod.Operand,0) as Operand  ,  ifnull(pod.bounce_qty,0) as bounce_qty    ,pod.dis_per , pod.dis_Amt , pod.OrgPrice , pod.tax_Amt , pod.total ,Unites.UnitName,  invf.Item_Name, pod.itemno,pod.price,pod.qty,pod.tax ,pod.unitNo,pod.sample  " +
                " , pod.pro_Total    , pod.ProID , pod.Pro_bounce  ,pod.Pro_dis_Per,  pod.Pro_amt    " +
                " from Sal_invoice_Det pod left join invf on invf.Item_No =  pod.itemno   " +
                " left join Unites on Unites.Unitno=  pod.unitNo  Where   ifnull(pod.doctype,'1')='" + DocType.toString() + "'  and pod.OrderNo='" + Order_no.getText().toString().replace("\u202c", "").replace("\u202d", "") + "'  order by pod.itemno ";

        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Sal_InvItems contactListItems = new Cls_Sal_InvItems();

                    contactListItems.setno(c1.getString(c1
                            .getColumnIndex("itemNo")));
                    contactListItems.setName(c1.getString(c1
                            .getColumnIndex("Item_Name")));
                    contactListItems.setprice(c1.getString(c1
                            .getColumnIndex("price")));
                    contactListItems.setItemOrgPrice(c1.getString(c1
                            .getColumnIndex("OrgPrice")));
                    contactListItems.setQty(c1.getString(c1
                            .getColumnIndex("qty")));
                    contactListItems.setTax(c1.getString(c1
                            .getColumnIndex("tax")));
                    contactListItems.setUniteNm(c1.getString(c1
                            .getColumnIndex("UnitName")));
                    contactListItems.setBounce(c1.getString(c1
                            .getColumnIndex("bounce_qty")));
                    contactListItems.setDiscount(c1.getString(c1
                            .getColumnIndex("dis_per")));
                    contactListItems.setDis_Amt(c1.getString(c1
                            .getColumnIndex("dis_Amt")));


                    contactListItems.setUnite(c1.getString(c1
                            .getColumnIndex("unitNo")));

                    contactListItems.setWeight(c1.getString(c1
                            .getColumnIndex("weight")));

                    contactListItems.setOperand(c1.getString(c1
                            .getColumnIndex("Operand")));

                    contactListItems.setTax_Amt(c1.getString(c1
                            .getColumnIndex("tax_Amt")));

                    contactListItems.setTotal(c1.getString(c1
                            .getColumnIndex("total")));


                    contactListItems.setProID(c1.getString(c1
                            .getColumnIndex("ProID")));

                    contactListItems.setPro_bounce(c1.getString(c1
                            .getColumnIndex("Pro_bounce")));

                    contactListItems.setPro_dis_Per(c1.getString(c1
                            .getColumnIndex("Pro_dis_Per")));

                    contactListItems.setPro_amt(c1.getString(c1
                            .getColumnIndex("Pro_amt")));

                    contactListItems.setPro_Total(c1.getString(c1
                            .getColumnIndex("pro_Total")));

                    contactListItems.setProType(c1.getString(c1
                            .getColumnIndex("Pro_Type")));

                    contactListItems.setDisPerFromHdr(c1.getString(c1
                            .getColumnIndex("DisPerFromHdr")));
                    contactListItems.setDisAmtFromHdr(c1.getString(c1
                            .getColumnIndex("DisAmtFromHdr")));
                    contactListItems.setSample(c1.getString(c1
                            .getColumnIndex("sample")));


                    contactList.add(contactListItems);

                    TTemp = Float.valueOf(c1.getString(c1.getColumnIndex("tax")));
                    PTemp = Float.valueOf(c1.getString(c1.getColumnIndex("price")));
                    PQty = Float.valueOf(c1.getString(c1.getColumnIndex("qty")));

                    PTemp = PTemp * PQty;

                    TTemp = TTemp / 100;
                    TTemp = TTemp * PTemp;

                    Total = Total + PTemp;
                    Total_Tax = Total_Tax + TTemp;
                    PTemp = 0;
                    PQty = 0;
                    TTemp = 0;
                } while (c1.moveToNext());


                etTotal.setText(String.valueOf(Total));
                et_Tottal_Tax.setText(String.valueOf(Total_Tax));
            }
            c1.close();
        }

    }

    public void btn_searchCustomer(View view) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String u = sharedPreferences.getString("UserID", "");


        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);

    /*    if (chk_Type.isChecked()) {
            Bundle bundle = new Bundle();
            bundle.putString("Scr", "Sale_Inv");
            FragmentManager Manager = getFragmentManager();
            Select_Cash_Customer obj = new Select_Cash_Customer();
            obj.setArguments(bundle);
            obj.show(Manager, null);

        } else {*/
        if (u == "-1" || ComInfo.ComNo == 2 || ComInfo.ComNo == Companies.Sector.getValue() || ComInfo.ComNo == Companies.Atls.getValue()) {
            Bundle bundle = new Bundle();
            bundle.putString("Scr", "Sale_Inv");
            FragmentManager Manager = getFragmentManager();
            Select_Customer obj = new Select_Customer();
            obj.setArguments(bundle);
            obj.show(Manager, null);
            //    }

        }
    }

    public void Set_Cust(String No, String Nm) {
        TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        acc.setText(No);
        CustNm.setText(Nm);
        CustNm.setError(null);
        customerstate();
    }
    /*public void CreateAlertDialogWithRadioButtonGroup() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Sale_InvoiceActivity.this);
        alertDialog.setTitle("رمز التحقق ");
        alertDialog.setMessage("لقد تجاوزت سقف التسهيلات للعميل ، سقف تسهيلات العميل هو " + "");


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        alertDialog.setIcon(R.drawable.key);

        alertDialog.setPositiveButton("موافق",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Get_RequestPermission();

                      //  CheckCelingNew(amt);


                    }
                });

        alertDialog.setNegativeButton("لا",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();

    }*/

    private void CheckCelingNew() {

        String Amt;

        SqlHandler sql_Handler = new SqlHandler(Sale_InvoiceActivity.this);
        String q = " select distinct  ifnull(Chqceling,0) as Chqceling  from Customers where no='" + tv_acc.getText().toString() + "'";
        Cursor c1 = sql_Handler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            ff = c1.getString(c1.getColumnIndex("Chqceling"));

            c1.close();
        }
        Amt = ff;
        Long i;
        sql_Handler = new SqlHandler(this);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        ContentValues cv = new ContentValues();
        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        String t = "";
        String Order_Status = "";
        final String ManNo, CustNo, OrderNo, Desc, Type;

        String q1 = "Select     ifnull(Status,-1) as  Status  From PermissionCode Where  Type='1' AND OrderNo='" + pono.getText().toString() + "'   Order by no desc limit 1";

        c1 = sqlHandler.selectQuery(q1);

        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            Order_Status = c1.getString(c1.getColumnIndex("Status"));
            c1.close();
        } else {
            Order_Status = "-1";
        }

      /*  if (Order_Status.equalsIgnoreCase("0")) {
            //  Toast.makeText(this,"00000",Toast.LENGTH_SHORT).show();
            Get_RequestPermission();
            return;

        } else*/
        if (Order_Status.equalsIgnoreCase("1")) {
            Save_Recod_Po();
            // Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
            return;
        }

      /*  else if(Order_Status.equalsIgnoreCase("2")){

            Toast.makeText(this,"222",Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this," Newwwwwwww",Toast.LENGTH_SHORT).show();
        }

*/


        String UserNm = DB.GetValue(this, "manf", "name", "man='" + UserID + "'");//    "SELECT  name   from  manf where man = '" + email.toString() + "' And password='" + password.toString() + "'";


        ManNo = UserID;
        CustNo = acc.getText().toString();
        OrderNo = pono.getText().toString();
        t = " اسم المندوب : ";
        t = t + UserNm;
        t = t + " اسم العميل : ";
        t = t + custNm.getText().toString();
        t = t + "  رقم الفاتورة : ";
        t = t + OrderNo;
        t = t + "    مبلغ الفاتورة : ";
        t = t + NetTotal.getText().toString();

        Desc = t;
        Type = "1";
        cv.put("ManNo", ManNo);
        cv.put("CustNo", CustNo);
        cv.put("OrderNo", OrderNo);
        cv.put("Status", "0");
        cv.put("Desc", Desc);
        cv.put("Type", Type);
        i = sqlHandler.Insert("PermissionCode", null, cv);


        if (i > 0) {


            loadingdialog = ProgressDialog.show(Sale_InvoiceActivity.this, "الرجاء الانتظار ...", "العمل جاري على طلب تفعيل سقف التسهيلات", true);
            loadingdialog.setCancelable(false);
            loadingdialog.setCanceledOnTouchOutside(false);
            loadingdialog.show();
            final Handler _handler = new Handler();

            new Thread(new Runnable() {
                @Override
                public void run() {

                    CallWebServices ws = new CallWebServices(Sale_InvoiceActivity.this);
                    ws.SendRequestPermission(ManNo, CustNo, OrderNo, Desc, "1", "1");
                    try {

                        if (We_Result.ID > 0) {
                            ContentValues cv = new ContentValues();
                            TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
                            cv.put("Post", We_Result.ID);
                            long i;
                            // i = sql_Handler.Update("PermissionCode", cv, "OrderNo='"+ DocNo.getText().toString()+"'");

                            _handler.post(new Runnable() {
                                public void run() {
                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            Sale_InvoiceActivity.this).create();
                                    alertDialog.setTitle("طلب تفعيل سقف التسهيلات");
                                    alertDialog.setMessage("تمت عملية طلب تفعيل سقف التسهيلات بنجاح رقم الحركة هو :" + We_Result.ID);
                                    alertDialog.setIcon(R.drawable.tick);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {


                                        }
                                    });
                                    loadingdialog.dismiss();
                                    alertDialog.show();


                                }
                            });
                        } else {

                            loadingdialog.dismiss();
                            _handler.post(new Runnable() {
                                public void run() {
                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            Sale_InvoiceActivity.this).create();
                                    alertDialog.setTitle("فشل في العملية  " + "   " + We_Result.ID + "");
                                    alertDialog.setMessage(We_Result.Msg.toString());
                                    alertDialog.setIcon(R.drawable.tick);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    alertDialog.show();
                                    alertDialog.setIcon(R.drawable.delete);
                                    alertDialog.setMessage("طلب تفعيل سقف التسهيلات لم يتم بنجاح" + "    ");
                                }
                            });
                        }

                    } catch (final Exception e) {
                        loadingdialog.dismiss();
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        Sale_InvoiceActivity.this).create();
                                alertDialog.setTitle("فشل في عمليه الاتصال");
                                alertDialog.setMessage(e.getMessage().toString());
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
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

    }

    private void CheckCelingOld() {


        String Amt;

        SqlHandler sql_Handler = new SqlHandler(Sale_InvoiceActivity.this);
        String q = " select distinct  ifnull(Chqceling,0) as Chqceling  from Customers where no='" + tv_acc.getText().toString() + "'";
        Cursor c1 = sql_Handler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            ff = c1.getString(c1.getColumnIndex("Chqceling"));

            c1.close();
        }
        Amt = ff;

        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        TextView acc = (TextView) findViewById(R.id.tv_acc);


        String Man = DB.GetValue(Sale_InvoiceActivity.this, "Tab_Password", "ManNo", "PassNo = 8 AND ManNo ='" + UserID + "'");
        final String pass = DB.GetValue(Sale_InvoiceActivity.this, "Tab_Password", "Password", "PassNo = '8' ");


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Sale_InvoiceActivity.this);
        alertDialog.setTitle("رمز التحقق ");
        alertDialog.setMessage("لقد تجاوزت سقف التسهيلات للعميل ، سقف تسهيلات العميل هو " + Amt);

        final EditText input = new EditText(Sale_InvoiceActivity.this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setTransformationMethod(new PasswordTransformationMethod());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.key);

        alertDialog.setPositiveButton("موافق",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String password = input.getText().toString();

                        if (pass.equals(password)) {
                            Save_Recod_Po();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "رمز التحقق غير صحيح", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

        alertDialog.setNegativeButton("لا",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();


    }

    private void CheckCeling() {


        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        TextView tv_CustNetTotal = (TextView) findViewById(R.id.tv_CustNetTotal);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        String q;
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        if (chk_Type.isChecked()) {

            alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle(tv_ScrTitle.getText().toString());
            alertDialog.setMessage("سيتم عمل سند قبض تلقائي نوع نقدي بقيمة " + NetTotal.getText().toString());

            alertDialog.setIcon(R.drawable.error_new);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Save_Recod_Po();
                }
            });
            alertDialog.show();
            return;


        }
        TextView tv_NetTotal = (TextView) findViewById(R.id.tv_CustNetTotal);
        Celing = "0";
        q = " select distinct  ifnull(Celing,0) as Celing  from Customers where no='" + acc.getText().toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            f = c1.getString(c1.getColumnIndex("Celing"));

            // if ((SToD(c1.getString(c1.getColumnIndex("Celing"))) - SToD(tv_NetTotal.getText().toString())) < SToD(NetTotal.getText().toString())) {
            // if ( SToD(tv_CustCelling.getText().toString()) < SToD(NetTotal.getText().toString())) {
            if (SToD(tv_CustCelling.getText().toString()) < (SToD(tv_CustNetTotal.getText().toString()) + SToD(NetTotal.getText().toString()))) {
                Celing = "0";
            } else {
                Celing = "0";
            }
            c1.close();
        }


        if (Celing.equalsIgnoreCase("1")) {
                   /*  if (ComInfo.ComNo == 1) {
                          CheckCelingNew( f);

                     } else {
                         CheckCelingOld(f);
                     }*/
            // CreateAlertDialogWithRadioButtonGroup(f);
        } else {
            Save_Recod_Po();
        }
    }

    public void btn_save_po(final View view) {
        Double SumSales=0.0,SalesCeiling=0.0;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
         SalesCeiling =Double.parseDouble( DB.GetValue(Sale_InvoiceActivity.this,"manf","SalesCeiling","man ='"+sharedPreferences.getString("UserID", "0")+"'"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

try {
    SumSales = Double.parseDouble(DB.GetsumValue(Sale_InvoiceActivity.this, "Sal_invoice_Hdr", "Net_Total", "date ='" + currentDateandTime + "'"));
}catch (Exception e){}
        try {
        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        SumSales+= SToD(NetTotal.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        }catch (Exception e){}


        if (SumSales>SalesCeiling  && SalesCeiling>0) {
            CreateAlertDialogWithRadioButtonGroup2();
        } else {
            if (ComInfo.ComNo == Companies.Atls.getValue()) {
                TextView acc = (TextView) findViewById(R.id.tv_acc);

                CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
          /*  String HowPay = DB.GetValue(Sale_InvoiceActivity.this, "Customers", "Pay_How", "barCode ='" + acc.getText().toString() + "' ");
            if(HowPay.equals("1"))
            {
                if(chk_Type.isChecked())
                {

                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            this).create();
                    alertDialog.setTitle("المجرة الدولية");
                    alertDialog.setMessage("لا يمكن بيع العميل فواتير ذمم");
                    alertDialog.setIcon(R.drawable.error_new);
                    alertDialog.setButton("موافق ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                    return;
                }
            }*/
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("update", "0");
                editor.apply();

                final TextView pono = (TextView) findViewById(R.id.et_OrdeNo);


                String q1 = " Select * From Sal_invoice_Hdr Where  ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo='" + pono.getText().toString() + "'";
                Cursor cc;
                cc = sqlHandler.selectQuery(q1);

                if (cc != null && cc.getCount() != 0) {
                    IsNew = false;
                    cc.close();
                } else {
                    IsNew = true;
                }


                if (IsNew == false) {

                    if (ComInfo.ComNo == Companies.beutyLine.getValue()) {

                        GlobaleVar.per = 0;
                        AlertDialog alertDialog1 = new AlertDialog.Builder(
                                this).create();
                        alertDialog1.setTitle(tv_ScrTitle.getText().toString());
                        alertDialog1.setMessage(" لا يمكن التعديل بعد الحفظ");
                        alertDialog1.setIcon(R.drawable.error_new);
                        alertDialog1.setButton("موافق", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                return;

                            }
                        });

                        alertDialog1.show();

                        return;
                    }

                }

                TextView  NetTotal = (TextView) findViewById(R.id.tv_NetTotal);

                Double TempTotal = 0.0;
                for (int x = 0; x < contactList.size(); x++) {
                    TempTotal = TempTotal + SToDNew(contactList.get(x).getTotal());
                }
                TempTotal = SToDNew(TempTotal + "");
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                if (Double.parseDouble(TempTotal + "") != Double.parseDouble(SToDNew(NetTotal.getText().toString()) + "")) {

                    alertDialog = new AlertDialog.Builder(
                            this).create();
                    alertDialog.setTitle(tv_ScrTitle.getText().toString() + TempTotal.toString());
                    alertDialog.setMessage("لا يمكن تخزين المستند ، الرجاء إعادة ادخال المواد   ");

                    alertDialog.setIcon(R.drawable.delete);
                    alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                    return;
                }

                final TextView tv_acc = (TextView) findViewById(R.id.tv_acc);
                ///////////////////////////////////////////////////

                 sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String Count = sharedPreferences.getString("InvCount", "0");
                String NumOfInvPerVisit = DB.GetValue(Sale_InvoiceActivity.this, "ComanyInfo", "NumOfInvPerVisit  ", "1=1");


                String q = "";
                 sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                 currentDateandTime = sdf.format(new Date());


                String RecVoucher_DocNo = DB.GetValue(Sale_InvoiceActivity.this, "RecVoucher", "DocNo", "CustAcc ='" + tv_acc.getText() + "' AND ((TrDate)=('" + currentDateandTime + "'))");
                String PAMENT_PERIOD_NO = DB.GetValue(Sale_InvoiceActivity.this, "Customers", "PAMENT_PERIOD_NO", "no ='" + tv_acc.getText() + "' ");

 /*       if (RecVoucher_DocNo == "-1" && PAMENT_PERIOD_NO.equals("0")) {
            alertDialog.setTitle(tv_ScrTitle.getText().toString());
            alertDialog.setMessage("يجب عمل سند قبض اولاَ ، لان نوع العميل فاتورة بفاتورة");
            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
            return;
        }*/
                //////////////////////////////////////////////////////////////////////////////////////


                q = "SELECT distinct *  from  Sal_invoice_Hdr where    ifnull(doctype,'1')='" + DocType.toString() + "'  and   Post >0 AND   OrderNo ='" + pono.getText().toString() + "'";
                acc = (TextView) findViewById(R.id.tv_acc);
                Cursor c1 = sqlHandler.selectQuery(q);
                if (c1 != null && c1.getCount() != 0) {


                    alertDialog.setTitle(tv_ScrTitle.getText().toString());
                    alertDialog.setMessage("لقد تم ترحيل المستند لايمكن التعديل");
                    alertDialog.setIcon(R.drawable.tick);
                    alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (ExistAfterSacve == 1) {
                                ExistAfterSacve = 0;
                                Intent k = new Intent(Sale_InvoiceActivity.this, JalMasterActivity.class);
                                startActivity(k);
                            }
                            if (DoPrint == 1) {
                                View view = null;
                                btn_print(view);
                            }

                        }
                    });

                    alertDialog.show();


                    c1.close();
                    return;
                } else {
/*
يبلبيلابابيبلابلابلالب
        ا
            بلاي
                ي
                    ل
                            ا
            ابل
*/
                    String Msg = "";
                    if (chk_Type.isChecked()) {
                        Msg = "سوف يتم تخزين الفاتورة نقدية" + "\n";
                        Msg += "هل  تريد الاستمرار بعملية الحفظ؟";
                    } else {
                        Msg = "سوف يتم تخزين الفاتورة ذمم" + "\n";
                        Msg += "هل  تريد الاستمرار بعملية الحفظ؟";
                    }


                    final TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
                    AlertDialog.Builder alertDialogYesNo = new AlertDialog.Builder(this);

                    if (ComInfo.ComNo == 1) {
                        q = "SELECT distinct *  from  Sal_invoice_Hdr where  ifnull(doctype,'1')='" + DocType.toString() + "'  and     acc  ='" + acc.getText() + "'   AND   date  ='" + currentDateandTime + "' " +
                                " And   OrderNo !='" + pono.getText().toString() + "'";

                        c1 = sqlHandler.selectQuery(q);
                        if (c1 != null && c1.getCount() != 0) {
                            Msg = "يوجد فاتورة لنفس هذا العميل في نفس هذا اليوم  " + "\n\r";
                            c1.close();

                        }

                    }
                    if (contactList.size() == 0) {


                        alertDialog.setTitle(tv_ScrTitle.getText().toString());
                        alertDialog.setMessage("لا يمكن تخزين المستند بدون مواد");            // Setting Icon to Dialog
                        alertDialog.setIcon(R.drawable.delete);
                        alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (ExistAfterSacve == 1) {
                                    ExistAfterSacve = 0;
                                    Intent k = new Intent(Sale_InvoiceActivity.this, JalMasterActivity.class);
                                    startActivity(k);
                                }


                            }
                        });

                        alertDialog.show();

                        return;

                    }


                    alertDialogYesNo.setTitle(tv_ScrTitle.getText().toString());
                    alertDialogYesNo.setMessage(Msg);

                    // Setting Icon to Dialog
                    alertDialogYesNo.setIcon(R.drawable.save);

                    alertDialogYesNo.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (ExistAfterSacve == 1) {
                                ExistAfterSacve = 0;
                                Intent k = new Intent(Sale_InvoiceActivity.this, JalMasterActivity.class);
                                startActivity(k);
                            }
                            if (DoPrint == 1) {
                                View view = null;
                                btn_print(view);
                            }

                            return;
                        }
                    });

                    alertDialogYesNo.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (custNm.getText().toString().length() == 0) {
                                custNm.setError("required!");
                                custNm.requestFocus();
                                return;
                            }

                            if (pono.getText().toString().length() == 0) {
                                pono.setError("required!");
                                pono.requestFocus();
                                return;
                            }

                            TextView tv_acc = (TextView) findViewById(R.id.tv_acc);

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                            String currentDateandTime = sdf.format(new Date());

                            String RecVoucher_DocNo = DB.GetValue(Sale_InvoiceActivity.this, "RecVoucher", "DocNo", "CustAcc ='" + tv_acc.getText() + "' AND ((TrDate)=('" + currentDateandTime + "'))");
                            String PAMENT_PERIOD_NO = DB.GetValue(Sale_InvoiceActivity.this, "Customers", "PAMENT_PERIOD_NO", "no ='" + tv_acc.getText() + "' ");


                            TextView tv_NetTotal = (TextView) findViewById(R.id.tv_CustNetTotal);

                            RecVoucher_DocNo = DB.GetValue(Sale_InvoiceActivity.this, "RecVoucher", "DocNo", "CustAcc ='" + tv_acc.getText() + "' AND ((TrDate)=('" + currentDateandTime + "'))");
                            PAMENT_PERIOD_NO = DB.GetValue(Sale_InvoiceActivity.this, "Customers", "PAMENT_PERIOD_NO", "no ='" + tv_acc.getText() + "' ");

                   /* if (RecVoucher_DocNo == "-1" && PAMENT_PERIOD_NO.equals("0")) {
                        if(Double.parseDouble(tv_NetTotal.getText().toString())>=0) {

                            final SqlHandler sql_Handler = new SqlHandler(Sale_InvoiceActivity.this);
                           String q = " select distinct  ifnull(Chqceling,0) as Chqceling  from Customers where no='" + tv_acc.getText().toString() + "'";
                            Cursor c1 = sql_Handler.selectQuery(q);
                            if (c1 != null && c1.getCount() != 0) {
                                c1.moveToFirst();
                                ff = c1.getString(c1.getColumnIndex("Chqceling"));

                                c1.close();
                            }
                            if(Get_RequestPermission().equals("0")){
                                CreateAlertDialogWithRadioButtonGroup(ff);

                            }else{
                                Save_Recod_Po();
                            }


                        }else{
                            Save_Recod_Po();


                        }}else{
                        Save_Recod_Po();

                    }*/


                 /*   if (RecVoucher_DocNo == "-1" && PAMENT_PERIOD_NO.equals("0")) {


                    }else{
                        Save_Recod_Po();
                    }*/

                            //    CreateAlertDialogWithRadioButtonGroup();

                            //   CreateAlertDialogWithRadioButtonGroup();
                            /*Get_RequestPermission();*/

                            TextView tv_CustNetTotal = (TextView) findViewById(R.id.tv_CustNetTotal);
                            CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);

                            //   if (chk_Type.isChecked())
                            if (!chk_Type.isChecked() && PAMENT_PERIOD_NO.equals("0") && ((SToD(tv_CustNetTotal.getText().toString()) > SToD(tv_CustCelling.getText().toString())) && (!(SToD(tv_CustCelling.getText().toString()) == 0)))) {
                                if (Double.parseDouble(tv_NetTotal.getText().toString()) > 1) {

                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            Sale_InvoiceActivity.this).create();
                                    alertDialog.setTitle("فاتورة المبيعات");
                                    alertDialog.setMessage("يجب تسديد الذمم المستحقة على العميل");
                                    alertDialog.setIcon(R.drawable.error_new);
                                    alertDialog.setButton("موافق ", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            CreateAlertDialogWithRadioButtonGroup2();
                                        }
                                    });
                                    alertDialog.show();
                                    return;
                                    //   CreateAlertDialogWithRadioButtonGroup2();


                                }
                            } else {
                                if (ComInfo.ComNo == Companies.diamond.getValue() || ComInfo.ComNo == Companies.nwaah.getValue()) {
                                    Save_Recod_Po();
                                } else {
                                    Save_Recod_Po();
                                }
                            }


                        }
                    });


                    alertDialogYesNo.show();


                }
            } else {
                if (customerstate().equalsIgnoreCase("1")) {
                    TextView acc = (TextView) findViewById(R.id.tv_acc);

                    CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
                    String HowPay = DB.GetValue(Sale_InvoiceActivity.this, "Customers", "Pay_How", "barCode ='" + acc.getText().toString() + "' ");
                    if (HowPay.equals("1")) {
                        if (chk_Type.isChecked()) {

                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    this).create();
                            alertDialog.setTitle("المجرة الدولية");
                            alertDialog.setMessage("لا يمكن بيع العميل فواتير ذمم");
                            alertDialog.setIcon(R.drawable.error_new);
                            alertDialog.setButton("موافق ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialog.show();
                            return;
                        }
                    }
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("update", "0");
                    editor.apply();

                    final TextView pono = (TextView) findViewById(R.id.et_OrdeNo);


                    String q1 = " Select * From Sal_invoice_Hdr Where  ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo='" + pono.getText().toString() + "'";
                    Cursor cc;
                    cc = sqlHandler.selectQuery(q1);

                    if (cc != null && cc.getCount() != 0) {
                        IsNew = false;
                        cc.close();
                    } else {
                        IsNew = true;
                    }


                    if (IsNew == false) {

                        if (ComInfo.ComNo == Companies.beutyLine.getValue()) {

                            GlobaleVar.per = 0;
                            AlertDialog alertDialog1 = new AlertDialog.Builder(
                                    this).create();
                            alertDialog1.setTitle(tv_ScrTitle.getText().toString());
                            alertDialog1.setMessage(" لا يمكن التعديل بعد الحفظ");
                            alertDialog1.setIcon(R.drawable.error_new);
                            alertDialog1.setButton("موافق", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    return;

                                }
                            });

                            alertDialog1.show();

                            return;
                        }

                    }

                    TextView  NetTotal = (TextView) findViewById(R.id.tv_NetTotal);

                    Double TempTotal = 0.0;
                    for (int x = 0; x < contactList.size(); x++) {
                        TempTotal = TempTotal + SToDNew(contactList.get(x).getTotal());
                    }
                    TempTotal = SToDNew(TempTotal + "");
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    if (Double.parseDouble(TempTotal + "") != Double.parseDouble(SToDNew(NetTotal.getText().toString()) + "")) {

                        alertDialog = new AlertDialog.Builder(
                                this).create();
                        alertDialog.setTitle(tv_ScrTitle.getText().toString() + TempTotal.toString());
                        alertDialog.setMessage("لا يمكن تخزين المستند ، الرجاء إعادة ادخال المواد");

                        alertDialog.setIcon(R.drawable.delete);
                        alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int which) {


                          }
                        });
                        alertDialog.show();
                        return;
                    }

                    final TextView tv_acc = (TextView) findViewById(R.id.tv_acc);
                    ///////////////////////////////////////////////////

                     sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    String Count = sharedPreferences.getString("InvCount", "0");
                    String NumOfInvPerVisit = DB.GetValue(Sale_InvoiceActivity.this, "ComanyInfo", "NumOfInvPerVisit  ", "1=1");


                    String q = "";
                     sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                     currentDateandTime = sdf.format(new Date());


                    String RecVoucher_DocNo = DB.GetValue(Sale_InvoiceActivity.this, "RecVoucher", "DocNo", "CustAcc ='" + tv_acc.getText() + "' AND ((TrDate)=('" + currentDateandTime + "'))");
                    String PAMENT_PERIOD_NO = DB.GetValue(Sale_InvoiceActivity.this, "Customers", "PAMENT_PERIOD_NO", "no ='" + tv_acc.getText() + "' ");
                    String Pay_How = DB.GetValue(Sale_InvoiceActivity.this, "Customers", "Pay_How", "no ='" + tv_acc.getText() + "' ");
                    TextView tv_NetTotal = (TextView) findViewById(R.id.tv_CustNetTotal);

        if (Pay_How.equals("2") && PAMENT_PERIOD_NO.equals("0") && Double.parseDouble(tv_NetTotal.getText().toString())>1) {
            alertDialog.setTitle(tv_ScrTitle.getText().toString());
            alertDialog.setMessage("يجب عمل سند قبض اولاَ ، لان نوع العميل فاتورة بفاتورة");
            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
            return;
        }
                    //////////////////////////////////////////////////////////////////////////////////////


                    q = "SELECT distinct *  from  Sal_invoice_Hdr where    ifnull(doctype,'1')='" + DocType.toString() + "'  and   Post >0 AND   OrderNo ='" + pono.getText().toString() + "'";
                    acc = (TextView) findViewById(R.id.tv_acc);
                    Cursor c1 = sqlHandler.selectQuery(q);
                    if (c1 != null && c1.getCount() != 0) {


                        alertDialog.setTitle(tv_ScrTitle.getText().toString());
                        alertDialog.setMessage("لقد تم ترحيل المستند لايمكن التعديل");
                        alertDialog.setIcon(R.drawable.tick);
                        alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (ExistAfterSacve == 1) {
                                    ExistAfterSacve = 0;
                                    Intent k = new Intent(Sale_InvoiceActivity.this, JalMasterActivity.class);
                                    startActivity(k);
                                }
                                if (DoPrint == 1) {
                                    View view = null;
                                    btn_print(view);
                                }

                            }
                        });

                        alertDialog.show();


                        c1.close();
                        return;
                    } else {
/*
يبلبيلابابيبلابلابلالب
        ا
            بلاي
                ي
                    ل
                            ا
            ابل
*/
                        String Msg = "";
                        if (chk_Type.isChecked()) {
                            Msg = "سوف يتم تخزين الفاتورة نقدية" + "\n";
                            Msg += "هل  تريد الاستمرار بعملية الحفظ؟";
                        } else {
                            Msg = "سوف يتم تخزين الفاتورة ذمم" + "\n";
                            Msg += "هل  تريد الاستمرار بعملية الحفظ؟";
                        }


                        final TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
                        AlertDialog.Builder alertDialogYesNo = new AlertDialog.Builder(this);

                        if (ComInfo.ComNo == 1) {
                            q = "SELECT distinct *  from  Sal_invoice_Hdr where  ifnull(doctype,'1')='" + DocType.toString() + "'  and     acc  ='" + acc.getText() + "'   AND   date  ='" + currentDateandTime + "' " +
                                    " And   OrderNo !='" + pono.getText().toString() + "'";

                            c1 = sqlHandler.selectQuery(q);
                            if (c1 != null && c1.getCount() != 0) {
                                Msg = "يوجد فاتورة لنفس هذا العميل في نفس هذا اليوم  " + "\n\r";
                                c1.close();

                            }

                        }
                        if (contactList.size() == 0) {


                            alertDialog.setTitle(tv_ScrTitle.getText().toString());
                            alertDialog.setMessage("لا يمكن تخزين المستند بدون مواد");            // Setting Icon to Dialog
                            alertDialog.setIcon(R.drawable.delete);
                            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (ExistAfterSacve == 1) {
                                        ExistAfterSacve = 0;
                                        Intent k = new Intent(Sale_InvoiceActivity.this, JalMasterActivity.class);
                                        startActivity(k);
                                    }


                                }
                            });

                            alertDialog.show();

                            return;

                        }


                        alertDialogYesNo.setTitle(tv_ScrTitle.getText().toString());
                        alertDialogYesNo.setMessage(Msg);

                        // Setting Icon to Dialog
                        alertDialogYesNo.setIcon(R.drawable.save);

                        alertDialogYesNo.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (ExistAfterSacve == 1) {
                                    ExistAfterSacve = 0;
                                    Intent k = new Intent(Sale_InvoiceActivity.this, JalMasterActivity.class);
                                    startActivity(k);
                                }
                                if (DoPrint == 1) {
                                    View view = null;
                                    btn_print(view);
                                }

                                return;
                            }
                        });

                        alertDialogYesNo.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (custNm.getText().toString().length() == 0) {
                                    custNm.setError("required!");
                                    custNm.requestFocus();
                                    return;
                                }

                                if (pono.getText().toString().length() == 0) {
                                    pono.setError("required!");
                                    pono.requestFocus();
                                    return;
                                }

                                TextView tv_acc = (TextView) findViewById(R.id.tv_acc);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                                String currentDateandTime = sdf.format(new Date());

                                String RecVoucher_DocNo = DB.GetValue(Sale_InvoiceActivity.this, "RecVoucher", "DocNo", "CustAcc ='" + tv_acc.getText() + "' AND ((TrDate)=('" + currentDateandTime + "'))");
                                String PAMENT_PERIOD_NO = DB.GetValue(Sale_InvoiceActivity.this, "Customers", "PAMENT_PERIOD_NO", "no ='" + tv_acc.getText() + "' ");


                                TextView tv_NetTotal = (TextView) findViewById(R.id.tv_CustNetTotal);

                                RecVoucher_DocNo = DB.GetValue(Sale_InvoiceActivity.this, "RecVoucher", "DocNo", "CustAcc ='" + tv_acc.getText() + "' AND ((TrDate)=('" + currentDateandTime + "'))");
                                PAMENT_PERIOD_NO = DB.GetValue(Sale_InvoiceActivity.this, "Customers", "PAMENT_PERIOD_NO", "no ='" + tv_acc.getText() + "' ");

                   /* if (RecVoucher_DocNo == "-1" && PAMENT_PERIOD_NO.equals("0")) {
                        if(Double.parseDouble(tv_NetTotal.getText().toString())>=0) {

                            final SqlHandler sql_Handler = new SqlHandler(Sale_InvoiceActivity.this);
                           String q = " select distinct  ifnull(Chqceling,0) as Chqceling  from Customers where no='" + tv_acc.getText().toString() + "'";
                            Cursor c1 = sql_Handler.selectQuery(q);
                            if (c1 != null && c1.getCount() != 0) {
                                c1.moveToFirst();
                                ff = c1.getString(c1.getColumnIndex("Chqceling"));

                                c1.close();
                            }
                            if(Get_RequestPermission().equals("0")){
                                CreateAlertDialogWithRadioButtonGroup(ff);

                            }else{
                                Save_Recod_Po();
                            }


                        }else{
                            Save_Recod_Po();


                        }}else{
                        Save_Recod_Po();

                    }*/


                 /*   if (RecVoucher_DocNo == "-1" && PAMENT_PERIOD_NO.equals("0")) {


                    }else{
                        Save_Recod_Po();
                    }*/

                                //    CreateAlertDialogWithRadioButtonGroup();

                                //   CreateAlertDialogWithRadioButtonGroup();
                                /*Get_RequestPermission();*/

                                TextView tv_CustNetTotal = (TextView) findViewById(R.id.tv_CustNetTotal);
                                CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
                                String AllowLoginOutside = (DB.GetValue(Sale_InvoiceActivity.this, "manf", "AllowLoginOutside", "man = '" + UserID + "'"));

                                //   if (chk_Type.isChecked())
                                if (AllowLoginOutside.equals("False") && !chk_Type.isChecked()) {


                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            Sale_InvoiceActivity.this).create();
                                    alertDialog.setTitle("فاتورة المبيعات");
                                    alertDialog.setMessage("غير مسموح لك في بيع فواتير ذمم الرجاء مراجعة المشرف");
                                    alertDialog.setIcon(R.drawable.error_new);
                                    alertDialog.setButton("موافق ", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    alertDialog.show();
                                    return;
                                    //   CreateAlertDialogWithRadioButtonGroup2();


                                }

                                if (!chk_Type.isChecked() && PAMENT_PERIOD_NO.equals("0") && ((SToD(tv_CustNetTotal.getText().toString()) > SToD(tv_CustCelling.getText().toString())) && (!(SToD(tv_CustCelling.getText().toString()) == 0)))) {
                                    if (Double.parseDouble(tv_NetTotal.getText().toString()) > 1) {

                                        AlertDialog alertDialog = new AlertDialog.Builder(
                                                Sale_InvoiceActivity.this).create();
                                        alertDialog.setTitle("فاتورة المبيعات");
                                        alertDialog.setMessage("يجب تسديد الذمم المستحقة على العميل");
                                        alertDialog.setIcon(R.drawable.error_new);
                                        alertDialog.setButton("موافق ", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                        alertDialog.show();
                                        return;
                                        //   CreateAlertDialogWithRadioButtonGroup2();


                                    }
                                } else {
                                    Save_Recod_Po();

                                }


                            }
                        });


                        alertDialogYesNo.show();


                    }
                } else {
                    Toast.makeText(Sale_InvoiceActivity.this, "هذا العميل ملغي , لا يمكن عمل فاتورة بيع", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void CreateAlertDialogWithRadioButtonGroup2() {


        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Sale_InvoiceActivity.this);

        builder.setTitle("الرجاء اختيار طريقة تفعيل  الموافقة على سقف التسهيلات ");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch (item) {
                    case 0:
                        CheckCelingOld();

                        break;
                    case 1:
                        SendSuperVervication();

                        break;

                }
                alertDialog2.dismiss();
            }
        });
        alertDialog2 = builder.create();

        alertDialog2.show();

    }


    public void SendSuperVervication() {
        final SqlHandler sql_Handler = new SqlHandler(Sale_InvoiceActivity.this);
        String q;
        Cursor c1;

        final TextView pono = (TextView) findViewById(R.id.et_OrdeNo);

        String Order_Status;
        String q1 = "Select     ifnull(Status,-1) as  Status  From PermissionCode Where  Type='1' AND OrderNo='" + pono.getText().toString() + "'   Order by no desc limit 1";

        c1 = sqlHandler.selectQuery(q1);

        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            Order_Status = c1.getString(c1.getColumnIndex("Status"));
            c1.close();
        } else {
            Order_Status = "-1";
        }

        if (Order_Status.equals("-1"))
            CheckCelingNew();
        else if (Order_Status.equals("1"))
            Save_Recod_Po();
        else
            Get_RequestPermission();


    }


    private String GetSumFinalDiscount() {
        double sum = 0.0;
        for (int x = 0; x < contactList.size(); x++) {
            sum = sum + SToD(contactList.get(x).getDisAmtFromHdr());


        }
        sum = SToD(sum + "");
        return sum + "";
    }


    /*  public void CreateAlertDialogWithRadioButtonGroup(final String amt) {


          android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Sale_InvoiceActivity.this);

          builder.setTitle("الرجاء اختيار طريقة تفعيل  الموافقة على سقف التسهيلات ");

          builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

              public void onClick(DialogInterface dialog, int item) {

                  switch (item) {
                      case 0:
                          CheckCelingOld(amt);

                          break;
                      case 1:
                          CheckCelingNew(amt);

                          break;

                  }
                  alertDialog2.dismiss();
              }
          });
          alertDialog2 = builder.create();

          alertDialog2.show();

      }
    */
    public void Save_Recod_Po1() {
       /* SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String del = preferences.getString("delete", "no");
        if(del.equals("yes")) {
            String q = "Delete from  Sal_invoice_Det where  ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo ='" + pono.getText().toString().replace("\u202c", "").replace("\u202d", "") + "'";
            //  String q = "Delete from  Sal_invoice_Det where OrderNo ='" + pono.getText().toString().replace("\u202c","").replace("\u202d","") + "'";
            sqlHandler.executeQuery(q);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("delete", "no");
            editor.apply();
            //  FillAdapter();
        }*/

        TextView Total = (TextView) findViewById(R.id.et_Total);
        if (Total.getText().toString().equals("0")) {

            AlertDialog alertDialog1 = new AlertDialog.Builder(
                    this).create();
            alertDialog1.setTitle(tv_ScrTitle.getText().toString());
            alertDialog1.setMessage(" لا يمكن تخزين فاتورة صفرية");
            alertDialog1.setIcon(R.drawable.error_new);
            alertDialog1.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;

                }
            });

            alertDialog1.show();

            return;
        }
        Integer Seq = 0;
        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        EditText et_hdr_Disc = (EditText) findViewById(R.id.et_hdr_Disc);

        // TextView Total = (TextView) findViewById(R.id.et_Total);
        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        TextView TotalTax = (TextView) findViewById(R.id.et_TotalTax);
        TextView dis = (TextView) findViewById(R.id.et_dis);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDate = sdf.format(new Date());

        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());


        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        long i;
        int dayOfWeek;
        Calendar c = Calendar.getInstance();
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        String q1 = "Select * From Sal_invoice_Hdr Where  ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo='" + pono.getText().toString() + "'";
        Cursor c1;
        c1 = sqlHandler.selectQuery(q1);

        if (c1 != null && c1.getCount() != 0) {
            IsNew = false;
            c1.close();
        } else {
            IsNew = true;
        }


        if (IsNew == true) {
            Seq = Integer.parseInt(DB.GetValue(this, "Sal_invoice_Hdr", "ifnull(Max(Seq),0)+1", "ifnull(doctype,'1')='" + DocType.toString() + "'  and  date='" + currentDate + "'"));

        } else {

            Seq = Integer.parseInt(DB.GetValue(this, "Sal_invoice_Hdr", "ifnull(Seq,0)", "ifnull(doctype,'1')='" + DocType.toString() + "'  and  OrderNo='" + pono.getText().toString() + "'"));

        }
        ContentValues cv = new ContentValues();
        cv.put("OrderNo", pono.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("acc", acc.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("date", currentDate.replace("\u202c", "").replace("\u202d", ""));
        cv.put("Time", currentDateandTime.replace("\u202c", "").replace("\u202d", ""));
        cv.put("userid", UserID.replace("\u202c", "").replace("\u202d", ""));
        cv.put("Total", SToD(Total.getText().toString().replace("\u202c", "").replace("\u202d", "")));
        cv.put("Net_Total", SToD(NetTotal.getText().toString().replace("\u202c", "").replace("\u202d", "")));
        cv.put("Tax_Total", SToD(TotalTax.getText().toString().replace("\u202c", "").replace("\u202d", "")));
        cv.put("Post", "-1");
        cv.put("QtyStoreSer", MaxStoreQtySer);
        cv.put("Nm", custNm.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("V_OrderNo", sharedPreferences.getString("V_OrderNo", "0"));
        cv.put("DayNum", dayOfWeek);
        cv.put("Seq", Seq);
        cv.put("OrderDesc", note_sal);
        cv.put("bounce_Total", "0");
        cv.put("IDN", sharedPreferences.getString("IDN", ""));
        cv.put("doctype", DocType.toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("TotalWithoutDiscount", SToD(NetTotal.getText().toString().replace("\u202c", "").replace("\u202d", "")) + SToD(GetSumFinalDiscount()));

        if (IncludeTax_Flag.isChecked()) {
            cv.put("include_Tax", "0");
        } else {
            cv.put("include_Tax", "-1");
        }

        if (chk_Type.isChecked()) {
            cv.put("inovice_type", "0");
        } else {
            cv.put("inovice_type", "-1");
        }


      /*  if (chk_hdr_disc.isChecked()) {
            cv.put("hdr_dis_per", "0");
        } else {
            cv.put("hdr_dis_per", "-1");
        }*/

        cv.put("hdr_dis_per", FinalDiscountpercent + "");
        cv.put("hdr_dis_value", FinalDiscountAmt + "");
        cv.put("hdr_dis_Type", FinalDiscountType + "");


        cv.put("disc_Total", dis.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("Pos_System", "0");

        if (IsNew == true) {
            i = sqlHandler.Insert("Sal_invoice_Hdr", null, cv);
        } else {
            i = sqlHandler.Update("Sal_invoice_Hdr", cv, "ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo ='" + pono.getText().toString().replace("\u202c", "").replace("\u202d", "") + "'");
        }
        sqlHandler.executeQuery(" Delete from  RecVoucherSeles where   SalesOrderNo ='" + pono.getText().toString() + "'");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String del = preferences.getString("delete", "no");
        if (i > 0) {

            String q = "Delete from  Sal_invoice_Det where  ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo ='" + pono.getText().toString().replace("\u202c", "").replace("\u202d", "") + "'";
            //  String q = "Delete from  Sal_invoice_Det where OrderNo ='" + pono.getText().toString().replace("\u202c","").replace("\u202d","") + "'";
            sqlHandler.executeQuery(q);


        /*else
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String del = preferences.getString("delete", "no");
        if(del.equals("yes")) {
            String q = "Delete from  Sal_invoice_Det where  ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo ='" + pono.getText().toString().replace("\u202c", "").replace("\u202d", "") + "'";
            //  String q = "Delete from  Sal_invoice_Det where OrderNo ='" + pono.getText().toString().replace("\u202c","").replace("\u202d","") + "'";
            sqlHandler.executeQuery(q);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("delete","no");
            editor.apply();
        }*/


            for (int x = 0; x < contactList.size(); x++) {
                try {
                    Cls_Sal_InvItems contactListItems = new Cls_Sal_InvItems();
                    contactListItems = contactList.get(x);


                    cv = new ContentValues();
                    cv.put("OrderNo", pono.getText().toString().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("itemno", contactListItems.getNo().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("price", contactListItems.getPrice().toString().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("qty", SToD(contactListItems.getQty().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("tax", SToD(contactListItems.getTax().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("unitNo", contactListItems.getUnite().toString().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("dis_per", SToD(contactListItems.getDiscount().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("dis_Amt", SToD(contactListItems.getDis_Amt().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("bounce_qty", contactListItems.getBounce().toString().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("tax_Amt", SToD(contactListItems.getTax_Amt().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("total", SToD(contactListItems.getTotal().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("ProID", contactListItems.getProID().toString().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("Pro_bounce", contactListItems.getPro_bounce().toString().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("Pro_dis_Per", SToD(contactListItems.getPro_dis_Per().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("Pro_amt", SToD(contactListItems.getPro_amt().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("pro_Total", SToD(contactListItems.getPro_Total().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("OrgPrice", SToD(contactListItems.getItemOrgPrice().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("Pro_Type", contactListItems.getProType().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("Operand", SToD(contactListItems.getOperand().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("weight", SToD(contactListItems.getWeight().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("DisAmtFromHdr", SToD(contactListItems.getDisAmtFromHdr().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("DisPerFromHdr", SToD(contactListItems.getDisPerFromHdr().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("Note", contactListItems.getNote().toString());
                    cv.put("sample", contactListItems.getSample());
                    cv.put("height", contactListItems.getHeight());
                    cv.put("Width", contactListItems.getWidth());
                    cv.put("fish", contactListItems.getFish());
                    cv.put("number", contactListItems.getNumber());
                    cv.put("doctype", DocType.toString());

                    //   if(IsNew == true) {
                    //    contactList.remove(x);
                    // }
                    if (i > 0) {

                        i = sqlHandler.Insert(this, "Sal_invoice_Det", null, cv);

                        if (i < 0) {
                            alertDialog.setTitle("  الرجاء تبليغ مسؤول النظام  للضرورة  " + contactListItems.getName().toString());
                            alertDialog.setMessage(cv.toString());
                            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            });
                            alertDialog.show();
                            try {
                                File myFile = new File("/sdcard/Android/data/Galaxy/Sal_invoice_Det.txt");
                                if (!myFile.exists()) {
                                    myFile.createNewFile();
                                }
                                FileOutputStream fOut = new FileOutputStream(myFile, true);
                                OutputStreamWriter myOutWriter =
                                        new OutputStreamWriter(fOut);
                                myOutWriter.append("\n\r" + "--------------------START-----------------------------\r\n");
                                myOutWriter.append("DateTime" + currentDate + "--" + currentDateandTime + "\r\n");
                                myOutWriter.append("SQL :" + cv.toString() + "\n\r");
                                myOutWriter.append("\n\r" + "--------------------END------------------------------\r\n");
                                myOutWriter.close();
                                fOut.close();
                            } catch (Exception ex) {

                            }
                            break;
                        }

                    }
                } catch (Exception ex) {
                    if (IsNew) {
                        sqlHandler.executeQuery(" Delete from  Sal_invoice_Hdr where  ifnull(doctype,'1')=' " + DocType.toString() + "'  and  OrderNo ='" + pono.getText().toString() + "'");
                        sqlHandler.executeQuery(" Delete from  Sal_invoice_Det where ifnull(doctype,'1')=' " + DocType.toString() + "'  and   OrderNo ='" + pono.getText().toString() + "'");
                        i = -1;
                    }
                }

            }

            if (i <= 0) {
                if (IsNew) {
                    sqlHandler.executeQuery(" Delete from  Sal_invoice_Hdr where  ifnull(doctype,'1')='" + DocType.toString() + "'  and  OrderNo ='" + pono.getText().toString() + "'");
                    sqlHandler.executeQuery(" Delete from  Sal_invoice_Det where ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo ='" + pono.getText().toString() + "'");
                }
            }

        }

        if (i > 0) {
            if (IsNew) {

                InsertLogTrans obj = new InsertLogTrans(Sale_InvoiceActivity.this, SCR_NO, SCR_ACTIONS.Insert.getValue(), et_OrdeNo.getText().toString(), tv_acc.getText().toString(), "", "0");

            } else {
                InsertLogTrans obj = new InsertLogTrans(Sale_InvoiceActivity.this, SCR_NO, SCR_ACTIONS.Modify.getValue(), et_OrdeNo.getText().toString(), tv_acc.getText().toString(), "", "0");
            }
            UpDateMaxOrderNo();
            //DoPrint = 1;
            DeleteAllPromotions();
            String s = " Delete  from  LoukupsOffers OrderNo='" + pono.getText() + "'";
            sqlHandler.executeQuery(s);
            Gf_Calc_PromotionNew();
            // GetMaxPONo();

            alertDialog.setTitle(tv_ScrTitle.getText().toString());
            alertDialog.setMessage("تمت عمليةالتخزين  بنجاح");
            Toast.makeText(this, "تمت عملية الحفظ بنجاح", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String Count = sharedPreferences.getString("InvCount", "0");
            Count = (SToD(Count) + 1) + "";
            editor.putString("InvCount", Count);
            editor.commit();
            IsChange = false;

            if (DiscountDept.equalsIgnoreCase("-1")) {
                ApplyCustDiscount();
            }
            DiscountDept = "-1";
            CustAmtDt();
            if (chk_Type.isChecked()) {
                Insert_AutRecv();
            }
            chk_Type.setEnabled(true);

            if (ComInfo.ComNo == Companies.beutyLine.getValue()) {
                DoShare();
            }
            IsNew = false;
            alertDialog.setIcon(R.drawable.tick);

            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (ExistAfterSacve == 1) {
                        Intent k = new Intent(Sale_InvoiceActivity.this, JalMasterActivity.class);
                        startActivity(k);
                    }

                    if (DoPrint == 1) {
                        View view = null;
                        btn_print(view);
                    }


                }
            });


            // alertDialog.show();


        }

    }

    public void Save_Recod_Po() {
        TextView Total = (TextView) findViewById(R.id.et_Total);
        if (Total.getText().toString().equals("0")) {

            AlertDialog alertDialog1 = new AlertDialog.Builder(
                    this).create();
            alertDialog1.setTitle(tv_ScrTitle.getText().toString());
            alertDialog1.setMessage(" لا يمكن تخزين فاتورة صفرية");
            alertDialog1.setIcon(R.drawable.error_new);
            alertDialog1.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;

                }
            });

            alertDialog1.show();

            return;
        }
        Integer Seq = 0;
        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        EditText et_hdr_Disc = (EditText) findViewById(R.id.et_hdr_Disc);

        // TextView Total = (TextView) findViewById(R.id.et_Total);
        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        TextView TotalTax = (TextView) findViewById(R.id.et_TotalTax);
        TextView dis = (TextView) findViewById(R.id.et_dis);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDate = sdf.format(new Date());

        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());


        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        long i;
        int dayOfWeek;
        Calendar c = Calendar.getInstance();
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        String q1 = "Select * From Sal_invoice_Hdr Where  ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo='" + pono.getText().toString() + "'";
        Cursor c1;
        c1 = sqlHandler.selectQuery(q1);

        if (c1 != null && c1.getCount() != 0) {
            IsNew = false;
            c1.close();
        } else {
            IsNew = true;
        }


        if (IsNew == true) {
            Seq = Integer.parseInt(DB.GetValue(this, "Sal_invoice_Hdr", "ifnull(Max(Seq),0)+1", "ifnull(doctype,'1')='" + DocType.toString() + "'  and  date='" + currentDate + "'"));

        } else {

            Seq = Integer.parseInt(DB.GetValue(this, "Sal_invoice_Hdr", "ifnull(Seq,0)", "ifnull(doctype,'1')='" + DocType.toString() + "'  and  OrderNo='" + pono.getText().toString() + "'"));

        }

        String branch = DB.GetValue(this, "Customers", "branch", "  no='" + sharedPreferences.getString("CustNo", "") + "'");


        ContentValues cv = new ContentValues();
        cv.put("OrderNo", pono.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("acc", acc.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("date", currentDate.replace("\u202c", "").replace("\u202d", ""));
        cv.put("Time", currentDateandTime.replace("\u202c", "").replace("\u202d", ""));
        cv.put("userid", UserID.replace("\u202c", "").replace("\u202d", ""));
        cv.put("Total", SToD(Total.getText().toString().replace("\u202c", "").replace("\u202d", "")));
        cv.put("Net_Total", SToD(NetTotal.getText().toString().replace("\u202c", "").replace("\u202d", "")));
        cv.put("Tax_Total", SToD(TotalTax.getText().toString().replace("\u202c", "").replace("\u202d", "")));
        cv.put("Post", "-1");
        cv.put("QtyStoreSer", MaxStoreQtySer);
        cv.put("Nm", custNm.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("V_OrderNo", sharedPreferences.getString("V_OrderNo", "0"));
        cv.put("DayNum", dayOfWeek);
        cv.put("Seq", Seq);
        cv.put("IDN", sharedPreferences.getString("IDN", ""));
        cv.put("OrderDesc", note_sal);
        cv.put("bounce_Total", "0");
        cv.put("doctype", DocType.toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("TotalWithoutDiscount", SToD(NetTotal.getText().toString().replace("\u202c", "").replace("\u202d", "")) + SToD(GetSumFinalDiscount()));

        cv.put("bounce_Total", "0");
        cv.put("branch", branch);


        if (IncludeTax_Flag.isChecked()) {
            cv.put("include_Tax", "0");
        } else {
            cv.put("include_Tax", "-1");
        }

        if (chk_Type.isChecked()) {
            cv.put("inovice_type", "0");
        } else {
            cv.put("inovice_type", "-1");
        }


      /*  if (chk_hdr_disc.isChecked()) {
            cv.put("hdr_dis_per", "0");
        } else {
            cv.put("hdr_dis_per", "-1");
        }*/

        cv.put("hdr_dis_per", FinalDiscountpercent + "");
        cv.put("hdr_dis_value", FinalDiscountAmt + "");
        cv.put("hdr_dis_Type", FinalDiscountType + "");


        cv.put("disc_Total", dis.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        cv.put("Pos_System", "0");
        if (IsNew == true) {
            i = sqlHandler.Insert("Sal_invoice_Hdr", null, cv);
        } else {
            i = sqlHandler.Update("Sal_invoice_Hdr", cv, "ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo ='" + pono.getText().toString().replace("\u202c", "").replace("\u202d", "") + "'");
        }


        if (i > 0) {
            String q = "Delete from  Sal_invoice_Det where  ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo ='" + pono.getText().toString().replace("\u202c", "").replace("\u202d", "") + "'";

            sqlHandler.executeQuery(q);

            for (int x = 0; x < contactList.size(); x++) {
                try {
                    Cls_Sal_InvItems contactListItems = new Cls_Sal_InvItems();
                    contactListItems = contactList.get(x);


                    cv = new ContentValues();
                    cv.put("OrderNo", pono.getText().toString().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("itemno", contactListItems.getNo().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("price", contactListItems.getPrice().toString().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("qty", SToD(contactListItems.getQty().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("tax", SToD(contactListItems.getTax().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("unitNo", contactListItems.getUnite().toString().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("dis_per", SToD(contactListItems.getDiscount().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("dis_Amt", SToD(contactListItems.getDis_Amt().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("bounce_qty", contactListItems.getBounce().toString().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("tax_Amt", SToD(contactListItems.getTax_Amt().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("total", SToD(contactListItems.getTotal().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("ProID", contactListItems.getProID().toString().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("Pro_bounce", contactListItems.getPro_bounce().toString().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("Pro_dis_Per", SToD(contactListItems.getPro_dis_Per().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("Pro_amt", SToD(contactListItems.getPro_amt().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("pro_Total", SToD(contactListItems.getPro_Total().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("OrgPrice", SToD(contactListItems.getItemOrgPrice().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("Pro_Type", contactListItems.getProType().replace("\u202c", "").replace("\u202d", ""));
                    cv.put("Operand", SToD(contactListItems.getOperand().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("weight", SToD(contactListItems.getWeight().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("DisAmtFromHdr", SToD(contactListItems.getDisAmtFromHdr().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("DisPerFromHdr", SToD(contactListItems.getDisPerFromHdr().toString().replace("\u202c", "").replace("\u202d", "")));
                    cv.put("height", contactListItems.getHeight());
                    cv.put("Width", contactListItems.getWidth());
                    cv.put("fish", contactListItems.getFish());
                    cv.put("number", contactListItems.getNumber());
                    if (flagV == 1) {
                        cv.put("Note", contactListItems.getNote().toString());
                    }
                    cv.put("sample", contactListItems.getSample());
                    cv.put("doctype", DocType.toString());
                    if (i > 0) {
                        i = sqlHandler.Insert(this, "Sal_invoice_Det", null, cv);
                        if (i < 0) {
                            alertDialog.setTitle("  الرجاء تبليغ مسؤول النظام  للضرورة  " + contactListItems.getName().toString());
                            alertDialog.setMessage(cv.toString());
                            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

                            alertDialog.show();
                            try {
                                File myFile = new File("/sdcard/Android/data/Galaxy/Sal_invoice_Det.txt");
                                if (!myFile.exists()) {
                                    myFile.createNewFile();
                                }
                                FileOutputStream fOut = new FileOutputStream(myFile, true);
                                OutputStreamWriter myOutWriter =
                                        new OutputStreamWriter(fOut);
                                myOutWriter.append("\n\r" + "--------------------START-----------------------------\r\n");
                                myOutWriter.append("DateTime" + currentDate + "--" + currentDateandTime + "\r\n");
                                myOutWriter.append("SQL :" + cv.toString() + "\n\r");
                                myOutWriter.append("\n\r" + "--------------------END------------------------------\r\n");
                                myOutWriter.close();
                                fOut.close();
                            } catch (Exception ex) {

                            }
                            break;
                        }

                    }
                } catch (Exception ex) {
                    if (IsNew) {
                        sqlHandler.executeQuery(" Delete from  Sal_invoice_Hdr where  ifnull(doctype,'1')=' " + DocType.toString() + "'  and  OrderNo ='" + pono.getText().toString() + "'");
                        sqlHandler.executeQuery(" Delete from  Sal_invoice_Det where ifnull(doctype,'1')=' " + DocType.toString() + "'  and   OrderNo ='" + pono.getText().toString() + "'");
                        i = -1;
                    }
                }


            }

            if (i <= 0) {
                if (IsNew) {
                    sqlHandler.executeQuery(" Delete from  Sal_invoice_Hdr where  ifnull(doctype,'1')='" + DocType.toString() + "'  and  OrderNo ='" + pono.getText().toString() + "'");
                    sqlHandler.executeQuery(" Delete from  Sal_invoice_Det where ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo ='" + pono.getText().toString() + "'");
                }
            }

        }

        if (i > 0) {
            if (IsNew) {

                InsertLogTrans obj = new InsertLogTrans(Sale_InvoiceActivity.this, SCR_NO, SCR_ACTIONS.Insert.getValue(), et_OrdeNo.getText().toString(), tv_acc.getText().toString(), "", "0");

            } else {
                InsertLogTrans obj = new InsertLogTrans(Sale_InvoiceActivity.this, SCR_NO, SCR_ACTIONS.Modify.getValue(), et_OrdeNo.getText().toString(), tv_acc.getText().toString(), "", "0");
            }

            UpDateMaxOrderNo();
            //DoPrint = 1;

            DeleteAllPromotions();
            Gf_Calc_PromotionNew();
            // GetMaxPONo();

            alertDialog.setTitle(tv_ScrTitle.getText().toString());
            alertDialog.setMessage("تمت عمليةالتخزين  بنجاح");
            Toast.makeText(this, "تمت عملية الحفظ بنجاح", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String Count = sharedPreferences.getString("InvCount", "0");
            Count = (SToD(Count) + 1) + "";
            editor.putString("InvCount", Count);
            editor.commit();
            IsChange = false;

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Boolean ApproveInvoicesDirectly = Boolean.parseBoolean(DB.GetValue(Sale_InvoiceActivity.this, "manf", "ApproveInvoicesDirectly", "man ='" + sharedPreferences.getString("UserID", "0") + "'"));

            if (ApproveInvoicesDirectly) {
                DoShare();
            }


            if (DiscountDept.equalsIgnoreCase("-1")) {
                ApplyCustDiscount();
            }
            DiscountDept = "-1";
            CustAmtDt();
            if (chk_Type.isChecked()) {
                Insert_AutRecv();
            } else {
                query = "Delete from  RecVoucherSeles where  SalesOrderNo ='" + et_OrdeNo.getText().toString() + "'";
                sqlHandler.executeQuery(query);
            }
            chk_Type.setEnabled(true);

            if (ComInfo.ComNo == Companies.beutyLine.getValue()) {
                DoShare();
            }
            IsNew = false;
            alertDialog.setIcon(R.drawable.tick);

            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (ExistAfterSacve == 1) {
                        Intent k = new Intent(Sale_InvoiceActivity.this, JalMasterActivity.class);
                        startActivity(k);
                    }

                    if (DoPrint == 1) {
                        View view = null;
                        btn_print(view);
                    }


                }
            });


            // alertDialog.show();


        }
        CustAmtDt();
    }

    public String GetMaxRecNo() {
        String OrderNo;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String Login = sharedPreferences.getString("Login", "No");


        String u = sharedPreferences.getString("UserID", "");
        sqlHandler = new SqlHandler(this);
        String query = "SELECT  COALESCE(MAX(DocNo), 0) +1 AS no FROM RecVoucherSeles   where UserID = '" + u.toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(query);
        Integer max = 0;

        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            max = Integer.parseInt(c1.getString(c1.getColumnIndex("no")));
            c1.close();
        }

        Integer max1 = 0;
        max1 = Integer.parseInt(DB.GetValue(Sale_InvoiceActivity.this, "OrdersSitting", "Payment", "1=1"));
        //max1 = sharedPreferences.getString("m2", "");
        if (max1 <= 0) {
            max1 = 0;
        }

        max1 = max1 + 1;
        if (max1 > max) {
            max = max1;
        }

        if (max.toString().length() == 1) {
            OrderNo = (intToString(Integer.valueOf(u), 2) + intToString(Integer.valueOf(max), 5));

        } else {

            OrderNo = (intToString(Integer.valueOf(max), 7));

        }
        return OrderNo;

    }

    private void Insert_AutRecv() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDate = sdf.format(new Date());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Integer Rec_Seq = 0;
        Rec_Seq = Integer.parseInt(DB.GetValue(this, "RecVoucherSeles", "ifnull(Max(Seq),0)+1", "TrDate='" + currentDate + "'"));
        TextView acc = (TextView) findViewById(R.id.tv_acc);
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        int dayOfWeek;
        Calendar c = Calendar.getInstance();
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);


        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);

        ContentValues cv = new ContentValues();
        pono = (TextView) findViewById(R.id.et_OrdeNo);
        String DocNo = pono.getText().toString();

        String RecvOrder = "-1";
        RecvOrder = DB.GetValue(Sale_InvoiceActivity.this, "RecVoucherSeles", "ifnull(DocNo,'-1')", "ifnull(SalesOrderNo,'-1')='" + pono.getText().toString() + "'");


        cv.put("DocNo", pono.getText().toString());
        cv.put("CustAcc", acc.getText().toString());
        cv.put("Amnt", SToD(NetTotal.getText().toString()));
        cv.put("TrDate", currentDate);
        cv.put("Desc", "سند قبض تلقائي لفاتورة نقدية رقم " + pono.getText().toString());
        cv.put("VouchType", "1");
        cv.put("curno", "1");
        cv.put("Cash", SToD(NetTotal.getText().toString()));
        cv.put("CheckTotal", "0");
        cv.put("Post", "-1");
        cv.put("Seq", Rec_Seq);
        cv.put("IDN", sharedPreferences.getString("IDN", ""));
        cv.put("SalesOrderNo", pono.getText().toString());
        cv.put("UserID", sharedPreferences.getString("UserID", ""));
        cv.put("V_OrderNo", sharedPreferences.getString("V_OrderNo", "0"));
        cv.put("DayNum", dayOfWeek);
        cv.put("FromSales", "1");
        long i;

        if (RecvOrder.equalsIgnoreCase("-1")) {
            i = sqlHandler.Insert("RecVoucherSeles", null, cv);
            UpDateRecMaxOrderNo();
        } else {
            i = sqlHandler.Update("RecVoucherSeles", cv, "DocNo='" + RecvOrder + "'");
        }
    }

    public String getJsonRecvVouchar(String no) {


        long Reslut = -1;

        String json = "[{''}]";


        String query = "Select  distinct   ifnull(PersonPayAmt,'') as PersonPayAmt, rc.V_OrderNo, rc.DocNo,  IFNULL(rc.CheckTotal,0) as CheckTotal, IFNULL(rc.Cash,0) as Cash, rc.Desc,rc.Amnt,rc.TrDate,rc.CustAcc  ,c.name , rc.curno  ,COALESCE(Post, -1)  as Post , " +
                "rc.UserID ,rc.VouchType ,rc.UserID,rc.FromSales from RecVoucherSeles rc   left join Customers c on c.no = rc.CustAcc " +
                " where rc.SalesOrderNo = '" + no.toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(query);
        JSONObject jsonObject = new JSONObject();
        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            try {
                jsonObject.put("OrderNo", c1.getString(c1.getColumnIndex("DocNo")));
                jsonObject.put("acc", c1.getString(c1.getColumnIndex("CustAcc")));
                jsonObject.put("Amt", c1.getString(c1.getColumnIndex("Amnt")));
                jsonObject.put("Date", c1.getString(c1.getColumnIndex("TrDate")));
                jsonObject.put("notes", c1.getString(c1.getColumnIndex("Desc")));
                jsonObject.put("VouchType", c1.getString(c1.getColumnIndex("VouchType")));
                jsonObject.put("CurNo", c1.getString(c1.getColumnIndex("curno")));
                if (c1.getString(c1.getColumnIndex("Cash")).toString().length() == 0) {
                    jsonObject.put("Cash", "0.0");
                } else {
                    jsonObject.put("Cash", c1.getString(c1.getColumnIndex("Cash")));
                }
                jsonObject.put("CheckTotal", c1.getString(c1.getColumnIndex("CheckTotal")));
                jsonObject.put("V_OrderNo", c1.getString(c1.getColumnIndex("V_OrderNo")));
                jsonObject.put("UserID", c1.getString(c1.getColumnIndex("UserID")));
                jsonObject.put("PersonPayAmt", c1.getString(c1.getColumnIndex("PersonPayAmt")));
                jsonObject.put("FromSales", c1.getString(c1.getColumnIndex("FromSales")));
            } catch (JSONException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            c1.close();
        }
        final String str;
        str = jsonObject.toString() + json;


     /*   CallWebServices ws = new CallWebServices(context);
        Reslut= ws.SavePayment(str);
        try {

            if (Reslut> 0) {
                //query = "Update  RecVoucher  set Post="+Reslut+" Where DocNo='"+ OrderNo+"'  and  "+ Reslut + " Not in (Select Post from RecVoucher )";
                query = "Update  RecVoucher  set Post="+Reslut+" Where DocNo='"+ OrderNo+"'    ";
                sqlHandler.executeQuery(query );
            }

        } catch (Exception e) {
            Reslut= -1;
        }*/


        return str;
    }

    private void UpDateRecMaxOrderNo() {//

        try {


            SqlHandler sqlHandler;
            sqlHandler = new SqlHandler(this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String u = sharedPreferences.getString("UserID", "");
            String query = "SELECT  ifnull(MAX (DocNo), 0)AS no FROM RecVoucherSeles   where  UserID = '" + u.toString() + "'";
            Cursor c1 = sqlHandler.selectQuery(query);
            String max = "0";

            if (c1 != null && c1.getCount() != 0) {
                c1.moveToFirst();
                max = c1.getString(c1.getColumnIndex("no"));
                c1.close();
            }

            query = " Update OrdersSitting SET Payment ='" + max + "'";
            //  sqlHandler.executeQuery(query);
     /*   SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("m2",max);
        editor.commit();*/
        } catch (Exception ex) {

        }
    }

    private void UpDateMaxOrderNo() {

      /*  String query = " SELECT  Distinct  COALESCE(MAX( cast(OrderNo as integer)), 0)   AS no   " +
                " FROM Sal_invoice_Hdr  where ifnull(doctype,'1')='"+DocType.toString()+ "'    ";
*/
        String query = " SELECT  Distinct  COALESCE(MAX( cast(OrderNo as integer)), 0)   AS no   " +
                " FROM Sal_invoice_Hdr   ";
        Cursor c1 = sqlHandler.selectQuery(query);
        String max = "0";

        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            max = c1.getString(c1.getColumnIndex("no"));
            c1.close();
        }

        //max = (intToString(Integer.valueOf(max), 7));
        if (DocType == 1) {
            query = "Update OrdersSitting SET Sales ='" + max + "'";
        } else if (DocType == 2) {
            query = "Update OrdersSitting SET RetSales ='" + max + "'";
        } else if (DocType == 3) {
            query = "Update OrdersSitting SET ReciveItemToCustomer ='" + max + "'";
        }
        Toast.makeText(this, max.toString(), Toast.LENGTH_SHORT);
        //  query = "Update OrdersSitting SET Sales ='" + max + "'";
        //   sqlHandler.executeQuery(query);
   /* SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("m1",max);
    editor.commit();*/
    }

    private void CalcTax() {
        Double All_Dis = 0.0;
        Double RowTotal = 0.0;
        Double NetRow = 0.0;
        Double TaxAmt = 0.0;
        Double TaxFactor = 0.0;
        Double All_Dis_Per = 0.0;
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;


        Cls_Sal_InvItems contactListItems;
        for (int x = 0; x < contactList.size(); x++) {
            contactListItems = new Cls_Sal_InvItems();
            contactListItems = contactList.get(x);
            All_Dis = Double.parseDouble(contactListItems.getDis_Amt().replace(",", "")) + Double.parseDouble(contactListItems.getDisAmtFromHdr().replace(",", "")) + Double.parseDouble(contactListItems.getPro_amt().replace(",", ""));
            All_Dis_Per = SToDNew(contactListItems.getDiscount()) + SToDNew(contactListItems.getDisPerFromHdr()) + SToDNew(contactListItems.getPro_dis_Per());

            if (IncludeTax_Flag.isChecked()) {

                contactListItems.setprice(SToDNew(String.valueOf((SToD(contactListItems.getItemOrgPrice())) / ((SToDNew(contactListItems.getTax()) / 100) + 1))).toString());
            } else {
                contactListItems.setprice(String.valueOf(SToDNew(contactListItems.getItemOrgPrice())));

            }
            //  contactListItems.setDis_Amt( (SToD(contactListItems.getprice()) * SToD(contactListItems.getQty()))  * (100)   );
            RowTotal = SToDNew(contactListItems.getprice()) * SToDNew(contactListItems.getQty());
            TaxFactor = (Double.parseDouble(contactListItems.getTax()) / 100);
            NetRow = RowTotal - (RowTotal * (All_Dis_Per / 100));
             /*if(Tax_Include.isChecked()) {
                 TaxAmt = NetRow - ( NetRow / (TaxFactor + 1)) ;
                  TaxAmt = NetRow - ( NetRow / (TaxFactor + 1)) ;
             }
             else {
                TaxAmt = NetRow  *  TaxFactor ;
           }*/
            TaxAmt = NetRow * TaxFactor;
            contactListItems.setTax_Amt(df.format(TaxAmt).toString());
        }
        showList();
    }

    private Double SToDNew(String str) {
        str = str.replaceAll("[^\\d.]", "");
        String f = "";
        NumberFormat nf_out;
        nf_out = NumberFormat.getNumberInstance(Locale.ENGLISH);
        nf_out.setMaximumFractionDigits(4);
        str = str.replace(",", "");
        Double d = 0.0;
        if (str.length() == 0) {
            str = "0";
        }
        if (str.length() > 0)
            try {
                d = Double.parseDouble(str);
                str = nf_out.format(d).replace(",", "");

            } catch (Exception ex) {
                str = "0";
            }

        //df.setParseBigDecimal(true);

        d = Double.valueOf(str.trim()).doubleValue();

        return d;
    }

    private void CalcTotal() {
        Double Total, Tax_Total, Dis_Amt, Po_Total;
        Cls_Sal_InvItems contactListItems = new Cls_Sal_InvItems();
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;
        Double All_Dis = 0.0;
        Double All_Dis_Per = 0.0;
        Total = 0.0;
        Tax_Total = 0.0;
        Dis_Amt = 0.0;
        Po_Total = 0.0;
        TextView Subtotal = (TextView) findViewById(R.id.et_Total);
        TextView TotalTax = (TextView) findViewById(R.id.et_TotalTax);
        TextView dis = (TextView) findViewById(R.id.et_dis);
        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);

        Double TaxVal = 0.0;
        double TaxFactor = 0.0;
        CalcTax();
        Double RowTotal = 0.0;
        Double pq = 0.0;
        Double opq = 0.0;
        Double V_NetTotal = 0.0;
        for (int x = 0; x < contactList.size(); x++) {
            contactListItems = new Cls_Sal_InvItems();
            contactListItems = contactList.get(x);
            All_Dis = SToDNew(contactListItems.getDis_Amt()) + SToDNew(contactListItems.getDisAmtFromHdr()) + SToDNew(contactListItems.getPro_amt());
            All_Dis_Per = SToDNew(contactListItems.getDiscount()) + SToDNew(contactListItems.getDisPerFromHdr()) + SToDNew(contactListItems.getPro_dis_Per());
            pq = SToDNew(contactListItems.getprice()) * SToDNew(contactListItems.getQty());
            opq = SToDNew(contactListItems.getItemOrgPrice()) * SToDNew(contactListItems.getQty());

            Tax_Total = Tax_Total + (SToDNew(contactListItems.getTax_Amt().toString()));
            Dis_Amt = Dis_Amt + (((opq) * (All_Dis_Per / 100)));

            if (IncludeTax_Flag.isChecked()) {
                RowTotal = opq - ((opq) * (All_Dis_Per / 100));//+ SToD(contactListItems.getTax_Amt());
               /* if( All_Dis_Per > 0) {
                    Total = Total + ((opq * (All_Dis_Per / 100)) - SToD(contactListItems.getTax_Amt()) + Dis_Amt);
                }else{
                    Total = Total + ((opq ) - SToD(contactListItems.getTax_Amt()) );

                }*/


            } else {
                RowTotal = pq - ((pq) * (All_Dis_Per / 100)) + SToDNew(contactListItems.getTax_Amt());
                Total = Total + pq;

            }

            V_NetTotal = V_NetTotal + SToDNew(RowTotal.toString().replace(",", ""));

            contactListItems.setTotal((SToDNew(RowTotal.toString())).toString().replace(",", ""));
            All_Dis = 0.0;

        }
        NumberFormat nf_out;
        nf_out = NumberFormat.getNumberInstance(Locale.ENGLISH);
        nf_out.setMaximumFractionDigits(4);
        Total = V_NetTotal - Tax_Total + Dis_Amt;
        TotalTax.setText(String.valueOf(nf_out.format(Tax_Total)).replace(",", ""));
        Subtotal.setText(String.valueOf(nf_out.format(Total)).replace(",", ""));
        dis.setText(String.valueOf(nf_out.format(Dis_Amt)).replace(",", ""));


       /* if (Tax_Include.isChecked()){
            Po_Total = Po_Total + ((Double.parseDouble(Subtotal.getText().toString().replace(",", "")) - Double.parseDouble(dis.getText().toString().replace(",", "")) ) + Double.parseDouble(TotalTax.getText().toString().replace(",", ""))    );
        }
        else{
            Po_Total = Po_Total + ((Double.parseDouble(Subtotal.getText().toString().replace(",", "")) - Double.parseDouble(dis.getText().toString().replace(",","")) )   + Double.parseDouble(TotalTax.getText().toString().replace(",", "")) );
        }*/
        Po_Total = Po_Total + ((SToDNew(Subtotal.getText().toString()) - SToDNew(dis.getText().toString())) + SToDNew(TotalTax.getText().toString()));

        showList();
        NetTotal.setText(String.valueOf(nf_out.format(V_NetTotal)));
        CustAmtDt();
    }

    public void btn_show_Pop(View view) {
        showPop();
    }

    public void showPop() {
        EditText Order = (EditText) findViewById(R.id.et_OrdeNo);
        TextView accno = (TextView) findViewById(R.id.tv_acc);
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "Sal_inv");
        bundle.putString("IsNew", IsNew.toString());
        bundle.putString("CatNo", CatNo);
        bundle.putString("OrderNo", Order.getText().toString());
        bundle.putString("CustomerNo", accno.getText().toString());


        FragmentManager Manager = getFragmentManager();
        PopSal_Inv_Select_Items obj = new PopSal_Inv_Select_Items();
        obj.setArguments(bundle);
        obj.show(Manager, null);
        IsChange = true;
    }

    private int checkStoreQty(String ItemNo, String UnitNo, String qty, String bounce) {


        int q = 1;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String u = sharedPreferences.getString("UserID", "");

        sqlHandler = new SqlHandler(this);
        String query = "SELECT distinct  ifnull(Operand,1)  as  Operand  from UnitItems where  item_no ='" + ItemNo + "'  and  unitno ='" + UnitNo + "'";
        Cursor c1 = sqlHandler.selectQuery(query);
        String Operand = "0";
        Double Order_qty = 0.0;
        if (c1 != null && c1.getCount() != 0) {
            if (c1.getCount() > 0) {
                c1.moveToFirst();
                Operand = c1.getString(c1.getColumnIndex("Operand"));
            }
            c1.close();
        }

        Order_qty = Double.parseDouble(Operand) * (Double.parseDouble(qty) + SToD(bounce));


        query = "SELECT  distinct  ifnull( qty,0)   as  qty   from ManStore where  itemno = '" + ItemNo + "'  ";
        c1 = sqlHandler.selectQuery(query);

        Double Store_qty = 0.0;
        if (c1 != null && c1.getCount() != 0) {
            if (c1.getCount() > 0) {
                c1.moveToFirst();
                Store_qty = Double.parseDouble(c1.getString(c1.getColumnIndex("qty")));
            }
//mohammad

            c1.close();
        }

        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        query = "SELECT    distinct    ifnull( sum  ( ifnull( sid.qty,0)  * (ifnull( ui.Operand,1))) ,0)  as Sal_Qty  from  Sal_invoice_Hdr  sih inner join Sal_invoice_Det sid on  sid.OrderNo = sih.OrderNo   and sid.doctype = sih.doctype " +
                " inner join  UnitItems ui on ui.item_no  = sid.itemNo and ui.unitno = sid.unitNo" +
                "    where  sih.Post = -1 and ui.item_no ='" + ItemNo + "'  " +
                "    And sid.OrderNo !='" + pono.getText().toString() + "'  and sih.UserID ='" + u.toString() + "'";

        //   "    where  QtyStoreSer = "+MaxStoreQtySer+" and ui.item_no ='"+ItemNo+"'";

        c1 = sqlHandler.selectQuery(query);

        Double Sal_Qty = 0.0;
        if (c1 != null && c1.getCount() != 0) {
            if (c1.getCount() > 0) {
                c1.moveToFirst();
                Sal_Qty = Double.parseDouble((c1.getString(c1.getColumnIndex("Sal_Qty"))).toString());
            }
//88
            c1.close();
        }
        Double SumReturn;
        SumReturn = Double.parseDouble(DB.GetValue(this, "Sal_return_Det", "ifnull( sum  ( ifnull( qty,0)  * (ifnull(Operand,1))) ,0)", "ItemNo ='" + ItemNo + "'"));
        if ((Store_qty - Sal_Qty - Order_qty + SumReturn) >= 0) {
            q = 1;
        } else {
            q = -1;
        }
        // q = 1;
        return q;
    }

    public void Save_List(String ItemNo, String p, String q, String t, String u, String dis, String bounce, String ItemNm, String UnitName, String dis_Amt, String Operand, String Weight, String flag, String height, String Width, String fish, String number) {

        // FillAdapter();

        if (bounce.toString().equals(""))
            bounce = "0";

        if (dis.toString().equals(""))
            dis = "0";

        if (dis_Amt.toString().equals(""))
            dis_Amt = "0";

        if (p.toString().equals(""))
            p = "0";

        if (q.toString().equals(""))
            q = "0";

        if (Weight.toString().equals(""))
            Weight = "0";

        Double Item_Total, Price, Tax_Amt, Tax, Total, Net_Total, Tax_Total;

        if (flag.equals("0")) {
            for (int x = 0; x < contactList.size(); x++) {
                Cls_Sal_InvItems contactListItems = new Cls_Sal_InvItems();
                contactListItems = contactList.get(x);
                if (contactListItems.getNo().equals(ItemNo) && contactListItems.getProType() != "3") {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            this).create();
                    alertDialog.setTitle(tv_ScrTitle.getText().toString());
                    alertDialog.setMessage("المادة موجودة");            // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.tick);
                    alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialog.show();
                    return;
                }
            }
        }

        if (AllowSalInvMinus != 1 && DocType != 2) {
            if (checkStoreQty(ItemNo, u, q, bounce) < 0) {
                AlertDialog alertDialog = new AlertDialog.Builder(
                        this).create();
                alertDialog.setTitle(tv_ScrTitle.getText().toString());
                alertDialog.setMessage("الكمية المطلوبة غير متوفرة" + " #");            // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.tick);
                alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialog.show();
                return;
            }
        }

        Item_Total = Double.parseDouble(q) * Double.parseDouble(p);
        Price = Double.parseDouble(p);
        Tax = Double.parseDouble(t);
        Item_Total = Double.parseDouble(Item_Total.toString());
        Double DisValue = Double.parseDouble(dis_Amt.toString().replace(",", ""));

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;
        NumberFormat nf_out;
        nf_out = NumberFormat.getNumberInstance(Locale.ENGLISH);
        nf_out.setMaximumFractionDigits(4);

        Cls_Sal_InvItems contactListItems = new Cls_Sal_InvItems();
        contactListItems.setno(ItemNo);
        contactListItems.setName(ItemNm);
        if (IncludeTax_Flag.isChecked()) {
            contactListItems.setprice(String.valueOf(Price / ((Tax / 100) + 1)));
        } else {
            contactListItems.setprice(String.valueOf(Price));

        }

        // contactListItems.setprice(String.valueOf(Price));
        contactListItems.setItemOrgPrice(String.valueOf(Price));
        contactListItems.setQty(q);
        //contactListItems.setNote("");
        contactListItems.setTax(String.valueOf(Tax));
        contactListItems.setUnite(u);
        contactListItems.setBounce(bounce);
        contactListItems.setDiscount(dis);
        contactListItems.setProID("0");
        contactListItems.setDis_Amt(dis_Amt);
        contactListItems.setUniteNm(UnitName);
        contactListItems.setPro_amt("0");
        contactListItems.setPro_dis_Per("0");
        contactListItems.setPro_bounce(bounce);
        contactListItems.setPro_Total("0");
        contactListItems.setDisAmtFromHdr("0");
        contactListItems.setDisPerFromHdr("0");
        contactListItems.setTax_Amt("0");
        contactListItems.setProType("0");
        contactListItems.setOperand(Operand);
        contactListItems.setWeight(Weight);
        contactListItems.setTotal(String.valueOf(nf_out.format(Item_Total)));
        contactListItems.setNote(idfromjard);
        contactListItems.setSample(flag);
        contactListItems.setHeight(height);
        contactListItems.setWidth(Width);
        contactListItems.setFish(fish);
        contactListItems.setNumber(number);
        contactList.add(contactListItems);
        // Gf_Calc_Promotion();

        CalcTotal();
        showList();


    }

    public void Update_List(String ItemNo, String p, String q, String t, String u, String dis, String bounce, String ItemNm, String UnitName, String dis_Amt, String Operand, String Weight, String flag, String height, String Width, String fish, String number) {


        if (bounce.toString().equals(""))
            bounce = "0";

        if (dis.toString().equals(""))
            dis = "0";

        if (dis_Amt.toString().equals(""))
            dis_Amt = "0";

        if (p.toString().equals(""))
            p = "0";

        if (q.toString().equals(""))
            q = "0";

        if (Weight.toString().equals(""))
            Weight = "0";

        Double Item_Total, Price, Tax_Amt, Tax, Total, Net_Total, Tax_Total;
        if (checkStoreQty(ItemNo, u, q, bounce) < 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle(tv_ScrTitle.getText().toString());
            alertDialog.setMessage("الكمية المطلوبة غير متوفرة");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alertDialog.show();
            return;
        }


        Item_Total = Double.parseDouble(q) * Double.parseDouble(p);
        Price = Double.parseDouble(p);
        Tax = Double.parseDouble(t);
        Item_Total = Double.parseDouble(Item_Total.toString());
        Double DisValue = Double.parseDouble(dis_Amt.toString().replace(",", ""));
        // Tax_Amt =(Double.parseDouble(Tax.toString()) /100)   *  ( Double.parseDouble(Item_Total.toString().replace(",","")) -  Double.parseDouble( dis_Amt.toString().replace(",","") ));
      /*  double TaxFactor = 0.0 ;
        if(Tax_Include.isChecked()) {
            TaxFactor =    (Double.parseDouble(Tax.toString()) /100)    + 1   ;
            Tax_Amt = (Item_Total - DisValue) -  (( Item_Total  - DisValue)/TaxFactor)   ;
            //Item_Total = (Item_Total/TaxFactor);
        }else  {
            Tax_Amt =(Double.parseDouble(Tax.toString()) /100)   *   ( Double.parseDouble(Item_Total.toString().replace(",",""))- DisValue);
        }*/

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;

        Cls_Sal_InvItems contactListItems = new Cls_Sal_InvItems();
        contactListItems = contactList.get(position);
        contactListItems.setno(ItemNo);
        contactListItems.setName(ItemNm);
        if (IncludeTax_Flag.isChecked()) {
            contactListItems.setprice(String.valueOf(Price / ((Tax / 100) + 1)));
        } else {
            contactListItems.setprice(String.valueOf(Price));

        }
        //contactListItems.setprice(String.valueOf(Price));
        contactListItems.setItemOrgPrice(String.valueOf(Price));
        contactListItems.setQty(q);
        contactListItems.setTax(String.valueOf(Tax));
        contactListItems.setUnite(u);
        contactListItems.setBounce(bounce);
        contactListItems.setDiscount(dis);
        contactListItems.setProID("");
        contactListItems.setDis_Amt(dis_Amt);
        contactListItems.setUniteNm(UnitName);
        contactListItems.setPro_amt("0");
        contactListItems.setPro_dis_Per("0");
        contactListItems.setPro_bounce(bounce);
        contactListItems.setPro_Total("0");
        contactListItems.setDisAmtFromHdr("0");
        contactListItems.setDisPerFromHdr("0");
        contactListItems.setTax_Amt("0");
        contactListItems.setProType("0");
        contactListItems.setOperand(Operand);
        contactListItems.setWeight(Weight);
        contactListItems.setTotal(String.valueOf(df.format(Item_Total)));
        contactListItems.setNote("تعديل فاتورة بيع");
        contactListItems.setSample(flag);
        contactListItems.setHeight(height);
        contactListItems.setWidth(Width);
        contactListItems.setFish(fish);
        contactListItems.setNumber(number);

        CalcTotal();
        showList();


        // Gf_Calc_Promotion();
    }

    public void Save_Method(String m, String p, String q, String t, String u) {
        EditText Order = (EditText) findViewById(R.id.et_OrdeNo);
        String query = "INSERT INTO Sal_invoice_Det(doctype,OrderNo,itemNo,unitNo,price,qty,tax) values (" + DocType.toString() + "," + Order.getText().toString() + ",'" + m + "','" + u + "','" + p + "','" + q + "','" + t + "')";
        sqlHandler.executeQuery(query);
        showList();
    }

    public void btn_delete(View view) {
        /*int flg;
        flg =Integer.parseInt( DB.GetValue(this,"ComanyInfo","AllowDeleteInvoice","1=1"));*/

        //  RemoveAnmation();


        ////////////////////////////////////////////////////////////////////
 /*       AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setTitle(tv_ScrTitle.getText().toString());
        alertDialog.setMessage("لا يمكن الحذف");
        alertDialog.setIcon(R.drawable.tick);
        alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;

            }
        });

        alertDialog.show();*/


        ///////////////////////////////////////////////////////////////////////
        String q = "SELECT *  from  Sal_invoice_Hdr where   Post >0 AND   OrderNo ='" + et_OrdeNo.getText().toString() + "'";

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("فاتورة مبيعات");
            alertDialog.setMessage("لقد تم ترحيل الفاتورة لايمكن التعديل");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {


                }
            });

            alertDialog.show();


            c1.close();
            return;
        } else {


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting Dialog Title
            alertDialog.setTitle("حذف فاتورة مبيعات");

            // Setting Dialog Message
            alertDialog.setMessage("هل انت متاكد من عملية الحذف");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.delete);
            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
                    //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Delete_Record_PO();
                    // Write your code here to invoke YES event
                    // Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                }
            });


            // Showing Alert Message
            alertDialog.show();
        }
    }

    public void Delete_Record_PO() {

        TextView OrdeNo = (TextView) findViewById(R.id.et_OrdeNo);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String currentDateandTime = sdf.format(new Date());


        String query = "Delete from  Sal_invoice_Hdr where ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo ='" + OrdeNo.getText().toString() + "'";
        sqlHandler.executeQuery(query);


        query = "Delete from  Sal_invoice_Det where ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo ='" + OrdeNo.getText().toString() + "'";
        sqlHandler.executeQuery(query);


        query = "Delete from  RecVoucherSeles where  SalesOrderNo ='" + OrdeNo.getText().toString() + "'";
        sqlHandler.executeQuery(query);


        GetMaxPONo();
        showList();
        TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView accno = (TextView) findViewById(R.id.tv_acc);
        CustNm.setText("");
        accno.setText("");

        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);

        IncludeTax_Flag.setChecked(false);
        chk_Type.setChecked(false);


        EditText et_hdr_Disc = (EditText) findViewById(R.id.et_hdr_Disc);
        et_hdr_Disc.setText("0");
        CheckBox chk_hdr_disc = (CheckBox) findViewById(R.id.chk_hdr_disc);
        chk_hdr_disc.setChecked(false);

        InsertLogTrans obj = new InsertLogTrans(Sale_InvoiceActivity.this, SCR_NO, SCR_ACTIONS.Delete.getValue(), et_OrdeNo.getText().toString(), tv_acc.getText().toString(), "", "0");


        IsNew = true;
        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();

        // Setting Dialog Title
        alertDialog.setTitle(tv_ScrTitle.getText().toString());

        // Setting Dialog Message
        alertDialog.setMessage("تمت عملية الحذف بنجاح");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    public void btn_Search_Orders(View view) {
        ExistAfterSacve = 0;

        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "Sal_inv");
        bundle.putString("doctype", DocType.toString());
        if (chk_Type.isChecked()) {
            bundle.putString("typ", "0");
        } else {
            bundle.putString("typ", "-1");
        }
        FragmentManager Manager = getFragmentManager();
        Sal_Inv_SearchActivity obj = new Sal_Inv_SearchActivity();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }

    public void Set_Order(String No) {
        No = No.replace("\u202c", "").replace("\u202d", "");
        TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView no = (TextView) findViewById(R.id.et_OrdeNo);
        TextView accno = (TextView) findViewById(R.id.tv_acc);
        EditText et_hdr_Disc = (EditText) findViewById(R.id.et_hdr_Disc);

        TextView tv_NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        TextView et_TotalTax = (TextView) findViewById(R.id.et_TotalTax);
        TextView et_dis = (TextView) findViewById(R.id.et_dis);
        no.setText(No);
        DriverNm = (MyTextView) mNav.findViewById(R.id.tv_DriverNm);

        DriverNm.setText("");
        FillAdapter();
        showList();
        String q = "Select  distinct  ifnull( s.hdr_dis_per,'0')  as   hdr_dis_per , ifnull( d.DriverNm,'') as drivernm, ifnull( s.hdr_dis_value,0) as hdr_dis_value ,s.Nm ,     s.inovice_type   ,s.include_Tax ,  s.disc_Total, s.OrderNo,s.Net_Total,s.Tax_Total ,s.acc ,s.date , c.name " +
                "    from  Sal_invoice_Hdr s   " +
                "   left join Customers c on c.no =s.acc   " +
                "   left join Driver d on d.DriverNo =s.driverno   " +
                "where  ifnull(s.doctype,'1')='" + DocType.toString() + "'  and   s.OrderNo = '" + No + "'";

        Cursor c1 = sqlHandler.selectQuery(q);
        CustNm.setText("");
        accno.setText("");

        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);


        CheckBox chk_hdrdiscount = (CheckBox) findViewById(R.id.chk_hdr_disc);
        chk_Type.setChecked(false);
        IncludeTax_Flag.setChecked(false);
        chk_hdrdiscount.setChecked(false);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.getCount() > 0) {
                c1.moveToFirst();
                try {


                    CustNm.setText(c1.getString(c1.getColumnIndex("Nm")).toString());

                } catch (Exception ex) {

                    Toast.makeText(this, "لا يمكن استرجاع الحساب", Toast.LENGTH_SHORT).show();
                }
                tv_HeaderDscount = (MyTextView) mNav.findViewById(R.id.tv_HeaderDscount);

                tv_HeaderDscount.setText(c1.getString(c1.getColumnIndex("hdr_dis_per")) + "%");
                accno.setText(c1.getString(c1.getColumnIndex("acc")));
                tv_NetTotal.setText(c1.getString(c1.getColumnIndex("Net_Total")));
                et_TotalTax.setText(c1.getString(c1.getColumnIndex("Tax_Total")));
                et_dis.setText(c1.getString(c1.getColumnIndex("disc_Total")));
                et_hdr_Disc.setText(c1.getString(c1.getColumnIndex("hdr_dis_value")));
                DriverNm.setText(c1.getString(c1.getColumnIndex("drivernm")));


                if (c1.getString(c1.getColumnIndex("include_Tax")).equals("0")) {
                    IncludeTax_Flag.setChecked(true);
                }
                if (c1.getString(c1.getColumnIndex("inovice_type")).equals("0")) {
                    chk_Type.setChecked(true);

                }
                chk_Type.setEnabled(true);
            }
            c1.close();
        }


        IsChange = false;
        IsNew = false;
    }

    public void RemoveAnmation() {
        ImageButton imageButton8 = (ImageButton) findViewById(R.id.imageButton8);
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton imageButton6 = (ImageButton) findViewById(R.id.imageButton6);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton imageButton7 = (ImageButton) findViewById(R.id.imageButton7);
        ImageButton imageButton9 = (ImageButton) findViewById(R.id.imageButton9);


        imageButton8.clearAnimation();
        imageButton3.clearAnimation();
        imageButton6.clearAnimation();
        imageButton2.clearAnimation();
        imageButton7.clearAnimation();
        imageButton9.clearAnimation();

    }

    public void SaveDelveryNm(final View view) {
        final TextView OrdeNo = (TextView) findViewById(R.id.et_OrdeNo);

        final String DelveryNm = DB.GetValue(Sale_InvoiceActivity.this, "Sal_invoice_Hdr", "DelveryNm", " ifnull(doctype,'1')='" + DocType.toString() + "'  and  OrderNo   ='" + OrdeNo.getText().toString().replace("\u202c", "").replace("\u202d", "") + "'");


        if (DelveryNm.trim().length() < 4) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Sale_InvoiceActivity.this);
            alertDialog.setTitle(tv_ScrTitle.getText().toString());
            alertDialog.setMessage("الرجاء ادخال اسم مستلم البضاعة");

            final EditText input = new EditText(Sale_InvoiceActivity.this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);

            input.setBackground(getResources().getDrawable(R.drawable.btn_cir_white_fill_black));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            alertDialog.setView(input);
            alertDialog.setIcon(R.drawable.key);

            alertDialog.setPositiveButton("موافق",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            if (input.getText().toString().equalsIgnoreCase("")) {
                                Toast.makeText(getApplicationContext(), "اسم مستلم البضاعة غير مدخل", Toast.LENGTH_SHORT).show();
                                input.setError("required!");
                                input.requestFocus();
                                return;

                            } else {
                                ContentValues cv = new ContentValues();
                                cv.put("DelveryNm", input.getText().toString().replace("\u202c", "").replace("\u202d", ""));
                                ;
                                Long i;
                                i = sqlHandler.Update("Sal_invoice_Hdr", cv, "ifnull(doctype,'1')='" + DocType.toString() + "'  and  OrderNo ='" + OrdeNo.getText().toString().replace("\u202c", "").replace("\u202d", "") + "'");
                                if (i > 0) {
                                    DoPrint(view);
                                    Toast.makeText(Sale_InvoiceActivity.this, "تمت عملية تخزين مستلم البضاعة بنجاح  ", Toast.LENGTH_SHORT).show();

                                }


                            }

                        }
                    });

            alertDialog.setNegativeButton("لا",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            alertDialog.show();
        } else {
            DoPrint(view);
        }


    }

    public void btn_print(View view) {
        // ImageButton imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        // InsertLogTrans obj=new InsertLogTrans(Sale_InvoiceActivity.this,SCR_NO , SCR_ACTIONS.Print.getValue(),et_OrdeNo.getText().toString(),tv_acc.getText().toString(),"");


        //DoPrint = 1;
        if (IsChange == true)
            btn_save_po(view);
        DoPrint(view);


    }

    private void DoPrint(View view) {


        String q1 = "SELECT distinct *  from  Sal_invoice_Hdr where    " +
                "   Post < 0 AND   OrderNo ='" + OrderNo.getText().toString().trim() + "'";

        Cursor c1 = sqlHandler.selectQuery(q1);
        if (c1 != null && c1.getCount() != 0) {
            new SweetAlertDialog(Sale_InvoiceActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("فاتورة المبيعات")
                    .setContentText("لا يمكن الطباعة الا بعد الاعتماد")
                    .setCustomImage(R.drawable.error_new)
                    .show();

            c1.close();
            return;
        }
        TextView OrdeNo = (TextView) findViewById(R.id.et_OrdeNo);
        TextView tv_acc = (TextView) findViewById(R.id.tv_acc);
        CheckBox chk_showTax = (CheckBox) findViewById(R.id.chk_showTax);
        chk_cus_name = (CheckBox) findViewById(R.id.chk_cus_name);
        Intent k;
        if (ComInfo.ComNo == Companies.bristage.getValue()) {
            k = new Intent(this, Convert_Sal_Invoice_To_ImgActivity_Prestaige.class);
        } else if (ComInfo.ComNo == Companies.Arabian.getValue()) {
            k = new Intent(this, Convert_Sal_Invoice_To_ImgActivity_Tab_10.class);
        } else if (ComInfo.ComNo == Companies.beutyLine.getValue()) {
            String q;

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String CHECK_IMG = DB.GetValue(this, "Customers", "CloseVisitWithoutimg", "no='" + tv_acc.getText() + "'");
            if (CHECK_IMG.equalsIgnoreCase("0")) {
                q = "Select * from VisitImagesHdr where V_OrderNo='" + sharedPreferences.getString("V_OrderNo", "0") + "'";
                Cursor c = sqlHandler.selectQuery(q);
                if (c != null && c.getCount() > 0) {
                    c.close();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            this).create();
                    alertDialog.setTitle("المجرة الدولية");
                    alertDialog.setMessage("لا يمكن طباعة الفاتورة دون اخذ صور");
                    alertDialog.setIcon(R.drawable.error_new);
                    alertDialog.setButton("موافق ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            GotoVisitImage();
                        }
                    });
                    alertDialog.show();
                    return;
                }
            }
            k = new Intent(this, Convert_Sal_Invoice_To_ImgActivity_Line.class);
        } else if (ComInfo.ComNo == Companies.Ukrania.getValue()) {
            k = new Intent(this, Convert_Sal_Invoice_To_ImgActivity_Okrania_Ipad.class);
        } else if (ComInfo.ComNo == Companies.Afrah.getValue() || ComInfo.ComNo == Companies.nwaah.getValue()) {
            //  k = new Intent(this, Convert_Sal_Invoice_To_ImgActivity_Line.class);

            k = new Intent(this, Xprinter_SalesInvoice.class);

            //k = new Intent(this, Convert_Sal_Invoice_To_ImgActivity_Okrania_Ipad.class);


        } else if (ComInfo.ComNo == Companies.diamond.getValue()) {
            //  k = new Intent(this, Convert_Sal_Invoice_To_ImgActivity_Line.class);

            k = new Intent(this, Convert_Sal_Invoice_To_ImgActivity.class);

            //k = new Intent(this, Convert_Sal_Invoice_To_ImgActivity_Okrania_Ipad.class);


        } else if (ComInfo.ComNo == Companies.electronic.getValue()) {
            //  k = new Intent(this, Convert_Sal_Invoice_To_ImgActivity_Line.class);

            k = new Intent(this, Xprinter_SalesInvoice_ele.class);
            k.putExtra("GSPN", "55566");
            //k = new Intent(this, Convert_Sal_Invoice_To_ImgActivity_Okrania_Ipad.class);


        } else {
            k = new Intent(this, Convert_Sal_Invoice_To_ImgActivity.class);
        }

        k.putExtra("OrderNo", OrdeNo.getText().toString().replace("\u202c", "").replace("\u202d", ""));
        if (chk_showTax.isChecked()) {
            k.putExtra("ShowTax", "1");
        } else {
            k.putExtra("ShowTax", "0");
        }
        IncludeTax_Flag = (CheckBox) findViewById(R.id.chk_Tax_Include);
        if (IncludeTax_Flag.isChecked()) {
            k.putExtra("Tax_Include", "1");
        } else {
            k.putExtra("Tax_Include", "0");
        }
        if (chk_cus_name.isChecked() == true) {
            k.putExtra("name", "1");
        } else {
            k.putExtra("name", "0");
        }
        DoPrint = 0;
        startActivity(k);
        // btn_new(view);
    }

    private void GotoVisitImage() {
        Intent v = new Intent(this, VisitImges.class);
        startActivity(v);
    }

    public void btn_new(View view) {
        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
        chk_Type.setEnabled(true);


        flagV = 0;
        // RemoveAnmation();
        ImageButton imageButton8 = (ImageButton) findViewById(R.id.imageButton8);
        // imageButton8.startAnimation(shake);
        //Fade_Fun(imageButton8);
        ExistAfterSacve = 0;
        GetMaxPONo();
        showList();

        TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView accno = (TextView) findViewById(R.id.tv_acc);
        EditText et_hdr_Disc = (EditText) findViewById(R.id.et_hdr_Disc);

        CheckBox chk_showTax = (CheckBox) findViewById(R.id.chk_showTax);
        et_hdr_Disc.setText("0");
        CustNm.setText("");
        accno.setText("");
        if (ComInfo.ComNo == Companies.Atls.getValue()) {
            //chk_Type.setChecked(true);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String cash_bill = DB.GetValue(this, "manf", "PosAcc", "man ='" + sharedPreferences.getString("UserID", "0") + "'");

            accno.setText(cash_bill);
            CustNm.setText("حساب نقدي");
        } else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            accno.setText(sharedPreferences.getString("CustNo", ""));
            CustNm.setText(sharedPreferences.getString("CustNm", ""));
        }
        CheckBox chk_hdr_disc = (CheckBox) findViewById(R.id.chk_hdr_disc);

        chk_Type = (CheckBox) findViewById(R.id.chk_Type);
        IsNew = true;
        if (ComInfo.ComNo == Companies.Afrah.getValue()) {
            //chk_Type.setChecked(true);
            IncludeTax_Flag.setChecked(false);
            IncludeTax_Flag.setEnabled(false);
        }
        chk_Type.setChecked(true);
        chk_Type.setEnabled(true);
        chk_hdr_disc.setChecked(false);
        BalanceQtyTrans = false;


//        DriverNm.setText("");
        if (ComInfo.ComNo == 2) {

            accno.setText("");
            CustNm.setText("");
        }

        if (ComInfo.ComNo == 3) {
            // chk_Type.setChecked(true);
            IncludeTax_Flag.setChecked(false);
            chk_showTax.setChecked(true);
            IncludeTax_Flag.setVisibility(View.INVISIBLE);
        }
        if (ComInfo.ComNo == Companies.beutyLine.getValue()) {

            IncludeTax_Flag.setChecked(false);
            IncludeTax_Flag.setVisibility(View.INVISIBLE);

            chk_showTax.setChecked(true);
            chk_showTax.setVisibility(View.INVISIBLE);

            chk_cus_name.setChecked(true);

        }
        tv_HeaderDscount = (MyTextView) mNav.findViewById(R.id.tv_HeaderDscount);
        tv_HeaderDscount.setText("0.0%");

        clearList();

    }

    public void btn_back(View view) {
        //   RemoveAnmation();
        ImageButton imageButton7 = (ImageButton) findViewById(R.id.imageButton7);
        // imageButton7.startAnimation(shake);
        ExistAfterSacve = 1;
        if (contactList.size() > 0 && IsChange == true) {
            btn_save_po(view);
        } else {
            Intent k = new Intent(this, JalMasterActivity.class);
            startActivity(k);
        }


    }

    int position;

    public void btn_Delete_Item(final View view) {

        String q = "SELECT *  from  Sal_invoice_Hdr where   Post >0 AND   OrderNo ='" + et_OrdeNo.getText().toString() + "'";

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("فاتورة مبيعات");
            alertDialog.setMessage("لقد تم ترحيل الفاتورة لايمكن التعديل");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {


                }
            });

            alertDialog.show();


            c1.close();
            return;
        } else {

            position = lvCustomList.getPositionForView(view);
            registerForContextMenu(view);
            openContextMenu(view);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Cls_Sal_InvItems contactListItems =new Cls_Sal_InvItems();

        Toast.makeText(Sale_InvoiceActivity.this,String.valueOf(525),Toast.LENGTH_SHORT).show();

        menu.setHeaderTitle(contactList.get(position).getName());
        menu.add(Menu.NONE, 1, Menu.NONE, "تعديل");
        menu.add(Menu.NONE, 2, Menu.NONE, "حذف");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case 1: {

                ArrayList<Cls_Sal_InvItems> Itemlist = new ArrayList<Cls_Sal_InvItems>();

                Itemlist.add(contactList.get(position));
                if (contactList.get(position).getProType().equals("3")) {
                    break;
                }


                TextView accno = (TextView) findViewById(R.id.tv_acc);

                Bundle bundle = new Bundle();
                bundle.putString("Scr", "Sal_inv");
                bundle.putString("CatNo", CatNo);
                bundle.putString("OrderNo", pono.getText().toString());
                bundle.putString("IsNew", IsNew.toString());
                bundle.putString("CustomerNo", accno.getText().toString());
                bundle.putSerializable("List", Itemlist);
                FragmentManager Manager = getFragmentManager();
                PopSal_Inv_Select_Items obj = new PopSal_Inv_Select_Items();
                obj.setArguments(bundle);
                obj.show(Manager, null);

            }
            break;
            case 2: {


               /* SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("delete","yes");
                editor.apply();
*/
                String q1 = "Select * From Sal_invoice_Hdr Where ifnull(doctype,'1')='" + DocType.toString() + "'  and    OrderNo='" + pono.getText().toString() + "'";
                Cursor c1;
                c1 = sqlHandler.selectQuery(q1);


                if (c1 != null && c1.getCount() != 0) {
                    IsNew = false;
                    c1.close();
                } else {
                    IsNew = true;
                }

                if (ComInfo.ComNo == 1 && IsNew == false) {


                    AlertDialog Dialog = new AlertDialog.Builder(
                            this).create();
                    Dialog.setTitle(tv_ScrTitle.getText().toString());
                    Dialog.setMessage("لا يمكن حذف  المواد ");
                    Dialog.setIcon(R.drawable.tick);
                    Dialog.setButton("موافق", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            return;

                        }
                    });

                    Dialog.show();
                } else {

                    if (contactList.get(position).getProType().equals("3")) {
                        break;
                    }


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setTitle(tv_ScrTitle.getText().toString());
                    alertDialog.setMessage("هل انت متاكد من عملية الحذف");
                    alertDialog.setIcon(R.drawable.delete);
                    alertDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String q = "delete  from AddcubicM where     Item_No ='" + contactList.get(position).getNo() + "' and OrderNo ='" + pono.getText().toString() + "'";
                            sqlHandler.executeQuery(q);
                            contactList.remove(position);

                            ResetPromotion();

                            // Gf_Calc_Promotion();
                            CalcTotal();
                            showList();
/*
                            String q = "Delete from  Sal_invoice_Det where  ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo ='" + pono.getText().toString().replace("\u202c", "").replace("\u202d", "") + "'";
                            //  String q = "Delete from  Sal_invoice_Det where OrderNo ='" + pono.getText().toString().replace("\u202c","").replace("\u202d","") + "'";
                            sqlHandler.executeQuery(q);
*/


                     /*       String q = "Delete from  Sal_invoice_Det where  ifnull(doctype,'1')='"+DocType.toString()+ "'  and   OrderNo ='" + pono.getText().toString().replace("\u202c","").replace("\u202d","") + "' and itemNo='"+contactList.get(position).getName()+"'";
                            //  String q = "Delete from  Sal_invoice_Det where OrderNo ='" + pono.getText().toString().replace("\u202c","").replace("\u202d","") + "'";
                            sqlHandler.executeQuery(q);*/


                        }
                    });

                    alertDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();

                }
            }
            break;

        }

        return super.onContextItemSelected(item);
    }

    //////////////////////////////////////////////////////////////
    private void fill_Offers_Group_Effict(String GroupNo) {

        offer_groups_Effict_List.clear();
        String query = " select distinct og.ID  , og.grv_no,   og.gro_name,  og.gro_type,  og.item_no,  og.unit_no, og.qty,  og.SerNo" +
                " from Offers_Groups og inner join Offers_Dtl_Cond on Gro_Num = og.grv_no where Trans_ID ='" + GroupNo + "'";
        Cursor c1 = sqlHandler.selectQuery(query);

        int f = 1;

        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {

                do {
                    Cls_Offers_Groups cls_offers_groups = new Cls_Offers_Groups();

                    ////////////////////////////////////////////////
                    Cls_Sal_InvItems Inv_Obj;

                    for (int j = 0; j < contactList.size(); j++) {
                        Inv_Obj = new Cls_Sal_InvItems();
                        Inv_Obj = contactList.get(j);

                        if (Inv_Obj.getno().equals(c1.getString(c1.getColumnIndex("item_no"))) && c1.getString(c1.getColumnIndex("gro_type")).equals("1")) {

                            f = Integer.parseInt(Inv_Obj.getQty()) / Integer.parseInt(c1.getString(c1.getColumnIndex("qty")));

                        } else {
                            f = 1;
                        }

                    }
                    ///////////////////////////////////////////////////

                    cls_offers_groups.setID(c1.getString(c1
                            .getColumnIndex("ID")));
                    cls_offers_groups.setGrv_no(c1.getString(c1
                            .getColumnIndex("grv_no")));
                    cls_offers_groups.setGro_ename(c1.getString(c1
                            .getColumnIndex("gro_name")));
                    cls_offers_groups.setGro_type(c1.getString(c1
                            .getColumnIndex("gro_type")));
                    cls_offers_groups.setItem_no(c1.getString(c1
                            .getColumnIndex("item_no")));
                    cls_offers_groups.setUnit_no(c1.getString(c1
                            .getColumnIndex("unit_no")));
                    cls_offers_groups.setQty(c1.getString(c1
                            .getColumnIndex("qty")));
                    cls_offers_groups.setSerNo(c1.getString(c1
                            .getColumnIndex("SerNo")));
                    cls_offers_groups.setSerNo(c1.getString(c1
                            .getColumnIndex("SerNo")));
                    cls_offers_groups.setFactor(f + "");
                    offer_groups_Effict_List.add(cls_offers_groups);

                } while (c1.moveToNext());
            }
            c1.close();
        }

    }

    private int checkItemInGroup(String ItemNo, String Group) {
        sqlHandler = new SqlHandler(this);
        String q = " select distinct * from Offers_Dtl_Cond odc  inner join Offers_Groups ogs on ogs.grv_no= odc.Gro_Num  " +
                "  where odc.Trans_ID ='" + Group + "' and ogs.item_no = '" + ItemNo + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            c1.close();
            return 1;
        }

        return 0;
    }

    private void InsertItemsOffersType3(String GroupNo, int f) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        Cls_Sal_InvItems Inv_Obj;
        cls_offers_dtl_giftses.clear();

        for (int j = 0; j < contactList.size(); j++) {
            Inv_Obj = new Cls_Sal_InvItems();
            Inv_Obj = contactList.get(j);

            if (checkItemInGroup(Inv_Obj.getNo(), GroupNo) == 1) {
                Inv_Obj.setProID(GroupNo);

            }

        }
        String query = " Select distinct u.UnitName , odc.Allaw_Repet,   i.Item_Name ,  odg.Item_No , odg.Unit_No , odg.QTY , odg.Unit_Rate from Offers_Dtl_Gifts  odg" +
                " Left join invf i on i.Item_No =  odg.Item_No   " +
                "  left join Unites  u on u.Unitno = odg.Unit_No" +
                "   left join  Offers_Dtl_Cond odc on odc.Trans_ID =  odg.Trans_ID" +
                " where odg.Trans_ID = '" + GroupNo + "'"; //"  where odc.Gro_Num  = '"+GroupNo+"'";

        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {


                    if (c1.getString(c1.getColumnIndex("Allaw_Repet")).equals("0")) {
                        f = 1;
                    }


                    String msg = " لا يمكن تطبيق العرض رقم " + GroupNo + " الخاص بالمادة رقم " + c1.getString(c1.getColumnIndex("Item_No")) + "وذلك بسبب عدم توافر كمية";
                    if (checkStoreQty(c1.getString(c1.getColumnIndex("Item_No")), c1.getString(c1.getColumnIndex("Unit_No")), "" + (f * Double.parseDouble(c1.getString(c1.getColumnIndex("QTY")))), "0") < 0) {

                        alertDialog.setTitle("عروض العميل");
                        alertDialog.setMessage(msg);            // Setting Icon to Dialog
                        alertDialog.setIcon(R.drawable.tick);
                        alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        alertDialog.show();

                    } else {
                        Inv_Obj = new Cls_Sal_InvItems();
                        Inv_Obj.setno(c1.getString(c1.getColumnIndex("Item_No")));
                        Inv_Obj.setName(c1.getString(c1.getColumnIndex("Item_Name")));
                        Inv_Obj.setprice("0");
                        Inv_Obj.setItemOrgPrice("0");
                        Inv_Obj.setQty("0");
                        Inv_Obj.setNote("0");
                        Inv_Obj.setSample(c1.getString(c1.getColumnIndex("sample")));
                        Inv_Obj.setTax("0");
                        Inv_Obj.setUnite(c1.getString(c1.getColumnIndex("Unit_No")));
                        Inv_Obj.setOperand(c1.getString(c1.getColumnIndex("Unit_Rate")));
                        Inv_Obj.setPro_bounce(String.valueOf(f * Double.parseDouble(c1.getString(c1.getColumnIndex("QTY")))));
                        Inv_Obj.setDiscount("0");
                        Inv_Obj.setProID(GroupNo);
                        Inv_Obj.setDis_Amt("0");
                        Inv_Obj.setUniteNm(c1.getString(c1.getColumnIndex("UnitName")));
                        Inv_Obj.setPro_amt("0");
                        Inv_Obj.setPro_dis_Per("0");
                        Inv_Obj.setBounce("0");
                        Inv_Obj.setPro_Total("0");
                        Inv_Obj.setTax_Amt("0");
                        Inv_Obj.setTotal("0");
                        Inv_Obj.setDisPerFromHdr("0");
                        Inv_Obj.setDisAmtFromHdr("0");
                        Inv_Obj.setProType("3");
                        Inv_Obj.setWeight("0");
                        contactList.add(Inv_Obj);
                        CalcTotal();
                        showList();
                    }

                } while (c1.moveToNext());

            }
            c1.close();
        }

    }

    private int CalcFactor(String g) {
        int f = 1;

        return f;
    }

    public void Apply_Promotion() {

        Double Rowtotal = 0.0;
        int x;
        Cls_Sal_InvItems Inv_Obj;
        Cls_Offers_Groups Effict_List;
        int min = 0;
        for (x = 0; x < cls_offers_hdrs.size(); x++) { // Fill Promtion With Item Promotions

            fill_Offers_Group_Effict(cls_offers_hdrs.get(x).getID());


            if (offer_groups_Effict_List.size() > 0) {

                min = Integer.valueOf(offer_groups_Effict_List.get(0).getFactor());
                for (int m = 0; m < offer_groups_Effict_List.size(); m++) {
                    if (Integer.valueOf(offer_groups_Effict_List.get(m).getFactor()) < min) {
                        min = Integer.valueOf(offer_groups_Effict_List.get(m).getFactor());
                    }
                }

            } else {
                min = 1;
            }

            if (cls_offers_hdrs.get(x).getOffer_Result_Type().toString().equals("3")) {
                InsertItemsOffersType3(cls_offers_hdrs.get(x).getID(), min);
            }

            int f = 0;
            ////////////////////////////////////////////////////////////////
            String query = " select distinct Offers_Dtl_Cond.Allaw_Repet  from Offers_Dtl_Cond " +
                    " where Offers_Dtl_Cond.Trans_ID = '" + cls_offers_hdrs.get(x).getID() + "'";
            Cursor c1 = sqlHandler.selectQuery(query);
            if (c1 != null && c1.getCount() != 0) {

                if (c1.moveToFirst()) {
                    if (c1.getString(c1.getColumnIndex("Allaw_Repet")).equals("0")) {
                        min = 1;
                    }
                }
                c1.close();
            }


            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            for (int j = 0; j < contactList.size(); j++) {
                Inv_Obj = new Cls_Sal_InvItems();
                Inv_Obj = contactList.get(j);
                for (int i = 0; i < offer_groups_Effict_List.size(); i++) {
                    Effict_List = new Cls_Offers_Groups();
                    Effict_List = offer_groups_Effict_List.get(i);

                    if (Inv_Obj.getno().equals(Effict_List.getItem_no())) {
                        //fill FACTOR
                        if (cls_offers_hdrs.get(x).getOffer_Result_Type().toString().equals("1")) { // per
                            if (Inv_Obj.getProID().toString().equals("")) {
                                Inv_Obj.setProID(cls_offers_hdrs.get(x).getID());
                                Inv_Obj.setPro_dis_Per(cls_offers_hdrs.get(x).getOffer_Dis().toString());
                                Rowtotal = Double.parseDouble(Inv_Obj.getQty()) * Double.parseDouble(Inv_Obj.getprice());
                                Inv_Obj.setPro_amt(String.valueOf(Rowtotal * ((min * Double.parseDouble(Inv_Obj.getPro_dis_Per()) / 100))));
                                Inv_Obj.setPro_dis_Per(String.valueOf((min * (Double.parseDouble(cls_offers_hdrs.get(x).getOffer_Dis())))));


                            }
                        } else if (cls_offers_hdrs.get(x).getOffer_Result_Type().toString().equals("2")) { // Amt
                            if (Inv_Obj.getProID().toString().equals("")) {
                                // Set Factor

                                Inv_Obj.setProID(cls_offers_hdrs.get(x).getID());
                                Inv_Obj.setPro_amt(String.valueOf(min * Double.parseDouble(cls_offers_hdrs.get(x).getOffer_Amt().toString())));
                                Rowtotal = Double.parseDouble(Inv_Obj.getQty()) * Double.parseDouble(Inv_Obj.getprice());
                                String per = String.valueOf(100 * (min * (Double.parseDouble(cls_offers_hdrs.get(x).getOffer_Amt().toString())) / Rowtotal));
                                Inv_Obj.setPro_dis_Per(String.valueOf(SToD(per)));


                            }

                        }


                    }
                }

            }

        }

    }

    private void Calc_LowPriPro() {
        int f = 1;
        sqlHandler = new SqlHandler(this);
        final TextView et_Total = (TextView) findViewById(R.id.et_Total);
        final TextView et_dis = (TextView) findViewById(R.id.et_dis);
        int f1 = 0;
        int f2 = 0;

        String q = "Select distinct Offer_Dis from Offers_Hdr where  Offer_Type = 1 and   TotalValue = 50  ";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                f2 = 1;

            }

            c1.close();
        }

        q = "Select distinct Offer_Dis from Offers_Hdr where  Offer_Type = 1 and   TotalValue = 100  ";
        c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                f1 = 1;

            }

            c1.close();
        }
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("العروض ");
        alertDialog.setIcon(R.drawable.tick);

        Double Re = 0.0;


        Cls_Sal_InvItems Inv_Obj;
        for (int i = 0; i < contactList.size(); i++) {
            Inv_Obj = new Cls_Sal_InvItems();
            Inv_Obj = contactList.get(i);
            if (Inv_Obj.getProID().equals("")) {
                Re = Re + SToD(Inv_Obj.getTax_Amt()) + (SToD(Inv_Obj.getQty()) * SToD(Inv_Obj.getprice()));
            }

        }

        if (Re >= 100 && f1 == 1) {
            alertDialog.setMessage("لقد حصلت على خصم 2 %");
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Cls_Sal_InvItems Inv_Obj;
                    Double Rowtotal;
                    for (int j = 0; j < contactList.size(); j++) {
                        Inv_Obj = new Cls_Sal_InvItems();
                        Inv_Obj = contactList.get(j);
                        if (Inv_Obj.getProID().toString().equals("")) {

                            if (Inv_Obj.getProID().equals("")) {
                                Inv_Obj.setProID("-99");
                                Rowtotal = Double.parseDouble(Inv_Obj.getQty()) * Double.parseDouble(Inv_Obj.getprice());
                                Inv_Obj.setPro_dis_Per("2" + "");
                                Inv_Obj.setPro_amt(String.valueOf(Rowtotal * (0.02)));

                            /*    Inv_Obj.setDisAmtFromHdr("0");
                                Inv_Obj.setDisPerFromHdr("0");
                                Inv_Obj.setDis_Amt("0");
                                Inv_Obj.setDiscount("0");*/

                                CalcTotal();
                                showList();

                            }
                        }

                    }

                }
            });


            alertDialog.show();

        } else if (Re >= 50 && f2 == 1) {
            alertDialog.setMessage("لقد حصلت على خصم 1 %");
            f = 1;
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Cls_Sal_InvItems Inv_Obj;
                    Double Rowtotal;
                    for (int j = 0; j < contactList.size(); j++) {
                        Inv_Obj = new Cls_Sal_InvItems();
                        Inv_Obj = contactList.get(j);


                        if (Inv_Obj.getProID().toString().equals("")) {
                            Inv_Obj.setProID("-99");
                            Rowtotal = Double.parseDouble(Inv_Obj.getQty()) * Double.parseDouble(Inv_Obj.getprice());
                            Inv_Obj.setPro_dis_Per("1" + "");
                            Inv_Obj.setPro_amt(String.valueOf(Rowtotal * (0.01)));


                            /*    Inv_Obj.setDisAmtFromHdr("0");
                                Inv_Obj.setDisPerFromHdr("0");
                                Inv_Obj.setDis_Amt("0");
                                Inv_Obj.setDiscount("0");*/


                            CalcTotal();
                            showList();
                        }

                    }

                }
            });
            alertDialog.show();

        }


    }

    private void ResetPromotion() {
        cls_offers_hdrs.clear();
        Cls_Sal_InvItems Inv_Obj;
        for (int j = (contactList.size() - 1); j >= 0; j--) {
            Inv_Obj = new Cls_Sal_InvItems();
            Inv_Obj = contactList.get(j);
            Inv_Obj.setProID("");
            Inv_Obj.setPro_dis_Per("0");
            Inv_Obj.setPro_amt("0");
            Inv_Obj.setPro_Total("0");
            Inv_Obj.setPro_bounce("0");

            if (Inv_Obj.getProType().equals("3")) {
                contactList.remove(Inv_Obj);
                // j = 0;
            }


        }
        CalcTotal();
        showList();
    }

    private void Gf_Calc_Promotion() {
        ResetPromotion();

        //cls_offers_hdrs.clear();
        Cls_Offers_Groups Offer_Group;
        Cls_Sal_InvItems Inv_Obj;
        for (int x = 0; x < offer_groups_List.size(); x++) {
            Offer_Group = new Cls_Offers_Groups();
            Offer_Group = offer_groups_List.get(x);

            if (Offer_Group.getSerNo().equals("1")) {
                for (int j = 0; j < contactList.size(); j++) {
                    Inv_Obj = new Cls_Sal_InvItems();
                    Inv_Obj = contactList.get(j);
                    // Inv_Obj.setProID("");
                    //Inv_Obj.setPro_bounce("0");
                    //Inv_Obj.setPro_dis_Per("0");
                    //Inv_Obj.setPro_amt("0");
                    if (Inv_Obj.getProID().toString().equals("") && (Inv_Obj.getno().equals(Offer_Group.getItem_no()))) {

                        Check_All_Promotion_Items(Offer_Group.getGrv_no());
                        Apply_Promotion();
                    }

                }


            }
        }


        Calc_LowPriPro();


    }

    public void btn_Calc_Promotion(View view) {
        // Gf_Calc_Promotion();
    }

    private int Check_All_Promotion_Items(String groupNo) {
        //   groupNo = "2";
        int result = 0;
        Cls_Offers_Groups Offer_Group;
        Cls_Sal_InvItems Inv_Obj;
        boolean flg = true;
        cls_offers_hdrs.clear();
        for (int x = 0; x < offer_groups_List.size(); x++) {
            Offer_Group = new Cls_Offers_Groups();
            Offer_Group = offer_groups_List.get(x);

            if (Offer_Group.getGrv_no().equals(groupNo)) {


                if (flg == true) {
                    flg = false;
                    for (int j = 0; j < contactList.size(); j++) {
                        Inv_Obj = new Cls_Sal_InvItems();
                        Inv_Obj = contactList.get(j);

                        //  if ( Inv_Obj.getProID().toString().equals("")&&(Inv_Obj.getno().equals(Offer_Group.getItem_no()))) {
                        if (Inv_Obj.getProID().toString().equals("") && (Offer_Group.getItem_no().equals(Inv_Obj.getno()))) {
                            if (Offer_Group.getGro_type().equals("1")) {
                                if ((Integer.valueOf(Inv_Obj.getQty()) >= Integer.valueOf(Offer_Group.getQty())) && (Inv_Obj.getUnite().equals(Offer_Group.getUnit_no()))) {
                                    flg = true;
                                    break;
                                }
                            } else {
                                flg = true;
                                break;
                            }
                        }//  end Inv_Obj.getno()==Offer_Group.getItem_no()

                    }
                }
    /* else {
    result = -1;
    return result;
    }*/
            } // end If (obj.getSerNo().equals("1"))


        }
        if (flg == true) {
            String q = "   SELECT  distinct oh.TotalValue,oh.ID,oh.Offer_No,oh.Offer_Name,oh.Offer_Date,oh.Offer_Type,oh.Offer_Status,oh.Offer_Begin_Date" +
                    " ,oh.Offer_Exp_Date,oh.Offer_Result_Type,oh.Offer_Dis , oh.Offer_Amt FROM Offers_Hdr oh left join    " +
                    " Offers_Dtl_Cond odc on odc.Trans_ID  = oh.ID where odc.Gro_Num = '" + groupNo + "'";


            Cursor c1 = sqlHandler.selectQuery(q);
            Cls_Offers_Hdr cls_offers_hdr = new Cls_Offers_Hdr();

            if (c1 != null && c1.getCount() != 0) {
                if (c1.getCount() > 0) {
                    c1.moveToFirst();
                    cls_offers_hdr.setOffer_Name(c1.getString(c1.getColumnIndex("Offer_Name")));
                    cls_offers_hdr.setID(c1.getString(c1.getColumnIndex("ID")));
                    cls_offers_hdr.setOffer_Type(c1.getString(c1.getColumnIndex("Offer_Type")));
                    cls_offers_hdr.setOffer_Dis(c1.getString(c1.getColumnIndex("Offer_Dis")));
                    cls_offers_hdr.setOffer_Amt(c1.getString(c1.getColumnIndex("Offer_Amt")));
                    cls_offers_hdr.setOffer_Result_Type(c1.getString(c1.getColumnIndex("Offer_Result_Type")));
                    cls_offers_hdr.setTotalValue(c1.getString(c1.getColumnIndex("TotalValue")));

                    cls_offers_hdrs.add(cls_offers_hdr);

                }
                c1.close();
            }
            //  Bundle bundle = new Bundle();
            // bundle.putParcelableArrayList("Scr", cls_offers_hdrs);
            // FragmentManager Manager =  getFragmentManager();
            /// PromotionEffict obj = new PromotionEffict();
            //  obj.setArguments(bundle);
            //  obj.show(Manager, null);


            result = Integer.valueOf(groupNo);
            //Toast.makeText(this,groupNo,Toast.LENGTH_SHORT).show();

        }
        return result;
    }

    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////
    private void Gf_Calc_PromotionNew() {
        //ResetPromotion();a

        if (chk_stopOffer.isChecked()) {
            return;
        }
        String q1;
        q1 = " DELETE   FROM Offers_Groups WHERE no NOT IN (SELECT MAX(no) FROM Offers_Groups " +
                " GROUP BY grv_no ,Item_No)";
        sqlHandler.executeQuery(q1);

        q1 = "DELETE   FROM Offers_Hdr WHERE no NOT IN (SELECT MAX(no) FROM Offers_Hdr GROUP BY Offer_No )";
        sqlHandler.executeQuery(q1);

        q1 = "DELETE   FROM Offers_Dtl_Gifts WHERE no NOT IN (SELECT MAX(no) FROM Offers_Dtl_Gifts GROUP BY Trans_ID,Item_No )";
        sqlHandler.executeQuery(q1);


        Fill_OffeNew();
        String ItemFactor = "1";
        String GroupFactor = "1";
        String GroupAmtFactor = "1";
        Integer GroupAmtFactorInt = 1;
        String q = "";
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        Cls_Offers_Hdr cls_offers_hdr;
        Double OrderQty = 0.0;
        String OrderAmt = "0";
        Double Offers_GroupsInOrder = 0.0;
        String Offers_GroupsItemsCount = "0";


        for (int x = 0; x < cls_offers_hdrsNew.size(); x++) {
            cls_offers_hdr = new Cls_Offers_Hdr();
            cls_offers_hdr = cls_offers_hdrsNew.get(x);
            fill_Offers_GroupNew(cls_offers_hdr.getGro_no());

            checkItem = CheckGeroupQty = CheckGroupAmt = false;
            OrderQty = 0.0;
            OrderAmt = "0";
            Cursor c1;


            if (offer_groups_List.size() == 0)
                continue;
            //checkItem
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (offer_groups_List.get(0).getGro_qty().equalsIgnoreCase("0")) {
                checkItem = true;
            } else if (offer_groups_List.get(0).getItem_no().equalsIgnoreCase("0")) {
                checkItem = true;
            } else {

                q = "select * from Offers_Groups Where grv_no ='" + cls_offers_hdr.getGro_no() + "'";


                Cursor c2 = sqlHandler.selectQuery(q);

                if (c2 != null && c2.getCount() != 0) {
                    if (c2.getCount() > 0) {
                        c2.moveToFirst();
                        do {
                            if ((c2.getString(c2.getColumnIndex("item_no"))).equals("-1") && (c2.getString(c2.getColumnIndex("invfno"))).equals("-1")) {
                                q = "   Select   ifnull(SUM(Sal_invoice_Det.qty*Operand),0)  as Co ,ifnull(SUM(bounce_qty*Operand),0)  as Co1  ,Sal_invoice_Det.OrderNo From    Sal_invoice_Det " +
                                        "  left  Join  invf    on  invf.Item_No = Sal_invoice_Det.itemNo" +
                                        "  left  Join  Offers_Groups    on  invf.Weight = Offers_Groups.Weight" +
                                        //      "   and( CAST( Sal_invoice_Det.qty as decimal) *  CAST( ifnull( Sal_invoice_Det.Operand,1) as decimal))   >=   CAST(Offers_Groups.qty as decimal )" +
                                        "    Where grv_no ='" + cls_offers_hdr.getGro_no() + "' and  ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'   and Sal_invoice_Det.OrderNo = '" + pono.getText().toString() + "' and cast(ifnull(Sal_invoice_Det.ProID, 0 ) as INTEGER )= 0" +
                                        "    and CAST(Offers_Groups.qty as INTEGER )>0 and Offers_Groups.Weight ='" + (c2.getString(c2.getColumnIndex("Weight"))) + "' " +
                                        " GROUP BY Sal_invoice_Det.OrderNo" +
                                        "  having Co   >=CAST(Offers_Groups.qty as decimal )  ";
                                c1 = sqlHandler.selectQuery(q);
                                if (c1 != null && c1.getCount() != 0) {
                                    c1.moveToFirst();
                                    String SumBounase = DB.GetValue(this, "LoukupsOffers", "ifnull(sum(AMT),0)", "OrderNo='" + pono.getText().toString() + "'");

                                    Offers_GroupsInOrder = SToD(c1.getString(c1.getColumnIndex("Co"))) - SToD(SumBounase);
                                    //Offers_GroupsInOrder;
                                    //  checkItem = true;
                                    c1.close();
                                }
                            } else if ((c2.getString(c2.getColumnIndex("item_no"))).equals("-1") && !((c2.getString(c2.getColumnIndex("invfno"))).equals("-1"))) {
                                q = "   Select    ifnull(SUM(Sal_invoice_Det.qty*Operand),0)  as Co ,ifnull(SUM(bounce_qty*Operand),0)  as Co1 From    Sal_invoice_Det " +
                                        "  left  Join  invf    on  invf.Item_No = Sal_invoice_Det.itemNo" +
                                        "  left  Join  Offers_Groups    on  invf.Type_No = Offers_Groups.invfno" +
                                        //"   and( CAST( Sal_invoice_Det.qty as decimal) *  CAST( ifnull( Sal_invoice_Det.Operand,1) as decimal))   >=   CAST(Offers_Groups.qty as decimal )" +
                                        "    Where grv_no ='" + cls_offers_hdr.getGro_no() + "' and  ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'   and Sal_invoice_Det.OrderNo = '" + pono.getText().toString() + "' and cast(ifnull(Sal_invoice_Det.ProID, 0 ) as INTEGER )= 0" +
                                        "    and CAST(Offers_Groups.qty as INTEGER )>0 and Offers_Groups.invfno ='" + (c2.getString(c2.getColumnIndex("invfno"))) + "' " +
                                        " GROUP BY Sal_invoice_Det.OrderNo" +
                                        "  having Co   >=CAST(Offers_Groups.qty as decimal )  ";
                                c1 = sqlHandler.selectQuery(q);
                                if (c1 != null && c1.getCount() != 0) {
                                    c1.moveToFirst();
                                    String SumBounase = DB.GetValue(this, "LoukupsOffers", "sum(AMT)", "OrderNo='" + pono.getText().toString() + "'");
                                    Offers_GroupsInOrder = SToD(c1.getString(c1.getColumnIndex("Co"))) - SToD(SumBounase);
                                    OfferAmt = Offers_GroupsInOrder;
                                    c1.close();
                                }

                            } else {
                                q = "   Select   ifnull(SUM(Sal_invoice_Det.qty*Operand),0)  as Co ,ifnull(SUM(bounce_qty*Operand),0)  as Co1  From    Sal_invoice_Det   inner  Join  Offers_Groups    on  " +
                                        "    Offers_Groups.item_no = Sal_invoice_Det.itemNo and " +
                                        "   ( CAST( Sal_invoice_Det.qty as decimal) *  CAST( ifnull( Sal_invoice_Det.Operand,1) as decimal))   >=   CAST(Offers_Groups.qty as decimal )" +
                                        "    Where grv_no ='" + cls_offers_hdr.getGro_no() + "' and  ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'   and Sal_invoice_Det.OrderNo = '" + pono.getText().toString() + "' and cast(ifnull(Sal_invoice_Det.ProID, 0 ) as INTEGER )= 0" +
                                        "    and CAST(Offers_Groups.qty as INTEGER )>0 and Offers_Groups.item_no ='" + (c2.getString(c2.getColumnIndex("item_no"))) + "' ";

                                c1 = sqlHandler.selectQuery(q);
                                if (c1 != null && c1.getCount() != 0) {
                                    c1.moveToFirst();
                                    String SumBounase = DB.GetValue(this, "LoukupsOffers", "sum(AMT)", "OrderNo='" + pono.getText().toString() + "'");
                                    Offers_GroupsInOrder = SToD(c1.getString(c1.getColumnIndex("Co"))) - SToD(SumBounase);//  checkItem = true;
                                    OfferAmt = Offers_GroupsInOrder;
                                    c1.close();
                                }


                            }

                        } while (c2.moveToNext());
                    }
                    q = "    Select   ifnull(Sum(Offers_Groups.qty*Offers_Groups.unit_rate),0) as Co  From Offers_Groups  Where grv_no ='" + cls_offers_hdr.getGro_no() + "'" +
                            "    and CAST(Offers_Groups.qty as INTEGER )>0 ";


                    c1 = sqlHandler.selectQuery(q);
                    if (c1 != null && c1.getCount() != 0) {
                        c1.moveToFirst();
                        Offers_GroupsItemsCount = (c1.getString(c1.getColumnIndex("Co")));
                        OfferAmt = SToD(Offers_GroupsItemsCount);
                        //  checkItem = true;
                        c1.close();
                    }

                    if ((Offers_GroupsInOrder) >= SToD(Offers_GroupsItemsCount)) {
                        checkItem = true;
                    } else {
                        OfferAmt = 0.0;
                        checkItem = false;
                        continue;


                    }
                    c2.close();
                }
/*
                q = "   Select  ifnull(Count(1),0) as Co  From    Sal_invoice_Det   inner  Join  Offers_Groups    on  " +
                        "    ( Offers_Groups.item_no = Sal_invoice_Det.itemNo or Offers_Groups.invfno = Sal_invoice_Det.itemNo) and " +
                        "   ( CAST( Sal_invoice_Det.qty as decimal) *  CAST( ifnull( Sal_invoice_Det.Operand,1) as decimal))   >=   CAST(Offers_Groups.qty as decimal )" +
                        "    Where grv_no ='" + cls_offers_hdr.getGro_no() + "' and  ifnull(Sal_invoice_Det.doctype,'1')='"+DocType.toString()+ "'   and Sal_invoice_Det.OrderNo = '" + pono.getText().toString() + "' and cast(ifnull(Sal_invoice_Det.ProID, 0 ) as INTEGER )= 0" +
                        "    and CAST(Offers_Groups.qty as INTEGER )>0 ";*/


            }

            // CheckOtherWise
            q = "Select   *  From  Sal_invoice_Det    inner  Join  Offers_Groups   on  " +
                    "  Offers_Groups.item_no = Sal_invoice_Det.itemNo and " +
                    "  Offers_Groups.item_no = Sal_invoice_Det.itemNo and     ( CAST( Sal_invoice_Det.qty as decimal) *  CAST( ifnull( Sal_invoice_Det.decimal,1) as INTEGER))   <   CAST(Offers_Groups.qty as decimal )" +
                    "  Where grv_no ='" + cls_offers_hdr.getGro_no() + "'   and  ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  and Sal_invoice_Det.OrderNo = '" + pono.getText().toString() + "' and cast(ifnull(Sal_invoice_Det.ProID, 0 ) as INTEGER )= 0" +
                    "  and CAST(Offers_Groups.qty as INTEGER )>0 ";


            c1 = sqlHandler.selectQuery(q);
            if (c1 != null && c1.getCount() != 0) {
                checkItem = false;
                c1.close();
            }


            if (checkItem == false)
                continue;


            ////////////////////////////////////////////////////////////////////////////////////////
            //CheckGeroupQty
            if (offer_groups_List.get(0).getGro_qty().equalsIgnoreCase("0")) {
                CheckGeroupQty = true;
            } else if (offer_groups_List.get(0).getItem_no().equalsIgnoreCase("0")) {
                CheckGeroupQty = true;
            } else {

                q = "select * from Offers_Groups Where grv_no ='" + cls_offers_hdr.getGro_no() + "'";


                Cursor c2 = sqlHandler.selectQuery(q);

                if (c2 != null && c2.getCount() != 0) {
                    if (c2.getCount() > 0) {
                        c2.moveToFirst();
                        do {
                            if ((c2.getString(c2.getColumnIndex("item_no"))).equals("-1") && (c2.getString(c2.getColumnIndex("invfno"))).equals("-1")) {
                                q = "    Select   ifnull( sum(CAST( ( CAST( Sal_invoice_Det.qty as decimal) *  CAST( ifnull( Sal_invoice_Det.Operand,1) as decimal)) as decimal)),0) as qty " +
                                        "  From     Sal_invoice_Det " +
                                        "  left  Join  invf    on  invf.Item_No = Sal_invoice_Det.itemNo" +
                                        "  left  Join  Offers_Groups    on  invf.Weight = Offers_Groups.Weight" +
                                        "  Where   grv_no ='" + cls_offers_hdr.getGro_no() + "' and " +
                                        " ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  " +
                                        "and Sal_invoice_Det.OrderNo = '" + pono.getText().toString() + "'" +
                                        "and Offers_Groups.Weight = '" + (c2.getString(c2.getColumnIndex("Weight"))) + "'" +
                                        " and cast(ifnull(Sal_invoice_Det.ProID, 0 ) as INTEGER )= 0";
                                c1 = sqlHandler.selectQuery(q);
                                if (c1 != null && c1.getCount() != 0) {
                                    c1.moveToFirst();
                                    OrderQty += SToD(c1.getString(c1.getColumnIndex("qty")));

                                    c1.close();


                                }
                            } else if ((c2.getString(c2.getColumnIndex("item_no"))).equals("-1") && !((c2.getString(c2.getColumnIndex("invfno"))).equals("-1"))) {

                                q = "    Select   ifnull( sum(CAST( ( CAST( Sal_invoice_Det.qty as decimal) *  CAST( ifnull( Sal_invoice_Det.Operand,1) as decimal)) as decimal)),0) as qty " +
                                        "  From     Sal_invoice_Det " +
                                        "  left  Join  invf    on  invf.Item_No = Sal_invoice_Det.itemNo" +
                                        "  left  Join  Offers_Groups    on  invf.Type_No = Offers_Groups.invfno" +
                                        "  Where   grv_no ='" + cls_offers_hdr.getGro_no() + "' and " +
                                        " ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  " +
                                        "and Sal_invoice_Det.OrderNo = '" + pono.getText().toString() + "'" +
                                        "and Offers_Groups.invfno = '" + (c2.getString(c2.getColumnIndex("invfno"))) + "'" +
                                        " and cast(ifnull(Sal_invoice_Det.ProID, 0 ) as INTEGER )= 0";
                                c1 = sqlHandler.selectQuery(q);
                                if (c1 != null && c1.getCount() != 0) {
                                    c1.moveToFirst();
                                    OrderQty += SToD(c1.getString(c1.getColumnIndex("qty")));

                                    c1.close();


                                }
                            } else {

                                q = "    Select   ifnull( sum(CAST( ( CAST( Sal_invoice_Det.qty as decimal) *  CAST( ifnull( Sal_invoice_Det.Operand,1) as decimal)) as decimal)),0) as qty " +
                                        "  From     Offers_Groups  inner  Join Sal_invoice_Det    on   Offers_Groups.item_no = Sal_invoice_Det.itemNo  " +
                                        "  Where   grv_no ='" + cls_offers_hdr.getGro_no() + "' and " +
                                        " ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  " +
                                        "and Sal_invoice_Det.OrderNo = '" + pono.getText().toString() + "'" +
                                        "and Offers_Groups.item_no = '" + (c2.getString(c2.getColumnIndex("item_no"))) + "'" +
                                        " and cast(ifnull(Sal_invoice_Det.ProID, 0 ) as INTEGER )= 0";


                                c1 = sqlHandler.selectQuery(q);
                                if (c1 != null && c1.getCount() != 0) {
                                    c1.moveToFirst();
                                    OrderQty += SToD(c1.getString(c1.getColumnIndex("qty")));

                                    c1.close();


                                }


                            }

                        } while (c2.moveToNext());
                    }

                    if ((OrderQty) >= SToD(offer_groups_List.get(0).getGro_qty())) {
                        GroupFactor = ((OrderQty) / SToD(offer_groups_List.get(0).getGro_qty())) + "";
                        CheckGeroupQty = true;
                    }
                    c2.close();
                }


            }


            if (CheckGeroupQty == false)
                continue;


            //CheckGroupAmt
            if (offer_groups_List.get(0).getGro_amt().equalsIgnoreCase("0.000")) {
                CheckGroupAmt = true;
            } else {
                q = "    Select   ifnull( sum(CAST( Sal_invoice_Det.total as real)),0)  as Amt From Sal_invoice_Det    " +
                        "  Where  ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  and Sal_invoice_Det.OrderNo = '" + pono.getText().toString() + "' and cast(ifnull(Sal_invoice_Det.ProID, 0 ) as INTEGER )= 0";


                c1 = sqlHandler.selectQuery(q);
                if (c1 != null && c1.getCount() != 0) {
                    c1.moveToFirst();
                    OrderAmt = (c1.getString(c1.getColumnIndex("Amt")));

                    c1.close();

                    if (SToD(OrderAmt) >= SToD(offer_groups_List.get(0).getGro_amt())) {
                        CheckGroupAmt = true;

                    }
                }
            }

            if (CheckGroupAmt == false)
                continue;


            if (checkItem == true && CheckGeroupQty == true && CheckGroupAmt == true) {


                if (cls_offers_hdr.getAllaw_Repet().equalsIgnoreCase("0")) {
                    ItemFactor = "1";
                } else {
                    q = "      Select      ifnull( min(     (   CAST( Sal_invoice_Det.qty as decimal)  *   CAST( ifnull( Sal_invoice_Det.Operand,1) as decimal)  ) /  CAST(Offers_Groups.qty as decimal )  ) ,0) as f   From    Offers_Groups  inner  Join Sal_invoice_Det    on  " +
                            "  Offers_Groups.item_no = Sal_invoice_Det.itemno and " +
                            "  Offers_Groups.item_no = Sal_invoice_Det.itemno and  (   CAST( Sal_invoice_Det.qty as decimal)  *    CAST( ifnull( Sal_invoice_Det.Operand,1) as decimal)     )>=   CAST(Offers_Groups.qty as decimal )" +
                            "  Where grv_no ='" + cls_offers_hdr.getGro_no() + "' and   ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  and  Sal_invoice_Det.OrderNo = '" + pono.getText().toString() + "' and cast(ifnull(Sal_invoice_Det.ProID, 0 ) as INTEGER )= 0" +
                            "  and CAST(Offers_Groups.qty as decimal )>0 ";

                    c1 = sqlHandler.selectQuery(q);
                    if (c1 != null && c1.getCount() != 0) {
                        c1.moveToFirst();
                        ItemFactor = (c1.getString(c1.getColumnIndex("f")));
                        if (ItemFactor.equalsIgnoreCase("0")) {
                            ItemFactor = GroupFactor;
                        }
                        c1.close();
                    }
                }


                if (SToD(GroupFactor) < SToD(ItemFactor)) {
                    ItemFactor = GroupFactor;
                }

                if (cls_offers_hdr.getOffer_Result_Type().equalsIgnoreCase("1")) {
                    AppledOfferType1(cls_offers_hdr.getGro_no().toString(), ItemFactor, cls_offers_hdr.getOffer_Dis().toString());
                    x = 0;
                    continue;
                }
                if (cls_offers_hdr.getOffer_Result_Type().equalsIgnoreCase("2")) {
                    AppledOfferType2(cls_offers_hdr.getGro_no().toString(), ItemFactor, cls_offers_hdr.getOffer_Amt().toString());
                    x = 0;
                    continue;
                }

                if (cls_offers_hdr.getOffer_Result_Type().equalsIgnoreCase("3")) {
                    if (cls_offers_hdr.getTotal_item().equalsIgnoreCase(cls_offers_hdr.getGift_Items_Count())) {
                        AppledOfferType3(cls_offers_hdr.getOffer_No().toString(), ItemFactor, "1");
                        x = 0;
                    } else {
                        AppledOfferType32(cls_offers_hdr.getOffer_No().toString(), ItemFactor, cls_offers_hdr.getTotal_item());
                        x = 0;
                        break;
                    }

                }

            }


        }// End loop
    }

    private void AppledOfferType1(String Grv_no, String ItemFactor, String dis_Per) {


        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);

        String s;
        String item_no = DB.GetValue(this, "Offers_Groups", "item_no", "grv_no='" + Grv_no + "' LIMIT 1");
        String Deptf = DB.GetValue(this, "Offers_Groups", "invfno", "grv_no='" + Grv_no + "' LIMIT 1");

        if (item_no.equals("-1") && Deptf.equals("-1")) {
            s = " Update Sal_invoice_Det Set ProID='" + Grv_no + "' ,Pro_bounce ='0'  , pro_Total ='" + dis_Per + "' ,  " +
                    "    Pro_amt =   (cast(  OrgPrice as real )*   cast(  qty as decimal ) * " + (SToD(dis_Per) / 100) + ") , Pro_dis_Per='" + dis_Per + "'" +
                    "    Where  itemno in ( select  Item_No  from invf where Weight in (select  Weight  from Offers_Groups where grv_no  ='" + Grv_no + "'))  and   ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  and OrderNo='" + pono.getText() + "'  and ProID='0'";

        } else if ((item_no.equals("-1") && !(Deptf.equals("-1")))) {
            s = " Update Sal_invoice_Det Set ProID='" + Grv_no + "' ,Pro_bounce ='0'  , pro_Total ='" + dis_Per + "' ,  " +
                    "    Pro_amt =   (cast(  OrgPrice as real )*   cast(  qty as decimal ) * " + (SToD(dis_Per) / 100) + ") , Pro_dis_Per='" + dis_Per + "'" +
                    "    Where  itemno in ( select  Item_No  from invf where Type_No in (select  invfno  from Offers_Groups where grv_no  ='" + Grv_no + "'))  and   ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  and OrderNo='" + pono.getText() + "'  and ProID='0'";

        } else {
            s = " Update Sal_invoice_Det Set ProID='" + Grv_no + "' ,Pro_bounce ='0'  , pro_Total ='" + dis_Per + "' ,  " +
                    "    Pro_amt =   (cast(  OrgPrice as real )*   cast(  qty as decimal ) * " + (SToD(dis_Per) / 100) + ") , Pro_dis_Per='" + dis_Per + "'" +
                    "    Where  itemno in ( select  item_no  from Offers_Groups where grv_no ='" + Grv_no + "')  and   ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  and OrderNo='" + pono.getText() + "'  and ProID='0'";

        }

        //  String grv_no = DB.GetValue(this, "Offers_Hdr", "gro_no", "Offer_No='" + Grv_no + "'");
        if (offer_groups_List.get(0).getItem_no().equalsIgnoreCase("0")) {
            s = " Update Sal_invoice_Det Set ProID='" + Grv_no + "' ,Pro_bounce ='0'  , pro_Total ='" + dis_Per + "' ,  " +
                    "    Pro_amt =   (cast(  OrgPrice as real )*   cast(  qty as decimal ) * " + (SToD(dis_Per) / 100) + ") , Pro_dis_Per='" + dis_Per + "'" +
                    "    Where     ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  and   OrderNo='" + pono.getText() + "'  and ProID='0'";

        }


        sqlHandler.executeQuery(s);

        TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView Order_no = (TextView) findViewById(R.id.et_OrdeNo);
        TextView accno = (TextView) findViewById(R.id.tv_acc);
        Set_Order(Order_no.getText().toString());

    }

    private void AppledOfferType2(String Grv_no, String ItemFactor, String dis_Amt) { //AMt

        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);

        String s = "";

        String GroupItem = "0";

        String q1 = "select  item_no  from Offers_Groups where grv_no ='" + Grv_no + "' and item_no !='0'";
        Cursor c1;
        c1 = sqlHandler.selectQuery(q1);
        if (c1 != null && c1.getCount() != 0) {
            GroupItem = "1";
            c1.close();
        } else {
            GroupItem = "0";
        }

        if (GroupItem.equalsIgnoreCase("0")) {

            s = " Update Sal_invoice_Det Set ProID='" + Grv_no + "' ,Pro_bounce ='0'  , pro_Total ='" + dis_Amt + "' ,  " +
                    " Pro_amt =" + (SToD(dis_Amt) + " *" + ItemFactor) + " , Pro_dis_Per=  ROUND (( " + ItemFactor + "*" + SToD(dis_Amt) + ")   /(cast(  OrgPrice as real )*   cast(  qty as integer ) ) ,3)" +
                    " Where   ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  and OrderNo='" + pono.getText() + "'  and ProID='0'";

        } else {


            s = " Update Sal_invoice_Det Set ProID='" + Grv_no + "' ,Pro_bounce ='0'  , pro_Total ='" + dis_Amt + "' ,  " +
                    " Pro_amt =" + (SToD(dis_Amt) + " *" + ItemFactor) + " , Pro_dis_Per=  ROUND (( " + ItemFactor + "*" + SToD(dis_Amt) + ")   /(cast(  OrgPrice as real )*   cast(  qty as integer ) ) ,3)" +
                    " Where  itemno in ( select  item_no  from Offers_Groups where grv_no ='" + Grv_no + "')  and    ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  and OrderNo='" + pono.getText() + "'  and ProID='0'";
        }
        sqlHandler.executeQuery(s);


        TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView Order_no = (TextView) findViewById(R.id.et_OrdeNo);
        TextView accno = (TextView) findViewById(R.id.tv_acc);
        Set_Order(Order_no.getText().toString());
    }

    private void AppledOfferType3(String GroupNo, String ItemFactor, String TotalItem) {

        if ((Integer.parseInt(ItemFactor)) > 0) {
            Long i;
            TextView Order_no = (TextView) findViewById(R.id.et_OrdeNo);
            ContactListItems Inv_Obj;
            //cls_offers_dtl_giftses.clear();


            String query = " Select distinct UnitItems.price as price,  UnitItems.Operand as Operand,   u.UnitName    , i.Item_Name ,  odg.Item_No , odg.Unit_No , odg.QTY , odg.Unit_Rate from Offers_Dtl_Gifts  odg" +
                    "    Left  join invf i on i.Item_No =  odg.Item_No   " +
                    "    left  join Unites  u on u.Unitno = odg.Unit_No " +
                    "     left join UnitItems on UnitItems.unitno  =u.Unitno  and UnitItems.item_no= odg.Item_No   " +
                    "    where odg.Trans_ID = '" + GroupNo + "'"; //"  where odc.Gro_Num  = '"+GroupNo+"'";

            Cursor c1 = sqlHandler.selectQuery(query);
            if (c1 != null && c1.getCount() != 0) {
                if (c1.moveToFirst()) {
                    do {
                        ContentValues cv = new ContentValues();
                        cv.put("OrderNo", Order_no.getText().toString());
                        cv.put("itemno", c1.getString(c1.getColumnIndex("Item_No")));
                        if (ComInfo.ComNo == Companies.beutyLine.getValue()) {
                            cv.put("price", c1.getString(c1.getColumnIndex("price")));
                            cv.put("dis_per", "100");
                            cv.put("dis_Amt", c1.getString(c1.getColumnIndex("price")));
                        } else {
                            cv.put("price", c1.getString(c1.getColumnIndex("price")));
                            cv.put("dis_per", "0");
                            cv.put("dis_Amt", "0");
                        }
                        cv.put("qty", "0");
                        cv.put("tax", "0");
                        cv.put("unitNo", c1.getString(c1.getColumnIndex("Unit_No")));
                        cv.put("Pro_bounce", 0);
                        cv.put("tax_Amt", "0");
                        cv.put("total", "0");
                        cv.put("bounce_qty", String.valueOf(Integer.parseInt(ItemFactor) * Double.parseDouble(c1.getString(c1.getColumnIndex("QTY")))));
                        cv.put("ProID", GroupNo);
                        cv.put("Pro_dis_Per", "0");
                        cv.put("Pro_amt", "0");
                        cv.put("Pro_Type", "3");
                        cv.put("pro_Total", "0");
                        cv.put("OrgPrice", "0");
                        cv.put("Operand", c1.getString(c1.getColumnIndex("Unit_Rate")));
                        cv.put("doctype", DocType.toString());
                        i = sqlHandler.Insert("Sal_invoice_Det", null, cv);
                    } while (c1.moveToNext());

                }
                c1.close();
            }
            TextView pono = (TextView) findViewById(R.id.et_OrdeNo);


            String grv_no = DB.GetValue(this, "Offers_Hdr", "gro_no", "Offer_No='" + GroupNo + "'");

            String s = " Update Sal_invoice_Det Set ProID='" + grv_no + "' ,Pro_bounce ='0'  , pro_Total ='0',  " +
                    "    Pro_amt =   '0' , Pro_dis_Per='0'  Where  Pro_Type !='3' and  itemno in" +
                    " ( select  item_no  from Offers_Groups where grv_no ='" + grv_no + "')  and   ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  and OrderNo='" + pono.getText() + "'  ";
            sqlHandler.executeQuery(s);


            TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);

            TextView accno = (TextView) findViewById(R.id.tv_acc);
            Set_Order(Order_no.getText().toString());
        }
    }

    public void AppledOfferType32(String GroupNo, final String ItemFactor, final String TotalItem) {
        DoPrint = 0;

        String q = "delete from TempGifts  ";
        sqlHandler.executeQuery(q);
        builder = new AlertDialog.Builder(Sale_InvoiceActivity.this);
        cvv = new ContentValues();

        LayoutInflater li = LayoutInflater.from(this);
        View view = li.inflate(R.layout.listview_dialog, null);
        listView = (ListView) view.findViewById(R.id.lvAlertList);
        final int q1 = Integer.parseInt(TotalItem);
        final String GrNo = GroupNo;

        Button btn_InsertItem = (Button) view.findViewById(R.id.btn_InsertItem);

        btn_InsertItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String q = " select * from  TempGifts ";
                sqlHandler.executeQuery(q);
                Cursor c1 = sqlHandler.selectQuery(q);
                if (c1 != null && c1.getCount() != 0) {
                    if (c1.getCount() == q1) {
                        alertDialog1.dismiss();
                        AppledOfferType33(GrNo, ItemFactor, TotalItem);
                    }
                    c1.close();
                }
            }
        });
        itemList = new ArrayList<AlertChoiceItem>();


        String query = " Select distinct u.UnitName , odc.Allaw_Repet,   i.Item_Name ,  odg.Item_No , odg.Unit_No , odg.QTY , odg.Unit_Rate from Offers_Dtl_Gifts  odg" +
                " Left join invf i on i.Item_No =  odg.Item_No   " +
                " left join Unites  u on u.Unitno = odg.Unit_No" +
                " left join  Offers_Dtl_Cond odc on odc.Trans_ID =  odg.Trans_ID" +
                " where odg.Trans_ID = '" + GroupNo + "'"; //"  where odc.Gro_Num  = '"+GroupNo+"'";

        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {

                    itemList.add(new AlertChoiceItem(false, "", 5, c1.getString(c1.getColumnIndex("Item_No")), c1.getString(c1.getColumnIndex("Item_Name")), "1"));

                } while (c1.moveToNext());

            }
            c1.close();
        }
        boolean bl[] = new boolean[itemList.size()];

        symtoms = new CharSequence[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            symtoms[i] = itemList.get(i).getItemNo() + " | " + itemList.get(i).getItemNm();
        }


        builder.setTitle(" عدد المواد المطلوب تحديدها هو : " + TotalItem);
        builder.setCancelable(false);
        builder.setMultiChoiceItems(symtoms, bl, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1, boolean arg2) {
                // TODO Auto-generated method stub
                itemList.get(arg1).setFlg("1");
                StringTokenizer tokens = new StringTokenizer(symtoms[arg1].toString(), "|");
                String first = tokens.nextToken();// this will contain "Fruit"
                first = first.trim();
                if (arg2) {
                    cvv.put("ItemNo", first);
                    cvv.put("OfferNo", "GroupNo");
                    cvv.put("Qty", "1");
                    cvv.put("UnitNo", "");

                    SqlRes = sqlHandler.Insert("TempGifts", null, cvv);
                } else // if (selList.contains(arg1))
                {
                    String q = "delete from TempGifts where ItemNo='" + first + "'";
                    sqlHandler.executeQuery(q);
                    // if the item is already selected then remove it
                    // selList.remove(Integer.valueOf(arg1));
                }
            }
        });

    /*  builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

    @Override
    public void onClick(DialogInterface dialog, int which) {
    // TODO Auto-generated method stub

    }
    });*/
        builder.setView(view);
        alertDialog1 = builder.show();
    }

    private void AppledOfferType33(String GroupNo, String ItemFactor, String TotalItem) {
        //   Toast.makeText(this,ItemFactor+"" , Toast.LENGTH_SHORT).show();
        Long i;
        TextView Order_no = (TextView) findViewById(R.id.et_OrdeNo);
        ContactListItems Inv_Obj;
        String query = " Select distinct  UnitItems.price as price  ,  u.UnitName    , i.Item_Name ,  odg.Item_No , odg.Unit_No , odg.QTY , odg.Unit_Rate from Offers_Dtl_Gifts  odg" +
                "  inner join  TempGifts on TempGifts.ItemNo=  odg.Item_No" +
                "    Left  join invf i on i.Item_No =  odg.Item_No   " +
                "    left  join Unites  u on u.Unitno = odg.Unit_No" +
                "    left join UnitItems on UnitItems.unitno  =u.Unitno  and UnitItems.item_no=  odg.Item_No " +
                "    where odg.Trans_ID = '" + GroupNo + "'"; //"  where odc.Gro_Num  = '"+GroupNo+"'";

        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    ContentValues cv = new ContentValues();
                    cv.put("OrderNo", Order_no.getText().toString());
                    cv.put("itemno", c1.getString(c1.getColumnIndex("Item_No")));
                    if (ComInfo.ComNo == Companies.beutyLine.getValue()) {
                        cv.put("price", c1.getString(c1.getColumnIndex("price")));
                        cv.put("dis_per", "100");
                        cv.put("dis_Amt", c1.getString(c1.getColumnIndex("price")));
                    } else {
                        cv.put("price", "0");
                        cv.put("dis_per", "0");
                        cv.put("dis_Amt", "0");
                    }
                    cv.put("qty", "0");
                    cv.put("tax", "0");
                    cv.put("unitNo", c1.getString(c1.getColumnIndex("Unit_No")));
                    //    cv.put("Pro_bounce", String.valueOf(SToD(ItemFactor) * SToD(c1.getString(c1.getColumnIndex("QTY")))));
                    cv.put("Pro_bounce", "0");
                    cv.put("tax_Amt", "0");
                    cv.put("total", "0");
                    // cv.put("bounce_qty", String.valueOf(SToD(ItemFactor) * SToD(c1.getString(c1.getColumnIndex("QTY")))));
                    String OP = DB.GetValue(this, "UnitItems", "Operand", "item_no='" + c1.getString(c1.getColumnIndex("Item_No")) + "' and unitno='" + c1.getString(c1.getColumnIndex("Unit_No")) + "'");
                    cv.put("bounce_qty", SToD(c1.getString(c1.getColumnIndex("QTY"))));
                    cv.put("ProID", GroupNo);
                    cv.put("Pro_dis_Per", "0");
                    cv.put("Pro_amt", "0");
                    cv.put("pro_Total", "0");
                    cv.put("OrgPrice", "0");
                    cv.put("Pro_Type", "3");
                    //    cv.put("Operand", c1.getString(c1.getColumnIndex("Unit_Rate")));
                    cv.put("Operand", OP);
                    cv.put("doctype", DocType.toString());
                    i = sqlHandler.Insert("Sal_invoice_Det", null, cv);
                } while (c1.moveToNext());

            }
            c1.close();
        }
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        query = "INSERT INTO LoukupsOffers(OrderNo,AMT) values " +
                "('" + pono.getText().toString() + "','" + OfferAmt + "')";

        sqlHandler.executeQuery(query);
        String grv_no = DB.GetValue(this, "Offers_Hdr", "gro_no", "Offer_No='" + GroupNo + "'");
        String s = " Update Sal_invoice_Det Set ProID='" + GroupNo + "' ,Pro_bounce ='0'  , pro_Total ='0',  " +
                "    Pro_amt =   '0' , Pro_dis_Per='0'  Where  Pro_Type !='3'  AND  itemno in ( select  item_no  from Offers_Groups where grv_no ='" + GroupNo + "')  and  ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  and  OrderNo='" + Order_no.getText() + "'  ";
        sqlHandler.executeQuery(s);
        Set_Order(Order_no.getText().toString());
        // DoPrint = 1;

        Gf_Calc_PromotionNew();
        if (DoPrint == 1) {
            View view = null;
            btn_print(view);
        }

    }

    private void Fill_OffeNew() {
        cls_offers_hdrsNew.clear();
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        String q = "    SELECT Offers_Hdr.*   FROM Offers_Hdr  inner join Offers_Groups on Offers_Groups.grv_no=  Offers_Hdr.gro_no" +
                "    WHERE  ( Offers_Groups.gro_type='0' or  Offers_Groups.gro_type='2' )  and     Offer_No not in ( Select ProID  From Sal_invoice_Det where   ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  and  OrderNo='" + pono.getText() + "' )   " +
                "    Order by CAST( Offer_priority as INTEGER)   asc   ";


        Cursor c1 = sqlHandler.selectQuery(q);
        Cls_Offers_Hdr cls_offers_hdr;

        if (c1 != null && c1.getCount() != 0) {
            if (c1.getCount() > 0) {
                c1.moveToFirst();
                do {
                    cls_offers_hdr = new Cls_Offers_Hdr();
                    cls_offers_hdr.setOffer_Name(c1.getString(c1.getColumnIndex("Offer_Name")));
                    cls_offers_hdr.setID(c1.getString(c1.getColumnIndex("ID")));
                    cls_offers_hdr.setOffer_No(c1.getString(c1.getColumnIndex("Offer_No")));
                    cls_offers_hdr.setOffer_Type(c1.getString(c1.getColumnIndex("Offer_Type")));
                    cls_offers_hdr.setOffer_Dis(c1.getString(c1.getColumnIndex("Offer_Dis")));
                    cls_offers_hdr.setOffer_Amt(c1.getString(c1.getColumnIndex("Offer_Amt")));
                    cls_offers_hdr.setOffer_Result_Type(c1.getString(c1.getColumnIndex("Offer_Result_Type")));
                    cls_offers_hdr.setTotalValue(c1.getString(c1.getColumnIndex("TotalValue")));
                    cls_offers_hdr.setOffer_priority(c1.getString(c1.getColumnIndex("Offer_priority")));
                    cls_offers_hdr.setGro_no(c1.getString(c1.getColumnIndex("gro_no")));
                    cls_offers_hdr.setAllaw_Repet(c1.getString(c1.getColumnIndex("Allaw_Repet")));
                    cls_offers_hdr.setTotal_item(c1.getString(c1.getColumnIndex("total_item")));
                    cls_offers_hdr.setGift_Items_Count(c1.getString(c1.getColumnIndex("Gift_Items_Count")));
                    cls_offers_hdrsNew.add(cls_offers_hdr);
                } while (c1.moveToNext());
            }
            c1.close();
        }
        //  Bundle bundle = new Bundle();
        // bundle.putParcelableArrayList("Scr", cls_offers_hdrs);
        // FragmentManager Manager =  getFragmentManager();
        /// PromotionEffict obj = new PromotionEffict()
        //  obj.setArguments(bundle);
        //  obj.show(Manager, null);


    }

    private void DeleteAllPromotions() {

        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        String s = " Update Sal_invoice_Det Set ProID='0' ,Pro_bounce ='0'  , pro_Total ='0'   " +
                " ,Pro_amt =  '0' , Pro_dis_Per='0'  Where  ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  and OrderNo='" + pono.getText() + "'";
        sqlHandler.executeQuery(s);
        s = " Delete  from  Sal_invoice_Det  Where  ifnull(Pro_Type ,'0')='3'    and   ifnull(Sal_invoice_Det.doctype,'1')='" + DocType.toString() + "'  and OrderNo='" + pono.getText() + "'";
        sqlHandler.executeQuery(s);
        s = " Delete from LoukupsOffers where OrderNo='" + pono.getText() + "'";
        sqlHandler.executeQuery(s);

        OfferAmt = 0.0;
        OfferAmt2 = 0.0;

        TextView Order_no = (TextView) findViewById(R.id.et_OrdeNo);
        Set_Order(Order_no.getText().toString());
    }

    private void fill_Offers_GroupNew(String grv_no) {
        offer_groups_List.clear();
        String query = " select * from Offers_Groups where (gro_type='0' or gro_type='2'  )  and grv_no ='" + grv_no + "'  ";
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Offers_Groups cls_offers_groups = new Cls_Offers_Groups();
                    cls_offers_groups.setID(c1.getString(c1
                            .getColumnIndex("ID")));
                    cls_offers_groups.setGrv_no(c1.getString(c1
                            .getColumnIndex("grv_no")));
                    cls_offers_groups.setGro_ename(c1.getString(c1
                            .getColumnIndex("gro_name")));
                    cls_offers_groups.setGro_type(c1.getString(c1
                            .getColumnIndex("gro_type")));
                    cls_offers_groups.setItem_no(c1.getString(c1
                            .getColumnIndex("item_no")));
                    cls_offers_groups.setUnit_no(c1.getString(c1
                            .getColumnIndex("unit_no")));
                    cls_offers_groups.setQty(c1.getString(c1
                            .getColumnIndex("qty")));
                    cls_offers_groups.setSerNo(c1.getString(c1
                            .getColumnIndex("SerNo")));

                    cls_offers_groups.setGro_qty(c1.getString(c1
                            .getColumnIndex("gro_qty")));
                    cls_offers_groups.setGro_amt(c1.getString(c1
                            .getColumnIndex("gro_amt")));
                    offer_groups_List.add(cls_offers_groups);

                } while (c1.moveToNext());
            }
            c1.close();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public void btn_share(View view) {
        DoShare();
    }

    private void DoShare() {
        InsertLogTrans obj = new InsertLogTrans(Sale_InvoiceActivity.this, SCR_NO, SCR_ACTIONS.Share.getValue(), et_OrdeNo.getText().toString(), tv_acc.getText().toString(), "", "0");
        final SqlHandler sql_Handler = new SqlHandler(this);
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        final String Doc_No = pono.getText().toString();

        loadingdialog = ProgressDialog.show(Sale_InvoiceActivity.this, "الرجاء الانتظار ...", "العمل جاري على اعتماد  " + tv_ScrTitle.getText().toString(), true);
        loadingdialog.setCancelable(false);
        loadingdialog.setCanceledOnTouchOutside(false);
        loadingdialog.show();
        final Handler _handler = new Handler();
        RecvV1 = getJsonRecvVouchar(Doc_No);

        // Toast.makeText(getApplicationContext(),str, Toast.LENGTH_LONG).show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                PostSalesInvoice obj = new PostSalesInvoice(Sale_InvoiceActivity.this);
                PostResult = obj.Post_Sal_Inv(Doc_No, DocType + "", RecvV1);
                try {

                    if (PostResult < 0) {
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        Sale_InvoiceActivity.this).create();
                                alertDialog.setTitle(tv_ScrTitle.getText().toString());
                                alertDialog.setMessage(" فشل في عملية الاعتماد" + PostResult + "");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                loadingdialog.dismiss();
                                alertDialog.show();
                            }
                        });


                    } else if (We_Result.ID > 0) {
                        ContentValues cv = new ContentValues();
                        final TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
                        cv.put("Post", We_Result.ID);
                        long i;
                        i = sql_Handler.Update("Sal_invoice_Hdr", cv, " ifnull(doctype,'1')='" + DocType.toString() + "'  and   OrderNo='" + DocNo.getText().toString() + "'");


                        _handler.post(new Runnable() {
                            public void run() {
                                updateManStore();
                                updateCustomer();
                                String query = " Update  Sal_invoice_Hdr  set Post='1'  where OrderNo='" + DocNo.getText().toString() + "'";
                                sqlHandler.executeQuery(query);

                                query = " Update  RecVoucherSeles  set Post='1'  where SalesOrderNo='" + DocNo.getText().toString() + "'";
                                sqlHandler.executeQuery(query);

                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        Sale_InvoiceActivity.this).create();
                                alertDialog.setTitle(tv_ScrTitle.getText().toString());
                                alertDialog.setMessage("تمت عملية الترحيل  بنجاح");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                loadingdialog.dismiss();
                                alertDialog.show();


                            }
                        });
                    } else {

                        loadingdialog.dismiss();
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        Sale_InvoiceActivity.this).create();
                                alertDialog.setTitle("فشل في عملية الاعتماد  " + "   " + We_Result.ID + "");
                                alertDialog.setMessage(We_Result.Msg.toString());
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                alertDialog.show();

                                alertDialog.setIcon(R.drawable.delete);
                                alertDialog.setMessage("عملية الاعتماد لم تتم بنجاح" + "    ");
                            }
                        });
                    }

                } catch (final Exception e) {
                    loadingdialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    Sale_InvoiceActivity.this).create();
                            alertDialog.setTitle("فشل في عمليه الاتصال");
                            alertDialog.setMessage(e.getMessage().toString());
                            alertDialog.setIcon(R.drawable.tick);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
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

    @Override
    public void onBackPressed() {


        View view = null;
        ExistAfterSacve = 1;
        if (contactList.size() > 0 && IsChange == true) {
            btn_save_po(view);
        } else {
            Intent k = new Intent(this, JalMasterActivity.class);
            startActivity(k);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sal_invoice_menu, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.BalanceQty:
                Bundle bundle = new Bundle();
                bundle.putString("Scr", "SalInvoice");
                FragmentManager Manager = getFragmentManager();
                SearchManBalanceQty obj = new SearchManBalanceQty();
                obj.setArguments(bundle);
                obj.show(Manager, null);
                break;
            case R.id.Exist:
                View view = null;
                btn_back(view);
                break;

        }

        return true;
    }

    public void InsertBalanceQty(String No, String Nm) {
        FillAdapterFromBalanceQty(No);
        showList();
        BalanceQtyTrans = true;
    }

    private void FillAdapterFromBalanceQty(String OrderNo) {
        contactList.clear();


        Double Item_Total, Price, Tax_Amt, Tax, Total, Net_Total, Tax_Total;
        sqlHandler = new SqlHandler(this);

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;


    /* query = "  select ifnull(pod.Operand,0) as Operand  ,  pod.bounce_qty,pod.dis_per , pod.dis_Amt , pod.OrgPrice , pod.tax_Amt , pod.total ,Unites.UnitName,  invf.Item_Name, pod.itemno,pod.price,pod.qty,pod.tax ,pod.unitNo  " +
    " , pod.pro_Total    , pod.ProID , pod.Pro_bounce  ,pod.Pro_dis_Per,  pod.Pro_amt    " +
    "   from Sal_invoice_Det pod left join invf on invf.Item_No =  pod.itemno    " +
    "   left join Unites on Unites.Unitno=  pod.unitNo  Where pod.OrderNo='" + Order_no.getText().toString() + "'";

    */

        String query = "  select distinct Unites.UnitName,  invf.Item_Name, pod.Item_No,pod.Qty,pod.ActQty,pod.Diff ,pod.Unit_No " +
                " ,UnitItems.Operand ,UnitItems.price , invf.tax  from BalanceQty pod left join invf on invf.Item_No =  pod.Item_No    " +
                "left join Unites on Unites.Unitno=  pod.Unit_No  " +
                " Left join UnitItems on UnitItems.item_no =  pod.Item_No and UnitItems.unitno = pod.Unit_No" +
                " Where    ifnull( cast( pod.Diff as double) ,0) < 0     and pod.OrderNo='" + OrderNo + "'";


        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    //    Save_List(c1.getString(c1.getColumnIndex("Item_No")), Price.getText().toString(), qty.getText().toString(), tax.getText().toString(), UnitNo, disc_per.getText().toString(), bounce.getText().toString(), str, UnitName, disc_Amt.getText().toString(),Operand);
                    //Save_List(c1.getString(c1.getColumnIndex("Item_No")), "0", c1.getString(c1.getColumnIndex("Diff")), "16", c1.getString(c1.getColumnIndex("Unit_No")), "0", "0", c1.getString(c1.getColumnIndex("Item_Name")), c1.getString(c1.getColumnIndex("UnitName")), "0", "1");
                    try {
                        Cls_Sal_InvItems contactListItems = new Cls_Sal_InvItems();
                        double diff = SToD(c1.getString(c1.getColumnIndex("Diff")));

                        contactListItems.setno(c1.getString(c1.getColumnIndex("Item_No")));
                        contactListItems.setName(c1.getString(c1.getColumnIndex("Item_Name")));

                        Price = SToD(c1.getString(c1.getColumnIndex("price")));
                        Tax = SToD(c1.getString(c1.getColumnIndex("tax")));
                        // Item_Total = SToD(c1.getString(c1.getColumnIndex("Diff"))) * Price;
                        Item_Total = diff * Price;

                        Item_Total = Double.parseDouble(Item_Total.toString());

                        if (IncludeTax_Flag.isChecked()) {
                            contactListItems.setprice(String.valueOf(Price / ((Tax / 100) + 1)));
                        } else {
                            contactListItems.setprice(String.valueOf(Price));

                        }

                        contactListItems.setItemOrgPrice(String.valueOf(Price));
                        contactListItems.setQty(String.valueOf(diff));
                        contactListItems.setTax(String.valueOf(Tax));
                        contactListItems.setUnite(c1.getString(c1.getColumnIndex("Unit_No")));
                        contactListItems.setWeight("0");
                        contactListItems.setBounce("0");
                        contactListItems.setDiscount("0");
                        contactListItems.setProID("");
                        contactListItems.setDis_Amt("0");
                        contactListItems.setUniteNm(c1.getString(c1.getColumnIndex("UnitName")));
                        contactListItems.setPro_amt("0");
                        contactListItems.setPro_dis_Per("0");
                        contactListItems.setPro_bounce("0");
                        contactListItems.setPro_Total("0");
                        contactListItems.setDisAmtFromHdr("0");
                        contactListItems.setDisPerFromHdr("0");
                        contactListItems.setTax_Amt("0");
                        contactListItems.setNote(OrderNo);
                        contactListItems.setProType("0");
                        //      contactListItems.setNote("0");
                        // contactListItems.setSample(c1.getString(c1.getColumnIndex("sample")));
                        contactListItems.setOperand(c1.getString(c1.getColumnIndex("Operand")));
                        contactListItems.setTotal(String.valueOf(df.format(Item_Total)));
                        contactList.add(contactListItems);

    /*  CalcTotal();
    showList();*/

                    } catch (Exception ex) {
                        Toast.makeText(this, "حدث خطا في عملية استرجاع البيانات", Toast.LENGTH_SHORT).show();
                    }
                } while (c1.moveToNext());


            }
            c1.close();
            CalcTotal();
            showList();

        }

    }

    public void Set_Driver(String No, String Nm) {
        DriverNm.setText(Nm);

        ContentValues cv = new ContentValues();
        cv.put("driverno", No);

        Long i;
        i = sqlHandler.Update("Sal_invoice_Hdr", cv, "ifnull(doctype,'1')='" + DocType.toString() + "'  and  OrderNo ='" + OrderNo.getText().toString() + "'");
        if (i > 0) {
            Toast.makeText(Sale_InvoiceActivity.this, "تمت عملية تخزين السائق بنجاح  ", Toast.LENGTH_SHORT).show();

        }


    }


    private void ApplyCustDiscount() {
        Double InvTotla = 0.0;

        for (int x = 0; x < contactList.size(); x++) {

            if (contactList.get(x).getProID().equalsIgnoreCase("") || contactList.get(x).getProID().equalsIgnoreCase("0")) {
                InvTotla = InvTotla + SToD(contactList.get(x).getTotal());
            }

        }
        if (InvTotla > 0) {
            query = "SELECT ifnull(Discount_Value,0)  as  Discount_Value from DeptDiscount " +
                    "  Where CustNo ='" + tv_acc.getText().toString() + "' And " + InvTotla + " " +
                    "    between From_Value and  To_Value  order by cast(Discount_Value  as float)  desc" +
                    "    limit 1  ";

            Cursor c1 = sqlHandler.selectQuery(query);
            if (c1 != null && c1.getCount() != 0) {
                if (c1.moveToFirst()) {
                    DisAmt = SToD(c1.getString(c1.getColumnIndex("Discount_Value")));
                }
                c1.close();
            }
        }


        if (DisAmt > 0) {
            DiscountDept = "2";
            InsertDiscount(DisAmt + "", "1");
            new SweetAlertDialog(Sale_InvoiceActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("فاتورة مبيعات")
                    .setContentText("لقد تم منح العميل خصم قيمتة " + " " + DisAmt + " % ")
                    .setCustomImage(R.drawable.tick).show();
        }
    }

    @Override
    public void applyTexts(String note) {
        note_sal = note;


    }

    public boolean discountcheck(double dis) {
        boolean discount = true;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String u = sharedPreferences.getString("UserID", "");
        for (int i = 0; i < contactList.size(); i++) {
            //  String gg=contactList.get(i).getno();
            String maxdis = DB.GetValue(Sale_InvoiceActivity.this, "manf", "MaxDiscount", "man ='" + u + "'");
            if (Double.parseDouble(contactList.get(i).getDiscount()) + dis > Double.parseDouble(maxdis)) {
                discount = false;
            }

        }

        return discount;

    }

    public void updateCustomer() {
        final Handler _handler = new Handler();


        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(Sale_InvoiceActivity.this);
                ws.GetCustomers(UserID);
                try {
                    Integer i;
                    String q = "";
                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray js_no = js.getJSONArray("no");
                    JSONArray js_name = js.getJSONArray("name");
                    JSONArray js_Ename = js.getJSONArray("Ename");
                    JSONArray js_barCode = js.getJSONArray("barCode");
                    JSONArray js_Address = js.getJSONArray("Address");
                    JSONArray js_State = js.getJSONArray("State");
                    JSONArray js_SMan = js.getJSONArray("SMan");
                    JSONArray js_Latitude = js.getJSONArray("Latitude");
                    JSONArray js_Longitude = js.getJSONArray("Longitude");
                    JSONArray js_Note2 = js.getJSONArray("Note2");
                    JSONArray js_sat = js.getJSONArray("sat");

                    JSONArray js_sun = js.getJSONArray("sun");
                    JSONArray js_mon = js.getJSONArray("mon");
                    JSONArray js_tues = js.getJSONArray("tues");
                    JSONArray js_wens = js.getJSONArray("wens");
                    JSONArray js_thurs = js.getJSONArray("thurs");
                    JSONArray js_sat1 = js.getJSONArray("sat1");
                    JSONArray js_sun1 = js.getJSONArray("sun1");
                    JSONArray js_mon1 = js.getJSONArray("mon1");
                    JSONArray js_tues1 = js.getJSONArray("tues1");
                    JSONArray js_wens1 = js.getJSONArray("wens1");
                    JSONArray js_thurs1 = js.getJSONArray("thurs1");
                    JSONArray js_Celing = js.getJSONArray("Celing");
                    JSONArray js_CatNo = js.getJSONArray("CatNo");
                    JSONArray js_CustType = js.getJSONArray("CustType");
                    JSONArray js_PAMENT_PERIOD_NO = js.getJSONArray("PAMENT_PERIOD_NO");
                    JSONArray js_CUST_PRV_MONTH = js.getJSONArray("CUST_PRV_MONTH");
                    JSONArray js_CUST_NET_BAL = js.getJSONArray("CUST_NET_BAL");
                    JSONArray js_Pay_How = js.getJSONArray("Pay_How");
                    JSONArray js_Cust_type = js.getJSONArray("Cust_type");


                    JSONArray js_TaxStatus = js.getJSONArray("TaxSts");
                    JSONArray js_country_Nm = js.getJSONArray("country_Nm");
                    JSONArray js_country_No = js.getJSONArray("country_No");
                    JSONArray js_CheckAlowedDay = js.getJSONArray("CheckAlowedDay");
                    JSONArray js_PromotionFlag = js.getJSONArray("PromotionFlag");
                    JSONArray js_CloseVisitWithoutimg = js.getJSONArray("CloseVisitWithoutimg");
                    JSONArray Chqceling = js.getJSONArray("Chqceling");
                    JSONArray BB_bill = js.getJSONArray("BB");
                    JSONArray BB_Chaq = js.getJSONArray("BB_Chaq");


                    q = "Delete from Customers";
                    sqlHandler.executeQuery(q);
                    q = "delete from sqlite_sequence where name='Customers'";
                    sqlHandler.executeQuery(q);

                    for (i = 0; i < js_no.length(); i++) {
                        q = "Insert INTO Customers(Tax_Status,no,name,Ename,barCode,Address,State,SMan,Latitude,Longitude,Note2,sat " +
                                " ,sun,mon,tues,wens,thurs,sat1,sun1,mon1,tues1,wens1,thurs1 , Celing , CatNo " +
                                ",CustType,PAMENT_PERIOD_NO , CUST_PRV_MONTH,CUST_NET_BAL,Pay_How,Cust_type,LocationNo,Location,CheckAlowedDay,PromotionFlag,CloseVisitWithoutimg,BB_Chaq,BB_bill,Chqceling) values ('"
                                + js_TaxStatus.get(i).toString()
                                + "','" + js_no.get(i).toString()
                                + "','" + js_name.get(i).toString()
                                + "','" + js_Ename.get(i).toString()
                                + "','" + js_barCode.get(i).toString()
                                + "','" + js_Address.get(i).toString()
                                + "','" + js_State.get(i).toString()
                                + "','" + js_SMan.get(i).toString()
                                + "','" + js_Latitude.get(i).toString()
                                + "','" + js_Longitude.get(i).toString()
                                + "','" + js_Note2.get(i).toString()
                                + "'," + js_sat.get(i).toString()
                                + "," + js_sun.get(i).toString()
                                + "," + js_mon.get(i).toString()
                                + "," + js_tues.get(i).toString()
                                + "," + js_wens.get(i).toString()
                                + "," + js_thurs.get(i).toString()
                                + "," + js_sat1.get(i).toString()
                                + "," + js_sun1.get(i).toString()
                                + "," + js_mon1.get(i).toString()
                                + "," + js_tues1.get(i).toString()
                                + "," + js_wens1.get(i).toString()
                                + "," + js_thurs1.get(i).toString()
                                + ",'" + js_Celing.get(i).toString()
                                + "','" + js_CatNo.get(i).toString()
                                + "','" + js_CustType.get(i).toString()
                                + "','" + js_PAMENT_PERIOD_NO.get(i).toString()
                                + "','" + js_CUST_PRV_MONTH.get(i).toString()
                                + "','" + js_CUST_NET_BAL.get(i).toString()
                                + "','" + js_Pay_How.get(i).toString()
                                + "','" + js_Cust_type.get(i).toString()
                                + "','" + js_country_No.get(i).toString()

                                + "','" + js_country_Nm.get(i).toString()
                                + "','" + js_CheckAlowedDay.get(i).toString()
                                + "','" + js_PromotionFlag.get(i).toString()
                                + "','" + js_CloseVisitWithoutimg.get(i).toString()
                                + "','" + BB_Chaq.get(i).toString()
                                + "','" + BB_bill.get(i).toString()
                                + "','" + Chqceling.get(i).toString()
                                + "')";
                        sqlHandler.executeQuery(q);

                    }
                    final int total = i;
                    _handler.post(new Runnable() {

                        public void run() {

                        }
                    });

                } catch (final Exception e) {

                    _handler.post(new Runnable() {

                        public void run() {

                        }
                    });
                }
            }
        }).start();
    }

   /* public void onProgressUpdate( ){



        final List<String> items_ls = new ArrayList<String>();

        final   TextView acc = (TextView)findViewById(R.id.tv_acc);
        FromDate = (EditText)findViewById(R.id.ed_FromDate);
        ToDate = (EditText)findViewById(R.id.ed_ToDate);




        AlertDialog alertDialog  ;
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("تاريخ بداية الفترة");
        alertDialog.setMessage( "هناك خطأ في طريقة ادخال بداية الفترة ، الرجاء ادخال التاريخ كالتالي dd/mm/yyyy");
        alertDialog.setIcon(R.drawable.delete);
        alertDialog.setButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                FromDate.setText("");
                FromDate.requestFocus();
            }
        });

        if(!isValidDate(FromDate.getText().toString())){
            alertDialog.show();
            return;
        }



        alertDialog.setTitle("تاريخ نهاية الفترة");
        alertDialog.setMessage( "هناك خطأ في طريقة ادخال نهاية الفترة ، الرجاء ادخال التاريخ كالتالي dd/mm/yyyy");
        alertDialog.setIcon(R.drawable.delete);
        alertDialog.setButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ToDate.setText("");
                ToDate.requestFocus();

            }
        });

        if(!isValidDate(ToDate.getText().toString())){

            alertDialog.show();
            return;
        }
        cls_acc_reportsList.clear();
        CheqBal.setText("");
        Ball.setText("");
        CusTop.setText("");
        NetBall.setText("");

        final Handler _handler = new Handler();
        CUST_NET_BAL =  DB.GetValue(Acc_ReportActivity.this, "Customers", "CUST_NET_BAL", "no ='" + acc.getText().toString() + "' ");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String UserID = sharedPreferences.getString("UserID", "");
        final ProgressDialog custDialog = new ProgressDialog(Acc_ReportActivity.this);

        custDialog.setTitle("الرجاء الانتظار");
        custDialog.setMessage("العمل جاري على نسخ البيانات");
        custDialog.setProgressStyle(custDialog.STYLE_HORIZONTAL);
        custDialog.setCanceledOnTouchOutside(false);
        custDialog.setProgress(0);
        custDialog.setMax(100);
        custDialog.show();

        Locale locale ;
        locale   = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Per= new DecimalFormat("0.000");

        new Thread(new Runnable() {
            @Override
            public void run() {

                CallWebServices ws = new CallWebServices(Acc_ReportActivity.this);
                ws.CallReport(acc.getText().toString(), FromDate.getText().toString(),ToDate.getText().toString(),UserID);


                try {
                    float t_dept , t_cred,t_bb ,tot ,t_tot,temp,t_rate;
                    t_dept= t_cred=t_bb =tot =t_tot =temp=t_rate =  0 ;
                    Integer i;
                    String q = "";
                    JSONObject js = new JSONObject(We_Result.Msg);


                    JSONArray js_doc_num= js.getJSONArray("doc_num");
                    JSONArray js_doctype= js.getJSONArray("doctype");
                    JSONArray js_cur_no= js.getJSONArray("cur_no");
                    JSONArray js_date= js.getJSONArray("date");
                    JSONArray js_des= js.getJSONArray("des");
                    JSONArray js_bb= js.getJSONArray("bb");
                    JSONArray js_dept= js.getJSONArray("dept");
                    JSONArray js_cred= js.getJSONArray("cred");
                    JSONArray js_rate= js.getJSONArray("rate");



                    Cls_Acc_Report cls_acc_report = new Cls_Acc_Report();

                    cls_acc_report.setTot("الرصيد");
                    cls_acc_report.setRate("العملة المقابلة");
                    cls_acc_report.setCred("الدائن");
                    cls_acc_report.setDept("المدين");
                    cls_acc_report.setBb("رصيد إفتتاحي");
                    cls_acc_report.setDes(" البيــــــــان");
                    cls_acc_report.setDate("التاريــخ");
                    cls_acc_report.setCur_no("العملة");
                    cls_acc_report.setDoctype("نوع المستند ");
                    cls_acc_report.setDoc_num("رقم المستند");
                    cls_acc_reportsList.add(cls_acc_report);

                    // date,fromstore,tostore,des,docno,itemno,qty,UnitNo,UnitRate,myear





                    for( i =0 ; i<js_doc_num.length();i++)
                    {
                        cls_acc_report = new Cls_Acc_Report();

                        cls_acc_report.setDoc_num(js_doc_num.get(i).toString());
                        cls_acc_report.setDoctype(js_doctype.get(i).toString());
                        cls_acc_report.setCur_no(js_cur_no.get(i).toString());
                        cls_acc_report.setDate(js_date.get(i).toString());
                        cls_acc_report.setDes(js_des.get(i).toString());
                        if(i==0)
                            cls_acc_report.setBb(js_bb.get(i).toString());
                        else
                            cls_acc_report.setBb("0.000");
                        cls_acc_report.setDept(js_dept.get(i).toString());
                        cls_acc_report.setCred(js_cred.get(i).toString());
                        if( Float.parseFloat( js_dept.get(i).toString())>0) {
                            temp = Float.parseFloat(js_dept.get(i).toString()) * Float.parseFloat(js_rate.get(i).toString());
                        } else {
                            temp = Float.parseFloat(js_cred.get(i).toString()) * Float.parseFloat(js_rate.get(i).toString());
                        }




                        cls_acc_report.setRate(( Per.format(temp)  + ""));
                        t_rate = t_rate + temp;
                        t_dept  = t_dept +  Float.parseFloat( js_dept.get(i).toString());
                        t_cred  = t_cred +  Float.parseFloat( js_cred.get(i).toString());
                        t_bb  =  Float.parseFloat( js_bb.get(i).toString());


                        if(i==0)
                            tot= tot +   (Float.parseFloat( js_dept.get(i).toString()) +   Float.parseFloat( js_bb.get(0).toString())  -   Float.parseFloat( js_cred.get(i).toString()));
                        else
                            tot= tot +   (Float.parseFloat( js_dept.get(i).toString())  -   Float.parseFloat( js_cred.get(i).toString()));


                        cls_acc_report.setTot(String.valueOf(Per.format(tot)));

                        cls_acc_reportsList.add(cls_acc_report);


                        custDialog.setMax(js_doc_num.length());
                        custDialog.incrementProgressBy(1);

                        if (custDialog.getProgress() == custDialog.getMax()) {
                            custDialog.dismiss();
                        }

                    }



                    Locale locale ;
                    locale   = new Locale("ar");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

                    final int total = i;
                    final  String   txtCheqBal = js.getString("CheqBal");
                    final  String   txtBall = js.getString("Ball");
                    final  String  txtCusTop = js.getString("CusTop");
                    final  String   txtNetBall = js.getString("NetBall");




                    final  String   S_t_bb =  String.valueOf(t_bb);
                    final  String   S_dept =  String.valueOf(t_dept);
                    final  String   S_cred =  String.valueOf(t_cred);
                    final  String   S_rate =  String.valueOf(t_rate);
                    final  String   S_tot =  String.valueOf(tot);


                    _handler.post(new Runnable() {

                        public void run() {

                            CheqBal.setText(txtCheqBal);
                            Ball.setText(txtBall);
                            CusTop.setText(txtCusTop);
                            NetBall.setText(S_tot);

                            Cls_Acc_Report cls_acc_report1 = new Cls_Acc_Report();
                            cls_acc_report1 = new Cls_Acc_Report();
                            cls_acc_report1.setDate("عدد الحركات");
                            cls_acc_report1.setCur_no((cls_acc_reportsList.size()-1)+"");
                            cls_acc_report1.setDoctype("");
                            cls_acc_report1.setDoc_num("");
                            cls_acc_report1.setDes("المجموع");
                            cls_acc_report1.setBb(Per.format(Double.parseDouble( S_t_bb))+"" );
                            cls_acc_report1.setDept( Per.format(Double.parseDouble( S_dept))+"");
                            cls_acc_report1.setCred(Per.format(Double.parseDouble(S_cred))+"");
                            cls_acc_report1.setRate(Per.format(Double.parseDouble( S_rate))+"");
                            //cls_acc_report1.setTot(CUST_NET_BAL);
                            cls_acc_report1.setTot(S_tot);


                            cls_acc_reportsList.add(cls_acc_report1);

                            Cls_Acc_Report_Adapter cls_acc_report_adapter = new Cls_Acc_Report_Adapter(
                                    Acc_ReportActivity.this, cls_acc_reportsList);

                            items_Lsit.setAdapter(cls_acc_report_adapter);
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    Acc_ReportActivity.this).create();
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

                } catch (final Exception e) {
                    custDialog.dismiss();
                    _handler.post(new Runnable() {

                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    Acc_ReportActivity.this).create();
                            alertDialog.setTitle("كشف حساب تفصيلي");
                            if (We_Result.ID == -404) {
                                alertDialog.setMessage(We_Result.Msg);
                            } else {
                                alertDialog.setMessage("لا يوجد بيانات" );
                            }
                            alertDialog.setIcon(R.drawable.tick);

                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialog.show();

                        }
                    });
                }


            }
        }).start();




    }*/

    private String Get_RequestPermission() {


        TextView acc = (TextView) findViewById(R.id.tv_acc);
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        ContentValues cv = new ContentValues();

        final SqlHandler sql_Handler = new SqlHandler(this);

        final String ManNo, CustNo, OrderNo, Desc, Type;

        ManNo = UserID;
        CustNo = acc.getText().toString();
        OrderNo = pono.getText().toString();

        Type = "1";

        greenProgressbar = this.getResources().getDrawable(R.drawable.progrees_states);
        final Handler _handler = new Handler();
        tv = new TextView(getApplicationContext());
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
        progressDialog = new ProgressDialog(Sale_InvoiceActivity.this);
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setMessage("  الرجاء الانتظار ..." + "  العمل جاري على جلب البيانات  ");
        tv.setText("فاتورة البيع");
        progressDialog.setCustomTitle(tv);
        progressDialog.setProgressDrawable(greenProgressbar);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(Sale_InvoiceActivity.this);
                ws.Get_Request_Permission(ManNo, CustNo, OrderNo, "1");
                try {
                    Integer i;
                    String q;
                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray js_Status = js.getJSONArray("Status");

                    Result = js_Status.get(0).toString();


                    progressDialog.setMax(1);
                    progressDialog.incrementProgressBy(1);
                    if (progressDialog.getProgress() == progressDialog.getMax()) {

                        progressDialog.dismiss();
                    }


                    _handler.post(new Runnable() {
                        public void run() {
                            if (Result.equals("1")) {
                                query = "Update PermissionCode set Status =1   Where   ManNo='" + ManNo + "' and CustNo ='" + CustNo + "' " +
                                        "And OrderNo ='" + OrderNo + "'";
                                sqlHandler.executeQuery(query);
                                Save_Recod_Po();
                            } else
                                CheckCelingNew();


                            //   CheckCelingNew();

                        }
                    });
                } catch (final Exception e) {
                    progressDialog.dismiss();
                    //CheckCelingNew();

                  /*  progressDialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {

                        }
                    });*/
                }
            }
        }).start();

        return Result;
    }


}