package com.example.interim;

public class OfferModelClass {

    private int companyImg;
    private String offerName, companyName, offerInfos;

    public OfferModelClass(int companyImg, String offerName, String companyName, String offerInfos) {
        this.companyImg = companyImg;
        this.offerName = offerName;
        this.companyName = companyName;
        this.offerInfos = offerInfos;
    }

    public int getCompanyImg() {
        return companyImg;
    }

    public void setCompanyImg(int companyImg) {
        this.companyImg = companyImg;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOfferInfos() {
        return offerInfos;
    }

    public void setOfferInfos(String offerInfos) {
        this.offerInfos = offerInfos;
    }
}
