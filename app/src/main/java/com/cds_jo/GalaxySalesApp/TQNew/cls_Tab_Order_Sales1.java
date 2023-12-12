package com.cds_jo.GalaxySalesApp.TQNew;

public class cls_Tab_Order_Sales1 {
    String custNm;
    String custNo;
    String Date;
    String Notes;
    String Acc;

    public String getPosted() {
        return Posted;
    }

    public void setPosted(String posted) {
        Posted = posted;
    }

    String Posted;
    public String getTot() {
        return Tot;
    }

    public void setTot(String tot) {
        Tot = tot;
    }

    String Tot;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    String Type;
    public String getAcc() {
        return Acc;
    }

    public void setAcc(String acc) {
        Acc = acc;
    }



    public String getCustNm() {
        return custNm;
    }

    public void setCustNm(String custNm) {
        this.custNm = custNm;
    }
    public cls_Tab_Order_Sales1(String custNo, String custNm, String Date, String Notes) {
        super();
        this.custNm=custNm;
        this.custNo = custNo;
        this.Date = Date;
        this.Notes = Notes;

    }
    public cls_Tab_Order_Sales1() {
        super();
        this.custNm="";
        this.custNo = "";
        this.Date = "";
        this.Notes = "";
        this.Acc="";

    }
    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }


}

