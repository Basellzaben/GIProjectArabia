package com.cds_jo.GalaxySalesApp;

public class RepModel {

    String CustNO,CustName,OrderNo,Tr_Date,DoubleVisit,Tr_Time,VisitType,VisitResult,VisitObjective;

    public RepModel() {
    }

    public RepModel(String custNO, String custName, String orderNo, String tr_Date, String doubleVisit, String tr_Time, String visitType, String visitResult, String visitObjective) {
        CustNO = custNO;
        CustName = custName;
        OrderNo = orderNo;
        Tr_Date = tr_Date;
        DoubleVisit = doubleVisit;
        Tr_Time = tr_Time;
        VisitType = visitType;
        VisitResult = visitResult;
        VisitObjective = visitObjective;
    }

    public String getCustNO() {
        return CustNO;
    }

    public void setCustNO(String custNO) {
        CustNO = custNO;
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

    public String getDoubleVisit() {
        return DoubleVisit;
    }

    public void setDoubleVisit(String doubleVisit) {
        DoubleVisit = doubleVisit;
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

    public String getVisitResult() {
        return VisitResult;
    }

    public void setVisitResult(String visitResult) {
        VisitResult = visitResult;
    }

    public String getVisitObjective() {
        return VisitObjective;
    }

    public void setVisitObjective(String visitObjective) {
        VisitObjective = visitObjective;
    }
}
