package com.cds_jo.GalaxySalesApp;

public class ItemsModelGift {

    String ItemNo,ItemName,Unit,Qty;

    public ItemsModelGift() {
    }

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

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public ItemsModelGift(String itemNo, String itemName, String unit, String qty) {
        ItemNo = itemNo;
        ItemName = itemName;
        Unit = unit;
        Qty = qty;
    }
}
