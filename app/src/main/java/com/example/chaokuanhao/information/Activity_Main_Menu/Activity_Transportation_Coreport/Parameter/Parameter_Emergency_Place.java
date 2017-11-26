package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter;

/**
 * Created by chaokuanhao on 26/11/2017.
 */

public class Parameter_Emergency_Place {
    private String mLat;
    private String mlng;
    private String mAddress;
    private String mPlaceRegion;
    private String mCellPhone;
    private String mNameOfTheMainAdapter;
    private String mAdministrator;
    private String mNumber;
    private String mPhone;
    private String mCountry;
    private String mNumberOfPeople;

    public Parameter_Emergency_Place( String a, String b, String c, String d, String e, String f, String g, String  h, String i, String j, String k ){
        mLat = a;
        mlng = b;
        mAddress = c;
        mPlaceRegion = d;
        mCellPhone = e;
        mNameOfTheMainAdapter = f;
        mAdministrator = g;
        mNumber = h;
        mPhone = i;
        mCountry = j;
        mNumberOfPeople = k;
    }

    public String getmLat() {
        return mLat;
    }

    public String getMlng() {
        return mlng;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmPlaceRegion() {
        return mPlaceRegion;
    }

    public String getmCellPhone() {
        return mCellPhone;
    }

    public String getmNameOfTheMainAdapter() {
        return mNameOfTheMainAdapter;
    }

    public String getmAdministrator() {
        return mAdministrator;
    }

    public String getmNumber() {
        return mNumber;
    }

    public String getmPhone() {
        return mPhone;
    }

    public String getmCountry() {
        return mCountry;
    }

    public String getmNumberOfPeople() {
        return mNumberOfPeople;
    }

    public void setmLat(String mLat) {
        this.mLat = mLat;
    }

    public void setMlng(String mlng) {
        this.mlng = mlng;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public void setmPlaceRegion(String mPlaceRegion) {
        this.mPlaceRegion = mPlaceRegion;
    }

    public void setmCellPhone(String mCellPhone) {
        this.mCellPhone = mCellPhone;
    }

    public void setmNameOfTheMainAdapter(String mNameOfTheMainAdapter) {
        this.mNameOfTheMainAdapter = mNameOfTheMainAdapter;
    }

    public void setmAdministrator(String mAdministrator) {
        this.mAdministrator = mAdministrator;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public void setmNumberOfPeople(String mNumberOfPeople) {
        this.mNumberOfPeople = mNumberOfPeople;
    }
}
