package com.cds_jo.GalaxySalesApp.TQNew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.R;

import java.util.ArrayList;

public class ItemsAdapter1  extends ArrayAdapter<ItemsModel1> {
    Context context;
    ArrayList<ItemsModel1> data = new ArrayList<ItemsModel1>();

    public ItemsAdapter1(Context context, int layoutResourceId, ArrayList<ItemsModel1> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder2 holder = null;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        row = inflater.inflate(R.layout.item_row1, parent, false);
        holder = new RecordHolder2();
        holder.ItemNo = (TextView) row.findViewById(R.id.ItemNo);
        holder.ItemName = (TextView) row.findViewById(R.id.ItemName);
        holder.Unit = (TextView) row.findViewById(R.id.Unit);
        holder.Total = (TextView) row.findViewById(R.id.Total);
        holder.Tax = (TextView) row.findViewById(R.id.Tax);
        holder.TaxAmt = (TextView) row.findViewById(R.id.TaxAmt);
        holder.Price = (TextView) row.findViewById(R.id.Price);
        holder.Qty = (TextView) row.findViewById(R.id.Qty);
        row.setTag(holder);

        ItemsModel1 item = data.get(position);
        holder.ItemNo.setText(item.getItemNo());
        holder.ItemName.setText(item.getItemName());
        holder.Unit.setText(item.getUnit());
        holder.Total.setText(item.getTotal());
        holder.Tax.setText(item.getTax());
        holder.TaxAmt.setText(item.getTaxAmt());
        holder.Price.setText(item.getPrice());
        holder.Qty.setText(item.getQty());



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

        TextView ItemNo; TextView ItemName;TextView Unit; TextView Total;
        TextView Tax; TextView TaxAmt;TextView Price;TextView Qty;

    }
}

