package com.cds_jo.GalaxySalesApp.Delivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cds_jo.GalaxySalesApp.Cls_Sal_InvItems;
import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class clsSalesDtalAdapter extends BaseAdapter {

    Context context;
    ArrayList<clsSalesDtl> contactList;

    public clsSalesDtalAdapter(Context context, ArrayList<clsSalesDtl> list) {

        this.context = context;
        contactList = list;
    }

    @Override
    public int getCount() {

        return contactList.size();
    }

    @Override
    public Object getItem(int position) {

        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }
    private  Double SToD(String str){
        String f = "";
        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        final DecimalFormat df = (DecimalFormat)nf;
        str = str.replace(".", "");
        Double d = 0.0;
        if (str.length()==0) {
            str = "0";
        }
        if (str.length()>0)
            try {
                d =  Double.parseDouble(str);
                str = df.format(d).replace(".", "");

            }
            catch (Exception ex)
            {
                str="0";
            }

        df.setParseBigDecimal(true);

     //   d = Double.valueOf(str.trim()).doubleValue();

        return d;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        clsSalesDtl contactListItems = contactList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.contact_list_row, null);

        }

       String Uintname = DB.GetValue(context, "Unites", "UnitName", "Unitno='" + contactListItems.getUnitNo() + "'");//    "SELECT  name   from  manf where man = '" + email.toString() + "' And password='" + password.toString() + "'";
       String Itemname = DB.GetValue(context, "invf", "Item_Name", "Item_No='" + contactListItems.getItem_no() + "'");//    "SELECT  name   from  manf where man = '" + email.toString() + "' And password='" + password.toString() + "'";


        TextView tvSlNo = (TextView) convertView.findViewById(R.id.tv_no);
        tvSlNo.setText(contactListItems.getItem_no());
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
        tvName.setText(Itemname);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tv_Price);
        tvPrice.setText(contactListItems.getPrice());
       /* if(ComInfo.ComNo==Companies.tariget.getValue()){
            tvPrice.setText(contactListItems.getprice());

        }*/

        TextView tvQTY = (TextView) convertView.findViewById(R.id.tv_Qty);
        tvQTY.setText(contactListItems.getQty());
        TextView tvTAX = (TextView) convertView.findViewById(R.id.tv_tax);
        tvTAX.setText(contactListItems.getItemTax());

        TextView Unit = (TextView) convertView.findViewById(R.id.tv_Unit);
        Unit.setText(Uintname);


        TextView Bounce = (TextView) convertView.findViewById(R.id.tv_Bounce);
        Bounce.setText( String.valueOf(SToD(contactListItems.getBonus())));


       /* TextView Disc = (TextView) convertView.findViewById(R.id.tv_Disc_Per);
        Disc.setText(String.valueOf (SToD( contactListItems.getDiscount() ) + SToD(contactListItems.getDisPerFromHdr()) + SToD(contactListItems.getPro_dis_Per())));
*/



        TextView Disc_Amt = (TextView) convertView.findViewById(R.id.tv_Disc_Amt);
        Disc_Amt.setText(contactListItems.getLinedis() ) ;


        TextView Total = (TextView) convertView.findViewById(R.id.tv_Total);
        Total.setText(String.valueOf((contactListItems.getSumWithOutTax())));


        TextView Tax_Amt = (TextView) convertView.findViewById(R.id.tv_Tax_Amt);
        Tax_Amt.setText(contactListItems.getTotalTax());


        return convertView;
    }

}

