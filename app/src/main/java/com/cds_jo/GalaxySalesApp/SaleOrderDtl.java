package com.cds_jo.GalaxySalesApp;

public class SaleOrderDtl {

String Item_no,Item_Name,price,Qty,Tax_Amt,Total,UnitName,Htotal,Dis_Amt,Bounce,Notes;

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

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

    public String getTax_Amt() {
        return Tax_Amt;
    }

    public void setTax_Amt(String tax_Amt) {
        Tax_Amt = tax_Amt;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getHtotal() {
        return Htotal;
    }

    public void setHtotal(String htotal) {
        Htotal = htotal;
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
}
