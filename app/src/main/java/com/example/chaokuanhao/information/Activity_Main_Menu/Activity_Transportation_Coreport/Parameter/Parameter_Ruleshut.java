package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter;

/**
 * Created by chaokuanhao on 26/11/2017.
 */

public class Parameter_Ruleshut {
    private String mLat;
    private String mLng;
    private String mPlace;
    private String mNumber;

    public Parameter_Ruleshut( String a, String b, String c, String d ){
        mLat = a;
        mLng = b;
        mPlace = c;
        mNumber = d;
    }

    public String getmLat() {
        return mLat;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public String getmNumber() {

        return mNumber;
    }

    public String getmLng() {
        return mLng;
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

    public void setmPlace(String mPlace) {
        this.mPlace = mPlace;
    }
}
