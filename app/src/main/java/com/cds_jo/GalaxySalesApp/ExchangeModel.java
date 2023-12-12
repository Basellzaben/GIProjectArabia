package com.cds_jo.GalaxySalesApp;

public class ExchangeModel {

    String no,namedtl,net,desc,flag,flagnum;

    public ExchangeModel() {

                           }

    public ExchangeModel(String no, String namedtl, String net, String desc, String flag,String flagnum) {
        this.no = no;
        this.namedtl = namedtl;
        this.net = net;
        this.desc = desc;
        this.flag = flag;
        this.flagnum=flagnum;
    }

    public String getFlagnum() {
        return flagnum;
    }

    public void setFlagnum(String flagnum) {
        this.flagnum = flagnum;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getnamedtl() {
        return namedtl;
    }

    public void setnamedtl(String namedtl) {
        this.namedtl = namedtl;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
