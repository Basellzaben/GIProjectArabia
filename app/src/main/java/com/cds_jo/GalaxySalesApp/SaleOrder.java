package com.cds_jo.GalaxySalesApp;

public class SaleOrder {



    String inovice_type,CustName,OrderNo,Tr_Date,Net_Total,ArabicName;
    String CustNo,Total,TaxTotal,DiscTotal,note,DeliveryDate;

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInovice_type() {
        return inovice_type;
    }

    public void setInovice_type(String inovice_type) {
        this.inovice_type = inovice_type;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getTr_Date() {
        return Tr_Date;
    }

    public void setTr_Date(String tr_Date) {
        Tr_Date = tr_Date;
    }

    public String getNet_Total() {
        return Net_Total;
    }

    public void setNet_Total(String net_Total) {
        Net_Total = net_Total;
    }

    public String getArabicName() {
        return ArabicName;
    }

    public void setArabicName(String arabicName) {
        ArabicName = arabicName;
    }

    public String getCustNo() {
        return CustNo;
    }

    public void setCustNo(String custNo) {
        CustNo = custNo;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getTaxTotal() {
        return TaxTotal;
    }

    public void setTaxTotal(String taxTotal) {
        TaxTotal = taxTotal;
    }

    public String getDiscTotal() {
        return DiscTotal;
    }

    public void setDiscTotal(String discTotal) {
        DiscTotal = discTotal;
    }
}
