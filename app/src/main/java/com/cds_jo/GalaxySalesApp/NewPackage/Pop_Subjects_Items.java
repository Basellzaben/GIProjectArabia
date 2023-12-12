package com.cds_jo.GalaxySalesApp.NewPackage;

import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.DoctorReportActivity;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.assist.Cls_Deptf;
import com.cds_jo.GalaxySalesApp.assist.Cls_Deptf_adapter;
import com.cds_jo.GalaxySalesApp.assist.Cls_Invf;
import com.cds_jo.GalaxySalesApp.assist.Cls_Invf_Adapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import Methdes.MyTextView;


public class Pop_Subjects_Items extends DialogFragment implements View.OnClickListener {
    public static final String CALCULATOR_PACKAGE = "com.android.calculator2";
    public static final String CALCULATOR_CLASS = "com.android.calculator2.Calculator";
    View form;
    Button add, cancel;
    ImageButton OpenCal;
    ListView items_Lsit;
    ListView items_Lsit1;
    TextView itemnm;
    TextView Store_Qty;
    public String ItemNo = "";
    SqlHandler sqlHandler;
    float min = 0;
    int ResultCode;

    EditText filter;
    ImageButton btn_filter_search;
    String UnitNo = "";
    String Operand = "";
    String pass = "";
    String UnitName = "";
    String str   = "";

    MyTextView Item_Name;

    int AllowSalInvMinus;
    EditText input;

    @Override
    public void onStart(){
        super.onStart();
        if (getDialog() == null)
            return;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom( getDialog().getWindow().getAttributes());
        lp.width =WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
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

        form = inflater.inflate(R.layout.fragment_pop__subjects__items, container, false);
        GloblaVar.tabno="7";
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.TOP|Gravity.RIGHT);

        getDialog().setTitle(String.valueOf(getResources().getString(R.string.title_item_list)));
        add = (Button) form.findViewById(R.id.btn_add_item);
        add.setOnClickListener(this);
        cancel = (Button) form.findViewById(R.id.btn_cancel_item);

        FillDeptf();
        cancel.setOnClickListener(this);




        items_Lsit = (ListView) form.findViewById(R.id.listView2);
        items_Lsit1 = (ListView) form.findViewById(R.id.listView3);



        Item_Name = (MyTextView) form.findViewById(R.id.Item_Name);


        sqlHandler = new SqlHandler(getActivity());


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
                // Toast.makeText(getActivity(),s.toString(),Toast.LENGTH_SHORT).show();
                FillItems();

            }
        });


        FillItems();



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





        items_Lsit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                try {
                    Double Qty = 0.0;
                    for (int i = 0; i < items_Lsit.getChildCount(); i++) {
                        View listItem = items_Lsit.getChildAt(i);
                        if (i % 2 == 0)
                            listItem.setBackgroundColor(Color.WHITE);
                        if (i % 2 == 1)
                            listItem.setBackgroundColor(getActivity().getResources().getColor(R.color.Gray2));
                    }
                    arg1.setBackgroundColor(Color.GRAY);

                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                    DecimalFormat df = (DecimalFormat) nf;

                    Cls_Invf o = (Cls_Invf) items_Lsit.getItemAtPosition(position);


                    GloblaVar.checkkey=String.valueOf(o.getItem_No());


                    str = (String) o.getItem_Name();
                    Item_Name.setText(str);

                    getDialog().setTitle(str);

                    ItemNo = o.getItem_No().toString();

                    FillKeys( ((Cls_Invf) items_Lsit.getItemAtPosition(position)).getItem_No().toString());


                } catch (Exception ex) {
                    Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }




            }
        });


        this.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        return form;
    }



    private void FillDeptf() {
        final Spinner sp_items_cat = (Spinner) form.findViewById(R.id.sp_item_cat);
        sqlHandler = new SqlHandler(getActivity());

        String query = "Select  ItemNo , ItemNameA,ItemNameE  from itemsn";
        ArrayList<Cls_Deptf> cls_deptfs = new ArrayList<Cls_Deptf>();
        cls_deptfs.clear();

        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Deptf clsDeptfs = new Cls_Deptf();

                    clsDeptfs.setType_No(c1.getString(c1
                            .getColumnIndex("ItemNo")));
                    LocaleHelper localeHelper=new LocaleHelper();
                    if(LocaleHelper.getlanguage(getActivity()).equals("ar"))
                    {     clsDeptfs.setType_Name(c1.getString(c1
                            .getColumnIndex("ItemNameA")));;}
                    else {
                        clsDeptfs.setType_Name(c1.getString(c1
                                .getColumnIndex("ItemNameE")));
                    }

                    cls_deptfs.add(clsDeptfs);

                } while (c1.moveToNext());

            }
            c1.close();
        }

        Cls_Deptf_adapter cls_unitItems_adapter = new Cls_Deptf_adapter(
                getActivity(), cls_deptfs);

        sp_items_cat.setAdapter(cls_unitItems_adapter);
    }



    private void FillKeys(String itemid) {
        filter = (EditText) form.findViewById(R.id.et_Search_filter);
        String query = "";
        sqlHandler = new SqlHandler(getActivity());


        Bundle bundle = this.getArguments();



        //    String KeyId=DB.GetValue(getActivity(),"ItemsKeysN","ItemId","ItemId='"+itemid+"'");

        //    query = "Select  ItemNo, DescrA  from AreasN where ItemNo='"+KeyId+"' and TableNo='7'";
        query="  select * from ItemsKeysN i left join AreasN u on  i.KeyId= u.Id where i.ItemId = '"+itemid+"'";
        query="select * from ItemsKeysN  where ItemId = '"+itemid+"'";

        LocaleHelper localeHelper=new LocaleHelper();
        ArrayList<keys_modle> cls_invf_List = new ArrayList<keys_modle>();
        cls_invf_List.clear();
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    keys_modle keys_modle = new keys_modle();

                    String f;
                    if(localeHelper.getlanguage(getActivity()).equals("ar"))
                    { f= DB.GetValue(getActivity(),"AreasN","DescrA","Id ='"+c1.getString(c1
                            .getColumnIndex("KeyId"))+"' and TableNo='7'");
                    }else{
                        f=DB.GetValue(getActivity(),"AreasN","DescrE","Id ='"+c1.getString(c1
                                .getColumnIndex("KeyId"))+"' and TableNo='7'");
                    }
                    keys_modle.setKey(/*c1.getString(c1
                            .getColumnIndex("DescrA"))*/f);
                    keys_modle.setItemNo(c1.getString(c1
                            .getColumnIndex("KeyId")));
                    cls_invf_List.add(keys_modle);

                } while (c1.moveToNext());

            }
            c1.close();
        }

        Cls_Keys_adapter cls_invf_adapter = new Cls_Keys_adapter(
                getActivity(), cls_invf_List);
        items_Lsit1.setAdapter(cls_invf_adapter);


    }



    private void FillItems() {
        filter = (EditText) form.findViewById(R.id.et_Search_filter);
        String query = "";
        sqlHandler = new SqlHandler(getActivity());


        Bundle bundle = this.getArguments();

        AllowSalInvMinus = -1;

        if (getArguments().getString("Scr") == "DR") {

            //     String query = "Select  ItemNo , ItemNameA  from itemsn";

            if (filter.getText().toString().equals("")) {
                query = "Select distinct itemsn.Id , itemsn.ItemNameA, itemsn.ItemNameE ,itemsn.Price, itemsn.TaxId   from itemsn  ";

            } else {
                query = "Select distinct  itemsn.Id , itemsn.ItemNameA, itemsn.ItemNameE,itemsn.Price, itemsn.TaxId   from itemsn " +
                        "  where Id  like '%" + filter.getText().toString() + "%'  or  ItemNameA like '%" + filter.getText().toString() + "%' or  ItemNameE like '%" + filter.getText().toString() + "%' ";
            }


            Spinner item_cat = (Spinner) form.findViewById(R.id.sp_item_cat);
            Integer indexValue = item_cat.getSelectedItemPosition();

            if (indexValue > 0) {

                Cls_Deptf o = (Cls_Deptf) item_cat.getItemAtPosition(indexValue);

                // query = query + "and    Type_No = '" + o.getType_No().toString() + "'";

            }


        }


        Spinner item_cat = (Spinner) form.findViewById(R.id.sp_item_cat);
        Integer indexValue = item_cat.getSelectedItemPosition();

        if (indexValue > 0) {

            Cls_Deptf o = (Cls_Deptf) item_cat.getItemAtPosition(indexValue);

            //query = query + "and    Type_No = '" + o.getType_No().toString() + "'";

        }





        ArrayList<Cls_Invf> cls_invf_List = new ArrayList<Cls_Invf>();
        cls_invf_List.clear();
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Invf cls_invf = new Cls_Invf();

                    cls_invf.setItem_No(c1.getString(c1
                            .getColumnIndex("Id")));

                    LocaleHelper localeHelper=new LocaleHelper();
                    if(LocaleHelper.getlanguage(getActivity()).equals("ar"))
                    {      cls_invf.setItem_Name(c1.getString(c1
                            .getColumnIndex("ItemNameA")));}
                    else {
                        cls_invf.setItem_Name(c1.getString(c1
                                .getColumnIndex("ItemNameE")));

                    }

                    cls_invf.setPrice(c1.getString(c1
                            .getColumnIndex("Price")));
                    cls_invf.setTax(c1.getString(c1
                            .getColumnIndex("TaxId")));
                    cls_invf_List.add(cls_invf);

                } while (c1.moveToNext());

            }
            c1.close();
        }

        Cls_Invf_Adapter cls_invf_adapter = new Cls_Invf_Adapter(
                getActivity(), cls_invf_List);
        items_Lsit.setAdapter(cls_invf_adapter);


    }

    @Override
    public void onClick(View v) {

        if (v == OpenCal) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setComponent(new ComponentName(
                    CALCULATOR_PACKAGE,
                    CALCULATOR_CLASS));

            try {

                startActivity(intent);
            } catch (Exception noSuchActivity) {


            }

        }


        EditText et_Search_filter = (EditText) form.findViewById(R.id.et_Search_filter);
        if (v == cancel) {
            this.dismiss();
        }
        if (v == add) {
            et_Search_filter.requestFocus();
            if (ItemNo.toString().length() == 0) {
                Toast.makeText(getActivity(), String.valueOf(getResources().getString(R.string.Pleaseselectaitem)), Toast.LENGTH_SHORT).show();
                return;
            }




            if (getArguments().getString("Scr") == "DR") {

                String item=DB.GetValue(getActivity(),"ItemsCheck","KeyId"," TransNo='"+getArguments().getString("transno")+"' and ItemNo='"+ItemNo+"'");
              /*  if(item.equals("-1"))
                    Toast.makeText(getActivity(),String.valueOf(getResources().getString(R.string.mustselectkey)),Toast.LENGTH_SHORT).show();
             else*/
                ((DoctorReportActivity) getActivity()).SaveSubject(ItemNo, str );

            }


            try {
                min = 0;


                ItemNo = "";
                UnitNo = "";
                Operand = "";


                for (int v1 = 0; v1 < items_Lsit.getChildCount(); v1++) {
                    View listItem = items_Lsit.getChildAt(v1);
                    if (v1 % 2 == 0)
                        listItem.setBackgroundColor(Color.WHITE);

                    if (v1 % 2 == 1)
                        listItem.setBackgroundColor(getActivity().getResources().getColor(R.color.Gray2));
                }
                Item_Name.setText("");


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }



}


