package com.cds_jo.GalaxySalesApp;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.NewPackage.LocaleHelper;

import java.util.ArrayList;

public class ReportAdapter extends ArrayAdapter<RepModel> {
    Context context;
    ArrayList<RepModel> data = new ArrayList<RepModel>();

    public ReportAdapter(Context context, int layoutResourceId, ArrayList<RepModel> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        row = inflater.inflate(R.layout.rep_row, parent, false);
        holder = new RecordHolder();
        holder.CustNO = (TextView) row.findViewById(R.id.custNO);
        holder.CustName = (TextView) row.findViewById(R.id.CustName);
        holder.OrderNo = (TextView) row.findViewById(R.id.orderNo);

        holder.Tr_Date = (TextView) row.findViewById(R.id.tr_Date);
        holder.DoubleVisit = (ImageView) row.findViewById(R.id.doubleVisit);
        holder.Tr_Time = (TextView) row.findViewById(R.id.tr_Time);

        holder.VisitType = (TextView) row.findViewById(R.id.visitType);
        holder.VisitResult = (TextView) row.findViewById(R.id.visitResult);
        holder.VisitObjective = (TextView) row.findViewById(R.id.visitObjective);
        row.setTag(holder);

        RepModel item = data.get(position);
        holder.CustNO.setText(item.getCustNO());
        holder.CustName.setText(item.getCustName());

        holder.OrderNo.setText(item.getOrderNo());
        holder.Tr_Date.setText(item.getTr_Date());
        if(item.getDoubleVisit().equals("1"))
        holder.DoubleVisit.setImageResource(R.drawable.check);
        else
            holder.DoubleVisit.setImageResource(R.drawable.uncheck);

        holder.Tr_Time.setText(item.getTr_Time());
        //query = "Select *  from AreasN where  TableNo='1'";


        String currentString = item.getVisitType();
        String[] separated = currentString.split(",");
      String type="";
      for(int i=0;i<separated.length;i++){
          String childtype;
          if(LocaleHelper.getlanguage(context).equals("ar") )
               childtype   = DB.GetValue(context, "AreasN", "DescrA", "Id='"+separated[i]+"'");
        else
              childtype   = DB.GetValue(context, "AreasN", "DescrE", "Id='"+separated[i]+"'");

          type+=childtype+"\n";
      }


        holder.VisitType.setText(type);
        holder.VisitResult.setText(item.getVisitResult());


         currentString = item.getVisitObjective();
        separated = currentString.split(",");
         type="";
        for(int i=0;i<separated.length;i++){
            String childtype;
            if(LocaleHelper.getlanguage(context).equals("ar") )
                childtype   = DB.GetValue(context, "AreasN", "DescrA", "Id='"+separated[i]+"'");
            else
                childtype   = DB.GetValue(context, "AreasN", "DescrE", "Id='"+separated[i]+"'");

            type+=childtype+"\n";
        }
        holder.VisitObjective.setText(type);




/*

        if(item.getStates().equals("1")){
            holder.DESC_A.setTextColor(Color.parseColor("#335DFF"));
        } else if(item.getStates().equals("2")){
            holder.DESC_A.setTextColor(Color.parseColor("#F68208"));
        }else if(item.getStates().equals("5")){
            holder.DESC_A.setTextColor(Color.parseColor("#32CA1A"));
        }else {
            holder.DESC_A.setTextColor(Color.parseColor("#CA1A1A"));
        }
*/


  /* convertView.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Bundle bundle = new Bundle();
           FragmentManager Manager =  getFragmentManager();
           PopSmallMenue obj = new PopSmallMenue();
           bundle.putString("Msg",  CustNo + "   " +CustNm );
           obj.setArguments(bundle);
           obj.show(Manager, null);

       }
   });*/

        // holder.AmountCurCredit.setText( new DecimalFormat("##.##").format( Double.parseDouble(item.getAmountCurCredit())));

        //holder.AmountCurCredit.setText(  String.format(Locale.US,"%.3f", Double.parseDouble(item.getAmountCurCredit())));
        return row;
    }

    static class RecordHolder {

        TextView CustNO,CustName,OrderNo,Tr_Date,Tr_Time,VisitType,VisitResult,VisitObjective;
ImageView DoubleVisit;
    }
}

