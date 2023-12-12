package com.cds_jo.GalaxySalesApp.NewPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cds_jo.GalaxySalesApp.Pos.ItemClickListener;
import com.cds_jo.GalaxySalesApp.Pos.Pos_Activity;
import com.cds_jo.GalaxySalesApp.R;

import java.io.File;
import java.util.ArrayList;

public class Cls_Country_All_adapter extends RecyclerView.Adapter <Cls_Country_All_adapter.ViewHolder>{

    ArrayList<Cls_Country>  deptfs;
    private Context context;
    public Cls_Country_All_adapter(Context context, ArrayList<Cls_Country> Cls_Countrys) {
        super();
        this.context = context;
        deptfs=Cls_Countrys;

    }
    @Override
    public Cls_Country_All_adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.country_all_day, viewGroup, false);
        return new Cls_Country_All_adapter.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final Cls_Country_All_adapter.ViewHolder viewHolder, final int i) {

        final int j=i;
        final Cls_Country_All_adapter.ViewHolder viewHolder2=viewHolder;

        viewHolder.tv_Nm.setText(deptfs.get(i).getNm());
        viewHolder.tv_ID.setText(deptfs.get(i).getID());


        String q=  ((MonthlySalesManSchedule)context).getAreaToday(GloblaVar.Date,GloblaVar.PeriodNo,deptfs.get(i).getID(),"2");

        if(q.equals("-1")) {
            viewHolder.imageButton34.setChecked(false);
            ////notifyDataSetChanged();
            //  tv_Nm.setText("done");
        }else{
            viewHolder.imageButton34.setChecked(true);

          //  notifyDataSetChanged();
            //((MonthlySalesManSchedule)context).FillAllCustomers("CountryNo = '"+obj.getID()+"'");

        }

      /*  viewHolder.setClickListener(new com.cds_jo.GalaxySalesApp.Pos.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context, "#" + position + " - " + deptfs.get(position).getType_No() ,
                            Toast.LENGTH_SHORT).show();

                } else { ((Pos_Activity) context).FillItems(deptfs.get(position).getType_No());

                }
            }
        });*/

        viewHolder.imageButton34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int x=  ((MonthlySalesManSchedule)context).getChackPosted();
                if(x==1){

                    String q=  ((MonthlySalesManSchedule)context).getAreaToday(GloblaVar.Date,GloblaVar.PeriodNo,deptfs.get(j).getID(),"2");


                    if(!(q.equals("-1"))) {
                        viewHolder2.imageButton34.setChecked(false);

                        ((MonthlySalesManSchedule)context).ClearLookups(deptfs.get(j).getID(),"2");
                        //  ((MonthlySalesManSchedule)context).clearcountryid(false);
                        //    ((MonthlySalesManSchedule)context).deleteallLookups(obj.getID(),"3",GloblaVar.Date,GloblaVar.PeriodNo);

                        notifyDataSetChanged();
                        //  Toast.makeText(context,"true",Toast.LENGTH_LONG).show();
                    }
                    else {
                        viewHolder2.imageButton34.setChecked(true);               // ((MonthlySalesManSchedule)context).UpdateScreen();

                        //  ((MonthlySalesManSchedule) context).btn_Insert_Cityandcustomer(obj.getID());
                        //  ((MonthlySalesManSchedule)context).btn_Insert_City(position);
                        ((MonthlySalesManSchedule)context).AddArea(j,"2");
                        //    ((MonthlySalesManSchedule)context).UpdateScreen();
                        //   ((MonthlySalesManSchedule)context).clearcountryid(true);
                        // ((MonthlySalesManSchedule)context).FillAllCustomers("SelectedDate.CountryId");
                        notifyDataSetChanged();
                        //    Toast.makeText(context,"false",Toast.LENGTH_LONG).show();

                    }

                    ((MonthlySalesManSchedule)context).SetEntry(GloblaVar.Date,GloblaVar.PeriodNo);

                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return deptfs.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener,
            View.OnLongClickListener {
        TextView tv_ID;
        TextView tv_Nm;
        CheckBox imageButton34;
        private ItemClickListener clickListener;

        ViewHolder(View itemView) {
            super(itemView);


            imageButton34 = (CheckBox) itemView.findViewById(R.id.imageButton34);
            tv_ID = (TextView) itemView.findViewById(R.id.tv_ID);
            tv_Nm = (TextView) itemView.findViewById(R.id.tv_Nm);
        }

        void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }
        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }
}


/*extends BaseAdapter {

    Context context;
    ArrayList<Cls_Country> cls_search_pos;

    public Cls_Country_All_adapter(Context context, ArrayList<Cls_Country> list) {

        this.context = context;
        cls_search_pos = list;
    }

    @Override
    public int getCount() {

        return cls_search_pos.size();
    }

    @Override
    public Object getItem(int position) {

        return cls_search_pos.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        final Cls_Country obj = cls_search_pos.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.country_all_day, null);

        }
        TextView tv_ID = (TextView) convertView.findViewById(R.id.tv_ID);
        tv_ID.setText(obj.getID());


        TextView tv_Nm = (TextView) convertView.findViewById(R.id.tv_Nm);
        tv_Nm.setText(obj.getNm());


        final CheckBox imageButton34=(CheckBox) convertView.findViewById(R.id.imageButton34);

        String q=  ((MonthlySalesManSchedule)context).getAreaToday(GloblaVar.Date,GloblaVar.PeriodNo,obj.getID(),"2");
        ((MonthlySalesManSchedule)context).showCustomer("2");
*//*
        imageButton34.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });
*//*


   *//*     imageButton34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MonthlySalesManSchedule)context).btn_Insert_City(position);




            }
        });*//*

        if(q.equals("-1")) {
            imageButton34.setChecked(false);
            //  tv_Nm.setText("done");
        }else{
            imageButton34.setChecked(true);
            //((MonthlySalesManSchedule)context).FillAllCustomers("CountryNo = '"+obj.getID()+"'");

        }


        imageButton34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x=  ((MonthlySalesManSchedule)context).getChackPosted();
                if(x==1){

                    String q=  ((MonthlySalesManSchedule)context).getAreaToday(GloblaVar.Date,GloblaVar.PeriodNo,obj.getID(),"2");


                    if(!(q.equals("-1"))) {
                        ((MonthlySalesManSchedule)context).ClearLookups(obj.getID(),"2");
                        imageButton34.setChecked(false);
                        //  ((MonthlySalesManSchedule)context).clearcountryid(false);
                        //    ((MonthlySalesManSchedule)context).deleteallLookups(obj.getID(),"3",GloblaVar.Date,GloblaVar.PeriodNo);


                    }
                    else {
                        //  ((MonthlySalesManSchedule) context).btn_Insert_Cityandcustomer(obj.getID());
                        //  ((MonthlySalesManSchedule)context).btn_Insert_City(position);
                        ((MonthlySalesManSchedule)context).AddArea(position,"2");
                        //    ((MonthlySalesManSchedule)context).UpdateScreen();
                        //   ((MonthlySalesManSchedule)context).clearcountryid(true);
                        imageButton34.setChecked(true);               // ((MonthlySalesManSchedule)context).UpdateScreen();
                        // ((MonthlySalesManSchedule)context).FillAllCustomers("SelectedDate.CountryId");

                    }

                    ((MonthlySalesManSchedule)context).SetEntry(GloblaVar.Date,GloblaVar.PeriodNo);

                }}
        });

        return convertView;
    }

}
*/
