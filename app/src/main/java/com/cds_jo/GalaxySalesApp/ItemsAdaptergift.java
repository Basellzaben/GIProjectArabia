package com.cds_jo.GalaxySalesApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.NewPackage.LocaleHelper;

import java.util.ArrayList;

public class ItemsAdaptergift extends ArrayAdapter<ItemsModelGift> {
    Context context;
    ArrayList<ItemsModelGift> data = new ArrayList<ItemsModelGift>();

    public ItemsAdaptergift(Context context, int layoutResourceId, ArrayList<ItemsModelGift> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemsAdaptergift.RecordHolder2 holder = null;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView ItemNo,ItemName,Unit,Qty;


        row = inflater.inflate(R.layout.item_rowgift, parent, false);
        holder = new ItemsAdaptergift.RecordHolder2();
        holder.ItemNo = (TextView) row.findViewById(R.id.ItemNo);
        holder.ItemName = (TextView) row.findViewById(R.id.ItemName);
        holder.Unit = (TextView) row.findViewById(R.id.Unit);
        holder.Qty = (TextView) row.findViewById(R.id.Qty);

        row.setTag(holder);

        ItemsModelGift item = data.get(position);
        holder.ItemNo.setText(item.getItemNo());
        holder.Unit.setText(item.getUnit());
        holder.Qty.setText(item.getQty());
        LocaleHelper localeHelper=new LocaleHelper();

        String name;

        if(localeHelper.getlanguage(context).equals("ar"))
            name= DB.GetValue(context,"itemsn","ItemNameA","ItemNo ='"+item.getItemNo()+"'");
        else
            name=DB.GetValue(context,"itemsn","ItemNameE","ItemNo ='"+item.getItemNo()+"' ");

        holder.ItemName.setText(name);


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

    static class RecordHolder2 {
        TextView ItemNo,ItemName,Unit,Qty;


    }
}


