package com.cds_jo.GalaxySalesApp.TQNew;

public class RepModel1 {
    String CustNo; String CustName;String OrderNo; String Total;
        String TaxTotal; String NetTotal;String DESC_A; String states;

public RepModel1() {
        }

public RepModel1(String custNo, String custName, String orderNo, String total, String taxTotal, String netTotal, String DESC_A, String states) {
        CustNo = custNo;
        CustName = custName;
        OrderNo = orderNo;
        Total = total;
        TaxTotal = taxTotal;
        NetTotal = netTotal;
        this.DESC_A = DESC_A;
        this.states = states;
        }

public String getCustNo() {
        return CustNo;
        }

public void setCustNo(String custNo) {
        CustNo = custNo;
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

public String getNetTotal() {
        return NetTotal;
        }

public void setNetTotal(String netTotal) {
        NetTotal = netTotal;
        }

public String getDESC_A() {
        return DESC_A;
        }

public void setDESC_A(String DESC_A) {
        this.DESC_A = DESC_A;
        }

public String getStates() {
        return states;
        }

public void setStates(String states) {
        this.states = states;
        }
        }
