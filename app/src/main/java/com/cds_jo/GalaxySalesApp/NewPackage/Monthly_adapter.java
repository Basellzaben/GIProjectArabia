package com.cds_jo.GalaxySalesApp.NewPackage;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.SqlHandler;

import java.util.ArrayList;

public class Monthly_adapter extends BaseAdapter {

    Context context;
    ArrayList<keys_modle> cls_keys;
    Fragment fr;
    public Monthly_adapter(Fragment f){
        fr=f;
    }
    public Monthly_adapter(Context context, ArrayList<keys_modle> list) {

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
        String q=  ((MonthlySalesManSchedule)context).getAreaToday(GloblaVar.Date,GloblaVar.PeriodNo,cls_deptf.getItemNo(),"1");

        if(q.equals("-1")) {
            check.setChecked(false);
        }
        else {
            check.setChecked(true);
        }


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
                int x=  ((MonthlySalesManSchedule)context).getChackPosted();
                if(x==1){
                    String q=  ((MonthlySalesManSchedule)context).getAreaToday(GloblaVar.Date,GloblaVar.PeriodNo,cls_deptf.getItemNo(),"1");


                    if(!(q.equals("-1"))) {
                        ((MonthlySalesManSchedule)context).ClearLookups(cls_deptf.getItemNo(),"1");
                        check.setChecked(false);
                        //  ((MonthlySalesManSchedule)context).clearcountryid(false);
                        //((MonthlySalesManSchedule)context).deleteallLookups(cls_deptf.getItemNo(),"1",GloblaVar.Date,GloblaVar.PeriodNo);


                    }
                    else {
                        //  ((MonthlySalesManSchedule) context).btn_Insert_Cityandcustomer(obj.getID());
                        //  ((MonthlySalesManSchedule)context).btn_Insert_City(position);
                        ((MonthlySalesManSchedule)context).AddArea(position,"1");
                        //    ((MonthlySalesManSchedule)context).UpdateScreen();
                        //   ((MonthlySalesManSchedule)context).clearcountryid(true);
                        check.setChecked(true);               // ((MonthlySalesManSchedule)context).UpdateScreen();
                        // ((MonthlySalesManSchedule)context).FillAllCustomers("SelectedDate.CountryId");

                    }

                    ((MonthlySalesManSchedule)context).SetEntry(GloblaVar.Date,GloblaVar.PeriodNo);



                }}
        });




        return convertView;
    }

/*    private void DeleteCheck( String key,String date,) {
        SqlHandler sqlHandler = new SqlHandler(context);

        String  q = "Delete from PlanMonthlyLookups where itemno='"+key+"'" +
                "and Date='"++"' and period='"++"'";

        sqlHandler.executeQuery(q);

    }*/





    public void SaveCheck(String No,String itemno,String Today_Date,String Period_No){

        SqlHandler sqlHandler = new SqlHandler(context);




        String  q = "Insert INTO PlanMonthlyLookups(Id,TabletNo," +
                "itemno,Date,period) values ('"
                +       No
                + "','" + "1"
                + "','" + itemno
                + "','" + Today_Date
                + "','" + Period_No
                + "')";
        sqlHandler.executeQuery(q);



    }


}

