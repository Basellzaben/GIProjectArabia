package com.cds_jo.GalaxySalesApp;


public class ItemsRepModel {


    String Item_no; String Item_Name;
    String price; String Qty;
    String UnitName; String Tax_Amt;
    String Dis_Amt; String Bounce;
    String Total;

    public String getItem_no() {
        return Item_no;
    }

    public void setItem_no(String item_no) {
        Item_no = item_no;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getTax_Amt() {
        return Tax_Amt;
    }

    public void setTax_Amt(String tax_Amt) {
        Tax_Amt = tax_Amt;
    }

    public String getDis_Amt() {
        return Dis_Amt;
    }

    public void setDis_Amt(String dis_Amt) {
        Dis_Amt = dis_Amt;
    }

    public String getBounce() {
        return Bounce;
    }

    public void setBounce(String bounce) {
        Bounce = bounce;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public ItemsRepModel() {
    }

    public ItemsRepModel(String item_no, String item_Name, String price, String qty, String unitName, String tax_Amt, String dis_Amt, String bounce, String total) {
        Item_no = item_no;
        Item_Name = item_Name;
        this.price = price;
        Qty = qty;
        UnitName = unitName;
        Tax_Amt = tax_Amt;
        Dis_Amt = dis_Amt;
        Bounce = bounce;
        Total = total;
    }
}
