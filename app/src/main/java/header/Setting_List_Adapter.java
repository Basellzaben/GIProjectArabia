package header;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cds_jo.GalaxySalesApp.Clinc_Visits_ReportActivity;
import com.cds_jo.GalaxySalesApp.ComInfo;
import com.cds_jo.GalaxySalesApp.Companies;
import com.cds_jo.GalaxySalesApp.CusfCard;
import com.cds_jo.GalaxySalesApp.CustLocations.CustomerLocation;
import com.cds_jo.GalaxySalesApp.CustomerNotes;
import com.cds_jo.GalaxySalesApp.CustomerQty;
import com.cds_jo.GalaxySalesApp.CustomerSigActivity;
import com.cds_jo.GalaxySalesApp.CustomerSummary.CustomerSummaryAct;
import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.DoctorReportSearchActivity;

import com.cds_jo.GalaxySalesApp.Exchange_voucher;
import com.cds_jo.GalaxySalesApp.GalaxyLoginActivity;
import com.cds_jo.GalaxySalesApp.GetPermession;

import com.cds_jo.GalaxySalesApp.GlobaleVar;
import com.cds_jo.GalaxySalesApp.ItemsDameged;

import com.cds_jo.GalaxySalesApp.Location_Activity;
import com.cds_jo.GalaxySalesApp.Location_Fragment;
import com.cds_jo.GalaxySalesApp.ManCard.DetailCardMan;
import com.cds_jo.GalaxySalesApp.Man_Qty;
import com.cds_jo.GalaxySalesApp.MyRequests;
import com.cds_jo.GalaxySalesApp.NewLoginActivity;
import com.cds_jo.GalaxySalesApp.NewPackage.GalaxyNewHomeActivity;
import com.cds_jo.GalaxySalesApp.NewPackage.LocaleHelper;
import com.cds_jo.GalaxySalesApp.PopShowOffers;

import com.cds_jo.GalaxySalesApp.Pop_Man_Vac;
import com.cds_jo.GalaxySalesApp.PreapareManQty;
import com.cds_jo.GalaxySalesApp.R;

import com.cds_jo.GalaxySalesApp.Reports.Report_Home;

import com.cds_jo.GalaxySalesApp.Sales_Invoices_Report;
import com.cds_jo.GalaxySalesApp.Sales_Invoices_Report2;
import com.cds_jo.GalaxySalesApp.SittingNew;
import com.cds_jo.GalaxySalesApp.TQNew.PopShowCustLastInvoice1;
import com.cds_jo.GalaxySalesApp.TQNew.PopShowInvoiceFromBatch;
import com.cds_jo.GalaxySalesApp.TQNew.ReoprtSaleActivity;
import com.cds_jo.GalaxySalesApp.Tracking.MapsActivity;
import com.cds_jo.GalaxySalesApp.TransferQty;
import com.cds_jo.GalaxySalesApp.VisitImges;

import com.cds_jo.GalaxySalesApp.VisitsDone_Report;
import com.cds_jo.GalaxySalesApp.WebPage.WebPageAct;
import com.cds_jo.GalaxySalesApp.assist.ManAttenActivity;
import com.cds_jo.GalaxySalesApp.assist.Monthly_Items_AmountAct;
import com.cds_jo.GalaxySalesApp.assist.PopShowCode;
import com.cds_jo.GalaxySalesApp.assist.Sale_ReturnActivity;
import com.cds_jo.GalaxySalesApp.assist.TOfCollections;
import com.cds_jo.GalaxySalesApp.requisitionsselling;
import com.cds_jo.GalaxySalesApp.returnRequest;
import com.cds_jo.GalaxySalesApp.returnsforsupervisor;

import java.util.ArrayList;
import java.util.Locale;

import Methdes.MethodToUse;
import Methdes.MyTextView;


public class Setting_List_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Main_List_Itme> myList;
    private LayoutInflater layoutInflater;
    private Typeface typeface;

    public Setting_List_Adapter(Context context, ArrayList<Main_List_Itme> myList) {
        this.context = context;
        this.myList = myList;
        layoutInflater = LayoutInflater.from(context);
        typeface = MethodToUse.SetTFace(context);

    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.setting_item1, parent, false);
        MyTextView Title = (MyTextView) convertView.findViewById(R.id.textView69);
        ImageView img = (ImageView) convertView.findViewById(R.id.imageView6);
        LinearLayout RR = (LinearLayout) convertView.findViewById(R.id.RR_Set);
        Title.setText(myList.get(position).getTitle());
        img.setImageResource(myList.get(position).getImg());
        // Title.setTypeface(typeface);

        // Title.setTypeface(Typeface.createFromAsset(context.getAssets(), "Hacen Tunisia Lt.ttf"));

        RR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Toast.makeText(context,position+"",Toast.LENGTH_LONG).show();
                GetPermession obj = new GetPermession();
                //Toast.makeText(context.getApplicationContext(), position +"", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                String RepType;
                RepType = sharedPreferences.getString("TypeRep", "-1");
                if (position == 0) {
                    if (RepType.equals("1")) {

                        if (ComInfo.ComNo == Companies.Afrah.getValue()) {

                            Intent intent = new Intent(context.getApplicationContext(), ReoprtSaleActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            // ((Activity) context).finish();
                        } else {
                            Intent intent = new Intent(context.getApplicationContext(), Sale_ReturnActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
                    } else {

                      /*  FragmentManager manager = ((Activity)context).getFragmentManager();

                        DoctorReportSearchActivity obj2 = new DoctorReportSearchActivity();
                        obj2.show(manager, null);

                     */  // Intent intent = new Intent(context.getApplicationContext(), DoctorReportActivity.class);

                        Intent intent = new Intent(context.getApplicationContext(), Clinc_Visits_ReportActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                        // ((Activity) context).finish();


                    }

                } else if (position == 1) {
                    if (RepType.equals("1")) {
                        if (ComInfo.ComNo == Companies.Afrah.getValue()) {


                            Bundle bundle = new Bundle();
                            bundle.putString("Scr", "po");
                            bundle.putString("ACC", sharedPreferences.getString("CustNo", ""));
                            FragmentManager Manager = ((Activity) context).getFragmentManager();
                            PopShowCustLastInvoice1 popShowCustLastInvoice = new PopShowCustLastInvoice1();
                            popShowCustLastInvoice.setArguments(bundle);
                            popShowCustLastInvoice.show(Manager, null);
                        } else {
                            Intent intent = new Intent(context.getApplicationContext(), PreapareManQty.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }

                    } else {
                        Intent intent = new Intent(context.getApplicationContext(), VisitsDone_Report.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }

                } else if (position == 2) {
                    if (RepType.equals("1")) {
                        if (ComInfo.ComNo == Companies.Afrah.getValue()) {
                            Bundle bundle = new Bundle();
                            bundle.putString("Scr", "po");
                            bundle.putString("ACC", sharedPreferences.getString("CustNo", ""));
                            FragmentManager Manager = ((Activity) context).getFragmentManager();
                            PopShowInvoiceFromBatch popShowInvoiceFromBatch = new PopShowInvoiceFromBatch();
                            popShowInvoiceFromBatch.setArguments(bundle);
                            popShowInvoiceFromBatch.show(Manager, null);

                        } else {
                            Intent intent = new Intent(context.getApplicationContext(), TransferQty.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }

                    } else {
                        String lan = LocaleHelper.getlanguage(context);//.setLocale(getActivity(), "ar");


                        if (lan.equals("ar")) {

                            Context contextt = LocaleHelper.setLocale(context, "ar");
                            Resources resources = contextt.getResources();
                            //Toast.makeText(context, "تم تغيير اللغة الى اللغة العربية" + "  " + lan, Toast.LENGTH_LONG).show();

                            Locale locale = new Locale("ar");
                            ComInfo.Lan = "ar";
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;


                            LocaleHelper localeHelper = new LocaleHelper();
                            localeHelper.setLocale(context, "ar");

                            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

                            //  Title.setText("English language");

                        } else if (lan.equals("en")) {

                            Context contextt = LocaleHelper.setLocale(context, "ar");
                            Resources resources = contextt.getResources();
                            // Toast.makeText(context,"تم تغيير اللغة الى اللغة الانجليزية"+"  "+lan,Toast.LENGTH_LONG).show();

                            Locale locale = new Locale("ar");
                            ComInfo.Lan = "ar";
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

                            LocaleHelper localeHelper = new LocaleHelper();
                            localeHelper.setLocale(context, "ar");
                            localeHelper.updateResourcesLegacy(context, "ar");


                        }

                        ((Activity) context).finish();
                        context.startActivity(((Activity) context).getIntent());


                    }

                } else if (position == 3) {
                    if (RepType.equals("1")) {
                        if (ComInfo.ComNo == Companies.Afrah.getValue()) {
                            Intent intent = new Intent(context.getApplicationContext(), CustomerLocation.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            // ((Activity) context).finish();


                        } else {
                          //    if ( ComInfo.ComNo != Companies.nwaah.getValue() ) {
                                Intent intent = new Intent(context.getApplicationContext(),CusfCard.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                         //    }
                        }

                    } else {
                        Intent intent = new Intent(context.getApplicationContext(), NewLoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }

                } else if (position == 4) {

                    if (RepType.equals("1")) {
                        //       Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                        if (ComInfo.ComNo == Companies.Afrah.getValue()) {


                            String lan = LocaleHelper.getlanguage(context);//.setLocale(getActivity(), "ar");


                            if (lan.equals("ar")) {

                                Context contextt = LocaleHelper.setLocale(context, "ar");
                                Resources resources = contextt.getResources();
                                //Toast.makeText(context, "تم تغيير اللغة الى اللغة العربية" + "  " + lan, Toast.LENGTH_LONG).show();

                                Locale locale = new Locale("ar");
                                ComInfo.Lan = "ar";
                                Locale.setDefault(locale);
                                Configuration config = new Configuration();
                                config.locale = locale;


                                LocaleHelper localeHelper = new LocaleHelper();
                                localeHelper.setLocale(context, "ar");

                                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
                                GlobaleVar.LanType = 2;
                                //  Title.setText("English language");

                            } else if (lan.equals("en")) {

                                Context contextt = LocaleHelper.setLocale(context, "ar");
                                Resources resources = contextt.getResources();
                                // Toast.makeText(context,"تم تغيير اللغة الى اللغة الانجليزية"+"  "+lan,Toast.LENGTH_LONG).show();

                                Locale locale = new Locale("ar");
                                ComInfo.Lan = "ar";
                                Locale.setDefault(locale);
                                Configuration config = new Configuration();
                                config.locale = locale;
                                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

                                LocaleHelper localeHelper = new LocaleHelper();
                                localeHelper.setLocale(context, "ar");
                                localeHelper.updateResourcesLegacy(context, "ar");

                                GlobaleVar.LanType = 1;

                            }

/*
                            ((Activity) context).finish();
                            context.startActivity( ((Activity) context).getIntent());*/
                            Intent intent = new Intent(context.getApplicationContext(), GalaxyNewHomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);

                        } else {
                            Intent intent = new Intent(context.getApplicationContext(), CustomerLocation.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }

                    } else {
                        Intent intent = new Intent(context.getApplicationContext(), NewLoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }


                } else if (position == 5) {


                        /*Intent intent = new Intent(context.getApplicationContext(), ItemGalleryActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                        ((Activity)context).finish();*/
                    if (RepType.equals("1")) {
                        if (ComInfo.ComNo == Companies.Afrah.getValue()) {

                            Intent intent = new Intent(context.getApplicationContext(), NewLoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            //  ((Activity) context).finish();

                        } else {
                            Intent intent = new Intent(context.getApplicationContext(), CustomerSummaryAct.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
                    }

                } else if (position == 6) {
                    /*Bundle bundle = new Bundle();
                    bundle.putString("Scr", "po");
                    bundle.putString("CatNo", "");
                    FragmentManager Manager = ((Activity) context).getFragmentManager();
                    PopShowCustLastTrans popShowOffers = new PopShowCustLastTrans();
                    popShowOffers.setArguments(bundle);
                    popShowOffers.show(Manager, null);*/
                    Intent intent = new Intent(context.getApplicationContext(), CustomerQty.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();


                } else if (position == 7) {
                    Intent intent = new Intent(context.getApplicationContext(), CustomerNotes.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();


                } else if (position == 8) {
                    Intent intent = new Intent(context.getApplicationContext(), VisitImges.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();

                } /*else if (position ==6) {
                    Intent intent = new Intent(context.getApplicationContext(), VisitImges.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }*/ else if (position == 9) {
                    Intent intent = new Intent(context.getApplicationContext(), Man_Qty.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();

                } else if (position == 10) {
                    Intent intent = new Intent(context.getApplicationContext(), ItemsDameged.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();

                } else if (position == 11) {
                    Intent intent = new Intent(context.getApplicationContext(), Monthly_Items_AmountAct.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();

                } else if (position == 12) {
                    Intent intent = new Intent(context.getApplicationContext(), TOfCollections.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();

                } else if (position == 13) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Scr", "po");
                    bundle.putString("CatNo", "");
                    FragmentManager Manager = ((Activity) context).getFragmentManager();
                    PopShowCode popShowOffers = new PopShowCode();
                    popShowOffers.setArguments(bundle);
                    popShowOffers.show(Manager, null);

                }

             /*   else if (position == 1111) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Login", "No");
                    editor.commit();

                    Intent intent = new Intent(context.getApplicationContext(), NewLoginActivity.class);
                    // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);

                } else if (position == 42) {


                    Intent intent = new Intent(context.getApplicationContext(), OrdersItems.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();


                } */
/*                else if (position == 5) {


                    Intent intent = new Intent(context.getApplicationContext(), TransQtyReportActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();


                }
                else if (position == 6) {
                    Intent intent = new Intent(context.getApplicationContext(), UpdateDataToMobileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();

                } else if (position == 7) {

                    Intent intent = new Intent(context.getApplicationContext(), ScheduleManActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();


                } else if (position == 8) {


//
                    Intent intent = new Intent(context.getApplicationContext(), CustomerQty.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();


                } else if (position == 100) {

                    Intent intent = new Intent(context.getApplicationContext(), NotificationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();


                } else if (position == 9) {

                    Intent intent = new Intent(context.getApplicationContext(), QuestneerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();


                } else if (position == 10) {


                    Intent intent = new Intent(context.getApplicationContext(), BluetoothConnectMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();


                } else if (position == 10) {

                    Intent intent = new Intent(context.getApplicationContext(), EditeTransActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();


                }*/
                else if (position == 14) {

                    FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
                    DetailCardMan detailCardMan = new DetailCardMan();
                    detailCardMan.show(fragmentManager, null);


                } else if (position == 15) {

                    Bundle bundle = new Bundle();
                    FragmentManager Manager = ((Activity) context).getFragmentManager();
                    Pop_Man_Vac popShowOffers = new Pop_Man_Vac();
                    popShowOffers.setArguments(bundle);
                    popShowOffers.show(Manager, null);


/*
                    Intent intent = new Intent(context.getApplicationContext(), ManSummeryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();*/


                } else if (position == 16) {

                    Intent intent = new Intent(context.getApplicationContext(), ManAttenActivity.class);
                    // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();


                } else if (position == 17) {

                    Intent intent = new Intent(context.getApplicationContext(), MapsActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();

                } else if (position == 18) {

                    Bundle bundle = new Bundle();
                    bundle.putString("Scr", "po");
                    bundle.putString("CatNo", "");
                    FragmentManager Manager = ((Activity) context).getFragmentManager();
                    PopShowOffers popShowOffers = new PopShowOffers();
                    popShowOffers.setArguments(bundle);
                    popShowOffers.show(Manager, null);


                } else if (position == 19) {


                    Intent intent = new Intent(context.getApplicationContext(), WebPageAct.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();

//                        Intent k = new Intent(v.getContext(),ManSummeryActivity.class);
//                        context.startActivity(k);


                } else if (position == 20) {

//تقرير المشرف
                    Intent intent = new Intent(context.getApplicationContext(), Report_Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();

//                        Intent k = new Intent(v.getContext(),ManSummeryActivity.class);
//                        context.startActivity(k);


                } else if (position == 21) {

         /*  Intent intent = new Intent(context.getApplicationContext(), ReturnItems.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                        ((Activity) context).finish();*/


                    String lan = LocaleHelper.getlanguage(context);//.setLocale(getActivity(), "ar");


                    if (lan.equals("ar")) {

                        Context contextt = LocaleHelper.setLocale(context, "en");
                        Resources resources = contextt.getResources();
                        //Toast.makeText(context, "تم تغيير اللغة الى اللغة العربية" + "  " + lan, Toast.LENGTH_LONG).show();

                        Locale locale = new Locale("ar");
                        ComInfo.Lan = "ar";
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;


                        LocaleHelper localeHelper = new LocaleHelper();
                        localeHelper.setLocale(context, "ar");

                        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
                        GlobaleVar.LanType = 2;
                        //  Title.setText("English language");

                    } else if (lan.equals("en")) {

                        Context contextt = LocaleHelper.setLocale(context, "ar");
                        Resources resources = contextt.getResources();
                        // Toast.makeText(context,"تم تغيير اللغة الى اللغة الانجليزية"+"  "+lan,Toast.LENGTH_LONG).show();

                        Locale locale = new Locale("ar");
                        ComInfo.Lan = "ar";
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

                        LocaleHelper localeHelper = new LocaleHelper();
                        localeHelper.setLocale(context, "ar");
                        localeHelper.updateResourcesLegacy(context, "ar");

                        GlobaleVar.LanType = 1;

                    }


                    ((Activity) context).finish();
                    context.startActivity(((Activity) context).getIntent());


                } else if (position == 22) {

//                        Intent k = new Intent(v.getContext(),DoctorReportActivity.class);
//                        context.startActivity(k);

             /*       Intent intent = new Intent(context.getApplicationContext(), DoctorReportActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();*/
                    Intent intent = new Intent(context.getApplicationContext(), SittingNew.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    //((Activity) context).finish();


                } else if (position == 23) {

//                        Intent k = new Intent(v.getContext(),DoctorReportActivity.class);
//                        context.startActivity(k);

             /*       Intent intent = new Intent(context.getApplicationContext(), DoctorReportActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();*/
                    Intent intent = new Intent(context.getApplicationContext(), Sales_Invoices_Report2.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();


                } else if (position == 24) {
                    Intent intent = new Intent(context.getApplicationContext(), Location_Activity.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
/*

//                        Intent k = new Intent(v.getContext(),DoctorReportActivity.class);
//                        context.startActivity(k);

             */
/*       Intent intent = new Intent(context.getApplicationContext(), DoctorReportActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();*//*

                    Intent intent = new Intent(context.getApplicationContext(), CustomerLocations.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
*/


                } else if (position == 25) {

//                        Intent k = new Intent(v.getContext(),DoctorReportActivity.class);
//                        context.startActivity(k);

             /*       Intent intent = new Intent(context.getApplicationContext(), DoctorReportActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();*/
                    Intent intent = new Intent(context.getApplicationContext(), NewLoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();


                } else if (position == 26) {
                    Intent intent = new Intent(context.getApplicationContext(), Exchange_voucher.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();


                } else if (position == 27) {
                    Intent intent = new Intent(context.getApplicationContext(), returnRequest.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }


                else if (position == 28) {

                     sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                     String UserID = sharedPreferences.getString("UserID", "-1");
                     String ManType = DB.GetValue(context, "manf", "ManType", "man='" + UserID + "'");
               if (!ManType.equals("2")) {

                     Intent intent = new Intent(context.getApplicationContext(), MyRequests.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
               }else{
                   Intent intent = new Intent(context.getApplicationContext(), requisitionsselling.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   context.startActivity(intent);
                    }
                }

                 else if (position == 29) {


                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    String UserID = sharedPreferences.getString("UserID", "-1");
                    String ManType = DB.GetValue(context, "manf", "ManType", "man='" + UserID + "'");
                    if (!ManType.equals("2")) {


                        Intent intent = new Intent(context.getApplicationContext(), requisitionsselling.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);

                    }else{
                        Intent intent = new Intent(context.getApplicationContext(), returnsforsupervisor.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }
                } else if (position == 30) {
                    Intent intent = new Intent(context.getApplicationContext(), returnsforsupervisor.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }


            }
        });
        return convertView;
    }
}
