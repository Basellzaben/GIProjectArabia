package com.cds_jo.GalaxySalesApp.TQNew;

public class check_Model {

    String CustAccount;
    String CheckNumber;
    String MaturityDate ;
    String AmountCurCredit ;

    public check_Model() {

    }

    public String getCustAccount() {
        return CustAccount;
    }

    public void setCustAccount(String custAccount) {
        CustAccount = custAccount;
    }

    public String getCheckNumber() {
        return CheckNumber;
    }

    public void setCheckNumber(String checkNumber) {
        CheckNumber = checkNumber;
    }

    public String getMaturityDate() {
        return MaturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        MaturityDate = maturityDate;
    }

    public String getAmountCurCredit() {
        return AmountCurCredit;
    }

    public void setAmountCurCredit(String amountCurCredit) {
        AmountCurCredit = amountCurCredit;
    }

    public check_Model(String custAccount, String checkNumber, String maturityDate, String amountCurCredit) {
        CustAccount = custAccount;
        CheckNumber = checkNumber;
        MaturityDate = maturityDate;
        AmountCurCredit = amountCurCredit;
    }
}
