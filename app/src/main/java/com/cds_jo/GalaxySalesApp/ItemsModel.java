package com.cds_jo.GalaxySalesApp;

public class ItemsModel {

    String SubjectID,SubjectDsec,ItemKeys;

    public String getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(String subjectID) {
        SubjectID = subjectID;
    }

    public String getSubjectDsec() {
        return SubjectDsec;
    }

    public void setSubjectDsec(String subjectDsec) {
        SubjectDsec = subjectDsec;
    }

    public String getItemKeys() {
        return ItemKeys;
    }

    public void setItemKeys(String itemKeys) {
        ItemKeys = itemKeys;
    }

    public ItemsModel() {
    }

    public ItemsModel(String subjectID, String subjectDsec, String itemKeys) {
        SubjectID = subjectID;
        SubjectDsec = subjectDsec;
        ItemKeys = itemKeys;
    }
}
