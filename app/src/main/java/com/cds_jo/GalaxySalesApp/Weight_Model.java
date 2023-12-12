package com.cds_jo.GalaxySalesApp;

public class Weight_Model {

    public Weight_Model() {
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    String weight,count;

    public Weight_Model(String weight, String count) {
        this.weight = weight;
        this.count = count;
    }
}
