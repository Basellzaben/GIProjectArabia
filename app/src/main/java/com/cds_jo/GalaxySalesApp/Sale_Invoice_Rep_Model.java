package com.cds_jo.GalaxySalesApp;

public class Sale_Invoice_Rep_Model {

    public Sale_Invoice_Rep_Model() {
    }

    String wight, name, qty;

    public String getWight() {
        return wight;
    }

    public void setWight(String wight) {
        this.wight = wight;
    }

    public String getname() {
        return name;
    }

    public void setname(String unit) {
        this.name = unit;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public Sale_Invoice_Rep_Model(String wight, String name, String qty) {
        this.wight = wight;
        this.name = name;
        this.qty = qty;
    }
}