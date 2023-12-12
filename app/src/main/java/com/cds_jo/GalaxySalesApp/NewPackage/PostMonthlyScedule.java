package com.cds_jo.GalaxySalesApp.NewPackage;

import android.content.Context;
import android.database.Cursor;

import com.cds_jo.GalaxySalesApp.DB;
import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PostMonthlyScedule {
    Context context;
    String query;
    SqlHandler sqlHandler;
    ArrayList<Cls_Post_Monthly_Schedule> PoList;

    public PostMonthlyScedule(Context conText) {
        context = conText;
        sqlHandler = new SqlHandler(conText);

    }

    public void Post_Scedule(String Month, String Year) {
        long Result = -1;

        PoList = new ArrayList<Cls_Post_Monthly_Schedule>();
        PoList.clear();

        Fill_Po_Adapter(Month, Year);
        String json = "[{''}]";
        try {
            if (PoList.size() > 0) {
                json = new Gson().toJson(PoList);

            } else
            {
                Result = -1;
                ;
            }
        } catch (Exception ex) {
            Result = -1;
            ;
        }

        final String str;


        if(json.length() < 10) {
            Result = -1;
        }
        str = json;
        CallWebServices ws = new CallWebServices(context);
        Result = ws.Save_MonthlySedule(str);

        try {
            if (Result > 0) {
                sqlHandler.executeQuery("Update Monthly_Schedule set Posted ='" + Result + "' Where TrYear ='" + Year + "' and TrMonth='" + Month + "'");
            }
        } catch (final Exception e) {
            Result = -1;
        }
        // return str;
    }

    private void Fill_Po_Adapter(String Month, String Yrar) {
        SimpleDateFormat Formt = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
        SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String query = "";
        String query1 = "";
        String Look = "";
        String Look1="";
        String Look2="";
        String Look3="";
        String VisitCustomers = "";
        String areano = "";
        PoList = new ArrayList<Cls_Post_Monthly_Schedule>();
        PoList.clear();

        query = "  Select   Period_No, Today_Date   ,User_No   ,Posted ,TrYear,TrMonth,DoubleVisit from Monthly_Schedule"; //+
        //    "      Where TrYear ='" + Yrar + "' and TrMonth='" + Month + "'";
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Cls_Post_Monthly_Schedule contactListItems = new Cls_Post_Monthly_Schedule();
                    String dat=c1.getString(c1.getColumnIndex("Today_Date"));
                    Date date1 = null;
                    try {
                        date1=formatter1.parse(dat);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String areaNo="";
                    String strCurrDate = Formt.format(date1);
                    contactListItems.setToday_Date(strCurrDate);
                    contactListItems.setPeriod_No(c1.getString(c1
                            .getColumnIndex("Period_No")));

                 /*   if(Look1.equals(""))
                    {*/
                    Look1 = getLooks(c1.getString(c1.getColumnIndex("Today_Date")) ,c1.getString(c1.getColumnIndex("Period_No")) ,"2");
             /*       }
                    else
                    {
                        if( getLooks(c1.getString(c1.getColumnIndex("Today_Date")) ,c1.getString(c1.getColumnIndex("Period_No")) ,"2").equals(""))
                        {
                            Look1="";
                        }
                        else
                        {
                            Look1 = ","+ getLooks(c1.getString(c1.getColumnIndex("Today_Date")) ,c1.getString(c1.getColumnIndex("Period_No")) ,"2");
                        }*/




                    contactListItems.setArea_No(Look1);
                    //contactListItems.setArea_No(areaNo);

                    contactListItems.setUser_No(c1.getString(c1
                            .getColumnIndex("User_No")));
                    contactListItems.setTrYear(c1.getString(c1
                            .getColumnIndex("TrYear")));
                    contactListItems.setTrMonth(c1.getString(c1
                            .getColumnIndex("TrMonth")));
                    contactListItems.setDoubleVisit(c1.getString(c1
                            .getColumnIndex("DoubleVisit")));


                  /*  if(Look2.equals(""))
                    {*/
                    Look2 = getLooks(c1.getString(c1.getColumnIndex("Today_Date")) ,c1.getString(c1.getColumnIndex("Period_No")) ,"1");
                  /*  }
                    else
                    {
                        Look2 = ","+ getLooks(c1.getString(c1.getColumnIndex("Today_Date")) ,c1.getString(c1.getColumnIndex("Period_No")) ,"1");
                    }*/
                    contactListItems.setLookupG(Look2);

                /*    if(Look3.equals(""))
                    {*/
                    Look3 = getLooks(c1.getString(c1.getColumnIndex("Today_Date")) ,c1.getString(c1.getColumnIndex("Period_No")) ,"3");
                /*    }
                    else
                    {
                        Look3 = ","+ getLooks(c1.getString(c1.getColumnIndex("Today_Date")) ,c1.getString(c1.getColumnIndex("Period_No")) ,"3");
                    }
*/
                    contactListItems.setVisitCustomers(Look3);




                    PoList.add(contactListItems);
                } while (c1.moveToNext());

            }
            c1.close();
        }


    }
    public String getLooks(String Date,String Period_No,String Tabletno)
    {
        String query = "  SELECT itemno FROM PlanMonthlyLookups " +
                "      Where period ='" + Period_No + "' and Date='" + Date + "'and TabletNo ='"+Tabletno+"'";
        Cursor c2 = sqlHandler.selectQuery(query);
        String Look="";
        if (c2 != null && c2.getCount() != 0) {
            if (c2.moveToFirst()) {
                do {
                    if(Tabletno.equals("3")) {
                        if (Look.equals("")) {
                            Look = String.valueOf(c2.getString(c2
                                    .getColumnIndex("itemno")));
                        } else {
                            Look += "," + String.valueOf(c2.getString(c2
                                    .getColumnIndex("itemno")));

                        }
                    }
                    else if(Tabletno.equals("2"))
                    {
                        String country2= DB.GetValue(context,"AreasN","Id","ItemNo='"+c2.getString(c2.getColumnIndex("itemno"))+"'and TableNo ='9'");

                        if (Look.equals("")) {
                            Look = String.valueOf(country2);
                        } else {
                            Look += "," + String.valueOf(country2);

                        }}
                    else
                    {
                        String country2= DB.GetValue(context,"AreasN","Id","ItemNo='"+c2.getString(c2.getColumnIndex("itemno"))+"'and TableNo ='13'");

                        if (Look.equals("")) {
                            Look = String.valueOf(country2);
                        } else {
                            Look += "," + String.valueOf(country2);

                        }
                    }


                } while (c2.moveToNext());

            }
            c2.close();
        }
        return Look;

    }
}


