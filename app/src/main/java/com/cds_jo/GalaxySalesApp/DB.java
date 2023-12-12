package com.cds_jo.GalaxySalesApp;

import android.content.Context;
import android.database.Cursor;

import com.cds_jo.GalaxySalesApp.NewPackage.SqlHandlerMed;

/**
 * Created by Hp on 20/06/2016.
 */
public  class DB {

    public  static String GetValue(Context _context,String Tbl ,String ClnNam ,String WhereStr){
        SqlHandler sqlHandler = new SqlHandler(_context);
        String R = "-1";
        String q = "";
        q= "Select ifnull ("+ClnNam+",'-1')  as v From " +  Tbl + "    Where "+ WhereStr;
        Cursor c= sqlHandler.selectQuery(q);
        try {
            if(c!=null && c.getCount() >0){
                c.moveToFirst();
                R=c.getString(c.getColumnIndex("v"));
              c.close();
            }
        }
        catch ( Exception ex){
            R="-1";
        }



        return R;

    }
    public  static String GetValueT(Context _context,String Tbl ,String ClnNam ,String WhereStr){
        SqlHandler sqlHandler = new SqlHandler(_context);
        String R = "-1";
        String q = "";
        q= "Select Top 1 ifnull ("+ClnNam+",'-1')  as v From " +  Tbl + "    Where "+ WhereStr;
        Cursor c= sqlHandler.selectQuery(q);
        try {
            if(c!=null && c.getCount() >0){
                c.moveToFirst();
                R=c.getString(c.getColumnIndex("v"));
                c.close();
            }
        }
        catch ( Exception ex){
            R="-1";
        }



        return R;

    }
    public  static String GetValueMed(Context _context,String Tbl ,String ClnNam ,String WhereStr){
        SqlHandlerMed sqlHandler = new SqlHandlerMed(_context);
        String R = "-1";
        String q = "";
        q= "Select ifnull ("+ClnNam+",'-1')  as v From " +  Tbl + "    Where "+ WhereStr;
        Cursor c= sqlHandler.selectQuery(q);
        try {
            if(c!=null && c.getCount() >0){
                c.moveToFirst();
                R=c.getString(c.getColumnIndex("v"));
              c.close();
            }
        }
        catch ( Exception ex){
            R="-1";
        }



        return R;

    }
    public  static String GetValueMax(Context _context,String Tbl ,String ClnNam ,String WhereStr){
        SqlHandler sqlHandler = new SqlHandler(_context);
        String R = "-1";
        String q = "";
        q= "Select max ("+ClnNam+")  as v From " +  Tbl + "    Where "+ WhereStr;
        Cursor c= sqlHandler.selectQuery(q);
        try {
            if(c!=null && c.getCount() >0){
                c.moveToFirst();
                R=c.getString(c.getColumnIndex("v"));
                c.close();
            }
        }
        catch ( Exception ex){
            R="-1";
        }



        return R;

    }

    public  static String GetsumValue(Context _context,String Tbl ,String ClnNam ,String WhereStr){
        SqlHandler sqlHandler = new SqlHandler(_context);
        String R = "-1";
        String q = "";
        q= "Select sum("+ClnNam+")  as v From " +  Tbl + "    Where "+ WhereStr;
        Cursor c= sqlHandler.selectQuery(q);
        try {
            if(c!=null && c.getCount() >0){
                c.moveToFirst();
                R=c.getString(c.getColumnIndex("v"));
                c.close();
            }
        }
        catch ( Exception ex){
            R="-1";
        }



        return R;

    }

}
