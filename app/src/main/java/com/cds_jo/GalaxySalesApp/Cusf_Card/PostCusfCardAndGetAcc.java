package com.cds_jo.GalaxySalesApp.Cusf_Card;


import android.content.Context;
import android.database.Cursor;
import android.provider.Settings;

import com.cds_jo.GalaxySalesApp.SqlHandler;
import com.cds_jo.GalaxySalesApp.assist.CallWebServices;

import org.json.JSONObject;

public class PostCusfCardAndGetAcc {
    Context context;
    SqlHandler sqlHandler;
    String Result;
    String query ;
    String COMPUTERNAME;
    public PostCusfCardAndGetAcc(Context conText) {
        context = conText;
        sqlHandler = new SqlHandler(conText);

          query="" ;
    }

    public String DoTrans(String No) {
        String Result = "-1";
        Result = PostDate(No);
        return Result+"";
    }

    public String PostDate(String No) {
        Result = "-1";
        COMPUTERNAME= Settings.Secure.getString(context.getContentResolver(), "bluetooth_name"  );
        COMPUTERNAME=COMPUTERNAME+" (" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID  )+")";
        String Nm , AreaDesc,CustType,Mobile,Acc,Lan,Long,GpsLocation,UserID;
        Nm =AreaDesc=CustType=Mobile=Acc=Lan=Long=GpsLocation=UserID="";
        String q = "   ";

        String Sat="";
        String Sun="";
        String Mon="";
        String Tues="";
        String Wens="";
        String Thurs="";
        String Frid="";


          q= "select  Nm,AreaDesc,CustType, Mobile,Lan,Long,GpsLocation,Acc, UserID " +
                  " ,Sat,Sun,Mon,Tues,Wens,Thurs,Frid " +
                  "from CusfCard where OrderNo='"+No+"'";
        Cursor c = sqlHandler.selectQuery(q);
        if(c!=null  && c.getCount()> 0 ){
            c.moveToNext();
            Nm= c.getString(c.getColumnIndex("Nm"));
            AreaDesc =c.getString(c.getColumnIndex("AreaDesc"));
            CustType =c.getString(c.getColumnIndex("CustType"));
            Mobile =c.getString(c.getColumnIndex("Mobile"));
            Lan =c.getString(c.getColumnIndex("Lan"));
            Long =c.getString(c.getColumnIndex("Long"));
            GpsLocation =c.getString(c.getColumnIndex("GpsLocation"));
            Acc=c.getString(c.getColumnIndex("Acc"));
            UserID=c.getString(c.getColumnIndex("UserID"));


            ////

            Sat=c.getString(c.getColumnIndex("Sat"));
            Sun=c.getString(c.getColumnIndex("Sun"));
            Mon=c.getString(c.getColumnIndex("Mon"));
            Tues=c.getString(c.getColumnIndex("Tues"));
            Wens=c.getString(c.getColumnIndex("Wens"));
            Thurs=c.getString(c.getColumnIndex("Thurs"));
            Frid=c.getString(c.getColumnIndex("Frid"));

            ///

            c.close();
        }


        COMPUTERNAME= Settings.Secure.getString(context.getContentResolver(), "bluetooth_name"  );
        COMPUTERNAME=COMPUTERNAME+" (" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID  )+")";





        CallWebServices ws = new CallWebServices(context);
        Result = ws.PostCustCardAndGetAcc(Nm,No,AreaDesc,CustType,Mobile,Acc,Lan,Long,GpsLocation,UserID
                ,COMPUTERNAME

                ,Sat,Sun,Mon,Tues,Wens,Thurs,Frid

        );
        try {
            if (!Result.equalsIgnoreCase("-1" ) && Double.parseDouble(Result)>0 ) {
                query = " Update  CusfCard  set Posted='"+Result+"',Acc='"+Result+"'   where OrderNo ='"+No+"' ";
                sqlHandler.executeQuery(query);
System.out.print("notpooooooosted");
            }else{

                System.out.print("notpooooooosted:  "+ String.valueOf(Double.parseDouble(Result)));


            }
        } catch (final Exception e) {
            System.out.print("notpooooooosted error "+e.getMessage());


        }

        return Result;
    }






}
