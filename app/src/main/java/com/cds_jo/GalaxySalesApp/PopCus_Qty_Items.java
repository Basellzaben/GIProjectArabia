
package com.cds_jo.GalaxySalesApp;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.Mentnis.AddItemmFragment;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.cds_jo.GalaxySalesApp.assist.Cls_Deptf;
import com.cds_jo.GalaxySalesApp.assist.Cls_Deptf_adapter;
import com.cds_jo.GalaxySalesApp.assist.Cls_Invf;
import com.cds_jo.GalaxySalesApp.assist.Cls_Invf_Adapter;
import com.cds_jo.GalaxySalesApp.assist.Cls_UnitItems;
import com.cds_jo.GalaxySalesApp.assist.Cls_UnitItems_Adapter;
import com.cds_jo.GalaxySalesApp.assist.ManAttenActivity;
import com.cds_jo.GalaxySalesApp.myelectomic.Pos_Ele_Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Hp on 07/02/2016.
 */

public class PopCus_Qty_Items extends DialogFragment implements View.OnClickListener  {
    View form ;
    ImageButton  OpenCal;
    Button add,cancel;
    ListView items_Lsit;
     String a,QTY2,Itemno2 ,ManNo2;
    public ProgressDialog loadingdialog;
    TextView itemnm;
    public String ItemNo5 = "";
    SqlHandler sqlHandler;
    ListView lstCat;
    int Type_No = -1;
    float min = 0 ;
    String FillMethod = "1";
    EditText filter   ;
    ImageButton btn_filter_search ;
    String UnitNo ="";
    String UnitName ="";
    String str= "";
      RadioButton rad_Per ;
       RadioButton rad_Amt;
    List<ContactListItems> UpdateItem ;
    public static final String CALCULATOR_PACKAGE ="com.android.calculator2";
    public static final String CALCULATOR_CLASS ="com.android.calculator2.Calculator";
    public void onStart() {
        super.onStart();

        // safety check
        if (getDialog() == null)
            return;

        int dialogWidth = WindowManager.LayoutParams.MATCH_PARENT;//340; // specify a value here
        int dialogHeight = WindowManager.LayoutParams.MATCH_PARENT;//400; // specify a value here
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);

    }
    @Override
    public View onCreateView( LayoutInflater inflater   , ViewGroup container  ,Bundle savestate){

        form =inflater.inflate(R.layout.popcustqty,container,false);

        getDialog().setTitle("Galaxy");
        add =(Button) form.findViewById(R.id.btn_add_item);
        add.setOnClickListener(this);
        cancel=(Button) form.findViewById(R.id.btn_cancel_item);
        FillDeptf();
        cancel.setOnClickListener(this);
        lstCat = (ListView) form.findViewById(R.id.lstCat);
        items_Lsit=(ListView) form.findViewById(R.id.listView2);
        final List<String> items_ls = new ArrayList<String>();
        final List<String> promotion_ls = new ArrayList<String>();

        EditText Price = (EditText)form.findViewById(R.id.et_price);
       final EditText qty = (EditText)form.findViewById(R.id.et_qty);

        sqlHandler =  new SqlHandler(getActivity());

        OpenCal=(ImageButton) form.findViewById(R.id.btn_OpenCal);
        OpenCal.setOnClickListener(this);
        qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    qty.setText("", TextView.BufferType.EDITABLE);
                }
            }

        });




        filter =    (EditText) form.findViewById(R.id.et_Search_filter);
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
        fillUnit("-1");

        final  Spinner item_cat = (Spinner)form.findViewById(R.id.sp_item_cat);
        lstCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Cls_Deptf o = (Cls_Deptf) lstCat.getItemAtPosition(position);
                Type_No = Integer.parseInt(o.getType_No());


                    FillItems();


            }
        });
        lstCat.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
        item_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               FillItems();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        final  Spinner sp_unite = (Spinner)form.findViewById(R.id.sp_units);

        sp_unite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Cls_UnitItems o = (Cls_UnitItems) sp_unite.getItemAtPosition(position);
                EditText Price = (EditText) form.findViewById(R.id.et_price);
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat df = (DecimalFormat)nf;



                Price.setText(String.valueOf(df.format(Double.valueOf(o.getPrice()))));


                UnitNo=o.getUnitno().toString();
                UnitName=o.getUnitDesc().toString();
                min=Float.valueOf(o.getMin());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

       items_Lsit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
               arg1.setBackgroundColor(Color.GRAY);
               NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
               DecimalFormat df = (DecimalFormat) nf;

               Cls_Invf o = (Cls_Invf) items_Lsit.getItemAtPosition(position);


               EditText et_qty = (EditText) form.findViewById(R.id.et_qty);
               et_qty.setText("1");


               str = (String) o.getItem_Name();//As you are using Default String Adapter
               // Toast.makeText(getActivity(),str,Toast.LENGTH_LONG).show();
               getDialog().setTitle(str);
               fillUnit(o.getItem_No().toString());
               ItemNo5 = o.getItem_No().toString();


           }
       });


        qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    qty.setText("", TextView.BufferType.EDITABLE);
                }
            }

        });



        if (getArguments().getString("Scr") == "CustQtyedit") {
            Cls_Invf o = (Cls_Invf) items_Lsit.getItemAtPosition(Integer.parseInt(getArguments().getString("itemid")));
            items_Lsit.setSelection(Integer.parseInt(getArguments().getString("itemid")));

            EditText et_qty = (EditText) form.findViewById(R.id.et_qty);
            et_qty.setText(getArguments().getString("qt"));


            str = (String) o.getItem_Name();//As you are using Default String Adapter
            // Toast.makeText(getActivity(),str,Toast.LENGTH_LONG).show();
            getDialog().setTitle(str);
            fillUnit(o.getItem_No().toString());
            ItemNo5 = o.getItem_No().toString();
        }


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
         filter.clearFocus();
        return  form;
    }

    private void FillDeptf() {
        //final Spinner sp_items_cat = (Spinner) form.findViewById(R.id.sp_item_cat);
        ListView lstCat = (ListView) form.findViewById(R.id.lstCat);
        sqlHandler = new SqlHandler(getActivity());
        String query = "";
        if (FillMethod.equalsIgnoreCase("1")) {
            query = "Select   distinct Type_No , Type_Name , etname from deptf where Type_No!='-1'";
        } else {
            query = "Select   distinct Type_No , Type_Name , etname from deptf";
        }

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
        }

        Cls_Deptf_adapter cls_unitItems_adapter = new Cls_Deptf_adapter(
                getActivity(), cls_deptfs);

        //sp_items_cat.setAdapter(cls_unitItems_adapter);
        lstCat.setAdapter(cls_unitItems_adapter);
    }

   /* private void   FillDeptf(){
        final Spinner sp_items_cat = (Spinner) form.findViewById(R.id.sp_item_cat);
        sqlHandler = new SqlHandler(getActivity());

        String  query = "Select  Type_No , Type_Name , etname from deptf";
        ArrayList<Cls_Deptf>  cls_deptfs = new ArrayList<Cls_Deptf>();
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
        }

        Cls_Deptf_adapter cls_unitItems_adapter = new Cls_Deptf_adapter(
                getActivity(), cls_deptfs);

        sp_items_cat.setAdapter(cls_unitItems_adapter);
    }*/
    private  void FillItems() {


        filter = (EditText) form.findViewById(R.id.et_Search_filter);
        String query="";
        sqlHandler = new SqlHandler(getActivity());

        Bundle bundle = this.getArguments();
        UpdateItem = (List<ContactListItems>) bundle.getSerializable("List");
        if (UpdateItem != null && UpdateItem.size() > 0) {

            query = "Select * from invf   where   invf.Item_No ='"+UpdateItem.get(0).getno().toString()+"'";
            str=UpdateItem.get(0).getName();
            getDialog().setTitle(str);
              EditText qty = (EditText)form.findViewById(R.id.et_qty);
            qty.setText(UpdateItem.get(0).getQty());
            ItemNo5 = UpdateItem.get(0).getno();
            fillUnit(UpdateItem.get(0).getno());
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

    else{
        if (filter.getText().toString().equals("")) {
            query = "Select * from invf where 1=1 ";
        } else {
            query = "Select * from invf where Item_Name  like '%" + filter.getText().toString() + "%'  or  Item_No like '%" + filter.getText().toString() + "%'  ";
        }
        }
        Spinner item_cat = (Spinner) form.findViewById(R.id.sp_item_cat);
        Integer indexValue = item_cat.getSelectedItemPosition();
        if (Type_No > 0) {
            query = query + "and    Type_No = '" + Type_No + "" + "'";
        }
        if (indexValue > 0) {

            Cls_Deptf o = (Cls_Deptf) item_cat.getItemAtPosition(indexValue);

            query = query +"and    Type_No = '"+ o.getType_No().toString()+"'";

        }
      /*  AlertView.showAlert( query,
                getResources().getString(R.string.dev_check_msg), getActivity());*/
        ArrayList<Cls_Invf> cls_invf_List = new ArrayList<Cls_Invf>();
      if (GlobaleVar.TaxSts == 0 ){

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
    }}
      else
      {
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

                      cls_invf_List.add(cls_invf);

                  } while (c1.moveToNext());

              }
          }
      }

    Cls_Invf_Adapter cls_invf_adapter = new Cls_Invf_Adapter(
            getActivity(), cls_invf_List);
    items_Lsit.setAdapter(cls_invf_adapter);





}

    public  void fillUnit(String item_no){


        Spinner sp_unite = (Spinner)form.findViewById(R.id.sp_units);
        sqlHandler = new SqlHandler(getActivity());


        String Strorder = "";
        if (ComInfo.ComNo == 3) { // salasel
            Strorder = " UnitItems.unitno desc ";
        } else if (ComInfo.ComNo == 2) {
            Strorder = " UnitItems.Min desc ";
        } else {
            Strorder = " UnitItems.unitno desc ";
        }


        String query = "Select  distinct  ifnull(UnitItems.Weight,0) as weight,    UnitItems.unitno ,UnitItems.Min ,UnitItems.price,Unites.UnitName " +
                " , ifnull(Operand,1) as Operand from UnitItems  left join  Unites on Unites.Unitno =UnitItems.unitno  " +
                "   where UnitItems.item_no ='" + item_no + "' order by  " + Strorder;
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
                    cls_unitItems.setOperand(c1.getString(c1
                            .getColumnIndex("Operand")));
                    cls_unitItems.setWeight(c1.getString(c1
                            .getColumnIndex("weight")));

                    cls_unitItemses.add(cls_unitItems);

                } while (c1.moveToNext());

            }
            c1.close();
        }

        Cls_UnitItems_Adapter cls_unitItems_adapter = new Cls_UnitItems_Adapter(
                getActivity(), cls_unitItemses);

        sp_unite.setAdapter(cls_unitItems_adapter);

        if (cls_unitItemses.size() > 0) {
            Cls_UnitItems o = (Cls_UnitItems) sp_unite.getItemAtPosition(0);
            UnitNo = o.getUnitno().toString();
            UnitName = o.getUnitDesc().toString();
           // Operand = o.getOperand().toString();
            min = Float.valueOf(o.getMin());
         //   Weight.setText(o.getWeight());
        }

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

    @Override
 public void onClick(View v) {

     if (v == OpenCal) {
         SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
         final String MobileType = sharedPreferences.getString("MobileType", "-2") ;
         //if  (MobileType.equals("2")) {
             Intent intent = new Intent();
             intent.setAction(Intent.ACTION_MAIN);
             intent.addCategory(Intent.CATEGORY_LAUNCHER);
             intent.setComponent(new ComponentName(
                     CALCULATOR_PACKAGE,
                     CALCULATOR_CLASS));
             try {
                 startActivity(intent);
             } catch (Exception noSuchActivity) {
                 Toast.makeText(getActivity(),"1  "+ noSuchActivity.getMessage().toString(), Toast.LENGTH_SHORT).show();

             }
         /*}



         if (MobileType.equals("1")){



             Intent i = new Intent();
             i.setAction(Intent.ACTION_MAIN);
             i.addCategory(Intent.CATEGORY_APP_CALCULATOR);
             try {

                 startActivity(i);
             } catch (Exception noSuchActivity) {
                 Toast.makeText(getActivity(), "2  "+noSuchActivity.getMessage().toString(), Toast.LENGTH_SHORT).show();

             }


         }*/
     }




     final EditText dis = (EditText)form.findViewById(R.id.et_disc_per);

     if(v==add) {
         SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
         final String UserID = sharedPreferences.getString("UserID", "");

         EditText qty = (EditText) form.findViewById(R.id.et_qty);

         if (qty.getText().toString().length() == 0) {
             qty.setError("الرجاء ادخال القيمة");
             qty.requestFocus();
             return;
         }
     if( ItemNo5.toString().length() == 0 ) {

             return;
         }

         if (getArguments().getString("Scr") == "CustQtyedit") {
             ((CustomerQty) getActivity()).deleteItem(Integer.parseInt(getArguments().getString("itemid")), qty.getText().toString());

           //  ((CustomerQty) getActivity()).Save_List(getArguments().getString("no"), qty.getText().toString(), UnitNo, str, getArguments().getString("name"));

             qty.setText("");
         }
         if (getArguments().getString("Scr") == "CustQty") {
             ((CustomerQty) getActivity()).Save_List(ItemNo5, qty.getText().toString(), UnitNo, str, UnitName);
             qty.setText("");
         }

         if (getArguments().getString("Scr") == "AddItem") {

             AddItemmFragment fragment = new AddItemmFragment();
             fragment.Save_List(ItemNo5, qty.getText().toString(), UnitNo, str, UnitName);
             qty.setText("");
         }

      /*   if (getArguments().getString("Scr") == "CustQty") {

             ((CustomerQty) getActivity()).Save_List(ItemNo5, qty.getText().toString(), UnitNo, str, UnitName);
             qty.setText("");
         }*/

         if (getArguments().getString("Scr") == "RetrnQty") {

             ((ReturnQty) getActivity()).Save_List(ItemNo5, qty.getText().toString(), UnitNo, str, UnitName);

         }

         if (getArguments().getString("Scr") == "PrePareQty") {
             int  uintitem = Integer.parseInt(DB.GetValue(getActivity(), "UnitItems", "cast(Operand as Intger)", "item_no ='"+ItemNo5+"' and unitno = '"+UnitNo+"'"));
            double sum;
             sum = SToD(qty.getText().toString())* SToD(String.valueOf(uintitem));
         cheakQty(String.valueOf(sum),ItemNo5,UserID);

         }


         try {
             min = 0;

          /*   qty.setText("");


             ItemNo5 = "";
             UnitNo = "";*/


         } catch (Exception e) {
e.printStackTrace();
         }
     }
     if(v==cancel){
         this.dismiss();
     }



    }

    private void cheakQty(String QTY,String ItemNo,String UserID) {

        QTY2 =QTY;
        Itemno2 =ItemNo;
        ManNo2=UserID;
        loadingdialog = ProgressDialog.show(getActivity(), "الرجاء الانتظار ...", "يتم التأكد من توفر الكميه في المستودع", true);
        loadingdialog.setCancelable(false);
        loadingdialog.setCanceledOnTouchOutside(false);
        loadingdialog.show();
        final Handler _handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                CallWebServices ws = new CallWebServices(getActivity());
                ws.GetCheckQty(ManNo2,QTY2,Itemno2);
                try {


                    Integer i;
                    String q = "";
                    JSONObject js = new JSONObject(We_Result.Msg);
                    JSONArray Id = js.getJSONArray("ID");
                    a=Id.get(0).toString();
                    _handler.post(new Runnable() {

                        public void run() {
                           if(a.equals("1"))
                            {
                            /*    new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                        .setContentText("حدث خطأ اثناء عملية تحديث الوقت")
                                        .setCustomImage(R.drawable.tick)
                                        .setConfirmText("رجــــوع")
                                        .show();
                                loadingdialog.dismiss();*/
                                Setvalue();
                                loadingdialog.dismiss();
                            }
                           else
                           {
                               new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                       .setContentText("الكمية غير متوفرة")
                                       .setCustomImage(R.drawable.error_new)
                                       .setConfirmText("رجــــوع")
                                       .show();
                               loadingdialog.dismiss();
                               return;
                           }
                        }
                    });


                } catch (final Exception e) {
                    loadingdialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    getActivity()).create();
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

    public void Setvalue() {
        EditText qty = (EditText) form.findViewById(R.id.et_qty);
        if (UpdateItem != null  && UpdateItem.size()>0) {

            ((PreapareManQty) getActivity()).Update_List(ItemNo5, qty.getText().toString(), UnitNo, str, UnitName);
        }else {

            ((PreapareManQty) getActivity()).Save_List(ItemNo5, qty.getText().toString(), UnitNo, str, UnitName);
        }
    }
    public void onListItemClick(ListView l, View v, int position, long id) {


        // Set the item as checked to be highlighted
        items_Lsit.setItemChecked(position, true);
        v.setBackgroundColor(Color.BLUE);

        //conversationAdapter.notifyDataSetChanged();

    }


    public  void get_total()
    {
        EditText Price = (EditText)form.findViewById(R.id.et_price);
        EditText qty = (EditText)form.findViewById(R.id.et_qty);
        EditText tax = (EditText)form.findViewById(R.id.et_tax);
        EditText dis = (EditText)form.findViewById(R.id.et_disc_per);
        EditText bo = (EditText)form.findViewById(R.id.et_bo);
        EditText net_total = (EditText)form.findViewById(R.id.et_totl);


           String str_p,str_q ;

        str_p =  Price.getText().toString();
        str_q=   qty.getText().toString();

        if( Price.getText().toString().length() == 0 ) {
            str_p="0";
        }

      if( qty.getText().toString().length() == 0 ) {
            str_q="0";
        }
         /* if( dis.getText().toString().length() == 0 ) {
            dis.setText("0");
        }
        if( bo.getText().toString().length() == 0 ) {
            bo.setText("0");
        }*/
        Double p =  Double.parseDouble(str_p);
        Double q =  Double.parseDouble(str_q);

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat)nf;

        net_total.setText(String.valueOf(df.format(p*q)));

    }

}


