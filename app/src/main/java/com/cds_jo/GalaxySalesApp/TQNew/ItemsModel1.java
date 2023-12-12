package com.cds_jo.GalaxySalesApp.TQNew;

public class ItemsModel1 {

    String ItemNo,ItemName,Unit,Total,Tax,TaxAmt,Price,Qty;

    public String getItemNo() {
        return ItemNo;
    }

    public void setItemNo(String itemNo) {
        ItemNo = itemNo;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getTax() {
        return Tax;
    }

    public void setTax(String tax) {
        Tax = tax;
    }

    public String getTaxAmt() {
        return TaxAmt;
    }

    public void setTaxAmt(String taxAmt) {
        TaxAmt = taxAmt;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public ItemsModel1() {
    }

    public ItemsModel1(String itemNo, String itemName, String unit, String total, String tax, String taxAmt, String price, String qty) {
        ItemNo = itemNo;
        ItemName = itemName;
        Unit = unit;
        Total = total;
        Tax = tax;
        TaxAmt = taxAmt;
        Price = price;
        Qty = qty;
    }
}

