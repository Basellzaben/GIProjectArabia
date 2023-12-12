package com.cds_jo.GalaxySalesApp.assist;


public class Customers {
    String LAT;   String LONG;

    String Nm;
    String Address;
    String Mobile;
    String Acc;
    String No;
    String IDN;
    String Location;

    public Customers() {

    }

    public String getLAT() {
        return LAT;
    }

    public void setLAT(String LAT) {
        this.LAT = LAT;
    }

    public String getLONG() {
        return LONG;
    }

    public void setLONG(String LONG) {
        this.LONG = LONG;
    }

    public String getNm() {
        return Nm;
    }

    public void setNm(String nm) {
        Nm = nm;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAcc() {
        return Acc;
    }

    public void setAcc(String acc) {
        Acc = acc;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getIDN() {
        return IDN;
    }

    public void setIDN(String IDN) {
        this.IDN = IDN;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public Customers(String LAT, String LONG, String nm, String address, String mobile, String acc, String no, String IDN, String location) {
        this.LAT = LAT;
        this.LONG = LONG;
        Nm = nm;
        Address = address;
        Mobile = mobile;
        Acc = acc;
        No = no;
        this.IDN = IDN;
        Location = location;
    }
}
