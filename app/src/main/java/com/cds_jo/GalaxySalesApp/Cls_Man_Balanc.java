package com.cds_jo.GalaxySalesApp;

/**
 * Created by Hp on 08/03/2016.
 */
public class Cls_Man_Balanc {






    public String getQtyAcc() {
        return qtyAcc;
    }

    public void setQtyAcc(String qtyAcc) {
        this.qtyAcc = qtyAcc;
    }

    String qtyAcc ;

    public String getQtySaled() {
        return qtySaled;
    }

    public void setQtySaled(String qtySaled) {
        this.qtySaled = qtySaled;
    }

    String qtySaled ;

    public String getItem_Name() {
        return Item_Name;
    }


    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    String Item_Name ;

    public String getAct_Aty() {
        return Act_Aty;
    }

    public void setAct_Aty(String act_Aty) {
        Act_Aty = act_Aty;
    }

    String Act_Aty ;



    public String getItemno() {
        return itemno;
    }

    public void setItemno(String itemno) {
        this.itemno = itemno;
    }





    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }




    public String getUnitNo() {
        return UnitNo;
    }

    public void setUnitNo(String unitNo) {
        UnitNo = unitNo;
    }

    public String getUnitRate() {
        return UnitRate;
    }

    public void setUnitRate(String unitRate) {
        UnitRate = unitRate;
    }


    String weight;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    String itemno;
            String qty;
            String UnitNo;
            String UnitRate;

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    String UnitName;

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    String diff;

    public Cls_Man_Balanc() {
    }

    public Cls_Man_Balanc(String qtyAcc, String qtySaled, String item_Name, String act_Aty, String itemno, String qty, String unitNo, String unitRate, String unitName, String diff,String weight) {
        this.qtyAcc = qtyAcc;
        this.qtySaled = qtySaled;
        Item_Name = item_Name;
        Act_Aty = act_Aty;
        this.itemno = itemno;
        this.qty = qty;
        UnitNo = unitNo;
        UnitRate = unitRate;
        UnitName = unitName;
        this.diff = diff;
        this.weight=weight;
    }
}
