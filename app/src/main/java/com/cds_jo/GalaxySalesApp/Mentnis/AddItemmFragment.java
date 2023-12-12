package com.cds_jo.GalaxySalesApp.Mentnis;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.Cls_Cust_Qty_Item_Adapter;
import com.cds_jo.GalaxySalesApp.ContactListItems;
import com.cds_jo.GalaxySalesApp.CustomerQty;
import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.JalMasterActivity;
import com.cds_jo.GalaxySalesApp.NewPackage.Cls_Keys_adapter;
import com.cds_jo.GalaxySalesApp.NewPackage.LocaleHelper;
import com.cds_jo.GalaxySalesApp.NewPackage.ViewVisitFrg;
import com.cds_jo.GalaxySalesApp.NewPackage.keys_modle;
import com.cds_jo.GalaxySalesApp.PopCus_Qty_Items;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class AddItemmFragment extends DialogFragment {

    SqlHandler sqlHandler;
    ListView lv_Items;
    ArrayList<ContactListItems> contactList ;
    EditText etItemNm, etPrice, etQuantity,etTax;
    Button btnsubmit;
    String UserID= "";
    String V_OrderNo= "";
    TextView tv;
    Drawable greenProgressbar;
    RelativeLayout.LayoutParams lp;
    public ProgressDialog loadingdialog;
    public   String json;
    Boolean IsNew;

    String transno;




        @Override
        public View onCreateView( LayoutInflater inflater   , ViewGroup container  ,Bundle savestate){

           View v =inflater.inflate(R.layout.fragment_add_itemm,container,false);


        lv_Items = (ListView) v.findViewById(R.id.LstvItems);
        sqlHandler = new SqlHandler(getActivity());
        IsNew = true;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        UserID= sharedPreferences.getString("UserID", "");



        contactList = new ArrayList<ContactListItems>();
        contactList.clear();


        transno=getArguments().getString("transno");
        String query = "";
        query="select  *  from Items_Maint where Trans_Id = '"+transno+"'";
        sqlHandler = new SqlHandler(getActivity());


        Bundle bundle = this.getArguments();

        ArrayList<ContactListItems> cls_invf_List =new ArrayList<ContactListItems>();
        cls_invf_List.clear();
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    String f;
                    ContactListItems keys_modle = new ContactListItems();
                    LocaleHelper LocaleHelper =new LocaleHelper();


                    keys_modle.setno(c1.getString(c1
                            .getColumnIndex("Item_No")));

                    keys_modle.setName(c1.getString(c1
                            .getColumnIndex("Item_Name")));
                    keys_modle.setQty(c1.getString(c1
                            .getColumnIndex("Qty")));

                    keys_modle.setUnite(c1.getString(c1
                            .getColumnIndex("Unit")));
                    String item2=DB.GetValue(getActivity(),"unites","UnitName","Unitno='"+c1.getString(c1
                            .getColumnIndex("Unit"))+"'");
                    keys_modle.setUniteNm(item2);





                    contactList.add(keys_modle);

                } while (c1.moveToNext());

            }
            c1.close();
        }
        FillKeysitems(contactList);

        // showList(0);








        return v;
    }
    public  void Save_List(String ItemNo, String q, String u, String ItemNm, String UnitName)
    {
        // Toast.makeText(CustomerQty.this,contactList.size()+"",Toast.LENGTH_LONG).show();
        for (int x = 0; x < contactList.size(); x++) {
            ContactListItems contactListItems = new ContactListItems();
            contactListItems = contactList.get(x);

            if ( contactListItems.getNo().equals(ItemNo)) {
                AlertDialog alertDialog = new AlertDialog.Builder(

                        getActivity()).create();

                alertDialog.setTitle("Galaxy");
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
      String    q1 = "insert into Items_Maint (Trans_Id,Item_No,Item_Name,Qty,Unit) values ('"
                +       transno
                + "','" + ItemNo
                + "','" + ItemNm
                + "','" + q
                + "','" + u


                + "')";
        sqlHandler.executeQuery(q1);

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat)nf;

        ContactListItems contactListItems = new ContactListItems();
        contactListItems.setno(ItemNo);
        contactListItems.setName(ItemNm);
        contactListItems.setUnite(u);
        contactListItems.setQty(q);
        contactListItems.setUniteNm(UnitName);
        contactList.add(contactListItems);


        FillKeysitems(contactList);




    }
    public void btn_show_Pop(View view) {
        showPop();
    }
    public  void showPop()
    {


        Bundle bundle = new Bundle();
        bundle.putString("Scr", "AddItem");

        FragmentManager Manager =  getFragmentManager();
        PopCus_Qty_Items obj = new PopCus_Qty_Items();
        obj.setArguments(bundle);
        obj.show(Manager, null);

    }
    private void FillKeysitems(ArrayList<ContactListItems> cls_invf_List) {
        //   filter = (EditText) form.findViewById(R.id.et_Search_filter);
        // String query = "";


        Cls_Cust_Qty_Item_Adapter contactListAdapter = new Cls_Cust_Qty_Item_Adapter(
                getActivity(), contactList);
        contactListAdapter.notifyDataSetChanged();
        lv_Items.setAdapter(contactListAdapter);



    }
}