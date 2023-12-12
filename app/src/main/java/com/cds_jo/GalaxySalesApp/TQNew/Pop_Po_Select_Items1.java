package com.cds_jo.GalaxySalesApp.TQNew;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.Cls_Qty_Batch;
import com.cds_jo.GalaxySalesApp.Cls_Sal_InvItems;
import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.cds_jo.GalaxySalesApp.assist.Cls_Cur;
import com.cds_jo.GalaxySalesApp.assist.Cls_Deptf;
import com.cds_jo.GalaxySalesApp.assist.Cls_Deptf_adapter;
import com.cds_jo.GalaxySalesApp.assist.Cls_Invf;
import com.cds_jo.GalaxySalesApp.assist.Cls_Invf_Adapter;
import com.cds_jo.GalaxySalesApp.assist.Cls_UnitItems;
import com.cds_jo.GalaxySalesApp.assist.Cls_UnitItems_Adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Methdes.MethodToUse;
import Methdes.MyTextView;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class Pop_Po_Select_Items1 extends DialogFragment implements View.OnClickListener {
    public static final String CALCULATOR_PACKAGE = "com.android.calculator2";
    public static final String CALCULATOR_CLASS = "com.android.calculator2.Calculator";
    View form;
    Button add, cancel,btn_get_Cust_Qty;
    ImageButton OpenCal;
    ListView items_Lsit , LstBounce,LstCustQty,Lst_Batch;
    TextView itemnm;
    Context context;
    TextView Store_Qty;
    public String ItemNo = "";
    SqlHandler sqlHandler;
    float min = 0;
    Double min_price = 0.0;
    EditText filter,et_StoreQty,et_ItemCount,ed_Top;
    ImageButton btn_filter_search;
    String UnitNo = "";
    String Operand = "";
    String accno = "";
    Cls_Qty_Batch cls_qty_batch;
    ArrayList<Cls_Qty_Batch> cls_qty_batches;
    String UnitName = "";
    String str = "";
    TextView tv  ;
    Cls_GetCustomerQty cls_getCustomerQty;
    ArrayList<Cls_GetCustomerQty>CustomerQty_List;
    Drawable greenProgressbar;
    RelativeLayout.LayoutParams lp;
    List<Cls_Sal_InvItems> UpdateItem;

    MyTextView tv_ExpDate, tv_Batch;
    ArrayList<Cls_Bounce_Plane> BouncePlaneList ;
    ArrayList<Cls_Item_Expire> ExpireBatchList ;
    int ItemCount = 0;
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
    @Override
    public void onStart()
    {
        super.onStart();


        if (getDialog() == null)
            return;


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom( getDialog().getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(lp);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setWindowAnimations(0);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savestate) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        form = inflater.inflate(R.layout.fragment_pop__po__select__items1, container, false);

        context=getActivity();
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.TOP|Gravity.LEFT);
        getDialog().setTitle("Galaxy");
        BouncePlaneList = new ArrayList<Cls_Bounce_Plane>();
        ExpireBatchList = new ArrayList<Cls_Item_Expire>();
        btn_get_Cust_Qty = (Button) form.findViewById(R.id.btn_get_Cust_Qty);
        add = (Button) form.findViewById(R.id.btn_add_item);
        add.setOnClickListener(this);
        btn_get_Cust_Qty.setOnClickListener(this);
        cancel = (Button) form.findViewById(R.id.btn_cancel_item);
        OpenCal = (ImageButton) form.findViewById(R.id.btn_OpenCal);

        btn_get_Cust_Qty.setTypeface(MethodToUse.SetTFace(context ));
        add.setTypeface(MethodToUse.SetTFace(context ));
        cancel.setTypeface(MethodToUse.SetTFace(context ));
        et_StoreQty = (EditText) form.findViewById(R.id.et_StoreQty);
        et_ItemCount = (EditText) form.findViewById(R.id.et_ItemCount);
        ed_Top = (EditText) form.findViewById(R.id.ed_Top);


        tv_ExpDate = (MyTextView) form.findViewById(R.id.tv_ExpDate);
        tv_Batch = (MyTextView) form.findViewById(R.id.tv_Batch);
        cls_qty_batches = new ArrayList<Cls_Qty_Batch>();

        FillDeptf();
        cancel.setOnClickListener(this);


        LstBounce = (ListView) form.findViewById(R.id.Lst_Bounce);
        LstBounce = (ListView) form.findViewById(R.id.Lst_Bounce);
        LstCustQty = (ListView) form.findViewById(R.id.Lst_CustQty);
        items_Lsit = (ListView) form.findViewById(R.id.listView2);
        Lst_Batch = (ListView) form.findViewById(R.id.Lst_Batch);
        final List<String> items_ls = new ArrayList<String>();


        final EditText Price = (EditText) form.findViewById(R.id.et_price);


        final EditText qty = (EditText) form.findViewById(R.id.et_qty);

        final EditText bo = (EditText) form.findViewById(R.id.et_bo);

        sqlHandler = new SqlHandler(context );





        Price.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Price.setText("", TextView.BufferType.EDITABLE);
                }
            }

        });
        qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    qty.setText("", TextView.BufferType.EDITABLE);
                }
            }

        });




        filter = (EditText) form.findViewById(R.id.et_Search_filter);

        filter.setInputType(InputType.TYPE_NULL);

        filter.setInputType(InputType.TYPE_CLASS_TEXT);
        filter.requestFocus();


        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Toast.makeText(context(),s.toString(),Toast.LENGTH_SHORT).show();
                FillItems();

            }
        });
        Price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {



                get_total();
            }
        });

        qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                get_total();
                CalcBounce();
                GetQtyPerc();
            }
        });


        bo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                GetQtyPerc();
            }
        });


        FillItems();
        // fillUnit("-1");

        final Spinner item_cat = (Spinner) form.findViewById(R.id.sp_item_cat);

        item_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FillItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        final Spinner sp_unite = (Spinner) form.findViewById(R.id.sp_units);

        sp_unite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Cls_UnitItems o = (Cls_UnitItems) sp_unite.getItemAtPosition(position);
                EditText Price = (EditText) form.findViewById(R.id.et_price);
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat df = (DecimalFormat) nf;

                Double CusPrice = 0.0;
                CusPrice = GetCustLastPrice(ItemNo, o.getUnitno().toString());
                if (CusPrice > 0) {
                    Price.setText(CusPrice.toString().replace(",", ""));
                } else {
                    Price.setText(String.valueOf(df.format(SToD(o.getPrice().toString()))).replace(",", ""));


                }


                UnitNo = o.getUnitno().toString();
                UnitName = o.getUnitDesc().toString();
                Operand = o.getOperand().toString();
                min = Float.valueOf(o.getMin());
                get_min_price();
                checkStoreQty();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        items_Lsit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Double Qty = 0.0;

                for (int i = 0; i < items_Lsit.getChildCount(); i++) {
                    View listItem = items_Lsit.getChildAt(i);
                    if (i % 2 == 0)
                        listItem.setBackgroundColor(Color.WHITE);
                    if (i % 2 == 1)
                        listItem.setBackgroundColor(context.getResources().getColor(R.color.Gray2));
                }


                arg1.setBackgroundColor(Color.GRAY);
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat df = (DecimalFormat) nf;

                Cls_Invf o = (Cls_Invf) items_Lsit.getItemAtPosition(position);
                ItemNo = o.getItem_No().toString();

                EditText et_Discount = (EditText) form.findViewById(R.id.et_Discount);
                EditText Price = (EditText) form.findViewById(R.id.et_price);
                EditText et_qty = (EditText) form.findViewById(R.id.et_qty);
                EditText et_totl = (EditText) form.findViewById(R.id.et_totl);
                et_totl.setError(null);
                et_qty.setText("1");
                et_qty.clearFocus();
                et_Discount.setText("");


                EditText tax = (EditText) form.findViewById(R.id.et_tax);
                tax.setText(o.getTax().toString());


                str = (String) o.getItem_Name();


                getDialog().setTitle(str);
                fillUnit(o.getItem_No().toString());
                ItemNo = o.getItem_No().toString();



                fillUnitFor_Taqtom(o.getItem_No().toString());

                tax.setText(DB.GetValue(context, "invf", "tax", "LOWER(Item_No)='" + o.getItem_No().toLowerCase() + "'"));





                Price.setError(null);
                Price.clearFocus();
                et_qty.setError(null);
                tax.setError(null);


                checkStoreQty();
                FillBouncePlane(o.getItem_No().toString());
                FillExpireDate(o.getItem_No().toString());
                //LstCustQty.setAdapter(null);


                /*et_qty.requestFocus();
                et_qty.selectAll();*/
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_qty, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    qty.setText("", TextView.BufferType.EDITABLE);
                }
            }

        });

        this.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        try {
            ItemCount = Integer.parseInt(getArguments().getString("contactListsize"));
            accno = getArguments().getString("accno") ;
        }catch (Exception ex){
            ItemCount = 0 ;
        }
        if (ItemCount==-1){
            ItemCount=0;
        }
        et_ItemCount.setText(ItemCount+"");
        return form;
    }
    private void FillExpireDate(String itemNo ) {
        ExpireBatchList.clear();
        String query = "Select  distinct expdate  , batchno,net, Item_No from QtyBatch Where Item_No='"+itemNo+"'  ";

        Cursor c1 = sqlHandler.selectQuery(query);
        Cls_Item_Expire obj;
        obj = new Cls_Item_Expire();
        obj.setBatchno("كمية");
        obj.setItem_Nm("بونص");


        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    obj = new Cls_Item_Expire();
                    obj.setNet(c1.getString(c1
                            .getColumnIndex("net")));
                    obj.setExpdate(c1.getString(c1
                            .getColumnIndex("expdate")));
                    obj.setBatchno(c1.getString(c1
                            .getColumnIndex("batchno")));
                    ExpireBatchList.add(obj);
                } while (c1.moveToNext());
            }
            c1.close();
        }else{
            ExpireBatchList.clear();
        }
        /*Cls_Expire_Batch_Adapter adapter = new Cls_Expire_Batch_Adapter(
                getActivity(), ExpireBatchList);
        Lst_Batch.setAdapter(adapter);*/

        //  LstCustQty.setAdapter(null);
        Cls_Expire_Batch_Adapter adapter1 = new Cls_Expire_Batch_Adapter(
                getActivity(), ExpireBatchList);
        LstCustQty.setAdapter(adapter1);
    }
    private void FillBouncePlane(String itemNo ) {
        BouncePlaneList.clear();
        String query = "Select  distinct Qty , Bounce from Item_Bounce Where Item_No='"+itemNo+"' order by cast(Qty as integer)  desc";
        ArrayList<Cls_Cur> cls_curs = new ArrayList<Cls_Cur>();
        cls_curs.clear();
        Cursor c1 = sqlHandler.selectQuery(query);
        Cls_Bounce_Plane obj;
        obj = new Cls_Bounce_Plane();
        obj.setQty("كمية");
        obj.setBounce("بونص");
        BouncePlaneList.add(obj);

        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    obj = new Cls_Bounce_Plane();
                    obj.setQty(c1.getString(c1
                            .getColumnIndex("Qty")));
                    obj.setBounce(c1.getString(c1
                            .getColumnIndex("Bounce")));
                    BouncePlaneList.add(obj);
                } while (c1.moveToNext());
            }
            c1.close();
        }else{
            BouncePlaneList.clear();
        }
        Cls_BouncePalne_Adapter adapter = new Cls_BouncePalne_Adapter(
                getActivity(), BouncePlaneList);
        LstBounce.setAdapter(adapter);
    }
    private void  FILLbounc(){
        ListView lst_Promotion = (ListView) form.findViewById(R.id.lst_Promotion);
        final List<String> promotion_ls = new ArrayList<String>();
        String q = "Select  distinct Qty,Bounce from Item_Bounce order by priority asc ";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    promotion_ls.add(c1.getString(c1.getColumnIndex("Qty")));

                } while (c1.moveToNext());

            }
            c1.close();
        }

        ArrayAdapter<String> promotion_ad = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_activated_1, promotion_ls);

        lst_Promotion.setAdapter(promotion_ad);

    }
    private void checkStoreQtyBatch() {


        final Handler _handler = new Handler();

        tv_ExpDate.setText("");
        tv_Batch.setText("");

        cls_qty_batches.clear();

        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(context);
                ws.TrnsferQtyFromMobileBatch(ItemNo, "1");
                try {
                    Integer i;

                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray js_Item_No = js.getJSONArray("Item_No");
                    JSONArray js_expdate = js.getJSONArray("expdate");
                    JSONArray js_batchno = js.getJSONArray("batchno");
                    JSONArray js_net = js.getJSONArray("net");


                    for (i = 0; i < js_Item_No.length(); i++) {
                        cls_qty_batch = new Cls_Qty_Batch();

                        cls_qty_batch.setItem_No(js_Item_No.get(i).toString());
                        cls_qty_batch.setExpdate(js_expdate.get(i).toString());
                        cls_qty_batch.setBatchno(js_batchno.get(i).toString());
                        cls_qty_batch.setNet(js_net.get(i).toString());

                        cls_qty_batches.add(cls_qty_batch);

                    }

                    _handler.post(new Runnable() {
                        public void run() {
                            cls_qty_batch = new Cls_Qty_Batch();
                            if (cls_qty_batches.size() > 0) {
                                cls_qty_batch = cls_qty_batches.get(0);

                                CalcBatchQty();
                            }
                         /*   else
                            {

                            }*/
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


        GetQtyPerc();
    }

    private void CalcBatchQty() {
        Double OrderQty = 0.0;


        Double StoreQty = 0.0;
        tv_ExpDate.setText("");
        tv_Batch.setText("");
        TextView tv_StoreQty = (TextView) form.findViewById(R.id.tv_StoreQty);
        Double Qty = 0.0;
        for (int i = 0; i < cls_qty_batches.size(); i++) {
            cls_qty_batch = new Cls_Qty_Batch();
            cls_qty_batch = cls_qty_batches.get(i);
            Qty = SToD(cls_qty_batch.getNet()) / SToD(Operand);
            OrderQty = ((OrdersItems1) context).GetOrderQty(ItemNo, cls_qty_batch.getBatchno(), cls_qty_batch.getExpdate());
            StoreQty = Qty - OrderQty;
            StoreQty = SToD(StoreQty + "");
            if (StoreQty > 0) {
                tv_ExpDate.setText(cls_qty_batch.getExpdate());
                tv_Batch.setText(cls_qty_batch.getBatchno());
                tv_StoreQty.setText(StoreQty + "");

                break;
            }


        }


    }

    private double GetCustLastPrice(String ItemNo, String UnitNo) {
        Double r = 0.0;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context );
        String CustNo = sharedPreferences.getString("CustNo", "");

        String q = "select ifnull(Price,0) as price  from CustLastPrice where " +
                " Item_No = '" + ItemNo + "' and Cust_No ='" + CustNo + "' and  Unit_No = '" + UnitNo + "'";
        Cursor c = sqlHandler.selectQuery(q);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            r = SToD(c.getString(c.getColumnIndex("price")));
            c.close();
        }

        return r;
    }

    private void get_min_price() {


        min_price = 0.0;
        String CatNo = "";
        CatNo = getArguments().getString("CatNo");
        if (CatNo != "0") {
            String q = "Select  ifnull( MinPrice,0) as min_price from Items_Categ where ItemCode = '" + ItemNo + "'   " +
                    "  And CategNo = '" + CatNo + "'";
            Cursor c1 = sqlHandler.selectQuery(q);
            if (c1 != null && c1.getCount() != 0) {
                if (c1.getCount() > 0) {
                    c1.moveToFirst();
                    min_price = SToD(Operand) * SToD(c1.getString(c1.getColumnIndex("min_price")));
                    min_price = SToD(min_price.toString());

                }
                c1.close();
            }
        }

    }

    private void checkStoreQty() {

        TextView Store_Qty = (TextView) form.findViewById(R.id.tv_StoreQty);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context );


        Double Order_qty = 0.0;
        Double Res = 0.0;


        String query = "SELECT   ifnull( qty,0)   as  qty   from ManStore where  itemno = '" + ItemNo + "'  ";
        Cursor c1 = sqlHandler.selectQuery(query);

        Double Store_qty = 0.0;
        if (c1 != null && c1.getCount() != 0) {
            try {
                if (c1.getCount() > 0) {

                    c1.moveToFirst();
                    Store_qty = SToD(c1.getString(c1.getColumnIndex("qty")));
                    c1.close();
                }
            } catch (Exception exception) {
                Store_qty = 0.0;
            }

        }


        query = "   SELECT       (ifnull( sum  ( ifnull( sid.qty,0)  * (ifnull( 1,1))) ,0)  +   ifnull( sum  ( ifnull( sid.bounce_qty,0)  * (ifnull( 1,1))) ,0) +  ifnull( sum  ( ifnull( sid.Pro_bounce,0)  * (ifnull(1,1))) ,0))  as Sal_Qty  from  Po_Hdr  sih inner join Po_dtl sid on  sid.OrderNo = sih.OrderNo" +
                "   inner join  UnitItems ui on ui.item_no  = sid.itemNo and ui.unitno = sid.unitNo" +
                "   where   sih.Posted = -1  and ui.item_no ='" + ItemNo + "'  ";



        if (UpdateItem != null && UpdateItem.size() > 0) {
            query = query + "And sid.OrderNo !='" + getArguments().getString("OrderNo") + "'";
        }
        c1 = sqlHandler.selectQuery(query);

        Double Sal_Qty = 0.0;
        if (c1 != null && c1.getCount() != 0) {
            if (c1.getCount() > 0) {
                c1.moveToFirst();
                Sal_Qty = SToD((c1.getString(c1.getColumnIndex("Sal_Qty"))).toString());
            }

            c1.close();

        }
        Res = Store_qty - Sal_Qty - Order_qty;
        if (SToD(Operand) == 0) {
            Res = 0.0;
        } else {
            Res = Res / SToD(Operand);
        }

        Store_Qty.setText(SToD(Res.toString()).toString());
        et_StoreQty.setText(SToD(Res.toString()).toString());


        GetQtyPerc();
    }


    private void CalcDiscount() {
     /*   final EditText dis = (EditText) form.findViewById(R.id.et_disc_per);
        final EditText totl = (EditText) form.findViewById(R.id.et_totl);
        final EditText Discount = (EditText) form.findViewById(R.id.et_Discount);
        final EditText dis_Amt = (EditText) form.findViewById(R.id.et_dis_Amt);
        final EditText et_totl = (EditText) form.findViewById(R.id.et_totl);

        final EditText et_qty = (EditText) form.findViewById(R.id.et_qty);
        final EditText et_price = (EditText) form.findViewById(R.id.et_price);

        Double pq = 0.0;
        pq = SToD(et_price.getText().toString()) * SToD(et_qty.getText().toString());
        if (rad_Per.isChecked()) {


            Double Total = ((SToD(Discount.getText().toString().replace(",", "")) / 100)) * pq;

            Total = SToD(Total.toString());

            dis_Amt.setText(String.valueOf((Total)));
            dis.setText(String.valueOf(Discount.getText()));

        } else {
            Double Total = 0.0;
            if (pq == 0) {
                Total = 0.0;
            } else {
                Total = (SToD(Discount.getText().toString()) / pq) * 100;
            }

            Total = SToD(Total.toString());


            dis.setText(Total.toString());
            dis_Amt.setText(String.valueOf(SToD(Discount.getText().toString())));

        }
        if (totl.getText().toString().equals("0")) {
            dis.setText("0");
            dis_Amt.setText("0");
        }

        et_totl.setText(String.valueOf(SToD(String.valueOf(pq - SToD(dis_Amt.getText().toString())))).replace(",", ""));
    */
    }

    private void FillDeptf() {
        final Spinner sp_items_cat = (Spinner) form.findViewById(R.id.sp_item_cat);
        sqlHandler = new SqlHandler(context );

        String query = "Select   distinct Type_No , Type_Name , etname from deptf";
        ArrayList<Cls_Deptf> cls_deptfs = new ArrayList<Cls_Deptf>();
        cls_deptfs.clear();

        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Deptf clsDeptfs = new Cls_Deptf();

                    clsDeptfs.setType_No(c1.getString(c1
                            .getColumnIndex("Type_No")));
                    clsDeptfs.setType_Name(c1.getString(c1
                            .getColumnIndex("Type_Name")));

                    cls_deptfs.add(clsDeptfs);

                } while (c1.moveToNext());

            }
            c1.close();
        }

        Cls_Deptf_adapter cls_unitItems_adapter = new Cls_Deptf_adapter(
                context , cls_deptfs);

        sp_items_cat.setAdapter(cls_unitItems_adapter);
    }

    private void FillItems() {
        filter = (EditText) form.findViewById(R.id.et_Search_filter);
        String query = "";
        sqlHandler = new SqlHandler(context );
        if (getArguments().getString("Scr") == "po") {
            if (filter.getText().toString().equals("")) {
                query = "Select distinct invf.Item_No , invf.Item_Name,invf.Price, invf.tax   from invf    " +
                        "   where 1=1   ";
            } else {
                query = "Select distinct  invf.Item_No , invf.Item_Name,invf.Price, invf.tax from  invf " +
                        "    where Item_Name  like '%" + filter.getText().toString() + "%'  or  Item_No like '%" + filter.getText().toString() + "%'      ";
            }

        }
        query= query +"    order by  invf.Item_Name asc  limit 30 ";

        ArrayList<Cls_Invf> cls_invf_List = new ArrayList<Cls_Invf>();
        cls_invf_List.clear();
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Invf cls_invf = new Cls_Invf();

                    cls_invf.setItem_No(c1.getString(c1
                            .getColumnIndex("Item_No")));
                    cls_invf.setItem_Name(c1.getString(c1
                            .getColumnIndex("Item_Name")));
                    cls_invf.setPrice(c1.getString(c1
                            .getColumnIndex("Price")));
                    cls_invf.setTax(c1.getString(c1
                            .getColumnIndex("tax")));
                    cls_invf_List.add(cls_invf);

                } while (c1.moveToNext());

            }
            c1.close();
        }

        Cls_Invf_Adapter cls_invf_adapter = new Cls_Invf_Adapter(
                context , cls_invf_List);
        items_Lsit.setAdapter(cls_invf_adapter);


    }

    public void fillUnitFor_Taqtom(String item_no) {

        String  p=   (DB.GetValue(context , "invf", "Price", "LOWER(Item_No)='" +item_no.toLowerCase() + "'"));
        EditText Price = (EditText) form.findViewById(R.id.et_price);

        Spinner sp_unite = (Spinner) form.findViewById(R.id.sp_units);
      /*  sqlHandler = new SqlHandler(context());

        String query = " Select  distinct Unites.Unitno ,'1' as Min ,inf.Price  as price,Unites.UnitName " +
                " , '1' as Operand from   Unites  left join invf inf   on  inf.Unit = Unites.Unitno   where inf.Item_No ='" + item_no + "'  ";
        ArrayList<Cls_UnitItems> cls_unitItemses = new ArrayList<Cls_UnitItems>();
        cls_unitItemses.clear();

        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_UnitItems cls_unitItems = new Cls_UnitItems();

                    cls_unitItems.setUnitno(c1.getString(c1
                            .getColumnIndex("Unitno")));
                    cls_unitItems.setUnitDesc(c1.getString(c1
                            .getColumnIndex("UnitName")));
                    cls_unitItems.setPrice(c1.getString(c1
                            .getColumnIndex("price")));
                    cls_unitItems.setMin(c1.getString(c1
                            .getColumnIndex("Min")));
                    cls_unitItems.setOperand(c1.getString(c1.getColumnIndex("Operand")));

                    cls_unitItemses.add(cls_unitItems);

                } while (c1.moveToNext());

            }
            c1.close();
        }

        Cls_UnitItems_Adapter cls_unitItems_adapter = new Cls_UnitItems_Adapter(
                context(), cls_unitItemses);

        sp_unite.setAdapter(cls_unitItems_adapter);
*/



        Cls_UnitItems cls_unitItems = new Cls_UnitItems();

        cls_unitItems.setUnitno("PCS");
        cls_unitItems.setUnitDesc("PCS");
        cls_unitItems.setPrice(p);
        cls_unitItems.setMin("1");
        cls_unitItems.setOperand("1");

        ArrayList<Cls_UnitItems> cls_unitItemses = new ArrayList<Cls_UnitItems>();
        cls_unitItemses.clear();
        cls_unitItemses.add(cls_unitItems);

        Cls_UnitItems_Adapter cls_unitItems_adapter = new Cls_UnitItems_Adapter(
                context , cls_unitItemses);

        sp_unite.setAdapter(cls_unitItems_adapter);
        if (cls_unitItemses.size() > 0) {
            Cls_UnitItems o = (Cls_UnitItems) sp_unite.getItemAtPosition(0);
            UnitNo = o.getUnitno().toString();
            UnitName = o.getUnitDesc().toString();
            Operand = o.getOperand().toString();
            min = Float.valueOf(o.getMin());
        }
        Price.setText(p);
    }

    public void fillUnit(String item_no) {


        Spinner sp_unite = (Spinner) form.findViewById(R.id.sp_units);
        sqlHandler = new SqlHandler(context );

        String query = "Select  distinct UnitItems.unitno ,UnitItems.Min ,UnitItems.price,Unites.UnitName " +
                " , ifnull(Operand,1) as Operand from UnitItems  left join  Unites on Unites.Unitno =UnitItems.unitno where UnitItems.item_no ='" + item_no + "' order by   UnitItems.unitno desc";
        ArrayList<Cls_UnitItems> cls_unitItemses = new ArrayList<Cls_UnitItems>();
        cls_unitItemses.clear();

        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_UnitItems cls_unitItems = new Cls_UnitItems();

                    cls_unitItems.setUnitno(c1.getString(c1
                            .getColumnIndex("unitno")));
                    cls_unitItems.setUnitDesc(c1.getString(c1
                            .getColumnIndex("UnitName")));
                    cls_unitItems.setPrice(c1.getString(c1
                            .getColumnIndex("price")));
                    cls_unitItems.setMin(c1.getString(c1
                            .getColumnIndex("Min")));
                    cls_unitItems.setOperand(c1.getString(c1.getColumnIndex("Operand")));

                    cls_unitItemses.add(cls_unitItems);

                } while (c1.moveToNext());

            }
            c1.close();
        }

        Cls_UnitItems_Adapter cls_unitItems_adapter = new Cls_UnitItems_Adapter(
                context , cls_unitItemses);

        sp_unite.setAdapter(cls_unitItems_adapter);

        if (cls_unitItemses.size() > 0) {
            Cls_UnitItems o = (Cls_UnitItems) sp_unite.getItemAtPosition(0);
            UnitNo = o.getUnitno().toString();
            UnitName = o.getUnitDesc().toString();
            Operand = o.getOperand().toString();
            min = Float.valueOf(o.getMin());
        }
    }

    @Override
    public void onClick(View v) {
        HidKeybad();

        if (v == cancel) {
            this.dismiss();
        }
        else if (v == btn_get_Cust_Qty) {
            if(accno.equalsIgnoreCase("")){
                new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setContentText("الرجاء تحديد العميل أولا")
                        .setCustomImage(R.drawable.error_new)
                        .setConfirmText("رجــــوع")
                        .show();
                return;
            }
            if(ItemNo.equalsIgnoreCase("")){
                new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setContentText("الرجاء تحديد المادة أولا")
                        .setCustomImage(R.drawable.error_new)
                        .setConfirmText("رجــــوع")
                        .show();
                return;
            }


            GetCustomerQty();
        }


        else if (v == add) {
            EditText Price = (EditText) form.findViewById(R.id.et_price);
            final EditText final_P = (EditText) form.findViewById(R.id.et_price);
            EditText qty = (EditText) form.findViewById(R.id.et_qty);
            EditText tax = (EditText) form.findViewById(R.id.et_tax);

            EditText bo = (EditText) form.findViewById(R.id.et_bo);
            // Spinner sp_unite = (Spinner)form.findViewById(R.id.sp_units);
            EditText bounce = (EditText) form.findViewById(R.id.et_bo);
            EditText disc_per = (EditText) form.findViewById(R.id.et_disc_per);
            EditText disc_Amt = (EditText) form.findViewById(R.id.et_dis_Amt);

            EditText et_Discount = (EditText) form.findViewById(R.id.et_Discount);
            TextView tv_qty_perc = (TextView) form.findViewById(R.id.tv_qty_perc);
            TextView tv_StoreQty = (TextView) form.findViewById(R.id.tv_StoreQty);


            EditText net_total = (EditText) form.findViewById(R.id.et_totl);

            Double PriceAftertAx = 0.0;
            net_total.setError(null);
            // net_total.clearFocus();
            Price.setError(null);
            //  net_total.setError(null);
            qty.setError(null);
            tax.setError(null);

            Price.setError(null);
            net_total.setError(null);
            qty.setError(null);
            tax.setError(null);

/*
            if (Price.getText().toString().length() > 0 && SToD(Price.getText().toString()) > 0 && (SToD(Price.getText().toString()) < min_price)) {

                AlertDialog alertDialog = new AlertDialog.Builder(
                        context()).create();
                alertDialog.setTitle("الحد الادني لبيع المادة ");

                alertDialog.setIcon(R.drawable.delete);
                alertDialog.setMessage("لقد تجاوزت الحد الادنى للسعر");//+ "   " + String.valueOf(min_price));
                // Setting OK Button
                alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //   final_P.setText(min_price.toString());


                    }
                });

                // Showing Alert Message
                alertDialog.show();
                return;
            }*/



            // if (SToD(tv_qty_perc.getText().toString()) > 100 || SToD(tv_StoreQty.getText().toString()) <= 0) {



            //  if (SToD(qty.getText().toString()) >= SToD(et_StoreQty.getText().toString())) {
            //      qty.setError(getResources().getText(R.string.EnterValue));
            //     qty.requestFocus();
            //    return;
            // }




            if (SToD(net_total.getText().toString()) <= 0) {
                net_total.setError("الرجاء ادخال القيمة");
                net_total.requestFocus();
                return;
            }


            if (SToD(qty.getText().toString()) >= 9000) {
                qty.setError(getResources().getText(R.string.EnterValue));
                qty.requestFocus();
                return;
            }

            if ((SToD(Price.getText().toString()) == 100000) || Price.getText().toString().length() > 10 || (SToD(Price.getText().toString())).toString().contains("E")) {
                qty.setError(getResources().getText(R.string.EnterValue));
                Price.requestFocus();
                return;
            }
            if (Price.getText().toString().length() == 0) {
                qty.setError(getResources().getText(R.string.EnterValue));
                Price.requestFocus();
                return;
            }

            if (qty.getText().toString().length() == 0) {
                qty.setError(getResources().getText(R.string.EnterValue));
                qty.requestFocus();
                return;
            }
            if (tax.getText().toString().length() == 0) {
                qty.setError(getResources().getText(R.string.EnterValue));
                tax.requestFocus();
                return;
            }


            if (ItemNo.toString().length() == 0) {

                return;
            }


            // PriceAftertAx=     (SToD(Price.getText().toString()) / ((SToD(tax.getText().toString()) / 100) + 1));
            // Price.setText    (  String.valueOf(  SToD(  PriceAftertAx.toString())));


         /*   if (SToD(et_StoreQty.getText().toString()) < ( SToD(qty.getText().toString())+ SToD(bounce.getText().toString()  ))) {

                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                        context ).create();
                alertDialog.setTitle("طلب البيع");
                alertDialog.setMessage("الكمية المطلوبة غير متوفرة");            // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.error_new);
                alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();


               // qty.setError(getResources().getText(R.string.EnterValue));
                qty.requestFocus();
                return;
            }*/

            if (getArguments().getString("Scr") == "po") {
                ((OrdersItems1) context ).Save_List(ItemNo, Price.getText().toString().replace(",", ""), qty.getText().toString().replace(",", ""), tax.getText().toString().replace(",", ""), UnitNo, disc_per.getText().toString().replace(",", ""), bounce.getText().toString().replace(",", ""), str, UnitName, disc_Amt.getText().toString().replace(",", ""));
                ItemCount=((OrdersItems1) context ).GetItemCount();
                et_ItemCount.setText(ItemCount+"");
            }

            try {
                FillBouncePlane("-1");
                FillExpireDate("-1");
                min = 0;
                Price.requestFocus();
                Price.setText("0");
                qty.setText("");
                tax.setText("");
                bo.setText("");

                disc_per.setText("0");
                disc_Amt.setText("0");
                net_total.setText("0");
                et_StoreQty.setText("0");
                ItemNo = "";
                UnitNo = "";
                Operand = "";
                tv_qty_perc.setText("");
                tv_StoreQty.setText("");
                et_Discount.setText("");
                //rad_Amt.setChecked(true);

            } catch (Exception e) {
                e.printStackTrace();
            }

            EditText et_Search_filter = (EditText) form.findViewById(R.id.et_Search_filter);
            if (!et_Search_filter.getText().toString().equalsIgnoreCase(""))
                et_Search_filter.setText("");
        }

    }
    private void GetCustomerQty(){
        CustomerQty_List=new ArrayList<Cls_GetCustomerQty>() ;
        CustomerQty_List.clear();
        String q;
        tv = new TextView(context);
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
        final Handler _handler = new Handler();
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setMessage("  الرجاء الانتظار ..." + "  العمل جاري على استرجاع البيانات  ");
        tv.setText("سحوبات العميل");
        progressDialog.setCustomTitle(tv);
        progressDialog.setProgressDrawable(greenProgressbar);
        progressDialog.show();
        Cls_GetCustomerQty obj;
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallWebServices ws = new CallWebServices(context);
                ws.GetCustomerQtyByItem(accno,ItemNo,ed_Top.getText().toString());
                try {
                    Integer i;
                    String q;
                    if (We_Result.ID > 0) {
                        JSONObject js = new JSONObject(We_Result.Msg);
                        JSONArray js_INVOICEID = js.getJSONArray("INVOICEID");
                        JSONArray js_INVOICEDATE = js.getJSONArray("INVOICEDATE");
                        JSONArray js_ITEMID = js.getJSONArray("ITEMID");
                        JSONArray js_SALESQTY = js.getJSONArray("SALESQTY");
                        JSONArray js_BOUNCE = js.getJSONArray("BOUNCE");
                        JSONArray js_INVENTBATCHID= js.getJSONArray("INVENTBATCHID");
                        JSONArray js_SALESPRICE= js.getJSONArray("SALESPRICE");


                        for (i = 0; i < js_INVOICEID.length(); i++) {
                            cls_getCustomerQty = new Cls_GetCustomerQty();
                            cls_getCustomerQty.setINVOICEID(js_INVOICEID.get(i).toString());
                            cls_getCustomerQty.setINVOICEDATE(js_INVOICEDATE.get(i).toString());
                            cls_getCustomerQty.setITEMID(js_ITEMID.get(i).toString());
                            cls_getCustomerQty.setSALESQTY(js_SALESQTY.get(i).toString());
                            cls_getCustomerQty.setBOUNCE(js_BOUNCE.get(i).toString());
                            cls_getCustomerQty.setBATCHID(js_INVENTBATCHID.get(i).toString());


                            CustomerQty_List.add(cls_getCustomerQty);

                            progressDialog.setMax(js_INVOICEID.length());
                            progressDialog.incrementProgressBy(1);
                            try {
                                if (progressDialog.getProgress() == progressDialog.getMax()) {
                                    progressDialog.dismiss();
                                }
                            }catch (Exception ds){}
                        }
                        final int total = i;
                        _handler.post(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                ShowCustQtyList();
                            }
                        });
                    }else {
                        _handler.post(new Runnable() {
                            public void run() {
                                try {
                                    progressDialog.dismiss();
                                    AlertDialog alertDialog = new AlertDialog.Builder(
                                            context).create();
                                    alertDialog.setTitle("كميات العميل");
                                    alertDialog.setMessage("لا يوجد بيانات ");
                                    alertDialog.setIcon(R.drawable.error_new);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    alertDialog.show();
                                }catch (Exception df){}

                            }
                        });
                    }
                }catch (final Exception e) {
                    progressDialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {
                            try {
                                progressDialog.dismiss();
                            }catch (Exception ds){}

                        }
                    });
                }
            }
        }).start();
    }
    private  void ShowCustQtyList(){
     /*     LstCustQty.setAdapter(null);
        Cls_CustQty_Adapter contactListAdapter = new Cls_CustQty_Adapter(
               context, CustomerQty_List);
        LstCustQty.setAdapter(contactListAdapter);*/
        HidKeybad();
    }
    public void onListItemClick(ListView l, View v, int position, long id) {


        // Set the item as checked to be highlighted
        items_Lsit.setItemChecked(position, true);
        v.setBackgroundColor(Color.BLUE);

        //conversationAdapter.notifyDataSetChanged();

    }


    public void CalcBounce() {

        EditText tv_Bounce = (EditText) form.findViewById(R.id.et_bo);
        EditText qty = (EditText) form.findViewById(R.id.et_qty);
        if (!qty.getText().toString().equalsIgnoreCase("")) {

            try {
                String q = "Select Bounce,AllowRepeats,Qty from Item_Bounce where  CAST(Item_Bounce.Qty as INTEGER ) <=" + Integer.parseInt(qty.getText().toString()) + "  AND Item_No='" + ItemNo + "'  ORDER BY CAST(Item_Bounce.priority as INTEGER )   asc   Limit 1 ";
                Cursor c1 = sqlHandler.selectQuery(q);
                tv_Bounce.setText("0");
                String Bounc, AllowRepeat, Contitions;
                AllowRepeat = "0";
                Contitions = "0";
                Bounc = "0";
                int f;
                if (c1 != null && c1.getCount() != 0) {
                    if (c1.moveToFirst()) {
                        do {
                            Bounc = c1.getString(c1.getColumnIndex("Bounce"));
                            AllowRepeat = c1.getString(c1.getColumnIndex("AllowRepeats"));
                            Contitions = c1.getString(c1.getColumnIndex("Qty"));
                        } while (c1.moveToNext());
                    }
                    c1.close();
                }

                if (AllowRepeat.equalsIgnoreCase("1")) {
                    f =Integer.parseInt(qty.getText().toString())/ Integer.parseInt(Contitions)   ;
                } else {
                    f = 1;
                }
                if (!Bounc.equalsIgnoreCase("0")) {
                    tv_Bounce.setText((f * Integer.parseInt(Bounc + "")) + "");
                }
            } catch(Exception ex){
                Toast.makeText(context , ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void get_total() {
        Double Perc = 0.0;
        EditText Price = (EditText) form.findViewById(R.id.et_price);
        EditText qty = (EditText) form.findViewById(R.id.et_qty);
        EditText tax = (EditText) form.findViewById(R.id.et_tax);
        EditText dis = (EditText) form.findViewById(R.id.et_disc_per);
        EditText bo = (EditText) form.findViewById(R.id.et_bo);
        EditText net_total = (EditText) form.findViewById(R.id.et_totl);


        String str_p, str_q;


        TextView tv_qty_perc = (TextView) form.findViewById(R.id.tv_qty_perc);
        TextView tv_StoreQty = (TextView) form.findViewById(R.id.tv_StoreQty);


        str_p = Price.getText().toString();
        str_q = qty.getText().toString();

        if (Price.getText().toString().length() == 0) {
            str_p = "0";
        }

        if (qty.getText().toString().length() == 0) {
            str_q = "0";
        }
         /* if( dis.getText().toString().length() == 0 ) {
            dis.setText("0");
        }
        if( bo.getText().toString().length() == 0 ) {
            bo.setText("0");
        }*/
        Double p = SToD(str_p);
        Double q = SToD(str_q);

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;

        net_total.setText(String.valueOf(df.format(p * q)).replace(",", ""));

    }

    private void GetQtyPerc() {

        Double Perc = 0.0;
        EditText qty = (EditText) form.findViewById(R.id.et_qty);
        EditText bo = (EditText) form.findViewById(R.id.et_bo);
        TextView tv_qty_perc = (TextView) form.findViewById(R.id.tv_qty_perc);
        TextView tv_StoreQty = (TextView) form.findViewById(R.id.tv_StoreQty);

        if (tv_StoreQty.getText().toString() == "") {
            Perc = 0.0;
        } else {
            if (SToD(tv_StoreQty.getText().toString()) == 0) {
                Perc = 0.0;
            } else {
                Perc = (SToD(qty.getText().toString()) + SToD(bo.getText().toString())) / SToD(tv_StoreQty.getText().toString());
            }
        }
        Perc = (Perc * 100);
        tv_qty_perc.setText(String.valueOf(SToD(Perc.toString())));

    }

    private  void HidKeybad(){
        try{
            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }catch (Exception ex){}
    }
}

