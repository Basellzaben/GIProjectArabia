package com.cds_jo.GalaxySalesApp.TQNew;

public class Cls_Item_Expire {
    public String getItem_No() {
        return Item_No;
    }

    public void setItem_No(String item_No) {
        Item_No = item_No;
    }

    public String getItem_Nm() {
        return Item_Nm;
    }

    public void setItem_Nm(String item_Nm) {
        Item_Nm = item_Nm;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    String Item_No,Item_Nm,expdate,batchno,net;
}

