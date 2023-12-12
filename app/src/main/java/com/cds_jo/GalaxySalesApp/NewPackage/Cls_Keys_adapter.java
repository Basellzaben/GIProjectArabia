package com.cds_jo.GalaxySalesApp.NewPackage;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.DoctorReportActivity;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;

import java.util.ArrayList;

public class Cls_Keys_adapter extends BaseAdapter {

    Context context;
    ArrayList<keys_modle> cls_keys;
    Fragment fr;
    public Cls_Keys_adapter(Fragment f){
        fr=f;
    }
    public Cls_Keys_adapter(Context context, ArrayList<keys_modle> list) {

        this.context = context;
        cls_keys = list;
    }@Override
    public int getCount() {

        return cls_keys.size();
    }

    @Override
    public Object getItem(int position) {

        return cls_keys.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        final keys_modle  cls_deptf = cls_keys.get(position);
        //   cls_deptf.setCheck(false);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.keys_item, null);

        }
        TextView key = (TextView) convertView.findViewById(R.id.key);
        key.setText(cls_deptf.getKey());

        final CheckBox check = (CheckBox) convertView.findViewById(R.id.check);

        if(cls_deptf.getCheck())
            check.setChecked(true);
        else
            check.setChecked(false);



        LinearLayout RR=(LinearLayout)convertView.findViewById(R.id.ss);
        if(position%2==0)
        {
            RR.setBackgroundColor(Color.WHITE);
        }
        else
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Gray2));

        }


        final View finalConvertView = convertView;
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {

                    SqlHandler sqlHandler = new SqlHandler(context);

                    String item2=DB.GetValue(context,"ManTeam","count(*)","ManNo='"+cls_deptf.getItemNo()+"' and Trans_Id='"+cls_deptf.getOrderNo()+"'");

                    if(item2.equals("0"))
                    {
                        String  q = "Insert INTO ManTeam(Trans_Id,ManName,ManNo) values ('"
                                +       cls_deptf.getOrderNo()
                                + "','" + cls_deptf.getKey()
                                + "','" + cls_deptf.getItemNo()

                                + "')";
                        sqlHandler.executeQuery(q);

                    }
                }
                else
                {
                    SqlHandler sqlHandler = new SqlHandler(context);

                    String item2=DB.GetValue(context,"ManTeam","count(*)","ManNo='"+cls_deptf.getItemNo()+"' and Trans_Id='"+cls_deptf.getOrderNo()+"'");

                    if(!item2.equals("0"))
                    {
                        String  q = "Delete from ManTeam where ManNo='"+cls_deptf.getItemNo()+"' AND Trans_Id='"+cls_deptf.getOrderNo()+"'";
                        sqlHandler.executeQuery(q);

                    }
                }







           /*
                String transNo= ((DoctorReportActivity) context).returntransno();
                String post= DB.GetValue(context,"DoctorReport","Posted","OrderNo ='"+transNo+"'");
                if(!post.equals("-1")){
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            context).create();

                    alertDialog.setTitle(String.valueOf(context.getResources().getString(R.string.Update)));
                    alertDialog.setMessage(String.valueOf(context.getResources().getString(R.string.UpdateNotAllowedafterpost)));


                    alertDialog.setIcon(R.drawable.delete);
                    alertDialog.setButton(String.valueOf(context.getResources().getString(R.string.ok)), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                    check.setEnabled(false);
    *//*alertDialog.setIcon(R.drawable.delete);
    alertDialog.setMessage("الزيارة تم اعتمادها , لا يمكن التعديل");*//*
                }else{

                    cls_deptf.setCheck(!cls_deptf.getCheck());
                    check.setChecked(cls_deptf.getCheck());

                    String max1 = DB.GetValueMax(context, "ItemsCheck", "Id", "1=1");
                    try {
                        if (max1.isEmpty() || max1.equals("-1"))
                            max1 = "0";
                        else
                            max1 = String.valueOf(Integer.valueOf(max1) + 1);
                    }catch (Exception e){
                        max1 = "0";

                    }

                    if(cls_deptf.getCheck())
                    {

                        if(GloblaVar.tabno.equals("5") || GloblaVar.tabno.equals("1")) {
                            if(GloblaVar.tabno.equals("5"))
                                onecheckjust("5",transNo);
                            else
                                onecheckjust("1",transNo);

                            SaveCheck(max1, transNo, GloblaVar.tabno, GloblaVar.checkkey, cls_deptf.key, cls_deptf.ItemNo, "0", GloblaVar.tabno);


                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    ((DoctorReportActivity) context).endViwFragment();// yourMethod();
                                }
                            }, 120);   //5 seconds

                        }else{
                            SaveCheck(max1, transNo, GloblaVar.tabno, GloblaVar.checkkey, cls_deptf.key, cls_deptf.ItemNo, "0", GloblaVar.tabno);



                        }
                        SaveCheck(max1, transNo, GloblaVar.tabno, GloblaVar.checkkey, cls_deptf.key, cls_deptf.ItemNo, "0", GloblaVar.tabno);

                    }else
                    {

                        DeleteCheck(cls_deptf.ItemNo);


                    }

                }*/
            }
        });





       /* check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



    }
});
*/

        return convertView;
    }

    private void DeleteCheck( String key) {
        SqlHandler sqlHandler = new SqlHandler(context);

        String  q = "Delete from ItemsCheck where KeyId='"+key+"'";

        sqlHandler.executeQuery(q);

    }

    private void onecheckjust( String tabno,String transno) {
        SqlHandler sqlHandler = new SqlHandler(context);

        String  q = "Delete from ItemsCheck where TabNo='"+tabno+"' AND TransNo='"+transno+"'";

        sqlHandler.executeQuery(q);

    }


    public void SaveCheck(String No,String TransNo,String TabNo,String ItemNo,String Desc,String KeyId ,String Save,String type){


        SqlHandler sqlHandler = new SqlHandler(context);

       /* try{
            sqlHandler.executeQuery(" CREATE TABLE IF NOT EXISTS ItemsCheck (" +
                    " Id integer primary key autoincrement" +
                    ", TransNo text null" +
                    ", TabNo text null" +
                    ", ItemNo text null" +
                    ", Desc text null" +
                    ", KeyId text null" +
                    ",SaveState text null) ");
        }catch ( SQLException e){ }
*/

       /* if (!type.equals("7"))
           ItemNo="";*/

        String  q = "Insert INTO ItemsCheck(Id,TransNo,TabNo," +
                "ItemNo,Desc,KeyId,SaveState) values ('"
                +       No
                + "','" + TransNo
                + "','" + TabNo
                + "','" + ItemNo
                + "','" + Desc
                + "','" + KeyId
                + "','" + Save
                + "')";
        sqlHandler.executeQuery(q);



    }



}



