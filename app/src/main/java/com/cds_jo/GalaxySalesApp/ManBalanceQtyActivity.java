package com.cds_jo.GalaxySalesApp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.assist.Cls_Man_Qty_Adapter;
import com.cds_jo.GalaxySalesApp.assist.Convert_Man_Balance_Qty_To_Img;
import com.cds_jo.GalaxySalesApp.assist.Sale_InvoiceActivity;
import com.cds_jo.GalaxySalesApp.assist.Sale_ReturnActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import header.Header_Frag;

public class ManBalanceQtyActivity extends AppCompatActivity {



    public void c( ArrayList<Cls_Man_Balanc> x){
        Cls_Man_Qty_Adapter cls_trans_qty_adapter = new Cls_Man_Qty_Adapter(
                ManBalanceQtyActivity.this, x);
        lst_Items.setAdapter(cls_trans_qty_adapter);
        cls_trans_qty_adapter.notifyDataSetChanged();
    }

ListView lst_Items ;
    ArrayList<Cls_Man_Balanc> fil;
    ArrayList<Cls_Man_Balanc>   cls_trans_qties ;
    TextView tv_RowCount ;
    SqlHandler sqlHandler;
    String UserID= "";
    Boolean IsNew;
    EditText Maxpo;
    Cls_Man_Qty_Adapter cls_trans_qty_adapter ;
    TextView itemName;
    String ItemSearch;
    String MaxSaleInvoiceNo = "";
    int AllowSalInvMinus ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_balance_qty);
        Toast.makeText(ManBalanceQtyActivity.this,"يجب تحديث البيانات قبل بدء التسوية",Toast.LENGTH_LONG).show();
        fil = new ArrayList<Cls_Man_Balanc>();

        Fragment frag = new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1, frag).commit();
        sqlHandler = new SqlHandler(this);
        lst_Items =(ListView)findViewById(R.id.lst_Items);
        Maxpo = (EditText) findViewById(R.id.et_OrdeNo);
        cls_trans_qties = new ArrayList<Cls_Man_Balanc>();
        tv_RowCount = (TextView)findViewById(R.id.tv_RowCount);
        IsNew = true;

        itemName = (EditText)findViewById(R.id.et_ItemName);











        itemName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
               //    search(s.toString());


            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                    search(s.toString());




             /* SearchByName(itemName.getText().toString());
                cls_trans_qty_adapter=new Cls_Man_Qty_Adapter(ManBalanceQtyActivity.this);
                cls_trans_qty_adapter.getFilter().filter(itemName.getText().toString());*/
            }
        });


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        UserID= sharedPreferences.getString("UserID", "");
        btn_GetData();
        GetMaxPONo();


    }

    public void max_unit(View v){
        ShowData(1);

    }
    public void min_unit(View v){        ShowData(0);
    }


    public void SearchByName(String itemName )
    {
        ItemSearch=itemName;

        AllowSalInvMinus =Integer.parseInt( DB.GetValue(this,"ComanyInfo","AllowSalInvMinus","1=1"));

        final ProgressDialog progressDialog;
        final Handler _handler = new Handler();
//        progressDialog = new ProgressDialog(ManBalanceQtyActivity.this);
//        progressDialog.setTitle("الرجاء الانتظار");
//        progressDialog.setMessage("العمل جاري على استرجاع كميات المستودع");
//        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setProgress(0);
//        progressDialog.setMax(100);
//        progressDialog.show();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        final String currentDateandTime = sdf.format(new Date());

        Calendar cc = Calendar.getInstance();

                try {
                    Integer i;
                    String q = "";


                    cls_trans_qties.clear();

                    Cls_Man_Balanc cls_trans_qty = new Cls_Man_Balanc();

                    SqlHandler sqlHandler = new SqlHandler(ManBalanceQtyActivity.this);

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ManBalanceQtyActivity.this);
                    String u = sharedPreferences.getString("UserID", "");

                    if (AllowSalInvMinus == 1) {
                        q = " Select distinct  *" +
                                "" +
                                "  from  invf inner join ManStore  ms   on invf.Item_No =  ms.itemno    left join Unites on Unites.Unitno=  ms.UnitNo where  Item_Name  like '%" + ItemSearch + "%'";

                    } else {
                        q = "Select distinct   *" +
                                "   from  ManStore  ms inner join invf on invf.Item_No =  ms.itemno    left join Unites on Unites.Unitno=  ms.UnitNo " +
                                " where Item_Name  like '%" + ItemSearch + "%'  and ms.SManNo ='" + u.toString() + "'";
                    }

                    q+=" ORDER BY CAST(invf.Weight AS int) DESC";

                    Double qty = 0.0;
                    Double SaledQtyNotPosted = 0.0;
                    Double RetQtyNotPosted = 0.0;
                    Cursor c = sqlHandler.selectQuery(q);
                    i = 0;
                    if (c != null && c.getCount() != 0) {
                        if (c.moveToFirst()) {
                            do {

                             //   double SumReturn =Double.parseDouble( DB.GetValue(ManBalanceQtyActivity.this,"Sal_return_Det","ifnull( sum  ( ifnull( qty,0)  * (ifnull(Operand,1))) ,0)","ItemNo ='" + c.getString(c.getColumnIndex("itemno")) + "' and Post = '-1' "));

                                cls_trans_qty = new Cls_Man_Balanc();


                                cls_trans_qty.setItemno(c.getString(c
                                        .getColumnIndex("itemno")));
                                cls_trans_qty.setItem_Name(c.getString(c
                                        .getColumnIndex("Item_Name")));
                                cls_trans_qty.setUnitNo(c.getString(c
                                        .getColumnIndex("Unitno")));
                                cls_trans_qty.setQtyAcc(SToD(c.getString(c
                                        .getColumnIndex("qty"))).toString());
                                cls_trans_qty.setUnitName(c.getString(c
                                        .getColumnIndex("UnitName")));
                                String weight=DB.GetValue(ManBalanceQtyActivity.this, "invf", "Weight", "Item_No='"+c.getString(c
                                        .getColumnIndex("itemno"))+"'");

                                cls_trans_qty.setWeight(weight);



                                SaledQtyNotPosted = GetSaledQtyNotPosted(c.getString(c.getColumnIndex("itemno")));
                                RetQtyNotPosted = GetRetQtyNotPosted(c.getString(c.getColumnIndex("itemno")));
                                cls_trans_qty.setQtySaled(SaledQtyNotPosted.toString());





                                qty = ((Double.parseDouble(c.getString(c.getColumnIndex("qty"))) - SaledQtyNotPosted + RetQtyNotPosted));
                                SaledQtyNotPosted = 0.0;
                                cls_trans_qty.setQty((SToD(qty.toString())).toString());


                                cls_trans_qty.setAct_Aty("0");
                                if (qty < 0) {
                                    cls_trans_qty.setDiff(((-1) * qty) + "");
                                }

                                if (qty != 0) {
                                    cls_trans_qties.add(cls_trans_qty);
                                }
                                qty = 0.0;

//
//                                progressDialog.setMax(c.getCount());
//
//                                progressDialog.incrementProgressBy(1);
//
//                                if (progressDialog.getProgress() == progressDialog.getMax()) {
//                                    progressDialog.dismiss();
//                                }
//
                                i = i + 1;

                            } while (c.moveToNext());

                        }
                        c.close();
                    }

                    Cls_Man_Qty_Adapter cls_trans_qty_adapter = new Cls_Man_Qty_Adapter(
                            ManBalanceQtyActivity.this, cls_trans_qties);
                    lst_Items.setAdapter(cls_trans_qty_adapter);
                   cls_trans_qty_adapter.notifyDataSetChanged();
                }




//                    final int total = i;
//                    _handler.post(new Runnable() {
//                        public void run() {
//                            tv_RowCount.setText(""+(total));
////                            progressDialog.dismiss();
//                            AlertDialog alertDialog = new AlertDialog.Builder(
//                                    ManBalanceQtyActivity.this).create();
//
//                            alertDialog.setMessage("تمت عملية تحديث البيانات بنجاح" + "   " + String.valueOf(total));
//                            alertDialog.setIcon(R.drawable.tick);
//                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                }
//                            });
//                            // alertDialog.show();
//
//                        }
//                    });
                catch (final Exception e) {
//                    progressDialog.dismiss();
//                   _handler.post(new Runnable() {
//                        public void run() {
//                            AlertDialog alertDialog = new AlertDialog.Builder(
//                                    ManBalanceQtyActivity.this).create();
//                            alertDialog.setTitle("فشل في عمليه الاتصال");
//                            alertDialog.setMessage(e.getMessage().toString());
//                            alertDialog.setIcon(R.drawable.tick);
//                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                }
//                            });
//                            alertDialog.show();
//                        }
//                    });
                }





    }
    public  void GetMaxPONo(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String u =  sharedPreferences.getString("UserID", "");
        String Login = sharedPreferences.getString("Login", "No");
        if(Login.toString().equals("No")){
            Intent i = new Intent(this,NewLoginActivity.class);
            startActivity(i);
        }

        String query = "SELECT  COALESCE(MAX(OrderNo), 0) +1 AS no FROM BalanceQty where userid ='"+UserID+"'";
        Cursor c1 = sqlHandler.selectQuery(query);
        String max="0";

        if (c1.getCount() > 0 && c1!=null) {
            c1.moveToFirst();
            max = c1.getString(c1.getColumnIndex("no"));
            c1.close();
        }

    /*    String max1="0";
        max1 = sharedPreferences.getString("m7", "");
        if (max1==""){
            max1 ="0";
        }
        max1 =String.valueOf(Integer.parseInt(max1) + 1);
        if (SToD(max1) > SToD(max))
        {
            max = max1 ;
        }*/

        if (max.length()==1) {
            Maxpo.setText(intToString(Integer.valueOf(u), 2) + intToString(Integer.valueOf(max), 5));

        }
        else {
            Maxpo.setText(intToString(Integer.valueOf(max), 7)  );

        }

        Maxpo.setFocusable(false);
        Maxpo.setEnabled(false);
        Maxpo.setCursorVisible(false);



        // Maxpo.setText(max);

    }
    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
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
    public void btn_GetData() {
        SqlHandler sqlHandler = new SqlHandler(ManBalanceQtyActivity.this);
        String query = "SELECT   distinct *   from  Sal_invoice_Hdr  where    Post = -1 And UserID  ='"+UserID+"'";


        Cursor c1 = sqlHandler.selectQuery(query);

        Double Sal_Qty = 0.0;
        if (c1 != null && c1.getCount() != 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    ManBalanceQtyActivity.this).create();

            alertDialog.setMessage("يوجد فواتير غير مرحلة ، الرجاء ترحيل الفواتير ومن عمل تسوية جرد  مستودع المندوب");

            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ShowData(1);
                }

            });
            alertDialog.show();
            c1.close();
        }
        else
        {
            ShowData(1);
        }


    }
    public void btn__Remove_Item( final View view) {

         position = lst_Items.getPositionForView(view);
        cls_trans_qties.remove(position);
        lst_Items.invalidateViews();

        tv_RowCount.setText("" + (cls_trans_qties.size()));
 /*
        position = lst_Items.getPositionForView(view);
       // cls_trans_qties.get(position).setAct_Aty(cls_trans_qties.get(position).getQty());
        cls_trans_qties.get(position).setDiff("0");
        lst_Items.invalidateViews();
      /*  Cls_Man_Qty_Adapter cls_trans_qty_adapter = new Cls_Man_Qty_Adapter(
                ManBalanceQtyActivity.this, cls_trans_qties);
        cls_trans_qty_adapter.notifyDataSetChanged() ;
*/
    }
    int position ;
    public void btn_Edite_Qty( final View view) {
        position = lst_Items.getPositionForView(view);
        Cls_Man_Balanc cls_trans_qty = new Cls_Man_Balanc();
        cls_trans_qty = cls_trans_qties.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("Qty", cls_trans_qty.getQty());
        bundle.putString("ItemName", cls_trans_qty.getItem_Name());
        FragmentManager Manager =  getFragmentManager();
        PopEnterActQty obj = new PopEnterActQty();
        obj.setArguments(bundle);
        obj.show(Manager, null);

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Cls_Sal_InvItems contactListItems =new Cls_Sal_InvItems();


        menu.setHeaderTitle("contactList.get(position).getName()");
        menu.add(Menu.NONE, 1, Menu.NONE, "تعديل");
        menu.add(Menu.NONE, 2, Menu.NONE, "حذف");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        TextView pono = (TextView)findViewById(R.id.et_OrdeNo);
        // TODO Auto-generated method stub
        switch(item.getItemId())
        {
            case 1:
            {

               /* ArrayList<Cls_Sal_InvItems> Itemlist = new ArrayList <Cls_Sal_InvItems> ();

                Itemlist.add(contactList.get(position))  ;
                Bundle bundle = new Bundle();
                bundle.putString("Scr", "Sal_inv");
                bundle.putString("OrderNo", pono.getText().toString());
                bundle.putSerializable("List", Itemlist);
                FragmentManager Manager =  getFragmentManager();
                PopSal_Inv_Select_Items obj = new PopSal_Inv_Select_Items();
                obj.setArguments(bundle);
                obj.show(Manager, null);*/
            }
            break;
            case 2:
            {
               /* AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("فاتورة مبيعات");
                alertDialog.setMessage("هل انت متاكد من عملية الحذف");
                alertDialog.setIcon(R.drawable.delete);
                alertDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        contactList.remove(position);
                        CalcTotal();
                        showList();

                    }
                });

                alertDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();*/

            }
            break;

        }

        return super.onContextItemSelected(item);
    }
    private void ShowData(final int unit){

        AllowSalInvMinus =Integer.parseInt( DB.GetValue(this,"ComanyInfo","AllowSalInvMinus","1=1"));

        final ProgressDialog progressDialog;
        final Handler _handler = new Handler();
        progressDialog = new ProgressDialog(ManBalanceQtyActivity.this);
        progressDialog.setTitle("الرجاء الانتظار");
        progressDialog.setMessage("العمل جاري على استرجاع كميات المستودع");
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
         progressDialog.show();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        final String currentDateandTime = sdf.format(new Date());

        Calendar cc = Calendar.getInstance();
        final int dayOfWeek = cc.get(Calendar.DAY_OF_WEEK);
         new Thread(new Runnable() {
            @Override
            public void run() {

               try {
                    Integer i;
                    String q = "";


                    cls_trans_qties.clear();

                    Cls_Man_Balanc cls_trans_qty = new Cls_Man_Balanc();

                    SqlHandler sqlHandler = new SqlHandler(ManBalanceQtyActivity.this);

                   SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ManBalanceQtyActivity.this);
                   String u = sharedPreferences.getString("UserID", "");

                   if(AllowSalInvMinus==1){
                       q = " Select distinct  ifnull(Unites.Unitno,0)as  Unitno,  ifnull(ms.ser,0) as ser, ifnull(ms.docno,0) as docno ,ifnull(ms.StoreName,0)  as StoreName, ifnull(invf.Item_No,0) as itemno  , ifnull(invf.Item_Name,0) as Item_Name" +
                               ",ifnull(Unites.UnitName ,0)  as  UnitName ,ifnull(ms.qty,0) as qty   ,ifnull(ms.des,0)as des ,  ifnull(ms.date  ,0)as date" +
                               "  from  invf left join ManStore  ms   on invf.Item_No =  ms.itemno    left join Unites on Unites.Unitno=  ms.UnitNo";

                   }else {
                       q = "Select distinct   ifnull(Unites.Unitno,0)as  Unitno, ms.ser, ms.docno ,ms.StoreName , ms.itemno , invf.Item_Name   ,Unites.UnitName     ,ms.qty  ,ms.des ,  ms.date " +
                               "   from  ManStore  ms left join invf on invf.Item_No =  ms.itemno    left join Unites on Unites.Unitno=  ms.UnitNo " +
                               "   and ms.SManNo ='" +u.toString()+"'";
                   }
                   q+=" ORDER BY CAST(invf.Weight AS int) DESC";

                              Double qty = 0.0;
                   Double SaledQtyNotPosted = 0.0 ;
                   Double RetQtyNotPosted = 0.0 ;
                    Cursor c =  sqlHandler.selectQuery(q);
                    i = 0;
                    if (c != null && c.getCount() != 0) {
                        if (c.moveToFirst()) {
                            do {


                                  cls_trans_qty = new Cls_Man_Balanc();


                                cls_trans_qty.setItemno(c.getString(c
                                        .getColumnIndex("itemno")));
                                cls_trans_qty.setItem_Name(c.getString(c
                                        .getColumnIndex("Item_Name")));


                                String us= DB.GetValueMax(ManBalanceQtyActivity.this, "UnitItems", "unitno", "item_no='"+c.getString(c.getColumnIndex("itemno"))+"'");

                                String op= DB.GetValue(ManBalanceQtyActivity.this, "UnitItems", "Operand", "item_no='"+c.getString(c.getColumnIndex("itemno"))+"' and unitno='"+us+"'");

                                Double operand= Double.valueOf(op);
                                String un_no= DB.GetValue(ManBalanceQtyActivity.this, "UnitItems", "unitno", "item_no='"+c.getString(c.getColumnIndex("itemno"))+"' and Operand='"+op+"'");
                                String un_name= DB.GetValue(ManBalanceQtyActivity.this, "Unites", "UnitName", "Unitno='"+un_no+"'");

                                Double unit_min=Double.valueOf(c.getString(c
                                        .getColumnIndex("qty")));


                             /*   String u_name=DB.GetValue(ManBalanceQtyActivity.this,"UnitItems","",
                                        "item_no='"+c.getString(c.getColumnIndex("itemno"))+"' and Operand='"+operand+"'")
*/

if(unit==1){
    cls_trans_qty.setQty(String.valueOf(SToD(String.valueOf(unit_min/operand))));
    cls_trans_qty.setUnitName(un_name);
}else{
    cls_trans_qty.setQty(String.valueOf(SToD(c.getString(c
            .getColumnIndex("qty")))));
    cls_trans_qty.setUnitName(c.getString(c
            .getColumnIndex("UnitName")));
}
                                cls_trans_qty.setUnitNo(un_no);


                              /*  cls_trans_qty.setQtyAcc(SToD(c.getString(c
                                        .getColumnIndex("qty"))).toString());*/

                                SaledQtyNotPosted = GetSaledQtyNotPosted(c.getString(c.getColumnIndex("itemno")));
                                RetQtyNotPosted = GetRetQtyNotPosted(c.getString(c.getColumnIndex("itemno")));

                                cls_trans_qty.setQtySaled(SaledQtyNotPosted.toString());

                                double SumReturn =Double.parseDouble( DB.GetValue(ManBalanceQtyActivity.this,"Sal_return_Det","ifnull( sum  ( ifnull( qty,0)  * (ifnull(Operand,1))) ,0)","ItemNo ='" + c.getString(c.getColumnIndex("itemno")) + "' and Post = '-1' "));

                                qty = ((Double.parseDouble(c.getString(c.getColumnIndex("qty"))) ));


                                String weight=DB.GetValue(ManBalanceQtyActivity.this, "invf", "Weight", "Item_No='"+c.getString(c
                                        .getColumnIndex("itemno"))+"'");

                                cls_trans_qty.setWeight(weight);



                              qty = ( (Double.parseDouble(c.getString(c.getColumnIndex("qty"))) - SaledQtyNotPosted)+RetQtyNotPosted);
                                SaledQtyNotPosted=0.0;
                               // cls_trans_qty.setQty((SToD(qty.toString())).toString());


                                cls_trans_qty.setAct_Aty("0");
                                if (qty<0) {
                                    cls_trans_qty.setDiff(( (-1)*qty)+"");
                                }

                                if (qty!=0){
                                //    cls_trans_qties.add(cls_trans_qty);
                                }
                                qty = 0.0;

                                cls_trans_qties.add(cls_trans_qty);

                                progressDialog.setMax(c.getCount());

                                progressDialog.incrementProgressBy(1);

                                if (progressDialog.getProgress() == progressDialog.getMax()) {
                                    progressDialog.dismiss();
                                }

                                i = i+1;

                            } while (c.moveToNext());

                        }
                        c.close();
                    }
                     _handler.post(new Runnable() {
                         public void run() {
                            Cls_Man_Qty_Adapter cls_trans_qty_adapter = new Cls_Man_Qty_Adapter(
                            ManBalanceQtyActivity.this, cls_trans_qties);
                            lst_Items.setAdapter(cls_trans_qty_adapter);




                        }
                    });

                    final int total = i;
                    _handler.post(new Runnable() {
                        public void run() {
                            tv_RowCount.setText(""+(total));
                            progressDialog.dismiss();
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    ManBalanceQtyActivity.this).create();

                            alertDialog.setMessage("تمت عملية تحديث البيانات بنجاح" + "   " + String.valueOf(total));
                            alertDialog.setIcon(R.drawable.tick);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            // alertDialog.show();

                        }
                    });
                } catch (final Exception e) {
                    progressDialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    ManBalanceQtyActivity.this).create();
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

    public void search(String s){
        Cls_Man_Balanc cls_trans_qty = new Cls_Man_Balanc();

        if(s.equals("") || s.isEmpty() || s==null){


           /* for (int i = 0; i < cls_trans_qties.size(); i++) {
                String data = cls_trans_qties.get(i).getItem_Name();
                if (data.toLowerCase().startsWith(s)) {
                    //  cls_trans_qty = cls_trans_qties.get(i);
                    //   String data = cls_trans_qties.get(i).getItem_Name();


                    cls_trans_qty = new Cls_Man_Balanc();

                    cls_trans_qty.setItemno(cls_trans_qties.get(i).getItemno());
                    cls_trans_qty.setItem_Name(cls_trans_qties.get(i).getItem_Name());
                    cls_trans_qty.setUnitNo(cls_trans_qties.get(i).getUnitNo());
                    cls_trans_qty.setQtyAcc(cls_trans_qties.get(i).getQtyAcc());
                    cls_trans_qty.setUnitName(cls_trans_qties.get(i).getUnitName());
                    cls_trans_qty.setQtySaled(cls_trans_qties.get(i).getQtySaled());
                    cls_trans_qty.setQty(cls_trans_qties.get(i).getQty());
                    cls_trans_qty.setAct_Aty(cls_trans_qties.get(i).getAct_Aty());
                    if (cls_trans_qties.get(i).getDiff() == null)
                        cls_trans_qty.setDiff("0");
                    else
                        cls_trans_qty.setDiff(cls_trans_qties.get(i).getDiff());

                    if(cls_trans_qties.get(i).getItem_Name().startsWith(s))
                    fil.add(cls_trans_qty);

                }
            }

            Cls_Man_Qty_Adapter cls_trans_qty_adapter = new Cls_Man_Qty_Adapter(
                    ManBalanceQtyActivity.this, fil);
            lst_Items.setAdapter(cls_trans_qty_adapter);
            cls_trans_qty_adapter.notifyDataSetChanged();
*/

            //Toast.makeText(M

            Cls_Man_Qty_Adapter cls_trans_qty_adapter = new Cls_Man_Qty_Adapter(
                    ManBalanceQtyActivity.this, cls_trans_qties);
            lst_Items.setAdapter(cls_trans_qty_adapter);



        }else {

            //  cls_trans_qties = new ArrayList<Cls_Man_Balanc>();
            fil.clear();

            for (int i = 0; i < cls_trans_qties.size(); i++) {

                //  cls_trans_qty = cls_trans_qties.get(i);
                String data = cls_trans_qties.get(i).getItem_Name();
              //  if (data.toLowerCase().startsWith(s)) {

                    cls_trans_qty = new Cls_Man_Balanc();


                    cls_trans_qty.setItemno(cls_trans_qties.get(i).getItemno());
                    cls_trans_qty.setItem_Name(cls_trans_qties.get(i).getItem_Name());
                    cls_trans_qty.setUnitNo(cls_trans_qties.get(i).getUnitNo());
                    cls_trans_qty.setQtyAcc(cls_trans_qties.get(i).getQtyAcc());
                    cls_trans_qty.setUnitName(cls_trans_qties.get(i).getUnitName());
                    cls_trans_qty.setQtySaled(cls_trans_qties.get(i).getQtySaled());
                    cls_trans_qty.setQty(cls_trans_qties.get(i).getQty());
                    cls_trans_qty.setAct_Aty(cls_trans_qties.get(i).getAct_Aty());


                cls_trans_qty.setWeight(cls_trans_qties.get(i).getWeight());


                    if(cls_trans_qties.get(i).getDiff()==null)
                        cls_trans_qty.setDiff("0");
                    else
                    cls_trans_qty.setDiff(cls_trans_qties.get(i).getDiff());

                    if(cls_trans_qties.get(i).getItem_Name().startsWith(s))
                    fil.add(cls_trans_qty);
else
    continue;

             //   }
            }

            Cls_Man_Qty_Adapter cls_trans_qty_adapter = new Cls_Man_Qty_Adapter(
                    ManBalanceQtyActivity.this, fil);
            lst_Items.setAdapter(cls_trans_qty_adapter);
            cls_trans_qty_adapter.notifyDataSetChanged();


            //Toast.makeText(ManBalanceQtyActivity.this,lst_Items.getCount(),Toast.LENGTH_SHORT).show();
        }
    }

public void s(String s){
    Cls_Man_Balanc cls_trans_qty = new Cls_Man_Balanc();

    for (int i = 0; i < cls_trans_qties.size(); i++) {

        //  cls_trans_qty = cls_trans_qties.get(i);
        String data = cls_trans_qties.get(i).getItem_Name();
        if (data.toLowerCase().startsWith(s)) {



            cls_trans_qty = new Cls_Man_Balanc();


            cls_trans_qty.setItemno(cls_trans_qties.get(i).getItemno());
            cls_trans_qty.setItem_Name(cls_trans_qties.get(i).getItem_Name());
            cls_trans_qty.setUnitNo(cls_trans_qties.get(i).getUnitNo());
            cls_trans_qty.setQtyAcc(cls_trans_qties.get(i).getQtyAcc());
            cls_trans_qty.setUnitName(cls_trans_qties.get(i).getUnitName());
            cls_trans_qty.setQtySaled(cls_trans_qties.get(i).getQtySaled());
            cls_trans_qty.setQty(cls_trans_qties.get(i).getQty());
            cls_trans_qty.setAct_Aty(cls_trans_qties.get(i).getAct_Aty());


            if(cls_trans_qties.get(i).getDiff()==null)
                cls_trans_qty.setDiff("0");
            else
                cls_trans_qty.setDiff(cls_trans_qties.get(i).getDiff());

            if(cls_trans_qties.get(i).getItem_Name().startsWith(s))
                fil.add(cls_trans_qty);


        }
    }

    Cls_Man_Qty_Adapter cls_trans_qty_adapter = new Cls_Man_Qty_Adapter(
            ManBalanceQtyActivity.this, fil);
    lst_Items.setAdapter(cls_trans_qty_adapter);
    cls_trans_qty_adapter.notifyDataSetChanged();


    //Toast.makeText(ManBalanceQtyActivity.this,lst_Items.getCount(),Toast.LENGTH_SHORT).show();
}



   // sih.Post = -1  and
    private Double GetSaledQtyNotPosted(String ItemNo ){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String u =  sharedPreferences.getString("UserID", "");
        SqlHandler sqlHandler = new SqlHandler(ManBalanceQtyActivity.this);
       /*String query = "SELECT  distinct    (ifnull( sum  ( ifnull( sid.qty,0)  * (ifnull( sid.Operand,1))) ,0)  +   ifnull( sum  ( ifnull( sid.bounce_qty,0)  * (ifnull( sid.Operand,1))) ,0) +  ifnull( sum  ( ifnull( sid.Pro_bounce,0)  * (ifnull( sid.Operand,1))) ,0))  as Sal_Qty  from  Sal_invoice_Hdr  sih inner join Sal_invoice_Det sid on  sid.OrderNo = sih.OrderNo" +
                " inner join  UnitItems ui on ui.item_no  = sid.itemNo and ui.unitno = sid.unitNo" +
                "    where ui.item_no ='"+ItemNo+"'   and sih.UserID ='" +u.toString()+"'"  ;
*/
        String query = "SELECT  distinct    (ifnull( sum  ( ifnull( sid.qty,0)  * (ifnull( sid.Operand,1))) ,0)  +   ifnull( sum  ( ifnull( sid.bounce_qty,0)  * (ifnull( sid.Operand,1))) ,0) +  ifnull( sum  ( ifnull( sid.Pro_bounce,0)  * (ifnull( sid.Operand,1))) ,0))  as Sal_Qty  from  Sal_invoice_Hdr  sih inner join Sal_invoice_Det sid on  sid.OrderNo = sih.OrderNo" +
                " inner join  UnitItems ui on ui.item_no  = sid.itemNo and ui.unitno = sid.unitNo" +
                "    where  sih.Post = '-1' and ui.item_no ='"+ItemNo+"'   and sih.UserID ='" +u.toString()+"'"  ;



        Cursor c1 = sqlHandler.selectQuery(query);

        Double Sal_Qty = 0.0;
        if (c1 != null && c1.getCount() != 0) {
            if (c1.getCount() > 0) {
                c1.moveToFirst();
                Sal_Qty =   Double.parseDouble(  (c1.getString(c1.getColumnIndex("Sal_Qty"))).toString());
            }
         c1.close();
    }



        return Sal_Qty;
    }
    public Double GetRetQtyNotPosted(String ItemNo ){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String u =  sharedPreferences.getString("UserID", "");
        SqlHandler sqlHandler = new SqlHandler(ManBalanceQtyActivity.this);
        String query = "SELECT     ifnull( sum  ( ifnull( sid.qty,0)  * (ifnull( sid.Operand,1))) ,0) as Sal_Qty  from  Sal_return_Hdr  sih inner join Sal_return_Det sid on  sid.OrderNo = sih.OrderNo" +
                " inner join  UnitItems ui on ui.item_no  = sid.itemNo and ui.unitno = sid.unitNo" +
                "    where  sih.Post = -1 and ui.item_no ='"+ItemNo+"'  and sih.UserID='"+u+"'";
        Cursor c1 = sqlHandler.selectQuery(query);

        Double Sal_Qty = 0.0;
        if (c1 != null && c1.getCount() != 0) {
            if (c1.getCount() > 0) {
                c1.moveToFirst();
                Sal_Qty =   Double.parseDouble(  (c1.getString(c1.getColumnIndex("Sal_Qty"))).toString());
            }
            c1.close();
        }



        return Sal_Qty;
    }
    public void btn_new(View view) {
        cls_trans_qties.clear();
        lst_Items.invalidateViews();
        GetMaxPONo();
        btn_GetData();
        IsNew = true;
    }
    public void btn_back(View view) {
        Intent i = new Intent(this,JalMasterActivity.class);
        startActivity(i);
    }

    public  void SaveQty(String ActQty , String Diff){



        EditText et_ItemName=(EditText)findViewById(R.id.et_ItemName);

        if(et_ItemName.getText().toString().isEmpty() || et_ItemName.getText().toString().equals("")) {
            cls_trans_qties.get(position).setAct_Aty(ActQty);
            cls_trans_qties.get(position).setDiff(Diff);
            for(int i=0;i<fil.size();i++){
                if(cls_trans_qties.get(position).getItemno().equals(fil.get(i).getItemno())){
                    fil.get(i).setAct_Aty(ActQty);
                    fil.get(i).setDiff(Diff);
                 //   Toast.makeText(ManBalanceQtyActivity.this,"empty done edit = "+Diff,Toast.LENGTH_SHORT).show();

                }
            }
            Cls_Man_Qty_Adapter cls_trans_qty_adapter = new Cls_Man_Qty_Adapter(
                    ManBalanceQtyActivity.this, cls_trans_qties);
            cls_trans_qty_adapter.notifyDataSetChanged();

          //  Toast.makeText(ManBalanceQtyActivity.this,"empty done edit",Toast.LENGTH_SHORT).show();

        }
        else {
            fil.get(position).setAct_Aty(ActQty);
            fil.get(position).setDiff(Diff);
for(int i=0;i<cls_trans_qties.size();i++){
    if(fil.get(position).getItemno().equals(cls_trans_qties.get(i).getItemno())){
        cls_trans_qties.get(i).setAct_Aty(ActQty);
        cls_trans_qties.get(i).setDiff(Diff);
    }
}
            cls_trans_qty_adapter = new Cls_Man_Qty_Adapter(
                    ManBalanceQtyActivity.this, fil);
            cls_trans_qty_adapter.notifyDataSetChanged();
          //  Toast.makeText(ManBalanceQtyActivity.this,"full done edit",Toast.LENGTH_SHORT).show();

        }

     /* lst_Items.setAdapter(cls_trans_qty_adapter);
        lst_Items.scrollBy(0,0);*/
     lst_Items.invalidateViews();




    }
    public void btn_save_po(View view) {
        TextView pono = (TextView)findViewById(R.id.et_OrdeNo);
      //  String sal_inv =DB.GetValue(this,"Sal_invoice_Det","ifnull(count(*),0) as coun","Note ='"+pono.getText().toString()+"'");
       // String sal_ret =DB.GetValue(this,"Sal_return_Det","ifnull(count(*),0) as coun","Note ='"+pono.getText().toString()+"'");
        String sal_inv =DB.GetValue(this,"Sal_invoice_Det","count(*)","Note ='"+pono.getText().toString()+"'");
        String sal_ret =DB.GetValue(this,"Sal_return_Det","count(*)","Note ='"+pono.getText().toString()+"'");
        if (  cls_trans_qties.size()==0) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("تسوية جرد المندوب");
            alertDialog.setMessage("لا يمكن التخزين دون وجود مواد");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();

           // return;
        }  else if (  !(Integer.parseInt(sal_inv)==0 || Integer.parseInt(sal_inv) ==-1)) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("تسوية جرد المندوب");
            alertDialog.setMessage("لا يمكن التخزين يوجد بها فاتورة بيع");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();

           // return;
        } else if (  !(Integer.parseInt(sal_ret)==0 || Integer.parseInt(sal_ret) ==-1)) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("تسوية جرد المندوب");
            alertDialog.setMessage("لا يمكن التخزين يوجد بها مردود بيع");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();

           // return;
        }
else {


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("جرد كميات مندوب");
            alertDialog.setMessage("هل تريد الاستمرار بعملية الحفظ");
            alertDialog.setIcon(R.drawable.save);
            alertDialog.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Save_Recod_Po();
                }
            });


            alertDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });


            alertDialog.show();


        }
    }
    public void   Save_Recod_Po()    {

        ContentValues cv;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        Long i  = Long.parseLong("0");

            String q ="Delete from  BalanceQty where OrderNo ='"+ Maxpo.getText().toString()+"'";
            sqlHandler.executeQuery(q);

            for (int x = 0; x < cls_trans_qties.size(); x++) {
                Cls_Man_Balanc cls_trans_qty = new Cls_Man_Balanc();
                cls_trans_qty = cls_trans_qties.get(x);

                cv = new ContentValues();
                cv.put("OrderNo", Maxpo.getText().toString());
                cv.put("Item_No",cls_trans_qty.getItemno());
                cv.put("Unit_No", cls_trans_qty.getUnitNo().toString());
                cv.put("Qty", cls_trans_qty.getQty().toString());
                cv.put("ActQty", cls_trans_qty.getAct_Aty().toString());
                cv.put("Diff", cls_trans_qty.getDiff()==null?"0": cls_trans_qty.getDiff().toString());
                cv.put("UserID", UserID);
                cv.put("date", currentDateandTime);
                cv.put("posted", "-1");
          /*  if ( cls_trans_qty.getDiff()!= null && SToD(cls_trans_qty.getDiff().toString())>0) {
                i = sqlHandler.Insert("BalanceQty", null, cv);
            }*/
                i = sqlHandler.Insert("BalanceQty", null, cv);
            }


        if (i> 0 ) {
            //  GetMaxPONo();
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("تسوية جرد المندوب");
            alertDialog.setMessage("تمت عملية الحفظ بنجاح ");
            IsNew=false;

            alertDialog.setIcon(R.drawable.tick);

            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    View view = null;
                   // GetRecord();
                    Do_print();
                }
            });


            alertDialog.show();
        }else{
            if (i> 0 ) {
                //  GetMaxPONo();
                AlertDialog alertDialog = new AlertDialog.Builder(
                        this).create();
                alertDialog.setTitle("تسوية جرد المندوب");
                alertDialog.setMessage("عملية  تسوية الجرد لم تتم بنجاح");
                IsNew=false;

                alertDialog.setIcon(R.drawable.tick);

                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                alertDialog.show();
            }
        }


    }



    public void btn_Search_Orders(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "ManBalance");
        FragmentManager Manager =  getFragmentManager();
        SearchManBalanceQty obj = new SearchManBalanceQty();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }

    public void Set_Order(String No, String Nm) {
        Maxpo.setText(No);
        GetRecord();
    }
    private void GetRecord(){
                cls_trans_qties.clear();
                lst_Items.invalidateViews();
                cls_trans_qties.clear();
                Cls_Man_Balanc cls_trans_qty;

                SqlHandler sqlHandler = new SqlHandler(ManBalanceQtyActivity.this);

        String q      = "  select distinct  Unites.UnitName,  invf.Item_Name, pod.Item_No,pod.Qty,pod.ActQty,pod.Diff ,pod.Unit_No from BalanceQty pod left join invf on invf.Item_No =  pod.Item_No    left join Unites on Unites.Unitno=  pod.Unit_No  Where pod.OrderNo='" + Maxpo.getText().toString() + "'";


        Cursor c =  sqlHandler.selectQuery(q);

                if (c != null && c.getCount() != 0) {
                if (c.moveToFirst()) {
                do {
                cls_trans_qty = new Cls_Man_Balanc();
                cls_trans_qty.setItemno(c.getString(c
                        .getColumnIndex("Item_No")));
                cls_trans_qty.setItem_Name(c.getString(c
                        .getColumnIndex("Item_Name")));
                cls_trans_qty.setUnitNo(c.getString(c
                        .getColumnIndex("Unit_No")));
                cls_trans_qty.setUnitName(c.getString(c
                        .getColumnIndex("UnitName")));
                cls_trans_qty.setQty(c.getString(c
                        .getColumnIndex("Qty")));
                cls_trans_qty.setAct_Aty(c.getString(c
                        .getColumnIndex("ActQty")));
                cls_trans_qty.setDiff(c.getString(c
                        .getColumnIndex("Diff")));
                cls_trans_qties.add(cls_trans_qty);


                } while (c.moveToNext());

                }
                c.close();
                }
        Cls_Man_Qty_Adapter cls_trans_qty_adapter = new Cls_Man_Qty_Adapter(
                ManBalanceQtyActivity.this, cls_trans_qties);
        lst_Items.setAdapter(cls_trans_qty_adapter);

        tv_RowCount.setText("" + (cls_trans_qties.size()));


    }

    public void btn_delete(View view) {


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("تسوية جرد المندوب");
            alertDialog.setMessage("هل انت متاكد من عملية الحذف");
            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Delete_Record_PO();

                }
            });


            alertDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });


            alertDialog.show();

    }

    public void Delete_Record_PO(){

        String query ="Delete from  BalanceQty where OrderNo  ='"+ Maxpo.getText().toString()+"'";
        sqlHandler.executeQuery(query);
        cls_trans_qties.clear();
        lst_Items.invalidateViews();
        GetMaxPONo();
        btn_GetData();
        IsNew = true;
        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setTitle("تسوية جرد المندوب");
        alertDialog.setMessage("تمت عملية الحذف بنجاح");
        alertDialog.setIcon(R.drawable.tick);
        alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    public void btn_print(View view) {

        Do_print();
    }
    public void Do_print() {
if(ComInfo.ComNo==12){
        Intent i = new Intent(this,XPinter_Man_Balance_Qty.class);
        i.putExtra("OrderNo", Maxpo.getText().toString());

        startActivity(i);
    }
    else
{
    Intent i = new Intent(this,Convert_Man_Balance_Qty_To_Img.class);
    i.putExtra("OrderNo", Maxpo.getText().toString());

    startActivity(i);
}
    }

    public void btn_ConvertToInvoice(View view) {
        TextView pono = (TextView)findViewById(R.id.et_OrdeNo);

        String sal_inv =DB.GetValue(this,"Sal_invoice_Det","count(*)","Note ='"+pono.getText().toString()+"'");

   if (  !(Integer.parseInt(sal_inv)==0 || Integer.parseInt(sal_inv) ==-1)) {

        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setTitle("تسوية جرد المندوب");
        alertDialog.setMessage("لا يمكن التخزين يوجد بها فاتورة بيع");            // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.tick);
        alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

        // return;
    }else{

       Intent i = new Intent(getBaseContext(), Sale_InvoiceActivity.class);
       i.putExtra("BalanceQtyOrderNo", Maxpo.getText().toString());
       i.putExtra("from", "0");
       i.putExtra("id", pono.getText().toString());
       startActivity(i);


   }




    }

    public void btn_ConvertTReturn(View view) {

        TextView pono = (TextView)findViewById(R.id.et_OrdeNo);

        String sal_ret =DB.GetValue(this,"Sal_return_Det","count(*)","Note ='"+pono.getText().toString()+"'");

        if (  !(Integer.parseInt(sal_ret)==0 || Integer.parseInt(sal_ret) ==-1)) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("تسوية جرد المندوب");
            alertDialog.setMessage("لا يمكن التخزين يوجد بها مرتجع ");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();

            // return;
        }else {


            Intent i = new Intent(ManBalanceQtyActivity.this, Sale_ReturnActivity.class);
            i.putExtra("BalanceQtyOrderNo", Maxpo.getText().toString());
            i.putExtra("from", "0");
            i.putExtra("id", pono.getText().toString());
            startActivity(i);

        }
    }
}
