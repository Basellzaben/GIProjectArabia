package com.cds_jo.GalaxySalesApp.TQNew;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.Cls_Qty_Batch;
import com.cds_jo.GalaxySalesApp.Cls_Sal_InvItems;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.assist.Cls_Deptf;
import com.cds_jo.GalaxySalesApp.assist.Cls_Deptf_adapter;
import com.cds_jo.GalaxySalesApp.assist.Cls_Invf;
import com.cds_jo.GalaxySalesApp.assist.Cls_UnitItems;
import com.cds_jo.GalaxySalesApp.assist.Cls_UnitItems_Adapter;

import java.util.ArrayList;
import java.util.List;

public class Pop_ItemSearchBatch extends DialogFragment implements View.OnClickListener  {
    View form;
    Context context;
    Button btn_GetItemsFromBatchs;
    ArrayList<Cls_Qty_Batch> cls_qty_batches;
    EditText filter;
    SqlHandler sqlHandler;
    List<Cls_Sal_InvItems> UpdateItem;
    public String ItemNo = "";
    ListView items_Lsit,lvItemQty;
    EditText et_Batch;
    public ExampleDialogListener listener;
    public interface OnInputSelected{
        void sendInput(String input);
    }
    public OnInputSelected mOnInputSelected;
    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getDialog().getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(lp);

        getDialog().getWindow().setWindowAnimations(0);

    }
    @Override
    public void onClick(View v) {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savestate) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        form = inflater.inflate(R.layout._pop_searchitem, container, false);
        context = getActivity();
        getDialog().setTitle("Galaxy");
//        btn_GetItemsFromBatchs.setOnClickListener(this);
        cls_qty_batches = new ArrayList<Cls_Qty_Batch>();
        filter = (EditText) form.findViewById(R.id.et_Search_filter);
        filter.setInputType(InputType.TYPE_CLASS_TEXT);
        filter.requestFocus();
        FillItems();
        et_Batch = (EditText) form.findViewById(R.id.et_Batch);
        et_Batch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (et_Batch.getText().toString().trim().length()>0) {
                    Autocomplate();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        lvItemQty = (ListView) form.findViewById(R.id.lvItemQty);
        FillDeptf();
        lvItemQty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                try {

                    //   tv_ACC.setText(ACC);


                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    //   tv_ACC.setText();
                    //    PopShowInvoiceFromBatch.setcustid(preferences.getString("OrderCustNm", ""));

                    Cls_Cust_Item_Qty o = (Cls_Cust_Item_Qty) lvItemQty.getItemAtPosition(position);
                   /* Bundle bundle = new Bundle();
                    bundle.putString("getItem_No",  o.getItem_No());
                    FragmentManager Manager = getFragmentManager();
                    Pop_ItemSearchBatch obj = new Pop_ItemSearchBatch();
                    obj.setArguments(bundle);
                    obj.show(Manager, null);
*/
                    Bundle bundle = new Bundle();
                    bundle.putString("ACC",getArguments().getString("ACC"));

                    //   bundle.putString("postion",o.getItem_No());
                    FragmentManager Manager = ((Activity) context).getFragmentManager();
                    PopShowInvoiceFromBatch popShowInvoiceFromBatch2 = new PopShowInvoiceFromBatch();
                    popShowInvoiceFromBatch2.setArguments(bundle);
                    popShowInvoiceFromBatch2.show(Manager, null);

                    Pop_ItemSearchBatch.this.dismiss();
                    // getActivity().getFragmentManager().beginTransaction().remove(Pop_ItemSearchBatch.this).commit();

                    globalVal.batch="";
                    globalVal.batch=o.getItem_No();
                    //  globalVal.idcus=preferences.getString("OrderCustNm", "");




                    //  PopShowInvoiceFromBatch b =new PopShowInvoiceFromBatch();
                    /*   b.SetData(o.getTransTypeDesc());
                     */






                }catch (Exception ffd){
                    Toast.makeText(getActivity(),ffd.getMessage(),Toast.LENGTH_LONG).show();
                }


            }

        });

        items_Lsit = (ListView) form.findViewById(R.id.listView2);
        items_Lsit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Cls_Invf1 o = (Cls_Invf1) items_Lsit.getItemAtPosition(position);
                ItemNo = o.getItem_No().toString();

                GET_ALL_ITEM_BATCH(ItemNo);


                if(getArguments().getString("check").equals("true")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Scr", "po");
                    bundle.putString("ACC", getArguments().getString("ACC"));
                    bundle.putString("NO", ItemNo);
                    FragmentManager Manager = getFragmentManager();
                    PopShowInvoiceFromBatch obj = new PopShowInvoiceFromBatch();
                    obj.setArguments(bundle);
                    obj.show(Manager, null);

                    Pop_ItemSearchBatch.this.dismiss();
                /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("ItemNo",ItemNo);
                editor.apply();*/
                }
            }
        });
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

                FillItems();

            }
        });
        return form;

    }

    private void Autocomplate() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        final ArrayList<Cls_Cust_Item_Qty> cls_cust_item_qties = new ArrayList<Cls_Cust_Item_Qty>();
        Cls_Cust_Item_Qty obj;
        String q = "SELECT Distinct   BatchNo FROM   Items_Batches WHERE   myear = '"+sharedPreferences.getString("myear","")+"' and BatchNo like '%" + et_Batch.getText().toString() + "%' limit 20";

        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    obj = new Cls_Cust_Item_Qty();
                    try {

                        obj.setItem_No(c1.getString(c1.getColumnIndex("BatchNo")));
                        obj.setTransType("10");
                        obj.setTransTypeDesc(c1.getString(c1.getColumnIndex("BatchNo")));
                        cls_cust_item_qties.add(obj);
                    } catch (Exception t) {
                    }
                } while (c1.moveToNext());

            }
            c1.close();
        }

        Cls_Cust_Qty_Adapter cls_acc_report_adapter = new Cls_Cust_Qty_Adapter(
                context, cls_cust_item_qties);
        lvItemQty.setAdapter(cls_acc_report_adapter);
    }
    private void FillItems() {
        filter = (EditText) form.findViewById(R.id.et_Search_filter);
        String query = "";
        sqlHandler = new SqlHandler(context);


        Bundle bundle = this.getArguments();
//        UpdateItem = (List<Cls_Sal_InvItems>) bundle.getSerializable("List");
//        bundle.putString("List");
        if (UpdateItem != null) {

            if (UpdateItem.size() > 0) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String s = UpdateItem.get(0).getno().toString();
                query = "Select  distinct invf.Item_No , invf.Item_Name,invf.Price, invf.tax   from invf    where   invf.Item_No ='" + UpdateItem.get(0).getno().toString() + "'and myear = '"+sharedPreferences.getString("myear","")+"'";
                EditText Price = (EditText) form.findViewById(R.id.et_price);
                EditText qty = (EditText) form.findViewById(R.id.et_qty);
                EditText tax = (EditText) form.findViewById(R.id.et_tax);

                EditText bo = (EditText) form.findViewById(R.id.et_bo);
                // Spinner sp_unite = (Spinner)form.findViewById(R.id.sp_units);
                EditText bounce = (EditText) form.findViewById(R.id.et_bo);
                EditText disc_per = (EditText) form.findViewById(R.id.et_disc_per);
                EditText disc_Amt = (EditText) form.findViewById(R.id.et_dis_Amt);

                EditText et_Discount = (EditText) form.findViewById(R.id.et_Discount);


                EditText net_total = (EditText) form.findViewById(R.id.et_totl);
                ItemNo = UpdateItem.get(0).getno();

                Cls_UnitItems cls_unitItems = new Cls_UnitItems();
                Spinner sp_unite = (Spinner) form.findViewById(R.id.sp_units);

                Cls_UnitItems_Adapter UnitItems = (Cls_UnitItems_Adapter) sp_unite.getAdapter();
                for (int i = 0; i < UnitItems.getCount(); i++) {
                    cls_unitItems = (Cls_UnitItems) UnitItems.getItem(i);


                    if (cls_unitItems.getUnitno().equals(UpdateItem.get(0).getUnite())) {
                        sp_unite.setSelection(i);
                        break;
                    }
                }

            }
        } else {


            if (getArguments().getString("Scr") == "po") {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                if (filter.getText().toString().equals("")) {
                    query = "Select distinct invf.Item_No , invf.Item_Name,invf.Price, invf.tax   from invf  " +
                            "   where 1=1";
                } else {
                    query = "Select distinct  invf.Item_No , invf.Item_Name,invf.Price, invf.tax from  invf " +
                            "    where Item_Name  like '%" + filter.getText().toString() + "%'  or  Item_No like '%" + filter.getText().toString() + "%'";
                }

                Spinner item_cat = (Spinner) form.findViewById(R.id.sp_item_cat);
                Integer indexValue = item_cat.getSelectedItemPosition();

                if (indexValue > 0) {

                    Cls_Deptf o = (Cls_Deptf) item_cat.getItemAtPosition(indexValue);

                    query = query + "and    Type_No = '" + o.getType_No().toString() + "'";

                }


            }

        }

        Spinner item_cat = (Spinner) form.findViewById(R.id.sp_item_cat);
        Integer indexValue = item_cat.getSelectedItemPosition();

        if (indexValue > 0) {

            Cls_Deptf o = (Cls_Deptf) item_cat.getItemAtPosition(indexValue);

            query = query + "and    Type_No = '" + o.getType_No().toString() + "'";

        }


        ArrayList<Cls_Invf1> cls_invf_List = new ArrayList<Cls_Invf1>();
        cls_invf_List.clear();
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Invf1 cls_invf = new Cls_Invf1();

                    cls_invf.setItem_No(c1.getString(c1
                            .getColumnIndex("Item_No")));
                    cls_invf.setItem_Name(c1.getString(c1
                            .getColumnIndex("Item_Name")));
                    cls_invf.setPrice(c1.getString(c1
                            .getColumnIndex("Price")));
                    cls_invf.setTax(c1.getString(c1
                            .getColumnIndex("tax")));
                    cls_invf.setShowQty("0");

                    cls_invf_List.add(cls_invf);

                } while (c1.moveToNext());

            }
            c1.close();
        }
        items_Lsit = (ListView) form.findViewById(R.id.listView2);
        Cls_ReturnQty_Fill_List_Adapter cls_invf_adapter = new Cls_ReturnQty_Fill_List_Adapter(
                context, cls_invf_List);
        items_Lsit.setAdapter(cls_invf_adapter);


    }
    private void FillDeptf() {
        final Spinner sp_items_cat = (Spinner) form.findViewById(R.id.sp_item_cat);
        sqlHandler = new SqlHandler(context);

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
                context, cls_deptfs);

        sp_items_cat.setAdapter(cls_unitItems_adapter);
    }

    private  void GET_ALL_ITEM_BATCH (String item_no){
        final ArrayList<Cls_Cust_Item_Qty> cls_cust_item_qties = new ArrayList<Cls_Cust_Item_Qty>();
        Cls_Cust_Item_Qty obj;
        //String q = "SELECT  Distinct ITEMID, INVENTBATCHID FROM   Gi_CUSTINVOICETRANS  WHERE   INVENTBATCHID<>''  and CUSTACCOUNT='"+ACC+"'  AND   ITEMID = '"+item_no+"' ";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String q = "SELECT Distinct   BatchNo FROM   Items_Batches WHERE    Item_No ='"+item_no+"'   and  BatchNo like '%" + et_Batch.getText().toString() + "%' limit 20";



        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    obj = new Cls_Cust_Item_Qty();
                    try {

                        obj.setItem_No(c1.getString(c1.getColumnIndex("BatchNo")));
                        obj.setTransType("10");
                        obj.setTransTypeDesc(c1.getString(c1.getColumnIndex("BatchNo")));
                        cls_cust_item_qties.add(obj);
                    } catch (Exception t) {
                    }
                } while (c1.moveToNext());

            }
            c1.close();
        }

        Cls_Cust_Qty_Adapter cls_acc_report_adapter = new Cls_Cust_Qty_Adapter(
                context, cls_cust_item_qties);
        lvItemQty.setAdapter(cls_acc_report_adapter);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {

        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String Batch);
    }
}

