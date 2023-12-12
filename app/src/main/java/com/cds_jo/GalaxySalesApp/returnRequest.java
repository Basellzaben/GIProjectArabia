package com.cds_jo.GalaxySalesApp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.PostTransActions.PostPayments;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.cds_jo.GalaxySalesApp.assist.Logtrans.InsertLogTrans;
import com.cds_jo.GalaxySalesApp.assist.Sale_InvoiceActivity;
import com.cds_jo.GalaxySalesApp.assist.Sale_ReturnActivity;
import com.google.gson.Gson;

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
import java.util.Locale;

import Methdes.MyTextView;
import header.Header_Frag;

public class returnRequest extends AppCompatActivity {
    String CatNo = "-1";
    int position;
    ListView lvCustomList;
    boolean IsNew;
    Integer DoPrint = 0, DocType = 2, DocTypeinv = 1;
    long PostResult = 0;

    SqlHandler sqlHandler;
    String UserID;
    ArrayList<Cls_Sal_InvItems> contactList;
    int po;
    public ProgressDialog loadingdialog;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Cls_Sal_InvItems contactListItems = new Cls_Sal_InvItems();
        menu.setHeaderTitle(contactList.get(position).getName());
        po = position;
        menu.add(Menu.NONE, 1, Menu.NONE, "تعديل");
        menu.add(Menu.NONE, 2, Menu.NONE, "حذف");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case 1: {

                ArrayList<Cls_Sal_InvItems> Itemlist = new ArrayList<Cls_Sal_InvItems>();

                Itemlist.add(contactList.get(position));
               /* if (contactList.get(position).getProType().equals("3")) {
                    break;
                }*/


                TextView accno = (TextView) findViewById(R.id.tv_acc);

                Bundle bundle = new Bundle();
                bundle.putString("Scr", "returnRequest");
                bundle.putString("CatNo", CatNo);
                bundle.putString("OrderNo", pono.getText().toString());
                bundle.putString("IsNew", "");
                bundle.putString("CustomerNo", accno.getText().toString());
                bundle.putSerializable("List", Itemlist);
                FragmentManager Manager = getFragmentManager();
                PopSal_return_Select_Items obj = new PopSal_return_Select_Items();
                obj.setArguments(bundle);
                obj.show(Manager, null);

            }
            break;
            case 2: {
                SqlHandler sqlHandler = new SqlHandler(this);

                String q1 = "Select * From ReturnRequestHDR Where orderno='" + pono.getText().toString() + "'";
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
                    Dialog.setTitle("tv_ScrTitle");
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
                    alertDialog.setTitle("إرجاع المواد");
                    alertDialog.setMessage("هل انت متاكد من عملية الحذف");
                    alertDialog.setIcon(R.drawable.delete);
                    alertDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            contactList.remove(position);

                            showList();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_request);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        lvCustomList = (ListView) findViewById(R.id.LstvItems);
        contactList = new ArrayList<Cls_Sal_InvItems>();
        UserID = sharedPreferences.getString("UserID", "");

        Fragment frag = new Header_Frag();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Frag1, frag).commit();
        TextView accno = (TextView) findViewById(R.id.tv_acc);
        TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
        accno.setText(sharedPreferences.getString("CustNo", ""));
        CustNm.setText(sharedPreferences.getString("CustNm", ""));
        Get_CatNo(accno.getText().toString());
        GetMaxPONo();
    }

    public void btn_show_Pop(View view) {
        showPop();
    }

    public void showPop() {
        EditText Order = (EditText) findViewById(R.id.et_OrdeNo);
        TextView accno = (TextView) findViewById(R.id.tv_acc);
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "returnRequest");
        bundle.putString("IsNew", "");
        bundle.putString("CatNo", CatNo);
        bundle.putString("OrderNo", Order.getText().toString());
        bundle.putString("CustomerNo", accno.getText().toString());

        //Bundle bundle=new Bundle();
        FragmentManager Manager = getFragmentManager();
        PopSal_return_Select_Items obj = new PopSal_return_Select_Items();
        obj.setArguments(bundle);
        obj.show(Manager, null);

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

    public void Update_List(String ItemNo, String p, String q, String t, String u, String ItemNm, String UnitName, String dis_Amt, String Operand, String Weight, String Damaged, String Note) {

        if (dis_Amt.toString().equals(""))
            dis_Amt = "0";

        if (p.toString().equals(""))
            p = "0";

        if (q.toString().equals(""))
            q = "0";

        if (Weight.toString().equals(""))
            Weight = "0";

        Double Item_Total, Price, Tax_Amt, Tax, Total, Net_Total, Tax_Total;

        Item_Total = Double.parseDouble(q) * Double.parseDouble(p);
        Price = Double.parseDouble(p);
        Tax = Double.parseDouble(t);
        Item_Total = Double.parseDouble(Item_Total.toString());
        Double DisValue = Double.parseDouble(dis_Amt.toString().replace(",", ""));


        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;

        Cls_Sal_InvItems contactListItems = new Cls_Sal_InvItems();
        contactListItems = contactList.get(po);
        contactListItems.setno(ItemNo);
        contactListItems.setName(contactListItems.getName());
        if (1 == 1) {
            contactListItems.setprice(String.valueOf(Price / ((Tax / 100) + 1)));
        } else {
            contactListItems.setprice(String.valueOf(Price));

        }
        //contactListItems.setprice(String.valueOf(Price));
        contactListItems.setItemOrgPrice(String.valueOf(Price));
        contactListItems.setQty(q);
        contactListItems.setTax(String.valueOf(Tax));
        contactListItems.setUnite(u);
        contactListItems.setDis_Amt(DisValue.toString());
        contactListItems.setProID("");
        contactListItems.setDis_Amt(dis_Amt);
        contactListItems.setUniteNm(UnitName);
        contactListItems.setPro_amt("0");
        contactListItems.setPro_dis_Per("0");
        contactListItems.setPro_bounce("0");
        contactListItems.setPro_Total("0");
        contactListItems.setDisAmtFromHdr("0");
        contactListItems.setDisPerFromHdr("0");
        contactListItems.setTax_Amt("0");
        contactListItems.setProType("0");
        contactListItems.setOperand(Operand);
        contactListItems.setWeight(Weight);
        contactListItems.setTotal(String.valueOf(df.format(Item_Total)));
        contactListItems.setDamaged(Damaged);
        contactListItems.setNote(Note);
        // contactList.add(contactListItems);

        //  CalcTotal();
        showList();


        // Gf_Calc_Promotion();
    }

    private void showList() {
        lvCustomList.setAdapter(null);
        returnRequestAdapter contactListAdapter = new returnRequestAdapter(
                returnRequest.this, contactList);
        lvCustomList.setAdapter(contactListAdapter);
    }

    public void btn_Delete_Item(final View view) {
        position = lvCustomList.getPositionForView(view);
        registerForContextMenu(view);
        openContextMenu(view);
    }

    public void Save_List(String ItemNo, String p, String q, String t, String u, String dis, String bounce, String ItemNm, String UnitName, String dis_Amt, String Operand, String Weight, String Damaged, String Note, String dis1) {
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
        for (int x = 0; x < contactList.size(); x++) {
            Cls_Sal_InvItems contactListItems = new Cls_Sal_InvItems();
            contactListItems = contactList.get(x);
            if (contactListItems.getNo().equals(ItemNo) && contactListItems.getProType() != "3") {
                AlertDialog alertDialog = new AlertDialog.Builder(
                        this).create();
                alertDialog.setTitle("tv_ScrTitle");
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
        Item_Total = Double.parseDouble(q) * Double.parseDouble(p);
        Price = Double.parseDouble(p);
        Tax = Double.parseDouble(t);
        Item_Total = Double.parseDouble(Item_Total.toString());
        Double DisValue = Double.parseDouble(dis_Amt.toString().replace(",", ""));
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;
        Cls_Sal_InvItems contactListItems = new Cls_Sal_InvItems();
        contactListItems.setno(ItemNo);
        contactListItems.setName(ItemNm);
        if (1 == 1) {
            contactListItems.setprice(String.valueOf(Price / ((Tax / 100) + 1)));
        } else {
            contactListItems.setprice(String.valueOf(Price));
        }
        contactListItems.setItemOrgPrice(String.valueOf(Price));
        contactListItems.setQty(q);
        contactListItems.setTax(String.valueOf(Tax));
        contactListItems.setUnite(u);
        contactListItems.setBounce(bounce);
        contactListItems.setDiscount(dis1);
        contactListItems.setProID("0");
        contactListItems.setDis_Amt(dis_Amt);
        contactListItems.setUniteNm(UnitName);
        contactListItems.setPro_amt("0");
        contactListItems.setPro_dis_Per("0");
        contactListItems.setPro_bounce("0");
        contactListItems.setPro_Total("0");
        contactListItems.setDisAmtFromHdr("0");
        contactListItems.setDisPerFromHdr("0");
        contactListItems.setTax_Amt("0");
        contactListItems.setProType("0");
        contactListItems.setOperand(Operand);
        contactListItems.setWeight(Weight);
        contactListItems.setTotal(String.valueOf(df.format(Item_Total)));
        contactListItems.setDamaged(Damaged);
        contactListItems.setNote(Note);
        contactList.add(contactListItems);
        // CalcTotal();
        showList();
    }
/*

    public void btn_save_po(final View view) {


        if (customerstate().equalsIgnoreCase("1")) {


            CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
            TextView accno = (TextView) findViewById(R.id.tv_acc);
            String HowPay = DB.GetValue(returnRequest.this, "Customers", "Pay_How", "no ='" + accno.getText().toString() + "' ");
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
            TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);

            Double TempTotal = 0.0;

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();


            final TextView tv_acc = (TextView) findViewById(R.id.tv_acc);
            ///////////////////////////////////////////////////

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String Count = sharedPreferences.getString("InvCount", "0");
            String NumOfInvPerVisit = DB.GetValue(returnRequest.this, "ComanyInfo", "NumOfInvPerVisit  ", "1=1");

            String q = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
            String currentDateandTime = sdf.format(new Date());


            String RecVoucher_DocNo = DB.GetValue(returnRequest.this, "RecVoucher", "DocNo", "CustAcc ='" + tv_acc.getText() + "' AND ((TrDate)=('" + currentDateandTime + "'))");
            String PAMENT_PERIOD_NO = DB.GetValue(returnRequest.this, "Customers", "PAMENT_PERIOD_NO", "no ='" + tv_acc.getText() + "' ");


            final TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
            q = "SELECT distinct *  from  Sal_return_Hdr where Post >0 AND   Orderno ='" + pono.getText().toString() + "'";
            TextView acc = (TextView) findViewById(R.id.tv_acc);
            Cursor c1 = sqlHandler.selectQuery(q);
            if (c1 != null && c1.getCount() != 0) {


                alertDialog.setTitle("ارجاع المواد");
                alertDialog.setMessage("لقد تم ترحيل المستند لايمكن التعديل");
                alertDialog.setIcon(R.drawable.tick);
                alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;

                    }
                });

                alertDialog.show();


                c1.close();
                return;
            } else {


                String Msg = "";

                final TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
                AlertDialog.Builder alertDialogYesNo = new AlertDialog.Builder(this);

                if (ComInfo.ComNo == 1) {
                    q = "SELECT distinct *  from  Sal_return_Hdr where acc  ='" + acc.getText() + "'   AND   date  ='" + currentDateandTime + "' " +
                            " And   Orderno !='" + pono.getText().toString() + "'";

                    c1 = sqlHandler.selectQuery(q);
                    if (c1 != null && c1.getCount() != 0) {
                        Msg = "يوجد فاتورة لنفس هذا العميل في نفس هذا اليوم  " + "\n\r";
                        c1.close();

                    }

                }
                if (contactList.size() == 0) {


                    alertDialog.setTitle("ارجاع المواد");
                    alertDialog.setMessage("لا يمكن تخزين المستند بدون مواد");            // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.delete);
                    alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;

                        }
                    });

                    alertDialog.show();

                    return;

                }


                alertDialogYesNo.setTitle("tv_ScrTitle");
                alertDialogYesNo.setMessage(Msg + "  " + "هل  تريد الاستمرار بعملية الحفظ " + "؟");

                // Setting Icon to Dialog
                alertDialogYesNo.setIcon(R.drawable.save);

                alertDialogYesNo.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

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

                        if (DocType == 2 || ComInfo.ComNo == Companies.beutyLine.getValue()) {
                            Save_Recod_Po();
                        }
                    }
                });


                alertDialogYesNo.show();


            }
        } else {

            Toast.makeText(returnRequest.this, "هذا العميل ملغي , لا يمكن عمل مرتجع مبيعات", Toast.LENGTH_SHORT).show();

        }
    }
*/

    public String customerstate() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView tv_acc = (TextView) findViewById(R.id.tv_acc);
        TextView tv_CustStatus = (TextView) findViewById(R.id.tv_CustStatus);

        String q = " select distinct      Tax_Status , Location,State  " +
                " from Customers where no='" + sharedPreferences.getString("CustNo", "") + "'";
        sqlHandler = new SqlHandler(returnRequest.this);

        String State = "";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            c1.moveToFirst();
            State = c1.getString(c1.getColumnIndex("State"));
            c1.close();
        }
    /*    if((State.equals("1") || State.equals("2") ||State.equals("2")))
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

    public void btn_save_po(final View v) {
        if (contactList.size() < 1) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("طلب المرتجع");
            alertDialog.setMessage("لا يمكن حفظ البطاقة، يجب ادخال المواد");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
            //  c1.close();//7
            //  return;
            return;
        }

        sqlHandler = new SqlHandler(returnRequest.this);

        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        String q = "SELECT Distinct *  from  ReturnRequestHDR where   posted !='0' AND   orderno ='" + pono.getText().toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("طلب المرتجع");
            alertDialog.setMessage("لا يمكن التعديل على البطاقة، لقد تم ترحيل الطلب");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
            //  c1.close();//7
            //  return;
            return;
        }


        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView acc = (TextView) findViewById(R.id.tv_acc);

        TextView Total = (TextView) findViewById(R.id.et_Total);
        TextView dis = (TextView) findViewById(R.id.et_dis);
        TextView NetTotal = (TextView) findViewById(R.id.tv_NetTotal);
        TextView TotalTax = (TextView) findViewById(R.id.et_TotalTax);

        CheckBox Tax_Include = (CheckBox) findViewById(R.id.chk_Tax_Include);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());


        Long i;
        ContentValues cv = new ContentValues();
        cv.put("orderno", pono.getText().toString());
        cv.put("custno", acc.getText().toString());
        cv.put("date", currentDateandTime);
        cv.put("userid", UserID);
        cv.put("custname", custNm.getText().toString());


        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
        if (chk_Type.isChecked())
            cv.put("custtype", "1");
        else
            cv.put("custtype", "0");
        cv.put("posted", "0");


        SqlHandler sqlHandler = new SqlHandler(this);

        String q1 = "Select * From ReturnRequestHDR Where orderno='" + pono.getText().toString() + "'";
        c1 = sqlHandler.selectQuery(q1);

        if (c1 != null && c1.getCount() != 0) {
            IsNew = false;
            c1.close();
        } else {
            IsNew = true;
        }


        if (IsNew == true) {
            i = sqlHandler.Insert("ReturnRequestHDR", null, cv);
        } else {
            i = sqlHandler.Update("ReturnRequestHDR", cv, "orderno ='" + pono.getText().toString() + "'");
        }


        if (i > 0) {
            q = "Delete from  ReturnRequestDTL where orderno ='" + pono.getText().toString() + "'";
            sqlHandler.executeQuery(q);

            for (int x = 0; x < contactList.size(); x++) {
                Cls_Sal_InvItems contactListItems = new Cls_Sal_InvItems();
                contactListItems = contactList.get(x);


                cv = new ContentValues();
                cv.put("orderno", pono.getText().toString());
                cv.put("itemno", contactListItems.getNo());
                cv.put("itemname", contactListItems.getName().toLowerCase());
                cv.put("qt", contactListItems.getQty().toString());
                cv.put("unit", contactListItems.getUnite().toString());
                cv.put("note", contactListItems.getNote().toString());
                i = sqlHandler.Insert("ReturnRequestDTL", null, cv);
            }
        }

        if (i > 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("Galaxy");
            alertDialog.setMessage("تمت عملية  الحفظ بنجاح");

            lvCustomList.setAdapter(null);


            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Boolean ApproveInvoicesDirectly = Boolean.parseBoolean(DB.GetValue(returnRequest.this, "manf", "ApproveInvoicesDirectly", "man ='" + sharedPreferences.getString("UserID", "0") + "'"));


            alertDialog.setIcon(R.drawable.tick);

            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            // Showing Alert Message
            alertDialog.show();

            if (ApproveInvoicesDirectly) {
                IsNew = false;
                DoShare();
            } else {
                contactList.clear();
                GetMaxPONo();
                showList();
                DoNew();
            }
        }
    }

    public void GetMaxPONo() {
        TextView OrderNo = (TextView) findViewById(R.id.et_OrdeNo);
        sqlHandler = new SqlHandler(returnRequest.this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String UserID = sharedPreferences.getString("UserID", "");

        String query = "SELECT  Distinct  COALESCE(MAX(orderno), 0) +1 AS no FROM ReturnRequestHDR where userid ='" + UserID + "'";
        Cursor c1 = sqlHandler.selectQuery(query);
        String max = "0";

        if (c1 != null && c1.getCount() > 0) {
            c1.moveToFirst();
            max = c1.getString(c1.getColumnIndex("no"));
            c1.close();
        }

        String max1 = "0";
        max1 = DB.GetValue(returnRequest.this, "OrdersSitting", "RetReqSales", "1=1").replaceAll("[^\\d.]", "");
        if (max1 == "") {
            max1 = "0";
        }
        max1 = String.valueOf(Integer.parseInt(max1) + 1);
        if (SToD(max1) > SToD(max)) {
            max = max1;
        }

        if (max.length() == 1) {
            OrderNo.setText(intToString(Integer.valueOf(UserID), 2) + intToString(Integer.valueOf(max), 5));

        } else {
            OrderNo.setText(intToString(Integer.valueOf(max), 7));

        }


        OrderNo.setFocusable(false);
        OrderNo.setEnabled(false);
        OrderNo.setCursorVisible(false);


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

    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
    }

    public void btn_Search_Orders(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("src", "returnrequest");
        FragmentManager Manager = getFragmentManager();
        SearchCustStoreQtyActivity obj = new SearchCustStoreQtyActivity();
        obj.setArguments(bundle);
        obj.show(Manager, null);
    }

    public void Set_Order(String No, String Nm, String acc) {

        TextView CustNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView Order_no = (TextView) findViewById(R.id.et_OrdeNo);
        TextView accno = (TextView) findViewById(R.id.tv_acc);
        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);


        Order_no.setText(No);

        CustNm.setText(Nm);
        accno.setText(acc);
        chk_Type.setChecked(false);
        contactList.clear();
        showList();

        sqlHandler = new SqlHandler(this);


        String query = "  select  *  from ReturnRequestHDR  where orderno ='" + Order_no.getText().toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                if (c1.getString(c1.getColumnIndex("custtype")).toString().equals("1")) {
                    chk_Type.setChecked(true);
                }

            }
        }
        c1.close();

        query = "  select  *  from ReturnRequestDTL  where orderno ='" + Order_no.getText().toString() + "'";
        c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {

                    String UintName = DB.GetValue(returnRequest.this,
                            "Unites", "UnitName", "Unitno='" + c1.getString(c1
                                    .getColumnIndex("unit")) + "'");


                    Cls_Sal_InvItems contactListItems = new Cls_Sal_InvItems();

                    contactListItems.setNo(c1.getString(c1
                            .getColumnIndex("itemno")));

                    contactListItems.setName(c1.getString(c1
                            .getColumnIndex("itemname")));

                    contactListItems.setQty(c1.getString(c1
                            .getColumnIndex("qt")));

                    contactListItems.setUniteNm(UintName);

                    contactListItems.setNote(c1.getString(c1
                            .getColumnIndex("note")));

                    contactListItems.setUnite(c1.getString(c1
                            .getColumnIndex("unit")));


                    contactList.add(contactListItems);

                } while (c1.moveToNext());

            }
        }
        c1.close();


        showList();
        IsNew = false;
    }

    public void btn_delete(View view) {
        TextView OrderNo = (TextView) findViewById(R.id.et_OrdeNo);

        //   Delete_Record_PO();
        String q = "SELECT Distinct *  from  ReturnRequestHDR where   Posted !='0' AND   orderno ='" + OrderNo.getText().toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("طلب المرتجع");
            alertDialog.setMessage("لا يمكن التعديل  لقد تم ترحيل الطلب");            // Setting Icon to Dialog
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
            alertDialog.setTitle("طلب المرتجع");
            alertDialog.setMessage("هل انت متاكد من عملية الحذف");
            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Delete_Record_PO();

                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });


            alertDialog.show();
        }
    }

    public void Delete_Record_PO() {
        TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        TextView acc = (TextView) findViewById(R.id.tv_acc);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String currentDateandTime = sdf.format(new Date());


        String query = "Delete from  ReturnRequestHDR where orderno ='" + pono.getText().toString() + "'";
        sqlHandler.executeQuery(query);


        query = "Delete from  ReturnRequestdtl where orderno ='" + pono.getText().toString() + "'";
        sqlHandler.executeQuery(query);
        contactList.clear();
        GetMaxPONo();
        showList();
        IsNew = true;
        custNm.setText("");
        acc.setText("");

        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();
        alertDialog.setTitle("Galaxy");
        alertDialog.setMessage("تمت عملية الحذف بنجاح");
        alertDialog.setIcon(R.drawable.tick);
        alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    public void btn_new(View view) {
        GetMaxPONo();
        showList();
        DoNew();
    }

    public void DoNew() {

        CheckBox chk_Type = (CheckBox) findViewById(R.id.chk_Type);
        chk_Type.setChecked(false);
        TextView acc = (TextView) findViewById(R.id.tv_acc);

        IsNew = true;
        contactList.clear();
        lvCustomList.setAdapter(null);
        returnRequestAdapter contactListAdapter = new returnRequestAdapter(
                returnRequest.this, contactList);
        lvCustomList.setAdapter(contactListAdapter);
    }

    public void DoShare() {
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        String q = "SELECT Distinct *  from  ReturnRequestHDR where   posted > '0' AND   orderno ='" + pono.getText().toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("طلب المرتجع");
            alertDialog.setMessage("لا يمكن التعديل على البطاقة، لقد تم ترحيل الطلب");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
            //  c1.close();//7
            //  return;
            return;
        }
        q = "SELECT Distinct *  from  ReturnRequestHDR where   posted > '-1' AND   orderno ='" + pono.getText().toString() + "'";
        c1 = sqlHandler.selectQuery(q);

        if ((c1 == null || c1.getCount() == 0) && IsNew) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("طلب المرتجع");
            alertDialog.setMessage("لا يمكن اعتماد الطلب قبل الحفظ");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
            //  c1.close();//7
            //  return;
            return;
        }

        final SqlHandler sql_Handler = new SqlHandler(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        pono = (TextView) findViewById(R.id.et_OrdeNo);

        final TextView acc = (TextView) findViewById(R.id.tv_acc);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        final String str;


        String query = "SELECT * FROM ReturnRequestHDR where orderno  ='" + pono.getText().toString() + "'";
        c1 = sqlHandler.selectQuery(query);
        String orderno = "", custno = "", custname = "", custtype = "", data = "", userid = "";

        if (c1.getCount() > 0) {
            c1.moveToFirst();
            orderno = c1.getString(c1.getColumnIndex("orderno"));
            custno = c1.getString(c1.getColumnIndex("custno"));
            custname = c1.getString(c1.getColumnIndex("custname"));
            custtype = c1.getString(c1.getColumnIndex("custtype"));
            userid = c1.getString(c1.getColumnIndex("userid"));


        }
        c1.close();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("OrderNo", orderno);
            jsonObject.put("CustNo", custno);
            jsonObject.put("CustName", custname);
            jsonObject.put("Customertype", custtype);
            jsonObject.put("UserID", userid);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        String json = new Gson().toJson(contactList);
        str = jsonObject.toString() + json;


        loadingdialog = ProgressDialog.show(returnRequest.this, "الرجاء الانتظار ...", "العمل جاري على اعتماد طلب البيع", true);
        loadingdialog.setCancelable(false);
        loadingdialog.setCanceledOnTouchOutside(false);
        loadingdialog.show();
        final Handler _handler = new Handler();


        // Toast.makeText(getApplicationContext(),str, Toast.LENGTH_LONG).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.print("kkkk: " + str + "     :kkkkk");
                CallWebServices ws = new CallWebServices(returnRequest.this);
                ws.save_ReturnRequest(str);
                try {

                    if (We_Result.ID > 0) {
                        ContentValues cv = new ContentValues();
                        TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
                        cv.put("posted", We_Result.ID);
                        long i;
                        i = sql_Handler.Update("ReturnRequestHDR", cv, "orderno='" + DocNo.getText().toString() + "'");

                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        returnRequest.this).create();
                                alertDialog.setTitle("اعتماد طلب المرتجع");
                                alertDialog.setMessage("تمت عملية اعتماد طلب المرتجع بنجاح" + We_Result.ID + "");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                loadingdialog.dismiss();
                                alertDialog.show();
                                alertDialog.show();
                                contactList.clear();
                                showList();


                            }
                        });
                    } else {

                        loadingdialog.dismiss();
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        returnRequest.this).create();
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
                                    returnRequest.this).create();
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

    public void btn_share(View view) {
        TextView pono = (TextView) findViewById(R.id.et_OrdeNo);
        String q = "SELECT Distinct *  from  ReturnRequestHDR where   posted > '0' AND   orderno ='" + pono.getText().toString() + "'";
        Cursor c1 = sqlHandler.selectQuery(q);
        if (c1 != null && c1.getCount() != 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("طلب المرتجع");
            alertDialog.setMessage("لا يمكن التعديل على البطاقة، لقد تم ترحيل الطلب");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
            //  c1.close();//7
            //  return;
            return;
        }
        q = "SELECT Distinct *  from  ReturnRequestHDR where   posted > '-1' AND   orderno ='" + pono.getText().toString() + "'";
        c1 = sqlHandler.selectQuery(q);

        if (c1 == null || c1.getCount() == 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();
            alertDialog.setTitle("طلب المرتجع");
            alertDialog.setMessage("لا يمكن اعتماد الطلب قبل الحفظ");            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("موافق", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
            //  c1.close();//7
            //  return;
            return;
        }

        final SqlHandler sql_Handler = new SqlHandler(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final TextView custNm = (TextView) findViewById(R.id.tv_cusnm);
        pono = (TextView) findViewById(R.id.et_OrdeNo);

        final TextView acc = (TextView) findViewById(R.id.tv_acc);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        final String str;


        String query = "SELECT * FROM ReturnRequestHDR where orderno  ='" + pono.getText().toString() + "'";
        c1 = sqlHandler.selectQuery(query);
        String orderno = "", custno = "", custname = "", custtype = "", data = "", userid = "";

        if (c1.getCount() > 0) {
            c1.moveToFirst();
            orderno = c1.getString(c1.getColumnIndex("orderno"));
            custno = c1.getString(c1.getColumnIndex("custno"));
            custname = c1.getString(c1.getColumnIndex("custname"));
            custtype = c1.getString(c1.getColumnIndex("custtype"));
            userid = c1.getString(c1.getColumnIndex("userid"));


        }
        c1.close();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("OrderNo", orderno);
            jsonObject.put("CustNo", custno);
            jsonObject.put("CustName", custname);
            jsonObject.put("Customertype", custtype);
            jsonObject.put("UserID", userid);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        String json = new Gson().toJson(contactList);
        str = jsonObject.toString() + json;


        loadingdialog = ProgressDialog.show(returnRequest.this, "الرجاء الانتظار ...", "العمل جاري على اعتماد طلب البيع", true);
        loadingdialog.setCancelable(false);
        loadingdialog.setCanceledOnTouchOutside(false);
        loadingdialog.show();
        final Handler _handler = new Handler();


        // Toast.makeText(getApplicationContext(),str, Toast.LENGTH_LONG).show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                CallWebServices ws = new CallWebServices(returnRequest.this);
                ws.save_ReturnRequest(str);
                try {

                    if (We_Result.ID > 0) {
                        ContentValues cv = new ContentValues();
                        TextView DocNo = (TextView) findViewById(R.id.et_OrdeNo);
                        cv.put("posted", We_Result.ID);
                        long i;
                        i = sql_Handler.Update("ReturnRequestHDR", cv, "orderno='" + DocNo.getText().toString() + "'");

                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        returnRequest.this).create();
                                alertDialog.setTitle("اعتماد طلب المرتجع");
                                alertDialog.setMessage("تمت عملية اعتماد طلب المرتجع بنجاح" + We_Result.ID + "");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                loadingdialog.dismiss();
                                alertDialog.show();
                                alertDialog.show();
                                contactList.clear();
                                showList();


                            }
                        });
                    } else {

                        loadingdialog.dismiss();
                        _handler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        returnRequest.this).create();
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
                                    returnRequest.this).create();
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

    public void btn_back(View view) {
        Intent k = new Intent(this, JalMasterActivity.class);
        startActivity(k);
    }

    @Override
    public void onBackPressed() {
        // This will be called either automatically for you on 2.0
        // or later, or by the code above on earlier versions of the
        // platform.
        return;
    }
}