package com.cds_jo.GalaxySalesApp.TQNew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.We_Result;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import Methdes.MyTextView;

public class cls_Tab__Order_Sales_adapter1 extends BaseAdapter {
    ArrayList<ContactListItems1> contactList;
    public ProgressDialog loadingdialog;
    View convertView2;
    Tab_SalesOrders1 tab_salesOrders;
    boolean done;
    Context context;
    ArrayList<cls_Tab_Order_Sales1> cls_Tab_Sales;

    public cls_Tab__Order_Sales_adapter1(Context context, ArrayList<cls_Tab_Order_Sales1> list,Tab_SalesOrders1 tab_salesOrders) {

        this.context = context;
        cls_Tab_Sales = list;
        this.tab_salesOrders=tab_salesOrders;
    }

    @Override
    public int getCount() {

        return cls_Tab_Sales.size();
    }

    @Override
    public Object getItem(int position) {

        return cls_Tab_Sales.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        final cls_Tab_Order_Sales1 cls_search_po = cls_Tab_Sales.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cls_tab_sales_order_row1, null);
            convertView2 = inflater.inflate(R.layout.cls_tab_sales_order_row1, null);

        }

        LinearLayout RR=(LinearLayout)convertView.findViewById(R.id.RR1);
        MyTextView tvcustNo = (MyTextView) convertView.findViewById(R.id.tv_no);
        tvcustNo.setText(cls_search_po.getCustNo());

        MyTextView tvCustNm = (MyTextView) convertView.findViewById(R.id.tv_nm);
        tvCustNm.setText(cls_search_po.getCustNm());

        MyTextView tvDate = (MyTextView) convertView.findViewById(R.id.tvDate);
        tvDate.setText(cls_search_po.getDate());

        MyTextView tvacc = (MyTextView) convertView.findViewById(R.id.tv_AccNo);
        tvacc.setText(cls_search_po.getAcc());


        MyTextView tv_tot = (MyTextView) convertView.findViewById(R.id.tv_tot);
        tv_tot.setText(cls_search_po.getTot());

/*
        /*TextView tv_invoType = (TextView) convertView.findViewById(R.id.tv_invoType);
         if (cls_search_po.getType().equals("-3")){
             tv_invoType.setText("نوع الفاتورة");
         }
       else if(cls_search_po.getType().equals("0")) {
           tv_invoType.setText("نقدية");
       }
       else {
           tv_invoType.setText("ذمــم");
       }
*/
        MyTextView tv_notes = (MyTextView) convertView.findViewById(R.id.tv_notes);
        //   if(ComInfo.Lan.equalsIgnoreCase("en")) {
         /*  if (cls_search_po.getNotes().equals("-3")) {
               tv_notes.setText("حالة الفاتورة");
           } else if (cls_search_po.getNotes().equals("-1")) {
               tv_notes.setText("غير مرحلة");
           } else {
               tv_notes.setText("مرحلة");
           }*/
        tv_notes.setText(cls_search_po.getNotes());
       /*}else    {

           if (cls_search_po.getNotes().equals("-3")){
               tv_notes.setText("status");
           }
           else if(cls_search_po.getNotes().equals("-1")) {
               tv_notes.setText("Unposted");
           }
           else {
               tv_notes.setText("Unposted");
           }

       }*/
        if(position%2==0)
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Gray2));
        }

        else
        {
            RR.setBackgroundColor(Color.WHITE);
        }

        final Button share = (Button) convertView.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context,cls_search_po.getCustNo()+"",Toast.LENGTH_SHORT).show();

                // Do_Share();
           /* Bundle bundle = new Bundle();
            bundle.putString("Scr", "po");
            bundle.putString("OrderNo",cls_search_po.getCustNo());
            FragmentManager Manager = ((ManSummeryActivity)context).getFragmentManager();
            PopShowStoreQtyReport obj = new PopShowStoreQtyReport();
            obj.setArguments(bundle);
            obj.show(Manager, null);*/

                if( Shere_Recod_Po(cls_search_po.getCustNo(),cls_search_po.getAcc(),cls_search_po.custNo,cls_search_po.custNm,cls_search_po.Tot,  convertView2)){
                    share.setText("تم الاعتماد");
                    share.setEnabled(false);
                }


            }
        });


        return convertView;
    }

    public boolean Shere_Recod_Po(String po,String acc,String cno,String cname,String tot,View v) {
        contactList = new ArrayList<ContactListItems1>();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());
        SqlHandler sqlHandler = new SqlHandler(context);
        String json;

        String q="select * from Po_dtl where orderno='" + po +"'";
        q = "  select Distinct Unites.UnitName, pod.OrgPrice ,pod.SPrice," +
                "  ifnull(invf.Item_Name,'Name Not Found')as Item_Name," +
                " pod.itemno,pod.price,pod.qty,pod.tax ,pod.unitNo" +
                " ,pod.dis_Amt,pod.dis_per,pod.bounce_qty ,  pod.tax_Amt" +
                "   , pod.total  " +
                " , pod.pro_Total    , pod.ProID , pod.Pro_bounce  ," +
                "pod.Pro_dis_Per,  pod.Pro_amt " +
                "    from Po_dtl pod left join invf on invf.Item_No =  pod.itemno " +
                "   left join Unites on Unites.Unitno=  pod.unitNo " +
                " Where  pod.orderno='" + po + "'";
        q = "  select Distinct Unites.UnitName, pod.OrgPrice ,pod.SPrice,  ifnull(invf.Item_Name,'Name Not Found')as Item_Name, pod.itemno,pod.price,pod.qty,pod.tax ,pod.unitNo ,pod.dis_Amt,pod.dis_per,pod.bounce_qty ,  pod.tax_Amt   , pod.total  " +
                " , pod.pro_Total    , pod.ProID , pod.Pro_bounce  ,pod.Pro_dis_Per,  pod.Pro_amt     from Po_dtl pod left join invf on invf.Item_No =  pod.itemno    left join Unites on Unites.Unitno=  pod.unitNo  Where pod.orderno='" + po + "'";

        Cursor c1 = sqlHandler.selectQuery(q);

        if (c1 != null && c1.getCount() != 0) {
            //   Toast.makeText(this, c1.getCount()+"  c1 ",Toast.LENGTH_LONG).show();
            if (c1.moveToFirst()) {
                do {
                    ContactListItems1 contactListItems = new ContactListItems1();

                    contactListItems.setno(c1.getString(c1
                            .getColumnIndex("itemno")));
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

                    contactListItems.setDis_Amt(c1.getString(c1
                            .getColumnIndex("dis_Amt")));

                    contactListItems.setUnite(c1.getString(c1
                            .getColumnIndex("unitNo")));

                    contactListItems.setTax_Amt(c1.getString(c1
                            .getColumnIndex("tax_Amt")));

                    contactListItems.setTotal(c1.getString(c1
                            .getColumnIndex("total")));

                    contactListItems.setSPrise(c1.getString(c1
                            .getColumnIndex("SPrice")));

                    contactListItems.setProID("");

                    contactListItems.setPro_bounce("0");

                    contactListItems.setPro_dis_Per("0");
                    contactListItems.setBatch("");
                    contactListItems.setExpDate("");
                    contactListItems.setOperand("1");

                    contactListItems.setPro_amt("0");

                    contactListItems.setPro_Total("0");
                    contactListItems.setDisAmtFromHdr("0");

                    contactListItems.setDisPerFromHdr("0");

                    contactList.add(contactListItems);

                } while (c1.moveToNext());

            }

            c1.close();
        }

        json = new Gson().toJson(contactList);

        return  Do_Share(po,json,cno,cname,tot,v);

    }
    private  boolean Do_Share (final String po, String jsonn, String cno, String cname, String tot,View v){

        final Button share=(Button)v.findViewById(R.id.share);
        final SqlHandler sqlHandler = new SqlHandler(context);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        final String str;



        String query = "SELECT Distinct  ifnull(OwnerSalesManNm,'') as  OwnerSalesManNm ,  ifnull(OwnerSalesManNo,'') as OwnerSalesManNo,   acc,  date ,Delv_day_count FROM Po_Hdr where orderno  ='" + po + "'";
        Cursor c1 = sqlHandler.selectQuery(query);
        String  Date, Cust_No, Delv_day_count;
        Date = Delv_day_count = "";


        String UserID = sharedPreferences.getString("UserID", "");

        Cust_No = DB.GetValue(context, "Po_Hdr", "acc", "orderno ='" + po+ "'");
        String Total = DB.GetValue(context, "Po_Hdr", "Total", "orderno ='" + po+ "'");
        String Net_Total = DB.GetValue(context, "Po_Hdr", "Total", "Net_Total ='" + po+ "'");
        String Tax_Total = DB.GetValue(context, "Po_Hdr", "Tax_Total", "orderno ='" + po+ "'");
        String ManLevel = DB.GetValue(context, "Po_Hdr", "ManLevel", "orderno ='" + po+ "'");
        String OwnerSalesManNm = DB.GetValue(context, "Po_Hdr", "OwnerSalesManNm", "orderno ='" + po+ "'");

        String ForApproval = DB.GetValue(context, "Po_Hdr", "ForApproval", "orderno ='" + po+ "'");
        String OwnerSalesManNo = DB.GetValue(context, "Po_Hdr", "OwnerSalesManNo", "orderno ='" + po+ "'");
        String MobileOrder = DB.GetValue(context, "Po_Hdr", "MobileOrder", "orderno ='" + po+ "'");
        String include_Tax = DB.GetValue(context, "Po_Hdr", "include_Tax", "orderno ='" + po+ "'");
        String disc_Total = DB.GetValue(context, "Po_Hdr", "disc_Total", "orderno ='" + po+ "'");

        String  LoginAccount = sharedPreferences.getString("LoginAccount", "");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String currentDateandTime2 = sdf2.format(new Date());


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Cust_No", Cust_No);
            jsonObject.put("day_Count", Delv_day_count == null ? "null" : Delv_day_count.toString());
            jsonObject.put("Date", currentDateandTime2);
            jsonObject.put("UserID", UserID);
            jsonObject.put("OrderNo",po);

            jsonObject.put("Total", Total);
            jsonObject.put("Net_Total", tot);
            jsonObject.put("Tax_Total",Tax_Total);
            jsonObject.put("bounce_Total", "0");
            jsonObject.put("OwnerSalesManNm", cname);

            if (OwnerSalesManNo.equalsIgnoreCase("")) {
                jsonObject.put("OwnerSalesManNo", LoginAccount);
            } else {
                jsonObject.put("OwnerSalesManNo", OwnerSalesManNo);
            }

            jsonObject.put("disc_Total", disc_Total);


            jsonObject.put("include_Tax", include_Tax);

            jsonObject.put("MobileOrder", MobileOrder);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        String json = new Gson().toJson(contactList);
        str = jsonObject.toString() + jsonn;


        loadingdialog = ProgressDialog.show(context, context.getResources().getText(R.string.PleaseWait), context.getResources().getText(R.string.PostUnderProccess), true);
        loadingdialog.setCancelable(true);
        loadingdialog.setCanceledOnTouchOutside(true);
        loadingdialog.show();
        final Handler _handler = new Handler();


        // Toast.makeText(getApplicationContext(),str, Toast.LENGTH_LONG).show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                CallWebServices ws = new CallWebServices(context);
                ws.Save_po(str, "Insert_PurshOrder");
                try {

                    if (We_Result.ID > 0) {

                        sqlHandler.executeQuery("Update Po_Hdr set posted ='"+We_Result.ID +"' Where orderno ='"+po+"'");

                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        context).create();
                                alertDialog.setTitle(context.getResources().getText(R.string.Ordersales));
                                ContentValues cv = new ContentValues();
                                cv.put("posted", We_Result.ID);
                                sqlHandler.executeQuery("Delete from Po_Hdr where orderno='" + po + "'");
                                sqlHandler.executeQuery("Delete from Po_dtl where orderno='" + po+ "'");
                                long i;
                                alertDialog.setMessage("تمت عملية الاعتماد  بنجاح");
                                //alertDialog.setMessage(getResources().getText(R.string.PostCompleteSuccfully) + "" + We_Result.ID + "");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                loadingdialog.dismiss();

                                share.setText("تم الاعتماد");
                                share.setBackgroundColor(Color.parseColor("#738b28"));
                                share.setEnabled(false);
                                share.setText("تم الاعتماد");
                                share.setBackgroundColor(Color.parseColor("#738b28"));
                                share.setEnabled(false);
                                tab_salesOrders.FillList();
                                notifyDataSetChanged();

                                try {
                                    share.setText("تم الاعتماد");
                                    share.setBackgroundColor(Color.parseColor("#738b28"));
                                    share.setEnabled(false);

                                    alertDialog.show();
                                }catch (Exception ecx){}

                                done=true;

                            }
                        });
                    } else {

                        loadingdialog.dismiss();
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        context).create();
                                if (We_Result.ID == -5) {
                                    alertDialog.setTitle("اعتماد طلب البيع");
                                    alertDialog.setMessage("تم اعتماد الطلب من قبل المشرف");
                                    alertDialog.setIcon(R.drawable.error_new);
                                    alertDialog.setButton("رجوع", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    try {
                                        alertDialog.show();
                                    }catch (Exception ecx){}


                                } else {

                                    alertDialog.setTitle("طلب البيع")   ;
                                    if (We_Result.Msg.toString().length()>1) {
                                        alertDialog.setMessage(We_Result.Msg.toString());
                                    }else{
                                        alertDialog.setMessage(context.getResources().getText(R.string.PostNotCompleteSuccfully));
                                    }
                                    alertDialog.setIcon(R.drawable.tick);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    try {
                                        alertDialog.show();
                                    }catch (Exception ecx){}

                                    alertDialog.setIcon(R.drawable.error_new);

                                }
                            }
                        });
                    }

                } catch (final Exception e) {
                    loadingdialog.dismiss();
                    _handler.post(new Runnable() {
                        public void run() {

                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    context).create();
                            alertDialog.setTitle(context.getResources().getText(R.string.ConnectError));
                            alertDialog.setMessage(e.getMessage().toString());
                            alertDialog.setIcon(R.drawable.tick);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

                            try {
                                alertDialog.show();
                            }catch (Exception ecx){}
                        }
                    });
                }
            }
        }).start();


        return done;
    }

}

