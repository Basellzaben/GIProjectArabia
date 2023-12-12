package com.cds_jo.GalaxySalesApp;

public class Cls_ClincVisitsReport {
    String OrderNo;
    String CustNm;

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getCustNm() {
        return CustNm;
    }

    public void setCustNm(String custNm) {
        CustNm = custNm;
    }

    public String getVNotes() {
        return VNotes;
    }

    public void setVNotes(String VNotes) {
        this.VNotes = VNotes;
    }

    public String getSNotes() {
        return SNotes;
    }

    public void setSNotes(String SNotes) {
        this.SNotes = SNotes;
    }

    public String getTr_Date() {
        return Tr_Date;
    }

    public void setTr_Date(String tr_Date) {
        Tr_Date = tr_Date;
    }

    public String getTr_Time() {
        return Tr_Time;
    }

    public void setTr_Time(String tr_Time) {
        Tr_Time = tr_Time;
    }

    public String getVisitType() {
        return VisitType;
    }

    public void setVisitType(String visitType) {
        VisitType = visitType;
    }

    public String getFreeSample() {
        return FreeSample;
    }

    public void setFreeSample(String freeSample) {
        FreeSample = freeSample;
    }

    public String getSubjects() {
        return Subjects;
    }

    public void setSubjects(String subjects) {
        Subjects = subjects;
    }

    String VNotes;
    String SNotes;
    String Tr_Date;
    String Tr_Time;
    String VisitType;
    String FreeSample;
    String Subjects;
}
