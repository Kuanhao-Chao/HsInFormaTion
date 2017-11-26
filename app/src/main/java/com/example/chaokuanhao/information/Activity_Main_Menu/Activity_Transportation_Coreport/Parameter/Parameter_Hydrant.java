package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter;

/**
 * Created by chaokuanhao on 26/11/2017.
 */

public class Parameter_Hydrant {
    private String mLat;
    private String mLng;
    private String mType;
    private String mNumber;
    private String mPlace;

    public Parameter_Hydrant( String a, String b, String c, String d, String e){
        mLat = a;
        mLng = b;
        mType = c;
        mNumber = d;
        mPlace = d;
    }

    public String getmLat() {
        return mLat;
    }

    public String getmLng() {
        return mLng;
    }

    public String getmType() {
        return mType;
    }

    public String getmNumber() {
        return mNumber;
    }

    public String getmPlace() {
        return mPlace;
    }

    public void setmLat(String mLat) {
        this.mLat = mLat;
    }

    public void setmLng(String mLng) {
        this.mLng = mLng;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public void setmPlace(String mPlace) {
        this.mPlace = mPlace;
    }
}
