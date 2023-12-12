package com.cds_jo.GalaxySalesApp;

        import android.content.Context;
        import android.graphics.Color;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.BaseAdapter;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.text.DecimalFormat;
        import java.text.NumberFormat;
        import java.util.ArrayList;
        import java.util.Locale;

public class myrequestAdapter  extends BaseAdapter {
    LinearLayout RR;
    Context context;
    ArrayList<myrequestsmodel> contactList;
    myrequestsmodel myrequestsmodel;
    public myrequestAdapter(Context context, ArrayList<myrequestsmodel> list) {

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

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        myrequestsmodel = contactList.get(position);
        LayoutInflater inflater;
        if (convertView == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.myrequest_row, null);

        }




        TextView inovice_type = (TextView) convertView.findViewById(R.id.inovice_type);
        inovice_type.setText(myrequestsmodel.getInovice_type());

        TextView CustName = (TextView) convertView.findViewById(R.id.CustName);
        CustName.setText(myrequestsmodel.getCustName());

        TextView OrderNo = (TextView) convertView.findViewById(R.id.OrderNo);
        OrderNo.setText(myrequestsmodel.getOrderNo());

        TextView Tr_Date = (TextView) convertView.findViewById(R.id.Tr_Date);
        Tr_Date.setText(myrequestsmodel.getTr_Date());

        TextView CustNo = (TextView) convertView.findViewById(R.id.CustNo);
        CustNo.setText(myrequestsmodel.getCustNo());

        TextView Notes = (TextView) convertView.findViewById(R.id.Notes);
        Notes.setText(myrequestsmodel.getNotes());







        RR=(LinearLayout)convertView.findViewById(R.id.RR);


        if(position%2==0)
        {
            RR.setBackgroundColor(Color.WHITE);

        }
        else
        {
            RR.setBackgroundColor(context.getResources().getColor(R.color.Gray2));

        }


        return convertView;
    }

}
