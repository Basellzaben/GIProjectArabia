package com.cds_jo.GalaxySalesApp.NewPackage;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;

import java.util.ArrayList;


public class ViewVisitFrg extends DialogFragment {
    View form ;
    SqlHandler sqlHandler;
    TextView types;
    //  ArrayList<GetDoneBarCode> list1;
    Button btn_Cancel;
    ListView lv;
    /* GetDoneBarCodeAdapter adapter;
     GetDoneBarCode cls_barCode;
 */
    String transno="";
    ProgressDialog progressdialog;


    /*  @Override
      public void onStart()
      {
          super.onStart();

          // safety check
          if (getDialog() == null)
              return;
          int dialogWidth =  WindowManager.LayoutParams.WRAP_CONTENT;//340; // specify a value here
          int dialogHeight =  WindowManager.LayoutParams.WRAP_CONTENT;//400; // specify a value here
          getDialog().getWindow().setLayout(dialogWidth, dialogHeight);

      }*/

    @Override
    public void onStart()
    {
        super.onStart();
        if (getDialog() == null)
            return;

        int dialogWidth = WindowManager.LayoutParams.MATCH_PARENT; // specify a value here
        int dialogHeight = WindowManager.LayoutParams.MATCH_PARENT;//400; // specify a value here

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        form= inflater.inflate(R.layout.fragment_view_visit_frg, container, false);
        lv=(ListView) form.findViewById(R.id.lv);
        types=(TextView) form.findViewById(R.id.type);
        transno=getArguments().getString("transno");
        String query = "";
        query="select  man ,name  from manf";

            FillKeysitems(query);





/*lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().remove(ViewVisitFrg.this).commit();

            }
        });*/

        btn_Cancel=(Button)form.findViewById(R.id.btn_Cancel);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().remove(ViewVisitFrg.this).commit();

            }
        });
        return form;
    }


    public void end(){
        getActivity().getFragmentManager().beginTransaction().remove(ViewVisitFrg.this).commit();

    }

    /*   public void FilterItems1( ) {
           String    query;
           if(getArguments().getString("Qty").equals("0")&& Double.parseDouble(getArguments().getString("bounas"))>0)
           {
               query = "Select   *   from Inv_Sal_Done where Item_No ='"+getArguments().getString("Item_no")+"' and oreder_no ='"+getArguments().getString("orderno")+"' and gift=1" ;
           }
           else
           {         query = "Select   *   from Inv_Sal_Done where Item_No ='"+getArguments().getString("Item_no")+"' and oreder_no ='"+getArguments().getString("orderno")+"'" ;

           }


           //  "  where "+
           //  "  Item_No   like '%" + Filter + "%'  or Item_Name like  '%" + Filter + "%' ";



           Cursor c1 = sqlHandler.selectQuery(query);
           if (c1 != null && c1.getCount() != 0) {
               if (c1.moveToFirst()) {
                   do {
                       cls_barCode = new GetDoneBarCode();
                       cls_barCode.setItemNo(" ");
                       cls_barCode.setItemNote(c1.getString(c1
                               .getColumnIndex("Item_No")));
                       cls_barCode.setBarCode(c1.getString(c1
                               .getColumnIndex("Sal")));
                       cls_barCode.setType(" ");
                       cls_barCode.setDate(" ");


                       list1.add(cls_barCode);

                   } while (c1.moveToNext());

               }
               c1.close();
           }

        *//*   adapter = new GetDoneBarCodeAdapter(getActivity(), list1);
        lv.setAdapter(adapter);*//*
        //rec_Item_id.setLayoutManager(new GridLayoutManager(this,4));
        //  rec_Item_id.setAdapter(myAdapter);



    }*/
    private void FillKeys(String query) {
        //   filter = (EditText) form.findViewById(R.id.et_Search_filter);
        // String query = "";
        sqlHandler = new SqlHandler(getActivity());


        Bundle bundle = this.getArguments();





        // String KeyId=DB.GetValue(getActivity(),"ItemsKeysN","KeyId","ItemId='"+itemid+"'");



        ArrayList<keys_modle> cls_invf_List = new ArrayList<keys_modle>();
        cls_invf_List.clear();
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    String f;
                    keys_modle keys_modle = new keys_modle();
                    LocaleHelper  LocaleHelper =new LocaleHelper();
                    if(LocaleHelper.getlanguage(getActivity()).equals("ar") )
                    { f=DB.GetValue(getActivity(),"AreasN","DescrA"," Id='"+c1.getString(c1
                            .getColumnIndex("KeyId"))+"'");}
                    else{
                        f=DB.GetValue(getActivity(),"AreasN","DescrE"," Id='"+c1.getString(c1
                                .getColumnIndex("KeyId"))+"'");
                    }
                    keys_modle.setKey(f);
                 /*if(LocaleHelper.getlanguage(getActivity()).equals("en"))
                 {

                     String   f=DB.GetValue(getActivity(),"AreasN","DescrA","TableNo='"+getArguments().getString("itemid")+"'");
                     keys_modle.setKey(*//*c1.getString(c1
                         .getColumnIndex("DescrA"))*//*f);
                 }
                 else
                 {
                     *//*String  f=DB.GetValue(getActivity(),"AreasN","DescrE","ItemNo ='"+c1.getString(c1
                             .getColumnIndex("KeyId"))+"' and TableNo='"+getArguments().getString("itemid")+"'");
*//*
                     String   f=DB.GetValue(getActivity(),"AreasN","DescrA","TableNo='"+getArguments().getString("itemid")+"'");

                     keys_modle.setKey(*//*c1.getString(c1
                             .getColumnIndex("DescrE"))*//*f);
                 }*/

                 /*keys_modle.setKey(c1.getString(c1
                         .getColumnIndex("DescrA")));*/
                    keys_modle.setItemNo(c1.getString(c1
                            .getColumnIndex("Id")));

                    //   query = "Select ItemNo, Desc  from ItemsCheck where TransNo='"+getArguments().getString("transno")+"' and ItemNo='"+getArguments().getString("itemid")+"'";
                /*  f=DB.GetValue(getActivity(),"AreasN","DescrE","ItemNo ='"+c1.getString(c1
                         .getColumnIndex("KeyId"))+"' and TableNo='"+getArguments().getString("itemid")+"'");
                 String f2=DB.GetValue(getActivity(),"AreasN","DescrA","ItemNo ='"+c1.getString(c1
                         .getColumnIndex("KeyId"))+"' and TableNo='"+getArguments().getString("itemid")+"'");

*/
                    //String  f1=DB.GetValue(getActivity(),"AreasN","DescrA","TableNo='"+GloblaVar.tabno+"'");
                    //  String  f2=DB.GetValue(getActivity(),"AreasN","DescrE"," TableNo='"+GloblaVar.tabno+"'");
                    String  f1=DB.GetValue(getActivity(),"AreasN","DescrA"," Id='"+c1.getString(c1
                            .getColumnIndex("KeyId"))+"'");
                    String   f2=DB.GetValue(getActivity(),"AreasN","DescrE"," Id='"+c1.getString(c1
                            .getColumnIndex("KeyId"))+"'");

                    String item=DB.GetValue(getActivity(),"ItemsCheck","KeyId","KeyId='"+/*c1.getString(c1
                         .getColumnIndex("DescrA"))*/f1+"' and TransNo='"+transno+"'");
                    String item2=DB.GetValue(getActivity(),"ItemsCheck","KeyId","KeyId='"+/*c1.getString(c1
                         .getColumnIndex("DescrE"))*/f2+"' and TransNo='"+transno+"'");

                    if(item.equals("-1") && item2.equals("-1")){
                        keys_modle.setCheck(false);

                    }
                    else {
                        keys_modle.setCheck(true);

                    }
                    cls_invf_List.add(keys_modle);

                } while (c1.moveToNext());

            }
            c1.close();
        }

        Cls_Keys_adapter cls_invf_adapter = new Cls_Keys_adapter(
                getActivity(), cls_invf_List);
        lv.setAdapter(cls_invf_adapter);

        Cls_Keys_adapter gg = new Cls_Keys_adapter(ViewVisitFrg.this);



    }
    private void FillKeysitems(String query) {
        //   filter = (EditText) form.findViewById(R.id.et_Search_filter);
        // String query = "";
        sqlHandler = new SqlHandler(getActivity());


        Bundle bundle = this.getArguments();





        // String KeyId=DB.GetValue(getActivity(),"ItemsKeysN","KeyId","ItemId='"+itemid+"'");



        ArrayList<keys_modle> cls_invf_List = new ArrayList<keys_modle>();
        cls_invf_List.clear();
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    String f;
                    keys_modle keys_modle = new keys_modle();
                    LocaleHelper  LocaleHelper =new LocaleHelper();


                    keys_modle.setKey(c1.getString(c1
                            .getColumnIndex("name")));

                    keys_modle.setItemNo(c1.getString(c1
                            .getColumnIndex("man")));


                    keys_modle.setOrderNo(transno);

                    String item2=DB.GetValue(getActivity(),"ManTeam","count(*)","ManNo='"+c1.getString(c1
                            .getColumnIndex("man"))+"' and Trans_Id='"+transno+"'");

                    if(item2.equals("0")){
                        keys_modle.setCheck(false);

                    }
                    else {
                        keys_modle.setCheck(true);

                    }
                    cls_invf_List.add(keys_modle);

                } while (c1.moveToNext());

            }
            c1.close();
        }

        Cls_Keys_adapter cls_invf_adapter = new Cls_Keys_adapter(
                getActivity(), cls_invf_List);
        lv.setAdapter(cls_invf_adapter);

        Cls_Keys_adapter gg = new Cls_Keys_adapter(ViewVisitFrg.this);



    }


    private void FillKeyslocups(String query) {
        //   filter = (EditText) form.findViewById(R.id.et_Search_filter);
        // String query = "";
        sqlHandler = new SqlHandler(getActivity());


        Bundle bundle = this.getArguments();





        // String KeyId=DB.GetValue(getActivity(),"ItemsKeysN","KeyId","ItemId='"+itemid+"'");



        ArrayList<keys_modle> cls_invf_List = new ArrayList<keys_modle>();
        cls_invf_List.clear();
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    keys_modle keys_modle = new keys_modle();
                    LocaleHelper  LocaleHelper =new LocaleHelper();
                    if(LocaleHelper.getlanguage(getActivity()).equals("ar"))
                    { keys_modle.setKey(c1.getString(c1
                            .getColumnIndex("DescrA")));}
                    else
                    {
                        keys_modle.setKey(c1.getString(c1
                                .getColumnIndex("DescrE")));
                    }

                 /*keys_modle.setKey(c1.getString(c1
                         .getColumnIndex("DescrA")));*/
                    keys_modle.setItemNo(c1.getString(c1
                            .getColumnIndex("ItemNo")));

                    //   query = "Select ItemNo, Desc  from ItemsCheck where TransNo='"+getArguments().getString("transno")+"' and ItemNo='"+getArguments().getString("itemid")+"'";

                    String item=DB.GetValue(getActivity(),"ItemsCheck","KeyId","Desc='"+c1.getString(c1
                            .getColumnIndex("DescrA"))+"' and TransNo='"+transno+"'");


                    String item2=DB.GetValue(getActivity(),"ItemsCheck","KeyId","Desc='"+c1.getString(c1
                            .getColumnIndex("DescrE"))+"' and TransNo='"+transno+"'");

                    if(item.equals("-1") && item2.equals("-1")){
                        keys_modle.setCheck(false);

                    }
                    else {
                        keys_modle.setCheck(true);

                    }
                    cls_invf_List.add(keys_modle);

                } while (c1.moveToNext());

            }
            c1.close();
        }

        Cls_Keys_adapter cls_invf_adapter = new Cls_Keys_adapter(
                getActivity(), cls_invf_List);
        lv.setAdapter(cls_invf_adapter);

        Cls_Keys_adapter gg = new Cls_Keys_adapter(ViewVisitFrg.this);



    }
}
