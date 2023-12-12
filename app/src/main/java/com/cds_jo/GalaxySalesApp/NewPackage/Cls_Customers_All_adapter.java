package com.cds_jo.GalaxySalesApp.NewPackage;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.Pos.ItemClickListener;
import com.cds_jo.GalaxySalesApp.R;
import com.cds_jo.GalaxySalesApp.assist.Customers;

import java.util.ArrayList;

public class Cls_Customers_All_adapter extends RecyclerView.Adapter <Cls_Customers_All_adapter.ViewHolder>{

    ArrayList<Cls_Country>  deptfs;
    private Context context;
    public Cls_Customers_All_adapter(Context context, ArrayList<Cls_Country> Cls_Countrys) {
        super();
        this.context = context;
        deptfs=Cls_Countrys;

    }
    @Override
    public Cls_Customers_All_adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.customers_all_day, viewGroup, false);
        return new Cls_Customers_All_adapter.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final Cls_Customers_All_adapter.ViewHolder viewHolder,  int i) {

final int j=i;
        viewHolder.tv_ID.setText(deptfs.get(i).getID());
        viewHolder.tv_Nm.setText(deptfs.get(i).getNm());
        String Catogry1 =((MonthlySalesManSchedule)context).getDetailCustomer("1",deptfs.get(i).getID());
        viewHolder.Catogry.setText(Catogry1);
        String VisitNo1 =((MonthlySalesManSchedule)context).getDetailCustomer("2",deptfs.get(i).getID());
        viewHolder.VisitNo.setText(VisitNo1);
        String PlanNo1 =((MonthlySalesManSchedule)context).getDetailCustomer("3",deptfs.get(i).getID());
        viewHolder.PlanNo.setText(PlanNo1);
        if(VisitNo1.equals(""))
        {
            VisitNo1="0";
        }
        viewHolder.Sum.setText((Integer.parseInt(VisitNo1)-Integer.parseInt(PlanNo1))+"");

        String q=  ((MonthlySalesManSchedule)context).getAreaToday(GloblaVar.Date,GloblaVar.PeriodNo,deptfs.get(i).getID(),"3");
      //  q=  ((MonthlySalesManSchedule)context).getAreaToday(GloblaVar.Date,GloblaVar.PeriodNo,obj.getID(),"3");

        if(q.equals("-1")) {
            viewHolder.imageButton34.setChecked(false);;
        }else{
            viewHolder.imageButton34.setChecked(true);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bundle bundle = new Bundle();
                    //  et_NoS = returntransno();
                    bundle.putString("CustNo", deptfs.get(j).getID());
                    FragmentManager fm = ((Activity) context).getFragmentManager();

                    // ViewVisitFrg exampleDialog = new ViewVisitFrg();
                    CustomerInfoFrag exampleDialog = new CustomerInfoFrag();
                    exampleDialog.setArguments(bundle);
                    exampleDialog.show(fm, "example dialog");
                }catch (Exception ex)
                {

                }
            }
        });
        /* if(!(q.equals("-1"))) {
                           viewHolder.checkBox.setChecked(false);
                            notifyDataSetChanged();
                            ((MonthlySalesManSchedule)context).ClearLookups(deptfs.get(j).getID(),"3");
                       */
     /*   viewHolder.imageButton34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    int x=  ((MonthlySalesManSchedule)context).getChackPosted();
                    if(x==1){

                        viewHolder.checkBox2.setVisibility(View.GONE);
                        viewHolder.checkBox.setVisibility(View.VISIBLE);
                            notifyDataSetChanged();
                            ((MonthlySalesManSchedule)context).ClearLookups(deptfs.get(j).getID(),"3");


                    }
                }catch (Exception e){

                    //Toast.makeText(context,)

                }
            }
        });*/
        viewHolder.imageButton34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    int x=  ((MonthlySalesManSchedule)context).getChackPosted();
                    if(x==1){


                       // viewHolder.checkBox2.setVisibility(View.VISIBLE);
                        String q=  ((MonthlySalesManSchedule)context).getAreaToday(GloblaVar.Date,GloblaVar.PeriodNo,deptfs.get(j).getID(),"3");
                        if(!(q.equals("-1"))) {
                            viewHolder.imageButton34.setChecked(false);
                          //  notifyDataSetChanged();
                            ((MonthlySalesManSchedule) context).ClearLookups(deptfs.get(j).getID(), "3");

                        }
                        else {
                            viewHolder.imageButton34.setChecked(true);

                            ((MonthlySalesManSchedule) context).AddArea(j, "3");
                            //notifyDataSetChanged();
                        }                        }
                }catch (Exception e){

                    //Toast.makeText(context,)

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

    CheckBox imageButton34;
        TextView tv_ID;
        TextView tv_Nm,Catogry,VisitNo,PlanNo,Sum;
        private com.cds_jo.GalaxySalesApp.Pos.ItemClickListener clickListener;

        ViewHolder(View itemView) {
            super(itemView);

             tv_ID = (TextView) itemView.findViewById(R.id.tv_ID);
             tv_Nm = (TextView) itemView.findViewById(R.id.tv_Nm);
             Catogry = (TextView) itemView.findViewById(R.id.Catogry);
             VisitNo = (TextView) itemView.findViewById(R.id.VisitNo);
             PlanNo = (TextView) itemView.findViewById(R.id.PlanNo);
             Sum = (TextView) itemView.findViewById(R.id.Sum);
            imageButton34=(CheckBox)itemView.findViewById(R.id.imageButton34);

        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
/*
     extends BaseAdapter {

        Context context;
        ArrayList<Cls_Country> cls_search_pos;
        String q;
    public Cls_Customers_All_adapter(Context context, ArrayList<Cls_Country> list) {

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
                convertView = inflater.inflate(R.layout.customers_all_day, null);

            }
            TextView tv_ID = (TextView) convertView.findViewById(R.id.tv_ID);
            tv_ID.setText(obj.getID());


            TextView tv_Nm = (TextView) convertView.findViewById(R.id.tv_Nm);
            tv_Nm.setText(obj.getNm());
            String Catogry1 =((MonthlySalesManSchedule)context).getDetailCustomer("1",obj.getID());
            TextView Catogry = (TextView) convertView.findViewById(R.id.Catogry);
            Catogry.setText(Catogry1);

            String VisitNo1 =((MonthlySalesManSchedule)context).getDetailCustomer("2",obj.getID());
            TextView VisitNo = (TextView) convertView.findViewById(R.id.VisitNo);
            VisitNo.setText(VisitNo1);
            String PlanNo1 =((MonthlySalesManSchedule)context).getDetailCustomer("3",obj.getID());
            TextView PlanNo = (TextView) convertView.findViewById(R.id.PlanNo);
            PlanNo.setText(PlanNo1);
            if(VisitNo1.equals(""))
            {
                VisitNo1="0";
            }
            TextView Sum = (TextView) convertView.findViewById(R.id.Sum);
            Sum.setText((Integer.parseInt(VisitNo1)-Integer.parseInt(PlanNo1))+"");

            final CheckBox checkBox=(CheckBox)convertView.findViewById(R.id.checkButton);

            //  q=  ((MonthlySalesManSchedule)context).GetTodayAllCustomers(GloblaVar.Date,GloblaVar.PeriodNo);
            q=  ((MonthlySalesManSchedule)context).getAreaToday(GloblaVar.Date,GloblaVar.PeriodNo,obj.getID(),"3");




            if(q.equals("-1")) {
                checkBox.setChecked(false);
                //  tv_Nm.setText("done");
            }else{
                checkBox.setChecked(true);


            }
            LinearLayout ss=(LinearLayout)convertView.findViewById(R.id.ss);
            ss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    //  et_NoS = returntransno();
                    bundle.putString("CustNo",obj.getID());


                    // ViewVisitFrg exampleDialog = new ViewVisitFrg();
                    CustomerInfoFrag exampleDialog = new CustomerInfoFrag();
                    exampleDialog.setArguments(bundle);
                    exampleDialog.show(((Activity) context).getFragmentManager(), "example dialog");
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int x=  ((MonthlySalesManSchedule)context).getChackPosted();
                        if(x==1){
                            String q=  ((MonthlySalesManSchedule)context).getAreaToday(GloblaVar.Date,GloblaVar.PeriodNo,obj.getID(),"3");
                            if(!(q.equals("-1"))) {
                                ((MonthlySalesManSchedule)context).ClearLookups(obj.getID(),"3");
                                //((MonthlySalesManSchedule) context).btn_delete_Cityandcustomer(obj.getID());
                                checkBox.setChecked(false);
                                // ((MonthlySalesManSchedule)context).deleteallLookups(obj.getID(),"3");
                                //  ((MonthlySalesManSchedule)context).UpdateScreen();


                            } else {
                                ((MonthlySalesManSchedule)context).AddArea(position,"3");
                                checkBox.setChecked(true);
                                //  ((MonthlySalesManSchedule)context).UpdateScreen();

                            }}
                    }catch (Exception e){

                        //Toast.makeText(context,)

                    }


                }
            });
            return convertView;
        }

    }



*/