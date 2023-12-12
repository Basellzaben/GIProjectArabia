package com.cds_jo.GalaxySalesApp.TQNew;

public class Cls_GetCustomerQty {
    public String getINVOICEID() {
        return INVOICEID;
    }

    public void setINVOICEID(String INVOICEID) {
        this.INVOICEID = INVOICEID;
    }

    public String getINVOICEDATE() {
        return INVOICEDATE;
    }

    public void setINVOICEDATE(String INVOICEDATE) {
        this.INVOICEDATE = INVOICEDATE;
    }

    public String getITEMID() {
        return ITEMID;
    }

    public void setITEMID(String ITEMID) {
        this.ITEMID = ITEMID;
    }

    public String getSALESQTY() {
        return SALESQTY;
    }

    public void setSALESQTY(String SALESQTY) {
        this.SALESQTY = SALESQTY;
    }

    public String getBOUNCE() {
        return BOUNCE;
    }

    public void setBOUNCE(String BOUNCE) {
        this.BOUNCE = BOUNCE;
    }

    String INVOICEID;
    String INVOICEDATE;
    String ITEMID;
    String SALESQTY;
    String BOUNCE;

    public String getBATCHID() {
        return BATCHID;
    }

    public void setBATCHID(String BATCHID) {
        this.BATCHID = BATCHID;
    }

    String BATCHID;
}
