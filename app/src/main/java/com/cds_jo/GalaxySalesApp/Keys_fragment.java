package com.cds_jo.GalaxySalesApp;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.NewPackage.LocaleHelper;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Keys_fragment extends DialogFragment {
    View form ;
    ImageButton add,cancel,acc,invoice,rec,order;
    Button back;
    ListView lisrta;
    ArrayList<String> lista;
    TextView tv;
    LinearLayout backk;
    String orderno,userno;
    Drawable greenProgressbar;
    RelativeLayout.LayoutParams lp;
    String u;
    @Override
    public void onStart()
    {
        super.onStart();

        // safety check
        if (getDialog() == null)
            return;

        //  int dialogWidth =410; // specify a value here
        //  int dialogHeight = 300; // specify a value here


        //  int dialogWidth = 420; // specify a value here
        //  int dialogHeight =WindowManager.LayoutParams.WRAP_CONTENT;
        //  getDialog().getWindow().setLayout(dialogWidth, dialogHeight);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom( getDialog().getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(lp);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setWindowAnimations(0);


    }


    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container,Bundle savestate){

        form =inflater.inflate(R.layout.fragment_keys_fragment,container,false);
        getDialog().setTitle(getArguments().getString("Msg"));
        backk= (LinearLayout) form.findViewById(R.id.back);
        lista=new ArrayList<>();


        orderno=  getArguments().getString("OrderNo");
        userno=  getArguments().getString("UserNo");

//        Toast.makeText(getActivity(),getArguments().getString("OrderNo"),Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        u =  sharedPreferences.getString("UserID", "");

        lisrta= (ListView) form.findViewById(R.id.lisrta);

       /* backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });*/



        LocaleHelper localeHelper=new LocaleHelper();

        String currentString = getArguments().getString("list");
        String[] separated = currentString.split(",");
        String type="";
        for(int i=0;i<separated.length;i++){
            String childtype;
            if(localeHelper.getlanguage(getActivity()).equals("ar"))
                childtype= DB.GetValue(getActivity(),"AreasN","DescrA","Id ='"+separated[i]+"' and TableNo='7'");
            else
                childtype=DB.GetValue(getActivity(),"AreasN","DescrE","Id ='"+separated[i]+"' and TableNo='7'");

            lista.add("\n"+childtype+"\n");
        }
        String [] gg=new String[lista.size()];
        for(int i=0;i<lista.size();i++){
            gg[i]=lista.get(i);
        }


        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, gg);
        lisrta.setAdapter(adapter);   return  form;
    }





}
